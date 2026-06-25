package okio;

/* JADX INFO: compiled from: Okio.kt */
/* JADX INFO: loaded from: classes2.dex */
abstract /* synthetic */ class Okio__OkioKt {
    public static final BufferedSource buffer(Source source) {
        source.getClass();
        return new RealBufferedSource(source);
    }
}
