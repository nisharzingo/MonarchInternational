package details.hotel.app.monarchint.WebAPI;

import details.hotel.app.monarchint.Model.Traveller;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TravellerAPI {

    @POST("Travellers")
    Call<Traveller> addTraveler(@Header("Authorization") String authKey, @Body Traveller body);

    @GET("Travellers/{id}")
    Call<Traveller> getTravellerDetails(@Header("Authorization") String authKey, @Path("id") int id);
}
