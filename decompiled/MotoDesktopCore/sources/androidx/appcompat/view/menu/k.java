package androidx.appcompat.view.menu;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class k extends BaseAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f771a = -1;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final /* synthetic */ l f772b;

    public k(l lVar) {
        this.f772b = lVar;
        a();
    }

    public final void a() {
        o oVar = this.f772b.f775c;
        q qVar = oVar.f802w;
        if (qVar != null) {
            oVar.i();
            ArrayList arrayList = oVar.f790j;
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                if (((q) arrayList.get(i2)) == qVar) {
                    this.f771a = i2;
                    return;
                }
            }
        }
        this.f771a = -1;
    }

    @Override // android.widget.Adapter
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final q getItem(int i2) {
        l lVar = this.f772b;
        o oVar = lVar.f775c;
        oVar.i();
        ArrayList arrayList = oVar.f790j;
        lVar.getClass();
        int i3 = this.f771a;
        if (i3 >= 0 && i2 >= i3) {
            i2++;
        }
        return (q) arrayList.get(i2);
    }

    @Override // android.widget.Adapter
    public final int getCount() {
        l lVar = this.f772b;
        o oVar = lVar.f775c;
        oVar.i();
        int size = oVar.f790j.size();
        lVar.getClass();
        return this.f771a < 0 ? size : size - 1;
    }

    @Override // android.widget.Adapter
    public final long getItemId(int i2) {
        return i2;
    }

    @Override // android.widget.Adapter
    public final View getView(int i2, View view, ViewGroup viewGroup) {
        if (view == null) {
            l lVar = this.f772b;
            view = lVar.f774b.inflate(lVar.f777e, viewGroup, false);
        }
        ((B) view).c(getItem(i2));
        return view;
    }

    @Override // android.widget.BaseAdapter
    public final void notifyDataSetChanged() {
        a();
        super.notifyDataSetChanged();
    }
}
