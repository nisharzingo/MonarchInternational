package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by ZingoHotels Tech on 10-12-2018.
 */

public class HotelImage implements Serializable {

    @SerializedName("HotelImageId")
    private int HotelImageId;

    @SerializedName("HotelId")
    private int HotelId;

    @SerializedName("Images")
    private String Images;

    @SerializedName("Image")
    private String Image;

    @SerializedName("Caption")
    private String Caption;

    @SerializedName("ImageOrder")
    private int ImageOrder;

    public int getHotelImageId() {
        return HotelImageId;
    }

    public void setHotelImageId(int hotelImageId) {
        HotelImageId = hotelImageId;
    }

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int hotelId) {
        HotelId = hotelId;
    }

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getCaption() {
        return Caption;
    }

    public void setCaption(String caption) {
        Caption = caption;
    }

    public int getImageOrder() {
        return ImageOrder;
    }

    public void setImageOrder(int imageOrder) {
        ImageOrder = imageOrder;
    }

    public static Comparator compareHotelImageByOrder = new Comparator() {
        @Override
        public int compare(Object o, Object t1) {
            HotelImage profile = (HotelImage) o;
            HotelImage profile1 = (HotelImage) t1;

            return profile.getImageOrder()-profile1.getImageOrder();
            //return profile.getHotelId().eq(profile1.getHotelId());
        }
    };
}
