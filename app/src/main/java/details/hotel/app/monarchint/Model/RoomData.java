package details.hotel.app.monarchint.Model;

import java.io.Serializable;

public class RoomData implements Serializable {

    String RoomName;
    int noOfAdults;
    int noOfChilds;

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }

    public int getNoOfAdults() {
        return noOfAdults;
    }

    public void setNoOfAdults(int noOfAdults) {
        this.noOfAdults = noOfAdults;
    }

    public int getNoOfChilds() {
        return noOfChilds;
    }

    public void setNoOfChilds(int noOfChilds) {
        this.noOfChilds = noOfChilds;
    }
}
