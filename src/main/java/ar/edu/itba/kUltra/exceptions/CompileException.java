package ar.edu.itba.kUltra.exceptions;

public class CompileException extends RuntimeException {
	public CompileException(String message) {
		super(message);
	}

	public CompileException(Throwable e) {
		super(e);
	}
}
