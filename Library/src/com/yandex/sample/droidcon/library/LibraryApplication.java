package com.yandex.sample.droidcon.library;

import android.app.Application;
import android.content.Context;

public abstract class LibraryApplication extends Application {

    public static final int EXPIRED = -1;
    public static final int UNLIMITED = -2;
    public static final String STATE_CHANGED = ".STATE_CHANGED";

    /**
     *
     * @return days left, EXPIRED if subscription already expired or UNLIMITED if subscription is not used
     */
    public int getSubscriptionDaysLeft() {
        return UNLIMITED;
    }

    public boolean isSubscribed() {
        return getSubscriptionDaysLeft() != EXPIRED;
    }

    public abstract boolean getEntitlementState(String sku);

    public abstract int getConsumableCount(String sku);

    public abstract void purchase(String sku);

    public static String makeStateChangedBroadcast(Context context) {
        return context.getPackageName() + STATE_CHANGED;
    }
}
