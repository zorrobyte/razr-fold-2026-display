package com.motorola.plugin.sdk.channel;

import android.os.RemoteException;

/* JADX INFO: loaded from: classes2.dex */
public class ConnectionException extends RemoteException {
    public static final int FAILURE_BIG_DATA = 3;
    public static final int FAILURE_BIND = 1;
    public static final int FAILURE_CALL = 2;
    public final int failure;

    public ConnectionException(int i, String str) {
        super(str);
        this.failure = i;
    }
}
