package com.android.systemui.statusbar.policy;

import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.SystemClock;
import android.text.Layout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.res.R$color;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$styleable;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/* JADX INFO: loaded from: classes.dex */
public class SmartReplyView extends ViewGroup {
    private final BreakIterator mBreakIterator;
    private PriorityQueue mCandidateButtonQueueForSqueezing;
    private int mCurrentBackgroundColor;
    private boolean mCurrentColorized;
    private int mCurrentRippleColor;
    private int mCurrentStrokeColor;
    private int mCurrentTextColor;
    private final int mDefaultBackgroundColor;
    private final int mDefaultStrokeColor;
    private final int mDefaultTextColor;
    private final int mDefaultTextColorDarkBg;
    private boolean mDidHideSystemReplies;
    private final int mHeightUpperLimit;
    private long mLastDispatchDrawTime;
    private long mLastDrawChildTime;
    private long mLastMeasureTime;
    private int mMaxNumActions;
    private int mMaxSqueezeRemeasureAttempts;
    private int mMinNumSystemGeneratedReplies;
    private final double mMinStrokeContrast;
    private final int mRippleColor;
    private final int mRippleColorDarkBg;
    private boolean mSmartRepliesGeneratedByAssistant;
    private View mSmartReplyContainer;
    private final int mSpacing;
    private final int mStrokeWidth;
    private int mTotalSqueezeRemeasureAttempts;
    private static final int MEASURE_SPEC_ANY_LENGTH = View.MeasureSpec.makeMeasureSpec(0, 0);
    private static final Comparator DECREASING_MEASURED_WIDTH_WITHOUT_PADDING_COMPARATOR = new Comparator() { // from class: com.android.systemui.statusbar.policy.SmartReplyView$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return SmartReplyView.$r8$lambda$Qp4EmhSQxcLXfKQtDl6bQzHPbw0((View) obj, (View) obj2);
        }
    };

    class LayoutParams extends ViewGroup.LayoutParams {
        SmartButtonType mButtonType;
        String mNoShowReason;
        private boolean show;
        private int squeezeStatus;

        private LayoutParams(int i, int i2) {
            super(i, i2);
            this.show = false;
            this.squeezeStatus = 0;
            this.mButtonType = SmartButtonType.REPLY;
            this.mNoShowReason = "new";
        }

        private LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.show = false;
            this.squeezeStatus = 0;
            this.mButtonType = SmartButtonType.REPLY;
            this.mNoShowReason = "new";
        }

        boolean isShown() {
            return this.show;
        }
    }

    public class SmartActions {
        public final List actions;
        public final boolean fromAssistant;

        public SmartActions(List list, boolean z) {
            this.actions = list;
            this.fromAssistant = z;
        }
    }

    enum SmartButtonType {
        REPLY,
        ACTION
    }

    public class SmartReplies {
        public final List choices;
        public final boolean fromAssistant;
        public final PendingIntent pendingIntent;
        public final RemoteInput remoteInput;

        public SmartReplies(List list, RemoteInput remoteInput, PendingIntent pendingIntent, boolean z) {
            this.choices = list;
            this.remoteInput = remoteInput;
            this.pendingIntent = pendingIntent;
            this.fromAssistant = z;
        }
    }

    class SmartSuggestionMeasures {
        int mMaxChildHeight;
        int mMeasuredWidth;

        SmartSuggestionMeasures(int i, int i2) {
            this.mMeasuredWidth = i;
            this.mMaxChildHeight = i2;
        }

        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public SmartSuggestionMeasures m2130clone() {
            return new SmartSuggestionMeasures(this.mMeasuredWidth, this.mMaxChildHeight);
        }
    }

    public static /* synthetic */ int $r8$lambda$Qp4EmhSQxcLXfKQtDl6bQzHPbw0(View view, View view2) {
        return ((view2.getMeasuredWidth() - view2.getPaddingLeft()) - view2.getPaddingRight()) - ((view.getMeasuredWidth() - view.getPaddingLeft()) - view.getPaddingRight());
    }

    public SmartReplyView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSmartRepliesGeneratedByAssistant = false;
        this.mHeightUpperLimit = NotificationUtils.getFontScaledHeight(((ViewGroup) this).mContext, R$dimen.smart_reply_button_max_height);
        int color = context.getColor(R$color.smart_reply_button_background);
        this.mDefaultBackgroundColor = color;
        this.mDefaultTextColor = ((ViewGroup) this).mContext.getColor(R$color.smart_reply_button_text);
        this.mDefaultTextColorDarkBg = ((ViewGroup) this).mContext.getColor(R$color.smart_reply_button_text_dark_bg);
        int color2 = ((ViewGroup) this).mContext.getColor(R$color.smart_reply_button_stroke);
        this.mDefaultStrokeColor = color2;
        int color3 = ((ViewGroup) this).mContext.getColor(R$color.notification_ripple_untinted_color);
        this.mRippleColor = color3;
        this.mRippleColorDarkBg = Color.argb(Color.alpha(color3), 255, 255, 255);
        this.mMinStrokeContrast = ContrastColorUtil.calculateContrast(color2, color);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SmartReplyView, 0, 0);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        int dimensionPixelSize = 0;
        int dimensionPixelSize2 = 0;
        for (int i = 0; i < indexCount; i++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i);
            if (index == R$styleable.SmartReplyView_spacing) {
                dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(i, 0);
            } else if (index == R$styleable.SmartReplyView_buttonStrokeWidth) {
                dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(i, 0);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        this.mStrokeWidth = dimensionPixelSize;
        this.mSpacing = dimensionPixelSize2;
        this.mBreakIterator = BreakIterator.getLineInstance();
        setBackgroundTintColor(this.mDefaultBackgroundColor, false);
        reallocateCandidateButtonQueueForSqueezing();
    }

    private void clearLayoutLineCount(View view) {
        if (view instanceof TextView) {
            ((TextView) view).nullLayouts();
            view.forceLayout();
        }
    }

    private int estimateOptimalSqueezedButtonTextWidth(Button button) {
        String string = button.getText().toString();
        TransformationMethod transformationMethod = button.getTransformationMethod();
        if (transformationMethod != null) {
            string = transformationMethod.getTransformation(string, button).toString();
        }
        int length = string.length();
        this.mBreakIterator.setText(string);
        if (this.mBreakIterator.preceding(length / 2) == -1 && this.mBreakIterator.next() == -1) {
            return -1;
        }
        TextPaint paint = button.getPaint();
        int iCurrent = this.mBreakIterator.current();
        float desiredWidth = Layout.getDesiredWidth(string, 0, iCurrent, paint);
        float desiredWidth2 = Layout.getDesiredWidth(string, iCurrent, length, paint);
        float fMax = Math.max(desiredWidth, desiredWidth2);
        if (desiredWidth != desiredWidth2) {
            boolean z = desiredWidth > desiredWidth2;
            int i = this.mMaxSqueezeRemeasureAttempts;
            int i2 = 0;
            while (i2 < i) {
                this.mTotalSqueezeRemeasureAttempts++;
                BreakIterator breakIterator = this.mBreakIterator;
                int iPrevious = z ? breakIterator.previous() : breakIterator.next();
                if (iPrevious == -1) {
                    break;
                }
                float desiredWidth3 = Layout.getDesiredWidth(string, 0, iPrevious, paint);
                float desiredWidth4 = Layout.getDesiredWidth(string, iPrevious, length, paint);
                float fMax2 = Math.max(desiredWidth3, desiredWidth4);
                if (fMax2 >= fMax) {
                    break;
                }
                if (z) {
                    if (desiredWidth3 <= desiredWidth4) {
                        fMax = fMax2;
                        break;
                    }
                    i2++;
                    fMax = fMax2;
                } else {
                    if (desiredWidth3 >= desiredWidth4) {
                        fMax = fMax2;
                        break;
                    }
                    i2++;
                    fMax = fMax2;
                }
            }
        }
        return (int) Math.ceil(fMax);
    }

    private List filterActionsOrReplies(SmartButtonType smartButtonType) {
        ArrayList arrayList = new ArrayList();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (childAt.getVisibility() == 0 && (childAt instanceof Button) && layoutParams.mButtonType == smartButtonType) {
                arrayList.add(childAt);
            }
        }
        return arrayList;
    }

    private int getStartCompoundDrawableWidthWithPadding(Button button) {
        Drawable drawable = button.getCompoundDrawablesRelative()[0];
        if (drawable == null) {
            return 0;
        }
        return drawable.getBounds().width() + button.getCompoundDrawablePadding();
    }

    private boolean gotEnoughSmartReplies(List list) {
        if (this.mMinNumSystemGeneratedReplies <= 1) {
            return true;
        }
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (((LayoutParams) ((View) it.next()).getLayoutParams()).show) {
                i++;
            }
        }
        return i == 0 || i >= this.mMinNumSystemGeneratedReplies;
    }

    public static SmartReplyView inflate(Context context, SmartReplyConstants smartReplyConstants) {
        SmartReplyView smartReplyView = (SmartReplyView) LayoutInflater.from(context).inflate(R$layout.smart_reply_view, (ViewGroup) null);
        smartReplyView.setMaxNumActions(smartReplyConstants.getMaxNumActions());
        smartReplyView.setMaxSqueezeRemeasureAttempts(smartReplyConstants.getMaxSqueezeRemeasureAttempts());
        smartReplyView.setMinNumSystemGeneratedReplies(smartReplyConstants.getMinNumSystemGeneratedReplies());
        return smartReplyView;
    }

    private void markButtonsWithPendingSqueezeStatusAs(int i, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            LayoutParams layoutParams = (LayoutParams) ((View) it.next()).getLayoutParams();
            if (layoutParams.squeezeStatus == 1) {
                layoutParams.squeezeStatus = i;
            }
        }
    }

    private void reallocateCandidateButtonQueueForSqueezing() {
        this.mCandidateButtonQueueForSqueezing = new PriorityQueue(Math.max(getChildCount(), 1), DECREASING_MEASURED_WIDTH_WITHOUT_PADDING_COMPARATOR);
    }

    private void remeasureButtonsIfNecessary(int i) {
        boolean z;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i, 1073741824);
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams.show) {
                int measuredWidth = childAt.getMeasuredWidth();
                if (layoutParams.squeezeStatus == 3) {
                    measuredWidth = Integer.MAX_VALUE;
                    z = true;
                } else {
                    z = false;
                }
                if (childAt.getMeasuredHeight() == i ? z : true) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, Integer.MIN_VALUE), iMakeMeasureSpec);
                }
            }
        }
    }

    private void resetButtonsLayoutParams() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            layoutParams.show = false;
            layoutParams.squeezeStatus = 0;
            layoutParams.mNoShowReason = "reset";
        }
    }

    private void setButtonColors(Button button) {
        Drawable background = button.getBackground();
        if (background instanceof RippleDrawable) {
            Drawable drawableMutate = background.mutate();
            RippleDrawable rippleDrawable = (RippleDrawable) drawableMutate;
            rippleDrawable.setColor(ColorStateList.valueOf(this.mCurrentRippleColor));
            Drawable drawable = rippleDrawable.getDrawable(0);
            if (drawable instanceof InsetDrawable) {
                Drawable drawable2 = ((InsetDrawable) drawable).getDrawable();
                if (drawable2 instanceof GradientDrawable) {
                    GradientDrawable gradientDrawable = (GradientDrawable) drawable2;
                    gradientDrawable.setColor(this.mCurrentBackgroundColor);
                    gradientDrawable.setStroke(this.mStrokeWidth, this.mCurrentStrokeColor);
                }
            }
            button.setBackground(drawableMutate);
        }
        button.setTextColor(this.mCurrentTextColor);
    }

    private int squeezeButton(Button button, int i) {
        int iEstimateOptimalSqueezedButtonTextWidth = estimateOptimalSqueezedButtonTextWidth(button);
        if (iEstimateOptimalSqueezedButtonTextWidth == -1) {
            return -1;
        }
        return squeezeButtonToTextWidth(button, i, iEstimateOptimalSqueezedButtonTextWidth);
    }

    private int squeezeButtonToTextWidth(Button button, int i, int i2) {
        int measuredWidth = button.getMeasuredWidth();
        clearLayoutLineCount(button);
        button.measure(View.MeasureSpec.makeMeasureSpec(button.getPaddingStart() + button.getPaddingEnd() + i2 + getStartCompoundDrawableWidthWithPadding(button), Integer.MIN_VALUE), i);
        if (button.getLayout() == null) {
            Log.wtf("SmartReplyView", "Button layout is null after measure.");
        }
        int measuredWidth2 = button.getMeasuredWidth();
        LayoutParams layoutParams = (LayoutParams) button.getLayoutParams();
        if (button.getLineCount() > 2 || measuredWidth2 >= measuredWidth) {
            layoutParams.squeezeStatus = 3;
            return -1;
        }
        layoutParams.squeezeStatus = 1;
        return measuredWidth - measuredWidth2;
    }

    public void addPreInflatedButtons(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Button button = (Button) it.next();
            addView(button);
            setButtonColors(button);
        }
        reallocateCandidateButtonQueueForSqueezing();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.mLastDispatchDrawTime = SystemClock.elapsedRealtime();
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        if (!((LayoutParams) view.getLayoutParams()).show) {
            return false;
        }
        this.mLastDrawChildTime = SystemClock.elapsedRealtime();
        return super.drawChild(canvas, view, j);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        int i = -2;
        return new LayoutParams(i, i);
    }

    @Override // android.view.ViewGroup
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams.width, layoutParams.height);
    }

    @Override // android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(((ViewGroup) this).mContext, attributeSet);
    }

    public int getHeightUpperLimit() {
        return this.mHeightUpperLimit;
    }

    void hideSmartSuggestions() {
        View view = this.mSmartReplyContainer;
        if (view != null) {
            view.setVisibility(8);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2 = getLayoutDirection() == 1;
        int i5 = z2 ? (i3 - i) - ((ViewGroup) this).mPaddingRight : ((ViewGroup) this).mPaddingLeft;
        int childCount = getChildCount();
        for (int i6 = 0; i6 < childCount; i6++) {
            View childAt = getChildAt(i6);
            if (((LayoutParams) childAt.getLayoutParams()).show) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i7 = z2 ? i5 - measuredWidth : i5;
                childAt.layout(i7, 0, i7 + measuredWidth, measuredHeight);
                int i8 = measuredWidth + this.mSpacing;
                i5 = z2 ? i5 - i8 : i5 + i8;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x014b  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void onMeasure(int r21, int r22) {
        /*
            Method dump skipped, instruction units count: 444
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartReplyView.onMeasure(int, int):void");
    }

    public void resetSmartSuggestions(View view) {
        this.mSmartReplyContainer = view;
        removeAllViews();
        setBackgroundTintColor(this.mDefaultBackgroundColor, false);
    }

    public void setBackgroundTintColor(int i, boolean z) {
        if (i == this.mCurrentBackgroundColor && z == this.mCurrentColorized) {
            return;
        }
        this.mCurrentBackgroundColor = i;
        this.mCurrentColorized = z;
        boolean zIsColorDark = ContrastColorUtil.isColorDark(i);
        int i2 = zIsColorDark ? this.mDefaultTextColorDarkBg : this.mDefaultTextColor;
        int i3 = i | DarkIconDispatcher.DEFAULT_INVERSE_ICON_TINT;
        int iEnsureTextContrast = ContrastColorUtil.ensureTextContrast(i2, i3, zIsColorDark);
        this.mCurrentTextColor = iEnsureTextContrast;
        if (!z) {
            iEnsureTextContrast = ContrastColorUtil.ensureContrast(this.mDefaultStrokeColor, i3, zIsColorDark, this.mMinStrokeContrast);
        }
        this.mCurrentStrokeColor = iEnsureTextContrast;
        this.mCurrentRippleColor = zIsColorDark ? this.mRippleColorDarkBg : this.mRippleColor;
        int childCount = getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            setButtonColors((Button) getChildAt(i4));
        }
    }

    public void setMaxNumActions(int i) {
        this.mMaxNumActions = i;
    }

    public void setMaxSqueezeRemeasureAttempts(int i) {
        this.mMaxSqueezeRemeasureAttempts = i;
    }

    public void setMinNumSystemGeneratedReplies(int i) {
        this.mMinNumSystemGeneratedReplies = i;
    }

    void setSmartRepliesGeneratedByAssistant(boolean z) {
        this.mSmartRepliesGeneratedByAssistant = z;
    }
}
