package dagger.internal;

import dagger.Lazy;
import dagger.internal.AbstractMapFactory;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public final class MapProviderFactory extends AbstractMapFactory implements Lazy {

    public final class Builder extends AbstractMapFactory.Builder {
        private Builder(int i) {
            super(i);
        }

        public MapProviderFactory build() {
            return new MapProviderFactory(this.map);
        }

        @Override // dagger.internal.AbstractMapFactory.Builder
        public Builder put(Object obj, Provider provider) {
            super.put(obj, provider);
            return this;
        }
    }

    private MapProviderFactory(Map map) {
        super(map);
    }

    public static Builder builder(int i) {
        return new Builder(i);
    }

    @Override // javax.inject.Provider
    public Map get() {
        return contributingMap();
    }
}
