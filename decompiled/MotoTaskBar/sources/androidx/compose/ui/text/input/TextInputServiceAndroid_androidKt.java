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
        int iM849getImeActioneUduSuo = imeOptions.m849getImeActioneUduSuo();
        ImeAction.Companion companion = ImeAction.Companion;
        int i = 6;
        if (ImeAction.m836equalsimpl0(iM849getImeActioneUduSuo, companion.m840getDefaulteUduSuo())) {
            if (!imeOptions.getSingleLine()) {
                i = 0;
            }
        } else if (ImeAction.m836equalsimpl0(iM849getImeActioneUduSuo, companion.m844getNoneeUduSuo())) {
            i = 1;
        } else if (ImeAction.m836equalsimpl0(iM849getImeActioneUduSuo, companion.m842getGoeUduSuo())) {
            i = 2;
        } else if (ImeAction.m836equalsimpl0(iM849getImeActioneUduSuo, companion.m843getNexteUduSuo())) {
            i = 5;
        } else if (ImeAction.m836equalsimpl0(iM849getImeActioneUduSuo, companion.m845getPreviouseUduSuo())) {
            i = 7;
        } else if (ImeAction.m836equalsimpl0(iM849getImeActioneUduSuo, companion.m846getSearcheUduSuo())) {
            i = 3;
        } else if (ImeAction.m836equalsimpl0(iM849getImeActioneUduSuo, companion.m847getSendeUduSuo())) {
            i = 4;
        } else if (!ImeAction.m836equalsimpl0(iM849getImeActioneUduSuo, companion.m841getDoneeUduSuo())) {
            throw new IllegalStateException("invalid ImeAction");
        }
        editorInfo.imeOptions = i;
        imeOptions.getPlatformImeOptions();
        int iM850getKeyboardTypePjHm6EE = imeOptions.m850getKeyboardTypePjHm6EE();
        KeyboardType.Companion companion2 = KeyboardType.Companion;
        if (KeyboardType.m861equalsimpl0(iM850getKeyboardTypePjHm6EE, companion2.m871getTextPjHm6EE())) {
            editorInfo.inputType = 1;
        } else if (KeyboardType.m861equalsimpl0(iM850getKeyboardTypePjHm6EE, companion2.m864getAsciiPjHm6EE())) {
            editorInfo.inputType = 1;
            editorInfo.imeOptions |= Integer.MIN_VALUE;
        } else if (KeyboardType.m861equalsimpl0(iM850getKeyboardTypePjHm6EE, companion2.m867getNumberPjHm6EE())) {
            editorInfo.inputType = 2;
        } else if (KeyboardType.m861equalsimpl0(iM850getKeyboardTypePjHm6EE, companion2.m870getPhonePjHm6EE())) {
            editorInfo.inputType = 3;
        } else if (KeyboardType.m861equalsimpl0(iM850getKeyboardTypePjHm6EE, companion2.m872getUriPjHm6EE())) {
            editorInfo.inputType = 17;
        } else if (KeyboardType.m861equalsimpl0(iM850getKeyboardTypePjHm6EE, companion2.m866getEmailPjHm6EE())) {
            editorInfo.inputType = 33;
        } else if (KeyboardType.m861equalsimpl0(iM850getKeyboardTypePjHm6EE, companion2.m869getPasswordPjHm6EE())) {
            editorInfo.inputType = 129;
        } else if (KeyboardType.m861equalsimpl0(iM850getKeyboardTypePjHm6EE, companion2.m868getNumberPasswordPjHm6EE())) {
            editorInfo.inputType = 18;
        } else {
            if (!KeyboardType.m861equalsimpl0(iM850getKeyboardTypePjHm6EE, companion2.m865getDecimalPjHm6EE())) {
                throw new IllegalStateException("Invalid Keyboard Type");
            }
            editorInfo.inputType = 8194;
        }
        if (!imeOptions.getSingleLine() && hasFlag(editorInfo.inputType, 1)) {
            editorInfo.inputType |= 131072;
            if (ImeAction.m836equalsimpl0(imeOptions.m849getImeActioneUduSuo(), companion.m840getDefaulteUduSuo())) {
                editorInfo.imeOptions |= 1073741824;
            }
        }
        if (hasFlag(editorInfo.inputType, 1)) {
            int iM848getCapitalizationIUNYP9k = imeOptions.m848getCapitalizationIUNYP9k();
            KeyboardCapitalization.Companion companion3 = KeyboardCapitalization.Companion;
            if (KeyboardCapitalization.m853equalsimpl0(iM848getCapitalizationIUNYP9k, companion3.m856getCharactersIUNYP9k())) {
                editorInfo.inputType |= 4096;
            } else if (KeyboardCapitalization.m853equalsimpl0(iM848getCapitalizationIUNYP9k, companion3.m859getWordsIUNYP9k())) {
                editorInfo.inputType |= 8192;
            } else if (KeyboardCapitalization.m853equalsimpl0(iM848getCapitalizationIUNYP9k, companion3.m858getSentencesIUNYP9k())) {
                editorInfo.inputType |= 16384;
            }
            if (imeOptions.getAutoCorrect()) {
                editorInfo.inputType |= 32768;
            }
        }
        editorInfo.initialSelStart = TextRange.m815getStartimpl(textFieldValue.m873getSelectiond9O1mEE());
        editorInfo.initialSelEnd = TextRange.m812getEndimpl(textFieldValue.m873getSelectiond9O1mEE());
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
