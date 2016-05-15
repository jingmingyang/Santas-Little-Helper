package me.chayut.santaslittlehelper;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.chayut.SantaHelperLogic.SantaLogic;
import me.chayut.SantaHelperLogic.SantaLocation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SetupLocationActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener,GoogleMap.OnMapLongClickListener {


    static final int REQUEST_ACTION =1;
    private final static String TAG = "SetupLocationActivity";
    Button btnOK, btnCancel;
    private GoogleMap mMap;
    private Marker hereMarker;
    private SantaLocation mLocation;
    private EditText etLocName;


    private boolean mLocationSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_location);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_set);
        mapFragment.getMapAsync(this);

        mLocation = new SantaLocation();

        etLocName = (EditText) findViewById(R.id.etLocName);


        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        if(mLocationSelected) {
                            Intent intent = new Intent();
                            mLocation.setName(etLocName.getText().toString());
                            intent.putExtra(SantaLogic.EXTRA_SANTA_LOCATION, mLocation);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(),"You have not select location",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        setResult(RESULT_CANCELED, intent);
                        finish();
                    }
                }
        );

        //prompt user
        Toast.makeText(getBaseContext(),"Click on map to Select location",
                Toast.LENGTH_LONG).show();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-33.86997, 151.2089);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));

        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);

        if(getIntent().hasExtra(SantaLogic.EXTRA_SANTA_LOCATION))
        {
            mLocation =getIntent().getParcelableExtra(SantaLogic.EXTRA_SANTA_LOCATION);
            etLocName.setText(mLocation.getName());
            mLocationSelected = true;
        }

    }

    @Override
    public void onMapClick(LatLng point)
    {
        mMap.clear();
        hereMarker =  mMap.addMarker(new MarkerOptions().position(point).title("This Location"));
        mLocation.setLatitude(point.latitude);
        mLocation.setLongitude(point.longitude);
        mLocationSelected = true;
    }

    @Override
    public void onMapLongClick(LatLng point)
    {
        mMap.addMarker(new MarkerOptions().position(point).title("Reference Location"));
    }


}
