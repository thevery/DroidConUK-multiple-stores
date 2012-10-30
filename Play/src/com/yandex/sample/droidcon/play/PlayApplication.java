package com.yandex.sample.droidcon.play;

import android.os.Handler;
import com.yandex.sample.droidcon.library.LibraryApplication;

public class PlayApplication extends LibraryApplication {
    private PlayPurchaseObserver playPurchaseObserver;
    private Handler mHandler;
    private BillingService mBillingService;
    /**
     * The developer payload that is sent with subsequent
     * purchase requests.
     */
    private String mPayloadContents = null;

    @Override
    public int getSubscriptionDaysLeft() {
        return EXPIRED;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        playPurchaseObserver = new PlayPurchaseObserver(this, mHandler);
        ResponseHandler.register(playPurchaseObserver);
        mBillingService = new BillingService();
        mBillingService.setContext(this);
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
        mBillingService.requestPurchase(sku, Consts.ITEM_TYPE_INAPP, mPayloadContents);
    }
}
