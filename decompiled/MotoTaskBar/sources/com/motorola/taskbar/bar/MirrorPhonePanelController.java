package com.motorola.taskbar.bar;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$style;
import com.motorola.taskbar.model.DisplayConfigurationController;
import com.motorola.taskbar.model.DisplayWindowManager;
import com.motorola.taskbar.panel.MirrorPhonePanel;
import com.motorola.taskbar.panel.MirrorPhoneTitleBar;
import com.motorola.taskbar.qs.PanelSizes;

/* JADX INFO: loaded from: classes2.dex */
public class MirrorPhonePanelController implements ConfigurationController.ConfigurationListener {
    private Context mContext;
    private DisplayConfigurationController mDisplayConfigurationController;
    private DisplayManager mDisplayManager;
    private Handler mMainHandler;
    private SparseArray mMirrorPhoneInfos = new SparseArray();
    private DisplayConfigurationController.Listener mDisplayConfigurationListener = new DisplayConfigurationController.Listener() { // from class: com.motorola.taskbar.bar.MirrorPhonePanelController.1
        @Override // com.motorola.taskbar.model.DisplayConfigurationController.Listener
        public void onDisplayDensityChangedChanged(int i) {
            MirrorPhonePanelController.this.sendRecreateMsg(i);
        }

        @Override // com.motorola.taskbar.model.DisplayConfigurationController.Listener
        public void onFontSizeChanged(int i) {
            MirrorPhonePanelController.this.sendRecreateMsg(i);
        }
    };
    private final Handler mHandler = new Handler() { // from class: com.motorola.taskbar.bar.MirrorPhonePanelController.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            MirrorPhonePanelController.this.reCreatPanel(message.arg1);
        }
    };
    private DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.motorola.taskbar.bar.MirrorPhonePanelController.3
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            if (i == 0) {
                synchronized (MirrorPhonePanelController.this.mMirrorPhoneInfos) {
                    for (int i2 = 0; i2 < MirrorPhonePanelController.this.mMirrorPhoneInfos.size(); i2++) {
                        try {
                            PanelInfo panelInfo = (PanelInfo) MirrorPhonePanelController.this.mMirrorPhoneInfos.valueAt(i2);
                            if (panelInfo != null) {
                                panelInfo.onDefaultDisplayChanged();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            MirrorPhonePanelController.this.handleDisplayRemoved(i);
            MirrorPhonePanelController.this.mHandler.removeMessages(1);
        }
    };

    class PanelInfo {
        private final int mDisplayId;
        private DisplayManager mDisplayManager;
        private WindowManager.LayoutParams mLayoutParams;
        private final Handler mMainHandler;
        private MirrorPhonePanel mMirrorPhonePanel;
        private MirrorPhoneTitleBar mMirrorPhoneTitleBar;
        private DisplayWindowManager.WindowViewManager mWindowViewManager;
        private boolean mIsShowing = false;
        private DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        private DisplayMetrics mDefaultDisplayMetrics = new DisplayMetrics();
        MirrorPhoneTitleBar.OnPositionUpdateListener mOnPositionUpdateListener = new MirrorPhoneTitleBar.OnPositionUpdateListener() { // from class: com.motorola.taskbar.bar.MirrorPhonePanelController.PanelInfo.1
            @Override // com.motorola.taskbar.panel.MirrorPhoneTitleBar.OnPositionUpdateListener
            public void onPositionUpdated(int i, int i2, boolean z) {
                PanelInfo.this.mLayoutParams.x = i;
                PanelInfo.this.mLayoutParams.y = i2;
                Log.d("MirrorPhonePanelController", "onPositionUpdated x = " + i + "; y = " + i2 + "; actionUp = " + z);
                PanelInfo.this.mWindowViewManager.updateViewLayout(PanelInfo.this.mMirrorPhonePanel, PanelInfo.this.mLayoutParams);
            }
        };

        PanelInfo(Context context, int i, Handler handler) {
            this.mDisplayId = i;
            DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            this.mDisplayManager = displayManager;
            Display display = displayManager.getDisplay(i);
            display.getRealMetrics(this.mDisplayMetrics);
            this.mWindowViewManager = DisplayWindowManager.getWindowViewManager(i, 2041);
            context.createDisplayContext(display);
            this.mMainHandler = handler;
            this.mDisplayManager.getDisplay(0).getRealMetrics(this.mDefaultDisplayMetrics);
        }

        private void initMirrorPhonePanel() {
            int[] mirrorPhoneSize = PanelSizes.getMirrorPhoneSize(this.mWindowViewManager.windowContext);
            if (this.mLayoutParams == null) {
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(mirrorPhoneSize[0], mirrorPhoneSize[1], 2041, 8650856, -3);
                this.mLayoutParams = layoutParams;
                layoutParams.motoPrivateFlags = 1;
                layoutParams.y = (this.mDisplayMetrics.heightPixels - mirrorPhoneSize[1]) - PanelSizes.getTaskbarCacheHeight(this.mDisplayId);
                if (this.mWindowViewManager.windowContext.getResources().getConfiguration().getLayoutDirection() == 1) {
                    this.mLayoutParams.x = 0;
                } else {
                    this.mLayoutParams.x = this.mDisplayMetrics.widthPixels - mirrorPhoneSize[0];
                }
                WindowManager.LayoutParams layoutParams2 = this.mLayoutParams;
                layoutParams2.gravity = 51;
                layoutParams2.windowAnimations = R$style.Animation_TaskbarMirrorPhonePanel;
                layoutParams2.setTitle("MotoMirrorPhone: " + this.mDisplayId);
            }
            MirrorPhonePanel mirrorPhonePanel = (MirrorPhonePanel) LayoutInflater.from(this.mWindowViewManager.windowContext).inflate(R$layout.mirror_panel, (ViewGroup) null);
            this.mMirrorPhonePanel = mirrorPhonePanel;
            MirrorPhoneTitleBar mirrorPhoneTitleBar = (MirrorPhoneTitleBar) mirrorPhonePanel.findViewById(R$id.title_bar);
            mirrorPhoneTitleBar.setOnPositionUpdateListener(this.mOnPositionUpdateListener);
            this.mMirrorPhonePanel.findViewById(R$id.title_close_icon).setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.bar.MirrorPhonePanelController$PanelInfo$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.lambda$initMirrorPhonePanel$0(view);
                }
            });
            this.mMirrorPhoneTitleBar = mirrorPhoneTitleBar;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$initMirrorPhonePanel$0(View view) {
            hidePanel();
        }

        private void updateLocation() {
            this.mDisplayManager.getDisplay(this.mDisplayId).getRealMetrics(this.mDisplayMetrics);
            if (this.mLayoutParams != null) {
                int[] mirrorPhoneSize = PanelSizes.getMirrorPhoneSize(this.mWindowViewManager.windowContext);
                this.mLayoutParams.y = (this.mDisplayMetrics.heightPixels - mirrorPhoneSize[1]) - PanelSizes.getTaskbarCacheHeight(this.mDisplayId);
                if (this.mWindowViewManager.windowContext.getResources().getConfiguration().getLayoutDirection() == 1) {
                    this.mLayoutParams.x = 0;
                } else {
                    this.mLayoutParams.x = this.mDisplayMetrics.widthPixels - mirrorPhoneSize[0];
                }
            }
        }

        public void destory() {
            hidePanel();
        }

        public boolean hideKeyInjectWindow() {
            MirrorPhonePanel mirrorPhonePanel = this.mMirrorPhonePanel;
            if (mirrorPhonePanel != null) {
                return mirrorPhonePanel.requestRemoveKeyInjectWindow();
            }
            return false;
        }

        public void hidePanel() {
            if (this.mIsShowing) {
                this.mWindowViewManager.removeView(this.mMirrorPhonePanel);
                this.mIsShowing = false;
            }
        }

        public void onDefaultDisplayChanged() {
            Display display = ((DisplayManager) this.mWindowViewManager.windowContext.getSystemService("display")).getDisplay(0);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getRealMetrics(displayMetrics);
            int i = displayMetrics.widthPixels;
            DisplayMetrics displayMetrics2 = this.mDefaultDisplayMetrics;
            if (i == displayMetrics2.widthPixels && displayMetrics.heightPixels == displayMetrics2.heightPixels) {
                return;
            }
            this.mDefaultDisplayMetrics = displayMetrics;
            boolean z = this.mIsShowing;
            hidePanel();
            if (this.mLayoutParams != null) {
                int[] mirrorPhoneSize = PanelSizes.getMirrorPhoneSize(this.mWindowViewManager.windowContext);
                WindowManager.LayoutParams layoutParams = this.mLayoutParams;
                layoutParams.width = mirrorPhoneSize[0];
                layoutParams.height = mirrorPhoneSize[1];
            }
            if (z) {
                showPanel();
            }
        }

        public void onUiModeChanged() {
            MirrorPhonePanel mirrorPhonePanel = this.mMirrorPhonePanel;
            if (mirrorPhonePanel == null) {
                return;
            }
            mirrorPhonePanel.setBackground(null);
            this.mMirrorPhonePanel.setBackgroundResource(R$drawable.mirror_phone_title_bg);
            this.mMirrorPhoneTitleBar.onUiModeChanged();
        }

        public void reCreatPanel() {
            boolean z = this.mIsShowing;
            hidePanel();
            if (this.mLayoutParams != null) {
                int[] mirrorPhoneSize = PanelSizes.getMirrorPhoneSize(this.mWindowViewManager.windowContext);
                WindowManager.LayoutParams layoutParams = this.mLayoutParams;
                layoutParams.width = mirrorPhoneSize[0];
                layoutParams.height = mirrorPhoneSize[1];
                updateLocation();
            }
            initMirrorPhonePanel();
            if (z) {
                showPanel();
            }
        }

        public void requestHidePanel() {
            if (this.mIsShowing) {
                hidePanel();
            }
        }

        public void requestSwitchPanel() {
            if (this.mIsShowing) {
                hidePanel();
            } else {
                showPanel();
            }
        }

        public void showPanel() {
            if (this.mIsShowing) {
                return;
            }
            if (this.mMirrorPhonePanel == null) {
                initMirrorPhonePanel();
            }
            this.mWindowViewManager.addView(this.mMirrorPhonePanel, this.mLayoutParams);
            this.mIsShowing = true;
        }
    }

    public MirrorPhonePanelController(Context context, Handler handler, ConfigurationController configurationController, DisplayConfigurationController displayConfigurationController) {
        this.mContext = context;
        this.mMainHandler = handler;
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        this.mDisplayManager = displayManager;
        displayManager.registerDisplayListener(this.mDisplayListener, this.mMainHandler);
        configurationController.addCallback(this);
        this.mDisplayConfigurationController = displayConfigurationController;
        displayConfigurationController.addCallback(this.mDisplayConfigurationListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleDisplayRemoved(int i) {
        synchronized (this.mMirrorPhoneInfos) {
            try {
                PanelInfo panelInfo = (PanelInfo) this.mMirrorPhoneInfos.get(i);
                if (panelInfo != null) {
                    panelInfo.destory();
                    this.mMirrorPhoneInfos.remove(i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void reCreatPanel(int i) {
        synchronized (this.mMirrorPhoneInfos) {
            try {
                PanelInfo panelInfo = (PanelInfo) this.mMirrorPhoneInfos.get(i);
                if (panelInfo != null) {
                    panelInfo.reCreatPanel();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRecreateMsg(int i) {
        this.mHandler.removeMessages(1);
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.what = 1;
        messageObtainMessage.arg1 = i;
        this.mHandler.sendMessageDelayed(messageObtainMessage, 300L);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onUiModeChanged() {
        synchronized (this.mMirrorPhoneInfos) {
            for (int i = 0; i < this.mMirrorPhoneInfos.size(); i++) {
                try {
                    ((PanelInfo) this.mMirrorPhoneInfos.valueAt(i)).onUiModeChanged();
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public boolean requestHideInjectKeyWindow(int i) {
        synchronized (this.mMirrorPhoneInfos) {
            try {
                PanelInfo panelInfo = (PanelInfo) this.mMirrorPhoneInfos.get(i);
                if (panelInfo == null) {
                    return false;
                }
                return panelInfo.hideKeyInjectWindow();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void requestHidePanel(int i) {
        synchronized (this.mMirrorPhoneInfos) {
            try {
                PanelInfo panelInfo = (PanelInfo) this.mMirrorPhoneInfos.get(i);
                if (panelInfo == null) {
                    return;
                }
                panelInfo.requestHidePanel();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void requestSwitchPanel(int i) {
        PanelInfo panelInfo;
        synchronized (this.mMirrorPhoneInfos) {
            try {
                panelInfo = (PanelInfo) this.mMirrorPhoneInfos.get(i);
                if (panelInfo == null) {
                    panelInfo = new PanelInfo(this.mContext, i, this.mMainHandler);
                    this.mMirrorPhoneInfos.append(i, panelInfo);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        panelInfo.requestSwitchPanel();
    }
}
