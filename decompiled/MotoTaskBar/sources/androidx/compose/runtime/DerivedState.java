package androidx.compose.runtime;

import androidx.collection.ObjectIntMap;

/* JADX INFO: compiled from: DerivedState.kt */
/* JADX INFO: loaded from: classes.dex */
public interface DerivedState extends State {

    /* JADX INFO: compiled from: DerivedState.kt */
    public interface Record {
        Object getCurrentValue();

        ObjectIntMap getDependencies();
    }

    Record getCurrentRecord();

    SnapshotMutationPolicy getPolicy();
}
