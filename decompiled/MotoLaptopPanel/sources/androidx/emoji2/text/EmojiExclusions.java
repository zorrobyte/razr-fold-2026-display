package androidx.emoji2.text;

import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
abstract class EmojiExclusions {

    abstract class EmojiExclusions_Api34 {
        static Set getExclusions() {
            return EmojiExclusions_Reflections.getExclusions();
        }
    }

    abstract class EmojiExclusions_Reflections {
        static Set getExclusions() {
            try {
                Class[] clsArr = new Class[0];
                Object objInvoke = Class.forName("android.text.EmojiConsistency").getMethod("getEmojiConsistencySet", null).invoke(null, null);
                if (objInvoke == null) {
                    return Collections.EMPTY_SET;
                }
                Set set = (Set) objInvoke;
                Iterator it = set.iterator();
                while (it.hasNext()) {
                    if (!(it.next() instanceof int[])) {
                        return Collections.EMPTY_SET;
                    }
                }
                return set;
            } catch (Throwable unused) {
                return Collections.EMPTY_SET;
            }
        }
    }

    static Set getEmojiExclusions() {
        return EmojiExclusions_Api34.getExclusions();
    }
}
