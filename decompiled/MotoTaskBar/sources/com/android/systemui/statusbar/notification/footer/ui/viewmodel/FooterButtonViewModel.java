package com.android.systemui.statusbar.notification.footer.ui.viewmodel;

import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.Flow;

/* JADX INFO: compiled from: FooterButtonViewModel.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FooterButtonViewModel {
    private final Flow accessibilityDescriptionId;
    private final Flow isVisible;
    private final Flow labelId;

    public FooterButtonViewModel(Flow flow, Flow flow2, Flow flow3) {
        flow.getClass();
        flow2.getClass();
        flow3.getClass();
        this.labelId = flow;
        this.accessibilityDescriptionId = flow2;
        this.isVisible = flow3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FooterButtonViewModel)) {
            return false;
        }
        FooterButtonViewModel footerButtonViewModel = (FooterButtonViewModel) obj;
        return Intrinsics.areEqual(this.labelId, footerButtonViewModel.labelId) && Intrinsics.areEqual(this.accessibilityDescriptionId, footerButtonViewModel.accessibilityDescriptionId) && Intrinsics.areEqual(this.isVisible, footerButtonViewModel.isVisible);
    }

    public final Flow getAccessibilityDescriptionId() {
        return this.accessibilityDescriptionId;
    }

    public final Flow getLabelId() {
        return this.labelId;
    }

    public int hashCode() {
        return (((this.labelId.hashCode() * 31) + this.accessibilityDescriptionId.hashCode()) * 31) + this.isVisible.hashCode();
    }

    public final Flow isVisible() {
        return this.isVisible;
    }

    public String toString() {
        return "FooterButtonViewModel(labelId=" + this.labelId + ", accessibilityDescriptionId=" + this.accessibilityDescriptionId + ", isVisible=" + this.isVisible + ")";
    }
}
