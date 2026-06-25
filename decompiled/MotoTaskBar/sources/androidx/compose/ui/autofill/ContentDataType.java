package androidx.compose.ui.autofill;

/* JADX INFO: compiled from: ContentDataType.android.kt */
/* JADX INFO: loaded from: classes.dex */
public interface ContentDataType {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: ContentDataType.android.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final ContentDataType None = ContentDataType_androidKt.ContentDataType(0);
        private static final ContentDataType Text = ContentDataType_androidKt.ContentDataType(1);
        private static final ContentDataType List = ContentDataType_androidKt.ContentDataType(3);
        private static final ContentDataType Date = ContentDataType_androidKt.ContentDataType(4);
        private static final ContentDataType Toggle = ContentDataType_androidKt.ContentDataType(2);

        private Companion() {
        }

        public final ContentDataType getText() {
            return Text;
        }
    }
}
