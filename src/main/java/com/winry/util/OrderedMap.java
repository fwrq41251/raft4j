package com.winry.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderedMap<K, V> {

	private Map<K, V> map = new HashMap<>();

	private List<V> list = new ArrayList<>();

	public V put(K key, V value) {
		V result = map.put(key, value);
		list.add(result);
		return result;
	}

	public V remove(K key) {
		V result = map.remove(key);
		list.remove(result);
		return result;
	}

	public V get(K key) {
		return map.get(key);
	}

	public List<V> getList() {
		return list;
	}

}
