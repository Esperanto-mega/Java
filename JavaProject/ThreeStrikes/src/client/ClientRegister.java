/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Client Register is the class to display
 * 
 * a page of register an account.
 *
 */

package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import auxiliary.ADT_of_User;
import auxiliary.Plea;
import auxiliary.Reply;
import auxiliary.ReplyPhase;

public class ClientRegister extends JFrame
{
	private static final long serialVersionUID = 7171383032964745287L;

	//Nickname.
	private JTextField Nickname;
	
	//Password.
	private JPasswordField Password;
	private JPasswordField ConfirmPassword;
	
	//Profile.
	private JComboBox Profile;
	
	//Sex.
	private JRadioButton Male;
	private JRadioButton Female;
	
	//Finish.
	private JButton OK;
	
	//Abandon
	private JButton Cancel;
	
	//Rewrite.
	private JButton Clear;
	
	public ClientRegister()
	{
		Initialize();
		setVisible(true);
	}

	public void Initialize()
	{
		getContentPane().setBackground(new Color(179, 220, 241));
		setTitle("Sign Up Account");
		setBounds((ClientDataStore.screenSize.width-400)/2,
				(ClientDataStore.screenSize.height-300)/2,400,300);
		getContentPane().setLayout(null);
		setResizable(false);
		
		JLabel nickname = new JLabel("Nickname:");
		nickname.setBounds(24,36,65,17);
		getContentPane().add(nickname);
		
		Nickname = new JTextField();
		Nickname.setBounds(90, 34, 110, 22);
		getContentPane().add(Nickname);
		
		JLabel password = new JLabel("Password:");
		JLabel confirmPassword = new JLabel("Confirm:");
		password.setBounds(24,72,65,17);
		confirmPassword.setBounds(24,107,65,17);
		getContentPane().add(password);
		getContentPane().add(confirmPassword);
		
		Password = new JPasswordField();
		ConfirmPassword = new JPasswordField();
		Password.setBounds(90,70,110,22);
		ConfirmPassword.setBounds(90,105,110,22);
		getContentPane().add(Password);
		getContentPane().add(ConfirmPassword);
		
		JLabel sex = new JLabel("Sex:");
		sex.setBounds(210,36,30,17);
		getContentPane().add(sex);
		
		Male = new JRadioButton("Male",true);
		Female = new JRadioButton("Female");
		Male.setBounds(248,31,60,25);
		Female.setBounds(310, 31, 75, 25);
		getContentPane().add(Male);
		getContentPane().add(Female);
		ButtonGroup buttonGroup = new ButtonGroup();
		Male.setOpaque(true);
		Female.setOpaque(true);
		Male.setBackground(new Color(179, 220, 241));
		Female.setBackground(new Color(179, 220, 241));
		buttonGroup.add(Male);
		buttonGroup.add(Female);
		
		JLabel profile = new JLabel("Profile:");
		profile.setBounds(210,72,55,17);
		getContentPane().add(profile);
		
		Profile = new JComboBox();
		Profile.setBounds(278,70,65,45);
		Profile.setMaximumRowCount(3);
		for(int i=0;i<11;++i)
		{
			Profile.addItem(new ImageIcon("D:\\NewDesktop\\Chat_Room-master\\ChatRoom\\images\\" + i + ".png"));
		}
		Profile.setSelectedIndex(0);
		getContentPane().add(Profile);
		
		OK = new JButton("OK");
		Cancel = new JButton("Cancel");
		Clear = new JButton("Clear");
		OK.setBounds(24,160,76,30);
		Cancel.setBounds(24,210,76,28);
		Clear.setBounds(124,160,76,30);
		getContentPane().add(OK);
		getContentPane().add(Cancel);
		getContentPane().add(Clear);
		/**Fourth Wrong : forget to add buttons.*/
		
		/**Button Action Listener.*/
		OK.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				// TODO 自动生成的方法存根
				if(Nickname.getText().length()==0||Password.getPassword().length==0)
				{
					JOptionPane.showMessageDialog(ClientRegister.this, "Nickname and password must be filled.");
				}
				else if(!(new String(Password.getPassword()).equals(new String(ConfirmPassword.getPassword()))))
				{
					JOptionPane.showMessageDialog(ClientRegister.this, "Passwords should be the same.");
					Password.setText("");
					ConfirmPassword.setText("");
					Password.requestFocusInWindow();
				}
				else
				{
					char SEX = Male.isSelected()?'M':'F';
					int PROFILE = Profile.getSelectedIndex();
					ADT_of_User newUser = new ADT_of_User(Nickname.getText(),new String(Password.getPassword()),SEX,PROFILE);
					
					try
					{
						ClientRegister.this.RealRegis(newUser);
					}
					catch(IOException ea)
					{
						ea.printStackTrace();
					}
					catch(ClassNotFoundException eb)
					{
						eb.printStackTrace();
					}
				}
			}
		});
		
		Cancel.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				// TODO 自动生成的方法存根
				ClientRegister.this.dispose();
			}
		});
		
		this.addWindowListener(new WindowAdapter() 
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				// TODO 自动生成的方法存根
				ClientRegister.this.dispose();
			}
		});
		
		Clear.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(final ActionEvent e)
			{
				// TODO 自动生成的方法存根
				String Reset="";
				Nickname.setText(Reset);
				Password.setText(Reset);
				ConfirmPassword.setText(Reset);
				Nickname.requestFocusInWindow();
			}
		});
	}
	
	private void RealRegis(ADT_of_User newUser) throws IOException,ClassNotFoundException
	{
		Plea plea = new Plea();
		plea.setAction("Register");
		plea.setData("User", newUser);
		
		Reply reply  = ClientToServer.sendMessage(plea);
		ReplyPhase phase = reply.getPhase();
		
		switch(phase)
		{
		case SUCCESS:
			ADT_of_User tempUser = (ADT_of_User)reply.getData("User");
			JOptionPane.showMessageDialog(ClientRegister.this, 
					"Wow!You have got yourself's account : "+tempUser.getID(),
					"Success Regiter.",JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(false);
			break;
		default:
			JOptionPane.showMessageDialog(ClientRegister.this, 
					"What a pity!Something wrong has happened.","ERROR",
					JOptionPane.ERROR_MESSAGE);
		}	
	}	
}

/**Nothing Wrong.*/