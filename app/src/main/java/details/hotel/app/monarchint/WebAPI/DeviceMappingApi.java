package details.hotel.app.monarchint.WebAPI;

import details.hotel.app.monarchint.Model.DeviceMapping;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface DeviceMappingApi {

    @POST("AgentDeviceMappings")
    Call<DeviceMapping> addProfileMap(@Body DeviceMapping body);
}
