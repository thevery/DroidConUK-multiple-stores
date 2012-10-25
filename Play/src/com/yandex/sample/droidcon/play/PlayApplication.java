package com.yandex.sample.droidcon.play;

import com.yandex.sample.droidcon.library.LibraryApplication;

public class PlayApplication extends LibraryApplication {
    @Override
    public int getSubscriptionDaysLeft() {
        return EXPIRED;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean getEntitlementState(String sku) {
        return false;
    }

    @Override
    public int getConsumableCount(String sku) {
        return 0;
    }

    @Override
    public void purchase(String sku) {
        //...
    }
}
