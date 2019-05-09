package details.hotel.app.monarchint.UI.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import details.hotel.app.monarchint.Adapter.MoreAdapter;
import details.hotel.app.monarchint.Adapter.NavigationListAdapter;
import details.hotel.app.monarchint.Model.NavBarItems;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.UI.Activities.AboutHotelActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

    ListView navBarListView;
    String[] title = null;


    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        try{

            View view = inflater.inflate(R.layout.fragment_more,container,false);

            navBarListView = (ListView) view.findViewById(R.id.navbar_list);
            title = getResources().getStringArray(R.array.more_option);

            if(title != null)
            {
                ArrayList<NavBarItems> navBarItemsList = new ArrayList<>();

                for (String t:title) {

                    NavBarItems navbarItem = new NavBarItems(t);
                    navBarItemsList.add(navbarItem);
                }
                MoreAdapter adapter = new MoreAdapter(getContext(),navBarItemsList);
                navBarListView.setAdapter(adapter);

            }

            navBarListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    try
                    {
                        displayViewBasedOnRoles(title[position]);
                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });

            return view;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private void displayViewBasedOnRoles(String s) {
        switch (s)
        {
            case "About Hotel":
                Intent dynamic_1 = new Intent(getContext(), AboutHotelActivity.class);
                startActivity(dynamic_1);

                break;

            /*case "Terms & Conditions":
                Intent dynamic_2 = new Intent(getContext(), TermsAndConditionActivity.class);
                startActivity(dynamic_2);

                break;

            case "Logout":
                Intent dynamic_3 = new Intent(getContext(), SplashActivity.class);
                startActivity(dynamic_3);

                break;

            case "Hotel Option":
                Intent dynamic_4 = new Intent(getContext(), HotelOptionsScreen.class);
                startActivity(dynamic_4);

                break;

                case "Create Hotel":

                    Intent dynamic_3 = new Intent(MainActivity.this,CreateHotelActivity.class);
                    startActivity(dynamic_3);

                    break;

                case "Create RatePlan":

                    Intent dynamic_4 = new Intent(MainActivity.this, CreateRatePlan.class);
                    startActivity(dynamic_4);

                    break;

                case "Create RateList":
                    Intent dynamic_5 = new Intent(MainActivity.this, CreateRatePlanListScreen.class);
                    startActivity(dynamic_5);

                    break;*/
        }
    }

}
