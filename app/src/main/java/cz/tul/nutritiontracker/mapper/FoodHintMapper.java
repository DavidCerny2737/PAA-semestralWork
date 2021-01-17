package cz.tul.nutritiontracker.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.tul.nutritiontracker.dto.BasicFoodDTO;
import cz.tul.nutritiontracker.dto.HintDTO;
import cz.tul.nutritiontracker.dto.enumerate.Nutrient;
import cz.tul.nutritiontracker.entity.Food;
import cz.tul.nutritiontracker.entity.Nutrition;
import cz.tul.nutritiontracker.restservice.ImageDownloadAsyncTask;

public class FoodHintMapper {

    public static HintDTO map(Food source){
        if(source == null)
            return null;

        HintDTO target = new HintDTO();
        BasicFoodDTO basic = new BasicFoodDTO();

        target.setWeight(source.getWeight());
        target.setDateOfInsertion(source.getDateOfInsertion());
        target.setFood(basic);
        basic.setImageUri(source.getImageUri());
        ImageDownloadAsyncTask task = ImageDownloadAsyncTask.getInstance(Collections.singletonList(target));
        task.run();

        basic.setCategory(source.getCategory());
        basic.setCategoryLabel(source.getCategoryLabel());
        basic.setId(source.getFoodId());
        basic.setName(source.getName());
        basic.setUri(source.getUri());

        Map<Nutrient, Double> nutrients = new HashMap<>();
        if(source.getNutritions() != null && source.getNutritions().size() != 0){
            for(Nutrition nutrition : source.getNutritions()){
                nutrients.put(Nutrient.valueOf(nutrition.getName()), nutrition.getValue());
            }
        }
        basic.setNutrients(nutrients);
        task.awaitTermination();
        return target;
    }

    public static List<HintDTO> mapList(List<Food> source){
        if(source == null || source.size() == 0)
            return Collections.emptyList();

        List<HintDTO> target = new ArrayList<>();
        for(Food food : source){
            target.add(map(food));
        }
        return target;
    }

    public static Food map(HintDTO source){
        if(source == null)
            return null;

        Food target = new Food();
        target.setWeight(source.getWeight());

        BasicFoodDTO basic = source.getFood();
        target.setCategory(basic.getCategory());
        target.setCategoryLabel(basic.getCategoryLabel());
        target.setFoodId(basic.getId());
        target.setImageUri(basic.getImageUri());
        target.setName(basic.getName());
        target.setUri(basic.getUri());
        target.setDateOfInsertion(source.getDateOfInsertion());

        List<Nutrition> nutritionList = new ArrayList<>();
        Nutrition singleItem;
        if(basic.getNutrients() != null && basic.getNutrients().size() != 0){
            for(Map.Entry<Nutrient, Double> entry : basic.getNutrients().entrySet()) {
                singleItem = new Nutrition();
                singleItem.setName(entry.getKey().name());
                singleItem.setValue(entry.getValue());
                singleItem.setFood(target);
                nutritionList.add(singleItem);
            }
        }
        target.setNutritions(nutritionList);
        return target;
    }

}
