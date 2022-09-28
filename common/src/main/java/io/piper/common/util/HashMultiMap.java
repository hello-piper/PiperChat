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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HashMultiMap
 *
 * @author piper
 */
public final class HashMultiMap<K, V> {
    private final ConcurrentHashMap<K, Set<V>> map;
    private final AtomicInteger size = new AtomicInteger(0);

    public HashMultiMap() {
        map = new ConcurrentHashMap<>(100000);
    }

    public HashMultiMap(int expectedKeys) {
        map = new ConcurrentHashMap<>(expectedKeys);
    }

    public boolean put(K key, V value) {
        this.map.compute(key, (k, vs) -> {
            if (vs == null)
                vs = ConcurrentHashMap.newKeySet(4);
            vs.add(value);
            return vs;
        });
        size.getAndIncrement();
        return true;
    }

    public Set<V> get(K k) {
        return map.get(k);
    }

    public boolean remove(K key, V value) {
        Set<V> set = map.get(key);
        boolean isRemove = set != null && set.remove(value);
        if (isRemove) {
            size.decrementAndGet();
        }
        return isRemove;
    }

    public Set<V> removeKey(K key) {
        Set<V> vs = map.get(key);
        if (null != vs) {
            map.remove(key);
            size.getAndAdd(-vs.size());
            return vs;
        }
        return null;
    }

    public boolean removeValue(V value) {
        for (Set<V> set : map.values()) {
            if (set.remove(value)) {
                size.decrementAndGet();
                return true;
            }
        }
        return false;
    }

    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    public boolean contains(K key, V value) {
        Set<V> collection = map.get(key);
        return collection != null && collection.contains(value);
    }

    public int keySize() {
        return map.size();
    }

    public int valueSize() {
        return this.size.get();
    }

    public Collection<Set<V>> values() {
        return map.values();
    }
}
