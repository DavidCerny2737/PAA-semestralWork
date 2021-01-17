package cz.tul.nutritiontracker.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaginationDTO {

    @Expose
    @SerializedName("next")
    private PaginationLinkDTO paginationLink;

    public PaginationLinkDTO getPaginationLink() {
        return paginationLink;
    }

    public void setPaginationLink(PaginationLinkDTO paginationLink) {
        this.paginationLink = paginationLink;
    }
}
