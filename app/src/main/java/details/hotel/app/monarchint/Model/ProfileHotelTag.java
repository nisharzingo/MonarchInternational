package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZingoHotels Tech on 11-12-2018.
 */

public class ProfileHotelTag implements Serializable  {

    @SerializedName("MappingId")
    private int MappingId;

    @SerializedName("ProfileId")
    private int ProfileId;

    @SerializedName("hotels")
    private HotelDetails hotels;

    @SerializedName("HotelId")
    private int HotelId;

    public int getMappingId() {
        return MappingId;
    }

    public void setMappingId(int mappingId) {
        MappingId = mappingId;
    }

    public int getProfileId() {
        return ProfileId;
    }

    public void setProfileId(int profileId) {
        ProfileId = profileId;
    }

    public HotelDetails getHotels() {
        return hotels;
    }

    public void setHotels(HotelDetails hotels) {
        this.hotels = hotels;
    }

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int hotelId) {
        HotelId = hotelId;
    }
}
