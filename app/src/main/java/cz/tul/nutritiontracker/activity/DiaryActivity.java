package cz.tul.nutritiontracker.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import cz.tul.nutritiontracker.R;
import cz.tul.nutritiontracker.adapter.CustomFoodListAdapter;
import cz.tul.nutritiontracker.dto.FoodContent;
import cz.tul.nutritiontracker.dto.HintDTO;
import cz.tul.nutritiontracker.entity.Food;
import cz.tul.nutritiontracker.entity.Nutrition;
import cz.tul.nutritiontracker.mapper.FoodHintMapper;
import cz.tul.nutritiontracker.restservice.ImageDownloadAsyncTask;

public class DiaryActivity extends MainActivity {

    private static final int PAGE_SIZE = 22;

    private List<HintDTO> food;
    private int page = 0;
    private long totalCount;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.activity_diary_title);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.menu);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        if(food == null) {
            progressDialog = new ProgressDialog(DiaryActivity.this);
            progressDialog.setMessage(getString(R.string.food_list_loading_label));
            progressDialog.setCancelable(false);
            progressDialog.setInverseBackgroundForced(false);
            progressDialog.show();

            totalCount = Food.count(Food.class);
            if(totalCount > PAGE_SIZE) {
                List<Food> entityList = Select.from(Food.class).orderBy(Food.DATE_ALIAS + " desc").offset(String.valueOf(page))
                        .limit(String.valueOf(PAGE_SIZE)).list();
                food = FoodHintMapper.mapList(entityList);
            } else {
                List<Food> entityList = Select.from(Food.class).orderBy(Food.DATE_ALIAS + " desc").list();
                food = FoodHintMapper.mapList(entityList);
                findViewById(R.id.next_page_btn).setVisibility(View.INVISIBLE);
            }

            if(food.size() != 0){
                ImageDownloadAsyncTask task = ImageDownloadAsyncTask.getInstance(food);
                task.run();
                task.awaitTermination();
            }
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        progressDialog.hide();
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

    public void nextPageClicked(View v){
        progressDialog.show();

        page++;
        List<Food> entityList = Select.from(Food.class).orderBy(Food.DATE_ALIAS)
                .limit(page * PAGE_SIZE + "," + PAGE_SIZE).list();
        food = FoodHintMapper.mapList(entityList);

        if(food.size() != 0){
            ImageDownloadAsyncTask task = ImageDownloadAsyncTask.getInstance(food);
            task.run();
            task.awaitTermination();
        }

        if((page * PAGE_SIZE + food.size()) >= totalCount)
            findViewById(R.id.next_page_btn).setVisibility(View.INVISIBLE);

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        progressDialog.hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null)
            progressDialog.dismiss();
    }

}
