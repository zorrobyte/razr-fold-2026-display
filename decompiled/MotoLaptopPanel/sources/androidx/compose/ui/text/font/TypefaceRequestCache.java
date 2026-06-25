package androidx.compose.ui.text.font;

import androidx.collection.LruCache;
import androidx.compose.runtime.State;
import androidx.compose.ui.text.platform.SynchronizedObject;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: FontFamilyResolver.kt */
/* JADX INFO: loaded from: classes.dex */
public final class TypefaceRequestCache {
    private final SynchronizedObject lock = new SynchronizedObject();
    private final LruCache resultCache = new LruCache(16);

    public final SynchronizedObject getLock$ui_text_release() {
        return this.lock;
    }

    public final State runCached(final TypefaceRequest typefaceRequest, Function1 function1) {
        synchronized (this.lock) {
            TypefaceResult typefaceResult = (TypefaceResult) this.resultCache.get(typefaceRequest);
            if (typefaceResult != null) {
                if (typefaceResult.getCacheable()) {
                    return typefaceResult;
                }
            }
            try {
                TypefaceResult typefaceResult2 = (TypefaceResult) function1.invoke(new Function1() { // from class: androidx.compose.ui.text.font.TypefaceRequestCache$runCached$currentTypefaceResult$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                        invoke((TypefaceResult) obj);
                        return Unit.INSTANCE;
                    }

                    public final void invoke(TypefaceResult typefaceResult3) {
                        SynchronizedObject lock$ui_text_release = this.this$0.getLock$ui_text_release();
                        TypefaceRequestCache typefaceRequestCache = this.this$0;
                        TypefaceRequest typefaceRequest2 = typefaceRequest;
                        synchronized (lock$ui_text_release) {
                            try {
                                if (typefaceResult3.getCacheable()) {
                                    typefaceRequestCache.resultCache.put(typefaceRequest2, typefaceResult3);
                                } else {
                                    typefaceRequestCache.resultCache.remove(typefaceRequest2);
                                }
                                Unit unit = Unit.INSTANCE;
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
                synchronized (this.lock) {
                    try {
                        if (this.resultCache.get(typefaceRequest) == null && typefaceResult2.getCacheable()) {
                            this.resultCache.put(typefaceRequest, typefaceResult2);
                        }
                        Unit unit = Unit.INSTANCE;
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return typefaceResult2;
            } catch (Exception e) {
                throw new IllegalStateException("Could not load font", e);
            }
        }
    }
}
