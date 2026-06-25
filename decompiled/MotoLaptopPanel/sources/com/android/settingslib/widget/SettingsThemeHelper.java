package com.android.settingslib.widget;

import android.content.Context;
import com.android.settingslib.widget.theme.flags.Flags;
import java.util.Arrays;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: SettingsThemeHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class SettingsThemeHelper {
    public static final SettingsThemeHelper INSTANCE = new SettingsThemeHelper();
    private static ExpressiveThemeState expressiveThemeState = ExpressiveThemeState.UNKNOWN;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: SettingsThemeHelper.kt */
    public final class ExpressiveThemeState {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ ExpressiveThemeState[] $VALUES;
        public static final ExpressiveThemeState UNKNOWN = new ExpressiveThemeState("UNKNOWN", 0);
        public static final ExpressiveThemeState ENABLED = new ExpressiveThemeState("ENABLED", 1);
        public static final ExpressiveThemeState DISABLED = new ExpressiveThemeState("DISABLED", 2);

        private static final /* synthetic */ ExpressiveThemeState[] $values() {
            return new ExpressiveThemeState[]{UNKNOWN, ENABLED, DISABLED};
        }

        static {
            ExpressiveThemeState[] expressiveThemeStateArr$values = $values();
            $VALUES = expressiveThemeStateArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(expressiveThemeStateArr$values);
        }

        private ExpressiveThemeState(String str, int i) {
        }

        public static ExpressiveThemeState valueOf(String str) {
            return (ExpressiveThemeState) Enum.valueOf(ExpressiveThemeState.class, str);
        }

        public static ExpressiveThemeState[] values() {
            return (ExpressiveThemeState[]) $VALUES.clone();
        }
    }

    private SettingsThemeHelper() {
    }

    private final boolean getPropBoolean(Context context, String str, boolean z) {
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            Object objInvoke = clsLoadClass.getMethod("getBoolean", (Class[]) Arrays.copyOf(new Class[]{String.class, Boolean.TYPE}, 2)).invoke(clsLoadClass, Arrays.copyOf(new Object[]{str, Boolean.valueOf(z)}, 2));
            objInvoke.getClass();
            return ((Boolean) objInvoke).booleanValue();
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception unused) {
            return z;
        }
    }

    private final String getPropString(Context context, String str, String str2) {
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            Object objInvoke = clsLoadClass.getMethod("get", (Class[]) Arrays.copyOf(new Class[]{String.class, String.class}, 2)).invoke(clsLoadClass, str, str2);
            objInvoke.getClass();
            return (String) objInvoke;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception unused) {
            return str2;
        }
    }

    public static final boolean isExpressiveTheme(Context context) throws Exception {
        context.getClass();
        INSTANCE.tryInit(context);
        if (expressiveThemeState != ExpressiveThemeState.UNKNOWN) {
            return expressiveThemeState == ExpressiveThemeState.ENABLED;
        }
        throw new Exception("need to call com.android.settingslib.widget.SettingsThemeHelper.init(Context) first.");
    }

    public static final boolean isTablet(Context context) {
        context.getClass();
        return StringsKt.split$default((CharSequence) INSTANCE.getPropString(context, "ro.build.characteristics", ""), new char[]{','}, false, 0, 6, (Object) null).contains("tablet");
    }

    private final void tryInit(Context context) {
        expressiveThemeState = (getPropBoolean(context, "is_expressive_design_enabled", false) || Flags.isExpressiveDesignEnabled()) ? ExpressiveThemeState.ENABLED : ExpressiveThemeState.DISABLED;
    }
}
