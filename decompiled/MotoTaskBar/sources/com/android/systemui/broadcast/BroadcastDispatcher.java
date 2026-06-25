package com.android.systemui.broadcast;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.SparseArray;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: BroadcastDispatcher.kt */
/* JADX INFO: loaded from: classes.dex */
public class BroadcastDispatcher extends BroadcastReceiver {
    private final Executor bgExecutor;
    private final Looper bgLooper;
    private final Context context;
    private final BroadcastDispatcher$handler$1 handler;
    private final SparseArray receiversByUser;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.broadcast.BroadcastDispatcher$handler$1] */
    public BroadcastDispatcher(Context context, final Looper looper, Executor executor) {
        context.getClass();
        looper.getClass();
        executor.getClass();
        this.context = context;
        this.bgLooper = looper;
        this.bgExecutor = executor;
        this.receiversByUser = new SparseArray(20);
        this.handler = new Handler(looper) { // from class: com.android.systemui.broadcast.BroadcastDispatcher$handler$1
            private int currentUser;

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                message.getClass();
                int i = message.what;
                if (i == 0) {
                    Object obj = message.obj;
                    obj.getClass();
                    ReceiverData receiverData = (ReceiverData) obj;
                    int identifier = receiverData.getUser().getIdentifier() == -2 ? this.currentUser : receiverData.getUser().getIdentifier();
                    if (identifier < -1) {
                        throw new IllegalStateException("Attempting to register receiver for invalid user {" + identifier + "}");
                    }
                    UserBroadcastDispatcher userBroadcastDispatcher = (UserBroadcastDispatcher) this.this$0.receiversByUser.get(identifier, this.this$0.createUBRForUser(identifier));
                    this.this$0.receiversByUser.put(identifier, userBroadcastDispatcher);
                    userBroadcastDispatcher.registerReceiver(receiverData);
                    return;
                }
                if (i == 1) {
                    int size = this.this$0.receiversByUser.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        UserBroadcastDispatcher userBroadcastDispatcher2 = (UserBroadcastDispatcher) this.this$0.receiversByUser.valueAt(i2);
                        Object obj2 = message.obj;
                        obj2.getClass();
                        userBroadcastDispatcher2.unregisterReceiver((BroadcastReceiver) obj2);
                    }
                    return;
                }
                if (i == 2) {
                    UserBroadcastDispatcher userBroadcastDispatcher3 = (UserBroadcastDispatcher) this.this$0.receiversByUser.get(message.arg1);
                    if (userBroadcastDispatcher3 != null) {
                        Object obj3 = message.obj;
                        obj3.getClass();
                        userBroadcastDispatcher3.unregisterReceiver((BroadcastReceiver) obj3);
                        return;
                    }
                    return;
                }
                if (i == 3) {
                    this.currentUser = message.arg1;
                } else if (i != 99) {
                    super.handleMessage(message);
                } else {
                    this.currentUser = ActivityManager.getCurrentUser();
                }
            }
        };
    }

    private final void checkFilter(IntentFilter intentFilter) {
        StringBuilder sb = new StringBuilder();
        if (intentFilter.countActions() == 0) {
            sb.append("Filter must contain at least one action. ");
        }
        if (intentFilter.countDataAuthorities() != 0) {
            sb.append("Filter cannot contain DataAuthorities. ");
        }
        if (intentFilter.countDataPaths() != 0) {
            sb.append("Filter cannot contain DataPaths. ");
        }
        if (intentFilter.countDataSchemes() != 0) {
            sb.append("Filter cannot contain DataSchemes. ");
        }
        if (intentFilter.countDataTypes() != 0) {
            sb.append("Filter cannot contain DataTypes. ");
        }
        if (intentFilter.getPriority() != 0) {
            sb.append("Filter cannot modify priority. ");
        }
        if (!TextUtils.isEmpty(sb)) {
            throw new IllegalArgumentException(sb.toString());
        }
    }

    public static /* synthetic */ void registerReceiver$default(BroadcastDispatcher broadcastDispatcher, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: registerReceiver");
        }
        if ((i & 4) != 0) {
            executor = broadcastDispatcher.context.getMainExecutor();
        }
        if ((i & 8) != 0) {
            userHandle = broadcastDispatcher.context.getUser();
        }
        broadcastDispatcher.registerReceiver(broadcastReceiver, intentFilter, executor, userHandle);
    }

    protected UserBroadcastDispatcher createUBRForUser(int i) {
        return new UserBroadcastDispatcher(this.context, i, this.bgLooper, this.bgExecutor);
    }

    public final void initialize() {
        sendEmptyMessage(99);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.USER_SWITCHED");
        UserHandle userHandle = UserHandle.ALL;
        userHandle.getClass();
        registerReceiver(this, intentFilter, null, userHandle);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        context.getClass();
        intent.getClass();
        if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.USER_SWITCHED")) {
            obtainMessage(3, intent.getIntExtra("android.intent.extra.user_handle", -10000), 0).sendToTarget();
        }
    }

    public final void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        broadcastReceiver.getClass();
        intentFilter.getClass();
        registerReceiver$default(this, broadcastReceiver, intentFilter, null, null, 12, null);
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, Executor executor, UserHandle userHandle) {
        broadcastReceiver.getClass();
        intentFilter.getClass();
        userHandle.getClass();
        checkFilter(intentFilter);
        BroadcastDispatcher$handler$1 broadcastDispatcher$handler$1 = this.handler;
        if (executor == null) {
            executor = this.context.getMainExecutor();
        }
        executor.getClass();
        broadcastDispatcher$handler$1.obtainMessage(0, new ReceiverData(broadcastReceiver, intentFilter, executor, userHandle)).sendToTarget();
    }

    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        broadcastReceiver.getClass();
        obtainMessage(1, broadcastReceiver).sendToTarget();
    }
}
