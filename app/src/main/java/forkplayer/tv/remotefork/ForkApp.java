package forkplayer.tv.remotefork;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by hamsterksu on 25.12.16.
 */

public class ForkApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            //TODO replace it
            Timber.plant(new Timber.DebugTree());
        }
    }
}
