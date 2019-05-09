package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurants implements Serializable {

    @SerializedName("RestaurantId")
    private int RestaurantId;

    @SerializedName("RestaurantName")
    private String RestaurantName;

    @SerializedName("IsApproved")
    private boolean IsApproved;

   /* @SerializedName("maps")
    private ArrayList<RestaurantMaps> maps;*/

    @SerializedName("DisplayName")
    private String DisplayName;

    @SerializedName("RestaurantType")
    private String RestaurantType;

    @SerializedName("StarRating")
    private String StarRating;

    @SerializedName("BuiltYear")
    private String BuiltYear;

    @SerializedName("Currency")
    private String Currency;

    @SerializedName("VCCCurrency")
    private String VCCCurrency;

    @SerializedName("StartTime")
    private String StartTime;

    @SerializedName("EndTime")
    private String EndTime;

    @SerializedName("Timezone")
    private String Timezone;

    @SerializedName("PlaceName")
    private String PlaceName;

    @SerializedName("HotelId")
    private int HotelId;

    @SerializedName("RestaurantProfileId")
    private int RestaurantProfileId;

    @SerializedName("Localty")
    private String Localty;

    @SerializedName("State")
    private String State;

    @SerializedName("Country")
    private String Country;

    @SerializedName("City")
    private String City;

    @SerializedName("PinCode")
    private String PinCode;

    @SerializedName("hotelImage")
    private ArrayList<RestaurantImage> hotelImage;

    @SerializedName("categoriesList")
    private ArrayList<RestaurantCategories> categoriesList;

  /*  @SerializedName("restaurantProfile")
    private RestaurantCategories restaurantProfile;*/



    public boolean isApproved() {
        return IsApproved;
    }

   /* public RestaurantCategories getRestaurantProfile() {
        return restaurantProfile;
    }

    public void setRestaurantProfile(RestaurantCategories restaurantProfile) {
        this.restaurantProfile = restaurantProfile;
    }*/

    public int getRestaurantProfileId() {
        return RestaurantProfileId;
    }

    public void setRestaurantProfileId(int restaurantProfileId) {
        RestaurantProfileId = restaurantProfileId;
    }

    public int getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        RestaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public boolean getApproved() {
        return IsApproved;
    }

    public void setApproved(boolean approved) {
        IsApproved = approved;
    }

   /* public ArrayList<RestaurantMaps> getMaps() {
        return maps;
    }

    public void setMaps(ArrayList<RestaurantMaps> maps) {
        this.maps = maps;
    }*/

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getRestaurantType() {
        return RestaurantType;
    }

    public void setRestaurantType(String restaurantType) {
        RestaurantType = restaurantType;
    }

    public String getStarRating() {
        return StarRating;
    }

    public void setStarRating(String starRating) {
        StarRating = starRating;
    }

    public String getBuiltYear() {
        return BuiltYear;
    }

    public void setBuiltYear(String builtYear) {
        BuiltYear = builtYear;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getVCCCurrency() {
        return VCCCurrency;
    }

    public void setVCCCurrency(String VCCCurrency) {
        this.VCCCurrency = VCCCurrency;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
    }

    public String getTimezone() {
        return Timezone;
    }

    public void setTimezone(String timezone) {
        Timezone = timezone;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int hotelId) {
        HotelId = hotelId;
    }

    public String getLocalty() {
        return Localty;
    }

    public void setLocalty(String localty) {
        Localty = localty;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public ArrayList<RestaurantImage> getHotelImage() {
        return hotelImage;
    }

    public void setHotelImage(ArrayList<RestaurantImage> hotelImage) {
        this.hotelImage = hotelImage;
    }

    public ArrayList<RestaurantCategories> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(ArrayList<RestaurantCategories> categoriesList) {
        this.categoriesList = categoriesList;
    }
}
