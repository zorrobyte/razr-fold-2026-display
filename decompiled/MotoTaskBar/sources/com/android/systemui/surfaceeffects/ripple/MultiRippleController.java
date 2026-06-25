package com.android.systemui.surfaceeffects.ripple;

import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: MultiRippleController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MultiRippleController {
    public static final Companion Companion = new Companion(null);
    private final MultiRippleView multipleRippleView;

    /* JADX INFO: compiled from: MultiRippleController.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getMAX_RIPPLE_NUMBER$annotations() {
        }
    }

    public MultiRippleController(MultiRippleView multiRippleView) {
        multiRippleView.getClass();
        this.multipleRippleView = multiRippleView;
    }

    public final void play(final RippleAnimation rippleAnimation) {
        rippleAnimation.getClass();
        if (this.multipleRippleView.getRipples().size() >= 10) {
            return;
        }
        this.multipleRippleView.getRipples().add(rippleAnimation);
        rippleAnimation.play(new Runnable() { // from class: com.android.systemui.surfaceeffects.ripple.MultiRippleController.play.1
            @Override // java.lang.Runnable
            public final void run() {
                MultiRippleController.this.multipleRippleView.getRipples().remove(rippleAnimation);
            }
        });
        this.multipleRippleView.invalidate();
    }

    public final void updateColor(int i) {
        ArrayList ripples = this.multipleRippleView.getRipples();
        int size = ripples.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = ripples.get(i2);
            i2++;
            ((RippleAnimation) obj).updateColor(i);
        }
    }
}
