package com.android.systemui.media.controls.shared.model;

import android.app.PendingIntent;
import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MediaData.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaDeviceData {
    private final boolean enabled;
    private final Drawable icon;
    private final String id;
    private final PendingIntent intent;
    private final CharSequence name;
    private final boolean showBroadcastButton;

    public MediaDeviceData(boolean z, Drawable drawable, CharSequence charSequence, PendingIntent pendingIntent, String str, boolean z2) {
        this.enabled = z;
        this.icon = drawable;
        this.name = charSequence;
        this.intent = pendingIntent;
        this.id = str;
        this.showBroadcastButton = z2;
    }

    public /* synthetic */ MediaDeviceData(boolean z, Drawable drawable, CharSequence charSequence, PendingIntent pendingIntent, String str, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, drawable, charSequence, (i & 8) != 0 ? null : pendingIntent, (i & 16) != 0 ? null : str, z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaDeviceData)) {
            return false;
        }
        MediaDeviceData mediaDeviceData = (MediaDeviceData) obj;
        return this.enabled == mediaDeviceData.enabled && Intrinsics.areEqual(this.icon, mediaDeviceData.icon) && Intrinsics.areEqual(this.name, mediaDeviceData.name) && Intrinsics.areEqual(this.intent, mediaDeviceData.intent) && Intrinsics.areEqual(this.id, mediaDeviceData.id) && this.showBroadcastButton == mediaDeviceData.showBroadcastButton;
    }

    public final boolean equalsWithoutIcon(MediaDeviceData mediaDeviceData) {
        return mediaDeviceData != null && this.enabled == mediaDeviceData.enabled && Intrinsics.areEqual(this.name, mediaDeviceData.name) && Intrinsics.areEqual(this.intent, mediaDeviceData.intent) && Intrinsics.areEqual(this.id, mediaDeviceData.id) && this.showBroadcastButton == mediaDeviceData.showBroadcastButton;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final Drawable getIcon() {
        return this.icon;
    }

    public final PendingIntent getIntent() {
        return this.intent;
    }

    public final CharSequence getName() {
        return this.name;
    }

    public int hashCode() {
        int iHashCode = Boolean.hashCode(this.enabled) * 31;
        Drawable drawable = this.icon;
        int iHashCode2 = (iHashCode + (drawable == null ? 0 : drawable.hashCode())) * 31;
        CharSequence charSequence = this.name;
        int iHashCode3 = (iHashCode2 + (charSequence == null ? 0 : charSequence.hashCode())) * 31;
        PendingIntent pendingIntent = this.intent;
        int iHashCode4 = (iHashCode3 + (pendingIntent == null ? 0 : pendingIntent.hashCode())) * 31;
        String str = this.id;
        return ((iHashCode4 + (str != null ? str.hashCode() : 0)) * 31) + Boolean.hashCode(this.showBroadcastButton);
    }

    public String toString() {
        boolean z = this.enabled;
        Drawable drawable = this.icon;
        CharSequence charSequence = this.name;
        return "MediaDeviceData(enabled=" + z + ", icon=" + drawable + ", name=" + ((Object) charSequence) + ", intent=" + this.intent + ", id=" + this.id + ", showBroadcastButton=" + this.showBroadcastButton + ")";
    }
}
