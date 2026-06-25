package androidx.appcompat.app;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: renamed from: androidx.appcompat.app.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0055b implements AdapterView.OnItemClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ e f513a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C0056c f514b;

    public C0055b(C0056c c0056c, e eVar) {
        this.f514b = c0056c;
        this.f513a = eVar;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
        C0056c c0056c = this.f514b;
        DialogInterface.OnClickListener onClickListener = c0056c.f528n;
        e eVar = this.f513a;
        onClickListener.onClick(eVar.f538b, i2);
        c0056c.getClass();
        eVar.f538b.dismiss();
    }
}
