package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TravellerAgentProfiles implements Serializable {

    @SerializedName("TravellerAgentProfileId")
    private int TravellerAgentProfileId;

    @SerializedName("Status")
    private String Status;

    @SerializedName("Plans")
    private String Plans;

    @SerializedName("FirstName")
    private String FirstName;

    @SerializedName("MiddleName")
    private String MiddleName;

    @SerializedName("LastName")
    private String LastName;

    @SerializedName("UserName")
    private String UserName;

    @SerializedName("Password")
    private String Password;

    @SerializedName("Gender")
    private String Gender;

    @SerializedName("Email")
    private String Email;

    @SerializedName("PhoneNumber")
    private String PhoneNumber;

    @SerializedName("Address")
    private String Address;

    @SerializedName("PinCode")
    private String PinCode;

    @SerializedName("UserRoleId")
    private int UserRoleId;

    @SerializedName("PlaceName")
    private String PlaceName;

    @SerializedName("UserRoles")
    private UserRole UserRoles;

    @SerializedName("ProfilePhoto")
    private String ProfilePhoto;

    @SerializedName("FrontSidePhoto")
    private String FrontSidePhoto;

    @SerializedName("BackSidePhoto")
    private String BackSidePhoto;

    @SerializedName("CommissionPercentage")
    private double CommissionPercentage;

    @SerializedName("CommissionAmount")
    private double CommissionAmount;

    @SerializedName("ReferralCodeUsed")
    private String ReferralCodeUsed;

    @SerializedName("ReferralAmount")
    private double ReferralAmount;

    @SerializedName("ReferralCodeToUseForOtherProfile")
    private String ReferralCodeToUseForOtherProfile;

    @SerializedName("ReferralAmountForOtherProfile")
    private long ReferralAmountForOtherProfile;

    @SerializedName("WalletBalance")
    private int WalletBalance;

    @SerializedName("UsedAmount")
    private int UsedAmount;

    public int getTravellerAgentProfileId() {
        return TravellerAgentProfileId;
    }

    public void setTravellerAgentProfileId(int travellerAgentProfileId) {
        TravellerAgentProfileId = travellerAgentProfileId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPlans() {
        return Plans;
    }

    public void setPlans(String plans) {
        Plans = plans;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public int getUserRoleId() {
        return UserRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        UserRoleId = userRoleId;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public UserRole getUserRoles() {
        return UserRoles;
    }

    public void setUserRoles(UserRole userRoles) {
        UserRoles = userRoles;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }

    public String getFrontSidePhoto() {
        return FrontSidePhoto;
    }

    public void setFrontSidePhoto(String frontSidePhoto) {
        FrontSidePhoto = frontSidePhoto;
    }

    public String getBackSidePhoto() {
        return BackSidePhoto;
    }

    public void setBackSidePhoto(String backSidePhoto) {
        BackSidePhoto = backSidePhoto;
    }

    public double getCommissionPercentage() {
        return CommissionPercentage;
    }

    public void setCommissionPercentage(double commissionPercentage) {
        CommissionPercentage = commissionPercentage;
    }

    public double getCommissionAmount() {
        return CommissionAmount;
    }

    public void setCommissionAmount(double commissionAmount) {
        CommissionAmount = commissionAmount;
    }

    public String getReferralCodeUsed() {
        return ReferralCodeUsed;
    }

    public void setReferralCodeUsed(String referralCodeUsed) {
        ReferralCodeUsed = referralCodeUsed;
    }

    public double getReferralAmount() {
        return ReferralAmount;
    }

    public void setReferralAmount(double referralAmount) {
        ReferralAmount = referralAmount;
    }

    public String getReferralCodeToUseForOtherProfile() {
        return ReferralCodeToUseForOtherProfile;
    }

    public void setReferralCodeToUseForOtherProfile(String referralCodeToUseForOtherProfile) {
        ReferralCodeToUseForOtherProfile = referralCodeToUseForOtherProfile;
    }

    public long getReferralAmountForOtherProfile() {
        return ReferralAmountForOtherProfile;
    }

    public void setReferralAmountForOtherProfile(long referralAmountForOtherProfile) {
        ReferralAmountForOtherProfile = referralAmountForOtherProfile;
    }

    public int getWalletBalance() {
        return WalletBalance;
    }

    public void setWalletBalance(int walletBalance) {
        WalletBalance = walletBalance;
    }

    public int getUsedAmount() {
        return UsedAmount;
    }

    public void setUsedAmount(int usedAmount) {
        UsedAmount = usedAmount;
    }


}
