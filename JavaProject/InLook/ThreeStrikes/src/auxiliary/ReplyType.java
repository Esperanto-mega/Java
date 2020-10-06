/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * ReplyType is a simple class to present
 * 
 *  different kinds of reply.
 *
 */
package auxiliary;

public enum ReplyType 
{
	/**Simple text.*/
	TEXT,
	
	/**Prepare to send file.*/
	GOING_TO_SENT_FILE,
	
	/**Approve receive file.*/
	AGREE_RECEIVE_FILE,
	
	/**Refuse to receive file.*/
	REFUSE_TO_RECEIVE_FILE,
	
	/**Receive file.*/
	RECEIVE_FILE,
	
	/**Log in.*/
	LOG_IN,
	
	/**Log out.*/
	LOG_OUT,
	
	/**Chat.*/
	CHAT,
	
	/**Shake window.*/
	SHAKE_WINDOW,
	
	/**Broadcast for server.*/
	BROADCAST,
	
	/**Kick out some users for server.*/
	KICK_OUT,
	
	/**Something else.*/
	OTHER
}

/**Nothing Wrong.*/
