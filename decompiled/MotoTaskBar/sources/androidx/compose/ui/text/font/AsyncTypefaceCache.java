package androidx.compose.ui.text.font;

import androidx.collection.LruCache;
import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.ui.text.platform.SynchronizedObject;

/* JADX INFO: compiled from: FontListFontFamilyTypefaceAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AsyncTypefaceCache {
    private final Object PermanentFailure = AsyncTypefaceResult.m822constructorimpl(null);
    private final LruCache resultCache = new LruCache(16);
    private final MutableScatterMap permanentCache = ScatterMapKt.mutableScatterMapOf();
    private final SynchronizedObject cacheLock = new SynchronizedObject();

    /* JADX INFO: compiled from: FontListFontFamilyTypefaceAdapter.kt */
    public abstract class AsyncTypefaceResult {
        /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
        public static Object m822constructorimpl(Object obj) {
            return obj;
        }
    }
}
