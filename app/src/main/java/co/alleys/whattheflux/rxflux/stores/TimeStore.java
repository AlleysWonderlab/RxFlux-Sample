package co.alleys.whattheflux.rxflux.stores;

import com.hardsoftstudio.rxflux.action.RxAction;
import com.hardsoftstudio.rxflux.dispatcher.Dispatcher;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;

import co.alleys.whattheflux.rxflux.actions.Actions;
import co.alleys.whattheflux.rxflux.actions.Keys;

/**
 * Created by Seok-Won on 8/30/16.
 */
public class TimeStore extends RxStore implements TimeStoreInterface {

    public static final String ID = "TimeStore";
    private static TimeStore instance;
    private Long time;

    public TimeStore(Dispatcher dispatcher) {
        super(dispatcher);
    }

    public static synchronized TimeStore get(Dispatcher dispatcher) {
        if (instance == null) instance = new TimeStore(dispatcher);
        return instance;
    }

    @Override
    public void onRxAction(RxAction action) {
        switch (action.getType()) {
            case Actions.GET_CURRENT_TIME:
                this.time = action.get(Keys.TIME);
                break;
            default: // IMPORTANT if we don't modify the store just ignore
                return;
        }
        postChange(new RxStoreChange(ID, action));
    }

    @Override
    public Long getCurrentTime() {
        return time == null ? 0L : time;
    }
}
