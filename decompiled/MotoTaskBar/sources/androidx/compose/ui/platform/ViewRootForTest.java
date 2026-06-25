package androidx.compose.ui.platform;

import androidx.compose.ui.node.RootForTest;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: ViewRootForTest.android.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ViewRootForTest extends RootForTest {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: ViewRootForTest.android.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static Function1 onViewCreatedCallback;

        private Companion() {
        }

        public final Function1 getOnViewCreatedCallback() {
            return onViewCreatedCallback;
        }
    }
}
