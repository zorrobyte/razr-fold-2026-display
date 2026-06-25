package com.android.systemui.plugins.log;

/* JADX INFO: compiled from: TableLogBufferBase.kt */
/* JADX INFO: loaded from: classes.dex */
public interface TableLogBufferBase {

    /* JADX INFO: compiled from: TableLogBufferBase.kt */
    public final class DefaultImpls {
        public static void logChange(TableLogBufferBase tableLogBufferBase, String str, String str2, Integer num) {
            str.getClass();
            str2.getClass();
            tableLogBufferBase.logChange(str, str2, num, false);
        }

        public static void logChange(TableLogBufferBase tableLogBufferBase, String str, String str2, String str3) {
            str.getClass();
            str2.getClass();
            tableLogBufferBase.logChange(str, str2, str3, false);
        }

        public static void logChange(TableLogBufferBase tableLogBufferBase, String str, String str2, boolean z) {
            str.getClass();
            str2.getClass();
            tableLogBufferBase.logChange(str, str2, z, false);
        }

        public static /* synthetic */ void logChange$default(TableLogBufferBase tableLogBufferBase, String str, String str2, Integer num, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logChange");
            }
            if ((i & 1) != 0) {
                str = "";
            }
            tableLogBufferBase.logChange(str, str2, num);
        }

        public static /* synthetic */ void logChange$default(TableLogBufferBase tableLogBufferBase, String str, String str2, Integer num, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logChange");
            }
            if ((i & 1) != 0) {
                str = "";
            }
            tableLogBufferBase.logChange(str, str2, num, z);
        }

        public static /* synthetic */ void logChange$default(TableLogBufferBase tableLogBufferBase, String str, String str2, String str3, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logChange");
            }
            if ((i & 1) != 0) {
                str = "";
            }
            tableLogBufferBase.logChange(str, str2, str3);
        }

        public static /* synthetic */ void logChange$default(TableLogBufferBase tableLogBufferBase, String str, String str2, String str3, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logChange");
            }
            if ((i & 1) != 0) {
                str = "";
            }
            tableLogBufferBase.logChange(str, str2, str3, z);
        }

        public static /* synthetic */ void logChange$default(TableLogBufferBase tableLogBufferBase, String str, String str2, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logChange");
            }
            if ((i & 1) != 0) {
                str = "";
            }
            tableLogBufferBase.logChange(str, str2, z);
        }

        public static /* synthetic */ void logChange$default(TableLogBufferBase tableLogBufferBase, String str, String str2, boolean z, boolean z2, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: logChange");
            }
            if ((i & 1) != 0) {
                str = "";
            }
            tableLogBufferBase.logChange(str, str2, z, z2);
        }
    }

    void logChange(String str, String str2, Integer num);

    void logChange(String str, String str2, Integer num, boolean z);

    void logChange(String str, String str2, String str3);

    void logChange(String str, String str2, String str3, boolean z);

    void logChange(String str, String str2, boolean z);

    void logChange(String str, String str2, boolean z, boolean z2);
}
