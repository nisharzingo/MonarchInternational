package details.hotel.app.monarchint.WebAPI;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.AvailabiltyCheckPostData;
import details.hotel.app.monarchint.Model.Rates;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ZingoHotels Tech on 11-12-2018.
 */

public interface RateApi {

    @GET("Rates/GetRatesByCategoryId/{CategoryId}")
    Call<ArrayList<Rates>> getRatesByCategoryId(@Header("Authorization") String authKey, @Path("CategoryId") int id);

    @POST("Rates/GetRatesBetweenTheDatesByHotelIdAndRoomCategoryId")
    Call<ArrayList<Rates>> getRatesForDate(@Header("Authorization") String authKey, @Body AvailabiltyCheckPostData body);

}
