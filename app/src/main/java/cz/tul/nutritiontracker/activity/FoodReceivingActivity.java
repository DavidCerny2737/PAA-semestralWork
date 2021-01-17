package cz.tul.nutritiontracker.activity;

import java.util.List;

import cz.tul.nutritiontracker.dto.HintDTO;
import cz.tul.nutritiontracker.dto.PaginationDTO;

public interface FoodReceivingActivity {

    public void setFoods(List<HintDTO> foods, PaginationDTO pagination);

    public void setErrorMessage(String errorMessage);

}
