package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SliderAdapter  extends PagerAdapter {

    //Activity activity;

    Context context;
    int[] slideImagesList;
    private LayoutInflater inflater;


    public SliderAdapter(Context context, int[] slideImagesList)
    {
        this.context = context;
        this.slideImagesList = slideImagesList;


    }

    @Override
    public int getCount() {
        return slideImagesList.length;
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
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate( slideImagesList[position],container,false);

        container.addView(view);
        return view;


    }
}