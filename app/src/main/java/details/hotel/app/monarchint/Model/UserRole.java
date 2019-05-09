package details.hotel.app.monarchint.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserRole implements Serializable {

    @SerializedName("UserRoleId")
    public int UserRoleId;

    @SerializedName("UserRoleName")
    public String UserRoleName;

    @SerializedName("UserRoleUniqueId")
    public String userRoleUniqueId;

    public int getUserRoleId() {
        return UserRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        UserRoleId = userRoleId;
    }

    public String getUserRoleName() {
        return UserRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        UserRoleName = userRoleName;
    }

    public String getUserRoleUniqueId() {
        return userRoleUniqueId;
    }

    public void setUserRoleUniqueId(String userRoleUniqueId) {
        this.userRoleUniqueId = userRoleUniqueId;
    }
}
