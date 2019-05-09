package details.hotel.app.monarchint.WebAPI;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.Restaurants;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface RestaurantAPI {

    @GET("Restaurant/GetRestaurantByRestaurantProfileId/{RestaurantProfileId}")
    Call<ArrayList<Restaurants>> getRestaurantByProfileId(@Header("Authorization") String authKey, @Path("RestaurantProfileId") int RestaurantProfileId);

    @GET("RestaurantImages/GetRestaurantImageByHotelId/{HotelId}")
    Call<ArrayList<Restaurants>> getRestaurantByHotelId(@Header("Authorization") String authKey, @Path("HotelId") int HotelId);

    @GET("Restaurant/GetRestaurantByHotelId/{HotelId}")
    Call<ArrayList<Restaurants>> getRestaurantWithoutImageByHotelId(@Header("Authorization") String authKey, @Path("HotelId") int HotelId);
}
