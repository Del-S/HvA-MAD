package cz.sucharda.pictureapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.sucharda.pictureapp.Adapters.AnimalAdapter;
import cz.sucharda.pictureapp.Model.Animal;
import cz.sucharda.pictureapp.R;

public class AnimalListFragment extends Fragment {

    private AnimalListCallback mCallback;

    public interface AnimalListCallback {
        void onAnimalClicked(Animal animal);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View animalListView = inflater.inflate(R.layout.fragment_animal_list, container, false);
        RecyclerView animalRecycler = (RecyclerView) animalListView.findViewById(R.id.cm_animeList);
        animalRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        animalRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        animalRecycler.setAdapter(new AnimalAdapter(container.getContext()));
        return animalListView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (AnimalListCallback) context;
        } catch (ClassCastException ex) {
            throw new ClassCastException(context.getPackageName()
                    + " must implement AnimalListCallback.");
        }
    }

}
