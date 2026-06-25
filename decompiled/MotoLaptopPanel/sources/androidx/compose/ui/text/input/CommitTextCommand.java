package androidx.compose.ui.text.input;

import androidx.compose.ui.text.AnnotatedString;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: EditCommand.kt */
/* JADX INFO: loaded from: classes.dex */
public final class CommitTextCommand implements EditCommand {
    private final AnnotatedString annotatedString;
    private final int newCursorPosition;

    public CommitTextCommand(AnnotatedString annotatedString, int i) {
        this.annotatedString = annotatedString;
        this.newCursorPosition = i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CommitTextCommand(String str, int i) {
        this(new AnnotatedString(str, null, 2, 0 == true ? 1 : 0), i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CommitTextCommand)) {
            return false;
        }
        CommitTextCommand commitTextCommand = (CommitTextCommand) obj;
        return Intrinsics.areEqual(getText(), commitTextCommand.getText()) && this.newCursorPosition == commitTextCommand.newCursorPosition;
    }

    public final String getText() {
        return this.annotatedString.getText();
    }

    public int hashCode() {
        return (getText().hashCode() * 31) + this.newCursorPosition;
    }

    public String toString() {
        return "CommitTextCommand(text='" + getText() + "', newCursorPosition=" + this.newCursorPosition + ')';
    }
}
