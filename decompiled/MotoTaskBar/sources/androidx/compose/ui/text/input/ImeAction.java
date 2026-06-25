package androidx.compose.ui.text.input;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: ImeAction.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ImeAction {
    private final int value;
    public static final Companion Companion = new Companion(null);
    private static final int Unspecified = m834constructorimpl(-1);
    private static final int Default = m834constructorimpl(1);
    private static final int None = m834constructorimpl(0);
    private static final int Go = m834constructorimpl(2);
    private static final int Search = m834constructorimpl(3);
    private static final int Send = m834constructorimpl(4);
    private static final int Previous = m834constructorimpl(5);
    private static final int Next = m834constructorimpl(6);
    private static final int Done = m834constructorimpl(7);

    /* JADX INFO: compiled from: ImeAction.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getDefault-eUduSuo, reason: not valid java name */
        public final int m840getDefaulteUduSuo() {
            return ImeAction.Default;
        }

        /* JADX INFO: renamed from: getDone-eUduSuo, reason: not valid java name */
        public final int m841getDoneeUduSuo() {
            return ImeAction.Done;
        }

        /* JADX INFO: renamed from: getGo-eUduSuo, reason: not valid java name */
        public final int m842getGoeUduSuo() {
            return ImeAction.Go;
        }

        /* JADX INFO: renamed from: getNext-eUduSuo, reason: not valid java name */
        public final int m843getNexteUduSuo() {
            return ImeAction.Next;
        }

        /* JADX INFO: renamed from: getNone-eUduSuo, reason: not valid java name */
        public final int m844getNoneeUduSuo() {
            return ImeAction.None;
        }

        /* JADX INFO: renamed from: getPrevious-eUduSuo, reason: not valid java name */
        public final int m845getPreviouseUduSuo() {
            return ImeAction.Previous;
        }

        /* JADX INFO: renamed from: getSearch-eUduSuo, reason: not valid java name */
        public final int m846getSearcheUduSuo() {
            return ImeAction.Search;
        }

        /* JADX INFO: renamed from: getSend-eUduSuo, reason: not valid java name */
        public final int m847getSendeUduSuo() {
            return ImeAction.Send;
        }
    }

    private /* synthetic */ ImeAction(int i) {
        this.value = i;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ImeAction m833boximpl(int i) {
        return new ImeAction(i);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static int m834constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m835equalsimpl(int i, Object obj) {
        return (obj instanceof ImeAction) && i == ((ImeAction) obj).m839unboximpl();
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m836equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m837hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m838toStringimpl(int i) {
        return m836equalsimpl0(i, Unspecified) ? "Unspecified" : m836equalsimpl0(i, None) ? "None" : m836equalsimpl0(i, Default) ? "Default" : m836equalsimpl0(i, Go) ? "Go" : m836equalsimpl0(i, Search) ? "Search" : m836equalsimpl0(i, Send) ? "Send" : m836equalsimpl0(i, Previous) ? "Previous" : m836equalsimpl0(i, Next) ? "Next" : m836equalsimpl0(i, Done) ? "Done" : "Invalid";
    }

    public boolean equals(Object obj) {
        return m835equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m837hashCodeimpl(this.value);
    }

    public String toString() {
        return m838toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ int m839unboximpl() {
        return this.value;
    }
}
