package org.darcy.sanguo.pojo;

import java.util.LinkedHashMap;

public class SimpleMap<K, V> extends LinkedHashMap<K, V> {
    private static final long serialVersionUID = 1L;

    public SimpleMap() {
    }

    public SimpleMap(K paramK, V paramV) {
        put(paramK, paramV);
    }
}