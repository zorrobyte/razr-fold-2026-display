package androidx.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$styleable;
import androidx.core.widget.NestedScrollView;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class e {

    /* JADX INFO: renamed from: A, reason: collision with root package name */
    public ListAdapter f529A;

    /* JADX INFO: renamed from: C, reason: collision with root package name */
    public final int f531C;

    /* JADX INFO: renamed from: D, reason: collision with root package name */
    public final int f532D;

    /* JADX INFO: renamed from: E, reason: collision with root package name */
    public final int f533E;

    /* JADX INFO: renamed from: F, reason: collision with root package name */
    public final boolean f534F;

    /* JADX INFO: renamed from: G, reason: collision with root package name */
    public final Y.x f535G;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Context f537a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public final s f538b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public final Window f539c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final int f540d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public CharSequence f541e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public CharSequence f542f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public AlertController$RecycleListView f543g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public Button f544h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public CharSequence f545i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public Message f546j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public Drawable f547k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public Button f548l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public CharSequence f549m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public Message f550n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public Drawable f551o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public Button f552p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public CharSequence f553q;
    public Message r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public Drawable f554s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public NestedScrollView f555t;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public Drawable f557v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public ImageView f558w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public TextView f559x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public TextView f560y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public View f561z;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public int f556u = 0;

    /* JADX INFO: renamed from: B, reason: collision with root package name */
    public int f530B = -1;

    /* JADX INFO: renamed from: H, reason: collision with root package name */
    public final K.c f536H = new K.c(2, this);

    public e(Context context, s sVar, Window window) {
        this.f537a = context;
        this.f538b = sVar;
        this.f539c = window;
        Y.x xVar = new Y.x();
        xVar.f456b = new WeakReference(sVar);
        this.f535G = xVar;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R$styleable.AlertDialog, R$attr.alertDialogStyle, 0);
        this.f531C = typedArrayObtainStyledAttributes.getResourceId(R$styleable.AlertDialog_android_layout, 0);
        typedArrayObtainStyledAttributes.getResourceId(R$styleable.AlertDialog_buttonPanelSideLayout, 0);
        this.f532D = typedArrayObtainStyledAttributes.getResourceId(R$styleable.AlertDialog_listLayout, 0);
        typedArrayObtainStyledAttributes.getResourceId(R$styleable.AlertDialog_multiChoiceItemLayout, 0);
        typedArrayObtainStyledAttributes.getResourceId(R$styleable.AlertDialog_singleChoiceItemLayout, 0);
        this.f533E = typedArrayObtainStyledAttributes.getResourceId(R$styleable.AlertDialog_listItemLayout, 0);
        this.f534F = typedArrayObtainStyledAttributes.getBoolean(R$styleable.AlertDialog_showTitle, true);
        this.f540d = typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.AlertDialog_buttonIconDimen, 0);
        typedArrayObtainStyledAttributes.recycle();
        sVar.a().t(1);
    }

    public static ViewGroup a(View view, View view2) {
        if (view == null) {
            if (view2 instanceof ViewStub) {
                view2 = ((ViewStub) view2).inflate();
            }
            return (ViewGroup) view2;
        }
        if (view2 != null) {
            ViewParent parent = view2.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(view2);
            }
        }
        if (view instanceof ViewStub) {
            view = ((ViewStub) view).inflate();
        }
        return (ViewGroup) view;
    }

    public final void b(int i2, CharSequence charSequence, DialogInterface.OnClickListener onClickListener) {
        Message messageObtainMessage = onClickListener != null ? this.f535G.obtainMessage(i2, onClickListener) : null;
        if (i2 == -3) {
            this.f553q = charSequence;
            this.r = messageObtainMessage;
            this.f554s = null;
        } else if (i2 == -2) {
            this.f549m = charSequence;
            this.f550n = messageObtainMessage;
            this.f551o = null;
        } else {
            if (i2 != -1) {
                throw new IllegalArgumentException("Button does not exist");
            }
            this.f545i = charSequence;
            this.f546j = messageObtainMessage;
            this.f547k = null;
        }
    }
}
