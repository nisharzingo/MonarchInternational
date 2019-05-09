package details.hotel.app.monarchint.UI.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import details.hotel.app.monarchint.Customs.CustomFonts.TextViewRobotoregular;
import details.hotel.app.monarchint.Model.HotelDetails;
import details.hotel.app.monarchint.Model.RoomBookings;
import details.hotel.app.monarchint.Model.Traveller;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.HotelsDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDoneScreen extends AppCompatActivity {

    private String sharePath="no";

    TextViewRobotoregular mBookingId,mHotelName,mGuestName,mGuestInfo,mTotalPrice,mCID,mCOD,mNights,mAddress,mRoomPrice,mGstprice,mPay,mhome;
    LinearLayout mScreenShot;

    RoomBookings roomBookings;
    Traveller traveller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            setContentView(R.layout.activity_booking_done_screen);

            mScreenShot = (LinearLayout)findViewById(R.id.screenshot);
            mBookingId = (TextViewRobotoregular)findViewById(R.id.booking_number);
            mHotelName = (TextViewRobotoregular)findViewById(R.id.booked_hotel_name);
            mGuestName = (TextViewRobotoregular)findViewById(R.id.customer_name);
            mGuestInfo = (TextViewRobotoregular)findViewById(R.id.booked_guest_info);
            mTotalPrice = (TextViewRobotoregular)findViewById(R.id.booked_total_price);
            mCID = (TextViewRobotoregular)findViewById(R.id.booked_check_in_date);
            mCOD = (TextViewRobotoregular)findViewById(R.id.booked_check_out_date);
            mNights = (TextViewRobotoregular)findViewById(R.id.booked_no_of_nights);
            mAddress = (TextViewRobotoregular)findViewById(R.id.booked_hotel_address);
            mRoomPrice = (TextViewRobotoregular)findViewById(R.id.total_room_tariff);
            mGstprice = (TextViewRobotoregular)findViewById(R.id.total_room_tariff_gst);
            mPay = (TextViewRobotoregular)findViewById(R.id.total_room_tariff_amount_payable);
            mhome = (TextViewRobotoregular)findViewById(R.id.go_to_home_booking);


            mhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent home  = new Intent(BookingDoneScreen.this,HotelOptionsScreen.class);
                    startActivity(home);
                    BookingDoneScreen.this.finish();


                }
            });

            Bundle bundle = getIntent().getExtras();

            if(bundle!=null){


                roomBookings = (RoomBookings)bundle.getSerializable("RoomBookings");
                traveller = (Traveller) bundle.getSerializable("Traveller");
            }
            getHotelAddress(PreferenceHandler.getInstance(BookingDoneScreen.this).getHotelID());
            if(roomBookings!=null){

                mBookingId.setText("Booking Id: #"+roomBookings.getBookingId());
                mHotelName.setText(PreferenceHandler.getInstance(BookingDoneScreen.this).getHotelName());

                if(traveller!=null){
                    mGuestName.setText(""+traveller.getFirstName());
                }

                mGuestInfo.setText(""+roomBookings.getNoOfAdults()+" Adults,"+roomBookings.getNoOfChild()+" Child(ren)\n"+roomBookings.getNoOfRooms()+" Room(s)");

                mTotalPrice.setText("Rs. "+roomBookings.getTotalAmount());

                String cid = roomBookings.getCheckInDate();
                String cot = roomBookings.getCheckOutDate();

                if(cid!=null&&cid.contains("/")){

                    try{

                        Date cidDate = new SimpleDateFormat("MM/dd/yyyy").parse(cid);
                        String cids = new SimpleDateFormat("MMM dd,yyyy").format(cidDate);

                        mCID.setText(""+cids);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                if(cot!=null&&cid.contains("/")){

                    try{

                        Date cidDate = new SimpleDateFormat("MM/dd/yyyy").parse(cot);
                        String cids = new SimpleDateFormat("MMm dd,yyyy").format(cidDate);

                        mCOD.setText(""+cids);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                mNights.setText(roomBookings.getDurationOfStay()+"N");

                mRoomPrice.setText("Rs. "+roomBookings.getSellRate());
                mGstprice.setText("Rs. "+roomBookings.getGstAmount());
                mPay.setText("Rs. "+roomBookings.getTotalAmount());
            }


            shareView(mScreenShot);

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public void shareView(View view) {
        View u = findViewById(R.id.screenshot);
        u.setDrawingCacheEnabled(true);
        LinearLayout z = (LinearLayout) findViewById(R.id.screenshot);
        int totalHeight = z.getChildAt(0).getHeight();
        int totalWidth = z.getChildAt(0).getWidth();
        u.layout(0, 0, totalWidth, totalHeight);
        u.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(u.getDrawingCache());
        u.setDrawingCacheEnabled(false);

        //Save bitmap
        String extr = Environment.getExternalStorageDirectory().toString() +   File.separator + "Folder";
        String fileName = new SimpleDateFormat("yyyyMMddhhmm'_report.jpg'").format(new Date());
        File myPath = new File(extr, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(getContentResolver(), b, "Screen", "screen");
        }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    private void getHotelAddress(final int id){

        final ProgressDialog progressDialog = new ProgressDialog(BookingDoneScreen.this);
        progressDialog.setTitle("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                //String auth_string = Util.getToken(getActivity());
                String auth_string = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI apiService = Util.getClient().create(HotelsDetailsAPI.class);
                Call<HotelDetails> call = apiService.getHotelByHotelId(auth_string,id);

                call.enqueue(new Callback<HotelDetails>() {
                    @Override
                    public void onResponse(Call<HotelDetails> call, Response<HotelDetails> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();
                        if (statusCode == 200) {

                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            final HotelDetails hotelDetails =  response.body();

                            if(hotelDetails != null)
                            {


                                mAddress.setText(hotelDetails.getHotelStreetAddress()+",\n"+hotelDetails.getCity()+",\n"+hotelDetails.getState()+"-"+hotelDetails.getPincode()+",\n"+hotelDetails.getCountry());





                            }




                        }else {
                            if (progressDialog!=null)
                                progressDialog.dismiss();

                        }
                    }

                    @Override
                    public void onFailure(Call<HotelDetails> call, Throwable t) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }


        });
    }

}
