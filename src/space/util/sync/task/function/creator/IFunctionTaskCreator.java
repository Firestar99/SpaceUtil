package space.util.sync.task.function.creator;

import space.util.sync.task.basic.ITask;
import space.util.sync.task.function.typehandler.ITypeHandler;

import java.util.concurrent.Executor;

@FunctionalInterface
public interface IFunctionTaskCreator<FUNCTION> {
	
	ITask create(ITypeHandler<FUNCTION> handler);
	
	default ITask execute(ITypeHandler<FUNCTION> handler) {
		return execute(Runnable::run, handler);
	}
	
	default ITask execute(Executor executor, ITypeHandler<FUNCTION> handler) {
		ITask task = create(handler);
		task.submit(executor);
		return task;
	}
}