/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Each user has a client thread , to listen and
 *
 * receive messages from server or other
 *
 * users' clients.
 *
 */
package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import auxiliary.ADT_of_User;
import auxiliary.FileData;
import auxiliary.Message;
import auxiliary.Plea;
import auxiliary.Reply;
import auxiliary.ReplyType;
import auxiliary.ShakeFrame;

public class ClientThread extends Thread
{
	private JFrame ThisFrame;
	
	public ClientThread(JFrame frame)
	{
		ThisFrame = frame;
	}
	
	public void run()
	{
		try
		{
			while(ClientDataStore.socket_to_Server.isConnected())
			{
				Reply reply = (Reply)ClientDataStore.obj_IS.readObject();
				ReplyType type = reply.getType();
				
				System.out.println("Client receives reply "+ type);
				if(type==ReplyType.LOG_IN)
				{
					//Log in.
					ADT_of_User user = (ADT_of_User)reply.getData("LoginUser");
					ClientDataStore.UI_Model.addUser(user);
					
					ClientChat.OnlineUsersCount.setText("Online Users List ¡¾"+ClientDataStore.UI_Model.getSize()+"¡¿");
					ClientToServer.appendText("¡¾Server¡¿User "+user.getNickname()+" has loged in.\n");
					/**Work very well.*/
				}
				else if(type==ReplyType.LOG_OUT)
				{
					//Log out.
					ADT_of_User user = (ADT_of_User)reply.getData("LogoutUser");
					ClientDataStore.UI_Model.removeUser(user);
					
					ClientChat.OnlineUsersCount.setText("Online Users List ¡¾"+ClientDataStore.UI_Model.getSize()+"¡¿");
					ClientToServer.appendText("¡¾Server¡¿User "+user.getNickname()+" has loged out.\n");
					/**Work very well.*/
				}
				else if(type==ReplyType.CHAT||type==ReplyType.BROADCAST)
				{
					//Chat.
					Message message  =(Message) reply.getData("TextMessage");
					ClientToServer.appendText(message.getContent());
				}
				else if(type==ReplyType.SHAKE_WINDOW)
				{
					//Shake.
					Message message  =(Message) reply.getData("Shake");
					ClientToServer.appendText(message.getContent());
					new ShakeFrame(ThisFrame).StartShake();
				}
				else if(type==ReplyType.GOING_TO_SENT_FILE)
				{
					//Going to send file.
					GoingSendFile(reply);
				}
				else if(type==ReplyType.AGREE_RECEIVE_FILE)
				{
					//Send file really.
					SendFile(reply);
				}
				else if(type==ReplyType.REFUSE_TO_RECEIVE_FILE)
				{
					//Refuse to receive file.
					ClientToServer.appendText("¡¾Server¡¿Receiver has refused to accept file.\n");
				}
				else if(type==ReplyType.RECEIVE_FILE)
				{
					//Receive file.
					ReceiveFile(reply);
				}
				else if(type==ReplyType.KICK_OUT)
				{
					//Kick out.
					ClientChat.KickOut();
				}
			}
		}
		catch(IOException ea)
		{
			
		}
		catch(ClassNotFoundException eb)
		{
			eb.printStackTrace();
		}
	}
	
