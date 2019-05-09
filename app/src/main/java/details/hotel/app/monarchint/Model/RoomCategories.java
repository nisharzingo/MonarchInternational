package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ZingoHotels Tech on 11-12-2018.
 */

public class RoomCategories implements Serializable {


    @SerializedName("RoomCategoryId")
    public int RoomCategoryId;

    @SerializedName("CategoryName")
    public String CategoryName;

    @SerializedName("Description")
    public String Description;

    @SerializedName("HotelsId")
    public int HotelsId;

    /*@SerializedName("ratePlan")
    public int ratePlan;*/

    @SerializedName("rates")
    public ArrayList<Rates> rates;

    public int getRoomCategoryId() {
        return RoomCategoryId;
    }

    public void setRoomCategoryId(int roomCategoryId) {
        RoomCategoryId = roomCategoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getHotelsId() {
        return HotelsId;
    }

    public void setHotelsId(int hotelsId) {
        HotelsId = hotelsId;
    }

    public ArrayList<Rates> getRates() {
        return rates;
    }

    public void setRates(ArrayList<Rates> rates) {
        this.rates = rates;
    }
}
