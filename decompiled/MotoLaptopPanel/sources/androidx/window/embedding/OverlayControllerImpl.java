package androidx.window.embedding;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.WindowMetrics;
import androidx.profileinstaller.ProfileInstallReceiver$$ExternalSyntheticLambda0;
import androidx.window.WindowSdkExtensions;
import androidx.window.extensions.core.util.function.Consumer;
import androidx.window.extensions.core.util.function.Function;
import androidx.window.extensions.embedding.ActivityEmbeddingComponent;
import androidx.window.extensions.embedding.ActivityStackAttributes;
import androidx.window.extensions.embedding.ActivityStackAttributesCalculatorParams;
import androidx.window.extensions.layout.WindowLayoutInfo;
import androidx.window.layout.WindowMetricsCalculator;
import androidx.window.layout.adapter.extensions.ExtensionsWindowLayoutInfoAdapter;
import androidx.window.layout.util.DensityCompatHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;

/* JADX INFO: compiled from: OverlayControllerImpl.kt */
/* JADX INFO: loaded from: classes.dex */
public class OverlayControllerImpl {
    private final EmbeddingAdapter adapter;
    private final ActivityEmbeddingComponent embeddingExtension;
    private final ReentrantLock globalLock;
    private Function1 overlayAttributesCalculator;
    private final ArrayMap overlayInfoToActivityStackCallbackMap;
    private final ArrayMap overlayTagToContainerMap;
    private final ArrayMap overlayTagToCurrentAttributesMap;
    private final Map overlayTagToDefaultAttributesMap;

