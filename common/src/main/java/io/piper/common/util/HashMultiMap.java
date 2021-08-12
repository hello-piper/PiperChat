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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMultiMap
 *
 * @author piper
 */
public final class HashMultiMap<K, V> {
    private static final long serialVersionUID = 0L;
    private static final int DEFAULT_VALUES_PER_KEY = 2;
    private int expectedValuesPerKey;
    private Map<K, Set<V>> map = null;

    public static <K, V> HashMultiMap<K, V> create() {
        return new HashMultiMap();
    }

    public static <K, V> HashMultiMap<K, V> create(int expectedKeys) {
        return new HashMultiMap(expectedKeys, DEFAULT_VALUES_PER_KEY);
    }

    public static <K, V> HashMultiMap<K, V> create(int expectedKeys, int expectedValuesPerKey) {
        return new HashMultiMap(expectedKeys, expectedValuesPerKey);
    }

    public static <K, V> HashMultiMap<K, V> createCont(int expectedKey) {
        return new HashMultiMap(expectedKey, DEFAULT_VALUES_PER_KEY, true);
    }

    public static <K, V> HashMultiMap<K, V> createCont(int expectedKeys, int expectedValuesPerKey) {
        return new HashMultiMap(expectedKeys, expectedValuesPerKey, true);
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
        map.computeIfAbsent(key, v -> new HashSet<>(expectedValuesPerKey)).add(value);
        Set<V> vs = map.get(key);
        if (null != vs) {
            vs.add(value);
        } else {
            HashSet<V> newSet = new HashSet<>(expectedValuesPerKey);
            newSet.add(value);
            map.put(key, newSet);
        }
        return true;
    }

    public Set<V> get(K k) {
        return map.get(k);
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

    public boolean remove(K key, V value) {
        Set<V> collection = map.get(key);
        return collection != null && collection.remove(value);
    }

    public Set<V> removeAll(K key) {
        Set<V> vs = map.get(key);
        if (null != vs) {
            vs.clear();
            map.remove(key);
            return vs;
        }
        return null;
    }

    public int size() {
        return map.size();
    }

}
