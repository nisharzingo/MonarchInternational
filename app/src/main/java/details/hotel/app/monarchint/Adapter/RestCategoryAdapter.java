package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import details.hotel.app.monarchint.Model.RestaurantCategories;
import details.hotel.app.monarchint.R;

public class RestCategoryAdapter extends RecyclerView.Adapter<RestCategoryAdapter.ViewHolder>{

    private Context context;
    private ArrayList<RestaurantCategories> list;
    public RestCategoryAdapter(Context context, ArrayList<RestaurantCategories> list) {

        this.context = context;
        this.list = list;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rest_cate_layout, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try {
            RestaurantCategories dto = list.get(position);
            holder.display_name.setText(dto.getCategoriesName());
            holder.start_sell.setText(dto.getDescription());
            //getFoodByCategory(dto.getCategoriesId(),holder.mFoodList,holder.start_sell)

            String cateIma = dto.getCategoriesImage();

            if(cateIma!=null&&!cateIma.isEmpty()){
                Picasso.with(context).load(cateIma).placeholder(R.drawable.no_image).
                        error(R.drawable.no_image).into(holder.mCaeImg);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

        public TextView display_name,start_sell;
        ImageView mCaeImg;
        RecyclerView mFoodList;
//

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);
            try{
                display_name = (TextView) itemView.findViewById(R.id.rest_cate_name);
                start_sell = (TextView) itemView.findViewById(R.id.rest_cate_start_sel);
                mCaeImg = (ImageView) itemView.findViewById(R.id.category_image_list);
                mFoodList = (RecyclerView) itemView.findViewById(R.id.category_food_list);
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }

}
