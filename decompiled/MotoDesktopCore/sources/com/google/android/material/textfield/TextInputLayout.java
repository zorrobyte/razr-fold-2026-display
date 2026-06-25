package com.google.android.material.textfield;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.G;
import androidx.customview.view.AbsSavedState;
import b.AbstractC0122a;
import com.google.android.material.R$color;
import com.google.android.material.R$id;
import com.google.android.material.R$layout;
import com.google.android.material.R$style;
import com.google.android.material.internal.CheckableImageButton;
import java.util.WeakHashMap;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public class TextInputLayout extends LinearLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public EditText f2212a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f2213b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public AppCompatTextView f2214c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f2215d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public CharSequence f2216e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f2217f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public GradientDrawable f2218g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public int f2219h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public int f2220i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public Drawable f2221j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public Typeface f2222k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public boolean f2223l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public Drawable f2224m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public CharSequence f2225n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public CheckableImageButton f2226o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public boolean f2227p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public ColorDrawable f2228q;
    public Drawable r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public ColorStateList f2229s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public boolean f2230t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public PorterDuff.Mode f2231u;

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public boolean f2232v;

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public ColorStateList f2233w;

    /* JADX INFO: renamed from: x, reason: collision with root package name */
    public ColorStateList f2234x;

    /* JADX INFO: renamed from: y, reason: collision with root package name */
    public int f2235y;

    /* JADX INFO: renamed from: z, reason: collision with root package name */
    public boolean f2236z;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new c();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public final CharSequence f2237c;

        /* JADX INFO: renamed from: d, reason: collision with root package name */
        public final boolean f2238d;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f2237c = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.f2238d = parcel.readInt() == 1;
        }

        public final String toString() {
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " error=" + ((Object) this.f2237c) + "}";
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            TextUtils.writeToParcel(this.f2237c, parcel, i2);
            parcel.writeInt(this.f2238d ? 1 : 0);
        }
    }

    public static void d(ViewGroup viewGroup, boolean z2) {
        int childCount = viewGroup.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = viewGroup.getChildAt(i2);
            childAt.setEnabled(z2);
            if (childAt instanceof ViewGroup) {
                d((ViewGroup) childAt, z2);
            }
        }
    }

    private Drawable getBoxBackground() {
        int i2 = this.f2219h;
        if (i2 == 1 || i2 == 2) {
            return this.f2218g;
        }
        throw new IllegalStateException();
    }

    private float[] getCornerRadiiAsArray() {
        WeakHashMap weakHashMap = l.f2836a;
        return getLayoutDirection() == 1 ? new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f} : new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    }

    private void setEditText(EditText editText) {
        if (this.f2212a != null) {
            throw new IllegalArgumentException("We already have an EditText, can only have one");
        }
        if (!(editText instanceof TextInputEditText)) {
            Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead.");
        }
        this.f2212a = editText;
        c();
        setTextInputAccessibilityDelegate(new b(this));
        EditText editText2 = this.f2212a;
        if (editText2 != null && (editText2.getTransformationMethod() instanceof PasswordTransformationMethod)) {
            this.f2212a.getTextSize();
            throw null;
        }
        this.f2212a.getTypeface();
        throw null;
    }

    private void setHintInternal(CharSequence charSequence) {
        if (TextUtils.equals(charSequence, this.f2216e)) {
            return;
        }
        this.f2216e = charSequence;
        throw null;
    }

    public final void a() {
        Drawable drawable;
        if (this.f2218g == null) {
            return;
        }
        int i2 = this.f2219h;
        if (i2 != 1 && i2 == 2 && this.f2235y == 0) {
            this.f2235y = this.f2234x.getColorForState(getDrawableState(), this.f2234x.getDefaultColor());
        }
        EditText editText = this.f2212a;
        if (editText != null && this.f2219h == 2) {
            if (editText.getBackground() != null) {
                this.f2221j = this.f2212a.getBackground();
            }
            EditText editText2 = this.f2212a;
            WeakHashMap weakHashMap = l.f2836a;
            editText2.setBackground(null);
        }
        EditText editText3 = this.f2212a;
        if (editText3 != null && this.f2219h == 1 && (drawable = this.f2221j) != null) {
            WeakHashMap weakHashMap2 = l.f2836a;
            editText3.setBackground(drawable);
        }
        this.f2218g.setCornerRadii(getCornerRadiiAsArray());
        this.f2218g.setColor(this.f2220i);
        invalidate();
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i2, ViewGroup.LayoutParams layoutParams) {
        if (!(view instanceof EditText)) {
            super.addView(view, i2, layoutParams);
        } else {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
            layoutParams2.gravity = (layoutParams2.gravity & (-113)) | 16;
            throw null;
        }
    }

    public final void b() {
        Drawable drawable = this.f2224m;
        if (drawable != null) {
            if (this.f2230t || this.f2232v) {
                Drawable drawableMutate = drawable.mutate();
                this.f2224m = drawableMutate;
                if (this.f2230t) {
                    drawableMutate.setTintList(this.f2229s);
                }
                if (this.f2232v) {
                    this.f2224m.setTintMode(this.f2231u);
                }
                CheckableImageButton checkableImageButton = this.f2226o;
                if (checkableImageButton != null) {
                    Drawable drawable2 = checkableImageButton.getDrawable();
                    Drawable drawable3 = this.f2224m;
                    if (drawable2 != drawable3) {
                        this.f2226o.setImageDrawable(drawable3);
                    }
                }
            }
        }
    }

    public final void c() {
        int i2 = this.f2219h;
        if (i2 == 0) {
            this.f2218g = null;
        } else if (i2 == 2 && this.f2215d && !(this.f2218g instanceof a)) {
            this.f2218g = new a();
        } else if (!(this.f2218g instanceof GradientDrawable)) {
            this.f2218g = new GradientDrawable();
        }
        if (this.f2219h != 0) {
            throw null;
        }
        g();
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i2) {
        super.dispatchProvideAutofillStructure(viewStructure, i2);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchRestoreInstanceState(SparseArray sparseArray) {
        super.dispatchRestoreInstanceState(sparseArray);
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        GradientDrawable gradientDrawable = this.f2218g;
        if (gradientDrawable != null) {
            gradientDrawable.draw(canvas);
        }
        super.draw(canvas);
        if (this.f2215d) {
            throw null;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        if (this.f2236z) {
            return;
        }
        this.f2236z = true;
        super.drawableStateChanged();
        getDrawableState();
        WeakHashMap weakHashMap = l.f2836a;
        if (isLaidOut()) {
            isEnabled();
        }
        isEnabled();
        EditText editText = this.f2212a;
        if (editText != null) {
            TextUtils.isEmpty(editText.getText());
        }
        EditText editText2 = this.f2212a;
        if (editText2 != null) {
            editText2.hasFocus();
        }
        throw null;
    }

    public final void e(TextView textView) {
        try {
            textView.setTextAppearance(0);
            if (textView.getTextColors().getDefaultColor() != -65281) {
                return;
            }
        } catch (Exception unused) {
        }
        textView.setTextAppearance(R$style.TextAppearance_AppCompat_Caption);
        textView.setTextColor(getContext().getColor(R$color.design_error));
    }

    public final void f() {
        EditText editText = this.f2212a;
        if (editText == null) {
            return;
        }
        if (!this.f2223l || ((editText == null || !(editText.getTransformationMethod() instanceof PasswordTransformationMethod)) && !this.f2227p)) {
            CheckableImageButton checkableImageButton = this.f2226o;
            if (checkableImageButton != null && checkableImageButton.getVisibility() == 0) {
                this.f2226o.setVisibility(8);
            }
            if (this.f2228q != null) {
                Drawable[] compoundDrawablesRelative = this.f2212a.getCompoundDrawablesRelative();
                if (compoundDrawablesRelative[2] == this.f2228q) {
                    this.f2212a.setCompoundDrawablesRelative(compoundDrawablesRelative[0], compoundDrawablesRelative[1], this.r, compoundDrawablesRelative[3]);
                    this.f2228q = null;
                    return;
                }
                return;
            }
            return;
        }
        if (this.f2226o == null) {
            CheckableImageButton checkableImageButton2 = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(R$layout.design_text_input_password_icon, (ViewGroup) null, false);
            this.f2226o = checkableImageButton2;
            checkableImageButton2.setImageDrawable(this.f2224m);
            this.f2226o.setContentDescription(this.f2225n);
            throw null;
        }
        EditText editText2 = this.f2212a;
        if (editText2 != null) {
            WeakHashMap weakHashMap = l.f2836a;
            if (editText2.getMinimumHeight() <= 0) {
                this.f2212a.setMinimumHeight(this.f2226o.getMinimumHeight());
            }
        }
        this.f2226o.setVisibility(0);
        this.f2226o.setChecked(this.f2227p);
        if (this.f2228q == null) {
            this.f2228q = new ColorDrawable();
        }
        this.f2228q.setBounds(0, 0, this.f2226o.getMeasuredWidth(), 1);
        Drawable[] compoundDrawablesRelative2 = this.f2212a.getCompoundDrawablesRelative();
        Drawable drawable = compoundDrawablesRelative2[2];
        ColorDrawable colorDrawable = this.f2228q;
        if (drawable != colorDrawable) {
            this.r = drawable;
        }
        this.f2212a.setCompoundDrawablesRelative(compoundDrawablesRelative2[0], compoundDrawablesRelative2[1], colorDrawable, compoundDrawablesRelative2[3]);
        this.f2226o.setPadding(this.f2212a.getPaddingLeft(), this.f2212a.getPaddingTop(), this.f2212a.getPaddingRight(), this.f2212a.getPaddingBottom());
    }

    public final void g() {
        Drawable background;
        int i2;
        if (this.f2219h == 0 || this.f2218g == null || this.f2212a == null || getRight() == 0) {
            return;
        }
        int left = this.f2212a.getLeft();
        EditText editText = this.f2212a;
        int top = 0;
        if (editText != null) {
            int i3 = this.f2219h;
            if (i3 == 1) {
                top = editText.getTop();
            } else if (i3 == 2) {
                top = editText.getTop();
                if (this.f2215d && ((i2 = this.f2219h) == 0 || i2 == 1 || i2 == 2)) {
                    throw null;
                }
            }
        }
        this.f2218g.setBounds(left, top, this.f2212a.getRight(), this.f2212a.getBottom());
        a();
        EditText editText2 = this.f2212a;
        if (editText2 == null || (background = editText2.getBackground()) == null) {
            return;
        }
        if (G.a(background)) {
            background = background.mutate();
        }
        P.a.a(this, this.f2212a, new Rect());
        Rect bounds = background.getBounds();
        if (bounds.left != bounds.right) {
            Rect rect = new Rect();
            background.getPadding(rect);
            background.setBounds(bounds.left - rect.left, bounds.top, (rect.right * 2) + bounds.right, this.f2212a.getBottom());
        }
    }

    public int getBoxBackgroundColor() {
        return this.f2220i;
    }

    public float getBoxCornerRadiusBottomEnd() {
        return 0.0f;
    }

    public float getBoxCornerRadiusBottomStart() {
        return 0.0f;
    }

    public float getBoxCornerRadiusTopEnd() {
        return 0.0f;
    }

    public float getBoxCornerRadiusTopStart() {
        return 0.0f;
    }

    public int getBoxStrokeColor() {
        return this.f2235y;
    }

    public int getCounterMaxLength() {
        return this.f2213b;
    }

    public CharSequence getCounterOverflowDescription() {
        return null;
    }

    public ColorStateList getDefaultHintTextColor() {
        return this.f2233w;
    }

    public EditText getEditText() {
        return this.f2212a;
    }

    public CharSequence getError() {
        throw null;
    }

    public int getErrorCurrentTextColors() {
        throw null;
    }

    public final int getErrorTextCurrentColor() {
        throw null;
    }

    public CharSequence getHelperText() {
        throw null;
    }

    public int getHelperTextCurrentTextColor() {
        throw null;
    }

    public CharSequence getHint() {
        if (this.f2215d) {
            return this.f2216e;
        }
        return null;
    }

    public final float getHintCollapsedTextHeight() {
        throw null;
    }

    public final int getHintCurrentCollapsedTextColor() {
        throw null;
    }

    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.f2225n;
    }

    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.f2224m;
    }

    public Typeface getTypeface() {
        return this.f2222k;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        EditText editText;
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.f2218g != null) {
            g();
        }
        if (!this.f2215d || (editText = this.f2212a) == null) {
            return;
        }
        P.a.a(this, editText, null);
        throw null;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i2, int i3) {
        f();
        super.onMeasure(i2, i3);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.f1465a);
        setError(savedState.f2237c);
        if (savedState.f2238d && this.f2223l) {
            int selectionEnd = this.f2212a.getSelectionEnd();
            EditText editText = this.f2212a;
            if (editText == null || !(editText.getTransformationMethod() instanceof PasswordTransformationMethod)) {
                this.f2212a.setTransformationMethod(PasswordTransformationMethod.getInstance());
                this.f2227p = false;
            } else {
                this.f2212a.setTransformationMethod(null);
                this.f2227p = true;
            }
            this.f2226o.setChecked(this.f2227p);
            this.f2226o.jumpDrawablesToCurrentState();
            this.f2212a.setSelection(selectionEnd);
        }
        requestLayout();
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        new SavedState(super.onSaveInstanceState());
        throw null;
    }

    public void setBoxBackgroundColor(int i2) {
        if (this.f2220i != i2) {
            this.f2220i = i2;
            a();
        }
    }

    public void setBoxBackgroundColorResource(int i2) {
        setBoxBackgroundColor(getContext().getColor(i2));
    }

    public void setBoxBackgroundMode(int i2) {
        if (i2 == this.f2219h) {
            return;
        }
        this.f2219h = i2;
        c();
    }

    public void setBoxStrokeColor(int i2) {
        if (this.f2235y != i2) {
            this.f2235y = i2;
            if (this.f2218g == null || this.f2219h == 0) {
                return;
            }
            EditText editText = this.f2212a;
            boolean z2 = editText != null && editText.hasFocus();
            EditText editText2 = this.f2212a;
            boolean z3 = editText2 != null && editText2.isHovered();
            if (this.f2219h == 2) {
                if (isEnabled()) {
                    throw null;
                }
                if (z3 || z2) {
                    isEnabled();
                }
                a();
            }
        }
    }

    public void setCounterEnabled(boolean z2) {
        if (z2) {
            if (!z2) {
                throw null;
            }
            AppCompatTextView appCompatTextView = new AppCompatTextView(getContext(), null);
            this.f2214c = appCompatTextView;
            appCompatTextView.setId(R$id.textinput_counter);
            Typeface typeface = this.f2222k;
            if (typeface != null) {
                this.f2214c.setTypeface(typeface);
            }
            this.f2214c.setMaxLines(1);
            e(this.f2214c);
            throw null;
        }
    }

    public void setCounterMaxLength(int i2) {
        if (this.f2213b != i2) {
            if (i2 > 0) {
                this.f2213b = i2;
            } else {
                this.f2213b = -1;
            }
        }
    }

    public void setDefaultHintTextColor(ColorStateList colorStateList) {
        this.f2233w = colorStateList;
        this.f2234x = colorStateList;
        if (this.f2212a == null) {
            return;
        }
        isEnabled();
        EditText editText = this.f2212a;
        if (editText != null) {
            TextUtils.isEmpty(editText.getText());
        }
        EditText editText2 = this.f2212a;
        if (editText2 != null) {
            editText2.hasFocus();
        }
        throw null;
    }

    @Override // android.view.View
    public void setEnabled(boolean z2) {
        d(this, z2);
        super.setEnabled(z2);
    }

    public void setError(CharSequence charSequence) {
        throw null;
    }

    public void setErrorEnabled(boolean z2) {
        throw null;
    }

    public void setErrorTextAppearance(int i2) {
        throw null;
    }

    public void setErrorTextColor(ColorStateList colorStateList) {
        throw null;
    }

    public void setHelperText(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            throw null;
        }
        throw null;
    }

    public void setHelperTextColor(ColorStateList colorStateList) {
        throw null;
    }

    public void setHelperTextEnabled(boolean z2) {
        throw null;
    }

    public void setHelperTextTextAppearance(int i2) {
        throw null;
    }

    public void setHint(CharSequence charSequence) {
        if (this.f2215d) {
            setHintInternal(charSequence);
            sendAccessibilityEvent(2048);
        }
    }

    public void setHintAnimationEnabled(boolean z2) {
    }

    public void setHintEnabled(boolean z2) {
        if (z2 != this.f2215d) {
            this.f2215d = z2;
            if (z2) {
                CharSequence hint = this.f2212a.getHint();
                if (!TextUtils.isEmpty(hint)) {
                    if (TextUtils.isEmpty(this.f2216e)) {
                        setHint(hint);
                    }
                    this.f2212a.setHint((CharSequence) null);
                }
                this.f2217f = true;
            } else {
                this.f2217f = false;
                if (!TextUtils.isEmpty(this.f2216e) && TextUtils.isEmpty(this.f2212a.getHint())) {
                    this.f2212a.setHint(this.f2216e);
                }
                setHintInternal(null);
            }
            if (this.f2212a != null) {
                throw null;
            }
        }
    }

    public void setHintTextAppearance(int i2) {
        throw null;
    }

    public void setPasswordVisibilityToggleContentDescription(int i2) {
        setPasswordVisibilityToggleContentDescription(i2 != 0 ? getResources().getText(i2) : null);
    }

    public void setPasswordVisibilityToggleContentDescription(CharSequence charSequence) {
        this.f2225n = charSequence;
        CheckableImageButton checkableImageButton = this.f2226o;
        if (checkableImageButton != null) {
            checkableImageButton.setContentDescription(charSequence);
        }
    }

    public void setPasswordVisibilityToggleDrawable(int i2) {
        setPasswordVisibilityToggleDrawable(i2 != 0 ? AbstractC0122a.a(getContext(), i2) : null);
    }

    public void setPasswordVisibilityToggleDrawable(Drawable drawable) {
        this.f2224m = drawable;
        CheckableImageButton checkableImageButton = this.f2226o;
        if (checkableImageButton != null) {
            checkableImageButton.setImageDrawable(drawable);
        }
    }

    public void setPasswordVisibilityToggleEnabled(boolean z2) {
        EditText editText;
        if (this.f2223l != z2) {
            this.f2223l = z2;
            if (!z2 && this.f2227p && (editText = this.f2212a) != null) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            this.f2227p = false;
            f();
        }
    }

    public void setPasswordVisibilityToggleTintList(ColorStateList colorStateList) {
        this.f2229s = colorStateList;
        this.f2230t = true;
        b();
    }

    public void setPasswordVisibilityToggleTintMode(PorterDuff.Mode mode) {
        this.f2231u = mode;
        this.f2232v = true;
        b();
    }

    public void setTextInputAccessibilityDelegate(b bVar) {
        EditText editText = this.f2212a;
        if (editText != null) {
            l.b(editText, bVar);
        }
    }

    public void setTypeface(Typeface typeface) {
        if (typeface == this.f2222k) {
            return;
        }
        this.f2222k = typeface;
        throw null;
    }
}
