package cz.sucharda.devicesapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cz.sucharda.devicesapp.fragments.DeviceDetailFragment;

public class DeviceDetailActivity extends BaseActivity {

    public static Intent newIntent(Context packageContext, Uri uri) {
        Intent intent = new Intent(packageContext, DeviceDetailActivity.class);
        intent.setData(uri);
        return intent;
    }

    @NonNull
    @Override
    protected Fragment createFragment() {
        Uri uri = getIntent().getData();
        return DeviceDetailFragment.newInstance(uri);
    }
}
