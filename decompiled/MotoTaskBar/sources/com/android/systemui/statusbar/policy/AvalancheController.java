package com.android.systemui.statusbar.policy;

import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.Flags;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* JADX INFO: compiled from: AvalancheController.kt */
/* JADX INFO: loaded from: classes.dex */
public final class AvalancheController implements Dumpable {
    private final boolean debug;
    private Set debugDropSet;
    private final Map debugRunnableLabelMap;
    private BaseHeadsUpManager.HeadsUpEntry headsUpEntryShowing;
    private List headsUpEntryShowingRunnableList;
    private final List nextList;
    private Map nextMap;
    private String previousHunKey;
    private final String tag;

    public AvalancheController(DumpManager dumpManager) {
        dumpManager.getClass();
        this.tag = "AvalancheController";
        this.previousHunKey = "";
        this.headsUpEntryShowingRunnableList = new ArrayList();
        this.nextList = new ArrayList();
        this.nextMap = new HashMap();
        this.debugRunnableLabelMap = new HashMap();
        this.debugDropSet = new HashSet();
        dumpManager.registerNormalDumpable("AvalancheController", this);
    }

    public static /* synthetic */ void getDebugDropSet$annotations() {
    }

    private final String getDropSetStr() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.debugDropSet.iterator();
        while (it.hasNext()) {
            arrayList.add("[" + getKey((BaseHeadsUpManager.HeadsUpEntry) it.next()) + "]");
        }
        String strJoin = String.join("\n", arrayList);
        strJoin.getClass();
        return strJoin;
    }

    public static /* synthetic */ void getHeadsUpEntryShowing$annotations() {
    }

    private final String getNextListStr() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.nextList.iterator();
        while (it.hasNext()) {
            arrayList.add("[" + getKey((BaseHeadsUpManager.HeadsUpEntry) it.next()) + "]");
        }
        String strJoin = String.join("\n", arrayList);
        strJoin.getClass();
        return strJoin;
    }

    public static /* synthetic */ void getNextMap$annotations() {
    }

    private final String getNextMapStr() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.nextMap.keySet().iterator();
        while (it.hasNext()) {
            arrayList.add("[" + getKey((BaseHeadsUpManager.HeadsUpEntry) it.next()) + "]");
        }
        String strJoin = String.join("\n", arrayList);
        strJoin.getClass();
        return strJoin;
    }

    private final String getStateStr() {
        return "SHOWING: [" + getKey(this.headsUpEntryShowing) + "]\nPREVIOUS: [" + this.previousHunKey + "]\nNEXT LIST: " + getNextListStr() + "\nNEXT MAP: " + getNextMapStr() + "\nDROPPED: " + getDropSetStr();
    }

    private final boolean isShowing(BaseHeadsUpManager.HeadsUpEntry headsUpEntry) {
        NotificationEntry notificationEntry;
        if (this.headsUpEntryShowing == null) {
            return false;
        }
        NotificationEntry notificationEntry2 = headsUpEntry.mEntry;
        String key = null;
        String key2 = notificationEntry2 != null ? notificationEntry2.getKey() : null;
        BaseHeadsUpManager.HeadsUpEntry headsUpEntry2 = this.headsUpEntryShowing;
        if (headsUpEntry2 != null && (notificationEntry = headsUpEntry2.mEntry) != null) {
            key = notificationEntry.getKey();
        }
        return Intrinsics.areEqual(key2, key);
    }

    private final void logState(String str) {
        if (this.debug) {
            Log.d(this.tag, "\n=================================================================================");
        }
        if (this.debug) {
            Log.d(this.tag, "STATE " + str);
        }
        if (this.debug) {
            Log.d(this.tag, getStateStr());
        }
        if (this.debug) {
            Log.d(this.tag, "=================================================================================\n");
        }
    }

    private final void showNextAfterRemove() {
        if (this.debug) {
            Log.d(this.tag, "SHOW NEXT");
        }
        this.headsUpEntryShowing = null;
        if (this.nextList.isEmpty()) {
            if (this.debug) {
                Log.d(this.tag, "NO MORE TO SHOW");
                return;
            }
            return;
        }
        CollectionsKt.sort(this.nextList);
        BaseHeadsUpManager.HeadsUpEntry headsUpEntry = (BaseHeadsUpManager.HeadsUpEntry) this.nextList.get(0);
        this.headsUpEntryShowing = headsUpEntry;
        Object obj = this.nextMap.get(headsUpEntry);
        obj.getClass();
        this.headsUpEntryShowingRunnableList = (List) obj;
        List list = this.nextList;
        List listSubList = list.subList(1, list.size());
        if (this.debug) {
            Iterator it = listSubList.iterator();
            while (it.hasNext()) {
                Object obj2 = this.nextMap.get((BaseHeadsUpManager.HeadsUpEntry) it.next());
                obj2.getClass();
                Iterator it2 = ((List) obj2).iterator();
                while (it2.hasNext()) {
                    this.debugRunnableLabelMap.remove((Runnable) it2.next());
                }
            }
            this.debugDropSet.addAll(listSubList);
        }
        clearNext();
        BaseHeadsUpManager.HeadsUpEntry headsUpEntry2 = this.headsUpEntryShowing;
        headsUpEntry2.getClass();
        showNow(headsUpEntry2, this.headsUpEntryShowingRunnableList);
    }

    private final void showNow(BaseHeadsUpManager.HeadsUpEntry headsUpEntry, List list) {
        if (this.debug) {
            Log.d(this.tag, "SHOW: " + getKey(headsUpEntry));
        }
        this.headsUpEntryShowing = headsUpEntry;
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Runnable runnable = (Runnable) it.next();
            if (this.debugRunnableLabelMap.containsKey(runnable) && this.debug) {
                Log.d(this.tag, "RUNNABLE: " + this.debugRunnableLabelMap.get(runnable));
            }
            runnable.run();
        }
    }

    public final void addToNext(BaseHeadsUpManager.HeadsUpEntry headsUpEntry, Runnable runnable) {
        headsUpEntry.getClass();
        runnable.getClass();
        this.nextMap.put(headsUpEntry, CollectionsKt.arrayListOf(runnable));
        this.nextList.add(headsUpEntry);
    }

    public final void clearNext() {
        this.nextList.clear();
        this.nextMap.clear();
    }

    public final void delete(BaseHeadsUpManager.HeadsUpEntry headsUpEntry, Runnable runnable, String str) {
        runnable.getClass();
        str.getClass();
        if (!Flags.notificationAvalancheThrottleHun()) {
            runnable.run();
            return;
        }
        String str2 = "[" + str + "] => AvalancheController.delete " + getKey(headsUpEntry);
        if (headsUpEntry == null) {
            if (this.debug) {
                Log.d(this.tag, str2 + " => cannot remove NULL entry");
                return;
            }
            return;
        }
        if (this.nextMap.containsKey(headsUpEntry)) {
            if (this.debug) {
                Log.d(this.tag, str2 + " => [remove from next]");
            }
            if (this.nextMap.containsKey(headsUpEntry)) {
                this.nextMap.remove(headsUpEntry);
            }
            if (this.nextList.contains(headsUpEntry)) {
                this.nextList.remove(headsUpEntry);
            }
        } else if (this.debugDropSet.contains(headsUpEntry)) {
            if (this.debug) {
                Log.d(this.tag, str2 + " => [remove from dropset]");
            }
            this.debugDropSet.remove(headsUpEntry);
        } else if (isShowing(headsUpEntry)) {
            if (this.debug) {
                Log.d(this.tag, str2 + " => [remove showing " + getKey(headsUpEntry) + "]");
            }
            this.previousHunKey = getKey(this.headsUpEntryShowing);
            runnable.run();
            showNextAfterRemove();
        } else if (this.debug) {
            Log.d(this.tag, str2 + " => [removing untracked " + getKey(headsUpEntry) + "]");
        }
        logState("after " + str2);
    }

    public final int getDurationMs(BaseHeadsUpManager.HeadsUpEntry headsUpEntry, int i) {
        headsUpEntry.getClass();
        if (Flags.notificationAvalancheThrottleHun()) {
            ArrayList arrayList = new ArrayList();
            BaseHeadsUpManager.HeadsUpEntry headsUpEntry2 = this.headsUpEntryShowing;
            if (headsUpEntry2 != null) {
                headsUpEntry2.getClass();
                arrayList.add(headsUpEntry2);
            }
            CollectionsKt.sort(this.nextList);
            List listPlus = CollectionsKt.plus((Collection) arrayList, (Iterable) this.nextList);
            if (!listPlus.isEmpty()) {
                Iterator it = listPlus.iterator();
                int i2 = 0;
                int i3 = -1;
                while (it.hasNext()) {
                    int i4 = i2 + 1;
                    if (Intrinsics.areEqual((BaseHeadsUpManager.HeadsUpEntry) it.next(), headsUpEntry)) {
                        i3 = i2;
                    }
                    i2 = i4;
                }
                if (i3 != -1) {
                    int i5 = i3 + 1;
                    if (i5 < listPlus.size()) {
                        BaseHeadsUpManager.HeadsUpEntry headsUpEntry3 = (BaseHeadsUpManager.HeadsUpEntry) listPlus.get(i5);
                        if (headsUpEntry3.compareNonTimeFields(headsUpEntry) == -1) {
                            if (!this.debug) {
                                return 500;
                            }
                            Log.d(this.tag, "Next entry is higher priority: 500ms");
                            return 500;
                        }
                        if (headsUpEntry3.compareNonTimeFields(headsUpEntry) == 0) {
                            if (!this.debug) {
                                return 1000;
                            }
                            Log.d(this.tag, "Next entry is same priority: 1000ms");
                            return 1000;
                        }
                        if (this.debug) {
                            Log.d(this.tag, "Next entry is lower priority, use default ms: " + i);
                        }
                    } else if (this.debug) {
                        Log.d(this.tag, "Last entry, use default ms: " + i);
                        return i;
                    }
                } else if (this.debug) {
                    Log.d(this.tag, "Untracked entry, use default ms: " + i);
                    return i;
                }
            } else if (this.debug) {
                Log.d(this.tag, "No avalanche HUNs, use default ms: " + i);
                return i;
            }
        }
        return i;
    }

    public final String getKey(BaseHeadsUpManager.HeadsUpEntry headsUpEntry) {
        if (headsUpEntry == null) {
            return "HeadsUpEntry null";
        }
        NotificationEntry notificationEntry = headsUpEntry.mEntry;
        if (notificationEntry == null) {
            return "HeadsUpEntry.mEntry null";
        }
        notificationEntry.getClass();
        String key = notificationEntry.getKey();
        key.getClass();
        return key;
    }

    public final List getWaitingKeys() {
        if (!Flags.notificationAvalancheThrottleHun()) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        Iterator it = this.nextMap.keySet().iterator();
        while (it.hasNext()) {
            NotificationEntry notificationEntry = ((BaseHeadsUpManager.HeadsUpEntry) it.next()).mEntry;
            if (notificationEntry != null) {
                notificationEntry.getClass();
                String key = notificationEntry.getKey();
                key.getClass();
                arrayList.add(key);
            }
        }
        return arrayList;
    }

    public final boolean isWaiting(String str) {
        str.getClass();
        if (!Flags.notificationAvalancheThrottleHun()) {
            return false;
        }
        Iterator it = this.nextMap.keySet().iterator();
        while (it.hasNext()) {
            NotificationEntry notificationEntry = ((BaseHeadsUpManager.HeadsUpEntry) it.next()).mEntry;
            if (StringsKt.equals$default(notificationEntry != null ? notificationEntry.getKey() : null, str, false, 2, null)) {
                return true;
            }
        }
        return false;
    }

    public final void update(BaseHeadsUpManager.HeadsUpEntry headsUpEntry, Runnable runnable, String str) {
        runnable.getClass();
        str.getClass();
        if (!Flags.notificationAvalancheThrottleHun()) {
            runnable.run();
            return;
        }
        String str2 = "[" + str + "] => AvalancheController.update [" + getKey(headsUpEntry) + "]";
        if (headsUpEntry == null) {
            if (this.debug) {
                Log.d(this.tag, "Entry is NULL, stop update.");
                return;
            }
            return;
        }
        if (this.debug) {
            this.debugRunnableLabelMap.put(runnable, str);
        }
        if (isShowing(headsUpEntry)) {
            if (this.debug) {
                Log.d(this.tag, "\n" + str2 + " => [update showing]");
            }
            runnable.run();
        } else if (this.nextMap.containsKey(headsUpEntry)) {
            if (this.debug) {
                Log.d(this.tag, "\n" + str2 + " => [update next]");
            }
            List list = (List) this.nextMap.get(headsUpEntry);
            if (list != null) {
                list.add(runnable);
            }
        } else if (this.headsUpEntryShowing == null) {
            if (this.debug) {
                Log.d(this.tag, "\n" + str2 + " => [showNow]");
            }
            showNow(headsUpEntry, CollectionsKt.arrayListOf(runnable));
        } else {
            if (this.nextMap.containsKey(headsUpEntry)) {
                this.nextMap.remove(headsUpEntry);
            }
            if (this.nextList.contains(headsUpEntry)) {
                this.nextList.remove(headsUpEntry);
            }
            addToNext(headsUpEntry, runnable);
            if (this.nextList.indexOf(headsUpEntry) == 0 && this.nextList.size() == 1) {
                BaseHeadsUpManager.HeadsUpEntry headsUpEntry2 = this.headsUpEntryShowing;
                headsUpEntry2.getClass();
                headsUpEntry2.updateEntry(false, "avalanche duration update");
            }
        }
        logState("after " + str2);
    }
}
