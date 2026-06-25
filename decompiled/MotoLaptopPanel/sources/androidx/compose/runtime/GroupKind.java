package androidx.compose.runtime;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: Composer.kt */
/* JADX INFO: loaded from: classes.dex */
abstract class GroupKind {
    public static final Companion Companion = new Companion(null);
    private static final int Group = m587constructorimpl(0);
    private static final int Node = m587constructorimpl(1);
    private static final int ReusableNode = m587constructorimpl(2);

    /* JADX INFO: compiled from: Composer.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getGroup-ULZAiWs, reason: not valid java name */
        public final int m588getGroupULZAiWs() {
            return GroupKind.Group;
        }

        /* JADX INFO: renamed from: getNode-ULZAiWs, reason: not valid java name */
        public final int m589getNodeULZAiWs() {
            return GroupKind.Node;
        }

        /* JADX INFO: renamed from: getReusableNode-ULZAiWs, reason: not valid java name */
        public final int m590getReusableNodeULZAiWs() {
            return GroupKind.ReusableNode;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static int m587constructorimpl(int i) {
        return i;
    }
}
