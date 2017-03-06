package cz.sucharda.pictureapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cz.sucharda.pictureapp.Fragments.AnimalFragment;
import cz.sucharda.pictureapp.Fragments.AnimalListFragment;
import cz.sucharda.pictureapp.Model.Animal;

public class MasterDetailActivity extends AppCompatActivity implements AnimalListFragment.AnimalListCallback {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_detail);

        mFragmentManager = getSupportFragmentManager();
        Fragment listFragment = mFragmentManager.findFragmentById(R.id.list_container);
        if (listFragment == null) {
            listFragment = new AnimalListFragment();
            mFragmentManager.beginTransaction()
                    .add(R.id.list_container, listFragment)
                    .commit();
        }
    }

    @Override
    public void onAnimalClicked(Animal animal) {
        Fragment animalFragment = AnimalFragment.newInstance(animal.getImageRes());
        mFragmentManager.beginTransaction()
                .replace(R.id.detail_container, animalFragment)
                .commit();
    }
}
