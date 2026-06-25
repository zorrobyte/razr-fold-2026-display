package com.motorola.glass;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import com.motorola.taskbar.MotoFeature;
import com.motorola.taskbar.TaskBarServiceCallBack;
import com.motorola.taskbar.TaskBarServiceProxy;
import com.motorola.taskbar.util.DebugConfig;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class GlassesMonitor {
    private static final boolean DEBUG = DebugConfig.DEBUG_COMMON;
    private Context mContext;
    private final DisplayManager mDisplayManager;
    private HandlerThread mHandlerThread;
    private MotoFeature mMotoFeature;
    private PackageManager mPackageManager;
    private TaskBarServiceProxy mTaskBarServiceProxy;
    private UsbManager mUsbManager;
    private PendingIntent mUsbPermissionIntent;
    private WorkHandler mWorkHandler;
    private Map mGlassManagerMap = new HashMap();
    private boolean mInitialized = false;
    private Display mCurrentDisplay = null;
    private TaskBarServiceCallBack mTaskBarServiceCallBack = new TaskBarServiceCallBack() { // from class: com.motorola.glass.GlassesMonitor.1
        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void onDisplayReady(int i) {
            Display display = GlassesMonitor.this.mDisplayManager.getDisplay(i);
            if (display == null || !GlassesMonitor.this.isValidDisplay(display)) {
                return;
            }
            GlassesMonitor.this.mCurrentDisplay = display;
            GlassesMonitor glassesMonitor = GlassesMonitor.this;
            glassesMonitor.notifyDisplayChanged(glassesMonitor.mCurrentDisplay);
        }

        @Override // com.motorola.taskbar.TaskBarServiceCallBack
        public void onDisplayRemoved(int i) {
            if (GlassesMonitor.this.mCurrentDisplay == null || GlassesMonitor.this.mCurrentDisplay.getDisplayId() != i) {
                return;
            }
            GlassesMonitor.this.mCurrentDisplay = null;
            GlassesMonitor glassesMonitor = GlassesMonitor.this;
            glassesMonitor.notifyDisplayChanged(glassesMonitor.mCurrentDisplay);
        }
    };
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() { // from class: com.motorola.glass.GlassesMonitor.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.hashCode();
            switch (action) {
                case "android.hardware.usb.action.USB_DEVICE_ATTACHED":
                    UsbDevice usbDevice = (UsbDevice) intent.getParcelableExtra("device");
                    if (GlassesMonitor.DEBUG) {
                        Log.d("GlassManager", "Usb device attached: " + usbDevice);
                    }
                    IGlassManager iGlassManagerFindGlassManager = GlassesMonitor.this.findGlassManager(usbDevice);
                    if (iGlassManagerFindGlassManager != null) {
                        GlassesMonitor.this.onUsbGlassAttached(usbDevice, intent, iGlassManagerFindGlassManager, true);
                        break;
                    }
                    break;
                case "android.hardware.usb.action.USB_DEVICE_DETACHED":
                    UsbDevice usbDevice2 = (UsbDevice) intent.getParcelableExtra("device");
                    if (GlassesMonitor.DEBUG) {
                        Log.d("GlassManager", "Usb device detached: " + usbDevice2);
                    }
                    IGlassManager iGlassManagerFindGlassManager2 = GlassesMonitor.this.findGlassManager(usbDevice2);
                    if (iGlassManagerFindGlassManager2 != null) {
                        iGlassManagerFindGlassManager2.onDeviceDetached(usbDevice2);
                        break;
                    }
                    break;
                case "com.motorola.systemui.desk.action.GLASS_USB_PERMISSION":
                    UsbDevice usbDevice3 = (UsbDevice) intent.getParcelableExtra("device");
                    if (GlassesMonitor.DEBUG) {
                        Log.d("GlassManager", "Usb get permission: " + usbDevice3 + "; granted: " + intent.getBooleanExtra("permission", false));
                    }
                    IGlassManager iGlassManagerFindGlassManager3 = GlassesMonitor.this.findGlassManager(usbDevice3);
                    if (iGlassManagerFindGlassManager3 != null) {
                        GlassesMonitor.this.onUsbGlassAttached(usbDevice3, intent, iGlassManagerFindGlassManager3, false);
                        break;
                    }
                    break;
            }
        }
    };

    class WorkHandler extends Handler {
        public WorkHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            if (!GlassesMonitor.this.mPackageManager.hasSystemFeature("android.hardware.usb.host")) {
                Log.w("GlassManager", "Has NOT USB HOST feature");
                return;
            }
            GlassesMonitor.this.mTaskBarServiceProxy.addCallback(GlassesMonitor.this.mTaskBarServiceCallBack);
            Display[] displays = GlassesMonitor.this.mDisplayManager.getDisplays();
            if (displays != null) {
                int length = displays.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        break;
                    }
                    Display display = displays[i];
                    if (GlassesMonitor.this.isValidDisplay(display)) {
                        GlassesMonitor.this.mCurrentDisplay = display;
                        break;
                    }
                    i++;
                }
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED");
            intentFilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED");
            intentFilter.addAction("com.motorola.systemui.desk.action.GLASS_USB_PERMISSION");
            GlassesMonitor.this.mContext.registerReceiver(GlassesMonitor.this.mUsbReceiver, intentFilter, null, this, 2);
            HashMap<String, UsbDevice> deviceList = GlassesMonitor.this.mUsbManager.getDeviceList();
            if (deviceList != null) {
                for (UsbDevice usbDevice : deviceList.values()) {
                    if (GlassesMonitor.DEBUG) {
                        Log.d("GlassManager", "Init Usb device: " + usbDevice);
                    }
                    IGlassManager iGlassManagerFindGlassManager = GlassesMonitor.this.findGlassManager(usbDevice);
                    if (iGlassManagerFindGlassManager != null) {
                        GlassesMonitor.this.onUsbGlassAttached(usbDevice, null, iGlassManagerFindGlassManager, true);
                    }
                }
            }
        }
    }

    public GlassesMonitor(Context context, TaskBarServiceProxy taskBarServiceProxy, MotoFeature motoFeature) {
        this.mContext = context;
        this.mUsbManager = (UsbManager) context.getSystemService("usb");
        this.mDisplayManager = (DisplayManager) this.mContext.getSystemService("display");
        this.mPackageManager = this.mContext.getPackageManager();
        Intent intent = new Intent("com.motorola.systemui.desk.action.GLASS_USB_PERMISSION");
        intent.setPackage(context.getPackageName());
        this.mUsbPermissionIntent = PendingIntent.getBroadcast(context, 0, intent, 33554432);
        this.mTaskBarServiceProxy = taskBarServiceProxy;
        this.mMotoFeature = motoFeature;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IGlassManager findGlassManager(UsbDevice usbDevice) {
        IGlassManager phantomGlassManager;
        if (usbDevice == null) {
            return null;
        }
        int vendorId = usbDevice.getVendorId();
        int productId = usbDevice.getProductId();
        if (vendorId != 6127 || productId != 47124) {
            return null;
        }
        String name = PhantomGlassManager.class.getName();
        synchronized (this.mGlassManagerMap) {
            try {
                phantomGlassManager = (IGlassManager) this.mGlassManagerMap.get(name);
                if (phantomGlassManager == null) {
                    phantomGlassManager = new PhantomGlassManager(this.mContext, this.mCurrentDisplay);
                    this.mGlassManagerMap.put(name, phantomGlassManager);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return phantomGlassManager;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isValidDisplay(Display display) {
        return this.mMotoFeature.isValidHDMIDisplay(display) && MotoFeature.isDesktopModeDisplay(display) && !MotoFeature.isAppStreamMode(display);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyDisplayChanged(Display display) {
        synchronized (this.mGlassManagerMap) {
            try {
                for (IGlassManager iGlassManager : this.mGlassManagerMap.values()) {
                    if (iGlassManager != null) {
                        iGlassManager.onDisplayChanged(display);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUsbGlassAttached(UsbDevice usbDevice, Intent intent, IGlassManager iGlassManager, boolean z) {
        if (intent != null && intent.getBooleanExtra("permission", false)) {
            iGlassManager.onDeviceAttached(usbDevice);
        } else if (z) {
            this.mUsbManager.grantPermission(usbDevice);
            this.mUsbManager.requestPermission(usbDevice, this.mUsbPermissionIntent);
        }
    }

    public void init() {
        if (this.mInitialized) {
            if (DEBUG) {
                Log.d("GlassManager", "Repeat initialization");
                new Exception().printStackTrace();
                return;
            }
            return;
        }
        this.mInitialized = true;
        HandlerThread handlerThread = new HandlerThread("GlassManager");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        WorkHandler workHandler = new WorkHandler(this.mHandlerThread.getLooper());
        this.mWorkHandler = workHandler;
        workHandler.sendEmptyMessage(1);
    }
}
