package auxiliary;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import server.ServerDataStore;
import server.ServerRecordClient;

public class ProcessUnit implements Runnable
{
	private Socket ThisClient;
	
	//Constructor.
	public ProcessUnit(Socket thisClient) 
	{
		//super();
		ThisClient = thisClient;
	}

	@Override
	public void run() 
	{
		// TODO 自动生成的方法存根
		boolean Continuous = true;
		try
		{
			ServerRecordClient thisRecord = new ServerRecordClient(
					new ObjectInputStream(ThisClient.getInputStream()), 
					new ObjectOutputStream(ThisClient.getOutputStream()));
			
			while(Continuous)
			{
				Plea plea = (Plea)thisRecord.getObject_IS().readObject();
				String action = plea.getAction();
				System.out.println("Server has accepted request "+action+" from client.");
				
				if(action.equals("Register"))
				{
					RegisteUser(thisRecord,plea);
				}
				else if(action.equals("Login"))
				{
					Login(thisRecord,plea);
				}
				else if(action.equals("Exit"))
				{
					Continuous = Exit(thisRecord,plea);
				}
				else if(action.equals("Chat"))
				{
					Chat(plea);
				}
				else if(action.equals("Shake"))
				{
					Shake(plea);
				}
				else if(action.equals("ToSendFile"))
				{
					ToSendFile(plea);
				}
				else  if(action.equals("AgreeReceiveFile"))
				{
					AgreeReceiveFile(plea);
				}
				else if(action.equals("RefuseReceiveFile"))
				{
					RefuseReceiveFile(plea);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**Register*/
	public void RegisteUser(ServerRecordClient thisRecord,Plea plea) throws IOException
	{
		ADT_of_User user = (ADT_of_User)plea.getData("User");
		ServiceForUser service = new ServiceForUser();
		service.AddUser(user);
		
		Reply reply = new Reply();
		reply.setPhase(ReplyPhase.SUCCESS);
		reply.setData("User", user);
		
		thisRecord.getObject_OS().writeObject(reply);
		thisRecord.getObject_OS().flush();
		
		ServerDataStore.RegistedUserUI.add(new String[] 
		{
			String.valueOf(user.getID()),
			user.getPassword(),
			user.getNickname(),
			String.valueOf(user.getSex())
		});
	}
	
	/**Log in.*/
	public void Login(ServerRecordClient thisRecord,Plea plea) throws IOException
	{
		String ID = (String)plea.getData("ID");
		String Password = (String)plea.getData("Password");
		ServiceForUser service = new ServiceForUser();
		ADT_of_User user = service.Login(Long.parseLong(ID), Password);
		
		Reply reply = new Reply();
		
		if(user!=null)
		{
			//Have signed in already.
			if(ServerDataStore.OnlineUserMap.containsKey(user.getID()))
			{
				reply.setPhase(ReplyPhase.SUCCESS);
				reply.setData("Message", "This user has loged in another position.");
				thisRecord.getObject_OS().writeObject(reply);
				thisRecord.getObject_OS().flush();
			}
			//A new user will sign in.
			else
			{
				ServerDataStore.OnlineUserMap.put(user.getID(), user);
				reply.setData("OnlineUsers", new CopyOnWriteArrayList<ADT_of_User>(ServerDataStore.OnlineUserMap.values()));
				reply.setPhase(ReplyPhase.SUCCESS);
				reply.setData("User", user);
				thisRecord.getObject_OS().writeObject(reply);
				thisRecord.getObject_OS().flush();
				
				Reply anotherReply = new Reply();
				anotherReply.setType(ReplyType.LOG_IN);
				anotherReply.setData("LoginUser", user);
				IterativeReply(anotherReply);
				
				ServerDataStore.OnlineInfoMap.put(user.getID(),thisRecord);
				
				ServerDataStore.OnlineUserUI.add(new String[] 
						{
								String.valueOf(user.getID()),
								user.getNickname(),
								String.valueOf(user.getSex())
						});
			}
		}
		else
		{
			reply.setPhase(ReplyPhase.SUCCESS);
			reply.setData("Message", "Account or password is wrong.");
			thisRecord.getObject_OS().writeObject(reply);
			thisRecord.getObject_OS().flush();
		}
	}
	
	/**Log out.*/
	public boolean Exit(ServerRecordClient thisRecord,Plea plea) throws IOException
	{
		System.out.println(ThisClient.getInetAddress().getHostAddress()+
				":"+ThisClient.getPort()+" has left.");
		
		ADT_of_User user = (ADT_of_User)plea.getData("User");
		ServerDataStore.OnlineInfoMap.remove(user.getID());
		ServerDataStore.OnlineUserMap.remove(user.getID());
		
		Reply reply = new Reply();
		reply.setType(ReplyType.LOG_OUT);
		reply.setData("LogoutUser", user);
		thisRecord.getObject_OS().writeObject(reply);
		thisRecord.getObject_OS().flush();
		ThisClient.close();
		
		ServerDataStore.OnlineUserUI.remove(user.getID());
		IterativeReply(reply);
		
		return false;
	}
	
	/**Chat.*/
	public void Chat(Plea plea) throws IOException
	{
		Message message = (Message)plea.getData("Message");
		Reply reply = new Reply();
		reply.setPhase(ReplyPhase.SUCCESS);
		reply.setType(ReplyType.CHAT);
		reply.setData("TextMessage", message);
		
		if(message.getReceiver()!=null)
		//Private chat.
		{
			ServerRecordClient service = ServerDataStore.OnlineInfoMap.get(message.getReceiver().getID());
			SendReply(service, reply);
		}
		else
		//Group chat.
		{
			for(Long ID:ServerDataStore.OnlineInfoMap.keySet())
			{
				if(message.getSender().getID()==ID)
				{
					//Skip sender.
					continue;
				}
				else
				{
					ServerRecordClient service=ServerDataStore.OnlineInfoMap.get(ID);
					SendReply(service,reply);
				}
			}
		}
	}
	
	/**Shake.*/
	public void Shake(Plea plea) throws IOException
	{
		Message message = (Message)plea.getData("Message");
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(" ").append(dateFormat.format(message.getSendTime()))
		.append(" ")
		.append(message.getSender().getNickname())
		.append("(").append(message.getSender().getID()).append(")")
		.append(" is shaking you!\n");
		message.setContent(buffer.toString());
		
		Reply reply = new Reply();
		reply.setPhase(ReplyPhase.SUCCESS);
		reply.setType(ReplyType.SHAKE_WINDOW);
		reply.setData("Shake", message);
		
		ServerRecordClient service = ServerDataStore.OnlineInfoMap.get(message.getReceiver().getID());
		/**Eighth Wrong : 'getReceiver' for 'getSender()'*/
		SendReply(service,reply);
	}
	
	/**Going to send file.*/
	public void ToSendFile(Plea plea) throws IOException
	{
		Reply reply = new Reply();
		reply.setPhase(ReplyPhase.SUCCESS);
		reply.setType(ReplyType.GOING_TO_SENT_FILE);
		FileData file = (FileData)plea.getData("File");
		reply.setData("SendFile", file);
		
		ServerRecordClient service = ServerDataStore.OnlineInfoMap.get(file.getReceiver().getID());
		SendReply(service,reply);	
	}
	
	/**Agree to receive file.*/
	public void AgreeReceiveFile(Plea plea) throws IOException
	{
		FileData file = (FileData)plea.getData("SendFile");
		
		Reply reply = new Reply();
		reply.setPhase(ReplyPhase.SUCCESS);
		reply.setData("SendFile", file);
		reply.setType(ReplyType.AGREE_RECEIVE_FILE);
		
		ServerRecordClient SenderRecord = ServerDataStore.OnlineInfoMap.get(file.getSender().getID());
		SendReply(SenderRecord,reply);
		
		Reply anotherReply = new Reply();
		anotherReply.setPhase(ReplyPhase.SUCCESS);
		anotherReply.setData("SendFile", file);
		anotherReply.setType(ReplyType.RECEIVE_FILE);
		
		ServerRecordClient ReceiverRecord = ServerDataStore.OnlineInfoMap.get(file.getReceiver().getID());
		/**Seventh Wrong: getReceiver() but not getSender()*/
		SendReply(ReceiverRecord,anotherReply);
	}
	
	/**Refuse to receive file.*/
	public void RefuseReceiveFile(Plea plea) throws IOException
	{
		FileData file = (FileData)plea.getData("SendFile");
		
		Reply reply = new Reply();
		reply.setPhase(ReplyPhase.SUCCESS);
		reply.setData("SendFile", file);
		reply.setType(ReplyType.REFUSE_TO_RECEIVE_FILE);
		
		ServerRecordClient record = ServerDataStore.OnlineInfoMap.get(file.getSender().getID());
		SendReply(record,reply);
	}
	
	private void IterativeReply(Reply reply) throws IOException
	{
		for(ServerRecordClient recordedClient:ServerDataStore.OnlineInfoMap.values())
		{
			ObjectOutputStream Object_OS = recordedClient.getObject_OS();
			Object_OS.writeObject(reply);
			Object_OS.flush();
		}
	}
	
	private void SendReply(ServerRecordClient thisRecord,Reply reply) throws IOException
	{
		ObjectOutputStream Object_OS = thisRecord.getObject_OS();
		Object_OS.writeObject(reply);
		Object_OS.flush();
	}
	
	private static void SendReplyOfficial(ServerRecordClient thisRecord,Reply reply) throws IOException
	{
		ObjectOutputStream Object_OS = thisRecord.getObject_OS();
		Object_OS.writeObject(reply);
		Object_OS.flush();
	}
	
	public static void Broadcast(String string)throws IOException
	{
		ADT_of_User system = new ADT_of_User(8888,"system");
		Message message = new Message();
		message.setSender(system);
		message.setSendTime(new Date());
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		StringBuffer buffer = new StringBuffer();
		buffer.append(" ").append(dateFormat.format(message.getSendTime())).append(" ");
		buffer.append("【System Notice】\n"+string+"\n");
		message.setContent(buffer.toString());
		
		Reply reply = new Reply();
		reply.setPhase(ReplyPhase.SUCCESS);
		reply.setType(ReplyType.BROADCAST);
		reply.setData("TextMessage", message);
		
		for(Long ID:ServerDataStore.OnlineInfoMap.keySet())
		{
			SendReplyOfficial(ServerDataStore.OnlineInfoMap.get(ID),reply);
		}
	}
	
	public static void Remove(ADT_of_User KickedUser)throws IOException
	{
		ADT_of_User system = new ADT_of_User(8888,"system");
		Message message = new Message();
		message.setSender(system);
		message.setSendTime(new Date());
		message.setReceiver(KickedUser);
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		StringBuffer buffer = new StringBuffer();
		buffer.append(" ").append(dateFormat.format(message.getSendTime())).append(" ");
		buffer.append("【System Notice】\nYou have been kicked out.\n");
		message.setContent(buffer.toString());
		
		Reply reply = new Reply();
		reply.setPhase(ReplyPhase.SUCCESS);
		reply.setType(ReplyType.KICK_OUT);
		reply.setData("TextMessage", message);
		
		ServerRecordClient record = ServerDataStore.OnlineInfoMap.get(message.getReceiver().getID());
		SendReplyOfficial(record,reply);
	}
	
	public static void SystemChat(String string,ADT_of_User ThatUser)throws IOException
	{
		ADT_of_User system = new ADT_of_User(8888,"system");
		Message message = new Message();
		message.setSender(system);
		message.setSendTime(new Date());
		message.setReceiver(ThatUser);
		
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		StringBuffer buffer = new StringBuffer();
		buffer.append(" ").append(dateFormat.format(message.getSendTime())).append(" ");
		buffer.append("【System Notice】\n"+string+"\n");
		message.setContent(buffer.toString());
		
		Reply reply = new Reply();
		reply.setPhase(ReplyPhase.SUCCESS);
		reply.setType(ReplyType.CHAT);
		reply.setData("TextMessage", message);
		
		ServerRecordClient record = ServerDataStore.OnlineInfoMap.get(message.getReceiver().getID());
		SendReplyOfficial(record,reply);
	}
}

/**Nothing Wrong.*/