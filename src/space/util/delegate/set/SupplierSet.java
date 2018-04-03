package space.util.delegate.set;

import space.util.delegate.collection.SupplierCollection;

import java.util.Set;
import java.util.function.Supplier;

public class SupplierSet<E> extends SupplierCollection<E> implements Set<E> {
	
	public SupplierSet(Supplier<? extends Set<E>> coll) {
		super(coll);
	}
}
