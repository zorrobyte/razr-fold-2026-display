package com.android.systemui.settings;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes.dex */
public abstract class CurrentUserTracker {
    private Consumer mCallback;
    private final UserReceiver mUserReceiver;

    class UserReceiver extends BroadcastReceiver {
        private static UserReceiver sInstance;
        private final BroadcastDispatcher mBroadcastDispatcher;
        private List mCallbacks = new ArrayList();
        private int mCurrentUserId;
        private boolean mReceiverRegistered;

        UserReceiver(BroadcastDispatcher broadcastDispatcher) {
            this.mBroadcastDispatcher = broadcastDispatcher;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addTracker(Consumer consumer) {
            if (!this.mCallbacks.contains(consumer)) {
                this.mCallbacks.add(consumer);
            }
            if (this.mReceiverRegistered) {
                return;
            }
            this.mCurrentUserId = ActivityManager.getCurrentUser();
            this.mBroadcastDispatcher.registerReceiver(this, new IntentFilter("android.intent.action.USER_SWITCHED"), null, UserHandle.ALL);
            this.mReceiverRegistered = true;
        }

        static UserReceiver getInstance(BroadcastDispatcher broadcastDispatcher) {
            if (sInstance == null) {
                sInstance = new UserReceiver(broadcastDispatcher);
            }
            return sInstance;
        }

        private void notifyUserSwitched(int i) {
            if (this.mCurrentUserId != i) {
                this.mCurrentUserId = i;
                ArrayList arrayList = new ArrayList(this.mCallbacks);
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    Consumer consumer = (Consumer) obj;
                    if (this.mCallbacks.contains(consumer)) {
                        consumer.accept(Integer.valueOf(i));
                    }
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void removeTracker(Consumer consumer) {
            if (this.mCallbacks.contains(consumer)) {
                this.mCallbacks.remove(consumer);
                if (this.mCallbacks.size() == 0 && this.mReceiverRegistered) {
                    this.mBroadcastDispatcher.unregisterReceiver(this);
                    this.mReceiverRegistered = false;
                }
            }
        }

        public int getCurrentUserId() {
            return this.mCurrentUserId;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                notifyUserSwitched(intent.getIntExtra("android.intent.extra.user_handle", 0));
            }
        }
    }

    public CurrentUserTracker(BroadcastDispatcher broadcastDispatcher) {
        this(UserReceiver.getInstance(broadcastDispatcher));
    }

    CurrentUserTracker(UserReceiver userReceiver) {
        this.mCallback = new Consumer() { // from class: com.android.systemui.settings.CurrentUserTracker$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.onUserSwitched(((Integer) obj).intValue());
            }
        };
        this.mUserReceiver = userReceiver;
    }

    public int getCurrentUserId() {
        return this.mUserReceiver.getCurrentUserId();
    }

    public abstract void onUserSwitched(int i);

    public void startTracking() {
        this.mUserReceiver.addTracker(this.mCallback);
    }

    public void stopTracking() {
        this.mUserReceiver.removeTracker(this.mCallback);
    }
}
