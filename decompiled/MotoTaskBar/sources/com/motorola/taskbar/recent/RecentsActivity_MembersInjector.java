package com.motorola.taskbar.recent;

import com.motorola.taskbar.model.SurfaceBlurController;

/* JADX INFO: loaded from: classes2.dex */
public abstract class RecentsActivity_MembersInjector {
    public static void injectRecentsController(RecentsActivity recentsActivity, RecentsController recentsController) {
        recentsActivity.recentsController = recentsController;
    }

    public static void injectSurfaceBlurController(RecentsActivity recentsActivity, SurfaceBlurController surfaceBlurController) {
        recentsActivity.surfaceBlurController = surfaceBlurController;
    }
}
