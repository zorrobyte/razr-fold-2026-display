package androidx.compose.ui.focus;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: FocusProperties.kt */
/* JADX INFO: loaded from: classes.dex */
public interface FocusProperties {
    boolean getCanFocus();

    default FocusRequester getDown() {
        return FocusRequester.Companion.getDefault();
    }

    default FocusRequester getEnd() {
        return FocusRequester.Companion.getDefault();
    }

    default FocusRequester getLeft() {
        return FocusRequester.Companion.getDefault();
    }

    default FocusRequester getNext() {
        return FocusRequester.Companion.getDefault();
    }

    default Function1 getOnEnter() {
        return new Function1() { // from class: androidx.compose.ui.focus.FocusProperties$onEnter$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((FocusEnterExitScope) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(FocusEnterExitScope focusEnterExitScope) {
            }
        };
    }

    default Function1 getOnExit() {
        return new Function1() { // from class: androidx.compose.ui.focus.FocusProperties$onExit$1
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Object invoke(Object obj) {
                invoke((FocusEnterExitScope) obj);
                return Unit.INSTANCE;
            }

            public final void invoke(FocusEnterExitScope focusEnterExitScope) {
            }
        };
    }

    default FocusRequester getPrevious() {
        return FocusRequester.Companion.getDefault();
    }

    default FocusRequester getRight() {
        return FocusRequester.Companion.getDefault();
    }

    default FocusRequester getStart() {
        return FocusRequester.Companion.getDefault();
    }

    default FocusRequester getUp() {
        return FocusRequester.Companion.getDefault();
    }
}
