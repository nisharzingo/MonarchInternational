package details.hotel.app.monarchint.UI.Activities;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import details.hotel.app.monarchint.Adapter.HotelImageAdapter;
import details.hotel.app.monarchint.Adapter.Pager;
import details.hotel.app.monarchint.Model.HotelDetails;
import details.hotel.app.monarchint.Model.HotelImage;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.UI.Fragments.AmenityListFragment;
import details.hotel.app.monarchint.UI.Fragments.GalleryFragment;
import details.hotel.app.monarchint.Utils.Constants;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ProgressBarUtil;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.HotelsDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutHotelActivity extends AppCompatActivity {

    private ViewPager viewPager_frag;
    TextView mHotelName,mHotelLocality;
    RecyclerView hotel_image_hr_recycler;
    private ImageView imageView;
    private TabLayout tabLayout;
    private ProgressBarUtil progressBarUtil;
    private HotelDetails aboutHotelModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            setContentView(R.layout.activity_about_hotel);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("About Hotel");

            progressBarUtil = new ProgressBarUtil(this);

            mHotelName = findViewById(R.id.hotel_name_about);
            mHotelLocality = findViewById(R.id.locality_about);
            imageView = findViewById(R.id.about_hotel_img);
            hotel_image_hr_recycler = (RecyclerView) findViewById(R.id.hotel_image_hr_recycler);
            hotel_image_hr_recycler.setLayoutManager(new LinearLayoutManager(AboutHotelActivity.this, LinearLayoutManager.HORIZONTAL, false));
            viewPager_frag = (ViewPager) findViewById(R.id.about_viewpager2);
            setupViewPager(viewPager_frag);

            tabLayout = (TabLayout) findViewById(R.id.about_tabs);
            tabLayout.setupWithViewPager(viewPager_frag);

            if(PreferenceHandler.getInstance(AboutHotelActivity.this).getHotelID()!=0){
                getAboutHotel(PreferenceHandler.getInstance(AboutHotelActivity.this).getHotelID());

            }else{
                getAboutHotel(Constants.HOTEL_DATA_ID);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setupViewPager(ViewPager viewPager) {

        Pager adapter = new Pager(getSupportFragmentManager());
        adapter.addFragment(new AmenityListFragment(), "Facilities");
        adapter.addFragment(new AmenityListFragment(), "Free");
        adapter.addFragment(new AmenityListFragment (), "Paid");
        viewPager.setAdapter(adapter);




    }


    private void getAboutHotel(final int HotelId) {
        progressBarUtil.showProgress("Loading...");
        HotelsDetailsAPI apiService = Util.getClient().create(HotelsDetailsAPI.class);
        Call<HotelDetails> call =  apiService.getHotelByHotelId("Basic emluZ290ZWFtOjk2MzI3MDA2ODU=",HotelId);
        call.enqueue(new Callback<HotelDetails>() {
            @Override
            public void onResponse(Call<HotelDetails> call, Response<HotelDetails> response) {
                int statusCode = response.code();

                if(statusCode == 200 || statusCode == 201 || statusCode == 203 || statusCode == 204) {
                    progressBarUtil.hideProgress();
                    aboutHotelModels = response.body();
                    if(aboutHotelModels!=null){

                        mHotelName.setText(""+aboutHotelModels.getHotelName());
                        mHotelLocality.setText(""+aboutHotelModels.getLocalty()+","+aboutHotelModels.getCity());

                        ArrayList<HotelImage> hotelImgModels = aboutHotelModels.getHotelImage();

                        if(hotelImgModels!=null&&hotelImgModels.size()!=0){

                            Picasso.with(getApplication()).load(hotelImgModels.get(0).getImages()).into(imageView);
                            HotelImageRecycler adapter = new HotelImageRecycler(AboutHotelActivity.this, hotelImgModels);
                            hotel_image_hr_recycler.setAdapter(adapter);
                        }

                    }



                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Failed due to: "+response.message(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<HotelDetails> call, Throwable t) {
                progressBarUtil.hideProgress();
            }
        });
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



                String base=imageObj.getImages();
                if(base != null && !base.isEmpty()){
                    Picasso.with(context).load(base).placeholder(R.drawable.no_image).
                            error(R.drawable.no_image).into(holder.mImage);


                }





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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            AboutHotelActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
