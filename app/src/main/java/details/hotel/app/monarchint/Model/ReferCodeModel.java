package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReferCodeModel implements Serializable {

    @SerializedName("ReferralCode")
    private String ReferralCode;

    @SerializedName("ReferralCodeUsed")
    private String ReferralCodeUsed;

    public String getReferralCode() {
        return ReferralCode;
    }

    public void setReferralCode(String referralCode) {
        ReferralCode = referralCode;
    }

    public String getReferralCodeUsed() {
        return ReferralCodeUsed;
    }

    public void setReferralCodeUsed(String referralCodeUsed) {
        ReferralCodeUsed = referralCodeUsed;
    }
}
