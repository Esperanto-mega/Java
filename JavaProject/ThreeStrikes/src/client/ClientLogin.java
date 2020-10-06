/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Client Login is a page for user to choose
 * 
 * between use an existed account to log in
 * 
 * and register a brand new account whose
 * 
 * nickname,profile and sex can be set by 
 * 
 * users' own.
 *
 */

package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import auxiliary.ADT_of_User;
import auxiliary.Plea;
import auxiliary.Reply;
import auxiliary.ReplyPhase;

public class ClientLogin extends JFrame
{
	/**Eclipse creates a serialVersionUID automatically.*/
	private static final long serialVersionUID = -3924675754480083668L;
	
	private JTextField ID_Input;
	private JPasswordField Password_Input;
	
	public ClientLogin()
	{
		Initialize();
		setVisible(true);
	}
	
	public void Initialize()
	{
		Font myFont = new Font("Arial Rounded MT Bold",Font.BOLD,18);
		
		this.setTitle("ThreeStrikes");
		this.setSize(430,360);
		
		//Client-Log-in page appears in the center of screen.
		int x=(int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int y=(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		x=(x-this.getWidth())/2;
		y=(y-this.getHeight())/2;
		this.setLocation(x,y);
		this.setResizable(false);
		
		//Logo for catch attention.
		Icon Logo=new ImageIcon("D:\\NewDesktop\\Chat_Room-master\\ChatRoom\\images\\logo.png");
		JLabel LogoLabel=new JLabel(Logo);
		LogoLabel.setPreferredSize(new Dimension(430,150));
		this.add(LogoLabel,BorderLayout.NORTH);
		
		//Panel for log in.
		JPanel MainLoginPanel=new JPanel();
		Border border=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		MainLoginPanel.setBorder(BorderFactory.createTitledBorder(border,"Login Info.",TitledBorder.CENTER,TitledBorder.TOP));
		this.add(MainLoginPanel,BorderLayout.CENTER);
		MainLoginPanel.setLayout(null);
		MainLoginPanel.setOpaque(true);
		MainLoginPanel.setBackground(new Color(179, 220, 241));
		
		JLabel ID_Label=new JLabel("Identity:");
		JLabel Password_Label=new JLabel("Password:");
		ID_Label.setFont(myFont);
		Password_Label.setFont(myFont);
		
		/**setBounds(int x,int y,int width,int height)*/
		//ID_Label.
		ID_Label.setBounds(80,40,110,30);
		MainLoginPanel.add(ID_Label);
		ID_Input=new JTextField();
		ID_Input.setBounds(190,40,150,30);
		ID_Input.requestFocusInWindow();
		MainLoginPanel.add(ID_Input);
		
		//Password_Label.
		Password_Label.setBounds(80,80,110,30);
		MainLoginPanel.add(Password_Label);
		Password_Input=new JPasswordField();
		Password_Input.setBounds(190,80,150,30);
		MainLoginPanel.add(Password_Input);
		
		//Some buttons with special function.
		JPanel ButtonPanel=new JPanel();
		this.add(ButtonPanel,BorderLayout.SOUTH);
		/**Third Wrong : should be 'SOUTH'*/
		ButtonPanel.setLayout(new BorderLayout());
		ButtonPanel.setBorder(new EmptyBorder(2,8,4,8));
		
		JButton RegisterButton = new JButton("Sign Up");
		JButton LoginButton=new JButton("Sign in");
		ButtonPanel.add(RegisterButton,BorderLayout.WEST);
		ButtonPanel.add(LoginButton,BorderLayout.EAST);
		ButtonPanel.setOpaque(true);
		ButtonPanel.setBackground(new Color(179, 220, 241));
		
		//Exit.
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				Plea plea=new Plea();
				plea.setAction("Exit");
				try
				{
					ClientToServer.sendMessage(plea);
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
				finally
				{
					System.exit(0);
				}
			}
		});
		
		RegisterButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO 自动生成的方法存根
				new ClientRegister();
			}
		});
		
		LoginButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO 自动生成的方法存根
				Login();
			}
		});
		
	}
	
	//@SuppressWarnings("unchecked")
	private void Login()
	{
		//Check ID and password.
		if(ID_Input.getText().length()==0||
				Password_Input.getPassword().length==0)
		{
			JOptionPane.showMessageDialog(ClientLogin.this, 
					"Account and password must be filled.",
					"ERROR",JOptionPane.ERROR_MESSAGE);
			ID_Input.requestFocusInWindow();
			return;
		}
		
		if(!ID_Input.getText().matches("^\\d*$"))
		{
			JOptionPane.showMessageDialog(ClientLogin.this,
					"Account only consists of numbers.",
					"ERROR",JOptionPane.ERROR_MESSAGE);
			ID_Input.requestFocusInWindow();
			return;
		}
		
		Plea plea = new Plea();
		plea.setAction("Login");
		plea.setData("ID", ID_Input.getText());
		plea.setData("Password", new String(Password_Input.getPassword()));
		
		Reply reply = null;
		try
		{
			reply = ClientToServer.sendMessage(plea);
		}
		catch(IOException et)
		{
			et.printStackTrace();
		}
		
		if(reply.getPhase()==ReplyPhase.SUCCESS)
		{
			ADT_of_User thisUser = (ADT_of_User)reply.getData("User");
			if(thisUser!=null)
			{
				ClientDataStore.thisUser=thisUser;
				ClientDataStore.onlineUsers=(List<ADT_of_User>)reply.getData("OnlineUsers");
				ClientLogin.this.dispose();
				new ClientChat();
			}
			else
			{
				String info = (String)reply.getData("Message");
				JOptionPane.showMessageDialog(ClientLogin.this,
						info,"ERROR",JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(ClientLogin.this,
					"What a pity!Something wrong has happened.","ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}

/**Nothing Wrong.*/