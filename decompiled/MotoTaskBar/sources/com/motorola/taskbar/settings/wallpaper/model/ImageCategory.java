package com.motorola.taskbar.settings.wallpaper.model;

/* JADX INFO: compiled from: ImageCategory.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class ImageCategory extends Category {
    private final int overlayIconResId;
    private final int overlayIconSizeDp;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImageCategory(String str, String str2, int i, int i2) {
        super(str, str2, i);
        str.getClass();
        str2.getClass();
        this.overlayIconResId = i2;
        this.overlayIconSizeDp = 128;
    }
}
