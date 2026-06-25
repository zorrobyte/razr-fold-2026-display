package com.motorola.taskbar.recent;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.FocusFinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcherKt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.OneShotPreDrawListener;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.SystemUIFactory;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.recents.model.ThumbnailData;
import com.motorola.internal.app.MotoDesktopManager;
import com.motorola.taskbar.R$id;
import com.motorola.taskbar.R$layout;
import com.motorola.taskbar.R$string;
import com.motorola.taskbar.guide.R$dimen;
import com.motorola.taskbar.guide.R$drawable;
import com.motorola.taskbar.model.SurfaceBlurController;
import com.motorola.taskbar.recent.RecentsActivity;
import com.motorola.taskbar.util.DebugConfig;
import com.motorola.taskbar.util.Utils;
import java.util.List;
import java.util.Objects;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;

/* JADX INFO: compiled from: RecentsActivity.kt */
/* JADX INFO: loaded from: classes2.dex */
public final class RecentsActivity extends FragmentActivity implements TaskViewHostCallback, RecentsRequestCallback {
    public static final Companion Companion = new Companion(null);
    private TextView clearAllView;
    private int dotPosition;
    private TextView emptyTextView;
    private final RecentsActivity$itemCallback$1 itemCallback;
    private int lastKeyCode;
    private int lastRecentsUIMode;
    private ImageView left;
    private LinearLayout mHelperIndicator;
    private int myDisplayId;
    private int myDisplayMode;
    private RecentsAdapter recentsAdapter;
    private View recentsContentView;
    public RecentsController recentsController;
    private int recentsIndicatorSize;
    private RecentsModel recentsModel;
    private View recentsStatisticContainerView;
    private TextView recentsStatisticView;
    private int recentsUIMode;
    private RecyclerView.LayoutManager recentsViewManager;
    private RecyclerView recyclerView;
    private ImageView right;
    private View showingContentView;
    public SurfaceBlurController surfaceBlurController;

    /* JADX INFO: compiled from: RecentsActivity.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void debugLog(Function0 function0) {
            function0.getClass();
            if (DebugConfig.DEBUG_COMMON) {
                function0.mo2224invoke();
            }
        }

        public final Intent launchIntent(Context context, boolean z) {
            context.getClass();
            Intent intent = new Intent();
            intent.setClass(context, RecentsActivity.class);
            intent.setPackage(context.getPackageName());
            intent.putExtra("withAltTab", z);
            intent.setFlags(268435456);
            return intent;
        }
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* JADX INFO: compiled from: RecentsActivity.kt */
    final class OP {
        private static final /* synthetic */ EnumEntries $ENTRIES;
        private static final /* synthetic */ OP[] $VALUES;
        private String desc;
        public static final OP THUMBNAIL_CHANGED = new OP("THUMBNAIL_CHANGED", 0, "THUMBNAIL");
        public static final OP LOAD_HIGH_RES_THUMBNAIL_CHANGED = new OP("LOAD_HIGH_RES_THUMBNAIL_CHANGED", 1, "LOAD_HIGH_RES");
        public static final OP DARK_INTENSITY = new OP("DARK_INTENSITY", 2, "DARK_INTENSITY");
        public static final OP QUICK_SWITCH_MODE = new OP("QUICK_SWITCH_MODE", 3, "QUICK_SWITCH_MODE");
        public static final OP RESET = new OP("RESET", 4, "RESET");

        private static final /* synthetic */ OP[] $values() {
            return new OP[]{THUMBNAIL_CHANGED, LOAD_HIGH_RES_THUMBNAIL_CHANGED, DARK_INTENSITY, QUICK_SWITCH_MODE, RESET};
        }

        static {
            OP[] opArr$values = $values();
            $VALUES = opArr$values;
            $ENTRIES = EnumEntriesKt.enumEntries(opArr$values);
        }

        private OP(String str, int i, String str2) {
            this.desc = str2;
        }

        public static OP valueOf(String str) {
            return (OP) Enum.valueOf(OP.class, str);
        }

        public static OP[] values() {
            return (OP[]) $VALUES.clone();
        }

        @Override // java.lang.Enum
        public String toString() {
            return this.desc;
        }
    }

    /* JADX INFO: compiled from: RecentsActivity.kt */
    final class RecentsAdapter extends ListAdapter {
        private final TaskViewHostCallback hostCallback;
        final /* synthetic */ RecentsActivity this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public RecentsAdapter(RecentsActivity recentsActivity, TaskViewHostCallback taskViewHostCallback) {
            super(recentsActivity.itemCallback);
            taskViewHostCallback.getClass();
            this.this$0 = recentsActivity;
            this.hostCallback = taskViewHostCallback;
        }

