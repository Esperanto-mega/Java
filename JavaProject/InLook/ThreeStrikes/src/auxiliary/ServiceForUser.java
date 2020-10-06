package auxiliary;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import server.ServerDataStore;

public class ServiceForUser 
{
	private static int UserCount = 3;
	
	public static void main(String[] args)
	{
		ServiceForUser service = new ServiceForUser();
		service.InternalTestUser();
		List<ADT_of_User> UserList = service.LoadUsers();
		for(ADT_of_User user:UserList)
		{
			System.out.println(user);
		}
	}

	public void InternalTestUser()
	{
		/**ADT_of_User(String nickname, String password, char sex, int profile) */
		ADT_of_User InternalTestUser_A = new ADT_of_User("Administrator","987654",'M',0);
		ADT_of_User InternalTestUser_B = new ADT_of_User("Test_One","987654",'F',1);
		ADT_of_User InternalTestUser_C = new ADT_of_User("Test_Two","987654",'F',2);
		
		InternalTestUser_A.setID(111111);
		InternalTestUser_B.setID(222222);
		InternalTestUser_C.setID(333333);
		
		List<ADT_of_User> UserList = new CopyOnWriteArrayList<ADT_of_User>();
		UserList.add(InternalTestUser_A);
		UserList.add(InternalTestUser_B);
		UserList.add(InternalTestUser_C);
		
		this.UpdateUserDatabase(UserList);
	}
	
	//Save users.
	private void UpdateUserDatabase(List<ADT_of_User> UserList)
	{
		ObjectOutputStream Object_OS = null;
		try
		{
			Object_OS = new ObjectOutputStream(
					new FileOutputStream(
							ServerDataStore.ServerProperty.getProperty("DBpath")));
			Object_OS.writeObject(UserList);
			Object_OS.flush();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			IO_close.Close(Object_OS);
		}
	}
	
	//Load all users.
	public List<ADT_of_User> LoadUsers()
	{
		List<ADT_of_User> UserList = null;
		ObjectInputStream Object_IS = null;
		try
		{
			Object_IS = new ObjectInputStream(
					new FileInputStream(
							ServerDataStore.ServerProperty.getProperty("DBpath")));
			UserList = (List<ADT_of_User>)Object_IS.readObject();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			IO_close.Close(Object_IS);
		}
		return UserList;
	}
	
	//Load a specific user via his id.
	public ADT_of_User LoadOneUser(long ID)
	{
		ADT_of_User ThisUser = null;
		List<ADT_of_User> UserList = LoadUsers();
		for(ADT_of_User user:UserList)
		{
			if(ID==user.getID())
			{
				ThisUser=user;
				break;
			}
		}
		return ThisUser;
	}
	
	//Add a new user.
	public void AddUser(ADT_of_User NewUser)
	{
		List<ADT_of_User> UserList = LoadUsers();
		UserCount = UserList.size();
		NewUser.setID(UserCount+1);
		UserList.add(NewUser);
		UpdateUserDatabase(UserList);
	}
	
	//User log in.
	public ADT_of_User Login(long ID,String Password)
	{
		ADT_of_User ThisUser = null;
		List<ADT_of_User> UserList = LoadUsers();
		for(ADT_of_User user:UserList)
		{
			if(ID==user.getID()&&Password.equals(user.getPassword()))
			{
				ThisUser=user;
				break;
			}
		}
		return ThisUser;
	}
}

/**Nothing Wrong.*/

class IO_close
{
	public static void Close(InputStream IS)
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
	
	public static void Close(OutputStream OS)
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
