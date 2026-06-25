package com.motorola.plugin.core.misc;

/* JADX INFO: compiled from: IPrinter.kt */
/* JADX INFO: loaded from: classes2.dex */
public interface IPrinter {

    /* JADX INFO: compiled from: IPrinter.kt */
    public final class DefaultImpls {
        public static /* synthetic */ IPrinter printIndex$default(IPrinter iPrinter, int i, Object obj, String str, int i2, Object obj2) {
            if (obj2 != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: printIndex");
            }
            if ((i2 & 4) != 0) {
                str = "#";
            }
            return iPrinter.printIndex(i, obj, str);
        }
    }

    IPrinter decreaseIndent();

    IPrinter increaseIndent();

    IPrinter newLine();

    IPrinter printHexPair(String str, int i);

    IPrinter printIndex(int i, Object obj, String str);

    IPrinter printPair(String str, Object obj);

    IPrinter printPair(String str, Object[] objArr);

    IPrinter printSingle(String str);

    IPrinter printTimed(long j, Object obj);
}
