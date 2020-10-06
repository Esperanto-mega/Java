/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Class Server Data Store is responsible for
 * 
 * store information that server need to 
 * 
 * manage the chat room.
 *
 */

package server;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentSkipListMap;

import auxiliary.ADT_of_User;

public class ServerDataStore 
{
	public static ServerSocket Ssocket;
	
	public static Map<Long,ServerRecordClient> OnlineInfoMap;
	
	public static Map<Long,ADT_of_User> OnlineUserMap;
	
	public static Properties ServerProperty;
	
	public static ServerOnlineUserUI OnlineUserUI;
	
	public static ServerRegistedUserUI RegistedUserUI;
	
	public static Dimension ScreenSize;
	
	static
	{
		OnlineInfoMap = new ConcurrentSkipListMap<Long,ServerRecordClient>();
		OnlineUserMap = new ConcurrentSkipListMap<Long,ADT_of_User>();
		ServerProperty = new Properties();
		OnlineUserUI = new ServerOnlineUserUI();
		RegistedUserUI = new ServerRegistedUserUI();
		ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		try
		{
			ServerProperty.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("ServerProperty.properties"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}

/**Nothing Wrong.*/
