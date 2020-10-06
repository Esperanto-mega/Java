package Client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.activation.FileDataSource;
import javax.activation.DataHandler;

public class ClientSendPage extends JFrame
{
	private static final long serialVersionUID = -9189305388378998171L;
	
	private String Account;
	private String Password;
	private JTextField Receiver;
	private JTextField Topic;
	private JTextArea MailMessage;
	private boolean hasAttachment=false;
	private ArrayList<String> FileName = new ArrayList<String>();
	
	private Session session;
	private Transport transport;
	private String SMTPServer = "smtp.163.com";
	
	public ClientSendPage(String account, String password) throws HeadlessException 
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
		MainPanel.setBorder(BorderFactory.createTitledBorder(border,"OutBox",TitledBorder.CENTER,TitledBorder.TOP));
		this.add(MainPanel,BorderLayout.CENTER);
		
		JLabel ReceiverLabel = new JLabel("Receiver:");
		ReceiverLabel.setFont(myFont);
		ReceiverLabel.setBounds(25, 30, 120,40);
		
		Receiver = new JTextField();
		Receiver.setFont(myFont);
		Receiver.setBounds(145, 30, 900, 40);
		
		JLabel TopicLabel = new JLabel("MailTopic:");
		TopicLabel.setFont(myFont);
		TopicLabel.setBounds(25, 90, 120, 40);
		
		Topic = new JTextField();
		Topic.setFont(myFont);
		Topic.setBounds(145, 90, 900, 40);
		
		JLabel BodyLabel = new JLabel("TextBody:");
		BodyLabel.setFont(myFont);
		BodyLabel.setBounds(25, 145, 120, 40);
		
		MailMessage = new JTextArea();
		MailMessage.setLineWrap(true);
		MailMessage.setFont(myFont);
		MailMessage.setBounds(145, 145, 900, 380);
		MailMessage.setBackground(new Color(101,151,188));
		MailMessage.setForeground(Color.WHITE);
		
		String ButtonAddr = "D:\\Eclipse_Workspace\\InLook\\Images\\SendMail.png";
		JButton SendButton = new JButton(new ImageIcon(ButtonAddr));
		SendButton.setMargin(new Insets(0,0,0,0));
		SendButton.setToolTipText("Send a mail.");
		SendButton.setBounds(25, 210, 100, 100);
		
		ButtonAddr = "D:\\Eclipse_Workspace\\InLook\\Images\\Exit.png";
		JButton ExitButton = new JButton(new ImageIcon(ButtonAddr));
		ExitButton.setMargin(new Insets(0,0,0,0));
		ExitButton.setToolTipText("Exit OutBox.");
		ExitButton.setBounds(25, 330,100,100);
		
		ButtonAddr = "D:\\Eclipse_Workspace\\InLook\\Images\\Appendix.png";
		JButton AppendixButton = new JButton(new ImageIcon(ButtonAddr));
		AppendixButton.setMargin(new Insets(0,0,0,0));
		AppendixButton.setToolTipText("Append something.");
		AppendixButton.setBounds(25, 450, 100, 60);
		
		MainPanel.add(ReceiverLabel);
		MainPanel.add(Receiver);
		MainPanel.add(TopicLabel);
		MainPanel.add(Topic);
		MainPanel.add(BodyLabel);
		MainPanel.add(MailMessage);
		MainPanel.add(SendButton);
		MainPanel.add(ExitButton);
		MainPanel.add(AppendixButton);
		
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
	     
	     //Exit button.
	     ExitButton.addActionListener(new ActionListener() 
	     {
			public void actionPerformed(ActionEvent e) 
			{
				// TODO 自动生成的方法存根
				Exit();
			}
		});
		
