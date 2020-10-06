package Client;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class ClientLauncher 
{

	public static void main(String[] args) 
	{
		// TODO 自动生成的方法存根
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try
		{
			 String LookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
	         UIManager.setLookAndFeel(LookAndFeel);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		new ClientLogin();
	}
}
