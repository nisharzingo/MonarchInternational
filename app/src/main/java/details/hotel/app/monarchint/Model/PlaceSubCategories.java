package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlaceSubCategories implements Serializable {

    @SerializedName("SubCategoriesId")
    private int SubCategoriesId;
    @SerializedName("SubCategoriesName")
    private String SubCategoriesName;
    @SerializedName("categories")
    private PlaceCategory categories;
    @SerializedName("CategoriesId")
    private int CategoriesId;
    @SerializedName("Description")
    private String Description;
    @SerializedName("SubCategoriesImage")
    private String SubCategoriesImage;
    @SerializedName("Reviews")
    private String Reviews;
    @SerializedName("StarRatings")
    private double StarRatings;
    @SerializedName("OrderNo")
    private int OrderNo;


    public void setCategories(PlaceCategory categories) {
        this.categories = categories;
    }

    public PlaceCategory getCategories() {
        return categories;
    }

    public void setCategoriesId(int categoriesId) {
        CategoriesId = categoriesId;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setOrderNo(int orderNo) {
        OrderNo = orderNo;
    }

    public void setReviews(String reviews) {
        Reviews = reviews;
    }

    public void setStarRatings(double starRatings) {
        StarRatings = starRatings;
    }

    public void setSubCategoriesId(int subCategoriesId) {
        SubCategoriesId = subCategoriesId;
    }

    public void setSubCategoriesImage(String subCategoriesImage) {
        SubCategoriesImage = subCategoriesImage;
    }

    public void setSubCategoriesName(String subCategoriesName) {
        SubCategoriesName = subCategoriesName;
    }

    public double getStarRatings() {
        return StarRatings;
    }

    public int getCategoriesId() {
        return CategoriesId;
    }

    public int getOrderNo() {
        return OrderNo;
    }

    public int getSubCategoriesId() {
        return SubCategoriesId;
    }

    public String getDescription() {
        return Description;
    }

    public String getReviews() {
        return Reviews;
    }

    public String getSubCategoriesImage() {
        return SubCategoriesImage;
    }

    public String getSubCategoriesName() {
        return SubCategoriesName;
    }

}
