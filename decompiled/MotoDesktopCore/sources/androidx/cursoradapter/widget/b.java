package androidx.cursoradapter.widget;

import android.database.DataSetObserver;

/* JADX INFO: loaded from: classes.dex */
public final class b extends DataSetObserver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ CursorAdapter f1462a;

    public b(CursorAdapter cursorAdapter) {
        this.f1462a = cursorAdapter;
    }

    @Override // android.database.DataSetObserver
    public final void onChanged() {
        CursorAdapter cursorAdapter = this.f1462a;
        cursorAdapter.mDataValid = true;
        cursorAdapter.notifyDataSetChanged();
    }

    @Override // android.database.DataSetObserver
    public final void onInvalidated() {
        CursorAdapter cursorAdapter = this.f1462a;
        cursorAdapter.mDataValid = false;
        cursorAdapter.notifyDataSetInvalidated();
    }
}
