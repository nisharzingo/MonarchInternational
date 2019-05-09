package details.hotel.app.monarchint.UI.Fragments;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import details.hotel.app.monarchint.Adapter.GalleryRowAdapter;
import details.hotel.app.monarchint.Model.HotelImage;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.Constants;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.HotelImagesAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GalleryRowFragment extends Fragment {

    private LinearLayout mNoPreview;
    private GridView imageGridView;
    ImageView mWhatsapp,mPhone;
    private ScrollView mScrollView;


    public GalleryRowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{
            View view = inflater.inflate(R.layout.fragment_gallery_row,container,false);
            imageGridView = (GridView) view.findViewById(R.id.gallery_image_grid);
            mNoPreview = (LinearLayout) view.findViewById(R.id.no_preview_lay);
            mScrollView = (ScrollView) view.findViewById(R.id.gallery_scroll_view);

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



            Bundle bun = getArguments();

            if(bun!=null){

                ArrayList<HotelImage> hotelImageList = (ArrayList<HotelImage>)bun.getSerializable("Image");

                if(hotelImageList!=null&&hotelImageList.size()!=0){

                    mNoPreview.setVisibility(View.GONE);
                    imageGridView.setVisibility(View.VISIBLE);

                    GalleryRowAdapter adapter = new GalleryRowAdapter(getActivity(),hotelImageList);
                    imageGridView.setAdapter(adapter);

                }else{
                    init();
                }

            }else{
                init();
            }



            return view;


        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
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

                                    Collections.sort(response.body(),HotelImage.compareHotelImageByOrder);

                                    ArrayList<HotelImage> hotelImages = response.body();

                                    ;

                                    if(hotelImages!=null&&hotelImages.size()!=0){

                                        mNoPreview.setVisibility(View.GONE);
                                        imageGridView.setVisibility(View.VISIBLE);

                                        GalleryRowAdapter adapter = new GalleryRowAdapter(getActivity(),hotelImages);
                                        imageGridView.setAdapter(adapter);

                                    }else{
                                        mNoPreview.setVisibility(View.VISIBLE);
                                        imageGridView.setVisibility(View.GONE);
                                    }
                                }
                                else
                                {

                                    mNoPreview.setVisibility(View.VISIBLE);
                                    imageGridView.setVisibility(View.GONE);

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }
                        else
                        {

                            mNoPreview.setVisibility(View.VISIBLE);
                            imageGridView.setVisibility(View.GONE);

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
                        mNoPreview.setVisibility(View.VISIBLE);
                        imageGridView.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    public void init(){
        if(PreferenceHandler.getInstance(getActivity()).getHotelID()!=0){
            getHotelImages(PreferenceHandler.getInstance(getActivity()).getHotelID());


        }else{
            getHotelImages(Constants.HOTEL_DATA_ID);

        }
    }
}
