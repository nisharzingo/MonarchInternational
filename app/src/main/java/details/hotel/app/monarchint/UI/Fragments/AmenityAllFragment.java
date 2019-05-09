package details.hotel.app.monarchint.UI.Fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import details.hotel.app.monarchint.Adapter.AmenityAdapter;
import details.hotel.app.monarchint.Adapter.AmenityRowAdapter;
import details.hotel.app.monarchint.Adapter.CustomGridViewAdapter;
import details.hotel.app.monarchint.Model.Amenities;
import details.hotel.app.monarchint.Model.PaidAmenities;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.Constants;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.PaidAmenitiesAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmenityAllFragment extends Fragment {

    GridView androidGridView;
    ArrayList<PaidAmenities> paidAmenities;
    ListView mAmenityList;

    String[] gridViewString = {
            "Wifi", "Airport Shuttle",  "No Smoking", "Family", "Restaurant"

    } ;
    int[] gridViewImageId = {
            R.drawable.wifi, R.drawable.airport_shuttle, R.drawable.nosmoking, R.drawable.family, R.drawable.restaurants

    };


    public AmenityAllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        try{

            View view = inflater.inflate(R.layout.fragment_amenity_all,container,false);


            return view;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CustomGridViewAdapter adapterViewAndroid = new CustomGridViewAdapter(getContext(), gridViewString, gridViewImageId);
        androidGridView=(GridView)view.findViewById(R.id.grid_view_image_text);
        mAmenityList = (ListView)view.findViewById(R.id.hotel_amenity_list);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(getContext(), "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();
            }
        });


        if(PreferenceHandler.getInstance(getActivity()).getHotelID()!=0){
            getCategories(PreferenceHandler.getInstance(getActivity()).getHotelID());



        }else{
            getCategories(Constants.HOTEL_DATA_ID);


        }


    }

    public void getCategories(final int id)
    {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading Collections");
        dialog.setCancelable(false);
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {

                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";

                final PaidAmenitiesAPI categoryApi = Util.getClient().create(PaidAmenitiesAPI.class);
                Call<ArrayList<PaidAmenities>> getBlog = categoryApi.getPaidAmenitiesByHotelId(authenticationString,id);
                //Call<ArrayList<Blogs>> getBlog = blogApi.getBlogs();

                getBlog.enqueue(new Callback<ArrayList<PaidAmenities>>() {

                    @Override
                    public void onResponse(Call<ArrayList<PaidAmenities>> call, Response<ArrayList<PaidAmenities>> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }

                        if (response.code() == 200)
                        {
                            //categoryArrayList = new ArrayList<>();
                            paidAmenities = response.body();



                            if(paidAmenities!=null&&paidAmenities.size()!=0){
                                mAmenityList.setVisibility(View.VISIBLE);
                              /*  mVisible.setVisibility(View.VISIBLE);
                                mNoAmenity.setVisibility(View.GONE);*/

                                AmenityAdapter adapter = new AmenityAdapter(getActivity(),paidAmenities);//,pagerModelArrayList);
                                mAmenityList.setAdapter(adapter);

                            }else{
                                mAmenityList.setVisibility(View.GONE);

                            }



                        }else{
                          /*  mAmenityList.setVisibility(View.GONE);
                            mNoAmenity.setVisibility(View.VISIBLE);
                            mVisible.setVisibility(View.GONE);*/
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PaidAmenities>> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                     /*   mAmenityList.setVisibility(View.GONE);
                        mNoAmenity.setVisibility(View.VISIBLE);
                        mVisible.setVisibility(View.GONE);*/
                        Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


                //System.out.println(TAG+" thread started");

            }

        });
    }



}
