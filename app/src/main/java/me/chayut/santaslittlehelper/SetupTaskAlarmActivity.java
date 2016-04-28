package me.chayut.santaslittlehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import me.chayut.SantaHelperLogic.SantaAction;
import me.chayut.SantaHelperLogic.SantaHelperLogic;
import me.chayut.SantaHelperLogic.SantaTask;
import me.chayut.SantaHelperLogic.SantaTaskAppoint;

public class SetupTaskAlarmActivity extends AppCompatActivity {


    private final static String TAG = "SetupTaskAlarmActivity";
    static final int REQUEST_ACTION =1;

    Button btnOK, btnCancel,btnSetAction;
    SantaTask mTask;
    SantaAction mAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_task_alarm);

        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra(SantaHelperLogic.EXTRA_SANTA_TASK_APPOINT,new SantaTaskAppoint());
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

        btnSetAction = (Button) findViewById(R.id.btnSetAction);
        btnSetAction.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(SetupTaskAlarmActivity.this,SelectActionActivity.class);
                        startActivityForResult(intent,REQUEST_ACTION);
                    }
                });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,String.format("onActivityResult(): %d", requestCode));

        if(resultCode == RESULT_OK) {

            SantaAction returnAction;

            switch (requestCode) {
                case REQUEST_ACTION:
                    break;
                default:
                    break;
            }
        }
    }


}
