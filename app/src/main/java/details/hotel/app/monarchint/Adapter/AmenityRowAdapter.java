package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.Amenities;
import details.hotel.app.monarchint.R;

public class AmenityRowAdapter extends BaseAdapter {

    Context context;
    ArrayList<Amenities> amenities;


    public AmenityRowAdapter(Context context, ArrayList<Amenities> amenities)
    {
        this.context = context;
        this.amenities = amenities;
    }
    @Override
    public int getCount() {
        //System.out.println("class SelectRoomGridViewAdapter = "+rooms.size());

        return amenities.size();

    }

    @Override
    public Object getItem(int position) {
        return amenities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.amenity_adapter_list,parent,false);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.amenity_image);
        TextView name = (TextView) convertView.findViewById(R.id.amenity_name);

        Amenities amenity = amenities.get(position);


        if(amenity!=null){

            String img = amenity.getDescription();
            name.setText(""+amenity.getAmenitiesName());

            if(img!=null&&!img.isEmpty()){
                Picasso.with(context).load(img).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(image);
            }

        }

        return convertView;
    }
}

