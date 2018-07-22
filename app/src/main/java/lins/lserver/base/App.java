package lins.lserver.base;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.orhanobut.hawk.Hawk;

/**
 * Created by Administrator on 2018/7/22.
 */

public class App extends MultiDexApplication {
//    static App instance = null;
    private static Context mContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //PosSDK mSDK = null;
//    PosApi mPosApi = null;
    public App(){
        super.onCreate();
//        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        Hawk.init(mContext).build();


    }


    public static Context getContext(){
        return mContext;
    }

}
