package details.hotel.app.monarchint.UI.Fragments;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import details.hotel.app.monarchint.Customs.CustomFonts.TextViewRobotoregular;
import details.hotel.app.monarchint.Model.AvailabiltyCheckGetData;
import details.hotel.app.monarchint.Model.AvailabiltyCheckPostData;
import details.hotel.app.monarchint.Model.Rates;
import details.hotel.app.monarchint.Model.RoomCategories;
import details.hotel.app.monarchint.Model.RoomData;
import details.hotel.app.monarchint.Model.RoomDataBooking;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.UI.Activities.BookingPaymentDetailsScreen;
import details.hotel.app.monarchint.Utils.Constants;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.AvailablityAPI;
import details.hotel.app.monarchint.WebAPI.HotelsDetailsAPI;
import details.hotel.app.monarchint.WebAPI.RateApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookNowFragment extends Fragment {

    TextViewRobotoregular mCategoryName,mAvailablity,mBook,mChangeCategoryName,mDateText,mPrice;
    //  TextView mBed,mAdult,mChild;
    LinearLayout mChangeCategory,mDateChange,mRoomAddLayout;
    ImageView mCategoryImage;//mBedAdd,mBedRemove,mAdultAdd,mAdultRemove,mChildAdd,mChildRemove,
    RoomCategories roomCategory;
    Button mAddRoom;

    ArrayList<RoomCategories> roomCategoriesArrayList;

    String fromDate = "",toDate="",from="",to="",imageUrl,fromText,toText;
    SimpleDateFormat sdf,sda;
    int availableRoomInCategory=0;

    ArrayList<RoomData> roomData;
    RoomDataBooking roomDataBooking;


    public BookNowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{
            View view = inflater.inflate(R.layout.fragment_book_now,container,false);

            mCategoryName = (TextViewRobotoregular)view.findViewById(R.id.category_name_book);
            mAvailablity = (TextViewRobotoregular)view.findViewById(R.id.room_avail);
            mBook = (TextViewRobotoregular)view.findViewById(R.id.book_category_text);
            mChangeCategoryName = (TextViewRobotoregular)view.findViewById(R.id.changeCategory);
            mDateText = (TextViewRobotoregular)view.findViewById(R.id.date_text);
            mPrice = (TextViewRobotoregular)view.findViewById(R.id.room_price);
            mAddRoom = (Button) view.findViewById(R.id.add_room);

           /* mBed = (TextView)findViewById(R.id.bed_count);
            mAdult = (TextView)findViewById(R.id.adult_count);
            mChild = (TextView)findViewById(R.id.child_count);*/

            mChangeCategory = (LinearLayout)view.findViewById(R.id.category_change);
            mDateChange = (LinearLayout)view.findViewById(R.id.date_layout);
            mRoomAddLayout = (LinearLayout)view.findViewById(R.id.room_add_layout);

            /*mBedAdd = (ImageView)findViewById(R.id.bed_add);
            mBedRemove = (ImageView)findViewById(R.id.bed_remove);
            mAdultAdd = (ImageView)findViewById(R.id.adult_add);
            mAdultRemove = (ImageView)findViewById(R.id.adult_remove);
            mChildAdd = (ImageView)findViewById(R.id.child_add);
            mChildRemove = (ImageView)findViewById(R.id.child_remove);*/
            mCategoryImage = (ImageView)view.findViewById(R.id.image);



            roomCategoriesArrayList = new ArrayList<>();
            sdf = new SimpleDateFormat("MMM dd");
            sda = new SimpleDateFormat("MM/dd/yyyy");

            Calendar cal = Calendar.getInstance();
            from = sdf.format(cal.getTime());
            fromDate = sda.format(cal.getTime());
            cal.add(Calendar.DATE,1);
            to = sdf.format(cal.getTime());
            toDate = sda.format(cal.getTime());

            mDateText.setText(""+from+" - "+to);



            mChangeCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(PreferenceHandler.getInstance(getActivity()).getHotelID()!=0){
                        getRoomCategories(PreferenceHandler.getInstance(getActivity()).getHotelID(),"Spinner");


                    }else{
                        getRoomCategories(Constants.HOTEL_DATA_ID,"Spinner");

                    }



                }
            });

            mAddRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mRoomAddLayout.getChildCount()<availableRoomInCategory){
                        onAddField(1);
                    }else{
                        Toast.makeText(getActivity(), "No available Rooms", Toast.LENGTH_SHORT).show();
                    }
                }
            });



            mBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i = mRoomAddLayout.getChildCount();

                    roomData = new ArrayList<>();
                    roomDataBooking = new RoomDataBooking();

                    roomDataBooking.setFromDate(fromText);
                    roomDataBooking.setToDate(toText);
                    roomDataBooking.setRoomCategory(roomCategory.getCategoryName());
                    roomDataBooking.setRoomCategoryId(roomCategory.getRoomCategoryId());


                    for (int j=0;j<i;j++){

                        LinearLayout linearLayout = (LinearLayout)mRoomAddLayout.getChildAt(j);
                        LinearLayout linearLayout1 = (LinearLayout)linearLayout.getChildAt(1);
                        LinearLayout linearLayout2 = (LinearLayout)linearLayout1.getChildAt(0);
                        LinearLayout linearLayout3 = (LinearLayout)linearLayout2.getChildAt(1);
                        TextView mAdult = (TextView)linearLayout3.getChildAt(1);

                        LinearLayout linearLayout4 = (LinearLayout)linearLayout1.getChildAt(1);
                        LinearLayout linearLayout5 = (LinearLayout)linearLayout4.getChildAt(1);
                        TextView  mChild = (TextView)linearLayout5.getChildAt(1);

                        RoomData roomDataValue = new RoomData();
                        roomDataValue.setRoomName("Room "+(j+1));
                        roomDataValue.setNoOfAdults(Integer.parseInt(mAdult.getText().toString()));
                        roomDataValue.setNoOfChilds(Integer.parseInt(mChild.getText().toString()));

                        roomData.add(roomDataValue);





                    }

                    roomDataBooking.setRoomData(roomData);

                    Intent book  = new Intent(getActivity(),BookingPaymentDetailsScreen.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable("RoomDataBooking",roomDataBooking);
                    book.putExtras(bundle1);
                    startActivity(book);

                }
            });

            mDateChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    openDatePicker();
                }
            });

            onAddField(1);
            return view;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void getRoomCategories(final int id,final String type)
    {

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                // String authenticationString = Util.getToken(HotelOptionsScreen.this);//"Basic " +  Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI hotelOperation = Util.getClient().create(HotelsDetailsAPI.class);
                Call<ArrayList<RoomCategories>> response = hotelOperation.getCategoriesByHotelId(authenticationString,
                        id);

                response.enqueue(new Callback<ArrayList<RoomCategories>>() {
                    @Override
                    public void onResponse(Call<ArrayList<RoomCategories>> call, Response<ArrayList<RoomCategories>> response) {
                        System.out.println("GetHotelByProfileId = "+response.code());
                        ArrayList<RoomCategories> hotelCategories = response.body();

                        if(response.code() == 200)
                        {
                            try{
                                if(hotelCategories != null && hotelCategories.size() != 0)
                                {

                                    roomCategoriesArrayList = hotelCategories;

                                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                                    builderSingle.setIcon(R.drawable.hotel_logo);
                                    builderSingle.setTitle("Select Category :");

                                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice);

                                    for (RoomCategories rc: roomCategoriesArrayList) {

                                        arrayAdapter.add(rc.getCategoryName());

                                    }
                                    builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            String strName = arrayAdapter.getItem(which);
                                            mChangeCategoryName.setText(strName+"");
                                            mCategoryName.setText(strName+"");

                                            roomCategory = roomCategoriesArrayList.get(which);

                                            AvailabiltyCheckPostData avpd = new AvailabiltyCheckPostData();

                                            if(PreferenceHandler.getInstance(getActivity()).getHotelID()!=0){
                                                avpd.setHotelId(PreferenceHandler.getInstance(getActivity()).getHotelID());


                                            }else{
                                                avpd.setHotelId(Constants.HOTEL_DATA_ID);

                                            }

                                            avpd.setFromDate(fromDate);
                                            avpd.setToDate(toDate);
                                            avpd.setRoomCategoryId(roomCategory.getRoomCategoryId());
                                            getRatesByCategoryId(avpd);
                                            getAvailabilty(avpd,roomCategory.getCategoryName(),type);

                                        }
                                    });
                                    builderSingle.show();

                                }
                                else
                                {



                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {

                        }


                    }

                    @Override
                    public void onFailure(Call<ArrayList<RoomCategories>> call, Throwable t) {


                    }
                });
            }
        });
    }

    public void getAvailabilty(final AvailabiltyCheckPostData availabiltyCheckPostData,final String name,final String type)
    {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Loading");
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                AvailablityAPI api = Util.getClient().create(AvailablityAPI.class);
                // String auth = Util.getToken(HotelOptionsScreen.this);
                String auth = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                final Call<ArrayList<AvailabiltyCheckGetData>> HotelImagereaponse = api.checkAvailablityofHotel(auth, availabiltyCheckPostData);

                HotelImagereaponse.enqueue(new Callback<ArrayList<AvailabiltyCheckGetData>>() {
                    @Override
                    public void onResponse(Call<ArrayList<AvailabiltyCheckGetData>> call, Response<ArrayList<AvailabiltyCheckGetData>> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        if(response.code() == 200 || response.code() == 201)
                        {
                            try{
                                if(response.body() != null && response.body().size() != 0)
                                {

                                    for (AvailabiltyCheckGetData avgd:response.body()) {

                                        if(avgd.getCategoryName().equalsIgnoreCase(name)){
                                            if(avgd.getAvailability()>0){
                                                availableRoomInCategory = avgd.getAvailability();

                                                if(availableRoomInCategory<5&&availableRoomInCategory>1){
                                                    mAvailablity.setText("In high demand! only "+availableRoomInCategory+" Rooms left");
                                                }else if(availableRoomInCategory==1){
                                                    mAvailablity.setText("In high demand! only "+availableRoomInCategory+" Room left");
                                                }else if(availableRoomInCategory>=5){
                                                    mAvailablity.setText("Only "+availableRoomInCategory+" Rooms left");
                                                }
                                            }

                                            if(type!=null&&type.equalsIgnoreCase("Spinner")){

                                                if(mRoomAddLayout.getChildCount()>availableRoomInCategory){

                                                    Toast.makeText(getActivity(), "Only "+availableRoomInCategory+" rooms available.Please select another category", Toast.LENGTH_SHORT).show();
                                                }

                                            }else{
                                                onAddField(1);
                                            }



                                            break;


                                        }

                                    }

                                }
                                else
                                {


                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {


                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<AvailabiltyCheckGetData>> call, Throwable t) {
                        System.out.println(t.getMessage());
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }

                    }
                });
            }
        });
    }

    public void onAddField(final int i) {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.add_view_room, null);


        final TextView   mAdult = (TextView)rowView.findViewById(R.id.adult_count);
        final TextView mChild = (TextView)rowView.findViewById(R.id.child_count);
        final TextViewRobotoregular mRoomCount = (TextViewRobotoregular)rowView.findViewById(R.id.room_count);




        final ImageView   mAdultAdd = (ImageView)rowView.findViewById(R.id.adult_add);
        final ImageView   mAdultRemove = (ImageView)rowView.findViewById(R.id.adult_remove);
        final ImageView  mChildAdd = (ImageView)rowView.findViewById(R.id.child_add);
        final ImageView  mChildRemove = (ImageView)rowView.findViewById(R.id.child_remove);
        final ImageView  mClose = (ImageView)rowView.findViewById(R.id.close_view);


        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeView();
            }
        });

        mRoomAddLayout.addView(rowView);

        mRoomCount.setText("Room "+mRoomAddLayout.getChildCount());

        mAdultAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String adultValue = mAdult.getText().toString();

                try{

                    int adultCount = Integer.parseInt(adultValue);

                    if(adultCount>=3){
                        Toast.makeText(getActivity(), "Maximum Adult", Toast.LENGTH_SHORT).show();
                    }else{

                        adultCount = adultCount+1;
                        mAdult.setText(""+adultCount);
                        mAdultRemove.setEnabled(true);

                        if(adultCount>=3){
                            mAdultAdd.setEnabled(false);
                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        mAdultRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String adultValue = mAdult.getText().toString();

                try{

                    int adultCount = Integer.parseInt(adultValue);

                    if(adultCount<2){
                        Toast.makeText(getActivity(), "Minimum Adult", Toast.LENGTH_SHORT).show();
                    }else{

                        adultCount = adultCount-1;
                        mAdult.setText(""+adultCount);
                        mAdultAdd.setEnabled(true);

                        if(adultCount<2){
                            mAdultRemove.setEnabled(false);
                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

        mChildAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String childValue = mChild.getText().toString();

                try{

                    int childCount = Integer.parseInt(childValue);

                    if(childCount>=2){
                        Toast.makeText(getActivity(), "Maximum Child", Toast.LENGTH_SHORT).show();
                    }else{

                        childCount = childCount+1;
                        mChild.setText(""+childCount);
                        mChildRemove.setEnabled(true);

                        if(childCount>=3){
                            mChildAdd.setEnabled(false);
                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        });

        mChildRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String childValue = mChild.getText().toString();

                try{

                    int childCount = Integer.parseInt(childValue);

                    if(childCount<1){
                        Toast.makeText(getActivity(), "Minimum child", Toast.LENGTH_SHORT).show();
                    }else{

                        childCount = childCount-1;
                        mChild.setText(""+childCount);
                        mChildAdd.setEnabled(true);

                        if(childCount<1){
                            mChildRemove.setEnabled(false);
                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });






    }

    public void removeView() {

        int no = mRoomAddLayout.getChildCount();
        if(no >1)
        {
            /*System.out.println(mCompitionLinearLayout.getChildAt(no-1));*/
            mRoomAddLayout.removeView(mRoomAddLayout.getChildAt(no-1));

        }
        else
        {
            Toast.makeText(getActivity(),"Atleast One Room need",Toast.LENGTH_SHORT).show();
        }

    }

    private void getRatesByCategoryId(final AvailabiltyCheckPostData avpd){

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
                            mPrice.setText("₹ "+response.body().get(0).getSellRateForSingle()+"/per night *");
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

    public void openDatePicker() {
        // Get Current Date
        try{
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            //launch datepicker modal
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            String from, to;
                            Log.d("Date", "DATE SELECTED " + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);


                            String date1 = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

                            //String date2 = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

                            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");




                            try {
                                Date fd = simpleDateFormat.parse(date1);


                                //cits = simpleDateFormat.format(fdate);
                                from = sdf.format(fd);


                                fromText = from;

                                openDatePickerTo(fromText);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //





                        }
                    }, mYear, mMonth, mDay);

            datePickerDialog.setTitle("Check-In Date");
            datePickerDialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void openDatePickerTo(final String fromTextValue) {
        // Get Current Date
        try{
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);
            //launch datepicker modal
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            String from, to;
                            Log.d("Date", "DATE SELECTED " + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            Calendar newDate = Calendar.getInstance();
                            newDate.set(year, monthOfYear, dayOfMonth);


                            String date1 = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

                            //String date2 = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

                            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy");




                            try {
                                Date fd = simpleDateFormat.parse(date1);
                                Date tds = sdf.parse(fromTextValue);

                                if(fd.getTime()<=tds.getTime()){
                                    Toast.makeText(getActivity(), "Check out date should be greater than Check-In Date", Toast.LENGTH_SHORT).show();
                                }else{
                                    //cits = simpleDateFormat.format(fdate);
                                    from = sdf.format(fd);


                                    toText = from;

                                    mDateText.setText(fromTextValue +" - "+toText);
                                }



                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //





                        }
                    }, mYear, mMonth, mDay);


            datePickerDialog.setTitle("Check-Out Date");
            datePickerDialog.show();
            datePickerDialog.setCanceledOnTouchOutside(false);

        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
