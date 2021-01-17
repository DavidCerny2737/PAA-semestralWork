package cz.tul.nutritiontracker.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

import cz.tul.nutritiontracker.R;
import cz.tul.nutritiontracker.activity.DiaryActivity;
import cz.tul.nutritiontracker.activity.DiaryDetailActivity;
import cz.tul.nutritiontracker.activity.ItemDetailActivity;
import cz.tul.nutritiontracker.activity.ItemListActivity;
import cz.tul.nutritiontracker.dto.HintDTO;
import cz.tul.nutritiontracker.fragment.WeightSelectDialogFragment;

public class CustomFoodListAdapter extends RecyclerView.Adapter<CustomFoodListAdapter.ViewHolder> {

    private List<HintDTO> items;
    private LayoutInflater inflater;
    private Context context;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    private final View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(context instanceof ItemListActivity) {
                String itemId = (String) v.getTag();
                Context context = v.getContext();
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtra(ItemDetailActivity.ARG_ITEM_ID, String.valueOf(itemId));
                context.startActivity(intent);
            } else if(context instanceof DiaryActivity){
                Context context = v.getContext();
                String itemId = (String) v.getTag();
                Intent intent = new Intent(context, DiaryDetailActivity.class);
                intent.putExtra(DiaryDetailActivity.ARG_ITEM_ID, itemId);
                context.startActivity(intent);
            }
        }

    };

    private static final int ROW_HEIGHT_NO_IMAGE = 100;

    public CustomFoodListAdapter(List<HintDTO> dataSet, Context context){
        inflater = LayoutInflater.from(context);
        this.items = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final HintDTO item = items.get(position);

        if(item.getFood().getImageBitmap() != null){
            holder.getImageView().setImageBitmap(item.getFood().getImageBitmap());
            holder.getView().setMinimumHeight(item.getFood().getImageBitmap().getHeight());
        } else {
            holder.getView().setMinimumHeight(ROW_HEIGHT_NO_IMAGE);
        }

        holder.getCategoryView().setText(item.getFood().getCategory());
        holder.getNameView().setText(item.getFood().getName().toUpperCase());
        holder.setFoodId(item.getFood().getId());

        ImageButton addBtn = (ImageButton) holder.getView().findViewById(R.id.addButton);
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                WeightSelectDialogFragment dialog = new WeightSelectDialogFragment();
                dialog.setFoodId(item.getFood().getId());
                dialog.show(((FragmentActivity) context).getSupportFragmentManager(), WeightSelectDialogFragment.class.getSimpleName());
            }

        });

        if(this.context instanceof DiaryActivity){
            holder.getInsertedView().setText(DATE_FORMAT.format(item.getDateOfInsertion()));
            holder.getWeightView().setText(String.valueOf(item.getWeight()));
            holder.getImageButton().setVisibility(View.INVISIBLE);
        } else {
            holder.getInsertedView().setVisibility(View.INVISIBLE);
            holder.getInsertedLabelView().setVisibility(View.INVISIBLE);

            holder.getWeightView().setVisibility(View.INVISIBLE);
            holder.getWeightLabelView().setVisibility(View.INVISIBLE);
        }

        holder.getView().setOnClickListener(onClickListener);
        holder.getView().setTag(item.getFood().getId());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView nameView;
        private TextView categoryView;
        private TextView insertedLabelView;
        private TextView insertedView;
        private TextView weightLabelView;
        private TextView weightView;
        private ImageButton imageButton;
        private String foodId;
        private View view;

        public ViewHolder(View view){
            super(view);
            this.view = view;
            this.imageView = view.findViewById(R.id.food_image);
            this.nameView = view.findViewById(R.id.food_name);
            this.categoryView = view.findViewById(R.id.food_category);
            this.imageButton = view.findViewById(R.id.addButton);
            this.insertedView = view.findViewById(R.id.food_inserted);
            this.insertedLabelView = view.findViewById(R.id.food_inserted_label);
            this.weightView = view.findViewById(R.id.food_weight);
            this.weightLabelView = view.findViewById(R.id.food_weight_label);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }

        public TextView getNameView() {
            return nameView;
        }

        public void setNameView(TextView nameView) {
            this.nameView = nameView;
        }

        public TextView getCategoryView() {
            return categoryView;
        }

        public void setCategoryView(TextView categoryView) {
            this.categoryView = categoryView;
        }

        public String getFoodId() {
            return foodId;
        }

        public void setFoodId(String foodId) {
            this.foodId = foodId;
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }

        public ImageButton getImageButton() {
            return imageButton;
        }

        public void setImageButton(ImageButton imageButton) {
            this.imageButton = imageButton;
        }

        public TextView getInsertedView() {
            return insertedView;
        }

        public void setInsertedView(TextView insertedView) {
            this.insertedView = insertedView;
        }

        public TextView getWeightView() {
            return weightView;
        }

        public void setWeightView(TextView weightView) {
            this.weightView = weightView;
        }

        public TextView getInsertedLabelView() {
            return insertedLabelView;
        }

        public void setInsertedLabelView(TextView insertedLabelView) {
            this.insertedLabelView = insertedLabelView;
        }

        public TextView getWeightLabelView() {
            return weightLabelView;
        }

        public void setWeightLabelView(TextView weightLabelView) {
            this.weightLabelView = weightLabelView;
        }
    }
}
