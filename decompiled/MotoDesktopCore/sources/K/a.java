package K;

import android.view.View;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import java.util.WeakHashMap;
import v.l;
import x.C0165b;

/* JADX INFO: loaded from: classes.dex */
public final class a implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f189a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final View f190b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final int f191c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final /* synthetic */ BottomSheetBehavior f192d;

    public a(BottomSheetBehavior bottomSheetBehavior, View view) {
        this.f189a = 0;
        this.f192d = bottomSheetBehavior;
        this.f190b = view;
        this.f191c = 4;
    }

    public a(BottomSheetBehavior bottomSheetBehavior, View view, int i2) {
        this.f189a = 1;
        this.f192d = bottomSheetBehavior;
        this.f190b = view;
        this.f191c = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i2 = this.f191c;
        View view = this.f190b;
        BottomSheetBehavior bottomSheetBehavior = this.f192d;
        switch (this.f189a) {
            case 0:
                bottomSheetBehavior.x(view, i2);
                break;
            default:
                C0165b c0165b = bottomSheetBehavior.f2120m;
                if (c0165b != null && c0165b.g()) {
                    WeakHashMap weakHashMap = l.f2836a;
                    view.postOnAnimation(this);
                } else {
                    bottomSheetBehavior.v(i2);
                }
                break;
        }
    }
}
