package androidx.compose.ui.res;

import android.content.res.Configuration;
import android.content.res.Resources;
import androidx.compose.ui.graphics.vector.ImageVector;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: VectorResources.android.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ImageVectorCache {
    private final HashMap map = new HashMap();

    /* JADX INFO: compiled from: VectorResources.android.kt */
    public final class ImageVectorEntry {
        private final int configFlags;
        private final ImageVector imageVector;

        public ImageVectorEntry(ImageVector imageVector, int i) {
            this.imageVector = imageVector;
            this.configFlags = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ImageVectorEntry)) {
                return false;
            }
            ImageVectorEntry imageVectorEntry = (ImageVectorEntry) obj;
            return Intrinsics.areEqual(this.imageVector, imageVectorEntry.imageVector) && this.configFlags == imageVectorEntry.configFlags;
        }

        public final int getConfigFlags() {
            return this.configFlags;
        }

        public final ImageVector getImageVector() {
            return this.imageVector;
        }

        public int hashCode() {
            return (this.imageVector.hashCode() * 31) + Integer.hashCode(this.configFlags);
        }

        public String toString() {
            return "ImageVectorEntry(imageVector=" + this.imageVector + ", configFlags=" + this.configFlags + ')';
        }
    }

    /* JADX INFO: compiled from: VectorResources.android.kt */
    public final class Key {
        private final int id;
        private final Resources.Theme theme;

        public Key(Resources.Theme theme, int i) {
            this.theme = theme;
            this.id = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Key)) {
                return false;
            }
            Key key = (Key) obj;
            return Intrinsics.areEqual(this.theme, key.theme) && this.id == key.id;
        }

        public int hashCode() {
            return (this.theme.hashCode() * 31) + Integer.hashCode(this.id);
        }

        public String toString() {
            return "Key(theme=" + this.theme + ", id=" + this.id + ')';
        }
    }

    public final void clear() {
        this.map.clear();
    }

    public final ImageVectorEntry get(Key key) {
        WeakReference weakReference = (WeakReference) this.map.get(key);
        if (weakReference != null) {
            return (ImageVectorEntry) weakReference.get();
        }
        return null;
    }

    public final void prune(int i) {
        Iterator it = this.map.entrySet().iterator();
        while (it.hasNext()) {
            ImageVectorEntry imageVectorEntry = (ImageVectorEntry) ((WeakReference) ((Map.Entry) it.next()).getValue()).get();
            if (imageVectorEntry == null || Configuration.needNewResources(i, imageVectorEntry.getConfigFlags())) {
                it.remove();
            }
        }
    }

    public final void set(Key key, ImageVectorEntry imageVectorEntry) {
        this.map.put(key, new WeakReference(imageVectorEntry));
    }
}
