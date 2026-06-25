package com.google.android.setupdesign.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.setupdesign.R$styleable;

/* JADX INFO: loaded from: classes.dex */
public class HeaderRecyclerView extends RecyclerView {
    private View header;
    private int headerRes;
    private boolean shouldApplyAdditionalMargin;
    private boolean shouldHandleActionUp;

    public class HeaderAdapter extends RecyclerView.Adapter {
        private final RecyclerView.Adapter adapter;
        private View header;
        private final RecyclerView.AdapterDataObserver observer;

        public HeaderAdapter(RecyclerView.Adapter adapter) {
            RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() { // from class: com.google.android.setupdesign.view.HeaderRecyclerView.HeaderAdapter.1
            };
            this.observer = adapterDataObserver;
            this.adapter = adapter;
            adapter.registerAdapterDataObserver(adapterDataObserver);
            setHasStableIds(adapter.hasStableIds());
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            int itemCount = this.adapter.getItemCount();
            return this.header != null ? itemCount + 1 : itemCount;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public long getItemId(int i) {
            if (this.header != null) {
                i--;
            }
            if (i < 0) {
                return Long.MAX_VALUE;
            }
            return this.adapter.getItemId(i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemViewType(int i) {
            if (this.header != null) {
                i--;
            }
            if (i < 0) {
                return Integer.MAX_VALUE;
            }
            return this.adapter.getItemViewType(i);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
            View view = this.header;
            if (view != null) {
                i--;
            }
            if (!(viewHolder instanceof HeaderViewHolder)) {
                this.adapter.onBindViewHolder(viewHolder, i);
            } else {
                if (view == null) {
                    throw new IllegalStateException("HeaderViewHolder cannot find mHeader");
                }
                if (view.getParent() != null) {
                    ((ViewGroup) this.header.getParent()).removeView(this.header);
                }
                ((FrameLayout) viewHolder.itemView).addView(this.header);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            if (i != Integer.MAX_VALUE) {
                return this.adapter.onCreateViewHolder(viewGroup, i);
            }
            FrameLayout frameLayout = new FrameLayout(viewGroup.getContext());
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -2));
            return new HeaderViewHolder(frameLayout);
        }

        public void setHeader(View view) {
            this.header = view;
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        HeaderViewHolder(View view) {
            super(view);
        }
    }

    public HeaderRecyclerView(Context context) {
        super(context);
        this.shouldHandleActionUp = false;
        init(null, 0);
    }

    public HeaderRecyclerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.shouldHandleActionUp = false;
        init(attributeSet, 0);
    }

    public HeaderRecyclerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.shouldHandleActionUp = false;
        init(attributeSet, i);
    }

    private boolean handleDpadDown() {
        View viewFindFocus = findFocus();
        if (viewFindFocus == null) {
            return false;
        }
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        viewFindFocus.getLocationInWindow(iArr);
        getLocationInWindow(iArr2);
        int measuredHeight = (iArr[1] + viewFindFocus.getMeasuredHeight()) - (iArr2[1] + getMeasuredHeight());
        if (measuredHeight <= 0) {
            return false;
        }
        smoothScrollBy(0, Math.min((int) (getMeasuredHeight() * 0.7f), measuredHeight));
        return true;
    }

    private boolean handleDpadUp() {
        View viewFindFocus = findFocus();
        if (viewFindFocus == null) {
            return false;
        }
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        viewFindFocus.getLocationInWindow(iArr);
        getLocationInWindow(iArr2);
        int i = iArr[1] - iArr2[1];
        if (i >= 0) {
            return false;
        }
        smoothScrollBy(0, Math.max((int) (getMeasuredHeight() * (-0.7f)), i));
        return true;
    }

    private boolean handleKeyEvent(KeyEvent keyEvent) {
        boolean zHandleDpadUp = false;
        if (this.shouldHandleActionUp && keyEvent.getAction() == 1) {
            this.shouldHandleActionUp = false;
            return true;
        }
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 19) {
                zHandleDpadUp = handleDpadUp();
            } else if (keyCode == 20) {
                zHandleDpadUp = handleDpadDown();
            }
            this.shouldHandleActionUp = zHandleDpadUp;
        }
        return zHandleDpadUp;
    }

    private void init(AttributeSet attributeSet, int i) {
        if (isInEditMode()) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.SudHeaderRecyclerView, i, 0);
        this.headerRes = typedArrayObtainStyledAttributes.getResourceId(R$styleable.SudHeaderRecyclerView_sudHeader, 0);
        this.shouldApplyAdditionalMargin = typedArrayObtainStyledAttributes.getBoolean(R$styleable.SudHeaderRecyclerView_sudShouldApplyAdditionalMargin, false);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (handleKeyEvent(keyEvent)) {
            return true;
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    public View getHeader() {
        return this.header;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        int i = this.header != null ? 1 : 0;
        accessibilityEvent.setItemCount(accessibilityEvent.getItemCount() - i);
        accessibilityEvent.setFromIndex(Math.max(accessibilityEvent.getFromIndex() - i, 0));
        accessibilityEvent.setToIndex(Math.max(accessibilityEvent.getToIndex() - i, 0));
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setAdapter(RecyclerView.Adapter adapter) {
        if (this.header != null && adapter != null) {
            HeaderAdapter headerAdapter = new HeaderAdapter(adapter);
            headerAdapter.setHeader(this.header);
            adapter = headerAdapter;
        }
        super.setAdapter(adapter);
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);
        if (layoutManager == null || this.header != null || this.headerRes == 0) {
            return;
        }
        this.header = LayoutInflater.from(getContext()).inflate(this.headerRes, (ViewGroup) this, false);
    }
}
