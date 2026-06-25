package androidx.compose.ui.text.input;

import android.view.inputmethod.ExtractedText;
import androidx.compose.ui.text.TextRange;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: InputState.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class InputState_androidKt {
    public static final ExtractedText toExtractedText(TextFieldValue textFieldValue) {
        ExtractedText extractedText = new ExtractedText();
        extractedText.text = textFieldValue.getText();
        extractedText.startOffset = 0;
        extractedText.partialEndOffset = textFieldValue.getText().length();
        extractedText.partialStartOffset = -1;
        extractedText.selectionStart = TextRange.m1595getMinimpl(textFieldValue.m1692getSelectiond9O1mEE());
        extractedText.selectionEnd = TextRange.m1594getMaximpl(textFieldValue.m1692getSelectiond9O1mEE());
        extractedText.flags = !StringsKt.contains$default((CharSequence) textFieldValue.getText(), '\n', false, 2, (Object) null) ? 1 : 0;
        return extractedText;
    }
}
