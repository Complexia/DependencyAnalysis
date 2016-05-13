/**
 * 
 */
package exceptions;

/**
 * Exception thrown when an IOVariable name can't be matched to an {@link IOVariable} object.
 * This is designed to better differentiate and specify the cause of a malfunction in the 
 * operation of uploadFile.
 *
 */
public class InvalidIOVariableReferenceException extends ServiceException
{

	/**
	 * The default constructor
	 */
	public InvalidIOVariableReferenceException() {
	}

	/**
	 * @param errMsg A message detailing the exception's circumstances
	 */
	public InvalidIOVariableReferenceException(String errMsg) {
		super(errMsg);
	}

	/**
	 * @param thrower the {@link Throwable} object that threw this exception
	 */
	public InvalidIOVariableReferenceException(Throwable thrower) {
		super(thrower);
	}

	/**
	 * @param errMsg A message detailing the exception's circumstances
	 * @param thrower the Throwable object that threw this exception
	 */
	public InvalidIOVariableReferenceException(String errMsg, Throwable thrower) {
		super(errMsg, thrower);
	}

	/**
	 * @param errMsg A message detailing the exception's circumstances.
	 * @param thrower the Throwable object that threw this exception.
	 * @param enableSuppression choose whether the exception can be suppressed or not.
	 * @param writeableStackTrace Choose whether the stack trace can be written out using printStackTrace.
	 */
	public InvalidIOVariableReferenceException(String errMsg, Throwable thrower, boolean enableSuppression, boolean writeableStackTrace) {
		super(errMsg, thrower, enableSuppression, writeableStackTrace);
	}

}
