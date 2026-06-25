package com.android.systemui.statusbar.domain.interactor;

import com.android.systemui.statusbar.data.repository.RemoteInputRepository;
import kotlinx.coroutines.flow.Flow;

/* JADX INFO: compiled from: RemoteInputInteractor.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RemoteInputInteractor {
    private final Flow isRemoteInputActive;

    public RemoteInputInteractor(RemoteInputRepository remoteInputRepository) {
        remoteInputRepository.getClass();
        this.isRemoteInputActive = remoteInputRepository.isRemoteInputActive();
    }

    public final Flow isRemoteInputActive() {
        return this.isRemoteInputActive;
    }
}
