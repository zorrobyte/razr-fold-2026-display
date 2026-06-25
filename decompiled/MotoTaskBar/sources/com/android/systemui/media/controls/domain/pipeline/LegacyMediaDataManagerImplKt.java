package com.android.systemui.media.controls.domain.pipeline;

import com.android.internal.logging.InstanceId;
import com.android.systemui.media.controls.shared.model.MediaData;
import java.util.List;
import kotlin.collections.CollectionsKt;

/* JADX INFO: compiled from: LegacyMediaDataManagerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class LegacyMediaDataManagerImplKt {
    private static final String[] ART_URIS = {"android.media.metadata.ALBUM_ART_URI", "android.media.metadata.ART_URI", "android.media.metadata.DISPLAY_ICON_URI"};
    private static final MediaData LOADING;

    static {
        List listEmptyList = CollectionsKt.emptyList();
        List listEmptyList2 = CollectionsKt.emptyList();
        InstanceId instanceIdFakeInstanceId = InstanceId.fakeInstanceId(-1);
        instanceIdFakeInstanceId.getClass();
        LOADING = new MediaData(-1, false, null, null, null, null, null, listEmptyList, listEmptyList2, null, "INVALID", null, null, null, true, null, 0, false, null, false, null, false, 0L, 0L, instanceIdFakeInstanceId, -1, false, null, 218038784, null);
    }
}
