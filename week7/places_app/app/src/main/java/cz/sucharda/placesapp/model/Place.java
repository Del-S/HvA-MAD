package cz.sucharda.placesapp.model;

public class Place {

    public static String TABLE = "places";
    public static String KEY_ID = "id";
    public static String KEY_NAME = "name";
    public static String KEY_CITY = "city";
    public static String KEY_LATITUDE = "latitude";
    public static String KEY_LONGITUDE= "longitude";
    public static final String[] ALL_COLUMNS = new String[]{KEY_ID, KEY_NAME, KEY_CITY, KEY_LATITUDE, KEY_LONGITUDE};

    private long id;
    private String name, city;
    private double latitude, longitude;

    public Place(long id, String name, String city, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Place(String name, String city, double latitude, double longitude) {
        this.name = name;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Place() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
