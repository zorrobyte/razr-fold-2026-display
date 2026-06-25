package com.android.systemui.qs;

import android.content.Context;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes.dex */
public class QSTileHost implements QSHost {
    private final Context mContext;
    private int mCurrentUser;
    private final Executor mMainExecutor;
    private final ArrayList mQsFactories;
    private Context mUserContext;
    private final UserTracker mUserTracker;
    UserTracker.Callback mUserTrackerCallback;
    private final LinkedHashMap mTiles = new LinkedHashMap();
    private final ArrayList mTileSpecs = new ArrayList();
    private final List mCallbacks = new ArrayList();

    /* JADX INFO: renamed from: $r8$lambda$6DEt9pwDYuvvgqYcBJ9qV8-eyLM, reason: not valid java name */
    public static /* synthetic */ boolean m1593$r8$lambda$6DEt9pwDYuvvgqYcBJ9qV8eyLM(List list, Map.Entry entry) {
        return !list.contains(entry.getKey());
    }

    public static /* synthetic */ void $r8$lambda$857_OfJqSsXM4vKknawZMgffjek(Map.Entry entry) {
        Log.d("QSTileHost", "Destroying tile: " + ((String) entry.getKey()));
        ((QSTile) entry.getValue()).destroy();
    }

    public QSTileHost(Context context, QSFactory qSFactory, Executor executor, UserTracker userTracker) {
        ArrayList arrayList = new ArrayList();
        this.mQsFactories = arrayList;
        this.mUserTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.qs.QSTileHost.2
            @Override // com.android.systemui.settings.UserTracker.Callback
            public void onUserChanged(int i, Context context2) {
                QSTileHost.this.loadTiles();
            }
        };
        this.mContext = context;
        this.mUserContext = context;
        this.mMainExecutor = executor;
        arrayList.add(qSFactory);
        this.mUserTracker = userTracker;
        this.mCurrentUser = userTracker.getUserId();
        userTracker.addCallback(this.mUserTrackerCallback, executor);
        executor.execute(new Runnable() { // from class: com.android.systemui.qs.QSTileHost.1
            @Override // java.lang.Runnable
            public void run() {
                QSTileHost.this.loadTiles();
            }
        });
    }

    protected static List loadTileSpecs(Context context) {
        return QSHost.getDefaultSpecs(context.getResources());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadTiles() {
        final List<String> listLoadTileSpecs = loadTileSpecs(this.mContext);
        int userId = this.mUserTracker.getUserId();
        Log.d("QSTileHost", "Recreating tiles: " + listLoadTileSpecs);
        this.mTiles.entrySet().stream().filter(new Predicate() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return QSTileHost.m1593$r8$lambda$6DEt9pwDYuvvgqYcBJ9qV8eyLM(listLoadTileSpecs, (Map.Entry) obj);
            }
        }).forEach(new Consumer() { // from class: com.android.systemui.qs.QSTileHost$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                QSTileHost.$r8$lambda$857_OfJqSsXM4vKknawZMgffjek((Map.Entry) obj);
            }
        });
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (String str : listLoadTileSpecs) {
            QSTile qSTile = (QSTile) this.mTiles.get(str);
            if (qSTile == null) {
                Log.d("QSTileHost", "Creating tile: " + str);
                try {
                    QSTile qSTileCreateTile = createTile(str);
                    if (qSTileCreateTile == null) {
                        Log.d("QSTileHost", "No factory for a spec: " + str);
                    } else if (qSTileCreateTile.isAvailable()) {
                        linkedHashMap.put(str, qSTileCreateTile);
                    } else {
                        qSTileCreateTile.destroy();
                        Log.d("QSTileHost", "Destroying not available tile: " + str);
                    }
                } catch (Throwable th) {
                    Log.w("QSTileHost", "Error creating tile for spec: " + str, th);
                }
            } else if (qSTile.isAvailable()) {
                Log.d("QSTileHost", "Adding " + qSTile);
                qSTile.removeCallbacks();
                if (this.mCurrentUser != userId) {
                    qSTile.userSwitch(userId);
                }
                linkedHashMap.put(str, qSTile);
            } else {
                qSTile.destroy();
                Log.d("QSTileHost", "Destroying not available tile: " + str);
            }
        }
        this.mCurrentUser = userId;
        this.mTileSpecs.clear();
        this.mTileSpecs.addAll(linkedHashMap.keySet());
        this.mTiles.clear();
        this.mTiles.putAll(linkedHashMap);
        if ((!linkedHashMap.isEmpty() || listLoadTileSpecs.isEmpty()) && this.mCallbacks.size() > 0) {
            ActivityResultRegistry$$ExternalSyntheticThrowCCEIfNotNull0.m(this.mCallbacks.get(0));
            throw null;
        }
    }

    public QSTile createTile(String str) {
        for (int i = 0; i < this.mQsFactories.size(); i++) {
            QSTile qSTileCreateTile = ((QSFactory) this.mQsFactories.get(i)).createTile(str);
            if (qSTileCreateTile != null) {
                return qSTileCreateTile;
            }
        }
        return null;
    }

    @Override // com.android.systemui.qs.QSHost
    public Context getContext() {
        return this.mContext;
    }

    @Override // com.android.systemui.qs.QSHost
    public Collection getTiles() {
        return this.mTiles.values();
    }
}
