package details.hotel.app.monarchint.WebAPI;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.ReferCodeModel;
import details.hotel.app.monarchint.Model.TravellerAgentProfiles;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AgentProfileAPI {

    @POST("TravellerAgentProfile/GetProfileByUserNameAndPassword")
    Call<ArrayList<TravellerAgentProfiles>> loginAgent(@Header("Authorization") String authKey, @Body TravellerAgentProfiles body);

    @POST("TravellerAgentProfile/GetProfileByReferralCode")
    Call<ArrayList<TravellerAgentProfiles>> getProfileByReferCode(@Header("Authorization") String authKey,@Body ReferCodeModel body);

    @POST("TravellerAgentProfile/GetTravellerAgentProfilesByReferralCodeUsed")
    Call<ArrayList<TravellerAgentProfiles>> getProfilesByUsedReferCode(@Header("Authorization") String authKey,@Body ReferCodeModel body);

    @GET("TravellerAgentProfiles/{id}")
    Call<TravellerAgentProfiles> getProfileByID(@Header("Authorization") String authKey,@Path("id") int id );

    @GET("TravellerAgentProfile/GetProfileByName/{UserName}")
    Call<String> getProfileByUserName(@Header("Authorization") String authKey,@Path("UserName") String UserName);

    @POST("TravellerAgentProfile/GetProfileByPhone")
    Call<ArrayList<TravellerAgentProfiles>> getProfileByPhone(@Header("Authorization") String authKey,@Body TravellerAgentProfiles body);


    @POST("TravellerAgentProfile/GetProfileByEmail")
    Call<String> getProfileByEmail(@Header("Authorization") String authKey,@Body TravellerAgentProfiles body);

    @POST("TravellerAgentProfiles")
    Call<TravellerAgentProfiles> loginApi(@Header("Authorization") String authKey, @Body TravellerAgentProfiles body);

}