        private final void onPartyBindViewHolder(RecentsHolder recentsHolder, int i, List list) {
            Sequence sequenceDistinct = SequencesKt.distinct(CollectionsKt.asSequence(list));
            RecentsActivity recentsActivity = this.this$0;
            for (Object obj : sequenceDistinct) {
                if (obj == OP.THUMBNAIL_CHANGED) {
                    recentsHolder.onThumbnailUpdate();
                } else if (obj == OP.RESET) {
                    recentsHolder.onRecycle();
                } else if (obj == OP.QUICK_SWITCH_MODE) {
                    recentsHolder.onQuickSwitchModeUpdate(recentsActivity.isQuickSwitchMode());
                } else if (obj == OP.LOAD_HIGH_RES_THUMBNAIL_CHANGED) {
                    recentsHolder.onRequestLoadHighResThumbnail();
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecentsHolder recentsHolder, int i) {
            recentsHolder.getClass();
            Object item = getItem(i);
            item.getClass();
            recentsHolder.bindTask((TaskItem) item, this.hostCallback, this.this$0.isQuickSwitchMode());
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(RecentsHolder recentsHolder, int i, List list) {
            recentsHolder.getClass();
            list.getClass();
            if (list.isEmpty()) {
                onBindViewHolder(recentsHolder, i);
            } else {
                onPartyBindViewHolder(recentsHolder, i, list);
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public RecentsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            viewGroup.getClass();
            View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.task_view, viewGroup, false);
            viewInflate.getClass();
            return new RecentsHolder(viewInflate);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public boolean onFailedToRecycleView(RecentsHolder recentsHolder) {
            recentsHolder.getClass();
            return true;
        }

        public final void onQuickSwitchModeChanged() {
            int size = getCurrentList().size();
            for (int i = 0; i < size; i++) {
                RecyclerView recyclerView = this.this$0.recyclerView;
                if (recyclerView == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
                    recyclerView = null;
                }
                RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(i);
                if (viewHolderFindViewHolderForAdapterPosition instanceof RecentsHolder) {
                    ((RecentsHolder) viewHolderFindViewHolderForAdapterPosition).onQuickSwitchModeUpdate(this.this$0.isQuickSwitchMode());
                }
            }
        }

        public final void onTaskSnapshotChanged(int i, ThumbnailData thumbnailData) {
            thumbnailData.getClass();
            int i2 = 0;
            for (TaskItem taskItem : getCurrentList()) {
                int i3 = i2 + 1;
                if (taskItem.getTaskKey().id == i) {
                    taskItem.setThumbnailData(thumbnailData);
                    notifyItemChanged(i2, OP.THUMBNAIL_CHANGED);
                    return;
                }
                i2 = i3;
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onViewRecycled(RecentsHolder recentsHolder) {
            recentsHolder.getClass();
            recentsHolder.onRecycle();
        }
    }

    /* JADX INFO: compiled from: RecentsActivity.kt */
    final class RecentsHolder extends RecyclerView.ViewHolder {
        private final TaskView taskView;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public RecentsHolder(View view) {
            super(view);
            view.getClass();
            this.taskView = (TaskView) view;
        }

        public final void bindTask(TaskItem taskItem, TaskViewHostCallback taskViewHostCallback, boolean z) {
            taskItem.getClass();
            taskViewHostCallback.getClass();
            TaskView taskView = this.taskView;
            Task nativeTask = taskItem.getNativeTask();
            nativeTask.getClass();
            taskView.bindTask(nativeTask, taskViewHostCallback);
            onQuickSwitchModeUpdate(z);
        }

        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        public final void launchTask(boolean z) {
            TaskView taskView = this.taskView;
            taskView.launchTask(taskView, z);
        }

        public final void onQuickSwitchModeUpdate(boolean z) {
            this.taskView.getDeleteView().setFocusable(!z ? 1 : 0);
            this.taskView.setFocusableInTouchMode(z);
        }

        public final void onRecycle() {
            this.taskView.onTaskViewVisibleChanged(false);
        }

        public final void onRequestLoadHighResThumbnail() {
            this.taskView.onTaskViewVisibleChanged(true);
        }

        public final void onThumbnailUpdate() {
            this.taskView.refreshThumbnail();
        }
    }

    /* JADX INFO: renamed from: com.motorola.taskbar.recent.RecentsActivity$onCreate$3, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: RecentsActivity.kt */
    final class ViewOnClickListenerC02033 implements View.OnClickListener {
        ViewOnClickListenerC02033() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit onClick$lambda$0() {
            Log.d("RecentsActivity", "clear all task");
            return Unit.INSTANCE;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            RecentsActivity.Companion.debugLog(new Function0() { // from class: com.motorola.taskbar.recent.RecentsActivity$onCreate$3$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                /* JADX INFO: renamed from: invoke */
                public final Object mo2224invoke() {
                    return RecentsActivity.ViewOnClickListenerC02033.onClick$lambda$0();
                }
            });
            RecentsModel recentsModel = RecentsActivity.this.recentsModel;
            if (recentsModel == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recentsModel");
                recentsModel = null;
            }
            view.getClass();
            recentsModel.clearAllTask(view);
            RecentsActivity.this.hideRecentsInternal("clear all task");
        }
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.motorola.taskbar.recent.RecentsActivity$itemCallback$1] */
    public RecentsActivity() {
        super(R$layout.activity_recents);
        this.recentsUIMode = -1;
        this.lastRecentsUIMode = -1;
        this.itemCallback = new DiffUtil.ItemCallback() { // from class: com.motorola.taskbar.recent.RecentsActivity$itemCallback$1
            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areContentsTheSame(TaskItem taskItem, TaskItem taskItem2) {
                taskItem.getClass();
                taskItem2.getClass();
                return Objects.equals(taskItem.getTaskKey(), taskItem2.getTaskKey()) && Objects.equals(taskItem.getIcon(), taskItem2.getIcon()) && Objects.equals(taskItem.getThumbnailData(), taskItem2.getThumbnailData());
            }

            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public boolean areItemsTheSame(TaskItem taskItem, TaskItem taskItem2) {
                taskItem.getClass();
                taskItem2.getClass();
                return taskItem.getTaskId() == taskItem2.getTaskId();
            }

            @Override // androidx.recyclerview.widget.DiffUtil.ItemCallback
            public Object getChangePayload(TaskItem taskItem, TaskItem taskItem2) {
                taskItem.getClass();
                taskItem2.getClass();
                Objects.equals(taskItem.getIcon(), taskItem2.getIcon());
                Objects.equals(taskItem.getThumbnailData(), taskItem2.getThumbnailData());
                return super.getChangePayload((Object) taskItem, (Object) taskItem2);
            }
        };
    }

    private final void addRecentsDotIndicatorIfNeed(Display display) {
        if (!MotoDesktopManager.isAppStreamMode(display) || getResources().getConfiguration().orientation == 2) {
            return;
        }
        ((ConstraintLayout) requireViewById(R$id.recents_dot_container)).setVisibility(0);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        RecyclerView recyclerView = this.recyclerView;
        RecyclerView recyclerView2 = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        linearSnapHelper.attachToRecyclerView(recyclerView);
        this.recentsIndicatorSize = calculateRecentsIndicator();
        ImageView imageView = this.left;
        if (imageView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("left");
            imageView = null;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.recent.RecentsActivity.addRecentsDotIndicatorIfNeed.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RecyclerView recyclerView3 = RecentsActivity.this.recyclerView;
                if (recyclerView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
                    recyclerView3 = null;
                }
                RecyclerView.LayoutManager layoutManager = recyclerView3.getLayoutManager();
                layoutManager.getClass();
                int iFindFirstCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
                RecentsActivity.this.scrollToPosition(Math.max(0, iFindFirstCompletelyVisibleItemPosition - 1), iFindFirstCompletelyVisibleItemPosition);
            }
        });
        ImageView imageView2 = this.right;
        if (imageView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("right");
            imageView2 = null;
        }
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.motorola.taskbar.recent.RecentsActivity.addRecentsDotIndicatorIfNeed.2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                RecyclerView recyclerView3 = RecentsActivity.this.recyclerView;
                if (recyclerView3 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
                    recyclerView3 = null;
                }
                RecyclerView.LayoutManager layoutManager = recyclerView3.getLayoutManager();
                layoutManager.getClass();
                int iFindFirstCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
                RecentsActivity.this.scrollToPosition(Math.min(RecentsActivity.this.recentsIndicatorSize, iFindFirstCompletelyVisibleItemPosition + 1), iFindFirstCompletelyVisibleItemPosition);
            }
        });
        RecyclerView recyclerView3 = this.recyclerView;
        if (recyclerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
        } else {
            recyclerView2 = recyclerView3;
        }
        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() { // from class: com.motorola.taskbar.recent.RecentsActivity.addRecentsDotIndicatorIfNeed.3
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView4, int i) {
                recyclerView4.getClass();
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView4, int i, int i2) {
                recyclerView4.getClass();
                RecentsActivity.this.drawRecentsIndicatorDot(recyclerView4);
            }
        });
    }

    private final int calculateRecentsIndicator() {
        if (!MotoDesktopManager.isAppStreamMode(getDisplay())) {
            return 0;
        }
        LinearLayout linearLayout = (LinearLayout) requireViewById(R$id.on_board_indicator);
        this.mHelperIndicator = linearLayout;
        RecyclerView recyclerView = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mHelperIndicator");
            linearLayout = null;
        }
        linearLayout.removeAllViews();
        int dimension = (int) getResources().getDimension(R$dimen.recents_indicator_size);
        int dimension2 = (int) getResources().getDimension(R$dimen.recents_indicator_space);
        RecentsModel recentsModel = this.recentsModel;
        if (recentsModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsModel");
            recentsModel = null;
        }
        int size = recentsModel.getTaskController().getTasks(this.myDisplayId).size();
        int iMin = Math.min(35, size);
        for (int i = 0; i < iMin; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dimension, dimension);
            ImageView imageView = new ImageView(this);
            if (i != 0) {
                layoutParams.setMarginStart(dimension2);
            }
            imageView.setLayoutParams(layoutParams);
            imageView.setBackgroundResource(R$drawable.recents_indicator_no_select);
            LinearLayout linearLayout2 = this.mHelperIndicator;
            if (linearLayout2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("mHelperIndicator");
                linearLayout2 = null;
            }
            linearLayout2.addView(imageView);
        }
        RecyclerView recyclerView2 = this.recyclerView;
        if (recyclerView2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
        } else {
            recyclerView = recyclerView2;
        }
        drawRecentsIndicatorDot(recyclerView);
        return size;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void drawRecentsIndicatorDot(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        layoutManager.getClass();
        int iFindFirstCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
        LinearLayout linearLayout = this.mHelperIndicator;
        LinearLayout linearLayout2 = null;
        if (linearLayout == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mHelperIndicator");
            linearLayout = null;
        }
        View childAt = linearLayout.getChildAt(this.dotPosition);
        if (childAt != null) {
            childAt.setBackgroundResource(R$drawable.recents_indicator_no_select);
        }
        this.dotPosition = iFindFirstCompletelyVisibleItemPosition;
        LinearLayout linearLayout3 = this.mHelperIndicator;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mHelperIndicator");
        } else {
            linearLayout2 = linearLayout3;
        }
        View childAt2 = linearLayout2.getChildAt(this.dotPosition);
        if (childAt2 != null) {
            childAt2.setBackgroundResource(R$drawable.recents_indicator_select);
        }
    }

    private final void fadeViews(final View view, final View view2) {
        ViewPropertyAnimator viewPropertyAnimatorAnimate = view.animate();
        viewPropertyAnimatorAnimate.cancel();
        viewPropertyAnimatorAnimate.alpha(1.0f);
        viewPropertyAnimatorAnimate.setDuration(300L);
        viewPropertyAnimatorAnimate.withStartAction(new Runnable() { // from class: com.motorola.taskbar.recent.RecentsActivity$fadeViews$1$1
            @Override // java.lang.Runnable
            public final void run() {
                view.setVisibility(0);
                view.setAlpha(0.0f);
            }
        });
        ViewPropertyAnimator viewPropertyAnimatorAnimate2 = view2.animate();
        viewPropertyAnimatorAnimate2.cancel();
        viewPropertyAnimatorAnimate2.alpha(0.0f);
        viewPropertyAnimatorAnimate2.setDuration(300L);
        viewPropertyAnimatorAnimate2.withEndAction(new Runnable() { // from class: com.motorola.taskbar.recent.RecentsActivity$fadeViews$2$1
            @Override // java.lang.Runnable
            public final void run() {
                view2.setVisibility(8);
            }
        });
    }

    private final void findNextFocusItemFromKeyShortcut(boolean z) {
        final Function1 function1 = new Function1() { // from class: com.motorola.taskbar.recent.RecentsActivity$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RecentsActivity.findNextFocusItemFromKeyShortcut$lambda$8(this.f$0, (View) obj);
            }
        };
        RecyclerView recyclerView = null;
        if (!z) {
            RecyclerView recyclerView2 = this.recyclerView;
            if (recyclerView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            } else {
                recyclerView = recyclerView2;
            }
            function1.invoke(recyclerView);
            return;
        }
        RecyclerView recyclerView3 = this.recyclerView;
        if (recyclerView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
        } else {
            recyclerView = recyclerView3;
        }
        if (!recyclerView.isLaidOut() || recyclerView.isLayoutRequested()) {
            recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.motorola.taskbar.recent.RecentsActivity$findNextFocusItemFromKeyShortcut$$inlined$doOnLayout$1
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    view.removeOnLayoutChangeListener(this);
                    function1.invoke(view);
                }
            });
        } else {
            function1.invoke(recyclerView);
        }
    }

    static /* synthetic */ void findNextFocusItemFromKeyShortcut$default(RecentsActivity recentsActivity, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        recentsActivity.findNextFocusItemFromKeyShortcut(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit findNextFocusItemFromKeyShortcut$lambda$8(RecentsActivity recentsActivity, View view) {
        view.getClass();
        RecyclerView recyclerView = recentsActivity.recyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        recentsActivity.performFocusNavigation(recyclerView);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void hideRecentsInternal(final String str) {
        Companion.debugLog(new Function0() { // from class: com.motorola.taskbar.recent.RecentsActivity$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return RecentsActivity.hideRecentsInternal$lambda$5(str);
            }
        });
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finishAndRemoveTask();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit hideRecentsInternal$lambda$5(String str) {
        Log.d("RecentsActivity", "hideRecents due to " + str);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean isQuickSwitchMode() {
        return this.recentsUIMode == 1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$1(RecentsActivity recentsActivity, OnBackPressedCallback onBackPressedCallback) {
        onBackPressedCallback.getClass();
        recentsActivity.hideRecentsInternal("back key");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$2(RecentsActivity recentsActivity, List list) {
        TextView textView = recentsActivity.recentsStatisticView;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsStatisticView");
            textView = null;
        }
        textView.setText(recentsActivity.getString(R$string.recents_statistic));
        RecentsAdapter recentsAdapter = recentsActivity.recentsAdapter;
        if (recentsAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsAdapter");
            recentsAdapter = null;
        }
        recentsAdapter.submitList(list);
        recentsActivity.updateContentViewVisibility(list.isEmpty());
        recentsActivity.calculateRecentsIndicator();
        if (recentsActivity.isQuickSwitchMode() && !list.isEmpty()) {
            findNextFocusItemFromKeyShortcut$default(recentsActivity, false, 1, null);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onCreate$lambda$3(RecentsActivity recentsActivity, Pair pair) {
        RecentsAdapter recentsAdapter = recentsActivity.recentsAdapter;
        if (recentsAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsAdapter");
            recentsAdapter = null;
        }
        recentsAdapter.onTaskSnapshotChanged(((Number) pair.getFirst()).intValue(), (ThumbnailData) pair.getSecond());
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onKeyUp$lambda$13$lambda$12() {
        Log.d("RecentsActivity", "quick launch focused task");
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit onWindowFocusChanged$lambda$11(RecentsActivity recentsActivity, boolean z) {
        Log.w("RecentsActivity", "onWindowFocusChanged#: #" + recentsActivity.myDisplayId + " hasFocus = " + z + ", isFinishing=" + recentsActivity.isFinishing());
        return Unit.INSTANCE;
    }

    private final void performFocusNavigation(ViewGroup viewGroup) {
        View viewFindFocus = viewGroup.findFocus();
        if (Intrinsics.areEqual(viewFindFocus, viewGroup)) {
            viewFindFocus = null;
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(viewGroup, viewFindFocus, 2);
        if (viewFindNextFocus == null || viewFindNextFocus == viewFindFocus) {
            return;
        }
        viewFindNextFocus.requestFocus();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit requestHideRecents$lambda$6(int i, RecentsActivity recentsActivity) {
        Log.d("RecentsActivity", "request RecentsHide in display #" + i + ", current display #" + recentsActivity.myDisplayId);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit requestLaunchNextTask$lambda$7(int i, RecentsActivity recentsActivity) {
        Log.d("RecentsActivity", "request launch next task in display #" + i + ", current display #" + recentsActivity.myDisplayId);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void scrollToPosition(int i, int i2) {
        RecyclerView recyclerView = this.recyclerView;
        LinearLayout linearLayout = null;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        recyclerView.smoothScrollToPosition(i);
        if (i >= 35 || i >= this.recentsIndicatorSize) {
            return;
        }
        LinearLayout linearLayout2 = this.mHelperIndicator;
        if (linearLayout2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mHelperIndicator");
            linearLayout2 = null;
        }
        View childAt = linearLayout2.getChildAt(i2);
        if (childAt != null) {
            childAt.setBackgroundResource(R$drawable.recents_indicator_no_select);
        }
        LinearLayout linearLayout3 = this.mHelperIndicator;
        if (linearLayout3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("mHelperIndicator");
        } else {
            linearLayout = linearLayout3;
        }
        View childAt2 = linearLayout.getChildAt(i);
        if (childAt2 != null) {
            childAt2.setBackgroundResource(R$drawable.recents_indicator_select);
        }
    }

    private final void updateContentViewVisibility(boolean z) {
        View view = this.showingContentView;
        TextView textView = this.emptyTextView;
        TextView textView2 = null;
        if (textView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyTextView");
            textView = null;
        }
        if (view != textView && z) {
            TextView textView3 = this.emptyTextView;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyTextView");
                textView3 = null;
            }
            this.showingContentView = textView3;
            TextView textView4 = this.emptyTextView;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyTextView");
                textView4 = null;
            }
            View view2 = this.recentsContentView;
            if (view2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recentsContentView");
                view2 = null;
            }
            fadeViews(textView4, view2);
        }
        View view3 = this.showingContentView;
        View view4 = this.recentsContentView;
        if (view4 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsContentView");
            view4 = null;
        }
        if (view3 == view4 || z) {
            return;
        }
        View view5 = this.recentsContentView;
        if (view5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsContentView");
            view5 = null;
        }
        this.showingContentView = view5;
        View view6 = this.recentsContentView;
        if (view6 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsContentView");
            view6 = null;
        }
        TextView textView5 = this.emptyTextView;
        if (textView5 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyTextView");
        } else {
            textView2 = textView5;
        }
        fadeViews(view6, textView2);
    }

    private final void updateDisplayMode(Display display) {
        int i;
        if (MotoDesktopManager.isMobileUiMode(display)) {
            i = 1;
            if (getResources().getConfiguration().orientation != 1) {
                i = 2;
            }
        } else {
            i = 0;
        }
        this.myDisplayMode = i;
    }

    private final void updateQuickSwitchMode(boolean z) {
        int i = this.recentsUIMode;
        if (i != z) {
            this.lastRecentsUIMode = i;
            this.recentsUIMode = z ? 1 : 0;
            this.lastKeyCode = 0;
            int i2 = !isQuickSwitchMode() ? 1 : 0;
            TextView textView = this.clearAllView;
            RecentsAdapter recentsAdapter = null;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("clearAllView");
                textView = null;
            }
            textView.setFocusable(i2);
            TextView textView2 = this.clearAllView;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("clearAllView");
                textView2 = null;
            }
            textView2.clearFocus();
            RecentsAdapter recentsAdapter2 = this.recentsAdapter;
            if (recentsAdapter2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recentsAdapter");
            } else {
                recentsAdapter = recentsAdapter2;
            }
            recentsAdapter.onQuickSwitchModeChanged();
        }
    }

    private final void updateRecentsListByDisplayMode() {
        View view = this.recentsContentView;
        TextView textView = null;
        if (view == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsContentView");
            view = null;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.getClass();
        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) layoutParams;
        View view2 = this.recentsStatisticContainerView;
        if (view2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsStatisticContainerView");
            view2 = null;
        }
        ViewGroup.LayoutParams layoutParams3 = view2.getLayoutParams();
        layoutParams3.getClass();
        FrameLayout.LayoutParams layoutParams4 = (FrameLayout.LayoutParams) layoutParams3;
        RecyclerView recyclerView = this.recyclerView;
        if (recyclerView == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
            recyclerView = null;
        }
        ViewGroup.LayoutParams layoutParams5 = recyclerView.getLayoutParams();
        layoutParams5.getClass();
        FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) layoutParams5;
        int i = this.myDisplayMode;
        if (i == 0) {
            RecyclerView recyclerView2 = this.recyclerView;
            if (recyclerView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
                recyclerView2 = null;
            }
            recyclerView2.setHorizontalScrollBarEnabled(false);
            RecyclerView recyclerView3 = this.recyclerView;
            if (recyclerView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
                recyclerView3 = null;
            }
            recyclerView3.setVerticalScrollBarEnabled(true);
            layoutParams2.gravity = 17;
            layoutParams4.topMargin = getResources().getDimensionPixelOffset(com.motorola.taskbar.R$dimen.recents_statistic_top_margin_desktop);
            layoutParams6.topMargin = getResources().getDimensionPixelOffset(com.motorola.taskbar.R$dimen.recents_list_top_margin_desktop);
            TextView textView2 = this.recentsStatisticView;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recentsStatisticView");
                textView2 = null;
            }
            textView2.setTextSize(0, getResources().getDimensionPixelSize(com.motorola.taskbar.R$dimen.recents_statistic_text_size_desktop));
            TextView textView3 = this.clearAllView;
            if (textView3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("clearAllView");
                textView3 = null;
            }
            textView3.setTextSize(0, getResources().getDimensionPixelSize(com.motorola.taskbar.R$dimen.recents_clear_all_text_size_desktop));
            TextView textView4 = this.emptyTextView;
            if (textView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyTextView");
            } else {
                textView = textView4;
            }
            textView.setTextSize(0, getResources().getDimensionPixelSize(com.motorola.taskbar.R$dimen.recents_empty_tips_text_size_desktop));
            return;
        }
        if (i == 1 || i == 2) {
            RecyclerView recyclerView4 = this.recyclerView;
            if (recyclerView4 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
                recyclerView4 = null;
            }
            recyclerView4.setHorizontalScrollBarEnabled(false);
            RecyclerView recyclerView5 = this.recyclerView;
            if (recyclerView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recyclerView");
                recyclerView5 = null;
            }
            recyclerView5.setVerticalScrollBarEnabled(false);
            layoutParams2.gravity = 1;
            layoutParams4.topMargin = getResources().getDimensionPixelOffset(com.motorola.taskbar.R$dimen.recents_statistic_top_margin);
            layoutParams6.topMargin = getResources().getDimensionPixelOffset(com.motorola.taskbar.R$dimen.recents_list_top_margin);
            TextView textView5 = this.recentsStatisticView;
            if (textView5 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recentsStatisticView");
                textView5 = null;
            }
            textView5.setTextSize(0, getResources().getDimensionPixelSize(com.motorola.taskbar.R$dimen.recents_statistic_text_size));
            TextView textView6 = this.clearAllView;
            if (textView6 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("clearAllView");
                textView6 = null;
            }
            textView6.setTextSize(0, getResources().getDimensionPixelSize(com.motorola.taskbar.R$dimen.recents_clear_all_text_size));
            TextView textView7 = this.emptyTextView;
            if (textView7 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("emptyTextView");
            } else {
                textView = textView7;
            }
            textView.setTextSize(0, getResources().getDimensionPixelSize(com.motorola.taskbar.R$dimen.recents_empty_tips_text_size));
        }
    }

    @Override // com.motorola.taskbar.recent.TaskViewHostCallback
    public void closeTask(View view, Task task) {
        view.getClass();
        task.getClass();
        RecentsModel recentsModel = this.recentsModel;
        if (recentsModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsModel");
            recentsModel = null;
        }
        recentsModel.closeTask(view, task.key.id);
    }

    @Override // com.motorola.taskbar.recent.TaskViewHostCallback
    public int getDisplayMode() {
        return this.myDisplayMode;
    }

    public final RecentsController getRecentsController() {
        RecentsController recentsController = this.recentsController;
        if (recentsController != null) {
            return recentsController;
        }
        Intrinsics.throwUninitializedPropertyAccessException("recentsController");
        return null;
    }

    public final SurfaceBlurController getSurfaceBlurController() {
        SurfaceBlurController surfaceBlurController = this.surfaceBlurController;
        if (surfaceBlurController != null) {
            return surfaceBlurController;
        }
        Intrinsics.throwUninitializedPropertyAccessException("surfaceBlurController");
        return null;
    }

    @Override // com.motorola.taskbar.recent.TaskViewHostCallback
    public void hideRecents(View view) {
        view.getClass();
        hideRecentsInternal("task view touch request");
    }

    @Override // com.motorola.taskbar.recent.TaskViewHostCallback
    public void launchTask(View view, Task task, boolean z) {
        view.getClass();
        task.getClass();
        RecentsModel recentsModel = this.recentsModel;
        if (recentsModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsModel");
            recentsModel = null;
        }
        recentsModel.launchTask(view, task, z);
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Utils.rejectHideTaskbar(getWindow());
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        SystemUIFactory.getInstance().getRootComponent().inject(this);
        getWindow().getDecorView().setSystemUiVisibility(772);
        WindowManager windowManager = (WindowManager) getSystemService(WindowManager.class);
        RecentsModel recentsModel = null;
        Display defaultDisplay = windowManager != null ? windowManager.getDefaultDisplay() : null;
        defaultDisplay.getClass();
        this.myDisplayId = defaultDisplay.getDisplayId();
        super.onCreate(bundle);
        this.recentsModel = (RecentsModel) new ViewModelProvider(getViewModelStore(), new ViewModelProvider.Factory() { // from class: com.motorola.taskbar.recent.RecentsActivity.onCreate.1
            @Override // androidx.lifecycle.ViewModelProvider.Factory
            public ViewModel create(Class cls) {
                cls.getClass();
                return new RecentsModel(RecentsActivity.this.myDisplayId, RecentsActivity.this);
            }
        }, null, 4, null).get("display#" + this.myDisplayId, RecentsModel.class);
        updateDisplayMode(defaultDisplay);
        this.recentsAdapter = new RecentsAdapter(this, this);
        int i = this.myDisplayMode;
        this.recentsViewManager = (i == 1 || 2 == i) ? new LinearLayoutManager(this, 0, false) : new GridLayoutManager(this, 4);
        View viewRequireViewById = requireViewById(R$id.recents_list);
        viewRequireViewById.getClass();
        RecyclerView recyclerView = (RecyclerView) viewRequireViewById;
        RecyclerView.LayoutManager layoutManager = this.recentsViewManager;
        if (layoutManager == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsViewManager");
            layoutManager = null;
        }
        recyclerView.setLayoutManager(layoutManager);
        RecentsAdapter recentsAdapter = this.recentsAdapter;
        if (recentsAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsAdapter");
            recentsAdapter = null;
        }
        recyclerView.setAdapter(recentsAdapter);
        this.recyclerView = recyclerView;
        ((RecentsView) requireViewById(R$id.recents_view)).attachHostCallback(this);
        this.recentsContentView = requireViewById(R$id.recents_content_container);
        this.emptyTextView = (TextView) requireViewById(R$id.empty_text);
        this.clearAllView = (TextView) requireViewById(R$id.clear_all_task);
        this.left = (ImageView) requireViewById(R$id.arrow_left);
        this.right = (ImageView) requireViewById(R$id.arrow_right);
        if (MotoDesktopManager.isAppStreamMode(getDisplay())) {
            TextView textView = this.clearAllView;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("clearAllView");
                textView = null;
            }
            textView.setVisibility(8);
        }
        this.recentsStatisticContainerView = requireViewById(R$id.recents_statistic_container);
        this.recentsStatisticView = (TextView) requireViewById(R$id.recents_statistic);
        if (MotoDesktopManager.isAppStreamMode(getDisplay())) {
            TextView textView2 = this.recentsStatisticView;
            if (textView2 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("recentsStatisticView");
                textView2 = null;
            }
            textView2.setVisibility(8);
        }
        TextView textView3 = this.clearAllView;
        if (textView3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("clearAllView");
            textView3 = null;
        }
        textView3.setOnClickListener(new ViewOnClickListenerC02033());
        updateRecentsListByDisplayMode();
        updateQuickSwitchMode(getIntent().getBooleanExtra("withAltTab", false));
        OnBackPressedDispatcherKt.addCallback$default(getOnBackPressedDispatcher(), this, false, new Function1() { // from class: com.motorola.taskbar.recent.RecentsActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RecentsActivity.onCreate$lambda$1(this.f$0, (OnBackPressedCallback) obj);
            }
        }, 2, null);
        getRecentsController().addCallback(this.myDisplayId, this);
        RecentsModel recentsModel2 = this.recentsModel;
        if (recentsModel2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsModel");
            recentsModel2 = null;
        }
        recentsModel2.getRecentsTasksListData().observe(this, new RecentsActivity$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.motorola.taskbar.recent.RecentsActivity$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RecentsActivity.onCreate$lambda$2(this.f$0, (List) obj);
            }
        }));
        RecentsModel recentsModel3 = this.recentsModel;
        if (recentsModel3 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsModel");
        } else {
            recentsModel = recentsModel3;
        }
        recentsModel.getTaskSnapshotData().observe(this, new RecentsActivity$sam$androidx_lifecycle_Observer$0(new Function1() { // from class: com.motorola.taskbar.recent.RecentsActivity$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return RecentsActivity.onCreate$lambda$3(this.f$0, (Pair) obj);
            }
        }));
        Utils.rejectShowingDecorCaptionView(getWindow());
        SurfaceBlurController surfaceBlurController = getSurfaceBlurController();
        View decorView = getWindow().getDecorView();
        decorView.getClass();
        surfaceBlurController.setSurface(decorView);
        final View decorView2 = getWindow().getDecorView();
        decorView2.getClass();
        OneShotPreDrawListener.add(decorView2, new Runnable() { // from class: com.motorola.taskbar.recent.RecentsActivity$onCreate$$inlined$doOnPreDraw$1
            @Override // java.lang.Runnable
            public final void run() {
                this.getSurfaceBlurController().setLevel(1.0f);
            }
        });
        addRecentsDotIndicatorIfNeed(defaultDisplay);
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        getRecentsController().reportRecentsHide(this.myDisplayId);
        getRecentsController().removeCallback(this.myDisplayId);
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004b  */
    @Override // android.app.Activity, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onKeyUp(int r5, android.view.KeyEvent r6) {
        /*
            r4 = this;
            r6.getClass()
            boolean r0 = r4.isQuickSwitchMode()
            if (r0 == 0) goto L5d
            r6 = 57
            r0 = 1
            if (r5 == r6) goto L13
            r6 = 58
            if (r5 == r6) goto L13
            goto L5a
        L13:
            int r6 = r4.lastKeyCode
            r1 = 61
            if (r6 != r1) goto L51
            androidx.recyclerview.widget.RecyclerView r6 = r4.recyclerView
            r1 = 0
            java.lang.String r2 = "recyclerView"
            if (r6 != 0) goto L24
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            r6 = r1
        L24:
            android.view.View r6 = r6.getFocusedChild()
            if (r6 == 0) goto L4b
            androidx.recyclerview.widget.RecyclerView r3 = r4.recyclerView
            if (r3 != 0) goto L32
            kotlin.jvm.internal.Intrinsics.throwUninitializedPropertyAccessException(r2)
            goto L33
        L32:
            r1 = r3
        L33:
            androidx.recyclerview.widget.RecyclerView$ViewHolder r6 = r1.findContainingViewHolder(r6)
            boolean r1 = r6 instanceof com.motorola.taskbar.recent.RecentsActivity.RecentsHolder
            if (r1 == 0) goto L4b
            com.motorola.taskbar.recent.RecentsActivity$Companion r1 = com.motorola.taskbar.recent.RecentsActivity.Companion
            com.motorola.taskbar.recent.RecentsActivity$$ExternalSyntheticLambda3 r2 = new com.motorola.taskbar.recent.RecentsActivity$$ExternalSyntheticLambda3
            r2.<init>()
            r1.debugLog(r2)
            com.motorola.taskbar.recent.RecentsActivity$RecentsHolder r6 = (com.motorola.taskbar.recent.RecentsActivity.RecentsHolder) r6
            r6.launchTask(r0)
            goto L5a
        L4b:
            java.lang.String r6 = "alt-tab key release, but no focused child"
            r4.hideRecentsInternal(r6)
            goto L5a
        L51:
            int r6 = r4.lastRecentsUIMode
            if (r6 == 0) goto L5a
            java.lang.String r6 = "alt key release"
            r4.hideRecentsInternal(r6)
        L5a:
            r4.lastKeyCode = r5
            return r0
        L5d:
            boolean r4 = super.onKeyUp(r5, r6)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.motorola.taskbar.recent.RecentsActivity.onKeyUp(int, android.view.KeyEvent):boolean");
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onResume() {
        super.onResume();
        getRecentsController().reportRecentsShow(this.myDisplayId);
        RecentsModel recentsModel = this.recentsModel;
        if (recentsModel == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsModel");
            recentsModel = null;
        }
        recentsModel.reloadHighRes();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStop() {
        super.onStop();
    }

    @Override // android.app.Activity, android.view.Window.Callback
    public void onWindowFocusChanged(final boolean z) {
        super.onWindowFocusChanged(z);
        Companion.debugLog(new Function0() { // from class: com.motorola.taskbar.recent.RecentsActivity$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return RecentsActivity.onWindowFocusChanged$lambda$11(this.f$0, z);
            }
        });
        if (z || isFinishing()) {
            return;
        }
        hideRecentsInternal("lost window focus");
    }

    @Override // com.motorola.taskbar.recent.RecentsRequestCallback
    public void requestHideRecents(final int i, boolean z) {
        Companion.debugLog(new Function0() { // from class: com.motorola.taskbar.recent.RecentsActivity$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return RecentsActivity.requestHideRecents$lambda$6(i, this);
            }
        });
        if (i != this.myDisplayId) {
            return;
        }
        getRecentsController().removeCallback(this.myDisplayId);
        hideRecentsInternal("recents controller");
    }

    @Override // com.motorola.taskbar.recent.RecentsRequestCallback
    public void requestLaunchNextTask(final int i, boolean z) {
        Companion.debugLog(new Function0() { // from class: com.motorola.taskbar.recent.RecentsActivity$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            /* JADX INFO: renamed from: invoke */
            public final Object mo2224invoke() {
                return RecentsActivity.requestLaunchNextTask$lambda$7(i, this);
            }
        });
        if (i != this.myDisplayId) {
            return;
        }
        updateQuickSwitchMode(z);
        RecentsAdapter recentsAdapter = this.recentsAdapter;
        if (recentsAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("recentsAdapter");
            recentsAdapter = null;
        }
        if (recentsAdapter.getItemCount() == 0) {
            hideRecentsInternal("launch task but empty list");
        } else {
            findNextFocusItemFromKeyShortcut(false);
        }
    }
}
