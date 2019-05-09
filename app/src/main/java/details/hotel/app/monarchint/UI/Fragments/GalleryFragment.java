package details.hotel.app.monarchint.UI.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import details.hotel.app.monarchint.Adapter.HotelGalleryAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {

    ViewPager hotelImagepager;
    Button mClose;

    RecyclerView hotel_image_hr_recycler;
    TextView mCaption;


    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        try{
            View view = inflater.inflate(R.layout.fragment_gallery,container,false);

            hotelImagepager = (ViewPager) view.findViewById(R.id.pager_image);
            mCaption = (TextView) view.findViewById(R.id.hoel_caption);

            hotel_image_hr_recycler = (RecyclerView)view.findViewById(R.id.hotel_image_hr_recycler);
            hotel_image_hr_recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));



            Bundle bun = getArguments();

            if(bun!=null){

                ArrayList<HotelImage> hotelImageList = (ArrayList<HotelImage>)bun.getSerializable("Image");
                int postion = bun.getInt("Position");
                if(hotelImageList!=null&&hotelImageList.size()!=0){

                    HotelGalleryAdapter activityImagesadapter = new HotelGalleryAdapter(getActivity(),hotelImageList);
                    hotelImagepager.setAdapter(activityImagesadapter);

                    HotelImageRecycler recyclerAdapter = new HotelImageRecycler(getActivity(),hotelImageList);
                    hotel_image_hr_recycler.setAdapter(recyclerAdapter);

                    if(postion!=0){

                        hotelImagepager.setCurrentItem(postion, true);
                    }
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

                                   /* for (int i=0;i<response.body().size();i++)
                                    {

                                        if(response.body().get(i).getImage()==null){

                                            hotelImages.add(response.body().get(i).getImages());
                                        }


                                    }*/

                                    if(hotelImages!=null&&hotelImages.size()!=0){
                                        HotelGalleryAdapter activityImagesadapter = new HotelGalleryAdapter(getActivity(),hotelImages);
                                        hotelImagepager.setAdapter(activityImagesadapter);

                                        HotelImageRecycler recyclerAdapter = new HotelImageRecycler(getActivity(),hotelImages);
                                        hotel_image_hr_recycler.setAdapter(recyclerAdapter);

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

    public void init(){
        if(PreferenceHandler.getInstance(getActivity()).getHotelID()!=0){
            getHotelImages(PreferenceHandler.getInstance(getActivity()).getHotelID());


        }else{
            getHotelImages(Constants.HOTEL_DATA_ID);

        }
    }

    public class HotelImageRecycler extends RecyclerView.Adapter<HotelImageRecycler.ViewHolder> {

        private Context context;
        private ArrayList<HotelImage> list;
        public HotelImageRecycler(Context context,ArrayList<HotelImage> list) {

            this.context = context;
            this.list = list;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            try{
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hotel_image_recycler_adapter, parent, false);
                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;

            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                return null;
            }

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            final HotelImage imageObj = list.get(position);

            if(imageObj!=null){

                mCaption.setText(""+list.get(0).getCaption());

                String base=imageObj.getImages();
                if(base != null && !base.isEmpty()){
                    Picasso.with(context).load(base).placeholder(R.drawable.no_image).
                            error(R.drawable.no_image).into(holder.mImage);


                }

                holder.mImageLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String cap = imageObj.getCaption();
                        hotelImagepager.setCurrentItem(position, true);

                        if(cap!=null&&!cap.isEmpty()){
                            mCaption.setText(""+cap);
                        }

                    }
                });



            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder  {

            ImageView mImage;
            LinearLayout mImageLayout;


//

            public ViewHolder(View itemView) {
                super(itemView);
                context = itemView.getContext();
                itemView.setClickable(true);

                mImageLayout = (LinearLayout) itemView.findViewById(R.id.image_layout);
                mImage = (ImageView) itemView.findViewById(R.id.hotel_image_adapter);


            }


        }


    }
}
