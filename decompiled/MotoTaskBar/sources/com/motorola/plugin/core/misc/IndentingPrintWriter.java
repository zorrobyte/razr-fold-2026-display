package com.motorola.plugin.core.misc;

import com.motorola.plugin.core.ExtensionsKt;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.CharsKt;

/* JADX INFO: compiled from: IndentingPrintWriter.kt */
/* JADX INFO: loaded from: classes2.dex */
public class IndentingPrintWriter extends PrintWriter implements IPrinter {
    private char[] mCurrentIndent;
    private int mCurrentLength;
    private boolean mEmptyLine;
    private final StringBuilder mIndentBuilder;
    private final char[] mSingleChar;
    private final String mSingleIndent;
    private final int mWrapLength;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IndentingPrintWriter(Writer writer) {
        this(writer, null, 0, 6, null);
        writer.getClass();
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public IndentingPrintWriter(Writer writer, String str) {
        this(writer, str, 0, 4, null);
        writer.getClass();
        str.getClass();
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IndentingPrintWriter(Writer writer, String str, int i) {
        super(writer);
        writer.getClass();
        str.getClass();
        this.mSingleIndent = str;
        this.mWrapLength = i;
        this.mIndentBuilder = new StringBuilder();
        this.mEmptyLine = true;
        this.mSingleChar = new char[1];
    }

    public /* synthetic */ IndentingPrintWriter(Writer writer, String str, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(writer, (i2 & 2) != 0 ? "  " : str, (i2 & 4) != 0 ? -1 : i);
    }

    private final void maybeWriteIndent() {
        if (this.mEmptyLine) {
            this.mEmptyLine = false;
            if (this.mIndentBuilder.length() > 0) {
                if (this.mCurrentIndent == null) {
                    String string = this.mIndentBuilder.toString();
                    string.getClass();
                    char[] charArray = string.toCharArray();
                    charArray.getClass();
                    this.mCurrentIndent = charArray;
                }
                char[] cArr = this.mCurrentIndent;
                cArr.getClass();
                super.write(cArr, 0, cArr.length);
            }
        }
    }

    public static /* synthetic */ IndentingPrintWriter setIndent$default(IndentingPrintWriter indentingPrintWriter, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setIndent");
        }
        if ((i2 & 1) != 0) {
            i = 1;
        }
        return indentingPrintWriter.setIndent(i);
    }

    @Override // com.motorola.plugin.core.misc.IPrinter
    public IPrinter decreaseIndent() {
        this.mIndentBuilder.delete(0, this.mSingleIndent.length());
        this.mCurrentIndent = null;
        return this;
    }

    @Override // com.motorola.plugin.core.misc.IPrinter
    public IPrinter increaseIndent() {
        this.mIndentBuilder.append(this.mSingleIndent);
        this.mCurrentIndent = null;
        return this;
    }

    @Override // com.motorola.plugin.core.misc.IPrinter
    public IPrinter newLine() {
        println();
        return this;
    }

    @Override // com.motorola.plugin.core.misc.IPrinter
    public IPrinter printHexPair(String str, int i) {
        str.getClass();
        String string = Integer.toString(i, CharsKt.checkRadix(16));
        string.getClass();
        printPair(str, Intrinsics.stringPlus("0x", string));
        return this;
    }

    @Override // com.motorola.plugin.core.misc.IPrinter
    public IPrinter printIndex(int i, Object obj, String str) {
        str.getClass();
        print(str + i + ' ' + obj + ' ');
        return this;
    }

    @Override // com.motorola.plugin.core.misc.IPrinter
    public IPrinter printPair(String str, Object obj) {
        str.getClass();
        print(str + '=' + obj + ' ');
        return this;
    }

    @Override // com.motorola.plugin.core.misc.IPrinter
    public IPrinter printPair(String str, Object[] objArr) {
        str.getClass();
        print(str + '=' + ((Object) Arrays.toString(objArr)) + ' ');
        return this;
    }

    @Override // com.motorola.plugin.core.misc.IPrinter
    public IPrinter printSingle(String str) {
        str.getClass();
        print(Intrinsics.stringPlus(str, " "));
        return this;
    }

    @Override // com.motorola.plugin.core.misc.IPrinter
    public IPrinter printTimed(long j, Object obj) {
        StringBuilder sb = new StringBuilder();
        sb.append(ExtensionsKt.timestampWithZone(j));
        sb.append(' ');
        sb.append(obj);
        print(sb.toString());
        return this;
    }

    @Override // java.io.PrintWriter
    public void println() {
        write(10);
    }

    public final IndentingPrintWriter setIndent(int i) {
        this.mIndentBuilder.setLength(0);
        for (int i2 = 0; i2 < i; i2++) {
            increaseIndent();
        }
        return this;
    }

    public final IndentingPrintWriter setIndent(String str) {
        str.getClass();
        this.mIndentBuilder.setLength(0);
        this.mIndentBuilder.append(str);
        this.mCurrentIndent = null;
        return this;
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(int i) {
        char[] cArr = this.mSingleChar;
        cArr[0] = (char) i;
        write(cArr, 0, 1);
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(String str, int i, int i2) {
        str.getClass();
        char[] cArr = new char[i2];
        str.getChars(i, i2 - i, cArr, 0);
        write(cArr, 0, i2);
    }

    @Override // java.io.PrintWriter, java.io.Writer
    public void write(char[] cArr, int i, int i2) {
        int i3;
        int i4;
        cArr.getClass();
        int length = this.mIndentBuilder.length();
        int i5 = i2 + i;
        loop0: while (true) {
            i3 = i;
            while (i < i5) {
                i4 = i + 1;
                char c = cArr[i];
                this.mCurrentLength++;
                if (c == '\n') {
                    maybeWriteIndent();
                    super.write(cArr, i3, i4 - i3);
                    this.mEmptyLine = true;
                    this.mCurrentLength = 0;
                    i3 = i4;
                }
                int i6 = this.mWrapLength;
                if (i6 > 0 && this.mCurrentLength >= i6 - length) {
                    if (!this.mEmptyLine) {
                        super.write(10);
                        this.mEmptyLine = true;
                        this.mCurrentLength = i4 - i3;
                    }
                }
                i = i4;
            }
            maybeWriteIndent();
            super.write(cArr, i3, i4 - i3);
            super.write(10);
            this.mEmptyLine = true;
            this.mCurrentLength = 0;
            i = i4;
        }
        if (i3 != i) {
            maybeWriteIndent();
            super.write(cArr, i3, i - i3);
        }
    }
}
