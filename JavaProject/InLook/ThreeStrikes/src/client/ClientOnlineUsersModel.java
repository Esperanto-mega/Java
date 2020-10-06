/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * This class is an UI model for online users'
 * 
 * display in the chat room page.
 *
 */
package client;

import java.util.List;

import javax.swing.AbstractListModel;

import auxiliary.ADT_of_User;

public class ClientOnlineUsersModel extends AbstractListModel 
{

	/**Eclipse creates a serialVersionUID automatically.*/
	private static final long serialVersionUID = -3043640290296787803L;
	
	private List<ADT_of_User> onlineUsers;

	public ClientOnlineUsersModel(List<ADT_of_User> onlineUsers) 
	{
		//super();
		this.onlineUsers = onlineUsers;
	}

	@Override
	public Object getElementAt(int arg0) 
	{
		// TODO 自动生成的方法存根
		return onlineUsers.get(arg0);
	}

	@Override
	public int getSize() 
	{
		// TODO 自动生成的方法存根
		return onlineUsers.size();
	}
	
	public void addUser(Object obj)
	{
		if(onlineUsers.contains(obj))
		{
			return;
		}
		int index = onlineUsers.size();
		onlineUsers.add((ADT_of_User)obj);
		
		fireIntervalAdded(this, index, index);
		/**The abstract list model subclass must call this method after 
		 * one or more elements have been added to the model. The 
		 * new element is specified by the intervals that are closed by 
		 * index0 and index1, including the end point. Note that index0 
		 * does not have to be less than or equal to index1.*/
	}
	
	public boolean removeUser(Object obj)
	{
		int index = onlineUsers.indexOf(obj);
		if(index>=0)
		{
			 fireIntervalRemoved(this, index, index);
		}
		return onlineUsers.remove(obj);
	}

	public List<ADT_of_User> getOnlineUsers() 
	{
		return onlineUsers;
	}
}

/**Nothing Wrong.*/
//Nothing Wrong.