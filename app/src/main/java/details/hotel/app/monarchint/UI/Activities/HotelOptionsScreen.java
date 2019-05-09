package details.hotel.app.monarchint.UI.Activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import details.hotel.app.monarchint.Adapter.HotelListAdapter;
import details.hotel.app.monarchint.Adapter.NavigationListAdapter;
import details.hotel.app.monarchint.Customs.CustomFonts.TextViewRobotoregular;
import details.hotel.app.monarchint.Model.HotelContacts;
import details.hotel.app.monarchint.Model.HotelDetails;
import details.hotel.app.monarchint.Model.NavBarItems;
import details.hotel.app.monarchint.Model.ProfileHotelTag;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.UI.Fragments.AmenityFragment;
import details.hotel.app.monarchint.UI.Fragments.ContactFragment;
import details.hotel.app.monarchint.UI.Fragments.GalleryRowFragment;
import details.hotel.app.monarchint.UI.Fragments.HomeScreenFragment;
import details.hotel.app.monarchint.UI.Fragments.LocationFragment;
import details.hotel.app.monarchint.UI.Fragments.RoomsFragment;
import details.hotel.app.monarchint.Utils.Constants;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.HotelsDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.view.GravityCompat.START;

public class HotelOptionsScreen extends AppCompatActivity {

    DrawerLayout baseLayout;
    ListView navBarListView;
    //ImageView mWhatsapp,mPhone;
    Toolbar mToolbar;
    TextViewRobotoregular mSelectHotel,mHotelName;
    LinearLayout mHotelListLay;
    RecyclerView mHotelList;
    RatingBar mHotelRating;

    Snackbar snackbar;
    String tag = "";

    String[] title = null;
    ArrayList<HotelDetails> hotelDetailsArrayList;

    String whatsappNumber ="",callNumber ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            setContentView(R.layout.activity_hotel_options_screen);


            baseLayout = (DrawerLayout) findViewById(R.id.base_layout);
            mToolbar = (Toolbar) findViewById(R.id.toolbar);

            navBarListView = (ListView) findViewById(R.id.navbar_list);
            mSelectHotel = (TextViewRobotoregular) findViewById(R.id.select_hotel);
            mHotelName = (TextViewRobotoregular) findViewById(R.id.hotel_name_list);
            mHotelListLay = (LinearLayout) findViewById(R.id.hotel_list_layout);
            mHotelList = (RecyclerView) findViewById(R.id.hotel_name);
            mHotelRating = (RatingBar) findViewById(R.id.hotel_ratingbar);
          /*  mWhatsapp = (ImageView) findViewById(R.id.whatsapp_icon);
            mPhone = (ImageView) findViewById(R.id.phone_icon);*/
            mHotelList.setVisibility(View.GONE);

           // new JSONParse().execute();

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, baseLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            baseLayout.setDrawerListener(toggle);
            toggle.syncState();

            title = getResources().getStringArray(R.array.hotel_menu);

            init();

            setUpNavbar();
            displayMenu("Home");

            navBarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    try{
                        displayMenu(title[position]);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            });

            if(PreferenceHandler.getInstance(HotelOptionsScreen.this).getHotelName()!=null&&!PreferenceHandler.getInstance(HotelOptionsScreen.this).getHotelName().isEmpty()){

                mHotelName.setText(""+PreferenceHandler.getInstance(HotelOptionsScreen.this).getHotelName());
            }

            mSelectHotel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggle_contents();
                }
            });





        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void setUpNavbar() throws Exception{

        if(title!=null&&title.length!=0){

            ArrayList<NavBarItems> navBarItemsList = new ArrayList<>();

            for (int i=0;i<title.length;i++)
            {
                NavBarItems navbarItem = new NavBarItems(title[i]);
                navBarItemsList.add(navbarItem);
            }

            NavigationListAdapter adapter = new NavigationListAdapter(getApplicationContext(),navBarItemsList);
            navBarListView.setAdapter(adapter);



        }
    }

    public void displayMenu(String option) throws Exception{

        if(baseLayout != null)
            baseLayout.closeDrawer(START);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (option)
        {

            case "Home":
                Fragment homeFragment = new HomeScreenFragment();
                tag = "Home";
                transaction.replace(R.id.hotel_fragment_view,homeFragment,"MY_FRAGMENT")
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .commit();
                break;

            case "Contact Us":
                Fragment contactFragment = new ContactFragment();
                tag = "Contact Us";
                transaction.replace(R.id.hotel_fragment_view,contactFragment,"MY_FRAGMENT")
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;

            case "Gallery":
                Fragment galleryFragment = new GalleryRowFragment();
                tag = "Gallery";
                transaction.replace(R.id.hotel_fragment_view,galleryFragment,"MY_FRAGMENT")
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;

            case "Amenities":
                Fragment amenityFragment = new AmenityFragment();
                tag = "Amenities";
                transaction.replace(R.id.hotel_fragment_view,amenityFragment,"MY_FRAGMENT")
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;

            case "Rooms":
                Fragment RoomsFragment = new RoomsFragment();
                tag = "Rooms";
                transaction.replace(R.id.hotel_fragment_view,RoomsFragment,"MY_FRAGMENT")
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;

            case "Location":
                Fragment locationFragment = new LocationFragment();
                tag = "Location";
                transaction.replace(R.id.hotel_fragment_view,locationFragment,"MY_FRAGMENT")
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;

            case "Book Now":

                Fragment bookFrag = new RoomsFragment();
                tag = "Book Now";
                transaction.replace(R.id.hotel_fragment_view,bookFrag,"MY_FRAGMENT")
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();
                break;

           /* case "Points":
               Intent points = new Intent(HotelOptionsScreen.this,PointsScreen.class);
               startActivity(points);
                break;*/

            case "Profile":

                if(PreferenceHandler.getInstance(HotelOptionsScreen.this).getTravellerId()!=0){
                    Intent profiles = new Intent(HotelOptionsScreen.this,ProfileOptionScreen.class);
                    startActivity(profiles);
                }else{
                    Intent profiles = new Intent(HotelOptionsScreen.this,LoginScreen.class);
                    startActivity(profiles);
                }

                break;

        }

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


    public void toggle_contents(){

        if(mHotelList.isShown()){
            slide_up(this, mHotelList);
            mHotelList.setVisibility(View.GONE);
        }
        else{
            mHotelList.setVisibility(View.VISIBLE);
            slide_down(this, mHotelList);
        }
    }


    public void getTaggedHotels()
    {

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


               // String authenticationString = Util.getToken(HotelOptionsScreen.this);//"Basic " +  Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI hotelOperation = Util.getClient().create(HotelsDetailsAPI.class);
                Call<ArrayList<ProfileHotelTag>> response = hotelOperation.getTaggedHotels(authenticationString, Constants.PROFILE_DATA_ID);

                response.enqueue(new Callback<ArrayList<ProfileHotelTag>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ProfileHotelTag>> call, Response<ArrayList<ProfileHotelTag>> response) {
                        System.out.println("GetHotelByProfileId = "+response.code());
                        ArrayList<ProfileHotelTag> hotelDetailseResponse = response.body();

                        if(response.code() == 200 || response.code() == 201 || response.code() == 204)
                        {
                            try{
                                if(hotelDetailseResponse != null && hotelDetailseResponse.size() != 0)
                                {


                                    ArrayList<ProfileHotelTag> taggedProfiles = response.body();
                                    hotelDetailsArrayList = new ArrayList<>();
                                    if(hotelDetailsArrayList != null && hotelDetailsArrayList.size() != 0)
                                    {
                                        hotelDetailsArrayList.clear();
                                    }

                                    for (int i=0;i<taggedProfiles.size();i++)
                                    {
                                        if(taggedProfiles.get(i).getHotels() != null)
                                        {
                                            hotelDetailsArrayList.add(taggedProfiles.get(i).getHotels());

                                        }
                                    }
                                    PreferenceHandler.getInstance(HotelOptionsScreen.this).setHotelId(taggedProfiles.get(0).getHotelId());
                                    PreferenceHandler.getInstance(HotelOptionsScreen.this).setRecType(taggedProfiles.get(0).getHotels().getReconcilationType());
                                    getHotelContact(taggedProfiles.get(0).getHotelId());
                                    PreferenceHandler.getInstance(HotelOptionsScreen.this).setHotelName(taggedProfiles.get(0).getHotels().getHotelName());
                                    PreferenceHandler.getInstance(HotelOptionsScreen.this).setAboutUs(taggedProfiles.get(0).getHotels().getAbouUs());
                                    HotelListAdapter adapter = new HotelListAdapter(HotelOptionsScreen.this,hotelDetailsArrayList);
                                    mHotelList.setAdapter(adapter);



                                }
                                else
                                {
                                    getHotelsByProfileId();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {

                            snackbar = Snackbar
                                    .make(baseLayout, "Something went wrong", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            init();

                                        }
                                    });
                            snackbar.show();

                        }


                    }

                    @Override
                    public void onFailure(Call<ArrayList<ProfileHotelTag>> call, Throwable t) {

                        snackbar = Snackbar
                                .make(baseLayout, "Failed", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        init();

                                    }
                                });
                        snackbar.show();

                    }
                });
            }
        });
    }

    public void getHotelsByProfileId()
    {

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                // String authenticationString = Util.getToken(HotelOptionsScreen.this);//"Basic " +  Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI hotelOperation = Util.getClient().create(HotelsDetailsAPI.class);
                Call<ArrayList<HotelDetails>> response = hotelOperation.getHotelByProfileId(authenticationString, Constants.PROFILE_DATA_ID);

                response.enqueue(new Callback<ArrayList<HotelDetails>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HotelDetails>> call, Response<ArrayList<HotelDetails>> response) {
                        System.out.println("GetHotelByProfileId = "+response.code());
                        ArrayList<HotelDetails> hotelDetailseResponse = response.body();

                        if(response.code() == 200)
                        {
                            try{
                                if(hotelDetailseResponse != null && hotelDetailseResponse.size() != 0)
                                {


                                    hotelDetailsArrayList = response.body();
                                    PreferenceHandler.getInstance(HotelOptionsScreen.this).setHotelId(hotelDetailsArrayList.get(0).getHotelId());
                                    PreferenceHandler.getInstance(HotelOptionsScreen.this).setRecType(hotelDetailsArrayList.get(0).getReconcilationType());
                                    PreferenceHandler.getInstance(HotelOptionsScreen.this).setHotelName(hotelDetailsArrayList.get(0).getHotelName());
                                    PreferenceHandler.getInstance(HotelOptionsScreen.this).setAboutUs(hotelDetailsArrayList.get(0).getAbouUs());
                                    getHotelContact(hotelDetailsArrayList.get(0).getHotelId());
                                    HotelListAdapter adapter = new HotelListAdapter(HotelOptionsScreen.this,hotelDetailsArrayList);
                                    mHotelList.setAdapter(adapter);
                                    //}
                                }
                                else
                                {

                                    snackbar = Snackbar
                                            .make(baseLayout, "No Hotels", Snackbar.LENGTH_INDEFINITE)
                                            .setAction("Retry", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    init();

                                                }
                                            });
                                    snackbar.show();

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {

                            snackbar = Snackbar
                                    .make(baseLayout, "Something went wrong", Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Retry", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            init();

                                        }
                                    });
                            snackbar.show();
                        }


                    }

                    @Override
                    public void onFailure(Call<ArrayList<HotelDetails>> call, Throwable t) {
                        snackbar = Snackbar
                                .make(baseLayout, "Failed", Snackbar.LENGTH_INDEFINITE)
                                .setAction("Retry", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        init();

                                    }
                                });
                        snackbar.show();

                    }
                });
            }
        });
    }

    public void init(){

        if(Util.isNetworkAvailable(HotelOptionsScreen.this)){

            if(snackbar!=null){
                snackbar.dismiss();
            }

            getTaggedHotels();

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


    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(HotelOptionsScreen.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            try{
                String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+Constants.PLACE_ID+"&fields=name,rating,reviews,formatted_phone_number&key="+Constants.PLACE_KEY;

                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setRequestMethod("GET");




                int responseCode = con.getResponseCode();

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);

                }
                in.close();

                //print result
                System.out.println("Response"+response.toString());
                JSONObject object = new JSONObject(response.toString());
                Log.e("JSON_OBJECT", object.toString());
                System.out.println("JSON_Object Data Fire = "+object.toString());
                JSONObject result = object.getJSONObject("result");

                return result;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
            //  JSONParser jParser = new JSONParser();

           /* // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);*/

        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array
                String rating = json.getString("rating");
                                                   /* mHotelStarReting.setText(""+rating+" *");
                                                    DecimalFormat decimalFormat = new DecimalFormat("##.##");*/


                double hotelRating = Double.parseDouble(rating);
                mHotelRating.setNumStars((int)hotelRating);
                /*mHotelStarReting.setText(""+hotelRating+" *");
                loadProgress(mProperty,(int) hotelRating);*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void getHotelContact(final int id){



        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                //String auth_string = Util.getToken(getActivity());
                String auth_string = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI apiService = Util.getClient().create(HotelsDetailsAPI.class);
                Call<ArrayList<HotelContacts>> call = apiService.getConatctByHotelId(auth_string,id);

                call.enqueue(new Callback<ArrayList<HotelContacts>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HotelContacts>> call, Response<ArrayList<HotelContacts>> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();
                        if (statusCode == 200) {


                            ArrayList<HotelContacts> hotelDetails =  response.body();

                            if(hotelDetails != null&&hotelDetails.size()!=0)
                            {
                                HotelContacts dto = hotelDetails.get(0);

                                if(dto!=null){

                                    whatsappNumber =dto.getHotelPhone();
                                    PreferenceHandler.getInstance(HotelOptionsScreen.this).setWhatsappNUmber(whatsappNumber);
                                    PreferenceHandler.getInstance(HotelOptionsScreen.this).setEmailList(dto.getEmailList());
                                    //PreferenceHandler.getInstance(HotelOptionsScreen.this).setEmail(dto.get());

                                }




                            }




                        }else {

                           //Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<HotelContacts>> call, Throwable t) {

                        Log.e("TAG", t.toString());
                    }
                });
            }


        });
    }


    @Override
    public void onBackPressed() {

        try {
            HomeScreenFragment myFragment = (HomeScreenFragment)getSupportFragmentManager().findFragmentByTag("MY_FRAGMENT");
            if (myFragment != null && myFragment.isVisible()) {
                // add your code here
                showalertbox();
            }else{
                super.onBackPressed();
            }


           /* if (count == 0) {
                showalertbox();
            } else {
                getFragmentManager().popBackStack();
            }*/

           /* if(tag!=null&&tag.equalsIgnoreCase("Home")){
                showalertbox();
            }else{

                super.onBackPressed();

            }*/

        } catch (Exception e) {
            e.printStackTrace();
            super.onBackPressed();
        }

    }

    private void showalertbox() throws Exception{



        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(HotelOptionsScreen.this);
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
    }
}
