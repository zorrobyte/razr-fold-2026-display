package com.google.android.material.bottomnavigation;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.FrameLayout;
import androidx.customview.view.AbsSavedState;
import d.i;

/* JADX INFO: loaded from: classes.dex */
public class BottomNavigationView extends FrameLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public i f2106a;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new e();

        /* JADX INFO: renamed from: c, reason: collision with root package name */
        public Bundle f2107c;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.f2107c = parcel.readBundle(classLoader);
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            super.writeToParcel(parcel, i2);
            parcel.writeBundle(this.f2107c);
        }
    }

    private MenuInflater getMenuInflater() {
        if (this.f2106a == null) {
            this.f2106a = new i(getContext());
        }
        return this.f2106a;
    }

    public Drawable getItemBackground() {
        throw null;
    }

    @Deprecated
    public int getItemBackgroundResource() {
        throw null;
    }

    public int getItemIconSize() {
        throw null;
    }

    public ColorStateList getItemIconTintList() {
        throw null;
    }

    public int getItemTextAppearanceActive() {
        throw null;
    }

    public int getItemTextAppearanceInactive() {
        throw null;
    }

    public ColorStateList getItemTextColor() {
        throw null;
    }

    public int getLabelVisibilityMode() {
        throw null;
    }

    public int getMaxItemCount() {
        return 5;
    }

    public Menu getMenu() {
        return null;
    }

    public int getSelectedItemId() {
        throw null;
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
        new SavedState(super.onSaveInstanceState()).f2107c = new Bundle();
        throw null;
    }

    public void setItemBackground(Drawable drawable) {
        throw null;
    }

    public void setItemBackgroundResource(int i2) {
        throw null;
    }

    public void setItemHorizontalTranslationEnabled(boolean z2) {
        throw null;
    }

    public void setItemIconSize(int i2) {
        throw null;
    }

    public void setItemIconSizeRes(int i2) {
        setItemIconSize(getResources().getDimensionPixelSize(i2));
    }

    public void setItemIconTintList(ColorStateList colorStateList) {
        throw null;
    }

    public void setItemTextAppearanceActive(int i2) {
        throw null;
    }

    public void setItemTextAppearanceInactive(int i2) {
        throw null;
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        throw null;
    }

    public void setLabelVisibilityMode(int i2) {
        throw null;
    }

    public void setOnNavigationItemReselectedListener(c cVar) {
    }

    public void setOnNavigationItemSelectedListener(d dVar) {
    }

    public void setSelectedItemId(int i2) {
        throw null;
    }
}
