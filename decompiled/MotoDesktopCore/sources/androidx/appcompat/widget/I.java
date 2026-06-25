package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.R$attr;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes.dex */
public class I extends ListView {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public final Rect f995a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f996b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public int f997c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public int f998d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f999e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public int f1000f;

    /* JADX INFO: renamed from: g, reason: collision with root package name */
    public final Field f1001g;

    /* JADX INFO: renamed from: h, reason: collision with root package name */
    public H f1002h;

    /* JADX INFO: renamed from: i, reason: collision with root package name */
    public boolean f1003i;

    /* JADX INFO: renamed from: j, reason: collision with root package name */
    public final boolean f1004j;

    /* JADX INFO: renamed from: k, reason: collision with root package name */
    public boolean f1005k;

    /* JADX INFO: renamed from: l, reason: collision with root package name */
    public androidx.core.widget.b f1006l;

    /* JADX INFO: renamed from: m, reason: collision with root package name */
    public F.e f1007m;

    public I(Context context, boolean z2) {
        super(context, null, R$attr.dropDownListViewStyle);
        this.f995a = new Rect();
        this.f996b = 0;
        this.f997c = 0;
        this.f998d = 0;
        this.f999e = 0;
        this.f1004j = z2;
        setCacheColorHint(0);
        try {
            Field declaredField = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
            this.f1001g = declaredField;
            declaredField.setAccessible(true);
        } catch (NoSuchFieldException e2) {
            e2.printStackTrace();
        }
    }

    public final int a(int i2, int i3) {
        int listPaddingTop = getListPaddingTop();
        int listPaddingBottom = getListPaddingBottom();
        getListPaddingLeft();
        getListPaddingRight();
        int dividerHeight = getDividerHeight();
        Drawable divider = getDivider();
        ListAdapter adapter = getAdapter();
        if (adapter == null) {
            return listPaddingTop + listPaddingBottom;
        }
        int measuredHeight = listPaddingTop + listPaddingBottom;
        if (dividerHeight <= 0 || divider == null) {
            dividerHeight = 0;
        }
        int count = adapter.getCount();
        int i4 = 0;
        View view = null;
        for (int i5 = 0; i5 < count; i5++) {
            int itemViewType = adapter.getItemViewType(i5);
            if (itemViewType != i4) {
                view = null;
                i4 = itemViewType;
            }
            view = adapter.getView(i5, view, this);
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = generateDefaultLayoutParams();
                view.setLayoutParams(layoutParams);
            }
            int i6 = layoutParams.height;
            view.measure(i2, i6 > 0 ? View.MeasureSpec.makeMeasureSpec(i6, 1073741824) : View.MeasureSpec.makeMeasureSpec(0, 0));
            view.forceLayout();
            if (i5 > 0) {
                measuredHeight += dividerHeight;
            }
            measuredHeight += view.getMeasuredHeight();
            if (measuredHeight >= i3) {
                return i3;
            }
        }
        return measuredHeight;
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0015  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean b(android.view.MotionEvent r17, int r18) {
        /*
            Method dump skipped, instruction units count: 360
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.I.b(android.view.MotionEvent, int):boolean");
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        Drawable selector;
        Rect rect = this.f995a;
        if (!rect.isEmpty() && (selector = getSelector()) != null) {
            selector.setBounds(rect);
            selector.draw(canvas);
        }
        super.dispatchDraw(canvas);
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        if (this.f1007m != null) {
            return;
        }
        super.drawableStateChanged();
        H h2 = this.f1002h;
        if (h2 != null) {
            h2.f994b = true;
        }
        Drawable selector = getSelector();
        if (selector != null && this.f1005k && isPressed()) {
            selector.setState(getDrawableState());
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean hasFocus() {
        return this.f1004j || super.hasFocus();
    }

    @Override // android.view.View
    public final boolean hasWindowFocus() {
        return this.f1004j || super.hasWindowFocus();
    }

    @Override // android.view.View
    public final boolean isFocused() {
        return this.f1004j || super.isFocused();
    }

    @Override // android.view.View
    public final boolean isInTouchMode() {
        return (this.f1004j && this.f1003i) || super.isInTouchMode();
    }

    @Override // android.widget.ListView, android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        this.f1007m = null;
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public boolean onHoverEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 10 && this.f1007m == null) {
            F.e eVar = new F.e(5, this);
            this.f1007m = eVar;
            post(eVar);
        }
        boolean zOnHoverEvent = super.onHoverEvent(motionEvent);
        if (actionMasked == 9 || actionMasked == 7) {
            int iPointToPosition = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
            if (iPointToPosition != -1 && iPointToPosition != getSelectedItemPosition()) {
                View childAt = getChildAt(iPointToPosition - getFirstVisiblePosition());
                if (childAt.isEnabled()) {
                    setSelectionFromTop(iPointToPosition, childAt.getTop() - getTop());
                }
                Drawable selector = getSelector();
                if (selector != null && this.f1005k && isPressed()) {
                    selector.setState(getDrawableState());
                }
            }
        } else {
            setSelection(-1);
        }
        return zOnHoverEvent;
    }

    @Override // android.widget.AbsListView, android.view.View
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.f1000f = pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
        }
        F.e eVar = this.f1007m;
        if (eVar != null) {
            I i2 = (I) eVar.f121b;
            i2.f1007m = null;
            i2.removeCallbacks(eVar);
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setListSelectionHidden(boolean z2) {
        this.f1003i = z2;
    }

    @Override // android.widget.AbsListView
    public void setSelector(Drawable drawable) {
        H h2 = null;
        if (drawable != null) {
            H h3 = new H();
            Drawable drawable2 = h3.f2015a;
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            h3.f2015a = drawable;
            drawable.setCallback(h3);
            h3.f994b = true;
            h2 = h3;
        }
        this.f1002h = h2;
        super.setSelector(h2);
        Rect rect = new Rect();
        if (drawable != null) {
            drawable.getPadding(rect);
        }
        this.f996b = rect.left;
        this.f997c = rect.top;
        this.f998d = rect.right;
        this.f999e = rect.bottom;
    }
}
