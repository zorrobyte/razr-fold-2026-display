package com.android.systemui.statusbar.notification;

/* JADX INFO: compiled from: FeedbackIcon.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FeedbackIcon {
    private final int contentDescRes;
    private final int iconRes;

    public FeedbackIcon(int i, int i2) {
        this.iconRes = i;
        this.contentDescRes = i2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FeedbackIcon)) {
            return false;
        }
        FeedbackIcon feedbackIcon = (FeedbackIcon) obj;
        return this.iconRes == feedbackIcon.iconRes && this.contentDescRes == feedbackIcon.contentDescRes;
    }

    public final int getContentDescRes() {
        return this.contentDescRes;
    }

    public final int getIconRes() {
        return this.iconRes;
    }

    public int hashCode() {
        return (Integer.hashCode(this.iconRes) * 31) + Integer.hashCode(this.contentDescRes);
    }

    public String toString() {
        return "FeedbackIcon(iconRes=" + this.iconRes + ", contentDescRes=" + this.contentDescRes + ")";
    }
}
