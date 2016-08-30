package co.alleys.whattheflux;

import android.app.Application;
import android.content.Context;

import com.hardsoftstudio.rxflux.RxFlux;
import com.hardsoftstudio.rxflux.util.LogLevel;

import co.alleys.whattheflux.rxflux.actions.TimeActionCreator;

/**
 * Created by Seok-Won on 8/30/16.
 */
public class App extends Application {

    private RxFlux rxFlux;

    private TimeActionCreator timeActionCreator;

    @Override
    public void onCreate() {
        super.onCreate();
        RxFlux.LOG_LEVEL = BuildConfig.DEBUG ? LogLevel.FULL : LogLevel.NONE;
        rxFlux = RxFlux.init(this);
        timeActionCreator = new TimeActionCreator(rxFlux.getDispatcher(),
                rxFlux.getSubscriptionManager());
    }

    public static App get(Context context) {
        return ((App) context.getApplicationContext());
    }

    public TimeActionCreator getTimeActionCreator() {
        return timeActionCreator;
    }

    public RxFlux getRxFlux() {
        return rxFlux;
    }
}
