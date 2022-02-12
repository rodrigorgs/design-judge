package br.ufba.designjudge.exception;

public class JudgeException extends RuntimeException {

	public JudgeException() {
		super();
	}

	public JudgeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JudgeException(String message, Throwable cause) {
		super(message, cause);
	}

	public JudgeException(String message) {
		super(message);
	}

	public JudgeException(Throwable cause) {
		super(cause);
	}

}
