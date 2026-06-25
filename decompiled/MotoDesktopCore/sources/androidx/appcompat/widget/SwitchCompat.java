package androidx.appcompat.widget;

import C.C0002c;
import android.R;
import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.CompoundButton;
import b.AbstractC0122a;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
public class SwitchCompat extends CompoundButton {

    /* JADX INFO: renamed from: v, reason: collision with root package name */
    public static final C0002c f1115v = new C0002c(Float.class, "thumbPos", 7);

    /* JADX INFO: renamed from: w, reason: collision with root package name */
    public static final int[] f1116w = {R.attr.state_checked};

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public Drawable f1117a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public ColorStateList f1118b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public PorterDuff.Mode f1119c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f1120d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f1121e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public Drawable f1122f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public ColorStateList f1123g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public PorterDuff.Mode f1124h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public boolean f1125i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public boolean f1126j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public int f1127k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public int f1128l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public int f1129m;

    /* JADX INFO: renamed from: n, reason: collision with root package name */
    public boolean f1130n;

    /* JADX INFO: renamed from: o, reason: collision with root package name */
    public CharSequence f1131o;

    /* JADX INFO: renamed from: p, reason: collision with root package name */
    public CharSequence f1132p;

    /* JADX INFO: renamed from: q, reason: collision with root package name */
    public boolean f1133q;
    public float r;

    /* JADX INFO: renamed from: s, reason: collision with root package name */
    public StaticLayout f1134s;

    /* JADX INFO: renamed from: t, reason: collision with root package name */
    public StaticLayout f1135t;

    /* JADX INFO: renamed from: u, reason: collision with root package name */
    public ObjectAnimator f1136u;

    private boolean getTargetCheckedState() {
        return this.r > 0.5f;
    }

    private int getThumbOffset() {
        return (int) (((y0.a(this) ? 1.0f - this.r : this.r) * getThumbScrollRange()) + 0.5f);
    }

    private int getThumbScrollRange() {
        Drawable drawable = this.f1122f;
        if (drawable == null) {
            return 0;
        }
        drawable.getPadding(null);
        Drawable drawable2 = this.f1117a;
        if (drawable2 != null) {
            G.b(drawable2);
            throw null;
        }
        Rect rect = G.f992a;
        throw null;
    }

    public final void a() {
        Drawable drawable = this.f1117a;
        if (drawable != null) {
            if (this.f1120d || this.f1121e) {
                Drawable drawableMutate = drawable.mutate();
                this.f1117a = drawableMutate;
                if (this.f1120d) {
                    drawableMutate.setTintList(this.f1118b);
                }
                if (this.f1121e) {
                    this.f1117a.setTintMode(this.f1119c);
                }
                if (this.f1117a.isStateful()) {
                    this.f1117a.setState(getDrawableState());
                }
            }
        }
    }

