package com.dkhai.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button bt_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();
    }

    private void addListenerOnButton() {
        bt_click = (Button) findViewById(R.id.click);

        bt_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "CLICKED", Toast.LENGTH_LONG).show();

                // package_name = "com.dkhai.app2"
                // activity_name = "com.dkhai.app2.MainActivity"

                if (isAppRunning(MainActivity.this,"com.dkhai.app1"))
                {
                    // Toast.makeText(getApplicationContext(), "RUNING", Toast.LENGTH_LONG).show();
                    Log.d("dkhai","RUNING");
                } else {
                    // Toast.makeText(getApplicationContext(), "KILLED", Toast.LENGTH_LONG).show();
                    Log.d("dkhai","KILLED");
                }
                startNewActivity(MainActivity.this, "com.dkhai.app2");
            }
        });
    }


    /** Checking Whether any Activity of Application is running or not
     * @param context
     * @return true if likely successful, false if unsuccessful
     */
    public static boolean isAppRunning(final Context context, final String packageName) {
        // Get the Activity Manager
        // List all services: adb shell dumpsys activity services
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // Get a list of running tasks
        final List<ActivityManager.RunningAppProcessInfo> procInfos = activityManager.getRunningAppProcesses();

        // com.dkhai.app1
        Log.d("dkhai", procInfos.get(0).processName);
        // 29733 =  top | grep app1
        Log.d("dkhai", String.valueOf(procInfos.get(0).pid));

        if (procInfos != null)
        {
            for (final ActivityManager.RunningAppProcessInfo processInfo : procInfos) {
                if (processInfo.processName.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Open another app.
     * @param context current Context, like Activity, App, or Service
     * @param packageName the full package name of the app to open
     * @return true if likely successful, false if unsuccessful
     */
    public void startNewActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            Log.d("dkhai","null packageName");
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}