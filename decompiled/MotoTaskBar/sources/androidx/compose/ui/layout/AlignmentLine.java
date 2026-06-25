package androidx.compose.ui.layout;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: AlignmentLine.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class AlignmentLine {
    public static final Companion Companion = new Companion(null);
    private final Function2 merger;

    /* JADX INFO: compiled from: AlignmentLine.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    private AlignmentLine(Function2 function2) {
        this.merger = function2;
    }

    public /* synthetic */ AlignmentLine(Function2 function2, DefaultConstructorMarker defaultConstructorMarker) {
        this(function2);
    }

    public final Function2 getMerger$ui_release() {
        return this.merger;
    }
}
