package details.hotel.app.monarchint.UI.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import details.hotel.app.monarchint.Model.EmailData;
import details.hotel.app.monarchint.Model.PaidAmenities;
import details.hotel.app.monarchint.Model.RoomBookings;
import details.hotel.app.monarchint.Model.Service;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.RestoUtil;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.RoomBookingAPI;
import details.hotel.app.monarchint.WebAPI.SendEmailAPI;
import details.hotel.app.monarchint.WebAPI.ServiceApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmenityBookingActivity extends AppCompatActivity {

    Spinner mBookingSource;
    EditText mBookingId,mName,mEmail,mPhone;
    TextView mRequest;

    RoomBookings roomBookings;

    PaidAmenities amenities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            setContentView(R.layout.activity_amenity_booking);

            mBookingSource = (Spinner)findViewById(R.id.booking_source_title);
            mBookingId = (EditText)findViewById(R.id.booking_id);
            mName = (EditText)findViewById(R.id.fullname);
            mEmail = (EditText)findViewById(R.id.email);
            mPhone = (EditText)findViewById(R.id.phone);
            mRequest = (TextView)findViewById(R.id.pay);

            Bundle bundle = getIntent().getExtras();
            if(bundle!=null){

                amenities = (PaidAmenities)bundle.getSerializable("Amenity");
            }


            mRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String ota = mBookingSource.getSelectedItem().toString();
                    String bookingId = mBookingId.getText().toString();
                    String name = mName.getText().toString();
                    String email = mEmail.getText().toString();
                    String phone = mPhone.getText().toString();

                    if((ota==null||ota.isEmpty())||(ota!=null&&ota.equalsIgnoreCase("Select"))){

                        Toast.makeText(AmenityBookingActivity.this, "Select Booking source", Toast.LENGTH_SHORT).show();

                    }else if(bookingId==null||bookingId.isEmpty()){

                        Toast.makeText(AmenityBookingActivity.this, "Booking Id required", Toast.LENGTH_SHORT).show();

                    }else if(name==null||name.isEmpty()){

                        Toast.makeText(AmenityBookingActivity.this, "name Id required", Toast.LENGTH_SHORT).show();

                    }else if(email==null||email.isEmpty()){

                        Toast.makeText(AmenityBookingActivity.this, "email Id required", Toast.LENGTH_SHORT).show();

                    }else if(phone==null||phone.isEmpty()){

                        Toast.makeText(AmenityBookingActivity.this, "phone Id required", Toast.LENGTH_SHORT).show();

                    }else{

                        String body = "<html><head><style>table " +
                                "{border-collapse: collapse;}" +
                                "table, td, th {" +
                                "border: 1px solid black;} table th{\n" +
                                "  color:#0000ff;\n" +
                                "}" +
                                "</style></head>" +
                                "<body>" +
                                "<h2>Dear Team Members,</h2>" +
                                "<p><br>Please find below Amenity request By Guest</p></br></br>"+
                                "<br><b>Guest Name: </b>"+name+

                                "</br><br><b>Email: </b>"+email+
                                "</br><br><b>Phone Number: </b>"+phone+
                                "</br><br><b>Boooking id: </b>"+bookingId+
                                "</br><br><b>Booking Source: </b>"+ota+
                                "</br><br><b>Amenity: </b>"+amenities.getText()+

                                "</br><p><b>Thanks</b></p><br><br></body></html>";

                        EmailData emailData = new EmailData();
                        emailData.setEmailAddress(PreferenceHandler.getInstance(AmenityBookingActivity.this).getEmailList()+",abhinav@zingohotels.com,nishar@zingohotels.com");
                        emailData.setBody(body);
                        emailData.setSubject("Hotel Monarch International Hotel : Amenity Booked");
                        emailData.setUserName("nishar@zingohotels.com");
                        emailData.setPassword("Razin@1993");
                        emailData.setFromName("Nishar");
                        emailData.setFromEmail("nishar@zingohotels.com");

                        if(Util.isNetworkAvailable(AmenityBookingActivity.this)){
                            sendEmailAutomatic(emailData);
                        }else{
                            Toast.makeText(AmenityBookingActivity.this, "Please check your Internet Connection", Toast.LENGTH_LONG).show();
                        }

                        /*if(ota.equalsIgnoreCase("OTA")){
                            searchByOTAId(ota);

                        }else {

                            try{

                                int bookingIds = Integer.parseInt(bookingId);
                                searchByBookingId(bookingIds);

                            }catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(AmenityBookingActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }*/
                    }

                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void searchByBookingId(final int bookingid) {

        final ProgressDialog dialog = new ProgressDialog(AmenityBookingActivity.this);
        dialog.setMessage("Please wait..");
        dialog.setCancelable(false);
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                RoomBookingAPI bookingApi = Util.getClient().create(RoomBookingAPI.class);
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                Call<RoomBookings> getBooking = bookingApi.getBookingById(authenticationString,bookingid);
                getBooking.enqueue(new Callback<RoomBookings>() {
                    @Override
                    public void onResponse(Call<RoomBookings> call, Response<RoomBookings> response) {

                        try
                        {
                            if(dialog != null && dialog.isShowing())
                            {
                                dialog.dismiss();
                            }
                            if(response.code() == 200)
                            {


                                if(response.body() != null)
                                {

                                    roomBookings = response.body();

                                    Service service = new Service();
                                    service.setService("Airport Pickup");
                                    service.setAmount(0);
                                    service.setPaidStatus("Paid");
                                    service.setPaymentMode("Cash");
                                    service.setPaymentDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
                                    service.setBookingNumber(roomBookings.getBookingNumber());//bookings1.getBookingNumber());
                                    service.setBookingId(roomBookings.getBookingId());//bookings1.getBookingId());
                                    addService(service);

                                }
                                else
                                {

                                }
                            }
                            else
                            {

                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<RoomBookings> call, Throwable t) {

                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }

                    }
                });
            }
        });
    }

    private void searchByOTAId(final String bookingid) {

        final ProgressDialog dialog = new ProgressDialog(AmenityBookingActivity.this);
        dialog.setMessage("Please wait..");
        dialog.setCancelable(false);
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                RoomBookingAPI bookingApi = Util.getClient().create(RoomBookingAPI.class);
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                Call<ArrayList<RoomBookings>> getBooking = bookingApi.getBookingByOTAId(authenticationString,bookingid);
                getBooking.enqueue(new Callback<ArrayList<RoomBookings>>() {
                    @Override
                    public void onResponse(Call<ArrayList<RoomBookings>> call, Response<ArrayList<RoomBookings>> response) {

                        try
                        {
                            if(dialog != null && dialog.isShowing())
                            {
                                dialog.dismiss();
                            }
                            if(response.code() == 200)
                            {


                                if(response.body() != null)
                                {

                                    roomBookings = (response.body().get(response.body().size()-1));

                                    Service service = new Service();
                                    service.setService("Airport Pickup");
                                    service.setAmount(0);
                                    service.setPaidStatus("Paid");
                                    service.setPaymentMode("Cash");
                                    service.setPaymentDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
                                    service.setBookingNumber(roomBookings.getBookingNumber());//bookings1.getBookingNumber());
                                    service.setBookingId(roomBookings.getBookingId());//bookings1.getBookingId());
                                    addService(service);

                                }
                                else
                                {

                                }
                            }
                            else
                            {

                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<RoomBookings>> call, Throwable t) {

                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }

                    }
                });
            }
        });
    }

    private void addService(final Service service) {

        final ProgressDialog dialog = new ProgressDialog(AmenityBookingActivity.this);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {

                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                ServiceApi serviceApi = Util.getClient().create(ServiceApi.class);

                Call<Service> response = serviceApi.addService(authenticationString,service);
                response.enqueue(new Callback<Service>() {
                    @Override
                    public void onResponse(Call<Service> call, Response<Service> response) {
                        //System.out.println("addhotel = "+response.code());
                        try
                        {
                            if(dialog != null && dialog.isShowing())
                            {
                                dialog.dismiss();
                            }
                            Service serviceResponse = response.body();
                            //System.out.println("addhotel = "+serviceResponse.getService());
                            if(response.code() == 200 && serviceResponse != null)
                            {

                                Toast.makeText(AmenityBookingActivity.this,"Service request send successfully",Toast.LENGTH_LONG).show();


                            }
                            else {

                                Toast.makeText(AmenityBookingActivity.this,"Please try after some time",Toast.LENGTH_SHORT).show();

                            }
                        }
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<Service> call, Throwable t) {
                        System.out.println("onFailure");
                        Toast.makeText(AmenityBookingActivity.this,"Please try after some time",Toast.LENGTH_SHORT).show();
                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                    }
                });

            }
        });
    }

    private void sendEmailAutomatic(final EmailData emailData) {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Sending Email..");
        dialog.setCancelable(false);
        dialog.show();


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                SendEmailAPI travellerApi = RestoUtil.getClient().create(SendEmailAPI.class);
                Call<String> response = travellerApi.sendEmail(emailData);

                response.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {


                        try{

                            if(dialog != null && dialog.isShowing())
                            {
                                dialog.dismiss();
                            }
                            System.out.println(response.code());
                            if(response.code() == 200||response.code() == 201)
                            {
                                if(response.body() != null)
                                {


                                    if(response.body().equalsIgnoreCase("Email Sent Successfully")){
                                        AmenityBookingActivity.this.finish();
                                        Toast.makeText(AmenityBookingActivity.this, "Amenity Placed", Toast.LENGTH_SHORT).show();
                                    }else{
                                        //Toast.makeText(ContactUsScreen.this, "Something went wrong. So please contact through Call", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }else{
                                // Toast.makeText(ContactUsScreen.this, "Something went wrong due to "+response.code()+". So please contact through Call", Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            if(dialog != null && dialog.isShowing())
                            {
                                dialog.dismiss();
                            }
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }
}
