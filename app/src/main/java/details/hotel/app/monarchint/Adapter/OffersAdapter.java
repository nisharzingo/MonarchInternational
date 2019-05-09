package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.Offers;
import details.hotel.app.monarchint.R;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHoler> {

    private ArrayList<Offers> mlist;
    private Context context;

    public OffersAdapter(Context context, ArrayList<Offers> mlist)
    {
        this.context = context;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public OffersViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_offer_new,parent,false);
        return new OffersViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHoler holder, int position) {

        holder.mType.setText(mlist.get(position).getOfferType());
        holder.mTitle.setText(mlist.get(position).getTitle());
        holder.mCode.setText(mlist.get(position).getCouponCode());
        holder.mDesc.setText(mlist.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class OffersViewHoler extends RecyclerView.ViewHolder {
        TextView mType, mTitle, mCode, mDesc;
        public OffersViewHoler(View view) {
            super(view);

             mType = (TextView)view.findViewById(R.id.offer_type);
             mTitle = (TextView)view.findViewById(R.id.offer_title);
             mCode = (TextView)view.findViewById(R.id.offer_coupon);
             mDesc = (TextView)view.findViewById(R.id.offer_description);
        }
    }
}
