package details.hotel.app.monarchint.UI.Fragments;


import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import details.hotel.app.monarchint.Adapter.RoomCategoriesListAdapter;
import details.hotel.app.monarchint.Model.HotelImage;
import details.hotel.app.monarchint.Model.RoomCategories;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.Constants;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.HotelImagesAPI;
import details.hotel.app.monarchint.WebAPI.HotelsDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomsFragment extends Fragment {

    RecyclerView mCategoryList;
    ImageView mWhatsapp,mPhone;

    public RoomsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        try{
            View view = inflater.inflate(R.layout.fragment_rooms,container,false);

            mCategoryList = (RecyclerView)view.findViewById(R.id.hotel_category_list);
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



            if(PreferenceHandler.getInstance(getActivity()).getHotelID()!=0){
                getRoomCategories(PreferenceHandler.getInstance(getActivity()).getHotelID());

            }else{
                getRoomCategories(Constants.HOTEL_DATA_ID);
            }


            return view;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void getRoomCategories(final int id)
    {

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                // String authenticationString = Util.getToken(HotelOptionsScreen.this);//"Basic " +  Base64.encodeToString(authentication.getBytes(), Base64.NO_WRAP);
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI hotelOperation = Util.getClient().create(HotelsDetailsAPI.class);
                Call<ArrayList<RoomCategories>> response = hotelOperation.getCategoriesByHotelId(authenticationString,
                        id);

                response.enqueue(new Callback<ArrayList<RoomCategories>>() {
                    @Override
                    public void onResponse(Call<ArrayList<RoomCategories>> call, Response<ArrayList<RoomCategories>> response) {
                        System.out.println("GetHotelByProfileId = "+response.code());
                        ArrayList<RoomCategories> hotelCategories = response.body();

                        if(response.code() == 200)
                        {
                            try{
                                if(hotelCategories != null && hotelCategories.size() != 0)
                                {


                                    RoomCategoriesListAdapter adapter = new RoomCategoriesListAdapter(getActivity(),hotelCategories);
                                    mCategoryList.setAdapter(adapter);



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
                    public void onFailure(Call<ArrayList<RoomCategories>> call, Throwable t) {


                    }
                });
            }
        });
    }

    public void getHotelImages(final int id)
    {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Please Loading");
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                HotelImagesAPI api = Util.getClient().create(HotelImagesAPI.class);
                // String auth = Util.getToken(HotelOptionsScreen.this);
                String auth = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                final Call<ArrayList<HotelImage>> HotelImagereaponse = api.getHotelImages(auth, id);

                HotelImagereaponse.enqueue(new Callback<ArrayList<HotelImage>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HotelImage>> call, Response<ArrayList<HotelImage>> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        if(response.code() == 200 || response.code() == 201)
                        {
                            try{
                                if(response.body() != null && response.body().size() != 0)
                                {

                                    ArrayList<String> hotelImages = new ArrayList<>();

                                    for (int i=0;i<response.body().size();i++)
                                    {

                                        hotelImages.add(response.body().get(i).getImages());
                                    }

                                    if(hotelImages!=null&&hotelImages.size()!=0){

                                    }
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
                    public void onFailure(Call<ArrayList<HotelImage>> call, Throwable t) {
                        System.out.println(t.getMessage());
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        Toast.makeText(getActivity(),"Please Check your data connection",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
