package details.hotel.app.monarchint.UI.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

import details.hotel.app.monarchint.Customs.CustomFonts.EditText_Roboto_Regular;
import details.hotel.app.monarchint.Customs.CustomFonts.TextViewRobotoregular;
import details.hotel.app.monarchint.Model.ReferCodeModel;
import details.hotel.app.monarchint.Model.TravellerAgentProfiles;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.Constants;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.AgentProfileAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpScreen extends AppCompatActivity {

    EditText_Roboto_Regular first_name,email_id,phone_no,user_name,password,confirm_password,refercode;//
    //private TextView city;
    TextViewRobotoregular mGuest,mAgent;
    private RadioButton mProfileMale,mProfileFemale,mProfileOthers;
    private CardView password_card,confirm_password_card;
    private TextViewRobotoregular submit;
    private TravellerAgentProfiles travellerAgentProfiles;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private String uniqueId;
    private int roleId = 5;
    double referedAmount = 0;

    private ProgressDialog progressDialog;

    //Intent values
    
    int referProfileId=0;
    double referAmountOtherProfile = 0;
    double walletAmountOther = 0;
    boolean response_str ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            setContentView(R.layout.activity_sign_up_screen);

            first_name = (EditText_Roboto_Regular) findViewById(R.id.first_name);
            email_id = (EditText_Roboto_Regular) findViewById(R.id.email_id_profile);
            phone_no = (EditText_Roboto_Regular) findViewById(R.id.phone_no);
            refercode = (EditText_Roboto_Regular) findViewById(R.id.refer_code);
            user_name = (EditText_Roboto_Regular) findViewById(R.id.user_name_profile);
            password = (EditText_Roboto_Regular) findViewById(R.id.password);
            confirm_password = (EditText_Roboto_Regular) findViewById(R.id.confirm_password);

            submit = (TextViewRobotoregular) findViewById(R.id.submit_profile);
            mProfileFemale = (RadioButton) findViewById(R.id.profile_female);
            mProfileMale = (RadioButton) findViewById(R.id.profile_male);
            mProfileOthers = (RadioButton) findViewById(R.id.profile_transgender);

            
            phone_no.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
            
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    validate();
                }
            });

       

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void validate()
    {
        
        String fName = first_name.getText().toString();
        String uName = user_name.getText().toString();
        String email = email_id.getText().toString();
        String phoneNumber = phone_no.getText().toString();
        String pPassword = password.getText().toString();
        String pEnterPassword = confirm_password.getText().toString();


        if(fName == null || fName.isEmpty())
        {
            first_name.setError("Please Enter Name");
            first_name.requestFocus();
        }else if(!mProfileMale.isChecked() && !mProfileFemale.isChecked() && !mProfileOthers.isChecked())
        {
            Toast.makeText(SignUpScreen.this,getResources().getString(R.string.gender_validation_message),Toast.LENGTH_SHORT).show();
        }
        else if(uName == null || uName.isEmpty())
        {
            user_name.setError(getResources().getString(R.string.unique_name_validation_message));
            user_name.requestFocus();
        }
        else if(email == null || email.isEmpty() )
        {
            email_id.setError(getResources().getString(R.string.email_validation_message));
            email_id.requestFocus();
        }
        else if(!isValidEmail(email))
        {
            email_id.setError(getResources().getString(R.string.email_validation_message));
            email_id.requestFocus();
        }
        else if(phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.length() != 10)
        {
            phone_no.setError(getResources().getString(R.string.phone_number_validation_message));
            phone_no.requestFocus();
        }else if(pPassword == null || pPassword.isEmpty())
        {
            password.setError("Please Enter Password");
            password.requestFocus();
        }
        else if(pEnterPassword == null || pEnterPassword.isEmpty())
        {
            confirm_password.setError("Please Enter Confirm Password");
            confirm_password.requestFocus();
        }
        else if(!pPassword.equals(pEnterPassword))
        {
            confirm_password.setError("Password and Confirm Password Should be Same");
            confirm_password.requestFocus();
        }
        else
        {
            register();
        }


    }



    public void register() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        if (isValidEmail(email_id.getText().toString())&& isValidUserName(user_name.getText().toString()) &&
                isValidPhone(phone_no.getText().toString())
                && isValidPassword(password.getText().toString())) {
            if (isValidConfirmPass(password.getText().toString(),confirm_password.getText().toString())) {

                boolean res1 = checkPhoneAvailablity();
            }
            else {
                progressDialog.dismiss();
                Toast.makeText(SignUpScreen.this,"Password dose not match.",Toast.LENGTH_SHORT).show();
            }
        }else {
            progressDialog.dismiss();
            Toast.makeText(SignUpScreen.this,"Please Enter valid credentials.",Toast.LENGTH_SHORT).show();
        }

    }

    private void loginThreadCall(){

        travellerAgentProfiles = new TravellerAgentProfiles();

        travellerAgentProfiles.setFirstName(first_name.getText().toString());
        travellerAgentProfiles.setEmail(email_id.getText().toString());
        travellerAgentProfiles.setPhoneNumber(phone_no.getText().toString());
        if(mProfileMale.isChecked())
        {
            travellerAgentProfiles.setGender("Male");
        }
        else if(mProfileFemale.isChecked())
        {
            travellerAgentProfiles.setGender("Female");
        }
        else if(mProfileOthers.isChecked())
        {
            travellerAgentProfiles.setGender("Others");
        }

        travellerAgentProfiles.setAddress("");
        travellerAgentProfiles.setPinCode("");
        travellerAgentProfiles.setUserRoleId(roleId);


        travellerAgentProfiles.setPassword(password.getText().toString());
        travellerAgentProfiles.setUserName(user_name.getText().toString());
        if(refercode.getText().toString()!=null && !refercode.getText().toString().isEmpty()){
            travellerAgentProfiles.setReferralCodeUsed(refercode.getText().toString());
            travellerAgentProfiles.setWalletBalance((int)referedAmount);

        }


        travellerAgentProfiles.setStatus("Active");
        travellerAgentProfiles.setPlans("Basic");


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                AgentProfileAPI apiService = Util.getClient().create(AgentProfileAPI.class);

                String authenticationString = Util.getToken(SignUpScreen.this);
                Call<TravellerAgentProfiles> call = apiService.loginApi(Constants.auth_string,travellerAgentProfiles);

                call.enqueue(new Callback<TravellerAgentProfiles>() {
                    @Override
                    public void onResponse(Call<TravellerAgentProfiles> call, Response<TravellerAgentProfiles> response) {

                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        int statusCode = response.code();
                        if (statusCode == 200 || statusCode == 201) {

                            TravellerAgentProfiles dto = response.body();

                            if(dto!=null){
                                Toast.makeText(SignUpScreen.this, "Profile Created Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpScreen.this, LoginScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }


                        }else {

                            Toast.makeText(SignUpScreen.this, "Registration failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TravellerAgentProfiles> call, Throwable t) {
                        // Log error here since request failed
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }
        });
    }


    public final static boolean isValidEmail(CharSequence target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else {
            System.out.println(Patterns.EMAIL_ADDRESS.matcher(target).matches());
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean isValidName(String target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else {
            return target.matches("[A-Za-z][^&*:;<>.,/]*");
        }
    }

    public final static boolean isValidPhone(String target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else if (target.length()==10){
            if (Patterns.PHONE.matcher(target).matches()){
                return target.matches("[0-9][^-. ]*");
            }
            return false;
        }else return false;
    }

    public final static boolean isValidPin(String target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else if (target.length()==6){
            return target.matches("^[0-9]*$");
        }else return false;
    }

    public final static boolean isValidUserName(String target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else {
            return target.matches("^[a-zA-Z][^/ ]*$");
        }//else return false;
    }

    public final static boolean isValidPassword(String target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else return true;
    }

    public final static boolean isValidConfirmPass(String target,String target2)
    {
        if (TextUtils.isEmpty(target) && TextUtils.isEmpty(target2))
        {
            return false;
        } else if (target.equals(target2)){
            return true;
        }else return false;
    }

    public boolean validate(EditText _emailText, EditText _passwordText ) {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Override
    protected void onResume() {
        super.onResume();

        email_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

//                isValidEmail();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*if(!isValidEmail(email_id.getText().toString()))
                {
                    email_id.requestFocus();
                    email_id.setError(getResources().getString(R.string.email_validation_message));
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        first_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                isValidEmail();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!isValidName(first_name.getText().toString()))
                {
                    first_name.requestFocus();
                    first_name.setError("Enter Correct Name ..!!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phone_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                isValidEmail();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!isValidPhone(phone_no.getText().toString()))
                {
                    phone_no.requestFocus();
                    phone_no.setError("Enter Correct Phone ..!!");


                }else{
                    // getTravelerByPhone(phone_traveler.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                isValidEmail();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!isValidUserName(user_name.getText().toString()))
                {
                    user_name.requestFocus();
                    user_name.setError("Enter valid user name ..!!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                isValidEmail();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!isValidPassword(password.getText().toString()))
                {
                    password.requestFocus();
                    password.setError("Should not be empty ..!!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                isValidEmail();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!isValidConfirmPass(password.getText().toString(),confirm_password.getText().toString()))
                {
                    confirm_password.requestFocus();
                    confirm_password.setError("Password Does Not Match");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }



    private boolean checkEmailAvailablity(){


        final TravellerAgentProfiles p1 = new TravellerAgentProfiles();

        p1.setEmail(email_id.getText().toString());

        String authenticationString = Util.getToken(SignUpScreen.this);

        AgentProfileAPI apiService = Util.getClient().create(AgentProfileAPI.class);
        Call<String> call = apiService.getProfileByEmail(Constants.auth_string,p1);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                int statusCode = response.code();
                if (statusCode == 200) {

                    String  s = response.body();

                    if (s.equals("Profile Exist")){
                        email_id.requestFocus();
                        email_id.setError("Email Already exist..!!");
                        response_str = true;
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                    }else {
                        if(refercode.getText().toString()==null||refercode.getText().toString().isEmpty()){
                            //boolean res2 = checkPhoneAvailablity();
                            boolean res3 = checkUserNameAvailability();

                        }else{
                            boolean res2 = checkReferalCode();
                        }
                        // boolean res2 = checkPhoneAvailablity();
                        response_str = false;
                    }

                }else {
                    if (progressDialog!=null)
                        progressDialog.dismiss();
                    Toast.makeText(SignUpScreen.this, "checking failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
                }
//                callGetStartEnd();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                if (progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("TAG", t.toString());
            }
        });
//            }


//        });


        return response_str;
    }


    private boolean checkReferalCode(){

        final ReferCodeModel p1 = new ReferCodeModel();

        p1.setReferralCode(refercode.getText().toString());

        String authenticationString = Util.getToken(SignUpScreen.this);

        AgentProfileAPI apiService = Util.getClient().create(AgentProfileAPI.class);
        Call<ArrayList<TravellerAgentProfiles>> call = apiService.getProfileByReferCode(Constants.auth_string,p1);

        call.enqueue(new Callback<ArrayList<TravellerAgentProfiles>>() {
            @Override
            public void onResponse(Call<ArrayList<TravellerAgentProfiles>> call, Response<ArrayList<TravellerAgentProfiles>> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();

                int statusCode = response.code();
                if (statusCode == 200) {

                    ArrayList<TravellerAgentProfiles> s = response.body();

                    if (s!=null && s.size() != 0) {
                        // boolean res2 = checkPhoneAvailablity();
                        boolean res3 = checkUserNameAvailability();
                        referedAmount = s.get(0).getReferralAmount();
                        referProfileId = s.get(0).getTravellerAgentProfileId();
                        referAmountOtherProfile = s.get(0).getReferralAmountForOtherProfile();
                        walletAmountOther = s.get(0).getWalletBalance();
                        System.out.println("Refered Amount=="+s.get(0).getReferralAmount());
                        response_str = false;
                    }
                    else
                    {
                        //boolean res2 = checkPhoneAvailablity();
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        response_str = true;
                        Toast.makeText(SignUpScreen.this, "Invalid referral code entered", Toast.LENGTH_SHORT).show();
                    }


                }else {

                    boolean res3 = checkUserNameAvailability();
                    response_str = true;

                }
//                callGetStartEnd();
            }

            @Override
            public void onFailure(Call<ArrayList<TravellerAgentProfiles>> call, Throwable t) {
                // Log error here since request failed
                if (progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("TAG", t.toString());
            }
        });


        return response_str;
    }

    private boolean checkPhoneAvailablity(){

        final TravellerAgentProfiles p1 = new TravellerAgentProfiles();

        p1.setPhoneNumber(phone_no.getText().toString());


        AgentProfileAPI apiService =
                Util.getClient().create(AgentProfileAPI.class);
        Call<ArrayList<TravellerAgentProfiles>> call = apiService.getProfileByPhone(Constants.auth_string,p1);

        call.enqueue(new Callback<ArrayList<TravellerAgentProfiles>>() {
            @Override
            public void onResponse(Call<ArrayList<TravellerAgentProfiles>> call, Response<ArrayList<TravellerAgentProfiles>> response) {
                //                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                int statusCode = response.code();
                if (statusCode == 200) {
                    //String  s = response.body();

                    if (response.body()!=null){
                        //if (s.equals("Profile Exist")){
                        if (progressDialog!=null)
                            progressDialog.dismiss();

                        phone_no.requestFocus();
                        phone_no.setError("Phone Number Already exist..!!");
                        response_str = true;
                    }


                }else if (statusCode == 404){
                    boolean res3 = checkEmailAvailablity();
                    response_str = false;
                } else{
                    if (progressDialog!=null)
                        progressDialog.dismiss();
                    Toast.makeText(SignUpScreen.this, "checking failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
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

        return response_str;
    }

    private boolean checkUserNameAvailability(){

        final TravellerAgentProfiles p1 = new TravellerAgentProfiles();

        p1.setUserName(user_name.getText().toString());

        AgentProfileAPI apiService = Util.getClient().create(AgentProfileAPI.class);
        String authenticationString = Util.getToken(SignUpScreen.this);
        Call<String> call = apiService.getProfileByUserName(Constants.auth_string,user_name.getText().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                int statusCode = response.code();
                if (statusCode == 200 || statusCode == 201) {

                    String  s = response.body();

                    if (s.equals("Profile Exist")){
                        if (progressDialog!=null)
                            progressDialog.dismiss();

                        user_name.requestFocus();
                        user_name.setError("User Already exist..!!");
                        response_str = true;
                    }
                    else
                    {
                        response_str = false;
                        loginThreadCall();
                    }

                }else {
                    if (progressDialog!=null)
                        progressDialog.dismiss();
                    Toast.makeText(SignUpScreen.this, "checking failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Log error here since request failed
                if (progressDialog!=null)
                    progressDialog.dismiss();
                Log.e("TAG", t.toString());
            }
        });

        return response_str;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home:
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
