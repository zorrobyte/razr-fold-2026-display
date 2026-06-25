package androidx.savedstate;

/* JADX INFO: compiled from: SavedStateReader.kt */
/* JADX INFO: loaded from: classes.dex */
abstract /* synthetic */ class SavedStateReaderKt__SavedStateReaderKt {
    public static final Void keyOrValueNotFoundError(String str) {
        str.getClass();
        throw new IllegalArgumentException("No valid saved state was found for the key '" + str + "'. It may be missing, null, or not of the expected type. This can occur if the value was saved with a different type or if the saved state was modified unexpectedly.");
    }
}
