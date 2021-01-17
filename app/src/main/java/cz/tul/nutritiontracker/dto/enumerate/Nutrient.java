package cz.tul.nutritiontracker.dto.enumerate;

public enum Nutrient {

    CA("Calcium", "mg"), CHOCDF("Carbs", "g"), CHOLE("Cholesterol", "mg"),
    FAMS("Monounsaturated", "g"), FAPU("Polyunsaturated", "g"), FASAT("Saturated", "g"),
    FAT("Fat", "g"), FATRN("Trans", "g"), FE("Iron", "mg"), FIBTG("Fiber", "g"),
    FOLDFE("Folate (Equivalent) ", "aeg"), K("Potassium", "mg"), MG("Magnesium", "mg"),
    NA("Sodium", "mg"), ENERC_KCAL("Energy", "kcal"), NIA("Niacin (B3)", "mg"),
    P("Phosphorus", "mg"), PROCNT("Protein", "g"), RIBF("Riboflavin (B2)", "mg"),
    SUGAR("Sugars", "g"), THIA("Thiamin (B1)", "mg"), TOCPHA("Vitamin E", "mg"),
    VITA_RAE("Vitamin A", "aeg"), VITB12 ("Vitamin B12", "aeg"), VITB6A("Vitamin B6", "mg"),
    VITC("Vitamin C", "mg"), VITD("Vitamin D", "aeg"), VITK1("Vitamin K", "aeg");

    private String name;
    private String unit;

    Nutrient(String name, String unit){
        this.name = name;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
}
