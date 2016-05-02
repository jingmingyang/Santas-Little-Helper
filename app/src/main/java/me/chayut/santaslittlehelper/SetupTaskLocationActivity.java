package me.chayut.santaslittlehelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import me.chayut.SantaHelperLogic.SantaAction;
import me.chayut.SantaHelperLogic.SantaLogic;
import me.chayut.SantaHelperLogic.SantaTask;
import me.chayut.SantaHelperLogic.SantaTaskLocation;

public class SetupTaskLocationActivity extends AppCompatActivity {

    static final int REQUEST_ACTION =1;
    private final static String TAG = "SetupTaskLocationAct";

    Button btnOK, btnCancel,btnSetAction;
    SantaTaskLocation mTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_task_location);

        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra(SantaLogic.EXTRA_SANTA_TASK_LOC,new SantaTaskLocation(new SantaAction()));
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
                        Intent intent = new Intent(SetupTaskLocationActivity.this,SelectActionActivity.class);
                        startActivityForResult(intent,REQUEST_ACTION);
                    }
                });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,String.format("onActivityResult(): %d: %d", requestCode,resultCode));

        if(resultCode == RESULT_OK) {

            switch (requestCode) {
                case REQUEST_ACTION:

                    SantaAction returnAction = (SantaAction) data.getParcelableExtra(SantaLogic.EXTRA_SANTA_ACTION);
                    mTask.setAction(returnAction);

                    //TODO: update UI according to returned action
                    Log.d(TAG, SantaLogic.EXTRA_SANTA_ACTION);
                    break;
                default:
                    break;
            }
        }
    }

}
