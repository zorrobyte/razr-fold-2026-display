package kotlinx.coroutines.internal;

/* JADX INFO: compiled from: Symbol.kt */
/* JADX INFO: loaded from: classes.dex */
public final class Symbol {
    public final String symbol;

    public Symbol(String str) {
        str.getClass();
        this.symbol = str;
    }

    public String toString() {
        return "<" + this.symbol + ">";
    }
}
