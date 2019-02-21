package utils.excel_read_write;

import java.io.PrintStream;
import java.io.PrintWriter;

public class SysLevelException extends RuntimeException {

    public SysLevelException(String msg, Throwable th) {
        super(msg, th);
    }

    public SysLevelException(String msg) {
        super(msg);
    }

    public SysLevelException(Throwable th) {
        super(th);
    }

    @Override
    public void printStackTrace(PrintStream s) {
        if (super.getCause() != null) {
            s.print(this.getClass().getName() + " Caused by: ");
            super.getCause().printStackTrace(s);
        } else {
            super.printStackTrace(s);
        }
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        if (super.getCause() != null) {
            s.print(this.getClass().getName() + " Caused by: ");
            super.getCause().printStackTrace(s);
        } else {
            super.printStackTrace(s);
        }
    }
}
