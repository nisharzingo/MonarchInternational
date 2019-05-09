package details.hotel.app.monarchint.UI.Resto;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import details.hotel.app.monarchint.Adapter.FoodCategoryAdapter;
import details.hotel.app.monarchint.Model.Foods;
import details.hotel.app.monarchint.Model.RestaurantCategories;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.RecyclerTouchListener;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.RestoCategoriesAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodListActivity extends AppCompatActivity {

    private RecyclerView food_list;
    private FoodCategoryAdapter adapter;
    private ArrayList<Foods> list;
    private ArrayList<RestaurantCategories> categoriesArrayList;
    private ProgressDialog progressDialog;

    View empty;

    int categoryId,subCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setContentView(R.layout.activity_food_list);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Food List");

            food_list = (RecyclerView) findViewById(R.id.food_list);

            empty = (View)findViewById(R.id.empty);
            getFoodList();



            food_list.addOnItemTouchListener(new RecyclerTouchListener(FoodListActivity.this, food_list, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    //showAlertBox(position);
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));



        }catch (Exception e){
            e.printStackTrace();
        }
    }




    private void getFoodList(){
        final ProgressDialog progressDialog = new ProgressDialog(FoodListActivity.this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //final int restId =  PreferenceHandler.getInstance(this).getHotelID();
        //final int restId =  1;


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                RestoCategoriesAPI apiService = Util.getClient().create(RestoCategoriesAPI.class);
                String authenticationString = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                Call<ArrayList<RestaurantCategories>> call = apiService.getCategoryByRestId(authenticationString, 3);

                call.enqueue(new Callback<ArrayList<RestaurantCategories>>() {
                    @Override
                    public void onResponse(Call<ArrayList<RestaurantCategories>> call, Response<ArrayList<RestaurantCategories>> response) {
                        int statusCode = response.code();
                        if (progressDialog!=null) {
                            progressDialog.dismiss();
                        }
                        if (statusCode == 200||statusCode == 201||statusCode == 204) {
                            try{
                                categoriesArrayList =  response.body();

//                            docList = list.get(1).getProfileList();

                                if(categoriesArrayList != null && categoriesArrayList.size() != 0)
                                {
                                   /* Collections.sort(list, new Comparator<Foods>() {
                                        @Override
                                        public int compare(Foods o1, Foods o2) {
                                            return o1.getFoodName().compareTo(o2.getFoodName());
                                        }
                                    });*/
                                    adapter = new FoodCategoryAdapter(FoodListActivity.this, categoriesArrayList);
                                    food_list.setAdapter(adapter);

                                }
                                else
                                {

                                }
//

                            }catch (Exception e){
                                e.printStackTrace();
                            }




                        }else if(statusCode==404){
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            empty.setVisibility(View.VISIBLE);

                        }else {

                            Toast.makeText(FoodListActivity.this, " failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
                        }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<RestaurantCategories>> call, Throwable t) {
                        // Log error here since request failed
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }


        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                FoodListActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
