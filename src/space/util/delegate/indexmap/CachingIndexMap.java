package space.util.delegate.indexmap;

import space.util.baseobject.Copyable;
import space.util.baseobject.ToString;
import space.util.baseobject.additional.Cache;
import space.util.indexmap.IndexMap;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

import java.util.function.IntFunction;

import static space.util.delegate.util.CacheUtil.*;

/**
 * {@link CachingIndexMap} is threadsafe, if the internal {@link CachingIndexMap#indexMap} is threadsafe.
 */
public class CachingIndexMap<VALUE> extends DefaultingIndexMap<VALUE> implements ToString, Cache {
	
	static {
		//noinspection unchecked
		Copyable.manualEntry(CachingIndexMap.class, d -> new CachingIndexMap(Copyable.copy(d.indexMap), d.def, d.iterateOverDef));
	}
	
	//no def iteration
	public CachingIndexMap(IndexMap<VALUE> indexMap, IntFunction<VALUE> def) {
		super(indexMap, def);
	}
	
	public CachingIndexMap(IndexMap<VALUE> indexMap, DefaultFunction<VALUE> def) {
		super(indexMap, def);
	}
	
	//with def iteration
	public CachingIndexMap(IndexMap<VALUE> indexMap, DefaultFunctionWithIteration<VALUE> def) {
		super(indexMap, def);
	}
	
	public CachingIndexMap(IndexMap<VALUE> indexMap, IndexMap<VALUE> def) {
		super(indexMap, def);
	}
	
	//with boolean
	public CachingIndexMap(IndexMap<VALUE> indexMap, IndexMap<VALUE> def, boolean iterateOverDef) {
		super(indexMap, def, iterateOverDef);
	}
	
	public CachingIndexMap(IndexMap<VALUE> indexMap, DefaultFunction<VALUE> def, boolean iterateOverDef) {
		super(indexMap, def, iterateOverDef);
	}
	
	//get
	@Override
	public VALUE get(int index) {
		VALUE thisV = indexMap.get(index);
		if (thisV != null)
			return fromNullToObject(thisV);
		
		VALUE newV = def.get(index);
		put(index, fromObjectToNull(newV));
		return newV;
	}
	
	@Override
	public void clearCache() {
		indexMap.clear();
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("indexMap", this.indexMap);
		tsh.add("def", this.def);
		tsh.add("iterateOverDef", this.iterateOverDef);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}
