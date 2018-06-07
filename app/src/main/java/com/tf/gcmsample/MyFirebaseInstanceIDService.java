package com.tf.gcmsample;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.ArrayList;
import java.util.List;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    public static final String PREF_INSTANCE_ID = "instance_id";

    private static List<OnTokenRefreshListener> sListeners = new ArrayList<>();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        final String instanceId = FirebaseInstanceId.getInstance().getToken();

        if (instanceId != null) {
            saveToken(instanceId);

            updateListeners(instanceId);
        }
    }

    private void saveToken(String instanceId) {
        getSharedPreferences(getPackageName(), MODE_PRIVATE).edit()
                .putString(PREF_INSTANCE_ID, instanceId)
                .apply();
    }

    private void updateListeners(final String instanceId) {
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                for (OnTokenRefreshListener listener : sListeners) {
                    listener.onTokenRefresh(instanceId);
                }
            }
        });
    }

    public static void addOnTokenRefreshListener(OnTokenRefreshListener listener) {
        sListeners.add(listener);
    }

    public static void removeOnTokenRefreshListener(OnTokenRefreshListener listener) {
        sListeners.remove(listener);
    }

    public interface OnTokenRefreshListener {
        void onTokenRefresh(String instanceId);
    }
}
