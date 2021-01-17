package cz.tul.nutritiontracker.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.util.Date;

public class HintDTO {

    @Expose
    @SerializedName("food")
    private BasicFoodDTO food;

    private Integer weight;

    private Date dateOfInsertion;

    public BasicFoodDTO getFood() {
        return food;
    }

    public void setFood(BasicFoodDTO food) {
        this.food = food;
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
}
