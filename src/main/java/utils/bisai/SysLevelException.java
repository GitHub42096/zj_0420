package utils.bisai;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 系统级异常
 * @author D.linsen
 */
public class SysLevelException extends RuntimeException {

	private static final long serialVersionUID = -3085055009384088487L;

	public SysLevelException(String msg, Throwable th) {
		super(msg, th);
	}

	public SysLevelException(String msg) {
		super(msg);
	}

	public SysLevelException(Throwable th) {
		super(th);
	}

	public void printStackTrace(PrintStream s) {
		if (super.getCause() != null) {
			s.print(getClass().getName() + " Caused by: ");
			super.getCause().printStackTrace(s);
		} else {
			super.printStackTrace(s);
		}
	}

	public void printStackTrace(PrintWriter s) {
		if (super.getCause() != null) {
			s.print(getClass().getName() + " Caused by: ");
			super.getCause().printStackTrace(s);
		} else {
			super.printStackTrace(s);
		}
	}

}
