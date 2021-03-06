package com.tamic.novate;

import android.content.Context;
import android.util.Log;

import com.tamic.novate.exception.NovateException;

import rx.Subscriber;

/**
 * BaseSubscriber
 * Created by Tamic on 2016-08-03.
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    protected Context context;


    public BaseSubscriber(Context context) {
        this.context = context;
        RxApiManager.get().add(this);
    }

    @Override
    final public void onError(java.lang.Throwable e) {
        RxApiManager.get().remove(this); //从容器中移除
        Log.v("Novate", e.getMessage());
        if (e instanceof Throwable) {
            Log.e("Novate", "--> e instanceof Throwable");
            onError((Throwable) e);
        } else {
            Log.e("Novate", "e !instanceof Throwable");
            onError(new Throwable(e, NovateException.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("Novate", "-->http is start");
        // todo some common as show loadding  and check netWork is NetworkAvailable
        // if  NetworkAvailable no !   must to call onCompleted
    }

    @Override
    public void onCompleted() {
        Log.v("Novate", "-->http is Complete");

        RxApiManager.get().remove(this);

        // todo some common as  dismiss loadding
    }

    public abstract void onError(Throwable e);

}
