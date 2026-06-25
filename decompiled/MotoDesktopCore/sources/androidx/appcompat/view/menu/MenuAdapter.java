package androidx.appcompat.view.menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class MenuAdapter extends BaseAdapter {
    o mAdapterMenu;
    private int mExpandedIndex = -1;
    private boolean mForceShowIcon;
    private final LayoutInflater mInflater;
    private final int mItemLayoutRes;
    private final boolean mOverflowOnly;

    public MenuAdapter(o oVar, LayoutInflater layoutInflater, boolean z2, int i2) {
        this.mOverflowOnly = z2;
        this.mInflater = layoutInflater;
        this.mAdapterMenu = oVar;
        this.mItemLayoutRes = i2;
        findExpandedIndex();
    }

    public void findExpandedIndex() {
        o oVar = this.mAdapterMenu;
        q qVar = oVar.f802w;
        if (qVar != null) {
            oVar.i();
            ArrayList arrayList = oVar.f790j;
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (((q) arrayList.get(i2)) == qVar) {
                    this.mExpandedIndex = i2;
                    return;
                }
            }
        }
        this.mExpandedIndex = -1;
    }

    public o getAdapterMenu() {
        return this.mAdapterMenu;
    }

    @Override // android.widget.Adapter
    public int getCount() {
        ArrayList arrayListL;
        if (this.mOverflowOnly) {
            o oVar = this.mAdapterMenu;
            oVar.i();
            arrayListL = oVar.f790j;
        } else {
            arrayListL = this.mAdapterMenu.l();
        }
        return this.mExpandedIndex < 0 ? arrayListL.size() : arrayListL.size() - 1;
    }

    public boolean getForceShowIcon() {
        return this.mForceShowIcon;
    }

    @Override // android.widget.Adapter
    public q getItem(int i2) {
        ArrayList arrayListL;
        if (this.mOverflowOnly) {
            o oVar = this.mAdapterMenu;
            oVar.i();
            arrayListL = oVar.f790j;
        } else {
            arrayListL = this.mAdapterMenu.l();
        }
        int i3 = this.mExpandedIndex;
        if (i3 >= 0 && i2 >= i3) {
            i2++;
        }
        return (q) arrayListL.get(i2);
    }

    @Override // android.widget.Adapter
    public long getItemId(int i2) {
        return i2;
    }

    @Override // android.widget.Adapter
    public View getView(int i2, View view, ViewGroup viewGroup) {
        boolean z2 = false;
        if (view == null) {
            view = this.mInflater.inflate(this.mItemLayoutRes, viewGroup, false);
        }
        int i3 = getItem(i2).f812b;
        int i4 = i2 - 1;
        int i5 = i4 >= 0 ? getItem(i4).f812b : i3;
        ListMenuItemView listMenuItemView = (ListMenuItemView) view;
        if (this.mAdapterMenu.m() && i3 != i5) {
            z2 = true;
        }
        listMenuItemView.setGroupDividerEnabled(z2);
        B b2 = (B) view;
        if (this.mForceShowIcon) {
            listMenuItemView.setForceShowIcon(true);
        }
        b2.c(getItem(i2));
        return view;
    }

    @Override // android.widget.BaseAdapter
    public void notifyDataSetChanged() {
        findExpandedIndex();
        super.notifyDataSetChanged();
    }

    public void setForceShowIcon(boolean z2) {
        this.mForceShowIcon = z2;
    }
}
