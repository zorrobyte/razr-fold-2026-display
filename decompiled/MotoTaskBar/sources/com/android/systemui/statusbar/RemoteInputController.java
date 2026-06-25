package com.android.systemui.statusbar;

import android.net.Uri;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.Pair;
import com.android.systemui.statusbar.notification.RemoteInputControllerLogger;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class RemoteInputController {
    private final Delegate mDelegate;
    private final RemoteInputControllerLogger mLogger;
    private final RemoteInputUriController mRemoteInputUriController;
    private final ArrayList mOpen = new ArrayList();
    private final ArrayMap mSpinning = new ArrayMap();
    private final ArrayList mCallbacks = new ArrayList(3);
    private Boolean mLastAppliedRemoteInputActive = null;

    public interface Callback {
        default void onRemoteInputActive(boolean z) {
        }

        default void onRemoteInputSent(NotificationEntry notificationEntry) {
        }
    }

    public interface Delegate {
        void lockScrollTo(NotificationEntry notificationEntry);

        void requestDisallowLongPressAndDismiss();

        void setRemoteInputActive(NotificationEntry notificationEntry, boolean z);
    }

    public RemoteInputController(Delegate delegate, RemoteInputUriController remoteInputUriController, RemoteInputControllerLogger remoteInputControllerLogger) {
        this.mDelegate = delegate;
        this.mRemoteInputUriController = remoteInputUriController;
        this.mLogger = remoteInputControllerLogger;
    }

    private void apply(NotificationEntry notificationEntry) {
        this.mDelegate.setRemoteInputActive(notificationEntry, isRemoteInputActive(notificationEntry));
        boolean zIsRemoteInputActive = isRemoteInputActive();
        int size = this.mCallbacks.size();
        for (int i = 0; i < size; i++) {
            ((Callback) this.mCallbacks.get(i)).onRemoteInputActive(zIsRemoteInputActive);
        }
        this.mLastAppliedRemoteInputActive = Boolean.valueOf(zIsRemoteInputActive);
    }

    private boolean pruneWeakThenRemoveAndContains(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, Object obj) {
        boolean z = false;
        for (int size = this.mOpen.size() - 1; size >= 0; size--) {
            NotificationEntry notificationEntry3 = (NotificationEntry) ((WeakReference) ((Pair) this.mOpen.get(size)).first).get();
            Object obj2 = ((Pair) this.mOpen.get(size)).second;
            boolean z2 = obj == null || obj2 == obj;
            if (notificationEntry3 == null || (notificationEntry3 == notificationEntry2 && z2)) {
                this.mOpen.remove(size);
            } else if (notificationEntry3 == notificationEntry) {
                if (obj == null || obj == obj2) {
                    z = true;
                } else {
                    this.mOpen.remove(size);
                }
            }
        }
        return z;
    }

    public void addCallback(Callback callback) {
        callback.getClass();
        this.mCallbacks.add(callback);
    }

    public void addRemoteInput(NotificationEntry notificationEntry, Object obj, String str) {
        notificationEntry.getClass();
        obj.getClass();
        boolean zIsRemoteInputActive = isRemoteInputActive(notificationEntry);
        boolean zPruneWeakThenRemoveAndContains = pruneWeakThenRemoveAndContains(notificationEntry, null, obj);
        this.mLogger.logAddRemoteInput(notificationEntry.getKey(), zIsRemoteInputActive, zPruneWeakThenRemoveAndContains, str, notificationEntry.getNotificationStyle());
        if (!zPruneWeakThenRemoveAndContains) {
            this.mOpen.add(new Pair(new WeakReference(notificationEntry), obj));
        }
        if (zIsRemoteInputActive) {
            return;
        }
        apply(notificationEntry);
    }

    public void addSpinning(String str, Object obj) {
        str.getClass();
        obj.getClass();
        this.mSpinning.put(str, obj);
    }

    public void closeRemoteInputs() {
        if (this.mOpen.size() == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.mOpen.size());
        for (int size = this.mOpen.size() - 1; size >= 0; size--) {
            NotificationEntry notificationEntry = (NotificationEntry) ((WeakReference) ((Pair) this.mOpen.get(size)).first).get();
            if (notificationEntry != null && notificationEntry.rowExists()) {
                arrayList.add(notificationEntry);
            }
        }
        for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
            NotificationEntry notificationEntry2 = (NotificationEntry) arrayList.get(size2);
            if (notificationEntry2.rowExists()) {
                notificationEntry2.closeRemoteInput();
            }
        }
    }

    public void grantInlineReplyUriPermission(StatusBarNotification statusBarNotification, Uri uri) {
        this.mRemoteInputUriController.grantInlineReplyUriPermission(statusBarNotification, uri);
    }

    public boolean isRemoteInputActive() {
        pruneWeakThenRemoveAndContains(null, null, null);
        return !this.mOpen.isEmpty();
    }

    public boolean isRemoteInputActive(NotificationEntry notificationEntry) {
        return pruneWeakThenRemoveAndContains(notificationEntry, null, null);
    }

    public boolean isSpinning(String str) {
        return this.mSpinning.containsKey(str);
    }

    public boolean isSpinning(String str, Object obj) {
        return this.mSpinning.get(str) == obj;
    }

    public void lockScrollTo(NotificationEntry notificationEntry) {
        this.mDelegate.lockScrollTo(notificationEntry);
    }

    public void remoteInputSent(NotificationEntry notificationEntry) {
        int size = this.mCallbacks.size();
        for (int i = 0; i < size; i++) {
            ((Callback) this.mCallbacks.get(i)).onRemoteInputSent(notificationEntry);
        }
    }

    public void removeCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }

    public void removeRemoteInput(NotificationEntry notificationEntry, Object obj, String str) {
        notificationEntry.getClass();
        if (notificationEntry.mRemoteEditImeVisible && notificationEntry.mRemoteEditImeAnimatingAway) {
            this.mLogger.logRemoveRemoteInput(notificationEntry.getKey(), true, true, isRemoteInputActive(notificationEntry), isRemoteInputActive(), str, notificationEntry.getNotificationStyle());
            return;
        }
        boolean zIsRemoteInputActive = isRemoteInputActive(notificationEntry);
        boolean zIsRemoteInputActive2 = isRemoteInputActive();
        this.mLogger.logRemoveRemoteInput(notificationEntry.getKey(), notificationEntry.mRemoteEditImeVisible, notificationEntry.mRemoteEditImeAnimatingAway, zIsRemoteInputActive, zIsRemoteInputActive2, str, notificationEntry.getNotificationStyle());
        if (zIsRemoteInputActive) {
            pruneWeakThenRemoveAndContains(null, notificationEntry, obj);
            apply(notificationEntry);
            return;
        }
        Boolean bool = this.mLastAppliedRemoteInputActive;
        if (bool == null || !bool.booleanValue() || zIsRemoteInputActive2) {
            return;
        }
        this.mLogger.logRemoteInputApplySkipped(notificationEntry.getKey(), str, notificationEntry.getNotificationStyle());
    }

    public void removeSpinning(String str, Object obj) {
        str.getClass();
        if (obj == null || this.mSpinning.get(str) == obj) {
            this.mSpinning.remove(str);
        }
    }

    public void requestDisallowLongPressAndDismiss() {
        this.mDelegate.requestDisallowLongPressAndDismiss();
    }
}
