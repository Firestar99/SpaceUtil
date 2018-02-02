package space.util.concurrent.lock.simplelock;

import space.util.concurrent.lock.keylock.KeyLock;

/**
 * a simple wrapper class from {@link ISimpleLock} to {@link KeyLock} with 'Thread' as the Key
 */
public class ThreadLock extends WrapperLock<Thread> {
	
	public ThreadLock() {
		super(Thread::currentThread);
	}
	
	public ThreadLock(KeyLock<? super Thread> lock) {
		super(Thread::currentThread, lock);
	}
}