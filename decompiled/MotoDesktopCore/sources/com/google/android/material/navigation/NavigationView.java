package com.google.android.material.navigation;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.customview.view.AbsSavedState;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import d.i;

/* JADX INFO: loaded from: classes.dex */
public class NavigationView extends ScrimInsetsFrameLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public i f2185a;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public Bundle f2186c;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f2186c = parcel.readBundle(classLoader);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeBundle(this.f2186c);
        }
    }

    private MenuInflater getMenuInflater() {
        if (this.f2185a == null) {
            this.f2185a = new i(getContext());
        }
        return this.f2185a;
    }

    public MenuItem getCheckedItem() {
        throw null;
    }

    public int getHeaderCount() {
        throw null;
    }

    public Drawable getItemBackground() {
        throw null;
    }

    public int getItemHorizontalPadding() {
        throw null;
    }

    public int getItemIconPadding() {
        throw null;
    }

    public ColorStateList getItemIconTintList() {
        throw null;
    }

    public ColorStateList getItemTextColor() {
        throw null;
    }

    public Menu getMenu() {
        return null;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE) {
            i2 = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(i2), 0), 1073741824);
        } else if (mode == 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(0, 1073741824);
        }
        super.onMeasure(i2, i3);
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            super.onRestoreInstanceState(((SavedState) parcelable).f1465a);
            throw null;
        }
        super.onRestoreInstanceState(parcelable);
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        new SavedState(super.onSaveInstanceState()).f2186c = new Bundle();
        throw null;
    }

    public void setCheckedItem(int i2) {
        throw null;
    }

    public void setCheckedItem(MenuItem menuItem) {
        menuItem.getItemId();
        throw null;
    }

    public void setItemBackground(Drawable drawable) {
        throw null;
    }

    public void setItemBackgroundResource(int i2) {
        setItemBackground(getContext().getDrawable(i2));
    }

    public void setItemHorizontalPadding(int i2) {
        throw null;
    }

    public void setItemHorizontalPaddingResource(int i2) {
        getResources().getDimensionPixelSize(i2);
        throw null;
    }

    public void setItemIconPadding(int i2) {
        throw null;
    }

    public void setItemIconPaddingResource(int i2) {
        getResources().getDimensionPixelSize(i2);
        throw null;
    }

    public void setItemIconTintList(ColorStateList colorStateList) {
        throw null;
    }

    public void setItemTextAppearance(int i2) {
        throw null;
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        throw null;
    }

    public void setNavigationItemSelectedListener(Q.a aVar) {
    }
}
