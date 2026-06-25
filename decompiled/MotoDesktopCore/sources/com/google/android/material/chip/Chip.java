package com.google.android.material.chip;

import H.c;
import R.a;
import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatCheckBox;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import x.AbstractC0164a;

/* JADX INFO: loaded from: classes.dex */
public class Chip extends AppCompatCheckBox {

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public static final int[] f2141g;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public View.OnClickListener f2142b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f2143c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public boolean f2144d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public boolean f2145e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public boolean f2146f;

    static {
        new Rect();
        f2141g = new int[]{R.attr.state_selected};
    }

    private RectF getCloseIconTouchBounds() {
        throw null;
    }

    private Rect getCloseIconTouchBoundsInt() {
        float f2 = getCloseIconTouchBounds().left;
        throw null;
    }

    private a getTextAppearance() {
        return null;
    }

    private void setCloseIconFocused(boolean z2) {
        if (this.f2146f != z2) {
            this.f2146f = z2;
            refreshDrawableState();
        }
    }

    private void setCloseIconHovered(boolean z2) {
        if (this.f2145e != z2) {
            this.f2145e = z2;
            refreshDrawableState();
        }
    }

    private void setCloseIconPressed(boolean z2) {
        if (this.f2144d != z2) {
            this.f2144d = z2;
            refreshDrawableState();
        }
    }

    private void setFocusedVirtualView(int i2) {
        int i3 = this.f2143c;
        if (i3 != i2) {
            if (i3 == 0) {
                setCloseIconFocused(false);
            }
            this.f2143c = i2;
            if (i2 == 0) {
                setCloseIconFocused(true);
            }
        }
    }

    public final boolean a(boolean z2) {
        if (this.f2143c == Integer.MIN_VALUE) {
            setFocusedVirtualView(-1);
        }
        if (z2) {
            if (this.f2143c == -1) {
                setFocusedVirtualView(0);
                return true;
            }
        } else if (this.f2143c == 0) {
            setFocusedVirtualView(-1);
            return true;
        }
        return false;
    }

