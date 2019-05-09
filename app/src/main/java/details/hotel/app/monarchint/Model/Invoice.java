package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Invoice implements Serializable {

    @SerializedName("TravellerId")
    public int TravellerId;

    @SerializedName("EmailAddress")
    public String EmailAddress;

    @SerializedName("BookingNumber")
    public String BookingNumber;

    @SerializedName("HotelId")
    public int HotelId;

    @SerializedName("CheckInDate")
    public String CheckInDate;

    @SerializedName("CheckOutDate")
    public String CheckOutDate;

    @SerializedName("RoomNo")
    public String RoomNo;

    public int getTravellerId() {
        return TravellerId;
    }

    public void setTravellerId(int travellerId) {
        TravellerId = travellerId;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getBookingNumber() {
        return BookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        BookingNumber = bookingNumber;
    }

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int hotelId) {
        HotelId = hotelId;
    }

    public String getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(String checkInDate) {
        CheckInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        CheckOutDate = checkOutDate;
    }

    public String getRoomNo() {
        return RoomNo;
    }

    public void setRoomNo(String roomNo) {
        RoomNo = roomNo;
    }
}
