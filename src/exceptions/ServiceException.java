/**
 * 
 */
package exceptions;

/**
 * General exception thrown by SimpleService objects.
 *
 */
public class ServiceException extends Exception {

	/**
	 * The default constructor
	 */
	public ServiceException() {
	}

	/**
	 * @param errMsg A message detailing the exception's circumstances
	 */
	public ServiceException(String errMsg) {
		super(errMsg);
	}

	/**
	 * @param thrower the {@link Throwable} object that threw this exception
	 */
	public ServiceException(Throwable thrower) {
		super(thrower);
	}

	/**
	 * @param errMsg A message detailing the exception's circumstances
	 * @param thrower the Throwable object that threw this exception
	 */
	public ServiceException(String errMsg, Throwable thrower) {
		super(errMsg, thrower);
	}

	/**
	 * @param errMsg A message detailing the exception's circumstances.
	 * @param thrower the Throwable object that threw this exception.
	 * @param enableSuppression choose whether the exception can be suppressed or not.
	 * @param writeableStackTrace Choose whether the stack trace can be written out using printStackTrace.
	 */
	public ServiceException(String errMsg, Throwable thrower, boolean enableSuppression, boolean writeableStackTrace) {
		super(errMsg, thrower, enableSuppression, writeableStackTrace);
	}

}
