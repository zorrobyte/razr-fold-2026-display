package androidx.lifecycle;

import android.app.Application;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SavedStateViewModelFactory.android.kt */
/* JADX INFO: loaded from: classes.dex */
public abstract class SavedStateViewModelFactory_androidKt {
    private static final List ANDROID_VIEWMODEL_SIGNATURE = CollectionsKt.listOf((Object[]) new Class[]{Application.class, SavedStateHandle.class});
    private static final List VIEWMODEL_SIGNATURE = CollectionsKt.listOf(SavedStateHandle.class);

    public static final Constructor findMatchingConstructor(Class cls, List list) {
        cls.getClass();
        list.getClass();
        Constructor<?>[] constructors = cls.getConstructors();
        constructors.getClass();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            parameterTypes.getClass();
            List list2 = ArraysKt.toList(parameterTypes);
            if (Intrinsics.areEqual(list, list2)) {
                return constructor;
            }
            if (list.size() == list2.size() && list2.containsAll(list)) {
                throw new UnsupportedOperationException("Class " + cls.getSimpleName() + " must have parameters in the proper order: " + list);
            }
        }
        return null;
    }

    public static final ViewModel newInstance(Class cls, Constructor constructor, Object... objArr) {
        cls.getClass();
        constructor.getClass();
        objArr.getClass();
        try {
            return (ViewModel) constructor.newInstance(Arrays.copyOf(objArr, objArr.length));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access " + cls, e);
        } catch (InstantiationException e2) {
            throw new RuntimeException("A " + cls + " cannot be instantiated.", e2);
        } catch (InvocationTargetException e3) {
            throw new RuntimeException("An exception happened in constructor of " + cls, e3.getCause());
        }
    }
}
