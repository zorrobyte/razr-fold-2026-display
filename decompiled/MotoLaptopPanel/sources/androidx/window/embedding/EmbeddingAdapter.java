package androidx.window.embedding;

import android.content.res.Configuration;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowMetrics;
import androidx.window.WindowSdkExtensions;
import androidx.window.core.Bounds;
import androidx.window.core.PredicateAdapter;
import androidx.window.embedding.DividerAttributes;
import androidx.window.embedding.EmbeddingAnimationParams;
import androidx.window.embedding.SplitAttributes;
import androidx.window.extensions.embedding.AnimationBackground;
import androidx.window.extensions.embedding.SplitAttributes;
import androidx.window.extensions.embedding.SplitInfo;
import androidx.window.extensions.embedding.WindowAttributes;
import androidx.window.extensions.layout.WindowLayoutInfo;
import androidx.window.layout.WindowMetricsCalculator;
import androidx.window.layout.adapter.extensions.ExtensionsWindowLayoutInfoAdapter;
import androidx.window.layout.util.DensityCompatHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* JADX INFO: compiled from: EmbeddingAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
public final class EmbeddingAdapter {
    private final VendorApiLevel1Impl api1Impl;
    private final VendorApiLevel2Impl api2Impl;
    private final VendorApiLevel3Impl api3Impl;
    private final PredicateAdapter predicateAdapter;
    public static final Companion Companion = new Companion(null);
    private static final String TAG = Reflection.getOrCreateKotlinClass(EmbeddingAdapter.class).getSimpleName();
    private static final String RULE_TAG_PREFIX = "ae-gen:";
    private static final Binder INVALID_SPLIT_INFO_TOKEN = new Binder();

    /* JADX INFO: compiled from: EmbeddingAdapter.kt */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX INFO: compiled from: EmbeddingAdapter.kt */
    final class VendorApiLevel1Impl {
        private final PredicateAdapter predicateAdapter;
        final /* synthetic */ EmbeddingAdapter this$0;

        public VendorApiLevel1Impl(EmbeddingAdapter embeddingAdapter, PredicateAdapter predicateAdapter) {
            predicateAdapter.getClass();
            this.this$0 = embeddingAdapter;
            this.predicateAdapter = predicateAdapter;
        }

        public final SplitAttributes getSplitAttributesCompat(androidx.window.extensions.embedding.SplitInfo splitInfo) {
            splitInfo.getClass();
            return new SplitAttributes.Builder().setSplitType(SplitAttributes.SplitType.Companion.buildSplitTypeFromValue$window_release(splitInfo.getSplitRatio())).setLayoutDirection(SplitAttributes.LayoutDirection.LOCALE).build();
        }

        public final ActivityStack translateCompat(androidx.window.extensions.embedding.ActivityStack activityStack) {
            activityStack.getClass();
            List activities = activityStack.getActivities();
            activities.getClass();
            return new ActivityStack(activities, activityStack.isEmpty());
        }

        public final SplitInfo translateCompat(androidx.window.extensions.embedding.SplitInfo splitInfo) {
            splitInfo.getClass();
            androidx.window.extensions.embedding.ActivityStack primaryActivityStack = splitInfo.getPrimaryActivityStack();
            primaryActivityStack.getClass();
            ActivityStack activityStackTranslateCompat = translateCompat(primaryActivityStack);
            androidx.window.extensions.embedding.ActivityStack secondaryActivityStack = splitInfo.getSecondaryActivityStack();
            secondaryActivityStack.getClass();
            return new SplitInfo(activityStackTranslateCompat, translateCompat(secondaryActivityStack), getSplitAttributesCompat(splitInfo));
        }
    }

    /* JADX INFO: compiled from: EmbeddingAdapter.kt */
    final class VendorApiLevel2Impl {
        public VendorApiLevel2Impl() {
        }

        public final SplitInfo translateCompat(androidx.window.extensions.embedding.SplitInfo splitInfo) {
            splitInfo.getClass();
            VendorApiLevel1Impl vendorApiLevel1Impl = EmbeddingAdapter.this.api1Impl;
            androidx.window.extensions.embedding.ActivityStack primaryActivityStack = splitInfo.getPrimaryActivityStack();
            primaryActivityStack.getClass();
            ActivityStack activityStackTranslateCompat = vendorApiLevel1Impl.translateCompat(primaryActivityStack);
            VendorApiLevel1Impl vendorApiLevel1Impl2 = EmbeddingAdapter.this.api1Impl;
            androidx.window.extensions.embedding.ActivityStack secondaryActivityStack = splitInfo.getSecondaryActivityStack();
            secondaryActivityStack.getClass();
            ActivityStack activityStackTranslateCompat2 = vendorApiLevel1Impl2.translateCompat(secondaryActivityStack);
            EmbeddingAdapter embeddingAdapter = EmbeddingAdapter.this;
            androidx.window.extensions.embedding.SplitAttributes splitAttributes = splitInfo.getSplitAttributes();
            splitAttributes.getClass();
            return new SplitInfo(activityStackTranslateCompat, activityStackTranslateCompat2, embeddingAdapter.translate$window_release(splitAttributes));
        }
    }

