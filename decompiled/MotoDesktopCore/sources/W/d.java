package W;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AbstractC0054a;
import com.google.android.material.R$layout;
import com.google.android.material.tabs.TabLayout;
import java.util.WeakHashMap;
import o.AbstractC0152a;
import v.l;

/* JADX INFO: loaded from: classes.dex */
public final class d extends LinearLayout {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public b f251a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public TextView f252b;

    /* JADX INFO: renamed from: c, reason: collision with root package name */
    public ImageView f253c;

    /* JADX INFO: renamed from: d, reason: collision with root package name */
    public View f254d;

    /* JADX INFO: renamed from: e, reason: collision with root package name */
    public int f255e;

    /* JADX INFO: renamed from: f, reason: collision with root package name */
    public final /* synthetic */ TabLayout f256f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public d(TabLayout tabLayout, Context context) {
        super(context);
        this.f256f = tabLayout;
        this.f255e = 2;
        b();
        tabLayout.getClass();
        WeakHashMap weakHashMap = l.f2836a;
        setPaddingRelative(0, 0, 0, 0);
        setGravity(17);
        setOrientation(!tabLayout.f2204j ? 1 : 0);
        setClickable(true);
        setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1002));
    }

    public final void a() {
        b bVar = this.f251a;
        View view = this.f254d;
        if (view != null) {
            removeView(view);
            this.f254d = null;
        }
        boolean z2 = false;
        if (this.f254d == null) {
            if (this.f253c == null) {
                ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R$layout.design_layout_tab_icon, (ViewGroup) this, false);
                addView(imageView, 0);
                this.f253c = imageView;
            }
            if (this.f252b == null) {
                TextView textView = (TextView) LayoutInflater.from(getContext()).inflate(R$layout.design_layout_tab_text, (ViewGroup) this, false);
                addView(textView);
                this.f252b = textView;
                this.f255e = textView.getMaxLines();
            }
            TextView textView2 = this.f252b;
            TabLayout tabLayout = this.f256f;
            tabLayout.getClass();
            textView2.setTextAppearance(0);
            ColorStateList colorStateList = tabLayout.f2196b;
            if (colorStateList != null) {
                this.f252b.setTextColor(colorStateList);
            }
            c(this.f252b, this.f253c);
        }
        if (bVar != null && !TextUtils.isEmpty(bVar.f244a)) {
            setContentDescription(bVar.f244a);
        }
        if (bVar != null) {
            TabLayout tabLayout2 = bVar.f246c;
            if (tabLayout2 == null) {
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            if (tabLayout2.getSelectedTabPosition() == bVar.f245b) {
                z2 = true;
            }
        }
        setSelected(z2);
    }

    public final void b() {
        TabLayout tabLayout = this.f256f;
        tabLayout.getClass();
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(0);
        Drawable rippleDrawable = gradientDrawable;
        if (tabLayout.f2198d != null) {
            GradientDrawable gradientDrawable2 = new GradientDrawable();
            gradientDrawable2.setCornerRadius(1.0E-5f);
            gradientDrawable2.setColor(-1);
            ColorStateList colorStateList = tabLayout.f2198d;
            int[] iArr = S.a.f238c;
            int colorForState = colorStateList != null ? colorStateList.getColorForState(S.a.f237b, colorStateList.getDefaultColor()) : 0;
            int iMin = Math.min(Color.alpha(colorForState) * 2, 255);
            int i2 = AbstractC0152a.f2780a;
            if (iMin < 0 || iMin > 255) {
                throw new IllegalArgumentException("alpha must be between 0 and 255.");
            }
            int i3 = (colorForState & 16777215) | (iMin << 24);
            int[][] iArr2 = {iArr, StateSet.NOTHING};
            int colorForState2 = colorStateList != null ? colorStateList.getColorForState(S.a.f236a, colorStateList.getDefaultColor()) : 0;
            int iMin2 = Math.min(Color.alpha(colorForState2) * 2, 255);
            if (iMin2 < 0 || iMin2 > 255) {
                throw new IllegalArgumentException("alpha must be between 0 and 255.");
            }
            ColorStateList colorStateList2 = new ColorStateList(iArr2, new int[]{i3, (colorForState2 & 16777215) | (iMin2 << 24)});
            boolean z2 = tabLayout.f2205k;
            GradientDrawable gradientDrawable3 = gradientDrawable;
            if (z2) {
                gradientDrawable3 = null;
            }
            if (z2) {
                gradientDrawable2 = null;
            }
            rippleDrawable = new RippleDrawable(colorStateList2, gradientDrawable3, gradientDrawable2);
        }
        WeakHashMap weakHashMap = l.f2836a;
        setBackground(rippleDrawable);
        tabLayout.invalidate();
    }

    public final void c(TextView textView, ImageView imageView) {
        if (imageView != null) {
            imageView.setVisibility(8);
            imageView.setImageDrawable(null);
        }
        boolean z2 = !TextUtils.isEmpty(null);
        if (textView != null) {
            if (z2) {
                textView.setText((CharSequence) null);
                textView.setVisibility(0);
                setVisibility(0);
            } else {
                textView.setVisibility(8);
                textView.setText((CharSequence) null);
            }
        }
        if (imageView != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
            TabLayout tabLayout = this.f256f;
            int iRound = (z2 && imageView.getVisibility() == 0) ? Math.round(tabLayout.getResources().getDisplayMetrics().density * 8) : 0;
            if (tabLayout.f2204j) {
                if (iRound != marginLayoutParams.getMarginEnd()) {
                    marginLayoutParams.setMarginEnd(iRound);
                    marginLayoutParams.bottomMargin = 0;
                    imageView.setLayoutParams(marginLayoutParams);
                    imageView.requestLayout();
                }
            } else if (iRound != marginLayoutParams.bottomMargin) {
                marginLayoutParams.bottomMargin = iRound;
                marginLayoutParams.setMarginEnd(0);
                imageView.setLayoutParams(marginLayoutParams);
                imageView.requestLayout();
            }
        }
        b bVar = this.f251a;
        setTooltipText(z2 ? null : bVar != null ? bVar.f244a : null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        getDrawableState();
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(AbstractC0054a.class.getName());
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(AbstractC0054a.class.getName());
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i2, int i3) {
        int size = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        TabLayout tabLayout = this.f256f;
        int tabMaxWidth = tabLayout.getTabMaxWidth();
        if (tabMaxWidth > 0 && (mode == 0 || size > tabMaxWidth)) {
            i2 = View.MeasureSpec.makeMeasureSpec(tabLayout.f2200f, Integer.MIN_VALUE);
        }
        super.onMeasure(i2, i3);
        if (this.f252b != null) {
            tabLayout.getClass();
            int i4 = this.f255e;
            ImageView imageView = this.f253c;
            if (imageView == null || imageView.getVisibility() != 0) {
                TextView textView = this.f252b;
                if (textView != null && textView.getLineCount() > 1) {
                    tabLayout.getClass();
                }
            } else {
                i4 = 1;
            }
            float textSize = this.f252b.getTextSize();
            int lineCount = this.f252b.getLineCount();
            int maxLines = this.f252b.getMaxLines();
            if (0.0f != textSize || (maxLines >= 0 && i4 != maxLines)) {
                if (tabLayout.f2203i == 1 && 0.0f > textSize && lineCount == 1) {
                    Layout layout = this.f252b.getLayout();
                    if (layout == null) {
                        return;
                    }
                    if ((0.0f / layout.getPaint().getTextSize()) * layout.getLineWidth(0) > (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()) {
                        return;
                    }
                }
                this.f252b.setTextSize(0, 0.0f);
                this.f252b.setMaxLines(i4);
                super.onMeasure(i2, i3);
            }
        }
    }

    @Override // android.view.View
    public final boolean performClick() {
        boolean zPerformClick = super.performClick();
        if (this.f251a == null) {
            return zPerformClick;
        }
        if (!zPerformClick) {
            playSoundEffect(0);
        }
        b bVar = this.f251a;
        TabLayout tabLayout = bVar.f246c;
        if (tabLayout == null) {
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }
        tabLayout.b(bVar, true);
        return true;
    }

    @Override // android.view.View
    public final void setSelected(boolean z2) {
        isSelected();
        super.setSelected(z2);
        TextView textView = this.f252b;
        if (textView != null) {
            textView.setSelected(z2);
        }
        ImageView imageView = this.f253c;
        if (imageView != null) {
            imageView.setSelected(z2);
        }
        View view = this.f254d;
        if (view != null) {
            view.setSelected(z2);
        }
    }
}
