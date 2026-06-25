package com.motorola.mobiledesktop.core;

import X.Q0;
import X.v0;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemProperties;
import com.motorola.mobiledesktop.core.desktop.DesktopActivity;

/* JADX INFO: loaded from: classes.dex */
public class MainActivity extends Activity {
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        v0.a("MainActivity", "MainActivity - onCreate");
        super.onCreate(bundle);
        if (Q0.f(this)) {
            startActivity(new Intent("com.motorola.mobiledesktop.rdp_desktop"));
            finish();
        } else {
            if ("true".equals(SystemProperties.get("ro.vendor.hw.displayport"))) {
                startActivity(new Intent(this, (Class<?>) DesktopActivity.class));
            }
            finish();
        }
    }
}
