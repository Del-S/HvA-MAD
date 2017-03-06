package cz.sucharda.pictureapp.Model;

public class Animal {

    private String mName;
    private int mImageRes;

    public Animal(String name, int imageRes) {
        mName = name;
        mImageRes = imageRes;
    }

    public String getName() {
        return mName;
    }

    public int getImageRes() {
        return mImageRes;
    }
}