package com.android.systemui.lifecycle;

import java.lang.StackWalker;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* JADX INFO: compiled from: RepeatWhenAttached.kt */
/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class RepeatWhenAttachedKt$inferTraceSectionName$interestingFrame$1$1 extends FunctionReferenceImpl implements Function1 {
    public static final RepeatWhenAttachedKt$inferTraceSectionName$interestingFrame$1$1 INSTANCE = new RepeatWhenAttachedKt$inferTraceSectionName$interestingFrame$1$1();

    RepeatWhenAttachedKt$inferTraceSectionName$interestingFrame$1$1() {
        super(1, RepeatWhenAttachedKt.class, "isFrameInteresting", "isFrameInteresting(Ljava/lang/StackWalker$StackFrame;)Z", 1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(StackWalker.StackFrame stackFrame) {
        stackFrame.getClass();
        return Boolean.valueOf(RepeatWhenAttachedKt.isFrameInteresting(stackFrame));
    }
}
