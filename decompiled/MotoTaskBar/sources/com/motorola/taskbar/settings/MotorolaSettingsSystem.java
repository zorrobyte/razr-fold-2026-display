package com.motorola.taskbar.settings;

import android.content.ContentResolver;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import com.motorola.android.provider.MotorolaSettings;

/* JADX INFO: loaded from: classes2.dex */
public abstract class MotorolaSettingsSystem {

    public enum ConnectType {
        NONE,
        RDP,
        DP,
        MIRROR
    }

    public static boolean autoAddIconToHomeEnable(ContentResolver contentResolver) {
        return MotorolaSettings.Global.getInt(contentResolver, "desktop_auto_add") == 1;
    }

    public static boolean getDisablePopupNotification(ContentResolver contentResolver) {
        return MotorolaSettings.System.getInt(contentResolver, "settings_disable_popup_notification") == 1;
    }

    private static String getDisplayModeString(int i, int i2, float f) {
        if (i <= 0 || i2 <= 0 || f <= 0.0f) {
            return "";
        }
        return i + ":" + i2 + ":" + f;
    }

    public static float getDisplayScale(ContentResolver contentResolver, ConnectType connectType) {
        try {
            int iOrdinal = connectType.ordinal();
            if (iOrdinal == 1) {
                return MotorolaSettings.System.getFloat(contentResolver, "remote_desktop_display_size_scale");
            }
            if (iOrdinal != 2 && iOrdinal == 3) {
                return MotorolaSettings.System.getFloat(contentResolver, "mirror_cast_display_size_scale");
            }
            return MotorolaSettings.System.getFloat(contentResolver, "desktop_display_size_scale");
        } catch (MotorolaSettings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public static float getFontScale(ContentResolver contentResolver, ConnectType connectType) {
        try {
            int iOrdinal = connectType.ordinal();
            if (iOrdinal == 1) {
                return MotorolaSettings.System.getFloat(contentResolver, "remote_desktop_font_size_scale");
            }
            if (iOrdinal != 2 && iOrdinal == 3) {
                return MotorolaSettings.System.getFloat(contentResolver, "mirror_cast_font_size_scale");
            }
            return MotorolaSettings.System.getFloat(contentResolver, "desktop_font_size_scale");
        } catch (MotorolaSettings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public static boolean getMultiAudioFocusEnable(ContentResolver contentResolver) {
        int i;
        try {
            i = Settings.System.getInt(contentResolver, "multi_audio_focus_enabled");
        } catch (Exception e) {
            e.printStackTrace();
            i = 0;
        }
        return i == 1;
    }

    public static boolean getPhoneInWindowEnable(ContentResolver contentResolver) {
        return MotorolaSettings.System.getInt(contentResolver, "settings_phone_in_window") == 1;
    }

    public static int getPointerSize(ContentResolver contentResolver) {
        return SettingsActivity.sConnectType == ConnectType.RDP ? MotorolaSettings.Secure.getInt(contentResolver, "desktop_pointer_icon_size_remote", 1) : MotorolaSettings.Secure.getInt(contentResolver, "desktop_pointer_icon_size", 2);
    }

    public static int getPowerSaveSec(ContentResolver contentResolver, ConnectType connectType) {
        try {
            int iOrdinal = connectType.ordinal();
            return (iOrdinal == 1 || iOrdinal == 2) ? MotorolaSettings.System.getInt(contentResolver, "power_save_sec_rdp") : iOrdinal != 3 ? MotorolaSettings.System.getInt(contentResolver, "power_save_sec_rdp") : MotorolaSettings.System.getInt(contentResolver, "power_save_sec_miracast");
        } catch (MotorolaSettings.SettingNotFoundException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int getPowerSaveSecMax(ContentResolver contentResolver) {
        try {
            return MotorolaSettings.Global.getInt(contentResolver, "ready_for_power_save_sec_max");
        } catch (MotorolaSettings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean getReversePrimaryButton(ContentResolver contentResolver) {
        return MotorolaSettings.System.getInt(contentResolver, "reverse_mouse_primary_button") == 1;
    }

    public static int getScreenOffTimeMax(ContentResolver contentResolver) {
        try {
            return MotorolaSettings.Global.getInt(contentResolver, "desktop_max_screen_timeout_seconds");
        } catch (MotorolaSettings.SettingNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int getTimeout(ContentResolver contentResolver) {
        try {
            return MotorolaSettings.Global.getInt(contentResolver, "desktop_screen_timeout_seconds");
        } catch (MotorolaSettings.SettingNotFoundException unused) {
            return 0;
        }
    }

    public static boolean putDisablePopupNotification(ContentResolver contentResolver, boolean z) {
        return MotorolaSettings.System.putInt(contentResolver, "settings_disable_popup_notification", z ? 1 : 0);
    }

    public static boolean putDisplayScale(ContentResolver contentResolver, float f, ConnectType connectType) {
        int iOrdinal = connectType.ordinal();
        if (iOrdinal == 1) {
            return MotorolaSettings.System.putFloat(contentResolver, "remote_desktop_display_size_scale", f);
        }
        if (iOrdinal == 2) {
            return MotorolaSettings.System.putFloat(contentResolver, "desktop_display_size_scale", f);
        }
        if (iOrdinal != 3) {
            return false;
        }
        return MotorolaSettings.System.putFloat(contentResolver, "mirror_cast_display_size_scale", f);
    }

    public static boolean putFontScale(ContentResolver contentResolver, float f, ConnectType connectType) {
        int iOrdinal = connectType.ordinal();
        if (iOrdinal == 1) {
            return MotorolaSettings.System.putFloat(contentResolver, "remote_desktop_font_size_scale", f);
        }
        if (iOrdinal == 2) {
            SystemProperties.set("persist.desktop.font_size_scale", Float.toString(f));
            return MotorolaSettings.System.putFloat(contentResolver, "desktop_font_size_scale", f);
        }
        if (iOrdinal != 3) {
            return false;
        }
        return MotorolaSettings.System.putFloat(contentResolver, "mirror_cast_font_size_scale", f);
    }

    public static boolean putPhoneInWindowEnable(ContentResolver contentResolver, boolean z) {
        return MotorolaSettings.System.putInt(contentResolver, "settings_phone_in_window", z ? 1 : 0);
    }

    public static boolean putPowerSaveSec(ContentResolver contentResolver, int i, ConnectType connectType) {
        int iOrdinal = connectType.ordinal();
        if (iOrdinal == 1 || iOrdinal == 2) {
            return MotorolaSettings.System.putInt(contentResolver, "power_save_sec_rdp", i);
        }
        if (iOrdinal != 3) {
            return false;
        }
        return MotorolaSettings.System.putInt(contentResolver, "power_save_sec_miracast", i);
    }

    public static boolean putTimeout(ContentResolver contentResolver, int i) {
        return MotorolaSettings.Global.putInt(contentResolver, "desktop_screen_timeout_seconds", i);
    }

    public static void setAutoAddIconToHome(ContentResolver contentResolver, boolean z) {
        MotorolaSettings.Global.putInt(contentResolver, "desktop_auto_add", z ? 1 : 0);
    }

    public static void setDisplayModeSetting(ContentResolver contentResolver, ConnectType connectType, int i, int i2, float f) {
        String displayModeString = getDisplayModeString(i, i2, f);
        int iOrdinal = connectType.ordinal();
        if (iOrdinal == 1) {
            MotorolaSettings.Global.putString(contentResolver, "desktop_resolution_remote", displayModeString);
        } else if (iOrdinal == 2) {
            MotorolaSettings.Global.putString(contentResolver, "desktop_resolution_dp", displayModeString);
        } else {
            if (iOrdinal != 3) {
                return;
            }
            MotorolaSettings.Global.putString(contentResolver, "desktop_resolution_wfd", displayModeString);
        }
    }

    public static void setMultiAudioFocusEnable(ContentResolver contentResolver, boolean z) {
        Settings.System.putInt(contentResolver, "multi_audio_focus_enabled", z ? 1 : 0);
    }

    public static void setPointerSize(ContentResolver contentResolver, int i) {
        Log.d("TAG", "setPointerSize: size = " + i);
        if (SettingsActivity.sConnectType == ConnectType.RDP) {
            MotorolaSettings.Secure.putInt(contentResolver, "desktop_pointer_icon_size_remote", i);
        }
        MotorolaSettings.Secure.putInt(contentResolver, "desktop_pointer_icon_size", i);
    }

    public static boolean setReversePrimaryButton(ContentResolver contentResolver, boolean z) {
        try {
            return MotorolaSettings.System.putInt(contentResolver, "reverse_mouse_primary_button", z ? 1 : 0);
        } catch (Exception unused) {
            return false;
        }
    }
}
