package androidx.compose.ui.text.input;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: KeyboardCapitalization.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class KeyboardCapitalization {
    public static final Companion Companion = new Companion(null);
    private static final int Unspecified = m1671constructorimpl(-1);
    private static final int None = m1671constructorimpl(0);
    private static final int Characters = m1671constructorimpl(1);
    private static final int Words = m1671constructorimpl(2);
    private static final int Sentences = m1671constructorimpl(3);

    /* JADX INFO: compiled from: KeyboardCapitalization.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: renamed from: getCharacters-IUNYP9k, reason: not valid java name */
        public final int m1675getCharactersIUNYP9k() {
            return KeyboardCapitalization.Characters;
        }

        /* JADX INFO: renamed from: getNone-IUNYP9k, reason: not valid java name */
        public final int m1676getNoneIUNYP9k() {
            return KeyboardCapitalization.None;
        }

        /* JADX INFO: renamed from: getSentences-IUNYP9k, reason: not valid java name */
        public final int m1677getSentencesIUNYP9k() {
            return KeyboardCapitalization.Sentences;
        }

        /* JADX INFO: renamed from: getWords-IUNYP9k, reason: not valid java name */
        public final int m1678getWordsIUNYP9k() {
            return KeyboardCapitalization.Words;
        }
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    private static int m1671constructorimpl(int i) {
        return i;
    }

    /* JADX INFO: renamed from: equals-impl0, reason: not valid java name */
    public static final boolean m1672equalsimpl0(int i, int i2) {
        return i == i2;
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m1673hashCodeimpl(int i) {
        return Integer.hashCode(i);
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m1674toStringimpl(int i) {
        return m1672equalsimpl0(i, Unspecified) ? "Unspecified" : m1672equalsimpl0(i, None) ? "None" : m1672equalsimpl0(i, Characters) ? "Characters" : m1672equalsimpl0(i, Words) ? "Words" : m1672equalsimpl0(i, Sentences) ? "Sentences" : "Invalid";
    }
}
