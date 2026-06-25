package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class B {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final TextView f962a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public n0 f963b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public n0 f964c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public n0 f965d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public n0 f966e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public n0 f967f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public n0 f968g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public final C f969h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int f970i = 0;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public Typeface f971j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f972k;

    public B(TextView textView) {
        this.f962a = textView;
        this.f969h = new C(textView);
    }

    public static n0 c(Context context, C0082t c0082t, int i2) {
        ColorStateList colorStateListK = c0082t.k(context, i2);
        if (colorStateListK == null) {
            return null;
        }
        n0 n0Var = new n0();
        n0Var.f1279d = true;
        n0Var.f1276a = colorStateListK;
        return n0Var;
    }

    public final void a(Drawable drawable, n0 n0Var) {
        if (drawable == null || n0Var == null) {
            return;
        }
        C0082t.m(drawable, n0Var, this.f962a.getDrawableState());
    }

    public final void b() {
        n0 n0Var = this.f963b;
        TextView textView = this.f962a;
        if (n0Var != null || this.f964c != null || this.f965d != null || this.f966e != null) {
            Drawable[] compoundDrawables = textView.getCompoundDrawables();
            a(compoundDrawables[0], this.f963b);
            a(compoundDrawables[1], this.f964c);
            a(compoundDrawables[2], this.f965d);
            a(compoundDrawables[3], this.f966e);
        }
        if (this.f967f == null && this.f968g == null) {
            return;
        }
        Drawable[] compoundDrawablesRelative = textView.getCompoundDrawablesRelative();
        a(compoundDrawablesRelative[0], this.f967f);
        a(compoundDrawablesRelative[2], this.f968g);
    }

    public final void d(AttributeSet attributeSet, int i2) {
        boolean z2;
        boolean z3;
        int resourceId;
        TextView textView = this.f962a;
        Context context = textView.getContext();
        C0082t c0082tF = C0082t.f();
        f0.b bVarM = f0.b.m(context, attributeSet, R$styleable.AppCompatTextHelper, i2, 0);
        int i3 = R$styleable.AppCompatTextHelper_android_textAppearance;
        TypedArray typedArray = (TypedArray) bVarM.f2538c;
        int resourceId2 = typedArray.getResourceId(i3, -1);
        if (typedArray.hasValue(R$styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.f963b = c(context, c0082tF, typedArray.getResourceId(R$styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (typedArray.hasValue(R$styleable.AppCompatTextHelper_android_drawableTop)) {
            this.f964c = c(context, c0082tF, typedArray.getResourceId(R$styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (typedArray.hasValue(R$styleable.AppCompatTextHelper_android_drawableRight)) {
            this.f965d = c(context, c0082tF, typedArray.getResourceId(R$styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (typedArray.hasValue(R$styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.f966e = c(context, c0082tF, typedArray.getResourceId(R$styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        if (typedArray.hasValue(R$styleable.AppCompatTextHelper_android_drawableStart)) {
            this.f967f = c(context, c0082tF, typedArray.getResourceId(R$styleable.AppCompatTextHelper_android_drawableStart, 0));
        }
        if (typedArray.hasValue(R$styleable.AppCompatTextHelper_android_drawableEnd)) {
            this.f968g = c(context, c0082tF, typedArray.getResourceId(R$styleable.AppCompatTextHelper_android_drawableEnd, 0));
        }
        bVarM.n();
        boolean z4 = textView.getTransformationMethod() instanceof PasswordTransformationMethod;
        if (resourceId2 != -1) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(resourceId2, R$styleable.TextAppearance);
            f0.b bVar = new f0.b(context, typedArrayObtainStyledAttributes);
            if (z4 || !typedArrayObtainStyledAttributes.hasValue(R$styleable.TextAppearance_textAllCaps)) {
                z2 = false;
                z3 = false;
            } else {
                z2 = typedArrayObtainStyledAttributes.getBoolean(R$styleable.TextAppearance_textAllCaps, false);
                z3 = true;
            }
            f(context, bVar);
            bVar.n();
        } else {
            z2 = false;
            z3 = false;
        }
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.TextAppearance, i2, 0);
        f0.b bVar2 = new f0.b(context, typedArrayObtainStyledAttributes2);
        if (!z4 && typedArrayObtainStyledAttributes2.hasValue(R$styleable.TextAppearance_textAllCaps)) {
            z2 = typedArrayObtainStyledAttributes2.getBoolean(R$styleable.TextAppearance_textAllCaps, false);
            z3 = true;
        }
        if (typedArrayObtainStyledAttributes2.hasValue(R$styleable.TextAppearance_android_textSize) && typedArrayObtainStyledAttributes2.getDimensionPixelSize(R$styleable.TextAppearance_android_textSize, -1) == 0) {
            textView.setTextSize(0, 0.0f);
        }
        f(context, bVar2);
        bVar2.n();
        if (!z4 && z3) {
            textView.setAllCaps(z2);
        }
        Typeface typeface = this.f971j;
        if (typeface != null) {
            textView.setTypeface(typeface, this.f970i);
        }
        int[] iArr = R$styleable.AppCompatTextView;
        C c2 = this.f969h;
        Context context2 = c2.f983i;
        TypedArray typedArrayObtainStyledAttributes3 = context2.obtainStyledAttributes(attributeSet, iArr, i2, 0);
        if (typedArrayObtainStyledAttributes3.hasValue(R$styleable.AppCompatTextView_autoSizeTextType)) {
            c2.f975a = typedArrayObtainStyledAttributes3.getInt(R$styleable.AppCompatTextView_autoSizeTextType, 0);
        }
        float dimension = typedArrayObtainStyledAttributes3.hasValue(R$styleable.AppCompatTextView_autoSizeStepGranularity) ? typedArrayObtainStyledAttributes3.getDimension(R$styleable.AppCompatTextView_autoSizeStepGranularity, -1.0f) : -1.0f;
        float dimension2 = typedArrayObtainStyledAttributes3.hasValue(R$styleable.AppCompatTextView_autoSizeMinTextSize) ? typedArrayObtainStyledAttributes3.getDimension(R$styleable.AppCompatTextView_autoSizeMinTextSize, -1.0f) : -1.0f;
        float dimension3 = typedArrayObtainStyledAttributes3.hasValue(R$styleable.AppCompatTextView_autoSizeMaxTextSize) ? typedArrayObtainStyledAttributes3.getDimension(R$styleable.AppCompatTextView_autoSizeMaxTextSize, -1.0f) : -1.0f;
        if (typedArrayObtainStyledAttributes3.hasValue(R$styleable.AppCompatTextView_autoSizePresetSizes) && (resourceId = typedArrayObtainStyledAttributes3.getResourceId(R$styleable.AppCompatTextView_autoSizePresetSizes, 0)) > 0) {
            TypedArray typedArrayObtainTypedArray = typedArrayObtainStyledAttributes3.getResources().obtainTypedArray(resourceId);
            int length = typedArrayObtainTypedArray.length();
            int[] iArr2 = new int[length];
            if (length > 0) {
                for (int i4 = 0; i4 < length; i4++) {
                    iArr2[i4] = typedArrayObtainTypedArray.getDimensionPixelSize(i4, -1);
                }
                c2.f980f = C.a(iArr2);
                c2.c();
            }
            typedArrayObtainTypedArray.recycle();
        }
        typedArrayObtainStyledAttributes3.recycle();
        if (!c2.d()) {
            c2.f975a = 0;
        } else if (c2.f975a == 1) {
            if (!c2.f981g) {
                DisplayMetrics displayMetrics = context2.getResources().getDisplayMetrics();
                if (dimension2 == -1.0f) {
                    dimension2 = TypedValue.applyDimension(2, 12.0f, displayMetrics);
                }
                if (dimension3 == -1.0f) {
                    dimension3 = TypedValue.applyDimension(2, 112.0f, displayMetrics);
                }
                if (dimension == -1.0f) {
                    dimension = 1.0f;
                }
                c2.e(dimension2, dimension3, dimension);
            }
            c2.b();
        }
        if (c2.f975a != 0) {
            int[] iArr3 = c2.f980f;
            if (iArr3.length > 0) {
                if (textView.getAutoSizeStepGranularity() != -1.0f) {
                    textView.setAutoSizeTextTypeUniformWithConfiguration(Math.round(c2.f978d), Math.round(c2.f979e), Math.round(c2.f977c), 0);
                } else {
                    textView.setAutoSizeTextTypeUniformWithPresetSizes(iArr3, 0);
                }
            }
        }
        TypedArray typedArrayObtainStyledAttributes4 = context.obtainStyledAttributes(attributeSet, R$styleable.AppCompatTextView);
        int dimensionPixelSize = typedArrayObtainStyledAttributes4.getDimensionPixelSize(R$styleable.AppCompatTextView_firstBaselineToTopHeight, -1);
        int dimensionPixelSize2 = typedArrayObtainStyledAttributes4.getDimensionPixelSize(R$styleable.AppCompatTextView_lastBaselineToBottomHeight, -1);
        int dimensionPixelSize3 = typedArrayObtainStyledAttributes4.getDimensionPixelSize(R$styleable.AppCompatTextView_lineHeight, -1);
        typedArrayObtainStyledAttributes4.recycle();
        if (dimensionPixelSize != -1) {
            if (dimensionPixelSize < 0) {
                throw new IllegalArgumentException();
            }
            textView.setFirstBaselineToTopHeight(dimensionPixelSize);
        }
        if (dimensionPixelSize2 != -1) {
            if (dimensionPixelSize2 < 0) {
                throw new IllegalArgumentException();
            }
            Paint.FontMetricsInt fontMetricsInt = textView.getPaint().getFontMetricsInt();
            int i5 = textView.getIncludeFontPadding() ? fontMetricsInt.bottom : fontMetricsInt.descent;
            if (dimensionPixelSize2 > Math.abs(i5)) {
                textView.setPadding(textView.getPaddingLeft(), textView.getPaddingTop(), textView.getPaddingRight(), dimensionPixelSize2 - i5);
            }
        }
        if (dimensionPixelSize3 != -1) {
            if (dimensionPixelSize3 < 0) {
                throw new IllegalArgumentException();
            }
            if (dimensionPixelSize3 != textView.getPaint().getFontMetricsInt(null)) {
                textView.setLineSpacing(dimensionPixelSize3 - r0, 1.0f);
            }
        }
    }

    public final void e(Context context, int i2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(i2, R$styleable.TextAppearance);
        f0.b bVar = new f0.b(context, typedArrayObtainStyledAttributes);
        boolean zHasValue = typedArrayObtainStyledAttributes.hasValue(R$styleable.TextAppearance_textAllCaps);
        TextView textView = this.f962a;
        if (zHasValue) {
            textView.setAllCaps(typedArrayObtainStyledAttributes.getBoolean(R$styleable.TextAppearance_textAllCaps, false));
        }
        if (typedArrayObtainStyledAttributes.hasValue(R$styleable.TextAppearance_android_textSize) && typedArrayObtainStyledAttributes.getDimensionPixelSize(R$styleable.TextAppearance_android_textSize, -1) == 0) {
            textView.setTextSize(0, 0.0f);
        }
        f(context, bVar);
        bVar.n();
        Typeface typeface = this.f971j;
        if (typeface != null) {
            textView.setTypeface(typeface, this.f970i);
        }
    }

    public final void f(Context context, f0.b bVar) {
        String string;
        int i2 = R$styleable.TextAppearance_android_textStyle;
        int i3 = this.f970i;
        TypedArray typedArray = (TypedArray) bVar.f2538c;
        this.f970i = typedArray.getInt(i2, i3);
        if (typedArray.hasValue(R$styleable.TextAppearance_android_fontFamily) || typedArray.hasValue(R$styleable.TextAppearance_fontFamily)) {
            this.f971j = null;
            int i4 = typedArray.hasValue(R$styleable.TextAppearance_fontFamily) ? R$styleable.TextAppearance_fontFamily : R$styleable.TextAppearance_android_fontFamily;
            if (!context.isRestricted()) {
                try {
                    Typeface typefaceH = bVar.h(i4, this.f970i, new C0084v(this, new WeakReference(this.f962a)));
                    this.f971j = typefaceH;
                    this.f972k = typefaceH == null;
                } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
                }
            }
            if (this.f971j != null || (string = typedArray.getString(i4)) == null) {
                return;
            }
            this.f971j = Typeface.create(string, this.f970i);
            return;
        }
        if (typedArray.hasValue(R$styleable.TextAppearance_android_typeface)) {
            this.f972k = false;
            int i5 = typedArray.getInt(R$styleable.TextAppearance_android_typeface, 1);
            if (i5 == 1) {
                this.f971j = Typeface.SANS_SERIF;
            } else if (i5 == 2) {
                this.f971j = Typeface.SERIF;
            } else {
                if (i5 != 3) {
                    return;
                }
                this.f971j = Typeface.MONOSPACE;
            }
        }
    }
}
