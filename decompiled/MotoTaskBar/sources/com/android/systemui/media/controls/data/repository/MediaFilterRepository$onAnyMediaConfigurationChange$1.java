package com.android.systemui.media.controls.data.repository;

import android.content.Context;
import com.android.systemui.statusbar.policy.ConfigurationController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* JADX INFO: compiled from: MediaFilterRepository.kt */
/* JADX INFO: loaded from: classes.dex */
final class MediaFilterRepository$onAnyMediaConfigurationChange$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $applicationContext;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ MediaFilterRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    MediaFilterRepository$onAnyMediaConfigurationChange$1(MediaFilterRepository mediaFilterRepository, Context context, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mediaFilterRepository;
        this.$applicationContext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(MediaFilterRepository mediaFilterRepository, MediaFilterRepository$onAnyMediaConfigurationChange$1$callback$1 mediaFilterRepository$onAnyMediaConfigurationChange$1$callback$1) {
        mediaFilterRepository.configurationController.removeCallback(mediaFilterRepository$onAnyMediaConfigurationChange$1$callback$1);
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaFilterRepository$onAnyMediaConfigurationChange$1 mediaFilterRepository$onAnyMediaConfigurationChange$1 = new MediaFilterRepository$onAnyMediaConfigurationChange$1(this.this$0, this.$applicationContext, continuation);
        mediaFilterRepository$onAnyMediaConfigurationChange$1.L$0 = obj;
        return mediaFilterRepository$onAnyMediaConfigurationChange$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((MediaFilterRepository$onAnyMediaConfigurationChange$1) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.media.controls.data.repository.MediaFilterRepository$onAnyMediaConfigurationChange$1$callback$1, java.lang.Object] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final MediaFilterRepository mediaFilterRepository = this.this$0;
            final Context context = this.$applicationContext;
            final ?? r1 = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$onAnyMediaConfigurationChange$1$callback$1
                @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
                public void onDensityOrFontScaleChanged() {
                    producerScope.mo2736trySendJP2dKIU(Unit.INSTANCE);
                }

                @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
                public void onLocaleListChanged() {
                    if (Intrinsics.areEqual(mediaFilterRepository.locale, context.getResources().getConfiguration().getLocales().get(0))) {
                        return;
                    }
                    mediaFilterRepository.locale = context.getResources().getConfiguration().getLocales().get(0);
                    producerScope.mo2736trySendJP2dKIU(Unit.INSTANCE);
                }

                @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
                public void onThemeChanged() {
                    producerScope.mo2736trySendJP2dKIU(Unit.INSTANCE);
                }

                @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
                public void onUiModeChanged() {
                    producerScope.mo2736trySendJP2dKIU(Unit.INSTANCE);
                }
            };
            this.this$0.configurationController.addCallback(r1);
            producerScope.mo2736trySendJP2dKIU(Unit.INSTANCE);
            final MediaFilterRepository mediaFilterRepository2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.controls.data.repository.MediaFilterRepository$onAnyMediaConfigurationChange$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return MediaFilterRepository$onAnyMediaConfigurationChange$1.invokeSuspend$lambda$0(mediaFilterRepository2, r1);
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
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
