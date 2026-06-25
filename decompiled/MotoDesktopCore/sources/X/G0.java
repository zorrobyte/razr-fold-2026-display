package X;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.app.role.RoleManager;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothCsipSetCoordinator;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothLeAudio;
import android.companion.AssociationRequest;
import android.companion.CompanionDeviceManager;
import android.companion.DeviceFilter;
import android.companion.virtual.VirtualDeviceManager;
import android.companion.virtual.VirtualDeviceParams;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.hardware.display.VirtualDisplayConfig;
import android.hardware.input.InputManager;
import android.hardware.input.InputSettings;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.location.LocationManager;
import android.media.AudioDeviceInfo;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRouter;
import android.net.InetAddresses;
import android.net.InterfaceConfiguration;
import android.net.LinkAddress;
import android.net.MacAddress;
import android.net.TetheringManager;
import android.net.Uri;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.DisplayInfo;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.Surface;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.inputmethod.InputMethodManager;
import com.motorola.android.dragdrop.IMotoDragDropRf;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.internal.app.MotoDesktopManager;
import com.motorola.internal.security.MotoSecurityUtils;
import com.motorola.mobiledesktop.core.IMotoDesktopManager;
import com.motorola.mobiledesktop.core.IRdpPimaryClipChangedListener;
import com.motorola.mobiledesktop.core.IRdpPointerStateChangedListener;
import com.motorola.mobiledesktop.core.IVirtualDisplayCallback;
import com.motorola.mobiledesktop.core.MotoDesktopService;
import com.motorola.mobiledesktop.core.R;
import com.motorola.mobiledesktop.core.TetheringRequest;
import com.motorola.mobiledesktop.core.bean.MotoActivityInfo;
import com.motorola.mobiledesktop.core.bean.MotoDisplay;
import com.motorola.mobiledesktop.core.bean.MotoDisplayMode;
import com.motorola.mobiledesktop.core.bean.MotoRunningTaskInfoNew;
import com.motorola.mobiledesktop.core.bean.MotoWifiDisplay;
import com.motorola.mobiledesktop.core.bean.NearbyShareDevice;
import com.motorola.mobiledesktop.core.tethering.InterfaceConfigurationParcel;
import com.motorola.mobiledesktop.core.uinput.AbsSetup;
import com.motorola.mobiledesktop.core.uinput.IClientToken;
import com.motorola.mobiledesktop.core.uinput.InputEvent;
import com.motorola.mobiledesktop.core.uinput.InputSetup;
import com.motorola.smartconnect.core.proxy.BaseProxy;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import motorola.core_services.cli.CLIManager;
import motorola.core_services.input.MotoInputManager;

/* JADX INFO: loaded from: classes.dex */
public final class G0 extends D {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ MotoDesktopService f270a;

