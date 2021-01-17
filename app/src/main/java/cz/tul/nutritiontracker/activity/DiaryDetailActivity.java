package cz.tul.nutritiontracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cz.tul.nutritiontracker.R;
import cz.tul.nutritiontracker.dto.FoodContent;
import cz.tul.nutritiontracker.dto.HintDTO;
import cz.tul.nutritiontracker.dto.enumerate.Nutrient;
import cz.tul.nutritiontracker.entity.Food;
import cz.tul.nutritiontracker.entity.Nutrition;
import cz.tul.nutritiontracker.fragment.USureDialogFragment;
import cz.tul.nutritiontracker.fragment.WeightSelectDialogFragment;
import cz.tul.nutritiontracker.mapper.FoodHintMapper;
import cz.tul.nutritiontracker.restservice.ImageDownloadAsyncTask;

public class DiaryDetailActivity extends MainActivity implements USureDialogFragment.NoticeDialogListenerUSure {

    public static final String ARG_ITEM_ID = "item_id";
    private static final String TAG = DiaryDetailActivity.class.getSimpleName();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private HintDTO foodItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_item_detail);
        setContentView(R.layout.activity_item_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.menu);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        final String foodId = getIntent().getStringExtra(ARG_ITEM_ID);
        try {
            Food entity = Select.from(Food.class).where(Condition.prop(Food.FOOD_ID_ALIAS).eq(foodId)).first();
            entity.setNutritions(Select.from(Nutrition.class)
                    .where(Condition.prop(Nutrition.FOOD_ALIAS).eq(entity.getId())).list());
            foodItem = FoodHintMapper.map(entity);

            ImageDownloadAsyncTask task = ImageDownloadAsyncTask.getInstance(Collections.singletonList(foodItem));
            task.run();
            task.awaitTermination();
        } catch (Exception e){
            Log.e(TAG, "detail error", e);
        }

        if (foodItem != null) {
            ((TextView) findViewById(R.id.foodDetailTitle)).setText(foodItem.getFood().getName().toUpperCase());
            ((ImageView) findViewById(R.id.foodDetailImage)).setImageBitmap(foodItem.getFood().getImageBitmap());
            ((TextView) findViewById(R.id.foodDetailCategoryLabel)).setText(getString(R.string.food_detail_category_itemize_placeholder_label,
                    foodItem.getFood().getCategory()));

            ((TextView) findViewById(R.id.foodDetailInserted)).setText(DATE_FORMAT.format(foodItem.getDateOfInsertion()));
            ((TextView) findViewById(R.id.foodDetailWeight)).setText(String.valueOf(foodItem.getWeight()));

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
                valueView.setText(String.format("%.2f",nutrientEntry.getValue()) + " " + nutrientEntry.getKey().getUnit());
                valueView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                valueView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

                tr.addView(nutrientView);
                tr.addView(valueView);
                table.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.delete));
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                USureDialogFragment dialog = new USureDialogFragment();
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
    public void onDialogPositiveClick(DialogFragment dialog) {
        Food original = Select.from(Food.class).where(Condition.prop(Food.FOOD_ID_ALIAS).eq(foodItem.getFood().getId())).first();
        List<Nutrition> nutritionList = Select.from(Nutrition.class).where(Condition.prop(Nutrition.FOOD_ALIAS).eq(original.getFoodId())).list();
        Nutrition.deleteInTx(nutritionList);
        Food.delete(original);

        Intent intent = new Intent(this, DiaryActivity.class);
        dialog.dismiss();
        startActivity(intent);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}
