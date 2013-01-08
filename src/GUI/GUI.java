package GUI;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import Main.Controller;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JMenuBar menuBar;
	private JMenu mnPayloads;
	private JMenu mnTimeout;
	private JMenuItem mntmSeconds;
	private JMenuItem mntmSeconds_1;
	private JMenuItem mntmSeconds_2;
	private JMenuItem mntmNone;
	public Controller controller;
	public Timer timer;
	
	public GUI(Controller controller) 
	{
		this.controller = controller;
	    this.setName("Server");
	    this.setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 283, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		
		menuBar = new JMenuBar();
		scrollPane.setColumnHeaderView(menuBar);
		
		mnPayloads = new JMenu("Payloads");
		menuBar.add(mnPayloads);
		
		mnTimeout = new JMenu("TimeOut");
		mnPayloads.add(mnTimeout);
		
		mntmSeconds = new JMenuItem("120 seconds");
		mnTimeout.add(mntmSeconds);
		
		mntmSeconds_1 = new JMenuItem("180 seconds");
		mnTimeout.add(mntmSeconds_1);
		
		mntmSeconds_2 = new JMenuItem("300 seconds");
		mnTimeout.add(mntmSeconds_2);
		
		mntmNone = new JMenuItem("None");
		mnTimeout.add(mntmNone);
		
		Reminder(3000);
		
		this.addWindowListener(new WindowListener() 
		{
			@Override
			public void windowActivated(WindowEvent arg0) {}
			@Override
			public void windowClosed(WindowEvent arg0){}
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			@Override
			public void windowIconified(WindowEvent arg0) {}
			@Override
			public void windowOpened(WindowEvent arg0) {}
			@Override
			public void windowClosing(WindowEvent e) 
			{
				System.exit(0);
			}
		});
	}
	
	
	public void Reminder(int miliseconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), 0, miliseconds);
	}

    class RemindTask extends TimerTask 
    {
        public void run() 
        {
        	textArea.setText("");
        	
        	if(controller.payloadDataControllerList != null)
        	{
	        	for(int i= 0; i < controller.payloadDataControllerList.size(); i++)
	        	{
	        		textArea.append(controller.payloadDataControllerList.get(i).deviceName);
	        		textArea.append("\n");
	        		
	        		if(controller.terminalDataControllerList != null)
	        		{
		        		for(int j = 0; j < controller.terminalDataControllerList.size(); j++)
		        		{
		        			if(controller.payloadDataControllerList.get(i).deviceName.equals(controller.terminalDataControllerList.get(j).payloadDeviceName))
		        			{
		        				String tempString = "    -";
		        				tempString += controller.terminalDataControllerList.get(j).terminalName;
		        				tempString += "  (";
		        				String tempString2 = controller.terminalDataControllerList.get(j).socket.getInetAddress().toString();
		        				tempString2 = tempString2.substring(1);
		        				tempString += tempString2;
		        				tempString += ")\n";
		        				textArea.append(tempString);
		        			}
		        		}
	        		}
	        	}
        	}
        	
        }
    }
    
	
	public void updateText(final String updateString)
	{
		try
		{
			textArea.append(updateString);
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
		catch(Exception e) 
		{ 
			System.out.println(e);
		}
	}
}
