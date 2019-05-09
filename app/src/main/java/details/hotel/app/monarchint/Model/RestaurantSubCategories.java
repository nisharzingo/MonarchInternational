package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantSubCategories implements Serializable {

    @SerializedName("SubCategoriesId")
    private int SubCategoriesId;

    @SerializedName("SubCategoriesName")
    private String SubCategoriesName;

    @SerializedName("categories")
    private RestaurantCategories categories;

    @SerializedName("CategoriesId")
    private int CategoriesId;

    @SerializedName("Description")
    private String Description;

    @SerializedName("SubCategoriesImage")
    private String SubCategoriesImage;

    @SerializedName("Reviews")
    private String Reviews;

    @SerializedName("StarRating")
    private double StarRating;

    @SerializedName("OrderNo")
    private int OrderNo;

    @SerializedName("foodList")
    private ArrayList<Foods> foodList;

    public int getSubCategoriesId() {
        return SubCategoriesId;
    }

    public void setSubCategoriesId(int subCategoriesId) {
        SubCategoriesId = subCategoriesId;
    }

    public String getSubCategoriesName() {
        return SubCategoriesName;
    }

    public void setSubCategoriesName(String subCategoriesName) {
        SubCategoriesName = subCategoriesName;
    }

    public RestaurantCategories getCategories() {
        return categories;
    }

    public void setCategories(RestaurantCategories categories) {
        this.categories = categories;
    }

    public int getCategoriesId() {
        return CategoriesId;
    }

    public void setCategoriesId(int categoriesId) {
        CategoriesId = categoriesId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSubCategoriesImage() {
        return SubCategoriesImage;
    }

    public void setSubCategoriesImage(String subCategoriesImage) {
        SubCategoriesImage = subCategoriesImage;
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

    public ArrayList<Foods> getFoodList() {
        return foodList;
    }

    public void setFoodList(ArrayList<Foods> foodList) {
        this.foodList = foodList;
    }
}