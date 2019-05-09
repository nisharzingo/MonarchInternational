package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.NavBarItemWithIcon;
import details.hotel.app.monarchint.R;

public class NavAdapterWithIcon extends BaseAdapter {

    private Context context;
    private ArrayList<NavBarItemWithIcon> mList = new ArrayList<>();

    public NavAdapterWithIcon(Context context, ArrayList<NavBarItemWithIcon> mList)
    {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int pos) {
        return mList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.nav_item_with_icon,viewGroup,false);
        }

        TextView mTitle = (TextView) view.findViewById(R.id.title);
        ImageView mIcon = (ImageView) view.findViewById(R.id.icon);

        mTitle.setText(mList.get(pos).getTitle().toString());
        mIcon.setImageResource(mList.get(pos).getIcon());
        return view;
    }
}
