package com.motorola.laptoppanel.service;

import java.util.Set;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.MutableSharedFlow;

/* JADX INFO: compiled from: PanelAppsManager.kt */
/* JADX INFO: loaded from: classes.dex */
final class PanelAppsManager$registerSettingsObserver$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Set $appsDiff;
    int label;
    final /* synthetic */ PanelAppsManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    PanelAppsManager$registerSettingsObserver$2$1(PanelAppsManager panelAppsManager, Set set, Continuation continuation) {
        super(2, continuation);
        this.this$0 = panelAppsManager;
        this.$appsDiff = set;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new PanelAppsManager$registerSettingsObserver$2$1(this.this$0, this.$appsDiff, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((PanelAppsManager$registerSettingsObserver$2$1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MutableSharedFlow mutableSharedFlow = this.this$0._disabledAppsChanged;
            Set set = this.$appsDiff;
            this.label = 1;
            if (mutableSharedFlow.emit(set, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
