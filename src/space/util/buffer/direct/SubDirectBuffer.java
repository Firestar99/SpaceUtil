package space.util.buffer.direct;

import space.util.freeableStorage.IFreeableStorage;
import space.util.string.toStringHelper.ToStringHelper;
import space.util.string.toStringHelper.ToStringHelper.ToStringHelperObjectsInstance;

public class SubDirectBuffer extends NotFreeableDirectBuffer {
	
	public Object parent;
	
	public SubDirectBuffer(long address, long capacity, Object parent, IFreeableStorage... lists) {
		super(address, capacity, lists);
		this.parent = parent;
	}
	
	@Override
	public synchronized void free() {
		super.free();
		parent = null;
	}
	
	@Override
	public <T> T toTSH(ToStringHelper<T> api) {
		ToStringHelperObjectsInstance<T> tsh = api.createObjectInstance(this);
		tsh.add("isFreed", this.storage.isFreed());
		tsh.add("parent", this.parent);
		tsh.add("address", this.storage.address);
		tsh.add("capacity", this.storage.capacity);
		return tsh.build();
	}
	
	@Override
	public String toString() {
		return toString0();
	}
}