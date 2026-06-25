package androidx.media3.datasource;

import android.content.Context;
import androidx.media3.common.util.Assertions;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class DefaultDataSource$Factory implements DataSource$Factory {
    private final DataSource$Factory baseDataSourceFactory;
    private final Context context;

    public DefaultDataSource$Factory(Context context) {
        this(context, new DataSource$Factory() { // from class: androidx.media3.datasource.DefaultHttpDataSource$Factory
            private final HttpDataSource$RequestProperties defaultRequestProperties = new Object() { // from class: androidx.media3.datasource.HttpDataSource$RequestProperties
                private final Map requestProperties = new HashMap();
            };
            private int connectTimeoutMs = 8000;
            private int readTimeoutMs = 8000;
        });
    }

    public DefaultDataSource$Factory(Context context, DataSource$Factory dataSource$Factory) {
        this.context = context.getApplicationContext();
        this.baseDataSourceFactory = (DataSource$Factory) Assertions.checkNotNull(dataSource$Factory);
    }
}
