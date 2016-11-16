package jp.ac.asojuku.st.batterywatch;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private MyBroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mReceiver, filter);

    }

    @Override
    protected  void onPause(){
        super.onPause();
        unregisterReceiver(mReceiver);
    }


    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent){

            //複数のインテントを受信する場合はifを使う
            if(intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)){

                int scale = intent.getIntExtra("scale", 0);
                int level = intent.getIntExtra("level", 0);
                int status = intent.getIntExtra("status", 0);
                String statusString = "";
                switch (status){
                    case BatteryManager.BATTERY_STATUS_UNKNOWN:
                        statusString = "unknown";
                        break;
                    case BatteryManager.BATTERY_STATUS_CHARGING:
                        statusString = "charging";
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        statusString = "discharging";
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                        statusString = "not charging";
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL:
                        statusString = "full";
                        break;
                }
                final Calendar calendar = Calendar.getInstance();
                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int minute = calendar.get(Calendar.MINUTE);
                final int second = calendar.get(Calendar.SECOND);

                String title = "Battery Watch";
                String msg = " " + hour + ":" + minute + ":" + second + " " + statusString + " " + level + "/" + scale;

                Log.v(title, msg);

                Activity mainActivity = (Activity)context;
                TextView tvtitle = (TextView) mainActivity.findViewById(R.id.tvtitle);

                tvtitle.setText(title);

                TextView tvmsg = (TextView) mainActivity.findViewById(R.id.tvmsg);

                tvmsg.setText(msg);



                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
