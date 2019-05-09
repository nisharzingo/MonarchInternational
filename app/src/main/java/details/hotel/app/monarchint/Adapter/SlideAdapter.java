package details.hotel.app.monarchint.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import details.hotel.app.monarchint.UI.Fragments.SlideOne;
import details.hotel.app.monarchint.UI.Fragments.SlideTwo;

public class SlideAdapter extends FragmentStatePagerAdapter {

    public SlideAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SlideOne tab1 = new SlideOne();
                return tab1;
            case 1:
                SlideTwo tab2 = new SlideTwo();
                return tab2;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
