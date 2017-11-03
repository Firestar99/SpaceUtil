package space.util.delegate.indexmap;

import space.util.baseobject.BaseObject;
import space.util.baseobject.Copyable;
import space.util.delegate.iterator.Iteratorable;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;

import java.util.Collection;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SynchronizedIndexMap<VALUE> extends DelegatingIndexMap<VALUE> {
	
	static {
		//noinspection unchecked
		BaseObject.initClass(SynchronizedIndexMap.class, d -> new SynchronizedIndexMap(Copyable.copy(d.indexMap)));
	}
	
	public SynchronizedIndexMap(IndexMap<VALUE> indexMap) {
		super(indexMap);
	}
	
	@Override
	public synchronized boolean isExpandable() {
		return super.isExpandable();
	}
	
	@Override
	public synchronized int size() {
		return super.size();
	}
	
	@Override
	public synchronized boolean contains(int index) {
		return super.contains(index);
	}
	
	@Override
	public synchronized void add(VALUE v) {
		super.add(v);
	}
	
	@Override
	public synchronized int indexOf(VALUE v) {
		return super.indexOf(v);
	}
	
	@Override
	public synchronized VALUE[] toArray() {
		return super.toArray();
	}
	
	@Override
	public synchronized VALUE[] toArray(VALUE[] array) {
		return super.toArray(array);
	}
	
	@Override
	public synchronized VALUE get(int index) {
		return super.get(index);
	}
	
	@Override
	public synchronized VALUE put(int index, VALUE v) {
		return super.put(index, v);
	}
	
	@Override
	public synchronized VALUE remove(int index) {
		return super.remove(index);
	}
	
	@Override
	public synchronized void addAll(Collection<VALUE> coll) {
		super.addAll(coll);
	}
	
	@Override
	public synchronized void putAll(IndexMap<VALUE> indexMap) {
		super.putAll(indexMap);
	}
	
	@Override
	public synchronized void putAllReplace(IndexMap<VALUE> indexMap) {
		super.putAllReplace(indexMap);
	}
	
	@Override
	public synchronized void putAllIfAbsent(IndexMap<VALUE> indexMap) {
		super.putAllIfAbsent(indexMap);
	}
	
	@Override
	public synchronized VALUE getOrDefault(int index, VALUE def) {
		return super.getOrDefault(index, def);
	}
	
	@Override
	public synchronized VALUE putIfAbsent(int index, VALUE v) {
		return super.putIfAbsent(index, v);
	}
	
	@Override
	public synchronized VALUE putIfAbsent(int index, Supplier<? extends VALUE> v) {
		return super.putIfAbsent(index, v);
	}
	
	@Override
	public synchronized VALUE replace(int index, VALUE newValue) {
		return super.replace(index, newValue);
	}
	
	@Override
	public synchronized VALUE replace(int index, Supplier<? extends VALUE> newValue) {
		return super.replace(index, newValue);
	}
	
	@Override
	public synchronized boolean replace(int index, VALUE oldValue, VALUE newValue) {
		return super.replace(index, oldValue, newValue);
	}
	
	@Override
	public synchronized boolean replace(int index, VALUE oldValue, Supplier<? extends VALUE> newValue) {
		return super.replace(index, oldValue, newValue);
	}
	
	@Override
	public synchronized boolean remove(int index, VALUE v) {
		return super.remove(index, v);
	}
	
	@Override
	public synchronized void clear() {
		super.clear();
	}
	
	@Override
	public synchronized Iteratorable<VALUE> iterator() {
		return super.iterator();
	}
	
	@Override
	public synchronized Iteratorable<IndexMapEntry<VALUE>> tableIterator() {
		return super.tableIterator();
	}
	
	@Override
	public synchronized void forEach(Consumer<? super VALUE> action) {
		super.forEach(action);
	}
	
	@Override
	public synchronized Spliterator<VALUE> spliterator() {
		return super.spliterator();
	}
	
	//Object
	@Override
	public synchronized int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public synchronized boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		return api.createModifier("synchronized", indexMap);
	}
}