package details.hotel.app.monarchint.WebAPI;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.Offers;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OfferAPI {

    @GET("Offers/GetOffersByHotelId/{HotelId}")
    Call<ArrayList<Offers>> getOffersByHotelId(@Header("Authorization") String authKey, @Path("HotelId") int HotelId);

    @POST("Offers")
    Call<Offers> postOffers(@Header("Authorization") String authKey, @Body Offers offers);

    @GET("Offers")
    Call<ArrayList<Offers>> getOffers(@Header("Authorization") String authKey);

    @PUT("Offers/{id}")
    Call<Offers> updateOffers(@Header("Authorization") String authKey, @Path("id") int id,@Body Offers restaurants);

    @GET("Offers/{id}")
    Call<Offers> getOffersById(@Header("Authorization") String authKey, @Path("id") int id);

    @DELETE("Offers/{id}")
    Call<Offers> deleteOffers(@Header("Authorization") String authKey, @Path("id") int id);
}
