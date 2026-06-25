package com.motorola.mobiledesktop.core.desktop;

import X.C0042v;
import X.InterfaceC0044x;
import X.v0;
import android.content.DialogInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import e0.k;

/* JADX INFO: loaded from: classes.dex */
public final class a implements DialogInterface.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f2326a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ RequestPermissionActivity f2327b;

    public /* synthetic */ a(RequestPermissionActivity requestPermissionActivity, int i2) {
        this.f2326a = i2;
        this.f2327b = requestPermissionActivity;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i2) {
        RequestPermissionActivity requestPermissionActivity = this.f2327b;
        switch (this.f2326a) {
            case 0:
                RequestPermissionActivity.h(requestPermissionActivity);
                requestPermissionActivity.finish();
                return;
            case 1:
                k kVarH = k.h();
                v0.a("k", "notifyClickCancel mCallback=" + ((InterfaceC0044x) kVarH.f2495a));
                InterfaceC0044x interfaceC0044x = (InterfaceC0044x) kVarH.f2495a;
                if (interfaceC0044x != null) {
                    try {
                        C0042v c0042v = (C0042v) interfaceC0044x;
                        Parcel parcelObtain = Parcel.obtain();
                        Parcel parcelObtain2 = Parcel.obtain();
                        try {
                            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ICorePermissionDialogCallback");
                            c0042v.f333a.transact(2, parcelObtain, parcelObtain2, 0);
                            parcelObtain2.readException();
                            parcelObtain2.recycle();
                            parcelObtain.recycle();
                        } catch (Throwable th) {
                            parcelObtain2.recycle();
                            parcelObtain.recycle();
                            throw th;
                        }
                    } catch (RemoteException e2) {
                        if (v0.f336c) {
                            Log.d("MotoDesktopCore", v0.d("k", "notifyClickCancel fail,"), e2);
                        }
                        e2.printStackTrace();
                    }
                }
                dialogInterface.dismiss();
                requestPermissionActivity.finish();
                return;
            case 2:
                RequestPermissionActivity.h(requestPermissionActivity);
                requestPermissionActivity.finish();
                return;
            default:
                dialogInterface.dismiss();
                requestPermissionActivity.finish();
                return;
        }
    }
}
