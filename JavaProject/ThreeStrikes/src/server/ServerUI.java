package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import auxiliary.ADT_of_User;
import auxiliary.ProcessUnit;
import auxiliary.ServiceForUser;

public class ServerUI extends JFrame
{
	private static final long serialVersionUID = -8174032924353178590L;

	private JTextField BroadCast;
	private JTable OnlineUsersTable;
	private JTable RegistedUsersTable;
	
//	public static void main(String[] a)
//	{
//		System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
//	}

	public ServerUI()
	{
		Initialize();
		LoadUsers();
		setVisible(true);
	}
	
	public void Initialize()
	{
		Font myFont = new Font("Arial Rounded MT Bold",Font.BOLD,20);
		setTitle("ThreeStrikesServer");
		int width = 900;
		int height = 600;
		this.setBackground(new Color(179, 220, 241));
		setBounds((ServerDataStore.ScreenSize.width-width)/2,(ServerDataStore.ScreenSize.height-height)/2,width,height);
		setLayout(new BorderLayout());
		setResizable(false);
		
		JPanel LeftMost = new JPanel();
		LeftMost.setLayout(new BorderLayout());
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
		LeftMost.setBorder(BorderFactory.createTitledBorder(border,"Server Priority",
				TitledBorder.LEFT,TitledBorder.TOP));
		LeftMost.setOpaque(true);
		LeftMost.setBackground(new Color(179, 220, 241));
		
		JPanel MainPanel = new JPanel();
		
		JPanel UtilPanel = new JPanel();
		UtilPanel.setLayout(new BorderLayout());
		
		JButton ExitButton = new JButton(" Exit");
		ExitButton.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,15));
		MainPanel.add(ExitButton);
		
//		JPanel Space = new JPanel();
//		Space.setSize(30, 60);
//		MainPanel.add(Space);
		
		JPanel BroadPanel = new JPanel();
		JLabel SystemNotice = new JLabel("System Notice");
		SystemNotice.setFont(myFont);
		BroadPanel.add(SystemNotice);
		
		BroadCast = new JTextField(15);
		JButton BroadCastButton = new JButton("BroadCast");
		BroadCastButton.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,15));
		
		ActionListener broadcast = new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO 自动生成的方法存根
				try
				{
					BroadCastMessage();
				}
				catch(IOException ex)
				{
					ex.printStackTrace();
				}
			}
		};

		BroadCast.addActionListener(broadcast);
		BroadCastButton.addActionListener(broadcast);
	
		BroadPanel.add(BroadCast);
		
		MainPanel.add(BroadCastButton);
		
		JLabel OnlineCount = new JLabel(" Now online users : "+ServerDataStore.OnlineUserMap.size());
		OnlineCount.setFont(myFont);
		
		MainPanel.setOpaque(true);
		MainPanel.setBackground(new Color(179, 220, 241));
		BroadPanel.setOpaque(true);
		BroadPanel.setBackground(new Color(179, 220, 241));
		OnlineCount.setOpaque(true);
		OnlineCount.setBackground(new Color(179, 220, 241));
		
		UtilPanel.add(MainPanel,BorderLayout.NORTH);
		UtilPanel.add(BroadPanel,BorderLayout.CENTER);
		UtilPanel.add(OnlineCount,BorderLayout.SOUTH);
		
		LeftMost.add(UtilPanel,BorderLayout.NORTH);
		Icon serverUI = new ImageIcon("D:\\NewDesktop\\Chat_Room-master\\ChatRoom\\images\\serverUI.png");
		JLabel serverUILabel = new JLabel(serverUI);
		serverUILabel.setPreferredSize(new Dimension(280,520));
		LeftMost.add(serverUILabel,BorderLayout.CENTER);
//		JPanel NULL = new JPanel();
//		LeftMost.add(NULL,BorderLayout.WEST);
//		LeftMost.add(NULL,BorderLayout.EAST);
		
		this.add(LeftMost,BorderLayout.WEST);
		
		OnlineUsersTable = new JTable(ServerDataStore.OnlineUserUI);
		RegistedUsersTable = new JTable(ServerDataStore.RegistedUserUI);
