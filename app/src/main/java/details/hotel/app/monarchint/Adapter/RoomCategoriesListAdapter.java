package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import details.hotel.app.monarchint.Customs.CustomAdapters.AutoScrollImageAdapter;
import details.hotel.app.monarchint.Model.AvailabiltyCheckPostData;
import details.hotel.app.monarchint.Model.HotelImage;
import details.hotel.app.monarchint.Model.Rates;
import details.hotel.app.monarchint.Model.RoomCategories;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.UI.Activities.BookAvailablityScreen;
import details.hotel.app.monarchint.Utils.Constants;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.HotelImagesAPI;
import details.hotel.app.monarchint.WebAPI.RateApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZingoHotels Tech on 11-12-2018.
 */

public class RoomCategoriesListAdapter extends RecyclerView.Adapter<RoomCategoriesListAdapter.ViewHolder> {

    Context context;
    ArrayList<RoomCategories> roomCategories;

    public RoomCategoriesListAdapter(Context context, ArrayList<RoomCategories> roomCategories)
    {
        this.context = context;
        this.roomCategories = roomCategories;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_room_category_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final RoomCategories roomCategory = roomCategories.get(position);

        if(roomCategory!=null){
            holder.mCategoryName.setText(roomCategories.get(position).getCategoryName());


            String desc = roomCategories.get(position).getDescription();

            if(desc!=null&&!desc.isEmpty()){

                holder.mDescription.setText(roomCategories.get(position).getDescription());

                int countLine= holder.mDescription.getLineHeight();
//                            System.out.println("LineCount " + countLine);
                if (countLine>=25){

                    holder.mReadMore.setVisibility(View.VISIBLE);
                    holder.mReadLess.setVisibility(View.GONE);
                }else{
                    holder.mReadMore.setVisibility(View.GONE);
                    holder.mReadLess.setVisibility(View.GONE);
                }

            }else{
                holder.mDescription.setVisibility(View.GONE);
                holder.mReadMore.setVisibility(View.GONE);
                holder.mReadLess.setVisibility(View.GONE);
            }

            holder.mReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.mReadMore.setVisibility(View.GONE);
                    holder.mReadLess.setVisibility(View.VISIBLE);
                    holder.mDescription.setMaxLines(Integer.MAX_VALUE);
                }
            });

            holder.mReadLess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.mReadMore.setVisibility(View.VISIBLE);
                    holder.mReadLess.setVisibility(View.GONE);
                    holder.mDescription.setMaxLines(1);

                }
            });


            getHotelImages(roomCategory.getHotelsId(),holder.mCategoryImage,roomCategory.getCategoryName(),holder.mImageUrl,holder.hotelImagesScroller);

            AvailabiltyCheckPostData avpd = new AvailabiltyCheckPostData();
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
            SimpleDateFormat sdfs = new SimpleDateFormat("MMM dd,yyyy");
            SimpleDateFormat sda = new SimpleDateFormat("MM/dd/yyyy");
            Calendar cal = Calendar.getInstance();
            String from = sdf.format(cal.getTime());
            String fromText = sdfs.format(cal.getTime());
            String fromDate = sda.format(cal.getTime());
            cal.add(Calendar.DATE,1);
            String to = sdf.format(cal.getTime());
            String toText = sdfs.format(cal.getTime());
            String toDate = sda.format(cal.getTime());


            if(PreferenceHandler.getInstance(context).getHotelID()!=0){
                avpd.setHotelId(PreferenceHandler.getInstance(context).getHotelID());


            }else{
                avpd.setHotelId(Constants.HOTEL_DATA_ID);

            }

            avpd.setFromDate(fromDate);
            avpd.setToDate(toDate);
            avpd.setRoomCategoryId(roomCategory.getRoomCategoryId());
            getRatesByCategoryId(avpd,holder.mSell,holder.mDispl,holder.mDisc);



            holder.mBookNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent book = new Intent(context, BookAvailablityScreen.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Category",roomCategory);
                    bundle.putString("ImageUrl",holder.mImageUrl.getText().toString());
                    book.putExtras(bundle);
                    context.startActivity(book);
                }
            });

        }else{

        }





    }

    @Override
    public int getItemCount() {
        return roomCategories.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mCategoryName,mDescription,mImageUrl,mSell,mDispl,mDisc,mReadMore,mReadLess;
        ImageView mCategoryImage;
        AutoScrollImageAdapter hotelImagesScroller;
        Button mBookNow;
        public ViewHolder(View itemView) {
            super(itemView);
            mCategoryName = (TextView)itemView.findViewById(R.id.category_name);
            mDescription = (TextView) itemView.findViewById(R.id.description);
            mImageUrl = (TextView) itemView.findViewById(R.id.image_url);
            mSell = (TextView) itemView.findViewById(R.id.room_price_dis);
            mDispl = (TextView) itemView.findViewById(R.id.room_price_dis_sell);
            mDisc = (TextView) itemView.findViewById(R.id.dis_sell);
            mReadMore = (TextView) itemView.findViewById(R.id.read_more);
            mReadLess = (TextView) itemView.findViewById(R.id.read_less);
            mCategoryImage = (ImageView) itemView.findViewById(R.id.category_image);
            mBookNow = (Button) itemView.findViewById(R.id.book_category);
            hotelImagesScroller = (AutoScrollImageAdapter) itemView.findViewById(R.id.hotel_images_scroller);
            hotelImagesScroller.setStopScrollWhenTouch(true);

            hotelImagesScroller.setClipToPadding(false);
            hotelImagesScroller.setPageMargin(18);
            hotelImagesScroller.setVisibility(View.GONE);
        }
    }

    private void getRatesByCategoryId(final int categoryId, final TextView sellRate){

        RateApi apiService = Util.getClient().create(RateApi.class);
        //String authenticationString = Util.getToken(context);
        String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
        Call<ArrayList<Rates>> call = apiService.getRatesByCategoryId(authenticationString,categoryId);

        call.enqueue(new Callback<ArrayList<Rates>>() {
            @Override
            public void onResponse(Call<ArrayList<Rates>> call, Response<ArrayList<Rates>> response) {
                try {
                    int statusCode = response.code();

                    if (statusCode == 200||statusCode==201||statusCode==204) {

                        if(response.body()!=null&&response.body().size()!=0){
                            sellRate.setText("₹ "+response.body().get(0).getSellRateForSingle()+"/-");
                        }




                    }else {

                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rates>> call, Throwable t) {
                // Log error here since request failed

                Log.e("TAG", t.toString());
            }
        });


    }

    public void getHotelImages(final int id,final ImageView imageView,final String name,final TextView imageUrl,final AutoScrollImageAdapter hotelImagesScroller)
    {


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

                        if(response.code() == 200 || response.code() == 201)
                        {
                            try{
                                if(response.body() != null && response.body().size() != 0)
                                {

                                    ArrayList<HotelImage> hotelImages = new ArrayList<>();

                                    for (int i=0;i<response.body().size();i++)
                                    {

                                       if(response.body().get(i).getCaption().equalsIgnoreCase(name)){

                                           String img = response.body().get(i).getImages();
                                           hotelImages.add(response.body().get(i));

                                           if(img!=null&&!img.isEmpty()){
                                               Picasso.with(context).load(img).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(imageView);
                                               imageUrl.setText(""+img);
                                           }

                                       }
                                    }

                                    if(hotelImages!=null&&hotelImages.size()!=0){
                                        imageView.setVisibility(View.GONE);
                                        hotelImagesScroller.setVisibility(View.VISIBLE);
                                        HotelImageAdapter activityImagesadapter = new HotelImageAdapter(context,hotelImages);
                                        hotelImagesScroller.setAdapter(activityImagesadapter);
                                        /*  mLoader.setVisibility(View.GONE);*/

                                    }else{

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

                    }
                });
            }
        });
    }

    private void getRatesByCategoryId(final AvailabiltyCheckPostData avpd,final TextView mPrice,final TextView mDispl,final TextView dis){

        RateApi apiService = Util.getClient().create(RateApi.class);
        //String authenticationString = Util.getToken(context);
        String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
        Call<ArrayList<Rates>> call = apiService.getRatesForDate(authenticationString,avpd);

        call.enqueue(new Callback<ArrayList<Rates>>() {
            @Override
            public void onResponse(Call<ArrayList<Rates>> call, Response<ArrayList<Rates>> response) {
                try {
                    int statusCode = response.code();

                    if (statusCode == 200||statusCode==201||statusCode==204) {

                        if(response.body()!=null&&response.body().size()!=0){
                            mPrice.setText("₹ "+response.body().get(0).getSellRateForSingle()+"/per night *");
                            mDispl.setText("₹ "+response.body().get(0).getDeclaredRateForSingle()+"/per night *");

                            int sell = response.body().get(0).getSellRateForSingle();
                            int displ = response.body().get(0).getDeclaredRateForSingle();
                            int disc = displ - sell;

                            dis.setText("Save ₹ "+disc);
                        }




                    }else {

                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Rates>> call, Throwable t) {
                // Log error here since request failed

                Log.e("TAG", t.toString());
            }
        });


    }

}
