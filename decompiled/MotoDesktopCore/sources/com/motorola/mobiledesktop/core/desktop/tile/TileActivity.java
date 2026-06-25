package com.motorola.mobiledesktop.core.desktop.tile;

import X.v0;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/* JADX INFO: loaded from: classes.dex */
public class TileActivity extends AppCompatActivity {
    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            Intent intent = new Intent("com.motorola.mobiledesktop.rdp_desktop");
            intent.addFlags(268435456);
            startActivity(intent);
        } catch (Exception e2) {
            v0.g("MobileDesktopTile", "goToDesktopActivity " + e2.toString());
        }
        finish();
    }
}
