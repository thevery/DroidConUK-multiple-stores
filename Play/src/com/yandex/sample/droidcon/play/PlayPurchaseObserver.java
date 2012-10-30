package com.yandex.sample.droidcon.play;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import com.yandex.sample.droidcon.library.LibraryApplication;

/**
 * A {@link com.yandex.sample.droidcon.play.PurchaseObserver} is used to get callbacks when Android Market sends
 * messages to this application so that we can update the UI.
 */
class PlayPurchaseObserver extends PurchaseObserver {
    private Context context;

    public PlayPurchaseObserver(Context context, Handler handler) {
        super(context, handler);
    }

    @Override
    public void onBillingSupported(boolean supported, String type) {
//        if (Consts.DEBUG) {
//            Log.i(Dungeons.TAG, "supported: " + supported);
//        }
//        if (type == null || type.equals(Consts.ITEM_TYPE_INAPP)) {
//            if (supported) {
//                dungeons.restoreDatabase();
//                dungeons.mBuyButton.setEnabled(true);
//                dungeons.mEditPayloadButton.setEnabled(true);
//            } else {
//                dungeons.showDialog(Dungeons.DIALOG_BILLING_NOT_SUPPORTED_ID);
//            }
//        } else if (type.equals(Consts.ITEM_TYPE_SUBSCRIPTION)) {
//            dungeons.mCatalogAdapter.setSubscriptionsSupported(supported);
//        } else {
//            dungeons.showDialog(Dungeons.DIALOG_SUBSCRIPTIONS_NOT_SUPPORTED_ID);
//        }
    }

    @Override
    public void onPurchaseStateChange(Consts.PurchaseState purchaseState, String itemId,
            int quantity, long purchaseTime, String developerPayload) {
        if (Consts.DEBUG) {
            Log.i(Dungeons.TAG, "onPurchaseStateChange() itemId: " + itemId + " " + purchaseState);
        }

        if (developerPayload == null) {
//            dungeons.logProductActivity(itemId, purchaseState.toString());
        } else {
//            dungeons.logProductActivity(itemId, purchaseState + "\n\t" + developerPayload);
        }

        if (purchaseState == Consts.PurchaseState.PURCHASED) {
            final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putBoolean(itemId, true);
            editor.commit();
            context.sendBroadcast(new Intent(LibraryApplication.makeStateChangedBroadcast(context)));

            //todo: actual work
//            dungeons.mOwnedItems.add(itemId);
//
//            // If this is a subscription, then enable the "Edit
//            // Subscriptions" button.
//            for (Dungeons.CatalogEntry e : Dungeons.CATALOG) {
//                if (e.sku.equals(itemId) &&
//                        e.managed.equals(Dungeons.Managed.SUBSCRIPTION)) {
//                    dungeons.mEditSubscriptionsButton.setVisibility(View.VISIBLE);
//                }
//            }
        }
//        dungeons.mCatalogAdapter.setOwnedItems(dungeons.mOwnedItems);
//        dungeons.mOwnedItemsCursor.requery();
    }

    @Override
    public void onRequestPurchaseResponse(BillingService.RequestPurchase request,
            Consts.ResponseCode responseCode) {
//        if (Consts.DEBUG) {
//            Log.d(Dungeons.TAG, request.mProductId + ": " + responseCode);
//        }
//        if (responseCode == Consts.ResponseCode.RESULT_OK) {
//            if (Consts.DEBUG) {
//                Log.i(Dungeons.TAG, "purchase was successfully sent to server");
//            }
//            dungeons.logProductActivity(request.mProductId, "sending purchase request");
//        } else if (responseCode == Consts.ResponseCode.RESULT_USER_CANCELED) {
//            if (Consts.DEBUG) {
//                Log.i(Dungeons.TAG, "user canceled purchase");
//            }
//            dungeons.logProductActivity(request.mProductId, "dismissed purchase dialog");
//        } else {
//            if (Consts.DEBUG) {
//                Log.i(Dungeons.TAG, "purchase failed");
//            }
//            dungeons.logProductActivity(request.mProductId, "request purchase returned " + responseCode);
//        }
    }

    @Override
    public void onRestoreTransactionsResponse(BillingService.RestoreTransactions request,
            Consts.ResponseCode responseCode) {
        if (responseCode == Consts.ResponseCode.RESULT_OK) {
            if (Consts.DEBUG) {
                Log.d(Dungeons.TAG, "completed RestoreTransactions request");
            }
            // Update the shared preferences so that we don't perform
            // a RestoreTransactions again.
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean(Dungeons.DB_INITIALIZED, true);
            edit.commit();
        } else {
            if (Consts.DEBUG) {
                Log.d(Dungeons.TAG, "RestoreTransactions error: " + responseCode);
            }
        }
    }
}
