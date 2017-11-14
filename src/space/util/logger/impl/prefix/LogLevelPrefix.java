package space.util.logger.impl.prefix;

import space.util.logger.impl.LogMessage;

public class LogLevelPrefix extends AbstractPrefix {
	
	public LogLevelPrefix() {
	}
	
	public LogLevelPrefix(char start, char end) {
		super(start, end);
	}
	
	@Override
	public void accept(LogMessage logMessage) {
		logMessage.prefix.append(start).append(logMessage.level).append(end);
	}
}