package com.android.systemui.qs.tileimpl;

import android.util.Log;
import com.android.systemui.qs.QSFactory;
import com.android.systemui.qs.QSTile;
import java.util.Map;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes.dex */
public class QSFactoryImpl implements QSFactory {
    protected final Map mTileMap;

    public QSFactoryImpl(Map map) {
        this.mTileMap = map;
    }

    @Override // com.android.systemui.qs.QSFactory
    public final QSTile createTile(String str) {
        QSTileImpl qSTileImplCreateTileInternal = createTileInternal(str);
        if (qSTileImplCreateTileInternal != null) {
            qSTileImplCreateTileInternal.initialize();
            qSTileImplCreateTileInternal.postStale();
            qSTileImplCreateTileInternal.setTileSpec(str);
        }
        return qSTileImplCreateTileInternal;
    }

    protected QSTileImpl createTileInternal(String str) {
        if (this.mTileMap.containsKey(str)) {
            return (QSTileImpl) ((Provider) this.mTileMap.get(str)).get();
        }
        Log.w("QSFactory", "No stock tile spec: " + str);
        return null;
    }
}
