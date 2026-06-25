package com.android.systemui.log;

/* JADX INFO: compiled from: LogBufferFactory.kt */
/* JADX INFO: loaded from: classes.dex */
public final class LogBufferFactory {
    public static /* synthetic */ LogBuffer create$default(LogBufferFactory logBufferFactory, String str, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = true;
        }
        return logBufferFactory.create(str, i, z);
    }

    public final LogBuffer create(String str, int i) {
        str.getClass();
        return create$default(this, str, i, false, 4, null);
    }

    public final LogBuffer create(String str, int i, boolean z) {
        str.getClass();
        return new LogBuffer();
    }
}
