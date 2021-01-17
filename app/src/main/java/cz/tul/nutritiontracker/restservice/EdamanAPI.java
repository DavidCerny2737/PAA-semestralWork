package cz.tul.nutritiontracker.restservice;

import cz.tul.nutritiontracker.dto.FoodDTO;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface EdamanAPI {

    @GET("parser")
    Single<FoodDTO> searchForFoods(@Query("app_id") String appId, @Query("app_key") String apiKey,
                                   @Query("ingr") String keyword);

    @GET
    Single<FoodDTO> searchForFoodWithLink(@Url String uri);

}
