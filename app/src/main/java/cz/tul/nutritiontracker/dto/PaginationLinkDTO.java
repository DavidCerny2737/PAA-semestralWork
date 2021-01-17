package cz.tul.nutritiontracker.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaginationLinkDTO {

    @Expose
    @SerializedName("title")
    private String title;

    @Expose
    @SerializedName("href")
    private String nextPageUri;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNextPageUri() {
        return nextPageUri;
    }

    public void setNextPageUri(String nextPageUri) {
        this.nextPageUri = nextPageUri;
    }
}
