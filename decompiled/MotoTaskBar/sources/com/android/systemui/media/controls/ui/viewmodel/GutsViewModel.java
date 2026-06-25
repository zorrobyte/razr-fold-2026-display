package com.android.systemui.media.controls.ui.viewmodel;

import android.graphics.drawable.Drawable;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: GutsViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class GutsViewModel {
    private final int accentPrimaryColor;
    private final Drawable cancelTextBackground;
    private final CharSequence gutsText;
    private final boolean isDismissEnabled;
    private final Function0 onDismissClicked;
    private final Function0 onSettingsClicked;
    private final int surfaceColor;
    private final int textPrimaryColor;

    public GutsViewModel(CharSequence charSequence, int i, int i2, int i3, boolean z, Function0 function0, Drawable drawable, Function0 function02) {
        charSequence.getClass();
        function0.getClass();
        function02.getClass();
        this.gutsText = charSequence;
        this.textPrimaryColor = i;
        this.accentPrimaryColor = i2;
        this.surfaceColor = i3;
        this.isDismissEnabled = z;
        this.onDismissClicked = function0;
        this.cancelTextBackground = drawable;
        this.onSettingsClicked = function02;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GutsViewModel)) {
            return false;
        }
        GutsViewModel gutsViewModel = (GutsViewModel) obj;
        return Intrinsics.areEqual(this.gutsText, gutsViewModel.gutsText) && this.textPrimaryColor == gutsViewModel.textPrimaryColor && this.accentPrimaryColor == gutsViewModel.accentPrimaryColor && this.surfaceColor == gutsViewModel.surfaceColor && this.isDismissEnabled == gutsViewModel.isDismissEnabled && Intrinsics.areEqual(this.onDismissClicked, gutsViewModel.onDismissClicked) && Intrinsics.areEqual(this.cancelTextBackground, gutsViewModel.cancelTextBackground) && Intrinsics.areEqual(this.onSettingsClicked, gutsViewModel.onSettingsClicked);
    }

    public final int getAccentPrimaryColor() {
        return this.accentPrimaryColor;
    }

    public final Drawable getCancelTextBackground() {
        return this.cancelTextBackground;
    }

    public final CharSequence getGutsText() {
        return this.gutsText;
    }

    public final Function0 getOnDismissClicked() {
        return this.onDismissClicked;
    }

    public final Function0 getOnSettingsClicked() {
        return this.onSettingsClicked;
    }

    public final int getSurfaceColor() {
        return this.surfaceColor;
    }

    public final int getTextPrimaryColor() {
        return this.textPrimaryColor;
    }

    public int hashCode() {
        int iHashCode = ((((((((((this.gutsText.hashCode() * 31) + Integer.hashCode(this.textPrimaryColor)) * 31) + Integer.hashCode(this.accentPrimaryColor)) * 31) + Integer.hashCode(this.surfaceColor)) * 31) + Boolean.hashCode(this.isDismissEnabled)) * 31) + this.onDismissClicked.hashCode()) * 31;
        Drawable drawable = this.cancelTextBackground;
        return ((iHashCode + (drawable == null ? 0 : drawable.hashCode())) * 31) + this.onSettingsClicked.hashCode();
    }

    public final boolean isDismissEnabled() {
        return this.isDismissEnabled;
    }

    public String toString() {
        CharSequence charSequence = this.gutsText;
        return "GutsViewModel(gutsText=" + ((Object) charSequence) + ", textPrimaryColor=" + this.textPrimaryColor + ", accentPrimaryColor=" + this.accentPrimaryColor + ", surfaceColor=" + this.surfaceColor + ", isDismissEnabled=" + this.isDismissEnabled + ", onDismissClicked=" + this.onDismissClicked + ", cancelTextBackground=" + this.cancelTextBackground + ", onSettingsClicked=" + this.onSettingsClicked + ")";
    }
}
