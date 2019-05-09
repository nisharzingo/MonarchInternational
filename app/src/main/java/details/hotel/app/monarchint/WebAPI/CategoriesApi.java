package details.hotel.app.monarchint.WebAPI;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.Foods;
import details.hotel.app.monarchint.Model.RestaurantCategories;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoriesApi {

    @POST("RestaurantCategories")
    Call<RestaurantCategories> addCategories(@Header("Authorization") String authKey, @Body RestaurantCategories body);

    @PUT("RestaurantCategories/{id}")
    Call<RestaurantCategories> updateCategories(@Header("Authorization") String authKey, @Path("id") int id, @Body RestaurantCategories body);

    @GET("RestaurantCategories/{id}")
    Call<RestaurantCategories> getCategories(@Header("Authorization") String authKey, @Path("id") int id);

    @DELETE("RestaurantCategories/{id}")
    Call<RestaurantCategories> deleteCategory(@Header("Authorization") String authKey, @Path("id") int id);

    @GET("RestaurantCategories/GetAllCategoriesByRestaurantId/{RestaurantId}")
    Call<ArrayList<RestaurantCategories>> getCategoryByRestId(@Header("Authorization") String authKey, @Path("RestaurantId") int id);

    @GET("Foods/GetFoodByCategoryId/{CategoryId}")
    Call<ArrayList<Foods>> getFoodByCate(@Header("Authorization") String authKey, @Path("CategoryId") int id);

    @GET("RestaurantCategories")
    Call<ArrayList<RestaurantCategories>> getCategory(@Header("Authorization") String authKey);
}
