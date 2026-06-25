package o;

import C.z;
import I.e;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.widget.TextView;
import androidx.appcompat.widget.B;
import androidx.appcompat.widget.C0084v;
import h.f;
import h.k;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import n.InterfaceC0151a;
import n.d;
import s.C0158a;
import s.g;

/* JADX INFO: renamed from: o.b, reason: case insensitive filesystem */
/* JADX INFO: loaded from: classes.dex */
public abstract class AbstractC0153b {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final C0154c f2781a = new C0154c();

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public static final f f2782b = new f(16);

    public static Typeface a(Context context, InterfaceC0151a interfaceC0151a, Resources resources, int i2, int i3, C0084v c0084v) {
        Typeface typeface = null;
        if (interfaceC0151a instanceof d) {
            d dVar = (d) interfaceC0151a;
            boolean z2 = dVar.f2779c == 0;
            int i4 = dVar.f2778b;
            C0158a c0158a = dVar.f2777a;
            f fVar = g.f2809a;
            String str = c0158a.f2794e + "-" + i3;
            Typeface typeface2 = (Typeface) g.f2809a.a(str);
            if (typeface2 != null) {
                B b2 = (B) c0084v.f1319c;
                if (b2.f972k) {
                    b2.f971j = typeface2;
                    TextView textView = (TextView) ((WeakReference) c0084v.f1318b).get();
                    if (textView != null) {
                        textView.setTypeface(typeface2, b2.f970i);
                    }
                }
                typeface = typeface2;
            } else if (z2 && i4 == -1) {
                s.f fVarB = g.b(context, c0158a, i3);
                int i5 = fVarB.f2808b;
                if (i5 == 0) {
                    c0084v.c(fVarB.f2807a, null);
                } else {
                    c0084v.b(i5, null);
                }
                typeface = fVarB.f2807a;
            } else {
                s.b bVar = new s.b(context, c0158a, i3, str);
                if (z2) {
                    try {
                        typeface = ((s.f) g.f2810b.h(bVar, i4)).f2807a;
                    } catch (InterruptedException unused) {
                    }
                } else {
                    s.c cVar = new s.c(c0084v);
                    synchronized (g.f2811c) {
                        try {
                            k kVar = g.f2812d;
                            if (kVar.containsKey(str)) {
                                ((ArrayList) kVar.get(str)).add(cVar);
                            } else {
                                ArrayList arrayList = new ArrayList();
                                arrayList.add(cVar);
                                kVar.put(str, arrayList);
                                z zVar = g.f2810b;
                                s.d dVar2 = new s.d(str);
                                zVar.getClass();
                                zVar.g(new e(bVar, new Handler(), dVar2));
                            }
                        } finally {
                        }
                    }
                }
            }
        } else {
            Typeface typefaceL = f2781a.l(context, (n.b) interfaceC0151a, resources, i3);
            if (typefaceL != null) {
                c0084v.c(typefaceL, null);
            } else {
                c0084v.b(-3, null);
            }
            typeface = typefaceL;
        }
        if (typeface != null) {
            f2782b.b(b(resources, i2, i3), typeface);
        }
        return typeface;
    }

    public static String b(Resources resources, int i2, int i3) {
        return resources.getResourcePackageName(i2) + "-" + i2 + "-" + i3;
    }
}
