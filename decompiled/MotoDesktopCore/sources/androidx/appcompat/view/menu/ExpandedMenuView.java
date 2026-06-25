package androidx.appcompat.view.menu;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

/* JADX INFO: loaded from: classes.dex */
public final class ExpandedMenuView extends ListView implements n, C, AdapterView.OnItemClickListener {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final int[] f672b = {R.attr.background, R.attr.divider};

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public o f673a;

    public ExpandedMenuView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnItemClickListener(this);
        f0.b bVarM = f0.b.m(context, attributeSet, f672b, R.attr.listViewStyle, 0);
        TypedArray typedArray = (TypedArray) bVarM.f2538c;
        if (typedArray.hasValue(0)) {
            setBackgroundDrawable(bVarM.f(0));
        }
        if (typedArray.hasValue(1)) {
            setDivider(bVarM.f(1));
        }
        bVarM.n();
    }

    @Override // androidx.appcompat.view.menu.n
    public final boolean a(q qVar) {
        return this.f673a.q(qVar, null, 0);
    }

    @Override // androidx.appcompat.view.menu.C
    public final void b(o oVar) {
        this.f673a = oVar;
    }

    public int getWindowAnimations() {
        return 0;
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setChildrenDrawingCacheEnabled(false);
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
        a((q) getAdapter().getItem(i2));
    }
}
