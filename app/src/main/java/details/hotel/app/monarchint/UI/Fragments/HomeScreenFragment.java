package details.hotel.app.monarchint.UI.Fragments;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import details.hotel.app.monarchint.Adapter.HotelImageAdapter;
import details.hotel.app.monarchint.Adapter.OfferNewAdapter;
import details.hotel.app.monarchint.Adapter.OfferSliderAdapter;
import details.hotel.app.monarchint.Adapter.RestSlideAdapter;
import details.hotel.app.monarchint.Customs.CustomAdapters.AutoScrollImageAdapter;
import details.hotel.app.monarchint.Model.HotelDetails;
import details.hotel.app.monarchint.Model.HotelImage;
import details.hotel.app.monarchint.Model.Offers;
import details.hotel.app.monarchint.Model.Restaurants;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.UI.Activities.RestaurantMenuScreen;
import details.hotel.app.monarchint.UI.Activities.ThingsToDoScreen;
import details.hotel.app.monarchint.Utils.Constants;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.HotelImagesAPI;
import details.hotel.app.monarchint.WebAPI.HotelsDetailsAPI;
import details.hotel.app.monarchint.WebAPI.OfferAPI;
import details.hotel.app.monarchint.WebAPI.RestaurantAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenFragment extends Fragment {

    AutoScrollImageAdapter hotelImagesScroller;
    ViewPager offerScroller,restScroller;
    ImageView mContactUs,mGallery,mAmenities,mRooms,mLocation,mBook,mRest,mAbout;//mLoader
    ImageView mWhatsapp,mPhone;
    CardView mThings;
    NestedScrollView mScroll;

    Snackbar snackbar;
    RelativeLayout baseLayout;
    LinearLayout mOfferLay,mRestLay,mResMenLay,mAboutLay;

    int[] layouts = {R.layout.activity_offer_one,R.layout.activity_offer_two,R.layout.activity_offer_three};
    int currentPage = 0,start = 0,end = 0;
    Timer timer;
    final long DELAY_MS = 2000;
    final long PERIOD_MS = 7000;

    public HomeScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{
            View view = inflater.inflate(R.layout.fragment_home_screen,container,false);

            baseLayout = (RelativeLayout) view.findViewById(R.id.hotel_home_base);
            mScroll = (NestedScrollView) view.findViewById(R.id.main_nested_scroll);
            mScroll.setFillViewport(true);
            mScroll.setNestedScrollingEnabled(false);
            hotelImagesScroller = (AutoScrollImageAdapter) view.findViewById(R.id.hotel_images_scroller);
            hotelImagesScroller.setStopScrollWhenTouch(true);

            hotelImagesScroller.setClipToPadding(false);
            hotelImagesScroller.setPageMargin(18);

            offerScroller = (ViewPager) view.findViewById(R.id.offer_image_scroller);
            restScroller = (ViewPager) view.findViewById(R.id.restaurants_pager);
            mThings = (CardView) view.findViewById(R.id.things_do);

            mWhatsapp = (ImageView) view.findViewById(R.id.whatsapp_icon);
            mPhone = (ImageView) view.findViewById(R.id.phone_icon);

           /* Restaurants rs = new Restaurants();
            rs.setRestaurantName("Saara's Grill n Spice");
            rs.setCity("Koramangala,Bengaluru");

            ArrayList<Restaurants> rsList = new ArrayList<>();
            rsList.add(rs);*/


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

            mThings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent things = new Intent(getActivity(),ThingsToDoScreen.class);
                    getActivity().startActivity(things);

                }
            });

         //  getRestaurantByProfileId();


          /*  offerScroller.setClipToPadding(false);
            offerScroller.setPageMargin(18);*/
         /*   SliderAdapter slider = new SliderAdapter(getActivity(),layouts);
            offerScroller.setAdapter(slider);

            offerScroller.setClipToPadding(false);
            offerScroller.setPageMargin(10);
            offerScroller.setPadding(60,0,60,0);*/


            /*offerScroller.setOnTouchListener(new View.OnTouchListener() {
                float oldX = 0, newX = 0, sens = 5;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            oldX = event.getX();
                            break;

                        case MotionEvent.ACTION_UP:
                            newX = event.getX();
                            if (Math.abs(oldX - newX) < sens) {
                                itemClicked(offerScroller.getCurrentItem());
                                return true;
                            }
                            oldX = 0;
                            newX = 0;
                            break;
                    }

                    return false;
                }
            });*/





            mContactUs = (ImageView)view.findViewById(R.id.hotel_contact_image);
            mGallery = (ImageView)view.findViewById(R.id.hotel_gallery_image);
            mAmenities = (ImageView)view.findViewById(R.id.hotel_amenity_image);
            mRooms = (ImageView)view.findViewById(R.id.hotel_room_image);
            mLocation = (ImageView)view.findViewById(R.id.hotel_location_image);
            mBook = (ImageView)view.findViewById(R.id.hotel_book_image);
            mAbout = (ImageView)view.findViewById(R.id.hotel_about_us);
            mRest = (ImageView)view.findViewById(R.id.hotel_rest);
            mRestLay = (LinearLayout) view.findViewById(R.id.rest_layout);
            mAboutLay = (LinearLayout) view.findViewById(R.id.about_us);
            mResMenLay = (LinearLayout) view.findViewById(R.id.rest_lay_menu);
            mOfferLay = (LinearLayout) view.findViewById(R.id.offer_layout);
            mRestLay.setVisibility(View.GONE);
            mOfferLay.setVisibility(View.GONE);

            String about = PreferenceHandler.getInstance(getActivity()).getAboutUs();

            if(about==null||about.isEmpty()){
                mAboutLay.setVisibility(View.GONE);
            }else{
                mAboutLay.setVisibility(View.VISIBLE);
            }
            /*mLoader = (ImageView)view.findViewById(R.id.loader);

            Glide.with(getActivity()).load(R.drawable.loader_square).into(mLoader);*/


            DrawableCompat.setTint(mContactUs.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            DrawableCompat.setTint(mGallery.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            DrawableCompat.setTint(mAmenities.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            DrawableCompat.setTint(mRooms.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            DrawableCompat.setTint(mLocation.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            DrawableCompat.setTint(mBook.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            DrawableCompat.setTint(mAbout.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
            DrawableCompat.setTint(mRest.getDrawable(), ContextCompat.getColor(getActivity(), R.color.colorPrimary));


            mContactUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment  contactFragment = new ContactFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.hotel_fragment_view, contactFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            mGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment  galleryFragment = new GalleryRowFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.hotel_fragment_view, galleryFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            mAmenities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment  amenityFragment = new AmenityFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.hotel_fragment_view, amenityFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            mRooms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment  roomsFragment = new RoomsFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.hotel_fragment_view, roomsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            mLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment  locationFrag = new LocationFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.hotel_fragment_view, locationFrag);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            mBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Fragment  roomsFragment = new RoomsFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.hotel_fragment_view, roomsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            init();

            return view;


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public void getHotelImages(final int hotelID)
    {


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                HotelImagesAPI api = Util.getClient().create(HotelImagesAPI.class);
                // String auth = Util.getToken(HotelOptionsScreen.this);
                String auth = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                final Call<ArrayList<HotelImage>> HotelImagereaponse = api.getHotelImages(auth, hotelID);

                HotelImagereaponse.enqueue(new Callback<ArrayList<HotelImage>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HotelImage>> call, Response<ArrayList<HotelImage>> response) {

                        if(response.code() == 200 || response.code() == 201)
                        {
                            try{
                                if(response.body() != null && response.body().size() != 0)
                                {
                                    Collections.sort(response.body(),HotelImage.compareHotelImageByOrder);

                                    ArrayList<HotelImage> hotelImages = response.body();

                                  /*  for (int i=0;i<response.body().size();i++)
                                    {

                                        if(response.body().get(i).getImage()==null&&response.body().get(i).getImages()!=null){


                                            hotelImages.add(response.body().get(i));
                                        }


                                    }*/

                                    if(hotelImages!=null&&hotelImages.size()!=0){
                                        HotelImageAdapter activityImagesadapter = new HotelImageAdapter(getActivity(),hotelImages);
                                        hotelImagesScroller.setAdapter(activityImagesadapter);
                                      /*  mLoader.setVisibility(View.GONE);*/

                                        final Handler handler = new Handler();
                                        final Runnable Update = new Runnable() {
                                            public void run() {
                                                if (currentPage == layouts.length && start == 0) {
                                                    currentPage = --currentPage;
                                                    start = 1;
                                                    end = 0;
                                                }else if(currentPage < layouts.length && currentPage != 0 && end == 0&& start == 1){
                                                    currentPage = --currentPage;
                                                }else if(currentPage == 0 && end == 0 && start == 1){
                                                    currentPage = 0;
                                                    end = 1;
                                                    start = 0;
                                                }else if(currentPage <= layouts.length&& start == 0){

                                                    currentPage = ++currentPage;
                                                }else if(currentPage == 0&& start == 0){

                                                    currentPage = ++currentPage;
                                                }else{

                                                }
                                                hotelImagesScroller.setCurrentItem(currentPage, true);

                                            }
                                        };

                                        timer = new Timer(); // This will create a new Thread
                                        timer .schedule(new TimerTask() { // task to be scheduled

                                            @Override
                                            public void run() {
                                                handler.post(Update);
                                            }
                                        }, DELAY_MS, PERIOD_MS);

                                    }else{

                                        snackbar = Snackbar
                                                .make(baseLayout, "No Images", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                }
                                else
                                {

                                    snackbar = Snackbar
                                            .make(baseLayout, "No Images", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {
                            snackbar = Snackbar
                                    .make(baseLayout, "Something went wrong", Snackbar.LENGTH_LONG);
                            snackbar.show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<HotelImage>> call, Throwable t) {
                        System.out.println(t.getMessage());

                        snackbar = Snackbar
                                .make(baseLayout, "Network Error", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }
        });
    }

    public void init(){

        if(Util.isNetworkAvailable(getActivity())){

            if(snackbar!=null){
                snackbar.dismiss();
            }

            if(PreferenceHandler.getInstance(getActivity()).getHotelID()!=0){
                getHotelImages(PreferenceHandler.getInstance(getActivity()).getHotelID());
                getRestaurantByProfileId(PreferenceHandler.getInstance(getActivity()).getHotelID());
                getOffersByHotelId(PreferenceHandler.getInstance(getActivity()).getHotelID());

            }else{
                getHotelImages(Constants.HOTEL_DATA_ID);
                getRestaurantByProfileId(Constants.HOTEL_DATA_ID);
                getOffersByHotelId(Constants.HOTEL_DATA_ID);
            }

        }else{
            snackbar = Snackbar
                    .make(baseLayout, "No Connection", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            init();

                        }
                    });
            snackbar.show();
        }

    }

    public void getRestaurantByProfileId(final int hotelId)
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
                Call<ArrayList<Restaurants>> response = hotelOperation.getRestaurantByHotelId(authenticationString, hotelId);

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

                                    mRestLay.setVisibility(View.VISIBLE);
                                    mResMenLay.setVisibility(View.GONE);

                                    RestSlideAdapter adapter = new RestSlideAdapter(getActivity(),restDetailseResponse);
                                    restScroller.setAdapter(adapter);

                                    restScroller.setClipToPadding(false);
                                    /*restScroller.setPageMargin(10);
                                    restScroller.setPadding(60,0,60,0);*/


                                    final Handler handler = new Handler();
                                    final Runnable Update = new Runnable() {
                                        public void run() {
                                            if (currentPage == layouts.length && start == 0) {
                                                currentPage = --currentPage;
                                                start = 1;
                                                end = 0;
                                            }else if(currentPage < layouts.length && currentPage != 0 && end == 0&& start == 1){
                                                currentPage = --currentPage;
                                            }else if(currentPage == 0 && end == 0 && start == 1){
                                                currentPage = 0;
                                                end = 1;
                                                start = 0;
                                            }else if(currentPage <= layouts.length&& start == 0){

                                                currentPage = ++currentPage;
                                            }else if(currentPage == 0&& start == 0){

                                                currentPage = ++currentPage;
                                            }else{

                                            }
                                            restScroller.setCurrentItem(currentPage, true);

                                        }
                                    };

                                    timer = new Timer(); // This will create a new Thread
                                    timer .schedule(new TimerTask() { // task to be scheduled

                                        @Override
                                        public void run() {
                                            handler.post(Update);
                                        }
                                    }, DELAY_MS, PERIOD_MS);

                                }
                                else
                                {
                                    mRestLay.setVisibility(View.GONE);
                                    mResMenLay.setVisibility(View.GONE);


                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {
                           // Toast.makeText(getActivity(),"Check your internet connection or please try after some time", Toast.LENGTH_LONG).show();
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

    public void getOffersByHotelId(final int hotelId)
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

                OfferAPI hotelOperation = Util.getClient().create(OfferAPI.class);
                Call<ArrayList<Offers>> response = hotelOperation.getOffersByHotelId(authenticationString, hotelId);

                response.enqueue(new Callback<ArrayList<Offers>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Offers>> call, Response<ArrayList<Offers>> response) {
                        System.out.println("GetHotelByProfileId = "+response.code());
                        final ArrayList<Offers> restDetailseResponse = response.body();

                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                        if(response.code() == 200)
                        {
                            try{
                                if(restDetailseResponse != null && restDetailseResponse.size() != 0)
                                {


                                    offerScroller.setVisibility(View.VISIBLE);
                                    mOfferLay.setVisibility(View.VISIBLE);


                                    OfferNewAdapter adapter = new OfferNewAdapter(getActivity(),restDetailseResponse);
                                    offerScroller.setAdapter(adapter);

                                    offerScroller.setClipToPadding(false);
                                    offerScroller.setPageMargin(10);
                                    offerScroller.setPadding(60,0,60,0);
                                    /*restScroller.setPageMargin(10);
                                    restScroller.setPadding(60,0,60,0);*/


                                    final Handler handler = new Handler();
                                    final Runnable Update = new Runnable() {
                                        public void run() {
                                            if (currentPage == restDetailseResponse.size() && start == 0) {
                                                currentPage = --currentPage;
                                                start = 1;
                                                end = 0;
                                            }else if(currentPage < restDetailseResponse.size()&& currentPage != 0 && end == 0&& start == 1){
                                                currentPage = --currentPage;
                                            }else if(currentPage == 0 && end == 0 && start == 1){
                                                currentPage = 0;
                                                end = 1;
                                                start = 0;
                                            }else if(currentPage <= restDetailseResponse.size()&& start == 0){

                                                currentPage = ++currentPage;
                                            }else if(currentPage == 0&& start == 0){

                                                currentPage = ++currentPage;
                                            }else{

                                            }
                                            offerScroller.setCurrentItem(currentPage, true);

                                        }
                                    };

                                    timer = new Timer(); // This will create a new Thread
                                    timer .schedule(new TimerTask() { // task to be scheduled

                                        @Override
                                        public void run() {
                                            handler.post(Update);
                                        }
                                    }, DELAY_MS, PERIOD_MS);

                                }
                                else
                                {
                                    offerScroller.setVisibility(View.GONE);
                                    mOfferLay.setVisibility(View.GONE);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {
                            offerScroller.setVisibility(View.GONE);
                            mOfferLay.setVisibility(View.GONE);
                        }


                    }

                    @Override
                    public void onFailure(Call<ArrayList<Offers>> call, Throwable t) {
                        System.out.println("Failed");
                        System.out.println(" Exception = "+t.getMessage());
                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                        offerScroller.setVisibility(View.GONE);
                        mOfferLay.setVisibility(View.GONE);
                    }
                });
            }
        });


    }

    public void itemClicked(final int id){

        if(id==0){

            Fragment  roomsFragment = new RoomsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.hotel_fragment_view, roomsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }else if(id==1){

            Fragment  roomsFragment = new RoomsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.hotel_fragment_view, roomsFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }else if(id==2){
            Intent mnu = new Intent(getActivity(),RestaurantMenuScreen.class);
            getActivity().startActivity(mnu);
        }

    }

    private void getHotel(final int id){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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

                                PreferenceHandler.getInstance(getActivity()).setAboutUs(hotelDetails.getAbouUs());

                            }




                        }else {
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
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


   /* @Override
    public void onDetach() {

        super.onDetach();

        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.exit_alert_layout,null);
        TextView dontCancelBtn = (TextView)view.findViewById(R.id.no_btn);
        TextView cancelBtn = (TextView)view.findViewById(R.id.exit_app_btn);
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

                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);


                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        //super.onDetach();
    }*/
}