	     SendButton.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				// TODO 自动生成的方法存根
				try
				{
					if(hasAttachment)
					{
						SendMailPro();
					}
					else
					{
						SendMail();
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
	     
	     AppendixButton.addActionListener(new ActionListener() 
	     {	
			public void actionPerformed(ActionEvent e)
			{
				// TODO 自动生成的方法存根
				try
				{
					AppendAttachment();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
		});
	}

	private void AppendAttachment() throws Exception
	{
		JFileChooser FileChooser = new JFileChooser();
		if(FileChooser.showOpenDialog(ClientSendPage.this)==JFileChooser.APPROVE_OPTION)
		{
			String FileAddr = FileChooser.getSelectedFile().getCanonicalPath();
			if(FileAddr!=null&&FileAddr.length()!=0)
			{
				hasAttachment=true;
				FileName.add(FileAddr);
			}
		}
	}
	
	private void SendMail() throws Exception
	{
		Properties pro = new Properties();
		pro.put("mail.transport.protocol","smtp");
		pro.put("mail.smtp.class","com.sun.mail.smtp.SMTPTransport");
		pro.put("mail.smtp.host",SMTPServer);
		
		/**SMTP port.*/
		pro.put("mail.smtp.port","25");
		
		/**Verify account.*/
		pro.put("mail.smtp.auth","true");
		
		session = Session.getInstance(pro, new Authenticator() 
		{
			 public PasswordAuthentication getPasswordAuthentication() 
		        { 
		          return new PasswordAuthentication(Account, Password); 
		        }      
		});
		
		transport = session.getTransport();
		
		Session NewSession = Session.getDefaultInstance(new Properties());
		MimeMessage message = new MimeMessage(NewSession);
		message.setFrom(new InternetAddress(Account));
		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(Receiver.getText()));
		message.setSentDate(new Date());
		message.setSubject(Topic.getText());
		message.setText(MailMessage.getText());
		message.saveChanges();
		
		transport.connect();
		transport.sendMessage(message, message.getAllRecipients());
		
		JOptionPane.showMessageDialog(ClientSendPage.this, "Mail has been sent successfully!");
		
		Receiver.setText("");
		Topic.setText("");
		MailMessage.setText("");
		
		transport.close();
	}
	
	private void SendMailPro() throws Exception
	{
		Properties pro = new Properties();
		pro.put("mail.transport.protocol","smtp");
		pro.put("mail.smtp.class","com.sun.mail.smtp.SMTPTransport");
		pro.put("mail.smtp.host",SMTPServer);
		
		/**SMTP port.*/
		pro.put("mail.smtp.port","25");
		
		/**Verify account.*/
		pro.put("mail.smtp.auth","true");
		
		session = Session.getInstance(pro, new Authenticator() 
		{
			 public PasswordAuthentication getPasswordAuthentication() 
		        { 
		          return new PasswordAuthentication(Account, Password); 
		        }      
		});
		
		transport = session.getTransport();
		Session NewSession = Session.getDefaultInstance(new Properties());
		MimeMessage message = new MimeMessage(NewSession);
		message.setFrom(new InternetAddress(Account));
		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(Receiver.getText()));
		message.setSentDate(new Date());
		message.setSubject(Topic.getText());
		
		MimeBodyPart ContentPart = CreateContent(MailMessage.getText());
		ArrayList<MimeBodyPart> FileList = new ArrayList<MimeBodyPart>();
		for(int i=0;i<FileName.size();++i)
		{
			FileList.add(CreateAttachment(FileName.get(i)));
		}
		
		MimeMultipart AllMultiPart  =new MimeMultipart("mixed");
		AllMultiPart.addBodyPart(ContentPart);
		for(int i=0;i<FileList.size();++i)
		{
			AllMultiPart.addBodyPart(FileList.get(i));
		}
		
		message.setContent(AllMultiPart);
		message.saveChanges();
		
		transport.connect();
		transport.sendMessage(message, message.getAllRecipients());
		
		JOptionPane.showMessageDialog(ClientSendPage.this, "Mail has been sent successfully!");
		
		Receiver.setText("");
		Topic.setText("");
		MailMessage.setText("");
		
		FileName.clear();
		transport.close();
	}
	
	public static MimeBodyPart CreateContent(String body) throws Exception
	{
		MimeBodyPart HTMLBodyPart = new MimeBodyPart();
		HTMLBodyPart.setContent(body, "text/html;charset=UTF-8");
		return HTMLBodyPart;
	}
	
	public static MimeBodyPart CreateAttachment(String FileName) throws Exception
	{
		MimeBodyPart Attachment = new MimeBodyPart();
		FileDataSource file = new FileDataSource(FileName);
		Attachment.setDataHandler(new DataHandler(file));
		Attachment.setFileName(file.getName());
		return Attachment;
	}
	
	private void Exit()
	{
		int inquire = JOptionPane.showConfirmDialog(ClientSendPage.this,
				"Sure to leave OutBox?","Leave OutBox.",
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

//	public static void main(String[] args)
//	{
//		// TODO 自动生成的方法存根
//		JFrame.setDefaultLookAndFeelDecorated(true);
//		JDialog.setDefaultLookAndFeelDecorated(true);
//		try
//		{
//			 String LookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
//	         UIManager.setLookAndFeel(LookAndFeel);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		new ClientSendPage("1", "2");
//	}
}
