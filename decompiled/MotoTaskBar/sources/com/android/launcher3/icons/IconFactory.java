package com.android.launcher3.icons;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class IconFactory extends BaseIconFactory {
    private static IconFactory sPool;
    private static int sPoolId;
    private static final Object sPoolSync = new Object();
    private final int mPoolId;
    private IconFactory next;

    private IconFactory(Context context, int i, int i2, int i3) {
        super(context, i, i2);
        this.mPoolId = i3;
    }

    public static IconFactory obtain(Context context) {
        synchronized (sPoolSync) {
            try {
                IconFactory iconFactory = sPool;
                if (iconFactory == null) {
                    return new IconFactory(context, context.getResources().getConfiguration().densityDpi, context.getResources().getDimensionPixelSize(R$dimen.default_icon_bitmap_size), sPoolId);
                }
                sPool = iconFactory.next;
                iconFactory.next = null;
                return iconFactory;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.launcher3.icons.BaseIconFactory, java.lang.AutoCloseable
    public void close() {
        recycle();
    }

    public void recycle() {
        synchronized (sPoolSync) {
            try {
                if (sPoolId != this.mPoolId) {
                    return;
                }
                clear();
                this.next = sPool;
                sPool = this;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
