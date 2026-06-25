package androidx.appcompat.app;

import X.w0;
import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import i.C0138a;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public class f {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final /* synthetic */ int f562a = 2;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f563b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public Object f564c;

    public f() {
        new HashSet(2);
        this.f563b = 0;
    }

    public f(int i2) {
        if (i2 <= 0) {
            throw new IllegalArgumentException("The max pool size must be > 0");
        }
        this.f564c = new Object[i2];
    }

    public f(int i2, s.e[] eVarArr) {
        this.f563b = i2;
        this.f564c = eVarArr;
    }

    public f(Context context, int i2) {
        this.f564c = new C0056c(new ContextThemeWrapper(context, g.c(context, i2)));
        this.f563b = i2;
    }

    public f(C0138a c0138a, int i2) {
        new f().f564c = this;
        this.f564c = c0138a;
        this.f563b = i2;
    }

    public static String d(int i2) {
        return i2 == 1 ? "DIRECT" : i2 == 2 ? "CENTER" : i2 == 3 ? "MATCH" : i2 == 4 ? "CHAIN" : i2 == 5 ? "BARRIER" : "UNCONNECTED";
    }

    public Object a() {
        int i2 = this.f563b;
        if (i2 <= 0) {
            return null;
        }
        int i3 = i2 - 1;
        Object[] objArr = (Object[]) this.f564c;
        Object obj = objArr[i3];
        objArr[i3] = null;
        this.f563b = i2 - 1;
        return obj;
    }

    public g b() {
        C0056c c0056c = (C0056c) this.f564c;
        g gVar = new g(c0056c.f515a, this.f563b);
        View view = c0056c.f519e;
        e eVar = gVar.f565c;
        if (view != null) {
            eVar.f561z = view;
        } else {
            CharSequence charSequence = c0056c.f518d;
            if (charSequence != null) {
                eVar.f541e = charSequence;
                TextView textView = eVar.f559x;
                if (textView != null) {
                    textView.setText(charSequence);
                }
            }
            Drawable drawable = c0056c.f517c;
            if (drawable != null) {
                eVar.f557v = drawable;
                eVar.f556u = 0;
                ImageView imageView = eVar.f558w;
                if (imageView != null) {
                    imageView.setVisibility(0);
                    eVar.f558w.setImageDrawable(drawable);
                }
            }
        }
        CharSequence charSequence2 = c0056c.f520f;
        if (charSequence2 != null) {
            eVar.f542f = charSequence2;
            TextView textView2 = eVar.f560y;
            if (textView2 != null) {
                textView2.setText(charSequence2);
            }
        }
        CharSequence charSequence3 = c0056c.f521g;
        if (charSequence3 != null) {
            eVar.b(-1, charSequence3, c0056c.f522h);
        }
        CharSequence charSequence4 = c0056c.f523i;
        if (charSequence4 != null) {
            eVar.b(-2, charSequence4, c0056c.f524j);
        }
        if (c0056c.f527m != null) {
            AlertController$RecycleListView alertController$RecycleListView = (AlertController$RecycleListView) c0056c.f516b.inflate(eVar.f532D, (ViewGroup) null);
            ListAdapter dVar = c0056c.f527m;
            if (dVar == null) {
                dVar = new d(c0056c.f515a, eVar.f533E, R.id.text1, null);
            }
            eVar.f529A = dVar;
            eVar.f530B = -1;
            if (c0056c.f528n != null) {
                alertController$RecycleListView.setOnItemClickListener(new C0055b(c0056c, eVar));
            }
            eVar.f543g = alertController$RecycleListView;
        }
        gVar.setCancelable(c0056c.f525k);
        if (c0056c.f525k) {
            gVar.setCanceledOnTouchOutside(true);
        }
        gVar.setOnCancelListener(null);
        gVar.setOnDismissListener(null);
        DialogInterface.OnKeyListener onKeyListener = c0056c.f526l;
        if (onKeyListener != null) {
            gVar.setOnKeyListener(onKeyListener);
        }
        return gVar;
    }

    public boolean c(Object obj) {
        int i2 = 0;
        while (true) {
            int i3 = this.f563b;
            Object[] objArr = (Object[]) this.f564c;
            if (i2 >= i3) {
                if (i3 >= objArr.length) {
                    return false;
                }
                objArr[i3] = obj;
                this.f563b = i3 + 1;
                return true;
            }
            if (objArr[i2] == obj) {
                throw new IllegalStateException("Already in the pool!");
            }
            i2++;
        }
    }

    public String toString() {
        switch (this.f562a) {
            case 1:
                return ((C0138a) this.f564c).f2626j + ":" + w0.d(this.f563b);
            case 2:
                int i2 = this.f563b;
                f fVar = (f) this.f564c;
                String strD = d(0);
                if (i2 != 1) {
                    return "{ " + fVar + " UNRESOLVED} type: " + strD;
                }
                return "[" + fVar + ", RESOLVED: " + ((Object) null) + ":0.0] type: " + strD;
            default:
                return super.toString();
        }
    }
}