	//Prepare to send file.
	public void GoingSendFile(Reply reply)
	{
		FileData goingSendFile = (FileData)reply.getData("SendFile");
		
		String SenderInfo = goingSendFile.getSender().getNickname()+
				"("+goingSendFile.getSender().getID()+")";
		
		String FileName = goingSendFile.getSourceFilename().substring(goingSendFile.getSourceFilename().lastIndexOf(File.separator)+1);
		
		int Choice = JOptionPane.showConfirmDialog(ThisFrame,
				SenderInfo+" want to send a file¡¾"+FileName+"¡¿ to you.\nWill you agree?",
				"Accept file.",JOptionPane.YES_NO_OPTION);
		
		try
		{
			Plea plea = new Plea();
			plea.setData("SendFile", goingSendFile);
			
			if(Choice==JOptionPane.YES_OPTION)
			{
				JFileChooser FileChooser = new JFileChooser();
				FileChooser.setSelectedFile(new File(FileName));
				
				int Answer = FileChooser.showSaveDialog(ThisFrame);
				
				if(Answer==JFileChooser.APPROVE_OPTION)
				{
					goingSendFile.setDestFilename(FileChooser.getSelectedFile().getCanonicalPath());
					goingSendFile.setDestIP(ClientDataStore.IP_Addr);
					goingSendFile.setDestPort(ClientDataStore.PORT_FOR_FILES);
					plea.setAction("AgreeReceiveFile");
					
					ClientToServer.appendText("¡¾Server¡¿You have agreed to receive files from "+
					SenderInfo+" , and we are downloading.\n");
				}
				else
				{
					plea.setAction("RefuseReceiveFile");
					ClientToServer.appendText("¡¾Server¡¿You have refused to receive files from "+SenderInfo+"\n");
				}
			}
			else
			{
				plea.setAction("RefuseReceiveFile");
				ClientToServer.appendText("¡¾Server¡¿You have refused to receive files from "+SenderInfo+"\n");
			}
			ClientToServer.Simple_SendMessage(plea);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//Send files.
	private void SendFile(Reply reply)
	{
		final FileData SendFile = (FileData)reply.getData("SendFile");
		
		Socket socket = null;
		BufferedInputStream Buffer_IS = null;
		BufferedOutputStream Buffer_OS = null;
		
		try
		{
			socket = new Socket(SendFile.getDestIP(),SendFile.getDestPort());
			Buffer_IS = new BufferedInputStream(new FileInputStream(SendFile.getSourceFilename()));
			Buffer_OS = new BufferedOutputStream(socket.getOutputStream());
			
			byte[] buffer_ = new byte[1024];
			int Sentinel=-1;
			while((Sentinel=Buffer_IS.read(buffer_))!=-1)
			{
				Buffer_OS.write(buffer_,0,Sentinel);
			}
			Buffer_OS.flush();
			synchronized (this)
			{
				ClientToServer.appendText("¡¾Server¡¿File has been sent successfully!\n");
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			IO_close.ShutDown(Buffer_IS, Buffer_OS);
			SocketClose.Close(socket);
		}
	}
	
	//Receive files.
	private void ReceiveFile(Reply reply)
	{
		final FileData SendFile = (FileData)reply.getData("SendFile");
		
		ServerSocket Ssocket = null;
		Socket socket = null;
		BufferedInputStream Buffer_IS = null;
		BufferedOutputStream Buffer_OS = null;
		
		try
		{
			Ssocket = new ServerSocket(SendFile.getDestPort());
			socket = Ssocket.accept();
			Buffer_IS = new BufferedInputStream(socket.getInputStream());
			Buffer_OS = new BufferedOutputStream(new FileOutputStream(SendFile.getDestFilename()));
			
			byte[] buffer_ = new byte[1024];
			int Sentinel=-1;
			while((Sentinel=Buffer_IS.read(buffer_))!=-1)
			{
				Buffer_OS.write(buffer_,0,Sentinel);
			}
			Buffer_OS.flush();
			synchronized (this)
			{
				ClientToServer.appendText("¡¾Server¡¿File has been received!\nPosition:¡¾"+
			SendFile.getDestFilename()+"¡¿\n");
			}	
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			IO_close.ShutDown(Buffer_IS, Buffer_OS);
			SocketClose.Close(socket);
			SocketClose.Close(Ssocket);
		}
	}
}

/**Nothing Wrong.*/

class IO_close
{
	private static void Close(InputStream IS)
	{
		if(IS!=null)
		{
			try
			{
				IS.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	private static void Close(OutputStream OS)
	{
		if(OS!=null)
		{
			try
			{
				OS.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void ShutDown(InputStream IS,OutputStream OS)
	{
		Close(IS);
		Close(OS);
	}
}

class SocketClose
{
	public static void Close(Socket s)
	{
		if(s!=null&&!s.isClosed())
		{
			try
			{
				s.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public static void Close(ServerSocket s)
	{
		if(s!=null&&!s.isClosed())
		{
			try
			{
				s.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
