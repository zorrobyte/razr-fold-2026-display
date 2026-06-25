package com.motorola.taskbar.media;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.Dependency;
import com.android.systemui.displays.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class DesktopFocusAudioOutputMonitor {
    private static final Uri CONTENT_URI_DEVICES = Uri.parse("content://com.motorola.mobiledesktop.media.MediaOutputRouteContentProvider/Device");
    private Handler mBgHandler;
    private ContentObserver mContentObserver;
    private final Context mContext;
    private Context mCurrentUserContext;
    private DesktopFocusAudioOutputListener mDesktopFocusAudioOutputListener;
    private HandlerThread mHandlerThread;
    private UserTracker mUserTracker;
    private Handler mUIHandler = new UIHandle();
    private int mRetryUpdateCount = 0;
    private String mCurrentDeviceName = null;
    private int mCurrentDeviceIconId = 0;
    private Drawable mCurrentDeviceDrawable = null;
    private String mLastNotifyDeviceName = null;
    private Drawable mLastNotifyDeviceDrawable = null;
    private final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.motorola.taskbar.media.DesktopFocusAudioOutputMonitor.1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public void onUserChanged(int i, Context context) {
            DesktopFocusAudioOutputMonitor.this.mCurrentUserContext.getContentResolver().unregisterContentObserver(DesktopFocusAudioOutputMonitor.this.mContentObserver);
            DesktopFocusAudioOutputMonitor.this.updateUserContext(context);
            DesktopFocusAudioOutputMonitor.this.mCurrentUserContext.getContentResolver().registerContentObserver(DesktopFocusAudioOutputMonitor.CONTENT_URI_DEVICES, true, DesktopFocusAudioOutputMonitor.this.mContentObserver);
            if (DesktopFocusAudioOutputMonitor.this.mBgHandler.hasMessages(2)) {
                return;
            }
            DesktopFocusAudioOutputMonitor.this.mBgHandler.sendEmptyMessage(2);
        }
    };

    public interface DesktopFocusAudioOutputListener {
        void onFocusAudioOutputChanged(String str, Drawable drawable);
    }

    class UIHandle extends Handler {
        public UIHandle() {
            super(Looper.getMainLooper());
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 3) {
                return;
            }
            DesktopFocusAudioOutputMonitor.this.mDesktopFocusAudioOutputListener.onFocusAudioOutputChanged(DesktopFocusAudioOutputMonitor.this.mLastNotifyDeviceName, DesktopFocusAudioOutputMonitor.this.mLastNotifyDeviceDrawable);
        }
    }

    class WorkBgHandle extends Handler {
        public WorkBgHandle(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1 || i == 2) {
                DesktopFocusAudioOutputMonitor.this.updateForceDevice();
                DesktopFocusAudioOutputMonitor.this.notifyChangedIfNeed();
            }
        }
    }

    public DesktopFocusAudioOutputMonitor(Context context, UserTracker userTracker) {
        this.mContext = context;
        this.mUserTracker = userTracker;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyChangedIfNeed() {
        String str = this.mCurrentDeviceName;
        Drawable drawable = this.mCurrentDeviceDrawable;
        if (Objects.equals(str, this.mLastNotifyDeviceName) && Objects.equals(drawable, this.mLastNotifyDeviceDrawable)) {
            return;
        }
        this.mLastNotifyDeviceName = str;
        this.mLastNotifyDeviceDrawable = drawable;
        this.mUIHandler.removeMessages(3);
        if (this.mUIHandler.hasMessages(3)) {
            return;
        }
        this.mUIHandler.sendEmptyMessage(3);
    }

    public static boolean startDesktopMediaRouteActivity(Context context, int i) {
        try {
            Intent flags = new Intent().setAction("com.motorola.mobiledesktop.action.MEDIA_OUTPUT").setPackage("com.motorola.mobiledesktop.core").setFlags(268435456);
            ActivityStarter activityStarter = (ActivityStarter) Dependency.getDisplay(i, ActivityStarter.class);
            if (activityStarter != null) {
                activityStarter.startActivity(flags, true);
            }
            return true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateForceDevice() {
        try {
            Cursor cursorQuery = this.mCurrentUserContext.getContentResolver().query(CONTENT_URI_DEVICES, null, null, null);
            if (cursorQuery == null) {
                Log.e("DesktopFocusAudioOutputMonitor", "update cursor is null");
                return;
            }
            if (!cursorQuery.moveToFirst()) {
                cursorQuery.close();
                Log.e("DesktopFocusAudioOutputMonitor", "update cursor is empty, request reupdate mRetryUpdateCount = " + this.mRetryUpdateCount);
                int i = this.mRetryUpdateCount + 1;
                this.mRetryUpdateCount = i;
                if (i < 3) {
                    this.mBgHandler.removeMessages(2);
                    this.mBgHandler.sendEmptyMessageDelayed(2, 100L);
                    return;
                }
                return;
            }
            while (true) {
                if (cursorQuery.getInt(cursorQuery.getColumnIndex("isFocusDevice")) == 1) {
                    String string = cursorQuery.getString(cursorQuery.getColumnIndex("DeviceName"));
                    int i2 = cursorQuery.getInt(cursorQuery.getColumnIndex("DeviceIconId"));
                    Drawable drawableLoadDrawable = Icon.createWithResource("com.motorola.mobiledesktop.core", i2).loadDrawable(this.mCurrentUserContext);
                    if (!TextUtils.isEmpty(string) && (!string.equals(this.mCurrentDeviceName) || this.mCurrentDeviceIconId != i2)) {
                        this.mCurrentDeviceName = string;
                        this.mCurrentDeviceIconId = i2;
                        this.mCurrentDeviceDrawable = drawableLoadDrawable;
                    }
                } else if (!cursorQuery.moveToNext()) {
                    break;
                }
            }
            cursorQuery.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateUserContext(Context context) {
        this.mCurrentUserContext = context;
    }

    public void start(DesktopFocusAudioOutputListener desktopFocusAudioOutputListener) {
        if (this.mHandlerThread != null) {
            return;
        }
        this.mDesktopFocusAudioOutputListener = desktopFocusAudioOutputListener;
        HandlerThread handlerThread = new HandlerThread("DesktopFocusAudioOutputMonitor");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mBgHandler = new WorkBgHandle(this.mHandlerThread.getLooper());
        updateUserContext(this.mUserTracker.getUserContext());
        this.mContentObserver = new ContentObserver(this.mBgHandler) { // from class: com.motorola.taskbar.media.DesktopFocusAudioOutputMonitor.2
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                if (DesktopFocusAudioOutputMonitor.this.mBgHandler.hasMessages(2)) {
                    return;
                }
                DesktopFocusAudioOutputMonitor.this.mBgHandler.sendEmptyMessage(2);
            }
        };
        try {
            this.mCurrentUserContext.getContentResolver().registerContentObserver(CONTENT_URI_DEVICES, true, this.mContentObserver);
            this.mUserTracker.addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
            this.mBgHandler.sendEmptyMessage(1);
        } catch (SecurityException unused) {
            this.mContentObserver = null;
        }
    }
}
