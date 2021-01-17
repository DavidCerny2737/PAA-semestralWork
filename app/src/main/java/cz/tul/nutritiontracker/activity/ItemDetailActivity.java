package cz.tul.nutritiontracker.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;

import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.Map;

import cz.tul.nutritiontracker.R;
import cz.tul.nutritiontracker.dto.FoodContent;
import cz.tul.nutritiontracker.dto.HintDTO;
import cz.tul.nutritiontracker.dto.enumerate.Nutrient;
import cz.tul.nutritiontracker.entity.Food;
import cz.tul.nutritiontracker.entity.Nutrition;
import cz.tul.nutritiontracker.fragment.WeightSelectDialogFragment;
import cz.tul.nutritiontracker.mapper.FoodHintMapper;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends MainActivity implements WeightSelectDialogFragment.NoticeDialogListenerWeightSelect {

    public static final String ARG_ITEM_ID = "item_id";
    private static final String TAG = ItemDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_item_detail);
        setContentView(R.layout.activity_item_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.menu);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        final String foodId = getIntent().getStringExtra(ARG_ITEM_ID);
        HintDTO foodItem = null;
        try {
            foodItem = FoodContent.foodMap.get(foodId);
        } catch (Exception e){
            Log.e(TAG, "detail error", e);
        }

        if (foodItem != null) {
            ((TextView) findViewById(R.id.foodDetailTitle)).setText(foodItem.getFood().getName().toUpperCase());
            ((ImageView) findViewById(R.id.foodDetailImage)).setImageBitmap(foodItem.getFood().getImageBitmap());
            ((TextView) findViewById(R.id.foodDetailCategoryLabel)).setText(getString(R.string.food_detail_category_itemize_placeholder_label,
                    foodItem.getFood().getCategory()));

            findViewById(R.id.foodDetailInsertedLayout).setVisibility(View.GONE);
            findViewById(R.id.foodDetailWeightLayout).setVisibility(View.GONE);

            TableLayout table = (TableLayout) findViewById(R.id.foodDetailNutrientTable);
            TableRow tr;
            TextView nutrientView, valueView;
            for(Map.Entry<Nutrient, Double> nutrientEntry: foodItem.getFood().getNutrients().entrySet()){
                tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                nutrientView = new TextView(this);
                nutrientView.setText(nutrientEntry.getKey().getName());
                nutrientView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                nutrientView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                valueView = new TextView(this);
                valueView.setText(String.format("%.2f", nutrientEntry.getValue()) + " " + nutrientEntry.getKey().getUnit());
                valueView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                valueView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                tr.addView(nutrientView);
                tr.addView(valueView);
                table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                WeightSelectDialogFragment dialog = new WeightSelectDialogFragment();
                dialog.setFoodId(foodId);
                dialog.show(getSupportFragmentManager(), WeightSelectDialogFragment.class.getSimpleName());
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int weight) {
        HintDTO food = FoodContent.foodMap.get(((WeightSelectDialogFragment) dialog).getFoodId());
        if(food != null) {
            food.setWeight(weight);
            Food newFood = FoodHintMapper.map(food);
            newFood.setDateOfInsertion(new Date());
            newFood.save();

            Nutrition.saveInTx(newFood.getNutritions());
            Log.i(TAG, "Food succesfully saved to DB.");
        } else {
            Log.e(TAG, "Food is null, aborting insertion.");
            Toast.makeText(this, getString(R.string.food_insert_null_error), Toast.LENGTH_LONG).show();
        }
        dialog.dismiss();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}