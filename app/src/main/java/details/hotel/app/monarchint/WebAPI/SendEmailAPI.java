package details.hotel.app.monarchint.WebAPI;

import details.hotel.app.monarchint.Model.EmailData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SendEmailAPI {

    @POST("Operation/SendInvoice")
    Call<String> sendEmail(@Body EmailData emailData);
}
