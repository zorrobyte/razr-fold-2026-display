package androidx.compose.runtime;

import androidx.compose.runtime.tooling.CompositionErrorContextImpl;
import kotlin.Unit;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CompletableJob;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobKt;

/* JADX INFO: compiled from: Effects.kt */
/* JADX INFO: loaded from: classes.dex */
public final class RememberedCoroutineScope implements CoroutineScope, RememberObserver {
    private volatile CoroutineContext _coroutineContext;
    private final Object lock = this;
    private final CoroutineContext overlayContext;
    private final CoroutineContext parentContext;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    public static final CoroutineContext CancelledCoroutineContext = new CancelledCoroutineContext();

    /* JADX INFO: compiled from: Effects.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public RememberedCoroutineScope(CoroutineContext coroutineContext, CoroutineContext coroutineContext2) {
        this.parentContext = coroutineContext;
        this.overlayContext = coroutineContext2;
    }

    public final void cancelIfCreated() {
        synchronized (this.lock) {
            try {
                CoroutineContext coroutineContext = this._coroutineContext;
                if (coroutineContext == null) {
                    this._coroutineContext = CancelledCoroutineContext;
                } else {
                    JobKt.cancel(coroutineContext, new ForgottenCoroutineScopeException());
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // kotlinx.coroutines.CoroutineScope
    public CoroutineContext getCoroutineContext() {
        CoroutineContext coroutineContextPlus;
        CoroutineContext coroutineContext = this._coroutineContext;
        if (coroutineContext == null || coroutineContext == CancelledCoroutineContext) {
            CompositionErrorContextImpl compositionErrorContextImpl = (CompositionErrorContextImpl) this.parentContext.get(CompositionErrorContextImpl.Key);
            CoroutineContext rememberedCoroutineScope$special$$inlined$CoroutineExceptionHandler$1 = compositionErrorContextImpl != null ? new RememberedCoroutineScope$special$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.Key, compositionErrorContextImpl, this) : EmptyCoroutineContext.INSTANCE;
            synchronized (this.lock) {
                try {
                    coroutineContextPlus = this._coroutineContext;
                    if (coroutineContextPlus == null) {
                        CoroutineContext coroutineContext2 = this.parentContext;
                        coroutineContextPlus = coroutineContext2.plus(JobKt.Job((Job) coroutineContext2.get(Job.Key))).plus(this.overlayContext).plus(rememberedCoroutineScope$special$$inlined$CoroutineExceptionHandler$1);
                    } else if (coroutineContextPlus == CancelledCoroutineContext) {
                        CoroutineContext coroutineContext3 = this.parentContext;
                        CompletableJob completableJobJob = JobKt.Job((Job) coroutineContext3.get(Job.Key));
                        completableJobJob.cancel(new ForgottenCoroutineScopeException());
                        coroutineContextPlus = coroutineContext3.plus(completableJobJob).plus(this.overlayContext).plus(rememberedCoroutineScope$special$$inlined$CoroutineExceptionHandler$1);
                    }
                    this._coroutineContext = coroutineContextPlus;
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
            coroutineContext = coroutineContextPlus;
        }
        coroutineContext.getClass();
        return coroutineContext;
    }

    @Override // androidx.compose.runtime.RememberObserver
    public void onAbandoned() {
        cancelIfCreated();
    }

    @Override // androidx.compose.runtime.RememberObserver
    public void onForgotten() {
        cancelIfCreated();
    }

    @Override // androidx.compose.runtime.RememberObserver
    public void onRemembered() {
    }
}
