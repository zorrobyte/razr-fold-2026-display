package com.motorola.taskbar.settings.wallpaper.model;

import android.text.TextUtils;

/* JADX INFO: compiled from: Category.kt */
/* JADX INFO: loaded from: classes2.dex */
public abstract class Category {
    private final String collectionId;
    private final boolean isEnumerable;
    private final int overlayIconSizeDp;
    private final int priority;
    private final String title;

    public Category(String str, String str2, int i) {
        str.getClass();
        str2.getClass();
        this.title = str;
        this.collectionId = str2;
        this.priority = i;
        this.overlayIconSizeDp = 40;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Category)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        return TextUtils.equals(this.collectionId, ((Category) obj).collectionId);
    }

    public int hashCode() {
        return this.collectionId.hashCode();
    }

    public boolean isEnumerable() {
        return this.isEnumerable;
    }
}
