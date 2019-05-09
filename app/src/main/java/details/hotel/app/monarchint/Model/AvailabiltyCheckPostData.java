package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZingoHotels Tech on 11-12-2018.
 */

public class AvailabiltyCheckPostData implements Serializable {

    @SerializedName("HotelId")
    public int HotelId;

    @SerializedName("RoomCategoryId")
    public int RoomCategoryId;

    @SerializedName("FromDate")
    public  String FromDate;

    @SerializedName("ToDate")
    public  String ToDate;

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int hotelId) {
        HotelId = hotelId;
    }

    public String getFromDate() {
        return FromDate;
    }

    public void setFromDate(String fromDate) {
        FromDate = fromDate;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public int getRoomCategoryId() {
        return RoomCategoryId;
    }

    public void setRoomCategoryId(int roomCategoryId) {
        RoomCategoryId = roomCategoryId;
    }
}
