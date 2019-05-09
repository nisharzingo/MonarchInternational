package details.hotel.app.monarchint.UI.Fragments;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import details.hotel.app.monarchint.Adapter.AmenityAdapter;
import details.hotel.app.monarchint.Adapter.AmenityRowAdapter;
import details.hotel.app.monarchint.Model.Amenities;
import details.hotel.app.monarchint.Model.PaidAmenities;
import details.hotel.app.monarchint.Model.PaidAmenitiesCategory;
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
public class AmenityFragment extends Fragment {

    private static ListView mAmenityList;
    LinearLayout mNoAmenity,mVisible;

    ImageView mWhatsapp,mPhone;
    GridView mAmenity;

    ArrayList<PaidAmenitiesCategory> paidAmenitiesCategories;
    ArrayList<PaidAmenities> paidAmenities;

    ArrayList<PaidAmenitiesCategory> listCategoryName;
    HashMap<PaidAmenitiesCategory,List<PaidAmenities>> listAmenitiesByCategory;

    public AmenityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{

            View view = inflater.inflate(R.layout.fragment_amenities_list,container,false);
            mAmenityList = (ListView)view.findViewById(R.id.hotel_amenity_list);
            mNoAmenity = (LinearLayout) view.findViewById(R.id.no_preview_lay);
            mVisible = (LinearLayout) view.findViewById(R.id.visible_ameniy);
            mAmenity = (GridView) view.findViewById(R.id.amenity_grid_list);
            mWhatsapp = (ImageView) view.findViewById(R.id.whatsapp_icon);
            mPhone = (ImageView) view.findViewById(R.id.phone_icon);


            final String whatsappNumber = PreferenceHandler.getInstance(getActivity()).getWhatsappNumber();

            mPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if(whatsappNumber!=null&&!whatsappNumber.isEmpty()){
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+whatsappNumber));
                        startActivity(intent);
                    }else{

                        Toast.makeText(getActivity(), "No Number to contcat", Toast.LENGTH_SHORT).show();
                    }



                }
            });

            mWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(whatsappNumber!=null&&!whatsappNumber.isEmpty()){
                        String digits = "\\d+";
                        String mob_num = ""+whatsappNumber;
                        if (mob_num.matches(digits))
                        {
                            try {

                                Uri uri = Uri.parse("whatsapp://send?phone=+91" + mob_num);
                                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(i);
                            }
                            catch (ActivityNotFoundException e){
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "WhatsApp not installed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{

                        Toast.makeText(getActivity(), "No Number to contcat", Toast.LENGTH_SHORT).show();
                    }



                }
            });



            if(PreferenceHandler.getInstance(getActivity()).getHotelID()!=0){
                getCategories(PreferenceHandler.getInstance(getActivity()).getHotelID());
                getAvailable(PreferenceHandler.getInstance(getActivity()).getHotelID());


            }else{
                getCategories(Constants.HOTEL_DATA_ID);
                getAvailable(Constants.HOTEL_DATA_ID);

            }

            return  view;

        }catch (Exception e){
            e.printStackTrace();
            return  null;
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
                                mVisible.setVisibility(View.VISIBLE);
                                mNoAmenity.setVisibility(View.GONE);

                                AmenityAdapter adapter = new AmenityAdapter(getActivity(),paidAmenities);//,pagerModelArrayList);
                                mAmenityList.setAdapter(adapter);

                            }else{
                                mAmenityList.setVisibility(View.GONE);
                                mNoAmenity.setVisibility(View.VISIBLE);
                                mVisible.setVisibility(View.GONE);
                            }




                        }else{
                            mAmenityList.setVisibility(View.GONE);
                            mNoAmenity.setVisibility(View.VISIBLE);
                            mVisible.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<PaidAmenities>> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        mAmenityList.setVisibility(View.GONE);
                        mNoAmenity.setVisibility(View.VISIBLE);
                        mVisible.setVisibility(View.GONE);
                        Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


                //System.out.println(TAG+" thread started");

            }

        });
    }


    public void getAvailable(final int id)
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
                Call<ArrayList<Amenities>> getBlog = categoryApi.getAmenitiesByHotelId(authenticationString,id);
                //Call<ArrayList<Blogs>> getBlog = blogApi.getBlogs();

                getBlog.enqueue(new Callback<ArrayList<Amenities>>() {

                    @Override
                    public void onResponse(Call<ArrayList<Amenities>> call, Response<ArrayList<Amenities>> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }

                        if (response.code() == 200&&response.body()!=null&&response.body().size()!=0)
                        {
                            //categoryArrayList = new ArrayList<>();

                            AmenityRowAdapter adapter = new AmenityRowAdapter(getActivity(),response.body());//,pagerModelArrayList);
                            mAmenity.setAdapter(adapter);
                            mVisible.setVisibility(View.VISIBLE);
                            mNoAmenity.setVisibility(View.GONE);

                        }else{


                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Amenities>> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }

                    }
                });


                //System.out.println(TAG+" thread started");

            }

        });
    }



    public static void slide_down(Context ctx, View v) {

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_down);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }


    public static void slide_up(Context ctx, View v) {

        Animation a = AnimationUtils.loadAnimation(ctx, R.anim.slide_up);
        if (a != null) {
            a.reset();
            if (v != null) {
                v.clearAnimation();
                v.startAnimation(a);
            }
        }
    }


    public void toggle_contents(final LinearLayout layout){

        if(layout.isShown()){
            slide_up(getActivity(), layout);
            layout.setVisibility(View.GONE);
        }
        else{
            layout.setVisibility(View.VISIBLE);
            slide_down(getActivity(), layout);
        }
    }


}
