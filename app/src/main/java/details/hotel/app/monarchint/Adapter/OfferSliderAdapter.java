package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.Offers;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.UI.Fragments.RoomsFragment;

public class OfferSliderAdapter extends PagerAdapter {

    //Activity activity;

    Context context;
    ArrayList<Offers> mList;
    private LayoutInflater inflater;


    public OfferSliderAdapter(Context context, ArrayList<Offers> mList)
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
        View view = inflater.inflate(R.layout.offer_slider_adapter,container,false);

        TextView mRestname = (TextView)view.findViewById(R.id.rest_name);

        ImageView mRestImage = (ImageView) view.findViewById(R.id.category_image);

        mRestname.setText(mList.get(position).getCouponCode());
        Picasso.with(context).load(mList.get(position).getOfferImage()).placeholder(R.drawable.no_image).
                error(R.drawable.no_image).into(mRestImage);



        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mList.get(position).getOfferType()!=null&&mList.get(position).getOfferType().equalsIgnoreCase("Hotel")){

                    Fragment roomsFragment = new RoomsFragment();
                    FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.hotel_fragment_view, roomsFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }


            }
        });


        container.addView(view);
        return view;


    }
}