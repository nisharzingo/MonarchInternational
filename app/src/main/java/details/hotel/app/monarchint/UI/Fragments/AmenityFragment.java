package details.hotel.app.monarchint.UI.Fragments;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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
import details.hotel.app.monarchint.Adapter.Pager;
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

    private ViewPager viewPager_frag;
    private TabLayout tabLayout;


    public AmenityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{

            View view = inflater.inflate(R.layout.fragment_amenities_list,container,false);



            final String whatsappNumber = PreferenceHandler.getInstance(getActivity()).getWhatsappNumber();


            viewPager_frag = (ViewPager) view.findViewById(R.id.about_viewpager2);


            tabLayout = (TabLayout) view.findViewById(R.id.about_tabs);
            tabLayout.setupWithViewPager(viewPager_frag);

            setupViewPager(viewPager_frag);


            return  view;

        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    private void setupViewPager(ViewPager viewPager) {

        Pager adapter = new Pager(getActivity().getSupportFragmentManager());
        adapter.addFragment(new AmenityAllFragment(), "Facilities");
        adapter.addFragment(new AmenityListFragment(), "Free");
        adapter.addFragment(new AmenityListFragment (), "Paid");
        viewPager.setAdapter(adapter);




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
