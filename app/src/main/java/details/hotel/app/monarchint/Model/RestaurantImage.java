package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;

public class RestaurantImage implements Serializable {

    @SerializedName("RestaurantImagesId")
    private int RestaurantImagesId;

    @SerializedName("Images")
    private String Images;

    @SerializedName("restaurant")
    private Restaurants restaurant;

    @SerializedName("RestaurantId")
    private int RestaurantId;

    @SerializedName("OrderNo")
    private int OrderNo;

    public int getRestaurantImagesId() {
        return RestaurantImagesId;
    }

    public void setRestaurantImagesId(int restaurantImagesId) {
        RestaurantImagesId = restaurantImagesId;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
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

    public int getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(int orderNo) {
        OrderNo = orderNo;
    }

    public static Comparator compareRestImageByOrder = new Comparator() {
        @Override
        public int compare(Object o, Object t1) {
            RestaurantImage profile = (RestaurantImage) o;
            RestaurantImage profile1 = (RestaurantImage) t1;

            return profile.getOrderNo()-profile1.getOrderNo();
            //return profile.getHotelId().eq(profile1.getHotelId());
        }
    };

}
