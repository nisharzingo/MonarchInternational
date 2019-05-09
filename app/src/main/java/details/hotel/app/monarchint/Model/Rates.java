package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZingoHotels Tech on 11-12-2018.
 */

public class Rates implements Serializable {

    @SerializedName("RateId")
    private int RateId;

    /*@SerializedName("company")
    private Company company;*/

    @SerializedName("CompanyId")
    private int CompanyId;

    @SerializedName("HotelId")
    private int HotelId;

    @SerializedName("roomCategory")
    private RoomCategories roomCategory;

    @SerializedName("RoomCategoryId")
    private int RoomCategoryId;

    @SerializedName("MealPlanType")
    private String MealPlanType;

    @SerializedName("HourlyCharges")
    private int HourlyCharges;

    @SerializedName("DeclaredRateForSingle")
    private int DeclaredRateForSingle;

    @SerializedName("SellRateForSingle")
    private int SellRateForSingle;

    @SerializedName("DeclaredRateForDouble")
    private int DeclaredRateForDouble;

    @SerializedName("SellRateForDouble")
    private int SellRateForDouble;

    @SerializedName("DeclaredRateForTriple")
    private int DeclaredRateForTriple;

    @SerializedName("SellRateForTriple")
    private int SellRateForTriple;

    @SerializedName("DeclaredRateForExtraAdult")
    private int DeclaredRateForExtraAdult;

    @SerializedName("SellRateForExtraAdult")
    private int SellRateForExtraAdult;

    @SerializedName("FromDate")
    private String FromDate;

    @SerializedName("ToDate")
    private String ToDate;

    public int getRateId() {
        return RateId;
    }

    public void setRateId(int rateId) {
        RateId = rateId;
    }

    public int getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(int companyId) {
        CompanyId = companyId;
    }

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int hotelId) {
        HotelId = hotelId;
    }

    public RoomCategories getRoomCategory() {
        return roomCategory;
    }

    public void setRoomCategory(RoomCategories roomCategory) {
        this.roomCategory = roomCategory;
    }

    public int getRoomCategoryId() {
        return RoomCategoryId;
    }

    public void setRoomCategoryId(int roomCategoryId) {
        RoomCategoryId = roomCategoryId;
    }

    public String getMealPlanType() {
        return MealPlanType;
    }

    public void setMealPlanType(String mealPlanType) {
        MealPlanType = mealPlanType;
    }

    public int getHourlyCharges() {
        return HourlyCharges;
    }

    public void setHourlyCharges(int hourlyCharges) {
        HourlyCharges = hourlyCharges;
    }

    public int getDeclaredRateForSingle() {
        return DeclaredRateForSingle;
    }

    public void setDeclaredRateForSingle(int declaredRateForSingle) {
        DeclaredRateForSingle = declaredRateForSingle;
    }

    public int getSellRateForSingle() {
        return SellRateForSingle;
    }

    public void setSellRateForSingle(int sellRateForSingle) {
        SellRateForSingle = sellRateForSingle;
    }

    public int getDeclaredRateForDouble() {
        return DeclaredRateForDouble;
    }

    public void setDeclaredRateForDouble(int declaredRateForDouble) {
        DeclaredRateForDouble = declaredRateForDouble;
    }

    public int getSellRateForDouble() {
        return SellRateForDouble;
    }

    public void setSellRateForDouble(int sellRateForDouble) {
        SellRateForDouble = sellRateForDouble;
    }

    public int getDeclaredRateForTriple() {
        return DeclaredRateForTriple;
    }

    public void setDeclaredRateForTriple(int declaredRateForTriple) {
        DeclaredRateForTriple = declaredRateForTriple;
    }

    public int getSellRateForTriple() {
        return SellRateForTriple;
    }

    public void setSellRateForTriple(int sellRateForTriple) {
        SellRateForTriple = sellRateForTriple;
    }

    public int getDeclaredRateForExtraAdult() {
        return DeclaredRateForExtraAdult;
    }

    public void setDeclaredRateForExtraAdult(int declaredRateForExtraAdult) {
        DeclaredRateForExtraAdult = declaredRateForExtraAdult;
    }

    public int getSellRateForExtraAdult() {
        return SellRateForExtraAdult;
    }

    public void setSellRateForExtraAdult(int sellRateForExtraAdult) {
        SellRateForExtraAdult = sellRateForExtraAdult;
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
}
