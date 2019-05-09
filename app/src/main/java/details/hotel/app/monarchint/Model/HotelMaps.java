package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZingoHotels Tech on 11-12-2018.
 */

public class HotelMaps implements Serializable {

    @SerializedName("MapId")
    public int MapId;

    @SerializedName("Latitude")
    public String Latitude;

    @SerializedName("Logitude")
    public String Logitude;

    @SerializedName("Location")
    public String Location;

    @SerializedName("HotelId")
    public int HotelId;

    @SerializedName("Hotels")
    public HotelDetails Hotels;

    @SerializedName("PlaceID")
    public String PlaceID;

    public int getMapId() {
        return MapId;
    }

    public void setMapId(int mapId) {
        MapId = mapId;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLogitude() {
        return Logitude;
    }

    public void setLogitude(String logitude) {
        Logitude = logitude;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int hotelId) {
        HotelId = hotelId;
    }

    public HotelDetails getHotels() {
        return Hotels;
    }

    public void setHotels(HotelDetails hotels) {
        Hotels = hotels;
    }

    public String getPlaceID() {
        return PlaceID;
    }

    public void setPlaceID(String placeID) {
        PlaceID = placeID;
    }
}
