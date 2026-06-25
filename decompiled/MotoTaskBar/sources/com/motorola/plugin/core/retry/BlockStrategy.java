package com.motorola.plugin.core.retry;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.DelayKt;

/* JADX INFO: compiled from: BlockStrategy.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface BlockStrategy {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: BlockStrategy.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final BlockStrategy coroutineDelayStrategy() {
            return new BlockStrategy() { // from class: com.motorola.plugin.core.retry.BlockStrategy$Companion$coroutineDelayStrategy$1
                @Override // com.motorola.plugin.core.retry.BlockStrategy
                public Object block(long j, Continuation continuation) {
                    Object objDelay = DelayKt.delay(j, continuation);
                    return objDelay == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objDelay : Unit.INSTANCE;
                }
            };
        }
    }

    Object block(long j, Continuation continuation);
}
