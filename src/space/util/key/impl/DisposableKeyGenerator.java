package space.util.key.impl;

import space.util.baseobject.ToString;
import space.util.delegate.indexmap.ReferenceIndexMap;
import space.util.delegate.list.IntList;
import space.util.indexmap.IndexMap;
import space.util.indexmap.IndexMapArray;
import space.util.key.IKey;
import space.util.key.IKeyGenerator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.function.Supplier;

public class DisposableKeyGenerator implements IKeyGenerator, ToString {
	
	public int counter;
	public IntList disposed;
	public IndexMap<IKey<?>> allKeys = new ReferenceIndexMap<>(new IndexMapArray<>(), WeakReference::new);
	
	public DisposableKeyGenerator(boolean allowReuse) {
		if (allowReuse)
			disposed = new IntList(0);
	}
	
	//generate
	@Override
	public synchronized <T> DisposableKey<T> generateKey() {
		return generateKey(() -> null);
	}
	
	@Override
	public synchronized <T> DisposableKey<T> generateKey(Supplier<T> def) {
		return new DisposableKey<>(disposed != null && !disposed.isEmpty() ? disposed.poll() : counter++, this, def);
	}
	
	//key
	@Override
	public synchronized IKey<?> getKey(int id) {
		return allKeys.get(id);
	}
	
	@Override
	public boolean isKeyOf(IKey<?> key) {
		return key instanceof DisposableKey && ((DisposableKey) key).storage.gen == this;
	}
	
	@Override
	public Collection<IKey<?>> getKeys() {
		return allKeys.values();
	}
	
	//dispose
	protected synchronized void dispose(int id) {
		allKeys.remove(id);
		if (disposed != null)
			disposed.add(id);
	}
	
	//toString
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("counter", this.counter);
		tsh.add("disposed", this.disposed == null ? "disabled" : Integer.toString(this.disposed.size()) + " Entries");
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}