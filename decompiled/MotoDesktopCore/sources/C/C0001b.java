package C;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Property;
import android.widget.ImageView;

/* JADX INFO: renamed from: C.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0001b extends Property {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f26a = 1;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public Object f27b;

    public C0001b() {
        super(Matrix.class, "imageMatrixProperty");
        this.f27b = new Matrix();
    }

    public /* synthetic */ C0001b(Class cls, String str) {
        super(cls, str);
    }

    @Override // android.util.Property
    public final Object get(Object obj) {
        switch (this.f26a) {
            case 0:
                ((Drawable) obj).copyBounds((Rect) this.f27b);
                return new PointF(r1.left, r1.top);
            default:
                Matrix matrix = (Matrix) this.f27b;
                matrix.set(((ImageView) obj).getImageMatrix());
                return matrix;
        }
    }

    @Override // android.util.Property
    public final void set(Object obj, Object obj2) {
        switch (this.f26a) {
            case 0:
                Drawable drawable = (Drawable) obj;
                PointF pointF = (PointF) obj2;
                Rect rect = (Rect) this.f27b;
                drawable.copyBounds(rect);
                rect.offsetTo(Math.round(pointF.x), Math.round(pointF.y));
                drawable.setBounds(rect);
                break;
            default:
                ((ImageView) obj).setImageMatrix((Matrix) obj2);
                break;
        }
    }
}
