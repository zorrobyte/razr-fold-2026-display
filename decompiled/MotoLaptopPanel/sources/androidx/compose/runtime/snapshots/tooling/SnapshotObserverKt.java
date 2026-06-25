package androidx.compose.runtime.snapshots.tooling;

import android.support.v4.media.session.MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.collection.ScatterSet;
import androidx.compose.runtime.collection.ScatterSetWrapperKt;
import androidx.compose.runtime.external.kotlinx.collections.immutable.PersistentList;
import androidx.compose.runtime.snapshots.Snapshot;
import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: SnapshotObserver.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SnapshotObserverKt {
    private static PersistentList observers;

    public static final void dispatchCreatedObservers(PersistentList persistentList, Snapshot snapshot, Snapshot snapshot2, Map map) {
        if (persistentList.size() > 0) {
            MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(persistentList.get(0));
            if (map == null) {
                throw null;
            }
            throw null;
        }
    }

    public static final void dispatchObserverOnApplied(Snapshot snapshot, ScatterSet scatterSet) {
        PersistentList persistentList = observers;
        if (persistentList == null || persistentList.isEmpty()) {
            return;
        }
        if (scatterSet == null || ScatterSetWrapperKt.wrapIntoSet(scatterSet) == null) {
            SetsKt.emptySet();
        }
        if (persistentList.size() <= 0) {
            return;
        }
        MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(persistentList.get(0));
        throw null;
    }

    public static final void dispatchObserverOnDispose(Snapshot snapshot) {
        PersistentList persistentList = observers;
        if (persistentList == null || persistentList.size() <= 0) {
            return;
        }
        MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(persistentList.get(0));
        throw null;
    }

    public static final Pair mergeObservers(PersistentList persistentList, Snapshot snapshot, boolean z, Function1 function1, Function1 function12) {
        if (persistentList.size() <= 0) {
            return TuplesKt.to(new SnapshotInstanceObservers(function1, function12), null);
        }
        MediaControllerCompat$MediaControllerImplApi21$ExtraBinderRequestResultReceiver$$ExternalSyntheticThrowCCEIfNotNull0.m(persistentList.get(0));
        throw null;
    }
}
