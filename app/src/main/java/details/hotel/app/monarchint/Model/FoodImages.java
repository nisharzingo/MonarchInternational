package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodImages implements Serializable {

    @SerializedName("FoodImagesId")
    private int FoodImagesId;

    @SerializedName("Images")
    private String Images;

    @SerializedName("food")
    private Foods food;

    @SerializedName("FoodId")
    private int FoodId;

    @SerializedName("OrderNo")
    private int OrderNo;

    public int getFoodImagesId() {
        return FoodImagesId;
    }

    public void setFoodImagesId(int foodImagesId) {
        FoodImagesId = foodImagesId;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public Foods getFood() {
        return food;
    }

    public void setFood(Foods food) {
        this.food = food;
    }

    public int getFoodId() {
        return FoodId;
    }

    public void setFoodId(int foodId) {
        FoodId = foodId;
    }

    public int getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(int orderNo) {
        OrderNo = orderNo;
    }
}
