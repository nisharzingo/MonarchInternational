package details.hotel.app.monarchint.UI.Activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import details.hotel.app.monarchint.Customs.CustomFonts.TextViewSFProDisplayRegular;
import details.hotel.app.monarchint.Model.TravellerAgentProfiles;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.AgentProfileAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointsScreen extends AppCompatActivity {


    TextView mCommision,mTotal,mReferalCode;
    TextViewSFProDisplayRegular mRedeem,mWallet,mUsed;
    private RecyclerView referal_list;
   // private ReferalCodeAdapter adapter;
    ArrayList<TravellerAgentProfiles> profiles;
    long commissionAmount;
    int walletAmount,usedAmount;
    View empty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            setContentView(R.layout.activity_points_screen);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            setTitle("Referals");

            commissionAmount = PreferenceHandler.getInstance(PointsScreen.this).getReferalAmount();
            //  walletAmount = PreferenceHandler.getInstance(ReferalCodeActivity.this).getReferedAmount();



            mCommision = (TextView)findViewById(R.id.commission_amount);
            mRedeem = (TextViewSFProDisplayRegular)findViewById(R.id.redeem);
            mRedeem.setVisibility(View.GONE);
            mWallet = (TextViewSFProDisplayRegular)findViewById(R.id.wallet_amount);
            mUsed = (TextViewSFProDisplayRegular)findViewById(R.id.used_amount);
            empty = (View)findViewById(R.id.empty);
            mTotal = (TextView)findViewById(R.id.total_earnings);
            mReferalCode = (TextView)findViewById(R.id.refer_code);
            referal_list = (RecyclerView) findViewById(R.id.referal_list);

           // mCommision.setText("₹ "+commissionAmount+" per new signup user");
            mReferalCode.setText("ZINGO"+PreferenceHandler.getInstance(PointsScreen.this).getUserId());


            getAgentProfilesId();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    private void getAgentProfilesId() {

        final ProgressDialog dialog = new ProgressDialog(PointsScreen.this);
        dialog.setTitle("Loading");
        dialog.setCancelable(false);
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                AgentProfileAPI profileApi = Util.getClient().create(AgentProfileAPI.class);
                String authenticationString = Util.getToken(PointsScreen.this);
                Call<TravellerAgentProfiles> getProfile = profileApi.getProfileByID(authenticationString,
                        PreferenceHandler.getInstance(PointsScreen.this).getTravellerId());
                //System.out.println("hotelid = "+hotelid);
                System.out.println();

                getProfile.enqueue(new Callback<TravellerAgentProfiles>() {
                    @Override
                    public void onResponse(Call<TravellerAgentProfiles> call, Response<TravellerAgentProfiles> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        try{
                            if(response.code() == 200)
                            {
                                TravellerAgentProfiles dto = response.body();

                                if(dto != null)
                                {

                                    walletAmount = dto.getWalletBalance();
                                    usedAmount = dto.getUsedAmount();
                                    mUsed.setText("₹ "+usedAmount);
                                   // checkReferalCode();

                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        try{
            int id = item.getItemId();
            switch (id) {

                case android.R.id.home:

                    PointsScreen.this.finish();
                    break;

               /* case R.id.action_share:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello this is Zingo Hotels Agent App. Join the Zingo Hotels referral programme, and earn money for every new referral.\n Open the Zingo Hotels App and visit the invite & earn section, and find out your referral code.  It’s an alpha-numeric code like: ZINGO"+ PreferenceHandler.getInstance(ReferalCodeActivity.this).getUserId()+"\n Keep Sharing & Earning.\nTo Download the app click here: https://play.google.com/store/apps/details?id=app.zingo.com.agentapp");
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent,"Zingo Agent" ));
                    break;*/



            }
            return super.onOptionsItemSelected(item);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }
}
