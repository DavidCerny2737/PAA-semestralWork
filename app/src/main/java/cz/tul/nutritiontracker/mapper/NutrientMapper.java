package cz.tul.nutritiontracker.mapper;

import cz.tul.nutritiontracker.entity.Nutrition;

public class NutrientMapper {

    public static Nutrition map(String name, Double value){
        if(name == null || name.equals("") || value == null)
            return null;

        Nutrition target = new Nutrition();
        target.setName(name);
        target.setValue(value);
        return target;
    }



}
