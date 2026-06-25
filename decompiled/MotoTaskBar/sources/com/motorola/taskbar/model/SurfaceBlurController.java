package com.motorola.taskbar.model;

import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import androidx.core.view.OneShotPreDrawListener;
import com.android.systemui.Dumpable;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.BlurUtils;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: SurfaceBlurController.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class SurfaceBlurController implements Dumpable {
    public static final Companion Companion = new Companion(null);
    private float mBlurLevel;
    private SurfaceControl mTargetSurfaceControl;

    /* JADX INFO: compiled from: SurfaceBlurController.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private final boolean getInvalidSurface() {
        SurfaceControl surfaceControl = this.mTargetSurfaceControl;
        if (surfaceControl == null) {
            return true;
        }
        surfaceControl.getClass();
        return !surfaceControl.isValid();
    }

    public final void setLevel(float f) {
        float fClamp = Utilities.clamp(f, 0.0f, 1.0f);
        if (Float.compare(this.mBlurLevel, fClamp) == 0) {
            return;
        }
        if (BlurUtils.supportsBlursOnWindows() && getInvalidSurface()) {
            return;
        }
        this.mBlurLevel = fClamp;
    }

    public final void setSurface(final View view) {
        view.getClass();
        OneShotPreDrawListener.add(view, new Runnable() { // from class: com.motorola.taskbar.model.SurfaceBlurController$setSurface$$inlined$doOnPreDraw$1
            @Override // java.lang.Runnable
            public final void run() {
                ViewRootImpl viewRootImpl = view.getViewRootImpl();
                viewRootImpl.getClass();
                this.mTargetSurfaceControl = viewRootImpl.getSurfaceControl();
                if (this.mTargetSurfaceControl != null) {
                    SurfaceBlurController surfaceBlurController = this;
                    surfaceBlurController.setLevel(surfaceBlurController.mBlurLevel);
                }
            }
        });
    }
}
