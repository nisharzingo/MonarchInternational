package details.hotel.app.monarchint.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import details.hotel.app.monarchint.UI.Fragments.ActiveBookingFragment;
import details.hotel.app.monarchint.UI.Fragments.CancelledBookingFragment;
import details.hotel.app.monarchint.UI.Fragments.CompletedBookingFragment;
import details.hotel.app.monarchint.UI.Fragments.UpcomingBookingFragment;

public class BookingHistoryViewPagerAdapter extends FragmentStatePagerAdapter {

    //String[] tabTitles = {"New Booking","Upcoming","All","Cancelled","Blocked Rooms"};
    String[] tabTitles = {"UPCOMING", "COMPLETED", "CANCEL","ACTIVE"};

    public BookingHistoryViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                UpcomingBookingFragment upcomingFragment = new UpcomingBookingFragment();
                return upcomingFragment;


            case 1:
                CompletedBookingFragment completedFragment = new CompletedBookingFragment();
                return completedFragment;

            case 2:
                CancelledBookingFragment cancelledFragment = new CancelledBookingFragment();
                return cancelledFragment;

            case 3:
                ActiveBookingFragment activefragment = new ActiveBookingFragment();
                return activefragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}