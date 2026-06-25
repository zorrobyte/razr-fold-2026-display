package com.motorola.taskbar.model;

import android.content.Context;
import android.database.ContentObserver;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import com.android.systemui.statusbar.policy.CallbackController;
import com.motorola.android.provider.MotorolaSettings;
import com.motorola.taskbar.MotoFeature;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes2.dex */
public class DisplayConfigurationController implements CallbackController {
    private final Context mContext;
    private final DisplayManager mDisplayManager;
    private final Handler mMainHandler;
    private final WindowManager mWindowManager;
    private final ArrayList mListeners = new ArrayList();
    private final SparseArray mDisplayDensitys = new SparseArray();
    private final SparseArray mDisplayWidths = new SparseArray();
    private DisplayManager.DisplayListener mDisplayListener = new AnonymousClass2();

    /* JADX INFO: renamed from: com.motorola.taskbar.model.DisplayConfigurationController$1, reason: invalid class name */
    class AnonymousClass1 extends ContentObserver {
        AnonymousClass1(Handler handler) {
            super(handler);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onChange$0() {
            DisplayConfigurationController.this.notifiyFontSizeChanged();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            DisplayConfigurationController.this.mMainHandler.postDelayed(new Runnable() { // from class: com.motorola.taskbar.model.DisplayConfigurationController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onChange$0();
                }
            }, 500L);
        }
    }

    /* JADX INFO: renamed from: com.motorola.taskbar.model.DisplayConfigurationController$2, reason: invalid class name */
    class AnonymousClass2 implements DisplayManager.DisplayListener {
        AnonymousClass2() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayChanged$0(int i) {
            DisplayConfigurationController.this.notifiyDensityChanged(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisplayChanged$1(int i) {
            DisplayConfigurationController.this.notifiyDensityChanged(i);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
            Display display = DisplayConfigurationController.this.mDisplayManager.getDisplay(i);
            if (display == null) {
                return;
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getRealMetrics(displayMetrics);
            DisplayConfigurationController.this.mDisplayDensitys.put(i, Float.valueOf(displayMetrics.density));
            DisplayConfigurationController.this.mDisplayWidths.put(i, Integer.valueOf(displayMetrics.widthPixels));
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(final int i) {
            Display display = DisplayConfigurationController.this.mDisplayManager.getDisplay(i);
            if (display == null) {
                return;
            }
            DisplayMetrics displayMetrics = new DisplayMetrics();
            display.getRealMetrics(displayMetrics);
            float f = displayMetrics.density;
            Float f2 = (Float) DisplayConfigurationController.this.mDisplayDensitys.get(i);
            if (f2 == null || f != f2.floatValue()) {
                DisplayConfigurationController.this.mDisplayDensitys.put(i, Float.valueOf(f));
                DisplayConfigurationController.this.mMainHandler.postDelayed(new Runnable() { // from class: com.motorola.taskbar.model.DisplayConfigurationController$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onDisplayChanged$0(i);
                    }
                }, 500L);
            }
            float f3 = displayMetrics.widthPixels;
            if (((Integer) DisplayConfigurationController.this.mDisplayWidths.get(i)) == null || f3 != r1.intValue()) {
                DisplayConfigurationController.this.mDisplayDensitys.put(i, Float.valueOf(f3));
                DisplayConfigurationController.this.mMainHandler.postDelayed(new Runnable() { // from class: com.motorola.taskbar.model.DisplayConfigurationController$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onDisplayChanged$1(i);
                    }
                }, 500L);
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
            DisplayConfigurationController.this.mDisplayDensitys.remove(i);
            DisplayConfigurationController.this.mDisplayWidths.remove(i);
        }
    }

    public interface Listener {
        void onDisplayDensityChangedChanged(int i);

        void onFontSizeChanged(int i);
    }

    public DisplayConfigurationController(Context context, Handler handler) {
        this.mContext = context;
        this.mMainHandler = handler;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        this.mDisplayManager = displayManager;
        displayManager.registerDisplayListener(this.mDisplayListener, handler);
        context.getContentResolver().registerContentObserver(MotorolaSettings.System.getUriFor("desktop_font_size_scale"), true, new AnonymousClass1(handler));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifiyDensityChanged(int i) {
        synchronized (this.mListeners) {
            for (int i2 = 0; i2 < this.mListeners.size(); i2++) {
                try {
                    ((Listener) this.mListeners.get(i2)).onDisplayDensityChangedChanged(i);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifiyFontSizeChanged() {
        for (Display display : this.mDisplayManager.getDisplays()) {
            if (display.getDisplayId() != 0 && MotoFeature.isDesktopModeDisplay(display)) {
                notifiyFontSizeChanged(display.getDisplayId());
            }
        }
    }

    private void notifiyFontSizeChanged(int i) {
        synchronized (this.mListeners) {
            for (int i2 = 0; i2 < this.mListeners.size(); i2++) {
                try {
                    ((Listener) this.mListeners.get(i2)).onFontSizeChanged(i);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void addCallback(Listener listener) {
        this.mListeners.add(listener);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public void removeCallback(Listener listener) {
        this.mListeners.remove(listener);
    }
}
