package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;

public class ClientCheckPage extends JFrame
{
	private static final long serialVersionUID = 9148652517958651948L;
	
	private final String folderName = "inbox";
	private final String savePosition = "D:\\NewDesktop\\New\\";

	private String Account;
	private String Password;

	private Session session;
	private Store store;
	private String Pop3Server = "pop.163.com";
	
	private int number;
	
	private JLabel TipsLabel;
	private JComboBox MailList;
	private JLabel from;
	private JLabel topic;
	private JTextArea body;

	public ClientCheckPage(String account, String password)
	{
		//super();
		Account = account;
		Password = password;
		Initialize();
		setVisible(true);
	}

	private void Initialize()
	{
		final Font myFont = new Font("Arial Rounded MT Bold",Font.BOLD,20);
		
		Properties pro = new Properties();
		pro.put("mail.store.protocol", "pop3");
		pro.put("mail.imap.class", "com.sun.mail.imap.IMAPStore");
		pro.put("mail.pop3.class", "com.sun.mail.pop3.POP3Store");
		
		session = Session.getInstance(pro,null);
		try
		{
			store = session.getStore("pop3");
			store.connect(Pop3Server, Account, Password);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	
		setTitle("InLook");
		setSize(1080,900);
		setResizable(false);
		int x = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int y = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		x = (x-this.getWidth())/2;
		y = (y-this.getHeight())/2;
		this.setLocation(x, y);

		String BackAddr = "D:\\Eclipse_Workspace\\InLook\\Images\\Send.png";
		Icon Back = new ImageIcon(BackAddr);
		JLabel BackLabel = new JLabel(Back);
		BackLabel.setPreferredSize(new Dimension(1080,300));

		this.add(BackLabel,BorderLayout.NORTH);

		JPanel MainPanel = new JPanel();
		MainPanel.setLayout(null);
		MainPanel.setOpaque(true);
		MainPanel.setBackground(new Color(255,250,247));

		Border border=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		MainPanel.setBorder(BorderFactory.createTitledBorder(border,"InBox",TitledBorder.CENTER,TitledBorder.TOP));
		this.add(MainPanel,BorderLayout.CENTER);

		String ButtonAddr = "D:\\Eclipse_Workspace\\InLook\\Images\\Refresh.png";
		JButton RefreshButton = new JButton(new ImageIcon(ButtonAddr));
		RefreshButton.setMargin(new Insets(0,0,0,0));
		RefreshButton.setToolTipText("Refresh InBox.");
		RefreshButton.setBounds(25, 30, 150, 150);

		ButtonAddr = "D:\\Eclipse_Workspace\\InLook\\Images\\Check.png";
		JButton CheckButton = new JButton(new ImageIcon(ButtonAddr));
		CheckButton.setMargin(new Insets(0,0,0,0));
		CheckButton.setToolTipText("View content.");
		CheckButton.setBounds(25, 195, 150, 150);

		ButtonAddr = "D:\\Eclipse_Workspace\\InLook\\Images\\Download.png";
		JButton DownloadButton = new JButton(new ImageIcon(ButtonAddr));
		DownloadButton.setMargin(new Insets(0,0,0,0));
		DownloadButton.setToolTipText("DownLoad appendix.");
		DownloadButton.setBounds(25, 365, 150, 150);
		
		ButtonAddr = "D:\\Eclipse_Workspace\\InLook\\Images\\Delete.png";
		JButton DeleteButton = new JButton(new ImageIcon(ButtonAddr));
		DeleteButton.setMargin(new Insets(0,0,0,0));
		DeleteButton.setToolTipText("Delete Mail.");
		DeleteButton.setBounds(955, 30, 80, 80);
		
		TipsLabel = new JLabel();
		TipsLabel.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,24));
		TipsLabel.setBounds(290,30,400,40);

