package androidx.compose.runtime.tooling;

import java.util.Iterator;
import java.util.List;
import kotlin.ExceptionsKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: ComposeStackTrace.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ComposeStackTraceKt {
    public static final void appendStackTrace(StringBuilder sb, List list) {
        List listCreateListBuilder = CollectionsKt.createListBuilder();
        List listAsReversed = CollectionsKt.asReversed(list);
        int size = listAsReversed.size();
        String str = null;
        String str2 = null;
        for (int i = 0; i < size; i++) {
            ComposeStackTraceFrame composeStackTraceFrame = (ComposeStackTraceFrame) listAsReversed.get(i);
            ParsedSourceInformation sourceInfo = composeStackTraceFrame.getSourceInfo();
            String functionName = sourceInfo.getFunctionName();
            if (functionName != null) {
                str = functionName;
            } else if (str == null) {
                str = "<unknown function>";
            }
            String fileName = sourceInfo.getFileName();
            if (fileName != null) {
                str2 = fileName;
            } else if (str2 == null) {
                str2 = "<unknown file>";
            }
            int[] lineNumbers = sourceInfo.getLineNumbers();
            String str3 = str + '(' + str2 + ':' + ((composeStackTraceFrame.getGroupOffset() == null || composeStackTraceFrame.getGroupOffset().intValue() >= lineNumbers.length) ? "<unknown line>" : String.valueOf(lineNumbers[composeStackTraceFrame.getGroupOffset().intValue()])) + ')';
            str3.getClass();
            if (!sourceInfo.isCall()) {
            }
            if (!Intrinsics.areEqual(sourceInfo.getFunctionName(), "rememberCompositionContext") || !Intrinsics.areEqual(sourceInfo.getPackageHash(), "9igjgp")) {
                listCreateListBuilder.add(str3);
            }
        }
        List listAsReversed2 = CollectionsKt.asReversed(CollectionsKt.build(listCreateListBuilder));
        int size2 = listAsReversed2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            sb.append("\tat " + ((String) listAsReversed2.get(i2)));
            sb.append('\n');
        }
    }

    public static final Throwable attachComposeStackTrace(Throwable th, Function0 function0) {
        tryAttachComposeStackTrace(th, function0);
        return th;
    }

    public static final boolean tryAttachComposeStackTrace(Throwable th, Function0 function0) {
        DiagnosticComposeException diagnosticComposeException;
        List suppressedExceptions = ExceptionsKt.getSuppressedExceptions(th);
        boolean z = false;
        if (suppressedExceptions == null || !suppressedExceptions.isEmpty()) {
            Iterator it = suppressedExceptions.iterator();
            while (it.hasNext()) {
                if (((Throwable) it.next()) instanceof DiagnosticComposeException) {
                    return false;
                }
            }
        }
        try {
            List list = (List) function0.mo2224invoke();
            boolean zIsEmpty = list.isEmpty();
            z = !zIsEmpty;
            diagnosticComposeException = !zIsEmpty ? new DiagnosticComposeException(list) : null;
        } catch (Throwable th2) {
            diagnosticComposeException = th2;
        }
        if (diagnosticComposeException != null) {
            ExceptionsKt.addSuppressed(th, diagnosticComposeException);
        }
        return z;
    }
}
