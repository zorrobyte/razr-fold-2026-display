package E;

import a.BinderC0053c;
import a.C0051a;
import a.InterfaceC0052b;
import android.net.LinkAddress;
import android.os.BadParcelableException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ResultReceiver;
import androidx.versionedparcelable.ParcelImpl;
import com.motorola.mobiledesktop.core.TetheringRequest;
import com.motorola.mobiledesktop.core.bean.AudioSettingInfo;
import com.motorola.mobiledesktop.core.bean.AudioSettingItemInfo;
import com.motorola.mobiledesktop.core.bean.MotoActivityInfo;
import com.motorola.mobiledesktop.core.bean.MotoDisplay;
import com.motorola.mobiledesktop.core.bean.MotoDisplayMode;
import com.motorola.mobiledesktop.core.bean.MotoRunningTaskInfo;
import com.motorola.mobiledesktop.core.bean.MotoRunningTaskInfoNew;
import com.motorola.mobiledesktop.core.bean.MotoWifiDisplay;
import com.motorola.mobiledesktop.core.bean.NearbyShareDevice;
import com.motorola.mobiledesktop.core.bean.RfDragShadow;
import com.motorola.mobiledesktop.core.tethering.InterfaceConfigurationParcel;
import com.motorola.mobiledesktop.core.tethering.IpPrefixParcel;
import com.motorola.mobiledesktop.core.tethering.RouteInfoParcel;
import com.motorola.mobiledesktop.core.uinput.EventType;

/* JADX INFO: loaded from: classes.dex */
public final class a implements Parcelable.Creator {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f109a;

    public /* synthetic */ a(int i2) {
        this.f109a = i2;
    }

