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
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Session;
import javax.mail.Transport;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClientMainPage extends JFrame
{
	private static final long serialVersionUID = 8063469568045248008L;
	
	private String Account;
	private String Password;
	
	public ClientMainPage(String account, String password) throws HeadlessException 
	{
		//super();
		Account = account;
		Password = password;
		Initialize();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void Initialize()
	{
		final Font myFont = new Font("Arial Rounded MT Bold",Font.BOLD,22);
		
		setTitle("InLook");
		setSize(600,400);
		setResizable(false);
		int x = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int y = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		x = (x-this.getWidth())/2;
		y = (y-this.getHeight())/2;
		this.setLocation(x, y);
		
		Icon Back = new ImageIcon("D:\\Eclipse_Workspace\\InLook\\Images\\Web.png");
		JLabel BackLabel = new JLabel(Back);
		BackLabel.setPreferredSize(new Dimension(600,150));
		this.add(BackLabel,BorderLayout.NORTH);
		
		Border border=BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		
		JPanel SendMailPanel = new JPanel();
		SendMailPanel.setLayout(null);
		SendMailPanel.setBorder(BorderFactory.createTitledBorder(border,"Send Mail",TitledBorder.CENTER,TitledBorder.TOP));
		
		JLabel SendMailLabel = new JLabel("OutBox");
		SendMailLabel.setBounds(120, 80, 120, 40);
		SendMailLabel.setFont(myFont);
		
		String ButtonAddr = "D:\\Eclipse_Workspace\\InLook\\Images\\OutBox.png";
		JButton OutBoxButton = new JButton(new ImageIcon(ButtonAddr));
		OutBoxButton.setMargin(new Insets(0,0,0,0));
		OutBoxButton.setToolTipText("Send a mail.");
		OutBoxButton.setBounds(60, 80, 40, 40);
		
		SendMailPanel.add(OutBoxButton);
		SendMailPanel.add(SendMailLabel);
		
		JPanel CheckMailPanel = new JPanel();
		CheckMailPanel.setLayout(null);
		CheckMailPanel.setBorder(BorderFactory.createTitledBorder(border,"Check Mail",TitledBorder.CENTER,TitledBorder.TOP));
		
		JLabel CheckMailLabel = new JLabel("InBox");
		CheckMailLabel.setBounds(120, 80, 120, 40);
		CheckMailLabel.setFont(myFont);

		ButtonAddr = "D:\\Eclipse_Workspace\\InLook\\Images\\InBox.png";
		JButton InBoxButton = new JButton(new ImageIcon(ButtonAddr));
		InBoxButton.setMargin(new Insets(0,0,0,0));
		InBoxButton.setToolTipText("Check mails.");
		InBoxButton.setBounds(60, 80, 40, 40);
		
		CheckMailPanel.add(InBoxButton);
		CheckMailPanel.add(CheckMailLabel);
		
		SendMailPanel.setOpaque(true);
		SendMailPanel.setBackground(new Color(179, 220, 241));
	    CheckMailPanel.setOpaque(true);
	    CheckMailPanel.setBackground(new Color(179, 220, 241));
	    
		JSplitPane SplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				SendMailPanel,CheckMailPanel);
		SplitPane.setDividerLocation(300);
		SplitPane.setDividerSize(10);
		SplitPane.setEnabled(false);
		this.add(SplitPane,BorderLayout.CENTER);
		
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
						TimeLabel.setText("Welcome, and now is "+dateFormat.format(new Date()));
						TimeLabel.setFont(myFont);
					}
			
				};
		new Timer().scheduleAtFixedRate(thisTask, 0, 1000);
		this.add(TimeLabel,BorderLayout.SOUTH);
		
		//OutBoxButton.
		OutBoxButton.addActionListener(new ActionListener()
		{	
			public void actionPerformed(ActionEvent e) 
			{
				// TODO 自动生成的方法存根
				new ClientSendPage(Account,Password);
			}
		});
		
		
		//InBoxButton.
		InBoxButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				// TODO 自动生成的方法存根
				new ClientCheckPage(Account,Password);
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
	
//	public static void main(String[] a)
//	{
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
//		new ClientWebPage("1"," 1");
//		System.out.println(Toolkit.getDefaultToolkit().getScreenSize());
//	}
	
	private void Exit()
	{
		int inquire = JOptionPane.showConfirmDialog(ClientMainPage.this,
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