//		OnlineUsersTable.setOpaque(true);
//		OnlineUsersTable.setBackground(new Color(179, 220, 241));
//		RegistedUsersTable.setOpaque(true);
//		RegistedUsersTable.setBackground(new Color(179, 220, 241));
		
		JPopupMenu PopupMenu = GetPopupMenu();
		OnlineUsersTable.setComponentPopupMenu(PopupMenu);
		
		JTabbedPane TabbedPane = new JTabbedPane();
		TabbedPane.addTab("OnlineUsersList", new JScrollPane(OnlineUsersTable));
		TabbedPane.addTab("RegistedUsersList", new JScrollPane(RegistedUsersTable));
		TabbedPane.setTabComponentAt(0, new JLabel("OnlineUsersList"));
		TabbedPane.setOpaque(true);
		TabbedPane.setBackground(new Color(179, 220, 241));
		this.add(TabbedPane,BorderLayout.CENTER);
		/**Sixth Wrong : forget to add 'TabbedPane'.*/
		
		final JLabel TimeLabel = new JLabel("",SwingConstants.RIGHT);
		TimeLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		TimeLabel.setOpaque(true);
		TimeLabel.setBackground(new Color(179, 220, 241));
		
		TimerTask thisTask = new TimerTask()
				{
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
					@Override
					public void run() 
					{
						// TODO 自动生成的方法存根
						TimeLabel.setText("Now:"+dateFormat.format(new Date()));
						TimeLabel.setFont(myFont);
					}
			
				};
		new Timer().scheduleAtFixedRate(thisTask, 0, 1000);
		this.add(TimeLabel,BorderLayout.SOUTH);
		
		this.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e)
			{
				Exit();
			}
		});
		
		ExitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO 自动生成的方法存根
				Exit();
			}
		});
	}
	
	private void LoadUsers()
	{
		List<ADT_of_User> UsersList = new ServiceForUser().LoadUsers();
		for(ADT_of_User user:UsersList)
		{
			ServerDataStore.RegistedUserUI.add(new String[]
					{
							String.valueOf(user.getID()),
							user.getPassword(),
							user.getNickname(),
							String.valueOf(user.getSex())
					});
		}
	}
	
	private void BroadCastMessage() throws IOException
	{
		ProcessUnit.Broadcast(BroadCast.getText());
		BroadCast.setText("");
	}
	
	private JPopupMenu GetPopupMenu()
	{
		JPopupMenu PopupMenu = new JPopupMenu();
		JMenuItem SendItem = new JMenuItem("SendMessage");
		JMenuItem KickOutItem = new JMenuItem("KickOut");
		
		SendItem.setActionCommand("SendMessage");
		KickOutItem.setActionCommand("KickOut");
		
		ActionListener Do = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO 自动生成的方法存根
				String command = e.getActionCommand();
				ConductCommand(command);
			}
		};
		
		SendItem.addActionListener(Do);
		KickOutItem.addActionListener(Do);
		PopupMenu.add(SendItem);
		PopupMenu.add(KickOutItem);
		
		return PopupMenu;
	}
	
	private void ConductCommand(String command)
	{
		final int SelectedRow = OnlineUsersTable.getSelectedRow();
		String UserID = (String)OnlineUsersTable.getValueAt(SelectedRow, 0);
		System.out.println(UserID);
		if(SelectedRow==-1)
		{
			JOptionPane.showMessageDialog(this,"Must select an user.");
			return;
		}
		
		if(command.equals("KickOut"))
		{
			try
			{
				ProcessUnit.Remove(ServerDataStore.OnlineUserMap.get(Long.valueOf(UserID)));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		else if (command.equals("SendMessage"))
		{
			final JDialog Dialog = new JDialog(this,true);
			final JTextField TextField = new JTextField(20);
			JButton Button = new JButton("Send");
			Dialog.setLayout(new FlowLayout());
			Dialog.setSize(250, 150);
			Dialog.add(TextField);
			Dialog.add(Button);
			
			Button.addActionListener(new ActionListener()
			{
				
				@Override
				public void actionPerformed(ActionEvent e)
				{
					// TODO 自动生成的方法存根
					System.out.println("Server has sent a message.");
					String SystemMessage = TextField.getText();
					try
					{
						ProcessUnit.SystemChat(SystemMessage, ServerDataStore.OnlineUserMap.get(Long.valueOf(UserID)));
					}
					catch(IOException  ex)
					{
						ex.printStackTrace();
					}
					TextField.setText("");
					Dialog.dispose();
				}
			});
			Dialog.setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Unknown Command:"+command);
		}
		SwingUtilities.updateComponentTreeUI(OnlineUsersTable);
	}
	
	private void Exit()
	{
		int Choice = JOptionPane.showConfirmDialog(ServerUI.this, "Sure to turn off server?\nAll the client will be disconnected.","Exit",JOptionPane.YES_NO_OPTION);
		
		if(Choice==JOptionPane.YES_OPTION)
		{
			System.exit(0);
		}
		else
		{
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		}
	}
}

/**Nothing Wrong.*/