    /* JADX INFO: compiled from: EmbeddingAdapter.kt */
    final class VendorApiLevel3Impl {
        public VendorApiLevel3Impl() {
        }

        public final SplitInfo translateCompat(androidx.window.extensions.embedding.SplitInfo splitInfo) {
            splitInfo.getClass();
            VendorApiLevel1Impl vendorApiLevel1Impl = EmbeddingAdapter.this.api1Impl;
            androidx.window.extensions.embedding.ActivityStack primaryActivityStack = splitInfo.getPrimaryActivityStack();
            primaryActivityStack.getClass();
            ActivityStack activityStackTranslateCompat = vendorApiLevel1Impl.translateCompat(primaryActivityStack);
            VendorApiLevel1Impl vendorApiLevel1Impl2 = EmbeddingAdapter.this.api1Impl;
            androidx.window.extensions.embedding.ActivityStack secondaryActivityStack = splitInfo.getSecondaryActivityStack();
            secondaryActivityStack.getClass();
            ActivityStack activityStackTranslateCompat2 = vendorApiLevel1Impl2.translateCompat(secondaryActivityStack);
            EmbeddingAdapter embeddingAdapter = EmbeddingAdapter.this;
            androidx.window.extensions.embedding.SplitAttributes splitAttributes = splitInfo.getSplitAttributes();
            splitAttributes.getClass();
            SplitAttributes splitAttributesTranslate$window_release = embeddingAdapter.translate$window_release(splitAttributes);
            IBinder token = splitInfo.getToken();
            token.getClass();
            return new SplitInfo(activityStackTranslateCompat, activityStackTranslateCompat2, splitAttributesTranslate$window_release, token);
        }
    }

    public EmbeddingAdapter(PredicateAdapter predicateAdapter) {
        predicateAdapter.getClass();
        this.predicateAdapter = predicateAdapter;
        this.api1Impl = new VendorApiLevel1Impl(this, predicateAdapter);
        this.api2Impl = new VendorApiLevel2Impl();
        this.api3Impl = new VendorApiLevel3Impl();
    }

    private final int getExtensionVersion() {
        return WindowSdkExtensions.Companion.getInstance().getExtensionVersion();
    }

    private final SplitInfo translate(androidx.window.extensions.embedding.SplitInfo splitInfo) {
        int extensionVersion = getExtensionVersion();
        if (extensionVersion == 1) {
            return this.api1Impl.translateCompat(splitInfo);
        }
        if (extensionVersion == 2) {
            return this.api2Impl.translateCompat(splitInfo);
        }
        if (3 <= extensionVersion && extensionVersion < 5) {
            return this.api3Impl.translateCompat(splitInfo);
        }
        androidx.window.extensions.embedding.ActivityStack primaryActivityStack = splitInfo.getPrimaryActivityStack();
        primaryActivityStack.getClass();
        ActivityStack activityStackTranslate$window_release = translate$window_release(primaryActivityStack);
        androidx.window.extensions.embedding.ActivityStack secondaryActivityStack = splitInfo.getSecondaryActivityStack();
        secondaryActivityStack.getClass();
        ActivityStack activityStackTranslate$window_release2 = translate$window_release(secondaryActivityStack);
        androidx.window.extensions.embedding.SplitAttributes splitAttributes = splitInfo.getSplitAttributes();
        splitAttributes.getClass();
        SplitAttributes splitAttributesTranslate$window_release = translate$window_release(splitAttributes);
        SplitInfo.Token splitInfoToken = splitInfo.getSplitInfoToken();
        splitInfoToken.getClass();
        return new SplitInfo(activityStackTranslate$window_release, activityStackTranslate$window_release2, splitAttributesTranslate$window_release, splitInfoToken);
    }

