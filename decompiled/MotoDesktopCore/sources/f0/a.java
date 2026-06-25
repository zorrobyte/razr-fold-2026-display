package f0;

import X.C0031n;
import X.InterfaceC0035p;
import X.v0;
import android.companion.AssociationInfo;
import android.companion.CompanionDeviceManager;
import android.content.Context;
import android.content.IntentSender;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public final class a extends CompanionDeviceManager.Callback {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final InterfaceC0035p f2532a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ b f2533b;

    public a(b bVar, InterfaceC0035p interfaceC0035p) {
        this.f2533b = bVar;
        this.f2532a = interfaceC0035p;
    }

    @Override // android.companion.CompanionDeviceManager.Callback
    public final void onAssociationCreated(AssociationInfo associationInfo) {
        v0.a("ScCompanionDeviceManager", "onAssociationCreated " + associationInfo);
        InterfaceC0035p interfaceC0035p = this.f2532a;
        if (interfaceC0035p != null) {
            try {
                C0031n c0031n = (C0031n) interfaceC0035p;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ICDMCallback");
                    if (associationInfo != null) {
                        parcelObtain.writeInt(1);
                        associationInfo.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    c0031n.f328a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    @Override // android.companion.CompanionDeviceManager.Callback
    public final void onAssociationPending(IntentSender intentSender) {
        v0.a("ScCompanionDeviceManager", "onAssociationPending");
        try {
            ((Context) this.f2533b.f2537b).startIntentSender(intentSender, null, 0, 0, 0);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        InterfaceC0035p interfaceC0035p = this.f2532a;
        if (interfaceC0035p != null) {
            try {
                C0031n c0031n = (C0031n) interfaceC0035p;
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ICDMCallback");
                    parcelObtain.writeInt(0);
                    c0031n.f328a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                } catch (Throwable th) {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                    throw th;
                }
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    @Override // android.companion.CompanionDeviceManager.Callback
    public final void onFailure(CharSequence charSequence) {
        v0.a("ScCompanionDeviceManager", "onFailure " + ((Object) charSequence));
        InterfaceC0035p interfaceC0035p = this.f2532a;
        if (interfaceC0035p != null) {
            try {
                ((C0031n) interfaceC0035p).a(charSequence);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
