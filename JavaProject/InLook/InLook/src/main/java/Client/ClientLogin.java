package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
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

public class ClientLogin extends JFrame
{
	private static final long serialVersionUID = -8170324176773770819L;
	
	private JTextField Account_IN;
	private JPasswordField Password_IN;
	
	public ClientLogin()
	{
		Initialize();
		setVisible(true);
	}
	
	private void Initialize()
	{
		final Font myFont = new Font("Arial Rounded MT Bold",Font.BOLD,20);
		
		this.setTitle("InLook");
		this.setSize(400,600);
		
		int x = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int y = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		x = (x-this.getWidth())/2;
		y = (y-this.getHeight())/2;
		this.setLocation(x, y);
		
		this.setResizable(false);
		
		Icon Back = new ImageIcon("D:\\Eclipse_Workspace\\InLook\\Images\\InLook.png");
		JLabel BackLabel = new JLabel(Back);
		BackLabel.setPreferredSize(new Dimension(400,200));
		this.add(BackLabel,BorderLayout.NORTH);
		
		JPanel MainLoginPanel=new JPanel();
		Border border=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		MainLoginPanel.setBorder(BorderFactory.createTitledBorder(border,"Login Mail",TitledBorder.CENTER,TitledBorder.TOP));
		this.add(MainLoginPanel,BorderLayout.CENTER);
		MainLoginPanel.setLayout(null);
		MainLoginPanel.setOpaque(true);
		MainLoginPanel.setBackground(new Color(179, 220, 241));
		
		JLabel Account = new JLabel("Account:");
		JLabel Password = new JLabel("Password:");
		JButton LoginButton = new JButton("Enter");
		
		Account.setFont(myFont);
		Account.setBounds(25, 80, 150, 30);
		Account_IN = new JTextField();
		Account_IN.setBounds(145, 80, 220, 30);
		Account_IN.setFont(myFont);
		Account_IN.requestFocusInWindow();
		
		Password.setFont(myFont);
		Password.setBounds(25, 200, 150, 30);
		Password_IN = new JPasswordField();
		Password_IN.setBounds(145, 200, 220, 30);
		Password_IN.setFont(myFont);
		
		LoginButton.setBounds(140,300, 120, 40);
		LoginButton.setFont(myFont);
		
		MainLoginPanel.add(Account);
		MainLoginPanel.add(Account_IN);
		MainLoginPanel.add(Password);
		MainLoginPanel.add(Password_IN);
		MainLoginPanel.add(LoginButton);
		
		LoginButton.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				// TODO 自动生成的方法存根
				Login();
			}
		});
		
		//Close the window.
	     this.addWindowListener(new WindowAdapter() 
	     {
			@Override
			public void windowClosing(WindowEvent e) 
			{
				// TODO 自动生成的方法存根
				Exit();
			}
		});
	}
	
	
	
	private void Login()
	{
		//Only simple verify.
		if(Account_IN.getText().length()==0||
				Password_IN.getPassword().length==0)
		{
			JOptionPane.showMessageDialog(ClientLogin.this, 
					"Account and password must be filled.","ERROR",
					JOptionPane.ERROR_MESSAGE);
			if(Account_IN.getText().length()!=0)
			{
				Password_IN.requestFocusInWindow();
			}
			else
			{
				Account_IN.requestFocusInWindow();
			}
			return;
		}
		
		String pattern = "^(\\w*|\\d*)@\\w*.(com|cn)$";
		if(!(Account_IN.getText().matches(pattern)))
		{
			JOptionPane.showMessageDialog(ClientLogin.this,
					"Account is not valid.","ERROR",
					JOptionPane.ERROR_MESSAGE);
			Account_IN.requestFocusInWindow();
			return;
		}
		ClientLogin.this.dispose();
		new ClientMainPage(Account_IN.getText(),new String(Password_IN.getPassword()));
	}
	
	private void Exit()
	{
		int inquire = JOptionPane.showConfirmDialog(ClientLogin.this,
				"Sure to leave InLook?","Leave InLook.",
				JOptionPane.YES_NO_OPTION);
		if(inquire==JOptionPane.YES_OPTION)
		{
			System.exit(0);
		}
		else
		{
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}
	}
	
}
