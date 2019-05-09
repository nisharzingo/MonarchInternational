package details.hotel.app.monarchint.WebAPI;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.HotelImage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ZingoHotels Tech on 10-12-2018.
 */

public interface HotelImagesAPI {

    @POST("HotelImagesAPI")
    Call<HotelImage> uploadImages(@Header("Authorization") String authKey, @Body HotelImage body);

    @GET("Hotels/GetHotelImagesByHotelId/{HotelId}")
    Call<ArrayList<HotelImage>> getHotelImages(@Header("Authorization") String authKey, @Path("HotelId") int HotelId);
}
