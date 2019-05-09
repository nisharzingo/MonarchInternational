package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Foods implements Serializable {

    @SerializedName("FoodId")
    private int FoodId;

    @SerializedName("FoodName")
    private String FoodName;

    @SerializedName("Description")
    private String Description;

    @SerializedName("DisplayPrice")
    private int DisplayPrice;

    @SerializedName("SellingPrice")
    private int SellingPrice;

    @SerializedName("DiscountPercentage")
    private double DiscountPercentage;

    @SerializedName("DiscountPrice")
    private int DiscountPrice;

    @SerializedName("subCategories")
    private RestaurantSubCategories subCategories;

    @SerializedName("SubCategoriesId")
    private int SubCategoriesId;

    @SerializedName("Status")
    private String Status;

    @SerializedName("Availability")
    private int Availability;

    @SerializedName("ValidFrom")
    private String ValidFrom;

    @SerializedName("ValidTo")
    private String ValidTo;

    @SerializedName("Review")
    private String Review;

    @SerializedName("Ratings")
    private double Ratings;

    @SerializedName("OtherDetails")
    private String OtherDetails;

    @SerializedName("HighlightsOfTheFood")
    private String HighlightsOfTheFood;

    @SerializedName("OrderNo")
    private int OrderNo;

    @SerializedName("foodImages")
    private ArrayList<FoodImages> foodImages;

    public int getFoodId() {
        return FoodId;
    }

    public void setFoodId(int foodId) {
        FoodId = foodId;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getDisplayPrice() {
        return DisplayPrice;
    }

    public void setDisplayPrice(int displayPrice) {
        DisplayPrice = displayPrice;
    }

    public int getSellingPrice() {
        return SellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        SellingPrice = sellingPrice;
    }

    public double getDiscountPercentage() {
        return DiscountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        DiscountPercentage = discountPercentage;
    }

    public int getDiscountPrice() {
        return DiscountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        DiscountPrice = discountPrice;
    }

    public RestaurantSubCategories getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(RestaurantSubCategories subCategories) {
        this.subCategories = subCategories;
    }

    public int getSubCategoriesId() {
        return SubCategoriesId;
    }

    public void setSubCategoriesId(int subCategoriesId) {
        SubCategoriesId = subCategoriesId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getAvailability() {
        return Availability;
    }

    public void setAvailability(int availability) {
        Availability = availability;
    }

    public String getValidFrom() {
        return ValidFrom;
    }

    public void setValidFrom(String validFrom) {
        ValidFrom = validFrom;
    }

    public String getValidTo() {
        return ValidTo;
    }

    public void setValidTo(String validTo) {
        ValidTo = validTo;
    }

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public double getRatings() {
        return Ratings;
    }

    public void setRatings(double ratings) {
        Ratings = ratings;
    }

    public String getOtherDetails() {
        return OtherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        OtherDetails = otherDetails;
    }

    public String getHighlightsOfTheFood() {
        return HighlightsOfTheFood;
    }

    public void setHighlightsOfTheFood(String highlightsOfTheFood) {
        HighlightsOfTheFood = highlightsOfTheFood;
    }

    public int getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(int orderNo) {
        OrderNo = orderNo;
    }

    public ArrayList<FoodImages> getFoodImages() {
        return foodImages;
    }

    public void setFoodImages(ArrayList<FoodImages> foodImages) {
        this.foodImages = foodImages;
    }
}
