package me.chayut.santaslittlehelper;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.chayut.SantaHelperLogic.SantaAction;
import me.chayut.SantaHelperLogic.SantaLogic;
import me.chayut.SantaHelperLogic.SantaTask;
import me.chayut.SantaHelperLogic.SantaTaskAppoint;
import me.chayut.SantaHelperLogic.SantaTaskBattery;
import me.chayut.SantaHelperLogic.SantaTaskLocation;

public class SetupTaskLocationActivity extends AppCompatActivity {

    static final int REQUEST_ACTION =1;
    private final static String TAG = "SetupTaskLocationAct";
    SantaTaskLocation mTask;
    private Button btnOK, btnCancel,btnSetAction;
    private TextView tvActionDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_task_location);

        //Setup UI
        tvActionDetail = (TextView) findViewById(R.id.tvActionDetails);

        btnOK = (Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent();

                        //TODO[UI]: read value from UI before return

                        //TODO[UI]: verify the the user input is valid

                        intent.putExtra(SantaLogic.EXTRA_SANTA_TASK_LOC,mTask);
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
                        intent.putExtra(SantaLogic.EXTRA_SANTA_ACTION,mTask.getAction());
                        startActivityForResult(intent,REQUEST_ACTION);
                    }
                });


        //Try Get parcelable
        if(getIntent().hasExtra(SantaLogic.EXTRA_SANTA_TASK_LOC))
        {
            mTask =getIntent().getParcelableExtra(SantaLogic.EXTRA_SANTA_TASK_LOC);

            //if there is parcelable, load value to UI

            //TODO[UI], load this value to UI

            //dont need convert back to SantaLocation object, simpler
            double latitude = mTask.getLatitude();
            double longitude = mTask.getLongitude();
            double range = mTask.getRange();


            tvActionDetail.setText(mTask.getAction().getTaskTypeString());

        }
        else
        {
            //if no intent parcelable, create new
            mTask = new SantaTaskLocation(new SantaAction(),43.0f,43.0f,20.2f);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,String.format("onActivityResult(): %d: %d", requestCode,resultCode));

        if(resultCode == RESULT_OK) {

            switch (requestCode) {
                case REQUEST_ACTION:

                    SantaAction returnAction = (SantaAction) data.getParcelableExtra(SantaLogic.EXTRA_SANTA_ACTION);
                    mTask.setAction(returnAction);

                    // update UI according to returned action
                    tvActionDetail.setText(returnAction.getTaskTypeString());
                    Log.d(TAG, SantaLogic.EXTRA_SANTA_ACTION);
                    break;
                default:
                    break;
            }
        }
    }

}
