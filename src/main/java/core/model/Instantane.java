package core.model;

public class Instantane {

    String image;
    private core.model.Jugement jugement;
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

    public core.model.Jugement getJugement() {
        return jugement;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

}
