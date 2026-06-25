package androidx.compose.runtime.tooling;

import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.GroupSourceInformation;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: ComposeStackTraceBuilder.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class ComposeStackTraceBuilder {
    private final List trace = new ArrayList();

    private final void appendTraceFrame(GroupSourceInformation groupSourceInformation, Object obj) {
        ComposeStackTraceFrame composeStackTraceFrameExtractTraceFrame = extractTraceFrame(groupSourceInformation, obj);
        if (composeStackTraceFrameExtractTraceFrame != null) {
            this.trace.add(composeStackTraceFrameExtractTraceFrame);
        }
    }

    private final ComposeStackTraceFrame extractTraceFrame(GroupSourceInformation groupSourceInformation, Object obj) {
        String sourceInformation = groupSourceInformation.getSourceInformation();
        ParsedSourceInformation sourceInfo = sourceInformation != null ? ComposeStackTraceBuilderKt.parseSourceInfo(sourceInformation) : null;
        if (sourceInfo == null) {
            return null;
        }
        if (obj == null) {
            return new ComposeStackTraceFrame(sourceInfo, null);
        }
        ArrayList groups = groupSourceInformation.getGroups();
        if (groups != null) {
            int size = groups.size();
            for (int i = 0; i < size; i++) {
                Object obj2 = groups.get(i);
                if (Intrinsics.areEqual(obj2, obj)) {
                    break;
                }
                sourceInformationOf(obj2);
            }
        }
        return new ComposeStackTraceFrame(sourceInfo, 0);
    }

    private final boolean findInGroupSourceInformation(GroupSourceInformation groupSourceInformation, Object obj) {
        ArrayList groups = groupSourceInformation.getGroups();
        boolean z = false;
        if (groups == null) {
            if (!groupSourceInformation.getClosed()) {
                appendTraceFrame(groupSourceInformation, null);
                return true;
            }
            int dataStartOffset = groupSourceInformation.getDataStartOffset();
            int dataEndOffset = groupSourceInformation.getDataEndOffset();
            if (obj instanceof Integer) {
                Number number = (Number) obj;
                int iIntValue = number.intValue();
                if ((dataStartOffset <= iIntValue && iIntValue < dataEndOffset) || (dataStartOffset == dataEndOffset && (obj instanceof Integer) && dataStartOffset == number.intValue())) {
                    z = true;
                }
                if (z) {
                    appendTraceFrame(groupSourceInformation, obj);
                }
            }
            return z;
        }
        int size = groups.size();
        for (int i = 0; i < size; i++) {
            Object obj2 = groups.get(i);
            if (obj2 instanceof Anchor) {
                if (Intrinsics.areEqual(obj2, obj)) {
                    appendTraceFrame(groupSourceInformation, obj2);
                    return true;
                }
            } else {
                if (!(obj2 instanceof GroupSourceInformation)) {
                    throw new IllegalStateException(("Unexpected child source info " + obj2).toString());
                }
                if (findInGroupSourceInformation((GroupSourceInformation) obj2, obj)) {
                    appendTraceFrame(groupSourceInformation, obj2);
                    return true;
                }
            }
        }
        return false;
    }

    private final boolean isCall(GroupSourceInformation groupSourceInformation) {
        String sourceInformation = groupSourceInformation.getSourceInformation();
        return sourceInformation != null && StringsKt.startsWith$default(sourceInformation, "C", false, 2, (Object) null);
    }

    private final GroupSourceInformation sourceInformationOf(Object obj) {
        if (obj instanceof Anchor) {
            return sourceInformationOf((Anchor) obj);
        }
        if (obj instanceof GroupSourceInformation) {
            return (GroupSourceInformation) obj;
        }
        throw new IllegalStateException(("Unexpected child source info " + obj).toString());
    }

    public abstract int groupKeyOf(Anchor anchor);

    public final void processEdge(GroupSourceInformation groupSourceInformation, Object obj) {
    }

    public abstract GroupSourceInformation sourceInformationOf(Anchor anchor);

    public final List trace() {
        return this.trace;
    }
}
