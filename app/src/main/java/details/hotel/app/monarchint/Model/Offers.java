package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Offers implements Serializable {

    @SerializedName("OfferId")
    public int OfferId;

    @SerializedName("HotelId")
    public int HotelId;

    @SerializedName("hotels")
    public HotelDetails hotels;

    @SerializedName("OfferType")
    public String OfferType;

    @SerializedName("OfferImage")
    public String OfferImage;

    @SerializedName("Title")
    public String Title;

    @SerializedName("Description")
    public String Description;

    @SerializedName("Percentage")
    public String Percentage;

    @SerializedName("Amount")
    public double Amount;

    @SerializedName("ValidFrom")
    public String ValidFrom;

    @SerializedName("ValidTo")
    public String ValidTo;

    @SerializedName("CouponCode")
    public String CouponCode;

    public int getOfferId() {
        return OfferId;
    }

    public void setOfferId(int offerId) {
        OfferId = offerId;
    }

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int hotelId) {
        HotelId = hotelId;
    }

    public HotelDetails getHotels() {
        return hotels;
    }

    public void setHotels(HotelDetails hotels) {
        this.hotels = hotels;
    }

    public String getOfferType() {
        return OfferType;
    }

    public void setOfferType(String offerType) {
        OfferType = offerType;
    }

    public String getOfferImage() {
        return OfferImage;
    }

    public void setOfferImage(String offerImage) {
        OfferImage = offerImage;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        Amount = amount;
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

    public String getCouponCode() {
        return CouponCode;
    }

    public void setCouponCode(String couponCode) {
        CouponCode = couponCode;
    }
}
