/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Class ClientDataStore is a buffer to 
 * 
 * store one client's basic information
 * 
 * temporarily , such as IP_Addr and
 * 
 * Port_number. Moreover , it owns some
 * 
 * properties that decide the connection
 * 
 * between a specified client and the 
 * 
 * server.
 *
 */

package client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

import auxiliary.ADT_of_User;

public class ClientDataStore
{
	//Current user.
	public static ADT_of_User thisUser;
	
	//Users online.
	public static List<ADT_of_User> onlineUsers;
	
	//Socket from client to server.
	public static Socket socket_to_Server;
	
	//Output & input stream from client to server.
	public static ObjectOutputStream obj_OS;
	public static ObjectInputStream obj_IS;
	
	//Properties for server.
	public static Properties set_of_Property;
	
	//Screen size of client dialog.
	public static Dimension screenSize;
	
	//IP address of this client.
	public static String IP_Addr;
	
	//Port for receive files.
	public static final int PORT_FOR_FILES=6667;
	
	//Model of UI for online users.
	public static ClientOnlineUsersModel UI_Model;
	
	static
	{
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		set_of_Property = new Properties();
		try
		{
			IP_Addr = InetAddress.getLocalHost().getHostAddress();
			set_of_Property.load(Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream("ServerProperty.properties"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	//Private constructor.
	//Avoid being constructed.
	private ClientDataStore()
	{
		//Have a nice day.
	}
}

/**Nothing Wrong.*/
//Nothing Wrong