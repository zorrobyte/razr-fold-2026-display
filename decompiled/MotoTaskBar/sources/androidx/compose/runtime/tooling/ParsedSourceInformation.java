package androidx.compose.runtime.tooling;

/* JADX INFO: compiled from: ComposeStackTrace.kt */
/* JADX INFO: loaded from: classes.dex */
public final class ParsedSourceInformation {
    private final String dataString;
    private final String fileName;
    private final String functionName;
    private final boolean isCall;
    private final int[] lineNumbers;
    private final String packageHash;

    public ParsedSourceInformation(boolean z, String str, String str2, String str3, int[] iArr, String str4) {
        this.isCall = z;
        this.functionName = str;
        this.fileName = str2;
        this.packageHash = str3;
        this.lineNumbers = iArr;
        this.dataString = str4;
    }

    public final String getFileName() {
        return this.fileName;
    }

    public final String getFunctionName() {
        return this.functionName;
    }

    public final int[] getLineNumbers() {
        return this.lineNumbers;
    }

    public final String getPackageHash() {
        return this.packageHash;
    }

    public final boolean isCall() {
        return this.isCall;
    }
}
