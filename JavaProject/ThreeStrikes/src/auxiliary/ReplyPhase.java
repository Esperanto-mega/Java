/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * ReplyPhase is a simple class to present
 * 
 *  different phases of reply.
 *
 */
package auxiliary;

public enum ReplyPhase 
{
	/**Plea has been dealt successfully.*/
	SUCCESS,
	
	/**Server encounters something wrong.*/
	SERVER_ERROR,
	
	/**Plea cannot be satisfied.*/
	NOT_SATISFIED,
	
	/**Wrong Plea.*/
	WORNG_PLEA
}

/**Nothing Wrong.*/