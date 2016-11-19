package org.throwable.trace.core.concurrency.map;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author zhangjinci
 * @version 2016/11/19 11:36
 * @function 使用读写锁包装线程安全的Map
 * e.g Map<String,String>  map = new ReadWriteMap<K, V>(new LinkedHashMap<>());
 * 可以为LinkedHashMap提供并发性
 */
public class ReadWriteMap<K, V> implements Map<K, V> {
    private final Map<K, V> map;

    //使用默认的非公平锁
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();

    public ReadWriteMap(Map<K, V> map) {
        this.map = map;
    }

    public V put(K key, V vaule) {
        writeLock.lock();
        try {
            return map.put(key, vaule);
        } finally {
            writeLock.unlock();
        }
    }

    public V get(Object key) {
        readLock.lock();
        try {
            return map.get(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public int size() {
        readLock.lock();
        try {
            return map.size();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        readLock.lock();
        try {
            return map.isEmpty();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        readLock.lock();
        try {
            return map.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsValue(Object value) {
        readLock.lock();
        try {
            return map.containsKey(value);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public V remove(Object key) {
        writeLock.lock();
        try {
            return map.remove(key);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        writeLock.lock();
        try {
            map.putAll(m);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void clear() {
        writeLock.lock();
        try {
            map.clear();
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Set<K> keySet() {
        readLock.lock();
        try {
            return map.keySet();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Collection<V> values() {
        readLock.lock();
        try {
            return map.values();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        readLock.lock();
        try {
            return map.entrySet();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        readLock.lock();
        try {
            return map.getOrDefault(key, defaultValue);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        readLock.lock();
        try {
            map.forEach(action);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        writeLock.lock();
        try {
            map.replaceAll(function);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        writeLock.lock();
        try {
            return map.putIfAbsent(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean remove(Object key, Object value) {
        writeLock.lock();
        try {
            return map.remove(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        writeLock.lock();
        try {
            return map.replace(key, oldValue, newValue);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public V replace(K key, V value) {
        writeLock.lock();
        try {
            return map.replace(key, value);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        writeLock.lock();
        try {
            return map.computeIfAbsent(key, mappingFunction);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        writeLock.lock();
        try {
            return map.computeIfPresent(key, remappingFunction);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        writeLock.lock();
        try {
            return map.compute(key, remappingFunction);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        writeLock.lock();
        try {
            return map.merge(key, value, remappingFunction);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public String toString() {
        return map.toString();
    }
}
