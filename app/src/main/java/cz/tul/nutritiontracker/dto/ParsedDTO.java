package cz.tul.nutritiontracker.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParsedDTO {

    @Expose
    @SerializedName("food")
    private HintDTO hint;

    public HintDTO getFoods() {
        return hint;
    }

    public void setFoods(HintDTO foods) {
        this.hint = foods;
    }
}
