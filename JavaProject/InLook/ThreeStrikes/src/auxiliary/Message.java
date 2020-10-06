/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Client Message is a class presents
 * 
 * the entity of a specified message.
 *
 */
package auxiliary;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable
{
	private static final long serialVersionUID = 8452890343597794904L;

	//Receiver.
	private ADT_of_User Receiver;
	
	//Sender.
	private ADT_of_User Sender;
	
	//Content of message.
	private String content;
	
	//Send time.
	private Date SendTime;

	public ADT_of_User getReceiver() 
	{
		return Receiver;
	}

	public void setReceiver(ADT_of_User receiver) 
	{
		Receiver = receiver;
	}

	public ADT_of_User getSender() 
	{
		return Sender;
	}

	public void setSender(ADT_of_User sender) 
	{
		Sender = sender;
	}

	public String getContent() 
	{
		return content;
	}

	public void setContent(String content) 
	{
		this.content = content;
	}

	public Date getSendTime() 
	{
		return SendTime;
	}

	public void setSendTime(Date sendTime)
	{
		SendTime = sendTime;
	}
}

/**Nothing Wrong.*/