package co.alleys.whattheflux.rxflux.actions;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.action.RxActionCreator;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.util.SubscriptionManager;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static co.alleys.whattheflux.rxflux.actions.Keys.TIME;

/**
 * Created by Seok-Won on 8/30/16.
 */
public class TimeActionCreator extends RxActionCreator implements Actions {

    public TimeActionCreator(Dispatcher dispatcher, SubscriptionManager manager) {
        super(dispatcher, manager);
    }

    @Override
    public void getCurrentTime() {
        final RxAction action = newRxAction(GET_CURRENT_TIME);
        if (hasRxAction(action)) return;
        addRxAction(action,
                getCurrentTimeObservable()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long time) {
                                postRxAction(newRxAction(GET_CURRENT_TIME, TIME, time));
                            }
                        }));
    }

    private Observable<Long> getCurrentTimeObservable() {
        return Observable.just(System.currentTimeMillis());
    }
}
