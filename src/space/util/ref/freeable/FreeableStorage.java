package space.util.ref.freeable;

import space.util.baseobject.exceptions.FreedException;
import space.util.ref.freeable.IFreeableStorageList.Entry;

import java.lang.ref.PhantomReference;

public abstract class FreeableStorage extends PhantomReference<Object> implements IFreeableStorage {
	
	public static FreeableStorage createAnonymous(IFreeableStorage... lists) {
		return new FreeableStorage(null, lists) {
			@Override
			protected void handleFree() {
			
			}
		};
	}
	
	private volatile boolean isFreed = false;
	private final IFreeableStorageList.Entry[] entries;
	private final int freePriority;
	private IFreeableStorageList subList;
	
	public FreeableStorage(Object referent, IFreeableStorage... lists) {
		super(referent, FreeableStorageCleaner.QUEUE);
		
		int freePriority = Integer.MIN_VALUE;
		entries = new IFreeableStorageList.Entry[lists.length];
		for (int i = 0; i < lists.length; i++) {
			entries[i] = lists[i].getSubList().insert(this);
			int lfp = lists[i].freePriority();
			if (lfp > freePriority)
				freePriority = lfp;
		}
		this.freePriority = freePriority - 1;
	}
	
	//free
	@Override
	public synchronized final void free() {
		if (isFreed)
			return;
		isFreed = true;
		
		//entries
		for (Entry entry : entries)
			entry.remove();
		//subList
		if (subList != null)
			subList.free();
		
		handleFree();
	}
	
	protected abstract void handleFree();
	
	//isFreed
	@Override
	public boolean isFreed() {
		return isFreed;
	}
	
	@Override
	public void throwIfFreed() throws FreedException {
		if (isFreed)
			throw new FreedException(this);
	}
	
	//other
	@Override
	public int freePriority() {
		return freePriority;
	}
	
	//children
	@Override
	public synchronized IFreeableStorageList getSubList() {
		return subList != null ? subList : (subList = FreeableStorageList.createList(freePriority));
	}
}
