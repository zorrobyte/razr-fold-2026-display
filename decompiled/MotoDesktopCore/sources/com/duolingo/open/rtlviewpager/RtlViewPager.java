package com.duolingo.open.rtlviewpager;

import F.k;
import G.b;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import androidx.viewpager.widget.ViewPager;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class RtlViewPager extends ViewPager {

    /* JADX INFO: renamed from: f0, reason: collision with root package name */
    public static final /* synthetic */ int f2019f0 = 0;

    /* JADX INFO: renamed from: d0, reason: collision with root package name */
    public final HashMap f2020d0;

    /* JADX INFO: renamed from: e0, reason: collision with root package name */
    public int f2021e0;

    public static class SavedState implements Parcelable {
        public static final Parcelable.ClassLoaderCreator<SavedState> CREATOR = new a();

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        public final Parcelable f2022a;

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        public final int f2023b;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            this.f2022a = parcel.readParcelable(classLoader == null ? getClass().getClassLoader() : classLoader);
            this.f2023b = parcel.readInt();
        }

        public SavedState(ViewPager.SavedState savedState, int i2) {
            this.f2022a = savedState;
            this.f2023b = i2;
        }

        @Override // android.os.Parcelable
        public final int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i2) {
            parcel.writeParcelable(this.f2022a, i2);
            parcel.writeInt(this.f2023b);
        }
    }

    public RtlViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f2020d0 = new HashMap();
        this.f2021e0 = 0;
    }

    public final boolean A() {
        return this.f2021e0 == 1;
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final void b(k kVar) {
        b bVar = new b(this, kVar);
        this.f2020d0.put(kVar, bVar);
        super.b(bVar);
    }

    @Override // androidx.viewpager.widget.ViewPager
    public F.a getAdapter() {
        G.a aVar = (G.a) super.getAdapter();
        if (aVar == null) {
            return null;
        }
        return aVar.f138c;
    }

    @Override // androidx.viewpager.widget.ViewPager
    public int getCurrentItem() {
        int currentItem = super.getCurrentItem();
        return (super.getAdapter() == null || !A()) ? currentItem : (r1.d() - currentItem) - 1;
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public void onMeasure(int i2, int i3) {
        if (View.MeasureSpec.getMode(i3) == 0) {
            int i4 = 0;
            for (int i5 = 0; i5 < getChildCount(); i5++) {
                View childAt = getChildAt(i5);
                childAt.measure(i2, View.MeasureSpec.makeMeasureSpec(0, 0));
                int measuredHeight = childAt.getMeasuredHeight();
                if (measuredHeight > i4) {
                    i4 = measuredHeight;
                }
            }
            i3 = View.MeasureSpec.makeMeasureSpec(i4, 1073741824);
        }
        super.onMeasure(i2, i3);
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        this.f2021e0 = savedState.f2023b;
        super.onRestoreInstanceState(savedState.f2022a);
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i2) {
        super.onRtlPropertiesChanged(i2);
        int i3 = i2 != 1 ? 0 : 1;
        if (i3 != this.f2021e0) {
            F.a adapter = super.getAdapter();
            int currentItem = adapter != null ? getCurrentItem() : 0;
            this.f2021e0 = i3;
            if (adapter != null) {
                adapter.j();
                setCurrentItem(currentItem);
            }
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public final Parcelable onSaveInstanceState() {
        return new SavedState((ViewPager.SavedState) super.onSaveInstanceState(), this.f2021e0);
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void setAdapter(F.a aVar) {
        if (aVar != null) {
            aVar = new G.a(this, aVar);
        }
        super.setAdapter(aVar);
        setCurrentItem(0);
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void setCurrentItem(int i2) {
        F.a adapter = super.getAdapter();
        if (adapter != null && A()) {
            i2 = (adapter.d() - i2) - 1;
        }
        super.setCurrentItem(i2);
    }

    @Override // androidx.viewpager.widget.ViewPager
    @Deprecated
    public void setOnPageChangeListener(k kVar) {
        super.setOnPageChangeListener(new b(this, kVar));
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final void t(k kVar) {
        b bVar = (b) this.f2020d0.remove(kVar);
        if (bVar != null) {
            super.t(bVar);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager
    public final void w(int i2) {
        F.a adapter = super.getAdapter();
        if (adapter != null && A()) {
            i2 = (adapter.d() - i2) - 1;
        }
        super.w(i2);
    }
}
