package details.hotel.app.monarchint.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ZingoHotels Tech on 10-12-2018.
 */

public class PreferenceHandler {

    private SharedPreferences sh;

    private PreferenceHandler() {

    }

    private PreferenceHandler(Context mContext) {
        sh = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    private static PreferenceHandler instance = null;

    /**
     *
     * @param mContext
     * @return {@link PreferenceHandler}
     */
    public synchronized static PreferenceHandler getInstance(Context mContext) {
        if (instance == null) {
            instance = new PreferenceHandler(mContext);
        }
        return instance;
    }

    public void setUserId(int id)
    {
        sh.edit().putInt(Constants.USER_ID,id).apply();
    }

    public int getUserId()
    {
        return sh.getInt(Constants.USER_ID,0);
    }

    public void setTravellerId(int id)
    {
        sh.edit().putInt(Constants.USER_ID,id).apply();
    }

    public int getTravellerId()
    {
        return sh.getInt(Constants.USER_ID,0);
    }

    public void setListSize(int id)
    {
        sh.edit().putInt("List",id).apply();
    }

    public int getListSize()
    {
        return sh.getInt("List",0);
    }

    public void setHotelId(int id)
    {
        sh.edit().putInt(Constants.HOTEL_ID,id).apply();
    }

    public int getHotelID()
    {
        return sh.getInt(Constants.HOTEL_ID,0);
    }


    public void setToken(String token)
    {
        sh.edit().putString(Constants.TOKEN,token).apply();
    }

    public String getToken()
    {
        return sh.getString(Constants.TOKEN,"");
    }


    public void setUserName(String username)
    {
        sh.edit().putString(Constants.USER_NAME,username).apply();
    }

    public String getUserName()
    {
        return sh.getString(Constants.USER_NAME,"");
    }


    public void setRecType(String username)
    {
        sh.edit().putString(Constants.REC_TYPE,username).apply();
    }

    public String getRecType()
    {
        return sh.getString(Constants.REC_TYPE,"");
    }

    public void setWhatsappNUmber(String username)
    {
        sh.edit().putString(Constants.whats_num,username).apply();
    }

    public String getWhatsappNumber()
    {
        return sh.getString(Constants.whats_num,"");
    }

    public void setEmailList(String username)
    {
        sh.edit().putString(Constants.email_lis,username).apply();
    }

    public String getEmailList()
    {
        return sh.getString(Constants.email_lis,"");
    }

    public void setPhoneNumber(String phonenumber)
    {
        sh.edit().putString(Constants.USER_PHONENUMER,phonenumber).apply();
    }

    public String getPhoneNumber()
    {
        return sh.getString(Constants.USER_PHONENUMER,"");
    }

    public void setHotelName(String id)
    {
        sh.edit().putString(Constants.HOTEL_NAME,id).apply();
    }

    public String getHotelName()
    {
        return sh.getString(Constants.HOTEL_NAME,"");
    }

    public void setLatitude(String id)
    {
        sh.edit().putString(Constants.HOTEL_LATi,id).apply();
    }

    public String getLatitude()
    {
        return sh.getString(Constants.HOTEL_LATi,"");
    }

    public void setLongitude(String id)
    {
        sh.edit().putString(Constants.HOTEL_LONGI,id).apply();
    }

    public String getLongitude()
    {
        return sh.getString(Constants.HOTEL_LONGI,"");
    }

    public void setAboutUs(String id)
    {
        sh.edit().putString(Constants.ABOUT,id).apply();
    }

    public String getAboutUs()
    {
        return sh.getString(Constants.ABOUT,"");
    }


    //TravellerAgent
    public void setUserRoleUniqueID(String approved)
    {
        sh.edit().putString(Constants.USER_ROLE_UNIQUE_ID,approved).apply();
    }

    public String getUserRoleUniqueID()
    {
        return sh.getString(Constants.USER_ROLE_UNIQUE_ID,"");
    }

    public void setAgentName(String username)
    {
        sh.edit().putString(Constants.AGENT_NAME,username).apply();
    }

    public String getAgentName()
    {
        return sh.getString(Constants.AGENT_NAME,"");
    }

    public void setFullName(String username)
    {
        sh.edit().putString(Constants.USER_FULL_NAME,username).apply();
    }

    public String getFullName()
    {
        return sh.getString(Constants.USER_FULL_NAME,"");
    }

    public void setCommissionAmount(double commisionAmount)
    {
        sh.edit().putLong(Constants.COMMISSION,Double.doubleToRawLongBits(commisionAmount)).apply();
    }
    public double getCommissionAmount()
    {
        return  Double.longBitsToDouble(sh.getLong(Constants.COMMISSION,0));
    }

    public void setReferalAmount(long referalAmount)
    {
        sh.edit().putLong(Constants.REFERAL,referalAmount).apply();
    }
    public long getReferalAmount()
    {
        return sh.getLong(Constants.REFERAL,0);
    }

    public void setReferedAmount(int referedAmount)
    {
        sh.edit().putInt(Constants.REFERED,referedAmount).apply();
    }
    public int getReferedAmount()
    {
        return sh.getInt(Constants.REFERED,0);
    }


    public double getCommissionPercentage()
    {
        return Double.longBitsToDouble(sh.getLong(Constants.COMMISSION_PERCENTAGE,0));
    }

    public void setCommissionPercentage(double commisionAmount)
    {
        sh.edit().putLong(Constants.COMMISSION_PERCENTAGE,Double.doubleToRawLongBits(commisionAmount)).apply();
    }



    public void setAgentPhoneNumber(String phonenumber)
    {
        sh.edit().putString(Constants.AGENT_PHONENUMER,phonenumber).apply();
    }

    public String getAgentPhoneNumber()
    {
        return sh.getString(Constants.AGENT_PHONENUMER,"");
    }


    public void clear(){
        sh.edit().clear().apply();

    }


}
