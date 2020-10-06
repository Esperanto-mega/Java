/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Client User Cell Renderer is the class that represents
 * 
 * the single user entity.
 *
 */
package auxiliary;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import auxiliary.ADT_of_User;

public class UserCellRenderer extends JLabel implements ListCellRenderer
{
	private static final long serialVersionUID = 4390793858305735219L;

	@Override
	public Component getListCellRendererComponent(
			JList list, Object obj, int index, boolean isChosen, boolean hasFocus) 
	{
		// TODO 自动生成的方法存根
		ADT_of_User Abstract_User = (ADT_of_User)obj;
		String Name = Abstract_User.getNickname()+
				"【"+Abstract_User.getID()+"】";
		setText(Name);
		setIcon(Abstract_User.getImageIcon());
		if(isChosen)
		{
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		}
		else
		{
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		setOpaque(true);
		
		return this;
	}
}

/**Nothing Wrong.*/
