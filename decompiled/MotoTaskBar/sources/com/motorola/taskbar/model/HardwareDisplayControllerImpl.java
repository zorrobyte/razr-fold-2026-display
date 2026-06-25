package com.motorola.taskbar.model;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.TaskBarServiceCallBack;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.model.HardwareDisplayController;
import com.motorola.taskbar.util.DebugConfig;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class HardwareDisplayControllerImpl implements HardwareDisplayController {
    private static final boolean DEBUG = DebugConfig.DEBUG_COMMON;
    private final Handler mBgHandler;
    private final Context mContext;
    private final DisplayManager mDisplayManager;
    private final MotoFeature mMotoFeature;
    private final TaskBarServiceProxy mTaskBarServiceProxy;
    private final Handler mUIHandler;
    private final WindowManager mWindowManager;
    private final List mListeners = new ArrayList();
    private boolean mHdmiDisplayRemoved = true;
    private int mDisplayIndex = 0;
    private int mDisplayId = -1;
    private boolean mIsDesktopMode = true;
    private TaskBarServiceCallBack mTaskBarServiceCallBack = new TaskBarServiceCallBack() { // from class: com.motorola.taskbar.model.HardwareDisplayControllerImpl.1
        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void onDisplayReady(int i) {
            if (HardwareDisplayControllerImpl.this.mMotoFeature.isValidHDMIOrWfdDisplay(i)) {
                HardwareDisplayControllerImpl.this.mBgHandler.removeCallbacks(HardwareDisplayControllerImpl.this.mHdmiRemovedCheck);
                if (!HardwareDisplayControllerImpl.this.mHdmiDisplayRemoved) {
                    HardwareDisplayControllerImpl hardwareDisplayControllerImpl = HardwareDisplayControllerImpl.this;
                    hardwareDisplayControllerImpl.mIsDesktopMode = hardwareDisplayControllerImpl.isDesktopMode(i);
                    int i2 = HardwareDisplayControllerImpl.this.mDisplayId;
                    HardwareDisplayControllerImpl.this.mDisplayId = i;
                    HardwareDisplayControllerImpl hardwareDisplayControllerImpl2 = HardwareDisplayControllerImpl.this;
                    hardwareDisplayControllerImpl2.notifyHardwareDisplayIdChanged(hardwareDisplayControllerImpl2.mDisplayIndex, i2, i, HardwareDisplayControllerImpl.this.mIsDesktopMode);
                    return;
                }
                HardwareDisplayControllerImpl.this.mHdmiDisplayRemoved = false;
                HardwareDisplayControllerImpl.this.mDisplayId = i;
                HardwareDisplayControllerImpl.this.mDisplayIndex++;
                HardwareDisplayControllerImpl hardwareDisplayControllerImpl3 = HardwareDisplayControllerImpl.this;
                hardwareDisplayControllerImpl3.mIsDesktopMode = hardwareDisplayControllerImpl3.isDesktopMode(i);
                HardwareDisplayControllerImpl hardwareDisplayControllerImpl4 = HardwareDisplayControllerImpl.this;
                hardwareDisplayControllerImpl4.notifyHardwareDisplayAdded(hardwareDisplayControllerImpl4.mDisplayIndex, HardwareDisplayControllerImpl.this.mDisplayId, HardwareDisplayControllerImpl.this.mIsDesktopMode);
            }
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void onDisplayRemoved(int i) {
            if (HardwareDisplayControllerImpl.this.mHdmiDisplayRemoved && HardwareDisplayControllerImpl.this.mDisplayId == i) {
                return;
            }
            HardwareDisplayControllerImpl.this.mBgHandler.removeCallbacks(HardwareDisplayControllerImpl.this.mHdmiRemovedCheck);
            HardwareDisplayControllerImpl.this.mBgHandler.postDelayed(HardwareDisplayControllerImpl.this.mHdmiRemovedCheck, 2000L);
        }
    };
    private Runnable mHdmiRemovedCheck = new Runnable() { // from class: com.motorola.taskbar.model.HardwareDisplayControllerImpl.2
        @Override // java.lang.Runnable
        public void run() {
            List hDMIIdList = HardwareDisplayControllerImpl.this.getHDMIIdList();
            if (hDMIIdList == null || hDMIIdList.size() == 0) {
                HardwareDisplayControllerImpl.this.mHdmiDisplayRemoved = true;
                int i = HardwareDisplayControllerImpl.this.mDisplayId;
                HardwareDisplayControllerImpl.this.mDisplayId = -1;
                HardwareDisplayControllerImpl hardwareDisplayControllerImpl = HardwareDisplayControllerImpl.this;
                hardwareDisplayControllerImpl.notifyHardwareDisplayRemoved(hardwareDisplayControllerImpl.mDisplayIndex, i);
                HardwareDisplayControllerImpl.this.mTaskBarServiceProxy.requestNavTrackpadGuide(false);
            }
        }
    };

    public HardwareDisplayControllerImpl(Context context, Handler handler, Handler handler2, TaskBarServiceProxy taskBarServiceProxy, MotoFeature motoFeature) {
        this.mContext = context;
        this.mUIHandler = handler;
        this.mBgHandler = handler2;
        this.mMotoFeature = motoFeature;
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        this.mTaskBarServiceProxy = taskBarServiceProxy;
        taskBarServiceProxy.addCallback(this.mTaskBarServiceCallBack);
        start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List getHDMIIdList() {
        ArrayList arrayList = new ArrayList();
        Display[] displays = this.mDisplayManager.getDisplays();
        if (displays != null) {
            for (Display display : displays) {
                if (this.mMotoFeature.isValidHDMIOrWfdDisplay(display)) {
                    arrayList.add(Integer.valueOf(display.getDisplayId()));
                }
            }
            if (DEBUG) {
                Log.d("HardwareDisplayController", "getHDMIIdList size = " + arrayList.size());
            }
        } else if (DEBUG) {
            Log.d("HardwareDisplayController", "getHDMIIdList not Display");
            return arrayList;
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDesktopMode(int i) {
        return this.mMotoFeature.isDesktopModeDisplay(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addCallback$3(HardwareDisplayController.HardwareDisplayListener hardwareDisplayListener, int i, int i2) {
        hardwareDisplayListener.onHardwareDisplayAdded(i, i2, isDesktopMode(this.mDisplayId));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyHardwareDisplayAdded$0(int i, int i2, boolean z) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mListeners) {
            arrayList.addAll(this.mListeners);
        }
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            ((HardwareDisplayController.HardwareDisplayListener) obj).onHardwareDisplayAdded(i, i2, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyHardwareDisplayIdChanged$2(int i, int i2, int i3, boolean z) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mListeners) {
            arrayList.addAll(this.mListeners);
        }
        int size = arrayList.size();
        int i4 = 0;
        while (i4 < size) {
            Object obj = arrayList.get(i4);
            i4++;
            ((HardwareDisplayController.HardwareDisplayListener) obj).onHardwareDisplayIdChanged(i, i2, i3, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyHardwareDisplayRemoved$1(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mListeners) {
            arrayList.addAll(this.mListeners);
        }
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            Object obj = arrayList.get(i3);
            i3++;
            ((HardwareDisplayController.HardwareDisplayListener) obj).onHardwareDisplayRemoved(i, i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyHardwareDisplayAdded(final int i, final int i2, final boolean z) {
        if (DEBUG) {
            Log.d("HardwareDisplayController", "notifyHardwareDisplayAdded index: " + i + "; id: " + i2 + "; isDesktopMode: " + z);
        }
        this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.model.HardwareDisplayControllerImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyHardwareDisplayAdded$0(i, i2, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyHardwareDisplayIdChanged(final int i, final int i2, final int i3, final boolean z) {
        if (DEBUG) {
            Log.d("HardwareDisplayController", "notifyHardwareDisplayIdChanged index: " + i + "; oldDisplayId: " + i2 + "; newDisplayId: " + i3 + "; isDesktopMode: " + z);
        }
        this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.model.HardwareDisplayControllerImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyHardwareDisplayIdChanged$2(i, i2, i3, z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyHardwareDisplayRemoved(final int i, final int i2) {
        if (DEBUG) {
            Log.d("HardwareDisplayController", "notifyHardwareDisplayRemoved index: " + i + "; id: " + i2);
        }
        this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.model.HardwareDisplayControllerImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyHardwareDisplayRemoved$1(i, i2);
            }
        });
    }

    private void start() {
        if (DEBUG) {
            Log.d("HardwareDisplayController", "isSupportCli: " + this.mMotoFeature.isSupportCli());
        }
        List hDMIIdList = getHDMIIdList();
        if (hDMIIdList == null || hDMIIdList.size() <= 0) {
            return;
        }
        this.mTaskBarServiceCallBack.onDisplayReady(((Integer) hDMIIdList.get(0)).intValue());
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(final HardwareDisplayController.HardwareDisplayListener hardwareDisplayListener) {
        synchronized (this.mListeners) {
            this.mListeners.add(hardwareDisplayListener);
        }
        if (this.mHdmiDisplayRemoved) {
            return;
        }
        final int i = this.mDisplayIndex;
        final int i2 = this.mDisplayId;
        this.mBgHandler.post(new Runnable() { // from class: com.motorola.taskbar.model.HardwareDisplayControllerImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$addCallback$3(hardwareDisplayListener, i, i2);
            }
        });
    }

    @Override // com.motorola.taskbar.model.HardwareDisplayController
    public int getDisplayId() {
        return this.mDisplayId;
    }

    @Override // com.motorola.taskbar.model.HardwareDisplayController
    public boolean isDesktopMode() {
        return this.mIsDesktopMode;
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(HardwareDisplayController.HardwareDisplayListener hardwareDisplayListener) {
        synchronized (this.mListeners) {
            this.mListeners.remove(hardwareDisplayListener);
        }
    }
}