		try 
		{
			ReceiveMessage();
		} 
		catch (Exception e)
		{
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

		MailList = new JComboBox();
		MailList.setBounds(740, 30, 180, 50);
		MailList.setMaximumRowCount(5);
		MailList.setFont(myFont);
		
		for(int i=0;i<number;++i)
		{
			MailList.addItem("Mail-No."+(i+1));
		}
		
		MailList.setSelectedIndex(0);
		
		JLabel SenderLabel = new JLabel("From:");
		SenderLabel.setFont(myFont);
		SenderLabel.setBounds(210, 100, 100, 40);
		
		JLabel TopicLabel = new JLabel("Topic:");
		TopicLabel.setFont(myFont);
		TopicLabel.setBounds(210, 170, 100, 40);
		
		JLabel BodyLabel = new JLabel("Body:");
		BodyLabel.setFont(myFont);
		BodyLabel.setBounds(210, 240, 100, 40);
		
		from = new JLabel("Mail address of sender.");
		from.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		from.setFont(myFont);
		from.setBounds(290, 100, 400, 40);
		
		topic = new JLabel("Mail subject.");
		topic.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		topic.setFont(myFont);
		topic.setBounds(290, 170, 400, 40);
		
		body = new JTextArea("Mail body.");
		body.setFont(myFont);
		body.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		body.setEditable(false);
		body.setLineWrap(true);
		body.setOpaque(true);
		body.setBackground(new Color(163,184,204));
		body.setForeground(new Color(255,250,247));
		body.setBounds(290, 240, 730, 275);

		MainPanel.add(RefreshButton);
		MainPanel.add(CheckButton);
		MainPanel.add(DownloadButton);
		MainPanel.add(TipsLabel);
		MainPanel.add(MailList);
		MainPanel.add(SenderLabel);
		MainPanel.add(TopicLabel);
		MainPanel.add(BodyLabel);
		MainPanel.add(from);
		MainPanel.add(topic);
		MainPanel.add(body);
		MainPanel.add(DeleteButton);

		final JLabel TimeLabel = new JLabel("",SwingConstants.RIGHT);
		TimeLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		TimeLabel.setOpaque(true);
		TimeLabel.setBackground(new Color(255,250,247));

		TimerTask thisTask = new TimerTask()
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
			@Override
			public void run() 
			{
				// TODO 自动生成的方法存根
				TimeLabel.setText("Welcome, and now is "+dateFormat.format(new Date()));
				TimeLabel.setFont(myFont);
			}

		};
		new Timer().scheduleAtFixedRate(thisTask, 0, 1000);
		this.add(TimeLabel,BorderLayout.SOUTH);
		
