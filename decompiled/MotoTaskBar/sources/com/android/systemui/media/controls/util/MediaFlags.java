package com.android.systemui.media.controls.util;

import android.os.UserHandle;
import com.android.systemui.flags.FeatureFlagsClassic;
import com.android.systemui.flags.Flags;

/* JADX INFO: compiled from: MediaFlags.kt */
/* JADX INFO: loaded from: classes.dex */
public final class MediaFlags {
    private final FeatureFlagsClassic featureFlags;

    public MediaFlags(FeatureFlagsClassic featureFlagsClassic) {
        featureFlagsClassic.getClass();
        this.featureFlags = featureFlagsClassic;
    }

    public final boolean areMediaSessionActionsEnabled(String str, UserHandle userHandle) {
        str.getClass();
        userHandle.getClass();
        return false;
    }

    public final boolean isMediaControlsRefactorEnabled() {
        return false;
    }

    public final boolean isRemoteResumeAllowed() {
        return this.featureFlags.isEnabled(Flags.INSTANCE.getMEDIA_REMOTE_RESUME());
    }

    public final boolean isResumeProgressEnabled() {
        return this.featureFlags.isEnabled(Flags.INSTANCE.getMEDIA_RESUME_PROGRESS());
    }

    public final boolean isRetainingPlayersEnabled() {
        return this.featureFlags.isEnabled(Flags.INSTANCE.getMEDIA_RETAIN_SESSIONS());
    }

    public final boolean isSceneContainerEnabled() {
        return false;
    }
}
