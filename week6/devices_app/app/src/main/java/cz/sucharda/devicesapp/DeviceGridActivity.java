package cz.sucharda.devicesapp;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import cz.sucharda.devicesapp.fragments.DeviceGridFragment;

public class DeviceGridActivity extends BaseActivity {

    @NonNull
    @Override
    protected Fragment createFragment() {
        return new DeviceGridFragment();
    }
}
