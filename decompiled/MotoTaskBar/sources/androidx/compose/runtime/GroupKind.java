package androidx.compose.runtime;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Composer.kt */
/* JADX INFO: loaded from: classes.dex */
abstract class GroupKind {
    public static final Companion Companion = new Companion(null);
    private static final int Group = m32constructorimpl(0);
    private static final int Node = m32constructorimpl(1);
    private static final int ReusableNode = m32constructorimpl(2);

    /* JADX INFO: compiled from: Composer.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getGroup-ULZAiWs, reason: not valid java name */
        public final int m33getGroupULZAiWs() {
            return GroupKind.Group;
        }

        /* JADX INFO: renamed from: getNode-ULZAiWs, reason: not valid java name */
        public final int m34getNodeULZAiWs() {
            return GroupKind.Node;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static int m32constructorimpl(int i) {
        return i;
    }
}
