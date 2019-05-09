package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.PaidAmenities;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.UI.Activities.AmenityBookingActivity;

public class AmenityAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PaidAmenities> mList = new ArrayList<>();

    public AmenityAdapter(Context context, ArrayList<PaidAmenities> mList)
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
    public View getView(final int pos, View view, ViewGroup viewGroup) {

        try{
            if(view == null)
            {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = mInflater.inflate(R.layout.adapter_amenities,viewGroup,false);
            }

            //<--- list main adapter---->
            TextView mAmenityName = (TextView)view.findViewById(R.id.description);
            ImageView mAmenityImage = (ImageView) view.findViewById(R.id.category_image);
            Button mBook = (Button) view.findViewById(R.id.book_category);


            if(mList.get(pos)!=null){


                mAmenityName.setText(""+mList.get(pos).getText());



                if(mList.get(pos).getImage()!=null){

                    Picasso.with(context).load(mList.get(pos).getImage()).placeholder(R.drawable.no_image).
                            error(R.drawable.no_image).into(mAmenityImage);
                }




                mBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent book = new Intent(context, AmenityBookingActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Amenity",mList.get(pos));
                        bundle.putString("ImageUrl",mList.get(pos).getImage());
                        book.putExtras(bundle);
                        context.startActivity(book);

                    }
                });



            }

            //<--- list main adapter---->


            return view;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }


    }
}
