/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * ADT_of_User is an auxiliary class to define
 * 
 * the properties and action of abstract users.
 *
 */

package auxiliary;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class ADT_of_User implements Serializable
{
	/**Eclipse creates a serialVersionUID automatically.*/
	private static final long serialVersionUID = -3978407551908932102L;
	
	private long ID;
	private String Nickname;
	private String Password;
	private char Sex;
	private int Profile;
	
	public ADT_of_User(String nickname, String password, char sex, int profile) 
	{
		//super();
		Password = password;
		Sex = sex;
		Profile = profile;
		if (nickname.equals("")||nickname==null)
		{
			Nickname="Unknown¡¾System-Set¡¿";
		}
		else
		{
			Nickname=nickname;
		}
	}

	public ADT_of_User(long iD, String password) 
	{
		//super();
		ID = iD;
		Password = password;
	}

	public long getID() 
	{
		return ID;
	}

	public void setID(long iD) 
	{
		ID = iD;
	}

	public String getNickname() 
	{
		return Nickname;
	}

	public void setNickname(String nickname) 
	{
		Nickname = nickname;
	}

	public String getPassword() 
	{
		return Password;
	}

	public void setPassword(String password) 
	{
		Password = password;
	}

	public char getSex() 
	{
		return Sex;
	}

	public void setSex(char sex) 
	{
		Sex = sex;
	}

	public int getProfile() 
	{
		return Profile;
	}

	public void setProfile(int profile) 
	{
		Profile = profile;
	}
	
	public ImageIcon getImageIcon()
	{
		ImageIcon Image_Icon = new ImageIcon("images/"+Profile+".png");
        return Image_Icon;
	}
	
	@Override
	public int hashCode()
	{
		final int hashNum=31;
		int result=1;
		
		result=hashNum*result+Profile;
		result=hashNum*result+(int)(ID^(ID>>32));
		result=hashNum*result+
				(
						(Nickname==null)?0:Nickname.hashCode()
				);
		result=hashNum*result+
				(
						(Password==null)?0:Password.hashCode()
				);
		result=hashNum*result+Sex;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this==obj)
		{
			return true;
		}
		if(obj==null)
		{
			return false;
		}
		if(this.getClass()!=obj.getClass())
		{
			return false;
		}
		ADT_of_User copy=(ADT_of_User)obj;
		if(ID!=copy.ID||Profile!=copy.Profile||Sex!=copy.Sex)
		{
			return false;
		}
		if(Nickname==null)
		{
			if(copy.Nickname!=null)
			{
				return false;
			}
		}
		else if(!Nickname.equals(copy.Nickname))
		{
			return false;
		}
		if(Password==null)
		{
			if(copy.Password!=null)
			{
				return false;
			}
		}
		else if(!Password.equals(copy.Password))
		{
			return false;
		}
		return true;
	}
	
	@Override 
	public String toString()
	{
		return this.getClass().getName()
                + "[UserID=" + this.ID
                + ",Password=" + this.Password
                + ",Nickname=" + this.Nickname
                + ",ProfileNum=" + this.Profile
                + ",Sex=" + this.Sex
                + "]";
	}
}

/**Nothing Wrong.*/
