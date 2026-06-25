package com.airbnb.lottie.model;

import androidx.core.util.Pair;

/* JADX INFO: loaded from: classes.dex */
public class MutablePair {
    Object first;
    Object second;

    private static boolean objectsEqual(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        return objectsEqual(pair.first, this.first) && objectsEqual(pair.second, this.second);
    }

    public int hashCode() {
        Object obj = this.first;
        int iHashCode = obj == null ? 0 : obj.hashCode();
        Object obj2 = this.second;
        return iHashCode ^ (obj2 != null ? obj2.hashCode() : 0);
    }

    public void set(Object obj, Object obj2) {
        this.first = obj;
        this.second = obj2;
    }

    public String toString() {
        return "Pair{" + this.first + " " + this.second + "}";
    }
}
