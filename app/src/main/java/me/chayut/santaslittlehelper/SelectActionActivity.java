package me.chayut.santaslittlehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import me.chayut.SantaHelperLogic.SantaAction;
import me.chayut.SantaHelperLogic.SantaHelperLogic;

public class SelectActionActivity extends AppCompatActivity {


    Button btnOK, btnCancel;
    SantaAction mAction;
    RadioButton rbSMS,rbEmail,rbWifi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);

        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra(SantaHelperLogic.EXTRA_SANTA_ACTION,new SantaAction());
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


        //radio button setup
        rbSMS = (RadioButton) findViewById(R.id.rbSMS);
        rbEmail = (RadioButton) findViewById(R.id.rbEmail);
        rbWifi = (RadioButton) findViewById(R.id.rbWifi);

        rbSMS.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         rbSMS.setChecked(true);
                                         rbEmail.setChecked(false);
                                         rbWifi.setChecked(false);
                                     }
                                 }
        );

        rbEmail.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         rbSMS.setChecked(false);
                                         rbEmail.setChecked(true);
                                         rbWifi.setChecked(false);
                                     }
                                 }
        );

        rbWifi.setOnClickListener(new View.OnClickListener() {
                                     public void onClick(View v) {
                                         rbSMS.setChecked(false);
                                         rbEmail.setChecked(false);
                                         rbWifi.setChecked(true);
                                     }
                                 }
        );


    }




}
