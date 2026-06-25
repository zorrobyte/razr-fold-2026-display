package com.android.systemui.statusbar;

import android.R;
import android.app.Notification;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.widget.CachingIconView;
import com.android.internal.widget.ConversationLayout;
import com.android.internal.widget.ImageFloatingTextView;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.shared.AsyncGroupHeaderViewInflation;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class NotificationGroupingUtil {
    private static final VisibilityApplicator APP_NAME_APPLICATOR;
    private static final TextViewComparator APP_NAME_COMPARATOR;
    private static final ViewComparator BADGE_COMPARATOR;
    private static final ResultApplicator LEFT_ICON_APPLICATOR;
    private static final TextViewComparator TEXT_VIEW_COMPARATOR;
    private static final VisibilityApplicator VISIBILITY_APPLICATOR;
    private final HashSet mDividers;
    private final ArrayList mProcessors;
    private final ExpandableNotificationRow mRow;
    private static final DataExtractor ICON_EXTRACTOR = new DataExtractor() { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.1
        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.DataExtractor
        public Object extractData(ExpandableNotificationRow expandableNotificationRow) {
            return expandableNotificationRow.getEntry().getSbn().getNotification();
        }
    };
    private static final IconComparator ICON_VISIBILITY_COMPARATOR = new IconComparator() { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.2
        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public boolean compare(View view, View view2, Object obj, Object obj2) {
            return hasSameIcon(obj, obj2) && hasSameColor(obj, obj2);
        }
    };
    private static final IconComparator GREY_COMPARATOR = new IconComparator() { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.3
        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public boolean compare(View view, View view2, Object obj, Object obj2) {
            return !hasSameIcon(obj, obj2) || hasSameColor(obj, obj2);
        }
    };
    private static final ResultApplicator GREY_APPLICATOR = new ResultApplicator() { // from class: com.android.systemui.statusbar.NotificationGroupingUtil.4
        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
        public void apply(View view, View view2, boolean z, boolean z2) {
            CachingIconView cachingIconViewFindViewById = view2.findViewById(R.id.icon);
            if (cachingIconViewFindViewById != null) {
                cachingIconViewFindViewById.setGrayedOut(z);
            }
        }
    };

    class AppNameApplicator extends VisibilityApplicator {
        private AppNameApplicator() {
            super();
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.VisibilityApplicator, com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
        public void apply(View view, View view2, boolean z, boolean z2) {
            if (z2 && (view instanceof ConversationLayout)) {
                z = ((ConversationLayout) view).shouldHideAppName();
            }
            super.apply(view, view2, z, z2);
        }
    }

    class AppNameComparator extends TextViewComparator {
        private AppNameComparator() {
            super();
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.TextViewComparator, com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public boolean compare(View view, View view2, Object obj, Object obj2) {
            if (isEmpty(view2)) {
                return true;
            }
            return super.compare(view, view2, obj, obj2);
        }
    }

    class BadgeComparator implements ViewComparator {
        private BadgeComparator() {
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public boolean compare(View view, View view2, Object obj, Object obj2) {
            return view.getVisibility() != 8;
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public boolean isEmpty(View view) {
            if (AsyncGroupHeaderViewInflation.isEnabled() && view == null) {
                return true;
            }
            return (view instanceof ImageView) && ((ImageView) view).getDrawable() == null;
        }
    }

    interface DataExtractor {
        Object extractData(ExpandableNotificationRow expandableNotificationRow);
    }

    abstract class IconComparator implements ViewComparator {
        private IconComparator() {
        }

        protected boolean hasSameColor(Object obj, Object obj2) {
            return ((Notification) obj).color == ((Notification) obj2).color;
        }

        protected boolean hasSameIcon(Object obj, Object obj2) {
            return ((Notification) obj).getSmallIcon().sameAs(((Notification) obj2).getSmallIcon());
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public boolean isEmpty(View view) {
            return false;
        }
    }

    class LeftIconApplicator implements ResultApplicator {
        public static final int[] MARGIN_ADJUSTED_VIEWS = {R.id.textPostalAddress, R.id.blocksDescendants, R.id.title, R.id.notification_top_line, R.id.notification_messaging};

        private LeftIconApplicator() {
        }

        void adjustMargins(boolean z, View view) {
            if (view == null) {
                return;
            }
            if (view instanceof ImageFloatingTextView) {
                ((ImageFloatingTextView) view).setHasImage(z);
                return;
            }
            Integer num = (Integer) view.getTag(z ? R.id.textNoSuggestions : R.id.textMultiLine);
            if (num == null) {
                return;
            }
            int iComplexToDimensionPixelOffset = TypedValue.complexToDimensionPixelOffset(num.intValue(), view.getResources().getDisplayMetrics());
            if (view instanceof NotificationHeaderView) {
                ((NotificationHeaderView) view).setTopLineExtraMarginEnd(iComplexToDimensionPixelOffset);
                return;
            }
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).setMarginEnd(iComplexToDimensionPixelOffset);
                view.setLayoutParams(layoutParams);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x002d  */
        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct add '--show-bad-code' argument
        */
        public void apply(android.view.View r6, android.view.View r7, boolean r8, boolean r9) {
            /*
                r5 = this;
                r6 = 16909253(0x10203c5, float:2.3879934E-38)
                android.view.View r6 = r7.findViewById(r6)
                android.widget.ImageView r6 = (android.widget.ImageView) r6
                if (r6 != 0) goto Ld
                goto L7f
            Ld:
                r9 = 16909537(0x10204e1, float:2.388073E-38)
                android.view.View r9 = r7.findViewById(r9)
                android.widget.ImageView r9 = (android.widget.ImageView) r9
                r0 = 0
                r1 = 1
                if (r9 == 0) goto L2d
                java.lang.Integer r2 = java.lang.Integer.valueOf(r1)
                r3 = 16909696(0x1020580, float:2.3881175E-38)
                java.lang.Object r3 = r9.getTag(r3)
                boolean r2 = r2.equals(r3)
                if (r2 == 0) goto L2d
                r2 = r1
                goto L2e
            L2d:
                r2 = r0
            L2e:
                java.lang.Integer r3 = java.lang.Integer.valueOf(r1)
                r4 = 16909702(0x1020586, float:2.3881192E-38)
                java.lang.Object r4 = r6.getTag(r4)
                boolean r3 = r3.equals(r4)
                if (r3 == 0) goto L50
                r3 = 0
                if (r9 != 0) goto L44
                r4 = r3
                goto L48
            L44:
                android.graphics.drawable.Drawable r4 = r9.getDrawable()
            L48:
                if (r8 == 0) goto L4d
                if (r2 != 0) goto L4d
                r3 = r4
            L4d:
                r6.setImageDrawable(r3)
            L50:
                r3 = 8
                if (r8 == 0) goto L56
                r4 = r0
                goto L57
            L56:
                r4 = r3
            L57:
                r6.setVisibility(r4)
                if (r9 == 0) goto L7f
                if (r2 != 0) goto L60
                if (r8 != 0) goto L67
            L60:
                android.graphics.drawable.Drawable r6 = r9.getDrawable()
                if (r6 == 0) goto L67
                goto L68
            L67:
                r1 = r0
            L68:
                if (r1 == 0) goto L6b
                r3 = r0
            L6b:
                r9.setVisibility(r3)
                int[] r6 = com.android.systemui.statusbar.NotificationGroupingUtil.LeftIconApplicator.MARGIN_ADJUSTED_VIEWS
                int r8 = r6.length
            L71:
                if (r0 >= r8) goto L7f
                r9 = r6[r0]
                android.view.View r9 = r7.findViewById(r9)
                r5.adjustMargins(r1, r9)
                int r0 = r0 + 1
                goto L71
            L7f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationGroupingUtil.LeftIconApplicator.apply(android.view.View, android.view.View, boolean, boolean):void");
        }
    }

    class Processor {
        private final ResultApplicator mApplicator;
        private boolean mApply;
        private final ViewComparator mComparator;
        private final DataExtractor mExtractor;
        private final int mId;
        private Object mParentData;
        private final ExpandableNotificationRow mParentRow;
        private View mParentView;

        Processor(ExpandableNotificationRow expandableNotificationRow, int i, DataExtractor dataExtractor, ViewComparator viewComparator, ResultApplicator resultApplicator) {
            this.mId = i;
            this.mExtractor = dataExtractor;
            this.mApplicator = resultApplicator;
            this.mComparator = viewComparator;
            this.mParentRow = expandableNotificationRow;
        }

        private void applyToView(boolean z, boolean z2, View view) {
            View viewFindViewById;
            if (view == null || (viewFindViewById = view.findViewById(this.mId)) == null || this.mComparator.isEmpty(viewFindViewById)) {
                return;
            }
            this.mApplicator.apply(view, viewFindViewById, z, z2);
        }

        public static Processor forTextView(ExpandableNotificationRow expandableNotificationRow, int i) {
            return new Processor(expandableNotificationRow, i, null, NotificationGroupingUtil.TEXT_VIEW_COMPARATOR, NotificationGroupingUtil.VISIBILITY_APPLICATOR);
        }

        public void apply(ExpandableNotificationRow expandableNotificationRow) {
            apply(expandableNotificationRow, false);
        }

        public void apply(ExpandableNotificationRow expandableNotificationRow, boolean z) {
            boolean z2 = this.mApply && !z;
            if (expandableNotificationRow.isSummaryWithChildren()) {
                applyToView(z2, z, expandableNotificationRow.getNotificationViewWrapper().getNotificationHeader());
                return;
            }
            applyToView(z2, z, expandableNotificationRow.getPrivateLayout().getContractedChild());
            applyToView(z2, z, expandableNotificationRow.getPrivateLayout().getHeadsUpChild());
            applyToView(z2, z, expandableNotificationRow.getPrivateLayout().getExpandedChild());
        }

        public void compareToGroupParent(ExpandableNotificationRow expandableNotificationRow) {
            View contractedChild;
            View viewFindViewById;
            if (!this.mApply || (contractedChild = expandableNotificationRow.getPrivateLayout().getContractedChild()) == null || (viewFindViewById = contractedChild.findViewById(this.mId)) == null) {
                return;
            }
            DataExtractor dataExtractor = this.mExtractor;
            this.mApply = this.mComparator.compare(this.mParentView, viewFindViewById, this.mParentData, dataExtractor == null ? null : dataExtractor.extractData(expandableNotificationRow));
        }

        public void init() {
            NotificationViewWrapper notificationViewWrapper = this.mParentRow.getNotificationViewWrapper();
            View notificationHeader = notificationViewWrapper == null ? null : notificationViewWrapper.getNotificationHeader();
            this.mParentView = notificationHeader == null ? null : notificationHeader.findViewById(this.mId);
            DataExtractor dataExtractor = this.mExtractor;
            this.mParentData = dataExtractor != null ? dataExtractor.extractData(this.mParentRow) : null;
            this.mApply = !this.mComparator.isEmpty(this.mParentView);
        }
    }

    interface ResultApplicator {
        void apply(View view, View view2, boolean z, boolean z2);
    }

    class TextViewComparator implements ViewComparator {
        private TextViewComparator() {
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public boolean compare(View view, View view2, Object obj, Object obj2) {
            TextView textView = (TextView) view;
            TextView textView2 = (TextView) view2;
            return Objects.equals(textView == null ? "" : textView.getText(), textView2 != null ? textView2.getText() : "");
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ViewComparator
        public boolean isEmpty(View view) {
            return view == null || TextUtils.isEmpty(((TextView) view).getText());
        }
    }

    interface ViewComparator {
        boolean compare(View view, View view2, Object obj, Object obj2);

        boolean isEmpty(View view);
    }

    class VisibilityApplicator implements ResultApplicator {
        private VisibilityApplicator() {
        }

        @Override // com.android.systemui.statusbar.NotificationGroupingUtil.ResultApplicator
        public void apply(View view, View view2, boolean z, boolean z2) {
            if (view2 != null) {
                view2.setVisibility(z ? 8 : 0);
            }
        }
    }

    static {
        TEXT_VIEW_COMPARATOR = new TextViewComparator();
        APP_NAME_COMPARATOR = new AppNameComparator();
        BADGE_COMPARATOR = new BadgeComparator();
        VISIBILITY_APPLICATOR = new VisibilityApplicator();
        APP_NAME_APPLICATOR = new AppNameApplicator();
        LEFT_ICON_APPLICATOR = new LeftIconApplicator();
    }

    public NotificationGroupingUtil(ExpandableNotificationRow expandableNotificationRow) {
        ArrayList arrayList = new ArrayList();
        this.mProcessors = arrayList;
        HashSet hashSet = new HashSet();
        this.mDividers = hashSet;
        this.mRow = expandableNotificationRow;
        DataExtractor dataExtractor = ICON_EXTRACTOR;
        IconComparator iconComparator = ICON_VISIBILITY_COMPARATOR;
        VisibilityApplicator visibilityApplicator = VISIBILITY_APPLICATOR;
        arrayList.add(new Processor(expandableNotificationRow, R.id.icon, dataExtractor, iconComparator, visibilityApplicator));
        arrayList.add(new Processor(expandableNotificationRow, R.id.systemExempted, dataExtractor, GREY_COMPARATOR, GREY_APPLICATOR));
        arrayList.add(new Processor(expandableNotificationRow, R.id.systemExempted, dataExtractor, iconComparator, LEFT_ICON_APPLICATOR));
        arrayList.add(new Processor(expandableNotificationRow, R.id.qwertz, null, BADGE_COMPARATOR, visibilityApplicator));
        arrayList.add(new Processor(expandableNotificationRow, R.id.app_ops, null, APP_NAME_COMPARATOR, APP_NAME_APPLICATOR));
        arrayList.add(Processor.forTextView(expandableNotificationRow, R.id.help));
        hashSet.add(Integer.valueOf(R.id.hidden));
        hashSet.add(Integer.valueOf(R.id.high));
        hashSet.add(Integer.valueOf(R.id.top_label));
    }

    private void sanitizeChild(View view, ExpandableNotificationRow expandableNotificationRow) {
        if (view != null) {
            sanitizeTopLine((ViewGroup) view.findViewById(R.id.old_app_icon), expandableNotificationRow);
        }
    }

    private void sanitizeTopLine(ViewGroup viewGroup, ExpandableNotificationRow expandableNotificationRow) {
        int i;
        View childAt;
        boolean z;
        if (viewGroup == null) {
            return;
        }
        int childCount = viewGroup.getChildCount();
        View viewFindViewById = viewGroup.findViewById(R.id.together);
        int i2 = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt2 = viewGroup.getChildAt(i2);
            if (!(childAt2 instanceof TextView) || childAt2.getVisibility() == 8 || this.mDividers.contains(Integer.valueOf(childAt2.getId())) || childAt2 == viewFindViewById) {
                i2++;
            } else if (expandableNotificationRow.getEntry().getSbn().getNotification().showsTime()) {
                break;
            } else {
                i = 8;
            }
        }
        i = 0;
        viewFindViewById.setVisibility(i);
        View view = null;
        int i3 = 0;
        while (i3 < childCount) {
            View childAt3 = viewGroup.getChildAt(i3);
            if (this.mDividers.contains(Integer.valueOf(childAt3.getId()))) {
                while (true) {
                    i3++;
                    if (i3 >= childCount) {
                        break;
                    }
                    childAt = viewGroup.getChildAt(i3);
                    if (this.mDividers.contains(Integer.valueOf(childAt.getId()))) {
                        i3--;
                        break;
                    } else if (childAt.getVisibility() != 8 && (childAt instanceof TextView)) {
                        if (view != null) {
                            z = true;
                        }
                    }
                }
                childAt = view;
                z = false;
                childAt3.setVisibility(z ? 0 : 8);
                view = childAt;
            } else if (childAt3.getVisibility() != 8 && (childAt3 instanceof TextView)) {
                view = childAt3;
            }
            i3++;
        }
    }

    private void sanitizeTopLineViews(ExpandableNotificationRow expandableNotificationRow) {
        if (expandableNotificationRow.isSummaryWithChildren()) {
            sanitizeTopLine(expandableNotificationRow.getNotificationViewWrapper().getNotificationHeader(), expandableNotificationRow);
            return;
        }
        NotificationContentView privateLayout = expandableNotificationRow.getPrivateLayout();
        sanitizeChild(privateLayout.getContractedChild(), expandableNotificationRow);
        sanitizeChild(privateLayout.getHeadsUpChild(), expandableNotificationRow);
        sanitizeChild(privateLayout.getExpandedChild(), expandableNotificationRow);
    }

    public void restoreChildNotification(ExpandableNotificationRow expandableNotificationRow) {
        for (int i = 0; i < this.mProcessors.size(); i++) {
            ((Processor) this.mProcessors.get(i)).apply(expandableNotificationRow, true);
        }
        sanitizeTopLineViews(expandableNotificationRow);
    }

    public void updateChildrenAppearance() {
        List attachedChildren = this.mRow.getAttachedChildren();
        if (attachedChildren == null || !this.mRow.isSummaryWithChildren()) {
            return;
        }
        for (int i = 0; i < this.mProcessors.size(); i++) {
            ((Processor) this.mProcessors.get(i)).init();
        }
        for (int i2 = 0; i2 < attachedChildren.size(); i2++) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) attachedChildren.get(i2);
            for (int i3 = 0; i3 < this.mProcessors.size(); i3++) {
                ((Processor) this.mProcessors.get(i3)).compareToGroupParent(expandableNotificationRow);
            }
        }
        for (int i4 = 0; i4 < attachedChildren.size(); i4++) {
            ExpandableNotificationRow expandableNotificationRow2 = (ExpandableNotificationRow) attachedChildren.get(i4);
            for (int i5 = 0; i5 < this.mProcessors.size(); i5++) {
                ((Processor) this.mProcessors.get(i5)).apply(expandableNotificationRow2);
            }
            sanitizeTopLineViews(expandableNotificationRow2);
        }
    }
}
