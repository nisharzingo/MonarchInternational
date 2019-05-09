package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantCategories implements Serializable {

    @SerializedName("CategoriesId")
    private int CategoriesId;

    @SerializedName("CategoriesName")
    private String CategoriesName;

    @SerializedName("restaurant")
    private Restaurants restaurant;

    @SerializedName("RestaurantId")
    private int RestaurantId;

    @SerializedName("Description")
    private String Description;

    @SerializedName("CategoriesImage")
    private String CategoriesImage;

    @SerializedName("Reviews")
    private String Reviews;

    @SerializedName("StarRating")
    private double StarRating;

    @SerializedName("OrderNo")
    private int OrderNo;

    @SerializedName("subCategoriesList")
    private ArrayList<RestaurantSubCategories> subCategoriesList;

    public int getCategoriesId() {
        return CategoriesId;
    }

    public void setCategoriesId(int categoriesId) {
        CategoriesId = categoriesId;
    }

    public String getCategoriesName() {
        return CategoriesName;
    }

    public void setCategoriesName(String categoriesName) {
        CategoriesName = categoriesName;
    }

    public Restaurants getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurants restaurant) {
        this.restaurant = restaurant;
    }

    public int getRestaurantId() {
        return RestaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        RestaurantId = restaurantId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategoriesImage() {
        return CategoriesImage;
    }

    public void setCategoriesImage(String categoriesImage) {
        CategoriesImage = categoriesImage;
    }

    public String getReviews() {
        return Reviews;
    }

    public void setReviews(String reviews) {
        Reviews = reviews;
    }

    public double getStarRating() {
        return StarRating;
    }

    public void setStarRating(double starRating) {
        StarRating = starRating;
    }

    public int getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(int orderNo) {
        OrderNo = orderNo;
    }

    public ArrayList<RestaurantSubCategories> getSubCategoriesList() {
        return subCategoriesList;
    }

    public void setSubCategoriesList(ArrayList<RestaurantSubCategories> subCategoriesList) {
        this.subCategoriesList = subCategoriesList;
    }
}
