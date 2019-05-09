package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.RestaurantImage;
import details.hotel.app.monarchint.R;

public class RestImageSlider extends PagerAdapter {

    //Activity activity;

    Context context;
    ArrayList<RestaurantImage> mList;
    private LayoutInflater inflater;


    public RestImageSlider(Context context, ArrayList<RestaurantImage> mList)
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
        View view = inflater.inflate(R.layout.adapter_hotel_images,container,false);
        ImageView hotel_img = (ImageView) view.findViewById(R.id.hotel_image);
        String img = mList.get(position).getImages();

        if(img!=null&&!img.isEmpty()){
            Picasso.with(context).load(img).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(hotel_img);
        }




        container.addView(view);
        return view;


    }
}