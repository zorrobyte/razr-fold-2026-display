package androidx.core.animation;

import android.util.Log;
import android.util.Property;
import androidx.core.animation.Keyframes;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class PropertyValuesHolder implements Cloneable {
    private static final Class[] DOUBLE_VARIANTS;
    private static final Class[] FLOAT_VARIANTS;
    private static final Class[] INTEGER_VARIANTS;
    private static final HashMap sGetterPropertyMap;
    static final HashMap sSetterPropertyMap;
    private TypeEvaluator mEvaluator;
    private Method mGetter;
    Keyframes mKeyframes;
    Property mProperty;
    String mPropertyName;
    Method mSetter;
    final Object[] mTmpValueArray;
    Class mValueType;

    class FloatPropertyValuesHolder extends PropertyValuesHolder {
        float mFloatAnimatedValue;
        Keyframes.FloatKeyframes mFloatKeyframes;

        FloatPropertyValuesHolder(Property property, float... fArr) {
            super(property);
            setFloatValues(fArr);
        }

        FloatPropertyValuesHolder(String str, float... fArr) {
            super(str);
            setFloatValues(fArr);
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        void calculateValue(float f) {
            this.mFloatAnimatedValue = this.mFloatKeyframes.getFloatValue(f);
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        /* JADX INFO: renamed from: clone, reason: merged with bridge method [inline-methods] */
        public FloatPropertyValuesHolder m1051clone() {
            FloatPropertyValuesHolder floatPropertyValuesHolder = (FloatPropertyValuesHolder) super.m1051clone();
            floatPropertyValuesHolder.mFloatKeyframes = (Keyframes.FloatKeyframes) floatPropertyValuesHolder.mKeyframes;
            return floatPropertyValuesHolder;
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        Object getAnimatedValue() {
            return Float.valueOf(this.mFloatAnimatedValue);
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        void setAnimatedValue(Object obj) {
            Property property = this.mProperty;
            if (property != null) {
                property.set(obj, Float.valueOf(this.mFloatAnimatedValue));
                return;
            }
            if (this.mSetter != null) {
                try {
                    this.mTmpValueArray[0] = Float.valueOf(this.mFloatAnimatedValue);
                    this.mSetter.invoke(obj, this.mTmpValueArray);
                } catch (IllegalAccessException e) {
                    Log.e("PropertyValuesHolder", e.toString());
                } catch (InvocationTargetException e2) {
                    Log.e("PropertyValuesHolder", e2.toString());
                }
            }
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        public void setFloatValues(float... fArr) {
            super.setFloatValues(fArr);
            this.mFloatKeyframes = (Keyframes.FloatKeyframes) this.mKeyframes;
        }

        @Override // androidx.core.animation.PropertyValuesHolder
        public void setProperty(Property property) {
            super.setProperty(property);
        }
    }

    static {
        Class cls = Float.TYPE;
        Class cls2 = Double.TYPE;
        Class cls3 = Integer.TYPE;
        FLOAT_VARIANTS = new Class[]{cls, Float.class, cls2, cls3, Double.class, Integer.class};
        INTEGER_VARIANTS = new Class[]{cls3, Integer.class, cls, cls2, Float.class, Double.class};
        DOUBLE_VARIANTS = new Class[]{cls2, Double.class, cls, cls3, Float.class, Integer.class};
        sSetterPropertyMap = new HashMap();
        sGetterPropertyMap = new HashMap();
    }

    PropertyValuesHolder(Property property) {
        this.mSetter = null;
        this.mGetter = null;
        this.mKeyframes = null;
        this.mTmpValueArray = new Object[1];
        this.mProperty = property;
        if (property != null) {
            this.mPropertyName = property.getName();
        }
    }

    PropertyValuesHolder(String str) {
        this.mSetter = null;
        this.mGetter = null;
        this.mKeyframes = null;
        this.mTmpValueArray = new Object[1];
        this.mPropertyName = str;
    }

    private Object convertBack(Object obj) {
        return obj;
    }

    static String getMethodName(String str, String str2) {
        if (str2 == null || str2.length() == 0) {
            return str;
        }
        return str + Character.toUpperCase(str2.charAt(0)) + str2.substring(1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private Method getPropertyFunction(Class cls, String str, Class cls2) {
        String methodName = getMethodName(str, this.mPropertyName);
        Method method = null;
        if (cls2 == null) {
            try {
                method = cls.getMethod(methodName, null);
            } catch (NoSuchMethodException unused) {
            }
        } else {
            Class[] clsArr = new Class[1];
            for (Class cls3 : cls2.equals(Float.class) ? FLOAT_VARIANTS : cls2.equals(Integer.class) ? INTEGER_VARIANTS : cls2.equals(Double.class) ? DOUBLE_VARIANTS : new Class[]{cls2}) {
                clsArr[0] = cls3;
                try {
                    try {
                        Method method2 = cls.getMethod(methodName, clsArr);
                        this.mValueType = cls3;
                        return method2;
                    } catch (NoSuchMethodException unused2) {
                    }
                } catch (NoSuchMethodException unused3) {
                    method = cls.getDeclaredMethod(methodName, clsArr);
                    method.setAccessible(true);
                    this.mValueType = cls3;
                    return method;
                }
            }
        }
        if (method == null) {
            Log.w("PropertyValuesHolder", "Method " + getMethodName(str, this.mPropertyName) + "() with type " + cls2 + " not found on target class " + cls);
        }
        return method;
    }

    public static PropertyValuesHolder ofFloat(Property property, float... fArr) {
        return new FloatPropertyValuesHolder(property, fArr);
    }

    public static PropertyValuesHolder ofFloat(String str, float... fArr) {
        return new FloatPropertyValuesHolder(str, fArr);
    }

    private void setupGetter(Class cls) {
        this.mGetter = setupSetterOrGetter(cls, sGetterPropertyMap, "get", null);
    }

    private Method setupSetterOrGetter(Class cls, HashMap map, String str, Class cls2) {
        Method propertyFunction;
        boolean zContainsKey;
        synchronized (map) {
            try {
                HashMap map2 = (HashMap) map.get(cls);
                propertyFunction = null;
                if (map2 != null) {
                    zContainsKey = map2.containsKey(this.mPropertyName);
                    if (zContainsKey) {
                        propertyFunction = (Method) map2.get(this.mPropertyName);
                    }
                } else {
                    zContainsKey = false;
                }
                if (!zContainsKey) {
                    propertyFunction = getPropertyFunction(cls, str, cls2);
                    if (map2 == null) {
                        map2 = new HashMap();
                        map.put(cls, map2);
                    }
                    map2.put(this.mPropertyName, propertyFunction);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return propertyFunction;
    }

    private void setupValue(Object obj, Keyframe keyframe) {
        Property property = this.mProperty;
        if (property != null) {
            keyframe.setValue(convertBack(property.get(obj)));
            return;
        }
        try {
            if (this.mGetter == null) {
                setupGetter(obj.getClass());
                if (this.mGetter == null) {
                    return;
                }
            }
            keyframe.setValue(convertBack(this.mGetter.invoke(obj, null)));
        } catch (IllegalAccessException e) {
            Log.e("PropertyValuesHolder", e.toString());
        } catch (InvocationTargetException e2) {
            Log.e("PropertyValuesHolder", e2.toString());
        }
    }

    abstract void calculateValue(float f);

    /* JADX INFO: renamed from: clone */
    public PropertyValuesHolder m1051clone() {
        try {
            PropertyValuesHolder propertyValuesHolder = (PropertyValuesHolder) super.clone();
            propertyValuesHolder.mPropertyName = this.mPropertyName;
            propertyValuesHolder.mProperty = this.mProperty;
            propertyValuesHolder.mKeyframes = this.mKeyframes.m1048clone();
            propertyValuesHolder.mEvaluator = this.mEvaluator;
            return propertyValuesHolder;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    abstract Object getAnimatedValue();

    public String getPropertyName() {
        return this.mPropertyName;
    }

    void init() {
        if (this.mEvaluator == null) {
            Class cls = this.mValueType;
            this.mEvaluator = cls == Integer.class ? IntEvaluator.getInstance() : cls == Float.class ? FloatEvaluator.getInstance() : null;
        }
        TypeEvaluator typeEvaluator = this.mEvaluator;
        if (typeEvaluator != null) {
            this.mKeyframes.setEvaluator(typeEvaluator);
        }
    }

    abstract void setAnimatedValue(Object obj);

    public void setFloatValues(float... fArr) {
        this.mValueType = Float.TYPE;
        this.mKeyframes = KeyframeSet.ofFloat(fArr);
    }

    public void setProperty(Property property) {
        this.mProperty = property;
    }

    void setupSetter(Class cls) {
        this.mSetter = setupSetterOrGetter(cls, sSetterPropertyMap, "set", this.mValueType);
    }

    void setupSetterAndGetter(Object obj) {
        if (this.mProperty != null) {
            try {
                List keyframes = this.mKeyframes.getKeyframes();
                int size = keyframes == null ? 0 : keyframes.size();
                Object objConvertBack = null;
                for (int i = 0; i < size; i++) {
                    Keyframe keyframe = (Keyframe) keyframes.get(i);
                    if (!keyframe.hasValue() || keyframe.valueWasSetOnStart()) {
                        if (objConvertBack == null) {
                            objConvertBack = convertBack(this.mProperty.get(obj));
                        }
                        keyframe.setValue(objConvertBack);
                        keyframe.setValueWasSetOnStart(true);
                    }
                }
                return;
            } catch (ClassCastException unused) {
                Log.w("PropertyValuesHolder", "No such property (" + this.mProperty.getName() + ") on target object " + obj + ". Trying reflection instead");
                this.mProperty = null;
            }
        }
        if (this.mProperty == null) {
            Class<?> cls = obj.getClass();
            if (this.mSetter == null) {
                setupSetter(cls);
            }
            List keyframes2 = this.mKeyframes.getKeyframes();
            int size2 = keyframes2 == null ? 0 : keyframes2.size();
            for (int i2 = 0; i2 < size2; i2++) {
                Keyframe keyframe2 = (Keyframe) keyframes2.get(i2);
                if (!keyframe2.hasValue() || keyframe2.valueWasSetOnStart()) {
                    if (this.mGetter == null) {
                        setupGetter(cls);
                        if (this.mGetter == null) {
                            return;
                        }
                    }
                    try {
                        keyframe2.setValue(convertBack(this.mGetter.invoke(obj, null)));
                        keyframe2.setValueWasSetOnStart(true);
                    } catch (IllegalAccessException e) {
                        Log.e("PropertyValuesHolder", e.toString());
                    } catch (InvocationTargetException e2) {
                        Log.e("PropertyValuesHolder", e2.toString());
                    }
                }
            }
        }
    }

    void setupStartValue(Object obj) {
        List keyframes = this.mKeyframes.getKeyframes();
        if (keyframes.isEmpty()) {
            return;
        }
        setupValue(obj, (Keyframe) keyframes.get(0));
    }

    public String toString() {
        return this.mPropertyName + ": " + this.mKeyframes.toString();
    }
}
