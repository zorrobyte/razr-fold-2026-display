package androidx.transition;

import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.graphics.Path;
import android.util.Property;

/* JADX INFO: loaded from: classes.dex */
abstract class ObjectAnimatorUtils {

    abstract class Api21Impl {
        static ObjectAnimator ofObject(Object obj, Property property, Path path) {
            return ObjectAnimator.ofObject(obj, (Property<Object, V>) property, (TypeConverter) null, path);
        }
    }

    static ObjectAnimator ofPointF(Object obj, Property property, Path path) {
        return Api21Impl.ofObject(obj, property, path);
    }
}
