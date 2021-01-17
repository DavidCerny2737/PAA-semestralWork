package cz.tul.nutritiontracker.restservice;

import cz.tul.nutritiontracker.activity.FoodReceivingActivity;

public interface EdamanService {

    void searchForFoods(String query, FoodReceivingActivity caller);

    void foodLinkPagination(String uri, FoodReceivingActivity caller);

    void findFoodById(String foodId);

    void dispose();
}
