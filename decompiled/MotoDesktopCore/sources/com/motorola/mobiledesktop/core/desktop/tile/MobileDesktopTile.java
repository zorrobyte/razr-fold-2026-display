package com.motorola.mobiledesktop.core.desktop.tile;

import X.B0;
import X.v0;
import android.content.Intent;
import android.os.SystemProperties;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.text.TextUtils;
import com.motorola.mobiledesktop.core.R;
import e0.d;

/* JADX INFO: loaded from: classes.dex */
public class MobileDesktopTile extends TileService {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public B0 f2328a;

    public final void a(int i2, String str, String str2) {
        int i3;
        v0.a("MobileDesktopTile", "refreshTile - connectType:" + i2 + ", clientName:" + str + " otherName:" + str2);
        String string = "1".equals(SystemProperties.get("ro.product.readyfor.lite")) ? getString(R.string.tile_label_lite) : getString(R.string.settings_title);
        int i4 = 2;
        if (i2 == 1) {
            i3 = 2;
        } else {
            if (i2 == 2) {
                str = getString(R.string.tile_hdmi_title);
            } else if (i2 != 3) {
                str = "";
                i3 = 1;
            }
            i3 = 2;
        }
        if (i3 != 1 || TextUtils.isEmpty(str2)) {
            i4 = i3;
            str2 = str;
        }
        Tile qsTile = getQsTile();
        if (qsTile != null) {
            qsTile.setState(i4);
            qsTile.setLabel(string);
            qsTile.setSubtitle(str2);
            qsTile.updateTile();
        }
    }

    @Override // android.service.quicksettings.TileService
    public final void onClick() {
        super.onClick();
        v0.a("MobileDesktopTile", "goToDesktopActivity");
        try {
            Intent intent = new Intent("com.motorola.mobiledesktop.rdp_desktop");
            intent.addFlags(268435456);
            startActivityAndCollapse(intent);
        } catch (Exception e2) {
            v0.g("MobileDesktopTile", "goToDesktopActivity " + e2.toString());
        }
    }

    @Override // android.service.quicksettings.TileService
    public final void onStartListening() {
        v0.a("MobileDesktopTile", "onStartListening");
        super.onStartListening();
        this.f2328a = new B0(1, this);
        a(d.d(getApplicationContext()).f2465c, d.d(getApplicationContext()).f2463a, d.d(getApplicationContext()).f2464b);
        d.d(getApplicationContext()).a(this.f2328a);
    }

    @Override // android.service.quicksettings.TileService
    public final void onStopListening() {
        v0.a("MobileDesktopTile", "onStopListening");
        super.onStopListening();
        d.d(getApplicationContext()).g(this.f2328a);
        this.f2328a = null;
    }
}
