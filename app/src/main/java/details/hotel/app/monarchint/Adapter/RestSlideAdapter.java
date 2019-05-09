package details.hotel.app.monarchint.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import details.hotel.app.monarchint.Customs.CustomAdapters.AutoScrollImageAdapter;
import details.hotel.app.monarchint.Model.RestaurantImage;
import details.hotel.app.monarchint.Model.Restaurants;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.UI.Activities.RestaurantMenuScreen;

public class RestSlideAdapter  extends PagerAdapter {

    //Activity activity;

    Context context;
    ArrayList<Restaurants> mList;
    private LayoutInflater inflater;


    public RestSlideAdapter(Context context, ArrayList<Restaurants> mList)
    {
        this.context = context;
        this.mList = mList;


    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View removableView = (View) object;
        container.removeView(removableView);

    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.rest_viewpager,container,false);

        TextView mRestname = (TextView)view.findViewById(R.id.rest_name);
        TextView mRestLocation = (TextView)view.findViewById(R.id.description);
        AutoScrollImageAdapter imageSlider = (AutoScrollImageAdapter)view.findViewById(R.id.rest_images_scroller);
        ImageView mRestImage = (ImageView) view.findViewById(R.id.category_image);
        FrameLayout mMainLay = (FrameLayout) view.findViewById(R.id.frame_main);

        mRestname.setText(mList.get(position).getRestaurantName());
        mRestLocation.setText(mList.get(position).getCity());

        if(mList.get(position).getHotelImage()!=null&&mList.get(position).getHotelImage().size()!=0){

            Collections.sort(mList.get(position).getHotelImage(),RestaurantImage.compareRestImageByOrder);

            mRestImage.setVisibility(View.GONE);
            imageSlider.setVisibility(View.VISIBLE);
            RestImageSlider activityImagesadapter = new RestImageSlider(context,mList.get(position).getHotelImage());
            imageSlider.setAdapter(activityImagesadapter);


        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mnu = new Intent(context,RestaurantMenuScreen.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Resto",mList.get(position));
                mnu.putExtras(bundle);
                ((Activity)context).startActivity(mnu);
            }
        });



        container.addView(view);
        return view;


    }
}