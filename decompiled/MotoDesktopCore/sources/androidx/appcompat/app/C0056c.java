package androidx.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListAdapter;

/* JADX INFO: renamed from: androidx.appcompat.app.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0056c {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f515a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final LayoutInflater f516b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Drawable f517c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public CharSequence f518d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public View f519e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public CharSequence f520f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public CharSequence f521g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public DialogInterface.OnClickListener f522h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public CharSequence f523i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public DialogInterface.OnClickListener f524j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f525k = true;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public DialogInterface.OnKeyListener f526l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public ListAdapter f527m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public DialogInterface.OnClickListener f528n;

    public C0056c(ContextThemeWrapper contextThemeWrapper) {
        this.f515a = contextThemeWrapper;
        this.f516b = (LayoutInflater) contextThemeWrapper.getSystemService("layout_inflater");
    }
}
