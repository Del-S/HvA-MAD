package cz.sucharda.placesapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import cz.sucharda.placesapp.model.Place;
import cz.sucharda.placesapp.model.cursors.PlaceCursorWrapper;
import cz.sucharda.placesapp.model.database.PlaceProvider;

public class PlaceDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final int PERMISSIONS_REQUEST_LOCATION = 1;
    private GoogleMap mMap;
    private EditText mNameEditText;
    private String mAction;
    private String mPlaceFilter;
    private Marker mMarker;
    private Place mPlace;
    private Uri mUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.apd_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNameEditText = (EditText) findViewById(R.id.apd_edit_place_name);

        mUri = getIntent().getParcelableExtra(PlaceProvider.CONTENT_ITEM_TYPE);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.apd_map);
        mapFragment.getMapAsync(this);

        populateMap();
    }

    public static Intent newIntent(Context packageContext, Uri uri) {
        Intent intent = new Intent(packageContext, PlaceDetailActivity.class);
        intent.setData(uri);
        return intent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (mAction.equals(Intent.ACTION_EDIT)) {
            getMenuInflater().inflate(R.menu.menu_place_detail_edit, menu);
        }
        if (mAction.equals(Intent.ACTION_INSERT)) {
            getMenuInflater().inflate(R.menu.menu_place_detail_add, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.mpd_delete:
                deletePlace();
                break;
            case R.id.mpd_add:
                // Fall-through to next case
            case R.id.mpd_edit:
                finishEditing();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        enableMyLocation();

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                marker.remove();
            }
            @Override
            public void onMarkerDrag(Marker marker) {
            }
            @Override
            public void onMarkerDragEnd(Marker marker) {
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                addMarker(latLng);
                // set the LatLng values each time a new marker is created
                mPlace.setLatitude(mMarker.getPosition().latitude);
                mPlace.setLongitude(mMarker.getPosition().longitude);
                mPlace.setCity(showCityName(mPlace.getLatitude(), mPlace.getLongitude()));
            }
        });

        if (mPlace.getLatitude() != 0.0 && mPlace.getLongitude() != 0.0) {
            Log.d("PlaceDetailI", "Display marker");
            // add marker and animate camera
            LatLng latLng = new LatLng(mPlace.getLatitude(), mPlace.getLongitude());
            addMarker(latLng);
            CameraUpdate markerLocation = CameraUpdateFactory.newLatLngZoom(latLng, 15);
            mMap.animateCamera(markerLocation);
        } else {
            LatLng latLng = new LatLng(52.092876, 5.104480);
            CameraUpdate markerLocation = CameraUpdateFactory.newLatLngZoom(latLng, 6);
            mMap.animateCamera(markerLocation);
        }
    }

    private void enableMyLocation() {
        // Check if location permissions are granted.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission.
            // The dialog box asking for permission is generated by this call.
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_LOCATION
            );
            return;
        }
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_LOCATION) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the
                // location-related task you need to do.
                enableMyLocation();
            } else {
                // Otherwise, disable functionality that requires permission(s)
                // and let your user know about it.
            }
        }
    }

    private String showCityName(double latitude, double longitude) {
        String cityName = "*No City*";
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            cityName = addresses.get(0).getLocality();
        } catch (Exception e) {
            Toast.makeText(PlaceDetailActivity.this, "No city could be found", Toast.LENGTH_SHORT).show();
        }

        return cityName;
    }

    private void addMarker(LatLng latLng) {
        mMap.clear();
        mMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title(mNameEditText.getText().toString())
                .snippet("city: " + showCityName(latLng.latitude, latLng.longitude))
                .draggable(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
    }

    private void deletePlace() {
        getContentResolver().delete(PlaceProvider.CONTENT_URI, mPlaceFilter, null);
        Toast.makeText(PlaceDetailActivity.this, "Place deleted", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }


    private void addPlace(Place place) {
        ContentValues values = new ContentValues();

        values.put(Place.KEY_NAME, place.getName());
        values.put(Place.KEY_CITY, place.getCity());
        values.put(Place.KEY_LATITUDE, place.getLatitude());
        values.put(Place.KEY_LONGITUDE, place.getLongitude());

        getContentResolver().insert(PlaceProvider.CONTENT_URI, values);

        Toast.makeText(PlaceDetailActivity.this, "Place added", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }

    private void updatePlace(Place place) {
        ContentValues values = new ContentValues();

        values.put(Place.KEY_NAME, place.getName());
        values.put(Place.KEY_CITY, place.getCity());
        values.put(Place.KEY_LATITUDE, place.getLatitude());
        values.put(Place.KEY_LONGITUDE, place.getLongitude());

        getContentResolver().update(PlaceProvider.CONTENT_URI, values, mPlaceFilter, null);

        Toast.makeText(PlaceDetailActivity.this, "Place updated", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
    }

    private void finishEditing() {
        // get the string from the EditText and set it in the object
        String name = mNameEditText.getText().toString().trim();
        mPlace.setName(name);
        switch (mAction) {
            case Intent.ACTION_INSERT:
                if (name.length() == 0) {
                    setResult(RESULT_CANCELED);
                } else {
                    addPlace(mPlace);
                }
                break;
            case Intent.ACTION_EDIT:
                if (name.length() == 0) {
                    deletePlace();
                } else {
                    updatePlace(mPlace);
                }
        }

        finish();
    }

    private void populateMap() {
        mPlace = new Place();
        // If the mUri is NULL, the activity is setup empty and seen as a new mPlace
        if (mUri == null) {
            mAction = Intent.ACTION_INSERT;
            setTitle("New Place");
        } else {
            // If the mUri is not NULL, the activity is loaded with the data from the database
            setTitle("Edit Place");
            mAction = Intent.ACTION_EDIT;
            mPlaceFilter = Place.KEY_ID + "=" + mUri.getLastPathSegment();
            PlaceCursorWrapper cursor = new PlaceCursorWrapper(getContentResolver().query(
                    PlaceProvider.CONTENT_URI,
                    Place.ALL_COLUMNS,
                    mPlaceFilter,
                    null,
                    null
            ));
            cursor.moveToFirst();
            mPlace = cursor.getPlace();
            cursor.close();
            // Set the values in the view
            mNameEditText.setText(mPlace.getName());
        }
    }

}
