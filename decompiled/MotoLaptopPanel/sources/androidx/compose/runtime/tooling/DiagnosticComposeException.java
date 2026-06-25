package androidx.compose.runtime.tooling;

import java.util.List;

/* JADX INFO: compiled from: DiagnosticComposeException.jvm.kt */
/* JADX INFO: loaded from: classes.dex */
public final class DiagnosticComposeException extends RuntimeException {
    private final List trace;

    public DiagnosticComposeException(List list) {
        this.trace = list;
    }

    @Override // java.lang.Throwable
    public Throwable fillInStackTrace() {
        setStackTrace(new StackTraceElement[0]);
        return this;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Composition stack when thrown:");
        sb.append('\n');
        ComposeStackTraceKt.appendStackTrace(sb, this.trace);
        String string = sb.toString();
        string.getClass();
        return string;
    }
}
