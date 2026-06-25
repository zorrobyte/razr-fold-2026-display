package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.appcompat.view.menu.MenuAdapter;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
public final class T extends Q implements S {

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public static final Method f1137D;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public S f1138C;

    static {
        try {
            f1137D = PopupWindow.class.getDeclaredMethod("setTouchModal", Boolean.TYPE);
        } catch (NoSuchMethodException unused) {
            Log.i("MenuPopupWindow", "Could not find method setTouchModal() on PopupWindow. Oh well.");
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [androidx.appcompat.widget.I, androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView] */
    @Override // androidx.appcompat.widget.Q
    public final I a(final Context context, final boolean z2) {
        ?? r0 = new I(context, z2) { // from class: androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView

            /* JADX INFO: renamed from: n, reason: collision with root package name */
            public final int f1036n;

            /* JADX INFO: renamed from: o, reason: collision with root package name */
            public final int f1037o;

            /* JADX INFO: renamed from: p, reason: collision with root package name */
            public S f1038p;

            /* JADX INFO: renamed from: q, reason: collision with root package name */
            public androidx.appcompat.view.menu.q f1039q;

            {
                super(context, z2);
                if (1 == context.getResources().getConfiguration().getLayoutDirection()) {
                    this.f1036n = 21;
                    this.f1037o = 22;
                } else {
                    this.f1036n = 22;
                    this.f1037o = 21;
                }
            }

            @Override // androidx.appcompat.widget.I, android.view.View
            public final boolean onHoverEvent(MotionEvent motionEvent) {
                MenuAdapter menuAdapter;
                int headersCount;
                int iPointToPosition;
                int i2;
                if (this.f1038p != null) {
                    ListAdapter adapter = getAdapter();
                    if (adapter instanceof HeaderViewListAdapter) {
                        HeaderViewListAdapter headerViewListAdapter = (HeaderViewListAdapter) adapter;
                        headersCount = headerViewListAdapter.getHeadersCount();
                        menuAdapter = (MenuAdapter) headerViewListAdapter.getWrappedAdapter();
                    } else {
                        menuAdapter = (MenuAdapter) adapter;
                        headersCount = 0;
                    }
                    androidx.appcompat.view.menu.q item = (motionEvent.getAction() == 10 || (iPointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY())) == -1 || (i2 = iPointToPosition - headersCount) < 0 || i2 >= menuAdapter.getCount()) ? null : menuAdapter.getItem(i2);
                    androidx.appcompat.view.menu.q qVar = this.f1039q;
                    if (qVar != item) {
                        androidx.appcompat.view.menu.o adapterMenu = menuAdapter.getAdapterMenu();
                        if (qVar != null) {
                            this.f1038p.d(adapterMenu, qVar);
                        }
                        this.f1039q = item;
                        if (item != null) {
                            this.f1038p.b(adapterMenu, item);
                        }
                    }
                }
                return super.onHoverEvent(motionEvent);
            }

            @Override // android.widget.ListView, android.widget.AbsListView, android.view.View, android.view.KeyEvent.Callback
            public final boolean onKeyDown(int i2, KeyEvent keyEvent) {
                ListMenuItemView listMenuItemView = (ListMenuItemView) getSelectedView();
                if (listMenuItemView != null && i2 == this.f1036n) {
                    if (listMenuItemView.isEnabled() && listMenuItemView.getItemData().hasSubMenu()) {
                        performItemClick(listMenuItemView, getSelectedItemPosition(), getSelectedItemId());
                    }
                    return true;
                }
                if (listMenuItemView == null || i2 != this.f1037o) {
                    return super.onKeyDown(i2, keyEvent);
                }
                setSelection(-1);
                ((MenuAdapter) getAdapter()).getAdapterMenu().c(false);
                return true;
            }

            public void setHoverListener(S s2) {
                this.f1038p = s2;
            }

            @Override // androidx.appcompat.widget.I, android.widget.AbsListView
            public /* bridge */ /* synthetic */ void setSelector(Drawable drawable) {
                super.setSelector(drawable);
            }
        };
        r0.setHoverListener(this);
        return r0;
    }

    @Override // androidx.appcompat.widget.S
    public final void b(androidx.appcompat.view.menu.o oVar, androidx.appcompat.view.menu.q qVar) {
        S s2 = this.f1138C;
        if (s2 != null) {
            s2.b(oVar, qVar);
        }
    }

    @Override // androidx.appcompat.widget.S
    public final void d(androidx.appcompat.view.menu.o oVar, MenuItem menuItem) {
        S s2 = this.f1138C;
        if (s2 != null) {
            s2.d(oVar, menuItem);
        }
    }
}
