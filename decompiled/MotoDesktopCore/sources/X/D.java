package X;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.companion.AssociationRequest;
import android.companion.virtual.VirtualDeviceParams;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.graphics.Rect;
import android.hardware.display.VirtualDisplayConfig;
import android.net.Uri;
import android.net.wifi.SoftApConfiguration;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.p2p.WifiP2pConfig;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.UserHandle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.Surface;
import android.view.WindowManager;
import androidx.appcompat.app.AbstractC0054a;
import com.motorola.mobiledesktop.core.IMotoDesktopManager;
import com.motorola.mobiledesktop.core.IRdpPimaryClipChangedListener;
import com.motorola.mobiledesktop.core.IVirtualDisplayCallback;
import com.motorola.mobiledesktop.core.TetheringRequest;
import com.motorola.mobiledesktop.core.bean.MotoDisplay;
import com.motorola.mobiledesktop.core.bean.MotoDisplayMode;
import com.motorola.mobiledesktop.core.bean.MotoRunningTaskInfoNew;
import com.motorola.mobiledesktop.core.bean.MotoWifiDisplay;
import com.motorola.mobiledesktop.core.bean.NearbyShareDevice;
import com.motorola.mobiledesktop.core.tethering.InterfaceConfigurationParcel;
import com.motorola.mobiledesktop.core.tethering.RouteInfoParcel;
import com.motorola.mobiledesktop.core.uinput.AbsSetup;
import com.motorola.mobiledesktop.core.uinput.EventType;
import com.motorola.mobiledesktop.core.uinput.IClientToken;
import com.motorola.mobiledesktop.core.uinput.InputEvent;
import com.motorola.mobiledesktop.core.uinput.InputSetup;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class D extends Binder implements IMotoDesktopManager {
    public static void d(Parcel parcel, Parcel parcel2) {
        AbstractBinderC0032n0.a(parcel.readStrongBinder());
        parcel.readInt();
        parcel.readInt();
        parcel.readString();
        parcel.readInt();
        parcel.readInt();
        parcel.readInt();
        parcel.readInt();
        parcel2.writeNoException();
        parcel2.writeInt(-1);
    }

    public static void g(Parcel parcel, Parcel parcel2) {
        AbstractBinderC0038q0.a(parcel.readStrongBinder());
        parcel.readInt();
        parcel.readInt();
        parcel.readString();
        parcel.readInt();
        parcel.readInt();
        parcel.readInt();
        parcel.readInt();
        parcel2.writeNoException();
        parcel2.writeInt(-1);
    }

    public final void a(Parcel parcel, Parcel parcel2) {
        ((G0) this).addDesktopIcon(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString(), (PendingIntent) AbstractC0054a.d(parcel, PendingIntent.CREATOR));
        parcel2.writeNoException();
    }

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this;
    }

    public final void b(Parcel parcel, Parcel parcel2) {
        int iCreateVirtualDisplay = ((G0) this).createVirtualDisplay(AbstractBinderC0026k0.a(parcel.readStrongBinder()), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (Surface) AbstractC0054a.d(parcel, Surface.CREATOR), parcel.readInt());
        parcel2.writeNoException();
        parcel2.writeInt(iCreateVirtualDisplay);
    }

    public final void c(Parcel parcel, Parcel parcel2) {
        int iCreateVirtualDisplayForMultiUser = ((G0) this).createVirtualDisplayForMultiUser(AbstractBinderC0032n0.a(parcel.readStrongBinder()), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (Surface) AbstractC0054a.d(parcel, Surface.CREATOR), parcel.readInt());
        parcel2.writeNoException();
        parcel2.writeInt(iCreateVirtualDisplayForMultiUser);
    }

    public final void e(Parcel parcel, Parcel parcel2) {
        int iCreateVirtualDisplayWithProjection = ((G0) this).createVirtualDisplayWithProjection(AbstractBinderC0032n0.a(parcel.readStrongBinder()), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (Rect) AbstractC0054a.d(parcel, Rect.CREATOR), (Surface) AbstractC0054a.d(parcel, Surface.CREATOR), parcel.readInt());
        parcel2.writeNoException();
        parcel2.writeInt(iCreateVirtualDisplayWithProjection);
    }

    public final void f(Parcel parcel, Parcel parcel2) {
        int iCreateVirtualDisplayWithoutTask = ((G0) this).createVirtualDisplayWithoutTask(AbstractBinderC0038q0.a(parcel.readStrongBinder()), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (Rect) AbstractC0054a.d(parcel, Rect.CREATOR), (Surface) AbstractC0054a.d(parcel, Surface.CREATOR), parcel.readInt());
        parcel2.writeNoException();
        parcel2.writeInt(iCreateVirtualDisplayWithoutTask);
    }

    public final void h(Parcel parcel, Parcel parcel2) {
        IVirtualDisplayCallback iVirtualDisplayCallback;
        IVirtualDisplayCallback iVirtualDisplayCallback2;
        IBinder strongBinder = parcel.readStrongBinder();
        if (strongBinder == null) {
            iVirtualDisplayCallback2 = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface(IVirtualDisplayCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IVirtualDisplayCallback)) {
                C0022i0 c0022i0 = new C0022i0();
                c0022i0.f324a = strongBinder;
                iVirtualDisplayCallback = c0022i0;
                int virtualDisplayID = ((G0) this).getVirtualDisplayID(iVirtualDisplayCallback, parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (Surface) AbstractC0054a.d(parcel, Surface.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(virtualDisplayID);
            }
            iVirtualDisplayCallback2 = (IVirtualDisplayCallback) iInterfaceQueryLocalInterface;
        }
        iVirtualDisplayCallback = iVirtualDisplayCallback2;
        int virtualDisplayID2 = ((G0) this).getVirtualDisplayID(iVirtualDisplayCallback, parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (Surface) AbstractC0054a.d(parcel, Surface.CREATOR), parcel.readInt());
        parcel2.writeNoException();
        parcel2.writeInt(virtualDisplayID2);
    }

    public final void i(Parcel parcel, Parcel parcel2) {
        int virtualDisplayIdV2 = ((G0) this).getVirtualDisplayIdV2(AbstractBinderC0026k0.a(parcel.readStrongBinder()), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (Surface) AbstractC0054a.d(parcel, Surface.CREATOR), parcel.readInt());
        parcel2.writeNoException();
        parcel2.writeInt(virtualDisplayIdV2);
    }

    public final void j(Parcel parcel, Parcel parcel2) {
        ((G0) this).getVirtualDisplayIdV2VDM(AbstractBinderC0026k0.a(parcel.readStrongBinder()), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (Surface) AbstractC0054a.d(parcel, Surface.CREATOR), parcel.readInt());
        parcel2.writeNoException();
        parcel2.writeInt(-1);
    }

    public final void k(Parcel parcel, Parcel parcel2) {
        j0.q qVar;
        j0.q qVar2;
        j0.g gVar;
        j0.g gVar2;
        j0.i iVar;
        j0.i iVar2;
        j0.o oVar;
        j0.o oVar2;
        j0.e eVar;
        j0.e eVar2;
        j0.k kVar;
        j0.k kVar2;
        IBinder strongBinder = parcel.readStrongBinder();
        j0.m mVar = null;
        if (strongBinder == null) {
            qVar2 = null;
        } else {
            IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.IStateListener");
            if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof j0.q)) {
                j0.p pVar = new j0.p();
                pVar.f2744a = strongBinder;
                qVar = pVar;
            } else {
                qVar = (j0.q) iInterfaceQueryLocalInterface;
            }
            qVar2 = qVar;
        }
        IBinder strongBinder2 = parcel.readStrongBinder();
        if (strongBinder2 == null) {
            gVar2 = null;
        } else {
            IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.IDeviceInfoListener");
            if (iInterfaceQueryLocalInterface2 == null || !(iInterfaceQueryLocalInterface2 instanceof j0.g)) {
                j0.f fVar = new j0.f();
                fVar.f2739a = strongBinder2;
                gVar = fVar;
            } else {
                gVar = (j0.g) iInterfaceQueryLocalInterface2;
            }
            gVar2 = gVar;
        }
        IBinder strongBinder3 = parcel.readStrongBinder();
        if (strongBinder3 == null) {
            iVar2 = null;
        } else {
            IInterface iInterfaceQueryLocalInterface3 = strongBinder3.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.IDiscoveryStateListener");
            if (iInterfaceQueryLocalInterface3 == null || !(iInterfaceQueryLocalInterface3 instanceof j0.i)) {
                j0.h hVar = new j0.h();
                hVar.f2740a = strongBinder3;
                iVar = hVar;
            } else {
                iVar = (j0.i) iInterfaceQueryLocalInterface3;
            }
            iVar2 = iVar;
        }
        IBinder strongBinder4 = parcel.readStrongBinder();
        if (strongBinder4 == null) {
            oVar2 = null;
        } else {
            IInterface iInterfaceQueryLocalInterface4 = strongBinder4.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.IPeerListListener");
            if (iInterfaceQueryLocalInterface4 == null || !(iInterfaceQueryLocalInterface4 instanceof j0.o)) {
                j0.n nVar = new j0.n();
                nVar.f2743a = strongBinder4;
                oVar = nVar;
            } else {
                oVar = (j0.o) iInterfaceQueryLocalInterface4;
            }
            oVar2 = oVar;
        }
        IBinder strongBinder5 = parcel.readStrongBinder();
        if (strongBinder5 == null) {
            eVar2 = null;
        } else {
            IInterface iInterfaceQueryLocalInterface5 = strongBinder5.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.IConnectionInfoListener");
            if (iInterfaceQueryLocalInterface5 == null || !(iInterfaceQueryLocalInterface5 instanceof j0.e)) {
                j0.d dVar = new j0.d();
                dVar.f2738a = strongBinder5;
                eVar = dVar;
            } else {
                eVar = (j0.e) iInterfaceQueryLocalInterface5;
            }
            eVar2 = eVar;
        }
        IBinder strongBinder6 = parcel.readStrongBinder();
        if (strongBinder6 == null) {
            kVar2 = null;
        } else {
            IInterface iInterfaceQueryLocalInterface6 = strongBinder6.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.IGroupInfoListener");
            if (iInterfaceQueryLocalInterface6 == null || !(iInterfaceQueryLocalInterface6 instanceof j0.k)) {
                j0.j jVar = new j0.j();
                jVar.f2741a = strongBinder6;
                kVar = jVar;
            } else {
                kVar = (j0.k) iInterfaceQueryLocalInterface6;
            }
            kVar2 = kVar;
        }
        IBinder strongBinder7 = parcel.readStrongBinder();
        if (strongBinder7 != null) {
            IInterface iInterfaceQueryLocalInterface7 = strongBinder7.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.INetworkInfoListener");
            if (iInterfaceQueryLocalInterface7 == null || !(iInterfaceQueryLocalInterface7 instanceof j0.m)) {
                j0.l lVar = new j0.l();
                lVar.f2742a = strongBinder7;
                mVar = lVar;
            } else {
                mVar = (j0.m) iInterfaceQueryLocalInterface7;
            }
        }
        ((G0) this).wifiDirectRegisterListener(qVar2, gVar2, iVar2, oVar2, eVar2, kVar2, mVar);
        parcel2.writeNoException();
    }

    @Override // android.os.Binder
    public final boolean onTransact(int i2, Parcel parcel, Parcel parcel2, int i3) {
        boolean z2;
        if (i2 >= 1 && i2 <= 16777215) {
            parcel.enforceInterface(IMotoDesktopManager.DESCRIPTOR);
        }
        if (i2 == 1598968902) {
            parcel2.writeString(IMotoDesktopManager.DESCRIPTOR);
            return true;
        }
        IRdpPimaryClipChangedListener iRdpPimaryClipChangedListener = null;
        a0.b bVar = null;
        a0.b bVar2 = null;
        InterfaceC0023j interfaceC0023j = null;
        InterfaceC0023j interfaceC0023j2 = null;
        InterfaceC0020h0 interfaceC0020h0 = null;
        InterfaceC0020h0 interfaceC0020h02 = null;
        InterfaceC0046z interfaceC0046z = null;
        InterfaceC0046z interfaceC0046z2 = null;
        j0.k kVar = null;
        j0.m mVar = null;
        j0.e eVar = null;
        j0.o oVar = null;
        j0.i iVar = null;
        j0.g gVar = null;
        j0.q qVar = null;
        InterfaceC0041u interfaceC0041u = null;
        InterfaceC0041u interfaceC0041u2 = null;
        Q q2 = null;
        Q q3 = null;
        T t2 = null;
        T t3 = null;
        F f2 = null;
        F f3 = null;
        IRdpPimaryClipChangedListener iRdpPimaryClipChangedListener2 = null;
        switch (i2) {
            case 1:
                h(parcel, parcel2);
                return true;
            case 2:
                ((G0) this).setVirtualDisplaySurface((Surface) AbstractC0054a.d(parcel, Surface.CREATOR));
                parcel2.writeNoException();
                return true;
            case 3:
                ((G0) this).releaseDisplay(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case EventType.EVENT_MSC /* 4 */:
                ((G0) this).setShowRdpPointer(parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            case EventType.EVENT_SW /* 5 */:
                ((G0) this).notifyAuthorized(parcel.readInt(), parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            case 6:
                ((G0) this).sendKeySync((KeyEvent) AbstractC0054a.d(parcel, KeyEvent.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 7:
                ((G0) this).sendPointerSync((MotionEvent) AbstractC0054a.d(parcel, MotionEvent.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 8:
                ((G0) this).registerOnRdpPointerStateChangedListener(W.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 9:
                ClipData primaryClip = ((G0) this).getPrimaryClip();
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, primaryClip);
                return true;
            case 10:
                ClipDescription primaryClipDescription = ((G0) this).getPrimaryClipDescription();
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, primaryClipDescription);
                return true;
            case 11:
                ((G0) this).setPrimaryClip((ClipData) AbstractC0054a.d(parcel, ClipData.CREATOR));
                parcel2.writeNoException();
                return true;
            case 12:
                IBinder strongBinder = parcel.readStrongBinder();
                if (strongBinder != null) {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface(IRdpPimaryClipChangedListener.DESCRIPTOR);
                    if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IRdpPimaryClipChangedListener)) {
                        U u2 = new U();
                        u2.f299a = strongBinder;
                        iRdpPimaryClipChangedListener = u2;
                    } else {
                        iRdpPimaryClipChangedListener = (IRdpPimaryClipChangedListener) iInterfaceQueryLocalInterface;
                    }
                }
                ((G0) this).addPrimaryClipChangedListener(iRdpPimaryClipChangedListener);
                parcel2.writeNoException();
                return true;
            case 13:
                IBinder strongBinder2 = parcel.readStrongBinder();
                if (strongBinder2 != null) {
                    IInterface iInterfaceQueryLocalInterface2 = strongBinder2.queryLocalInterface(IRdpPimaryClipChangedListener.DESCRIPTOR);
                    if (iInterfaceQueryLocalInterface2 == null || !(iInterfaceQueryLocalInterface2 instanceof IRdpPimaryClipChangedListener)) {
                        U u3 = new U();
                        u3.f299a = strongBinder2;
                        iRdpPimaryClipChangedListener2 = u3;
                    } else {
                        iRdpPimaryClipChangedListener2 = (IRdpPimaryClipChangedListener) iInterfaceQueryLocalInterface2;
                    }
                }
                ((G0) this).removePrimaryClipChangedListener(iRdpPimaryClipChangedListener2);
                parcel2.writeNoException();
                return true;
            case 14:
                ((G0) this).setRdpAudioActivatedState(parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            case 15:
                ((G0) this).setRdpAudioStartRecord();
                parcel2.writeNoException();
                return true;
            case 16:
                ((G0) this).setRdpAudioRecordingCallState(parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            case EventType.EVENT_LED /* 17 */:
                ((G0) this).setRdpPointerPosition(parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case EventType.EVENT_SND /* 18 */:
                ((G0) this).enableRdpcdrom(parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            case 19:
                boolean zCreateAudioRecord = ((G0) this).createAudioRecord(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zCreateAudioRecord ? 1 : 0);
                return true;
            case EventType.EVENT_REP /* 20 */:
                int audioRecordState = ((G0) this).getAudioRecordState();
                parcel2.writeNoException();
                parcel2.writeInt(audioRecordState);
                return true;
            case EventType.EVENT_FF /* 21 */:
                ((G0) this).startAudioRecording();
                parcel2.writeNoException();
                return true;
            case EventType.EVENT_PWR /* 22 */:
                byte[] bArrCreateByteArray = parcel.createByteArray();
                int audioRecordData = ((G0) this).readAudioRecordData(bArrCreateByteArray, parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(audioRecordData);
                parcel2.writeByteArray(bArrCreateByteArray);
                return true;
            case EventType.EVENT_FF_STATUS /* 23 */:
                ((G0) this).releaseAudioRecord();
                parcel2.writeNoException();
                return true;
            case 24:
                ((G0) this).putIntToMotorolaSettings(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 25:
                a(parcel, parcel2);
                return true;
            case 26:
                ((G0) this).removeDesktopIcon(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 27:
                int[] iArrQueryDesktopIcon = ((G0) this).queryDesktopIcon(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeIntArray(iArrQueryDesktopIcon);
                return true;
            case 28:
                z2 = parcel.readInt() != 0;
                String string = parcel.readString();
                String string2 = parcel.readString();
                IBinder strongBinder3 = parcel.readStrongBinder();
                if (strongBinder3 != null) {
                    IInterface iInterfaceQueryLocalInterface3 = strongBinder3.queryLocalInterface("com.motorola.mobiledesktop.core.INcmCallback");
                    if (iInterfaceQueryLocalInterface3 == null || !(iInterfaceQueryLocalInterface3 instanceof F)) {
                        E e2 = new E();
                        e2.f263a = strongBinder3;
                        f3 = e2;
                    } else {
                        f3 = (F) iInterfaceQueryLocalInterface3;
                    }
                }
                ((G0) this).setNcmFunction(z2, string, string2, f3);
                parcel2.writeNoException();
                return true;
            case 29:
                ((G0) this).dhcpRedirect(parcel.readInt() != 0, parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 30:
                parcel2.writeNoException();
                parcel2.writeInt(126);
                return true;
            case 31:
                ((G0) this).setConnectState(parcel.readInt() != 0, parcel.readInt() != 0, parcel.readString());
                parcel2.writeNoException();
                return true;
            case 32:
                ((G0) this).setWifiDisplayCallback(t0.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 33:
                List listQueryWifiDisplayDevice = ((G0) this).queryWifiDisplayDevice();
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, listQueryWifiDisplayDevice);
                return true;
            case 34:
                ((G0) this).connectWifiDisplay((MotoWifiDisplay) AbstractC0054a.d(parcel, MotoWifiDisplay.CREATOR));
                parcel2.writeNoException();
                return true;
            case 35:
                ((G0) this).disconnectWifiDisplay();
                parcel2.writeNoException();
                return true;
            case 36:
                boolean zIsWifiDisplaySwitchOn = ((G0) this).isWifiDisplaySwitchOn();
                parcel2.writeNoException();
                parcel2.writeInt(zIsWifiDisplaySwitchOn ? 1 : 0);
                return true;
            case 37:
                ((G0) this).setWifiDisplaySwitch(parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            case 38:
                ((G0) this).forgetWifiDisplay(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 39:
                ((G0) this).cancelConnectWifiDisplay();
                parcel2.writeNoException();
                return true;
            case 40:
                boolean z3 = parcel.readInt() != 0;
                boolean z4 = parcel.readInt() != 0;
                String string3 = parcel.readString();
                String string4 = parcel.readString();
                IBinder strongBinder4 = parcel.readStrongBinder();
                if (strongBinder4 != null) {
                    IInterface iInterfaceQueryLocalInterface4 = strongBinder4.queryLocalInterface("com.motorola.mobiledesktop.core.INcmCallback");
                    if (iInterfaceQueryLocalInterface4 == null || !(iInterfaceQueryLocalInterface4 instanceof F)) {
                        E e3 = new E();
                        e3.f263a = strongBinder4;
                        f2 = e3;
                    } else {
                        f2 = (F) iInterfaceQueryLocalInterface4;
                    }
                }
                ((G0) this).setTetheringFunction(z3, z4, string3, string4, f2);
                parcel2.writeNoException();
                return true;
            case 41:
                ((G0) this).setVirtualDisplaySupportedModes(parcel.createTypedArrayList(MotoDisplayMode.CREATOR));
                parcel2.writeNoException();
                return true;
            case 42:
                ((G0) this).resizeVirtualDisplay(parcel.readInt(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 43:
                ((G0) this).stopHotspot();
                parcel2.writeNoException();
                return true;
            case 44:
                int i4 = parcel.readInt();
                IBinder strongBinder5 = parcel.readStrongBinder();
                if (strongBinder5 != null) {
                    IInterface iInterfaceQueryLocalInterface5 = strongBinder5.queryLocalInterface("com.motorola.mobiledesktop.core.IPointerStateChangedListener");
                    if (iInterfaceQueryLocalInterface5 == null || !(iInterfaceQueryLocalInterface5 instanceof T)) {
                        S s2 = new S();
                        s2.f298a = strongBinder5;
                        t3 = s2;
                    } else {
                        t3 = (T) iInterfaceQueryLocalInterface5;
                    }
                }
                ((G0) this).registerOnPointerStateChangedListener(i4, t3);
                parcel2.writeNoException();
                return true;
            case 45:
                IBinder strongBinder6 = parcel.readStrongBinder();
                if (strongBinder6 != null) {
                    IInterface iInterfaceQueryLocalInterface6 = strongBinder6.queryLocalInterface("com.motorola.mobiledesktop.core.IPointerStateChangedListener");
                    if (iInterfaceQueryLocalInterface6 == null || !(iInterfaceQueryLocalInterface6 instanceof T)) {
                        S s3 = new S();
                        s3.f298a = strongBinder6;
                        t2 = s3;
                    } else {
                        t2 = (T) iInterfaceQueryLocalInterface6;
                    }
                }
                ((G0) this).deregisterOnPointerStateChangedListener(t2);
                parcel2.writeNoException();
                return true;
            case 46:
                int i5 = parcel.readInt();
                IBinder strongBinder7 = parcel.readStrongBinder();
                if (strongBinder7 != null) {
                    IInterface iInterfaceQueryLocalInterface7 = strongBinder7.queryLocalInterface("com.motorola.mobiledesktop.core.IPointerPositionChangedListener");
                    if (iInterfaceQueryLocalInterface7 == null || !(iInterfaceQueryLocalInterface7 instanceof Q)) {
                        P p2 = new P();
                        p2.f285a = strongBinder7;
                        q3 = p2;
                    } else {
                        q3 = (Q) iInterfaceQueryLocalInterface7;
                    }
                }
                ((G0) this).registerOnPointerPositionChangedListener(i5, q3);
                parcel2.writeNoException();
                return true;
            case 47:
                IBinder strongBinder8 = parcel.readStrongBinder();
                if (strongBinder8 != null) {
                    IInterface iInterfaceQueryLocalInterface8 = strongBinder8.queryLocalInterface("com.motorola.mobiledesktop.core.IPointerPositionChangedListener");
                    if (iInterfaceQueryLocalInterface8 == null || !(iInterfaceQueryLocalInterface8 instanceof Q)) {
                        P p3 = new P();
                        p3.f285a = strongBinder8;
                        q2 = p3;
                    } else {
                        q2 = (Q) iInterfaceQueryLocalInterface8;
                    }
                }
                ((G0) this).deregisterOnPointerPositionChangedListener(q2);
                parcel2.writeNoException();
                return true;
            case 48:
                ((G0) this).setShowPointer(parcel.readInt(), parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            case 49:
                MotoDisplay motoDisplay = ((G0) this).getMotoDisplay(parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, motoDisplay);
                return true;
            case 50:
                MotoWifiDisplay connectedWifiDisplay = ((G0) this).getConnectedWifiDisplay();
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, connectedWifiDisplay);
                return true;
            case 51:
                ((G0) this).startWifiDisplayScan();
                parcel2.writeNoException();
                return true;
            case 52:
                ((G0) this).stopWifiDisplayScan();
                parcel2.writeNoException();
                return true;
            case 53:
                boolean zIsWifiDisplayConnecting = ((G0) this).isWifiDisplayConnecting();
                parcel2.writeNoException();
                parcel2.writeInt(zIsWifiDisplayConnecting ? 1 : 0);
                return true;
            case 54:
                Uri uri = (Uri) AbstractC0054a.d(parcel, Uri.CREATOR);
                z2 = parcel.readInt() != 0;
                int i6 = parcel.readInt();
                IBinder strongBinder9 = parcel.readStrongBinder();
                if (strongBinder9 != null) {
                    IInterface iInterfaceQueryLocalInterface9 = strongBinder9.queryLocalInterface("com.motorola.mobiledesktop.core.IContentObserver");
                    if (iInterfaceQueryLocalInterface9 == null || !(iInterfaceQueryLocalInterface9 instanceof InterfaceC0041u)) {
                        C0040t c0040t = new C0040t();
                        c0040t.f332a = strongBinder9;
                        interfaceC0041u2 = c0040t;
                    } else {
                        interfaceC0041u2 = (InterfaceC0041u) iInterfaceQueryLocalInterface9;
                    }
                }
                ((G0) this).registerContentObserver(uri, z2, i6, interfaceC0041u2);
                parcel2.writeNoException();
                return true;
            case 55:
                IBinder strongBinder10 = parcel.readStrongBinder();
                if (strongBinder10 != null) {
                    IInterface iInterfaceQueryLocalInterface10 = strongBinder10.queryLocalInterface("com.motorola.mobiledesktop.core.IContentObserver");
                    if (iInterfaceQueryLocalInterface10 == null || !(iInterfaceQueryLocalInterface10 instanceof InterfaceC0041u)) {
                        C0040t c0040t2 = new C0040t();
                        c0040t2.f332a = strongBinder10;
                        interfaceC0041u = c0040t2;
                    } else {
                        interfaceC0041u = (InterfaceC0041u) iInterfaceQueryLocalInterface10;
                    }
                }
                ((G0) this).unregisterContentObserver(interfaceC0041u);
                parcel2.writeNoException();
                return true;
            case 56:
                boolean currentUserLargePointerSetting = ((G0) this).getCurrentUserLargePointerSetting();
                parcel2.writeNoException();
                parcel2.writeInt(currentUserLargePointerSetting ? 1 : 0);
                return true;
            case 57:
                ((G0) this).setLocationEnabled();
                parcel2.writeNoException();
                return true;
            case 58:
                int currentUserPointerSize = ((G0) this).getCurrentUserPointerSize();
                parcel2.writeNoException();
                parcel2.writeInt(currentUserPointerSize);
                return true;
            case 59:
                ((G0) this).grantRuntimePermission(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 60:
                ((G0) this).revokeRuntimePermission(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 61:
                long usbCurrentFunctions = ((G0) this).getUsbCurrentFunctions();
                parcel2.writeNoException();
                parcel2.writeLong(usbCurrentFunctions);
                return true;
            case 62:
                long usbWebcamFunction = ((G0) this).getUsbWebcamFunction();
                parcel2.writeNoException();
                parcel2.writeLong(usbWebcamFunction);
                return true;
            case 63:
                ((G0) this).startSettingsUsbDetailsActivity();
                parcel2.writeNoException();
                return true;
            case 64:
                k(parcel, parcel2);
                return true;
            case 65:
                IBinder strongBinder11 = parcel.readStrongBinder();
                if (strongBinder11 != null) {
                    IInterface iInterfaceQueryLocalInterface11 = strongBinder11.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.IStateListener");
                    if (iInterfaceQueryLocalInterface11 == null || !(iInterfaceQueryLocalInterface11 instanceof j0.q)) {
                        j0.p pVar = new j0.p();
                        pVar.f2744a = strongBinder11;
                        qVar = pVar;
                    } else {
                        qVar = (j0.q) iInterfaceQueryLocalInterface11;
                    }
                }
                ((G0) this).wifiDirectRequestState(qVar);
                parcel2.writeNoException();
                return true;
            case 66:
                IBinder strongBinder12 = parcel.readStrongBinder();
                if (strongBinder12 != null) {
                    IInterface iInterfaceQueryLocalInterface12 = strongBinder12.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.IDeviceInfoListener");
                    if (iInterfaceQueryLocalInterface12 == null || !(iInterfaceQueryLocalInterface12 instanceof j0.g)) {
                        j0.f fVar = new j0.f();
                        fVar.f2739a = strongBinder12;
                        gVar = fVar;
                    } else {
                        gVar = (j0.g) iInterfaceQueryLocalInterface12;
                    }
                }
                ((G0) this).wifiDirectRequestDeviceInfo(gVar);
                parcel2.writeNoException();
                return true;
            case 67:
                IBinder strongBinder13 = parcel.readStrongBinder();
                if (strongBinder13 != null) {
                    IInterface iInterfaceQueryLocalInterface13 = strongBinder13.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.IDiscoveryStateListener");
                    if (iInterfaceQueryLocalInterface13 == null || !(iInterfaceQueryLocalInterface13 instanceof j0.i)) {
                        j0.h hVar = new j0.h();
                        hVar.f2740a = strongBinder13;
                        iVar = hVar;
                    } else {
                        iVar = (j0.i) iInterfaceQueryLocalInterface13;
                    }
                }
                ((G0) this).wifiDirectRequestDiscoveryState(iVar);
                parcel2.writeNoException();
                return true;
            case 68:
                ((G0) this).wifiDirectDiscoverPeers(j0.b.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 69:
                ((G0) this).wifiDirectStopPeerDiscovery(j0.b.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 70:
                IBinder strongBinder14 = parcel.readStrongBinder();
                if (strongBinder14 != null) {
                    IInterface iInterfaceQueryLocalInterface14 = strongBinder14.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.IPeerListListener");
                    if (iInterfaceQueryLocalInterface14 == null || !(iInterfaceQueryLocalInterface14 instanceof j0.o)) {
                        j0.n nVar = new j0.n();
                        nVar.f2743a = strongBinder14;
                        oVar = nVar;
                    } else {
                        oVar = (j0.o) iInterfaceQueryLocalInterface14;
                    }
                }
                ((G0) this).wifiDirectRequestPeers(oVar);
                parcel2.writeNoException();
                return true;
            case 71:
                ((G0) this).wifiDirectConnect((WifiP2pConfig) AbstractC0054a.d(parcel, WifiP2pConfig.CREATOR), j0.b.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 72:
                ((G0) this).wifiDirectCancelConnect(j0.b.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 73:
                IBinder strongBinder15 = parcel.readStrongBinder();
                if (strongBinder15 != null) {
                    IInterface iInterfaceQueryLocalInterface15 = strongBinder15.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.IConnectionInfoListener");
                    if (iInterfaceQueryLocalInterface15 == null || !(iInterfaceQueryLocalInterface15 instanceof j0.e)) {
                        j0.d dVar = new j0.d();
                        dVar.f2738a = strongBinder15;
                        eVar = dVar;
                    } else {
                        eVar = (j0.e) iInterfaceQueryLocalInterface15;
                    }
                }
                ((G0) this).wifiDirectRequestConnectionInfo(eVar);
                parcel2.writeNoException();
                return true;
            case 74:
                IBinder strongBinder16 = parcel.readStrongBinder();
                if (strongBinder16 != null) {
                    IInterface iInterfaceQueryLocalInterface16 = strongBinder16.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.INetworkInfoListener");
                    if (iInterfaceQueryLocalInterface16 == null || !(iInterfaceQueryLocalInterface16 instanceof j0.m)) {
                        j0.l lVar = new j0.l();
                        lVar.f2742a = strongBinder16;
                        mVar = lVar;
                    } else {
                        mVar = (j0.m) iInterfaceQueryLocalInterface16;
                    }
                }
                ((G0) this).wifiDirectRequestNetworkInfo(mVar);
                parcel2.writeNoException();
                return true;
            case 75:
                ((G0) this).wifiDirectCreateGroup((WifiP2pConfig) AbstractC0054a.d(parcel, WifiP2pConfig.CREATOR), j0.b.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 76:
                ((G0) this).wifiDirectRemoveGroup(j0.b.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 77:
                IBinder strongBinder17 = parcel.readStrongBinder();
                if (strongBinder17 != null) {
                    IInterface iInterfaceQueryLocalInterface17 = strongBinder17.queryLocalInterface("com.motorola.mobiledesktop.core.wifidirect.IGroupInfoListener");
                    if (iInterfaceQueryLocalInterface17 == null || !(iInterfaceQueryLocalInterface17 instanceof j0.k)) {
                        j0.j jVar = new j0.j();
                        jVar.f2741a = strongBinder17;
                        kVar = jVar;
                    } else {
                        kVar = (j0.k) iInterfaceQueryLocalInterface17;
                    }
                }
                ((G0) this).wifiDirectRequestGroupInfo(kVar);
                parcel2.writeNoException();
                return true;
            case 78:
                ((G0) this).wifiDirectDeletePersistentGroup(parcel.readString(), j0.b.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 79:
                ((G0) this).setWifiEnabled(parcel.readInt() != 0);
                parcel2.writeNoException();
                parcel2.writeInt(1);
                return true;
            case 80:
                ((G0) this).stopAudioRecording();
                parcel2.writeNoException();
                return true;
            case 81:
                List desktopSupportedModes = ((G0) this).getDesktopSupportedModes(parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, desktopSupportedModes);
                return true;
            case 82:
                ((G0) this).putMotoSettingString(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 83:
                ((G0) this).listenCallStateChange(r.a(parcel.readStrongBinder()), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 84:
                int callState = ((G0) this).getCallState();
                parcel2.writeNoException();
                parcel2.writeInt(callState);
                return true;
            case 85:
                String serial = ((G0) this).getSerial();
                parcel2.writeNoException();
                parcel2.writeString(serial);
                return true;
            case 86:
                ((G0) this).launchActivity((Intent) AbstractC0054a.d(parcel, Intent.CREATOR));
                parcel2.writeNoException();
                return true;
            case 87:
                ((G0) this).putSystemSettingString(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 88:
                ((G0) this).putIntToMotorolaSystemSettings(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 89:
                boolean zCancelBondProcess = ((G0) this).cancelBondProcess((BluetoothDevice) AbstractC0054a.d(parcel, BluetoothDevice.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(zCancelBondProcess ? 1 : 0);
                return true;
            case 90:
                boolean zCreateBond = ((G0) this).createBond((BluetoothDevice) AbstractC0054a.d(parcel, BluetoothDevice.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zCreateBond ? 1 : 0);
                return true;
            case 91:
                boolean zRemoveBond = ((G0) this).removeBond((BluetoothDevice) AbstractC0054a.d(parcel, BluetoothDevice.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(zRemoveBond ? 1 : 0);
                return true;
            case 92:
                boolean pairingConfirmation = ((G0) this).setPairingConfirmation((BluetoothDevice) AbstractC0054a.d(parcel, BluetoothDevice.CREATOR), parcel.readInt() != 0);
                parcel2.writeNoException();
                parcel2.writeInt(pairingConfirmation ? 1 : 0);
                return true;
            case 93:
                MotoDisplayMode currentDisplayMode = ((G0) this).getCurrentDisplayMode(parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, currentDisplayMode);
                return true;
            case 94:
                boolean zIsReadyForDisplay = ((G0) this).isReadyForDisplay(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zIsReadyForDisplay ? 1 : 0);
                return true;
            case 95:
                int displayUiMode = ((G0) this).getDisplayUiMode(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(displayUiMode);
                return true;
            case 96:
                MotoWifiDisplay connectingWifiDisplay = ((G0) this).getConnectingWifiDisplay();
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, connectingWifiDisplay);
                return true;
            case 97:
                ((G0) this).setUsbFunctions(parcel.readLong());
                parcel2.writeNoException();
                return true;
            case 98:
                ((G0) this).setIpForwardingEnabled(parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            case 99:
                String[] strArrListInterfaces = ((G0) this).listInterfaces();
                parcel2.writeNoException();
                parcel2.writeStringArray(strArrListInterfaces);
                return true;
            case 100:
                ((G0) this).setInterfaceConfig(parcel.readString(), (InterfaceConfigurationParcel) AbstractC0054a.d(parcel, InterfaceConfigurationParcel.CREATOR));
                parcel2.writeNoException();
                return true;
            case 101:
                ((G0) this).tetherInterface(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 102:
                ((G0) this).untetherInterface(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 103:
                ((G0) this).addInterfaceToLocalNetwork(parcel.readString(), parcel.createTypedArrayList(RouteInfoParcel.CREATOR));
                parcel2.writeNoException();
                return true;
            case 104:
                ((G0) this).removeRoutesFromLocalNetwork(parcel.createTypedArrayList(RouteInfoParcel.CREATOR));
                parcel2.writeNoException();
                return true;
            case 105:
                ((G0) this).startTetheringByOemNetd(parcel.readInt() != 0, parcel.readInt() != 0, parcel.createStringArray());
                parcel2.writeNoException();
                return true;
            case 106:
                ((G0) this).stopTethering();
                parcel2.writeNoException();
                return true;
            case 107:
                ((G0) this).interfaceAddAddress(parcel.readString(), parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 108:
                ((G0) this).goToPermissionManageSetting(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 109:
                ((G0) this).lockDevice();
                parcel2.writeNoException();
                return true;
            case 110:
                i(parcel, parcel2);
                return true;
            case 111:
                SoftApConfiguration softApConfiguration = ((G0) this).getSoftApConfiguration();
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, softApConfiguration);
                return true;
            case 112:
                boolean softApConfiguration2 = ((G0) this).setSoftApConfiguration((SoftApConfiguration) AbstractC0054a.d(parcel, SoftApConfiguration.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(softApConfiguration2 ? 1 : 0);
                return true;
            case 113:
                boolean zStartTetheredHotspot = ((G0) this).startTetheredHotspot((SoftApConfiguration) AbstractC0054a.d(parcel, SoftApConfiguration.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(zStartTetheredHotspot ? 1 : 0);
                return true;
            case 114:
                boolean zStopSoftAp = ((G0) this).stopSoftAp();
                parcel2.writeNoException();
                parcel2.writeInt(zStopSoftAp ? 1 : 0);
                return true;
            case 115:
                ((G0) this).registerSoftApCallback(AbstractBinderC0008b0.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 116:
                ((G0) this).unregisterSoftApCallback();
                parcel2.writeNoException();
                return true;
            case 117:
                ((G0) this).setCorePermissionDialogCallback(AbstractBinderC0043w.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 118:
                boolean zIsTetheringStarted = ((G0) this).isTetheringStarted();
                parcel2.writeNoException();
                parcel2.writeInt(zIsTetheringStarted ? 1 : 0);
                return true;
            case 119:
                ((G0) this).wakeUpDisplayGroup(parcel.readInt(), parcel.readLong(), parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 120:
                ((G0) this).goToSleepDisplayGroup(parcel.readInt(), parcel.readLong(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 121:
                int r4PcPointerSize = ((G0) this).getR4PcPointerSize();
                parcel2.writeNoException();
                parcel2.writeInt(r4PcPointerSize);
                return true;
            case 122:
                ((G0) this).putMotoSettingStringWithType(parcel.readInt(), parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 123:
                b(parcel, parcel2);
                return true;
            case 124:
                ((G0) this).releaseVirtualDisplay(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 125:
                ((G0) this).addUniqueIdAssociation(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 126:
                ((G0) this).removeUniqueIdAssociation(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 127:
                ((G0) this).startApp((Intent) AbstractC0054a.d(parcel, Intent.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 128:
                ((G0) this).forceStopPackage(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 129:
                ((G0) this).removeTask(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 130:
                List motoRunningTaskInfos = ((G0) this).getMotoRunningTaskInfos();
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, motoRunningTaskInfos);
                return true;
            case 131:
                List motoRunningTaskInfosByDisplayId = ((G0) this).getMotoRunningTaskInfosByDisplayId(parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, motoRunningTaskInfosByDisplayId);
                return true;
            case 132:
                ((G0) this).launchActivityWithDisplayId((Intent) AbstractC0054a.d(parcel, Intent.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 133:
                ((G0) this).resizeVirtualDisplayByDisplayId(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 134:
                ((G0) this).setVirtualDisplaySurfaceByDisplayId(parcel.readInt(), (Surface) AbstractC0054a.d(parcel, Surface.CREATOR));
                parcel2.writeNoException();
                return true;
            case 135:
                ((G0) this).startAppAsUser((Intent) AbstractC0054a.d(parcel, Intent.CREATOR), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 136:
                Intent intent = ((G0) this).getIntent((PendingIntent) AbstractC0054a.d(parcel, PendingIntent.CREATOR));
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, intent);
                return true;
            case 137:
                List runningTaskInfos = ((G0) this).getRunningTaskInfos();
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, runningTaskInfos);
                return true;
            case 138:
                List runningTaskInfosByDisplayId = ((G0) this).getRunningTaskInfosByDisplayId(parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, runningTaskInfosByDisplayId);
                return true;
            case 139:
                ((G0) this).sendPendingIntent((PendingIntent) AbstractC0054a.d(parcel, PendingIntent.CREATOR));
                parcel2.writeNoException();
                return true;
            case 140:
                ((G0) this).forceStopPackageAsUser(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 141:
                c(parcel, parcel2);
                return true;
            case 142:
                boolean zIsAppCloneUser = ((G0) this).isAppCloneUser(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zIsAppCloneUser ? 1 : 0);
                return true;
            case 143:
                PackageInfo packageInfoAsUser = ((G0) this).getPackageInfoAsUser(parcel.readString(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, packageInfoAsUser);
                return true;
            case 144:
                List listQueryIntentActivitiesAsUser = ((G0) this).queryIntentActivitiesAsUser((Intent) AbstractC0054a.d(parcel, Intent.CREATOR), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, listQueryIntentActivitiesAsUser);
                return true;
            case 145:
                int fieldValueByReflect = ((G0) this).getFieldValueByReflect(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(fieldValueByReflect);
                return true;
            case 146:
                boolean zCreateAudioTrackForMic = ((G0) this).createAudioTrackForMic(parcel.readInt(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zCreateAudioTrackForMic ? 1 : 0);
                return true;
            case 147:
                int iWriteAudioTrackDataForMic = ((G0) this).writeAudioTrackDataForMic(parcel.createByteArray(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(iWriteAudioTrackDataForMic);
                return true;
            case 148:
                ((G0) this).releaseAudioTrackForMic();
                parcel2.writeNoException();
                return true;
            case 149:
                boolean zCreateAudioTrackForIpCall = ((G0) this).createAudioTrackForIpCall(parcel.readInt(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zCreateAudioTrackForIpCall ? 1 : 0);
                return true;
            case 150:
                int iWriteAudioTrackDataForIpCall = ((G0) this).writeAudioTrackDataForIpCall(parcel.createByteArray(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(iWriteAudioTrackDataForIpCall);
                return true;
            case 151:
                ((G0) this).releaseAudioTrackForIpCall();
                parcel2.writeNoException();
                return true;
            case 152:
                boolean zCreateAudioTrackForDialCall = ((G0) this).createAudioTrackForDialCall(parcel.readInt(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zCreateAudioTrackForDialCall ? 1 : 0);
                return true;
            case 153:
                int iWriteAudioTrackDataForDialCall = ((G0) this).writeAudioTrackDataForDialCall(parcel.createByteArray(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(iWriteAudioTrackDataForDialCall);
                return true;
            case 154:
                ((G0) this).releaseAudioTrackForDialCall();
                parcel2.writeNoException();
                return true;
            case 155:
                boolean zDesktopCommitText = ((G0) this).desktopCommitText(parcel.readInt(), (CharSequence) AbstractC0054a.d(parcel, TextUtils.CHAR_SEQUENCE_CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(zDesktopCommitText ? 1 : 0);
                return true;
            case 156:
                IBinder strongBinder18 = parcel.readStrongBinder();
                if (strongBinder18 != null) {
                    IInterface iInterfaceQueryLocalInterface18 = strongBinder18.queryLocalInterface("com.motorola.mobiledesktop.core.IDesktopInputMethodListener");
                    if (iInterfaceQueryLocalInterface18 == null || !(iInterfaceQueryLocalInterface18 instanceof InterfaceC0046z)) {
                        C0045y c0045y = new C0045y();
                        c0045y.f342a = strongBinder18;
                        interfaceC0046z2 = c0045y;
                    } else {
                        interfaceC0046z2 = (InterfaceC0046z) iInterfaceQueryLocalInterface18;
                    }
                }
                ((G0) this).registerDesktopInputMethodListener(interfaceC0046z2);
                parcel2.writeNoException();
                return true;
            case 157:
                IBinder strongBinder19 = parcel.readStrongBinder();
                if (strongBinder19 != null) {
                    IInterface iInterfaceQueryLocalInterface19 = strongBinder19.queryLocalInterface("com.motorola.mobiledesktop.core.IDesktopInputMethodListener");
                    if (iInterfaceQueryLocalInterface19 == null || !(iInterfaceQueryLocalInterface19 instanceof InterfaceC0046z)) {
                        C0045y c0045y2 = new C0045y();
                        c0045y2.f342a = strongBinder19;
                        interfaceC0046z = c0045y2;
                    } else {
                        interfaceC0046z = (InterfaceC0046z) iInterfaceQueryLocalInterface19;
                    }
                }
                ((G0) this).unregisterDesktopInputMethodListener(interfaceC0046z);
                parcel2.writeNoException();
                return true;
            case 158:
                ((G0) this).putSystemSettingIntWithType(parcel.readInt(), parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 159:
                ((G0) this).setDisplayImePolicy(parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 160:
                Rect focusedWindowRect = ((G0) this).getFocusedWindowRect(parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, focusedWindowRect);
                return true;
            case 161:
                float[] lastMousePosition = ((G0) this).getLastMousePosition(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeFloatArray(lastMousePosition);
                return true;
            case 162:
                ((G0) this).sendKeyEvent((KeyEvent) AbstractC0054a.d(parcel, KeyEvent.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 163:
                ((G0) this).injectInputEvent((MotionEvent) AbstractC0054a.d(parcel, MotionEvent.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 164:
                WindowManager.LayoutParams layoutParamsObtainPreventFocusWindowLayoutParams = ((G0) this).obtainPreventFocusWindowLayoutParams();
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, layoutParamsObtainPreventFocusWindowLayoutParams);
                return true;
            case 165:
                parcel.readInt();
                parcel.readInt();
                parcel2.writeNoException();
                return true;
            case 166:
                Intent launchIntentForPackage = ((G0) this).getLaunchIntentForPackage(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, launchIntentForPackage);
                return true;
            case 167:
                ((G0) this).setPackageChangeCallback(K.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 168:
                int focusDisplayId = ((G0) this).getFocusDisplayId();
                parcel2.writeNoException();
                parcel2.writeInt(focusDisplayId);
                return true;
            case 169:
                MotoRunningTaskInfoNew topTaskInfo = ((G0) this).getTopTaskInfo(parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, topTaskInfo);
                return true;
            case 170:
                List recentTasksByDisplayId = ((G0) this).getRecentTasksByDisplayId(parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, recentTasksByDisplayId);
                return true;
            case 171:
                boolean zIsRestrictedDisplay = ((G0) this).isRestrictedDisplay(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zIsRestrictedDisplay ? 1 : 0);
                return true;
            case 172:
                ((G0) this).startShortcut(parcel.readString(), parcel.readString(), (Rect) AbstractC0054a.d(parcel, Rect.CREATOR), (Bundle) AbstractC0054a.d(parcel, Bundle.CREATOR), (UserHandle) AbstractC0054a.d(parcel, UserHandle.CREATOR));
                parcel2.writeNoException();
                return true;
            case 173:
                e(parcel, parcel2);
                return true;
            case 174:
                ((G0) this).setProjectionRect(parcel.readInt(), (Rect) AbstractC0054a.d(parcel, Rect.CREATOR));
                parcel2.writeNoException();
                return true;
            case 175:
                int motoSettingIntegerWithType = ((G0) this).getMotoSettingIntegerWithType(parcel.readInt(), parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(motoSettingIntegerWithType);
                return true;
            case 176:
                float motoSettingFloatWithType = ((G0) this).getMotoSettingFloatWithType(parcel.readInt(), parcel.readString(), parcel.readFloat());
                parcel2.writeNoException();
                parcel2.writeFloat(motoSettingFloatWithType);
                return true;
            case 177:
                ((G0) this).putMotoSettingIntegerWithType(parcel.readInt(), parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 178:
                ((G0) this).putMotoSettingFloatWithType(parcel.readInt(), parcel.readString(), parcel.readFloat());
                parcel2.writeNoException();
                return true;
            case 179:
                int pointSpeed = ((G0) this).getPointSpeed();
                parcel2.writeNoException();
                parcel2.writeInt(pointSpeed);
                return true;
            case 180:
                ((G0) this).setPointSpeed(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 181:
                ((G0) this).setForcedDisplayDensity(parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 182:
                int defaultDisplayDensity = ((G0) this).getDefaultDisplayDensity(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(defaultDisplayDensity);
                return true;
            case 183:
                ((G0) this).setDesktopRestartModeByPackages(parcel.createStringArrayList(), parcel.createStringArrayList());
                parcel2.writeNoException();
                return true;
            case 184:
                List desktopRestartModeByPackages = ((G0) this).getDesktopRestartModeByPackages(parcel.createStringArrayList());
                parcel2.writeNoException();
                parcel2.writeStringList(desktopRestartModeByPackages);
                return true;
            case 185:
                Bundle appRestartConfigerBundle = ((G0) this).getAppRestartConfigerBundle(parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, appRestartConfigerBundle);
                return true;
            case 186:
                ((G0) this).setFontScale(parcel.readInt(), parcel.readFloat());
                parcel2.writeNoException();
                return true;
            case 187:
                ((G0) this).startTethering((TetheringRequest) AbstractC0054a.d(parcel, TetheringRequest.CREATOR), AbstractBinderC0014e0.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 188:
                ((G0) this).stopTetheringWithType(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 189:
                ((G0) this).launchActivityWithBundle((Intent) AbstractC0054a.d(parcel, Intent.CREATOR), (Bundle) AbstractC0054a.d(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
                return true;
            case 190:
                boolean zIsFwkSupportDragDrop = ((G0) this).isFwkSupportDragDrop();
                parcel2.writeNoException();
                parcel2.writeInt(zIsFwkSupportDragDrop ? 1 : 0);
                return true;
            case 191:
                ((G0) this).bindDragDropService();
                parcel2.writeNoException();
                return true;
            case 192:
                ((G0) this).unbindDragDropService();
                parcel2.writeNoException();
                return true;
            case 193:
                ((G0) this).onReadyForDrop(parcel.readLong(), (ClipData) AbstractC0054a.d(parcel, ClipData.CREATOR), parcel.readInt(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 194:
                ((G0) this).registerDragDropCallback(B.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 195:
                ((G0) this).setCustomPointerIconForFullScreen((PointerIcon) AbstractC0054a.d(parcel, PointerIcon.CREATOR), parcel.readInt(), parcel.readInt() != 0, parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 196:
                ((G0) this).setAudioSettingChangeCallback(AbstractBinderC0027l.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 197:
                ((G0) this).setAudioSettingVolumeByIndex(parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 198:
                ((G0) this).setAudioSettingCheckState(parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            case 199:
                ((G0) this).setAudioSettingFocus(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 200:
                ((G0) this).startNearbyShareSysWatcher(H.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 201:
                ((G0) this).stopNearbyShareSysWatcher();
                parcel2.writeNoException();
                return true;
            case 202:
                ((G0) this).sendUriToNearbyPc((NearbyShareDevice) AbstractC0054a.d(parcel, NearbyShareDevice.CREATOR), parcel.readString());
                parcel2.writeNoException();
                return true;
            case 203:
                String physicalDisplayId = ((G0) this).getPhysicalDisplayId(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeString(physicalDisplayId);
                return true;
            case 204:
                boolean zEnableWiFi = ((G0) this).enableWiFi(parcel.readInt() != 0);
                parcel2.writeNoException();
                parcel2.writeInt(zEnableWiFi ? 1 : 0);
                return true;
            case 205:
                boolean zEnableBle = ((G0) this).enableBle(parcel.readInt() != 0);
                parcel2.writeNoException();
                parcel2.writeInt(zEnableBle ? 1 : 0);
                return true;
            case 206:
                ((G0) this).collapseStatusBar();
                parcel2.writeNoException();
                return true;
            case 207:
                ((G0) this).setUsbFunctionsAuthenticated(parcel.readLong(), parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            case 208:
                ((G0) this).setClientDeviceInfo(parcel.readString(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 209:
                Bundle bundleCallContentProvider = ((G0) this).callContentProvider(parcel.readString(), parcel.readString(), parcel.readString(), (Bundle) AbstractC0054a.d(parcel, Bundle.CREATOR));
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, bundleCallContentProvider);
                return true;
            case 210:
                int iRegisterReceiver = ((G0) this).registerReceiver((IntentFilter) AbstractC0054a.d(parcel, IntentFilter.CREATOR), Y.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                parcel2.writeInt(iRegisterReceiver);
                return true;
            case 211:
                boolean zUnRegisterReceiver = ((G0) this).unRegisterReceiver(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zUnRegisterReceiver ? 1 : 0);
                return true;
            case 212:
                ((G0) this).setExactAndAllowWhileIdle(parcel.readInt(), parcel.readLong(), (PendingIntent) AbstractC0054a.d(parcel, PendingIntent.CREATOR));
                parcel2.writeNoException();
                return true;
            case 213:
                ((G0) this).cancelAlarm((PendingIntent) AbstractC0054a.d(parcel, PendingIntent.CREATOR));
                parcel2.writeNoException();
                return true;
            case 214:
                ((G0) this).grantUriPermission((Uri) AbstractC0054a.d(parcel, Uri.CREATOR));
                parcel2.writeNoException();
                return true;
            case 215:
                boolean zIsDpDisplayModeSupported = ((G0) this).isDpDisplayModeSupported();
                parcel2.writeNoException();
                parcel2.writeInt(zIsDpDisplayModeSupported ? 1 : 0);
                return true;
            case 216:
                List supportedModes = ((G0) this).getSupportedModes(parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, supportedModes);
                return true;
            case 217:
                ((G0) this).setUserPreferredDisplayMode(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readFloat());
                parcel2.writeNoException();
                return true;
            case 218:
                boolean zIsDeviceOwnerMode = ((G0) this).isDeviceOwnerMode();
                parcel2.writeNoException();
                parcel2.writeInt(zIsDeviceOwnerMode ? 1 : 0);
                return true;
            case 219:
                f(parcel, parcel2);
                return true;
            case 220:
                IBinder strongBinder20 = parcel.readStrongBinder();
                if (strongBinder20 != null) {
                    IInterface iInterfaceQueryLocalInterface20 = strongBinder20.queryLocalInterface("com.motorola.mobiledesktop.core.ITaskStackListener");
                    if (iInterfaceQueryLocalInterface20 == null || !(iInterfaceQueryLocalInterface20 instanceof InterfaceC0020h0)) {
                        C0018g0 c0018g0 = new C0018g0();
                        c0018g0.f322a = strongBinder20;
                        interfaceC0020h02 = c0018g0;
                    } else {
                        interfaceC0020h02 = (InterfaceC0020h0) iInterfaceQueryLocalInterface20;
                    }
                }
                ((G0) this).setTaskStackListener(interfaceC0020h02);
                parcel2.writeNoException();
                return true;
            case 221:
                List runningTasks = ((G0) this).getRunningTasks();
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, runningTasks);
                return true;
            case 222:
                ActivityManager.RunningTaskInfo topVisbleTask = ((G0) this).getTopVisbleTask(parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, topVisbleTask);
                return true;
            case 223:
                MotoRunningTaskInfoNew motoRunningTaskInfo = ((G0) this).getMotoRunningTaskInfo((ActivityManager.RunningTaskInfo) AbstractC0054a.d(parcel, ActivityManager.RunningTaskInfo.CREATOR));
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, motoRunningTaskInfo);
                return true;
            case 224:
                IBinder strongBinder21 = parcel.readStrongBinder();
                if (strongBinder21 != null) {
                    IInterface iInterfaceQueryLocalInterface21 = strongBinder21.queryLocalInterface("com.motorola.mobiledesktop.core.ITaskStackListener");
                    if (iInterfaceQueryLocalInterface21 == null || !(iInterfaceQueryLocalInterface21 instanceof InterfaceC0020h0)) {
                        C0018g0 c0018g02 = new C0018g0();
                        c0018g02.f322a = strongBinder21;
                        interfaceC0020h0 = c0018g02;
                    } else {
                        interfaceC0020h0 = (InterfaceC0020h0) iInterfaceQueryLocalInterface21;
                    }
                }
                ((G0) this).setMotoTaskStackListener(interfaceC0020h0);
                parcel2.writeNoException();
                return true;
            case 225:
                ((G0) this).rotateDisplay(parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 226:
                boolean zIsDisplayRotatable = ((G0) this).isDisplayRotatable(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zIsDisplayRotatable ? 1 : 0);
                return true;
            case 227:
                Rect cliCutoutSize = ((G0) this).getCliCutoutSize();
                parcel2.writeNoException();
                AbstractC0054a.f(parcel2, cliCutoutSize);
                return true;
            case 228:
                ((G0) this).setCompatibilityMode(parcel.readString(), parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            case 229:
                boolean zIsMethodExists = ((G0) this).isMethodExists(parcel.readString(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(zIsMethodExists ? 1 : 0);
                return true;
            case 230:
                ((G0) this).startAccessory(parcel.createStringArray());
                parcel2.writeNoException();
                return true;
            case 231:
                String bluetoothAddress = ((G0) this).getBluetoothAddress();
                parcel2.writeNoException();
                parcel2.writeString(bluetoothAddress);
                return true;
            case 232:
                ((G0) this).setClientBluetoothDeviceAddress(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 233:
                ((G0) this).setReadyForConnectedType(parcel.readInt(), parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            case 234:
                ((G0) this).disconnectBluetooth(parcel.readString());
                parcel2.writeNoException();
                return true;
            case 235:
                IBinder strongBinder22 = parcel.readStrongBinder();
                if (strongBinder22 != null) {
                    IInterface iInterfaceQueryLocalInterface22 = strongBinder22.queryLocalInterface("com.motorola.mobiledesktop.core.IAudioPbPkgNameListChangeListener");
                    if (iInterfaceQueryLocalInterface22 == null || !(iInterfaceQueryLocalInterface22 instanceof InterfaceC0023j)) {
                        C0021i c0021i = new C0021i();
                        c0021i.f323a = strongBinder22;
                        interfaceC0023j2 = c0021i;
                    } else {
                        interfaceC0023j2 = (InterfaceC0023j) iInterfaceQueryLocalInterface22;
                    }
                }
                ((G0) this).registerOnAudioPbPkgNameListChangeListener(interfaceC0023j2);
                parcel2.writeNoException();
                return true;
            case 236:
                IBinder strongBinder23 = parcel.readStrongBinder();
                if (strongBinder23 != null) {
                    IInterface iInterfaceQueryLocalInterface23 = strongBinder23.queryLocalInterface("com.motorola.mobiledesktop.core.IAudioPbPkgNameListChangeListener");
                    if (iInterfaceQueryLocalInterface23 == null || !(iInterfaceQueryLocalInterface23 instanceof InterfaceC0023j)) {
                        C0021i c0021i2 = new C0021i();
                        c0021i2.f323a = strongBinder23;
                        interfaceC0023j = c0021i2;
                    } else {
                        interfaceC0023j = (InterfaceC0023j) iInterfaceQueryLocalInterface23;
                    }
                }
                ((G0) this).unregisterOnAudioPbPkgNameListChangeListener(interfaceC0023j);
                parcel2.writeNoException();
                return true;
            case 237:
                ((G0) this).startAppWithActivityOptions((Intent) AbstractC0054a.d(parcel, Intent.CREATOR), (Bundle) AbstractC0054a.d(parcel, Bundle.CREATOR), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 238:
                float[] desiredDisplayModeSpecsRange = ((G0) this).getDesiredDisplayModeSpecsRange(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeFloatArray(desiredDisplayModeSpecsRange);
                return true;
            case 239:
                List listQueryIntentActivitiesByPaging = ((G0) this).queryIntentActivitiesByPaging((Intent) AbstractC0054a.d(parcel, Intent.CREATOR), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, listQueryIntentActivitiesByPaging);
                return true;
            case 240:
                List listQueryMotoActivityInfoList = ((G0) this).queryMotoActivityInfoList((Intent) AbstractC0054a.d(parcel, Intent.CREATOR), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, listQueryMotoActivityInfoList);
                return true;
            case 241:
                int[] physicalDisplayInfo = ((G0) this).getPhysicalDisplayInfo();
                parcel2.writeNoException();
                parcel2.writeIntArray(physicalDisplayInfo);
                return true;
            case 242:
                ((G0) this).setCustomPointerIconForFullScreenV((PointerIcon) AbstractC0054a.d(parcel, PointerIcon.CREATOR), parcel.readInt(), parcel.readInt() != 0, parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 243:
                ((G0) this).moveRootTaskToDisplay(parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 244:
                boolean zIsLeaFeatureSupported = ((G0) this).isLeaFeatureSupported((BluetoothDevice) AbstractC0054a.d(parcel, BluetoothDevice.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(zIsLeaFeatureSupported ? 1 : 0);
                return true;
            case 245:
                boolean zReconnectBluetoothProfile = ((G0) this).reconnectBluetoothProfile(parcel.createTypedArrayList(BluetoothDevice.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(zReconnectBluetoothProfile ? 1 : 0);
                return true;
            case 246:
                boolean zReconnectLeaProfile = ((G0) this).reconnectLeaProfile(parcel.createTypedArrayList(BluetoothDevice.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(zReconnectLeaProfile ? 1 : 0);
                return true;
            case 247:
                int leaProfileStatus = ((G0) this).getLeaProfileStatus((BluetoothDevice) AbstractC0054a.d(parcel, BluetoothDevice.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(leaProfileStatus);
                return true;
            case 248:
                ((G0) this).setPointerIconVisible(parcel.readInt() != 0, parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 249:
                boolean zStartToAssociate = ((G0) this).startToAssociate((AssociationRequest) AbstractC0054a.d(parcel, AssociationRequest.CREATOR), AbstractBinderC0033o.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                parcel2.writeInt(zStartToAssociate ? 1 : 0);
                return true;
            case 250:
                ((G0) this).startToUnassociate(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 251:
                int iCreateVirtualDevice = ((G0) this).createVirtualDevice(parcel.readInt(), (VirtualDeviceParams) AbstractC0054a.d(parcel, VirtualDeviceParams.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(iCreateVirtualDevice);
                return true;
            case 252:
                ((G0) this).closeVirtualDevice(parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 253:
                j(parcel, parcel2);
                return true;
            case 254:
                d(parcel, parcel2);
                return true;
            case 255:
                g(parcel, parcel2);
                return true;
            case 256:
                ((G0) this).releaseVirtualDisplayVDM(parcel.readInt(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 257:
                ((G0) this).setActivityStateChangedCallback(AbstractBinderC0017g.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            case 258:
                ((G0) this).resetUsbPort(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 259:
                int iUinputDeviceOpen = ((G0) this).uinputDeviceOpen(IClientToken.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                parcel2.writeInt(iUinputDeviceOpen);
                return true;
            case 260:
                boolean zUinputDeviceClose = ((G0) this).uinputDeviceClose(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zUinputDeviceClose ? 1 : 0);
                return true;
            case 261:
                boolean zUinputAbsSetup = ((G0) this).uinputAbsSetup(parcel.readInt(), (AbsSetup) AbstractC0054a.d(parcel, AbsSetup.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(zUinputAbsSetup ? 1 : 0);
                return true;
            case 262:
                boolean zUinputEnable = ((G0) this).uinputEnable(parcel.readInt(), parcel.readInt(), parcel.createCharArray());
                parcel2.writeNoException();
                parcel2.writeInt(zUinputEnable ? 1 : 0);
                return true;
            case 263:
                boolean zUinputDoIoctl = ((G0) this).uinputDoIoctl(parcel.readInt(), parcel.readInt(), parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zUinputDoIoctl ? 1 : 0);
                return true;
            case 264:
                boolean zUinputDoStrIoctl = ((G0) this).uinputDoStrIoctl(parcel.readInt(), parcel.readInt(), parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(zUinputDoStrIoctl ? 1 : 0);
                return true;
            case 265:
                boolean zUinputCreate = ((G0) this).uinputCreate(parcel.readInt(), (InputSetup) AbstractC0054a.d(parcel, InputSetup.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(zUinputCreate ? 1 : 0);
                return true;
            case 266:
                boolean zUinputEmit = ((G0) this).uinputEmit(parcel.readInt(), (InputEvent[]) parcel.createTypedArray(InputEvent.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(zUinputEmit ? 1 : 0);
                return true;
            case 267:
                ((G0) this).setGoIntent(parcel.readInt());
                parcel2.writeNoException();
                return true;
            case 268:
                ((G0) this).startPlatformService((Intent) AbstractC0054a.d(parcel, Intent.CREATOR));
                parcel2.writeNoException();
                return true;
            case 269:
                boolean zBindPlatformService = ((G0) this).bindPlatformService((ComponentName) AbstractC0054a.d(parcel, ComponentName.CREATOR), N.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                parcel2.writeInt(zBindPlatformService ? 1 : 0);
                return true;
            case 270:
                ((G0) this).unbindPlatformService((ComponentName) AbstractC0054a.d(parcel, ComponentName.CREATOR));
                parcel2.writeNoException();
                return true;
            case 271:
                List listWifiGetCallerConfiguredNetworks = ((G0) this).wifiGetCallerConfiguredNetworks();
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, listWifiGetCallerConfiguredNetworks);
                return true;
            case 272:
                int iWifiAddNetwork = ((G0) this).wifiAddNetwork((WifiConfiguration) AbstractC0054a.d(parcel, WifiConfiguration.CREATOR));
                parcel2.writeNoException();
                parcel2.writeInt(iWifiAddNetwork);
                return true;
            case 273:
                boolean zWifiRemoveNetwork = ((G0) this).wifiRemoveNetwork(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zWifiRemoveNetwork ? 1 : 0);
                return true;
            case 274:
                boolean zWifiEnableNetwork = ((G0) this).wifiEnableNetwork(parcel.readInt(), parcel.readInt() != 0);
                parcel2.writeNoException();
                parcel2.writeInt(zWifiEnableNetwork ? 1 : 0);
                return true;
            case 275:
                boolean zWifiDisableNetwork = ((G0) this).wifiDisableNetwork(parcel.readInt());
                parcel2.writeNoException();
                parcel2.writeInt(zWifiDisableNetwork ? 1 : 0);
                return true;
            case 276:
                boolean zWifiDisconnect = ((G0) this).wifiDisconnect();
                parcel2.writeNoException();
                parcel2.writeInt(zWifiDisconnect ? 1 : 0);
                return true;
            case 277:
                boolean zWifiReconnect = ((G0) this).wifiReconnect();
                parcel2.writeNoException();
                parcel2.writeInt(zWifiReconnect ? 1 : 0);
                return true;
            case 278:
                int iCreateVirtualDisplayVDM = ((G0) this).createVirtualDisplayVDM(parcel.readInt(), parcel.readInt(), (VirtualDisplayConfig) AbstractC0054a.d(parcel, VirtualDisplayConfig.CREATOR), (Rect) AbstractC0054a.d(parcel, Rect.CREATOR), AbstractBinderC0038q0.a(parcel.readStrongBinder()));
                parcel2.writeNoException();
                parcel2.writeInt(iCreateVirtualDisplayVDM);
                return true;
            case 279:
                ((G0) this).setVirtualDisplaySupportedModesVDM(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.createTypedArrayList(MotoDisplayMode.CREATOR));
                parcel2.writeNoException();
                return true;
            case 280:
                int i7 = parcel.readInt();
                int i8 = parcel.readInt();
                IBinder strongBinder24 = parcel.readStrongBinder();
                if (strongBinder24 != null) {
                    IInterface iInterfaceQueryLocalInterface24 = strongBinder24.queryLocalInterface("com.motorola.mobiledesktop.core.companion.virtual.IActivityListener");
                    if (iInterfaceQueryLocalInterface24 == null || !(iInterfaceQueryLocalInterface24 instanceof a0.b)) {
                        a0.a aVar = new a0.a();
                        aVar.f464a = strongBinder24;
                        bVar2 = aVar;
                    } else {
                        bVar2 = (a0.b) iInterfaceQueryLocalInterface24;
                    }
                }
                ((G0) this).addActivityListenerVDM(i7, i8, bVar2);
                parcel2.writeNoException();
                return true;
            case 281:
                int i9 = parcel.readInt();
                int i10 = parcel.readInt();
                IBinder strongBinder25 = parcel.readStrongBinder();
                if (strongBinder25 != null) {
                    IInterface iInterfaceQueryLocalInterface25 = strongBinder25.queryLocalInterface("com.motorola.mobiledesktop.core.companion.virtual.IActivityListener");
                    if (iInterfaceQueryLocalInterface25 == null || !(iInterfaceQueryLocalInterface25 instanceof a0.b)) {
                        a0.a aVar2 = new a0.a();
                        aVar2.f464a = strongBinder25;
                        bVar = aVar2;
                    } else {
                        bVar = (a0.b) iInterfaceQueryLocalInterface25;
                    }
                }
                ((G0) this).removeActivityListenerVDM(i9, i10, bVar);
                parcel2.writeNoException();
                return true;
            case 282:
                ((G0) this).isVDMSupported();
                parcel2.writeNoException();
                parcel2.writeInt(1);
                return true;
            case 283:
                List myAssociations = ((G0) this).getMyAssociations();
                parcel2.writeNoException();
                AbstractC0054a.e(parcel2, myAssociations);
                return true;
            case 284:
                boolean zIsRoleHeld = ((G0) this).isRoleHeld(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(zIsRoleHeld ? 1 : 0);
                return true;
            case 285:
                int[] iArrAidlFeature = ((G0) this).aidlFeature();
                parcel2.writeNoException();
                parcel2.writeIntArray(iArrAidlFeature);
                return true;
            case 286:
                IBinder coreProxyBinder = ((G0) this).getCoreProxyBinder(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeStrongBinder(coreProxyBinder);
                return true;
            default:
                return super.onTransact(i2, parcel, parcel2, i3);
        }
    }
}
