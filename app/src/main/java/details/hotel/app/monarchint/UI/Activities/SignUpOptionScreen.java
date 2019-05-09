package details.hotel.app.monarchint.UI.Activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import details.hotel.app.monarchint.Adapter.SlideAdapter;
import details.hotel.app.monarchint.Customs.CustomFonts.TextViewRobotoregular;
import details.hotel.app.monarchint.R;
import me.relex.circleindicator.CircleIndicator;

public class SignUpOptionScreen extends AppCompatActivity {

    private ViewPager viewPager;
    private ImageView mAppLogo;
    private SlideAdapter a;
    private CircleIndicator indicator;

    TextViewRobotoregular mSignUp,mLogin, mLoginAsGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            setContentView(R.layout.activity_sign_up_option_screen);

            viewPager = (ViewPager)findViewById(R.id.view_pager_slide);
            mAppLogo = (ImageView) findViewById(R.id.app_logos);
            indicator = (CircleIndicator)findViewById(R.id.indicator);
            mSignUp = (TextViewRobotoregular)findViewById(R.id.signUp);
            mLogin = (TextViewRobotoregular)findViewById(R.id.login);
            mLoginAsGuest = (TextViewRobotoregular)findViewById(R.id.login_user_asguest_btn);


            a = new SlideAdapter(getSupportFragmentManager());
            viewPager.setAdapter(a);
            indicator.setViewPager(viewPager);
            a.registerDataSetObserver(indicator.getDataSetObserver());

            mLoginAsGuest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent guestLogin = new Intent(SignUpOptionScreen.this, HotelOptionsScreen.class);
                    Intent guestLogin = new Intent(SignUpOptionScreen.this, BaseActivity.class);
                    startActivity(guestLogin);
                    SignUpOptionScreen.this.finish();
                }
            });



            mLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent login = new Intent(SignUpOptionScreen.this,LoginScreen.class);
                    startActivity(login);

                }
            });

            mSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent signUp = new Intent(SignUpOptionScreen.this,SignUpScreen.class);
                    startActivity(signUp);

                }
            });

            mAppLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