		//RefreshButton.
		RefreshButton.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e)
			{
				// TODO 自动生成的方法存根
				try 
				{
					ReceiveMessage();
				} 
				catch (Exception et)
				{
					// TODO 自动生成的 catch 块
					et.printStackTrace();
				}
				from.setText("Mail address of sender.");
				topic.setText("Mail subject.");
				body.setText("Mail body.");
			}
		});
		
		//CheckButton.
		CheckButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				// TODO 自动生成的方法存根
				try 
				{
					ViewContent();
				} 
				catch (Exception e1) 
				{
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		
		//DownoadButton.
		DownloadButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				// TODO 自动生成的方法存根
				try
				{
					DownLoadAttachment(MailList.getSelectedIndex());
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
		
		
		//DeleteButton.
		DeleteButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				// TODO 自动生成的方法存根
				try
				{
					DeleteMail(MailList.getSelectedIndex());
				} 
				catch (Exception e1) 
				{
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		
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
	
	private void DownLoadAttachment(int index) throws Exception
	{
		Folder folder = store.getFolder(folderName);
		if(folder==null)
		{
			throw new Exception(folderName+" does not exist.");
		}
		folder.open(Folder.READ_WRITE);
		int Mail_Index = MailList.getSelectedIndex();
		MimeMessage ThisMessage = (MimeMessage)((folder.getMessages())[Mail_Index]);
		
		if(hasAttachment(ThisMessage))
		{
			SaveAttachment(ThisMessage,savePosition+ThisMessage.getSubject()+"_");
			JOptionPane.showMessageDialog(ClientCheckPage.this, "Attachment has been downloaded successfully!");
		}
		else
		{
			JOptionPane.showMessageDialog(ClientCheckPage.this,"This mail has no attachment.","ERROR",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void SaveAttachment(Part part,String savePosition) throws Exception
	{
		if(part.isMimeType("multipart/*"))
		{
			Multipart multipart = (Multipart)part.getContent();
			int partCount = multipart.getCount();
			for (int i=0;i<partCount;++i)
			{
				BodyPart bodypart = multipart.getBodyPart(i);
				String disp = bodypart.getDisposition();
				if(disp!=null&&(disp.equalsIgnoreCase(Part.ATTACHMENT)||disp.equalsIgnoreCase(Part.INLINE)))
				{
					InputStream is = bodypart.getInputStream();
					SaveFile(is,savePosition,Decode(bodypart.getFileName()));
				}
				else if(bodypart.isMimeType("multipart/*"))
				{
					SaveAttachment(bodypart, savePosition);
				}
				else
				{
					String contentType = bodypart.getContentType();
					if(contentType.indexOf("name")!=-1||contentType.indexOf("application")!=-1)
					{
						SaveFile(bodypart.getInputStream(), savePosition, Decode(bodypart.getFileName()));
					}
				}
			}
		}
	}
	
	private void SaveFile(InputStream is,String savePosition,String fileName) throws Exception
	{
		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(savePosition+fileName)));
		int index=-1;
		while((index=bis.read())!=-1)
		{
			bos.write(index);
			bos.flush();
		}
		bos.close();
		bis.close();
	}
	
	private String Decode(String encode) throws Exception
	{
		if(encode==null||encode.equals(""))
		{
			return "";
		}
		else
		{
			return MimeUtility.decodeText(encode);
		}
	}

	private void DeleteMail(int index) throws Exception
	{
		Folder folder = store.getFolder(folderName);
		if(folder==null)
		{
			throw new Exception(folderName+" does not exist.");
		}
		folder.open(Folder.READ_WRITE);
		int inquire = JOptionPane.showConfirmDialog(ClientCheckPage.this,
				"Sure to delete Mail-No."+(index+1)+" ?","Delete Mail.",
				JOptionPane.YES_NO_OPTION);
		if(inquire==JOptionPane.YES_OPTION)
		{
			Message DeleteMessage = (folder.getMessages())[index];
			DeleteMessage.setFlag(Flags.Flag.DELETED, true);
			JOptionPane.showMessageDialog(ClientCheckPage.this, "Mail-No."+(index+1)+" has been deleted.");
		}
		else
		{
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}
		folder.close(true);
	}
	
	private void ViewContent() throws Exception
	{
		Folder folder = store.getFolder(folderName);
		if(folder==null)
		{
			throw new Exception(folderName+" does not exist.");
		}
		folder.open(Folder.READ_WRITE);
		int Mail_Index = MailList.getSelectedIndex();
		MimeMessage ThisMessage = (MimeMessage)((folder.getMessages())[Mail_Index]);
		
		String sender = String.valueOf((ThisMessage.getFrom())[0]);
		from.setText(sender);
		topic.setText(ThisMessage.getSubject());
		
		if(hasAttachment(ThisMessage))
		{
			StringBuffer textbody = new StringBuffer();
			GetTextBody(ThisMessage,textbody);
			body.setText(textbody.toString()+"\n\nNOTE:This mail has ATTACHMENT.");
		}
		else
		{
			String textBody = String.valueOf(ThisMessage.getContent());
			body.setText(textBody);
		}

		ThisMessage.setFlag(Flags.Flag.SEEN, true);
		folder.close(true);
	}
	
	private StringBuffer GetTextBody(Part part,StringBuffer textbody) throws Exception
	{
		boolean hasTextAttach = part.getContentType().indexOf("name")>0;
		if(part.isMimeType("text/*")&&!hasTextAttach)
		{
			textbody.append(part.getContent().toString());
		}
		else if(part.isMimeType("message/rfc822"))
		{
			GetTextBody((Part)part.getContent(),textbody);
		}
		else if(part.isMimeType("multipart/*"))
		{
			Multipart multipart = (Multipart)part.getContent();
			int partCount = multipart.getCount();
			for(int i=0;i<partCount;++i)
			{
				BodyPart bodypart = multipart.getBodyPart(i);
				GetTextBody(bodypart, textbody);
			}
		}
		
		return textbody;
	}
	
	private boolean hasAttachment(Part part)throws Exception
	{
		boolean has = false;
		if(part.isMimeType("multipart/*"))
		{
			MimeMultipart multipart = (MimeMultipart)part.getContent();
			int partCount = multipart.getCount();
			for(int i=0;i<partCount;++i)
			{
				BodyPart bodyPart = multipart.getBodyPart(i);
				String disp = bodyPart.getDisposition();
				if(disp!=null&&(disp.equalsIgnoreCase(Part.ATTACHMENT)||disp.equalsIgnoreCase(Part.INLINE)))
				{
					has = true;
				}
				else if(bodyPart.isMimeType("multipart/*"))
				{
					has = hasAttachment(bodyPart);
				}
				else
				{
					String contentType = bodyPart.getContentType();
					if(contentType.indexOf("application")!=-1)
					{
						has = true;
					}
					if(contentType.indexOf("name")!=-1)
					{
						has = true;
					}
				}
				if(has)
				{
					break;
				}
			}
		}
		else if(part.isMimeType("message/rfc822"))
		{
			has = hasAttachment((Part)part.getContent());
		}
		return has;
	}
	
	private void ReceiveMessage() throws Exception
	{
		Folder folder = store.getFolder(folderName);
		if(folder==null)
		{
			throw new Exception(folderName+" does not exist.");
		}
		folder.open(Folder.READ_WRITE);
		number = folder.getMessageCount();
		TipsLabel.setText("Totally, mail number is "+number+".");
	}
	
	private void Exit()
	{
		int inquire = JOptionPane.showConfirmDialog(ClientCheckPage.this,
				"Sure to leave InBox?","Leave InBox.",
				JOptionPane.YES_NO_OPTION);
		if(inquire==JOptionPane.YES_OPTION)
		{
			this.dispose();
		}
		else
		{
			this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		}
	}

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
		new ClientCheckPage("1", "2");
	}
}
