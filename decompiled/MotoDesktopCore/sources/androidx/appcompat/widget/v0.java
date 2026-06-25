package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$string;
import androidx.appcompat.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public final class v0 implements F {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Toolbar f1320a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f1321b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public ScrollingTabContainerView f1322c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public final View f1323d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public Drawable f1324e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public Drawable f1325f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final Drawable f1326g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final boolean f1327h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public CharSequence f1328i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final CharSequence f1329j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public final CharSequence f1330k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public Window.Callback f1331l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public boolean f1332m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public C0074k f1333n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public final int f1334o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public final Drawable f1335p;

    public v0(Toolbar toolbar, boolean z2) {
        int i2;
        Drawable drawable;
        int i3 = R$string.abc_action_bar_up_description;
        this.f1334o = 0;
        this.f1320a = toolbar;
        this.f1328i = toolbar.getTitle();
        this.f1329j = toolbar.getSubtitle();
        this.f1327h = this.f1328i != null;
        this.f1326g = toolbar.getNavigationIcon();
        f0.b bVarM = f0.b.m(toolbar.getContext(), null, R$styleable.ActionBar, R$attr.actionBarStyle, 0);
        this.f1335p = bVarM.f(R$styleable.ActionBar_homeAsUpIndicator);
        if (z2) {
            int i4 = R$styleable.ActionBar_title;
            TypedArray typedArray = (TypedArray) bVarM.f2538c;
            CharSequence text = typedArray.getText(i4);
            if (!TextUtils.isEmpty(text)) {
                this.f1327h = true;
                this.f1328i = text;
                if ((this.f1321b & 8) != 0) {
                    this.f1320a.setTitle(text);
                }
            }
            CharSequence text2 = typedArray.getText(R$styleable.ActionBar_subtitle);
            if (!TextUtils.isEmpty(text2)) {
                this.f1329j = text2;
                if ((this.f1321b & 8) != 0) {
                    toolbar.setSubtitle(text2);
                }
            }
            Drawable drawableF = bVarM.f(R$styleable.ActionBar_logo);
            if (drawableF != null) {
                this.f1325f = drawableF;
                c();
            }
            Drawable drawableF2 = bVarM.f(R$styleable.ActionBar_icon);
            if (drawableF2 != null) {
                this.f1324e = drawableF2;
                c();
            }
            if (this.f1326g == null && (drawable = this.f1335p) != null) {
                this.f1326g = drawable;
                int i5 = this.f1321b & 4;
                Toolbar toolbar2 = this.f1320a;
                if (i5 != 0) {
                    toolbar2.setNavigationIcon(drawable);
                } else {
                    toolbar2.setNavigationIcon((Drawable) null);
                }
            }
            a(typedArray.getInt(R$styleable.ActionBar_displayOptions, 0));
            int resourceId = typedArray.getResourceId(R$styleable.ActionBar_customNavigationLayout, 0);
            if (resourceId != 0) {
                View viewInflate = LayoutInflater.from(toolbar.getContext()).inflate(resourceId, (ViewGroup) toolbar, false);
                View view = this.f1323d;
                if (view != null && (this.f1321b & 16) != 0) {
                    toolbar.removeView(view);
                }
                this.f1323d = viewInflate;
                if (viewInflate != null && (this.f1321b & 16) != 0) {
                    toolbar.addView(viewInflate);
                }
                a(this.f1321b | 16);
            }
            int layoutDimension = typedArray.getLayoutDimension(R$styleable.ActionBar_height, 0);
            if (layoutDimension > 0) {
                ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
                layoutParams.height = layoutDimension;
                toolbar.setLayoutParams(layoutParams);
            }
            int dimensionPixelOffset = typedArray.getDimensionPixelOffset(R$styleable.ActionBar_contentInsetStart, -1);
            int dimensionPixelOffset2 = typedArray.getDimensionPixelOffset(R$styleable.ActionBar_contentInsetEnd, -1);
            if (dimensionPixelOffset >= 0 || dimensionPixelOffset2 >= 0) {
                int iMax = Math.max(dimensionPixelOffset, 0);
                int iMax2 = Math.max(dimensionPixelOffset2, 0);
                toolbar.c();
                toolbar.f1172t.a(iMax, iMax2);
            }
            int resourceId2 = typedArray.getResourceId(R$styleable.ActionBar_titleTextStyle, 0);
            if (resourceId2 != 0) {
                Context context = toolbar.getContext();
                toolbar.f1165l = resourceId2;
                AppCompatTextView appCompatTextView = toolbar.f1155b;
                if (appCompatTextView != null) {
                    appCompatTextView.setTextAppearance(context, resourceId2);
                }
            }
            int resourceId3 = typedArray.getResourceId(R$styleable.ActionBar_subtitleTextStyle, 0);
            if (resourceId3 != 0) {
                Context context2 = toolbar.getContext();
                toolbar.f1166m = resourceId3;
                AppCompatTextView appCompatTextView2 = toolbar.f1156c;
                if (appCompatTextView2 != null) {
                    appCompatTextView2.setTextAppearance(context2, resourceId3);
                }
            }
            int resourceId4 = typedArray.getResourceId(R$styleable.ActionBar_popupTheme, 0);
            if (resourceId4 != 0) {
                toolbar.setPopupTheme(resourceId4);
            }
        } else {
            if (toolbar.getNavigationIcon() != null) {
                this.f1335p = toolbar.getNavigationIcon();
                i2 = 15;
            } else {
                i2 = 11;
            }
            this.f1321b = i2;
        }
        bVarM.n();
        if (i3 != this.f1334o) {
            this.f1334o = i3;
            if (TextUtils.isEmpty(toolbar.getNavigationContentDescription())) {
                int i6 = this.f1334o;
                this.f1330k = i6 != 0 ? toolbar.getContext().getString(i6) : null;
                b();
            }
        }
        this.f1330k = toolbar.getNavigationContentDescription();
        toolbar.setNavigationOnClickListener(new t0(this));
    }

    public final void a(int i2) {
        View view;
        int i3 = this.f1321b ^ i2;
        this.f1321b = i2;
        if (i3 != 0) {
            if ((i3 & 4) != 0) {
                if ((i2 & 4) != 0) {
                    b();
                }
                int i4 = this.f1321b & 4;
                Toolbar toolbar = this.f1320a;
                if (i4 != 0) {
                    Drawable drawable = this.f1326g;
                    if (drawable == null) {
                        drawable = this.f1335p;
                    }
                    toolbar.setNavigationIcon(drawable);
                } else {
                    toolbar.setNavigationIcon((Drawable) null);
                }
            }
            if ((i3 & 3) != 0) {
                c();
            }
            int i5 = i3 & 8;
            Toolbar toolbar2 = this.f1320a;
            if (i5 != 0) {
                if ((i2 & 8) != 0) {
                    toolbar2.setTitle(this.f1328i);
                    toolbar2.setSubtitle(this.f1329j);
                } else {
                    toolbar2.setTitle((CharSequence) null);
                    toolbar2.setSubtitle((CharSequence) null);
                }
            }
            if ((i3 & 16) == 0 || (view = this.f1323d) == null) {
                return;
            }
            if ((i2 & 16) != 0) {
                toolbar2.addView(view);
            } else {
                toolbar2.removeView(view);
            }
        }
    }

    public final void b() {
        if ((this.f1321b & 4) != 0) {
            boolean zIsEmpty = TextUtils.isEmpty(this.f1330k);
            Toolbar toolbar = this.f1320a;
            if (zIsEmpty) {
                toolbar.setNavigationContentDescription(this.f1334o);
            } else {
                toolbar.setNavigationContentDescription(this.f1330k);
            }
        }
    }

    public final void c() {
        Drawable drawable;
        int i2 = this.f1321b;
        if ((i2 & 2) == 0) {
            drawable = null;
        } else if ((i2 & 1) == 0 || (drawable = this.f1325f) == null) {
            drawable = this.f1324e;
        }
        this.f1320a.setLogo(drawable);
    }
}
