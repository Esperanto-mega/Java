/**
 * @author Esperanto.
 * @since 2020.6.30
 * @version 1.0
 * 
 * Client Chat is the class that initialize
 * 
 * the UI of chat room.
 *
 */
package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import auxiliary.ADT_of_User;
import auxiliary.FileData;
import auxiliary.Message;
import auxiliary.Plea;
import auxiliary.ShakeFrame;
import auxiliary.UserCellRenderer;

public class ClientChat extends JFrame
{
	private static final long serialVersionUID = -7807521818281721187L;
	
	//System tips.
	private JLabel TipLabel;
	
	//This user's information.
	private JLabel ThisUserLabel;
	
	//Group chat messages area.
	public static JTextArea GroupMessageArea;
	
	//Message edit area.
	public static JTextArea RemainToSendArea;
	
	//List of online users.
	public static JList OnlineUsersList;
	
	//Current online users' number.
	public static JLabel OnlineUsersCount;
	
	//File wait to be sent.
	public static FileData FileToSend;
	
	//Chat privately check box.
	public JCheckBox One_OneChat;
	
	public ClientChat()
	{
		Initialize();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void Initialize()
	{
		setTitle("ThreeStrikes");
		setSize(630,500);
		setResizable(false);
		
		 int x = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	     int y = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	     x=(x-this.getWidth())/2;
	     y=(y-this.getHeight())/2;
	     this.setLocation(x,y);
	     
	     //Main chat panel.
	     JPanel MainChatPanel = new JPanel();
	     MainChatPanel.setLayout(new BorderLayout());
	     
	     //Users list panel.
	     JPanel UserPanel = new JPanel();
	     UserPanel.setLayout(new BorderLayout());
	     
	     //Split MainChatPanel and OnlineUsersListPanel.
	     JSplitPane SplitPane_a = 
	    		 new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
	    				 MainChatPanel,UserPanel);
	     SplitPane_a.setDividerLocation(380);
	     SplitPane_a.setDividerSize(10);
	     SplitPane_a.setOneTouchExpandable(true);
	     SplitPane_a.setEnabled(false);
	     this.add(SplitPane_a,BorderLayout.CENTER);
	     
	     //Message display panel.
	     JPanel MessageDisplayPanel = new JPanel();
	     MessageDisplayPanel.setLayout(new BorderLayout());
	     
	     //Type in message panel.
	     JPanel TypeInPanel = new JPanel();
	     TypeInPanel.setLayout(new BorderLayout());
	     
	     //Split MessageDisplayPanel and TypeInPanel.
	     JSplitPane SplitPane_b = 
	    		 new JSplitPane(JSplitPane.VERTICAL_SPLIT,
	    				 MessageDisplayPanel,TypeInPanel);
	     SplitPane_b.setDividerLocation(300);
	     SplitPane_b.setDividerSize(1);
	     MainChatPanel.add(SplitPane_b,BorderLayout.CENTER);
	     
	     TipLabel = new JLabel("【Now:Group Chatting】");
	     TipLabel.setOpaque(true);
	     TipLabel.setBackground(new Color(179, 220, 241));
	     MessageDisplayPanel.add(TipLabel,BorderLayout.NORTH);
	     
	     GroupMessageArea = new JTextArea();
	     GroupMessageArea.setLineWrap(true);
	     MessageDisplayPanel.add(new JScrollPane(GroupMessageArea,
	    		 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	     /**First Error : miss a bracket.*/
	     
	     JPanel FunctionPanel = new JPanel();
	     FunctionPanel.setLayout(new BorderLayout());
	     TypeInPanel.add(FunctionPanel,BorderLayout.NORTH);
	     
	     //Function buttons panel.
	     JPanel ButtonPanel = new JPanel();
	     ButtonPanel.setOpaque(true);
	     ButtonPanel.setBackground(new Color(179, 220, 241));
	     ButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	     FunctionPanel.add(ButtonPanel,BorderLayout.CENTER);
	     
	     //Private chat choice.
	     One_OneChat = new JCheckBox("One on one");
	     One_OneChat.setOpaque(true);
	     One_OneChat.setBackground(new Color(179, 220, 241));
	     FunctionPanel.add(One_OneChat,BorderLayout.EAST);
	     
	     //Font style button.
	     String ButtonAddr="D:\\NewDesktop\\Chat_Room-master\\ChatRoom\\images\\Font.png";
	     JButton FontButton = 
	    		 new JButton( new ImageIcon(ButtonAddr));
	     FontButton.setMargin(new Insets(0,0,0,0));
	     FontButton.setToolTipText("Modify Font Style.");
	     ButtonPanel.add(FontButton);
	     
	     //Emoji button.
	     ButtonAddr = "D:\\NewDesktop\\Chat_Room-master\\ChatRoom\\images\\Emoji.png";
	     JButton EmojiButton = 
	    		 new JButton(new ImageIcon(ButtonAddr));
	     EmojiButton.setMargin(new Insets(0,0,0,0));
	     EmojiButton.setToolTipText("Choose an Emoji.");
	     ButtonPanel.add(EmojiButton);
	     
	     //Shaking button.
	     ButtonAddr = "D:\\NewDesktop\\Chat_Room-master\\ChatRoom\\images\\Shaking.png";
	     JButton ShakingButton = 
	    		 new JButton(new ImageIcon(ButtonAddr));
	     ShakingButton.setMargin(new Insets(0,0,0,0));
	     ShakingButton.setToolTipText("Nudge your friend.");
	     ButtonPanel.add(ShakingButton);
	     
	     //Send file button.
	     ButtonAddr = "D:\\NewDesktop\\Chat_Room-master\\ChatRoom\\images\\File.png";
	     JButton SendFileButton = 
	    		 new JButton(new ImageIcon(ButtonAddr));
	     SendFileButton.setMargin(new Insets(0,0,0,0));
	     SendFileButton.setToolTipText("Send your friend a file.");
	     ButtonPanel.add(SendFileButton);
	     
	     //Voice call button.
	     ButtonAddr = "D:\\NewDesktop\\Chat_Room-master\\ChatRoom\\images\\VoiceCall.png";
	     JButton VoiceCallButton = 
	    		 new JButton(new ImageIcon(ButtonAddr));
	     VoiceCallButton.setMargin(new Insets(0,0,0,0));
	     VoiceCallButton.setToolTipText("Voice Call.");
	     ButtonPanel.add(VoiceCallButton);
	     
	     //Video call button.
	     ButtonAddr = "D:\\NewDesktop\\Chat_Room-master\\ChatRoom\\images\\VideoCall.png";
	     JButton VideoCallButton = 
	    		 new JButton(new ImageIcon(ButtonAddr));
	     VideoCallButton.setMargin(new Insets(0,0,0,0));
	     VideoCallButton.setToolTipText("Video Call.");
	     ButtonPanel.add(VideoCallButton);
	     
	     //Video call button.
	     ButtonAddr = "D:\\NewDesktop\\Chat_Room-master\\ChatRoom\\images\\ScreenShot.png";
	     JButton ScreenShotButton = 
	    		 new JButton(new ImageIcon(ButtonAddr));
	     ScreenShotButton.setMargin(new Insets(0,0,0,0));
	     ScreenShotButton.setToolTipText("Screen Shot.");
	     ButtonPanel.add(ScreenShotButton);
	     
	     //Remain to send area.
	     RemainToSendArea = new JTextArea();
	     RemainToSendArea.setLineWrap(true);
	     TypeInPanel.add(new JScrollPane(RemainToSendArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	     
	     //Send or exit panel.
	     JPanel UtilPanel = new JPanel();
	     UtilPanel.setOpaque(true);
	     UtilPanel.setBackground(new Color(179, 220, 241));
	     UtilPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	     this.add(UtilPanel,BorderLayout.SOUTH);
	     
	     //Exit button.
	     JButton ExitButton = new JButton("Exit");
	     ExitButton.setToolTipText("Leave the chat room.");
	     UtilPanel.add(ExitButton);
	     
	     //Send button.
	     JButton SendButton = new JButton("Send");
	     SendButton.setToolTipText("Send your message.");
	     UtilPanel.add(SendButton);
	     
	     TypeInPanel.add(UtilPanel,BorderLayout.SOUTH);
	     
	     //Online users list panel.
	     JPanel OnlineUsersListPanel = new JPanel();
	     OnlineUsersListPanel.setLayout(new BorderLayout());
	     OnlineUsersCount = new JLabel(" Online Users List 【1】");
	     OnlineUsersCount.setOpaque(true);
	     OnlineUsersCount.setBackground(new Color(179, 220, 241));
	     OnlineUsersListPanel.add(OnlineUsersCount,BorderLayout.NORTH);
	     
	     //Current user panel.
	     JPanel ThisUserPanel = new JPanel();
	     ThisUserPanel.setOpaque(true);
	     ThisUserPanel.setBackground(new Color(179, 220, 241));
	     ThisUserPanel.setLayout(new BorderLayout());
	     ThisUserPanel.add(new JLabel("【Me】"),BorderLayout.NORTH);
	     
	     //Split online users list panel and current user panel.
	     JSplitPane SplitPane_c = 
	    		 new JSplitPane(JSplitPane.VERTICAL_SPLIT,
	    				 OnlineUsersListPanel,ThisUserPanel);
	     SplitPane_c.setDividerLocation(340);
	     SplitPane_c.setDividerSize(1);
	     UserPanel.add(SplitPane_c,BorderLayout.CENTER);
	     
	     ClientDataStore.UI_Model = 
	    		 new ClientOnlineUsersModel(ClientDataStore.onlineUsers);
	     
	     OnlineUsersList = new JList(ClientDataStore.UI_Model);
	     OnlineUsersList.setCellRenderer(new UserCellRenderer());
	     OnlineUsersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	     OnlineUsersListPanel.add(new JScrollPane(OnlineUsersList,
	    		 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
	     
	     //This user.
	     ThisUserLabel = new JLabel();
	     ThisUserPanel.add(ThisUserLabel,BorderLayout.CENTER);
	     
	     /**Action Listener*/
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
	     /**Second Wrong : windowClosing for windowClosed.*/
	    	
	     //Exit button.
	     ExitButton.addActionListener(new ActionListener() 
	     {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO 自动生成的方法存根
				Exit();
			}
		});
	     
	     //Chat one to one.
	     One_OneChat.addActionListener(new ActionListener()
	     {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO 自动生成的方法存根
				if(One_OneChat.isSelected())
				{
					ADT_of_User ChosenUser = (ADT_of_User)OnlineUsersList.getSelectedValue();
					if(ChosenUser==null)
					{
						TipLabel.setText("【Choose an online user,open up a new chat.】");
					}
					else if(ClientDataStore.thisUser.getID()==ChosenUser.getID())
					{
						TipLabel.setText("【Hey,you cannot talk oneself.】");
					}
					else
					{
						TipLabel.setText("【Now:You are chatting with "+
					ChosenUser.getNickname()+"("+ChosenUser.getID()+") privately");
					}
				}
				else
				{
					TipLabel.setText("【Now:Group Chatting】");
				}
			}
		});
	     
	     //Choose an user.
	     OnlineUsersList.addMouseListener(new MouseAdapter() 
	     {
	    	 @Override
	    	 public void mouseClicked(MouseEvent e)
	    	 {
	    		 ADT_of_User ChosenUser = (ADT_of_User)OnlineUsersList.getSelectedValue();
	    		 if(One_OneChat.isSelected())
	    		 {
	    			 if(ClientDataStore.thisUser.getID()==ChosenUser.getID())
	    			 {
	    				 TipLabel.setText("【Hey,you cannot talk oneself.】");
	    			 }
	    			 else
	    			 {
	    				 TipLabel.setText("【Now:You are chatting with "+
	    							ChosenUser.getNickname()+"("+ChosenUser.getID()+") privately");
	    			 }
	    		 }
	    	 }
		});
	     
	     //Edit text message area.
	     RemainToSendArea.addKeyListener(new KeyAdapter() 
	     {
	    	 @Override
	    	 public void keyPressed(KeyEvent e)
	    	 {
	    		 if(e.getKeyCode()==Event.ENTER)
	    		 {
	    			 SendMessage();
	    		 }
	    	 }
		});
	     SendButton.addActionListener(new ActionListener() 
	     {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO 自动生成的方法存根
				SendMessage();
			}
		});
	     
	     //Shaking your friend.
	     ShakingButton.addActionListener(new ActionListener() 
	     {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				// TODO 自动生成的方法存根
				Shaking();
			}
		});
	     
	     //Send file.
	     SendFileButton.addActionListener(new ActionListener()
	     {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// TODO 自动生成的方法存根
				SendFile();
			}
		});
	     
	     LoadData();
	}
 
	public void LoadData()
	{
		if(null!=ClientDataStore.thisUser)
		{
		    ThisUserLabel.setForeground(Color.BLUE);
			ThisUserLabel.setIcon(new ImageIcon(
                    		"D:\\NewDesktop\\Chat_Room-master\\ChatRoom\\images\\" 
                    + ClientDataStore.thisUser.getProfile() + ".png"));
			ThisUserLabel.setText(ClientDataStore.thisUser.getNickname()
                    + "(" + ClientDataStore.thisUser.getID() + ")");
			ThisUserLabel.setOpaque(true);
			ThisUserLabel.setBackground(Color.WHITE);
		}
		OnlineUsersCount.setText("Online Users List 【"+ClientDataStore.UI_Model.getSize()+"】");
		new ClientThread(this).start();
	}
	
	private void Exit()
	{
		int inquire = JOptionPane.showConfirmDialog(ClientChat.this,
				"Sure to leave chatroom?","Leave chatroom.",
				JOptionPane.YES_NO_OPTION);
		if(inquire==JOptionPane.YES_OPTION)
		{
			Plea plea = new Plea();
			plea.setAction("Exit");
			plea.setData("User", ClientDataStore.thisUser);
			try
			{
				ClientToServer.sendMessage(plea);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				System.exit(0);
			}
		}
		else
		{
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}
	}
	
	public void SendMessage()
	{
		String Message = RemainToSendArea.getText();
		if("".equals(Message))
		{
			JOptionPane.showMessageDialog(ClientChat.this, "Cannot send empty message.",
					"ERROR",JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			ADT_of_User ChosenUser = (ADT_of_User)OnlineUsersList.getSelectedValue();
			
			Message ThisMessage = new Message();
			if(One_OneChat.isSelected())
			//Chat one to one.
			{
				if(null==ChosenUser)
				{
					JOptionPane.showMessageDialog(ClientChat.this, "Please choose a user.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
				}
				else if(ClientDataStore.thisUser.getID()==ChosenUser.getID())
				{
					JOptionPane.showMessageDialog(ClientChat.this, "Hey,you cannot talk oneself.",
                            "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
				}
				else
				{
					ThisMessage.setReceiver(ChosenUser);
				}
			}
			ThisMessage.setSender(ClientDataStore.thisUser);
			ThisMessage.setSendTime(new Date());
			
			DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			StringBuffer buffer = new StringBuffer();
			
			buffer.append(" ").append(dateFormat.format(ThisMessage.getSendTime())).append(" ")
				.append(ThisMessage.getSender().getNickname()).append("(")
				.append(ThisMessage.getSender().getID()).append(")");
			
			//Group chat.
			if(!One_OneChat.isSelected())
			{
				buffer.append("【BroadCast】");
			}
			buffer.append("\n").append(Message).append("\n");
			ThisMessage.setContent(buffer.toString());
			
			Plea plea = new Plea();
			plea.setAction("Chat");
			plea.setData("Message", ThisMessage);
			try
			{
				ClientToServer.Simple_SendMessage(plea);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			//After sending a message,clear type in area.
			InputMap inputmap = RemainToSendArea.getInputMap();
			ActionMap actionmap = RemainToSendArea.getActionMap();
			Object TransferTextActionKey = "TRANSFER_TEXT";
			
			inputmap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), TransferTextActionKey);
			actionmap.put(TransferTextActionKey, new AbstractAction() 
			{
				private static final long serialVersionUID = 5644604112667652457L;

				@Override
				public void actionPerformed(ActionEvent e) 
				{
					// TODO 自动生成的方法存根
					RemainToSendArea.setText("");
					RemainToSendArea.requestFocus();
				}
			});
			RemainToSendArea.setText("");
			ClientToServer.appendText(ThisMessage.getContent());
		}
	}
	
	//Shaking your friend.
	private void Shaking()
	{
		ADT_of_User ChosenUser = (ADT_of_User)OnlineUsersList.getSelectedValue();
		if(null!=ChosenUser)
		{
			if(ClientDataStore.thisUser.getID()==ChosenUser.getID())
			{
				JOptionPane.showMessageDialog(ClientChat.this,"Hey,you cannot talk oneself.",
						"ERROR",JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				Message ThisMessage = new Message();
				ThisMessage.setSender(ClientDataStore.thisUser);
				ThisMessage.setReceiver(ChosenUser);
				ThisMessage.setSendTime(new Date());
				
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				StringBuffer buffer = new StringBuffer();
				
				buffer.append(" ").append(dateFormat.format(ThisMessage.getSendTime()))
                	.append(" You shakes ").append(ThisMessage.getReceiver().getNickname())
                	.append("(").append(ThisMessage.getReceiver().getID()).append(")\n");
				
				ThisMessage.setContent(buffer.toString());
				
				Plea plea = new Plea();
				plea.setAction("Shake");
				plea.setData("Message", ThisMessage);
				try
				{
					ClientToServer.Simple_SendMessage(plea);
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				ClientToServer.appendText(ThisMessage.getContent());
				new ShakeFrame(ClientChat.this).StartShake();
			}
		}
		else
		{
			JOptionPane.showMessageDialog(ClientChat.this, "Cannot shake a group.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void KickOut()
	{
		JOptionPane.showMessageDialog(null,
                "You have been kicked out!", "【System Notice】",JOptionPane.WARNING_MESSAGE);
		
		Plea plea = new Plea();
		plea.setAction("Exit");
		plea.setData("User", ClientDataStore.thisUser);
		try
		{
			ClientToServer.sendMessage(plea);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.exit(0);
		}
	}
	
	private void SendFile()
	{
		ADT_of_User ChosenUser = (ADT_of_User)OnlineUsersList.getSelectedValue();
		
		if(ChosenUser!=null)
		{
			if(ClientDataStore.thisUser.getID()==ChosenUser.getID())
			{
				JOptionPane.showMessageDialog(ClientChat.this, "You cannot send yourself a file!",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				JFileChooser FileChooser = new JFileChooser();
				if(FileChooser.showOpenDialog(ClientChat.this)==JFileChooser.APPROVE_OPTION)
				{
					File file = FileChooser.getSelectedFile();
					FileToSend = new FileData();
					FileToSend.setSender(ClientDataStore.thisUser);
					FileToSend.setReceiver(ChosenUser);
					
					try
					{
						FileToSend.setSourceFilename(file.getCanonicalPath());
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
					FileToSend.setSendTime(new Date());
					
					Plea plea = new Plea();
					plea.setAction("ToSendFile");
					plea.setData("File", FileToSend);
					
					try
					{
						ClientToServer.Simple_SendMessage(plea);
					}
					catch(IOException e)
					{
						e.printStackTrace();
					}
					
					ClientToServer.appendText("【Server】You are sending file to "+
					ChosenUser.getNickname()+"("+ChosenUser.getID()+") , and waiting for agreement.\n");
				}
			}
		}
		else
		{
			JOptionPane.showMessageDialog(ClientChat.this, "Choose only one user online!",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
}

/**Nothing Wrong.*/
//Nothing Wrong.