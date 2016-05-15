package me.chayut.santaslittlehelper;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import me.chayut.SantaHelperLogic.SantaLogic;
import me.chayut.SantaHelperLogic.SantaLocation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SetupLocationActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener,GoogleMap.OnMapLongClickListener {


    static final int REQUEST_ACTION =1;
    private final static String TAG = "SetupLocationActivity";
    Button btnOK, btnCancel;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_location);

        final EditText etLocName = (EditText) findViewById(R.id.etLocName);

        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        intent.putExtra(SantaLogic.EXTRA_SANTA_LOCATION,new SantaLocation(etLocName.getText().toString(),12.0,11.0));
                        setResult(RESULT_OK, intent);
                        finish();
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

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(-33.86997, 151.2089);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                mMap.addMarker(new MarkerOptions().position(point).title("Reference Location"));

            }
        });
    }

    @Override
    public void onMapClick(LatLng point)
    {
        mMap.addMarker(new MarkerOptions().position(point).title("Reference Location"));
        // Start_location.lo=point.longitude;
        // Start_location.la=point.latitude;
    }

    @Override
    public void onMapLongClick(LatLng point)
    {
        mMap.addMarker(new MarkerOptions().position(point).title("Reference Location"));
        // Start_location.lo=point.longitude;
        //Start_location.la=point.latitude;
    }



}
