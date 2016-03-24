package me.chayut.santaslittlehelper;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import me.chayut.SantaHelperLogic.EndPoint;

public class MainActivity extends AppCompatActivity {


    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EndPoint mEndpoint = new EndPoint();


        Intent intent = new Intent(MainActivity.this, SantaService.class);
        intent.putExtra("Extra",mEndpoint);
        startService(intent);


    }
}
