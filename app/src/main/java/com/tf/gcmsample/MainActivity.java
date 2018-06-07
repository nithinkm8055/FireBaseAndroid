package com.tf.gcmsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MyFirebaseInstanceIDService.OnTokenRefreshListener {

    private TextView lblInstanceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lblInstanceId = (TextView) findViewById(R.id.lblInstanceId);

        String instanceId = getSharedPreferences(getPackageName(), MODE_PRIVATE)
                .getString(MyFirebaseInstanceIDService.PREF_INSTANCE_ID, "Not registered yet");
        onTokenRefresh(instanceId);

        Log.d("InstanceID", instanceId);

        MyFirebaseInstanceIDService.addOnTokenRefreshListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        MyFirebaseInstanceIDService.removeOnTokenRefreshListener(this);
    }

    @Override
    public void onTokenRefresh(String instanceId) {
        lblInstanceId.setText(instanceId);
    }
}
