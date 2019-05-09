package details.hotel.app.monarchint.WebAPI;

import details.hotel.app.monarchint.Model.EmailData;
import details.hotel.app.monarchint.Model.Invoice;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SendEmailAPI {

    @POST("Operation/SendInvoice")
    Call<String> sendEmail(@Body EmailData emailData);

    @POST("Calculation/SendInvoice")
    Call<String> sendEmails(@Header("Authorization") String authKey, @Body Invoice emailData);
}
