package com.android.systemui.media.controls.ui.viewmodel;

import com.android.systemui.common.shared.model.Icon;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaOutputSwitcherViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaOutputSwitcherViewModel {
    private final float alpha;
    private final Icon deviceIcon;
    private final CharSequence deviceString;
    private final boolean isCurrentBroadcastApp;
    private final boolean isIntentValid;
    private final boolean isTapEnabled;
    private final boolean isVisible;
    private final Function0 onClicked;

    public MediaOutputSwitcherViewModel(boolean z, CharSequence charSequence, Icon icon, boolean z2, boolean z3, float f, boolean z4, Function0 function0) {
        charSequence.getClass();
        icon.getClass();
        function0.getClass();
        this.isTapEnabled = z;
        this.deviceString = charSequence;
        this.deviceIcon = icon;
        this.isCurrentBroadcastApp = z2;
        this.isIntentValid = z3;
        this.alpha = f;
        this.isVisible = z4;
        this.onClicked = function0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaOutputSwitcherViewModel)) {
            return false;
        }
        MediaOutputSwitcherViewModel mediaOutputSwitcherViewModel = (MediaOutputSwitcherViewModel) obj;
        return this.isTapEnabled == mediaOutputSwitcherViewModel.isTapEnabled && Intrinsics.areEqual(this.deviceString, mediaOutputSwitcherViewModel.deviceString) && Intrinsics.areEqual(this.deviceIcon, mediaOutputSwitcherViewModel.deviceIcon) && this.isCurrentBroadcastApp == mediaOutputSwitcherViewModel.isCurrentBroadcastApp && this.isIntentValid == mediaOutputSwitcherViewModel.isIntentValid && Float.compare(this.alpha, mediaOutputSwitcherViewModel.alpha) == 0 && this.isVisible == mediaOutputSwitcherViewModel.isVisible && Intrinsics.areEqual(this.onClicked, mediaOutputSwitcherViewModel.onClicked);
    }

    public final float getAlpha() {
        return this.alpha;
    }

    public final Icon getDeviceIcon() {
        return this.deviceIcon;
    }

    public final CharSequence getDeviceString() {
        return this.deviceString;
    }

    public final Function0 getOnClicked() {
        return this.onClicked;
    }

    public int hashCode() {
        return (((((((((((((Boolean.hashCode(this.isTapEnabled) * 31) + this.deviceString.hashCode()) * 31) + this.deviceIcon.hashCode()) * 31) + Boolean.hashCode(this.isCurrentBroadcastApp)) * 31) + Boolean.hashCode(this.isIntentValid)) * 31) + Float.hashCode(this.alpha)) * 31) + Boolean.hashCode(this.isVisible)) * 31) + this.onClicked.hashCode();
    }

    public final boolean isTapEnabled() {
        return this.isTapEnabled;
    }

    public String toString() {
        boolean z = this.isTapEnabled;
        CharSequence charSequence = this.deviceString;
        return "MediaOutputSwitcherViewModel(isTapEnabled=" + z + ", deviceString=" + ((Object) charSequence) + ", deviceIcon=" + this.deviceIcon + ", isCurrentBroadcastApp=" + this.isCurrentBroadcastApp + ", isIntentValid=" + this.isIntentValid + ", alpha=" + this.alpha + ", isVisible=" + this.isVisible + ", onClicked=" + this.onClicked + ")";
    }
}
