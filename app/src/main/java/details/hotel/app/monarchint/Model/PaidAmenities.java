package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZingoHotels Tech on 10-12-2018.
 */

public class PaidAmenities implements Serializable {
    @SerializedName("PaidAmenitiesId")
    private int paidAmenityId;

    @SerializedName("PaidAmenitiesName")
    private String text;

    @SerializedName("Description")
    private String description;

    @SerializedName("Price")
    private int amenityRate;

    @SerializedName("Category")
    private String Category;

    @SerializedName("HotelId")
    private int hotelId;

    @SerializedName("PaidAmenitiesCategoriesId")
    private int PaidAmenitiesCategoriesId;

    @SerializedName("Availability")
    private int Availability;

    @SerializedName("Image")
    private String Image;

    public int getPaidAmenityId() {
        return paidAmenityId;
    }

    public void setPaidAmenityId(int paidAmenityId) {
        this.paidAmenityId = paidAmenityId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmenityRate() {
        return amenityRate;
    }

    public void setAmenityRate(int amenityRate) {
        this.amenityRate = amenityRate;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getPaidAmenitiesCategoriesId() {
        return PaidAmenitiesCategoriesId;
    }

    public void setPaidAmenitiesCategoriesId(int paidAmenitiesCategoriesId) {
        PaidAmenitiesCategoriesId = paidAmenitiesCategoriesId;
    }

    public int getAvailability() {
        return Availability;
    }

    public void setAvailability(int availability) {
        Availability = availability;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
