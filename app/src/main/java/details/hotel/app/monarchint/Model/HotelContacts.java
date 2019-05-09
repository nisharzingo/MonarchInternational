package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HotelContacts implements Serializable {

    @SerializedName("ContactId")
    public int ContactId;

    @SerializedName("HotelPhone")
    public String HotelPhone;

    @SerializedName("HotelMobile")
    public String HotelMobile;

    @SerializedName("HotelEmail")
    public String HotelEmail;

    @SerializedName("PhoneList")
    public String PhoneList;

    @SerializedName("EmailList")
    public String EmailList;

    @SerializedName("WebsiteList")
    public String WebsiteList;

    @SerializedName("HotelId")
    public int HotelId;

    @SerializedName("Hotels")
    public HotelDetails Hotels;

    public int getContactId() {
        return ContactId;
    }

    public void setContactId(int contactId) {
        ContactId = contactId;
    }

    public String getHotelPhone() {
        return HotelPhone;
    }

    public void setHotelPhone(String hotelPhone) {
        HotelPhone = hotelPhone;
    }

    public String getHotelMobile() {
        return HotelMobile;
    }

    public void setHotelMobile(String hotelMobile) {
        HotelMobile = hotelMobile;
    }

    public String getHotelEmail() {
        return HotelEmail;
    }

    public void setHotelEmail(String hotelEmail) {
        HotelEmail = hotelEmail;
    }

    public String getPhoneList() {
        return PhoneList;
    }

    public void setPhoneList(String phoneList) {
        PhoneList = phoneList;
    }

    public String getEmailList() {
        return EmailList;
    }

    public void setEmailList(String emailList) {
        EmailList = emailList;
    }

    public String getWebsiteList() {
        return WebsiteList;
    }

    public void setWebsiteList(String websiteList) {
        WebsiteList = websiteList;
    }

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int hotelId) {
        HotelId = hotelId;
    }

    public HotelDetails getHotels() {
        return Hotels;
    }

    public void setHotels(HotelDetails hotels) {
        Hotels = hotels;
    }
}
