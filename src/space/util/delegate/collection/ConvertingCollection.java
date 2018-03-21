package space.util.delegate.collection;

import space.util.baseobject.ToString;
import space.util.delegate.iterator.ConvertingIterator;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@SuppressWarnings("unused")
public abstract class ConvertingCollection<F, T> implements Collection<T>, ToString {
	
	public Collection<F> coll;
	
	protected ConvertingCollection(Collection<F> coll) {
		this.coll = coll;
	}
	
	@Override
	@SuppressWarnings("TypeParameterHidesVisibleType")
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("coll", this.coll);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
	
	public static <F, T> OneDirectionalUnmodifiable<F, T> createConvertingOneDirectionalUnmodifiable(Collection<F> coll, Function<F, T> remap) {
		return new OneDirectionalUnmodifiable<>(coll, remap);
	}
	
	public static <F, T> BiDirectionalUnmodifiable<F, T> createConvertingBiDirectionalUnmodifiable(Collection<F> coll, Function<F, T> remap, Function<T, F> reverse) {
		return new BiDirectionalUnmodifiable<>(coll, remap, reverse);
	}
	
	public static <F, T> BiDirectionalSparse<F, T> createConvertingBiDirectionalSparse(Collection<F> coll, Function<F, T> remap, Function<T, F> reverseAdd) {
		return new BiDirectionalSparse<>(coll, remap, reverseAdd);
	}
	
	public static <F, T> BiDirectional<F, T> createConvertingBiDirectional(Collection<F> coll, Function<F, T> remap, Function<T, F> reverse) {
		return new BiDirectional<>(coll, remap, reverse);
	}
	
	public static <F, T> BiDirectional<F, T> createConvertingBiDirectional(Collection<F> coll, Function<F, T> remap, Function<T, F> reverse, Function<T, F> reverseAdd) {
		return new BiDirectional<>(coll, remap, reverse, reverseAdd);
	}
	
	public static class OneDirectionalUnmodifiable<F, T> extends ConvertingCollection<F, T> {
		
		public Function<F, T> remap;
		
		public OneDirectionalUnmodifiable(Collection<F> coll, Function<F, T> remap) {
			super(coll);
			this.remap = remap;
		}
		
		//access methods
		@Override
		public int size() {
			return coll.size();
		}
		
		@Override
		public boolean isEmpty() {
			return coll.isEmpty();
		}
		
		@Override
		public boolean contains(Object o) {
			for (F f : coll)
				if (Objects.equals(remap.apply(f), o))
					return true;
			return false;
		}
		
		@Override
		public Iterator<T> iterator() {
			return ConvertingIterator.createConverterOneDirectionalUnmodifiable(coll.iterator(), remap);
		}
		
		@Override
		public Object[] toArray() {
			//noinspection unchecked
			F[] org = (F[]) coll.toArray();
			Object[] ret = new Object[org.length];
			for (int i = 0; i < org.length; i++)
				ret[i] = remap.apply(org[i]);
			return ret;
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public <T1> T1[] toArray(T1[] a) {
			F[] org = (F[]) coll.toArray();
			if (a.length < org.length) {
				//new instance
				T1[] ret = (T1[]) Array.newInstance(a.getClass(), org.length);
				for (int i = 0; i < org.length; i++)
					ret[i] = (T1) remap.apply(org[i]);
				return ret;
			}
			
			//use existing
			for (int i = 0; i < org.length; i++)
				a[i] = (T1) remap.apply(org[i]);
			a[org.length] = null;
			return a;
		}
		
		//modify methods
		@Override
		public boolean add(T t) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public boolean containsAll(Collection<?> c) {
			outer:
			for (F f : coll) {
				T t = remap.apply(f);
				for (Object o : c)
					if (Objects.equals(t, o))
						continue outer;
				return false;
			}
			return true;
		}
		
		@Override
		public boolean addAll(Collection<? extends T> c) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public boolean removeIf(Predicate<? super T> filter) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public void clear() {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		
		@Override
		public void forEach(Consumer<? super T> action) {
			coll.forEach(f -> action.accept(remap.apply(f)));
		}
		
		@Override
		@SuppressWarnings("TypeParameterHidesVisibleType")
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("coll", this.coll);
			tsh.add("remap", this.remap);
			return tsh.build();
		}
	}
	
	public static class BiDirectionalUnmodifiable<F, T> extends OneDirectionalUnmodifiable<F, T> {
		
		public Function<T, F> reverse;
		
		public BiDirectionalUnmodifiable(Collection<F> coll, Function<F, T> remap, Function<T, F> reverse) {
			super(coll, remap);
			this.reverse = reverse;
		}
		
