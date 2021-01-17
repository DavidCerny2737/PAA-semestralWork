package cz.tul.nutritiontracker.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodDTO {

    @Expose
    @SerializedName("text")
    private String query;

    @Expose
    @SerializedName("parsed")
    private List<HintDTO> parsed;

    @Expose
    @SerializedName("hints")
    private List<HintDTO> hints;

    @Expose
    @SerializedName("_links")
    private PaginationDTO pagination;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<HintDTO> getParsed() {
        return parsed;
    }

    public void setParsed(List<HintDTO> parsed) {
        this.parsed = parsed;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }

    public List<HintDTO> getHints() {
        return hints;
    }

    public void setHints(List<HintDTO> hints) {
        this.hints = hints;
    }
}
