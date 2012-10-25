package com.yandex.sample.droidcon.amazon;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.amazon.inapp.purchasing.PurchasingManager;
import com.yandex.sample.droidcon.library.LibraryApplication;

public class AmazonApplication extends LibraryApplication {
    private String currentUser;

    @Override
    public int getSubscriptionDaysLeft() {
        return EXPIRED;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ButtonClickerObserver buttonClickerObserver = new ButtonClickerObserver(this);
        PurchasingManager.registerObserver(buttonClickerObserver);
    }

    @Override
    public boolean getEntitlementState(String sku) {
        final SharedPreferences preferences = getSharedPreferencesForCurrentUser();
        preferences.getBoolean(sku, false);
        return false;
    }

    @Override
    public int getConsumableCount(String sku) {
        return 0;
    }

    @Override
    public void purchase(String sku) {
        String requestId = PurchasingManager.initiatePurchaseRequest(sku);
//                storeRequestId(requestId, BLUE_BUTTON);
    }

    public void setCurrentUser(String currentUser) {
        Log.v(ButtonClickerObserver.TAG, "setCurrentUser: currentUser=" + currentUser);
        this.currentUser = currentUser;
    }

    public String getCurrentUser() {
        Log.v(ButtonClickerObserver.TAG, "getCurrentUser: returns " + currentUser);
        return currentUser;
    }

    /**
     * Get the SharedPreferences file for the current user.
     *
     * @return SharedPreferences file for a user.
     */
    private SharedPreferences getSharedPreferencesForCurrentUser() {
        final SharedPreferences settings = getSharedPreferences(currentUser, Context.MODE_PRIVATE);
        return settings;
    }

}
