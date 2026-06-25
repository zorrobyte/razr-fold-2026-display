package com.android.systemui.statusbar.notification.stack;

import android.content.Context;
import android.content.res.Resources;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.policy.SystemBarUtils;
import com.android.systemui.res.R$bool;
import com.android.systemui.res.R$dimen;
import com.android.systemui.statusbar.EmptyShadeView;
import com.android.systemui.statusbar.notification.SourceType;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.notification.footer.ui.view.FooterView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.shared.NotificationsImprovedHunAnimation;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class StackScrollAlgorithm {
    private static final SourceType STACK_SCROLL_ALGO = SourceType.from("StackScrollAlgorithm");
    private boolean mClipNotificationScrollToTop;
    private int mCollapsedSize;
    private boolean mEnableNotificationClipping;
    private float mGapHeight;
    private float mGapHeightOnLockscreen;
    private int mHeadsUpAppearHeightBottom;
    float mHeadsUpAppearStartAboveScreen;
    float mHeadsUpInset;
    private final ViewGroup mHostView;
    private boolean mIsExpanded;
    private float mLargeCornerRadius;
    private int mMarginBottom;
    private float mNotificationScrimPadding;
    private float mPaddingBetweenElements;
    private int mPinnedZTranslationExtra;
    private float mQuickQsOffsetHeight;
    private float mSmallCornerRadius;
    private float mStackScrollHeightExtent;
    private StackScrollAlgorithmState mTempAlgorithmState = new StackScrollAlgorithmState();

    public interface SectionProvider {
        boolean beginsSection(View view, View view2);
    }

    public class StackScrollAlgorithmState {
        public ExpandableView firstViewInShelf;
        private float mCurrentExpandedYPosition;
        private float mCurrentYPosition;
        public int scrollY;
        public final ArrayList visibleChildren = new ArrayList();
    }

    public StackScrollAlgorithm(Context context, ViewGroup viewGroup) {
        this.mHostView = viewGroup;
        initView(context);
    }

    private boolean childNeedsGapHeight(SectionProvider sectionProvider, int i, View view, View view2) {
        return sectionProvider.beginsSection(view, view2) && i > 0 && !(view2 instanceof SectionHeaderView) && !(view instanceof FooterView);
    }

    private void clampHunToMaxTranslation(AmbientState ambientState, ExpandableNotificationRow expandableNotificationRow, ExpandableViewState expandableViewState) {
        float fMin = Math.min(ambientState.getMaxHeadsUpTranslation(), ambientState.getInnerHeight() + ambientState.getTopPadding() + ambientState.getStackTranslation());
        float fMin2 = Math.min(expandableViewState.getYTranslation(), fMin - expandableNotificationRow.getCollapsedHeight());
        expandableViewState.height = (int) Math.min(expandableViewState.height, fMin - fMin2);
        expandableViewState.setYTranslation(fMin2);
        float fComputeCornerRoundnessForPinnedHun = computeCornerRoundnessForPinnedHun(this.mHostView.getHeight(), ambientState.getStackY(), getMaxAllowedChildHeight(expandableNotificationRow), expandableNotificationRow.isLastInSection() ? 1.0f : this.mSmallCornerRadius / this.mLargeCornerRadius);
        SourceType sourceType = STACK_SCROLL_ALGO;
        expandableNotificationRow.requestBottomRoundness(fComputeCornerRoundnessForPinnedHun, sourceType);
        expandableNotificationRow.addOnDetachResetRoundness(sourceType);
    }

    private void getNotificationChildrenStates(StackScrollAlgorithmState stackScrollAlgorithmState) {
        int size = stackScrollAlgorithmState.visibleChildren.size();
        for (int i = 0; i < size; i++) {
            ExpandableView expandableView = (ExpandableView) stackScrollAlgorithmState.visibleChildren.get(i);
            if (expandableView instanceof ExpandableNotificationRow) {
                ((ExpandableNotificationRow) expandableView).updateChildrenStates();
            }
        }
    }

    private ExpandableView getPreviousView(int i, StackScrollAlgorithmState stackScrollAlgorithmState) {
        if (i > 0) {
            return (ExpandableView) stackScrollAlgorithmState.visibleChildren.get(i - 1);
        }
        return null;
    }

    private boolean hasNonClearableNotifs(StackScrollAlgorithmState stackScrollAlgorithmState) {
        for (int i = 0; i < stackScrollAlgorithmState.visibleChildren.size(); i++) {
            View view = (View) stackScrollAlgorithmState.visibleChildren.get(i);
            if ((view instanceof ExpandableNotificationRow) && !((ExpandableNotificationRow) view).canViewBeCleared()) {
                return true;
            }
        }
        return false;
    }

    private void initAlgorithmState(StackScrollAlgorithmState stackScrollAlgorithmState, AmbientState ambientState) {
        stackScrollAlgorithmState.scrollY = ambientState.getScrollY();
        stackScrollAlgorithmState.mCurrentYPosition = -r0;
        stackScrollAlgorithmState.mCurrentExpandedYPosition = -stackScrollAlgorithmState.scrollY;
        int childCount = this.mHostView.getChildCount();
        stackScrollAlgorithmState.visibleChildren.clear();
        stackScrollAlgorithmState.visibleChildren.ensureCapacity(childCount);
        int iUpdateNotGoneIndex = 0;
        for (int i = 0; i < childCount; i++) {
            ExpandableView expandableView = (ExpandableView) this.mHostView.getChildAt(i);
            if (expandableView.getVisibility() != 8) {
                iUpdateNotGoneIndex = updateNotGoneIndex(stackScrollAlgorithmState, iUpdateNotGoneIndex, expandableView);
                if (expandableView instanceof ExpandableNotificationRow) {
                    ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) expandableView;
                    List<ExpandableNotificationRow> attachedChildren = expandableNotificationRow.getAttachedChildren();
                    if (expandableNotificationRow.isSummaryWithChildren() && attachedChildren != null) {
                        for (ExpandableNotificationRow expandableNotificationRow2 : attachedChildren) {
                            if (expandableNotificationRow2.getVisibility() != 8) {
                                expandableNotificationRow2.getViewState().notGoneIndex = iUpdateNotGoneIndex;
                                iUpdateNotGoneIndex++;
                            }
                        }
                    }
                }
            }
        }
        ambientState.getScrollY();
        if (ambientState.isOnKeyguard() && ambientState.isBypassEnabled()) {
            ambientState.isPulseExpanding();
        }
        stackScrollAlgorithmState.firstViewInShelf = null;
        for (int i2 = 0; i2 < stackScrollAlgorithmState.visibleChildren.size(); i2++) {
            ExpandableView expandableView2 = (ExpandableView) stackScrollAlgorithmState.visibleChildren.get(i2);
            if (childNeedsGapHeight(ambientState.getSectionProvider(), i2, expandableView2, getPreviousView(i2, stackScrollAlgorithmState))) {
                getGapForLocation(ambientState.getFractionToShade(), ambientState.isOnKeyguard());
            }
            getMaxAllowedChildHeight(expandableView2);
        }
    }

    private void resetChildViewStates() {
        int childCount = this.mHostView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ((ExpandableView) this.mHostView.getChildAt(i)).resetViewState();
        }
    }

    private void setLocation(ExpandableViewState expandableViewState, float f, int i) {
        expandableViewState.location = 4;
        if (f <= 0.0f) {
            expandableViewState.location = 2;
        }
    }

    private void updateAlphaState(StackScrollAlgorithmState stackScrollAlgorithmState, AmbientState ambientState) {
        ArrayList arrayList = stackScrollAlgorithmState.visibleChildren;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            ExpandableView expandableView = (ExpandableView) obj;
            ExpandableViewState viewState = expandableView.getViewState();
            if (ambientState.isShadeExpanded() && expandableView == ambientState.getTrackedHeadsUpRow()) {
                viewState.setAlpha(1.0f);
            }
        }
    }

    private void updateClipping(StackScrollAlgorithmState stackScrollAlgorithmState, AmbientState ambientState) {
        float fMax = 0.0f;
        float stackY = ambientState.isOnKeyguard() ? 0.0f : ambientState.getStackY() - ambientState.getScrollY();
        int size = stackScrollAlgorithmState.visibleChildren.size();
        float f = 0.0f;
        boolean z = true;
        for (int i = 0; i < size; i++) {
            ExpandableView expandableView = (ExpandableView) stackScrollAlgorithmState.visibleChildren.get(i);
            ExpandableViewState viewState = expandableView.getViewState();
            if (!expandableView.mustStayOnScreen() || viewState.headsUpIsVisible) {
                fMax = Math.max(stackY, fMax);
            }
            float yTranslation = viewState.getYTranslation();
            float f2 = viewState.height + yTranslation;
            boolean z2 = (expandableView instanceof ExpandableNotificationRow) && expandableView.isPinned();
            if (!this.mClipNotificationScrollToTop || z || (!(z2 || expandableView.isHeadsUpAnimatingAway()) || f2 <= f || ambientState.isShadeExpanded())) {
                viewState.clipBottomAmount = 0;
            } else {
                viewState.clipBottomAmount = this.mEnableNotificationClipping ? (int) (f2 - f) : 0;
            }
            if (z) {
                f = f2;
            }
            if (z2) {
                z = false;
            }
            if (!expandableView.isTransparent()) {
                if (!z2) {
                    yTranslation = f2;
                }
                fMax = Math.max(fMax, yTranslation);
            }
        }
    }

    private void updateDimmedAndHideSensitive(AmbientState ambientState, StackScrollAlgorithmState stackScrollAlgorithmState) {
        boolean zIsHideSensitive = ambientState.isHideSensitive();
        int size = stackScrollAlgorithmState.visibleChildren.size();
        for (int i = 0; i < size; i++) {
            ((ExpandableView) stackScrollAlgorithmState.visibleChildren.get(i)).getViewState().hideSensitive = zIsHideSensitive;
        }
    }

    private void updateHeadsUpStates(StackScrollAlgorithmState stackScrollAlgorithmState, AmbientState ambientState) {
        ExpandableNotificationRow expandableNotificationRow;
        ExpandableViewState viewState;
        int size = stackScrollAlgorithmState.visibleChildren.size();
        float stackTopMargin = this.mHeadsUpInset - ambientState.getStackTopMargin();
        ExpandableNotificationRow trackedHeadsUpRow = ambientState.getTrackedHeadsUpRow();
        if (trackedHeadsUpRow != null && (viewState = trackedHeadsUpRow.getViewState()) != null) {
            viewState.setYTranslation(MathUtils.lerp(stackTopMargin, viewState.getYTranslation() - ambientState.getStackTranslation(), ambientState.getAppearFraction()));
        }
        ExpandableNotificationRow expandableNotificationRow2 = null;
        for (int i = 0; i < size; i++) {
            View view = (View) stackScrollAlgorithmState.visibleChildren.get(i);
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) view;
                if (expandableNotificationRow3.isHeadsUp() || expandableNotificationRow3.isHeadsUpAnimatingAway()) {
                    ExpandableViewState viewState2 = expandableNotificationRow3.getViewState();
                    if (expandableNotificationRow2 == null && expandableNotificationRow3.mustStayOnScreen() && !viewState2.headsUpIsVisible) {
                        viewState2.location = 1;
                        expandableNotificationRow = expandableNotificationRow3;
                    } else {
                        expandableNotificationRow = expandableNotificationRow2;
                    }
                    boolean z = expandableNotificationRow == expandableNotificationRow3;
                    float yTranslation = viewState2.getYTranslation() + viewState2.height;
                    if (this.mIsExpanded && shouldHunBeVisibleWhenScrolled(expandableNotificationRow3.mustStayOnScreen(), viewState2.headsUpIsVisible, expandableNotificationRow3.showingPulsing(), ambientState.isOnKeyguard(), expandableNotificationRow3.getEntry().isStickyAndNotDemoted())) {
                        clampHunToTop(ambientState.getUseSplitShade() ? this.mNotificationScrimPadding : this.mQuickQsOffsetHeight, ambientState.getStackTranslation(), expandableNotificationRow3.getCollapsedHeight(), viewState2);
                        if (z && expandableNotificationRow3.isAboveShelf()) {
                            clampHunToMaxTranslation(ambientState, expandableNotificationRow3, viewState2);
                            viewState2.hidden = false;
                        }
                    }
                    if (expandableNotificationRow3.isPinned()) {
                        viewState2.setYTranslation(Math.max(viewState2.getYTranslation(), stackTopMargin));
                        viewState2.height = Math.max(expandableNotificationRow3.getIntrinsicHeight(), viewState2.height);
                        viewState2.hidden = false;
                        ExpandableViewState viewState3 = expandableNotificationRow == null ? null : expandableNotificationRow.getViewState();
                        if (viewState3 != null && !z && (!this.mIsExpanded || yTranslation > viewState3.getYTranslation() + viewState3.height)) {
                            viewState2.height = expandableNotificationRow3.getIntrinsicHeight();
                        }
                        if (!this.mIsExpanded && z && ambientState.getScrollY() > 0) {
                            viewState2.setYTranslation(viewState2.getYTranslation() - ambientState.getScrollY());
                        }
                    }
                    if (expandableNotificationRow3.isHeadsUpAnimatingAway()) {
                        if (!NotificationsImprovedHunAnimation.isEnabled() || ambientState.isDozing()) {
                            viewState2.setYTranslation(Math.max(viewState2.getYTranslation(), stackTopMargin));
                        } else if (shouldHunAppearFromBottom(ambientState, viewState2)) {
                            viewState2.setYTranslation(this.mHeadsUpAppearHeightBottom + this.mHeadsUpAppearStartAboveScreen);
                        } else {
                            viewState2.setYTranslation((-ambientState.getStackTopMargin()) - this.mHeadsUpAppearStartAboveScreen);
                        }
                        viewState2.hidden = false;
                    }
                    expandableNotificationRow2 = expandableNotificationRow;
                }
            }
        }
    }

    private int updateNotGoneIndex(StackScrollAlgorithmState stackScrollAlgorithmState, int i, ExpandableView expandableView) {
        expandableView.getViewState().notGoneIndex = i;
        stackScrollAlgorithmState.visibleChildren.add(expandableView);
        return i + 1;
    }

    private void updateResources(Context context) {
        Resources resources = context.getResources();
        this.mPaddingBetweenElements = resources.getDimensionPixelSize(R$dimen.notification_divider_height);
        this.mCollapsedSize = resources.getDimensionPixelSize(R$dimen.notification_min_height);
        this.mEnableNotificationClipping = resources.getBoolean(R$bool.notification_enable_clipping);
        this.mClipNotificationScrollToTop = resources.getBoolean(R$bool.config_clipNotificationScrollToTop);
        this.mHeadsUpInset = SystemBarUtils.getStatusBarHeight(context) + resources.getDimensionPixelSize(R$dimen.heads_up_status_bar_padding);
        this.mHeadsUpAppearStartAboveScreen = resources.getDimensionPixelSize(R$dimen.heads_up_appear_y_above_screen);
        this.mPinnedZTranslationExtra = resources.getDimensionPixelSize(R$dimen.heads_up_pinned_elevation);
        this.mGapHeight = resources.getDimensionPixelSize(R$dimen.notification_section_divider_height);
        this.mGapHeightOnLockscreen = resources.getDimensionPixelSize(R$dimen.notification_section_divider_height_lockscreen);
        this.mNotificationScrimPadding = resources.getDimensionPixelSize(R$dimen.notification_side_paddings);
        this.mMarginBottom = resources.getDimensionPixelSize(R$dimen.notification_panel_margin_bottom);
        this.mQuickQsOffsetHeight = SystemBarUtils.getQuickQsOffsetHeight(context);
        this.mSmallCornerRadius = resources.getDimension(R$dimen.notification_corner_radius_small);
        this.mLargeCornerRadius = resources.getDimension(R$dimen.notification_corner_radius);
    }

    private void updateSpeedBumpState(StackScrollAlgorithmState stackScrollAlgorithmState, int i) {
        int size = stackScrollAlgorithmState.visibleChildren.size();
        int i2 = 0;
        while (i2 < size) {
            ((ExpandableView) stackScrollAlgorithmState.visibleChildren.get(i2)).getViewState().belowSpeedBump = i2 >= i;
            i2++;
        }
    }

    void clampHunToTop(float f, float f2, float f3, ExpandableViewState expandableViewState) {
        float fMax = Math.max(f + f2, expandableViewState.getYTranslation());
        expandableViewState.height = (int) Math.max(expandableViewState.height - (fMax - expandableViewState.getYTranslation()), f3);
        expandableViewState.setYTranslation(fMax);
    }

    float computeCornerRoundnessForPinnedHun(float f, float f2, float f3, float f4) {
        return MathUtils.lerp(f4, 1.0f, Math.min(1.0f, Math.max(0.0f, f2 - (f - f3)) / f3));
    }

    float getGapForLocation(float f, boolean z) {
        return f > 0.0f ? MathUtils.lerp(this.mGapHeightOnLockscreen, this.mGapHeight, f) : z ? this.mGapHeightOnLockscreen : this.mGapHeight;
    }

    public float getGapHeightForChild(SectionProvider sectionProvider, int i, View view, View view2, float f, boolean z) {
        if (childNeedsGapHeight(sectionProvider, i, view, view2)) {
            return getGapForLocation(f, z);
        }
        return 0.0f;
    }

    protected int getMaxAllowedChildHeight(View view) {
        return view instanceof ExpandableView ? ((ExpandableView) view).getIntrinsicHeight() : view == null ? this.mCollapsedSize : view.getHeight();
    }

    public float getStackScrollHeightExtent() {
        return this.mStackScrollHeightExtent;
    }

    public void initView(Context context) {
        updateResources(context);
    }

    void maybeUpdateHeadsUpIsVisible(ExpandableViewState expandableViewState, boolean z, boolean z2, boolean z3, float f, float f2) {
        if (z && z2 && z3) {
            expandableViewState.headsUpIsVisible = f < f2;
        }
    }

    public void resetViewStates(AmbientState ambientState, int i) {
        StackScrollAlgorithmState stackScrollAlgorithmState = this.mTempAlgorithmState;
        resetChildViewStates();
        initAlgorithmState(stackScrollAlgorithmState, ambientState);
        updatePositionsForState(stackScrollAlgorithmState, ambientState);
        updateHeadsUpStates(stackScrollAlgorithmState, ambientState);
        updatePulsingStates(stackScrollAlgorithmState, ambientState);
        updateDimmedAndHideSensitive(ambientState, stackScrollAlgorithmState);
        updateClipping(stackScrollAlgorithmState, ambientState);
        updateSpeedBumpState(stackScrollAlgorithmState, i);
        updateAlphaState(stackScrollAlgorithmState, ambientState);
        getNotificationChildrenStates(stackScrollAlgorithmState);
    }

    public boolean shouldHunAppearFromBottom(AmbientState ambientState, ExpandableViewState expandableViewState) {
        return expandableViewState.getYTranslation() + ((float) expandableViewState.height) >= ambientState.getMaxHeadsUpTranslation();
    }

    boolean shouldHunBeVisibleWhenScrolled(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        if (!z || z2 || z3) {
            return false;
        }
        return !z4 || z5;
    }

    protected void updateChild(int i, StackScrollAlgorithmState stackScrollAlgorithmState, AmbientState ambientState) {
        ExpandableView expandableView = (ExpandableView) stackScrollAlgorithmState.visibleChildren.get(i);
        ExpandableViewState viewState = expandableView.getViewState();
        boolean z = false;
        viewState.location = 0;
        if (childNeedsGapHeight(ambientState.getSectionProvider(), i, expandableView, getPreviousView(i, stackScrollAlgorithmState))) {
            float gapForLocation = getGapForLocation(ambientState.getFractionToShade(), ambientState.isOnKeyguard());
            stackScrollAlgorithmState.mCurrentYPosition += gapForLocation * 1.0f;
            stackScrollAlgorithmState.mCurrentExpandedYPosition += gapForLocation;
        }
        viewState.setYTranslation(stackScrollAlgorithmState.mCurrentYPosition);
        maybeUpdateHeadsUpIsVisible(viewState, ambientState.isShadeExpanded(), expandableView.mustStayOnScreen(), viewState.getYTranslation() >= this.mNotificationScrimPadding, viewState.getYTranslation() + viewState.height + ambientState.getStackY(), ambientState.getMaxHeadsUpTranslation());
        if (!(expandableView instanceof FooterView)) {
            if (expandableView instanceof EmptyShadeView) {
                viewState.setYTranslation((((ambientState.getLayoutMaxHeight() + this.mMarginBottom) - ambientState.getStackY()) - getMaxAllowedChildHeight(expandableView)) / 2.0f);
            } else if (expandableView != ambientState.getTrackedHeadsUpRow() && ambientState.isExpansionChanging()) {
                viewState.hidden = false;
                ExpandableView expandableView2 = stackScrollAlgorithmState.firstViewInShelf;
                if (expandableView2 != null && i >= stackScrollAlgorithmState.visibleChildren.indexOf(expandableView2)) {
                    z = true;
                }
                viewState.inShelf = z;
            }
            viewState.height = getMaxAllowedChildHeight(expandableView);
            if (!expandableView.isPinned() && !expandableView.isHeadsUpAnimatingAway() && !ambientState.isPulsingRow(expandableView)) {
                viewState.height = (int) (viewState.height * 1.0f);
            }
        } else if (!FooterViewRefactor.isEnabled()) {
            boolean zIsShadeExpanded = ambientState.isShadeExpanded();
            boolean z2 = stackScrollAlgorithmState.firstViewInShelf != null;
            if (zIsShadeExpanded) {
                boolean z3 = stackScrollAlgorithmState.mCurrentExpandedYPosition + ((float) expandableView.getIntrinsicHeight()) > ambientState.getStackEndHeight();
                FooterView.FooterViewState footerViewState = (FooterView.FooterViewState) viewState;
                if (z2 || z3 || (ambientState.isClearAllInProgress() && !hasNonClearableNotifs(stackScrollAlgorithmState))) {
                    z = true;
                }
                footerViewState.hideContent = z;
            } else {
                viewState.hidden = true;
            }
        } else if (((FooterView) expandableView).shouldBeHidden() || !ambientState.isShadeExpanded()) {
            viewState.hidden = true;
        } else {
            FooterView.FooterViewState footerViewState2 = (FooterView.FooterViewState) viewState;
            if ((stackScrollAlgorithmState.mCurrentExpandedYPosition + ((float) expandableView.getIntrinsicHeight()) > ambientState.getStackEndHeight()) || (ambientState.isClearAllInProgress() && !hasNonClearableNotifs(stackScrollAlgorithmState))) {
                z = true;
            }
            footerViewState2.hideContent = z;
        }
        stackScrollAlgorithmState.mCurrentYPosition += 1.0f * (getMaxAllowedChildHeight(expandableView) + this.mPaddingBetweenElements);
        stackScrollAlgorithmState.mCurrentExpandedYPosition += expandableView.getIntrinsicHeight() + this.mPaddingBetweenElements;
        setLocation(expandableView.getViewState(), stackScrollAlgorithmState.mCurrentYPosition, i);
        viewState.setYTranslation(viewState.getYTranslation() + ambientState.getStackY());
    }

    protected void updatePositionsForState(StackScrollAlgorithmState stackScrollAlgorithmState, AmbientState ambientState) {
        if (!ambientState.isOnKeyguard() || (ambientState.isBypassEnabled() && ambientState.isPulseExpanding())) {
            stackScrollAlgorithmState.mCurrentYPosition += this.mNotificationScrimPadding;
            stackScrollAlgorithmState.mCurrentExpandedYPosition += this.mNotificationScrimPadding;
        }
        int size = stackScrollAlgorithmState.visibleChildren.size();
        for (int i = 0; i < size; i++) {
            updateChild(i, stackScrollAlgorithmState, ambientState);
        }
        this.mStackScrollHeightExtent = stackScrollAlgorithmState.mCurrentYPosition;
    }

    void updatePulsingStates(StackScrollAlgorithmState stackScrollAlgorithmState, AmbientState ambientState) {
        int size = stackScrollAlgorithmState.visibleChildren.size();
        ExpandableNotificationRow expandableNotificationRow = null;
        for (int i = 0; i < size; i++) {
            View view = (View) stackScrollAlgorithmState.visibleChildren.get(i);
            if (view instanceof ExpandableNotificationRow) {
                ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) view;
                if (expandableNotificationRow2.showingPulsing() && (i != 0 || !ambientState.isPulseExpanding())) {
                    expandableNotificationRow2.getViewState().hidden = false;
                    expandableNotificationRow = expandableNotificationRow2;
                }
            }
        }
        if (ambientState.getDozeAmount() == 0.0f || ambientState.getDozeAmount() == 1.0f) {
            ambientState.setPulsingRow(expandableNotificationRow);
        }
    }

    public void updateQSFrameTop(int i) {
    }

    void updateViewWithShelf(ExpandableView expandableView, ExpandableViewState expandableViewState, float f) {
        expandableViewState.setYTranslation(Math.min(expandableViewState.getYTranslation(), f));
        if (expandableViewState.getYTranslation() >= f) {
            expandableViewState.hidden = (expandableView.isExpandAnimationRunning() || expandableView.hasExpandingChild()) ? false : true;
            expandableViewState.inShelf = true;
            expandableViewState.headsUpIsVisible = false;
        }
    }
}
