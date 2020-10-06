/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * File Data records the information of
 * 
 * file which will be sent.
 *
 */
package auxiliary;

import java.io.Serializable;
import java.util.Date;

public class FileData implements Serializable
{
	private static final long serialVersionUID = 7807586177895424411L;

	//Sender.
	private ADT_of_User Sender;
	
	//Receiver.
	private ADT_of_User Receiver;
	
	//Source file name.
	private String SourceFilename;
	
	//Destination file name.
	private String DestFilename;
	
	//Send time.
	private Date SendTime;
	
	//Destination IP address.
	private String DestIP;
	
	//Destination port.
	private int DestPort;

	public ADT_of_User getSender() 
	{
		return Sender;
	}

	public void setSender(ADT_of_User sender) 
	{
		Sender = sender;
	}

	public ADT_of_User getReceiver()
	{
		return Receiver;
	}

	public void setReceiver(ADT_of_User receiver) 
	{
		Receiver = receiver;
	}

	public String getSourceFilename() 
	{
		return SourceFilename;
	}

	public void setSourceFilename(String sourceFilename) 
	{
		SourceFilename = sourceFilename;
	}

	public String getDestFilename() 
	{
		return DestFilename;
	}

	public void setDestFilename(String destFilename) 
	{
		DestFilename = destFilename;
	}

	public Date getSendTime() 
	{
		return SendTime;
	}

	public void setSendTime(Date sendTime) 
	{
		SendTime = sendTime;
	}

	public String getDestIP()
	{
		return DestIP;
	}

	public void setDestIP(String destIP) 
	{
		DestIP = destIP;
	}

	public int getDestPort() 
	{
		return DestPort;
	}

	public void setDestPort(int destPort) 
	{
		DestPort = destPort;
	}
}

/**Nothing Wrong.*/
