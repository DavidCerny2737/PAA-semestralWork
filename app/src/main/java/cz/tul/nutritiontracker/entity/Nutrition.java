package cz.tul.nutritiontracker.entity;

import com.orm.SugarRecord;

import java.util.Objects;

public class Nutrition extends SugarRecord{

    public static final String FOOD_ALIAS = "FOOD";

    private String name;
    private Double value;
    private Food food;

    public Nutrition(){}

    public Nutrition(String name, Double value, Food food) {
        this.name = name;
        this.value = value;
        this.food = food;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nutrition nutrition = (Nutrition) o;
        return Objects.equals(name, nutrition.name) &&
                Objects.equals(value, nutrition.value) &&
                Objects.equals(food, nutrition.food);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value, food);
    }
}
