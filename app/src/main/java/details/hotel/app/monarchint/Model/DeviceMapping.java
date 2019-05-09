package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DeviceMapping  implements Serializable {

    @SerializedName("AgentDeviceMappingId")
    private int ProfileDeviceMappingId;

    @SerializedName("TravellerAgentProfileId")
    private int ProfileId;

    @SerializedName("DeviceId")
    private String DeviceId;

    public int getProfileDeviceMappingId() {
        return ProfileDeviceMappingId;
    }

    public void setProfileDeviceMappingId(int profileDeviceMappingId) {
        ProfileDeviceMappingId = profileDeviceMappingId;
    }

    public int getProfileId() {
        return ProfileId;
    }

    public void setProfileId(int profileId) {
        ProfileId = profileId;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }
}
