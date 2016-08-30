package co.alleys.whattheflux;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

import com.hardsoftstudio.rxflux.action.RxError;
import com.hardsoftstudio.rxflux.dispatcher.RxViewDispatch;
import com.hardsoftstudio.rxflux.store.RxStore;
import com.hardsoftstudio.rxflux.store.RxStoreChange;

import java.util.ArrayList;
import java.util.List;

import co.alleys.whattheflux.rxflux.actions.Actions;
import co.alleys.whattheflux.rxflux.stores.TimeStore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RxViewDispatch {
    TextView txtCurrentTime;
    AppCompatButton btnGetCurrentTime;
    TimeStore timeStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init() {
        txtCurrentTime = (TextView) findViewById(R.id.txt_current_time);
        btnGetCurrentTime = (AppCompatButton) findViewById(R.id.btn_get_current_time);
        btnGetCurrentTime.setOnClickListener(this);

        txtCurrentTime.setText(String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_get_current_time: {
                ((App) getApplicationContext())
                        .getTimeActionCreator()
                        .getCurrentTime();
            }
        }
    }

    @Override
    public void onRxStoreChanged(@NonNull RxStoreChange change) {
        switch (change.getStoreId()) {
            case TimeStore.ID: {
                switch (change.getRxAction().getType()) {
                    case Actions.GET_CURRENT_TIME: {
                        txtCurrentTime.setText(String.valueOf(timeStore.getCurrentTime()));
                    }
                }
            }
        }
    }

    @Override
    public void onRxError(@NonNull RxError error) {
    }

    @Override
    public void onRxViewRegistered() {
        // If there is any fragment that needs to register store changes we can do it here
    }

    @Override
    public void onRxViewUnRegistered() {
        // If there is any fragment that has registered for store changes we can unregister now
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToRegister() {
        timeStore = TimeStore.get(App.get(this).getRxFlux().getDispatcher());
        List<RxStore> rxStoreList = new ArrayList<>();
        rxStoreList.add(timeStore);
        return rxStoreList;
    }

    @Nullable
    @Override
    public List<RxStore> getRxStoreListToUnRegister() {
        return null;
    }
}
