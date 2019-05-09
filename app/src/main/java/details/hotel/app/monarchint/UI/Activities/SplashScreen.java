package details.hotel.app.monarchint.UI.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import details.hotel.app.monarchint.BuildConfig;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.PreferenceHandler;

public class SplashScreen extends AppCompatActivity {

    private TextView mVersionCode;
    private Animation animation;
    private ImageView logo,pics;
    private TextView appTitle;
    private TextView appSlogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{

            setContentView(R.layout.activity_splash_screen);

            mVersionCode = (TextView) findViewById(R.id.version_name);
            String versioncode = BuildConfig.VERSION_NAME;
            mVersionCode.setText("Ver: "+versioncode+"");

            logo = (ImageView) findViewById(R.id.logo_img);
            pics = (ImageView) findViewById(R.id.pics);
            appTitle = (TextView) findViewById(R.id.track_txt);
            appSlogan = (TextView) findViewById(R.id.pro_txt);

            if (savedInstanceState == null) {
                flyIn();
            }

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    int profileId = PreferenceHandler.getInstance(SplashScreen.this).getTravellerId();
                    if(profileId!=0){
                        Intent intent = new Intent(SplashScreen.this, HotelOptionsScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(intent);
                        SplashScreen.this.finish();

                    }else{

                        Intent intent = new Intent(SplashScreen.this, SignUpOptionScreen.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        startActivity(intent);
                        SplashScreen.this.finish();

                    }
                }
            }, 3000);




        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void flyIn() {
        animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        logo.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,
                R.anim.app_name_animation);
        appTitle.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this, R.anim.pro_animation);
        appSlogan.startAnimation(animation);

        animation = AnimationUtils.loadAnimation(this,R.anim.logo_animation);
        pics.startAnimation(animation);
    }
}
