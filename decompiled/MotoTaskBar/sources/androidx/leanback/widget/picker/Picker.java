package androidx.leanback.widget.picker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.core.view.ViewCompat;
import androidx.leanback.R$attr;
import androidx.leanback.R$dimen;
import androidx.leanback.R$id;
import androidx.leanback.R$layout;
import androidx.leanback.R$styleable;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class Picker extends FrameLayout {
    private int mAlphaAnimDuration;
    private final OnChildViewHolderSelectedListener mColumnChangeListener;
    final List mColumnViews;
    ArrayList mColumns;
    private Interpolator mDecelerateInterpolator;
    private float mFocusedAlpha;
    private float mInvisibleColumnAlpha;
    private ArrayList mListeners;
    private int mPickerItemLayoutId;
    private int mPickerItemTextViewId;
    private ViewGroup mPickerView;
    private int mSelectedColumn;
    private List mSeparators;
    private float mUnfocusedAlpha;
    private float mVisibleColumnAlpha;
    private float mVisibleItems;
    private float mVisibleItemsActivated;

    class PickerScrollArrayAdapter extends RecyclerView.Adapter {
        private final int mColIndex;
        private PickerColumn mData;
        private final int mResource;
        private final int mTextViewResourceId;

        PickerScrollArrayAdapter(int i, int i2, int i3) {
            this.mResource = i;
            this.mColIndex = i3;
            this.mTextViewResourceId = i2;
            this.mData = (PickerColumn) Picker.this.mColumns.get(i3);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            PickerColumn pickerColumn = this.mData;
            if (pickerColumn == null) {
                return 0;
            }
            return pickerColumn.getCount();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            PickerColumn pickerColumn;
            TextView textView = viewHolder.textView;
            if (textView != null && (pickerColumn = this.mData) != null) {
                textView.setText(pickerColumn.getLabelFor(pickerColumn.getMinValue() + i));
            }
            Picker picker = Picker.this;
            picker.setOrAnimateAlpha(viewHolder.itemView, ((VerticalGridView) picker.mColumnViews.get(this.mColIndex)).getSelectedPosition() == i, this.mColIndex, false);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(this.mResource, viewGroup, false);
            int i2 = this.mTextViewResourceId;
            return new ViewHolder(viewInflate, i2 != 0 ? (TextView) viewInflate.findViewById(i2) : (TextView) viewInflate);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onViewAttachedToWindow(ViewHolder viewHolder) {
            viewHolder.itemView.setFocusable(Picker.this.isActivated());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        ViewHolder(View view, TextView textView) {
            super(view);
            this.textView = textView;
        }
    }

    public Picker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R$attr.pickerStyle);
    }

    public Picker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mColumnViews = new ArrayList();
        this.mVisibleItemsActivated = 3.0f;
        this.mVisibleItems = 1.0f;
        this.mSelectedColumn = 0;
        this.mSeparators = new ArrayList();
        this.mColumnChangeListener = new OnChildViewHolderSelectedListener() { // from class: androidx.leanback.widget.picker.Picker.1
            @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
            public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i2, int i3) {
                int iIndexOf = Picker.this.mColumnViews.indexOf((VerticalGridView) recyclerView);
                Picker.this.updateColumnAlpha(iIndexOf, true);
                if (viewHolder != null) {
                    Picker.this.onColumnValueChanged(iIndexOf, ((PickerColumn) Picker.this.mColumns.get(iIndexOf)).getMinValue() + i2);
                }
            }
        };
        int[] iArr = R$styleable.lbPicker;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, 0);
        ViewCompat.saveAttributeDataForStyleable(this, context, iArr, attributeSet, typedArrayObtainStyledAttributes, i, 0);
        this.mPickerItemLayoutId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.lbPicker_pickerItemLayout, R$layout.lb_picker_item);
        this.mPickerItemTextViewId = typedArrayObtainStyledAttributes.getResourceId(R$styleable.lbPicker_pickerItemTextViewId, 0);
        typedArrayObtainStyledAttributes.recycle();
        setEnabled(true);
        setDescendantFocusability(262144);
        this.mFocusedAlpha = 1.0f;
        this.mUnfocusedAlpha = 1.0f;
        this.mVisibleColumnAlpha = 0.5f;
        this.mInvisibleColumnAlpha = 0.0f;
        this.mAlphaAnimDuration = 200;
        this.mDecelerateInterpolator = new DecelerateInterpolator(2.5f);
        this.mPickerView = (ViewGroup) ((ViewGroup) LayoutInflater.from(getContext()).inflate(R$layout.lb_picker, (ViewGroup) this, true)).findViewById(R$id.picker);
    }

    private void notifyValueChanged(int i) {
        int size;
        if (this.mListeners == null || r1.size() - 1 < 0) {
            return;
        }
        ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mListeners.get(size));
        throw null;
    }

    private void setOrAnimateAlpha(View view, boolean z, float f, float f2, Interpolator interpolator) {
        view.animate().cancel();
        if (!z) {
            view.setAlpha(f);
            return;
        }
        if (f2 >= 0.0f) {
            view.setAlpha(f2);
        }
        view.animate().alpha(f).setDuration(this.mAlphaAnimDuration).setInterpolator(interpolator).start();
    }

    private void updateColumnSize() {
        for (int i = 0; i < getColumnsCount(); i++) {
            updateColumnSize((VerticalGridView) this.mColumnViews.get(i));
        }
    }

    private void updateColumnSize(VerticalGridView verticalGridView) {
        ViewGroup.LayoutParams layoutParams = verticalGridView.getLayoutParams();
        float activatedVisibleItemCount = isActivated() ? getActivatedVisibleItemCount() : getVisibleItemCount();
        layoutParams.height = (int) ((getPickerItemHeightPixels() * activatedVisibleItemCount) + (verticalGridView.getVerticalSpacing() * (activatedVisibleItemCount - 1.0f)));
        verticalGridView.setLayoutParams(layoutParams);
    }

    private void updateItemFocusable() {
        boolean zIsActivated = isActivated();
        for (int i = 0; i < getColumnsCount(); i++) {
            VerticalGridView verticalGridView = (VerticalGridView) this.mColumnViews.get(i);
            for (int i2 = 0; i2 < verticalGridView.getChildCount(); i2++) {
                verticalGridView.getChildAt(i2).setFocusable(zIsActivated);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (!isActivated()) {
            return super.dispatchKeyEvent(keyEvent);
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyCode != 23 && keyCode != 66) {
            return super.dispatchKeyEvent(keyEvent);
        }
        if (keyEvent.getAction() == 1) {
            performClick();
        }
        return true;
    }

    public float getActivatedVisibleItemCount() {
        return this.mVisibleItemsActivated;
    }

    public PickerColumn getColumnAt(int i) {
        ArrayList arrayList = this.mColumns;
        if (arrayList == null) {
            return null;
        }
        return (PickerColumn) arrayList.get(i);
    }

    public int getColumnsCount() {
        ArrayList arrayList = this.mColumns;
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    protected int getPickerItemHeightPixels() {
        return getContext().getResources().getDimensionPixelSize(R$dimen.picker_item_height);
    }

    public final int getPickerItemLayoutId() {
        return this.mPickerItemLayoutId;
    }

    public final int getPickerItemTextViewId() {
        return this.mPickerItemTextViewId;
    }

    public int getSelectedColumn() {
        return this.mSelectedColumn;
    }

    public float getVisibleItemCount() {
        return 1.0f;
    }

    public void onColumnValueChanged(int i, int i2) {
        PickerColumn pickerColumn = (PickerColumn) this.mColumns.get(i);
        if (pickerColumn.getCurrentValue() != i2) {
            pickerColumn.setCurrentValue(i2);
            notifyValueChanged(i);
        }
    }

    @Override // android.view.ViewGroup
    protected boolean onRequestFocusInDescendants(int i, Rect rect) {
        int selectedColumn = getSelectedColumn();
        if (selectedColumn < 0 || selectedColumn >= this.mColumnViews.size()) {
            return false;
        }
        return ((VerticalGridView) this.mColumnViews.get(selectedColumn)).requestFocus(i, rect);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        super.requestChildFocus(view, view2);
        for (int i = 0; i < this.mColumnViews.size(); i++) {
            if (((VerticalGridView) this.mColumnViews.get(i)).hasFocus()) {
                setSelectedColumn(i);
            }
        }
    }

    @Override // android.view.View
    public void setActivated(boolean z) {
        if (z == isActivated()) {
            super.setActivated(z);
            return;
        }
        super.setActivated(z);
        boolean zHasFocus = hasFocus();
        int selectedColumn = getSelectedColumn();
        setDescendantFocusability(131072);
        if (!z && zHasFocus && isFocusable()) {
            requestFocus();
        }
        for (int i = 0; i < getColumnsCount(); i++) {
            ((VerticalGridView) this.mColumnViews.get(i)).setFocusable(z);
        }
        updateColumnSize();
        updateItemFocusable();
        if (z && zHasFocus && selectedColumn >= 0) {
            ((VerticalGridView) this.mColumnViews.get(selectedColumn)).requestFocus();
        }
        setDescendantFocusability(262144);
    }

    public void setColumnAt(int i, PickerColumn pickerColumn) {
        this.mColumns.set(i, pickerColumn);
        VerticalGridView verticalGridView = (VerticalGridView) this.mColumnViews.get(i);
        PickerScrollArrayAdapter pickerScrollArrayAdapter = (PickerScrollArrayAdapter) verticalGridView.getAdapter();
        if (pickerScrollArrayAdapter != null) {
            pickerScrollArrayAdapter.notifyDataSetChanged();
        }
        verticalGridView.setSelectedPosition(pickerColumn.getCurrentValue() - pickerColumn.getMinValue());
    }

    public void setColumnValue(int i, int i2, boolean z) {
        PickerColumn pickerColumn = (PickerColumn) this.mColumns.get(i);
        if (pickerColumn.getCurrentValue() != i2) {
            pickerColumn.setCurrentValue(i2);
            notifyValueChanged(i);
            VerticalGridView verticalGridView = (VerticalGridView) this.mColumnViews.get(i);
            if (verticalGridView != null) {
                int minValue = i2 - ((PickerColumn) this.mColumns.get(i)).getMinValue();
                if (z) {
                    verticalGridView.setSelectedPositionSmooth(minValue);
                } else {
                    verticalGridView.setSelectedPosition(minValue);
                }
            }
        }
    }

    public void setColumns(List list) {
        if (this.mSeparators.size() == 0) {
            throw new IllegalStateException("Separators size is: " + this.mSeparators.size() + ". At least one separator must be provided");
        }
        if (this.mSeparators.size() == 1) {
            CharSequence charSequence = (CharSequence) this.mSeparators.get(0);
            this.mSeparators.clear();
            this.mSeparators.add("");
            for (int i = 0; i < list.size() - 1; i++) {
                this.mSeparators.add(charSequence);
            }
            this.mSeparators.add("");
        } else if (this.mSeparators.size() != list.size() + 1) {
            throw new IllegalStateException("Separators size: " + this.mSeparators.size() + " mustequal the size of columns: " + list.size() + " + 1");
        }
        this.mColumnViews.clear();
        this.mPickerView.removeAllViews();
        ArrayList arrayList = new ArrayList(list);
        this.mColumns = arrayList;
        if (this.mSelectedColumn > arrayList.size() - 1) {
            this.mSelectedColumn = this.mColumns.size() - 1;
        }
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(getContext());
        int columnsCount = getColumnsCount();
        if (!TextUtils.isEmpty((CharSequence) this.mSeparators.get(0))) {
            TextView textView = (TextView) layoutInflaterFrom.inflate(R$layout.lb_picker_separator, this.mPickerView, false);
            textView.setText((CharSequence) this.mSeparators.get(0));
            this.mPickerView.addView(textView);
        }
        int i2 = 0;
        while (i2 < columnsCount) {
            VerticalGridView verticalGridView = (VerticalGridView) layoutInflaterFrom.inflate(R$layout.lb_picker_column, this.mPickerView, false);
            updateColumnSize(verticalGridView);
            verticalGridView.setWindowAlignment(0);
            verticalGridView.setHasFixedSize(false);
            verticalGridView.setFocusable(isActivated());
            verticalGridView.setItemViewCacheSize(0);
            this.mColumnViews.add(verticalGridView);
            this.mPickerView.addView(verticalGridView);
            int i3 = i2 + 1;
            if (!TextUtils.isEmpty((CharSequence) this.mSeparators.get(i3))) {
                TextView textView2 = (TextView) layoutInflaterFrom.inflate(R$layout.lb_picker_separator, this.mPickerView, false);
                textView2.setText((CharSequence) this.mSeparators.get(i3));
                this.mPickerView.addView(textView2);
            }
            verticalGridView.setAdapter(new PickerScrollArrayAdapter(getPickerItemLayoutId(), getPickerItemTextViewId(), i2));
            verticalGridView.setOnChildViewHolderSelectedListener(this.mColumnChangeListener);
            i2 = i3;
        }
    }

    void setOrAnimateAlpha(View view, boolean z, int i, boolean z2) {
        boolean z3 = i == this.mSelectedColumn || !hasFocus();
        if (z) {
            if (z3) {
                setOrAnimateAlpha(view, z2, this.mFocusedAlpha, -1.0f, this.mDecelerateInterpolator);
                return;
            } else {
                setOrAnimateAlpha(view, z2, this.mUnfocusedAlpha, -1.0f, this.mDecelerateInterpolator);
                return;
            }
        }
        if (z3) {
            setOrAnimateAlpha(view, z2, this.mVisibleColumnAlpha, -1.0f, this.mDecelerateInterpolator);
        } else {
            setOrAnimateAlpha(view, z2, this.mInvisibleColumnAlpha, -1.0f, this.mDecelerateInterpolator);
        }
    }

    public void setSelectedColumn(int i) {
        if (this.mSelectedColumn != i) {
            this.mSelectedColumn = i;
            for (int i2 = 0; i2 < this.mColumnViews.size(); i2++) {
                updateColumnAlpha(i2, true);
            }
        }
        VerticalGridView verticalGridView = (VerticalGridView) this.mColumnViews.get(i);
        if (!hasFocus() || verticalGridView.hasFocus()) {
            return;
        }
        verticalGridView.requestFocus();
    }

    public final void setSeparator(CharSequence charSequence) {
        setSeparators(Arrays.asList(charSequence));
    }

    public final void setSeparators(List list) {
        this.mSeparators.clear();
        this.mSeparators.addAll(list);
    }

    void updateColumnAlpha(int i, boolean z) {
        VerticalGridView verticalGridView = (VerticalGridView) this.mColumnViews.get(i);
        int selectedPosition = verticalGridView.getSelectedPosition();
        int i2 = 0;
        while (i2 < verticalGridView.getAdapter().getItemCount()) {
            View viewFindViewByPosition = verticalGridView.getLayoutManager().findViewByPosition(i2);
            if (viewFindViewByPosition != null) {
                setOrAnimateAlpha(viewFindViewByPosition, selectedPosition == i2, i, z);
            }
            i2++;
        }
    }
}
