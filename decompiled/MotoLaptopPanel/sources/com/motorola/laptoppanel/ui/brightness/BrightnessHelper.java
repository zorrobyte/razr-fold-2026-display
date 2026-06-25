package com.motorola.laptoppanel.ui.brightness;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import com.android.settingslib.display.BrightnessUtils;
import com.motorola.laptoppanel.util.Logger;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt;

/* JADX INFO: compiled from: BrightnessHelper.kt */
/* JADX INFO: loaded from: classes.dex */
public final class BrightnessHelper {
    private static volatile BrightnessHelper INSTANCE;
    private final float brightnessMax;
    private final float brightnessMin;
    private final BrightnessHelper$brightnessObserver$1 brightnessObserver;
    private final Context context;
    private final int displayId;
    private final DisplayManager displayManager;
    private final int hbmPolicy;
    private boolean isAutoMode;
    private final CopyOnWriteArrayList listeners;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;

    /* JADX INFO: compiled from: BrightnessHelper.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final BrightnessHelper getInstance(Context context) {
            BrightnessHelper brightnessHelper;
            context.getClass();
            BrightnessHelper brightnessHelper2 = BrightnessHelper.INSTANCE;
            if (brightnessHelper2 != null) {
                return brightnessHelper2;
            }
            synchronized (this) {
                brightnessHelper = BrightnessHelper.INSTANCE;
                if (brightnessHelper == null) {
                    brightnessHelper = new BrightnessHelper(context, null);
                    BrightnessHelper.INSTANCE = brightnessHelper;
                }
            }
            return brightnessHelper;
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.motorola.laptoppanel.ui.brightness.BrightnessHelper$brightnessObserver$1] */
    private BrightnessHelper(final Context context) {
        this.context = context.getApplicationContext();
        this.listeners = new CopyOnWriteArrayList();
        final Handler handler = new Handler(Looper.getMainLooper());
        this.brightnessObserver = new ContentObserver(handler) { // from class: com.motorola.laptoppanel.ui.brightness.BrightnessHelper$brightnessObserver$1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                BrightnessHelper brightnessHelper = this.this$0;
                ContentResolver contentResolver = context.getContentResolver();
                contentResolver.getClass();
                brightnessHelper.isAutoMode = brightnessHelper.isAutoBrightness(contentResolver);
                Iterator it = this.this$0.listeners.iterator();
                while (it.hasNext()) {
                    ((Function0) it.next()).invoke();
                }
            }
        };
        Object systemService = context.getSystemService("display");
        systemService.getClass();
        DisplayManager displayManager = (DisplayManager) systemService;
        this.displayManager = displayManager;
        int displayId = context.getDisplayId();
        this.displayId = displayId;
        BrightnessInfo motoBrightnessInfo = displayManager.getDisplay(displayId).getMotoBrightnessInfo();
        if (motoBrightnessInfo != null) {
            this.brightnessMin = motoBrightnessInfo.brightnessMinimum;
            this.brightnessMax = motoBrightnessInfo.brightnessMaximum;
            this.hbmPolicy = motoBrightnessInfo.highBrightnessPolicy;
        } else {
            this.brightnessMin = 0.0f;
            this.brightnessMax = 1.0f;
            this.hbmPolicy = 0;
        }
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.getClass();
        this.isAutoMode = isAutoBrightness(contentResolver);
        Logger.INSTANCE.d("BrightnessHelper", "Initialized BrightnessHelper: min=" + this.brightnessMin + ", max=" + this.brightnessMax + ", policy=" + this.hbmPolicy, new Object[0]);
    }

    public /* synthetic */ BrightnessHelper(Context context, DefaultConstructorMarker defaultConstructorMarker) {
        this(context);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isAutoBrightness(ContentResolver contentResolver) {
        try {
            return Settings.System.getInt(contentResolver, "screen_brightness_mode") == 1;
        } catch (Settings.SettingNotFoundException unused) {
            Logger.INSTANCE.e("BrightnessHelper", "Failed to find SCREEN_BRIGHTNESS_MODE", new Object[0]);
            return false;
        }
    }

    private final float lerp(float f, float f2, float f3) {
        return f + ((f2 - f) * f3);
    }

    public final int getCurrentBrightness() throws Settings.SettingNotFoundException {
        int i = Settings.System.getInt(this.context.getContentResolver(), "screen_brightness");
        int iConvertLinearToGammaFloat = BrightnessUtils.convertLinearToGammaFloat(i, 0.0f, this.brightnessMax * 255, this.hbmPolicy);
        Logger.INSTANCE.d("BrightnessHelper", "Get current brightness: setting brightness = " + i + ", gamma=" + iConvertLinearToGammaFloat, new Object[0]);
        return iConvertLinearToGammaFloat;
    }

    public final float getLinearBrightness(float f) {
        return BrightnessUtils.convertGammaToLinearFloat((int) f, 0.0f, 1.0f, this.hbmPolicy);
    }

    public final void registerBrightnessObserver(Function0 function0) {
        function0.getClass();
        if (!this.listeners.contains(function0)) {
            this.listeners.add(function0);
            if (this.listeners.size() == 1) {
                try {
                    this.context.getContentResolver().registerContentObserver(Settings.System.getUriFor("screen_brightness"), false, this.brightnessObserver);
                    this.context.getContentResolver().registerContentObserver(Settings.System.getUriFor("screen_brightness_mode"), false, this.brightnessObserver);
                } catch (Exception e) {
                    Logger.INSTANCE.e("BrightnessHelper", "Failed to register brightness observer", e);
                }
            }
        }
        function0.invoke();
    }

    public final void setBrightness(float f) {
        float linearBrightness = getLinearBrightness(f);
        int iRoundToInt = MathKt.roundToInt(lerp(0.0f, this.brightnessMax * 255, linearBrightness));
        Logger.INSTANCE.d("BrightnessHelper", "setBrightness: gamma=" + f + ", linear=" + linearBrightness + ", settingValue=" + iRoundToInt, new Object[0]);
        if (this.isAutoMode) {
            Settings.System.putInt(this.context.getContentResolver(), "screen_brightness_mode", 0);
        }
        Settings.System.putInt(this.context.getContentResolver(), "screen_brightness", iRoundToInt);
    }

    public final void unregisterBrightnessObserver(Function0 function0) {
        function0.getClass();
        if (this.listeners.remove(function0) && this.listeners.isEmpty()) {
            try {
                this.context.getContentResolver().unregisterContentObserver(this.brightnessObserver);
            } catch (Exception e) {
                Logger.INSTANCE.e("BrightnessHelper", "Failed to unregister brightness observer", e);
            }
            if (this.isAutoMode) {
                Settings.System.putInt(this.context.getContentResolver(), "screen_brightness_mode", 1);
            }
        }
    }
}
