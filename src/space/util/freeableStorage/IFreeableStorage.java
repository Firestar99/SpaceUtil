package space.util.freeableStorage;

import space.util.baseobject.additional.Freeable;

/**
 * {@link IFreeableStorage} is an advanced native or other resource free-ing system.
 * To use it you need a front-end Object and (mostly as a STATIC inner class) a Storage-Object (this class) for values required for eg. deallocation.
 * When creating a Storage you have to extend one of the three types of {@link IFreeableStorage}:
 * <ul>
 * <li>{@link FreeableStorage} - Phantom - {@link java.lang.ref.PhantomReference#get()} always returns null &emsp;&emsp;<b><- use this for native resource deallocation</b></li>
 * <li>{@link FreeableStorageWeak} - Weak - {@link java.lang.ref.WeakReference#get()} returns the front-end Object if not already deallocated &emsp;&emsp;<b><- use this for java cleanup stuff</b></li>
 * <li>{@link FreeableStorageSoft} - Soft - {@link java.lang.ref.SoftReference#get()} always returns the front-end object &emsp;&emsp;<b><- use this for out-of-memory / cache setups (in java)</b></li>
 * <li>See the different java.lang.ref Classes and the Package-Summary for more info.</li>
 * </ul>
 * <p>
 * They handle everything for you, from cleaning up hooks at your parents, freeing children first (explained later) and even the detection of already being freed.
 * You only have to implement the {@link FreeableStorage#handleFree()} Method and actually free your resources (eg. {@link sun.misc.Unsafe#freeMemory(long) Unsafe.freeMemory(long)}).
 * Creating the Storage-Object requires you to give it the front-end Object.
 * Note that <b>having any Reference from the Storage to the front-end Object will cause it not to get cleaned up!</b>
 * So make sure that if you are using Storage as an inner class it is a static class.
 * <p>
 * A {@link IFreeableStorage} also ALWAYS has "parents", which you can specify in the constructor.
 * When a parent is freed, their children are being freed first, then the parent. This allows for eg. Instance having multiple Buffers setups to free the Buffers first and then the instance.
 * Everything should be rooted sometime into the global {@link IFreeableStorage#ROOT_LIST}, which will be cleaned up automatically then the JVM starts the Shutdown threads.<br>
 * If your are using the SpaceEngine you should root it into your Side first. (which is then rooted into {@link IFreeableStorage#ROOT_LIST}).
 * You can also get the {@link IFreeableStorageList} containing all children of a Storage-Object with {@link IFreeableStorage#getSubList()}
 * <p>
 * If you want an {@link IFreeableStorage} Object in between without any free-ing capabilities, you can use {@link IFreeableStorage#createAnonymous(IFreeableStorage...)} to do so.
 * Have a look into {@link FreeableStorageCleaner} to setup a {@link space.util.logger.Logger} for cleanup information or other things cleanup related.
 */
public interface IFreeableStorage extends Freeable {
	
	IFreeableStorageList ROOT_LIST = FreeableStorageList.createList(0);
	
	/**
	 * Higher numbers are released first, lower later.
	 * Use this to increase efficiency of freeing and removing from any {@link IFreeableStorageList}
	 */
	int freePriority();
	
	/**
	 * Gets the subList of this {@link IFreeableStorage}
	 *
	 * @return a {@link IFreeableStorageList} to hook into
	 */
	IFreeableStorageList getSubList();
	
	static FreeableStorage createAnonymous(IFreeableStorage... parents) {
		return new FreeableStorage(null, parents) {
			@Override
			protected void handleFree() {
			
			}
		};
	}
}