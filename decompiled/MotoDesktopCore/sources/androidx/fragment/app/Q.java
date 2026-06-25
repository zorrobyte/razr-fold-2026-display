package androidx.fragment.app;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/* JADX INFO: loaded from: classes.dex */
public final class Q implements AdapterView.OnItemClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ ListFragment f1586a;

    public Q(ListFragment listFragment) {
        this.f1586a = listFragment;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
        this.f1586a.onListItemClick((ListView) adapterView, view, i2, j2);
    }
}
