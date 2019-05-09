package details.hotel.app.monarchint.UI.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.UI.Fragments.GalleryRowFragment;
import details.hotel.app.monarchint.UI.Fragments.HomeScreenFragment;
import details.hotel.app.monarchint.UI.Fragments.MoreFragment;
import details.hotel.app.monarchint.UI.Fragments.RoomCategoryFragment;
import details.hotel.app.monarchint.UI.Fragments.RoomsFragment;

public class BaseActivity extends AppCompatActivity {



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    fragment = new HomeScreenFragment();
                    break;
                case R.id.navigation_dashboard:
                    fragment = new GalleryRowFragment();
                    break;
                case R.id.navigation_notifications:
                    fragment = new RoomCategoryFragment();
                    break;
                case R.id.navigation_more:
                    fragment = new MoreFragment();
                    break;

            }
            return loadFragment(fragment);
        }
    };

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.hotel_fragment_view, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            setContentView(R.layout.activity_base);

           /* getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
            setTitle("Hotel Monarch International");

            loadFragment(new HomeScreenFragment());

            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