    public final void b() {
        Drawable drawable = this.f1122f;
        if (drawable != null) {
            if (this.f1125i || this.f1126j) {
                Drawable drawableMutate = drawable.mutate();
                this.f1122f = drawableMutate;
                if (this.f1125i) {
                    drawableMutate.setTintList(this.f1123g);
                }
                if (this.f1126j) {
                    this.f1122f.setTintMode(this.f1124h);
                }
                if (this.f1122f.isStateful()) {
                    this.f1122f.setState(getDrawableState());
                }
            }
        }
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        getThumbOffset();
        Drawable drawable = this.f1117a;
        if (drawable != null) {
            G.b(drawable);
        } else {
            Rect rect = G.f992a;
        }
        Drawable drawable2 = this.f1122f;
        if (drawable2 != null) {
            drawable2.getPadding(null);
            throw null;
        }
        Drawable drawable3 = this.f1117a;
        if (drawable3 == null) {
            super.draw(canvas);
        } else {
            drawable3.getPadding(null);
            throw null;
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableHotspotChanged(float f2, float f3) {
        super.drawableHotspotChanged(f2, f3);
        Drawable drawable = this.f1117a;
        if (drawable != null) {
            drawable.setHotspot(f2, f3);
        }
        Drawable drawable2 = this.f1122f;
        if (drawable2 != null) {
            drawable2.setHotspot(f2, f3);
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.f1117a;
        boolean state = (drawable == null || !drawable.isStateful()) ? false : drawable.setState(drawableState);
        Drawable drawable2 = this.f1122f;
        if (drawable2 != null && drawable2.isStateful()) {
            state |= drawable2.setState(drawableState);
        }
        if (state) {
            invalidate();
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public int getCompoundPaddingLeft() {
        if (!y0.a(this)) {
            return super.getCompoundPaddingLeft();
        }
        int compoundPaddingLeft = super.getCompoundPaddingLeft();
        return !TextUtils.isEmpty(getText()) ? compoundPaddingLeft + this.f1129m : compoundPaddingLeft;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView
    public int getCompoundPaddingRight() {
        if (y0.a(this)) {
            return super.getCompoundPaddingRight();
        }
        int compoundPaddingRight = super.getCompoundPaddingRight();
        return !TextUtils.isEmpty(getText()) ? compoundPaddingRight + this.f1129m : compoundPaddingRight;
    }

    public boolean getShowText() {
        return this.f1133q;
    }

    public boolean getSplitTrack() {
        return this.f1130n;
    }

    public int getSwitchMinWidth() {
        return this.f1128l;
    }

    public int getSwitchPadding() {
        return this.f1129m;
    }

    public CharSequence getTextOff() {
        return this.f1132p;
    }

    public CharSequence getTextOn() {
        return this.f1131o;
    }

    public Drawable getThumbDrawable() {
        return this.f1117a;
    }

    public int getThumbTextPadding() {
        return this.f1127k;
    }

    public ColorStateList getThumbTintList() {
        return this.f1118b;
    }

    public PorterDuff.Mode getThumbTintMode() {
        return this.f1119c;
    }

    public Drawable getTrackDrawable() {
        return this.f1122f;
    }

    public ColorStateList getTrackTintList() {
        return this.f1123g;
    }

    public PorterDuff.Mode getTrackTintMode() {
        return this.f1124h;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.f1117a;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
        Drawable drawable2 = this.f1122f;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
        }
        ObjectAnimator objectAnimator = this.f1136u;
        if (objectAnimator == null || !objectAnimator.isStarted()) {
            return;
        }
        this.f1136u.end();
        this.f1136u = null;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final int[] onCreateDrawableState(int i2) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i2 + 1);
        if (isChecked()) {
            CompoundButton.mergeDrawableStates(iArrOnCreateDrawableState, f1116w);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Drawable drawable = this.f1122f;
        drawable.getClass();
        drawable.getPadding(null);
        throw null;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName("android.widget.Switch");
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("android.widget.Switch");
        CharSequence charSequence = isChecked() ? this.f1131o : this.f1132p;
        if (TextUtils.isEmpty(charSequence)) {
            return;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        if (TextUtils.isEmpty(text)) {
            accessibilityNodeInfo.setText(charSequence);
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(text);
        sb.append(' ');
        sb.append(charSequence);
        accessibilityNodeInfo.setText(sb);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.f1117a != null) {
            this.f1122f.getClass();
            this.f1122f.getPadding(null);
            int i6 = G.b(this.f1117a).left;
            throw null;
        }
        if (y0.a(this)) {
            getPaddingLeft();
        } else {
            getWidth();
            getPaddingRight();
        }
        int gravity = getGravity() & 112;
        if (gravity == 16) {
            getPaddingTop();
            getHeight();
            getPaddingBottom();
        } else if (gravity != 80) {
            getPaddingTop();
        } else {
            getHeight();
            getPaddingBottom();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void onMeasure(int i2, int i3) {
        if (this.f1133q) {
            if (this.f1134s == null) {
                CharSequence charSequence = this.f1131o;
                this.f1134s = new StaticLayout(charSequence, null, charSequence != null ? (int) Math.ceil(Layout.getDesiredWidth(charSequence, null)) : 0, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            }
            if (this.f1135t == null) {
                CharSequence charSequence2 = this.f1132p;
                this.f1135t = new StaticLayout(charSequence2, null, charSequence2 != null ? (int) Math.ceil(Layout.getDesiredWidth(charSequence2, null)) : 0, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
            }
        }
        Drawable drawable = this.f1117a;
        if (drawable != null) {
            drawable.getPadding(null);
            this.f1117a.getIntrinsicWidth();
            throw null;
        }
        Math.max(this.f1133q ? (this.f1127k * 2) + Math.max(this.f1134s.getWidth(), this.f1135t.getWidth()) : 0, 0);
        this.f1122f.getClass();
        this.f1122f.getPadding(null);
        this.f1122f.getIntrinsicHeight();
        throw null;
    }

    @Override // android.view.View
    public final void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        CharSequence charSequence = isChecked() ? this.f1131o : this.f1132p;
        if (charSequence != null) {
            accessibilityEvent.getText().add(charSequence);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        throw null;
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z2) {
        super.setChecked(z2);
        boolean zIsChecked = isChecked();
        if (getWindowToken() != null) {
            WeakHashMap weakHashMap = v.l.f2836a;
            if (isLaidOut()) {
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(this, f1115v, zIsChecked ? 1.0f : 0.0f);
                this.f1136u = objectAnimatorOfFloat;
                objectAnimatorOfFloat.setDuration(250L);
                this.f1136u.setAutoCancel(true);
                this.f1136u.start();
                return;
            }
        }
        ObjectAnimator objectAnimator = this.f1136u;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        setThumbPosition(zIsChecked ? 1.0f : 0.0f);
    }

    @Override // android.widget.TextView
    public void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(callback);
    }

    public void setShowText(boolean z2) {
        if (this.f1133q != z2) {
            this.f1133q = z2;
            requestLayout();
        }
    }

    public void setSplitTrack(boolean z2) {
        this.f1130n = z2;
        invalidate();
    }

    public void setSwitchMinWidth(int i2) {
        this.f1128l = i2;
        requestLayout();
    }

    public void setSwitchPadding(int i2) {
        this.f1129m = i2;
        requestLayout();
    }

    public void setSwitchTypeface(Typeface typeface) {
        throw null;
    }

    public void setTextOff(CharSequence charSequence) {
        this.f1132p = charSequence;
        requestLayout();
    }

    public void setTextOn(CharSequence charSequence) {
        this.f1131o = charSequence;
        requestLayout();
    }

    public void setThumbDrawable(Drawable drawable) {
        Drawable drawable2 = this.f1117a;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.f1117a = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public void setThumbPosition(float f2) {
        this.r = f2;
        invalidate();
    }

    public void setThumbResource(int i2) {
        setThumbDrawable(AbstractC0122a.a(getContext(), i2));
    }

    public void setThumbTextPadding(int i2) {
        this.f1127k = i2;
        requestLayout();
    }

    public void setThumbTintList(ColorStateList colorStateList) {
        this.f1118b = colorStateList;
        this.f1120d = true;
        a();
    }

    public void setThumbTintMode(PorterDuff.Mode mode) {
        this.f1119c = mode;
        this.f1121e = true;
        a();
    }

    public void setTrackDrawable(Drawable drawable) {
        Drawable drawable2 = this.f1122f;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.f1122f = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        requestLayout();
    }

    public void setTrackResource(int i2) {
        setTrackDrawable(AbstractC0122a.a(getContext(), i2));
    }

    public void setTrackTintList(ColorStateList colorStateList) {
        this.f1123g = colorStateList;
        this.f1125i = true;
        b();
    }

    public void setTrackTintMode(PorterDuff.Mode mode) {
        this.f1124h = mode;
        this.f1126j = true;
        b();
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public final void toggle() {
        setChecked(!isChecked());
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.f1117a || drawable == this.f1122f;
    }
}
