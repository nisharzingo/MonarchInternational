package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import details.hotel.app.monarchint.Model.HotelDetails;
import details.hotel.app.monarchint.Model.RoomBookings;
import details.hotel.app.monarchint.Model.Traveller;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.HotelsDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BookingRecyclerViewAdapter extends RecyclerView.Adapter<BookingRecyclerViewAdapter.ViewHolder> {


    private Context context;
    private ArrayList<RoomBookings> bookingArrayList;
    ArrayList<Traveller> travellerArrayList;

    public BookingRecyclerViewAdapter(Context context, ArrayList<RoomBookings> bookingArrayList, ArrayList<Traveller> travellerArrayList)
    {
        this.context = context;
        this.bookingArrayList = bookingArrayList;
        this.travellerArrayList = travellerArrayList;
        setHasStableIds(true);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_booking_page_recyclerview_layout,
                parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        final RoomBookings bookings1 = bookingArrayList.get(position);
        final Traveller traveller = travellerArrayList.get(position);

        if(bookings1 != null && traveller != null)
        {
            //holder.mBookedPersonName.setText(traveller.getFirstName()+" "+traveller.getLastName());
            holder.mBookedPersonName.setText(traveller.getFirstName());
            getHotel(bookings1.getHotelId(),holder.mBookingSourceType);

            holder.mBookedDate.setText("Booked On: "+getBookedOnDateFormate(bookings1.getBookingDate()));
            if(bookings1.getCheckInDate()!=null && !bookings1.getCheckInDate().isEmpty()&&bookings1.getCheckOutDate()!=null && !bookings1.getCheckOutDate().isEmpty()){
                holder.mBookingDates.setText(getBookingDateFormate(bookings1.getCheckInDate())
                        +" To "+getBookingDateFormate(bookings1.getCheckOutDate()));
            }
            holder.mNetAmount.setText("Net Amount: ₹ "+bookings1.getTotalAmount());

            if(traveller.getFirstName() != null && !traveller.getFirstName().isEmpty())
            {


                String[] ab = traveller.getFirstName().split(" ");
                if(ab.length > 1)
                {
                    //if(ab[1].charAt(0) != "")
                    holder.mShortName.setText(ab[0].charAt(0)+"");//+""+ab[1].charAt(0));
                }
                else
                {
                    holder.mShortName.setText(ab[0].charAt(0)+"");
                }
            }


            holder.mNoofRooms.setText((long)getDays(bookings1.getCheckInDate(),bookings1.getCheckOutDate())+" Night(s)");
            holder.mCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String callingNumber = travellerArrayList.get(position).getPhoneNumber();

                    if(callingNumber != null && !callingNumber.isEmpty())
                    {
                        if (!callingNumber.equals("")) {
                            Uri number = Uri.parse("tel:" + callingNumber);
                            Intent dial = new Intent(Intent.ACTION_DIAL, number);
                            context.startActivity(dial);
                        }
                    }
                }
            });

            holder.mBookedRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bookings1.getRoomId() == 0 &&
                            bookings1.getBookingStatus() != null &&
                            bookings1.getBookingStatus().equalsIgnoreCase("quick"))
                    {

                    }
                }
            });


            holder.mparent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  Intent intent = new Intent(context,BookingHistoryDetailsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Booking",bookings1);
                    intent.putExtras(bundle);
                    context.startActivity(intent);*/
                }
            });

        }
    }

    private void getHotel(final int id,final TextView mHotelName){



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


                            final HotelDetails hotelDetails =  response.body();

                            if(hotelDetails != null)
                            {



                                mHotelName.setText(hotelDetails.getHotelName());




                            }




                        }else {

                        }
                    }

                    @Override
                    public void onFailure(Call<HotelDetails> call, Throwable t) {

                        Log.e("TAG", t.toString());
                    }
                });
            }


        });
    }

    private long getDays(String checkInDate, String checkOutDate) {

        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat myFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        /*String inputString1 = "01/10/2018";
        String inputString2 = "01/19/2018";*/
        Date date1,date2;
        try {
            if(checkInDate.contains("-"))
            {
                date1 = myFormat1.parse(checkInDate);
                date2 = myFormat1.parse(checkOutDate);
            }
            else
            {
                date1 = myFormat.parse(checkInDate);
                date2 = myFormat.parse(checkOutDate);
            }
            long diff = date2.getTime() - date1.getTime();
            System.out.println ("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return bookingArrayList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mBookedPersonName,mBookedDate,mBookingDates,mNoofRooms,mNetAmount,mShortName,mCall,mPayAtHotel,mBookedRoom,mBookingSourceType;
        LinearLayout mparent;

        public ViewHolder(View itemView) {
            super(itemView);

            mBookedPersonName = (TextView) itemView.findViewById(R.id.booked_person_name);
            mBookedDate = (TextView) itemView.findViewById(R.id.booked_date);
            mBookingDates = (TextView) itemView.findViewById(R.id.booked_from_to_date);
            mNoofRooms = (TextView) itemView.findViewById(R.id.booked_no_rooms_night);
            mNetAmount = (TextView) itemView.findViewById(R.id.net_amount);
            mShortName = (TextView) itemView.findViewById(R.id.person_short_name);
            mCall = (TextView) itemView.findViewById(R.id.call_booked_person);
            mPayAtHotel = (TextView) itemView.findViewById(R.id.pay_at_hotel);
            mBookedRoom = (TextView) itemView.findViewById(R.id.call_booked_room_no);
            mparent = (LinearLayout) itemView.findViewById(R.id.parent_layout_for_user_details);
            mBookingSourceType = (TextView) itemView.findViewById(R.id.booking_source_type);

        }
    }

    public String getBookedOnDateFormate(String sdate)
    {
        try {
            Date date = new SimpleDateFormat("MM/dd/yyyy").parse(sdate);
            String sDate = new SimpleDateFormat("dd MMM yyyy").format(date);
            System.out.println("sDate = "+sDate);
            return sDate;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }


    }

    public String getBookingDateFormate(String bdate)
    {
        String sDate = null;
        try {
            if(bdate.contains("-"))
            {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(bdate);
                sDate = new SimpleDateFormat("dd MMM").format(date);
            }
            else
            {
                Date date = new SimpleDateFormat("MM/dd/yyyy").parse(bdate);
                sDate = new SimpleDateFormat("dd MMM").format(date);
            }
            System.out.println("sDate = "+sDate);
            return sDate;

        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


}