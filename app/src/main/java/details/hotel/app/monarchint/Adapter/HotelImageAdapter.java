package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.HotelImage;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.UI.Fragments.GalleryFragment;

/**
 * Created by ZingoHotels Tech on 10-12-2018.
 */

public class HotelImageAdapter extends PagerAdapter {

    Context context;
    ArrayList<HotelImage> activityImages;


    public HotelImageAdapter(Context context, ArrayList<HotelImage> activityImages)
    {
        this.context = context;
        this.activityImages = activityImages;


    }

    @Override
    public int getCount() {
        return activityImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container,int position, Object object) {
        View removableView = (View) object;
        container.removeView(removableView);

    }

    @Override
    public Object instantiateItem(ViewGroup container,  final int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adapter_hotel_images,container,false);
        ImageView hotel_img = (ImageView) view.findViewById(R.id.hotel_image);
        String img = activityImages.get(position).getImages();

        if(img!=null&&!img.isEmpty()){
            Picasso.with(context).load(img).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(hotel_img);
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putSerializable("Image", activityImages);
                bundle.putInt("Position",position);
                FragmentTransaction transaction =  ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                Fragment galleryFragment = new GalleryFragment();
                galleryFragment.setArguments(bundle);
                transaction.replace(R.id.hotel_fragment_view,galleryFragment)
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .addToBackStack(null)
                        .commit();

            }
        });

        container.addView(view);
        return view;


    }
}