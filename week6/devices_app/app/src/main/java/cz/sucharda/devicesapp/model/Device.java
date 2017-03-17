package cz.sucharda.devicesapp.model;

public class Device {

    public static String TABLE = "devices";
    public static String KEY_ID = "id";
    public static String KEY_NAME = "name";
    public static String KEY_VALUES = "value";

    private long id;
    private String name;
    private double value;

    public Device(long id, String name, double value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Device(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public Device() {
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
