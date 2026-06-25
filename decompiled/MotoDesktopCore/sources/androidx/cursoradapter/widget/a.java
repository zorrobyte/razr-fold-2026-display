package androidx.cursoradapter.widget;

import android.database.ContentObserver;
import android.os.Handler;

/* JADX INFO: loaded from: classes.dex */
public final class a extends ContentObserver {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ CursorAdapter f1461a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public a(CursorAdapter cursorAdapter) {
        super(new Handler());
        this.f1461a = cursorAdapter;
    }

    @Override // android.database.ContentObserver
    public final boolean deliverSelfNotifications() {
        return true;
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z2) {
        this.f1461a.onContentChanged();
    }
}
