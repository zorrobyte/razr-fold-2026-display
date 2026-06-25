package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
final class ByFunctionOrdering extends Ordering implements Serializable {
    private static final long serialVersionUID = 0;
    final Function function;
    final Ordering ordering;

    ByFunctionOrdering(Function function, Ordering ordering) {
        function.getClass();
        this.function = function;
        ordering.getClass();
        this.ordering = ordering;
    }

    @Override // com.google.common.collect.Ordering, java.util.Comparator
    public int compare(Object obj, Object obj2) {
        return this.ordering.compare(this.function.apply(obj), this.function.apply(obj2));
    }

    @Override // java.util.Comparator
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ByFunctionOrdering) {
            ByFunctionOrdering byFunctionOrdering = (ByFunctionOrdering) obj;
            if (this.function.equals(byFunctionOrdering.function) && this.ordering.equals(byFunctionOrdering.ordering)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.function, this.ordering);
    }

    public String toString() {
        return this.ordering + ".onResultOf(" + this.function + ")";
    }
}
