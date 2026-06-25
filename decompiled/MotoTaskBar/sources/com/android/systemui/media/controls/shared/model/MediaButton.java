package com.android.systemui.media.controls.shared.model;

import com.motorola.taskbar.R$id;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaData.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaButton {
    private final MediaAction custom0;
    private final MediaAction custom1;
    private final MediaAction nextOrCustom;
    private final MediaAction playOrPause;
    private final MediaAction prevOrCustom;
    private final boolean reserveNext;
    private final boolean reservePrev;

    public MediaButton(MediaAction mediaAction, MediaAction mediaAction2, MediaAction mediaAction3, MediaAction mediaAction4, MediaAction mediaAction5, boolean z, boolean z2) {
        this.playOrPause = mediaAction;
        this.nextOrCustom = mediaAction2;
        this.prevOrCustom = mediaAction3;
        this.custom0 = mediaAction4;
        this.custom1 = mediaAction5;
        this.reserveNext = z;
        this.reservePrev = z2;
    }

    public /* synthetic */ MediaButton(MediaAction mediaAction, MediaAction mediaAction2, MediaAction mediaAction3, MediaAction mediaAction4, MediaAction mediaAction5, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : mediaAction, (i & 2) != 0 ? null : mediaAction2, (i & 4) != 0 ? null : mediaAction3, (i & 8) != 0 ? null : mediaAction4, (i & 16) != 0 ? null : mediaAction5, (i & 32) != 0 ? false : z, (i & 64) != 0 ? false : z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaButton)) {
            return false;
        }
        MediaButton mediaButton = (MediaButton) obj;
        return Intrinsics.areEqual(this.playOrPause, mediaButton.playOrPause) && Intrinsics.areEqual(this.nextOrCustom, mediaButton.nextOrCustom) && Intrinsics.areEqual(this.prevOrCustom, mediaButton.prevOrCustom) && Intrinsics.areEqual(this.custom0, mediaButton.custom0) && Intrinsics.areEqual(this.custom1, mediaButton.custom1) && this.reserveNext == mediaButton.reserveNext && this.reservePrev == mediaButton.reservePrev;
    }

    public final MediaAction getActionById(int i) {
        if (i == R$id.actionPlayPause) {
            return this.playOrPause;
        }
        if (i == R$id.actionNext) {
            return this.nextOrCustom;
        }
        if (i == R$id.actionPrev) {
            return this.prevOrCustom;
        }
        if (i == R$id.action0) {
            return this.custom0;
        }
        if (i == R$id.action1) {
            return this.custom1;
        }
        return null;
    }

    public final boolean getReserveNext() {
        return this.reserveNext;
    }

    public final boolean getReservePrev() {
        return this.reservePrev;
    }

    public int hashCode() {
        MediaAction mediaAction = this.playOrPause;
        int iHashCode = (mediaAction == null ? 0 : mediaAction.hashCode()) * 31;
        MediaAction mediaAction2 = this.nextOrCustom;
        int iHashCode2 = (iHashCode + (mediaAction2 == null ? 0 : mediaAction2.hashCode())) * 31;
        MediaAction mediaAction3 = this.prevOrCustom;
        int iHashCode3 = (iHashCode2 + (mediaAction3 == null ? 0 : mediaAction3.hashCode())) * 31;
        MediaAction mediaAction4 = this.custom0;
        int iHashCode4 = (iHashCode3 + (mediaAction4 == null ? 0 : mediaAction4.hashCode())) * 31;
        MediaAction mediaAction5 = this.custom1;
        return ((((iHashCode4 + (mediaAction5 != null ? mediaAction5.hashCode() : 0)) * 31) + Boolean.hashCode(this.reserveNext)) * 31) + Boolean.hashCode(this.reservePrev);
    }

    public String toString() {
        return "MediaButton(playOrPause=" + this.playOrPause + ", nextOrCustom=" + this.nextOrCustom + ", prevOrCustom=" + this.prevOrCustom + ", custom0=" + this.custom0 + ", custom1=" + this.custom1 + ", reserveNext=" + this.reserveNext + ", reservePrev=" + this.reservePrev + ")";
    }
}
