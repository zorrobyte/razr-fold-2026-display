package com.motorola.mobiledesktop.core.desktop;

import X.C0042v;
import X.InterfaceC0044x;
import X.v0;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.C0056c;
import androidx.appcompat.app.f;
import androidx.appcompat.app.g;
import com.motorola.internal.app.MotoDesktopManager;
import com.motorola.mobiledesktop.core.R;
import e0.k;

/* JADX INFO: loaded from: classes.dex */
public class RequestPermissionActivity extends AppCompatActivity {

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public boolean f2323o = false;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public boolean f2324p = false;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public int f2325q;
    public g r;

    public static void h(RequestPermissionActivity requestPermissionActivity) {
        requestPermissionActivity.getClass();
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", requestPermissionActivity.getPackageName(), null));
        try {
            requestPermissionActivity.startActivity(intent);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static void i(Context context, g gVar) {
        Button button = gVar.f565c.f548l;
        button.setAllCaps(false);
        button.setTextColor(context.getResources().getColor(R.color.colorAccent));
        Button button2 = gVar.f565c.f544h;
        button2.setAllCaps(false);
        button2.setTextColor(context.getResources().getColor(R.color.colorAccent));
    }

    @Override // android.app.Activity
    public final void finish() {
        super.finish();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f2323o = getIntent().getBooleanExtra("first_request_permission", false);
        this.f2324p = getIntent().getBooleanExtra("request_from_main", false);
        this.f2325q = getIntent().getIntExtra("permission_type", 0);
        v0.a("RequestPermissionActivity", "onCreate mFirstRequestPermission:" + this.f2323o + ", mRequestFromMain:" + this.f2324p + ", mPermissionType:" + this.f2325q);
        if (MotoDesktopManager.isDesktopMode(getDisplay())) {
            setTheme(R.style.AppTheme);
        }
        String[] strArr = new String[0];
        int i2 = this.f2325q;
        if (i2 == 0) {
            strArr = new String[]{"android.permission.RECORD_AUDIO"};
        }
        if (i2 == 1) {
            strArr = new String[]{"android.permission.BLUETOOTH_CONNECT"};
        }
        if (i2 == 2) {
            strArr = new String[]{"android.permission.ACCESS_FINE_LOCATION"};
        }
        if (i2 == 3) {
            strArr = new String[]{"android.permission.BLUETOOTH_SCAN"};
        }
        if (i2 == 4) {
            strArr = new String[]{"android.permission.NEARBY_WIFI_DEVICES"};
        }
        if (strArr.length > 0) {
            requestPermissions(strArr, 2);
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i2, strArr, iArr);
        if (i2 != 2) {
            super.finish();
            return;
        }
        if (strArr.length == 0) {
            super.finish();
            return;
        }
        for (int i3 = 0; i3 < strArr.length; i3++) {
            v0.a("RequestPermissionActivity", "onRequestPermissionsResult permission:" + strArr[i3] + ", ret:" + iArr[i3]);
            if (iArr[i3] == 0) {
                k kVarH = k.h();
                v0.a("k", "notifyClickAllow mCallback=" + ((InterfaceC0044x) kVarH.f2495a));
                InterfaceC0044x interfaceC0044x = (InterfaceC0044x) kVarH.f2495a;
                if (interfaceC0044x != null) {
                    try {
                        C0042v c0042v = (C0042v) interfaceC0044x;
                        Parcel parcelObtain = Parcel.obtain();
                        Parcel parcelObtain2 = Parcel.obtain();
                        try {
                            parcelObtain.writeInterfaceToken("com.motorola.mobiledesktop.core.ICorePermissionDialogCallback");
                            c0042v.f333a.transact(1, parcelObtain, parcelObtain2, 0);
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
                            Log.d("MotoDesktopCore", v0.d("k", "notifyClickAllow fail,"), e2);
                        }
                        e2.printStackTrace();
                    }
                }
                super.finish();
            } else if (shouldShowRequestPermissionRationale(strArr[i3])) {
                k kVarH2 = k.h();
                v0.a("k", "notifyClickDenied mCallback=" + ((InterfaceC0044x) kVarH2.f2495a));
                InterfaceC0044x interfaceC0044x2 = (InterfaceC0044x) kVarH2.f2495a;
                if (interfaceC0044x2 != null) {
                    try {
                        C0042v c0042v2 = (C0042v) interfaceC0044x2;
                        Parcel parcelObtain3 = Parcel.obtain();
                        Parcel parcelObtain4 = Parcel.obtain();
                        try {
                            parcelObtain3.writeInterfaceToken("com.motorola.mobiledesktop.core.ICorePermissionDialogCallback");
                            c0042v2.f333a.transact(3, parcelObtain3, parcelObtain4, 0);
                            parcelObtain4.readException();
                            parcelObtain4.recycle();
                            parcelObtain3.recycle();
                        } catch (Throwable th2) {
                            parcelObtain4.recycle();
                            parcelObtain3.recycle();
                            throw th2;
                        }
                    } catch (RemoteException e3) {
                        if (v0.f336c) {
                            Log.d("MotoDesktopCore", v0.d("k", "notifyClickDenied fail,"), e3);
                        }
                        e3.printStackTrace();
                    }
                }
                super.finish();
            } else if (this.f2324p) {
                g gVar = this.r;
                if (gVar != null && gVar.isShowing()) {
                    v0.a("RequestPermissionActivity", "mDialog is showing");
                    return;
                }
                String string = strArr[0].contains("BLUETOOTH") ? String.format(getResources().getString(R.string.main_core_bluetooth_connect_permission_grant), getString(R.string.app_name)) : strArr[0].contains("android.permission.ACCESS_FINE_LOCATION") ? String.format(getResources().getString(R.string.main_core_location_permission_grant), getString(R.string.app_name)) : strArr[0].contains("android.permission.RECORD_AUDIO") ? getString(R.string.record_audio_permission_required_message) : strArr[0].contains("android.permission.NEARBY_WIFI_DEVICES") ? String.format(getResources().getString(R.string.main_core_location_permission_grant), getString(R.string.app_name)) : "";
                f fVar = new f(this, R.style.DialogTheme);
                int i4 = R.string.record_audio_permission_required_title;
                C0056c c0056c = (C0056c) fVar.f564c;
                c0056c.f518d = c0056c.f515a.getText(i4);
                c0056c.f520f = string;
                c0056c.f525k = false;
                int i5 = R.string.dialog_cancel;
                a aVar = new a(this, 1);
                c0056c.f523i = c0056c.f515a.getText(i5);
                c0056c.f524j = aVar;
                int i6 = R.string.dialog_allow;
                a aVar2 = new a(this, 0);
                c0056c.f521g = c0056c.f515a.getText(i6);
                c0056c.f522h = aVar2;
                g gVarB = fVar.b();
                this.r = gVarB;
                gVarB.show();
                i(this, this.r);
            } else {
                f fVar2 = new f(this, R.style.DialogTheme);
                int i7 = R.string.record_audio_permission_required_title;
                C0056c c0056c2 = (C0056c) fVar2.f564c;
                c0056c2.f518d = c0056c2.f515a.getText(i7);
                c0056c2.f520f = c0056c2.f515a.getText(R.string.record_audio_permission_required_message);
                c0056c2.f525k = false;
                int i8 = R.string.dialog_cancel;
                a aVar3 = new a(this, 3);
                c0056c2.f523i = c0056c2.f515a.getText(i8);
                c0056c2.f524j = aVar3;
                int i9 = R.string.dialog_allow;
                a aVar4 = new a(this, 2);
                c0056c2.f521g = c0056c2.f515a.getText(i9);
                c0056c2.f522h = aVar4;
                g gVarB2 = fVar2.b();
                gVarB2.show();
                i(this, gVarB2);
            }
        }
    }
}
