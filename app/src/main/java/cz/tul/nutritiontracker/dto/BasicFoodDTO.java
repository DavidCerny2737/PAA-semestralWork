package cz.tul.nutritiontracker.dto;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

import cz.tul.nutritiontracker.dto.enumerate.Nutrient;

public class BasicFoodDTO {

    @Expose
    @SerializedName("foodId")
    private String id;

    @Expose
    @SerializedName("uri")
    private String uri;

    @Expose
    @SerializedName("label")
    private String name;

    @Expose
    @SerializedName("nutrients")
    private Map<Nutrient, Double> nutrients;

    @Expose
    @SerializedName("category")
    private String category;

    @Expose
    @SerializedName("categoryLabel")
    private String categoryLabel;

    @Expose
    @SerializedName("image")
    private String imageUri;

    private Bitmap imageBitmap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Nutrient, Double> getNutrients() {
        return nutrients;
    }

    public void setNutrients(Map<Nutrient, Double> nutrients) {
        this.nutrients = nutrients;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryLabel() {
        return categoryLabel;
    }

    public void setCategoryLabel(String categoryLabel) {
        this.categoryLabel = categoryLabel;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}
