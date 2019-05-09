package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ZingoHotels Tech on 11-12-2018.
 */

public class AvailabiltyCheckGetData implements Serializable {

    @SerializedName("CategoryName")
    public String CategoryName;

    @SerializedName("Availability")
    public int Availability;

    @SerializedName("TotalRooms")
    public int TotalRooms;

    @SerializedName("ratePlan")
    public ArrayList<Rates> ratePlan;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public int getAvailability() {
        return Availability;
    }

    public void setAvailability(int availability) {
        Availability = availability;
    }

    public int getTotalRooms() {
        return TotalRooms;
    }

    public void setTotalRooms(int totalRooms) {
        TotalRooms = totalRooms;
    }

    public ArrayList<Rates> getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(ArrayList<Rates> ratePlan) {
        this.ratePlan = ratePlan;
    }
}
