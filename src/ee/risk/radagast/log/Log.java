/*
 *     Radagast is a classification framework for user submitted textual content.
 *
 *     Copyright (C) 2016 by ranger
 *     https://github.com/theranger/radagast
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ee.risk.radagast.log;

import sun.misc.JavaLangAccess;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class Log {

	public enum Level { DEBUG, INFO, WARN }
	private final Logger logger = Logger.getAnonymousLogger();
	private static final int FRAME_DEPTH = 3;

	private Log(Level level) {
		switch (level) {
			case DEBUG:
				logger.setLevel(java.util.logging.Level.ALL);
				break;

			case INFO:
				logger.setLevel(java.util.logging.Level.INFO);
				break;

			case WARN:
				logger.setLevel(java.util.logging.Level.WARNING);
				break;
		}

		// Add console handler to any level
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(logger.getLevel());
		logger.addHandler(consoleHandler);
	}

	public static Log getLogger(Level level) {
		return new Log(level);
	}

	public void debug(String fmt, Object... args) {
		log(java.util.logging.Level.FINER, fmt, args);
	}

	private void log(java.util.logging.Level level, String fmt, Object... args) {
		if (logger.getLevel().intValue() > level.intValue()) return;

		LogRecord logRecord = new LogRecord(level, String.format(fmt, args));
		JavaLangAccess access = sun.misc.SharedSecrets.getJavaLangAccess();
		Throwable throwable = new Throwable();

		if (access.getStackTraceDepth(throwable) >= FRAME_DEPTH) {
			StackTraceElement frame = access.getStackTraceElement(throwable, FRAME_DEPTH);
			logRecord.setSourceClassName(frame.getClassName());
			logRecord.setSourceMethodName(frame.getMethodName());
		}
		logger.log(logRecord);
	}
}
