package details.hotel.app.monarchint.UI.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import customfonts.MyEditText;
import customfonts.MyTextView;
import details.hotel.app.monarchint.FireBase.SharedPrefManager;
import details.hotel.app.monarchint.Model.DeviceMapping;
import details.hotel.app.monarchint.Model.TravellerAgentProfiles;
import details.hotel.app.monarchint.Model.UserRole;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.Constants;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.AgentProfileAPI;
import details.hotel.app.monarchint.WebAPI.DeviceMappingApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {

    MyEditText mUserName,mUserPwd;
    MyTextView mLogin,mSignUp;


    public static final int MY_PERMISSIONS_REQUEST_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            setContentView(R.layout.activity_login_screen);

            mUserName = (MyEditText)findViewById(R.id.signin_Username);
            mUserPwd = (MyEditText)findViewById(R.id.signin_Password);

            mLogin = (MyTextView)findViewById(R.id.signinBtn);
            mSignUp = (MyTextView)findViewById(R.id.signupBtn);

            mSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        Intent signUp = new Intent(LoginScreen.this,SignUpScreen.class);
                        startActivity(signUp);
                        LoginScreen.this.finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try
                    {

                        Util.hideKeyboard(LoginScreen.this);
                        if (!mUserName.getText().toString().trim().isEmpty() && !mUserPwd.getText().toString().trim().isEmpty()) {
                            validate();
                            TravellerAgentProfiles travellerAgentProfiles = new TravellerAgentProfiles();
                            travellerAgentProfiles.setUserName(mUserName.getText().toString());
                            travellerAgentProfiles.setPassword(mUserPwd.getText().toString());
                            login(travellerAgentProfiles);
                        }
                        else
                            Toast.makeText(LoginScreen.this, "Fields should not be empty", Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });

        }catch(Exception e){

            e.printStackTrace();
        }
    }

    private void validate()
    {
        String uname = mUserName.getText().toString();
        String pass = mUserName.getText().toString();

        if(uname == null || uname.isEmpty())
        {
            mUserName.setError(getResources().getString(R.string.user_name_validation_message));
            mUserName.requestFocus();
        }
        else if(pass == null || pass.isEmpty())
        {
            mUserPwd.setError(getResources().getString(R.string.password_validation_message));
            mUserPwd.requestFocus();
        }
    }
    private void login(final TravellerAgentProfiles travellerAgentProfiles) throws Exception{

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {

                try
                {
                    AgentProfileAPI apiService = Util.getClient().create(AgentProfileAPI.class);
                    //  String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                    Call<ArrayList<TravellerAgentProfiles>> call = apiService.loginAgent(Constants.auth_string,travellerAgentProfiles);

                    call.enqueue(new Callback<ArrayList<TravellerAgentProfiles>>() {
                        @Override
                        public void onResponse(Call<ArrayList<TravellerAgentProfiles>> call, Response<ArrayList<TravellerAgentProfiles>> response) {

                            try
                            {
                                int statusCode = response.code();
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                                if (statusCode == 200 || statusCode == 201) {

                                    ArrayList<TravellerAgentProfiles> dto1 = response.body();

                                    if (dto1!=null && dto1.size()!=0) {

                                        TravellerAgentProfiles dto = dto1.get(0);

                                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginScreen.this);
                                        SharedPreferences.Editor spe = sp.edit();
                                        spe.putInt(Constants.USER_ID, dto.getTravellerAgentProfileId());
                                        PreferenceHandler.getInstance(LoginScreen.this).setTravellerId(dto.getTravellerAgentProfileId());
                                        PreferenceHandler.getInstance(LoginScreen.this).setCommissionAmount(dto.getCommissionAmount());
                                        PreferenceHandler.getInstance(LoginScreen.this).setCommissionPercentage(dto.getCommissionPercentage());
                                        PreferenceHandler.getInstance(LoginScreen.this).setReferalAmount(dto.getReferralAmountForOtherProfile());
                                        PreferenceHandler.getInstance(LoginScreen.this).setReferedAmount(dto.getWalletBalance());


                                        if(dto.getMiddleName()==null||dto.getLastName()==null){
                                            PreferenceHandler.getInstance(LoginScreen.this).setFullName(dto.getFirstName());
                                        }else{
                                            PreferenceHandler.getInstance(LoginScreen.this).setFullName(dto.getFirstName()+" "+dto.getLastName());
                                        }

                                        PreferenceHandler.getInstance(LoginScreen.this).setAgentPhoneNumber(dto.getPhoneNumber());
                                        PreferenceHandler.getInstance(LoginScreen.this).setAgentName(dto.getUserName());

                                        UserRole userRole = dto.getUserRoles();
                                        if(userRole != null)
                                        {
                                            PreferenceHandler.getInstance(LoginScreen.this).setUserRoleUniqueID(userRole.getUserRoleUniqueId());
                                        }

                                        String token = SharedPrefManager.getInstance(LoginScreen.this).getDeviceToken();
                                        System.out.println("token"+token);

                                        if(token!=null){
                                            DeviceMapping hm = new DeviceMapping();
                                            hm.setProfileId(dto.getTravellerAgentProfileId());
                                            hm.setDeviceId(token);
                                            addDeviceId(hm);
                                        }else{
                                            Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginScreen.this, BaseActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                            startActivity(intent);
                                            LoginScreen.this.finish();
                                        }

                                    }else{

                                        Toast.makeText(LoginScreen.this, "Login credentials are wrong..", Toast.LENGTH_SHORT).show();

                                    }
                                }else {
                                    if (progressDialog!=null)
                                        progressDialog.dismiss();
                                    Toast.makeText(LoginScreen.this, "Login failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Call<ArrayList<TravellerAgentProfiles>> call, Throwable t) {
                            // Log error here since request failed
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            Log.e("TAG", t.toString());
                        }
                    });
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void addDeviceId(final DeviceMapping hm) throws Exception
    {
        final ProgressDialog dialog = new ProgressDialog(LoginScreen.this);
        dialog.setMessage("Saving Details");
        dialog.setCancelable(false);
        dialog.show();
        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                try {
                    String authenticationString = Util.getToken(LoginScreen.this);
                    DeviceMappingApi hotelOperation = Util.getClient().create(DeviceMappingApi.class);
                    Call<DeviceMapping> response = hotelOperation.addProfileMap(hm);

                    response.enqueue(new Callback<DeviceMapping>() {
                        @Override
                        public void onResponse(Call<DeviceMapping> call, Response<DeviceMapping> response) {

                            try
                            {
                                if(dialog != null && dialog.isShowing())
                                {
                                    dialog.dismiss();
                                }
                                if(response.code() == 200||response.code() == 201||response.code() == 202||response.code() == 204)
                                {
                                    DeviceMapping hotelDetailseResponse = response.body();

                                    if(hotelDetailseResponse != null)
                                    {
                                        Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginScreen.this, BaseActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                        startActivity(intent);
                                        LoginScreen.this.finish();
                                    }



                                }else if(response.code() == 404){
                                    if(response.body()==null){
                                        Toast.makeText(LoginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginScreen.this, BaseActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                        startActivity(intent);
                                        LoginScreen.this.finish();
                                    }
                                }
                                else
                                {

                                    Toast.makeText(LoginScreen.this,"Check your internet connection or please try after some time", Toast.LENGTH_LONG).show();
                                }
                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(Call<DeviceMapping> call, Throwable t) {
                            System.out.println("Failed");
                            System.out.println(" Exception = "+t.getMessage());
                            if(dialog != null && dialog.isShowing())
                            {
                                dialog.dismiss();
                            }
                            Toast.makeText(LoginScreen.this,"Check your internet connection or please try after some time", Toast.LENGTH_LONG).show();

                        }
                    });
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });
    }
}
