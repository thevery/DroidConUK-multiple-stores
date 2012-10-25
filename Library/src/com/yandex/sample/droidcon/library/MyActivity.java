package com.yandex.sample.droidcon.library;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MyActivity extends Activity {

    public static final String IN_APP_SKU = "com.amazon.buttonclicker.blue_button";
    public static final String SUBSCRIPTION_SKU = "com.amazon.buttonclicker.subscription.1mo";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        TextView subscription = (TextView) findViewById(R.id.subscription);
        final LibraryApplication application = (LibraryApplication) getApplication();
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

    public void onSubscriptionClick(View v){
        purchaseSku(SUBSCRIPTION_SKU);
    }

    public void onLevel4Click(View v){
        purchaseSku(IN_APP_SKU);
    }

    private void purchaseSku(String sku) {
        final LibraryApplication application = (LibraryApplication) getApplication();
        application.purchase(sku);
    }
}
