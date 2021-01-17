package cz.tul.nutritiontracker.activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cz.tul.nutritiontracker.R;
import cz.tul.nutritiontracker.adapter.CustomFoodListAdapter;
import cz.tul.nutritiontracker.dto.HintDTO;
import cz.tul.nutritiontracker.dto.PaginationDTO;
import cz.tul.nutritiontracker.dto.FoodContent;
import cz.tul.nutritiontracker.entity.Food;
import cz.tul.nutritiontracker.entity.Nutrition;
import cz.tul.nutritiontracker.fragment.WeightSelectDialogFragment;
import cz.tul.nutritiontracker.mapper.FoodHintMapper;
import cz.tul.nutritiontracker.restservice.EdamanService;
import cz.tul.nutritiontracker.restservice.EdamanServiceBean;
import cz.tul.nutritiontracker.restservice.ImageDownloadAsyncTask;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends MainActivity implements FoodReceivingActivity, WeightSelectDialogFragment.NoticeDialogListenerWeightSelect {

    private static final String TAG = ItemListActivity.class.getSimpleName();

    private List<HintDTO> food;
    private PaginationDTO pagination;
    private String errorMessage;

    private EdamanService edamanService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.app_name);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.menu);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        /**
         * Whether or not the activity is in two-pane mode, i.e. running on a tablet
         * device.
         */
        String searchQuery = getIntent().getStringExtra(getString(R.string.menu_search_query));
        Log.i("ItemListActivity: ", searchQuery);

        if(food == null) {
            progressDialog = new ProgressDialog(ItemListActivity.this);
            progressDialog.setMessage(getString(R.string.food_list_loading_label));
            progressDialog.setCancelable(false);
            progressDialog.setInverseBackgroundForced(false);
            progressDialog.show();

            this.edamanService = EdamanServiceBean.getInstance();
            edamanService.searchForFoods(searchQuery, this);
        } else {
            View recyclerView = findViewById(R.id.item_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);
        }
    }

    public void nextPageClicked(View v){
        progressDialog = new ProgressDialog(ItemListActivity.this);
        progressDialog.setMessage(getString(R.string.food_list_loading_label));
        progressDialog.setCancelable(false);
        progressDialog.setInverseBackgroundForced(false);
        progressDialog.show();

        if(edamanService == null)
            edamanService = EdamanServiceBean.getInstance();
        if(pagination != null)
            edamanService.foodLinkPagination(pagination.getPaginationLink().getNextPageUri(), this);
        else {
            Toast.makeText(this, getString(R.string.food_list_pagination_null_toast), Toast.LENGTH_LONG).show();
            progressDialog.hide();
        }
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        FoodContent.init(food);
        recyclerView.setAdapter(new CustomFoodListAdapter(FoodContent.foodList, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public List<HintDTO> getFoods() {
        return food;
    }

    public void setFoods(List<HintDTO> foods, PaginationDTO pagination) {
        if(progressDialog != null)
            progressDialog.hide();

        this.food = foods;
        Button nextPageBtn = findViewById(R.id.next_page_btn);
        if(pagination == null || pagination.getPaginationLink().getNextPageUri() == null) {
            nextPageBtn.setVisibility(View.INVISIBLE);
        } else {
            this.pagination = pagination;
            nextPageBtn.setVisibility(View.VISIBLE);
        }
        ImageDownloadAsyncTask asyncTask = ImageDownloadAsyncTask.getInstance();

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String error) {
        this.errorMessage = error;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null)
            progressDialog.dismiss();
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