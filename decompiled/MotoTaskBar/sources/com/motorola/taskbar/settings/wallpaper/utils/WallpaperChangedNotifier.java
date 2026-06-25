package com.motorola.taskbar.settings.wallpaper.utils;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.TypeIntrinsics;

/* JADX INFO: compiled from: WallpaperChangedNotifier.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class WallpaperChangedNotifier {
    public static final WallpaperChangedNotifier INSTANCE = new WallpaperChangedNotifier();
    private static final List mListeners = new ArrayList();

    /* JADX INFO: compiled from: WallpaperChangedNotifier.kt */
    public interface Listener {
        void onWallpaperChanged();
    }

    private WallpaperChangedNotifier() {
    }

    public final void notifyWallpaperChanged() {
        int size = mListeners.size();
        for (int i = 0; i < size; i++) {
            ((Listener) mListeners.get(i)).onWallpaperChanged();
        }
    }

    public final void registerListener(Listener listener) {
        listener.getClass();
        List list = mListeners;
        if (list.contains(listener)) {
            return;
        }
        list.add(listener);
    }

    public final void unregisterListener(Listener listener) {
        TypeIntrinsics.asMutableCollection(mListeners).remove(listener);
    }
}
