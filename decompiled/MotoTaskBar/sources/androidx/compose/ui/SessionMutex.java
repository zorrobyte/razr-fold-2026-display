package androidx.compose.ui;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: SessionMutex.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SessionMutex {
    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static AtomicReference m103constructorimpl() {
        return m104constructorimpl(new AtomicReference(null));
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static AtomicReference m104constructorimpl(AtomicReference atomicReference) {
        return atomicReference;
    }

    /* JADX INFO: renamed from: getCurrentSession-impl, reason: not valid java name */
    public static final Object m105getCurrentSessionimpl(AtomicReference atomicReference) {
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(atomicReference.get());
        return null;
    }
}
