package com.motorola.plugin.core.container;

import com.motorola.plugin.core.discovery.DiscoverInfo;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* JADX INFO: compiled from: PluginInstanceContainerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
final class PluginInstanceContainerImpl$createPluginInstance$2$pluginInstance$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ DiscoverInfo $discoverInfo;
    final /* synthetic */ Class $prototypePluginClass;
    int label;
    final /* synthetic */ PluginInstanceContainerImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PluginInstanceContainerImpl$createPluginInstance$2$pluginInstance$1(PluginInstanceContainerImpl pluginInstanceContainerImpl, DiscoverInfo discoverInfo, Class cls, Continuation continuation) {
        super(2, continuation);
        this.this$0 = pluginInstanceContainerImpl;
        this.$discoverInfo = discoverInfo;
        this.$prototypePluginClass = cls;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PluginInstanceContainerImpl$createPluginInstance$2$pluginInstance$1(this.this$0, this.$discoverInfo, this.$prototypePluginClass, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((PluginInstanceContainerImpl$createPluginInstance$2$pluginInstance$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return this.this$0.loadPluginSafely(this.$discoverInfo, this.$prototypePluginClass);
    }
}
