package details.hotel.app.monarchint.UI.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import details.hotel.app.monarchint.Adapter.BookingRecyclerViewAdapter;
import details.hotel.app.monarchint.Model.RoomBookings;
import details.hotel.app.monarchint.Model.Traveller;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.RoomBookingAPI;
import details.hotel.app.monarchint.WebAPI.TravellerAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass. fragment_completed_booking
 */
public class CompletedBookingFragment extends Fragment {

    RecyclerView recyclerView;
    View empty;


    ArrayList<Traveller> travellerArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_upcoming_booking, container, false);

        try {

            View view = inflater.inflate(R.layout.fragment_completed_booking, container, false);
            recyclerView = (RecyclerView) view.findViewById(R.id.upcoming_bookings_list);
            empty = (View)view.findViewById(R.id.empty);
            getBookings();
            return view;

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "UI not Supporting", Toast.LENGTH_SHORT).show();
            return null;
        }
    }


    private void getBookings() {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle(R.string.loader_message);
        dialog.setCancelable(false);
        dialog.show();


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                RoomBookingAPI bookingApi = Util.getClient().create(RoomBookingAPI.class);
                String authenticationString = Util.getToken(getActivity());
                final Call<ArrayList<RoomBookings>> getAllBookings = bookingApi.getBookingsByProfileId(authenticationString, PreferenceHandler.getInstance(getActivity()).getTravellerId());

                getAllBookings.enqueue(new Callback<ArrayList<RoomBookings>>() {
                    @Override
                    public void onResponse(Call<ArrayList<RoomBookings>> call, Response<ArrayList<RoomBookings>> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        if(response.code() == 200)
                        {
                            if(response.body() != null)
                            {
                                ArrayList<RoomBookings> bookings1ArrayList = response.body();

                                ArrayList<RoomBookings> arrayList = new ArrayList<>();
                                for (int i=0;i<bookings1ArrayList.size();i++)
                                {
                                    if(bookings1ArrayList.get(i).getBookingStatus() != null &&
                                            bookings1ArrayList.get(i).getBookingStatus().equals("Completed"))
                                    {
                                        RoomBookings bookings1 = bookings1ArrayList.get(i);
                                        arrayList.add(bookings1);
                                    }
                                }

                                if(arrayList.size() != 0)
                                {
                                    Collections.sort(bookings1ArrayList, new Comparator<RoomBookings>() {
                                        @Override
                                        public int compare(RoomBookings o1, RoomBookings o2) {
                                            return o2.getCheckInDate().compareTo(o1.getCheckInDate());
                                        }
                                    });
                                    getTravellers(arrayList);
                                }
                                else
                                {
                                    empty.setVisibility(View.VISIBLE);
                                }
                            }else{
                                empty.setVisibility(View.VISIBLE);
                            }

                        }else{
                            empty.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<RoomBookings>> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        empty.setVisibility(View.VISIBLE);
                    }
                });
            }
        });


    }


    private void getTravellers(final ArrayList<RoomBookings> body) {
        travellerArrayList = new ArrayList<>();
        if(body.size() != 0)
        {
            final ProgressDialog dialog = new ProgressDialog(getActivity());
            dialog.setTitle(R.string.loader_message);
            dialog.setCancelable(false);
            dialog.show();
            for (int i = 0;i<body.size();i++)
            {
                String auth_string = Util.getToken(getActivity());//"Basic " +  Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
                TravellerAPI travellerApi = Util.getClient().create(TravellerAPI.class);
                Call<Traveller> getTraveller = travellerApi.getTravellerDetails(auth_string,body.get(i).getTravellerId());

                getTraveller.enqueue(new Callback<Traveller>() {
                    @Override
                    public void onResponse(Call<Traveller> call, Response<Traveller> response) {
                        if(response.code() == 200)
                        {
                            if(response.body() != null)
                            {
                                travellerArrayList.add(response.body());
                                if(body.size() == travellerArrayList.size())
                                {
                                    empty.setVisibility(View.GONE);
                                    BookingRecyclerViewAdapter bookingRecyclerViewAdapter = new BookingRecyclerViewAdapter(getActivity(),body,travellerArrayList);
                                    recyclerView.setAdapter(bookingRecyclerViewAdapter);
                                }
                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Please try after some time",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Traveller> call, Throwable t) {

                    }
                });
            }

            if(dialog != null)
            {
                dialog.dismiss();
            }
        }
        else
        {

        }


    }
}
