package androidx.compose.ui.text.input;

import android.view.Choreographer;
import android.view.inputmethod.EditorInfo;
import androidx.compose.ui.text.TextRange;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.KeyboardCapitalization;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.core.view.inputmethod.EditorInfoCompat;
import androidx.emoji2.text.EmojiCompat;
import java.util.concurrent.Executor;

/* JADX INFO: compiled from: TextInputServiceAndroid.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class TextInputServiceAndroid_androidKt {
    public static final Executor asExecutor(final Choreographer choreographer) {
        return new Executor() { // from class: androidx.compose.ui.text.input.TextInputServiceAndroid_androidKt$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.Executor
            public final void execute(Runnable runnable) {
                TextInputServiceAndroid_androidKt.asExecutor$lambda$2(choreographer, runnable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void asExecutor$lambda$2(Choreographer choreographer, final Runnable runnable) {
        choreographer.postFrameCallback(new Choreographer.FrameCallback() { // from class: androidx.compose.ui.text.input.TextInputServiceAndroid_androidKt$$ExternalSyntheticLambda1
            @Override // android.view.Choreographer.FrameCallback
            public final void doFrame(long j) {
                runnable.run();
            }
        });
    }

    private static final boolean hasFlag(int i, int i2) {
        return (i & i2) == i2;
    }

    public static final void update(EditorInfo editorInfo, ImeOptions imeOptions, TextFieldValue textFieldValue) {
        int iM1668getImeActioneUduSuo = imeOptions.m1668getImeActioneUduSuo();
        ImeAction.Companion companion = ImeAction.Companion;
        int i = 6;
        if (ImeAction.m1655equalsimpl0(iM1668getImeActioneUduSuo, companion.m1659getDefaulteUduSuo())) {
            if (!imeOptions.getSingleLine()) {
                i = 0;
            }
        } else if (ImeAction.m1655equalsimpl0(iM1668getImeActioneUduSuo, companion.m1663getNoneeUduSuo())) {
            i = 1;
        } else if (ImeAction.m1655equalsimpl0(iM1668getImeActioneUduSuo, companion.m1661getGoeUduSuo())) {
            i = 2;
        } else if (ImeAction.m1655equalsimpl0(iM1668getImeActioneUduSuo, companion.m1662getNexteUduSuo())) {
            i = 5;
        } else if (ImeAction.m1655equalsimpl0(iM1668getImeActioneUduSuo, companion.m1664getPreviouseUduSuo())) {
            i = 7;
        } else if (ImeAction.m1655equalsimpl0(iM1668getImeActioneUduSuo, companion.m1665getSearcheUduSuo())) {
            i = 3;
        } else if (ImeAction.m1655equalsimpl0(iM1668getImeActioneUduSuo, companion.m1666getSendeUduSuo())) {
            i = 4;
        } else if (!ImeAction.m1655equalsimpl0(iM1668getImeActioneUduSuo, companion.m1660getDoneeUduSuo())) {
            throw new IllegalStateException("invalid ImeAction");
        }
        editorInfo.imeOptions = i;
        imeOptions.getPlatformImeOptions();
        int iM1669getKeyboardTypePjHm6EE = imeOptions.m1669getKeyboardTypePjHm6EE();
        KeyboardType.Companion companion2 = KeyboardType.Companion;
        if (KeyboardType.m1680equalsimpl0(iM1669getKeyboardTypePjHm6EE, companion2.m1690getTextPjHm6EE())) {
            editorInfo.inputType = 1;
        } else if (KeyboardType.m1680equalsimpl0(iM1669getKeyboardTypePjHm6EE, companion2.m1683getAsciiPjHm6EE())) {
            editorInfo.inputType = 1;
            editorInfo.imeOptions |= Integer.MIN_VALUE;
        } else if (KeyboardType.m1680equalsimpl0(iM1669getKeyboardTypePjHm6EE, companion2.m1686getNumberPjHm6EE())) {
            editorInfo.inputType = 2;
        } else if (KeyboardType.m1680equalsimpl0(iM1669getKeyboardTypePjHm6EE, companion2.m1689getPhonePjHm6EE())) {
            editorInfo.inputType = 3;
        } else if (KeyboardType.m1680equalsimpl0(iM1669getKeyboardTypePjHm6EE, companion2.m1691getUriPjHm6EE())) {
            editorInfo.inputType = 17;
        } else if (KeyboardType.m1680equalsimpl0(iM1669getKeyboardTypePjHm6EE, companion2.m1685getEmailPjHm6EE())) {
            editorInfo.inputType = 33;
        } else if (KeyboardType.m1680equalsimpl0(iM1669getKeyboardTypePjHm6EE, companion2.m1688getPasswordPjHm6EE())) {
            editorInfo.inputType = 129;
        } else if (KeyboardType.m1680equalsimpl0(iM1669getKeyboardTypePjHm6EE, companion2.m1687getNumberPasswordPjHm6EE())) {
            editorInfo.inputType = 18;
        } else {
            if (!KeyboardType.m1680equalsimpl0(iM1669getKeyboardTypePjHm6EE, companion2.m1684getDecimalPjHm6EE())) {
                throw new IllegalStateException("Invalid Keyboard Type");
            }
            editorInfo.inputType = 8194;
        }
        if (!imeOptions.getSingleLine() && hasFlag(editorInfo.inputType, 1)) {
            editorInfo.inputType |= 131072;
            if (ImeAction.m1655equalsimpl0(imeOptions.m1668getImeActioneUduSuo(), companion.m1659getDefaulteUduSuo())) {
                editorInfo.imeOptions |= 1073741824;
            }
        }
        if (hasFlag(editorInfo.inputType, 1)) {
            int iM1667getCapitalizationIUNYP9k = imeOptions.m1667getCapitalizationIUNYP9k();
            KeyboardCapitalization.Companion companion3 = KeyboardCapitalization.Companion;
            if (KeyboardCapitalization.m1672equalsimpl0(iM1667getCapitalizationIUNYP9k, companion3.m1675getCharactersIUNYP9k())) {
                editorInfo.inputType |= 4096;
            } else if (KeyboardCapitalization.m1672equalsimpl0(iM1667getCapitalizationIUNYP9k, companion3.m1678getWordsIUNYP9k())) {
                editorInfo.inputType |= 8192;
            } else if (KeyboardCapitalization.m1672equalsimpl0(iM1667getCapitalizationIUNYP9k, companion3.m1677getSentencesIUNYP9k())) {
                editorInfo.inputType |= 16384;
            }
            if (imeOptions.getAutoCorrect()) {
                editorInfo.inputType |= 32768;
            }
        }
        editorInfo.initialSelStart = TextRange.m1596getStartimpl(textFieldValue.m1692getSelectiond9O1mEE());
        editorInfo.initialSelEnd = TextRange.m1593getEndimpl(textFieldValue.m1692getSelectiond9O1mEE());
        EditorInfoCompat.setInitialSurroundingText(editorInfo, textFieldValue.getText());
        editorInfo.imeOptions |= 33554432;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void updateWithEmojiCompat(EditorInfo editorInfo) {
        if (EmojiCompat.isConfigured()) {
            EmojiCompat.get().updateEditorInfo(editorInfo);
        }
    }
}
