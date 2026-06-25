package C;

import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import androidx.cardview.widget.CardView;
import com.motorola.mobiledesktop.core.uinput.EventType;
import g.AbstractC0133b;
import g.C0132a;

/* JADX INFO: loaded from: classes.dex */
public final class n implements W.a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f58a;

    public /* synthetic */ n(int i2) {
        this.f58a = i2;
    }

    public n(int[] iArr, ValueAnimator valueAnimator) {
        this.f58a = 1;
    }

    public static Path a(float f2, float f3, float f4, float f5) {
        Path path = new Path();
        path.moveTo(f2, f3);
        path.lineTo(f4, f5);
        return path;
    }

    public int b(Object obj) {
        switch (this.f58a) {
            case EventType.EVENT_SW /* 5 */:
                return ((s.e) obj).f2804c;
            default:
                return ((n.c) obj).f2772b;
        }
    }

    public boolean c(Object obj) {
        switch (this.f58a) {
            case EventType.EVENT_SW /* 5 */:
                return ((s.e) obj).f2805d;
            default:
                return ((n.c) obj).f2773c;
        }
    }

    public void d(F.f fVar, float f2) {
        C0132a c0132a = (C0132a) ((Drawable) fVar.f123a);
        CardView cardView = (CardView) fVar.f124b;
        boolean useCompatPadding = cardView.getUseCompatPadding();
        boolean preventCornerOverlap = cardView.getPreventCornerOverlap();
        if (f2 != c0132a.f2544e || c0132a.f2545f != useCompatPadding || c0132a.f2546g != preventCornerOverlap) {
            c0132a.f2544e = f2;
            c0132a.f2545f = useCompatPadding;
            c0132a.f2546g = preventCornerOverlap;
            c0132a.b(null);
            c0132a.invalidateSelf();
        }
        if (!cardView.getUseCompatPadding()) {
            fVar.j(0, 0, 0, 0);
            return;
        }
        C0132a c0132a2 = (C0132a) ((Drawable) fVar.f123a);
        float f3 = c0132a2.f2544e;
        float f4 = c0132a2.f2540a;
        int iCeil = (int) Math.ceil(AbstractC0133b.a(f3, f4, cardView.getPreventCornerOverlap()));
        int iCeil2 = (int) Math.ceil(AbstractC0133b.b(f3, f4, cardView.getPreventCornerOverlap()));
        fVar.j(iCeil, iCeil2, iCeil, iCeil2);
    }
}
