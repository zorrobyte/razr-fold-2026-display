package com.motorola.plugin.core.channel;

import com.motorola.plugin.core.Level;
import com.motorola.plugin.core.PluginConfigKt;
import kotlin.coroutines.AbstractCoroutineContextElement;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.CoroutineExceptionHandler;

/* JADX INFO: compiled from: CoroutineExceptionHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class AbstractCommonChannelImpl$optimisticBind$$inlined$CoroutineExceptionHandler$1 extends AbstractCoroutineContextElement implements CoroutineExceptionHandler {
    final /* synthetic */ AbstractCommonChannelImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbstractCommonChannelImpl$optimisticBind$$inlined$CoroutineExceptionHandler$1(CoroutineExceptionHandler.Key key, AbstractCommonChannelImpl abstractCommonChannelImpl) {
        super(key);
        this.this$0 = abstractCommonChannelImpl;
    }

    @Override // kotlinx.coroutines.CoroutineExceptionHandler
    public void handleException(CoroutineContext coroutineContext, final Throwable th) {
        String str = this.this$0.logTag;
        Level level = Level.ERROR;
        final AbstractCommonChannelImpl abstractCommonChannelImpl = this.this$0;
        PluginConfigKt.trace$default(str, level, false, null, false, null, new Function0() { // from class: com.motorola.plugin.core.channel.AbstractCommonChannelImpl$optimisticBind$errorHandler$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return '[' + abstractCommonChannelImpl.getToken() + "] Optimistic bind retry error " + th;
            }
        }, 60, null);
    }
}
