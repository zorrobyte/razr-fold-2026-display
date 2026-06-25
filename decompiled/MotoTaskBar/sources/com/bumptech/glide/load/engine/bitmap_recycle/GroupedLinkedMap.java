package com.bumptech.glide.load.engine.bitmap_recycle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class GroupedLinkedMap {
    private final LinkedEntry head = new LinkedEntry();
    private final Map keyToEntry = new HashMap();

    class LinkedEntry {
        final Object key;
        LinkedEntry next;
        LinkedEntry prev;
        private List values;

        LinkedEntry() {
            this(null);
        }

        LinkedEntry(Object obj) {
            this.prev = this;
            this.next = this;
            this.key = obj;
        }

        public void add(Object obj) {
            if (this.values == null) {
                this.values = new ArrayList();
            }
            this.values.add(obj);
        }

        public Object removeLast() {
            int size = size();
            if (size > 0) {
                return this.values.remove(size - 1);
            }
            return null;
        }

        public int size() {
            List list = this.values;
            if (list != null) {
                return list.size();
            }
            return 0;
        }
    }

    GroupedLinkedMap() {
    }

    private void makeHead(LinkedEntry linkedEntry) {
        removeEntry(linkedEntry);
        LinkedEntry linkedEntry2 = this.head;
        linkedEntry.prev = linkedEntry2;
        linkedEntry.next = linkedEntry2.next;
        updateEntry(linkedEntry);
    }

    private void makeTail(LinkedEntry linkedEntry) {
        removeEntry(linkedEntry);
        LinkedEntry linkedEntry2 = this.head;
        linkedEntry.prev = linkedEntry2.prev;
        linkedEntry.next = linkedEntry2;
        updateEntry(linkedEntry);
    }

    private static void removeEntry(LinkedEntry linkedEntry) {
        LinkedEntry linkedEntry2 = linkedEntry.prev;
        linkedEntry2.next = linkedEntry.next;
        linkedEntry.next.prev = linkedEntry2;
    }

    private static void updateEntry(LinkedEntry linkedEntry) {
        linkedEntry.next.prev = linkedEntry;
        linkedEntry.prev.next = linkedEntry;
    }

    public Object get(Poolable poolable) {
        LinkedEntry linkedEntry = (LinkedEntry) this.keyToEntry.get(poolable);
        if (linkedEntry == null) {
            linkedEntry = new LinkedEntry(poolable);
            this.keyToEntry.put(poolable, linkedEntry);
        } else {
            poolable.offer();
        }
        makeHead(linkedEntry);
        return linkedEntry.removeLast();
    }

    public void put(Poolable poolable, Object obj) {
        LinkedEntry linkedEntry = (LinkedEntry) this.keyToEntry.get(poolable);
        if (linkedEntry == null) {
            linkedEntry = new LinkedEntry(poolable);
            makeTail(linkedEntry);
            this.keyToEntry.put(poolable, linkedEntry);
        } else {
            poolable.offer();
        }
        linkedEntry.add(obj);
    }

    public Object removeLast() {
        for (LinkedEntry linkedEntry = this.head.prev; !linkedEntry.equals(this.head); linkedEntry = linkedEntry.prev) {
            Object objRemoveLast = linkedEntry.removeLast();
            if (objRemoveLast != null) {
                return objRemoveLast;
            }
            removeEntry(linkedEntry);
            this.keyToEntry.remove(linkedEntry.key);
            ((Poolable) linkedEntry.key).offer();
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GroupedLinkedMap( ");
        LinkedEntry linkedEntry = this.head.next;
        boolean z = false;
        while (!linkedEntry.equals(this.head)) {
            sb.append('{');
            sb.append(linkedEntry.key);
            sb.append(':');
            sb.append(linkedEntry.size());
            sb.append("}, ");
            linkedEntry = linkedEntry.next;
            z = true;
        }
        if (z) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append(" )");
        return sb.toString();
    }
}