    public G0(MotoDesktopService motoDesktopService) {
        this.f270a = motoDesktopService;
        attachInterface(this, IMotoDesktopManager.DESCRIPTOR);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void addActivityListenerVDM(int i2, int i3, a0.b bVar) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        g0.e eVarF = this.f270a.f2263U.f(i2, i3);
        if (eVarF == null || bVar == null) {
            return;
        }
        synchronized (eVarF.f2559c) {
            eVarF.f2559c.add(bVar);
            try {
                ((a0.a) bVar).f464a.linkToDeath(eVarF.f2562f, 0);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
            eVarF.a();
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void addDesktopIcon(String str, int i2, int i3, int i4, String str2, PendingIntent pendingIntent) {
        if (Q0.g()) {
            return;
        }
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "addDesktopIcon " + i3);
        try {
            int i5 = R.drawable.ic_copy;
            if (i3 != 0) {
                if (i3 == 1) {
                    i5 = R.drawable.ic_wifi_quality_great;
                } else if (i3 == 2) {
                    i5 = R.drawable.ic_wifi_quality_moderate;
                } else if (i3 == 3) {
                    i5 = R.drawable.ic_wifi_quality_poor;
                }
            }
            motoDesktopService.f2293s.addDesktopIcon(str, i2, i5, i4, str2, pendingIntent);
        } catch (Exception e2) {
            v0.a("MotoDesktopService", "addDesktopIcon, error:" + e2.toString());
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void addInterfaceToLocalNetwork(String str, List list) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "addInterfaceToLocalNetwork - iface = " + str);
        INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void addPrimaryClipChangedListener(IRdpPimaryClipChangedListener iRdpPimaryClipChangedListener) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "addPrimaryClipChangedListener");
        synchronized (this.f270a.f2291p) {
            MotoDesktopService motoDesktopService = this.f270a;
            motoDesktopService.f2282i = iRdpPimaryClipChangedListener;
            synchronized (motoDesktopService.p0) {
                try {
                    if (!motoDesktopService.o0) {
                        v0.a("MotoDesktopService", "addPrimaryClipChangedListenerInternal");
                        motoDesktopService.f2281h.addPrimaryClipChangedListener(motoDesktopService.p0);
                        motoDesktopService.o0 = true;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void addUniqueIdAssociation(String str, int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        DisplayInfo displayInfo = new DisplayInfo();
        Display display = motoDesktopService.f2300z.getDisplay(i2);
        if (display == null || !display.getDisplayInfo(displayInfo)) {
            return;
        }
        v0.a("MotoDesktopService", "addUniqueIdAssociation: " + str + " uniqueId: " + displayInfo.uniqueId);
        motoDesktopService.f2269b.addUniqueIdAssociationByPort(str, displayInfo.uniqueId);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int[] aidlFeature() {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        return new int[0];
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int aidlVersion() {
        return 126;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void bindDragDropService() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (i0.a.f2629a) {
            e0.g gVarA = e0.g.a(motoDesktopService.getApplicationContext());
            gVarA.getClass();
            v0.a("FwkDragDropManager", "bindDragDropService");
            if (gVarA.f2475c) {
                v0.a("FwkDragDropManager", "Enabled, don't need bind again");
                return;
            }
            gVarA.f2475c = true;
            Handler handler = new Handler(Looper.getMainLooper());
            gVarA.f2478f = handler;
            F.e eVar = gVarA.f2481i;
            handler.removeCallbacks(eVar);
            gVarA.f2478f.postDelayed(eVar, gVarA.f2477e == 0 ? 0L : 100L);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean bindPlatformService(ComponentName componentName, O o2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            if (componentName == null || o2 == null) {
                v0.a("MotoDesktopService", "bindPlatformService targetComponentName or platformConn is null");
                return false;
            }
            String shortString = componentName.toShortString();
            v0.a("MotoDesktopService", "bindPlatformService compName:" + shortString);
            if (motoDesktopService.l0.containsKey(shortString)) {
                v0.a("MotoDesktopService", "bindPlatformService already added");
                return true;
            }
            Intent intent = new Intent();
            intent.setComponent(componentName);
            F0 f02 = new F0(this, o2);
            try {
                ((M) o2).f278a.linkToDeath(motoDesktopService.q0, 0);
            } catch (RemoteException e2) {
                v0.c("MotoDesktopService", "bindPlatformService linkToDeath exception", e2);
            }
            return motoDesktopService.bindService(intent, f02, 1);
        } catch (Exception e3) {
            v0.c("MotoDesktopService", "bindPlatformService Exception", e3);
            return false;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final Bundle callContentProvider(String str, String str2, String str3, Bundle bundle) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        Bundle bundle2 = new Bundle();
        try {
            ContentProviderClient contentProviderClientAcquireContentProviderClient = motoDesktopService.getApplicationContext().getContentResolver().acquireContentProviderClient(str);
            return contentProviderClientAcquireContentProviderClient != null ? contentProviderClientAcquireContentProviderClient.call(str2, str3, bundle) : bundle2;
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "callContentProvider Exception ", e2);
            return bundle2;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void cancelAlarm(PendingIntent pendingIntent) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            motoDesktopService.f2251I.cancel(pendingIntent);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "cancelAlarm Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean cancelBondProcess(BluetoothDevice bluetoothDevice) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "cancelBondProcess");
        if (bluetoothDevice != null) {
            return bluetoothDevice.cancelBondProcess();
        }
        return false;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void cancelConnectWifiDisplay() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "cancelConnectWifiDisplay ");
        e0.A a2 = motoDesktopService.f2294t;
        a2.getClass();
        v0.e("A", "cancelConnectWifiDisplay");
        a2.d();
        u0 u0Var = a2.f2426e;
        if (u0Var != null) {
            try {
                s0 s0Var = (s0) u0Var;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.IWifiDisplayCallback");
                    s0Var.f331a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void closeVirtualDevice(int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (i2 < 0) {
            return;
        }
        w0.b(i3, "closeVirtualDevice virtualDeviceId=", "MotoDesktopService");
        if (i3 < 0) {
            v0.b("MotoDesktopService", "virtualDeviceId < 0");
            return;
        }
        F.f fVar = motoDesktopService.f2263U;
        fVar.getClass();
        g0.f fVar2 = new g0.f(i2, i3);
        g0.e eVar = (g0.e) ((ConcurrentHashMap) fVar.f124b).remove(fVar2);
        if (eVar != null) {
            eVar.f2557a.close();
            return;
        }
        v0.g("VirtualDeviceManager", "failed to find device for " + fVar2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void collapseStatusBar() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            Object systemService = motoDesktopService.getApplicationContext().getSystemService("statusbar");
            systemService.getClass().getMethod("collapsePanels", null).invoke(systemService, null);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "collapseStatusBar Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void connectWifiDisplay(MotoWifiDisplay motoWifiDisplay) {
        String strD;
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "connectWifiDisplay motoWifiDisplay=" + motoWifiDisplay);
        e0.A a2 = motoDesktopService.f2294t;
        a2.getClass();
        v0.e("A", "connectWifiDisplay motoWifiDisplay=" + motoWifiDisplay);
        if (motoWifiDisplay.mIsChromecast) {
            MediaRouter mediaRouter = a2.f2425d;
            int routeCount = mediaRouter.getRouteCount();
            new ArrayList();
            int i2 = 0;
            while (true) {
                if (i2 >= routeCount) {
                    break;
                }
                MediaRouter.RouteInfo routeAt = mediaRouter.getRouteAt(i2);
                if (routeAt.getSupportedTypes() == 7 && (strD = Y.r.D(routeAt, MediaRouter.RouteInfo.class.getName())) != null && strD.equals(motoWifiDisplay.mDeviceAddress)) {
                    v0.e("A", "connectWifiDisplay find and select chromecast");
                    a2.f2427f = motoWifiDisplay;
                    routeAt.select();
                    break;
                }
                i2++;
            }
        } else {
            a2.f2427f = motoWifiDisplay;
            a2.f2422a.connectWifiDisplay(motoWifiDisplay.mDeviceAddress);
        }
        e0.d dVarD = e0.d.d(a2.f2424c);
        a2.f2441u = dVarD;
        dVarD.a(a2.f2444x);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean createAudioRecord(int i2, int i3, int i4, int i5, int i6) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (motoDesktopService.r != null) {
            return true;
        }
        motoDesktopService.r = new AudioRecord(i2, i3, i4, i5, i6);
        w0.b(i2, "createAudioRecord audioSource = ", "MotoDesktopService");
        return motoDesktopService.r != null;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean createAudioTrackForDialCall(int i2, int i3, int i4) {
        AudioDeviceInfo audioDeviceInfo;
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "createAudioTrackForDialCall");
        if (motoDesktopService.f2255M != null) {
            releaseAudioTrackForDialCall();
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                motoDesktopService.f2255M = new AudioTrack(3, i2, i4, i3, AudioTrack.getMinBufferSize(i2, i4, i3), 1);
                AudioManager audioManager = motoDesktopService.f2250H;
                boolean z2 = Q0.f288a;
                AudioDeviceInfo[] devices = audioManager.getDevices(2);
                int length = devices.length;
                int i5 = 0;
                while (true) {
                    if (i5 >= length) {
                        audioDeviceInfo = null;
                        break;
                    }
                    audioDeviceInfo = devices[i5];
                    if (audioDeviceInfo.getType() == 18) {
                        break;
                    }
                    i5++;
                }
                if (audioDeviceInfo != null) {
                    v0.a("MotoDesktopService", "get telephonyDevice != null");
                    motoDesktopService.f2255M.setPreferredDevice(audioDeviceInfo);
                }
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                if (motoDesktopService.f2255M != null) {
                    v0.a("MotoDesktopService", "createAudioTrackForDialCall successful");
                    motoDesktopService.f2255M.setVolume(1.0f);
                    motoDesktopService.f2255M.play();
                }
                return true;
            } catch (Exception e2) {
                v0.a("MotoDesktopService", "createAudioTrackForDialCall " + e2.getMessage());
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            throw th;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean createAudioTrackForIpCall(int i2, int i3, int i4) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "createAudioTrackForIpCall");
        if (motoDesktopService.f2254L != null) {
            releaseAudioTrackForIpCall();
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                motoDesktopService.f2254L = motoDesktopService.f2250H.getCallUplinkInjectionAudioTrack(new AudioFormat.Builder().setSampleRate(i2).setEncoding(i3).setChannelMask(i4).build());
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                if (motoDesktopService.f2254L == null) {
                    return true;
                }
                v0.a("MotoDesktopService", "createAudioTrackForIpCall successful");
                motoDesktopService.f2254L.setVolume(1.0f);
                motoDesktopService.f2254L.play();
                return true;
            } catch (Exception e2) {
                v0.a("MotoDesktopService", "createAudioTrackForIpCall " + e2.getMessage());
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            throw th;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean createAudioTrackForMic(int i2, int i3, int i4) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "createAudioTrackForMic");
        if (motoDesktopService.f2253K != null) {
            releaseAudioTrackForMic();
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                motoDesktopService.f2253K = Q0.a(new AudioFormat.Builder().setSampleRate(i2).setEncoding(i3).setChannelMask(i4).build(), motoDesktopService.f2250H);
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                if (motoDesktopService.f2253K == null) {
                    return true;
                }
                v0.a("MotoDesktopService", "createAudioTrackForMic successful");
                motoDesktopService.f2253K.setVolume(1.0f);
                motoDesktopService.f2253K.play();
                return true;
            } catch (Exception e2) {
                v0.a("MotoDesktopService", "createAudioTrackForMic " + e2.getMessage());
                Binder.restoreCallingIdentity(jClearCallingIdentity);
                return false;
            }
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
            throw th;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean createBond(BluetoothDevice bluetoothDevice, int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "createBond: " + i2);
        if (bluetoothDevice != null) {
            return bluetoothDevice.createBond(i2);
        }
        return false;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int createVirtualDevice(int i2, VirtualDeviceParams virtualDeviceParams) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "createVirtualDevice associationId=" + i2 + " params=" + virtualDeviceParams);
        if (i2 < 0 || virtualDeviceParams == null) {
            v0.b("MotoDesktopService", "associationId = -1");
            return -1;
        }
        F.f fVar = motoDesktopService.f2263U;
        Binder.getCallingUid();
        VirtualDeviceManager.VirtualDevice virtualDeviceCreateVirtualDevice = ((VirtualDeviceManager) fVar.f123a).createVirtualDevice(i2, virtualDeviceParams);
        if (virtualDeviceCreateVirtualDevice == null) {
            v0.b("VirtualDeviceManager", "failed to createVirtualDevice");
            return -1;
        }
        int deviceId = virtualDeviceCreateVirtualDevice.getDeviceId();
        ((ConcurrentHashMap) fVar.f124b).put(new g0.f(i2, deviceId), new g0.e(virtualDeviceCreateVirtualDevice));
        return deviceId;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int createVirtualDisplay(InterfaceC0028l0 interfaceC0028l0, String str, int i2, int i3, int i4, Surface surface, int i5) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "createVirtualDisplay:" + str);
        d0.a aVar = new d0.a(interfaceC0028l0, 2);
        VirtualDisplay virtualDisplayCreateVirtualDisplay = this.f270a.f2267a.createVirtualDisplay(str, i2, i3, i4, surface, Y.r.K(i5), aVar, this.f270a.f2279g);
        int displayId = -1;
        if (virtualDisplayCreateVirtualDisplay != null) {
            if (virtualDisplayCreateVirtualDisplay.getDisplay() != null) {
                displayId = virtualDisplayCreateVirtualDisplay.getDisplay().getDisplayId();
                aVar.f2412b = displayId;
            }
            MotoDesktopService motoDesktopService = this.f270a;
            e0.b bVar = motoDesktopService.f2249G;
            J0 j02 = motoDesktopService.q0;
            synchronized (bVar.f2455f) {
                if (bVar.f2457h == null) {
                    bVar.f2457h = interfaceC0028l0;
                    try {
                        ((C0024j0) interfaceC0028l0).f325a.linkToDeath(j02, 0);
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                    bVar.f2456g.registerTaskStackListener(bVar.f2461l);
                }
                bVar.f2451b.put(Integer.valueOf(displayId), virtualDisplayCreateVirtualDisplay);
            }
        }
        w0.b(displayId, "createVirtualDisplay, displayId = ", "MotoDesktopService");
        return displayId;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int createVirtualDisplayForMultiUser(InterfaceC0034o0 interfaceC0034o0, String str, int i2, int i3, int i4, Surface surface, int i5) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "createVirtualDisplayForMultiUser:" + str + ", flags=" + i5);
        d0.a aVar = new d0.a(interfaceC0034o0, 0);
        VirtualDisplay virtualDisplayCreateVirtualDisplay = motoDesktopService.f2267a.createVirtualDisplay(str, i2, i3, i4, surface, i5, aVar, motoDesktopService.f2279g);
        int displayId = -1;
        if (virtualDisplayCreateVirtualDisplay != null) {
            if (virtualDisplayCreateVirtualDisplay.getDisplay() != null) {
                displayId = virtualDisplayCreateVirtualDisplay.getDisplay().getDisplayId();
                aVar.f2412b = displayId;
            }
            motoDesktopService.f2249G.e(interfaceC0034o0, motoDesktopService.q0, displayId, virtualDisplayCreateVirtualDisplay);
        }
        w0.b(displayId, "createVirtualDisplayForMultiUser, displayId = ", "MotoDesktopService");
        return displayId;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int createVirtualDisplayForMultiUserVDM(InterfaceC0034o0 interfaceC0034o0, int i2, int i3, String str, int i4, int i5, int i6, Surface surface, int i7) {
        return -1;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int createVirtualDisplayVDM(int i2, int i3, VirtualDisplayConfig virtualDisplayConfig, Rect rect, r0 r0Var) {
        Display display;
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        g0.e eVarF = motoDesktopService.f2263U.f(i2, i3);
        int displayId = -1;
        if (eVarF != null) {
            if (rect != null) {
                VirtualDisplayConfig.Builder builder = new VirtualDisplayConfig.Builder(virtualDisplayConfig.getName(), virtualDisplayConfig.getWidth(), virtualDisplayConfig.getHeight(), virtualDisplayConfig.getDensityDpi());
                builder.setFlags(virtualDisplayConfig.getFlags());
                builder.setSurface(virtualDisplayConfig.getSurface());
                builder.setDisplayCategories(virtualDisplayConfig.getDisplayCategories());
                builder.setRequestedRefreshRate(virtualDisplayConfig.getRequestedRefreshRate());
                builder.setUniqueId(virtualDisplayConfig.getUniqueId());
                builder.setDisplayIdToMirror(virtualDisplayConfig.getDisplayIdToMirror());
                builder.setWindowManagerMirroringEnabled(virtualDisplayConfig.isWindowManagerMirroringEnabled());
                builder.setProjectionRect(rect);
                virtualDisplayConfig = builder.build();
            }
            d0.a aVar = new d0.a(r0Var, 1);
            VirtualDisplay virtualDisplayCreateVirtualDisplay = eVarF.f2557a.createVirtualDisplay(virtualDisplayConfig, new g0.a(), aVar);
            if (virtualDisplayCreateVirtualDisplay != null && (display = virtualDisplayCreateVirtualDisplay.getDisplay()) != null) {
                try {
                    ((C0036p0) r0Var).f329a.linkToDeath(eVarF.f2562f, 0);
                } catch (RemoteException e2) {
                    e2.printStackTrace();
                }
                displayId = display.getDisplayId();
                eVarF.f2558b.put(Integer.valueOf(displayId), new g0.d(displayId, virtualDisplayCreateVirtualDisplay, ((C0036p0) r0Var).f329a));
                aVar.f2412b = displayId;
            }
        }
        motoDesktopService.f2249G.d(r0Var, motoDesktopService.q0);
        return displayId;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int createVirtualDisplayWithProjection(InterfaceC0034o0 interfaceC0034o0, String str, int i2, int i3, int i4, Rect rect, Surface surface, int i5) {
        VirtualDisplay virtualDisplayCreateVirtualDisplay;
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "createVirtualDisplayWithProjection:" + str + ", flags=" + i5);
        d0.a aVar = new d0.a(interfaceC0034o0, 0);
        try {
            virtualDisplayCreateVirtualDisplay = motoDesktopService.f2267a.createVirtualDisplay(str, i2, i3, i4, rect, surface, i5, aVar, motoDesktopService.f2279g);
        } catch (Error e2) {
            e2.printStackTrace();
            virtualDisplayCreateVirtualDisplay = null;
        }
        int displayId = -1;
        if (virtualDisplayCreateVirtualDisplay != null) {
            if (virtualDisplayCreateVirtualDisplay.getDisplay() != null) {
                displayId = virtualDisplayCreateVirtualDisplay.getDisplay().getDisplayId();
                aVar.f2412b = displayId;
            }
            motoDesktopService.f2249G.e(interfaceC0034o0, motoDesktopService.q0, displayId, virtualDisplayCreateVirtualDisplay);
        }
        w0.b(displayId, "createVirtualDisplayWithProjection, displayId = ", "MotoDesktopService");
        return displayId;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int createVirtualDisplayWithoutTask(r0 r0Var, String str, int i2, int i3, int i4, Rect rect, Surface surface, int i5) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "createVirtualDisplayWithoutTask IVirtualDisplayCallbackV4 name:" + str + ", flags=" + i5);
        d0.a aVar = new d0.a(r0Var, 1);
        int displayId = -1;
        try {
            MotoDesktopService motoDesktopService = this.f270a;
            VirtualDisplay virtualDisplayCreateVirtualDisplay = motoDesktopService.f2267a.createVirtualDisplay(str, i2, i3, i4, rect, surface, i5, aVar, motoDesktopService.f2279g);
            if (virtualDisplayCreateVirtualDisplay != null) {
                if (virtualDisplayCreateVirtualDisplay.getDisplay() != null) {
                    displayId = virtualDisplayCreateVirtualDisplay.getDisplay().getDisplayId();
                    aVar.f2412b = displayId;
                }
                MotoDesktopService motoDesktopService2 = this.f270a;
                motoDesktopService2.f2249G.d(r0Var, motoDesktopService2.q0);
                e0.b bVar = this.f270a.f2249G;
                synchronized (bVar.f2455f) {
                    bVar.f2451b.put(Integer.valueOf(displayId), virtualDisplayCreateVirtualDisplay);
                }
            }
        } catch (Error e2) {
            e2.printStackTrace();
        }
        w0.b(displayId, "createVirtualDisplayWithoutTask IVirtualDisplayCallbackV4, displayId = ", "MotoDesktopService");
        return displayId;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int createVirtualDisplayWithoutTaskVDM(r0 r0Var, int i2, int i3, String str, int i4, int i5, int i6, Rect rect, Surface surface, int i7) {
        return -1;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void deregisterOnPointerPositionChangedListener(Q q2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        motoDesktopService.f(q2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void deregisterOnPointerStateChangedListener(T t2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        motoDesktopService.g(t2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean desktopCommitText(int i2, CharSequence charSequence) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            return ((InputMethodManager) motoDesktopService.getSystemService("input_method")).desktopCommitText(i2, charSequence);
        } catch (NoSuchMethodError e2) {
            e2.printStackTrace();
            return false;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void dhcpRedirect(boolean z2, String str, int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "dhcpRedirect - enable = " + z2 + ", ip:" + str + ", port:" + i2);
        INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void disconnectBluetooth(String str) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        boolean z2 = Q0.f288a;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        try {
            Set<BluetoothDevice> bondedDevices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
            if (bondedDevices != null && !bondedDevices.isEmpty()) {
                for (BluetoothDevice bluetoothDevice : bondedDevices) {
                    if (str.equalsIgnoreCase(bluetoothDevice.getAddress()) && bluetoothDevice.isConnected()) {
                        bluetoothDevice.disconnect();
                        v0.a("Utils", "disconnectBluetooth BtAddress = " + str);
                    }
                }
            }
        } catch (SecurityException e2) {
            v0.a("Utils", "disconnectBluetooth e = " + e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void disconnectWifiDisplay() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "disconnectWifiDisplay ");
        motoDesktopService.f2294t.d();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean enableBle(boolean z2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        try {
            v0.b("MotoDesktopService", "enableBle enable = " + z2);
            return z2 ? BluetoothAdapter.getDefaultAdapter().enable() : BluetoothAdapter.getDefaultAdapter().disable();
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "enableBle Exception ", e2);
            return false;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void enableRdpcdrom(boolean z2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "enableRdpcdrom + enable = " + z2);
        if (z2) {
            motoDesktopService.f2292q.setCurrentFunctions(256L);
        } else {
            motoDesktopService.f2292q.setCurrentFunctions(0L);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean enableWiFi(boolean z2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            v0.a("MotoDesktopService", "enableWiFi enable = " + z2);
            return ((WifiManager) motoDesktopService.f2294t.f2424c.getSystemService("wifi")).setWifiEnabled(z2);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "enableWiFi Exception ", e2);
            return false;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void forceStopPackage(String str) {
        v0.a("MotoDesktopService", "forceStopPackage:" + str);
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        motoDesktopService.f2247E.forceStopPackage(str);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void forceStopPackageAsUser(String str, int i2) {
        v0.a("MotoDesktopService", "forceStopPackageAsUser:" + str + ", userId=" + i2);
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        motoDesktopService.f2247E.forceStopPackageAsUser(str, i2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void forgetWifiDisplay(String str) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "forgetWifiDisplay ");
        e0.A a2 = motoDesktopService.f2294t;
        a2.getClass();
        v0.e("A", "forgetWifiDisplay");
        a2.f2422a.forgetWifiDisplay(str);
        e0.v.b(a2.f2424c).a(str, new e0.u(str));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final Bundle getAppRestartConfigerBundle(int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
        activityOptionsMakeBasic.setLaunchWindowingMode(1);
        activityOptionsMakeBasic.setLaunchDisplayId(i2);
        return activityOptionsMakeBasic.toBundle();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getAudioRecordState() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        AudioRecord audioRecord = motoDesktopService.r;
        if (audioRecord == null) {
            return 0;
        }
        return audioRecord.getState();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final String getBluetoothAddress() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            return motoDesktopService.f2245C.getAdapter().getAddress();
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "getBluetoothAddress() Exception ", e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getCallState() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        int callState = motoDesktopService.f2299y.getCallState();
        w0.b(callState, "getCallState callState = ", "MotoDesktopService");
        return callState;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final Rect getCliCutoutSize() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            Rect cliDisplayCutout = CLIManager.getInstance(motoDesktopService.getApplicationContext()).getCliDisplayCutout();
            StringBuilder sb = new StringBuilder("getCliCutoutSize:");
            sb.append(cliDisplayCutout != null ? cliDisplayCutout : "null");
            v0.a("MotoDesktopService", sb.toString());
            return cliDisplayCutout;
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "getCliCutoutSize Exception ", e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final MotoWifiDisplay getConnectedWifiDisplay() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "getConnectedWifiDisplay ");
        return motoDesktopService.f2294t.e();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final MotoWifiDisplay getConnectingWifiDisplay() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "getConnectingWifiDisplay");
        e0.A a2 = motoDesktopService.f2294t;
        v0.a("A", "getConnectingWifiDisplay mSelectedWifiDisplay=" + a2.f2427f);
        return a2.f2427f;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final IBinder getCoreProxyBinder(String str) {
        v0.a("MotoDesktopService", "getCoreProxyBinder:" + str);
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        synchronized (this.f270a.f2276e0) {
            try {
                if (!this.f270a.f2276e0.containsKey(str)) {
                    MotoDesktopService motoDesktopService = this.f270a;
                    BaseProxy baseProxy = (BaseProxy) MotoDesktopService.b(motoDesktopService, motoDesktopService.getApplicationContext(), str);
                    if (baseProxy != null) {
                        baseProxy.onCreate();
                        this.f270a.f2276e0.put(str, baseProxy);
                    }
                }
                if (!this.f270a.f2276e0.containsKey(str) || this.f270a.f2276e0.get(str) == null) {
                    return null;
                }
                return ((BaseProxy) this.f270a.f2276e0.get(str)).onBinder();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final MotoDisplayMode getCurrentDisplayMode(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "getCurrentDisplayMode");
        Display display = ((DisplayManager) motoDesktopService.getSystemService("display")).getDisplay(i2);
        if (display == null) {
            v0.a("MotoDesktopService", "display == null");
            return null;
        }
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        return new MotoDisplayMode(displayInfo.logicalWidth, displayInfo.logicalHeight, displayInfo.refreshRateOverride);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean getCurrentUserLargePointerSetting() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return MotorolaSettings.Secure.getIntForUser(motoDesktopService.getContentResolver(), "accessibility_ready_for_large_pointer_icon", 1, -2) == 1;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getCurrentUserPointerSize() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return MotorolaSettings.Secure.getIntForUser(motoDesktopService.getContentResolver(), "desktop_pointer_icon_size", 1, -2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getDefaultDisplayDensity(int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        try {
            return WindowManagerGlobal.getWindowManagerService().getInitialDisplayDensity(i2);
        } catch (RemoteException unused) {
            return -1;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final float[] getDesiredDisplayModeSpecsRange(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            return motoDesktopService.f2300z.getDesiredDisplayModeSpecsRange(i2);
        } catch (Error | Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List getDesktopRestartModeByPackages(List list) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        return ActivityManager.getService().getDesktopRestartModeByPackages(list);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List getDesktopSupportedModes(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        ArrayList arrayList = new ArrayList();
        if (Q0.g()) {
            return arrayList;
        }
        for (Display.Mode mode : ((DisplayManager) motoDesktopService.getSystemService("display")).getDisplay(i2).getDesktopSupportedModes()) {
            arrayList.add(new MotoDisplayMode(mode.getPhysicalWidth(), mode.getPhysicalHeight(), mode.getRefreshRate()));
        }
        v0.a("MotoDesktopService", "getDesktopSupportedModes result :" + arrayList);
        return arrayList;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getDisplayUiMode(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        Display display = motoDesktopService.f2300z.getDisplay(i2);
        if (display == null) {
            return 0;
        }
        if (MotoDesktopManager.isDesktopMode(display)) {
            return 1;
        }
        return MotoDesktopManager.isMobileUiMode(display) ? 2 : 0;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getFieldValueByReflect(String str, String str2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        return Q0.d(str, str2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getFocusDisplayId() {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        try {
            return WindowManagerGlobal.getWindowManagerService().getFocusedDisplayId();
        } catch (Error e2) {
            e2.printStackTrace();
            return -1;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final Rect getFocusedWindowRect(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            return ((WindowManager) motoDesktopService.getSystemService("window")).getFocusedWindowRect(i2);
        } catch (NoSuchMethodError e2) {
            e2.printStackTrace();
            return new Rect();
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final Intent getIntent(PendingIntent pendingIntent) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        if (pendingIntent != null) {
            return pendingIntent.getIntent();
        }
        return null;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final float[] getLastMousePosition(int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        float[] fArr = new float[2];
        try {
            Bundle lastMousePosition = WindowManagerGlobal.getWindowManagerService().getLastMousePosition(i2);
            if (lastMousePosition != null) {
                fArr[0] = lastMousePosition.getFloat("mouse_x", 0.0f);
                fArr[1] = lastMousePosition.getFloat("mouse_y", 0.0f);
            }
        } catch (RemoteException | Error e2) {
            e2.printStackTrace();
        }
        return fArr;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final Intent getLaunchIntentForPackage(String str, int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.createContextAsUser(new UserHandle(i2), 0).getPackageManager().getLaunchIntentForPackage(str);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getLeaProfileStatus(BluetoothDevice bluetoothDevice) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.b("MotoDesktopService", "getLeaProfileStatus has been called");
        new ArrayList();
        BluetoothAdapter adapter = this.f270a.f2245C.getAdapter();
        if (adapter == null) {
            v0.b("MotoDesktopService", "getAdapter failed");
            return 0;
        }
        MotoDesktopService motoDesktopService = this.f270a;
        if (motoDesktopService.f2266Y == null) {
            if (!adapter.getProfileProxy(motoDesktopService.getApplicationContext(), this.f270a.V, 22)) {
                return 0;
            }
            synchronized (this.f270a.f2268a0) {
                try {
                    try {
                        this.f270a.f2268a0.wait();
                        if (this.f270a.f2266Y == null) {
                            return 0;
                        }
                    } catch (InterruptedException e2) {
                        v0.b("MotoDesktopService", "can not get lea profile proxy, reason is: " + e2);
                        return 0;
                    }
                } finally {
                }
            }
        }
        if (this.f270a.f2266Y.getConnectedDevices().contains(bluetoothDevice)) {
            return this.f270a.f2266Y.getConnectionState(bluetoothDevice);
        }
        return 0;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final MotoDisplay getMotoDisplay(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        Display display = ((DisplayManager) motoDesktopService.getSystemService("display")).getDisplay(i2);
        if (display != null) {
            return new MotoDisplay(display);
        }
        return null;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final MotoRunningTaskInfoNew getMotoRunningTaskInfo(ActivityManager.RunningTaskInfo runningTaskInfo) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        try {
            MotoRunningTaskInfoNew motoRunningTaskInfoNew = new MotoRunningTaskInfoNew();
            motoRunningTaskInfoNew.setRunningTaskInfo(runningTaskInfo);
            return motoRunningTaskInfoNew;
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "getMotoRunningTaskInfo Exception ", e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List getMotoRunningTaskInfos() {
        v0.a("MotoDesktopService", "getMotoRunningTaskInfos");
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            return Y.r.v(motoDesktopService);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "getMotoRunningTaskInfos error", e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List getMotoRunningTaskInfosByDisplayId(int i2) {
        v0.a("MotoDesktopService", "getMotoRunningTaskInfosByDisplayId displayId=" + i2);
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            return Y.r.w(motoDesktopService, i2);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "getMotoRunningTaskInfosByDisplayId error", e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final float getMotoSettingFloatWithType(int i2, String str, float f2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (i2 == 0) {
            return MotorolaSettings.Global.getFloat(motoDesktopService.getContentResolver(), str, f2);
        }
        if (i2 == 1) {
            return MotorolaSettings.Secure.getFloat(motoDesktopService.getContentResolver(), str, f2);
        }
        if (i2 != 2) {
            return -1.0f;
        }
        return MotorolaSettings.System.getFloat(motoDesktopService.getContentResolver(), str, f2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getMotoSettingIntegerWithType(int i2, String str, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (i2 == 0) {
            return MotorolaSettings.Global.getInt(motoDesktopService.getContentResolver(), str, i3);
        }
        if (i2 == 1) {
            return MotorolaSettings.Secure.getInt(motoDesktopService.getContentResolver(), str, i3);
        }
        if (i2 != 2) {
            return -1;
        }
        return MotorolaSettings.System.getInt(motoDesktopService.getContentResolver(), str, i3);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List getMyAssociations() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return ((CompanionDeviceManager) motoDesktopService.f2262T.f2538c).getMyAssociations();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final PackageInfo getPackageInfoAsUser(String str, int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "getPackageInfoAsUser:" + str + ", userId=" + i3);
        try {
            return motoDesktopService.getPackageManager().getPackageInfoAsUser(str, i2, i3);
        } catch (PackageManager.NameNotFoundException e2) {
            v0.c("MotoDesktopService", "getPackageInfoAsUser error:" + str, e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final String getPhysicalDisplayId(int i2) {
        DisplayAddress.Physical address;
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            Display display = motoDesktopService.f2300z.getDisplay(i2);
            return (display == null || (address = display.getAddress()) == null) ? "" : String.valueOf(address.getPhysicalDisplayId());
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "getPhysicalDisplayId Exception ", e2);
            return "";
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int[] getPhysicalDisplayInfo() {
        try {
            DisplayInfo displayInfo = new DisplayInfo();
            this.f270a.f2300z.getDisplay(0).getDisplayInfo(displayInfo);
            return new int[]{displayInfo.logicalWidth, displayInfo.logicalHeight};
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "getPhysicalDisplayInfo error:", e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getPointSpeed() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return InputSettings.getPointerSpeed(motoDesktopService.getApplicationContext());
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final ClipData getPrimaryClip() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2281h.getPrimaryClip();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final ClipDescription getPrimaryClipDescription() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2281h.getPrimaryClipDescription();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getR4PcPointerSize() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return MotorolaSettings.Secure.getIntForUser(motoDesktopService.getContentResolver(), "desktop_pointer_icon_size_remote", 1, -2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List getRecentTasksByDisplayId(int i2, int i3) {
        v0.a("MotoDesktopService", "getRecentTasksByDisplayId displayId=" + i2 + ", userId=" + i3);
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        try {
            return Y.r.x(i2, i3);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "getRecentTasksByDisplayId error", e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List getRunningTaskInfos() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            return Y.r.A(motoDesktopService);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "getRunningTaskInfos error", e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List getRunningTaskInfosByDisplayId(int i2) {
        v0.a("MotoDesktopService", "getRunningTaskInfosByDisplayId displayId=" + i2);
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            return Y.r.B(motoDesktopService, i2);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "getRunningTaskInfosByDisplayId error", e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List getRunningTasks() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            return ((ActivityManager) motoDesktopService.getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "getRunningTasks Exception ", e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final String getSerial() {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "getSerial");
        return Build.getSerial();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final SoftApConfiguration getSoftApConfiguration() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2244B.getSoftApConfiguration();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List getSupportedModes(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        ArrayList arrayList = new ArrayList();
        try {
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (Q0.g()) {
            return arrayList;
        }
        Display display = ((DisplayManager) motoDesktopService.getSystemService("display")).getDisplay(i2);
        if (display != null) {
            DisplayInfo displayInfo = new DisplayInfo();
            display.getDisplayInfo(displayInfo);
            for (Display.Mode mode : displayInfo.supportedModes) {
                arrayList.add(new MotoDisplayMode(mode.getPhysicalWidth(), mode.getPhysicalHeight(), mode.getRefreshRate()));
            }
        } else {
            v0.b("MotoDesktopService", "getSupportedModes display not found for " + i2);
        }
        v0.a("MotoDesktopService", "getSupportedModes result :" + arrayList);
        return arrayList;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final MotoRunningTaskInfoNew getTopTaskInfo(int i2) {
        MotoRunningTaskInfoNew motoRunningTaskInfoNew;
        ActivityManager.RunningTaskInfo topVisibleTask;
        w0.b(i2, "getTopTaskInfo displayId=", "MotoDesktopService");
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        MotoRunningTaskInfoNew motoRunningTaskInfoNew2 = null;
        try {
            try {
                topVisibleTask = ActivityTaskManager.getService().getTopVisibleTask(i2);
            } catch (Exception e2) {
                v0.c("MotoDesktopService", "getTopTaskInfo error", e2);
                return motoRunningTaskInfoNew2;
            }
        } catch (NoSuchMethodError e3) {
            e = e3;
            motoRunningTaskInfoNew = null;
        }
        if (topVisibleTask == null || topVisibleTask.baseIntent == null) {
            return null;
        }
        motoRunningTaskInfoNew = new MotoRunningTaskInfoNew();
        try {
            motoRunningTaskInfoNew.setRunningTaskInfo(topVisibleTask);
        } catch (NoSuchMethodError e4) {
            e = e4;
            v0.b("AppUtils", "getTopTaskInfo NoSuchMethodError:" + e.toString());
        }
        motoRunningTaskInfoNew2 = motoRunningTaskInfoNew;
        return motoRunningTaskInfoNew2;
        v0.b("AppUtils", "getTopTaskInfo NoSuchMethodError:" + e.toString());
        motoRunningTaskInfoNew2 = motoRunningTaskInfoNew;
        return motoRunningTaskInfoNew2;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final ActivityManager.RunningTaskInfo getTopVisbleTask(int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        try {
            return ActivityTaskManager.getService().getTopVisibleTask(i2);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "getTopVisbleTask Exception ", e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final long getUsbCurrentFunctions() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2292q.getCurrentFunctions();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final long getUsbWebcamFunction() {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        try {
            return UsbManager.class.getDeclaredField("FUNCTION_WEBCAM").getLong(null);
        } catch (Exception e2) {
            e2.printStackTrace();
            return 2147483648L;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getVirtualDisplayID(IVirtualDisplayCallback iVirtualDisplayCallback, String str, int i2, int i3, int i4, Surface surface, int i5) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "getVirtualDisplayID, Binder.getCallingPid() = " + Binder.getCallingPid());
        motoDesktopService.l();
        VirtualDisplay virtualDisplayCreateVirtualDisplay = motoDesktopService.f2267a.createVirtualDisplay(str, i2, i3, i4, surface, Y.r.K(i5));
        motoDesktopService.f2273d = virtualDisplayCreateVirtualDisplay;
        if (virtualDisplayCreateVirtualDisplay != null) {
            displayId = virtualDisplayCreateVirtualDisplay.getDisplay() != null ? motoDesktopService.f2273d.getDisplay().getDisplayId() : -1;
            motoDesktopService.f2275e = iVirtualDisplayCallback;
            try {
                iVirtualDisplayCallback.asBinder().linkToDeath(motoDesktopService.q0, 0);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
        w0.b(displayId, "getVirtualDisplayIDInternal, displayId = ", "MotoDesktopService");
        return displayId;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getVirtualDisplayIdV2(InterfaceC0028l0 interfaceC0028l0, String str, int i2, int i3, int i4, Surface surface, int i5) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        motoDesktopService.l();
        d0.a aVar = new d0.a(interfaceC0028l0, 2);
        VirtualDisplay virtualDisplayCreateVirtualDisplay = motoDesktopService.f2267a.createVirtualDisplay(str, i2, i3, i4, surface, Y.r.K(i5), aVar, motoDesktopService.f2279g);
        motoDesktopService.f2273d = virtualDisplayCreateVirtualDisplay;
        int displayId = -1;
        if (virtualDisplayCreateVirtualDisplay != null) {
            if (virtualDisplayCreateVirtualDisplay.getDisplay() != null) {
                displayId = motoDesktopService.f2273d.getDisplay().getDisplayId();
                aVar.f2412b = displayId;
            }
            motoDesktopService.f2277f = interfaceC0028l0;
            try {
                ((C0024j0) interfaceC0028l0).f325a.linkToDeath(motoDesktopService.q0, 0);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
        w0.b(displayId, "getVirtualDisplayIDInternalV2, displayId = ", "MotoDesktopService");
        return displayId;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int getVirtualDisplayIdV2VDM(InterfaceC0028l0 interfaceC0028l0, int i2, int i3, String str, int i4, int i5, int i6, Surface surface, int i7) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "getVirtualDisplayIdV2VDM");
        motoDesktopService.getClass();
        return -1;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void goToPermissionManageSetting(String str) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "goToPermissionManageSetting, packageName: " + str);
        Intent intent = new Intent("android.intent.action.MANAGE_APP_PERMISSIONS");
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.addFlags(268435456);
        motoDesktopService.startActivity(intent);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void goToSleepDisplayGroup(int i2, long j2, int i3, int i4) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "goToSleepDisplayGroup displayId: " + i2);
        motoDesktopService.f2246D.goToSleep(i2, j2, i3, i4);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void grantRuntimePermission(String str, String str2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "grantRuntimePermission ");
        motoDesktopService.f2294t.getClass();
        v0.g("A", "grantRuntimePermission remove the code for GTS");
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void grantUriPermission(Uri uri) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            v0.a("MotoDesktopService", "grantUriPermission uri:" + uri);
            motoDesktopService.getApplicationContext().grantUriPermission("com.motorola.mobiledesktop", uri, 1);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "grantUriPermission Exception", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void injectInputEvent(MotionEvent motionEvent, int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        if (motionEvent == null) {
            v0.g("MotoDesktopService", "injectInputEvent error, event is null");
        } else {
            motionEvent.setDisplayId(i2);
            InputManager.getInstance().injectInputEvent(motionEvent, 0);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void interfaceAddAddress(String str, String str2, int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "tetherApplyDnsInterfaces");
        INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management")).interfaceAddAddress(str, str2, i2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isAppCloneUser(int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "isAppCloneUser:" + i2);
        return UserHandle.isAppCloneUser(i2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isDeviceOwnerMode() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            if (motoDesktopService.f2252J.getDeviceOwnerComponentOnCallingUser() == null) {
                return false;
            }
            v0.a("MotoDesktopService", "DeviceOwner");
            return true;
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "isDeviceOwnerMode Exception ", e2);
            return false;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isDisplayRotatable(int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        try {
            boolean zIsDisplayRotatable = WindowManagerGlobal.getWindowManagerService().isDisplayRotatable(i2);
            v0.a("MotoDesktopService", "isDisplayRotatable:" + zIsDisplayRotatable);
            return zIsDisplayRotatable;
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "isDisplayRotatable Exception ", e2);
            return false;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isDpDisplayModeSupported() {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        return SystemProperties.getBoolean("ro.vendor.mot.hw.dynmic_dp_mode", false);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isFwkSupportDragDrop() {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        return i0.a.f2629a;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isLeaFeatureSupported(BluetoothDevice bluetoothDevice) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        BluetoothAdapter adapter = motoDesktopService.f2245C.getAdapter();
        if (adapter == null) {
            v0.b("MotoDesktopService", "getAdapter failed");
            return false;
        }
        new ArrayList();
        if (adapter.getSupportedProfiles().contains(22)) {
            v0.b("MotoDesktopService", "BT device support LeaProfile");
            return true;
        }
        v0.b("MotoDesktopService", "BT device unsupport LeaProfile");
        return false;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isMethodExists(String str, String str2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        return Q0.e(str, str2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isReadyForDisplay(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        Display display = motoDesktopService.f2300z.getDisplay(i2);
        if (display != null) {
            return MotoDesktopManager.isReadyForDisplay(motoDesktopService.getApplicationContext(), display);
        }
        return false;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isRestrictedDisplay(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        Display display = motoDesktopService.f2300z.getDisplay(i2);
        if (display != null) {
            return MotoDesktopManager.isRestrictedDisplay(motoDesktopService.getApplicationContext(), display);
        }
        return false;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isRoleHeld(String str) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return ((RoleManager) motoDesktopService.f2262T.f2539d).isRoleHeld(str);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isTetheringStarted() {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "isTetheringStarted");
        return INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management")).oemIsTetheringStarted();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isVDMSupported() {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        return true;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isWifiDisplayConnecting() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "isWifiDisplayConnecting ");
        e0.A a2 = motoDesktopService.f2294t;
        v0.a("A", "isWifiDisplayConnecting mSelectedWifiDisplay=" + a2.f2427f);
        return a2.f2427f != null;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean isWifiDisplaySwitchOn() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "isWifiDisplaySwitchOn");
        e0.A a2 = motoDesktopService.f2294t;
        a2.getClass();
        v0.a("A", "isWifiDisplaySwitchOn");
        return Settings.Global.getInt(a2.f2424c.getContentResolver(), "wifi_display_on", 0) == 1;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void launchActivity(Intent intent) {
        v0.a("MotoDesktopService", "launchActivity:" + intent);
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
            activityOptionsMakeBasic.setLaunchDisplayId(0);
            motoDesktopService.startActivity(intent, activityOptionsMakeBasic.toBundle());
        } catch (Exception e2) {
            v0.b("MotoDesktopService", "launchActivity:" + e2.toString());
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void launchActivityWithBundle(Intent intent, Bundle bundle) {
        v0.a("MotoDesktopService", "launchActivityWithBundle:" + intent);
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            motoDesktopService.startActivity(intent, bundle);
        } catch (Exception e2) {
            v0.b("MotoDesktopService", "launchActivityWithBundle:" + e2.toString());
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void launchActivityWithDisplayId(Intent intent, int i2) {
        v0.a("MotoDesktopService", "launchActivityWithDisplayId:" + intent);
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
            activityOptionsMakeBasic.setLaunchDisplayId(i2);
            Bundle bundle = activityOptionsMakeBasic.toBundle();
            bundle.putInt("android.activity.splashScreenStyle", 1);
            motoDesktopService.startActivity(intent, bundle);
        } catch (Exception e2) {
            v0.b("MotoDesktopService", "launchActivityWithDisplayId:" + e2.toString());
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final String[] listInterfaces() {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "listInterfaces");
        return INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management")).listInterfaces();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void listenCallStateChange(InterfaceC0039s interfaceC0039s, int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "add listenCallStateChange events = " + i2);
        synchronized (this.f270a.f2291p) {
            try {
                if (i2 == 0) {
                    this.f270a.f2243A = null;
                } else {
                    this.f270a.f2243A = interfaceC0039s;
                }
                MotoDesktopService motoDesktopService = this.f270a;
                motoDesktopService.f2299y.listen(motoDesktopService.t0, i2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void lockDevice() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            motoDesktopService.f2252J.lockNow();
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "Lock screen error!", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void moveRootTaskToDisplay(int i2, int i3) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "moveRootTaskToDisplay() taskId " + i2 + ", displayId " + i3);
        try {
            ActivityTaskManager.getService().moveRootTaskToDisplay(i2, i3);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "moveRootTaskToDisplay() Exception", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void notifyAuthorized(int i2, boolean z2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "notifyAuthorized");
        Context applicationContext = motoDesktopService.getApplicationContext();
        boolean z3 = Q0.f288a;
        try {
            Class<?> cls = Class.forName("com.motorola.internal.app.MotoDesktopManager");
            cls.getDeclaredMethod("notifyAuthorized", Integer.TYPE, Boolean.TYPE).invoke(applicationContext.getSystemService(cls), Integer.valueOf(i2), Boolean.valueOf(z2));
        } catch (Exception e2) {
            v0.g("Utils", "notifyFWAuthorized error:" + e2.toString());
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final WindowManager.LayoutParams obtainPreventFocusWindowLayoutParams() {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        try {
            layoutParams.motoPrivateFlags = getFieldValueByReflect("android.view.WindowManager$LayoutParams", "MOTO_PRIVATE_FLAG_PREVENT_SWITCH_DISPLAY_FOCUS");
        } catch (RemoteException e2) {
            v0.c("MotoDesktopService", "obtainPreventFocusWindowLayoutParams Exception!", e2);
        }
        return layoutParams;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void onReadyForDrop(long j2, ClipData clipData, int i2, int i3, int i4) {
        IMotoDragDropRf iMotoDragDropRf;
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (i0.a.f2629a) {
            e0.g gVarA = e0.g.a(motoDesktopService.getApplicationContext());
            if (!gVarA.f2476d || (iMotoDragDropRf = gVarA.f2474b) == null) {
                return;
            }
            try {
                iMotoDragDropRf.onReadyForDrop(j2, clipData, i2, i3, i4);
            } catch (RemoteException e2) {
                v0.h("FwkDragDropManager", "onReadyForDrop", e2);
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void putIntToMotorolaSettings(String str, int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "putIntToMotorolaSettings - key:" + str + ", value:" + i2);
        try {
            MotorolaSettings.Global.putInt(motoDesktopService.getApplicationContext().getContentResolver(), str, i2);
        } catch (Exception e2) {
            v0.a("MotoDesktopService", "putIntToMotorolaSettings key:" + str + ", error:" + e2.toString());
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void putIntToMotorolaSystemSettings(String str, int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "putIntToMotorolaSystemSettings start");
        MotorolaSettings.System.putInt(motoDesktopService.getContentResolver(), str, i2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void putMotoSettingFloatWithType(int i2, String str, float f2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "type:" + i2 + "   key:" + str + "  value:" + f2);
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 != 2) {
                    return;
                }
            }
            MotorolaSettings.System.putFloat(motoDesktopService.getContentResolver(), str, f2);
        }
        MotorolaSettings.Global.putFloat(motoDesktopService.getContentResolver(), str, f2);
        MotorolaSettings.Secure.putFloat(motoDesktopService.getContentResolver(), str, f2);
        MotorolaSettings.System.putFloat(motoDesktopService.getContentResolver(), str, f2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void putMotoSettingIntegerWithType(int i2, String str, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (i2 != 0) {
            if (i2 != 1) {
                if (i2 != 2) {
                    return;
                }
            }
            MotorolaSettings.System.putInt(motoDesktopService.getContentResolver(), str, i3);
        }
        MotorolaSettings.Global.putInt(motoDesktopService.getContentResolver(), str, i3);
        MotorolaSettings.Secure.putInt(motoDesktopService.getContentResolver(), str, i3);
        MotorolaSettings.System.putInt(motoDesktopService.getContentResolver(), str, i3);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void putMotoSettingString(String str, String str2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "putMotoSettingString start");
        MotorolaSettings.Global.putString(motoDesktopService.getContentResolver(), str, str2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void putMotoSettingStringWithType(int i2, String str, String str2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (i2 == 0) {
            MotorolaSettings.Global.putString(motoDesktopService.getContentResolver(), str, str2);
        } else if (i2 == 1) {
            MotorolaSettings.Secure.putString(motoDesktopService.getContentResolver(), str, str2);
        } else {
            if (i2 != 2) {
                return;
            }
            MotorolaSettings.System.putString(motoDesktopService.getContentResolver(), str, str2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void putSystemSettingIntWithType(int i2, String str, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (i2 == 0) {
            Settings.Global.putInt(motoDesktopService.getContentResolver(), str, i3);
        } else if (i2 == 1) {
            Settings.Secure.putInt(motoDesktopService.getContentResolver(), str, i3);
        } else {
            if (i2 != 2) {
                return;
            }
            Settings.System.putInt(motoDesktopService.getContentResolver(), str, i3);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void putSystemSettingString(String str, String str2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "putSystemSettingString start");
        Settings.Global.putString(motoDesktopService.getContentResolver(), str, str2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int[] queryDesktopIcon(String str, int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "queryDesktopIcon - slot:" + str + " displayId:" + i2);
        try {
            Cursor cursorQuery = motoDesktopService.getContentResolver().query(Uri.parse("content://com.motorola.systemui.desk.SystemIconProvider/IconPosition/" + str + "/" + i2), null, null, null);
            int[] iArr = new int[5];
            if (cursorQuery.moveToFirst()) {
                v0.a("TaskBarIcon", "getCount" + cursorQuery.getCount());
                try {
                    try {
                        iArr[0] = cursorQuery.getInt(cursorQuery.getColumnIndex("displayId"));
                        iArr[1] = cursorQuery.getInt(cursorQuery.getColumnIndex("left"));
                        iArr[2] = cursorQuery.getInt(cursorQuery.getColumnIndex("top"));
                        iArr[3] = cursorQuery.getInt(cursorQuery.getColumnIndex("right"));
                        iArr[4] = cursorQuery.getInt(cursorQuery.getColumnIndex("bottom"));
                        return iArr;
                    } catch (Exception unused) {
                    }
                } finally {
                    cursorQuery.close();
                }
            }
        } catch (Exception e2) {
            v0.a("MotoDesktopService", "queryDesktopIcon, error:" + e2.toString());
        }
        return null;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List queryIntentActivitiesAsUser(Intent intent, int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "queryIntentActivitiesAsUser:" + intent + ", userId=" + i3);
        try {
            return motoDesktopService.getPackageManager().queryIntentActivitiesAsUser(intent, i2, i3);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "queryIntentActivitiesAsUser error", e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List queryIntentActivitiesByPaging(Intent intent, int i2, int i3, int i4, int i5) {
        int i6;
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "queryIntentActivitiesByPaging:" + intent + ", userId=" + i3 + ",page=" + i4 + ",pageSize=" + i5);
        try {
            List listQueryIntentActivitiesAsUser = motoDesktopService.getPackageManager().queryIntentActivitiesAsUser(intent, i2, i3);
            int size = listQueryIntentActivitiesAsUser == null ? 0 : listQueryIntentActivitiesAsUser.size();
            if (size != 0 && (i6 = i4 * i5) <= size) {
                int i7 = (i4 + 1) * i5;
                if (i7 <= size) {
                    size = i7;
                }
                return new ArrayList(listQueryIntentActivitiesAsUser.subList(i6, size));
            }
            return new ArrayList();
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "queryIntentActivitiesByPaging error", e2);
            return null;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List queryMotoActivityInfoList(Intent intent, int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "queryMotoActivityInfoList:" + intent + ", userId=" + i3);
        ArrayList arrayList = new ArrayList();
        try {
            List<ResolveInfo> listQueryIntentActivitiesAsUser = motoDesktopService.getPackageManager().queryIntentActivitiesAsUser(intent, i2, i3);
            if (listQueryIntentActivitiesAsUser != null) {
                for (ResolveInfo resolveInfo : listQueryIntentActivitiesAsUser) {
                    MotoActivityInfo motoActivityInfo = new MotoActivityInfo();
                    motoActivityInfo.setPackageName(resolveInfo.activityInfo.packageName);
                    motoActivityInfo.setName(resolveInfo.activityInfo.name);
                    motoActivityInfo.setActivityInfoLable(resolveInfo.activityInfo.loadLabel(motoDesktopService.getPackageManager()).toString());
                    arrayList.add(motoActivityInfo);
                }
            }
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "queryMotoActivityInfoList error", e2);
        }
        return arrayList;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List queryWifiDisplayDevice() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "queryWifiDisplayDevice");
        return motoDesktopService.f2294t.g();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int readAudioRecordData(byte[] bArr, int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        AudioRecord audioRecord = motoDesktopService.r;
        if (audioRecord == null) {
            return -1;
        }
        return audioRecord.read(bArr, i2, i3);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean reconnectBluetoothProfile(List list) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        if (list.isEmpty() || list.size() < 2) {
            v0.a("MotoDesktopService", "leAudio device is null, reConnectBluetoothProfile error");
            return false;
        }
        BluetoothDevice bluetoothDevice = (BluetoothDevice) list.get(0);
        BluetoothDevice bluetoothDevice2 = (BluetoothDevice) list.get(1);
        if (bluetoothDevice == null || bluetoothDevice2 == null) {
            v0.a("MotoDesktopService", "fail to get lea device,stop connect to BluetoothProfile");
            return false;
        }
        v0.a("MotoDesktopService", "le1 unbind device address is: " + bluetoothDevice.getAddress() + ", le2 unbind device address is: " + bluetoothDevice2.getAddress());
        BluetoothAdapter adapter = this.f270a.f2245C.getAdapter();
        if (adapter == null) {
            v0.b("MotoDesktopService", "getAdapter failed");
            return false;
        }
        new ArrayList();
        List supportedProfiles = adapter.getSupportedProfiles();
        MotoDesktopService motoDesktopService = this.f270a;
        BluetoothLeAudio bluetoothLeAudio = motoDesktopService.f2266Y;
        if (bluetoothLeAudio == null) {
            if (!MotoDesktopService.a(motoDesktopService, adapter, motoDesktopService.V, supportedProfiles, 22)) {
                return false;
            }
            synchronized (this.f270a.f2268a0) {
                v0.b("MotoDesktopService", "stop leaudio");
                try {
                    this.f270a.f2268a0.wait();
                    if (this.f270a.f2266Y != null) {
                        v0.b("MotoDesktopService", "disable LeaProfile");
                        this.f270a.f2266Y.setConnectionPolicy(bluetoothDevice, 0);
                        this.f270a.f2266Y.setConnectionPolicy(bluetoothDevice2, 0);
                        adapter.closeProfileProxy(22, this.f270a.f2266Y);
                        v0.b("MotoDesktopService", "disable LeaProfile success, lea status is: " + adapter.getProfileConnectionState(22));
                    }
                } catch (InterruptedException e2) {
                    v0.b("MotoDesktopService", "can not get lea profile proxy, reason is: " + e2);
                    return false;
                }
            }
        } else if (bluetoothLeAudio.getConnectionPolicy(bluetoothDevice) != 0 && this.f270a.f2266Y.getConnectionPolicy(bluetoothDevice2) != 0) {
            v0.b("MotoDesktopService", "disable LeaProfile");
            this.f270a.f2266Y.setConnectionPolicy(bluetoothDevice, 0);
            this.f270a.f2266Y.setConnectionPolicy(bluetoothDevice2, 0);
            adapter.closeProfileProxy(22, this.f270a.f2266Y);
            v0.b("MotoDesktopService", "disable LeaProfile success, lea status is: " + adapter.getProfileConnectionState(22));
        }
        MotoDesktopService motoDesktopService2 = this.f270a;
        BluetoothCsipSetCoordinator bluetoothCsipSetCoordinator = motoDesktopService2.Z;
        if (bluetoothCsipSetCoordinator == null) {
            if (!MotoDesktopService.a(motoDesktopService2, adapter, motoDesktopService2.V, supportedProfiles, 25)) {
                return false;
            }
            synchronized (this.f270a.f2268a0) {
                v0.b("MotoDesktopService", "stop csipSetCoordinatorProfile");
                try {
                    this.f270a.f2268a0.wait();
                    if (this.f270a.Z != null) {
                        v0.b("MotoDesktopService", "disable CsipSetCoordinatorProfile");
                        this.f270a.Z.setConnectionPolicy(bluetoothDevice, 0);
                        this.f270a.Z.setConnectionPolicy(bluetoothDevice2, 0);
                        adapter.closeProfileProxy(25, this.f270a.Z);
                        v0.b("MotoDesktopService", "disable CsipSetCoordinatorProfile success, CsipSetCoordinator status is: " + adapter.getProfileConnectionState(25));
                    }
                } catch (InterruptedException e3) {
                    v0.b("MotoDesktopService", "can not get CsipSetCoordinator profile proxy, reason is: " + e3);
                    return false;
                }
            }
        } else if (bluetoothCsipSetCoordinator.getConnectionPolicy(bluetoothDevice) != 0 && this.f270a.Z.getConnectionPolicy(bluetoothDevice2) != 0) {
            v0.b("MotoDesktopService", "disable CsipSetCoordinatorProfile");
            this.f270a.Z.setConnectionPolicy(bluetoothDevice, 0);
            this.f270a.Z.setConnectionPolicy(bluetoothDevice2, 0);
            adapter.closeProfileProxy(25, this.f270a.Z);
            v0.b("MotoDesktopService", "disable CsipSetCoordinatorProfile success, CsipSetCoordinator status is: " + adapter.getProfileConnectionState(25));
        }
        MotoDesktopService motoDesktopService3 = this.f270a;
        BluetoothA2dp bluetoothA2dp = motoDesktopService3.f2264W;
        if (bluetoothA2dp == null) {
            if (!MotoDesktopService.a(motoDesktopService3, adapter, motoDesktopService3.V, supportedProfiles, 2)) {
                return false;
            }
            synchronized (this.f270a.f2268a0) {
                v0.b("MotoDesktopService", "start a2dp");
                try {
                    this.f270a.f2268a0.wait();
                    if (this.f270a.f2264W != null) {
                        v0.b("MotoDesktopService", "enable A2dpProfile");
                        this.f270a.f2264W.setConnectionPolicy(bluetoothDevice, 100);
                        v0.b("MotoDesktopService", "enable A2dpProfile success");
                    }
                } catch (InterruptedException e4) {
                    v0.b("MotoDesktopService", "can not get a2dp profile proxy, reason is: " + e4);
                    return false;
                }
            }
        } else if (bluetoothA2dp.getConnectionPolicy(bluetoothDevice) != 100) {
            v0.b("MotoDesktopService", "enable A2dpProfile");
            this.f270a.f2264W.setConnectionPolicy(bluetoothDevice, 100);
            v0.b("MotoDesktopService", "enable A2dpProfile success");
        }
        MotoDesktopService motoDesktopService4 = this.f270a;
        BluetoothHeadset bluetoothHeadset = motoDesktopService4.f2265X;
        if (bluetoothHeadset == null) {
            if (!MotoDesktopService.a(motoDesktopService4, adapter, motoDesktopService4.V, supportedProfiles, 1)) {
                return false;
            }
            synchronized (this.f270a.f2268a0) {
                v0.b("MotoDesktopService", "start headset");
                try {
                    this.f270a.f2268a0.wait();
                    if (this.f270a.f2265X != null) {
                        v0.b("MotoDesktopService", "enable HeadsetProfile");
                        this.f270a.f2265X.setConnectionPolicy(bluetoothDevice, 100);
                        v0.b("MotoDesktopService", "enable HeadsetProfile success");
                    }
                } catch (InterruptedException e5) {
                    v0.b("MotoDesktopService", "can not get headset profile proxy, reason is: " + e5);
                    return false;
                }
            }
        } else if (bluetoothHeadset.getConnectionPolicy(bluetoothDevice) != 100) {
            v0.b("MotoDesktopService", "enable HeadsetProfile");
            this.f270a.f2265X.setConnectionPolicy(bluetoothDevice, 100);
            v0.b("MotoDesktopService", "enable HeadsetProfile success");
        }
        return true;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean reconnectLeaProfile(List list) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        if (list.isEmpty() || list.size() < 2) {
            v0.a("MotoDesktopService", "leAudio device is null, reConnectLeaProfile error");
            return false;
        }
        BluetoothDevice bluetoothDevice = (BluetoothDevice) list.get(0);
        BluetoothDevice bluetoothDevice2 = (BluetoothDevice) list.get(1);
        if (bluetoothDevice == null || bluetoothDevice2 == null) {
            v0.a("MotoDesktopService", "fail to get lea device,stop connect to LeaProfile");
            return false;
        }
        v0.a("MotoDesktopService", "le1 bind device address is: " + bluetoothDevice.getAddress() + ", le2 bind device address is: " + bluetoothDevice2.getAddress());
        BluetoothAdapter adapter = this.f270a.f2245C.getAdapter();
        if (adapter == null) {
            v0.b("MotoDesktopService", "getAdapter failed");
            return false;
        }
        new ArrayList();
        List supportedProfiles = adapter.getSupportedProfiles();
        MotoDesktopService motoDesktopService = this.f270a;
        BluetoothA2dp bluetoothA2dp = motoDesktopService.f2264W;
        if (bluetoothA2dp == null) {
            if (!MotoDesktopService.a(motoDesktopService, adapter, motoDesktopService.V, supportedProfiles, 2)) {
                return false;
            }
            synchronized (this.f270a.f2268a0) {
                v0.b("MotoDesktopService", "stop a2dp");
                try {
                    this.f270a.f2268a0.wait();
                    if (this.f270a.f2264W != null) {
                        v0.b("MotoDesktopService", "disable A2dpProfile");
                        this.f270a.f2264W.setConnectionPolicy(bluetoothDevice, 0);
                        adapter.closeProfileProxy(2, this.f270a.f2264W);
                        v0.b("MotoDesktopService", "disable A2dpProfile success");
                    }
                } catch (InterruptedException e2) {
                    v0.b("MotoDesktopService", "can not get a2dp profile proxy, reason is: " + e2);
                    return false;
                }
            }
        } else if (bluetoothA2dp.getConnectionPolicy(bluetoothDevice) != 0) {
            v0.b("MotoDesktopService", "disable A2dpProfile");
            this.f270a.f2264W.setConnectionPolicy(bluetoothDevice, 0);
            adapter.closeProfileProxy(2, this.f270a.f2264W);
            v0.b("MotoDesktopService", "disable A2dpProfile success");
        }
        MotoDesktopService motoDesktopService2 = this.f270a;
        BluetoothHeadset bluetoothHeadset = motoDesktopService2.f2265X;
        if (bluetoothHeadset == null) {
            if (!MotoDesktopService.a(motoDesktopService2, adapter, motoDesktopService2.V, supportedProfiles, 1)) {
                return false;
            }
            synchronized (this.f270a.f2268a0) {
                v0.b("MotoDesktopService", "stop headset");
                try {
                    this.f270a.f2268a0.wait();
                    if (this.f270a.f2265X != null) {
                        v0.b("MotoDesktopService", "disable HeadsetProfile");
                        this.f270a.f2265X.setConnectionPolicy(bluetoothDevice, 0);
                        adapter.closeProfileProxy(1, this.f270a.f2265X);
                        v0.b("MotoDesktopService", "disable HeadsetProfile success");
                    }
                } catch (InterruptedException e3) {
                    v0.b("MotoDesktopService", "can not get headset profile proxy, reason is: " + e3);
                    return false;
                }
            }
        } else if (bluetoothHeadset.getConnectionPolicy(bluetoothDevice) != 0) {
            v0.b("MotoDesktopService", "disable HeadsetProfile");
            this.f270a.f2265X.setConnectionPolicy(bluetoothDevice, 0);
            adapter.closeProfileProxy(1, this.f270a.f2265X);
            v0.b("MotoDesktopService", "disable HeadsetProfile success");
        }
        int bondState = bluetoothDevice.getBondState();
        int bondState2 = bluetoothDevice2.getBondState();
        v0.a("MotoDesktopService", "bind: le1 device bond state is: " + bondState + ", le2 device bond state is: " + bondState2);
        if (bondState == 10) {
            v0.a("MotoDesktopService", "create bond to LE1");
            bluetoothDevice.createBond(2);
            v0.a("MotoDesktopService", "create bond to LE1 success");
        }
        if (bondState2 == 10) {
            v0.a("MotoDesktopService", "create bond to LE2");
            bluetoothDevice2.createBond(2);
            v0.a("MotoDesktopService", "create bond to LE2 success");
        }
        MotoDesktopService motoDesktopService3 = this.f270a;
        BluetoothLeAudio bluetoothLeAudio = motoDesktopService3.f2266Y;
        if (bluetoothLeAudio == null) {
            if (MotoDesktopService.a(motoDesktopService3, adapter, motoDesktopService3.V, supportedProfiles, 22)) {
                synchronized (this.f270a.f2268a0) {
                    v0.b("MotoDesktopService", "start leaudio");
                    try {
                        this.f270a.f2268a0.wait();
                        if (this.f270a.f2266Y != null) {
                            v0.b("MotoDesktopService", "enable LeaudioProfile");
                            this.f270a.f2266Y.setConnectionPolicy(bluetoothDevice, 100);
                            this.f270a.f2266Y.setConnectionPolicy(bluetoothDevice2, 100);
                            v0.b("MotoDesktopService", "enable LeaudioProfile success, lea status is: " + adapter.getProfileConnectionState(22));
                        }
                    } catch (InterruptedException e4) {
                        v0.b("MotoDesktopService", "can not get leaudio profile proxy, reason is: " + e4);
                        return false;
                    }
                }
            }
        } else if (bluetoothLeAudio.getConnectionPolicy(bluetoothDevice) != 100 && this.f270a.f2266Y.getConnectionPolicy(bluetoothDevice2) != 100) {
            v0.b("MotoDesktopService", "enable LeaudioProfile");
            this.f270a.f2266Y.setConnectionPolicy(bluetoothDevice, 100);
            this.f270a.f2266Y.setConnectionPolicy(bluetoothDevice2, 100);
            v0.b("MotoDesktopService", "enable LeaudioProfile success, lea status is: " + adapter.getProfileConnectionState(22));
        }
        MotoDesktopService motoDesktopService4 = this.f270a;
        BluetoothCsipSetCoordinator bluetoothCsipSetCoordinator = motoDesktopService4.Z;
        if (bluetoothCsipSetCoordinator == null) {
            if (!MotoDesktopService.a(motoDesktopService4, adapter, motoDesktopService4.V, supportedProfiles, 25)) {
                return false;
            }
            synchronized (this.f270a.f2268a0) {
                v0.b("MotoDesktopService", "start csipsetcoordinator");
                try {
                    this.f270a.f2268a0.wait();
                    if (this.f270a.Z != null) {
                        v0.b("MotoDesktopService", "enable csipsetcoordinator");
                        this.f270a.Z.setConnectionPolicy(bluetoothDevice, 100);
                        this.f270a.Z.setConnectionPolicy(bluetoothDevice2, 100);
                        v0.b("MotoDesktopService", "enable csipsetcoordinator success, csipsetcoordinator status is: " + adapter.getProfileConnectionState(25));
                    }
                } catch (InterruptedException e5) {
                    v0.b("MotoDesktopService", "can not get csipsetcoordinator profile proxy, reason is: " + e5);
                    return false;
                }
            }
        } else if (bluetoothCsipSetCoordinator.getConnectionPolicy(bluetoothDevice) != 100 && this.f270a.Z.getConnectionPolicy(bluetoothDevice2) != 100) {
            v0.b("MotoDesktopService", "enable csipsetcoordinator");
            this.f270a.Z.setConnectionPolicy(bluetoothDevice, 100);
            this.f270a.Z.setConnectionPolicy(bluetoothDevice2, 100);
            v0.b("MotoDesktopService", "enable csipsetcoordinator success, csipsetcoordinator status is: " + adapter.getProfileConnectionState(25));
        }
        return true;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void registerContentObserver(Uri uri, boolean z2, int i2, InterfaceC0041u interfaceC0041u) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        MotoDesktopService motoDesktopService = this.f270a;
        motoDesktopService.getClass();
        if (interfaceC0041u == null) {
            v0.b("MotoDesktopService", "iContentObserver is null");
            return;
        }
        x0 x0Var = new x0(motoDesktopService, motoDesktopService.f2279g, interfaceC0041u);
        try {
            ((C0040t) interfaceC0041u).f332a.linkToDeath(new y0(motoDesktopService, interfaceC0041u), 0);
        } catch (RemoteException unused) {
        }
        motoDesktopService.getContentResolver().registerContentObserver(uri, z2, x0Var, i2);
        synchronized (motoDesktopService.r0) {
            try {
                ArrayList arrayList = (ArrayList) motoDesktopService.r0.get(((C0040t) interfaceC0041u).f332a);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    motoDesktopService.r0.put(((C0040t) interfaceC0041u).f332a, arrayList);
                }
                arrayList.add(x0Var);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void registerDesktopInputMethodListener(InterfaceC0046z interfaceC0046z) {
        synchronized (this.f270a.f2257O) {
            this.f270a.f2257O.add(interfaceC0046z);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void registerDragDropCallback(C c2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (i0.a.f2629a) {
            e0.g.a(motoDesktopService.getApplicationContext()).f2480h = c2;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void registerOnAudioPbPkgNameListChangeListener(InterfaceC0023j interfaceC0023j) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        Context applicationContext = this.f270a.getApplicationContext();
        if (e0.i.f2485h == null) {
            e0.i.f2485h = new e0.i(applicationContext);
        }
        e0.i iVar = e0.i.f2485h;
        if (((ArrayList) iVar.f2490e).contains(interfaceC0023j)) {
            return;
        }
        ((ArrayList) iVar.f2490e).add(interfaceC0023j);
        iVar.a(((AudioManager) iVar.f2487b).getActivePlaybackConfigurations(), false);
        synchronized (((HashMap) iVar.f2491f)) {
            try {
                if (((InterfaceC0023j) ((HashMap) iVar.f2491f).get(((C0021i) interfaceC0023j).f323a)) == null) {
                    ((HashMap) iVar.f2491f).put(((C0021i) interfaceC0023j).f323a, interfaceC0023j);
                }
            } finally {
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void registerOnPointerPositionChangedListener(int i2, Q q2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        MotoDesktopService motoDesktopService = this.f270a;
        motoDesktopService.getClass();
        if (i2 != 1 && i2 != 2) {
            v0.b("MotoDesktopService", "registerOnPointerPositionChangedListenerInternal invalid pointer type: " + i2);
        } else {
            if (q2 == null) {
                v0.b("MotoDesktopService", "registerOnPointerPositionChangedListenerInternal listener is null");
                return;
            }
            synchronized (motoDesktopService.f2291p) {
                synchronized (motoDesktopService.f2291p) {
                    try {
                        if (motoDesktopService.f2289n.get(i2) == null) {
                            M0 m0 = new M0(motoDesktopService, i2);
                            motoDesktopService.f2269b.registerOnPointerPositionChangedListener(i2, m0, motoDesktopService.f2279g);
                            motoDesktopService.f2289n.put(i2, m0);
                        }
                    } finally {
                    }
                }
                motoDesktopService.f2287l.add(new O0(i2, q2));
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void registerOnPointerStateChangedListener(int i2, T t2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        MotoDesktopService motoDesktopService = this.f270a;
        motoDesktopService.getClass();
        if (i2 != 1 && i2 != 2) {
            v0.b("MotoDesktopService", "registerOnPointerStateChangedListenerInternal invalid pointer type: " + i2);
            return;
        }
        if (t2 == null) {
            v0.b("MotoDesktopService", "registerOnPointerStateChangedListenerInternal listener is null");
            return;
        }
        synchronized (motoDesktopService.f2291p) {
            synchronized (motoDesktopService.f2291p) {
                try {
                    if (motoDesktopService.f2288m.get(i2) == null) {
                        N0 n0 = new N0(motoDesktopService, i2);
                        motoDesktopService.f2269b.registerOnPointerStateChangedListener(i2, n0, motoDesktopService.f2279g);
                        motoDesktopService.f2288m.put(i2, n0);
                    }
                } finally {
                }
            }
            motoDesktopService.f2286k.add(new O0(i2, t2));
            Integer num = (Integer) motoDesktopService.f2290o.get(i2);
            int iIntValue = num != null ? num.intValue() : -1;
            if (iIntValue != -1) {
                try {
                    ((S) t2).a(iIntValue);
                } catch (RemoteException unused) {
                }
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void registerOnRdpPointerStateChangedListener(IRdpPointerStateChangedListener iRdpPointerStateChangedListener) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "registerOnRdpPointerStateChangedListener");
        synchronized (this.f270a.f2291p) {
            MotoDesktopService motoDesktopService = this.f270a;
            motoDesktopService.f2284j = iRdpPointerStateChangedListener;
            if (!motoDesktopService.m0) {
                v0.a("MotoDesktopService", "registerOnRdpPointerStateChangedListenerInternal");
                motoDesktopService.f2269b.registerOnRdpPointerStateChangedListener(motoDesktopService.n0, motoDesktopService.f2279g);
                motoDesktopService.m0 = true;
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int registerReceiver(IntentFilter intentFilter, Z z2) {
        int i2;
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        if (intentFilter == null) {
            return -1;
        }
        K0 k0 = new K0();
        k0.f276b = z2;
        this.f270a.getApplicationContext().registerReceiver(k0, intentFilter, 2);
        synchronized (this.f270a.f2272c0) {
            MotoDesktopService motoDesktopService = this.f270a;
            i2 = motoDesktopService.f2274d0;
            motoDesktopService.f2272c0.put(i2, k0);
            this.f270a.f2274d0++;
        }
        return i2;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void registerSoftApCallback(InterfaceC0010c0 interfaceC0010c0) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        MotoDesktopService motoDesktopService = this.f270a;
        synchronized (motoDesktopService.f2291p) {
            if (interfaceC0010c0 != null) {
                try {
                    if (motoDesktopService.u0 == null) {
                        motoDesktopService.u0 = interfaceC0010c0;
                        motoDesktopService.f2244B.registerSoftApCallback(motoDesktopService.getMainExecutor(), motoDesktopService.v0);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void releaseAudioRecord() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (motoDesktopService.r == null) {
            return;
        }
        v0.a("MotoDesktopService", "releaseAudioRecordInternal");
        motoDesktopService.r.release();
        motoDesktopService.r = null;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void releaseAudioTrackForDialCall() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "releaseAudioTrackForDialCall");
        motoDesktopService.i();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void releaseAudioTrackForIpCall() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "releaseAudioTrackForIpCall");
        motoDesktopService.j();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void releaseAudioTrackForMic() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "releaseAudioTrackForMic");
        motoDesktopService.k();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void releaseDisplay(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        motoDesktopService.l();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void releaseVirtualDisplay(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (!motoDesktopService.f2263U.i(i2)) {
            motoDesktopService.f2249G.f(motoDesktopService.q0, i2);
            return;
        }
        F.f fVar = motoDesktopService.f2263U;
        g0.e eVarF = fVar.f(-1, -1);
        if (eVarF == null) {
            eVarF = fVar.h(i2);
        }
        if (eVarF != null) {
            eVarF.b(i2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void releaseVirtualDisplayVDM(int i2, int i3, int i4) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "releaseVirtualDisplayVDM associationId=" + i2);
        F.f fVar = motoDesktopService.f2263U;
        g0.e eVarF = fVar.f(i2, i3);
        if (eVarF == null) {
            eVarF = fVar.h(i4);
        }
        if (eVarF != null) {
            eVarF.b(i4);
        }
        motoDesktopService.f2249G.f(motoDesktopService.q0, i4);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void removeActivityListenerVDM(int i2, int i3, a0.b bVar) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        g0.e eVarF = motoDesktopService.f2263U.f(i2, i3);
        if (eVarF != null) {
            eVarF.c(bVar);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean removeBond(BluetoothDevice bluetoothDevice) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "removeBond");
        if (bluetoothDevice != null) {
            return bluetoothDevice.removeBond();
        }
        return false;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void removeDesktopIcon(String str, int i2) {
        if (Q0.g()) {
            return;
        }
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "removeDesktopIcon");
        try {
            motoDesktopService.f2293s.removeDesktopIcon(str, i2);
        } catch (Exception e2) {
            v0.a("MotoDesktopService", "putIntToMotorolaSettings, error:" + e2.toString());
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void removePrimaryClipChangedListener(IRdpPimaryClipChangedListener iRdpPimaryClipChangedListener) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "removePrimaryClipChangedListener");
        synchronized (this.f270a.f2291p) {
            MotoDesktopService motoDesktopService = this.f270a;
            motoDesktopService.f2282i = null;
            motoDesktopService.o();
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void removeRoutesFromLocalNetwork(List list) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "removeRoutesFromLocalNetwork");
        INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void removeTask(int i2) {
        v0.a("MotoDesktopService", "removeTask:" + i2);
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        motoDesktopService.f2248F.removeTask(i2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void removeUniqueIdAssociation(String str) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "removeUniqueIdAssociation: " + str);
        motoDesktopService.f2269b.removeUniqueIdAssociationByPort(str);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void resetUsbPort(int i2) {
        UsbPortStatus status;
        MotoDesktopService motoDesktopService = this.f270a;
        try {
            List ports = motoDesktopService.f2292q.getPorts();
            int size = ports.size();
            if (size <= 0) {
                v0.a("MotoDesktopService", "No USB ports");
                return;
            }
            UsbPort usbPort = null;
            if (i2 == -1 || i2 >= size) {
                i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        status = null;
                        break;
                    }
                    UsbPortStatus status2 = ((UsbPort) ports.get(i2)).getStatus();
                    if (status2.isConnected()) {
                        usbPort = (UsbPort) ports.get(i2);
                        v0.a("MotoDesktopService", "Use the default USB port: port" + i2);
                        status = status2;
                        break;
                    }
                    i2++;
                }
            } else {
                status = ((UsbPort) ports.get(i2)).getStatus();
                if (status.isConnected()) {
                    usbPort = (UsbPort) ports.get(i2);
                    v0.a("MotoDesktopService", "Get the USB port: port" + i2);
                }
            }
            if (usbPort == null || !status.isConnected()) {
                v0.a("MotoDesktopService", "There is no available reset USB port");
                return;
            }
            v0.a("MotoDesktopService", "Reset the USB port: port" + i2);
            usbPort.resetUsbPort(motoDesktopService.getMainExecutor(), motoDesktopService.f2285j0);
        } catch (Exception e2) {
            v0.a("MotoDesktopService", "Error communicating with UsbManager: " + e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void resizeVirtualDisplay(int i2, int i3, int i4) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        VirtualDisplay virtualDisplay = motoDesktopService.f2273d;
        if (virtualDisplay == null) {
            v0.b("MotoDesktopService", "virtual display is null");
        } else {
            virtualDisplay.resize(i2, i3, i4);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void resizeVirtualDisplayByDisplayId(int i2, int i3, int i4, int i5) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (motoDesktopService.f2263U.i(i2)) {
            g0.e eVarH = motoDesktopService.f2263U.h(i2);
            if (eVarH == null) {
                v0.b("VirtualDeviceManager", "failed to find virtual device to resize display: " + i2);
                return;
            } else {
                g0.d dVar = (g0.d) eVarH.f2558b.get(Integer.valueOf(i2));
                if (dVar != null) {
                    dVar.f2555b.resize(i3, i4, i5);
                    return;
                } else {
                    v0.b("ScVirtualDevice", "failed to find virtual display to set resize");
                    return;
                }
            }
        }
        e0.b bVar = motoDesktopService.f2249G;
        bVar.getClass();
        v0.a("AppStreamManager_Core", "resizeVirtualDisplayInternal displayId=" + i2 + ", width=" + i3 + ", height=" + i4 + ", densityDpi=" + i5);
        VirtualDisplay virtualDisplay = (VirtualDisplay) bVar.f2451b.get(Integer.valueOf(i2));
        if (virtualDisplay == null) {
            v0.b("AppStreamManager_Core", "virtual display is null");
        } else {
            virtualDisplay.resize(i3, i4, i5);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void revokeRuntimePermission(String str, String str2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "revokeRuntimePermission ");
        motoDesktopService.f2294t.getClass();
        v0.g("A", "revokeRuntimePermission remove the code for GTS");
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void rotateDisplay(int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            WindowManagerGlobal.getWindowManagerService().freezeDisplayRotation(i2, i3, motoDesktopService.getPackageName());
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "rotateDisplay Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void sendKeyEvent(KeyEvent keyEvent, int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        if (keyEvent == null && keyEvent == null) {
            v0.g("MotoDesktopService", "sendKeyEvent error, keyEvent is null");
        } else {
            keyEvent.setDisplayId(i2);
            InputManager.getInstance().injectInputEvent(keyEvent, 0);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void sendKeySync(KeyEvent keyEvent, int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        motoDesktopService.getClass();
        try {
            Class.forName("android.view.KeyEvent").getMethod("setDisplayId", Integer.TYPE).invoke(keyEvent, Integer.valueOf(i2));
        } catch (Exception e2) {
            v0.g("MotoDesktopService", "setKeyEventDisplayId error:" + e2.toString());
        }
        motoDesktopService.f2271c.sendKeySync(keyEvent);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void sendPendingIntent(PendingIntent pendingIntent) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "sendPendingIntent from sbm:" + pendingIntent);
        if (pendingIntent != null) {
            try {
                motoDesktopService.f2293s.sendPendingIntent(pendingIntent);
            } catch (Exception e2) {
                v0.a("MotoDesktopService", "sendPendingIntent, error:" + e2.toString());
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void sendPointerSync(MotionEvent motionEvent, int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        motoDesktopService.getClass();
        try {
            Class.forName("android.view.MotionEvent").getMethod("setDisplayId", Integer.TYPE).invoke(motionEvent, Integer.valueOf(i2));
        } catch (Exception e2) {
            v0.g("MotoDesktopService", "setMotionEventDisplayId error:" + e2.toString());
        }
        motoDesktopService.f2269b.injectInputEvent(motionEvent, 0);
        motionEvent.getAxisValue(9);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void sendUriToNearbyPc(NearbyShareDevice nearbyShareDevice, String str) {
        v0.a("MotoDesktopService", "sendUriToNearSharePc not supported");
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setActivityStateChangedCallback(InterfaceC0019h interfaceC0019h) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            v0.a("MotoDesktopService", "setActivityStateChangedCallback ");
            e0.i.b(motoDesktopService).c(motoDesktopService.q0, interfaceC0019h);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "setActivityStateChangedCallback Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setAudioSettingChangeCallback(InterfaceC0029m interfaceC0029m) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            C0013e c0013e = motoDesktopService.f2283i0;
            c0013e.f320c = interfaceC0029m;
            if (interfaceC0029m != null) {
                c0013e.f319b.post(new F.e(1, c0013e));
            }
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "setAudioSettingChangeCallback() Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setAudioSettingCheckState(boolean z2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            C0013e c0013e = motoDesktopService.f2283i0;
            c0013e.f319b.post(new RunnableC0009c(c0013e, z2));
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "setAudioSettingCheckState() Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setAudioSettingFocus(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            C0013e c0013e = motoDesktopService.f2283i0;
            c0013e.f319b.post(new RunnableC0011d(c0013e, i2, 0));
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "setAudioSettingFocus() Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setAudioSettingVolumeByIndex(int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            C0013e c0013e = motoDesktopService.f2283i0;
            c0013e.f319b.post(new RunnableC0007b(c0013e, i2, i3));
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "setAudioSettingVolumeByIndex() Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setClientBluetoothDeviceAddress(String str) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        boolean z2 = Q0.f288a;
        v0.g("Utils", "setClientBluetoothDeviceAddress = " + str);
        Q0.f295h = str;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setClientDeviceInfo(String str, int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "setClientDeviceInfo = " + str + " deviceType = " + i2);
        Q0.h(str, i2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setCompatibilityMode(String str, boolean z2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "setCompatibilityMode compatibilitymode= " + z2);
        try {
            if (Q0.e("android.hardware.display.DisplayManager", "setCompatibilityMode")) {
                motoDesktopService.f2300z.setCompatibilityMode(str, z2);
            } else {
                v0.b("MotoDesktopService", "DisplayManager setCompatibilityMode method is not exist ");
            }
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "setCompatibilityMode Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setConnectState(boolean z2, boolean z3, String str) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "setConnectState - rdpConnected:" + z2 + " otherConnected:" + z3 + " pcName:" + str);
        try {
            e0.d.d(motoDesktopService.getApplicationContext()).i(z2, z3, str);
        } catch (Exception e2) {
            v0.a("MotoDesktopService", "setConnectState, error:" + e2.toString());
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setCorePermissionDialogCallback(InterfaceC0044x interfaceC0044x) {
        v0.a("MotoDesktopService", "setCorePermissionDialogCallback callback=" + interfaceC0044x);
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        e0.k.h().f2495a = interfaceC0044x;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setCustomPointerIcon(PointerIcon pointerIcon, int i2, boolean z2) {
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setCustomPointerIconForFullScreen(PointerIcon pointerIcon, int i2, boolean z2, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            MotoInputManager.getInstance(motoDesktopService).setCustomPointerIconForFullScreen(pointerIcon, i2, z2, i3);
        } catch (Error e2) {
            v0.b("MotoDesktopService", "setCustomPointerIconForFullScreen:" + e2.toString());
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setCustomPointerIconForFullScreenV(PointerIcon pointerIcon, int i2, boolean z2, int i3, int i4) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            MotoInputManager.getInstance(motoDesktopService).setCustomPointerIconForFullScreen(pointerIcon, i2, z2, i3, i4);
        } catch (Error e2) {
            v0.b("MotoDesktopService", "setCustomPointerIconForFullScreen:" + e2.toString());
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setDesktopRestartModeByPackages(List list, List list2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        ActivityManager.getService().setDesktopRestartModeByPackages(list, list2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setDisplayImePolicy(int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            ((WindowManager) motoDesktopService.getSystemService("window")).setDisplayImePolicy(i2, i3);
        } catch (NoSuchMethodError e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setExactAndAllowWhileIdle(int i2, long j2, PendingIntent pendingIntent) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            motoDesktopService.f2251I.setExactAndAllowWhileIdle(i2, j2, pendingIntent);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "setExactAndAllowWhileIdle Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setFontScale(int i2, float f2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        try {
            IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
            windowManagerService.getClass().getDeclaredMethod("setFontScale", Integer.TYPE, Float.TYPE).invoke(windowManagerService, Integer.valueOf(i2), Float.valueOf(f2));
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "setFontScale Exception!", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setForcedDisplayDensity(int i2, int i3) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        AsyncTask.execute(new E0(i2, i3, UserHandle.myUserId()));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setGoIntent(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "setGoIntent value= " + i2);
        try {
            if (Q0.e("android.hardware.display.DisplayManager", "setGoIntent")) {
                motoDesktopService.f2300z.setGoIntent(i2);
            } else {
                v0.b("MotoDesktopService", "setGoIntent method is not exist in DisplayManager");
            }
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "setGoIntent Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setInterfaceConfig(String str, InterfaceConfigurationParcel interfaceConfigurationParcel) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "setInterfaceConfig - iface = " + str + ", addr:" + interfaceConfigurationParcel.f2341c + " prefixLength:" + interfaceConfigurationParcel.f2342d);
        INetworkManagementService iNetworkManagementServiceAsInterface = INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
        InetAddress numericAddress = InetAddresses.parseNumericAddress(interfaceConfigurationParcel.f2341c);
        InterfaceConfiguration interfaceConfiguration = new InterfaceConfiguration();
        interfaceConfiguration.setHardwareAddress(interfaceConfigurationParcel.f2340b);
        interfaceConfiguration.setLinkAddress(new LinkAddress(numericAddress, interfaceConfigurationParcel.f2342d));
        for (String str2 : interfaceConfigurationParcel.f2343e) {
            interfaceConfiguration.setFlag(str2);
        }
        v0.b("MotoDesktopService", "iConfig:" + interfaceConfiguration.toString());
        iNetworkManagementServiceAsInterface.setInterfaceConfig(str, interfaceConfiguration);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setIpForwardingEnabled(boolean z2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "setIpForwardingEnabled = " + z2);
        INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management")).oemSetIpForwardingEnabled(z2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setLocationEnabled() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "setLocationEnabled ");
        e0.A a2 = motoDesktopService.f2294t;
        a2.getClass();
        v0.a("A", "setLocationEnabled");
        ((LocationManager) a2.f2424c.getSystemService(LocationManager.class)).setLocationEnabledForUser(true, UserHandle.of(UserHandle.myUserId()));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setMotoTaskStackListener(InterfaceC0020h0 interfaceC0020h0) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            e0.i.b(motoDesktopService).d(motoDesktopService.q0, interfaceC0020h0);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "setMotoTaskStackListener Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setNcmFunction(boolean z2, String str, String str2, F f2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "setNcmFunction - enable = " + z2 + ", serverIp:" + str + ", clientIp:" + str2);
        setTetheringFunction(z2, false, str, str2, f2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setPackageChangeCallback(L l2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "setPackageChangeCallback=" + l2);
        if (l2 != null) {
            try {
                ((J) l2).f273a.linkToDeath(motoDesktopService.q0, 0);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        } else {
            L l3 = (L) motoDesktopService.f2295u.f2486a;
            if (l3 != null) {
                ((J) l3).f273a.unlinkToDeath(motoDesktopService.q0, 0);
            }
        }
        motoDesktopService.f2295u.e(l2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean setPairingConfirmation(BluetoothDevice bluetoothDevice, boolean z2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "setPairingConfirmation: " + z2);
        if (bluetoothDevice != null) {
            return bluetoothDevice.setPairingConfirmation(z2);
        }
        return false;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setPointSpeed(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        InputSettings.setPointerSpeed(motoDesktopService.getApplicationContext(), i2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setPointerIconVisible(boolean z2, int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            motoDesktopService.f2269b.setPointerIconVisible(z2, i2);
        } catch (Error | Exception unused) {
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setPrimaryClip(ClipData clipData) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        motoDesktopService.f2281h.setPrimaryClip(clipData);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setProjectionRect(int i2, Rect rect) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (!motoDesktopService.f2263U.i(i2)) {
            VirtualDisplay virtualDisplay = (VirtualDisplay) motoDesktopService.f2249G.f2451b.get(Integer.valueOf(i2));
            if (virtualDisplay == null) {
                v0.b("AppStreamManager_Core", "virtual display is null");
                return;
            } else {
                virtualDisplay.setProjectionRect(rect);
                return;
            }
        }
        g0.e eVarH = motoDesktopService.f2263U.h(i2);
        if (eVarH == null) {
            v0.b("VirtualDeviceManager", "failed to find virtual device to set projection rect: " + i2);
        } else {
            g0.d dVar = (g0.d) eVarH.f2558b.get(Integer.valueOf(i2));
            if (dVar != null) {
                dVar.f2555b.setProjectionRect(rect);
            } else {
                v0.b("ScVirtualDevice", "failed to find virtual display to set projection rect");
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setRdpAudioActivatedState(boolean z2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        Q0.j(z2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setRdpAudioRecordingCallState(boolean z2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        Context applicationContext = motoDesktopService.getApplicationContext();
        boolean z3 = Q0.f288a;
        MotorolaSettings.Global.putInt(applicationContext.getContentResolver(), "is_rdp_in_recording_call_audio", z2 ? 1 : 0);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setRdpAudioStartRecord() {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "setRdpAudioStartRecord");
        Q0.i(false);
        for (Y.o oVar : Q0.f292e) {
            oVar.getClass();
            v0.a("AudioReceiver", "setRdpAudioStartRecord");
            for (Y.f fVar : oVar.f419a.f423c) {
                synchronized (fVar.f360a.f389b) {
                    try {
                        for (Y.h hVar : fVar.f360a.f391d) {
                            Y.i iVar = fVar.f360a;
                            hVar.a(iVar.f395h, iVar.f397j, iVar.f399l);
                        }
                    } finally {
                    }
                }
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setRdpPointerPosition(int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "setRdpPointerPosition");
        motoDesktopService.f2269b.setPointerPosition(2, i2, i3);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setReadyForConnectedType(int i2, boolean z2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        Q0.k(i2, z2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setShowPointer(int i2, boolean z2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (i2 == 1 || i2 == 2) {
            v0.a("MotoDesktopService", "setShowPointer pointerType: " + i2 + " show: " + z2);
            motoDesktopService.f2269b.setShowPointer(i2, z2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setShowRdpPointer(boolean z2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "setShowRdpPointer: " + z2);
        motoDesktopService.f2269b.setShowPointer(2, z2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean setSoftApConfiguration(SoftApConfiguration softApConfiguration) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2244B.setSoftApConfiguration(softApConfiguration);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setTaskStackListener(InterfaceC0020h0 interfaceC0020h0) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        try {
            MotoDesktopService motoDesktopService = this.f270a;
            e0.b bVar = motoDesktopService.f2249G;
            J0 j02 = motoDesktopService.q0;
            synchronized (bVar.f2455f) {
                if (bVar.f2460k == null && interfaceC0020h0 != null) {
                    try {
                        ((C0018g0) interfaceC0020h0).f322a.linkToDeath(j02, 0);
                        bVar.f2460k = interfaceC0020h0;
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                    }
                }
            }
        } catch (Exception e3) {
            v0.c("MotoDesktopService", "setTaskStackListener Exception ", e3);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setTetheringFunction(boolean z2, boolean z3, String str, String str2, F f2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "setTetheringFunction - enable = " + z2 + ", sharedNet:" + z3 + ", serverIp:" + str + ", clientIp:" + str2);
        if (z2) {
            ((TetheringManager) motoDesktopService.getBaseContext().getSystemService("tethering")).startTethering(new TetheringManager.TetheringRequest.Builder(1).setStaticIpv4Addresses(new LinkAddress(str), new LinkAddress(str2)).setConnectivityScope(z3 ? 1 : 2).build(), new HandlerExecutor(motoDesktopService.f2279g), new D0(f2, 0));
        } else {
            motoDesktopService.f2292q.setCurrentFunctions(0L);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setUsbFunctions(long j2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "setUsbFunctions = " + j2);
        motoDesktopService.f2292q.setCurrentFunctions(j2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setUsbFunctionsAuthenticated(long j2, boolean z2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            MotoSecurityUtils.setUsbFunctionsAuthenticated(motoDesktopService.getApplicationContext(), j2, z2);
        } catch (Error e2) {
            v0.b("MotoDesktopService", "setUsbFunctionsAuthenticated:" + e2.toString());
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setUserPreferredDisplayMode(int i2, int i3, int i4, float f2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        try {
            Display display = ((DisplayManager) motoDesktopService.getSystemService("display")).getDisplay(i2);
            if (display != null) {
                display.setUserPreferredDisplayMode(new Display.Mode(i3, i4, f2));
                v0.a("MotoDesktopService", "setUserPreferredDisplayMode " + i2 + " " + i3 + ":" + i4 + ":" + f2);
            } else {
                v0.b("MotoDesktopService", "failed to get display for " + i2);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setVirtualDisplaySupportedModes(List list) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        motoDesktopService.getClass();
        if (Q0.g()) {
            return;
        }
        if (motoDesktopService.f2273d == null) {
            v0.b("MotoDesktopService", "virtual display is null");
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MotoDisplayMode motoDisplayMode = (MotoDisplayMode) it.next();
            arrayList.add(new Display.Mode(0, motoDisplayMode.width, motoDisplayMode.height, motoDisplayMode.fps));
        }
        motoDesktopService.f2273d.setSupportedModes(arrayList);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setVirtualDisplaySupportedModesVDM(int i2, int i3, int i4, List list) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        F.f fVar = motoDesktopService.f2263U;
        g0.e eVarF = fVar.f(i2, i3);
        if (eVarF == null) {
            eVarF = fVar.h(i4);
        }
        if (eVarF == null) {
            v0.b("VirtualDeviceManager", "failed to find virtual device to set supported modes for display: " + i4);
            return;
        }
        v0.a("ScVirtualDevice", "setVirtualDisplaySupportedModes display id " + i4 + " mode count: " + list.size());
        g0.d dVar = (g0.d) eVarF.f2558b.get(Integer.valueOf(i4));
        if (dVar == null) {
            v0.b("ScVirtualDevice", "failed to find virtual display to set supported modes");
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MotoDisplayMode motoDisplayMode = (MotoDisplayMode) it.next();
            arrayList.add(new Display.Mode(0, motoDisplayMode.width, motoDisplayMode.height, motoDisplayMode.fps));
        }
        dVar.f2555b.setSupportedModes(arrayList);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setVirtualDisplaySurface(Surface surface) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (motoDesktopService.f2273d != null) {
            v0.a("MotoDesktopService", "setVirtualDisplaySurface");
            motoDesktopService.f2273d.setSurface(surface);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setVirtualDisplaySurfaceByDisplayId(int i2, Surface surface) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "setVirtualDisplaySurfaceByDisplayId:" + i2);
        if (motoDesktopService.f2263U.i(i2)) {
            g0.e eVarH = motoDesktopService.f2263U.h(i2);
            if (eVarH == null) {
                v0.b("VirtualDeviceManager", "failed to find virtual device to set display surface: " + i2);
                return;
            } else {
                g0.d dVar = (g0.d) eVarH.f2558b.get(Integer.valueOf(i2));
                if (dVar != null) {
                    dVar.f2555b.setSurface(surface);
                    return;
                } else {
                    v0.b("ScVirtualDevice", "failed to find virtual display to set surface");
                    return;
                }
            }
        }
        e0.b bVar = motoDesktopService.f2249G;
        bVar.getClass();
        v0.a("AppStreamManager_Core", "setVirtualDisplaySurface:" + i2 + ", surface=" + surface);
        VirtualDisplay virtualDisplay = (VirtualDisplay) bVar.f2451b.get(Integer.valueOf(i2));
        if (virtualDisplay == null) {
            v0.b("AppStreamManager_Core", "virtual display is null");
        } else {
            virtualDisplay.setSurface(surface);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setWifiDisplayCallback(u0 u0Var) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "setWifiDisplayCallback=" + u0Var);
        if (u0Var != null) {
            try {
                ((s0) u0Var).f331a.linkToDeath(motoDesktopService.q0, 0);
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        } else {
            u0 u0Var2 = motoDesktopService.f2294t.f2426e;
            if (u0Var2 != null) {
                ((s0) u0Var2).f331a.unlinkToDeath(motoDesktopService.q0, 0);
            }
        }
        e0.A a2 = motoDesktopService.f2294t;
        a2.getClass();
        v0.a("A", "setWifiDisplayCallback=" + u0Var);
        a2.f2426e = u0Var;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void setWifiDisplaySwitch(boolean z2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "setWifiDisplaySwitch: " + z2);
        e0.A a2 = motoDesktopService.f2294t;
        a2.getClass();
        v0.e("A", "setWifiDisplaySwitch:" + z2);
        Context context = a2.f2424c;
        if (z2) {
            Settings.Global.putInt(context.getContentResolver(), "wifi_display_on", 1);
        } else {
            Settings.Global.putInt(context.getContentResolver(), "wifi_display_on", 0);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean setWifiEnabled(boolean z2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "setWifiEnabled enabled=" + z2);
        e0.A a2 = motoDesktopService.f2294t;
        a2.getClass();
        try {
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            intent.addFlags(268435456);
            a2.f2424c.startActivity(intent);
            return true;
        } catch (Exception e2) {
            v0.h("A", "setWifiEnabled", e2);
            return true;
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void startAccessory(String[] strArr) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "startAccessory");
        try {
            if (Q0.e("android.hardware.usb.UsbManager", "startAccessory")) {
                motoDesktopService.f2292q.startAccessory(strArr);
            } else {
                v0.b("MotoDesktopService", "UsbManager startAccessory method is not exist ");
            }
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "startAccessory Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void startApp(Intent intent, int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        e0.b bVar = this.f270a.f2249G;
        if (intent == null) {
            bVar.getClass();
            v0.b("AppStreamManager_Core", "startApp intent is null for displayID " + i2);
            return;
        }
        synchronized (bVar.f2455f) {
            ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
            activityOptionsMakeBasic.setLaunchDisplayId(i2);
            try {
                v0.a("AppStreamManager_Core", "startApp:" + intent + ", displayId=" + i2);
                bVar.f2450a.startActivity(intent, activityOptionsMakeBasic.toBundle());
            } catch (Exception e2) {
                v0.c("AppStreamManager_Core", "startApp error", e2);
            }
            ComponentName component = intent.getComponent();
            if (component != null) {
                bVar.f2452c.add(component.getPackageName());
                v0.a("AppStreamManager_Core", "startApp:" + bVar.f2452c);
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void startAppAsUser(Intent intent, int i2, int i3) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        e0.b bVar = this.f270a.f2249G;
        if (intent == null) {
            bVar.getClass();
            v0.b("AppStreamManager_Core", "startAppAsUser intent is null for displayID=" + i2 + ", userId=" + i3);
            return;
        }
        synchronized (bVar.f2455f) {
            ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
            UserHandle userHandleOf = UserHandle.of(i3);
            activityOptionsMakeBasic.setLaunchDisplayId(i2);
            activityOptionsMakeBasic.addMotoFlag(4);
            try {
                v0.a("AppStreamManager_Core", "startAppAsUser:" + intent + ", displayId=" + i2 + ", userId=" + i3);
                bVar.f2450a.startActivityAsUser(intent, activityOptionsMakeBasic.toBundle(), userHandleOf);
            } catch (Exception e2) {
                v0.c("AppStreamManager_Core", "startAppAsUser error", e2);
            }
            ComponentName component = intent.getComponent();
            if (component != null) {
                bVar.f2453d.add(Y.r.k(i3, component.getPackageName()));
                v0.a("AppStreamManager_Core", "startAppAsUser:" + bVar.f2453d);
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void startAppWithActivityOptions(Intent intent, Bundle bundle, int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (intent == null) {
            v0.b("MotoDesktopService", "startAppWithActivityOptions intent is null");
            return;
        }
        try {
            v0.a("MotoDesktopService", "startAppWithActivityOptions :intent=" + intent + ", options=" + bundle + ", userId=" + i2);
            motoDesktopService.getApplicationContext().startActivityAsUser(intent, bundle, UserHandle.of(i2));
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "startAppWithActivityOptions error", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void startAudioRecording() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        AudioRecord audioRecord = motoDesktopService.r;
        if (audioRecord == null || audioRecord.getRecordingState() == 3) {
            return;
        }
        motoDesktopService.r.startRecording();
        v0.a("MotoDesktopService", "startAudioRecording");
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void startNearbyShareSysWatcher(I i2) {
        v0.a("MotoDesktopService", "startNearShareSysWatcher not supported");
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void startPlatformService(Intent intent) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "startPlatformService intent:" + intent);
        try {
            motoDesktopService.startService(intent);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "startPlatformService Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void startSettingsUsbDetailsActivity() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        Intent intent = new Intent();
        intent.setClassName("com.android.settings", "com.android.settings.Settings$UsbDetailsActivity");
        intent.addFlags(268435456);
        motoDesktopService.startActivity(intent);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void startShortcut(String str, String str2, Rect rect, Bundle bundle, UserHandle userHandle) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "startShortcut ");
        ((LauncherApps) motoDesktopService.getSystemService(LauncherApps.class)).startShortcut(str, str2, rect, bundle, userHandle);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean startTetheredHotspot(SoftApConfiguration softApConfiguration) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2244B.startTetheredHotspot(softApConfiguration);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void startTethering(TetheringRequest tetheringRequest, InterfaceC0016f0 interfaceC0016f0) {
        LinkAddress linkAddress;
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        TetheringManager tetheringManager = (TetheringManager) motoDesktopService.getSystemService("tethering");
        TetheringManager.TetheringRequest.Builder shouldShowEntitlementUi = new TetheringManager.TetheringRequest.Builder(tetheringRequest.f2301a).setExemptFromEntitlementCheck(tetheringRequest.f2304d).setShouldShowEntitlementUi(tetheringRequest.f2305e);
        int i2 = tetheringRequest.f2306f;
        if (i2 != 0) {
            shouldShowEntitlementUi.setConnectivityScope(i2);
        }
        LinkAddress linkAddress2 = tetheringRequest.f2302b;
        if (linkAddress2 != null && (linkAddress = tetheringRequest.f2303c) != null) {
            shouldShowEntitlementUi.setStaticIpv4Addresses(linkAddress2, linkAddress);
        }
        TetheringManager.TetheringRequest tetheringRequestBuild = shouldShowEntitlementUi.build();
        v0.a("MotoDesktopService", "startTethering: " + tetheringRequestBuild);
        tetheringManager.startTethering(tetheringRequestBuild, new HandlerExecutor(motoDesktopService.f2279g), new D0(interfaceC0016f0, 1));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void startTetheringByOemNetd(boolean z2, boolean z3, String[] strArr) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "startTetheringByOemNetd");
        INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management")).startTetheringByOemNetd(z2, z3, strArr);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean startToAssociate(AssociationRequest associationRequest, InterfaceC0035p interfaceC0035p) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "startToAssociate associationRequest=" + associationRequest + " callback=" + interfaceC0035p);
        f0.b bVar = motoDesktopService.f2262T;
        bVar.getClass();
        if (associationRequest == null) {
            v0.b("ScCompanionDeviceManager", "association request is null");
            return false;
        }
        AssociationRequest.Builder builder = new AssociationRequest.Builder();
        builder.setSingleDevice(associationRequest.isSingleDevice()).setDeviceProfile(associationRequest.getDeviceProfile()).setDisplayName(associationRequest.getDisplayName()).setForceConfirmation(associationRequest.isForceConfirmation()).setSelfManaged(associationRequest.isSelfManaged());
        Iterator it = associationRequest.getDeviceFilters().iterator();
        while (it.hasNext()) {
            builder.addDeviceFilter((DeviceFilter) it.next());
        }
        ((CompanionDeviceManager) bVar.f2538c).associate(builder.build(), new f0.a(bVar, interfaceC0035p), (Handler) null);
        v0.e("ScCompanionDeviceManager", "associate");
        return true;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void startToUnassociate(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "startToUnassociate associationId=" + i2);
        f0.b bVar = motoDesktopService.f2262T;
        bVar.getClass();
        v0.e("ScCompanionDeviceManager", "disassociate: " + i2);
        ((CompanionDeviceManager) bVar.f2538c).disassociate(i2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void startWifiDisplayScan() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "startWifiDisplayScan ");
        e0.A a2 = motoDesktopService.f2294t;
        a2.getClass();
        v0.e("A", "startWifiDisplayScan");
        e0.w wVar = a2.f2434m;
        Context context = a2.f2424c;
        if (wVar == null) {
            a2.f2434m = new e0.w(a2, 1);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.hardware.display.action.WIFI_DISPLAY_STATUS_CHANGED");
            intentFilter.addAction("android.net.wifi.p2p.CONNECTION_STATE_CHANGE");
            context.registerReceiver(a2.f2434m, intentFilter);
            v0.a("A", "startWifiDisplayScan registerReceiver:" + a2.f2434m);
        }
        if (a2.f2435n == null) {
            a2.f2435n = new C0005a(a2, new Handler(Looper.getMainLooper()), 2);
            context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("wifi_display_on"), false, a2.f2435n);
            context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("wifi_display_certification_on"), false, a2.f2435n);
            context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("wifi_display_wps_config"), false, a2.f2435n);
            v0.a("A", "startWifiDisplayScan registerContentObserver");
        } else {
            v0.a("A", "startWifiDisplayScan mSettingsObserver is not null");
        }
        MediaRouter mediaRouter = a2.f2425d;
        if (mediaRouter == null || a2.f2436o != null) {
            v0.a("A", "startWifiDisplayScan mRouter is null or mRouterCallback is not null");
        } else {
            e0.z zVar = new e0.z(a2);
            a2.f2436o = zVar;
            mediaRouter.addCallback(4, zVar, 1);
            v0.a("A", "startWifiDisplayScan MediaRouter addCallback");
        }
        v0.a("A", "startWifiDisplayScan end");
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void stopAudioRecording() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        AudioRecord audioRecord = motoDesktopService.r;
        if (audioRecord != null && audioRecord.getRecordingState() == 3) {
            motoDesktopService.r.stop();
            v0.a("MotoDesktopService", "stopAudioRecording");
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void stopHotspot() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "stopHotspot ");
        e0.A a2 = motoDesktopService.f2294t;
        a2.getClass();
        v0.a("A", "stopHotspot");
        ((TetheringManager) a2.f2424c.getSystemService(TetheringManager.class)).stopTethering(0);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void stopNearbyShareSysWatcher() {
        v0.a("MotoDesktopService", "stopNearShareSysWatcher not supported");
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean stopSoftAp() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2244B.stopSoftAp();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void stopTethering() {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "stopTethering");
        INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management")).oemStopTethering();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void stopTetheringWithType(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "stopTethering " + i2);
        ((TetheringManager) motoDesktopService.getSystemService("tethering")).stopTethering(i2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void stopWifiDisplayScan() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "stopWifiDisplayScan ");
        motoDesktopService.f2294t.i();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void tetherInterface(String str) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "tetherInterface - iface = " + str);
        INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management")).oemTetherInterface(str);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean uinputAbsSetup(int i2, AbsSetup absSetup) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2261S.absSetup(Binder.getCallingUid(), i2, absSetup);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean uinputCreate(int i2, InputSetup inputSetup) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2261S.create(Binder.getCallingUid(), i2, inputSetup);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean uinputDeviceClose(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2261S.deviceClose(Binder.getCallingUid(), i2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int uinputDeviceOpen(IClientToken iClientToken) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2261S.deviceOpen(Binder.getCallingUid(), iClientToken);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean uinputDoIoctl(int i2, int i3, int i4) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2261S.doIoctl(Binder.getCallingUid(), i2, i3, i4);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean uinputDoStrIoctl(int i2, int i3, String str) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2261S.doStrIoctl(Binder.getCallingUid(), i2, i3, str);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean uinputEmit(int i2, InputEvent[] inputEventArr) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2261S.emit(Binder.getCallingUid(), i2, inputEventArr);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean uinputEnable(int i2, int i3, char[] cArr) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        return motoDesktopService.f2261S.enable(Binder.getCallingUid(), i2, i3, cArr);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean unRegisterReceiver(int i2) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        BroadcastReceiver broadcastReceiver = (BroadcastReceiver) this.f270a.f2272c0.get(i2);
        if (broadcastReceiver == null) {
            return false;
        }
        synchronized (this.f270a.f2272c0) {
            this.f270a.f2272c0.remove(i2);
        }
        this.f270a.getApplicationContext().unregisterReceiver(broadcastReceiver);
        return true;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void unbindDragDropService() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (i0.a.f2629a) {
            e0.g.a(motoDesktopService.getApplicationContext()).b();
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void unbindPlatformService(ComponentName componentName) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        if (componentName == null) {
            return;
        }
        try {
            String shortString = componentName.toShortString();
            v0.a("MotoDesktopService", "unbindPlatformService compoName:" + shortString);
            HashMap map = motoDesktopService.l0;
            ServiceConnection serviceConnection = (ServiceConnection) map.get(shortString);
            if (serviceConnection != null) {
                motoDesktopService.unbindService(serviceConnection);
            }
            map.remove(shortString);
        } catch (Exception e2) {
            v0.c("MotoDesktopService", "startPlatformService Exception ", e2);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void unregisterContentObserver(InterfaceC0041u interfaceC0041u) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        MotoDesktopService motoDesktopService = this.f270a;
        if (interfaceC0041u == null) {
            motoDesktopService.getClass();
            v0.b("MotoDesktopService", "iContentObserver is null");
            return;
        }
        synchronized (motoDesktopService.r0) {
            try {
                Iterator it = ((ArrayList) motoDesktopService.r0.get(((C0040t) interfaceC0041u).f332a)).iterator();
                while (it.hasNext()) {
                    motoDesktopService.getContentResolver().unregisterContentObserver((ContentObserver) it.next());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void unregisterDesktopInputMethodListener(InterfaceC0046z interfaceC0046z) {
        synchronized (this.f270a.f2257O) {
            this.f270a.f2257O.remove(interfaceC0046z);
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void unregisterOnAudioPbPkgNameListChangeListener(InterfaceC0023j interfaceC0023j) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        Context applicationContext = this.f270a.getApplicationContext();
        if (e0.i.f2485h == null) {
            e0.i.f2485h = new e0.i(applicationContext);
        }
        e0.i iVar = e0.i.f2485h;
        synchronized (((HashMap) iVar.f2491f)) {
            try {
                InterfaceC0023j interfaceC0023j2 = (InterfaceC0023j) ((HashMap) iVar.f2491f).get(((C0021i) interfaceC0023j).f323a);
                if (interfaceC0023j2 != null) {
                    ((HashMap) iVar.f2491f).remove(((C0021i) interfaceC0023j).f323a);
                    ((ArrayList) iVar.f2490e).remove(interfaceC0023j2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void unregisterSoftApCallback() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        motoDesktopService.r();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void untetherInterface(String str) {
        MotoDesktopService.d(this.f270a, Binder.getCallingUid());
        v0.a("MotoDesktopService", "untetherInterface - iface = " + str);
        INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management")).oemUntetherInterface(str);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wakeUpDisplayGroup(int i2, long j2, int i3, String str) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        motoDesktopService.f2246D.wakeUp(j2, i3, str, i2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int wifiAddNetwork(WifiConfiguration wifiConfiguration) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "wifiAddNetwork conf:" + wifiConfiguration);
        return motoDesktopService.f2244B.addNetwork(wifiConfiguration);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectCancelConnect(j0.c cVar) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        vVar.f2514c.cancelConnect(vVar.f2515d, e0.u.a(cVar));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectConnect(WifiP2pConfig wifiP2pConfig, j0.c cVar) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        vVar.getClass();
        if (wifiP2pConfig.getNetworkName() == null || wifiP2pConfig.getNetworkName().isEmpty()) {
            wifiP2pConfig = new WifiP2pConfig.Builder().setDeviceAddress(MacAddress.fromString(wifiP2pConfig.deviceAddress)).setJoinExistingGroup(true).build();
        }
        v0.a("v", "connect to" + wifiP2pConfig.toString());
        vVar.f2514c.connect(vVar.f2515d, wifiP2pConfig, e0.u.a(cVar));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectCreateGroup(WifiP2pConfig wifiP2pConfig, j0.c cVar) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        WifiP2pManager wifiP2pManager = vVar.f2514c;
        if (wifiP2pConfig == null) {
            wifiP2pManager.createGroup(vVar.f2515d, e0.u.a(cVar));
        } else {
            wifiP2pManager.createGroup(vVar.f2515d, wifiP2pConfig, e0.u.a(cVar));
        }
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectDeletePersistentGroup(String str, j0.c cVar) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        if (str == null) {
            vVar.getClass();
            return;
        }
        vVar.f2514c.requestPersistentGroupInfo(vVar.f2515d, new e0.l(vVar, str, cVar, 0));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectDiscoverPeers(j0.c cVar) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        vVar.f2514c.discoverPeers(vVar.f2515d, e0.u.a(cVar));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectRegisterListener(j0.q qVar, j0.g gVar, j0.i iVar, j0.o oVar, j0.e eVar, j0.k kVar, j0.m mVar) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        vVar.f2517f = qVar;
        vVar.f2518g = gVar;
        vVar.f2519h = iVar;
        vVar.f2520i = oVar;
        vVar.f2521j = eVar;
        vVar.f2522k = kVar;
        vVar.f2523l = mVar;
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectRemoveGroup(j0.c cVar) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        vVar.getClass();
        v0.a("v", "removeGroup");
        vVar.f2514c.removeGroup(vVar.f2515d, e0.u.a(cVar));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectRequestConnectionInfo(j0.e eVar) {
        e0.r rVar;
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        if (eVar != null) {
            vVar.getClass();
            rVar = new e0.r(eVar);
        } else {
            rVar = null;
        }
        vVar.f2514c.requestConnectionInfo(vVar.f2515d, rVar);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectRequestDeviceInfo(j0.g gVar) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        if (gVar == null) {
            vVar.getClass();
            throw new IllegalArgumentException("This listener cannot be null.");
        }
        vVar.f2514c.requestDeviceInfo(vVar.f2515d, new e0.o(gVar));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectRequestDiscoveryState(j0.i iVar) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        if (iVar == null) {
            vVar.getClass();
            throw new IllegalArgumentException("This listener cannot be null.");
        }
        vVar.f2514c.requestDiscoveryState(vVar.f2515d, new e0.p(iVar));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectRequestGroupInfo(j0.k kVar) {
        e0.t tVar;
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        if (kVar != null) {
            vVar.getClass();
            tVar = new e0.t(0, kVar);
        } else {
            tVar = null;
        }
        vVar.f2514c.requestGroupInfo(vVar.f2515d, tVar);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectRequestNetworkInfo(j0.m mVar) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        if (mVar == null) {
            vVar.getClass();
            throw new IllegalArgumentException("This listener cannot be null.");
        }
        vVar.f2514c.requestNetworkInfo(vVar.f2515d, new e0.s(mVar));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectRequestPeers(j0.o oVar) {
        e0.q qVar;
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        if (oVar != null) {
            vVar.getClass();
            qVar = new e0.q(oVar);
        } else {
            qVar = null;
        }
        vVar.f2514c.requestPeers(vVar.f2515d, qVar);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectRequestState(j0.q qVar) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        if (qVar == null) {
            vVar.getClass();
            throw new IllegalArgumentException("This listener cannot be null.");
        }
        vVar.f2514c.requestP2pState(vVar.f2515d, new e0.n(qVar));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final void wifiDirectStopPeerDiscovery(j0.c cVar) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        e0.v vVar = motoDesktopService.f2298x;
        vVar.f2514c.stopPeerDiscovery(vVar.f2515d, e0.u.a(cVar));
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean wifiDisableNetwork(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "wifiDisableNetwork netId:" + i2);
        return motoDesktopService.f2244B.disableNetwork(i2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean wifiDisconnect() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "wifiDisconnect");
        return motoDesktopService.f2244B.disconnect();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean wifiEnableNetwork(int i2, boolean z2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "wifiEnableNetwork netId:" + i2 + " attemptConnect:" + z2);
        return motoDesktopService.f2244B.enableNetwork(i2, z2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final List wifiGetCallerConfiguredNetworks() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "wifiGetCallerConfiguredNetworks");
        return motoDesktopService.f2244B.getCallerConfiguredNetworks();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean wifiReconnect() {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "wifiReconnect");
        return motoDesktopService.f2244B.reconnect();
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final boolean wifiRemoveNetwork(int i2) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        v0.a("MotoDesktopService", "wifiRemoveNetwork netId:" + i2);
        return motoDesktopService.f2244B.removeNetwork(i2);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int writeAudioTrackDataForDialCall(byte[] bArr, int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        AudioTrack audioTrack = motoDesktopService.f2255M;
        if (audioTrack == null) {
            return -1;
        }
        return audioTrack.write(bArr, i2, i3);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int writeAudioTrackDataForIpCall(byte[] bArr, int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        AudioTrack audioTrack = motoDesktopService.f2254L;
        if (audioTrack == null) {
            return -1;
        }
        return audioTrack.write(bArr, i2, i3);
    }

    @Override // com.motorola.mobiledesktop.core.IMotoDesktopManager
    public final int writeAudioTrackDataForMic(byte[] bArr, int i2, int i3) {
        int callingUid = Binder.getCallingUid();
        MotoDesktopService motoDesktopService = this.f270a;
        MotoDesktopService.d(motoDesktopService, callingUid);
        AudioTrack audioTrack = motoDesktopService.f2253K;
        if (audioTrack == null) {
            return -1;
        }
        return audioTrack.write(bArr, i2, i3);
    }
}
