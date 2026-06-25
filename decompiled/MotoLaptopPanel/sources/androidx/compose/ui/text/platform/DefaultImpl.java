package androidx.compose.ui.text.platform;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.runtime.State;
import androidx.emoji2.text.EmojiCompat;

/* JADX INFO: compiled from: EmojiCompatStatus.android.kt */
/* JADX INFO: loaded from: classes.dex */
final class DefaultImpl implements EmojiCompatStatusDelegate {
    private State loadState;

    public DefaultImpl() {
        this.loadState = EmojiCompat.isConfigured() ? getFontLoadState() : null;
    }

    private final State getFontLoadState() {
        EmojiCompat emojiCompat = EmojiCompat.get();
        if (emojiCompat.getLoadState() == 1) {
            return new ImmutableBool(true);
        }
        final MutableState mutableStateMutableStateOf$default = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(Boolean.FALSE, null, 2, null);
        emojiCompat.registerInitCallback(new EmojiCompat.InitCallback() { // from class: androidx.compose.ui.text.platform.DefaultImpl$getFontLoadState$initCallback$1
            @Override // androidx.emoji2.text.EmojiCompat.InitCallback
            public void onFailed(Throwable th) {
                this.loadState = EmojiCompatStatus_androidKt.Falsey;
            }

            @Override // androidx.emoji2.text.EmojiCompat.InitCallback
            public void onInitialized() {
                mutableStateMutableStateOf$default.setValue(Boolean.TRUE);
                this.loadState = new ImmutableBool(true);
            }
        });
        return mutableStateMutableStateOf$default;
    }

    @Override // androidx.compose.ui.text.platform.EmojiCompatStatusDelegate
    public State getFontLoaded() {
        State state = this.loadState;
        if (state != null) {
            state.getClass();
            return state;
        }
        if (!EmojiCompat.isConfigured()) {
            return EmojiCompatStatus_androidKt.Falsey;
        }
        State fontLoadState = getFontLoadState();
        this.loadState = fontLoadState;
        fontLoadState.getClass();
        return fontLoadState;
    }
}
