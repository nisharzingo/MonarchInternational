package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import details.hotel.app.monarchint.R;

public class OfferAdapter  extends PagerAdapter {

    Context context;
    ArrayList<String> activityImages;


    public OfferAdapter(Context context, ArrayList<String> activityImages)
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
    public void destroyItem(ViewGroup container, int position, Object object) {
        View removableView = (View) object;
        container.removeView(removableView);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.adapter_offer_design,container,false);
        ImageView hotel_img = (ImageView) view.findViewById(R.id.category_banner);
        String img = activityImages.get(position);

        if(img!=null&&!img.isEmpty()){
            Picasso.with(context).load(img).placeholder(R.drawable.no_image).
                    error(R.drawable.no_image).into(hotel_img);
        }



        container.addView(view);
        return view;


    }
}