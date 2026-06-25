package androidx.appcompat.widget;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.appcompat.widget.SearchView;

/* JADX INFO: loaded from: classes.dex */
public final class i0 extends TouchDelegate {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final View f1214a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final Rect f1215b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Rect f1216c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final Rect f1217d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public final int f1218e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f1219f;

    public i0(Rect rect, Rect rect2, SearchView.SearchAutoComplete searchAutoComplete) {
        super(rect, searchAutoComplete);
        int scaledTouchSlop = ViewConfiguration.get(searchAutoComplete.getContext()).getScaledTouchSlop();
        this.f1218e = scaledTouchSlop;
        Rect rect3 = new Rect();
        this.f1215b = rect3;
        Rect rect4 = new Rect();
        this.f1217d = rect4;
        Rect rect5 = new Rect();
        this.f1216c = rect5;
        rect3.set(rect);
        rect4.set(rect);
        int i2 = -scaledTouchSlop;
        rect4.inset(i2, i2);
        rect5.set(rect2);
        this.f1214a = searchAutoComplete;
    }

    @Override // android.view.TouchDelegate
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z2;
        boolean z3;
        int x2 = (int) motionEvent.getX();
        int y2 = (int) motionEvent.getY();
        int action = motionEvent.getAction();
        boolean z4 = true;
        if (action != 0) {
            if (action == 1 || action == 2) {
                z3 = this.f1219f;
                if (z3 && !this.f1217d.contains(x2, y2)) {
                    z4 = z3;
                    z2 = false;
                }
            } else {
                if (action == 3) {
                    z3 = this.f1219f;
                    this.f1219f = false;
                }
                z2 = true;
                z4 = false;
            }
            z4 = z3;
            z2 = true;
        } else if (this.f1215b.contains(x2, y2)) {
            this.f1219f = true;
            z2 = true;
        } else {
            z2 = true;
            z4 = false;
        }
        if (!z4) {
            return false;
        }
        Rect rect = this.f1216c;
        View view = this.f1214a;
        if (!z2 || rect.contains(x2, y2)) {
            motionEvent.setLocation(x2 - rect.left, y2 - rect.top);
        } else {
            motionEvent.setLocation(view.getWidth() / 2, view.getHeight() / 2);
        }
        return view.dispatchTouchEvent(motionEvent);
    }
}
