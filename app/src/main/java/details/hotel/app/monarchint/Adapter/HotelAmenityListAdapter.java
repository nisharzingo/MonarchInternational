package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.PaidAmenities;
import details.hotel.app.monarchint.R;

/**
 * Created by ZingoHotels Tech on 10-12-2018.
 */

public class HotelAmenityListAdapter extends RecyclerView.Adapter<HotelAmenityListAdapter.ViewHolder> {
    private Context context;
    ArrayList<PaidAmenities> amenityList;

    CheckBox item;

    public HotelAmenityListAdapter(Context context, ArrayList<PaidAmenities> amenityList) {

        this.context = context;
        this.amenityList = amenityList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_amenity_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        PaidAmenities feature = amenityList.get(position);


        holder.mAmenityName.setText(feature.getText());



    }

    @Override
    public int getItemCount() {
        return amenityList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

        public TextView mAmenityName;


        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);

            mAmenityName = (TextView) itemView.findViewById(R.id.txtName);



        }
    }
}