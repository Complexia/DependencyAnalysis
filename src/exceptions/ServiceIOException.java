/**
 * 
 */
package exceptions;

/**
 * Exception designed for handling any IO problems that arise from reading service data into or out of
 * a file or other appropriate IO stream.
 * Use the message field to specify the exact details of the IO operation that threw this exception and include details
 * on what the specific problem was.
 *
 */
public class ServiceIOException extends ServiceException {

	/**
	 * Default constructor.
	 */
	public ServiceIOException() 
	{
		
	}

	/**
	 * @param errMsg A message detailing the exception's circumstances
	 */
	public ServiceIOException(String errMsg) 
	{
		super(errMsg);
	}

	/**
	 * @param thrower the {@link Throwable} object that threw this exception
	 */
	public ServiceIOException(Throwable thrower) 
	{
		super(thrower);
	}

	/**
	 * @param errMsg A message detailing the exception's circumstances
	 * @param thrower the Throwable object that threw this exception
	 */
	public ServiceIOException(String errMsg, Throwable thrower) {
		super(errMsg, thrower);
	}

	/**
	 * @param errMsg A message detailing the exception's circumstances.
	 * @param thrower the Throwable object that threw this exception.
	 * @param enableSuppression choose whether the exception can be suppressed or not.
	 * @param writeableStackTrace Choose whether the stack trace can be written out using printStackTrace.
	 */
	public ServiceIOException(String errMsg, Throwable thrower, boolean enableSuppression,
			boolean writeableStackTrace) {
		super(errMsg, thrower, enableSuppression, writeableStackTrace);
	}

}
