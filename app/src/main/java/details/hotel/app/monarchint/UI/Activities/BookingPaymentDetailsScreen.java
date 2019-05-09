package details.hotel.app.monarchint.UI.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airpay.airpaysdk_simplifiedotp.AirpayActivity;
import com.airpay.airpaysdk_simplifiedotp.ResponseMessage;
import com.airpay.airpaysdk_simplifiedotp.Transaction;
import com.razorpay.Checkout;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.CRC32;

import details.hotel.app.monarchint.Adapter.OfferNewAdapter;
import details.hotel.app.monarchint.Customs.CustomFonts.TextViewSFProDisplayRegular;
import details.hotel.app.monarchint.Model.AvailabiltyCheckPostData;
import details.hotel.app.monarchint.Model.BookingsNotificationManagers;
import details.hotel.app.monarchint.Model.EmailData;
import details.hotel.app.monarchint.Model.FireBaseModel;
import details.hotel.app.monarchint.Model.Invoice;
import details.hotel.app.monarchint.Model.Offers;
import details.hotel.app.monarchint.Model.Rates;
import details.hotel.app.monarchint.Model.RoomBookings;
import details.hotel.app.monarchint.Model.RoomData;
import details.hotel.app.monarchint.Model.RoomDataBooking;
import details.hotel.app.monarchint.Model.Traveller;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.Constants;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.RestoUtil;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.OfferAPI;
import details.hotel.app.monarchint.WebAPI.RateApi;
import details.hotel.app.monarchint.WebAPI.RoomBookingAPI;
import details.hotel.app.monarchint.WebAPI.SendEmailAPI;
import details.hotel.app.monarchint.WebAPI.TravellerAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingPaymentDetailsScreen extends AppCompatActivity implements ResponseMessage {

    TextViewSFProDisplayRegular mFrom,mTo,mAmount,mNights,mNoRooms,mNoPax;
    EditText mName,mEmail,mPhone,mGstNum,mCompanyname,mOfferEdit;
    TextView mPay,mHotelName,mHotelCharge,mHotelGst,mDiscount,mDisplay,mZingoMoney,mApplyoffer;
    RadioButton mBusiness,mPersonal,mMale,mFemale;
    LinearLayout mBusinessLay;

    RoomDataBooking roomDataBooking;


    //Booking objects
    int travellerIid=0;
    Traveller traveller;
    int adultCountTotal=0,childCountTotal=0,roomCount=0;
    String sellRate,gstPercentage,gstAmounts,totalAmounts,displayPrice;
    String razorpayPaymentID="";

    //airpay
    ResponseMessage resp;
    ArrayList<Transaction> transactionList;
    private String ErrorMessage = "invalid";
    public boolean ischaracter;
    public boolean boolIsError_new = true;


    private int k = 0;
    public final int PERMISSION_REQUEST_CODE = 101;

    ArrayList<Offers> offersArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            setContentView(R.layout.activity_booking_payment_new_design);//  08046767000 7337877282

            getOffersByHotelId(Constants.HOTEL_DATA_ID);

            mFrom = (TextViewSFProDisplayRegular)findViewById(R.id.checkintime);
            mTo = (TextViewSFProDisplayRegular)findViewById(R.id.checkouttime);
            mNoRooms = (TextViewSFProDisplayRegular)findViewById(R.id.room_value);
            mNoPax = (TextViewSFProDisplayRegular)findViewById(R.id.pax_count);
            mAmount = (TextViewSFProDisplayRegular)findViewById(R.id.tv);
            mNights = (TextViewSFProDisplayRegular)findViewById(R.id.txt_no_of_Nights);
            mPay = (TextView) findViewById(R.id.pay);
            mHotelName = (TextView) findViewById(R.id.txt_hotel_name);
            mDisplay = (TextView) findViewById(R.id.hotel_charges_display);
            mHotelCharge = (TextView) findViewById(R.id.hotel_charges);
            mHotelGst = (TextView) findViewById(R.id.hotel_gst_charges);
            mDiscount = (TextView) findViewById(R.id.hotel_discount);
            mZingoMoney = (TextView) findViewById(R.id.zingo_money);
            mApplyoffer = (TextView) findViewById(R.id.apply_promo_code);
            mOfferEdit = (EditText) findViewById(R.id.enter_promo_code);

            mName = (EditText)findViewById(R.id.fullname);
            mEmail = (EditText)findViewById(R.id.email);
            mPhone = (EditText)findViewById(R.id.phone);
            mCompanyname = (EditText)findViewById(R.id.company_name);
            mGstNum = (EditText)findViewById(R.id.company_gst_num);

            mBusiness = (RadioButton) findViewById(R.id.booking_company);
            mPersonal = (RadioButton) findViewById(R.id.booking_personal);
            mPersonal.setChecked(true);

            mMale = (RadioButton) findViewById(R.id.booking_male);
            mFemale = (RadioButton) findViewById(R.id.booking_female);

            mBusinessLay = (LinearLayout) findViewById(R.id.business_layout);

            Bundle bundle = getIntent().getExtras();

            if(bundle!=null){

                roomDataBooking = (RoomDataBooking)bundle.getSerializable("RoomDataBooking");
            }


            if(roomDataBooking!=null){

                mFrom.setText(""+roomDataBooking.getFromDate());
                mTo.setText(""+roomDataBooking.getToDate());

                int days = getDays(roomDataBooking.getFromDate(),roomDataBooking.getToDate());

                mNights.setText(days+" Nights");
               if(roomDataBooking.getRoomData()!=null&&roomDataBooking.getRoomData().size()!=0){

                   mNoRooms.setText(""+roomDataBooking.getRoomData().size());
                   roomCount = roomDataBooking.getRoomData().size();
                   int adultCount = 0;
                   int childCount = 0;

                   for (RoomData roomData:roomDataBooking.getRoomData()) {

                       adultCount = adultCount+roomData.getNoOfAdults();
                       childCount = childCount+roomData.getNoOfChilds();

                   }

                   mNoPax.setText(""+adultCount+" adults, "+childCount+" child");
               }

                AvailabiltyCheckPostData avpd = new AvailabiltyCheckPostData();

                if(PreferenceHandler.getInstance(BookingPaymentDetailsScreen.this).getHotelID()!=0){
                    avpd.setHotelId(PreferenceHandler.getInstance(BookingPaymentDetailsScreen.this).getHotelID());

                    mHotelName.setText(PreferenceHandler.getInstance(BookingPaymentDetailsScreen.this).getHotelName());


                }else{
                    avpd.setHotelId(Constants.HOTEL_DATA_ID);

                }

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                SimpleDateFormat sdfs = new SimpleDateFormat("MMM dd,yyyy");

                try{
                    avpd.setFromDate(sdf.format(sdfs.parse(roomDataBooking.getFromDate())));
                    avpd.setToDate(sdf.format(sdfs.parse(roomDataBooking.getToDate())));
                }catch (Exception e){
                    e.printStackTrace();
                }



                avpd.setRoomCategoryId(roomDataBooking.getRoomCategoryId());
                getRatesByCategoryId(avpd,roomDataBooking);
            }

            mPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String name = mName.getText().toString();
                    String email = mEmail.getText().toString();
                    String phone = mPhone.getText().toString();
                    String company = mCompanyname.getText().toString();
                    String gstNUm = mGstNum.getText().toString();


                    if(name==null || name.isEmpty()){

                        mName.setError("Name required");
                        mName.requestFocus();

                    }else if(email==null || email.isEmpty()){

                        mEmail.setError("Email required");
                        mEmail.requestFocus();

                    }else if(phone==null || phone.isEmpty()){

                        mPhone.setError("Mobile number required");
                        mPhone.requestFocus();

                    }else{

                        String rec = PreferenceHandler.getInstance(BookingPaymentDetailsScreen.this).getRecType();

                        if(mBusiness.isChecked()){

                            if(company==null || company.isEmpty()){

                                mCompanyname.setError("Name required");
                                mCompanyname.requestFocus();

                            }else if(gstNUm==null || gstNUm.isEmpty()){

                                mGstNum.setError("GST Number required");
                                mGstNum.requestFocus();

                            }else{
                                try {
                                    showalertbox();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }else{

                            try {
                                showalertbox();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }








                    }
                }
            });

            mPersonal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBusinessLay.setVisibility(View.GONE);
                }
            });

            mBusiness.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBusinessLay.setVisibility(View.VISIBLE);
                }
            });

            mApplyoffer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String offer_code = mOfferEdit.getText().toString();

                    if(offer_code==null&&offer_code.isEmpty()){

                        Toast.makeText(BookingPaymentDetailsScreen.this, "Please enter Coupon Code", Toast.LENGTH_SHORT).show();

                    }else if (offersArrayList==null&&offersArrayList.size()==0){

                        Toast.makeText(BookingPaymentDetailsScreen.this, "No Offer for you", Toast.LENGTH_SHORT).show();

                    }else{

                        for(int i=0;i<offersArrayList.size();i++){

                            if(offer_code.equalsIgnoreCase(offersArrayList.get(i).getCouponCode())){

                                calculation(offersArrayList.get(i).getPercentage());
                                break;
                            }
                        }
                    }


                }
            });


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void getRatesByCategoryId(final AvailabiltyCheckPostData avpd,final RoomDataBooking roomDataBooking){

        RateApi apiService = Util.getClient().create(RateApi.class);
        //String authenticationString = Util.getToken(context);
        String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
        Call<ArrayList<Rates>> call = apiService.getRatesForDate(authenticationString,avpd);

        call.enqueue(new Callback<ArrayList<Rates>>() {
            @Override
            public void onResponse(Call<ArrayList<Rates>> call, Response<ArrayList<Rates>> response) {
                try {
                    int statusCode = response.code();

                    if (statusCode == 200||statusCode==201||statusCode==204) {

                        if(response.body()!=null&&response.body().size()!=0){



                            int total = 0 ;
                            double gstAmount = 0 ;
                            int totalAMount = 0 ;
                            int displayRate =0 ;

                            for (int i =0;i<(response.body().size()-1);i++){
                                Rates rates = response.body().get(i);

                                if(rates!=null){

                                    if(roomDataBooking.getRoomData()!=null&&roomDataBooking.getRoomData().size()!=0){

                                        for (RoomData roomDaat: roomDataBooking.getRoomData()) {

                                            int roomAdult = roomDaat.getNoOfAdults();
                                            adultCountTotal = adultCountTotal+roomAdult;
                                            childCountTotal = childCountTotal+roomDaat.getNoOfChilds();
                                            int value =  0;

                                            if(roomAdult==1){

                                                displayRate = displayRate+rates.getDeclaredRateForSingle();
                                                total = total+rates.getSellRateForSingle();
                                                value = rates.getSellRateForSingle();
                                            }else if(roomAdult==2){

                                                displayRate = displayRate+rates.getDeclaredRateForDouble();
                                                total = total+rates.getDeclaredRateForDouble();
                                                value = rates.getDeclaredRateForDouble();
                                            }else if(roomAdult==3){

                                                displayRate = displayRate+rates.getDeclaredRateForTriple();
                                                total = total+rates.getSellRateForTriple();
                                                value = rates.getSellRateForTriple();
                                            }

                                            if(value >= 0 && value <= 999.99)
                                            {

                                               gstAmount = gstAmount+0;
                                            }else if(value >= 1000 && value <= 2499.99)
                                            {


                                                double gst = (value * 12);
                                                double gstamount = gst /100;
                                                gstAmount=gstAmount+gstamount;

                                            }
                                            else if(value >= 2500 && value <= 7499.99)
                                            {
                                                double gst = (value * 18);
                                                double gstamount = gst /100;
                                                gstAmount=gstAmount+gstamount;
                                            }
                                            else if(value >= 7500)
                                            {
                                                double gst = (value * 28);
                                                double gstamount = gst /100;
                                                gstAmount=gstAmount+gstamount;

                                            }


                                        }

                                        sellRate = total+"";
                                        displayPrice = displayPrice+"";
                                        gstAmounts = (int)gstAmount+"";
                                        totalAMount = totalAMount + total+ (int)gstAmount;
                                        totalAmounts = totalAMount+"";

                                        double disvalue = totalAMount*10;
                                        double dis = disvalue/100;
                                        int value = totalAMount - (int)dis;
                                        mAmount.setText("₹ "+value);
                                        mDiscount.setText("₹ "+(int)dis);

                                        double disvalues = value*10;
                                        double diss = disvalues/100;
                                        int values = value - (int)diss;

                                        mPay.setText("Pay ₹ "+value);
                                        mHotelCharge.setText("₹ "+sellRate);
                                        mDisplay.setText("₹ "+displayPrice);
                                        mHotelGst.setText("₹ "+gstAmounts);
                                    }


                                }
                            }


                        }




                    }else {

                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rates>> call, Throwable t) {
                // Log error here since request failed

                Log.e("TAG", t.toString());
            }
        });


    }

    private void addTraveler(final String type){

        final ProgressDialog progressDialog = new ProgressDialog(BookingPaymentDetailsScreen.this);
        progressDialog.setTitle("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        final Traveller dto = new Traveller();
        dto.setFirstName(mName.getText().toString());
        dto.setMiddleName("");
        dto.setLastName("");
        dto.setPhoneNumber(mPhone.getText().toString());
        dto.setEmail(mEmail.getText().toString());
        dto.setUserRoleId(1);
        if(mBusiness.isChecked()){
            dto.setCompany(mCompanyname.getText().toString());
            dto.setCustomerGST(mGstNum.getText().toString());
        }


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                TravellerAPI apiService = Util.getClient().create(TravellerAPI.class);
                Call<Traveller> call = apiService.addTraveler(authenticationString,dto);

                call.enqueue(new Callback<Traveller>() {
                    @Override
                    public void onResponse(Call<Traveller> call, Response<Traveller> response) {

                        int statusCode = response.code();
                        try{
                            if (statusCode == 200 || statusCode == 201 || statusCode == 203 || statusCode == 204) {

                                if (progressDialog!=null)
                                    progressDialog.dismiss();

                                Traveller dto = response.body();
                                System.out.println("Response Traveller==="+response.body());

                                if (dto != null) {
                                    traveller = dto;
                                    travellerIid  = dto.getTravellerId();

                                    validateBook(type);
                                }


                            }else {
                                if (progressDialog!=null)
                                    progressDialog.dismiss();
                                Toast.makeText(BookingPaymentDetailsScreen.this, " Failed due to : "+response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(Call<Traveller> call, Throwable t) {
                        // Log error here since request failed
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }


        });
    }

    private void validateBook(final String type){
        try{

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat sdfs = new SimpleDateFormat("MMM dd,yyyy");

           /* try{
                avpd.setFromDate(sdf.format(sdfs.parse(roomDataBooking.getFromDate())));
                avpd.setToDate(sdf.format(sdfs.parse(roomDataBooking.getToDate())));
            }catch (Exception e){
                e.printStackTrace();
            }*/
            String from = roomDataBooking.getFromDate();
            String to = roomDataBooking.getToDate();
            String adult = ""+adultCountTotal;
            String child = ""+childCountTotal;
            String sell = sellRate;
            String gst = gstAmounts;
            String gsta = gstAmounts;
            String total = totalAmounts;
            String checkintime = "12:00 pm";
            String checkouttime = "11:00 am";


            String roomType = roomDataBooking.getRoomCategory();


            if(from == null || from.isEmpty()){

                Toast.makeText(BookingPaymentDetailsScreen.this, "Something not correct", Toast.LENGTH_SHORT).show();

            }else  if(to == null || to.isEmpty()){

                Toast.makeText(BookingPaymentDetailsScreen.this, "Something not correct", Toast.LENGTH_SHORT).show();

            }else  if(checkintime == null || checkintime.isEmpty()){

                Toast.makeText(BookingPaymentDetailsScreen.this, "Something not correct", Toast.LENGTH_SHORT).show();

            }else  if(checkouttime == null || checkouttime.isEmpty()){

                Toast.makeText(BookingPaymentDetailsScreen.this, "Something not correct", Toast.LENGTH_SHORT).show();

            }
            else  if(adult == null || adult.isEmpty()){

                Toast.makeText(BookingPaymentDetailsScreen.this, "Something not correct", Toast.LENGTH_SHORT).show();

            }else  if(child == null || child.isEmpty()){

                Toast.makeText(BookingPaymentDetailsScreen.this, "Something not correct", Toast.LENGTH_SHORT).show();

            }else  if(roomType == null || roomType.isEmpty()){

                Toast.makeText(BookingPaymentDetailsScreen.this, "Something not correct", Toast.LENGTH_SHORT).show();

            }else  if(sell == null || sell.isEmpty()){

                Toast.makeText(BookingPaymentDetailsScreen.this, "Something not correct", Toast.LENGTH_SHORT).show();

            }else  if(gst == null || gst.isEmpty()){

                Toast.makeText(BookingPaymentDetailsScreen.this, "Something not correct", Toast.LENGTH_SHORT).show();

            }else  if(gsta == null || gsta.isEmpty()){

                Toast.makeText(BookingPaymentDetailsScreen.this, "Something not correct", Toast.LENGTH_SHORT).show();

            }else  if(total == null || total.isEmpty()){

                Toast.makeText(BookingPaymentDetailsScreen.this, "Something not correct", Toast.LENGTH_SHORT).show();

            }else{
                RoomBookings bookings = new RoomBookings();
                String bookingnumber = randomByDate();
                bookings.setBookingNumber(bookingnumber);

                bookings.setTravellerId(travellerIid);
                bookings.setCheckInDate(sdf.format(sdfs.parse(roomDataBooking.getFromDate())));
                bookings.setOptCheckInDate(sdf.format(sdfs.parse(roomDataBooking.getFromDate())));
                bookings.setCheckOutDate(sdf.format(sdfs.parse(roomDataBooking.getToDate())));
                bookings.setOptCheckOutDate(sdf.format(sdfs.parse(roomDataBooking.getToDate())));

                bookings.setBookingSourceType("Offline");
                bookings.setBookingSource("Zingo Direct");
                bookings.setTravellerAgentId(PreferenceHandler.getInstance(BookingPaymentDetailsScreen.this).getTravellerId());

                if(PreferenceHandler.getInstance(BookingPaymentDetailsScreen.this).getHotelID()!=0){
                    bookings.setHotelId(PreferenceHandler.getInstance(BookingPaymentDetailsScreen.this).getHotelID());


                }else{
                    bookings.setHotelId(Constants.HOTEL_DATA_ID);

                }

                bookings.setNoOfAdults(Integer.parseInt(adult));
                bookings.setNoOfChild(Integer.parseInt(child));
                bookings.setBookingStatus("Quick");



                String [] arrOfStr = gst.split("%", 2);
                int value = Integer.parseInt(arrOfStr[0]);
                bookings.setGst(value);
                bookings.setTotalAmount((int) Double.parseDouble(total));

                if(type!=null&&type.equalsIgnoreCase("paylater")){

                    bookings.setBalanceAmount((int) Double.parseDouble(total));
                }else{
                    /*double valu =  Double.parseDouble(total) * 10;
                    double per = valu/100.0;*/
                    bookings.setBalanceAmount(0);
                }

                bookings.setBookingPlan("EP");
                bookings.setRoomCategory(roomType);

                Date fd = null,td = null;

                try {
                    fd = sdfs.parse(roomDataBooking.getFromDate());
                    td = sdfs.parse(roomDataBooking.getToDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long diffDays=1;
                try {
                    long diff = td.getTime() - fd.getTime();
                     diffDays = diff / (24 * 60 * 60 * 1000);

                }catch(Exception e){
                    e.printStackTrace();
                }
                bookings.setDurationOfStay((int) diffDays);
                bookings.setCheckInTime("12:00 pm");
                bookings.setCheckOutTime("11:00 am");

                bookings.setNoOfRooms(roomCount);
                bookings.setSellRate((int)Double.parseDouble(sell));
                bookings.setGstAmount((int) Double.parseDouble(gsta));



                long date = System.currentTimeMillis();


                String bookingDate = sdf.format(date);

                bookings.setBookingDate(bookingDate);

                SimpleDateFormat sdft = new SimpleDateFormat("hh:mm a");
                Date d = new Date();
                String time = sdft.format(d);

                bookings.setBookingTime(time);

                updateRoomBooking(bookings);
            }
        }catch (Exception e){
            e.printStackTrace();
        }





    }

    //Generate BookingNumber
    public String randomByDate() throws Exception{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date random = new Date();
        String bookNumber = dateFormat.format(random);
        System.out.println(""+bookNumber);
        return ""+bookNumber;
    }

    private void updateRoomBooking(final RoomBookings bookings){

        final ProgressDialog progressDialog = new ProgressDialog(BookingPaymentDetailsScreen.this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                RoomBookingAPI apiService = Util.getClient().create(RoomBookingAPI.class);
                Call<RoomBookings> call = apiService.postBooking(authenticationString,bookings);

                call.enqueue(new Callback<RoomBookings>() {
                    @Override
                    public void onResponse(Call<RoomBookings> call, Response<RoomBookings> response) {

                        int statusCode = response.code();
                        try{
                            if (statusCode == 200 || statusCode == 201 || statusCode == 203 || statusCode == 204) {

                                if (progressDialog != null)
                                    progressDialog.dismiss();
                                RoomBookings dto = response.body();

                                if (dto != null) {



                                   /* String body = "<html><head><style>table " +
                                            "{border-collapse: collapse;}" +
                                            "table, td, th {" +
                                            "border: 1px solid black;} table th{\n" +
                                            "  color:#0000ff;\n" +
                                            "}" +
                                            "</style></head>" +
                                            "<body>" +
                                            "<h2>Dear Team Members,</h2>" +
                                            "<p><br>One Booking happened with 10% payment Payment Id: "+razorpayPaymentID+" Booking id:"+dto.getBookingId()+"</p></br></br>"+

                                            "</br><p><b>Thanks</b></p><br><br></body></html>";

                                    EmailData emailData = new EmailData();
                                    emailData.setEmailAddress(PreferenceHandler.getInstance(BookingPaymentDetailsScreen.this).getEmailList()+",abhinav@zingohotels.com,nishar@zingohotels.com");
                                    emailData.setBody(body);
                                    emailData.setSubject("Hotel Monarch International Hotel Booking");
                                    emailData.setUserName("nishar@zingohotels.com");
                                    emailData.setPassword("Razin@1993");
                                    emailData.setFromName("Nishar");
                                    emailData.setFromEmail("nishar@zingohotels.com");*/

                                    Invoice invoice = new Invoice();
                                    invoice.setTravellerId(dto.getTravellerId());
                                    invoice.setEmailAddress(mEmail.getText().toString()+",nishar@zingohotels.com,abhinav@zingohotels.com");
                                    invoice.setBookingNumber(dto.getBookingNumber());
                                    invoice.setHotelId(dto.getHotelId());
                                    invoice.setCheckInDate(dto.getCheckInDate());
                                    invoice.setCheckOutDate(dto.getCheckOutDate());
                                    invoice.setRoomNo("0");

                                    Invoice invoices = new Invoice();
                                    invoices.setTravellerId(dto.getTravellerId());
                                    invoices.setEmailAddress("hotelmonarchint@gmail.com,nishar@zingohotels.com,abhinav@zingohotels.com");
                                    invoices.setBookingNumber(dto.getBookingNumber());
                                    invoices.setHotelId(dto.getHotelId());
                                    invoices.setCheckInDate(dto.getCheckInDate());
                                    invoices.setCheckOutDate(dto.getCheckOutDate());
                                    invoices.setRoomNo("0");

                                    if(Util.isNetworkAvailable(BookingPaymentDetailsScreen.this)){
                                        sendEmailAutomatics(invoice);
                                        sendEmailAutomatics(invoices);
                                    }else{
                                        Toast.makeText(BookingPaymentDetailsScreen.this, "Please check your Internet Connection", Toast.LENGTH_LONG).show();
                                    }

                                    FireBaseModel fm = new FireBaseModel();
                                    fm.setSenderId("415720091200");
                                    fm.setServerId("AIzaSyBFdghUu7AgQVnu27xkKKLHJ6oSz9AnQ8M");
                                    fm.setHotelId(dto.getHotelId());
                                    fm.setTitle("New Booking from Zingo Hotels");
                                    fm.setMessage("Congrats! Hotel Monarch International got one new booking for "+dto.getDurationOfStay() +" nights from "+dto.getCheckInDate()+" to "+dto.getCheckInDate()+"\nBooking ID:"+dto.getBookingId());
                                    //registerTokenInDB(fm);
                                    fm.setTravellerName(mName.getText().toString());
                                    fm.setNoOfGuest("No of Guest: "+dto.getNoOfAdults());
                                    fm.setCheckInDate(""+dto.getCheckInDate());
                                    fm.setCheckOutDate(""+dto.getCheckOutDate());
                                    fm.setTotalAmount("Rs. "+dto.getTotalAmount());
                                    fm.setCommissionAmount("Rs. "+dto.getCommissionAmount());
                                    fm.setNetAmount("Rs. "+dto.getTotalAmount());
                                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy, hh:mm a");
                                    SimpleDateFormat sdft = new SimpleDateFormat("hh:mm a");
                                    fm.setNotificationDate(sdf.format(new Date()));
                                    fm.setNotificationTime(sdft.format(new Date()));
                                    fm.setBookingDateTime(""+dto.getBookingDate());

                                    sendNotification(fm,dto.getBookingStatus());

                                    Toast.makeText(BookingPaymentDetailsScreen.this, "Booking done", Toast.LENGTH_SHORT).show();
                                    Intent book = new Intent(BookingPaymentDetailsScreen.this,BookingDoneScreen.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("RoomBookings",dto);
                                    bundle.putSerializable("Traveller",traveller);
                                    book.putExtras(bundle);
                                    startActivity(book);
                                    BookingPaymentDetailsScreen.this.finish();
                                 }

                            } else {
                                if (progressDialog != null)
                                    progressDialog.dismiss();

                                Toast.makeText(BookingPaymentDetailsScreen.this, " failed due to : " + response.message(), Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }




                    }

                    @Override
                    public void onFailure(Call<RoomBookings> call, Throwable t) {
                        // Log error here since request failed
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });

            }


        });


    }

    private int getDays(final String checkinDate,final String checkoutDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");
        Date fd = null,td = null;
        try {
            fd = sdf.parse(checkinDate);
            //System.out.println("Text=="+book_from_date.getText().toString()+" Date"+fd);
            td = sdf.parse(checkoutDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            long diff = td.getTime() - fd.getTime();
            int diffDays = (int) diff / (24 * 60 * 60 * 1000);
            System.out.println("Diff===" + diffDays);
            return diffDays;

        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
        double amount=0;
        double per = 0;

        try {
            JSONObject options = new JSONObject();
            if(mAmount.getText().toString().contains(" ")){
                String total[] = mAmount.getText().toString().split(" ");
                amount = Double.parseDouble(total[1]);
                double valu = amount * 10;
                per = valu/100.0;
            }else{
                amount = Double.parseDouble(mAmount.getText().toString());
                double valu = amount * 10;
                per = valu/100.0;
            }
            //int amount = Integer.parseInt(mHotelTotalCharges.getText().toString());
            options.put("name", mHotelName.getText().toString() );
            options.put("description", "For  "+mName.getText().toString());
            //You can omit the image option to fetch the image from dashboard
            //options.put("image", R.drawable.app_logo);
            options.put("currency", "INR");
            //options.put("amount",amount * 100);
            options.put("amount",(int)per * 100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", mEmail.getText().toString());
            preFill.put("contact",mPhone.getText().toString() );

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
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

    private void sendEmailAutomatics(final Invoice emailData) {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Sending Email..");
        dialog.setCancelable(false);
        dialog.show();


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {

                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                SendEmailAPI travellerApi = Util.getClient().create(SendEmailAPI.class);
                Call<String> response = travellerApi.sendEmails(authenticationString,emailData);

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

    private void showalertbox() throws Exception{


        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(BookingPaymentDetailsScreen.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.exit_alert_layout,null);
        TextView dontCancelBtn = (TextView)view.findViewById(R.id.no_btn);
        dontCancelBtn.setText("Pay @ Hotel");
        TextView cancelBtn = (TextView)view.findViewById(R.id.exit_app_btn);
        cancelBtn.setText("Pay Now");
        TextView ask = (TextView)view.findViewById(R.id.ask);
        TextViewSFProDisplayRegular text = (TextViewSFProDisplayRegular)view.findViewById(R.id.text);

        text.setText("Payment");
        ask.setText("Do you want to Pay?");
        dialogBuilder.setView(view);
        final android.app.AlertDialog dialog = dialogBuilder.create();
        dialog.show();
        dontCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    if(dialog != null)
                    {
                        dialog.dismiss();
                    }

                    if(travellerIid==0){
                        addTraveler("paylater");
                        //System.out.println("Not exist "+isexist(tlist));
                    }else
                    {
                        validateBook("paylater");
                    }

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    if(dialog != null)
                    {
                        dialog.dismiss();
                    }

                    airpay();

                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
    }

    //airpay Payment Gatewy
    public void callback(ArrayList<Transaction> data, boolean flag) {
        if (data != null) {
        }
    }

    public void airpay(){


        Intent myIntent = new Intent(this, AirpayActivity.class);

        Bundle b = new Bundle();

        // Please enter Merchant configuration value


        // Live Merchant Details - Merchant Id -
        b.putString("USERNAME", Constants.AIR_UN);
        b.putString("PASSWORD", Constants.AIR_PWD);
        b.putString("SECRET", Constants.AIR_SEC);
        b.putString("MERCHANT_ID", Constants.AIR_MI);


        b.putString("EMAIL", mEmail.getText().toString().trim());

        // This is for dynamic phone no value code - Uncomment it
        b.putString("PHONE", "" + mPhone.getText().toString().trim());
					/*//  Please enter phone no value
					b.putString("PHONE", "");*/
        b.putString("FIRSTNAME", mName.getEditableText().toString().trim());
        b.putString("LASTNAME", "");
        // b.putString("ADDRESS", address.getEditableText().toString().trim());
        //b.putString("CITY", city.getEditableText().toString().trim());
        //b.putString("STATE", state.getEditableText().toString().trim());
        //b.putString("COUNTRY", country.getEditableText().toString().trim());
        //b.putString("PIN_CODE", pincode.getEditableText().toString().trim());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date random = new Date();
        String bookNumber = dateFormat.format(random);
        b.putString("ORDER_ID", "OGB"+bookNumber);




        if(mAmount.getText().toString().contains(" ")){
            String total[] = mAmount.getText().toString().split(" ");
            double amount = Double.parseDouble(total[1]);
            double valu = amount * 10;
            double per = valu/100.0;
            String perValue = String.valueOf(amount);
            b.putString("AMOUNT", perValue.trim());
        }else{
            double amount = Double.parseDouble(mAmount.getText().toString());
            double valu = amount * 10;
            double per = valu/100.0;
            String perValue = String.valueOf(per);
            b.putString("AMOUNT", perValue.trim());
        }

        b.putString("CURRENCY", "356");
        b.putString("ISOCURRENCY", "INR");
        b.putString("CHMOD", "");
        b.putString("CUSTOMVAR", "");
        b.putString("TXNSUBTYPE", "");
        b.putString("WALLET", "0");

        // Live Success URL Merchant Id -
        b.putString("SUCCESS_URL", "www.zingohotels.com");


        b.putParcelable("RESPONSEMESSAGE", (Parcelable) resp);

        myIntent.putExtras(b);
        startActivityForResult(myIntent, 120);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Bundle bundle = data.getExtras();
            transactionList = new ArrayList<Transaction>();
            transactionList = (ArrayList<Transaction>) bundle.getSerializable("DATA");
            if (transactionList != null) {
                Toast.makeText(this, transactionList.get(0).getSTATUS() + "\n" + transactionList.get(0).getSTATUSMSG(), Toast.LENGTH_LONG).show();

                if (transactionList.get(0).getSTATUS() != null) {
                    Log.e("STATUS -> ", "=" + transactionList.get(0).getSTATUS());

                }
                if (transactionList.get(0).getMERCHANTKEY() != null) {
                    Log.e("MERCHANT KEY -> ", "=" + transactionList.get(0).getMERCHANTKEY());

                }
                if (transactionList.get(0).getMERCHANTPOSTTYPE() != null) {
                    Log.e("MERCHANT POST TYPE ", "=" + transactionList.get(0).getMERCHANTPOSTTYPE());
                }
                if (transactionList.get(0).getSTATUSMSG() != null) {
                    Log.e("STATUS MSG -> ", "=" + transactionList.get(0).getSTATUSMSG()); //  success or fail
                }
                if (transactionList.get(0).getTRANSACTIONAMT() != null) {
                    Log.e("TRANSACTION AMT -> ", "=" + transactionList.get(0).getTRANSACTIONAMT());

                }
                if (transactionList.get(0).getTXN_MODE() != null) {
                    Log.e("TXN MODE -> ", "=" + transactionList.get(0).getTXN_MODE());
                }
                if (transactionList.get(0).getMERCHANTTRANSACTIONID() != null) {
                    Log.e("MERCHANT_TXN_ID -> ", "=" + transactionList.get(0).getMERCHANTTRANSACTIONID()); // order id

                }
                if (transactionList.get(0).getSECUREHASH() != null) {
                    Log.e("SECURE HASH -> ", "=" + transactionList.get(0).getSECUREHASH());
                }
                if (transactionList.get(0).getCUSTOMVAR() != null) {
                    Log.e("CUSTOMVAR -> ", "=" + transactionList.get(0).getCUSTOMVAR());
                }
                if (transactionList.get(0).getTRANSACTIONID() != null) {
                    Log.e("TXN ID -> ", "=" + transactionList.get(0).getTRANSACTIONID());
                }
                if (transactionList.get(0).getTRANSACTIONSTATUS() != null) {
                    Log.e("TXN STATUS -> ", "=" + transactionList.get(0).getTRANSACTIONSTATUS());
                }
                if (transactionList.get(0).getTXN_DATE_TIME() != null) {
                    Log.e("TXN_DATETIME -> ", "=" + transactionList.get(0).getTXN_DATE_TIME());
                }
                if (transactionList.get(0).getTXN_CURRENCY_CODE() != null) {
                    Log.e("TXN_CURRENCY_CODE -> ", "=" + transactionList.get(0).getTXN_CURRENCY_CODE());
                }
                if (transactionList.get(0).getTRANSACTIONVARIANT() != null) {
                    Log.e("TRANSACTIONVARIANT -> ", "=" + transactionList.get(0).getTRANSACTIONVARIANT());
                }
                if (transactionList.get(0).getCHMOD() != null) {
                    Log.e("CHMOD -> ", "=" + transactionList.get(0).getCHMOD());
                }
                if (transactionList.get(0).getBANKNAME() != null) {
                    Log.e("BANKNAME -> ", "=" + transactionList.get(0).getBANKNAME());
                }
                if (transactionList.get(0).getCARDISSUER() != null) {
                    Log.e("CARDISSUER -> ", "=" + transactionList.get(0).getCARDISSUER());
                }
                if (transactionList.get(0).getFULLNAME() != null) {
                    Log.e("FULLNAME -> ", "=" + transactionList.get(0).getFULLNAME());
                }
                if (transactionList.get(0).getEMAIL() != null) {
                    Log.e("EMAIL -> ", "=" + transactionList.get(0).getEMAIL());
                }
                if (transactionList.get(0).getCONTACTNO() != null) {
                    Log.e("CONTACTNO -> ", "=" + transactionList.get(0).getCONTACTNO());
                }
                if (transactionList.get(0).getMERCHANT_NAME() != null) {
                    Log.e("MERCHANT_NAME -> ", "=" + transactionList.get(0).getMERCHANT_NAME());
                }
                if (transactionList.get(0).getSETTLEMENT_DATE() != null) {
                    Log.e("SETTLEMENT_DATE -> ", "=" + transactionList.get(0).getSETTLEMENT_DATE());
                }
                if (transactionList.get(0).getSURCHARGE() != null) {
                    Log.e("SURCHARGE -> ", "=" + transactionList.get(0).getSURCHARGE());
                }
                if (transactionList.get(0).getBILLEDAMOUNT() != null) {
                    Log.e("BILLEDAMOUNT -> ", "=" + transactionList.get(0).getBILLEDAMOUNT());
                }
                if (transactionList.get(0).getISRISK() != null) {
                    Log.e("ISRISK -> ", "=" + transactionList.get(0).getISRISK());
                }

                String transid = transactionList.get(0).getMERCHANTTRANSACTIONID();
                String apTransactionID = transactionList.get(0).getTRANSACTIONID();
                String amount = transactionList.get(0).getTRANSACTIONAMT();
                String transtatus = transactionList.get(0).getTRANSACTIONSTATUS();
                String message = transactionList.get(0).getSTATUSMSG();

                String merchantid = Constants.AIR_MI; // Please enter Merchant Id
                String username = Constants.AIR_UN;        // Please enter Username
                String sParam = transid + ":" + apTransactionID + ":" + amount + ":" + transtatus + ":" + message + ":" + merchantid + ":" + username;
                CRC32 crc = new CRC32();
                crc.update(sParam.getBytes());
                String sCRC = "" + crc.getValue();
                Log.e("Verified Hash ==", "Verified Hash= " + sCRC);

                if (sCRC.equalsIgnoreCase(transactionList.get(0).getSECUREHASH())) {
                    Log.e("Airpay Secure ->", " Secure hash mismatched");
                } else {
                    Log.e("Airpay Secure ->", " Secure hash matched");
                }


                //Log.e("Remaining Params-->>","Remaining Params-->>"+transactionList.get(0).getMyMap());

                // This code is to get remaining extra value pair.
                for (String key : transactionList.get(0).getMyMap().keySet()) {
                    Log.e("EXTRA-->>", "KEY: " + key + " VALUE: " + transactionList.get(0).getMyMap().get(key));
                    String extra_param= transactionList.get(0).getMyMap().get("PRI_ACC_NO_START"); // To replace key value as you want
                    Log.e("Extra Param -->","="+extra_param);
                    transactionList.get(0).getMyMap().get(key);
                }

                if(transactionList.get(0).getSTATUS().equalsIgnoreCase("200")||transactionList.get(0).getSTATUS().contains("success")){

                    razorpayPaymentID = apTransactionID;

                    if(travellerIid==0){
                        addTraveler("paynow");
                        //System.out.println("Not exist "+isexist(tlist));
                    }else
                    {
                        validateBook("paynow");
                    }

                }

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.e("Error Message --- >>>", "Error Message --- >>> " + e.getMessage());
        }
    }

    public void getOffersByHotelId(final int hotelId)
    {


        //restId = 0;

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {

                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";

                OfferAPI hotelOperation = Util.getClient().create(OfferAPI.class);
                Call<ArrayList<Offers>> response = hotelOperation.getOffersByHotelId(authenticationString, hotelId);

                response.enqueue(new Callback<ArrayList<Offers>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Offers>> call, Response<ArrayList<Offers>> response) {
                        System.out.println("GetHotelByProfileId = "+response.code());



                        if(response.code() == 200)
                        {
                            try{
                                offersArrayList = response.body();

                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {
                        }


                    }

                    @Override
                    public void onFailure(Call<ArrayList<Offers>> call, Throwable t) {
                        System.out.println("Failed");
                        System.out.println(" Exception = "+t.getMessage());

                    }
                });
            }
        });


    }

    public void calculation(final String per){

        try{

            double percentage = Double.parseDouble(per);
            int totalAMount = Integer.parseInt(totalAmounts);

            double disvalue = totalAMount*percentage;
            double dis = disvalue/100;
            int value = totalAMount - (int)dis;
            mAmount.setText("₹ "+value);
            mDiscount.setText("₹ "+(int)dis);

            double disvalues = value*percentage;
            double diss = disvalues/100;
            int values = value - (int)diss;

            mPay.setText("Pay ₹ "+value);
            mHotelCharge.setText("₹ "+sellRate);
            mDisplay.setText("₹ "+displayPrice);
            mHotelGst.setText("₹ "+gstAmounts);

            mZingoMoney.setText("You got "+per+"% discount from total amount.");

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }




    }

    public void sendNotification(final FireBaseModel fireBaseModel, final String bookingStatus) {


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                String auth_string = Util.getToken(BookingPaymentDetailsScreen.this);
                RoomBookingAPI apiService =
                        Util.getClient().create(RoomBookingAPI.class);


                System.out.println("Model" + fireBaseModel.toString());
                Call<ArrayList<String>> call = apiService.sendBookingNotification(auth_string, fireBaseModel)/*getString()*/;

                call.enqueue(new Callback<ArrayList<String>>() {
                    @Override
                    public void onResponse(Call<ArrayList<String>> call, retrofit2.Response<ArrayList<String>> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();
                        try{
                            if (statusCode == 200) {

                                ArrayList<String> list = response.body();

                                Toast.makeText(BookingPaymentDetailsScreen.this, "Notification Send Successfully", Toast.LENGTH_SHORT).show();



                                //sendEmailattache();
                               /* NotificationManager nf = new NotificationManager();
                                nf.setNotificationText(fireBaseModel.getTitle());
                                nf.setNotificationFor(fireBaseModel.getMessage());
                                nf.setHotelId(fireBaseModel.getHotelId());
                                savenotification(nf);*/

                                BookingsNotificationManagers nf = new BookingsNotificationManagers();
                                nf.setTitle(fireBaseModel.getTitle());
                                nf.setMessage(fireBaseModel.getMessage());
                                nf.setHotelId(fireBaseModel.getHotelId());
                                nf.setTravellerName(fireBaseModel.getTravellerName());
                                nf.setCheckInDate(fireBaseModel.getCheckInDate());
                                nf.setCheckOutDate(fireBaseModel.getCheckOutDate());
                                nf.setTotalAmount(fireBaseModel.getTotalAmount());
                                nf.setCommissionAmount(fireBaseModel.getCommissionAmount());
                                nf.setNetAmount(fireBaseModel.getNetAmount());
                                nf.setNoOfGuest(fireBaseModel.getNoOfGuest());
                                nf.setNotificationDate(fireBaseModel.getNotificationDate());
                                nf.setNotificationTime(fireBaseModel.getNotificationTime());
                                nf.setBookingDateTime(fireBaseModel.getBookingDateTime());
                                nf.setBooking(bookingStatus);
                                saveBookingnotification(nf);



                            } else {

                                Toast.makeText(BookingPaymentDetailsScreen.this, " failed due to status code:" + statusCode, Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                        // Log error here since request failed

                        Log.e("TAG", t.toString());
                    }
                });
            }


        });
    }

    private void saveBookingnotification(final BookingsNotificationManagers notification) {

        final ProgressDialog dialog = new ProgressDialog(BookingPaymentDetailsScreen.this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hotel id = "+notification.getHotelId());
                String auth_string = Util.getToken(BookingPaymentDetailsScreen.this);
                RoomBookingAPI travellerApi = Util.getClient().create(RoomBookingAPI.class);
                Call<BookingsNotificationManagers> response = travellerApi.saveBookingNotification(auth_string,notification);

                response.enqueue(new Callback<BookingsNotificationManagers>() {
                    @Override
                    public void onResponse(Call<BookingsNotificationManagers> call, Response<BookingsNotificationManagers> response) {

                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        try{
                            System.out.println(response.code());
                            if(response.code() == 200||response.code() == 201)
                            {
                                if(response.body() != null)
                                {



                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<BookingsNotificationManagers> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }

}
