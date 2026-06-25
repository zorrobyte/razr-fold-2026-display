package com.motorola.glass;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.motorola.taskbar.R$dimen;
import com.motorola.taskbar.R$drawable;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.R$style;
import com.motorola.taskbar.model.DisplayWindowManager;
import com.motorola.taskbar.qs.PanelSizes;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.taskbar.util.WindowViewWapper;
import com.motorola.trackpad.ReadyForProxy;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PhantomGlassManager implements IGlassManager {
    private static final boolean DEBUG = DebugConfig.DEBUG_COMMON;
    private Context mContext;
    private Display mDisplay;
    private WindowViewWapper mGlassStatusWindowViewWapper;
    private ImageView mTypeIconView;
    private TextView mTypeNameView;
    private USBDataReceiverThread mUSBDataReceiverThread;
    private UsbManager mUsbManager;
    private ProgressBar mValueProgressBar;
    private DisplayWindowManager.WindowViewManager mWindowViewManager = null;
    private ViewGroup mGlassStatusRootView = null;
    private WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();
    private final ReadyForProxy.OnModeChooserListener mModeChooserListener = new ReadyForProxy.OnModeChooserListener() { // from class: com.motorola.glass.PhantomGlassManager.1
        @Override // com.motorola.trackpad.ReadyForProxy.OnModeChooserListener
        public void onModeChooserShowingStateChanged(int i, boolean z) {
            if (z) {
                PhantomGlassManager phantomGlassManager = PhantomGlassManager.this;
                phantomGlassManager.onDisplayChanged(phantomGlassManager.mDisplay);
            }
        }
    };
    private DisplayWindowManager.ConfigurationChangedCallback mConfigurationChangedCallback = new DisplayWindowManager.ConfigurationChangedCallback() { // from class: com.motorola.glass.PhantomGlassManager.2
        @Override // com.motorola.taskbar.model.DisplayWindowManager.ConfigurationChangedCallback
        public void onConfigurationChanged(Configuration configuration) {
            PhantomGlassManager.this.mUIHandler.removeCallbacksAndMessages(null);
            if (PhantomGlassManager.this.mGlassStatusWindowViewWapper != null) {
                PhantomGlassManager.this.mGlassStatusWindowViewWapper.removeView();
            }
            PhantomGlassManager.this.createGlassStatusRootView();
        }
    };
    private final UIHandle mUIHandler = new UIHandle();
    private ReadyForProxy mReadyForProxy = (ReadyForProxy) Dependency.get(ReadyForProxy.class);

    class UIHandle extends Handler {
        public UIHandle() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                PhantomGlassManager.this.handleUpdateStatus((byte) message.arg1, message.arg2);
                return;
            }
            if (i == 2) {
                PhantomGlassManager.this.createGlassStatusRootView();
                return;
            }
            if (i != 3) {
                if (i != 4) {
                    return;
                }
                PhantomGlassManager.this.initGlassViewIfNeed();
            } else if (PhantomGlassManager.this.mGlassStatusWindowViewWapper != null) {
                PhantomGlassManager.this.mGlassStatusWindowViewWapper.removeView();
            }
        }
    }

    class USBDataReceiverThread extends Thread {
        private UsbDeviceConnection mUsbDeviceConnection;
        private List mUsbInterfaceList;

        public USBDataReceiverThread(UsbDeviceConnection usbDeviceConnection, List list) {
            this.mUsbDeviceConnection = usbDeviceConnection;
            this.mUsbInterfaceList = list;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                if (this.mUsbDeviceConnection != null) {
                    while (!Thread.interrupted()) {
                        for (UsbInterface usbInterface : this.mUsbInterfaceList) {
                            for (int i = 0; i < usbInterface.getEndpointCount(); i++) {
                                UsbEndpoint endpoint = usbInterface.getEndpoint(i);
                                if (128 == endpoint.getDirection()) {
                                    int maxPacketSize = endpoint.getMaxPacketSize();
                                    byte[] bArr = new byte[maxPacketSize];
                                    if (this.mUsbDeviceConnection.bulkTransfer(endpoint, bArr, maxPacketSize, 100) > 0) {
                                        PhantomGlassManager.this.onUSBDataReceive(bArr);
                                    } else if (this.mUsbDeviceConnection.controlTransfer(160, 1, 512, 0, bArr, maxPacketSize, 100) > 0) {
                                        PhantomGlassManager.this.onUSBDataReceive(bArr);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("PhantomGlassManager", "Error in receive thread", e);
            }
        }
    }

    public PhantomGlassManager(Context context, Display display) {
        this.mDisplay = null;
        this.mContext = context;
        this.mDisplay = display;
        this.mUsbManager = (UsbManager) context.getSystemService("usb");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createGlassStatusRootView() {
        DisplayWindowManager.WindowViewManager windowViewManager = this.mWindowViewManager;
        if (windowViewManager == null) {
            Log.e("PhantomGlassManager", "createGlassStatusRootView with NULL mWindowViewManager");
            return;
        }
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(windowViewManager.getContext()).inflate(R$layout.glass_status_toast, (ViewGroup) null);
        this.mGlassStatusRootView = viewGroup;
        this.mTypeIconView = (ImageView) viewGroup.findViewById(R$id.typeIcon);
        this.mTypeNameView = (TextView) this.mGlassStatusRootView.findViewById(R$id.typeName);
        this.mValueProgressBar = (ProgressBar) this.mGlassStatusRootView.findViewById(R$id.progress);
        this.mGlassStatusWindowViewWapper = new WindowViewWapper(this.mWindowViewManager.getWindowManager(), this.mGlassStatusRootView);
        measureGlassStatusRootView();
    }

    private void detachedView() {
        this.mUIHandler.removeCallbacksAndMessages(null);
        DisplayWindowManager.WindowViewManager windowViewManager = this.mWindowViewManager;
        if (windowViewManager != null) {
            windowViewManager.removeConfigurationChangedCallbacks(this.mConfigurationChangedCallback);
            this.mWindowViewManager = null;
        }
        WindowViewWapper windowViewWapper = this.mGlassStatusWindowViewWapper;
        if (windowViewWapper != null) {
            windowViewWapper.removeView();
        }
        this.mGlassStatusRootView = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUpdateStatus(byte b, int i) {
        if (this.mGlassStatusRootView == null) {
            Log.w("PhantomGlassManager", "handleUpdateStatus mGlassStatusRootView did not inited");
            return;
        }
        this.mUIHandler.removeMessages(3);
        if (b == 0) {
            this.mTypeIconView.setImageResource(R$drawable.ic_glasses_brightness);
            this.mTypeNameView.setText(R$string.glass_brightness_description);
            this.mValueProgressBar.setMin(1);
            this.mValueProgressBar.setMax(10);
            this.mValueProgressBar.setProgress(i);
            this.mValueProgressBar.setVisibility(0);
        } else if (b == 1) {
            this.mTypeIconView.setImageResource(R$drawable.ic_glasses_volume);
            this.mTypeNameView.setText(R$string.glass_volume_description);
            this.mValueProgressBar.setMin(1);
            this.mValueProgressBar.setMax(10);
            this.mValueProgressBar.setProgress(i);
            this.mValueProgressBar.setVisibility(0);
        } else if (b != 2) {
            if (b != 3) {
                if (b != 10) {
                    return;
                }
                boolean z = i == 1;
                this.mTypeIconView.setImageResource(z ? R$drawable.ic_glasses_anti_blue_on : R$drawable.ic_glasses_anti_blue_off);
                this.mTypeNameView.setText(z ? R$string.glass_anti_blue_light_on_description : R$string.glass_anti_blue_light_off_description);
                this.mValueProgressBar.setVisibility(8);
            } else if (!updateDisplayModeUI((byte) (i & 255))) {
                return;
            }
        } else if (!updateColorModeUI((byte) (i & 255))) {
            return;
        }
        WindowViewWapper windowViewWapper = this.mGlassStatusWindowViewWapper;
        if (windowViewWapper != null) {
            if (!windowViewWapper.isAddedToWindow()) {
                this.mGlassStatusWindowViewWapper.addView(this.mLayoutParams);
            }
            this.mUIHandler.sendEmptyMessageDelayed(3, 4000L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initGlassViewIfNeed() {
        Display display = this.mDisplay;
        if (display != null && this.mGlassStatusRootView == null && this.mWindowViewManager == null) {
            DisplayWindowManager.WindowViewManager windowViewManager = DisplayWindowManager.getWindowViewManager(display.getDisplayId(), 2036);
            this.mWindowViewManager = windowViewManager;
            windowViewManager.addConfigurationChangedCallbacks(this.mConfigurationChangedCallback);
            this.mUIHandler.sendEmptyMessageDelayed(2, 2000L);
        }
    }

    private void logColorMode(byte b) {
        String str;
        if (b == 0) {
            str = "normal mode";
        } else if (b == 1) {
            str = "anti-bluelight mode";
        } else if (b == 2) {
            str = "cold mode";
        } else if (b != 3) {
            str = "unknown mode: 0x" + Integer.toHexString(b & 255);
        } else {
            str = "warm mode";
        }
        Log.d("PhantomGlassManager", "color mode change to: " + str);
    }

    private void logDisplayMode(byte b) {
        String str;
        if (b == 0) {
            str = "DP-direct mode";
        } else if (b == 1) {
            str = "3DoF mode";
        } else if (b != 2) {
            str = "unknown mode: 0x" + Integer.toHexString(b & 255);
        } else {
            str = "Auto-hide mode";
        }
        Log.d("PhantomGlassManager", "display mode change to: " + str);
    }

    private void measureGlassStatusRootView() {
        Context context = this.mWindowViewManager.getContext();
        this.mLayoutParams.width = context.getResources().getDimensionPixelSize(R$dimen.glass_status_toast_width);
        this.mLayoutParams.height = context.getResources().getDimensionPixelSize(R$dimen.glass_status_toast_height);
        Rect rect = new Rect();
        this.mDisplay.getRectSize(rect);
        int iWidth = rect.width();
        Resources resources = context.getResources();
        int i = R$dimen.glass_status_toast_min_gap;
        int dimensionPixelSize = iWidth - (resources.getDimensionPixelSize(i) * 2);
        WindowManager.LayoutParams layoutParams = this.mLayoutParams;
        if (layoutParams.width > dimensionPixelSize) {
            layoutParams.width = dimensionPixelSize;
        }
        layoutParams.setTitle("PhantomGlassToast");
        this.mLayoutParams.packageName = this.mContext.getOpPackageName();
        WindowManager.LayoutParams layoutParams2 = this.mLayoutParams;
        int iWidth2 = rect.width();
        WindowManager.LayoutParams layoutParams3 = this.mLayoutParams;
        layoutParams2.x = (iWidth2 - layoutParams3.width) / 2;
        layoutParams3.y = PanelSizes.getTaskbarHeight(this.mDisplay.getDisplayId(), context) + context.getResources().getDimensionPixelSize(i);
        WindowManager.LayoutParams layoutParams4 = this.mLayoutParams;
        layoutParams4.type = 2036;
        layoutParams4.format = -3;
        layoutParams4.windowAnimations = R$style.Animation_Tooltip;
        layoutParams4.flags = 8;
        layoutParams4.privateFlags = 16;
        layoutParams4.gravity = 83;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v10, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public void onUSBDataReceive(byte[] bArr) {
        Message messageObtainMessage;
        StringBuilder sb = new StringBuilder();
        sb.append("onUSBDataReceive len: " + bArr.length + ": 0x");
        int length = bArr.length;
        for (int i = 0; i < length; i++) {
            sb.append(Integer.toHexString(bArr[i] & 255));
            sb.append(" ");
        }
        boolean z = DEBUG;
        if (z) {
            Log.d("PhantomGlassManager", sb.toString());
        }
        if (bArr.length < 6) {
            Log.w("PhantomGlassManager", "onUSBDataReceive payload length too short: " + bArr.length);
            return;
        }
        if (bArr[0] != -86 || bArr[1] != 85 || bArr[2] != 6 || bArr[3] != -45) {
            Log.w("PhantomGlassManager", "onUSBDataReceive ignore invalid payload: " + ((Object) sb));
            return;
        }
        byte b = bArr[4];
        if (b == 0) {
            int i2 = bArr[5] & 255;
            if (z) {
                Log.d("PhantomGlassManager", "Brightness change to: " + i2);
            }
            messageObtainMessage = this.mUIHandler.obtainMessage(1, bArr[4] & 255, i2);
        } else if (b == 1) {
            int i3 = bArr[5] & 255;
            if (z) {
                Log.d("PhantomGlassManager", "Volume  change to: " + i3);
            }
            messageObtainMessage = this.mUIHandler.obtainMessage(1, bArr[4] & 255, i3);
        } else if (b == 2) {
            byte b2 = bArr[5];
            if (z) {
                logColorMode(b2);
            }
            messageObtainMessage = this.mUIHandler.obtainMessage(1, bArr[4] & 255, b2 & 255);
        } else if (b == 3) {
            byte b3 = bArr[5];
            if (z) {
                logDisplayMode(b3);
            }
            messageObtainMessage = this.mUIHandler.obtainMessage(1, bArr[4] & 255, b3 & 255);
        } else {
            if (b != 10) {
                Log.w("PhantomGlassManager", "onUSBDataReceive ignore unknown type: 0x" + Integer.toHexString(bArr[4] & 255));
                return;
            }
            ?? r2 = bArr[5] == 1 ? 1 : 0;
            if (z) {
                Log.d("PhantomGlassManager", "anti-bluelight switch to: " + ((boolean) r2));
            }
            messageObtainMessage = this.mUIHandler.obtainMessage(1, bArr[4] & 255, r2);
        }
        if (messageObtainMessage != null) {
            this.mUIHandler.removeMessages(1);
            this.mUIHandler.sendMessage(messageObtainMessage);
        }
    }

    private void stopCurrentDevice() {
        detachedView();
        USBDataReceiverThread uSBDataReceiverThread = this.mUSBDataReceiverThread;
        if (uSBDataReceiverThread != null) {
            uSBDataReceiverThread.interrupt();
            this.mUSBDataReceiverThread = null;
        }
    }

    private boolean updateColorModeUI(byte b) {
        if (b == 0) {
            this.mTypeNameView.setText(R$string.glass_color_mode_standard_description);
        } else if (b == 1) {
            this.mTypeNameView.setText(R$string.glass_color_mode_anti_blue_light_description);
        } else if (b == 2) {
            this.mTypeNameView.setText(R$string.glass_color_mode_cold_description);
        } else {
            if (b != 3) {
                return false;
            }
            this.mTypeNameView.setText(R$string.glass_color_mode_warm_description);
        }
        this.mTypeIconView.setImageResource(R$drawable.ic_glasses_color_mode);
        this.mValueProgressBar.setVisibility(8);
        return true;
    }

    private boolean updateDisplayModeUI(byte b) {
        if (b == 0) {
            this.mTypeNameView.setText(R$string.glass_dp_mode_head_lock_description);
        } else if (b == 1) {
            this.mTypeNameView.setText(R$string.glass_dp_mode_space_lock_description);
        } else {
            if (b != 2) {
                return false;
            }
            this.mTypeNameView.setText(R$string.glass_dp_mode_auto_hide_description);
        }
        this.mTypeIconView.setImageResource(R$drawable.ic_glasses_display);
        this.mValueProgressBar.setVisibility(8);
        return true;
    }

    @Override // com.motorola.glass.IGlassManager
    public void onDeviceAttached(UsbDevice usbDevice) {
        stopCurrentDevice();
        UsbDeviceConnection usbDeviceConnectionOpenDevice = this.mUsbManager.openDevice(usbDevice);
        if (usbDeviceConnectionOpenDevice == null) {
            Log.d("PhantomGlassManager", "openDevice failed: " + usbDevice);
            return;
        }
        LinkedList linkedList = new LinkedList();
        for (int i = 0; i < usbDevice.getInterfaceCount(); i++) {
            UsbInterface usbInterface = usbDevice.getInterface(i);
            usbDeviceConnectionOpenDevice.claimInterface(usbInterface, true);
            linkedList.add(usbInterface);
        }
        USBDataReceiverThread uSBDataReceiverThread = new USBDataReceiverThread(usbDeviceConnectionOpenDevice, linkedList);
        this.mUSBDataReceiverThread = uSBDataReceiverThread;
        uSBDataReceiverThread.start();
        if (this.mUSBDataReceiverThread != null && this.mDisplay != null) {
            this.mUIHandler.sendEmptyMessage(4);
        }
        this.mReadyForProxy.addModeChooserListener(this.mModeChooserListener);
    }

    @Override // com.motorola.glass.IGlassManager
    public void onDeviceDetached(UsbDevice usbDevice) {
        stopCurrentDevice();
        this.mReadyForProxy.removeModeChooserListener(this.mModeChooserListener);
    }

    @Override // com.motorola.glass.IGlassManager
    public void onDisplayChanged(Display display) {
        this.mDisplay = display;
        detachedView();
        if (this.mUSBDataReceiverThread == null || this.mDisplay == null) {
            return;
        }
        this.mUIHandler.sendEmptyMessage(4);
    }
}
