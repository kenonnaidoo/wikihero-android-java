package com.kenon.wikihero.internet;

import android.content.Context;
import android.net.ConnectivityManager;

public class InternetConnection {
    private static Context mContext;

    public InternetConnection(Context mContext) {
        this.mContext = mContext;
    }

    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}
