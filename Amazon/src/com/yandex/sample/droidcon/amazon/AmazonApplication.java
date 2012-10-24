package com.yandex.sample.droidcon.amazon;

import com.yandex.sample.droidcon.library.LibraryApplication;

public class AmazonApplication extends LibraryApplication {
    @Override
    public int getSubscriptionDaysLeft() {
        return EXPIRED;
    }
}
