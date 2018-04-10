package space.util.delegate.map;

import space.util.baseobject.ToString;
import space.util.delegate.collection.ConvertingCollection;
import space.util.delegate.set.ConvertingSet;
import space.util.delegate.set.UnmodifiableSet;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class ConvertingMap<K, F, T> implements Map<K, T>, ToString {
	
	public Map<K, F> map;
	
	public ConvertingMap(Map<K, F> map) {
		this.map = map;
	}
	
	@Override
	public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
		ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
		tsh.add("map", this.map);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public static class OneDirectionalUnmodifiable<K, F, T> extends ConvertingMap<K, F, T> {
		
		public Function<? super F, ? extends T> remap;
		
		public OneDirectionalUnmodifiable(Map<K, F> map, Function<? super F, ? extends T> remap) {
			super(map);
			this.remap = remap;
		}
		
		@Override
		public int size() {
			return map.size();
		}
		
		@Override
		public boolean isEmpty() {
			return map.isEmpty();
		}
		
		@Override
		public boolean containsKey(Object key) {
			return get(key) != null;
		}
		
		@Override
		public boolean containsValue(Object value) {
			for (F f : map.values())
				if (Objects.equals(remap.apply(f), value))
					return true;
			return false;
		}
		
		@Override
		public T get(Object key) {
			return remap.apply(map.get(key));
		}
		
		@Override
		public T put(K key, T value) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public T remove(Object key) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public void putAll(Map<? extends K, ? extends T> m) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public void clear() {
			map.clear();
		}
		
		@Override
		public Set<K> keySet() {
			return new UnmodifiableSet<>(map.keySet());
		}
		
		@Override
		public Collection<T> values() {
			return new ConvertingCollection.OneDirectionalUnmodifiable<>(map.values(), remap);
		}
		
		@Override
		public Set<Map.Entry<K, T>> entrySet() {
			return new ConvertingSet.BiDirectionalUnmodifiable<>(map.entrySet(), entry -> entry == null ? null : new Entry(entry), entry -> entry instanceof OneDirectionalUnmodifiable.Entry ? ((Entry) entry).entry : null);
		}
		
		@Override
		@SuppressWarnings("SuspiciousMethodCalls")
		public T getOrDefault(Object key, T defaultValue) {
			T ret = remap.apply(map.get(key));
			return ret == null ? defaultValue : ret;
		}
		
		@Override
		public void forEach(BiConsumer<? super K, ? super T> action) {
			map.forEach((k, f) -> action.accept(k, remap.apply(f)));
		}
		
		@Override
		public void replaceAll(BiFunction<? super K, ? super T, ? extends T> function) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public T putIfAbsent(K key, T value) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public boolean remove(Object key, Object value) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public boolean replace(K key, T oldValue, T newValue) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public T replace(K key, T value) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public T computeIfAbsent(K key, Function<? super K, ? extends T> mappingFunction) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public T computeIfPresent(K key, BiFunction<? super K, ? super T, ? extends T> remappingFunction) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public T compute(K key, BiFunction<? super K, ? super T, ? extends T> remappingFunction) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public T merge(K key, T value, BiFunction<? super T, ? super T, ? extends T> remappingFunction) {
			throw new UnsupportedOperationException("unmodifiable");
		}
		
		@Override
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("map", this.map);
			tsh.add("remap", this.remap);
			return tsh.build();
		}
		
		public class Entry implements Map.Entry<K, T>, ToString {
			
			public Map.Entry<K, F> entry;
			
			public Entry(Map.Entry<K, F> entry) {
				this.entry = entry;
			}
			
			@Override
			public K getKey() {
				return entry.getKey();
			}
			
			@Override
			public T getValue() {
				return remap.apply(entry.getValue());
			}
			
			@Override
			public T setValue(T value) {
				throw new UnsupportedOperationException("unmodifiable");
			}
			
			@Override
			public int hashCode() {
				return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
			}
			
			@Override
			public boolean equals(Object obj) {
				if (this == obj)
					return true;
				if (!(obj instanceof Map.Entry))
					return false;
				Map.Entry other = (Map.Entry) obj;
				return Objects.equals(getKey(), other.getKey()) && Objects.equals(getValue(), other.getValue());
			}
			
			@Override
			public String toString() {
				return toString0();
			}
			
			@Override
			public <TSH> TSH toTSH(ToStringHelper<TSH> api) {
				ToStringHelperObjectsInstance<TSH> tsh = api.createObjectInstance(this);
				tsh.add("entry", this.entry);
				return tsh.build();
			}
		}
	}
	
	public static class BiDirectionalUnmodifiable<K, F, T> extends OneDirectionalUnmodifiable<K, F, T> {
		
		public Function<? super T, ? extends F> reverse;
		
		public BiDirectionalUnmodifiable(Map<K, F> map, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverse) {
			super(map, remap);
			this.reverse = reverse;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean containsValue(Object value) {
			return map.containsValue(reverse.apply((T) value));
		}
		
		@Override
		public Collection<T> values() {
			return new ConvertingCollection.BiDirectionalUnmodifiable<>(map.values(), remap, reverse);
		}
		
		@Override
		public <TSHTYPE> TSHTYPE toTSH(ToStringHelper<TSHTYPE> api) {
			ToStringHelperObjectsInstance<TSHTYPE> tsh = api.createObjectInstance(this);
			tsh.add("map", this.map);
			tsh.add("remap", this.remap);
			tsh.add("reverse", this.reverse);
			return tsh.build();
		}
	}
	
	public static class BiDirectionalSparse<K, F, T> extends OneDirectionalUnmodifiable<K, F, T> {
		
		public Function<? super T, ? extends F> reverseSparse;
		
		public BiDirectionalSparse(Map<K, F> map, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverseSparse) {
			super(map, remap);
			this.reverseSparse = reverseSparse;
		}
		
		@Override
		public T put(K key, T value) {
			return remap.apply(map.put(key, reverseSparse.apply(value)));
		}
		
		@Override
		public T remove(Object key) {
			return remap.apply(map.remove(key));
		}
		
		@Override
		public void putAll(Map<? extends K, ? extends T> m) {
			map.putAll(new ConvertingMap.OneDirectionalUnmodifiable<>(m, reverseSparse));
		}
		
		@Override
		public Set<K> keySet() {
			return map.keySet();
		}
		
		@Override
		public Collection<T> values() {
			return new ConvertingCollection.BiDirectionalSparse<>(map.values(), remap, reverseSparse);
		}
		
		@Override
		public Set<Map.Entry<K, T>> entrySet() {
			return new ConvertingSet.BiDirectional<Map.Entry<K, F>, Map.Entry<K, T>>(map.entrySet(), entry -> entry == null ? null : new Entry(entry), entry -> entry instanceof BiDirectionalSparse.Entry ? ((BiDirectionalSparse.Entry) entry).entry : null);
		}
		
		@Override
		public void replaceAll(BiFunction<? super K, ? super T, ? extends T> function) {
			map.replaceAll((k, f) -> {
				T t = remap.apply(f);
				T tRet = function.apply(k, t);
				return t == tRet ? f : reverseSparse.apply(tRet);
			});
		}
		
		@Override
		public T putIfAbsent(K key, T value) {
			return remap.apply(map.computeIfAbsent(key, k -> reverseSparse.apply(value)));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean remove(Object key, Object value) {
			boolean[] ret = new boolean[1];
			map.compute((K) key, (k, f) -> {
				if (!Objects.equals(remap.apply(f), value))
					return f;
				ret[0] = true;
				return null;
			});
			return ret[0];
		}
		
		@Override
		public boolean replace(K key, T oldValue, T newValue) {
			boolean[] ret = new boolean[1];
			map.compute(key, (k, f) -> {
				if (!Objects.equals(remap.apply(f), oldValue))
					return f;
				ret[0] = true;
				return reverseSparse.apply(newValue);
			});
			return ret[0];
		}
		
		@Override
		public T replace(K key, T value) {
			return remap.apply(map.computeIfPresent(key, (k, f) -> reverseSparse.apply(value)));
		}
		
		@Override
		public T computeIfAbsent(K key, Function<? super K, ? extends T> mappingFunction) {
			return remap.apply(map.computeIfAbsent(key, k -> reverseSparse.apply(mappingFunction.apply(k))));
		}
		
		@Override
		public T computeIfPresent(K key, BiFunction<? super K, ? super T, ? extends T> remappingFunction) {
			return remap.apply(map.computeIfPresent(key, (k, f) -> {
				T t = remap.apply(f);
				T ret = remappingFunction.apply(k, t);
				return ret == t ? f : reverseSparse.apply(ret);
			}));
		}
		
		@Override
		public T compute(K key, BiFunction<? super K, ? super T, ? extends T> remappingFunction) {
			return remap.apply(map.computeIfPresent(key, (k, f) -> {
				T t = remap.apply(f);
				T ret = remappingFunction.apply(k, t);
				return ret == t ? f : reverseSparse.apply(ret);
			}));
		}
		
		@Override
		public T merge(K key, T value, BiFunction<? super T, ? super T, ? extends T> remappingFunction) {
			return remap.apply(map.merge(key, reverseSparse.apply(value), (f1, f2) -> {
				T t1 = remap.apply(f1);
				T t2 = remap.apply(f2);
				T ret = remappingFunction.apply(t1, t2);
				if (ret == t1)
					return f1;
				if (ret == t2)
					return f2;
				return reverseSparse.apply(ret);
			}));
		}
		
		public class Entry extends OneDirectionalUnmodifiable<K, F, T>.Entry {
			
			public Entry(Map.Entry<K, F> entry) {
				super(entry);
			}
			
			@Override
			public T setValue(T value) {
				return remap.apply(entry.setValue(reverseSparse.apply(value)));
			}
		}
	}
	
	public static class BiDirectional<K, F, T> extends BiDirectionalSparse<K, F, T> {
		
		public Function<? super T, ? extends F> reverse;
		
		public BiDirectional(Map<K, F> map, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverse) {
			this(map, remap, reverse, reverse);
		}
		
		public BiDirectional(Map<K, F> map, Function<? super F, ? extends T> remap, Function<? super T, ? extends F> reverseSparse, Function<? super T, ? extends F> reverse) {
			super(map, remap, reverseSparse);
			this.reverse = reverse;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean containsValue(Object value) {
			return map.containsValue(reverse.apply((T) value));
		}
		
		@Override
		public Collection<T> values() {
			return new ConvertingCollection.BiDirectional<>(map.values(), remap, reverse);
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean remove(Object key, Object value) {
			return map.remove(key, reverse.apply((T) value));
		}
	}
}