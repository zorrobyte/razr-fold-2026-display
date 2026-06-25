package com.motorola.mobiledesktop.core.audio;

import X.v0;
import Y.i;
import android.app.StatusBarManager;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.Display;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.C0090b;
import androidx.fragment.app.z;
import com.motorola.internal.app.MotoDesktopManager;
import com.motorola.mobiledesktop.core.R;

/* JADX INFO: loaded from: classes.dex */
public class MediaOutputRouteActivity extends AppCompatActivity {

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public MediaOutputRouteFragment f2317o;

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        Intent intentCreateAdminSupportIntent;
        super.onCreate(bundle);
        if (getPackageManager().checkPermission("android.permission.RECORD_AUDIO", getPackageName()) != 0) {
            for (Display display : ((DisplayManager) getSystemService("display")).getDisplays()) {
                if (MotoDesktopManager.isAppStreamMode(display) || (display.getType() == 5 && MotoDesktopManager.isReadyForPackage(display.getOwnerPackageName()))) {
                    finish();
                    try {
                        Intent intent = new Intent("com.motorola.mobiledesktop.core.action.REQUEST_PERMISSION");
                        intent.putExtra("permission_type", 0);
                        startActivity(intent);
                        return;
                    } catch (ActivityNotFoundException e2) {
                        e2.printStackTrace();
                        return;
                    }
                }
            }
        }
        setContentView(R.layout.activity_media_route);
        if (!i.u(this).f412z) {
            finish();
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(DevicePolicyManager.class);
            if (devicePolicyManager == null || (intentCreateAdminSupportIntent = devicePolicyManager.createAdminSupportIntent("no_adjust_volume")) == null) {
                return;
            }
            startActivity(intentCreateAdminSupportIntent);
            return;
        }
        ((StatusBarManager) getSystemService("statusbar")).collapsePanels();
        Configuration configuration = getResources().getConfiguration();
        MediaOutputRouteFragment mediaOutputRouteFragment = new MediaOutputRouteFragment();
        this.f2317o = mediaOutputRouteFragment;
        if (configuration != null) {
            mediaOutputRouteFragment.setConfiguration(configuration.orientation);
        }
        z zVar = this.f1527c.f1637a.f1641e;
        zVar.getClass();
        C0090b c0090b = new C0090b(zVar);
        int i2 = R.id.fl_content;
        MediaOutputRouteFragment mediaOutputRouteFragment2 = this.f2317o;
        if (i2 == 0) {
            throw new IllegalArgumentException("Must use non-zero containerViewId");
        }
        c0090b.e(i2, mediaOutputRouteFragment2, null, 2);
        c0090b.d(false);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        v0.a("MediaOutputRouteActivity", "onDestroy");
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onResume() {
        super.onResume();
    }
}
