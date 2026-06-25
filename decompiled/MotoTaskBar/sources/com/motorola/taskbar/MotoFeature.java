package com.motorola.taskbar;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.view.DisplayInfo;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.internal.app.MotoDesktopManager;
import motorola.core_services.cli.CLIManager;

/* JADX INFO: loaded from: classes2.dex */
public class MotoFeature {
    private Context mContext;
    private final DisplayManager mDisplayManager;

    public MotoFeature(Context context) {
        this.mContext = context;
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
    }

    public static boolean isAppStreamMode(Context context) {
        return isAppStreamMode(context.getDisplay());
    }

    public static boolean isAppStreamMode(Display display) {
        return MotoDesktopManager.isAppStreamMode(display);
    }

    public static boolean isDesktopModeDisplay(Display display) {
        return MotoDesktopManager.isDesktopMode(display) || MotoDesktopManager.isMobileUiMode(display);
    }

    public static boolean isMobileUiMode(Context context) {
        return MotoDesktopManager.isMobileUiMode(context.getDisplay());
    }

    public static boolean isMobileUiMode(Display display) {
        return MotoDesktopManager.isMobileUiMode(display);
    }

    private boolean isValidExtraDisplayId(int i) {
        return (i == 0 || isCliDisplay(i)) ? false : true;
    }

    public boolean isCliDisplay(int i) {
        return i == 1 && isSupportCli();
    }

    public boolean isDesktopModeDisplay(int i) {
        Display display = this.mDisplayManager.getDisplay(i);
        if (display == null) {
            return false;
        }
        return isDesktopModeDisplay(display);
    }

    public boolean isLidClosed() {
        return MotorolaSettings.Global.getInt(this.mContext.getContentResolver(), "lid_state", 1) == 0;
    }

    public boolean isMobileUiOrAppStreamDisplay(Context context) {
        return isAppStreamMode(context.getDisplay()) || isMobileUiMode(context.getDisplay());
    }

    public boolean isRdpDesktopDisplay(int i) {
        Display display = this.mDisplayManager.getDisplay(i);
        if (display == null) {
            return false;
        }
        return isRdpDesktopDisplay(display);
    }

    public boolean isRdpDesktopDisplay(Display display) {
        return (display.getFlags() & 4194304) != 0;
    }

    public boolean isRemoteDisplay(Context context) {
        Display display = context.getDisplay();
        if (display == null) {
            return false;
        }
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        return (displayInfo.flags & 33554432) != 0;
    }

    public boolean isSupportCli() {
        return CLIManager.isCLISupported() && ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplays().length > 1;
    }

    public boolean isValidHDMIDisplay(Display display) {
        return display != null && display.getType() == 2 && !MotoDesktopManager.isBlackListDisplay(this.mContext, display) && isValidExtraDisplayId(display.getDisplayId());
    }

    public boolean isValidHDMIOrWfdDisplay(int i) {
        Display display = this.mDisplayManager.getDisplay(i);
        if (display == null) {
            return false;
        }
        return isValidHDMIOrWfdDisplay(display);
    }

    public boolean isValidHDMIOrWfdDisplay(Display display) {
        return (display.getType() == 2 || display.getType() == 3) && !MotoDesktopManager.isBlackListDisplay(this.mContext, display) && isValidExtraDisplayId(display.getDisplayId());
    }
}
