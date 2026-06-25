package com.airbnb.lottie;

import android.graphics.Rect;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LottieComposition {
    private Rect bounds;
    private SparseArrayCompat characters;
    private float endFrame;
    private Map fonts;
    private float frameRate;
    private boolean hasDashPattern;
    private Map images;
    private float imagesDpScale;
    private LongSparseArray layerMap;
    private List layers;
    private List markers;
    private Map precomps;
    private float startFrame;
    private int unscaledHeight;
    private int unscaledWidth;
    private final PerformanceTracker performanceTracker = new PerformanceTracker();
    private final HashSet warnings = new HashSet();
    private int maskAndMatteCount = 0;

    public void addWarning(String str) {
        Logger.warning(str);
        this.warnings.add(str);
    }

    public Rect getBounds() {
        return this.bounds;
    }

    public SparseArrayCompat getCharacters() {
        return this.characters;
    }

    public float getDuration() {
        return (long) ((getDurationFrames() / this.frameRate) * 1000.0f);
    }

    public float getDurationFrames() {
        return this.endFrame - this.startFrame;
    }

    public float getEndFrame() {
        return this.endFrame;
    }

    public Map getFonts() {
        return this.fonts;
    }

    public float getFrameForProgress(float f) {
        return MiscUtils.lerp(this.startFrame, this.endFrame, f);
    }

    public float getFrameRate() {
        return this.frameRate;
    }

    public Map getImages() {
        float fDpScale = Utils.dpScale();
        if (fDpScale != this.imagesDpScale) {
            for (Map.Entry entry : this.images.entrySet()) {
                this.images.put((String) entry.getKey(), ((LottieImageAsset) entry.getValue()).copyWithScale(this.imagesDpScale / fDpScale));
            }
        }
        this.imagesDpScale = fDpScale;
        return this.images;
    }

    public List getLayers() {
        return this.layers;
    }

    public Marker getMarker(String str) {
        int size = this.markers.size();
        for (int i = 0; i < size; i++) {
            Marker marker = (Marker) this.markers.get(i);
            if (marker.matchesName(str)) {
                return marker;
            }
        }
        return null;
    }

    public int getMaskAndMatteCount() {
        return this.maskAndMatteCount;
    }

    public PerformanceTracker getPerformanceTracker() {
        return this.performanceTracker;
    }

    public List getPrecomps(String str) {
        return (List) this.precomps.get(str);
    }

    public float getStartFrame() {
        return this.startFrame;
    }

    public boolean hasDashPattern() {
        return this.hasDashPattern;
    }

    public void incrementMatteOrMaskCount(int i) {
        this.maskAndMatteCount += i;
    }

    public void init(Rect rect, float f, float f2, float f3, List list, LongSparseArray longSparseArray, Map map, Map map2, float f4, SparseArrayCompat sparseArrayCompat, Map map3, List list2, int i, int i2) {
        this.bounds = rect;
        this.startFrame = f;
        this.endFrame = f2;
        this.frameRate = f3;
        this.layers = list;
        this.layerMap = longSparseArray;
        this.precomps = map;
        this.images = map2;
        this.imagesDpScale = f4;
        this.characters = sparseArrayCompat;
        this.fonts = map3;
        this.markers = list2;
        this.unscaledWidth = i;
        this.unscaledHeight = i2;
    }

    public Layer layerModelForId(long j) {
        return (Layer) this.layerMap.get(j);
    }

    public void setHasDashPattern(boolean z) {
        this.hasDashPattern = z;
    }

    public void setPerformanceTrackingEnabled(boolean z) {
        this.performanceTracker.setEnabled(z);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("LottieComposition:\n");
        Iterator it = this.layers.iterator();
        while (it.hasNext()) {
            sb.append(((Layer) it.next()).toString("\t"));
        }
        return sb.toString();
    }
}
