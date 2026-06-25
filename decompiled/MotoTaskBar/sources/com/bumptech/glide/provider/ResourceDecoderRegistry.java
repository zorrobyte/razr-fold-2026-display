package com.bumptech.glide.provider;

import com.bumptech.glide.load.ResourceDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class ResourceDecoderRegistry {
    private final List bucketPriorityList = new ArrayList();
    private final Map decoders = new HashMap();

    class Entry {
        private final Class dataClass;
        final ResourceDecoder decoder;
        final Class resourceClass;

        public Entry(Class cls, Class cls2, ResourceDecoder resourceDecoder) {
            this.dataClass = cls;
            this.resourceClass = cls2;
            this.decoder = resourceDecoder;
        }

        public boolean handles(Class cls, Class cls2) {
            return this.dataClass.isAssignableFrom(cls) && cls2.isAssignableFrom(this.resourceClass);
        }
    }

    private synchronized List getOrAddEntryList(String str) {
        List arrayList;
        try {
            if (!this.bucketPriorityList.contains(str)) {
                this.bucketPriorityList.add(str);
            }
            arrayList = (List) this.decoders.get(str);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.decoders.put(str, arrayList);
            }
        } catch (Throwable th) {
            throw th;
        }
        return arrayList;
    }

    public synchronized void append(String str, ResourceDecoder resourceDecoder, Class cls, Class cls2) {
        getOrAddEntryList(str).add(new Entry(cls, cls2, resourceDecoder));
    }

    public synchronized List getDecoders(Class cls, Class cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator it = this.bucketPriorityList.iterator();
        while (it.hasNext()) {
            List<Entry> list = (List) this.decoders.get((String) it.next());
            if (list != null) {
                for (Entry entry : list) {
                    if (entry.handles(cls, cls2)) {
                        arrayList.add(entry.decoder);
                    }
                }
            }
        }
        return arrayList;
    }

    public synchronized List getResourceClasses(Class cls, Class cls2) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator it = this.bucketPriorityList.iterator();
        while (it.hasNext()) {
            List<Entry> list = (List) this.decoders.get((String) it.next());
            if (list != null) {
                for (Entry entry : list) {
                    if (entry.handles(cls, cls2) && !arrayList.contains(entry.resourceClass)) {
                        arrayList.add(entry.resourceClass);
                    }
                }
            }
        }
        return arrayList;
    }

    public synchronized void setBucketPriorityList(List list) {
        try {
            ArrayList arrayList = new ArrayList(this.bucketPriorityList);
            this.bucketPriorityList.clear();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                this.bucketPriorityList.add((String) it.next());
            }
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                String str = (String) obj;
                if (!list.contains(str)) {
                    this.bucketPriorityList.add(str);
                }
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
