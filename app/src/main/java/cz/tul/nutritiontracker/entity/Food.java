package cz.tul.nutritiontracker.entity;

import android.content.Intent;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cz.tul.nutritiontracker.dto.enumerate.Nutrient;

public class Food extends SugarRecord {

    public static final String DATE_ALIAS = "DATE_OF_INSERTION";
    public static final String FOOD_ID_ALIAS = "FOOD_ID";

    private Long id;
    private String foodId;
    private String uri;
    private String name;
    private List<Nutrition> nutritions;
    private String category;
    private String categoryLabel;
    private String imageUri;
    private Integer weight;
    private Date dateOfInsertion;

    public Food() {}

    public Food(Long id, String foodId, String uri, String name, List<Nutrition> nutritions, String category, String categoryLabel, String imageUri, Integer weight, Date dateOfInsertion) {
        this.id = id;
        this.foodId = foodId;
        this.uri = uri;
        this.name = name;
        this.nutritions = nutritions;
        this.category = category;
        this.categoryLabel = categoryLabel;
        this.imageUri = imageUri;
        this.weight = weight;
        this.dateOfInsertion = dateOfInsertion;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
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

    public List<Nutrition> getNutritions() {
        return nutritions;
    }

    public void setNutritions(List<Nutrition> nutritions) {
        this.nutritions = nutritions;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Date getDateOfInsertion() {
        return dateOfInsertion;
    }

    public void setDateOfInsertion(Date dateOfInsertion) {
        this.dateOfInsertion = dateOfInsertion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(foodId, food.foodId) &&
                Objects.equals(weight, food.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(foodId, weight);
    }
}
