/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Client To Server is a practical class
 * 
 * to send request from Client to Server.
 *
 */
package client;

import java.io.IOException;

import auxiliary.Plea;
import auxiliary.Reply;

public class ClientToServer 
{
	public static Reply sendMessage(Plea plea) throws IOException
	{
		Reply reply=null;
		try
		{
			/**Send plea.*/
			ClientDataStore.obj_OS.writeObject(plea);
			ClientDataStore.obj_OS.flush();
			System.out.println("Client send a request : "+plea.getAction());
			
			if(!"Exit".equals(plea.getAction()))
			{
				//Not exit , so receive reply.
				reply = (Reply)ClientDataStore.obj_IS.readObject();
				System.out.println("Client receive a reply : "+reply.getPhase());
			}
			else
			{
				System.out.println("Client has left.");
			}
		}
		catch(IOException e)
		{
			throw e;
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return reply;
	}
	
	public static void Simple_SendMessage(Plea plea) throws IOException
	{
		try
		{
			ClientDataStore.obj_OS.writeObject(plea);
			ClientDataStore.obj_OS.flush();
			System.out.println("Client send a request : "+plea.getAction());
		}
		catch(IOException e)
		{
			throw e;
		}
	}
	
	//Append text message to the chat room page.
	public static void appendText(String txt)
	{
		ClientChat.GroupMessageArea.append(txt);
		
		ClientChat.GroupMessageArea.setCaretPosition(ClientChat.GroupMessageArea.getDocument().getLength());
	}
}
/**Fifth Wrong : forget to implement 'appendText'.*/

/**Nothing Wrong.*/
