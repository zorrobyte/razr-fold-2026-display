package kotlin.text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.RangesKt;

/* JADX INFO: compiled from: Regex.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class Regex implements Serializable {
    public static final Companion Companion = new Companion(null);
    private Set _options;
    private final Pattern nativePattern;

    /* JADX INFO: compiled from: Regex.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: Regex.kt */
    final class Serialized implements Serializable {
        public static final Companion Companion = new Companion(null);
        private static final long serialVersionUID = 0;
        private final int flags;
        private final String pattern;

        /* JADX INFO: compiled from: Regex.kt */
        public final class Companion {
            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public Serialized(String str, int i) {
            str.getClass();
            this.pattern = str;
            this.flags = i;
        }

        private final Object readResolve() {
            Pattern patternCompile = Pattern.compile(this.pattern, this.flags);
            patternCompile.getClass();
            return new Regex(patternCompile);
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Regex(String str) {
        str.getClass();
        Pattern patternCompile = Pattern.compile(str);
        patternCompile.getClass();
        this(patternCompile);
    }

    public Regex(Pattern pattern) {
        pattern.getClass();
        this.nativePattern = pattern;
    }

    private final Object writeReplace() {
        String strPattern = this.nativePattern.pattern();
        strPattern.getClass();
        return new Serialized(strPattern, this.nativePattern.flags());
    }

    public final List split(CharSequence charSequence, int i) {
        charSequence.getClass();
        StringsKt__StringsKt.requireNonNegativeLimit(i);
        Matcher matcher = this.nativePattern.matcher(charSequence);
        if (i == 1 || !matcher.find()) {
            return CollectionsKt.listOf(charSequence.toString());
        }
        ArrayList arrayList = new ArrayList(i > 0 ? RangesKt.coerceAtMost(i, 10) : 10);
        int i2 = i - 1;
        int iEnd = 0;
        do {
            arrayList.add(charSequence.subSequence(iEnd, matcher.start()).toString());
            iEnd = matcher.end();
            if (i2 >= 0 && arrayList.size() == i2) {
                break;
            }
        } while (matcher.find());
        arrayList.add(charSequence.subSequence(iEnd, charSequence.length()).toString());
        return arrayList;
    }

    public String toString() {
        String string = this.nativePattern.toString();
        string.getClass();
        return string;
    }
}
