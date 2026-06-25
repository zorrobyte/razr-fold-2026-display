package androidx.compose.runtime.tooling;

import androidx.compose.runtime.Anchor;
import androidx.compose.runtime.GroupSourceInformation;
import androidx.compose.runtime.SlotReader;

/* JADX INFO: compiled from: ComposeStackTraceBuilder.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ReaderTraceBuilder extends ComposeStackTraceBuilder {
    private final SlotReader reader;

    public ReaderTraceBuilder(SlotReader slotReader) {
        this.reader = slotReader;
    }

    @Override // androidx.compose.runtime.tooling.ComposeStackTraceBuilder
    public int groupKeyOf(Anchor anchor) {
        SlotReader slotReader = this.reader;
        return slotReader.groupKey(slotReader.getTable$runtime_release().anchorIndex(anchor));
    }

    @Override // androidx.compose.runtime.tooling.ComposeStackTraceBuilder
    public GroupSourceInformation sourceInformationOf(Anchor anchor) {
        return this.reader.getTable$runtime_release().sourceInformationOf(this.reader.getTable$runtime_release().anchorIndex(anchor));
    }
}
