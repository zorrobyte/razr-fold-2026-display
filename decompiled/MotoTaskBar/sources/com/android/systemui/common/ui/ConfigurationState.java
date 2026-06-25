package com.android.systemui.common.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.ConfigurationControllerExtKt;
import com.android.systemui.util.kotlin.FlowKt$emitOnStart$1;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* JADX INFO: compiled from: ConfigurationState.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ConfigurationState {
    private final ConfigurationController configurationController;
    private final Context context;
    private final LayoutInflater layoutInflater;

    public ConfigurationState(ConfigurationController configurationController, Context context, LayoutInflater layoutInflater) {
        configurationController.getClass();
        context.getClass();
        layoutInflater.getClass();
        this.configurationController = configurationController;
        this.context = context;
        this.layoutInflater = layoutInflater;
    }

    public final Flow inflateLayout(final int i, final ViewGroup viewGroup, final boolean z) {
        final Flow flowOnStart = FlowKt.onStart(FlowKt.merge(ConfigurationControllerExtKt.getOnThemeChanged(this.configurationController), ConfigurationControllerExtKt.getOnDensityOrFontScaleChanged(this.configurationController)), new FlowKt$emitOnStart$1(null));
        return new Flow() { // from class: com.android.systemui.common.ui.ConfigurationState$inflateLayout$$inlined$map$1

            /* JADX INFO: renamed from: com.android.systemui.common.ui.ConfigurationState$inflateLayout$$inlined$map$1$2, reason: invalid class name */
            /* JADX INFO: compiled from: Emitters.kt */
            public final class AnonymousClass2 implements FlowCollector {
                final /* synthetic */ boolean $attachToRoot$inlined;
                final /* synthetic */ int $id$inlined;
                final /* synthetic */ ViewGroup $root$inlined;
                final /* synthetic */ FlowCollector $this_unsafeFlow;
                final /* synthetic */ ConfigurationState this$0;

                /* JADX INFO: renamed from: com.android.systemui.common.ui.ConfigurationState$inflateLayout$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, ConfigurationState configurationState, int i, ViewGroup viewGroup, boolean z) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = configurationState;
                    this.$id$inlined = i;
                    this.$root$inlined = viewGroup;
                    this.$attachToRoot$inlined = z;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct add '--show-bad-code' argument
                */
                public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                    /*
                        r5 = this;
                        boolean r0 = r7 instanceof com.android.systemui.common.ui.ConfigurationState$inflateLayout$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r7
                        com.android.systemui.common.ui.ConfigurationState$inflateLayout$$inlined$map$1$2$1 r0 = (com.android.systemui.common.ui.ConfigurationState$inflateLayout$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.common.ui.ConfigurationState$inflateLayout$$inlined$map$1$2$1 r0 = new com.android.systemui.common.ui.ConfigurationState$inflateLayout$$inlined$map$1$2$1
                        r0.<init>(r7)
                    L18:
                        java.lang.Object r7 = r0.result
                        java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L31
                        if (r2 != r3) goto L29
                        kotlin.ResultKt.throwOnFailure(r7)
                        goto L54
                    L29:
                        java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                        java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                        r5.<init>(r6)
                        throw r5
                    L31:
                        kotlin.ResultKt.throwOnFailure(r7)
                        kotlinx.coroutines.flow.FlowCollector r7 = r5.$this_unsafeFlow
                        kotlin.Unit r6 = (kotlin.Unit) r6
                        com.android.systemui.common.ui.ConfigurationState r6 = r5.this$0
                        android.view.LayoutInflater r6 = com.android.systemui.common.ui.ConfigurationState.access$getLayoutInflater$p(r6)
                        int r2 = r5.$id$inlined
                        android.view.ViewGroup r4 = r5.$root$inlined
                        boolean r5 = r5.$attachToRoot$inlined
                        android.view.View r5 = r6.inflate(r2, r4, r5)
                        r5.getClass()
                        r0.label = r3
                        java.lang.Object r5 = r7.emit(r5, r0)
                        if (r5 != r1) goto L54
                        return r1
                    L54:
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        return r5
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.common.ui.ConfigurationState$inflateLayout$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowOnStart.collect(new AnonymousClass2(flowCollector, this, i, viewGroup, z), continuation);
                return objCollect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
