package cz.sucharda.devicesapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.af_fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.af_fragment_container, fragment)
                    .commit();
        }
    }
    @NonNull
    protected abstract Fragment createFragment();
}
