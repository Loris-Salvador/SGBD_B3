package core.model;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Instantane {

    String image;
    private Jugement jugement;
    private int timeStamp;

    public Instantane(String image, Jugement jugement, int timeStamp)
    {
        this.image = image;
        this.jugement = jugement;
        this.timeStamp = timeStamp;
    }

    public String getImage() {
        return image;
    }

    public Jugement getJugement() {
        return jugement;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

}
