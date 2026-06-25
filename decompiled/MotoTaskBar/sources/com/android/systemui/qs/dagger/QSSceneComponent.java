package com.android.systemui.qs.dagger;

import android.view.View;

/* JADX INFO: compiled from: QSSceneComponent.kt */
/* JADX INFO: loaded from: classes.dex */
public interface QSSceneComponent extends QSComponent {

    /* JADX INFO: compiled from: QSSceneComponent.kt */
    public interface Factory {
        QSSceneComponent create(View view);
    }
}
