package com.motorola.taskbar.settings.wallpaper.module;

import com.motorola.taskbar.settings.wallpaper.model.Category;

/* JADX INFO: compiled from: CategoryReceiver.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface CategoryReceiver {
    void doneFetchingCategories();

    void onCategoryReceived(Category category);
}