    public OverlayControllerImpl(ActivityEmbeddingComponent activityEmbeddingComponent, EmbeddingAdapter embeddingAdapter) {
        activityEmbeddingComponent.getClass();
        embeddingAdapter.getClass();
        this.embeddingExtension = activityEmbeddingComponent;
        this.adapter = embeddingAdapter;
        this.globalLock = new ReentrantLock();
        this.overlayTagToDefaultAttributesMap = new ArrayMap();
        this.overlayTagToCurrentAttributesMap = new ArrayMap();
        this.overlayTagToContainerMap = new ArrayMap();
        this.overlayInfoToActivityStackCallbackMap = new ArrayMap();
        WindowSdkExtensions.Companion.getInstance().requireExtensionVersion$window_release(8);
        activityEmbeddingComponent.setActivityStackAttributesCalculator(new Function() { // from class: androidx.window.embedding.OverlayControllerImpl$$ExternalSyntheticLambda0
            public final Object apply(Object obj) {
                return OverlayControllerImpl._init_$lambda$3(this.f$0, (ActivityStackAttributesCalculatorParams) obj);
            }
        });
        activityEmbeddingComponent.registerActivityStackCallback(new ProfileInstallReceiver$$ExternalSyntheticLambda0(), new Consumer() { // from class: androidx.window.embedding.OverlayControllerImpl$$ExternalSyntheticLambda1
            public final void accept(Object obj) {
                OverlayControllerImpl._init_$lambda$6(this.f$0, (List) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ActivityStackAttributes _init_$lambda$3(OverlayControllerImpl overlayControllerImpl, ActivityStackAttributesCalculatorParams activityStackAttributesCalculatorParams) {
        ReentrantLock reentrantLock = overlayControllerImpl.globalLock;
        reentrantLock.lock();
        try {
            androidx.window.extensions.embedding.ParentContainerInfo parentContainerInfo = activityStackAttributesCalculatorParams.getParentContainerInfo();
            parentContainerInfo.getClass();
            DensityCompatHelper companion = DensityCompatHelper.Companion.getInstance();
            Configuration configuration = parentContainerInfo.getConfiguration();
            configuration.getClass();
            WindowMetrics windowMetrics = parentContainerInfo.getWindowMetrics();
            windowMetrics.getClass();
            float fDensity = companion.density(configuration, windowMetrics);
            WindowMetricsCalculator.Companion companion2 = WindowMetricsCalculator.Companion;
            WindowMetrics windowMetrics2 = parentContainerInfo.getWindowMetrics();
            windowMetrics2.getClass();
            androidx.window.layout.WindowMetrics windowMetricsTranslateWindowMetrics$window_release = companion2.translateWindowMetrics$window_release(windowMetrics2, fDensity);
            String activityStackTag = activityStackAttributesCalculatorParams.getActivityStackTag();
            activityStackTag.getClass();
            ActivityEmbeddingOptionsImpl activityEmbeddingOptionsImpl = ActivityEmbeddingOptionsImpl.INSTANCE;
            Bundle launchOptions = activityStackAttributesCalculatorParams.getLaunchOptions();
            launchOptions.getClass();
            OverlayAttributes overlayAttributes$window_release = activityEmbeddingOptionsImpl.getOverlayAttributes$window_release(launchOptions);
            WindowMetrics windowMetrics3 = activityStackAttributesCalculatorParams.getParentContainerInfo().getWindowMetrics();
            windowMetrics3.getClass();
            androidx.window.layout.WindowMetrics windowMetricsTranslateWindowMetrics$window_release2 = companion2.translateWindowMetrics$window_release(windowMetrics3, fDensity);
            Configuration configuration2 = activityStackAttributesCalculatorParams.getParentContainerInfo().getConfiguration();
            configuration2.getClass();
            ExtensionsWindowLayoutInfoAdapter extensionsWindowLayoutInfoAdapter = ExtensionsWindowLayoutInfoAdapter.INSTANCE;
            WindowLayoutInfo windowLayoutInfo = parentContainerInfo.getWindowLayoutInfo();
            windowLayoutInfo.getClass();
            OverlayAttributes overlayAttributesCalculateOverlayAttributes$window_release = overlayControllerImpl.calculateOverlayAttributes$window_release(activityStackTag, overlayAttributes$window_release, windowMetricsTranslateWindowMetrics$window_release2, configuration2, extensionsWindowLayoutInfoAdapter.translate$window_release(windowMetricsTranslateWindowMetrics$window_release, windowLayoutInfo));
            Bundle launchOptions2 = activityStackAttributesCalculatorParams.getLaunchOptions();
            launchOptions2.getClass();
            activityEmbeddingOptionsImpl.putActivityStackAlignment$window_release(launchOptions2, overlayAttributesCalculateOverlayAttributes$window_release.getBounds());
            return overlayControllerImpl.toActivityStackAttributes(overlayAttributesCalculateOverlayAttributes$window_release, parentContainerInfo);
        } finally {
            reentrantLock.unlock();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void _init_$lambda$6(OverlayControllerImpl overlayControllerImpl, List list) {
        ReentrantLock reentrantLock = overlayControllerImpl.globalLock;
        reentrantLock.lock();
        try {
            Set setKeySet = overlayControllerImpl.overlayTagToContainerMap.keySet();
            setKeySet.getClass();
            overlayControllerImpl.overlayTagToContainerMap.clear();
            ArrayMap arrayMap = overlayControllerImpl.overlayTagToContainerMap;
            list.getClass();
            List<androidx.window.extensions.embedding.ActivityStack> overlayContainers = overlayControllerImpl.getOverlayContainers(list);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(overlayContainers, 10));
            for (androidx.window.extensions.embedding.ActivityStack activityStack : overlayContainers) {
                String tag = activityStack.getTag();
                tag.getClass();
                arrayList.add(new Pair(tag, activityStack));
            }
            MapsKt.putAll(arrayMap, arrayList);
            overlayControllerImpl.cleanUpDismissedOverlayContainerRecords(setKeySet);
            Unit unit = Unit.INSTANCE;
            reentrantLock.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    private final void cleanUpDismissedOverlayContainerRecords(Set set) {
        if (set.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        Set setKeySet = this.overlayTagToContainerMap.keySet();
        setKeySet.getClass();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (!setKeySet.contains(str) && this.embeddingExtension.getActivityStackToken(str) == null) {
                arrayList.add(str);
            }
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            String str2 = (String) obj;
            this.overlayTagToDefaultAttributesMap.remove(str2);
            this.overlayTagToCurrentAttributesMap.remove(str2);
        }
    }

    private final List getOverlayContainers(List list) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (((androidx.window.extensions.embedding.ActivityStack) obj).getTag() != null) {
                arrayList.add(obj);
            }
        }
        return CollectionsKt.toList(arrayList);
    }

    private final ActivityStackAttributes toActivityStackAttributes(OverlayAttributes overlayAttributes, androidx.window.extensions.embedding.ParentContainerInfo parentContainerInfo) {
        ActivityStackAttributes activityStackAttributesBuild = new ActivityStackAttributes.Builder().setRelativeBounds(EmbeddingBounds.Companion.translateEmbeddingBounds$window_release(overlayAttributes.getBounds(), this.adapter.translate$window_release(parentContainerInfo)).toRect()).setWindowAttributes(this.adapter.translateWindowAttributes$window_release()).build();
        activityStackAttributesBuild.getClass();
        return activityStackAttributesBuild;
    }

    public final OverlayAttributes calculateOverlayAttributes$window_release(String str, OverlayAttributes overlayAttributes, androidx.window.layout.WindowMetrics windowMetrics, Configuration configuration, androidx.window.layout.WindowLayoutInfo windowLayoutInfo) {
        OverlayAttributes overlayAttributes2;
        String str2;
        str.getClass();
        windowMetrics.getClass();
        configuration.getClass();
        windowLayoutInfo.getClass();
        OverlayAttributes updatedOverlayAttributes$window_release = getUpdatedOverlayAttributes$window_release(str);
        if (updatedOverlayAttributes$window_release != null) {
            overlayAttributes2 = updatedOverlayAttributes$window_release;
        } else {
            if (overlayAttributes == null) {
                throw new IllegalArgumentException("Can't retrieve overlay attributes from launch options");
            }
            overlayAttributes2 = overlayAttributes;
        }
        Function1 overlayAttributesCalculator$window_release = getOverlayAttributesCalculator$window_release();
        if (overlayAttributesCalculator$window_release != null) {
            str2 = str;
            OverlayAttributes overlayAttributes3 = (OverlayAttributes) overlayAttributesCalculator$window_release.invoke(new OverlayAttributesCalculatorParams(windowMetrics, configuration, windowLayoutInfo, str2, overlayAttributes2));
            if (overlayAttributes3 != null) {
                overlayAttributes2 = overlayAttributes3;
            }
        } else {
            str2 = str;
        }
        this.overlayTagToCurrentAttributesMap.put(str2, overlayAttributes2);
        return overlayAttributes2;
    }

    public final Function1 getOverlayAttributesCalculator$window_release() {
        ReentrantLock reentrantLock = this.globalLock;
        reentrantLock.lock();
        try {
            return this.overlayAttributesCalculator;
        } finally {
            reentrantLock.unlock();
        }
    }

    public OverlayAttributes getUpdatedOverlayAttributes$window_release(String str) {
        str.getClass();
        return (OverlayAttributes) this.overlayTagToDefaultAttributesMap.get(str);
    }
}
