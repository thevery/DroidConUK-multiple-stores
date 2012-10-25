package com.yandex.sample.droidcon.library;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MyActivity extends Activity {

    public static final String IN_APP_SKU_UPPER = "com.yandex.sample.droidcon.library.upper";
    public static final String IN_APP_SKU_ADVANCED = "com.yandex.sample.droidcon.library.advanced";
    public static final String SUBSCRIPTION_SKU = "com.yandex.sample.droidcon.library.1mo";

    public static final Map<Integer, String> VIEW2SKU = new HashMap<Integer, String>() {{
        put(R.id.level3, IN_APP_SKU_UPPER);
        put(R.id.level4, IN_APP_SKU_ADVANCED);
        put(R.id.subscription, SUBSCRIPTION_SKU);
    }};

    private BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        updateState();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("MyActivity.onReceive");
                updateState();
            }
        };
    }

    @Override
    protected void onResume() {
        System.out.println("MyActivity.onResume");
        registerReceiver(receiver, new IntentFilter(LibraryApplication.makeStateChangedBroadcast(this)));
        super.onResume();
        updateState();
    }

    @Override
    protected void onPause() {
        System.out.println("MyActivity.onPause");
        unregisterReceiver(receiver);
        super.onPause();
    }

    private void updateState() {
        final LibraryApplication application = (LibraryApplication) getApplication();
        for (Integer viewId : VIEW2SKU.keySet()) {
            TextView level4 = (TextView) findViewById(viewId);
            final boolean purchased = application.getEntitlementState(VIEW2SKU.get(viewId));
            if (purchased) {
                level4.setTextColor(getResources().getColor(R.color.text_blue_button_unlocked));
                level4.setBackgroundResource(R.drawable.btn_blue);
                level4.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        }

        TextView subscription = (TextView) findViewById(R.id.subscription);

        final int daysLeft = application.getSubscriptionDaysLeft();
        switch (daysLeft) {
            case LibraryApplication.UNLIMITED:
                subscription.setVisibility(View.GONE);
                break;
            case LibraryApplication.EXPIRED:
                subscription.setVisibility(View.VISIBLE);
                //todo: better visual indication
                subscription.setText(R.string.days_expired);
                break;
            default:
                subscription.setText(getString(R.string.days_left, daysLeft));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void onLevelClick(View v) {
        purchaseSku(VIEW2SKU.get(v.getId()));
    }

    private void purchaseSku(String sku) {
        final LibraryApplication application = (LibraryApplication) getApplication();
        application.purchase(sku);
    }
}
