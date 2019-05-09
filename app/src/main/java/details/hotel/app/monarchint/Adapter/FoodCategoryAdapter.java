package details.hotel.app.monarchint.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import details.hotel.app.monarchint.Model.Foods;
import details.hotel.app.monarchint.Model.RestaurantCategories;
import details.hotel.app.monarchint.Model.RestaurantSubCategories;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.Util;

public class FoodCategoryAdapter  extends RecyclerView.Adapter<FoodCategoryAdapter.ViewHolder>{

    private Context context;
    private ArrayList<RestaurantCategories> list;
    boolean visible;

    public FoodCategoryAdapter(Context context, ArrayList<RestaurantCategories> list) {

        this.context = context;
        this.list = list;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        try{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_food_by_category, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try{


            visible = false;
            final RestaurantCategories foodCategory = list.get(position);
            final ArrayList<RestaurantSubCategories> subCategoriesArrayList = new ArrayList<>();

            if(foodCategory!=null){

                holder.mCategoryName.setText(""+foodCategory.getCategoriesName());
                holder.mCategoryDesc.setText(""+foodCategory.getDescription());

                String categoryImage = foodCategory.getCategoriesImage();
                holder.mCategoryImage.setImageBitmap(Util.decodeBase64(categoryImage));
                holder.layout.setVisibility(View.GONE);

                if(foodCategory.getSubCategoriesList()!=null&&foodCategory.getSubCategoriesList().size()!=0){

                    for (RestaurantSubCategories subcategory:foodCategory.getSubCategoriesList()) {

                        subCategoriesArrayList.add(subcategory);

                    }

                    if(subCategoriesArrayList!=null&&subCategoriesArrayList.size()!=0){
                        SubCategoryAdapter foodMenuAdapter = new SubCategoryAdapter(context,subCategoriesArrayList);
                        holder.mFoodList.setAdapter(foodMenuAdapter);
                    }

                }



            }


            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(subCategoriesArrayList!=null&&subCategoriesArrayList.size()!=0){


                        if(holder.layout.getVisibility()==View.GONE){

                            holder.layout.setVisibility(View.VISIBLE);

                        }else {
                            holder.layout.setVisibility(View.GONE);
                        }

                    }else{
                        Toast.makeText(context, "No Foods in this category", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView mCategoryName,mCategoryDesc;
        ImageView mCategoryImage;
        FrameLayout mLayout;
        LinearLayout layout;
        RecyclerView mFoodList;




        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setClickable(true);

            mCategoryName = (TextView)itemView.findViewById(R.id.food_category_name);
            mCategoryDesc = (TextView)itemView.findViewById(R.id.food_category_desc);
            mCategoryImage = (ImageView) itemView.findViewById(R.id.food_category_image);
            mLayout = (FrameLayout) itemView.findViewById(R.id.category_layout);
            layout = (LinearLayout) itemView.findViewById(R.id.sub_layout);
            mFoodList = (RecyclerView) itemView.findViewById(R.id.submenu_list);



        }
    }
    public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

        private Context context;
        private ArrayList<RestaurantSubCategories> list;
        boolean click = false;

        public SubCategoryAdapter(Context context, ArrayList<RestaurantSubCategories> list)
        {
            this.context = context;
            this.list = list;
        }

        @Override
        public SubCategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            try{
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_submenu,parent,false);
                return new SubCategoryAdapter.ViewHolder(view);
            }catch (Exception e){
                e.printStackTrace();
                return  null;
            }

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            try{
                RestaurantSubCategories subCategories = list.get(position);


                if(subCategories != null)
                {
                    holder.mItemTitle.setText(subCategories.getSubCategoriesName());
                    // getFood(subCategories.getSubCategoriesId(),holder.mItemsGridview);

                    ArrayList<Foods> foodsArrayList = new ArrayList<>();
                    if(subCategories.getFoodList()!=null&&subCategories.getFoodList().size()!=0){

                        for (Foods food:subCategories.getFoodList()) {

                            foodsArrayList.add(food);
                        }

                        if(foodsArrayList!=null&&foodsArrayList.size()!=0){
                            Collections.sort(foodsArrayList, new Comparator<Foods>() {
                                @Override
                                public int compare(Foods o1, Foods o2) {
                                    return o1.getFoodName().compareTo(o2.getFoodName());
                                }
                            });
                            FoodAdapter adapter = new FoodAdapter(context,foodsArrayList);
                            holder.mItemsGridview.setAdapter(adapter);

                        }

                    }

                    holder.mItemTitle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(click){
                                holder.mItemsGridview.setVisibility(View.GONE);
                                click = false;
                            }else{
                                holder.mItemsGridview.setVisibility(View.VISIBLE);
                                click = true;
                            }
                        }
                    });

                }
            }catch (Exception e){
                e.printStackTrace();
            }



        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView mItemTitle;
            RecyclerView mItemsGridview;

            public ViewHolder(View itemView) {
                super(itemView);

                try{
                    mItemTitle = (TextView)itemView.findViewById(R.id.submenu_heading);
                    mItemsGridview = (RecyclerView)itemView.findViewById(R.id.foodmenu_list);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }


    }



}
