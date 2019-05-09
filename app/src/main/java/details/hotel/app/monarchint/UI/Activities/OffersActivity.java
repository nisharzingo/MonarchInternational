package details.hotel.app.monarchint.UI.Activities;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import details.hotel.app.monarchint.Adapter.OfferNewAdapter;
import details.hotel.app.monarchint.Adapter.OffersAdapter;
import details.hotel.app.monarchint.Model.Offers;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.UI.Resto.FoodListActivity;
import details.hotel.app.monarchint.Utils.Constants;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.OfferAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersActivity extends AppCompatActivity {

     RecyclerView recyclerView;
    Timer timer;
    final long DELAY_MS = 2000;
    final long PERIOD_MS = 7000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Offer List");

        recyclerView = findViewById(R.id.offers_recycler);
        getOffersByHotelId();
    }

    public void getOffersByHotelId()
    {
        final ProgressDialog dialog = new ProgressDialog(OffersActivity.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        //restId = 0;
        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {

                OfferAPI hotelOperation = Util.getClient().create(OfferAPI.class);
                Call<ArrayList<Offers>> response = hotelOperation.getOffersByHotelId("Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==", Constants.HOTEL_DATA_ID);

                response.enqueue(new Callback<ArrayList<Offers>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Offers>> call, Response<ArrayList<Offers>> response) {
                        System.out.println("GetHotelByProfileId = "+response.code());
                        final ArrayList<Offers> restDetailseResponse = response.body();

                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                        if(response.code() == 200)
                        {
                            try{
                                if(restDetailseResponse != null && restDetailseResponse.size() != 0)
                                {
                                    recyclerView.setVisibility(View.VISIBLE);

                                    final OffersAdapter adapter = new OffersAdapter(OffersActivity.this,restDetailseResponse);
                                    recyclerView.setAdapter(adapter);


                                }
                                else
                                {
                                    recyclerView.setVisibility(View.GONE);
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                        else
                        {
                            recyclerView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Offers>> call, Throwable t) {
                        System.out.println("Failed");
                        System.out.println(" Exception = "+t.getMessage());
                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                        recyclerView.setVisibility(View.GONE);
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
                OffersActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
