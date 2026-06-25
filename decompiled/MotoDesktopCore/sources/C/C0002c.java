package C;

import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Property;
import android.view.View;
import androidx.appcompat.widget.SwitchCompat;
import com.motorola.mobiledesktop.core.uinput.EventType;
import java.util.WeakHashMap;

/* JADX INFO: renamed from: C.c, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public final class C0002c extends Property {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f28a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C0002c(Class cls, String str, int i2) {
        super(cls, str);
        this.f28a = i2;
    }

    @Override // android.util.Property
    public final Object get(Object obj) {
        switch (this.f28a) {
            case 0:
                return null;
            case 1:
                return null;
            case 2:
                return null;
            case 3:
                return null;
            case EventType.EVENT_MSC /* 4 */:
                return null;
            case EventType.EVENT_SW /* 5 */:
                return Float.valueOf(A.f3a.G((View) obj));
            case 6:
                WeakHashMap weakHashMap = v.l.f2836a;
                return ((View) obj).getClipBounds();
            default:
                return Float.valueOf(((SwitchCompat) obj).r);
        }
    }

    @Override // android.util.Property
    public final void set(Object obj, Object obj2) {
        switch (this.f28a) {
            case 0:
                f fVar = (f) obj;
                PointF pointF = (PointF) obj2;
                fVar.getClass();
                fVar.f31a = Math.round(pointF.x);
                int iRound = Math.round(pointF.y);
                fVar.f32b = iRound;
                int i2 = fVar.f36f + 1;
                fVar.f36f = i2;
                if (i2 == fVar.f37g) {
                    A.a(fVar.f35e, fVar.f31a, iRound, fVar.f33c, fVar.f34d);
                    fVar.f36f = 0;
                    fVar.f37g = 0;
                }
                break;
            case 1:
                f fVar2 = (f) obj;
                PointF pointF2 = (PointF) obj2;
                fVar2.getClass();
                fVar2.f33c = Math.round(pointF2.x);
                int iRound2 = Math.round(pointF2.y);
                fVar2.f34d = iRound2;
                int i3 = fVar2.f37g + 1;
                fVar2.f37g = i3;
                if (fVar2.f36f == i3) {
                    A.a(fVar2.f35e, fVar2.f31a, fVar2.f32b, fVar2.f33c, iRound2);
                    fVar2.f36f = 0;
                    fVar2.f37g = 0;
                }
                break;
            case 2:
                View view = (View) obj;
                PointF pointF3 = (PointF) obj2;
                A.a(view, view.getLeft(), view.getTop(), Math.round(pointF3.x), Math.round(pointF3.y));
                break;
            case 3:
                View view2 = (View) obj;
                PointF pointF4 = (PointF) obj2;
                A.a(view2, Math.round(pointF4.x), Math.round(pointF4.y), view2.getRight(), view2.getBottom());
                break;
            case EventType.EVENT_MSC /* 4 */:
                View view3 = (View) obj;
                PointF pointF5 = (PointF) obj2;
                int iRound3 = Math.round(pointF5.x);
                int iRound4 = Math.round(pointF5.y);
                A.a(view3, iRound3, iRound4, view3.getWidth() + iRound3, view3.getHeight() + iRound4);
                break;
            case EventType.EVENT_SW /* 5 */:
                A.b((View) obj, ((Float) obj2).floatValue());
                break;
            case 6:
                WeakHashMap weakHashMap = v.l.f2836a;
                ((View) obj).setClipBounds((Rect) obj2);
                break;
            default:
                ((SwitchCompat) obj).setThumbPosition(((Float) obj2).floatValue());
                break;
        }
    }
}
