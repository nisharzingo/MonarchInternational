package details.hotel.app.monarchint.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import details.hotel.app.monarchint.Customs.CustomFonts.TextViewRobotoregular;
import details.hotel.app.monarchint.Model.HotelContacts;
import details.hotel.app.monarchint.Model.HotelDetails;
import details.hotel.app.monarchint.R;
import details.hotel.app.monarchint.Utils.PreferenceHandler;
import details.hotel.app.monarchint.Utils.ThreadExecuter;
import details.hotel.app.monarchint.Utils.Util;
import details.hotel.app.monarchint.WebAPI.HotelsDetailsAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ZingoHotels Tech on 11-12-2018.
 */

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.ViewHolder> {

    Context context;
    ArrayList<HotelDetails> hotelDetailsArrayList;

    public HotelListAdapter(Context context, ArrayList<HotelDetails> hotelDetailsArrayList)
    {
        this.context = context;
        this.hotelDetailsArrayList = hotelDetailsArrayList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_hotel_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        try{

            holder.mHotelName.setText(hotelDetailsArrayList.get(position).getHotelDisplayName());
            holder.mHotelLocality.setText(hotelDetailsArrayList.get(position).getLocalty());

            holder.mBaseHotel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    PreferenceHandler.getInstance(context).setHotelId(hotelDetailsArrayList.get(position).getHotelId());
                    PreferenceHandler.getInstance(context).setHotelName(hotelDetailsArrayList.get(position).getHotelName());
                    PreferenceHandler.getInstance(context).setRecType(hotelDetailsArrayList.get(position).getReconcilationType());
                    PreferenceHandler.getInstance(context).setAboutUs(hotelDetailsArrayList.get(position).getAbouUs());
                    //Intent sameActivity = new Intent(context, HotelOptionsScreen.class);
                    getHotelContact(hotelDetailsArrayList.get(position).getHotelId());
                    ((Activity)context).recreate();


                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return hotelDetailsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextViewRobotoregular mHotelName,mHotelLocality;
        LinearLayout mBaseHotel;
        public ViewHolder(View itemView) {
            super(itemView);
            mHotelName = (TextViewRobotoregular)itemView.findViewById(R.id.adapter_hotel_name);
            mHotelLocality = (TextViewRobotoregular) itemView.findViewById(R.id.adapter_hotel_location);
            mBaseHotel = (LinearLayout) itemView.findViewById(R.id.base_hotel_layout);
        }
    }

    private void getHotelContact(final int id){



        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                //String auth_string = Util.getToken(getActivity());
                String auth_string = "Basic TW9obmlBdmQ6ODIyMDgxOTcwNg==";
                HotelsDetailsAPI apiService = Util.getClient().create(HotelsDetailsAPI.class);
                Call<ArrayList<HotelContacts>> call = apiService.getConatctByHotelId(auth_string,id);

                call.enqueue(new Callback<ArrayList<HotelContacts>>() {
                    @Override
                    public void onResponse(Call<ArrayList<HotelContacts>> call, Response<ArrayList<HotelContacts>> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();
                        if (statusCode == 200) {


                            ArrayList<HotelContacts> hotelDetails =  response.body();

                            if(hotelDetails != null&&hotelDetails.size()!=0)
                            {
                                HotelContacts dto = hotelDetails.get(0);

                                if(dto!=null){


                                    PreferenceHandler.getInstance(context).setWhatsappNUmber(""+dto.getHotelPhone());

                                }




                            }else{
                                PreferenceHandler.getInstance(context).setWhatsappNUmber("");
                            }




                        }else {

                            PreferenceHandler.getInstance(context).setWhatsappNUmber("");
                            //Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<HotelContacts>> call, Throwable t) {

                        Log.e("TAG", t.toString());
                        PreferenceHandler.getInstance(context).setWhatsappNUmber("");
                    }
                });
            }


        });
    }
}
