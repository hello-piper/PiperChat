/*
 * Copyright (c) 2020-2030 The Piper(https://github.com/hello-piper)
 *
 * The PiperChat is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 *
 * http://license.coscl.org.cn/MulanPSL2
 *
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT, MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 */
package io.piper.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HashMultiMap
 *
 * @author piper
 */
public final class HashMultiMap<K, V> {
    private final Map<K, Set<V>> map;
    private final int expectedValuesPerKey;
    private static final int DEFAULT_VALUES_PER_KEY = 2;
    private final AtomicInteger valueSize = new AtomicInteger(0);

    public static <K, V> HashMultiMap<K, V> create() {
        return new HashMultiMap<>();
    }

    public static <K, V> HashMultiMap<K, V> create(int expectedKeys) {
        return new HashMultiMap<>(expectedKeys, DEFAULT_VALUES_PER_KEY);
    }

    public static <K, V> HashMultiMap<K, V> create(int expectedKeys, int expectedValuesPerKey) {
        return new HashMultiMap<>(expectedKeys, expectedValuesPerKey);
    }

    public static <K, V> HashMultiMap<K, V> createCont(int expectedKey) {
        return new HashMultiMap<>(expectedKey, DEFAULT_VALUES_PER_KEY, true);
    }

    public static <K, V> HashMultiMap<K, V> createCont(int expectedKeys, int expectedValuesPerKey) {
        return new HashMultiMap<>(expectedKeys, expectedValuesPerKey, true);
    }

    private HashMultiMap() {
        this(12, DEFAULT_VALUES_PER_KEY);
    }

    private HashMultiMap(int expectedKeys, int expectedValuesPerKey) {
        this.expectedValuesPerKey = expectedValuesPerKey;
        this.map = new HashMap<>(expectedKeys);
    }

    private HashMultiMap(int expectedKeys, int expectedValuesPerKey, boolean concurrent) {
        this.expectedValuesPerKey = expectedValuesPerKey;
        if (concurrent) {
            map = new ConcurrentHashMap<>(expectedKeys);
        } else {
            map = new HashMap<>(expectedKeys);
        }
    }

    public boolean put(K key, V value) {
        this.map.compute(key, (k, vs) -> {
            if (vs == null)
                vs = ConcurrentHashMap.newKeySet(4);
            vs.add(value);
            return vs;
        });
        valueSize.getAndIncrement();
        return true;
    }

    public Set<V> get(K k) {
        return map.get(k);
    }

    public boolean remove(K key, V value) {
        Set<V> set = map.get(key);
        boolean isRemove = set != null && set.remove(value);
        if (isRemove) {
            valueSize.decrementAndGet();
        }
        return isRemove;
    }

    public Set<V> removeKey(K key) {
        Set<V> vs = map.remove(key);
        if (vs != null && vs.size() > 0) {
            valueSize.getAndAdd(-vs.size());
            return vs;
        }
        return null;
    }

    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    public boolean containsValue(V value) {
        Iterator<Set<V>> iterator = map.values().iterator();
        for (Set<V> set : map.values()) {
            if (set.contains(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsEntry(K key, V value) {
        Set<V> collection = map.get(key);
        return collection != null && collection.contains(value);
    }

    public int keySize() {
        return map.size();
    }

    public int valueSize() {
        return this.valueSize.get();
    }
}
