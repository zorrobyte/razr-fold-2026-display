package androidx.compose.ui.text.input;

import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.TextRange;

/* JADX INFO: compiled from: TextFieldValue.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TextFieldValueKt {
    public static final AnnotatedString getSelectedText(TextFieldValue textFieldValue) {
        return textFieldValue.getAnnotatedString().m765subSequence5zctL8(textFieldValue.m873getSelectiond9O1mEE());
    }

    public static final AnnotatedString getTextAfterSelection(TextFieldValue textFieldValue, int i) {
        return textFieldValue.getAnnotatedString().subSequence(TextRange.m813getMaximpl(textFieldValue.m873getSelectiond9O1mEE()), Math.min(TextRange.m813getMaximpl(textFieldValue.m873getSelectiond9O1mEE()) + i, textFieldValue.getText().length()));
    }

    public static final AnnotatedString getTextBeforeSelection(TextFieldValue textFieldValue, int i) {
        return textFieldValue.getAnnotatedString().subSequence(Math.max(0, TextRange.m814getMinimpl(textFieldValue.m873getSelectiond9O1mEE()) - i), TextRange.m814getMinimpl(textFieldValue.m873getSelectiond9O1mEE()));
    }
}
