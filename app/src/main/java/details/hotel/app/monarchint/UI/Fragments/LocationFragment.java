package details.hotel.app.monarchint.UI.Fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.HotelMaps;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.HotelsDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {

    private GoogleMap mMap;
    MapView mapView;
    ImageView mWhatsapp,mPhone;
    Marker marker;


    public LocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{
            View view = inflater.inflate(R.layout.fragment_location,container,false);

            mapView = (MapView) view.findViewById(R.id.hotel_location_map_view);

            mWhatsapp = (ImageView) view.findViewById(R.id.whatsapp_icon);
            mPhone = (ImageView) view.findViewById(R.id.phone_icon);


            final String whatsappNumber = PreferenceHandler.getInstance(getActivity()).getWhatsappNumber();

            mPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if(whatsappNumber!=null&&!whatsappNumber.isEmpty()){
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+whatsappNumber));
                        startActivity(intent);
                    }else{

                        Toast.makeText(getActivity(), "No Number to contcat", Toast.LENGTH_SHORT).show();
                    }



                }
            });

            mWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(whatsappNumber!=null&&!whatsappNumber.isEmpty()){
                        String digits = "\\d+";
                        String mob_num = ""+whatsappNumber;
                        if (mob_num.matches(digits))
                        {
                            try {

                                Uri uri = Uri.parse("whatsapp://send?phone=+91" + mob_num);
                                Intent i = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(i);
                            }
                            catch (ActivityNotFoundException e){
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "WhatsApp not installed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{

                        Toast.makeText(getActivity(), "No Number to contcat", Toast.LENGTH_SHORT).show();
                    }



                }
            });


            mapView.onCreate(savedInstanceState);
            mapView.onResume();

            try {
                MapsInitializer.initialize(getActivity());
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    mMap = googleMap;


                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                    String longi = PreferenceHandler.getInstance(getActivity()).getLongitude();
                    String lati = PreferenceHandler.getInstance(getActivity()).getLatitude();

                    if(longi!=null&&!longi.isEmpty()&&lati!=null&&!lati.isEmpty()){

                        try{

                            LatLng latlng = new LatLng(Double.parseDouble(lati),Double.parseDouble(longi));
                            LatLngBounds bounds = new LatLngBounds(latlng,latlng);
                            mMap.setLatLngBoundsForCameraTarget(bounds);

                            marker = mMap.addMarker(new MarkerOptions()
                                    .position(latlng)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                            CameraPosition cameraPosition = new CameraPosition.Builder().zoom(50).target(latlng).build();
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        }catch (Exception e){
                            e.printStackTrace();
                            mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();
                        }
                    }else{

                        try{
                            getHotelMaps();


                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }






                }
            });



            return view;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }



    public Bitmap convertToBitmap(Drawable drawable, int widthPixels, int heightPixels) {
        Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mutableBitmap);
        drawable.setBounds(0, 0, widthPixels, heightPixels);
        drawable.draw(canvas);

        return mutableBitmap;
    }

    public void getHotelMaps()
    {

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                // String authenticationString = Util.getToken(HotelOptionsScreen.this);//"Basic " +  Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI hotelOperation = Util.getClient().create(HotelsDetailsAPI.class);
                Call<ArrayList<HotelMaps>> response = hotelOperation.getHotelMapByHotelId(authenticationString,
                        PreferenceHandler.getInstance(getActivity()).getHotelID());

                response.enqueue(new Callback<ArrayList<HotelMaps>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HotelMaps>> call, Response<ArrayList<HotelMaps>> response) {
                        System.out.println("GetHotelByProfileId = "+response.code());
                        ArrayList<HotelMaps> hotelMaps = response.body();

                        if(response.code() == 200)
                        {
                            try{
                                if(hotelMaps != null && hotelMaps.size() != 0)
                                {



                                    mMap.clear();


                                    LatLng latlng = new LatLng(Double.parseDouble(hotelMaps.get((hotelMaps.size()-1)).getLatitude()),Double.parseDouble(hotelMaps.get((hotelMaps.size()-1)).getLogitude()));
                                    PreferenceHandler.getInstance(getActivity()).setLatitude(hotelMaps.get((hotelMaps.size()-1)).getLatitude());
                                    PreferenceHandler.getInstance(getActivity()).setLongitude(hotelMaps.get((hotelMaps.size()-1)).getLogitude());
                                    marker = mMap.addMarker(new MarkerOptions()
                                            .position(latlng)
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                    CameraPosition cameraPosition = new CameraPosition.Builder().zoom(50).target(latlng).build();
                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                                    //}
                                }
                                else
                                {



                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {

                        }


                    }

                    @Override
                    public void onFailure(Call<ArrayList<HotelMaps>> call, Throwable t) {


                    }
                });
            }
        });
    }

}