    private final EmbeddingAnimationBackground translateToJetpackAnimationBackground(AnimationBackground animationBackground) {
        WindowSdkExtensions.Companion.getInstance().requireExtensionVersion$window_release(5);
        return animationBackground instanceof AnimationBackground.ColorBackground ? EmbeddingAnimationBackground.Companion.createColorBackground(((AnimationBackground.ColorBackground) animationBackground).getColor()) : EmbeddingAnimationBackground.DEFAULT;
    }

    private final EmbeddingAnimationParams.AnimationSpec translateToJetpackAnimationSpec(int i) {
        WindowSdkExtensions.Companion.getInstance().requireExtensionVersion$window_release(7);
        return i == 0 ? EmbeddingAnimationParams.AnimationSpec.JUMP_CUT : EmbeddingAnimationParams.AnimationSpec.DEFAULT;
    }

    public final List translate(List list) {
        list.getClass();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(translate((androidx.window.extensions.embedding.SplitInfo) it.next()));
        }
        return arrayList;
    }

    public final ActivityStack translate$window_release(androidx.window.extensions.embedding.ActivityStack activityStack) {
        activityStack.getClass();
        int extensionVersion = getExtensionVersion();
        if (1 <= extensionVersion && extensionVersion < 5) {
            return this.api1Impl.translateCompat(activityStack);
        }
        List activities = activityStack.getActivities();
        activities.getClass();
        return new ActivityStack(activities, activityStack.isEmpty(), activityStack.getActivityStackToken());
    }

    public final ParentContainerInfo translate$window_release(androidx.window.extensions.embedding.ParentContainerInfo parentContainerInfo) {
        parentContainerInfo.getClass();
        Configuration configuration = parentContainerInfo.getConfiguration();
        configuration.getClass();
        DensityCompatHelper companion = DensityCompatHelper.Companion.getInstance();
        Configuration configuration2 = parentContainerInfo.getConfiguration();
        configuration2.getClass();
        WindowMetrics windowMetrics = parentContainerInfo.getWindowMetrics();
        windowMetrics.getClass();
        float fDensity = companion.density(configuration2, windowMetrics);
        WindowMetricsCalculator.Companion companion2 = WindowMetricsCalculator.Companion;
        WindowMetrics windowMetrics2 = parentContainerInfo.getWindowMetrics();
        windowMetrics2.getClass();
        androidx.window.layout.WindowMetrics windowMetricsTranslateWindowMetrics$window_release = companion2.translateWindowMetrics$window_release(windowMetrics2, fDensity);
        Bounds bounds = new Bounds(windowMetricsTranslateWindowMetrics$window_release.getBounds());
        ExtensionsWindowLayoutInfoAdapter extensionsWindowLayoutInfoAdapter = ExtensionsWindowLayoutInfoAdapter.INSTANCE;
        WindowLayoutInfo windowLayoutInfo = parentContainerInfo.getWindowLayoutInfo();
        windowLayoutInfo.getClass();
        return new ParentContainerInfo(bounds, extensionsWindowLayoutInfoAdapter.translate$window_release(windowMetricsTranslateWindowMetrics$window_release, windowLayoutInfo), configuration, fDensity);
    }

    public final SplitAttributes translate$window_release(androidx.window.extensions.embedding.SplitAttributes splitAttributes) {
        SplitAttributes.SplitType splitTypeRatio;
        SplitAttributes.LayoutDirection layoutDirection;
        splitAttributes.getClass();
        SplitAttributes.Builder builder = new SplitAttributes.Builder();
        SplitAttributes.SplitType.RatioSplitType splitType = splitAttributes.getSplitType();
        splitType.getClass();
        if (splitType instanceof SplitAttributes.SplitType.HingeSplitType) {
            splitTypeRatio = SplitAttributes.SplitType.SPLIT_TYPE_HINGE;
        } else if (splitType instanceof SplitAttributes.SplitType.ExpandContainersSplitType) {
            splitTypeRatio = SplitAttributes.SplitType.SPLIT_TYPE_EXPAND;
        } else {
            if (!(splitType instanceof SplitAttributes.SplitType.RatioSplitType)) {
                throw new IllegalArgumentException("Unknown split type: " + splitType);
            }
            splitTypeRatio = SplitAttributes.SplitType.Companion.ratio(splitType.getRatio());
        }
        SplitAttributes.Builder splitType2 = builder.setSplitType(splitTypeRatio);
        int layoutDirection2 = splitAttributes.getLayoutDirection();
        if (layoutDirection2 == 0) {
            layoutDirection = SplitAttributes.LayoutDirection.LEFT_TO_RIGHT;
        } else if (layoutDirection2 == 1) {
            layoutDirection = SplitAttributes.LayoutDirection.RIGHT_TO_LEFT;
        } else if (layoutDirection2 == 3) {
            layoutDirection = SplitAttributes.LayoutDirection.LOCALE;
        } else if (layoutDirection2 == 4) {
            layoutDirection = SplitAttributes.LayoutDirection.TOP_TO_BOTTOM;
        } else {
            if (layoutDirection2 != 5) {
                throw new IllegalArgumentException("Unknown layout direction: " + layoutDirection2);
            }
            layoutDirection = SplitAttributes.LayoutDirection.BOTTOM_TO_TOP;
        }
        SplitAttributes.Builder layoutDirection3 = splitType2.setLayoutDirection(layoutDirection);
        int extensionVersion = getExtensionVersion();
        if (5 <= extensionVersion && extensionVersion < 7) {
            EmbeddingAnimationParams.Builder builder2 = new EmbeddingAnimationParams.Builder();
            AnimationBackground animationBackground = splitAttributes.getAnimationBackground();
            animationBackground.getClass();
            layoutDirection3.setAnimationParams(builder2.setAnimationBackground(translateToJetpackAnimationBackground(animationBackground)).build());
        }
        if (getExtensionVersion() >= 7) {
            EmbeddingAnimationParams.Builder builder3 = new EmbeddingAnimationParams.Builder();
            AnimationBackground animationBackground2 = splitAttributes.getAnimationParams().getAnimationBackground();
            animationBackground2.getClass();
            layoutDirection3.setAnimationParams(builder3.setAnimationBackground(translateToJetpackAnimationBackground(animationBackground2)).setOpenAnimation(translateToJetpackAnimationSpec(splitAttributes.getAnimationParams().getOpenAnimationResId())).setCloseAnimation(translateToJetpackAnimationSpec(splitAttributes.getAnimationParams().getCloseAnimationResId())).setChangeAnimation(translateToJetpackAnimationSpec(splitAttributes.getAnimationParams().getChangeAnimationResId())).build());
        }
        if (getExtensionVersion() >= 6) {
            layoutDirection3.setDividerAttributes(translateToJetpackDividerAttributes(splitAttributes.getDividerAttributes()));
        }
        return layoutDirection3.build();
    }

    public final List translate$window_release(List list) {
        list.getClass();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(translate$window_release((androidx.window.extensions.embedding.ActivityStack) it.next()));
        }
        return arrayList;
    }

    public final DividerAttributes translateToJetpackDividerAttributes(androidx.window.extensions.embedding.DividerAttributes dividerAttributes) {
        WindowSdkExtensions.Companion.getInstance().requireExtensionVersion$window_release(6);
        if (dividerAttributes == null) {
            return DividerAttributes.NO_DIVIDER;
        }
        int dividerType = dividerAttributes.getDividerType();
        if (dividerType == 1) {
            return new DividerAttributes.FixedDividerAttributes.Builder().setWidthDp(dividerAttributes.getWidthDp()).setColor(dividerAttributes.getDividerColor()).build();
        }
        if (dividerType == 2) {
            return new DividerAttributes.DraggableDividerAttributes.Builder().setWidthDp(dividerAttributes.getWidthDp()).setColor(dividerAttributes.getDividerColor()).setDragRange((dividerAttributes.getPrimaryMinRatio() == -1.0f && dividerAttributes.getPrimaryMaxRatio() == -1.0f) ? DividerAttributes.DragRange.DRAG_RANGE_SYSTEM_DEFAULT : new DividerAttributes.DragRange.SplitRatioDragRange(dividerAttributes.getPrimaryMinRatio(), dividerAttributes.getPrimaryMaxRatio())).setDraggingToFullscreenAllowed(getExtensionVersion() >= 7 && dividerAttributes.isDraggingToFullscreenAllowed()).build();
        }
        Log.w(TAG, "Unknown divider type " + dividerAttributes + ".dividerType, default to fixed divider type");
        return new DividerAttributes.FixedDividerAttributes.Builder().setWidthDp(dividerAttributes.getWidthDp()).setColor(dividerAttributes.getDividerColor()).build();
    }

    public final WindowAttributes translateWindowAttributes$window_release() {
        WindowSdkExtensions.Companion.getInstance().requireExtensionVersion$window_release(5);
        return new WindowAttributes(Intrinsics.areEqual(null, EmbeddingConfiguration$DimAreaBehavior.ON_ACTIVITY_STACK) ? 1 : 2);
    }
}
