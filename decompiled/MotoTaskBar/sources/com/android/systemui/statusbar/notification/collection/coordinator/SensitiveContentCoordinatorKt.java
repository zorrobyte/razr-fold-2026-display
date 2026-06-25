package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.ListEntry;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: SensitiveContentCoordinator.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SensitiveContentCoordinatorKt {

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$1, reason: invalid class name */
    /* JADX INFO: compiled from: SensitiveContentCoordinator.kt */
    final /* synthetic */ class AnonymousClass1 extends FunctionReferenceImpl implements Function1 {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(1, SensitiveContentCoordinatorKt.class, "extractAllRepresentativeEntries", "extractAllRepresentativeEntries(Lcom/android/systemui/statusbar/notification/collection/ListEntry;)Lkotlin/sequences/Sequence;", 1);
        }

        @Override // kotlin.jvm.functions.Function1
        public final Sequence invoke(ListEntry listEntry) {
            listEntry.getClass();
            return SensitiveContentCoordinatorKt.extractAllRepresentativeEntries(listEntry);
        }
    }

    /* JADX INFO: renamed from: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt$extractAllRepresentativeEntries$2, reason: invalid class name */
    /* JADX INFO: compiled from: SensitiveContentCoordinator.kt */
    final class AnonymousClass2 extends RestrictedSuspendLambda implements Function2 {
        final /* synthetic */ ListEntry $listEntry;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(ListEntry listEntry, Continuation continuation) {
            super(2, continuation);
            this.$listEntry = listEntry;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.$listEntry, continuation);
            anonymousClass2.L$0 = obj;
            return anonymousClass2;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(SequenceScope sequenceScope, Continuation continuation) {
            return ((AnonymousClass2) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0059, code lost:
        
            if (r1.yieldAll(r5, r4) == r0) goto L19;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public final java.lang.Object invokeSuspend(java.lang.Object r5) {
            /*
                r4 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r4.label
                r2 = 2
                r3 = 1
                if (r1 == 0) goto L22
                if (r1 == r3) goto L1a
                if (r1 != r2) goto L12
                kotlin.ResultKt.throwOnFailure(r5)
                goto L5c
            L12:
                java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                r4.<init>(r5)
                throw r4
            L1a:
                java.lang.Object r1 = r4.L$0
                kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
                kotlin.ResultKt.throwOnFailure(r5)
                goto L3d
            L22:
                kotlin.ResultKt.throwOnFailure(r5)
                java.lang.Object r5 = r4.L$0
                r1 = r5
                kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
                com.android.systemui.statusbar.notification.collection.ListEntry r5 = r4.$listEntry
                com.android.systemui.statusbar.notification.collection.NotificationEntry r5 = r5.getRepresentativeEntry()
                if (r5 == 0) goto L3d
                r4.L$0 = r1
                r4.label = r3
                java.lang.Object r5 = r1.yield(r5, r4)
                if (r5 != r0) goto L3d
                goto L5b
            L3d:
                com.android.systemui.statusbar.notification.collection.ListEntry r5 = r4.$listEntry
                boolean r3 = r5 instanceof com.android.systemui.statusbar.notification.collection.GroupEntry
                if (r3 == 0) goto L5c
                com.android.systemui.statusbar.notification.collection.GroupEntry r5 = (com.android.systemui.statusbar.notification.collection.GroupEntry) r5
                java.util.List r5 = r5.getChildren()
                r5.getClass()
                kotlin.sequences.Sequence r5 = com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt.access$extractAllRepresentativeEntries(r5)
                r3 = 0
                r4.L$0 = r3
                r4.label = r2
                java.lang.Object r4 = r1.yieldAll(r5, r4)
                if (r4 != r0) goto L5c
            L5b:
                return r0
            L5c:
                kotlin.Unit r4 = kotlin.Unit.INSTANCE
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorKt.AnonymousClass2.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence extractAllRepresentativeEntries(ListEntry listEntry) {
        return SequencesKt.sequence(new AnonymousClass2(listEntry, null));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Sequence extractAllRepresentativeEntries(List list) {
        return SequencesKt.flatMap(CollectionsKt.asSequence(list), AnonymousClass1.INSTANCE);
    }
}
