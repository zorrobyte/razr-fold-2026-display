package androidx.appcompat.widget;

import androidx.cursoradapter.widget.CursorAdapter;

/* JADX INFO: loaded from: classes.dex */
public final class X implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f1194a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ SearchView f1195b;

    public /* synthetic */ X(SearchView searchView, int i2) {
        this.f1194a = i2;
        this.f1195b = searchView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f1194a) {
            case 0:
                this.f1195b.r();
                break;
            default:
                CursorAdapter cursorAdapter = this.f1195b.f1086O;
                if (cursorAdapter != null && (cursorAdapter instanceof k0)) {
                    cursorAdapter.changeCursor(null);
                    break;
                }
                break;
        }
    }
}
