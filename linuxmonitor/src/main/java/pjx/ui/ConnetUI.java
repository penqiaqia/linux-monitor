package pjx.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import pjx.client.ClientSocket;

public class ConnetUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Font fonts = new Font("宋体", Font.BOLD, 20);
	private Font fonth = new Font("黑体", Font.BOLD, 20);
	private Dimension d = new Dimension(180, 30);
	private JTextField hostTF;
	private JTextField portTF;
	private final static String defaultHost = "127.0.0.1";
	private final static String defaultPort = "8080";
	private ClientSocket cs;

	public void setCs(ClientSocket cs) {
		this.cs = cs;
	}

	public ConnetUI() {
		this.setSize(350, 200);
		this.setTitle("linux monitor");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(connetJpanel());
		this.setLocationRelativeTo(null);
		this.setResizable(false);

	}

	public JPanel connetJpanel() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel centerPanel = new JPanel(new GridLayout(2, 0));
		JPanel paneH = new JPanel(new FlowLayout(1));
		JPanel paneP = new JPanel(new FlowLayout(1));
		JPanel paneBtn = new JPanel(new FlowLayout(1));
		JLabel hostLabel = new JLabel("taget host:");
		hostLabel.setFont(fonts);
		JLabel portLabel = new JLabel("taget port:");
		portLabel.setFont(fonts);
		hostTF = new JTextField(defaultHost);
		hostTF.setFont(fonth);
		hostTF.setPreferredSize(d);
		portTF = new JTextField(defaultPort);
		portTF.setFont(fonth);
		portTF.setPreferredSize(d);
		JButton connetBtn = new JButton("connet");
		connetBtn.setFont(fonts);
		connetBtn.addActionListener(e -> {
			try {
				if (cs.connect(hostTF.getText(), Integer.parseInt(portTF.getText())))
					cs.showInfo();

			} catch (Exception e1) {
				StringBuffer buf = new StringBuffer();
				for (StackTraceElement ste : e1.getStackTrace()) {
					buf.append(ste.toString() + "\n");
				}

				JOptionPane.showMessageDialog(this, "连接服务器失败" + buf);
			}
			;
		});
		JButton cancelBtn = new JButton("cancel");
		cancelBtn.setFont(fonts);
		cancelBtn.addActionListener(e -> {
			System.exit(0);
//			String[] option = { "退出","取消" };
//			int a = JOptionPane.showOptionDialog(null, "是否退出", "提示信息", JOptionPane.YES_NO_CANCEL_OPTION,
//					JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
//			System.out.println(a);
		});
		paneH.add(hostLabel);
		paneH.add(hostTF);
		paneP.add(portLabel);
		paneP.add(portTF);
		paneBtn.add(connetBtn);
		paneBtn.add(cancelBtn);
		centerPanel.add(paneH);
		centerPanel.add(paneP);
		panel.add(centerPanel, BorderLayout.NORTH);
		panel.add(paneBtn, BorderLayout.SOUTH);
		return panel;
	}

	public static void main(String[] args) {
		ConnetUI cu = new ConnetUI();
		cu.setVisible(true);
	}

}
