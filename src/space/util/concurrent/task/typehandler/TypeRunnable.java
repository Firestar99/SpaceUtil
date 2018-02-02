package space.util.concurrent.task.typehandler;

import java.util.function.Supplier;

public class TypeRunnable {
	
	public static final ITypeHandler<Runnable> INSTANCE = Runnable::run;
	public static final Supplier<ITypeHandler<Runnable>> SUPPLIER = () -> INSTANCE;
}