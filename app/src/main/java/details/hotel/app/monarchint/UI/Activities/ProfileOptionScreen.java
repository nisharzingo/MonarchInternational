package details.hotel.app.monarchint.UI.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import details.hotel.app.monarchint.Adapter.NavAdapterWithIcon;
import details.hotel.app.monarchint.Model.NavBarItemWithIcon;
import details.hotel.app.monarchint.Model.TravellerAgentProfiles;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.AgentProfileAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileOptionScreen extends AppCompatActivity {

    CircleImageView mProfilePhoto;
    TextView mProfileName,mWishField,mReferColde;
    LinearLayout mNavbarHeader;
    ListView navBarListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            setContentView(R.layout.activity_profile_option_screen);

            mProfileName = (TextView)findViewById(R.id.main_user_name);
            mReferColde = (TextView)findViewById(R.id.referal_code);
            mWishField = (TextView)findViewById(R.id.main_greetings);
            mProfilePhoto = (CircleImageView) findViewById(R.id.profile_photo);
            mNavbarHeader = (LinearLayout) findViewById(R.id.main_user_profile);

            navBarListView = (ListView) findViewById(R.id.navbar_list);

            mReferColde.setText("ZINGO"+ PreferenceHandler.getInstance(ProfileOptionScreen.this).getTravellerId());
            mProfileName.setText(""+PreferenceHandler.getInstance(ProfileOptionScreen.this).getFullName());

            setUpNavigationDrawer();
            getTimeFromAndroid();
            getProfileById();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void setUpNavigationDrawer() {

        TypedArray icons = null;
        String[] title  = null;
        icons = getResources().obtainTypedArray(R.array.agent_navbar_images);
        title  = getResources().getStringArray(R.array.agent_navbar_items_title);

        ArrayList<NavBarItemWithIcon> navBarItemsList = new ArrayList<>();

        for (int i=0;i<title.length;i++)
        {
            NavBarItemWithIcon navbarItem = new NavBarItemWithIcon(title[i],icons.getResourceId(i, -1));
            navBarItemsList.add(navbarItem);
        }

        NavAdapterWithIcon adapter = new NavAdapterWithIcon(getApplicationContext(),navBarItemsList);
        navBarListView.setAdapter(adapter);

        final String[] finalTitle = title;
        navBarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // drawer.closeDrawer(START);
                displayView(finalTitle[position]);
            }
        });
    }


    private void displayView(String position) {
        //System.out.println("position = "+position);
        switch (position)
        {
            case "My profile":

                break;



            case "Earnings":
                Intent rc = new Intent(ProfileOptionScreen.this,PointsScreen.class);
                startActivity(rc);
                break;
            case "Booking History":
                Intent bookingHistory = new Intent(ProfileOptionScreen.this,BookingHistoryActivity.class);
                startActivity(bookingHistory);
                break;
            /*case "Invite & Earn":

                Intent rc = new Intent(ProfileOptionScreen.this,PointsScreen.class);
                startActivity(rc);
                break;
*/
            /*case "Notifications":


                Intent nfm = new Intent(ProfileOptionScreen.this,NotificationListActivity.class);
                startActivity(nfm);
                break;*/


           /* case "Call Back":
                call();
                break;*/
            case "Logout":
                showalertbox();
                break;



        }
    }

    private void showalertbox() {



        final android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(ProfileOptionScreen.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.exit_alert_layout,null);
        TextView dontCancelBtn = (TextView)view.findViewById(R.id.no_btn);
        TextView cancelBtn = (TextView)view.findViewById(R.id.exit_app_btn);
        cancelBtn.setText("Logout");
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

                    PreferenceHandler.getInstance(ProfileOptionScreen.this).clear();
                    Toast.makeText(ProfileOptionScreen.this,"Logout done successfully",Toast.LENGTH_SHORT).show();
                    PreferenceHandler.getInstance(ProfileOptionScreen.this).clear();
                    Intent log = new Intent(ProfileOptionScreen.this, SignUpOptionScreen.class);
                    log.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    log.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(log);

                    finish();


                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
    }



    private void getProfileById() {

        final ProgressDialog dialog = new ProgressDialog(ProfileOptionScreen.this);
        dialog.setTitle("Loading");
        dialog.setCancelable(false);
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                AgentProfileAPI profileApi = Util.getClient().create(AgentProfileAPI.class);
                String authenticationString = Util.getToken(ProfileOptionScreen.this);
                Call<TravellerAgentProfiles> getProfile = profileApi.getProfileByID(authenticationString,
                        PreferenceHandler.getInstance(ProfileOptionScreen.this).getUserId());
                //System.out.println("hotelid = "+hotelid);
                System.out.println();

                getProfile.enqueue(new Callback<TravellerAgentProfiles>() {
                    @Override
                    public void onResponse(Call<TravellerAgentProfiles> call, Response<TravellerAgentProfiles> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        if(response.code() == 200)
                        {
                            TravellerAgentProfiles dto = response.body();

                            if(dto != null)
                            {

                                if(dto.getProfilePhoto() != null && !dto.getProfilePhoto().isEmpty())
                                {
                                    if(dto.getProfilePhoto() == null|| dto.getProfilePhoto().equalsIgnoreCase("") ||dto.getProfilePhoto().equalsIgnoreCase("test")){
                                        mProfilePhoto.setImageResource(R.drawable.icons_profile);
                                    }else{
                                        Picasso.with(ProfileOptionScreen.this).load(dto.getProfilePhoto()).placeholder(R.drawable.icons_profile).error(R.drawable.icons_profile).into(mProfilePhoto);
                                    }

                                    // mUserProfileImage.setEnabled(false);
                                }else {
                                    mProfilePhoto.setImageResource(R.drawable.icons_profile);
                                }

                                mProfileName.setText(""+dto.getFirstName());

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<TravellerAgentProfiles> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    private void getTimeFromAndroid() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        if(hours>=1 && hours<=12){
            //Toast.makeText(this, "Good Morning "+PreferenceHandler.getInstance(HotelListActivity.this).getUserFullName(), Toast.LENGTH_SHORT).show();
            mWishField.setText("Good Morning");
        }else if(hours>=12 && hours<=16){
            //Toast.makeText(this, "Good Afternoon "+PreferenceHandler.getInstance(HotelListActivity.this).getUserFullName(), Toast.LENGTH_SHORT).show();
            mWishField.setText("Good Afternoon");
        }else if(hours>=16 && hours<=20){
            mWishField.setText("Good Evening");
            //Toast.makeText(this, "Good Evening "+PreferenceHandler.getInstance(HotelListActivity.this).getUserFullName(), Toast.LENGTH_SHORT).show();
        }else if(hours>=20 && hours<=24){
            mWishField.setText("Good Night");
            //Toast.makeText(this, "Good Night "+PreferenceHandler.getInstance(HotelListActivity.this).getUserFullName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home:
                goback();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        goback();
    }

    public void goback()
    {
            ProfileOptionScreen.this.finish();
    }
}
