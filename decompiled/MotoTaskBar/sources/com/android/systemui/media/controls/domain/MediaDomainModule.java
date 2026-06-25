package com.android.systemui.media.controls.domain;

import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.media.controls.util.MediaFlags;
import javax.inject.Provider;

/* JADX INFO: compiled from: MediaDomainModule.kt */
/* JADX INFO: loaded from: classes.dex */
public interface MediaDomainModule {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: MediaDomainModule.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final MediaDataManager providesMediaDataManager(Provider provider, Provider provider2, MediaFlags mediaFlags) {
            provider.getClass();
            provider2.getClass();
            mediaFlags.getClass();
            if (mediaFlags.isMediaControlsRefactorEnabled()) {
                Object obj = provider2.get();
                obj.getClass();
                return (MediaDataManager) obj;
            }
            Object obj2 = provider.get();
            obj2.getClass();
            return (MediaDataManager) obj2;
        }
    }
}