		//access methods
		@Override
		@SuppressWarnings("unchecked")
		public boolean contains(Object o) {
			return coll.contains(reverse.apply((T) o));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean containsAll(Collection<?> c) {
			return coll.containsAll(createConvertingOneDirectionalUnmodifiable((Collection<T>) c, reverse));
		}
		
		@Override
		@SuppressWarnings("TypeParameterHidesVisibleType")
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("coll", this.coll);
			tsh.add("remap", this.remap);
			tsh.add("reverse", this.reverse);
			return tsh.build();
		}
	}
	
	public static class BiDirectionalSparse<F, T> extends OneDirectionalUnmodifiable<F, T> {
		
		public Function<T, F> reverseAdd;
		
		public BiDirectionalSparse(Collection<F> coll, Function<F, T> remap, Function<T, F> reverseAdd) {
			super(coll, remap);
			this.reverseAdd = reverseAdd;
		}
		
		//modify methods
		@Override
		public boolean add(T t) {
			return coll.add(reverseAdd.apply(t));
		}
		
		@Override
		public boolean remove(Object o) {
			Iterator<F> iter = coll.iterator();
			while (iter.hasNext()) {
				if (Objects.equals(remap.apply(iter.next()), o)) {
					iter.remove();
					return true;
				}
			}
			return false;
		}
		
		@Override
		public boolean addAll(Collection<? extends T> c) {
			ArrayList<F> list = new ArrayList<>(c.size());
			c.forEach(o -> list.add(reverseAdd.apply(o)));
			return coll.addAll(list);
		}
		
		@Override
		public boolean removeAll(Collection<?> c) {
			boolean mod = false;
			Iterator<F> iter = coll.iterator();
			while (iter.hasNext()) {
				T t = remap.apply(iter.next());
				if (c.contains(t)) {
					iter.remove();
					mod = true;
				}
			}
			return mod;
		}
		
		@Override
		public Iterator<T> iterator() {
			return ConvertingIterator.createConverterOneDirectional(coll.iterator(), remap);
		}
		
		@Override
		public boolean retainAll(Collection<?> c) {
			boolean mod = false;
			Iterator<F> iter = coll.iterator();
			while (iter.hasNext()) {
				T t = remap.apply(iter.next());
				if (!c.contains(t)) {
					iter.remove();
					mod = true;
				}
			}
			return mod;
		}
		
		@Override
		public void clear() {
			super.clear();
		}
		
		@Override
		public boolean removeIf(Predicate<? super T> filter) {
			return coll.removeIf(f -> filter.test(remap.apply(f)));
		}
		
		@Override
		@SuppressWarnings("TypeParameterHidesVisibleType")
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("coll", this.coll);
			tsh.add("remap", this.remap);
			tsh.add("reverseAdd", this.reverseAdd);
			return tsh.build();
		}
	}
	
	public static class BiDirectional<F, T> extends BiDirectionalSparse<F, T> {
		
		public Function<T, F> reverse;
		
		public BiDirectional(Collection<F> coll, Function<F, T> remap, Function<T, F> reverse) {
			this(coll, remap, reverse, reverse);
		}
		
		public BiDirectional(Collection<F> coll, Function<F, T> remap, Function<T, F> reverse, Function<T, F> reverseAdd) {
			super(coll, remap, reverseAdd);
			this.reverse = reverse;
		}
		
		//modify methods
		@Override
		@SuppressWarnings("unchecked")
		public boolean remove(Object o) {
			return coll.remove(reverse.apply((T) o));
		}        //access methods
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean removeAll(Collection<?> c) {
			return coll.removeAll(createConvertingOneDirectionalUnmodifiable((Collection<T>) c, reverse));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean retainAll(Collection<?> c) {
			return coll.retainAll(createConvertingOneDirectionalUnmodifiable((Collection<T>) c, reverse));
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean contains(Object o) {
			return coll.contains(reverse.apply((T) o));
		}
		
		@Override
		@SuppressWarnings("TypeParameterHidesVisibleType")
		public <T> T toTSH(ToStringHelper<T> api) {
			ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
			tsh.add("coll", this.coll);
			tsh.add("remap", this.remap);
			tsh.add("reverseAdd", this.reverseAdd);
			tsh.add("reverse", this.reverse);
			return tsh.build();
		}
		
		@Override
		@SuppressWarnings("unchecked")
		public boolean containsAll(Collection<?> c) {
			return coll.containsAll(createConvertingOneDirectionalUnmodifiable((Collection<T>) c, reverse));
		}
	}
}
