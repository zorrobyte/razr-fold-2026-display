package androidx.transition;

import android.animation.PropertyValuesHolder;
import android.animation.TypeConverter;
import android.graphics.Path;
import android.util.Property;

/* JADX INFO: loaded from: classes.dex */
abstract class PropertyValuesHolderUtils {

    abstract class Api21Impl {
        static PropertyValuesHolder ofObject(Property property, Path path) {
            return PropertyValuesHolder.ofObject(property, (TypeConverter) null, path);
        }
    }

    static PropertyValuesHolder ofPointF(Property property, Path path) {
        return Api21Impl.ofObject(property, path);
    }
}
