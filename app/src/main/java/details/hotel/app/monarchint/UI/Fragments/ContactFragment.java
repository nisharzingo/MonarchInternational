package details.hotel.app.monarchint.UI.Fragments;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

import details.hotel.app.monarchint.Customs.CustomFonts.TextViewRobotoregular;
import details.hotel.app.monarchint.Customs.CustomFonts.TextViewSFProDisplayRegular;
import details.hotel.app.monarchint.Model.HotelContacts;
import details.hotel.app.monarchint.Model.HotelDetails;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.Constants;
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
public class ContactFragment extends Fragment {

    TextViewSFProDisplayRegular mAddress,mHotelName,mPhoneWhatsapp;
    TextViewRobotoregular mPhoneText,mEmailText;
    LinearLayout mPhone,mEmail,mWhatsapp;

    String email,phone;

    private GoogleMap mMap;
    MapView mapView;

    Marker marker;


    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{
            View view = inflater.inflate(R.layout.fragment_contact,container,false);

            mAddress = (TextViewSFProDisplayRegular)view.findViewById(R.id.hotel_address);
            mHotelName = (TextViewSFProDisplayRegular)view.findViewById(R.id.hotel_name);
            mPhoneWhatsapp = (TextViewSFProDisplayRegular)view.findViewById(R.id.number_whatsapp);
            mPhoneText = (TextViewRobotoregular)view.findViewById(R.id.phone_text);
            mEmailText = (TextViewRobotoregular)view.findViewById(R.id.email_text);

            mPhone = (LinearLayout) view.findViewById(R.id.phone_number_lay);
            mEmail = (LinearLayout)view.findViewById(R.id.email_lay);
            mWhatsapp = (LinearLayout)view.findViewById(R.id.whatsapp_open);

            mapView = (MapView) view.findViewById(R.id.hotel_location_map_view);

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



                    }






                }
            });
            if(PreferenceHandler.getInstance(getActivity()).getHotelID()!=0){
                getHotel(PreferenceHandler.getInstance(getActivity()).getHotelID());
                getHotelContact(PreferenceHandler.getInstance(getActivity()).getHotelID());

            }else{
                getHotel(Constants.HOTEL_DATA_ID);
                getHotelContact(Constants.HOTEL_DATA_ID);
            }


            /*mPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(phone!=null&&!phone.isEmpty()){
                        if (ActivityCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:"+phone));
                            startActivity(callIntent);
                        }else{
                            Toast.makeText(getActivity(), "Permission denied.Please give access in Settings", Toast.LENGTH_SHORT).show();
                        }
                    }



                }


            });*/

            mEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(email!=null&&!email.isEmpty()){
                        Intent i = new Intent(Intent.ACTION_SENDTO);
                        i.setType("message/rfc822");
                        i.setData(Uri.parse("mailto:"));
                        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
                        i.putExtra(Intent.EXTRA_SUBJECT, "Inquiry");

                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                    }



                }
            });

            mWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String digits = "\\d+";
                    String mob_num = ""+phone;
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

                }
            });
           // getHotel(98);
            return view;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    private void getHotel(final int id){

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                //String auth_string = Util.getToken(getActivity());
                String auth_string = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI apiService = Util.getClient().create(HotelsDetailsAPI.class);
                Call<HotelDetails> call = apiService.getHotelByHotelId(auth_string,id);

                call.enqueue(new Callback<HotelDetails>() {
                    @Override
                    public void onResponse(Call<HotelDetails> call, Response<HotelDetails> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();
                        if (statusCode == 200) {

                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            final HotelDetails hotelDetails =  response.body();

                            if(hotelDetails != null)
                            {


                                mAddress.setText(hotelDetails.getHotelStreetAddress()+",\n"+hotelDetails.getCity()+",\n"+hotelDetails.getState()+"-"+hotelDetails.getPincode()+",\n"+hotelDetails.getCountry());
                                mHotelName.setText(hotelDetails.getHotelName());

                                String longi = PreferenceHandler.getInstance(getActivity()).getLongitude();
                                String lati = PreferenceHandler.getInstance(getActivity()).getLatitude();

                                if(longi!=null&&!longi.isEmpty()&&lati!=null&&!lati.isEmpty()){

                                }else{
                                    if(hotelDetails.getMaps()!=null&&hotelDetails.getMaps().size()!=0){

                                        mapView.getMapAsync(new OnMapReadyCallback() {
                                            @Override
                                            public void onMapReady(GoogleMap googleMap) {
                                                mMap = googleMap;


                                            /*if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                                // TODO: Consider calling

                                                return;
                                            }*/

                                                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                                mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();


                                                try{

                                                    mMap.clear();
                                                    LatLng latlng = new LatLng(Double.parseDouble(hotelDetails.getMaps().get((hotelDetails.getMaps().size()-1)).getLatitude()),Double.parseDouble(hotelDetails.getMaps().get((hotelDetails.getMaps().size()-1)).getLogitude()));
                                                    marker = mMap.addMarker(new MarkerOptions()
                                                            .position(latlng)
                                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                                                    CameraPosition cameraPosition = new CameraPosition.Builder().zoom(50).target(latlng).build();
                                                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                }


                                            }
                                        });


                                    }
                                }


                            }




                        }else {
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<HotelDetails> call, Throwable t) {
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }


        });
    }

    private void getHotelContact(final int id){



        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                //String auth_string = Util.getToken(getActivity());
                String auth_string = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI apiService = Util.getClient().create(HotelsDetailsAPI.class);
                Call<ArrayList<HotelContacts>> call = apiService.getConatctByHotelId(auth_string,id);

                call.enqueue(new Callback<ArrayList<HotelContacts>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HotelContacts>> call, Response<ArrayList<HotelContacts>> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();
                        if (statusCode == 200) {


                            ArrayList<HotelContacts> hotelDetails =  response.body();

                            if(hotelDetails != null&&hotelDetails.size()!=0)
                            {
                                HotelContacts dto = hotelDetails.get(0);

                                if(dto!=null){

                                    phone =dto.getHotelPhone();
                                    email =dto.getHotelEmail();
                                    mPhoneText.setText(""+dto.getHotelMobile());
                                    mEmailText.setText(""+dto.getHotelEmail());
                                    mPhoneWhatsapp.setText(""+phone);


                                }




                            }




                        }else {

                            Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<HotelContacts>> call, Throwable t) {

                        Log.e("TAG", t.toString());
                    }
                });
            }


        });
    }

}
