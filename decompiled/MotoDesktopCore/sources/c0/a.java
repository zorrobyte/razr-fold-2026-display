package c0;

import android.view.View;
import android.view.ViewGroup;
import com.motorola.mobiledesktop.core.desktop.tutorial.SlideView;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class a extends F.a {

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final /* synthetic */ SlideView f2016c;

    public a(SlideView slideView) {
        this.f2016c = slideView;
    }

    @Override // F.a
    public final void a(ViewGroup viewGroup, int i2, Object obj) {
        viewGroup.removeView((View) this.f2016c.f2329g0.get(i2));
    }

    @Override // F.a
    public final int d() {
        List list = this.f2016c.f2329g0;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // F.a
    public final Object h(ViewGroup viewGroup, int i2) {
        View view = (View) this.f2016c.f2329g0.get(i2);
        viewGroup.addView(view);
        return view;
    }

    @Override // F.a
    public final boolean i(View view, Object obj) {
        return view == obj;
    }
}
