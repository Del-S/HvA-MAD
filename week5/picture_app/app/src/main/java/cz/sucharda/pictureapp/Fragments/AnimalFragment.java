package cz.sucharda.pictureapp.Fragments;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cz.sucharda.pictureapp.R;

public class AnimalFragment extends Fragment {

    private static final String ARG_IMAGE_RES = "arg_image";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int animalImageRes = getArguments().getInt(ARG_IMAGE_RES);

        View animalView = inflater.inflate(R.layout.fragment_animal, container, false);
        ImageView animalImageView = (ImageView) animalView.findViewById(R.id.image_view_animal);
        animalImageView.setImageResource(animalImageRes);
        return animalView;
    }

    public static AnimalFragment newInstance(@DrawableRes int imageRes) {
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_RES, imageRes);
        AnimalFragment fragment = new AnimalFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
