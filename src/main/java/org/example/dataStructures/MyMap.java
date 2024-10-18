package org.example.dataStructures;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MyMap<K,V>  implements Map<K,V> {


    private final ArrayList<K> keys;
    private final ArrayList<V> values;

    public MyMap(){
        this.keys = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean isEmpty() {
        return keys.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return keys.contains((K) key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values.contains((V) value);
    }

    @Override
    public V get(Object key) {
        if(keys.contains((K) key)){
            return values.get(keys.indexOf((K) key));
        }
        else{
            return null;
        }

    }

    @Override
    public V put(K key, V value){
        if(!keys.contains(key)){
            this.keys.add(key);
            this.values.add(value);
            return null;
        }
        else{
            V prev = values.get(keys.indexOf((K) key));
            this.values.set(keys.indexOf((K) key), value);
            return prev;
        }

    }

    @Override
    public V remove(Object key) {
        V prev = values.get(keys.indexOf((K) key));
        values.remove(keys.indexOf((K) key));
        keys.remove((K) key);
        return prev;
    }

    @Override
    public void clear() {
        values.clear();
        keys.clear();
    }

    @Override
    public List<V> values() {
        return values;
    }

    public List<K> keys(){
        return keys;
    }


// Placeholders

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }



    @Override
    public Set<Entry<K, V>> entrySet() {
        return Set.of();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return Map.super.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        Map.super.forEach(action);
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Map.super.replaceAll(function);
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return Map.super.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return Map.super.remove(key, value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return Map.super.replace(key, oldValue, newValue);
    }

    @Override
    public V replace(K key, V value) {
        return Map.super.replace(key, value);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return Map.super.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return Map.super.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return Map.super.compute(key, remappingFunction);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return Map.super.merge(key, value, remappingFunction);
    }

}
