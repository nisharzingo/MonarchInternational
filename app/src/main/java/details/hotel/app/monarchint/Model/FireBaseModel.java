package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZingoHotels.com on 4/7/2018.
 */


public class FireBaseModel implements Serializable {

    @SerializedName("HotelId")
    private int HotelId;

    public int getHotelId() {
        return HotelId;
    }

    public void setHotelId(int hotelId) {
        HotelId = hotelId;
    }

    @SerializedName("Title")
    private String Title;

    @SerializedName("Message")
    private String Message;

    @SerializedName("ServerId")
    private String ServerId;

    @SerializedName("SenderId")
    private String SenderId;

    //New Custom model for notification for data pay
    @SerializedName("TravellerName")
    private String TravellerName;

    @SerializedName("NoOfGuest")
    private String NoOfGuest;

    @SerializedName("CheckInDate")
    private String CheckInDate;

    @SerializedName("CheckOutDate")
    private String CheckOutDate;

    @SerializedName("TotalAmount")
    private String TotalAmount;

    @SerializedName("CommissionAmount")
    private String CommissionAmount;

    @SerializedName("NetAmount")
    private String NetAmount;

    @SerializedName("NotificationDate")
    private String NotificationDate;

    @SerializedName("NotificationTime")
    private String NotificationTime;

    @SerializedName("BookingDateTime")
    private String BookingDateTime;



    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getServerId() {
        return ServerId;
    }

    public void setServerId(String serverId) {
        ServerId = serverId;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    //New


    public String getTravellerName() {
        return TravellerName;
    }

    public void setTravellerName(String travellerName) {
        TravellerName = travellerName;
    }

    public String getNoOfGuest() {
        return NoOfGuest;
    }

    public void setNoOfGuest(String noOfGuest) {
        NoOfGuest = noOfGuest;
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

    public String getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }

    public String getCommissionAmount() {
        return CommissionAmount;
    }

    public void setCommissionAmount(String commissionAmount) {
        CommissionAmount = commissionAmount;
    }

    public String getNetAmount() {
        return NetAmount;
    }

    public void setNetAmount(String netAmount) {
        NetAmount = netAmount;
    }

    public String getNotificationDate() {
        return NotificationDate;
    }

    public void setNotificationDate(String notificationDate) {
        NotificationDate = notificationDate;
    }

    public String getNotificationTime() {
        return NotificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        NotificationTime = notificationTime;
    }

    public String getBookingDateTime() {
        return BookingDateTime;
    }

    public void setBookingDateTime(String bookingDateTime) {
        BookingDateTime = bookingDateTime;
    }
}
