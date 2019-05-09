package details.hotel.app.monarchint.UI.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import details.hotel.app.monarchint.R;

public class AppTCScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            setContentView(R.layout.activity_app_tcscreen);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
