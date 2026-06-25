package com.android.systemui.statusbar.notification.stack;

import android.R;
import android.app.Notification;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.widget.NotificationExpandButton;
import com.android.systemui.res.R$bool;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.NotificationGroupingUtil;
import com.android.systemui.statusbar.notification.FeedbackIcon;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.NotificationUtils;
import com.android.systemui.statusbar.notification.Roundable;
import com.android.systemui.statusbar.notification.RoundableState;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.HybridGroupManager;
import com.android.systemui.statusbar.notification.row.HybridNotificationView;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.shared.AsyncHybridViewInflation;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationHeaderViewWrapper;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import com.motorola.taskbar.R$layout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NotificationChildrenContainer extends ViewGroup implements NotificationFadeAware, Roundable {
    private static final AnimationProperties ALPHA_FADE_IN = new AnimationProperties() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer.1
        private AnimationFilter mAnimationFilter = new AnimationFilter().animateAlpha();

        @Override // com.android.systemui.statusbar.notification.stack.AnimationProperties
        public AnimationFilter getAnimationFilter() {
            return this.mAnimationFilter;
        }
    }.setDuration(200);
    private static final SourceType FROM_PARENT = SourceType.from("FromParent(NCC)");
    static final int NUMBER_OF_CHILDREN_WHEN_COLLAPSED = 2;
    static final int NUMBER_OF_CHILDREN_WHEN_SYSTEM_EXPANDED = 5;
    private int mActualHeight;
    private final List mAttachedChildren;
    private Path mChildClipPath;
    private int mChildPadding;
    private boolean mChildrenExpanded;
    private int mClipBottomAmount;
    private float mCollapsedBottomPadding;
    private ExpandableNotificationRow mContainingNotification;
    private boolean mContainingNotificationIsFaded;
    private ViewGroup mCurrentHeader;
    private int mCurrentHeaderTranslation;
    private float mDividerAlpha;
    private int mDividerHeight;
    private final List mDividers;
    private boolean mEnableShadowOnChildNotifications;
    private NotificationHeaderView mGroupHeader;
    private NotificationHeaderViewWrapper mGroupHeaderWrapper;
    private ViewState mGroupOverFlowState;
    private NotificationGroupingUtil mGroupingUtil;
    private View.OnClickListener mHeaderClickListener;
    private int mHeaderHeight;
    private final Path mHeaderPath;
    private ViewState mHeaderViewState;
    private float mHeaderVisibleAmount;
    private boolean mHideDividersDuringExpand;
    private final HybridGroupManager mHybridGroupManager;
    private boolean mIsConversation;
    private boolean mIsMinimized;
    private NotificationChildrenContainerLogger mLogger;
    private int mMinSingleLineHeight;
    private NotificationHeaderView mMinimizedGroupHeader;
    private NotificationHeaderViewWrapper mMinimizedGroupHeaderWrapper;
    private boolean mNeverAppliedGroupState;
    private int mNotificationHeaderMargin;
    private int mNotificationTopPadding;
    private TextView mOverflowNumber;
    private int mRealHeight;
    private RoundableState mRoundableState;
    private boolean mShowDividersWhenExpanded;
    private boolean mShowGroupCountInExpander;
    private int mTranslationForHeader;
    private int mUntruncatedChildCount;
    private boolean mUserLocked;

    public NotificationChildrenContainer(Context context) {
        this(context, null);
    }

    public NotificationChildrenContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationChildrenContainer(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public NotificationChildrenContainer(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDividers = new ArrayList();
        this.mAttachedChildren = new ArrayList();
        this.mChildClipPath = null;
        this.mHeaderPath = new Path();
        this.mCurrentHeaderTranslation = 0;
        this.mHeaderVisibleAmount = 1.0f;
        this.mContainingNotificationIsFaded = false;
        this.mHybridGroupManager = new HybridGroupManager(getContext());
        this.mRoundableState = new RoundableState(this, this, 0.0f);
        initDimens();
        setClipChildren(false);
    }

    private ViewGroup calculateDesiredHeader() {
        return showingAsLowPriority() ? this.mMinimizedGroupHeader : this.mGroupHeader;
    }

    private void ensureRemovedFromTransientContainer(View view) {
        if (view.getParent() == null || !(view instanceof ExpandableView)) {
            return;
        }
        ((ExpandableView) view).removeFromTransientContainerForAdditionTo(this);
    }

    private int getIntrinsicHeight(float f) {
        int iInterpolate;
        if (showingAsLowPriority()) {
            return AsyncGroupHeaderViewInflation.isEnabled() ? this.mHeaderHeight : this.mMinimizedGroupHeader.getHeight();
        }
        int intrinsicHeight = this.mNotificationHeaderMargin + this.mCurrentHeaderTranslation;
        int size = this.mAttachedChildren.size();
        float groupExpandFraction = this.mUserLocked ? getGroupExpandFraction() : 0.0f;
        boolean z = this.mChildrenExpanded;
        boolean z2 = true;
        int i = 0;
        for (int i2 = 0; i2 < size && i < f; i2++) {
            if (z2) {
                iInterpolate = this.mUserLocked ? (int) (intrinsicHeight + NotificationUtils.interpolate(0.0f, this.mNotificationTopPadding + this.mDividerHeight, groupExpandFraction)) : intrinsicHeight + (z ? this.mNotificationTopPadding + this.mDividerHeight : 0);
                z2 = false;
            } else if (this.mUserLocked) {
                iInterpolate = (int) (intrinsicHeight + NotificationUtils.interpolate(this.mChildPadding, this.mDividerHeight, groupExpandFraction));
            } else {
                iInterpolate = intrinsicHeight + (z ? this.mDividerHeight : this.mChildPadding);
            }
            intrinsicHeight = iInterpolate + ((ExpandableNotificationRow) this.mAttachedChildren.get(i2)).getIntrinsicHeight();
            i++;
        }
        return this.mUserLocked ? (int) (intrinsicHeight + NotificationUtils.interpolate(this.mCollapsedBottomPadding, 0.0f, groupExpandFraction)) : !z ? (int) (intrinsicHeight + this.mCollapsedBottomPadding) : intrinsicHeight;
    }

    private int getMinHeight(int i, boolean z) {
        return getMinHeight(i, z, this.mCurrentHeaderTranslation);
    }

    private int getMinHeight(int i, boolean z, int i2) {
        int height;
        if (!z && showingAsLowPriority()) {
            if (AsyncGroupHeaderViewInflation.isEnabled()) {
                return this.mHeaderHeight;
            }
            NotificationHeaderView notificationHeaderView = this.mMinimizedGroupHeader;
            if (notificationHeaderView != null) {
                return notificationHeaderView.getHeight();
            }
            Log.e("NotificationChildrenContainer", "getMinHeight: low priority header is null", new Exception());
            return 0;
        }
        int i3 = this.mNotificationHeaderMargin + i2;
        int size = this.mAttachedChildren.size();
        boolean z2 = true;
        int i4 = 0;
        for (int i5 = 0; i5 < size && i4 < i; i5++) {
            if (z2) {
                z2 = false;
            } else {
                i3 += this.mChildPadding;
            }
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i5);
            HybridNotificationView singleLineView = expandableNotificationRow.getSingleLineView();
            if (singleLineView != null) {
                height = singleLineView.getHeight();
            } else if (AsyncHybridViewInflation.isEnabled()) {
                height = this.mMinSingleLineHeight;
            } else {
                Log.e("NotificationChildrenContainer", "getMinHeight: child " + expandableNotificationRow.getEntry().getKey() + " single line view is null", new Exception());
                i4++;
            }
            i3 += height;
            i4++;
        }
        return (int) (i3 + this.mCollapsedBottomPadding);
    }

    private int getVisibleChildrenExpandHeight() {
        int maxExpandHeight = this.mNotificationHeaderMargin + this.mCurrentHeaderTranslation + this.mNotificationTopPadding + this.mDividerHeight;
        int size = this.mAttachedChildren.size();
        int maxAllowedVisibleChildren = getMaxAllowedVisibleChildren(true);
        int i = 0;
        for (int i2 = 0; i2 < size && i < maxAllowedVisibleChildren; i2++) {
            maxExpandHeight = (int) (maxExpandHeight + (((ExpandableNotificationRow) this.mAttachedChildren.get(i2)).isExpanded(true) ? r6.getMaxExpandHeight() : r6.getShowingLayout().getMinHeight(true)));
            i++;
        }
        return maxExpandHeight;
    }

    private NotificationViewWrapper getWrapperForView(View view) {
        return view == this.mGroupHeader ? this.mGroupHeaderWrapper : this.mMinimizedGroupHeaderWrapper;
    }

    private View inflateDivider() {
        return LayoutInflater.from(((ViewGroup) this).mContext).inflate(R$layout.notification_children_divider, (ViewGroup) this, false);
    }

    private void initDimens() {
        Resources resources = getResources();
        this.mChildPadding = resources.getDimensionPixelOffset(R$dimen.notification_children_padding);
        this.mDividerHeight = resources.getDimensionPixelOffset(R$dimen.notification_children_container_divider_height);
        this.mDividerAlpha = resources.getFloat(R$dimen.notification_divider_alpha);
        this.mNotificationHeaderMargin = resources.getDimensionPixelOffset(R$dimen.notification_children_container_margin_top);
        int dimensionPixelOffset = resources.getDimensionPixelOffset(R$dimen.notification_children_container_top_padding);
        this.mNotificationTopPadding = dimensionPixelOffset;
        this.mHeaderHeight = this.mNotificationHeaderMargin + dimensionPixelOffset;
        this.mCollapsedBottomPadding = resources.getDimensionPixelOffset(R$dimen.notification_children_collapsed_bottom_padding);
        this.mEnableShadowOnChildNotifications = resources.getBoolean(R$bool.config_enableShadowOnChildNotifications);
        this.mShowGroupCountInExpander = resources.getBoolean(R$bool.config_showNotificationGroupCountInExpander);
        this.mShowDividersWhenExpanded = resources.getBoolean(R$bool.config_showDividersWhenGroupNotificationExpanded);
        this.mHideDividersDuringExpand = resources.getBoolean(R$bool.config_hideDividersDuringExpand);
        this.mTranslationForHeader = resources.getDimensionPixelOffset(R.dimen.notification_header_shrink_hide_width) - this.mNotificationHeaderMargin;
        this.mHybridGroupManager.initDimens();
        this.mMinSingleLineHeight = getResources().getDimensionPixelSize(R$dimen.conversation_single_line_face_pile_size);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateHeaderVisibility$0() {
        updateHeaderVisibility(false);
    }

    private void removeGroupHeader() {
        NotificationHeaderView notificationHeaderView = this.mGroupHeader;
        if (notificationHeaderView == null) {
            return;
        }
        removeView(notificationHeaderView);
        this.mGroupHeader = null;
        this.mGroupHeaderWrapper = null;
    }

    private void removeLowPriorityGroupHeader() {
        NotificationHeaderView notificationHeaderView = this.mMinimizedGroupHeader;
        if (notificationHeaderView == null) {
            return;
        }
        removeView(notificationHeaderView);
        this.mMinimizedGroupHeader = null;
        this.mMinimizedGroupHeaderWrapper = null;
    }

    private void resetHeaderVisibilityIfNeeded(View view, View view2) {
        if (view == null) {
            return;
        }
        if (view != this.mCurrentHeader && view != view2) {
            getWrapperForView(view).setVisible(false);
            view.setVisibility(4);
        }
        if (view != view2 || view.getVisibility() == 0) {
            return;
        }
        getWrapperForView(view).setVisible(true);
        view.setVisibility(0);
    }

    private void setExpandButtonNumber(NotificationViewWrapper notificationViewWrapper) {
        View expandButton = notificationViewWrapper == null ? null : notificationViewWrapper.getExpandButton();
        if (expandButton instanceof NotificationExpandButton) {
            ((NotificationExpandButton) expandButton).setNumber(this.mUntruncatedChildCount);
        }
    }

    private void startChildAlphaAnimations(boolean z) {
        float f = z ? 1.0f : 0.0f;
        float f2 = 1.0f - f;
        int size = this.mAttachedChildren.size();
        for (int i = 0; i < size && i < 5; i++) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i);
            expandableNotificationRow.setAlpha(f2);
            ViewState viewState = new ViewState();
            viewState.initFrom(expandableNotificationRow);
            viewState.setAlpha(f);
            AnimationProperties animationProperties = ALPHA_FADE_IN;
            animationProperties.setDelay(i * 50);
            viewState.animateTo(expandableNotificationRow, animationProperties);
        }
    }

    private void updateChildrenClipping() {
        int i;
        boolean z;
        if (this.mContainingNotification.hasExpandingChild()) {
            return;
        }
        int size = this.mAttachedChildren.size();
        int actualHeight = this.mContainingNotification.getActualHeight() - this.mClipBottomAmount;
        for (int i2 = 0; i2 < size; i2++) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i2);
            if (expandableNotificationRow.getVisibility() != 8) {
                float translationY = expandableNotificationRow.getTranslationY();
                float actualHeight2 = expandableNotificationRow.getActualHeight() + translationY;
                float f = actualHeight;
                if (translationY > f) {
                    i = 0;
                    z = false;
                } else {
                    i = actualHeight2 > f ? (int) (actualHeight2 - f) : 0;
                    z = true;
                }
                if (z != (expandableNotificationRow.getVisibility() == 0)) {
                    expandableNotificationRow.setVisibility(z ? 0 : 4);
                }
                expandableNotificationRow.setClipBottomAmount(i);
            }
        }
    }

    private void updateHeaderTouchability() {
        NotificationHeaderView notificationHeaderView = this.mGroupHeader;
        if (notificationHeaderView != null) {
            notificationHeaderView.setAcceptAllTouches(this.mChildrenExpanded || this.mUserLocked);
        }
    }

    private void updateHeaderTransformation() {
        if (this.mUserLocked && showingAsLowPriority()) {
            float groupExpandFraction = getGroupExpandFraction();
            this.mGroupHeaderWrapper.transformFrom(this.mMinimizedGroupHeaderWrapper, groupExpandFraction);
            this.mGroupHeader.setVisibility(0);
            this.mMinimizedGroupHeaderWrapper.transformTo(this.mGroupHeaderWrapper, groupExpandFraction);
        }
    }

    private void updateHeaderVisibility(boolean z) {
        NotificationHeaderView notificationHeaderView = this.mCurrentHeader;
        NotificationHeaderView notificationHeaderViewCalculateDesiredHeader = calculateDesiredHeader();
        if (notificationHeaderView == notificationHeaderViewCalculateDesiredHeader) {
            return;
        }
        if (AsyncGroupHeaderViewInflation.isEnabled() && notificationHeaderViewCalculateDesiredHeader == null) {
            return;
        }
        if (z) {
            if (notificationHeaderViewCalculateDesiredHeader == null || notificationHeaderView == null) {
                z = false;
            } else {
                notificationHeaderView.setVisibility(0);
                notificationHeaderViewCalculateDesiredHeader.setVisibility(0);
                NotificationViewWrapper wrapperForView = getWrapperForView(notificationHeaderViewCalculateDesiredHeader);
                NotificationViewWrapper wrapperForView2 = getWrapperForView(notificationHeaderView);
                wrapperForView.transformFrom(wrapperForView2);
                wrapperForView2.transformTo(wrapperForView, new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$updateHeaderVisibility$0();
                    }
                });
                startChildAlphaAnimations(notificationHeaderViewCalculateDesiredHeader == this.mGroupHeader);
            }
        }
        if (!z) {
            if (notificationHeaderViewCalculateDesiredHeader != null) {
                getWrapperForView(notificationHeaderViewCalculateDesiredHeader).setVisible(true);
                notificationHeaderViewCalculateDesiredHeader.setVisibility(0);
            }
            if (notificationHeaderView != null) {
                NotificationViewWrapper wrapperForView3 = getWrapperForView(notificationHeaderView);
                if (wrapperForView3 != null) {
                    wrapperForView3.setVisible(false);
                }
                notificationHeaderView.setVisibility(4);
            }
        }
        resetHeaderVisibilityIfNeeded(this.mGroupHeader, notificationHeaderViewCalculateDesiredHeader);
        resetHeaderVisibilityIfNeeded(this.mMinimizedGroupHeader, notificationHeaderViewCalculateDesiredHeader);
        this.mCurrentHeader = notificationHeaderViewCalculateDesiredHeader;
    }

    public void addNotification(ExpandableNotificationRow expandableNotificationRow, int i) {
        ensureRemovedFromTransientContainer(expandableNotificationRow);
        if (i < 0) {
            i = this.mAttachedChildren.size();
        }
        this.mAttachedChildren.add(i, expandableNotificationRow);
        addView(expandableNotificationRow);
        expandableNotificationRow.setUserLocked(this.mUserLocked);
        View viewInflateDivider = inflateDivider();
        addView(viewInflateDivider);
        this.mDividers.add(i, viewInflateDivider);
        expandableNotificationRow.setContentTransformationAmount(0.0f, false);
        expandableNotificationRow.setNotificationFaded(this.mContainingNotificationIsFaded);
        ExpandableViewState viewState = expandableNotificationRow.getViewState();
        if (viewState != null) {
            viewState.cancelAnimations(expandableNotificationRow);
            expandableNotificationRow.cancelAppearDrawing();
        }
        applyRoundnessAndInvalidate();
    }

    public void addTransientView(View view, int i) {
        NotificationChildrenContainerLogger notificationChildrenContainerLogger = this.mLogger;
        if (notificationChildrenContainerLogger != null && (view instanceof ExpandableNotificationRow)) {
            notificationChildrenContainerLogger.addTransientRow(((ExpandableNotificationRow) view).getEntry(), getContainingNotification().getEntry(), i);
        }
        super.addTransientView(view, i);
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public void applyRoundnessAndInvalidate() {
        NotificationHeaderViewWrapper notificationHeaderViewWrapper = this.mGroupHeaderWrapper;
        if (notificationHeaderViewWrapper != null) {
            notificationHeaderViewWrapper.requestTopRoundness(getTopRoundness(), FROM_PARENT, false);
        }
        NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = this.mMinimizedGroupHeaderWrapper;
        if (notificationHeaderViewWrapper2 != null) {
            notificationHeaderViewWrapper2.requestTopRoundness(getTopRoundness(), FROM_PARENT, false);
        }
        boolean z = true;
        for (int size = this.mAttachedChildren.size() - 1; size >= 0; size--) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(size);
            if (expandableNotificationRow.getVisibility() != 8) {
                expandableNotificationRow.requestRoundness(0.0f, z ? getBottomRoundness() : 0.0f, FROM_PARENT, false);
                z = false;
            }
        }
        super.applyRoundnessAndInvalidate();
    }

    public void applyState() {
        int size = this.mAttachedChildren.size();
        ViewState viewState = new ViewState();
        float groupExpandFraction = this.mUserLocked ? getGroupExpandFraction() : 0.0f;
        boolean z = (this.mChildrenExpanded && this.mShowDividersWhenExpanded) || ((!showingAsLowPriority() && (this.mUserLocked || this.mContainingNotification.isGroupExpansionChanging())) && !this.mHideDividersDuringExpand);
        for (int i = 0; i < size; i++) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i);
            ExpandableViewState viewState2 = expandableNotificationRow.getViewState();
            viewState2.applyToView(expandableNotificationRow);
            View view = (View) this.mDividers.get(i);
            viewState.initFrom(view);
            viewState.setYTranslation(viewState2.getYTranslation() - this.mDividerHeight);
            float fInterpolate = (!this.mChildrenExpanded || viewState2.getAlpha() == 0.0f) ? 0.0f : this.mDividerAlpha;
            if (this.mUserLocked && !showingAsLowPriority() && viewState2.getAlpha() != 0.0f) {
                fInterpolate = NotificationUtils.interpolate(0.0f, this.mDividerAlpha, Math.min(viewState2.getAlpha(), groupExpandFraction));
            }
            viewState.hidden = !z;
            viewState.setAlpha(fInterpolate);
            viewState.applyToView(view);
            expandableNotificationRow.setFakeShadowIntensity(0.0f, 0.0f, 0, 0);
        }
        ViewState viewState3 = this.mGroupOverFlowState;
        if (viewState3 != null) {
            viewState3.applyToView(this.mOverflowNumber);
            this.mNeverAppliedGroupState = false;
        }
        ViewState viewState4 = this.mHeaderViewState;
        if (viewState4 != null) {
            viewState4.applyToView(this.mGroupHeader);
        }
        updateChildrenClipping();
    }

    @Override // android.view.ViewGroup
    protected boolean drawChild(Canvas canvas, View view, long j) {
        boolean z;
        Path path = this.mChildClipPath;
        boolean z2 = true;
        if (path != null) {
            float translation = view instanceof ExpandableNotificationRow ? ((ExpandableNotificationRow) view).getTranslation() : view.getTranslationX();
            canvas.save();
            if (translation != 0.0f) {
                path.offset(translation, 0.0f);
                canvas.clipPath(path);
                path.offset(-translation, 0.0f);
            } else {
                canvas.clipPath(path);
            }
            z = true;
        } else {
            z = false;
        }
        if ((view instanceof NotificationHeaderView) && this.mGroupHeaderWrapper.hasRoundedCorner()) {
            float[] updatedRadii = this.mGroupHeaderWrapper.getUpdatedRadii();
            this.mHeaderPath.reset();
            this.mHeaderPath.addRoundRect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom(), updatedRadii, Path.Direction.CW);
            if (z) {
                z2 = z;
            } else {
                canvas.save();
            }
            canvas.clipPath(this.mHeaderPath);
            z = z2;
        }
        if (!z) {
            return super.drawChild(canvas, view, j);
        }
        boolean zDrawChild = super.drawChild(canvas, view, j);
        canvas.restore();
        return zDrawChild;
    }

    public List getAttachedChildren() {
        return this.mAttachedChildren;
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public int getClipHeight() {
        return Math.max(this.mActualHeight - this.mClipBottomAmount, 0);
    }

    public int getCollapsedHeight() {
        return getMinHeight(getMaxAllowedVisibleChildren(true), false);
    }

    public ExpandableNotificationRow getContainingNotification() {
        return this.mContainingNotification;
    }

    public ViewGroup getCurrentHeaderView() {
        return this.mCurrentHeader;
    }

    public float getGroupExpandFraction() {
        int maxContentHeight = showingAsLowPriority() ? getMaxContentHeight() : getVisibleChildrenExpandHeight();
        int collapsedHeight = getCollapsedHeight();
        return Math.max(0.0f, Math.min(1.0f, (this.mActualHeight - collapsedHeight) / (maxContentHeight - collapsedHeight)));
    }

    public NotificationHeaderView getGroupHeader() {
        return this.mGroupHeader;
    }

    public int getIntrinsicHeight() {
        return getIntrinsicHeight(getMaxAllowedVisibleChildren());
    }

    int getMaxAllowedVisibleChildren() {
        return getMaxAllowedVisibleChildren(false);
    }

    int getMaxAllowedVisibleChildren(boolean z) {
        if (!z && ((this.mChildrenExpanded || this.mContainingNotification.isUserLocked()) && !showingAsLowPriority())) {
            return 8;
        }
        if (this.mIsMinimized) {
            return 5;
        }
        if (this.mContainingNotification.isOnKeyguard() || !this.mContainingNotification.isExpanded()) {
            return (this.mContainingNotification.isHeadsUpState() && this.mContainingNotification.canShowHeadsUp()) ? 5 : 2;
        }
        return 5;
    }

    public int getMaxContentHeight() {
        if (showingAsLowPriority()) {
            return getMinHeight(5, true);
        }
        int maxExpandHeight = this.mNotificationHeaderMargin + this.mCurrentHeaderTranslation + this.mNotificationTopPadding;
        int size = this.mAttachedChildren.size();
        int i = 0;
        for (int i2 = 0; i2 < size && i < 8; i2++) {
            maxExpandHeight = (int) (maxExpandHeight + (((ExpandableNotificationRow) this.mAttachedChildren.get(i2)).isExpanded(true) ? r5.getMaxExpandHeight() : r5.getShowingLayout().getMinHeight(true)));
            i++;
        }
        return i > 0 ? maxExpandHeight + (i * this.mDividerHeight) : maxExpandHeight;
    }

    public int getMinHeight() {
        return getMinHeight(2, false);
    }

    public NotificationViewWrapper getMinimizedGroupHeaderWrapper() {
        return this.mMinimizedGroupHeaderWrapper;
    }

    public NotificationHeaderView getMinimizedNotificationHeader() {
        return this.mMinimizedGroupHeader;
    }

    public int getNotificationChildCount() {
        return this.mAttachedChildren.size();
    }

    public NotificationHeaderViewWrapper getNotificationHeaderWrapper() {
        return this.mGroupHeaderWrapper;
    }

    public NotificationViewWrapper getNotificationViewWrapper() {
        return this.mGroupHeaderWrapper;
    }

    public int getPositionInLinearLayout(View view) {
        int intrinsicHeight = this.mNotificationHeaderMargin + this.mCurrentHeaderTranslation + this.mNotificationTopPadding;
        for (int i = 0; i < this.mAttachedChildren.size(); i++) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i);
            boolean z = expandableNotificationRow.getVisibility() != 8;
            if (z) {
                intrinsicHeight += this.mDividerHeight;
            }
            if (expandableNotificationRow == view) {
                return intrinsicHeight;
            }
            if (z) {
                intrinsicHeight += expandableNotificationRow.getIntrinsicHeight();
            }
        }
        return 0;
    }

    @Override // com.android.systemui.statusbar.notification.Roundable
    public RoundableState getRoundableState() {
        return this.mRoundableState;
    }

    public ExpandableNotificationRow getViewAtPosition(float f) {
        int size = this.mAttachedChildren.size();
        for (int i = 0; i < size; i++) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i);
            float translationY = expandableNotificationRow.getTranslationY();
            float fMax = Math.max(0, expandableNotificationRow.getClipTopAmount()) + translationY;
            float actualHeight = translationY + expandableNotificationRow.getActualHeight();
            if (f >= fMax && f <= actualHeight) {
                return expandableNotificationRow;
            }
        }
        return null;
    }

    public NotificationViewWrapper getVisibleWrapper() {
        return showingAsLowPriority() ? this.mMinimizedGroupHeaderWrapper : this.mGroupHeaderWrapper;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isUserLocked() {
        return this.mUserLocked;
    }

    @Override // android.view.View
    protected void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateGroupOverflow();
    }

    public void onExpansionChanged() {
        if (this.mIsMinimized) {
            boolean z = this.mUserLocked;
            if (z) {
                setUserLocked(z);
            }
            updateHeaderVisibility(true);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int iMin = Math.min(this.mAttachedChildren.size(), 8);
        for (int i5 = 0; i5 < iMin; i5++) {
            View view = (View) this.mAttachedChildren.get(i5);
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            ((View) this.mDividers.get(i5)).layout(0, 0, getWidth(), this.mDividerHeight);
        }
        if (this.mOverflowNumber != null) {
            int width = getLayoutDirection() == 1 ? 0 : getWidth() - this.mOverflowNumber.getMeasuredWidth();
            int measuredWidth = this.mOverflowNumber.getMeasuredWidth() + width;
            TextView textView = this.mOverflowNumber;
            textView.layout(width, 0, measuredWidth, textView.getMeasuredHeight());
        }
        NotificationHeaderView notificationHeaderView = this.mGroupHeader;
        if (notificationHeaderView != null) {
            notificationHeaderView.layout(0, 0, notificationHeaderView.getMeasuredWidth(), this.mGroupHeader.getMeasuredHeight());
        }
        NotificationHeaderView notificationHeaderView2 = this.mMinimizedGroupHeader;
        if (notificationHeaderView2 != null) {
            notificationHeaderView2.layout(0, 0, notificationHeaderView2.getMeasuredWidth(), this.mMinimizedGroupHeader.getMeasuredHeight());
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        TextView textView;
        Trace.beginSection("NotificationChildrenContainer#onMeasure");
        int mode = View.MeasureSpec.getMode(i2);
        boolean z = mode == 1073741824;
        boolean z2 = mode == Integer.MIN_VALUE;
        int size = View.MeasureSpec.getSize(i2);
        if (z || z2) {
            i2 = View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
        }
        int size2 = View.MeasureSpec.getSize(i);
        TextView textView2 = this.mOverflowNumber;
        if (textView2 != null) {
            textView2.measure(View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE), i2);
        }
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mDividerHeight, 1073741824);
        int iMin = this.mNotificationHeaderMargin + this.mNotificationTopPadding;
        int iMin2 = Math.min(this.mAttachedChildren.size(), 8);
        int maxAllowedVisibleChildren = getMaxAllowedVisibleChildren(true);
        int i3 = iMin2 > maxAllowedVisibleChildren ? maxAllowedVisibleChildren - 1 : -1;
        int i4 = 0;
        while (i4 < iMin2) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i4);
            expandableNotificationRow.setSingleLineWidthIndention((i4 != i3 || (textView = this.mOverflowNumber) == null) ? 0 : textView.getMeasuredWidth());
            expandableNotificationRow.measure(i, i2);
            ((View) this.mDividers.get(i4)).measure(i, iMakeMeasureSpec);
            if (expandableNotificationRow.getVisibility() != 8) {
                iMin += expandableNotificationRow.getMeasuredHeight() + this.mDividerHeight;
            }
            i4++;
        }
        this.mRealHeight = iMin;
        if (mode != 0) {
            iMin = Math.min(iMin, size);
        }
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mHeaderHeight, 1073741824);
        NotificationHeaderView notificationHeaderView = this.mGroupHeader;
        if (notificationHeaderView != null) {
            notificationHeaderView.measure(i, iMakeMeasureSpec2);
        }
        NotificationHeaderView notificationHeaderView2 = this.mMinimizedGroupHeader;
        if (notificationHeaderView2 != null) {
            notificationHeaderView2.measure(i, iMakeMeasureSpec2);
        }
        setMeasuredDimension(size2, iMin);
        Trace.endSection();
    }

    public void onNotificationUpdated() {
        if (this.mShowGroupCountInExpander) {
            return;
        }
        this.mHybridGroupManager.setOverflowNumberColor(this.mOverflowNumber, ((ViewGroup) this).mContext.getColor(R.color.materialColorSurfaceContainerLowest));
    }

    public boolean pointInView(float f, float f2, float f3) {
        float f4 = -f3;
        return f >= f4 && f2 >= f4 && f < ((float) (((ViewGroup) this).mRight - ((ViewGroup) this).mLeft)) + f3 && f2 < ((float) this.mRealHeight) + f3;
    }

    public void prepareExpansionChanged() {
    }

    public void reInflateViews(View.OnClickListener onClickListener, StatusBarNotification statusBarNotification) {
        if (!AsyncGroupHeaderViewInflation.isEnabled()) {
            NotificationHeaderView notificationHeaderView = this.mGroupHeader;
            if (notificationHeaderView != null) {
                removeView(notificationHeaderView);
                this.mGroupHeader = null;
            }
            NotificationHeaderView notificationHeaderView2 = this.mMinimizedGroupHeader;
            if (notificationHeaderView2 != null) {
                removeView(notificationHeaderView2);
                this.mMinimizedGroupHeader = null;
            }
            recreateNotificationHeader(onClickListener, this.mIsConversation);
        }
        initDimens();
        for (int i = 0; i < this.mDividers.size(); i++) {
            View view = (View) this.mDividers.get(i);
            int iIndexOfChild = indexOfChild(view);
            removeView(view);
            View viewInflateDivider = inflateDivider();
            addView(viewInflateDivider, iIndexOfChild);
            this.mDividers.set(i, viewInflateDivider);
        }
        removeView(this.mOverflowNumber);
        this.mOverflowNumber = null;
        this.mGroupOverFlowState = null;
        updateGroupOverflow();
    }

    void recreateLowPriorityHeader(Notification.Builder builder, boolean z) {
        AsyncGroupHeaderViewInflation.assertInLegacyMode();
        StatusBarNotification sbn = this.mContainingNotification.getEntry().getSbn();
        if (!this.mIsMinimized) {
            removeView(this.mMinimizedGroupHeader);
            this.mMinimizedGroupHeader = null;
            this.mMinimizedGroupHeaderWrapper = null;
            return;
        }
        if (builder == null) {
            builder = Notification.Builder.recoverBuilder(getContext(), sbn.getNotification());
        }
        RemoteViews remoteViewsMakeLowPriorityContentView = builder.makeLowPriorityContentView(true);
        if (this.mMinimizedGroupHeader == null) {
            NotificationHeaderView notificationHeaderViewApply = remoteViewsMakeLowPriorityContentView.apply(getContext(), this);
            this.mMinimizedGroupHeader = notificationHeaderViewApply;
            notificationHeaderViewApply.findViewById(R.id.expand_button_number).setVisibility(0);
            this.mMinimizedGroupHeader.setOnClickListener(this.mHeaderClickListener);
            this.mMinimizedGroupHeaderWrapper = (NotificationHeaderViewWrapper) NotificationViewWrapper.wrap(getContext(), this.mMinimizedGroupHeader, this.mContainingNotification);
            this.mGroupHeaderWrapper.setOnRoundnessChangedListener(new NotificationChildrenContainer$$ExternalSyntheticLambda0(this));
            addView((View) this.mMinimizedGroupHeader, 0);
            invalidate();
        } else {
            remoteViewsMakeLowPriorityContentView.reapply(getContext(), this.mMinimizedGroupHeader);
        }
        this.mMinimizedGroupHeaderWrapper.onContentUpdated(this.mContainingNotification);
        resetHeaderVisibilityIfNeeded(this.mMinimizedGroupHeader, calculateDesiredHeader());
    }

    public void recreateNotificationHeader(View.OnClickListener onClickListener, boolean z) {
        AsyncGroupHeaderViewInflation.assertInLegacyMode();
        Trace.beginSection("NotifChildCont#recreateHeader");
        this.mHeaderClickListener = onClickListener;
        this.mIsConversation = z;
        Notification.Builder builderRecoverBuilder = Notification.Builder.recoverBuilder(getContext(), this.mContainingNotification.getEntry().getSbn().getNotification());
        Trace.beginSection("recreateHeader#makeNotificationGroupHeader");
        RemoteViews remoteViewsMakeNotificationGroupHeader = builderRecoverBuilder.makeNotificationGroupHeader();
        Trace.endSection();
        if (this.mGroupHeader == null) {
            Trace.beginSection("recreateHeader#apply");
            this.mGroupHeader = remoteViewsMakeNotificationGroupHeader.apply(getContext(), this);
            Trace.endSection();
            this.mGroupHeader.findViewById(R.id.expand_button_number).setVisibility(0);
            this.mGroupHeader.setOnClickListener(this.mHeaderClickListener);
            NotificationHeaderViewWrapper notificationHeaderViewWrapper = (NotificationHeaderViewWrapper) NotificationViewWrapper.wrap(getContext(), this.mGroupHeader, this.mContainingNotification);
            this.mGroupHeaderWrapper = notificationHeaderViewWrapper;
            notificationHeaderViewWrapper.setOnRoundnessChangedListener(new NotificationChildrenContainer$$ExternalSyntheticLambda0(this));
            addView((View) this.mGroupHeader, 0);
            invalidate();
        } else {
            Trace.beginSection("recreateHeader#reapply");
            remoteViewsMakeNotificationGroupHeader.reapply(getContext(), this.mGroupHeader);
            Trace.endSection();
        }
        this.mGroupHeaderWrapper.setExpanded(this.mChildrenExpanded);
        this.mGroupHeaderWrapper.onContentUpdated(this.mContainingNotification);
        recreateLowPriorityHeader(builderRecoverBuilder, z);
        updateHeaderVisibility(false);
        updateChildrenAppearance();
        Trace.endSection();
    }

    public void removeNotification(ExpandableNotificationRow expandableNotificationRow) {
        int iIndexOf = this.mAttachedChildren.indexOf(expandableNotificationRow);
        this.mAttachedChildren.remove(expandableNotificationRow);
        removeView(expandableNotificationRow);
        final View view = (View) this.mDividers.remove(iIndexOf);
        removeView(view);
        getOverlay().add(view);
        CrossFadeHelper.fadeOut(view, new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer.2
            @Override // java.lang.Runnable
            public void run() {
                NotificationChildrenContainer.this.getOverlay().remove(view);
            }
        });
        expandableNotificationRow.setSystemChildExpanded(false);
        expandableNotificationRow.setNotificationFaded(false);
        expandableNotificationRow.setUserLocked(false);
        if (!expandableNotificationRow.isRemoved()) {
            this.mGroupingUtil.restoreChildNotification(expandableNotificationRow);
        }
        expandableNotificationRow.requestRoundnessReset(FROM_PARENT, false);
        applyRoundnessAndInvalidate();
    }

    public void removeTransientView(View view) {
        NotificationChildrenContainerLogger notificationChildrenContainerLogger = this.mLogger;
        if (notificationChildrenContainerLogger != null && (view instanceof ExpandableNotificationRow)) {
            notificationChildrenContainerLogger.removeTransientRow(((ExpandableNotificationRow) view).getEntry(), getContainingNotification().getEntry());
        }
        super.removeTransientView(view);
    }

    public void setActualHeight(int i) {
        if (this.mUserLocked) {
            this.mActualHeight = i;
            float groupExpandFraction = getGroupExpandFraction();
            boolean zShowingAsLowPriority = showingAsLowPriority();
            updateHeaderTransformation();
            int maxAllowedVisibleChildren = getMaxAllowedVisibleChildren(true);
            int size = this.mAttachedChildren.size();
            for (int i2 = 0; i2 < size; i2++) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i2);
                float minHeight = zShowingAsLowPriority ? expandableNotificationRow.getShowingLayout().getMinHeight(false) : expandableNotificationRow.isExpanded(true) ? expandableNotificationRow.getMaxExpandHeight() : expandableNotificationRow.getShowingLayout().getMinHeight(true);
                if (i2 < maxAllowedVisibleChildren) {
                    expandableNotificationRow.setActualHeight((int) NotificationUtils.interpolate(expandableNotificationRow.getShowingLayout().getMinHeight(false), minHeight, groupExpandFraction), false);
                } else {
                    expandableNotificationRow.setActualHeight((int) minHeight, false);
                }
            }
        }
    }

    public void setChildClipPath(Path path) {
        this.mChildClipPath = path;
        invalidate();
    }

    public void setChildrenExpanded(boolean z) {
        this.mChildrenExpanded = z;
        updateExpansionStates();
        NotificationHeaderViewWrapper notificationHeaderViewWrapper = this.mGroupHeaderWrapper;
        if (notificationHeaderViewWrapper != null) {
            notificationHeaderViewWrapper.setExpanded(z);
        }
        int size = this.mAttachedChildren.size();
        for (int i = 0; i < size; i++) {
            ((ExpandableNotificationRow) this.mAttachedChildren.get(i)).setChildrenExpanded(z, false);
        }
        updateHeaderTouchability();
    }

    public void setClipBottomAmount(int i) {
        this.mClipBottomAmount = i;
        updateChildrenClipping();
    }

    public void setContainingNotification(ExpandableNotificationRow expandableNotificationRow) {
        this.mContainingNotification = expandableNotificationRow;
        this.mGroupingUtil = new NotificationGroupingUtil(this.mContainingNotification);
    }

    public void setContentAlpha(float f) {
        if (this.mGroupHeader != null) {
            for (int i = 0; i < this.mGroupHeader.getChildCount(); i++) {
                this.mGroupHeader.getChildAt(i).setAlpha(f);
            }
        }
        Iterator it = getAttachedChildren().iterator();
        while (it.hasNext()) {
            ((ExpandableNotificationRow) it.next()).setContentAlpha(f);
        }
    }

    public void setFeedbackIcon(FeedbackIcon feedbackIcon) {
        NotificationHeaderViewWrapper notificationHeaderViewWrapper = this.mGroupHeaderWrapper;
        if (notificationHeaderViewWrapper != null) {
            notificationHeaderViewWrapper.setFeedbackIcon(feedbackIcon);
        }
        NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = this.mMinimizedGroupHeaderWrapper;
        if (notificationHeaderViewWrapper2 != null) {
            notificationHeaderViewWrapper2.setFeedbackIcon(feedbackIcon);
        }
    }

    public void setGroupHeader(NotificationHeaderView notificationHeaderView, View.OnClickListener onClickListener) {
        if (AsyncGroupHeaderViewInflation.isUnexpectedlyInLegacyMode()) {
            return;
        }
        this.mHeaderClickListener = onClickListener;
        removeGroupHeader();
        if (notificationHeaderView == null) {
            return;
        }
        this.mGroupHeader = notificationHeaderView;
        notificationHeaderView.findViewById(R.id.expand_button_number).setVisibility(0);
        this.mGroupHeader.setOnClickListener(this.mHeaderClickListener);
        NotificationHeaderViewWrapper notificationHeaderViewWrapper = (NotificationHeaderViewWrapper) NotificationViewWrapper.wrap(getContext(), this.mGroupHeader, this.mContainingNotification);
        this.mGroupHeaderWrapper = notificationHeaderViewWrapper;
        notificationHeaderViewWrapper.setOnRoundnessChangedListener(new NotificationChildrenContainer$$ExternalSyntheticLambda0(this));
        addView((View) this.mGroupHeader, 0);
        invalidate();
        this.mGroupHeaderWrapper.setExpanded(this.mChildrenExpanded);
        this.mGroupHeaderWrapper.onContentUpdated(this.mContainingNotification);
        updateHeaderVisibility(false);
        updateChildrenAppearance();
        Trace.endSection();
    }

    public void setIsMinimized(boolean z) {
        this.mIsMinimized = z;
        if (this.mContainingNotification != null) {
            if (!AsyncGroupHeaderViewInflation.isEnabled()) {
                recreateLowPriorityHeader(null, this.mIsConversation);
            }
            updateHeaderVisibility(false);
        }
        boolean z2 = this.mUserLocked;
        if (z2) {
            setUserLocked(z2);
        }
    }

    public void setLogger(NotificationChildrenContainerLogger notificationChildrenContainerLogger) {
        this.mLogger = notificationChildrenContainerLogger;
    }

    public void setLowPriorityGroupHeader(NotificationHeaderView notificationHeaderView, View.OnClickListener onClickListener) {
        if (AsyncGroupHeaderViewInflation.isUnexpectedlyInLegacyMode()) {
            return;
        }
        removeLowPriorityGroupHeader();
        if (notificationHeaderView == null) {
            return;
        }
        this.mMinimizedGroupHeader = notificationHeaderView;
        notificationHeaderView.findViewById(R.id.expand_button_number).setVisibility(0);
        this.mMinimizedGroupHeader.setOnClickListener(onClickListener);
        NotificationHeaderViewWrapper notificationHeaderViewWrapper = (NotificationHeaderViewWrapper) NotificationViewWrapper.wrap(getContext(), this.mMinimizedGroupHeader, this.mContainingNotification);
        this.mMinimizedGroupHeaderWrapper = notificationHeaderViewWrapper;
        notificationHeaderViewWrapper.setOnRoundnessChangedListener(new NotificationChildrenContainer$$ExternalSyntheticLambda0(this));
        addView((View) this.mMinimizedGroupHeader, 0);
        invalidate();
        this.mMinimizedGroupHeaderWrapper.onContentUpdated(this.mContainingNotification);
        updateHeaderVisibility(false);
        updateChildrenAppearance();
    }

    @Override // com.android.systemui.statusbar.notification.NotificationFadeAware
    public void setNotificationFaded(boolean z) {
        this.mContainingNotificationIsFaded = z;
        NotificationHeaderViewWrapper notificationHeaderViewWrapper = this.mGroupHeaderWrapper;
        if (notificationHeaderViewWrapper != null) {
            notificationHeaderViewWrapper.setNotificationFaded(z);
        }
        NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = this.mMinimizedGroupHeaderWrapper;
        if (notificationHeaderViewWrapper2 != null) {
            notificationHeaderViewWrapper2.setNotificationFaded(z);
        }
        Iterator it = this.mAttachedChildren.iterator();
        while (it.hasNext()) {
            ((ExpandableNotificationRow) it.next()).setNotificationFaded(z);
        }
    }

    public void setNotificationGroupWhen(long j) {
        NotificationHeaderViewWrapper notificationHeaderViewWrapper = this.mGroupHeaderWrapper;
        if (notificationHeaderViewWrapper != null) {
            notificationHeaderViewWrapper.setNotificationWhen(j);
        }
        NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = this.mMinimizedGroupHeaderWrapper;
        if (notificationHeaderViewWrapper2 != null) {
            notificationHeaderViewWrapper2.setNotificationWhen(j);
        }
    }

    public void setRecentlyAudiblyAlerted(boolean z) {
        NotificationHeaderViewWrapper notificationHeaderViewWrapper = this.mGroupHeaderWrapper;
        if (notificationHeaderViewWrapper != null) {
            notificationHeaderViewWrapper.setRecentlyAudiblyAlerted(z);
        }
        NotificationHeaderViewWrapper notificationHeaderViewWrapper2 = this.mMinimizedGroupHeaderWrapper;
        if (notificationHeaderViewWrapper2 != null) {
            notificationHeaderViewWrapper2.setRecentlyAudiblyAlerted(z);
        }
    }

    public void setUntruncatedChildCount(int i) {
        this.mUntruncatedChildCount = i;
        updateGroupOverflow();
    }

    public void setUserLocked(boolean z) {
        this.mUserLocked = z;
        if (!z) {
            updateHeaderVisibility(false);
        }
        int size = this.mAttachedChildren.size();
        for (int i = 0; i < size; i++) {
            ((ExpandableNotificationRow) this.mAttachedChildren.get(i)).setUserLocked(z && !showingAsLowPriority());
        }
        updateHeaderTouchability();
    }

    public boolean showingAsLowPriority() {
        return this.mIsMinimized && !this.mContainingNotification.isExpanded();
    }

    public void startAnimationToState(AnimationProperties animationProperties) {
        int size = this.mAttachedChildren.size();
        ViewState viewState = new ViewState();
        float groupExpandFraction = getGroupExpandFraction();
        boolean z = (this.mChildrenExpanded && this.mShowDividersWhenExpanded) || ((!showingAsLowPriority() && (this.mUserLocked || this.mContainingNotification.isGroupExpansionChanging())) && !this.mHideDividersDuringExpand);
        for (int i = size - 1; i >= 0; i--) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i);
            ExpandableViewState viewState2 = expandableNotificationRow.getViewState();
            viewState2.animateTo(expandableNotificationRow, animationProperties);
            View view = (View) this.mDividers.get(i);
            viewState.initFrom(view);
            viewState.setYTranslation(viewState2.getYTranslation() - this.mDividerHeight);
            float fInterpolate = (!this.mChildrenExpanded || viewState2.getAlpha() == 0.0f) ? 0.0f : this.mDividerAlpha;
            if (this.mUserLocked && !showingAsLowPriority() && viewState2.getAlpha() != 0.0f) {
                fInterpolate = NotificationUtils.interpolate(0.0f, this.mDividerAlpha, Math.min(viewState2.getAlpha(), groupExpandFraction));
            }
            viewState.hidden = !z;
            viewState.setAlpha(fInterpolate);
            viewState.animateTo(view, animationProperties);
            expandableNotificationRow.setFakeShadowIntensity(0.0f, 0.0f, 0, 0);
        }
        if (this.mOverflowNumber != null) {
            if (this.mNeverAppliedGroupState) {
                float alpha = this.mGroupOverFlowState.getAlpha();
                this.mGroupOverFlowState.setAlpha(0.0f);
                this.mGroupOverFlowState.applyToView(this.mOverflowNumber);
                this.mGroupOverFlowState.setAlpha(alpha);
                this.mNeverAppliedGroupState = false;
            }
            this.mGroupOverFlowState.animateTo(this.mOverflowNumber, animationProperties);
        }
        View view2 = this.mGroupHeader;
        if (view2 != null) {
            this.mHeaderViewState.applyToView(view2);
        }
        updateChildrenClipping();
    }

    public void updateChildrenAppearance() {
        this.mGroupingUtil.updateChildrenAppearance();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void updateExpansionStates() {
        /*
            r5 = this;
            boolean r0 = r5.mChildrenExpanded
            if (r0 != 0) goto L28
            boolean r0 = r5.mUserLocked
            if (r0 == 0) goto L9
            goto L28
        L9:
            java.util.List r0 = r5.mAttachedChildren
            int r0 = r0.size()
            r1 = 0
            r2 = r1
        L11:
            if (r2 >= r0) goto L28
            java.util.List r3 = r5.mAttachedChildren
            java.lang.Object r3 = r3.get(r2)
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = (com.android.systemui.statusbar.notification.row.ExpandableNotificationRow) r3
            if (r2 != 0) goto L21
            r4 = 1
            if (r0 != r4) goto L21
            goto L22
        L21:
            r4 = r1
        L22:
            r3.setSystemChildExpanded(r4)
            int r2 = r2 + 1
            goto L11
        L28:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer.updateExpansionStates():void");
    }

    public void updateGroupOverflow() {
        if (this.mShowGroupCountInExpander) {
            setExpandButtonNumber(this.mGroupHeaderWrapper);
            setExpandButtonNumber(this.mMinimizedGroupHeaderWrapper);
            return;
        }
        int maxAllowedVisibleChildren = getMaxAllowedVisibleChildren(true);
        int i = this.mUntruncatedChildCount;
        if (i > maxAllowedVisibleChildren) {
            this.mOverflowNumber = this.mHybridGroupManager.bindOverflowNumber(this.mOverflowNumber, i - maxAllowedVisibleChildren, this);
            if (this.mGroupOverFlowState == null) {
                this.mGroupOverFlowState = new ViewState();
                this.mNeverAppliedGroupState = true;
                return;
            }
            return;
        }
        TextView textView = this.mOverflowNumber;
        if (textView != null) {
            removeView(textView);
            if (isShown() && isAttachedToWindow()) {
                final TextView textView2 = this.mOverflowNumber;
                addTransientView(textView2, getTransientViewCount());
                CrossFadeHelper.fadeOut(textView2, new Runnable() { // from class: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer.3
                    @Override // java.lang.Runnable
                    public void run() {
                        NotificationChildrenContainer.this.removeTransientView(textView2);
                    }
                });
            }
            this.mOverflowNumber = null;
            this.mGroupOverFlowState = null;
        }
    }

    public void updateHeaderForExpansion(boolean z) {
        NotificationHeaderView notificationHeaderView = this.mGroupHeader;
        if (notificationHeaderView != null) {
            if (!z) {
                notificationHeaderView.setHeaderBackgroundDrawable((Drawable) null);
                return;
            }
            ColorDrawable colorDrawable = new ColorDrawable();
            colorDrawable.setColor(this.mContainingNotification.calculateBgColor());
            this.mGroupHeader.setHeaderBackgroundDrawable(colorDrawable);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v0 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v5 */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v7 */
    /* JADX WARN: Type inference failed for: r13v8 */
    /* JADX WARN: Type inference failed for: r7v0 */
    /* JADX WARN: Type inference failed for: r7v1, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* JADX WARN: Type inference failed for: r8v19 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v3, types: [int] */
    public void updateState(ExpandableViewState expandableViewState) {
        int maxAllowedVisibleChildren;
        float groupExpandFraction;
        int iInterpolate;
        ?? r13;
        float f;
        int size = this.mAttachedChildren.size();
        int i = this.mNotificationHeaderMargin + this.mCurrentHeaderTranslation;
        int maxAllowedVisibleChildren2 = getMaxAllowedVisibleChildren();
        int i2 = maxAllowedVisibleChildren2 - 1;
        ?? r7 = 0;
        boolean z = this.mUserLocked && !showingAsLowPriority();
        float f2 = 0.0f;
        if (this.mUserLocked) {
            groupExpandFraction = getGroupExpandFraction();
            maxAllowedVisibleChildren = getMaxAllowedVisibleChildren(true);
        } else {
            maxAllowedVisibleChildren = maxAllowedVisibleChildren2;
            groupExpandFraction = 0.0f;
        }
        boolean z2 = this.mChildrenExpanded && !this.mContainingNotification.isGroupExpansionChanging();
        int i3 = 0;
        ?? r132 = 1;
        while (i3 < size) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i3);
            if (r132 != 0) {
                if (z) {
                    iInterpolate = (int) (i + NotificationUtils.interpolate(f2, this.mNotificationTopPadding + this.mDividerHeight, groupExpandFraction));
                } else {
                    iInterpolate = i + (this.mChildrenExpanded ? this.mNotificationTopPadding + this.mDividerHeight : r7);
                }
                r13 = r7;
            } else if (z) {
                iInterpolate = (int) (i + NotificationUtils.interpolate(this.mChildPadding, this.mDividerHeight, groupExpandFraction));
                r13 = r132;
            } else {
                iInterpolate = i + (this.mChildrenExpanded ? this.mDividerHeight : this.mChildPadding);
                r13 = r132;
            }
            ExpandableViewState viewState = expandableNotificationRow.getViewState();
            int intrinsicHeight = expandableNotificationRow.getIntrinsicHeight();
            viewState.height = intrinsicHeight;
            viewState.setYTranslation(iInterpolate);
            viewState.hidden = r7;
            if (expandableNotificationRow.isExpandAnimationRunning() || this.mContainingNotification.hasExpandingChild()) {
                f = 0.0f;
                viewState.setZTranslation(expandableNotificationRow.getTranslationZ());
            } else if (z2 && this.mEnableShadowOnChildNotifications) {
                viewState.setZTranslation(expandableViewState.getZTranslation());
                f = 0.0f;
            } else {
                f = 0.0f;
                viewState.setZTranslation(0.0f);
            }
            viewState.hideSensitive = expandableViewState.hideSensitive;
            viewState.belowSpeedBump = expandableViewState.belowSpeedBump;
            viewState.clipTopAmount = r7;
            viewState.setAlpha(f);
            if (i3 < maxAllowedVisibleChildren) {
                viewState.setAlpha(showingAsLowPriority() ? groupExpandFraction : 1.0f);
            } else if (groupExpandFraction == 1.0f && i3 <= i2) {
                viewState.setAlpha((this.mActualHeight - viewState.getYTranslation()) / viewState.height);
                viewState.setAlpha(Math.max(0.0f, Math.min(1.0f, viewState.getAlpha())));
            }
            viewState.location = expandableViewState.location;
            viewState.inShelf = expandableViewState.inShelf;
            i = iInterpolate + intrinsicHeight;
            i3++;
            r7 = 0;
            f2 = 0.0f;
            r132 = r13;
        }
        if (this.mOverflowNumber != null) {
            ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) this.mAttachedChildren.get(Math.min(getMaxAllowedVisibleChildren(true), size) - 1);
            this.mGroupOverFlowState.copyFrom(expandableNotificationRow2.getViewState());
            if (this.mChildrenExpanded) {
                ViewState viewState2 = this.mGroupOverFlowState;
                viewState2.setYTranslation(viewState2.getYTranslation() + this.mNotificationHeaderMargin);
                this.mGroupOverFlowState.setAlpha(0.0f);
            } else {
                HybridNotificationView singleLineView = expandableNotificationRow2.getSingleLineView();
                if (singleLineView != null) {
                    TextView textView = singleLineView.getTextView();
                    if (textView.getVisibility() == 8) {
                        textView = singleLineView.getTitleView();
                    }
                    if (textView.getVisibility() != 8) {
                        singleLineView = textView;
                    }
                    this.mGroupOverFlowState.setAlpha(singleLineView.getAlpha());
                    this.mGroupOverFlowState.setYTranslation(this.mGroupOverFlowState.getYTranslation() + NotificationUtils.getRelativeYOffset(singleLineView, expandableNotificationRow2));
                }
            }
        }
        if (this.mGroupHeader != null) {
            if (this.mHeaderViewState == null) {
                this.mHeaderViewState = new ViewState();
            }
            this.mHeaderViewState.initFrom(this.mGroupHeader);
            if (this.mContainingNotification.hasExpandingChild()) {
                this.mHeaderViewState.setZTranslation(this.mGroupHeader.getTranslationZ());
            } else if (z2) {
                this.mHeaderViewState.setZTranslation(expandableViewState.getZTranslation());
            } else {
                this.mHeaderViewState.setZTranslation(0.0f);
            }
            this.mHeaderViewState.setYTranslation(this.mCurrentHeaderTranslation);
            this.mHeaderViewState.setAlpha(this.mHeaderVisibleAmount);
            this.mHeaderViewState.hidden = false;
        }
    }
}
