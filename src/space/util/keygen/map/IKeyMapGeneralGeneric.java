package space.util.keygen.map;

import space.util.keygen.IKey;

import java.util.function.Supplier;

/**
 * every {@link IKey} is getting a VALUE, <b>INDEPENDENT</b> on the generic of the {@link IKey}
 */
public interface IKeyMapGeneralGeneric<VALUE> extends IKeyMap<VALUE> {
	
	//methods
	boolean contains(IKey<?> key);
	
	VALUE get(IKey<?> key);
	
	VALUE put(IKey<?> key, VALUE v);
	
	VALUE remove(IKey<?> key);
	
	VALUE getOrDefault(IKey<?> key, VALUE def);
	
	VALUE putIfAbsent(IKey<?> key, VALUE v);
	
	VALUE putIfAbsent(IKey<?> key, Supplier<? extends VALUE> v);
	
	VALUE replace(IKey<?> key, VALUE newValue);
	
	boolean replace(IKey<?> key, VALUE oldValue, VALUE newValue);
	
	boolean replace(IKey<?> key, VALUE oldValue, Supplier<? extends VALUE> newValue);
	
	boolean remove(IKey<?> key, VALUE v);
}