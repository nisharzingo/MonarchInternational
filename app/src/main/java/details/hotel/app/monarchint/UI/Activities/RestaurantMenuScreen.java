package details.hotel.app.monarchint.UI.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

import details.hotel.app.monarchint.Adapter.RestImageSlider;
import details.hotel.app.monarchint.Customs.CustomAdapters.AutoScrollImageAdapter;
import details.hotel.app.monarchint.Customs.CustomFonts.TextViewRobotoregular;
import details.hotel.app.monarchint.Model.EmailData;
import details.hotel.app.monarchint.Model.Foods;
import details.hotel.app.monarchint.Model.RestaurantCategories;
import details.hotel.app.monarchint.Model.RestaurantImage;
import details.hotel.app.monarchint.Model.Restaurants;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.RestoUtil;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.CategoriesApi;
import details.hotel.app.monarchint.WebAPI.SendEmailAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurantMenuScreen extends AppCompatActivity  implements PaymentResultListener {

    //dynamic field
    TextView mRestName,mDescription;
    ImageView mRestImage;
    RecyclerView category_list;
    LinearLayout mTotalLay;
    TextViewRobotoregular mTotalAmt,mOrder;

   /* Button mBook,mNonVegBook,mVegBook;
    TextView mBuffet,mVeg,mNonVeg;
    LinearLayout mBuffetlay,mveglay,mnonveglay;*/
    String type = "";
    AutoScrollImageAdapter imageSlider;

    Restaurants restaurants;

    int currentPage = 0,start = 0,end = 0;
    Timer timer;
    final long DELAY_MS = 2000;
    final long PERIOD_MS = 3000;

    double total=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{

            setContentView(R.layout.acctivity_rest_detail_new);

            mRestName = (TextView)findViewById(R.id.category_name);
            mDescription = (TextView)findViewById(R.id.description);
            imageSlider = (AutoScrollImageAdapter)findViewById(R.id.rest_images_scroller);
            mRestImage = (ImageView) findViewById(R.id.category_image);
            category_list = (RecyclerView) findViewById(R.id.category_list);
            mTotalLay = (LinearLayout) findViewById(R.id.total_pay);
            mTotalAmt = (TextViewRobotoregular) findViewById(R.id.txttotal);
            mOrder = (TextViewRobotoregular) findViewById(R.id.txtorder);

            /*mBook = (Button)findViewById(R.id.book_mennu);
            mVegBook = (Button)findViewById(R.id.book_mennu_veg);
            mNonVegBook = (Button)findViewById(R.id.book_mennu_non);



            mBuffet = (TextView)findViewById(R.id.show_menu);
            mVeg = (TextView)findViewById(R.id.show_menu_veg);
            mNonVeg = (TextView)findViewById(R.id.show_menu_non);

            mBuffetlay = (LinearLayout)findViewById(R.id.buffet_menu);
            mnonveglay = (LinearLayout)findViewById(R.id.non_veg_menu);
            mveglay = (LinearLayout)findViewById(R.id.veg_menu);*/


            Bundle bundle = getIntent().getExtras();


            if(bundle!=null){

                restaurants = (Restaurants)bundle.getSerializable("Resto");
            }

            if(restaurants!=null){

                mRestName.setText(""+restaurants.getRestaurantName());
                mDescription.setText(""+restaurants.getDisplayName());
                getCategoryList(restaurants.getRestaurantId());

                if(restaurants.getHotelImage()!=null&&restaurants.getHotelImage().size()!=0){

                    Collections.sort(restaurants.getHotelImage(),RestaurantImage.compareRestImageByOrder);

                    mRestImage.setVisibility(View.GONE);
                    imageSlider.setVisibility(View.VISIBLE);
                    RestImageSlider activityImagesadapter = new RestImageSlider(RestaurantMenuScreen.this,restaurants.getHotelImage());
                    imageSlider.setAdapter(activityImagesadapter);

                    imageSlider.setClipToPadding(false);
                                    /*restScroller.setPageMargin(10);
                                    restScroller.setPadding(60,0,60,0);*/


                    final Handler handler = new Handler();
                    final Runnable Update = new Runnable() {
                        public void run() {
                            if (currentPage == restaurants.getHotelImage().size() && start == 0) {
                                currentPage = --currentPage;
                                start = 1;
                                end = 0;
                            }else if(currentPage < restaurants.getHotelImage().size() && currentPage != 0 && end == 0&& start == 1){
                                currentPage = --currentPage;
                            }else if(currentPage == 0 && end == 0 && start == 1){
                                currentPage = 0;
                                end = 1;
                                start = 0;
                            }else if(currentPage <= restaurants.getHotelImage().size()&& start == 0){

                                currentPage = ++currentPage;
                            }else if(currentPage == 0&& start == 0){

                                currentPage = ++currentPage;
                            }else{

                            }
                            imageSlider.setCurrentItem(currentPage, true);

                        }
                    };

                    timer = new Timer(); // This will create a new Thread
                    timer .schedule(new TimerTask() { // task to be scheduled

                        @Override
                        public void run() {
                            handler.post(Update);
                        }
                    }, DELAY_MS, PERIOD_MS);


                }

            }

            mOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(total>=0){

                        startPayment();

                    }
                }
            });


           /* mBuffet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(mBuffetlay.getVisibility()==View.VISIBLE){

                        mBuffetlay.setVisibility(View.GONE);
                    }else if(mBuffetlay.getVisibility()==View.GONE){
                        mBuffetlay.setVisibility(View.VISIBLE);
                    }
                }
            });

            mVeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(mveglay.getVisibility()==View.VISIBLE){

                        mveglay.setVisibility(View.GONE);
                    }else if(mveglay.getVisibility()==View.GONE){
                        mveglay.setVisibility(View.VISIBLE);
                    }
                }
            });

            mNonVeg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(mnonveglay.getVisibility()==View.VISIBLE){

                        mnonveglay.setVisibility(View.GONE);
                    }else if(mnonveglay.getVisibility()==View.GONE){
                        mnonveglay.setVisibility(View.VISIBLE);
                    }
                }
            });

            mBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    type = "Buff";
                    startPayment();
                }
            });
            mVegBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type = "Veg";
                    startPayment();
                }
            });

            mNonVegBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    type = "Non";
                    startPayment();
                }
            });*/

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
        double amount=0;
        double per = 0;

        try {
            JSONObject options = new JSONObject();

            //int amount = Integer.parseInt(mHotelTotalCharges.getText().toString());
            options.put("name", restaurants.getRestaurantName());
            options.put("description", "For  Ordering Food");

            //You can omit the image option to fetch the image from dashboard
            //options.put("image", R.drawable.app_logo);
            options.put("currency", "INR");
            //options.put("amount",amount * 100);

            options.put("amount",(int)total* 100);



            JSONObject preFill = new JSONObject();
            preFill.put("email", "");
            preFill.put("contact","");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }



    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {

            //addPayment();
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();


            String body = "<html><head><style>table " +
                    "{border-collapse: collapse;}" +
                    "table, td, th {" +
                    "border: 1px solid black;} table th{\n" +
                    "  color:#0000ff;\n" +
                    "}" +
                    "</style></head>" +
                    "<body>" +
                    "<h2>Dear Team Members,</h2>" +
                    "<p><br>You got order for food "+type+"payment id "+razorpayPaymentID+"</p></br></br>"+

                    "</br><p><b>Thanks</b></p><br><br></body></html>";

            EmailData emailData = new EmailData();
            emailData.setEmailAddress(PreferenceHandler.getInstance(RestaurantMenuScreen.this).getEmailList()+",abhinav@zingohotels.com,nishar@zingohotels.com");
            emailData.setBody(body);
            emailData.setSubject(""+restaurants.getRestaurantName()+" Orders");
            emailData.setUserName("nishar@zingohotels.com");
            emailData.setPassword("Razin@1993");
            emailData.setFromName("Nishar");
            emailData.setFromEmail("nishar@zingohotels.com");

            if(Util.isNetworkAvailable(RestaurantMenuScreen.this)){
                sendEmailAutomatic(emailData);
            }else{
                Toast.makeText(this, "Please check your Internet Connection", Toast.LENGTH_LONG).show();
            }

            //addTraveler();
        } catch (Exception e) {
            //  Log.e(TAG, "Exception in onPaymentSuccess", e);
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendEmailAutomatic(final EmailData emailData) {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Sending Email..");
        dialog.setCancelable(false);
        dialog.show();


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                SendEmailAPI travellerApi = RestoUtil.getClient().create(SendEmailAPI.class);
                Call<String> response = travellerApi.sendEmail(emailData);

                response.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {


                        try{

                            if(dialog != null && dialog.isShowing())
                            {
                                dialog.dismiss();
                            }
                            System.out.println(response.code());
                            if(response.code() == 200||response.code() == 201)
                            {
                                if(response.body() != null)
                                {


                                    if(response.body().equalsIgnoreCase("Email Sent Successfully")){
                                       RestaurantMenuScreen.this.finish();
                                        Toast.makeText(RestaurantMenuScreen.this, "Order Placed", Toast.LENGTH_SHORT).show();
                                    }else{
                                        //Toast.makeText(ContactUsScreen.this, "Something went wrong. So please contact through Call", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }else{
                               // Toast.makeText(ContactUsScreen.this, "Something went wrong due to "+response.code()+". So please contact through Call", Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            if(dialog != null && dialog.isShowing())
                            {
                                dialog.dismiss();
                            }
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        if(dialog != null && dialog.isShowing())
                        {
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    private void getCategoryList(final int restId){
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMenuScreen.this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //final int restId =  PreferenceHandler.getInstance(this).getHotelID();


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                CategoriesApi apiService = Util.getClient().create(CategoriesApi.class);
                String authenticationString = Util.getToken(RestaurantMenuScreen.this);
                Call<ArrayList<RestaurantCategories>> call = apiService.getCategoryByRestId(authenticationString,restId)/*getRooms()*/;

                call.enqueue(new Callback<ArrayList<RestaurantCategories>>() {
                    @Override
                    public void onResponse(Call<ArrayList<RestaurantCategories>> call, Response<ArrayList<RestaurantCategories>> response) {
                        int statusCode = response.code();
                        if (progressDialog!=null) {
                            progressDialog.dismiss();
                        }
                        if (statusCode == 200||statusCode == 201||statusCode == 204) {
                            try{
                                ArrayList<RestaurantCategories> list =  response.body();



                                if(list != null && list.size() != 0)
                                {
                                    Collections.sort(list, new Comparator<RestaurantCategories>() {
                                        @Override
                                        public int compare(RestaurantCategories o1, RestaurantCategories o2) {
                                            return o1.getCategoriesName().compareTo(o2.getCategoriesName());
                                        }
                                    });
                                    RestCategoryAdapter adapter = new RestCategoryAdapter(RestaurantMenuScreen.this, list);
                                    category_list.setAdapter(adapter);

                                }
                                else
                                {
                                    Toast.makeText(RestaurantMenuScreen.this, "No Food Menu", Toast.LENGTH_SHORT).show();
                                }
//

                            }catch (Exception e){
                                e.printStackTrace();
                            }




                        }else if(statusCode==404){
                            if (progressDialog!=null)
                                progressDialog.dismiss();

                        }else {

                            Toast.makeText(RestaurantMenuScreen.this, " failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
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
        public void onBindViewHolder(final ViewHolder holder, int position) {

            try {
                RestaurantCategories dto = list.get(position);
                holder.display_name.setText(dto.getCategoriesName());

                getFoodByCategory(dto.getCategoriesId(),holder.mFoodList,holder.start_sell,holder.dispay_item,holder.mShow);
                String cateIma = dto.getCategoriesImage();

                if(cateIma!=null&&!cateIma.isEmpty()){
                    Picasso.with(context).load(cateIma).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(holder.mCaeImg);
                }

                holder.mShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(holder.mFoodList.getVisibility()==View.GONE){
                            holder.mFoodList.setVisibility(View.VISIBLE);
                            holder.mShow.setText("Hide Menu");
                        }else{
                            holder.mFoodList.setVisibility(View.GONE);
                            holder.mShow.setText("Show Menu");
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


        class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

            public TextView display_name,start_sell,dispay_item;
            ImageView mCaeImg;
            RecyclerView mFoodList;
            Button mShow;
//

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setClickable(true);
                try{
                    display_name = (TextView) itemView.findViewById(R.id.rest_cate_name);
                    start_sell = (TextView) itemView.findViewById(R.id.rest_cate_start_sel);
                    dispay_item = (TextView) itemView.findViewById(R.id.rest_cate_start_displa);
                    mCaeImg = (ImageView) itemView.findViewById(R.id.category_image_list);
                    mFoodList = (RecyclerView) itemView.findViewById(R.id.category_food_list);
                    mShow = (Button) itemView.findViewById(R.id.book_mennu);
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }

    }

    private void getFoodByCategory(final int restId,final RecyclerView foodList,final TextView mstart,final TextView mDisp,final Button mShow){
        final ProgressDialog progressDialog = new ProgressDialog(RestaurantMenuScreen.this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //final int restId =  PreferenceHandler.getInstance(this).getHotelID();


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                CategoriesApi apiService = Util.getClient().create(CategoriesApi.class);
                String authenticationString = Util.getToken(RestaurantMenuScreen.this);
                Call<ArrayList<Foods>> call = apiService.getFoodByCate(authenticationString,restId)/*getRooms()*/;

                call.enqueue(new Callback<ArrayList<Foods>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Foods>> call, Response<ArrayList<Foods>> response) {
                        int statusCode = response.code();
                        if (progressDialog!=null) {
                            progressDialog.dismiss();
                        }
                        if (statusCode == 200||statusCode == 201||statusCode == 204) {
                            try{
                                ArrayList<Foods> list =  response.body();



                                if(list != null && list.size() != 0)
                                {
                                    Collections.sort(list, new Comparator<Foods>() {
                                        @Override
                                        public int compare(Foods o1, Foods o2) {
                                            return o1.getFoodName().compareTo(o2.getFoodName());
                                        }
                                    });

                                    mstart.setText("Starts at ₹ "+ list.get(0).getSellingPrice());

                                    if(list.size()>1){
                                        mDisp.setText(list.size()+" items are available");
                                    }else if(list.size()==1){
                                        mDisp.setText(list.size()+" item is available");
                                    }

                                    FoodAdapter adapter = new FoodAdapter(RestaurantMenuScreen.this, list);
                                    foodList.setAdapter(adapter);

                                }
                                else
                                {
                                    mDisp.setText("No items");
                                    mShow.setText("SOLD OUT");
                                    mShow.setBackgroundColor(Color.RED);
                                    mShow.setEnabled(false);
                                    Toast.makeText(RestaurantMenuScreen.this, "No Food Menu", Toast.LENGTH_SHORT).show();
                                }
//

                            }catch (Exception e){
                                e.printStackTrace();
                            }




                        }else if(statusCode==404){
                            if (progressDialog!=null)
                                progressDialog.dismiss();

                        }else {

                            Toast.makeText(RestaurantMenuScreen.this, " failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
                        }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Foods>> call, Throwable t) {
                        // Log error here since request failed
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }


        });
    }


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
                        .inflate(R.layout.rest_food_add, parent, false);
                ViewHolder viewHolder = new ViewHolder(v);
                return viewHolder;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            try {
                final Foods dto = list.get(position);
                holder.display_name.setText(""+dto.getFoodName());
                holder.start_sell.setText("₹ "+dto.getSellingPrice());
                holder.disp_price.setText("₹ "+dto.getDisplayPrice());

                if(dto.getFoodImages()!=null&&dto.getFoodImages().size()!=0){
                    String cateIma = dto.getFoodImages().get(0).getImages();

                    if(cateIma!=null&&!cateIma.isEmpty()){
                        Picasso.with(context).load(cateIma).placeholder(R.drawable.no_image).
                                error(R.drawable.no_image).into(holder.mCaeImg);
                    }
                }

                holder.mAddFood.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                       holder.mAddFood.setVisibility(View.GONE);
                       holder.mFoodLayout.setVisibility(View.VISIBLE);
                       mTotalLay.setVisibility(View.VISIBLE);
                       holder.mFoodCount.setText("1");
                       total = total+dto.getSellingPrice();
                       mTotalAmt.setText("TOTAL : ₹ "+total);

                    }
                });

                holder.mAddItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int valueFood = Integer.parseInt(holder.mFoodCount.getText().toString());
                        holder.mFoodCount.setText((valueFood+1)+"");
                        total = total+dto.getSellingPrice();
                        mTotalAmt.setText("TOTAL : ₹ "+total);



                    }
                });

                holder.mRemoveItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int valueFood = Integer.parseInt(holder.mFoodCount.getText().toString())-1;

                        if(valueFood<1){
                            holder.mFoodLayout.setVisibility(View.GONE);
                            holder.mAddFood.setVisibility(View.VISIBLE);
                            holder.mFoodCount.setText("0");
                        }else{
                            holder.mFoodCount.setText((valueFood)+"");
                        }

                        total = total-dto.getSellingPrice();

                        if(total<=0){
                            mTotalLay.setVisibility(View.GONE);

                        }else{
                            mTotalAmt.setText("TOTAL : ₹ "+total);
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


        class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

            public TextViewRobotoregular display_name,start_sell,disp_price,item;
            ImageView mCaeImg,mAddItem,mRemoveItem;
            TextView mFoodCount;
            Button mAddFood;
            LinearLayout mFoodLayout;
//

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setClickable(true);
                try{
                    display_name = (TextViewRobotoregular) itemView.findViewById(R.id.itemname3);
                    start_sell = (TextViewRobotoregular) itemView.findViewById(R.id.sell_rate);
                    disp_price = (TextViewRobotoregular) itemView.findViewById(R.id.displ_rate);
                    item = (TextViewRobotoregular) itemView.findViewById(R.id.displ_rate_item);
                    mCaeImg = (ImageView) itemView.findViewById(R.id.category_image_list);
                    //mAdd = (ImageView) itemView.findViewById(R.id.add_icon);
                    mFoodLayout = (LinearLayout) itemView.findViewById(R.id.food_add_layout);
                    mAddItem = (ImageView) itemView.findViewById(R.id.food_add);
                    mRemoveItem = (ImageView) itemView.findViewById(R.id.food_remove);
                    mFoodCount = (TextView) itemView.findViewById(R.id.food_count);
                    mAddFood = (Button) itemView.findViewById(R.id.add_food);

                }catch (Exception e){
                    e.printStackTrace();
                }


            }
        }

    }

}
