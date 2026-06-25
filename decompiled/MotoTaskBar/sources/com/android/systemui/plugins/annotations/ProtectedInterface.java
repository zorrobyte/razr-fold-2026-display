package com.android.systemui.plugins.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;

/* JADX INFO: compiled from: ProtectedInterface.kt */
/* JADX INFO: loaded from: classes.dex */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
public @interface ProtectedInterface {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* JADX INFO: compiled from: ProtectedInterface.kt */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        private static final ProtectedInterface Default;

        static {
            final String[] strArr = {"java.lang.Exception", "java.lang.LinkageError"};
            Default = new ProtectedInterface(strArr) { // from class: com.android.systemui.plugins.annotations.ProtectedInterface$Companion$annotationImpl$com_android_systemui_plugins_annotations_ProtectedInterface$0
                private final /* synthetic */ String[] exTypes;

                {
                    strArr.getClass();
                    this.exTypes = strArr;
                }

                @Override // java.lang.annotation.Annotation
                public final /* synthetic */ Class annotationType() {
                    return ProtectedInterface.class;
                }

                @Override // java.lang.annotation.Annotation
                public final boolean equals(Object obj) {
                    return (obj instanceof ProtectedInterface) && Arrays.equals(exTypes(), ((ProtectedInterface) obj).exTypes());
                }

                @Override // com.android.systemui.plugins.annotations.ProtectedInterface
                public final /* synthetic */ String[] exTypes() {
                    return this.exTypes;
                }

                @Override // java.lang.annotation.Annotation
                public final int hashCode() {
                    return Arrays.hashCode(this.exTypes) ^ ("exTypes".hashCode() * 127);
                }

                @Override // java.lang.annotation.Annotation
                public final String toString() {
                    return "@com.android.systemui.plugins.annotations.ProtectedInterface(exTypes=" + Arrays.toString(this.exTypes) + ')';
                }
            };
        }

        private Companion() {
        }

        public final ProtectedInterface getDefault() {
            return Default;
        }
    }

    String[] exTypes();
}
