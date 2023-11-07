package core.model;

public class Instantane {

    private String image;
    private Jugement jugement;
    private int timeStamp;
    private String dateExpertise;

    public Instantane(String image, Jugement jugement, int timeStamp, String dateExpertise)
    {
        this.image = image;
        this.jugement = jugement;
        this.timeStamp = timeStamp;
        this.dateExpertise = dateExpertise;
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
    public String getDateExpertise() { return dateExpertise; }

}
