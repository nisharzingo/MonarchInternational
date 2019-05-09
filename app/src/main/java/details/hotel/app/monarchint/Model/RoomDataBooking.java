package details.hotel.app.monarchint.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class RoomDataBooking implements Serializable {

    String roomCategory;
    int roomCategoryId;
    String fromDate;
    String toDate;
    ArrayList<RoomData> roomData;

    public String getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(String roomCategory) {
        this.roomCategory = roomCategory;
    }

    public int getRoomCategoryId() {
        return roomCategoryId;
    }

    public void setRoomCategoryId(int roomCategoryId) {
        this.roomCategoryId = roomCategoryId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public ArrayList<RoomData> getRoomData() {
        return roomData;
    }

    public void setRoomData(ArrayList<RoomData> roomData) {
        this.roomData = roomData;
    }
}
