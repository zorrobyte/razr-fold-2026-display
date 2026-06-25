package androidx.cursoradapter.widget;

import android.database.Cursor;

/* JADX INFO: loaded from: classes.dex */
public interface c {
    void changeCursor(Cursor cursor);

    CharSequence convertToString(Cursor cursor);

    Cursor getCursor();

    Cursor runQueryOnBackgroundThread(CharSequence charSequence);
}
