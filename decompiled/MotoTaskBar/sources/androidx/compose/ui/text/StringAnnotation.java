package androidx.compose.ui.text;

import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: StringAnnotation.kt */
/* JADX INFO: loaded from: classes.dex */
public final class StringAnnotation {
    private final String value;

    private /* synthetic */ StringAnnotation(String str) {
        this.value = str;
    }

    /* JADX INFO: renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ StringAnnotation m801boximpl(String str) {
        return new StringAnnotation(str);
    }

    /* JADX INFO: renamed from: constructor-impl, reason: not valid java name */
    public static String m802constructorimpl(String str) {
        return str;
    }

    /* JADX INFO: renamed from: equals-impl, reason: not valid java name */
    public static boolean m803equalsimpl(String str, Object obj) {
        return (obj instanceof StringAnnotation) && Intrinsics.areEqual(str, ((StringAnnotation) obj).m806unboximpl());
    }

    /* JADX INFO: renamed from: hashCode-impl, reason: not valid java name */
    public static int m804hashCodeimpl(String str) {
        return str.hashCode();
    }

    /* JADX INFO: renamed from: toString-impl, reason: not valid java name */
    public static String m805toStringimpl(String str) {
        return "StringAnnotation(value=" + str + ')';
    }

    public boolean equals(Object obj) {
        return m803equalsimpl(this.value, obj);
    }

    public int hashCode() {
        return m804hashCodeimpl(this.value);
    }

    public String toString() {
        return m805toStringimpl(this.value);
    }

    /* JADX INFO: renamed from: unbox-impl, reason: not valid java name */
    public final /* synthetic */ String m806unboximpl() {
        return this.value;
    }
}
