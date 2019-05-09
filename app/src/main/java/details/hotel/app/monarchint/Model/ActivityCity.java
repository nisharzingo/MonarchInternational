package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ActivityCity implements Serializable {

    @SerializedName("CityId")
    private int CityId;
    @SerializedName("CityName")
    private String CityName;
    @SerializedName("categoriesList")
    private ArrayList<PlaceCategory> categoriesList;

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public ArrayList<PlaceCategory> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(ArrayList<PlaceCategory> categoriesList) {
        this.categoriesList = categoriesList;
    }
}
