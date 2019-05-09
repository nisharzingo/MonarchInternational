package details.hotel.app.monarchint.UI.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import details.hotel.app.monarchint.Adapter.RestaurantListAdapter;
import details.hotel.app.monarchint.Model.Restaurants;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.RestaurantAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass. fragment_resto
 */
public class RestoFragment extends Fragment {

    private static ListView mRestList;



    public RestoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{

            View view = inflater.inflate(R.layout.fragment_resto,container,false);
            mRestList = (ListView)view.findViewById(R.id.rest_list);

            getRestaurantByProfileId();

            if(PreferenceHandler.getInstance(getActivity()).getHotelID()!=0){
               // getCategories(PreferenceHandler.getInstance(getActivity()).getHotelID());


            }else{
              //  getCategories(Constants.HOTEL_DATA_ID);

            }

            return  view;

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    public void getRestaurantByProfileId()
    {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        //restId = 0;

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {

                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";

                RestaurantAPI hotelOperation = Util.getClient().create(RestaurantAPI.class);
                Call<ArrayList<Restaurants>> response = hotelOperation.getRestaurantByProfileId(authenticationString,
                        6);

                response.enqueue(new Callback<ArrayList<Restaurants>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Restaurants>> call, Response<ArrayList<Restaurants>> response) {
                        System.out.println("GetHotelByProfileId = "+response.code());
                        ArrayList<Restaurants> restDetailseResponse = response.body();

                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                        if(response.code() == 200)
                        {
                            try{
                                if(restDetailseResponse != null && restDetailseResponse.size() != 0)
                                {

                                  /*  Restaurants restaurants = restDetailseResponse.get(0);
                                    if(restaurants!=null){
                                       *//* PreferanceHandler.getInstance(LandingScreenForOptions.this).setRestaurantId(restaurants.getRestaurantId());
                                        PreferanceHandler.getInstance(LandingScreenForOptions.this).setRestaurantApproved(restaurants.getApproved());
                                        PreferanceHandler.getInstance(LandingScreenForOptions.this).setRestaurantName(restaurants.getRestaurantName());
                                        PreferanceHandler.getInstance(LandingScreenForOptions.this).setRestaurantLocality(restaurants.getLocalty());
*//*
                                        mRestName.setText(restaurants.getRestaurantName());
                                        restId= restaurants.getRestaurantId();
                                        menuSetup(restId);
                                    }else{
                                        Toast.makeText(LandingScreenForOptions.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }*/

                                    RestaurantListAdapter adapter = new RestaurantListAdapter(getActivity(),restDetailseResponse);
                                    mRestList.setAdapter(adapter);



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
                            Toast.makeText(getActivity(),"Check your internet connection or please try after some time",
                                    Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<ArrayList<Restaurants>> call, Throwable t) {
                        System.out.println("Failed");
                        System.out.println(" Exception = "+t.getMessage());
                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                        Toast.makeText(getActivity(),"Check your internet connection or please try after some time", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });


    }



}
