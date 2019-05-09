package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.Foods;
import details.hotel.app.monarchint.R;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Foods> list;
    public FoodAdapter(Context context, ArrayList<Foods> list) {

        this.context = context;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_category_list, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try{
            final Foods dto = list.get(position);
            holder.display_name.setText(dto.getFoodName());
            holder.long_description.setText(dto.getDescription());


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

        public TextView display_name,long_description;
        public CardView card_category;
//

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            try{
                display_name = (TextView) itemView.findViewById(R.id.category_name);
                long_description = (TextView) itemView.findViewById(R.id.description);
                card_category = (CardView) itemView.findViewById(R.id.card_category);


            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

}
