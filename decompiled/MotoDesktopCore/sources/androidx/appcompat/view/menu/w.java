package androidx.appcompat.view.menu;

import android.content.Context;
import android.graphics.Rect;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;

/* JADX INFO: loaded from: classes.dex */
public abstract class w implements E, A, AdapterView.OnItemClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Rect f843a;

    public static int j(MenuAdapter menuAdapter, Context context, int i2) {
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int count = menuAdapter.getCount();
        int i3 = 0;
        int i4 = 0;
        FrameLayout frameLayout = null;
        View view = null;
        for (int i5 = 0; i5 < count; i5++) {
            int itemViewType = menuAdapter.getItemViewType(i5);
            if (itemViewType != i4) {
                view = null;
                i4 = itemViewType;
            }
            if (frameLayout == null) {
                frameLayout = new FrameLayout(context);
            }
            view = menuAdapter.getView(i5, view, frameLayout);
            view.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            int measuredWidth = view.getMeasuredWidth();
            if (measuredWidth >= i2) {
                return i2;
            }
            if (measuredWidth > i3) {
                i3 = measuredWidth;
            }
        }
        return i3;
    }

    public static boolean r(o oVar) {
        int size = oVar.f786f.size();
        for (int i2 = 0; i2 < size; i2++) {
            MenuItem item = oVar.getItem(i2);
            if (item.isVisible() && item.getIcon() != null) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean d(q qVar) {
        return false;
    }

    @Override // androidx.appcompat.view.menu.A
    public final void e(Context context, o oVar) {
    }

    @Override // androidx.appcompat.view.menu.A
    public final boolean h(q qVar) {
        return false;
    }

    public abstract void i(o oVar);

    public abstract void k(View view);

    public abstract void l(boolean z2);

    public abstract void m(int i2);

    public abstract void n(int i2);

    public abstract void o(PopupWindow.OnDismissListener onDismissListener);

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView adapterView, View view, int i2, long j2) {
        ListAdapter listAdapter = (ListAdapter) adapterView.getAdapter();
        (listAdapter instanceof HeaderViewListAdapter ? (MenuAdapter) ((HeaderViewListAdapter) listAdapter).getWrappedAdapter() : (MenuAdapter) listAdapter).mAdapterMenu.q((MenuItem) listAdapter.getItem(i2), this, (this instanceof j) ^ true ? 0 : 4);
    }

    public abstract void p(boolean z2);

    public abstract void q(int i2);
}
