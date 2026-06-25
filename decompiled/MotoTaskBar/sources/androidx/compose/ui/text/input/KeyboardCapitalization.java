package androidx.compose.ui.text.input;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: KeyboardCapitalization.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class KeyboardCapitalization {
    public static final Companion Companion = new Companion(null);
    private static final int Unspecified = m852constructorimpl(-1);
    private static final int None = m852constructorimpl(0);
    private static final int Characters = m852constructorimpl(1);
    private static final int Words = m852constructorimpl(2);
    private static final int Sentences = m852constructorimpl(3);

    /* JADX INFO: compiled from: KeyboardCapitalization.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getCharacters-IUNYP9k, reason: not valid java name */
        public final int m856getCharactersIUNYP9k() {
            return KeyboardCapitalization.Characters;
        }

        /* JADX INFO: renamed from: getNone-IUNYP9k, reason: not valid java name */
        public final int m857getNoneIUNYP9k() {
            return KeyboardCapitalization.None;
        }

        /* JADX INFO: renamed from: getSentences-IUNYP9k, reason: not valid java name */
        public final int m858getSentencesIUNYP9k() {
            return KeyboardCapitalization.Sentences;
        }

        /* JADX INFO: renamed from: getWords-IUNYP9k, reason: not valid java name */
        public final int m859getWordsIUNYP9k() {
            return KeyboardCapitalization.Words;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static int m852constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m853equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m854hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m855toStringimpl(int i) {
        return m853equalsimpl0(i, Unspecified) ? "Unspecified" : m853equalsimpl0(i, None) ? "None" : m853equalsimpl0(i, Characters) ? "Characters" : m853equalsimpl0(i, Words) ? "Words" : m853equalsimpl0(i, Sentences) ? "Sentences" : "Invalid";
    }
}
