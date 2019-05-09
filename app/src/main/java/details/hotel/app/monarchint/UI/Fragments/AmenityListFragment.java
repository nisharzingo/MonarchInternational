package details.hotel.app.monarchint.UI.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import details.hotel.app.monarchint.Adapter.CustomGridViewAdapter;
import details.hotel.app.monarchint.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AmenityListFragment extends Fragment {


    GridView androidGridView;

    String[] gridViewString = {
            "Wifi", "Airport Shuttle",  "No Smoking", "Family", "Restaurant"

    } ;
    int[] gridViewImageId = {
            R.drawable.wifi, R.drawable.airport_shuttle, R.drawable.nosmoking, R.drawable.family, R.drawable.restaurants

    };




    public AmenityListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try{

            View view = inflater.inflate(R.layout.fragment_amenity_list,container,false);


            return view;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CustomGridViewAdapter adapterViewAndroid = new CustomGridViewAdapter(getContext(), gridViewString, gridViewImageId);
        androidGridView=(GridView)view.findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                Toast.makeText(getContext(), "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();
            }
        });


    }

}
