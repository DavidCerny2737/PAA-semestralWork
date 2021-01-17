package cz.tul.nutritiontracker.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodContent {

    private static final String TAG = FoodContent.class.getSimpleName();

    public static List<HintDTO> foodList = new ArrayList<>();

    public static Map<String, HintDTO> foodMap = new HashMap<>();

    public static int totalCount;

    public static void init(List<HintDTO> foods){
        foodList = foods;
        totalCount = foods.size();
        foodMap = new HashMap<>();
        for(HintDTO food : foodList){
            foodMap.put(food.getFood().getId(), food);
        }
    }

    private static void addItem(HintDTO item) {
        foodList.add(item);
        foodMap.put(String.valueOf(item.getFood().getId()), item);
    }

    private static HintDTO createFoodItem(int position) {
        return foodList.get(position);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

}
