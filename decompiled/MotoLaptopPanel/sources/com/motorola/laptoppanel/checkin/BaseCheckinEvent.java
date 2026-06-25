package com.motorola.laptoppanel.checkin;

import android.content.ContentResolver;
import com.motorola.android.provider.CheckinEvent;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: BaseCheckinEvent.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class BaseCheckinEvent {
    private final CheckinEvent event;

    public BaseCheckinEvent(String str, String str2, String str3, long j) {
        str.getClass();
        str2.getClass();
        str3.getClass();
        this.event = new CheckinEvent(str, str2, str3, j);
    }

    public /* synthetic */ BaseCheckinEvent(String str, String str2, String str3, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, (i & 8) != 0 ? System.currentTimeMillis() : j);
    }

    public final void publish(ContentResolver contentResolver) {
        contentResolver.getClass();
        this.event.publish(contentResolver);
    }

    public final void setValue(String str, String str2) {
        str.getClass();
        str2.getClass();
        this.event.setValue(str, str2);
    }
}