    @Override // android.os.Parcelable.Creator
    public final Object createFromParcel(Parcel parcel) {
        InterfaceC0052b interfaceC0052b = null;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        switch (this.f109a) {
            case 0:
                return new ParcelImpl(parcel);
            case 1:
                TetheringRequest tetheringRequest = new TetheringRequest();
                tetheringRequest.f2301a = 0;
                tetheringRequest.f2304d = false;
                tetheringRequest.f2305e = false;
                tetheringRequest.f2306f = 0;
                int iDataPosition = parcel.dataPosition();
                int i2 = parcel.readInt();
                try {
                    if (i2 < 4) {
                        throw new BadParcelableException("Parcelable too small");
                    }
                    if (parcel.dataPosition() - iDataPosition < i2) {
                        tetheringRequest.f2301a = parcel.readInt();
                        if (parcel.dataPosition() - iDataPosition < i2) {
                            Parcelable.Creator creator = LinkAddress.CREATOR;
                            tetheringRequest.f2302b = (LinkAddress) (parcel.readInt() != 0 ? creator.createFromParcel(parcel) : null);
                            if (parcel.dataPosition() - iDataPosition < i2) {
                                tetheringRequest.f2303c = (LinkAddress) (parcel.readInt() != 0 ? creator.createFromParcel(parcel) : null);
                                if (parcel.dataPosition() - iDataPosition < i2) {
                                    tetheringRequest.f2304d = parcel.readInt() != 0;
                                    if (parcel.dataPosition() - iDataPosition < i2) {
                                        tetheringRequest.f2305e = parcel.readInt() != 0;
                                        if (parcel.dataPosition() - iDataPosition < i2) {
                                            tetheringRequest.f2306f = parcel.readInt();
                                            if (iDataPosition > Integer.MAX_VALUE - i2) {
                                                throw new BadParcelableException("Overflow in the size of parcelable");
                                            }
                                        } else if (iDataPosition > Integer.MAX_VALUE - i2) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (iDataPosition > Integer.MAX_VALUE - i2) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition > Integer.MAX_VALUE - i2) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition > Integer.MAX_VALUE - i2) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition > Integer.MAX_VALUE - i2) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition > Integer.MAX_VALUE - i2) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(iDataPosition + i2);
                    return tetheringRequest;
                } catch (Throwable th) {
                    if (iDataPosition > Integer.MAX_VALUE - i2) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(iDataPosition + i2);
                    throw th;
                }
            case 2:
                return new AudioSettingInfo(parcel);
            case 3:
                return new AudioSettingItemInfo(parcel);
            case EventType.EVENT_MSC /* 4 */:
                return new MotoActivityInfo(parcel);
            case EventType.EVENT_SW /* 5 */:
                return new MotoDisplay(parcel, z2 ? 1 : 0);
            case 6:
                return new MotoDisplayMode(parcel, z3 ? 1 : 0);
            case 7:
                return new MotoRunningTaskInfo(parcel);
            case 8:
                return new MotoRunningTaskInfoNew(parcel);
            case 9:
                return new MotoWifiDisplay(parcel);
            case 10:
                return new NearbyShareDevice(parcel);
            case 11:
                return new RfDragShadow(parcel, z4 ? 1 : 0);
            case 12:
                ResultReceiver resultReceiver = new ResultReceiver();
                IBinder strongBinder = parcel.readStrongBinder();
                int i3 = BinderC0053c.f462b;
                if (strongBinder != null) {
                    IInterface iInterfaceQueryLocalInterface = strongBinder.queryLocalInterface("android.support.v4.os.IResultReceiver");
                    if (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof InterfaceC0052b)) {
                        C0051a c0051a = new C0051a();
                        c0051a.f461a = strongBinder;
                        interfaceC0052b = c0051a;
                    } else {
                        interfaceC0052b = (InterfaceC0052b) iInterfaceQueryLocalInterface;
                    }
                }
                resultReceiver.f465a = interfaceC0052b;
                return resultReceiver;
            case 13:
                InterfaceConfigurationParcel interfaceConfigurationParcel = new InterfaceConfigurationParcel();
                interfaceConfigurationParcel.f2342d = 0;
                int iDataPosition2 = parcel.dataPosition();
                int i4 = parcel.readInt();
                try {
                    if (i4 < 4) {
                        throw new BadParcelableException("Parcelable too small");
                    }
                    if (parcel.dataPosition() - iDataPosition2 < i4) {
                        interfaceConfigurationParcel.f2339a = parcel.readString();
                        if (parcel.dataPosition() - iDataPosition2 < i4) {
                            interfaceConfigurationParcel.f2340b = parcel.readString();
                            if (parcel.dataPosition() - iDataPosition2 < i4) {
                                interfaceConfigurationParcel.f2341c = parcel.readString();
                                if (parcel.dataPosition() - iDataPosition2 < i4) {
                                    interfaceConfigurationParcel.f2342d = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition2 < i4) {
                                        interfaceConfigurationParcel.f2343e = parcel.createStringArray();
                                        if (iDataPosition2 > Integer.MAX_VALUE - i4) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (iDataPosition2 > Integer.MAX_VALUE - i4) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition2 > Integer.MAX_VALUE - i4) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition2 > Integer.MAX_VALUE - i4) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition2 > Integer.MAX_VALUE - i4) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition2 > Integer.MAX_VALUE - i4) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(iDataPosition2 + i4);
                    return interfaceConfigurationParcel;
                } catch (Throwable th2) {
                    if (iDataPosition2 > Integer.MAX_VALUE - i4) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(iDataPosition2 + i4);
                    throw th2;
                }
            case 14:
                IpPrefixParcel ipPrefixParcel = new IpPrefixParcel();
                ipPrefixParcel.f2345b = 0;
                int iDataPosition3 = parcel.dataPosition();
                int i5 = parcel.readInt();
                try {
                    if (i5 < 4) {
                        throw new BadParcelableException("Parcelable too small");
                    }
                    if (parcel.dataPosition() - iDataPosition3 < i5) {
                        ipPrefixParcel.f2344a = parcel.createByteArray();
                        if (parcel.dataPosition() - iDataPosition3 < i5) {
                            ipPrefixParcel.f2345b = parcel.readInt();
                            if (iDataPosition3 > Integer.MAX_VALUE - i5) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition3 > Integer.MAX_VALUE - i5) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition3 > Integer.MAX_VALUE - i5) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(iDataPosition3 + i5);
                    return ipPrefixParcel;
                } catch (Throwable th3) {
                    if (iDataPosition3 > Integer.MAX_VALUE - i5) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(iDataPosition3 + i5);
                    throw th3;
                }
            default:
                RouteInfoParcel routeInfoParcel = new RouteInfoParcel();
                routeInfoParcel.f2349d = 0;
                routeInfoParcel.f2350e = 0;
                int iDataPosition4 = parcel.dataPosition();
                int i6 = parcel.readInt();
                try {
                    if (i6 < 4) {
                        throw new BadParcelableException("Parcelable too small");
                    }
                    if (parcel.dataPosition() - iDataPosition4 < i6) {
                        routeInfoParcel.f2346a = parcel.readInt() != 0 ? IpPrefixParcel.CREATOR.createFromParcel(parcel) : null;
                        if (parcel.dataPosition() - iDataPosition4 < i6) {
                            routeInfoParcel.f2347b = parcel.createByteArray();
                            if (parcel.dataPosition() - iDataPosition4 < i6) {
                                routeInfoParcel.f2348c = parcel.readString();
                                if (parcel.dataPosition() - iDataPosition4 < i6) {
                                    routeInfoParcel.f2349d = parcel.readInt();
                                    if (parcel.dataPosition() - iDataPosition4 < i6) {
                                        routeInfoParcel.f2350e = parcel.readInt();
                                        if (iDataPosition4 > Integer.MAX_VALUE - i6) {
                                            throw new BadParcelableException("Overflow in the size of parcelable");
                                        }
                                    } else if (iDataPosition4 > Integer.MAX_VALUE - i6) {
                                        throw new BadParcelableException("Overflow in the size of parcelable");
                                    }
                                } else if (iDataPosition4 > Integer.MAX_VALUE - i6) {
                                    throw new BadParcelableException("Overflow in the size of parcelable");
                                }
                            } else if (iDataPosition4 > Integer.MAX_VALUE - i6) {
                                throw new BadParcelableException("Overflow in the size of parcelable");
                            }
                        } else if (iDataPosition4 > Integer.MAX_VALUE - i6) {
                            throw new BadParcelableException("Overflow in the size of parcelable");
                        }
                    } else if (iDataPosition4 > Integer.MAX_VALUE - i6) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(iDataPosition4 + i6);
                    return routeInfoParcel;
                } catch (Throwable th4) {
                    if (iDataPosition4 > Integer.MAX_VALUE - i6) {
                        throw new BadParcelableException("Overflow in the size of parcelable");
                    }
                    parcel.setDataPosition(iDataPosition4 + i6);
                    throw th4;
                }
        }
    }

    @Override // android.os.Parcelable.Creator
    public final Object[] newArray(int i2) {
        switch (this.f109a) {
            case 0:
                return new ParcelImpl[i2];
            case 1:
                return new TetheringRequest[i2];
            case 2:
                return new AudioSettingInfo[i2];
            case 3:
                return new AudioSettingItemInfo[i2];
            case EventType.EVENT_MSC /* 4 */:
                return new MotoActivityInfo[i2];
            case EventType.EVENT_SW /* 5 */:
                return new MotoDisplay[i2];
            case 6:
                return new MotoDisplayMode[i2];
            case 7:
                return new MotoRunningTaskInfo[i2];
            case 8:
                return new MotoRunningTaskInfoNew[i2];
            case 9:
                return new MotoWifiDisplay[i2];
            case 10:
                return new NearbyShareDevice[i2];
            case 11:
                return new RfDragShadow[i2];
            case 12:
                return new ResultReceiver[i2];
            case 13:
                return new InterfaceConfigurationParcel[i2];
            case 14:
                return new IpPrefixParcel[i2];
            default:
                return new RouteInfoParcel[i2];
        }
    }
}
