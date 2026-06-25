package androidx.compose.ui.focus;

import androidx.compose.runtime.collection.MutableVector;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: FocusRequester.kt */
/* JADX INFO: loaded from: classes.dex */
public final class FocusRequester {
    private final MutableVector focusRequesterNodes = new MutableVector(new FocusRequesterModifierNode[16], 0);
    public static final Companion Companion = new Companion(null);
    private static final FocusRequester Default = new FocusRequester();
    private static final FocusRequester Cancel = new FocusRequester();
    private static final FocusRequester Redirect = new FocusRequester();

    /* JADX INFO: compiled from: FocusRequester.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final FocusRequester getCancel() {
            return FocusRequester.Cancel;
        }

        public final FocusRequester getDefault() {
            return FocusRequester.Default;
        }

        public final FocusRequester getRedirect$ui_release() {
            return FocusRequester.Redirect;
        }
    }

    /* JADX INFO: renamed from: requestFocus-3ESFkO8$default, reason: not valid java name */
    public static /* synthetic */ boolean m712requestFocus3ESFkO8$default(FocusRequester focusRequester, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = FocusDirection.Companion.m692getEnterdhqQ8s();
        }
        return focusRequester.m713requestFocus3ESFkO8(i);
    }

    /* JADX WARN: Code restructure failed: missing block: B:78:0x005c, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean findFocusTargetNode$ui_release(kotlin.jvm.functions.Function1 r15) {
        /*
            Method dump skipped, instruction units count: 281
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.focus.FocusRequester.findFocusTargetNode$ui_release(kotlin.jvm.functions.Function1):boolean");
    }

    /* JADX INFO: renamed from: requestFocus-3ESFkO8, reason: not valid java name */
    public final boolean m713requestFocus3ESFkO8(final int i) {
        return findFocusTargetNode$ui_release(new Function1() { // from class: androidx.compose.ui.focus.FocusRequester$requestFocus$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(FocusTargetNode focusTargetNode) {
                return Boolean.valueOf(focusTargetNode.mo715requestFocus3ESFkO8(i));
            }
        });
    }
}
