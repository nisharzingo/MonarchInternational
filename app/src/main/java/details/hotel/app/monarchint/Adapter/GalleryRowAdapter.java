package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.HotelImage;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.UI.Fragments.GalleryFragment;

public class GalleryRowAdapter extends BaseAdapter {

    Context context;
    ArrayList<HotelImage> images;


    public GalleryRowAdapter(Context context, ArrayList<HotelImage> images)
    {
        this.context = context;
        this.images = images;
    }
    @Override
    public int getCount() {
        //System.out.println("class SelectRoomGridViewAdapter = "+rooms.size());

        return images.size();

    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
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
            convertView = inflater.inflate(R.layout.gallery_room_adapter,parent,false);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.image_gallery);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.gallery_layout);

        String img = images.get(position).getImages();

        if(img!=null&&!img.isEmpty()){
            Picasso.with(context).load(img).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(image);
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putSerializable("Image", images);
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
        return convertView;
    }
}
