package details.hotel.app.monarchint.WebAPI;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.RoomBookings;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RoomBookingAPI {

    @POST("RoomBookings")
    Call<RoomBookings> postBooking(@Header("Authorization") String authKey, @Body RoomBookings  body);

    @GET("RoomBookings/{id}")
    Call<RoomBookings> getBookingById(@Header("Authorization") String authKey,@Path("id") int id);

    @GET("RoomBookings/GetRoomBookingsByOTABookingId/{OTABookingId}")
    Call<ArrayList<RoomBookings>> getBookingByOTAId(@Header("Authorization") String authKey, @Path("id") String id);

    @GET("RoomBookings/GetRoomBookingByTravellerAgentId/{TravellerAgentId}")
    Call<ArrayList<RoomBookings>> getBookingsByProfileId(@Header("Authorization") String authKey, @Path("TravellerAgentId") int TravellerAgentId);

}
