package com.android.systemui.statusbar.policy.dagger;

import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.RemoteInputViewController;

/* JADX INFO: compiled from: RemoteInput.kt */
/* JADX INFO: loaded from: classes.dex */
public interface RemoteInputViewSubcomponent {

    /* JADX INFO: compiled from: RemoteInput.kt */
    public interface Factory {
        RemoteInputViewSubcomponent create(RemoteInputView remoteInputView, RemoteInputController remoteInputController);
    }

    RemoteInputViewController getController();
}
