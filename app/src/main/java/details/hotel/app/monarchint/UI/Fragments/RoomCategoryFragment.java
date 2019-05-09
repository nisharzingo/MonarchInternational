package details.hotel.app.monarchint.UI.Fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import details.hotel.app.monarchint.Adapter.RoomCategoriesListAdapter;
import details.hotel.app.monarchint.Adapter.RoomCategoryAdapter;
import details.hotel.app.monarchint.Model.RoomCategories;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.Constants;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.HotelsDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomCategoryFragment extends Fragment {

    RecyclerView mCategoryList;


    public RoomCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{
            View view = inflater.inflate(R.layout.fragment_room_category,container,false);

            mCategoryList = (RecyclerView)view.findViewById(R.id.room_category_recycler);


            if(PreferenceHandler.getInstance(getActivity()).getHotelID()!=0){

                getRoomCategories(PreferenceHandler.getInstance(getActivity()).getHotelID());

            }else{
                getRoomCategories(Constants.HOTEL_DATA_ID);
            }


            return view;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void getRoomCategories(final int id)
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


                                    RoomCategoryAdapter adapter = new RoomCategoryAdapter(getActivity(),hotelCategories);
                                    mCategoryList.setAdapter(adapter);



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

}