    @Override // android.view.View
    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() != 10) {
            throw null;
        }
        try {
            Field declaredField = AbstractC0164a.class.getDeclaredField("c");
            declaredField.setAccessible(true);
            if (((Integer) declaredField.get(null)).intValue() == Integer.MIN_VALUE) {
                throw null;
            }
            Method declaredMethod = AbstractC0164a.class.getDeclaredMethod("f", Integer.TYPE);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(null, Integer.MIN_VALUE);
            return true;
        } catch (IllegalAccessException e2) {
            Log.e("Chip", "Unable to send Accessibility Exit event", e2);
            throw null;
        } catch (NoSuchFieldException e3) {
            Log.e("Chip", "Unable to send Accessibility Exit event", e3);
            throw null;
        } catch (NoSuchMethodException e4) {
            Log.e("Chip", "Unable to send Accessibility Exit event", e4);
            throw null;
        } catch (InvocationTargetException e5) {
            Log.e("Chip", "Unable to send Accessibility Exit event", e5);
            throw null;
        }
    }

    @Override // android.view.View
    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        throw null;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
    }

    public Drawable getCheckedIcon() {
        return null;
    }

    public ColorStateList getChipBackgroundColor() {
        return null;
    }

    public float getChipCornerRadius() {
        return 0.0f;
    }

    public Drawable getChipDrawable() {
        return null;
    }

    public float getChipEndPadding() {
        return 0.0f;
    }

    public Drawable getChipIcon() {
        return null;
    }

    public float getChipIconSize() {
        return 0.0f;
    }

    public ColorStateList getChipIconTint() {
        return null;
    }

    public float getChipMinHeight() {
        return 0.0f;
    }

    public float getChipStartPadding() {
        return 0.0f;
    }

    public ColorStateList getChipStrokeColor() {
        return null;
    }

    public float getChipStrokeWidth() {
        return 0.0f;
    }

    @Deprecated
    public CharSequence getChipText() {
        return getText();
    }

    public Drawable getCloseIcon() {
        return null;
    }

    public CharSequence getCloseIconContentDescription() {
        return null;
    }

    public float getCloseIconEndPadding() {
        return 0.0f;
    }

    public float getCloseIconSize() {
        return 0.0f;
    }

    public float getCloseIconStartPadding() {
        return 0.0f;
    }

    public ColorStateList getCloseIconTint() {
        return null;
    }

    @Override // android.widget.TextView
    public TextUtils.TruncateAt getEllipsize() {
        return null;
    }

    @Override // android.widget.TextView, android.view.View
    public final void getFocusedRect(Rect rect) {
        if (this.f2143c == 0) {
            rect.set(getCloseIconTouchBoundsInt());
        } else {
            super.getFocusedRect(rect);
        }
    }

    public c getHideMotionSpec() {
        return null;
    }

    public float getIconEndPadding() {
        return 0.0f;
    }

    public float getIconStartPadding() {
        return 0.0f;
    }

    public ColorStateList getRippleColor() {
        return null;
    }

    public c getShowMotionSpec() {
        return null;
    }

    @Override // android.widget.TextView
    public CharSequence getText() {
        return "";
    }

    public float getTextEndPadding() {
        return 0.0f;
    }

    public float getTextStartPadding() {
        return 0.0f;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final int[] onCreateDrawableState(int i2) {
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i2 + 1);
        if (isChecked()) {
            CheckBox.mergeDrawableStates(iArrOnCreateDrawableState, f2141g);
        }
        return iArrOnCreateDrawableState;
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    public final void onDraw(Canvas canvas) {
        TextUtils.isEmpty(getText());
        super.onDraw(canvas);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onFocusChanged(boolean z2, int i2, Rect rect) {
        if (z2) {
            setFocusedVirtualView(-1);
        } else {
            setFocusedVirtualView(Integer.MIN_VALUE);
        }
        invalidate();
        super.onFocusChanged(z2, i2, rect);
        throw null;
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 7) {
            setCloseIconHovered(getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY()));
        } else if (actionMasked == 10) {
            setCloseIconHovered(false);
        }
        return super.onHoverEvent(motionEvent);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003d  */
    @Override // android.widget.TextView, android.view.View, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onKeyDown(int r7, android.view.KeyEvent r8) {
        /*
            r6 = this;
            int r0 = r8.getKeyCode()
            r1 = 61
            r2 = 1
            r3 = 0
            if (r0 == r1) goto L55
            r1 = 66
            if (r0 == r1) goto L3d
            switch(r0) {
                case 21: goto L29;
                case 22: goto L13;
                case 23: goto L3d;
                default: goto L11;
            }
        L11:
            goto L81
        L13:
            boolean r0 = r8.hasNoModifiers()
            if (r0 == 0) goto L81
            java.util.WeakHashMap r0 = v.l.f2836a
            int r0 = r6.getLayoutDirection()
            if (r0 != r2) goto L22
            r3 = r2
        L22:
            r0 = r3 ^ 1
            boolean r3 = r6.a(r0)
            goto L81
        L29:
            boolean r0 = r8.hasNoModifiers()
            if (r0 == 0) goto L81
            java.util.WeakHashMap r0 = v.l.f2836a
            int r0 = r6.getLayoutDirection()
            if (r0 != r2) goto L38
            r3 = r2
        L38:
            boolean r3 = r6.a(r3)
            goto L81
        L3d:
            int r0 = r6.f2143c
            r1 = -1
            if (r0 == r1) goto L51
            if (r0 == 0) goto L45
            goto L81
        L45:
            r6.playSoundEffect(r3)
            android.view.View$OnClickListener r7 = r6.f2142b
            if (r7 == 0) goto L4f
            r7.onClick(r6)
        L4f:
            r6 = 0
            throw r6
        L51:
            r6.performClick()
            return r2
        L55:
            boolean r0 = r8.hasNoModifiers()
            if (r0 == 0) goto L5d
            r0 = 2
            goto L66
        L5d:
            boolean r0 = r8.hasModifiers(r2)
            if (r0 == 0) goto L65
            r0 = r2
            goto L66
        L65:
            r0 = r3
        L66:
            if (r0 == 0) goto L81
            android.view.ViewParent r1 = r6.getParent()
            r4 = r6
        L6d:
            android.view.View r4 = r4.focusSearch(r0)
            if (r4 == 0) goto L7b
            if (r4 == r6) goto L7b
            android.view.ViewParent r5 = r4.getParent()
            if (r5 == r1) goto L6d
        L7b:
            if (r4 == 0) goto L81
            r4.requestFocus()
            return r2
        L81:
            if (r3 == 0) goto L87
            r6.invalidate()
            return r2
        L87:
            boolean r6 = super.onKeyDown(r7, r8)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.Chip.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    @Override // android.widget.Button, android.widget.TextView, android.view.View
    public final PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i2) {
        if (getCloseIconTouchBounds().contains(motionEvent.getX(), motionEvent.getY()) && isEnabled()) {
            return PointerIcon.getSystemIcon(getContext(), 1002);
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:? A[RETURN, SYNTHETIC] */
    @Override // android.widget.TextView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            int r0 = r6.getActionMasked()
            android.graphics.RectF r1 = r5.getCloseIconTouchBounds()
            float r2 = r6.getX()
            float r3 = r6.getY()
            boolean r1 = r1.contains(r2, r3)
            r2 = 0
            r3 = 1
            if (r0 == 0) goto L3f
            if (r0 == r3) goto L2b
            r4 = 2
            if (r0 == r4) goto L21
            r1 = 3
            if (r0 == r1) goto L2f
            goto L45
        L21:
            boolean r0 = r5.f2144d
            if (r0 == 0) goto L45
            if (r1 != 0) goto L4b
            r5.setCloseIconPressed(r2)
            goto L4b
        L2b:
            boolean r0 = r5.f2144d
            if (r0 != 0) goto L33
        L2f:
            r5.setCloseIconPressed(r2)
            goto L45
        L33:
            r5.playSoundEffect(r2)
            android.view.View$OnClickListener r6 = r5.f2142b
            if (r6 == 0) goto L3d
            r6.onClick(r5)
        L3d:
            r5 = 0
            throw r5
        L3f:
            if (r1 == 0) goto L45
            r5.setCloseIconPressed(r3)
            goto L4b
        L45:
            boolean r5 = super.onTouchEvent(r6)
            if (r5 == 0) goto L4c
        L4b:
            r2 = r3
        L4c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.chip.Chip.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
        if (drawable != null && drawable != null) {
            throw new UnsupportedOperationException("Do not set the background; Chip manages its own background drawable.");
        }
        super.setBackground(drawable);
    }

    @Override // android.view.View
    public void setBackgroundColor(int i2) {
        throw new UnsupportedOperationException("Do not set the background color; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        if (drawable != null && drawable != null) {
            throw new UnsupportedOperationException("Do not set the background drawable; Chip manages its own background drawable.");
        }
        super.setBackgroundDrawable(drawable);
    }

    @Override // android.view.View
    public void setBackgroundResource(int i2) {
        throw new UnsupportedOperationException("Do not set the background resource; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public void setBackgroundTintList(ColorStateList colorStateList) {
        throw new UnsupportedOperationException("Do not set the background tint list; Chip manages its own background drawable.");
    }

    @Override // android.view.View
    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        throw new UnsupportedOperationException("Do not set the background tint mode; Chip manages its own background drawable.");
    }

    public void setCheckable(boolean z2) {
    }

    public void setCheckableResource(int i2) {
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z2) {
    }

    public void setCheckedIcon(Drawable drawable) {
    }

    @Deprecated
    public void setCheckedIconEnabled(boolean z2) {
        setCheckedIconVisible(z2);
    }

    @Deprecated
    public void setCheckedIconEnabledResource(int i2) {
        setCheckedIconVisible(i2);
    }

    public void setCheckedIconResource(int i2) {
    }

    public void setCheckedIconVisible(int i2) {
    }

    public void setCheckedIconVisible(boolean z2) {
    }

    public void setChipBackgroundColor(ColorStateList colorStateList) {
    }

    public void setChipBackgroundColorResource(int i2) {
    }

    public void setChipCornerRadius(float f2) {
    }

    public void setChipCornerRadiusResource(int i2) {
    }

    public void setChipDrawable(L.a aVar) {
    }

    public void setChipEndPadding(float f2) {
    }

    public void setChipEndPaddingResource(int i2) {
    }

    public void setChipIcon(Drawable drawable) {
    }

    @Deprecated
    public void setChipIconEnabled(boolean z2) {
        setChipIconVisible(z2);
    }

    @Deprecated
    public void setChipIconEnabledResource(int i2) {
        setChipIconVisible(i2);
    }

    public void setChipIconResource(int i2) {
    }

    public void setChipIconSize(float f2) {
    }

    public void setChipIconSizeResource(int i2) {
    }

    public void setChipIconTint(ColorStateList colorStateList) {
    }

    public void setChipIconTintResource(int i2) {
    }

    public void setChipIconVisible(int i2) {
    }

    public void setChipIconVisible(boolean z2) {
    }

    public void setChipMinHeight(float f2) {
    }

    public void setChipMinHeightResource(int i2) {
    }

    public void setChipStartPadding(float f2) {
    }

    public void setChipStartPaddingResource(int i2) {
    }

    public void setChipStrokeColor(ColorStateList colorStateList) {
    }

    public void setChipStrokeColorResource(int i2) {
    }

    public void setChipStrokeWidth(float f2) {
    }

    public void setChipStrokeWidthResource(int i2) {
    }

    @Deprecated
    public void setChipText(CharSequence charSequence) {
        setText(charSequence);
    }

    @Deprecated
    public void setChipTextResource(int i2) {
        setText(getResources().getString(i2));
    }

    public void setCloseIcon(Drawable drawable) {
    }

    public void setCloseIconContentDescription(CharSequence charSequence) {
    }

    @Deprecated
    public void setCloseIconEnabled(boolean z2) {
        setCloseIconVisible(z2);
    }

    @Deprecated
    public void setCloseIconEnabledResource(int i2) {
        setCloseIconVisible(i2);
    }

    public void setCloseIconEndPadding(float f2) {
    }

    public void setCloseIconEndPaddingResource(int i2) {
    }

    public void setCloseIconResource(int i2) {
    }

    public void setCloseIconSize(float f2) {
    }

    public void setCloseIconSizeResource(int i2) {
    }

    public void setCloseIconStartPadding(float f2) {
    }

    public void setCloseIconStartPaddingResource(int i2) {
    }

    public void setCloseIconTint(ColorStateList colorStateList) {
    }

    public void setCloseIconTintResource(int i2) {
    }

    public void setCloseIconVisible(int i2) {
    }

    public void setCloseIconVisible(boolean z2) {
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawables(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (drawable3 != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawables(drawable, drawable2, drawable3, drawable4);
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (drawable3 != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(int i2, int i3, int i4, int i5) {
        if (i2 != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (i4 != 0) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(i2, i3, i4, i5);
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesRelativeWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (drawable3 != null) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesWithIntrinsicBounds(int i2, int i3, int i4, int i5) {
        if (i2 != 0) {
            throw new UnsupportedOperationException("Please set start drawable using R.attr#chipIcon.");
        }
        if (i4 != 0) {
            throw new UnsupportedOperationException("Please set end drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesWithIntrinsicBounds(i2, i3, i4, i5);
    }

    @Override // android.widget.TextView
    public final void setCompoundDrawablesWithIntrinsicBounds(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
        if (drawable != null) {
            throw new UnsupportedOperationException("Please set left drawable using R.attr#chipIcon.");
        }
        if (drawable3 != null) {
            throw new UnsupportedOperationException("Please set right drawable using R.attr#closeIcon.");
        }
        super.setCompoundDrawablesWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
    }

    @Override // android.widget.TextView
    public void setEllipsize(TextUtils.TruncateAt truncateAt) {
    }

    @Override // android.widget.TextView
    public void setGravity(int i2) {
        if (i2 != 8388627) {
            Log.w("Chip", "Chip text must be vertically center and start aligned");
        } else {
            super.setGravity(i2);
        }
    }

    public void setHideMotionSpec(c cVar) {
    }

    public void setHideMotionSpecResource(int i2) {
    }

    public void setIconEndPadding(float f2) {
    }

    public void setIconEndPaddingResource(int i2) {
    }

    public void setIconStartPadding(float f2) {
    }

    public void setIconStartPaddingResource(int i2) {
    }

    @Override // android.widget.TextView
    public void setLines(int i2) {
        if (i2 > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setLines(i2);
    }

    @Override // android.widget.TextView
    public void setMaxLines(int i2) {
        if (i2 > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setMaxLines(i2);
    }

    @Override // android.widget.TextView
    public void setMaxWidth(int i2) {
        super.setMaxWidth(i2);
    }

    @Override // android.widget.TextView
    public void setMinLines(int i2) {
        if (i2 > 1) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setMinLines(i2);
    }

    public void setOnCheckedChangeListenerInternal(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
    }

    public void setOnCloseIconClickListener(View.OnClickListener onClickListener) {
        this.f2142b = onClickListener;
    }

    public void setRippleColor(ColorStateList colorStateList) {
    }

    public void setRippleColorResource(int i2) {
    }

    public void setShowMotionSpec(c cVar) {
    }

    public void setShowMotionSpecResource(int i2) {
    }

    @Override // android.widget.TextView
    public void setSingleLine(boolean z2) {
        if (!z2) {
            throw new UnsupportedOperationException("Chip does not support multi-line text");
        }
        super.setSingleLine(z2);
    }

    @Override // android.widget.TextView
    public final void setText(CharSequence charSequence, TextView.BufferType bufferType) {
    }

    @Override // android.widget.TextView
    public void setTextAppearance(int i2) {
        super.setTextAppearance(i2);
        getTextAppearance();
    }

    public void setTextAppearance(a aVar) {
        getTextAppearance();
    }

    @Override // android.widget.TextView
    public final void setTextAppearance(Context context, int i2) {
        super.setTextAppearance(context, i2);
        getTextAppearance();
    }

    public void setTextAppearanceResource(int i2) {
        setTextAppearance(getContext(), i2);
    }

    public void setTextEndPadding(float f2) {
    }

    public void setTextEndPaddingResource(int i2) {
    }

    public void setTextStartPadding(float f2) {
    }

    public void setTextStartPaddingResource(int i2) {
    }
}
