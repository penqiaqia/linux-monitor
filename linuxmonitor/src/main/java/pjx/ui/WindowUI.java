package pjx.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;

import com.alibaba.fastjson.JSONObject;

import pjx.client.ClientSocket;

public class WindowUI extends JWindow {
	private int offsetX;
	private int offsetY;
	private JPanel panel;
	private JPanel cpu;
	private JPanel mem;
	private JLabel lcpu;
	private JLabel lmem;
	private Font font = new Font("宋体", Font.BOLD, 20);
	private Color green = new Color(95, 151, 89);
	private Color red = new Color(235, 107, 96);
	private Color orange = new Color(245, 191, 80);
	private ClientSocket cs;

	public void setCs(ClientSocket cs) {
		this.cs = cs;
	}

	public WindowUI() {
		JSONObject obj = new JSONObject();
		obj.put("cpu", "20.89");
		obj.put("mem", "78.89");
		this.setSize(300, 200);
		this.setAlwaysOnTop(true);
		this.add(getPanel());
		this.setLocation(getwindowDimension().width - 300, getwindowDimension().height - 200);
		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				setLocation(e.getXOnScreen() - offsetX, e.getYOnScreen() - offsetY);
			}
		});
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				offsetX = e.getX();
				offsetY = e.getY();
			}
		});
	}

	public void updateInfo(String cpu, String mem) {
		Double c = Double.valueOf(cpu);
		Double m = Double.valueOf(mem);
		if (c < 50) {
			this.cpu.setBackground(green);
		} else if (50 <= c && c < 75) {
			this.cpu.setBackground(orange);
		} else {
			this.cpu.setBackground(red);
		}
		if (m < 50) {
			this.mem.setBackground(green);
		} else if (50 <= m && m < 75) {
			this.mem.setBackground(orange);
		} else {
			this.mem.setBackground(red);
		}
		lcpu.setText(cpu);
		lmem.setText(mem);
		panel.updateUI();

	}

	/*
	 * 获取屏幕宽和高
	 */
	public Rectangle getwindowDimension() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		return ge.getMaximumWindowBounds();
	}

	/*
	 * 获取任务管理器高度
	 */
	public int getButtomHight() {
		Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(this.getGraphicsConfiguration());
		System.out.println(insets.bottom);
		return insets.bottom;
	}

	public JPanel getPanel() {
		JButton exit = new JButton("x");
		exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					String[] option = { "退出", "取消" };
					int a = JOptionPane.showOptionDialog(null, "是否退出", "提示信息", JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
					if (a == 0) {
						System.exit(0);
					}
				}
			}
		});
		exit.setPreferredSize(new Dimension(20, 20));
		JPanel rn = new JPanel(new BorderLayout());
		JLabel jmem = new JLabel("mem:");
		rn.add(jmem, BorderLayout.CENTER);
		rn.add(exit, BorderLayout.EAST);
		panel = new JPanel(new GridLayout(0, 2));
		cpu = new JPanel(new BorderLayout());
		mem = new JPanel(new BorderLayout());
		JLabel jcpu = new JLabel("cpu:");
		jcpu.setFont(font);
		lcpu = new JLabel("...");
		lcpu.setFont(font);
		cpu.add(jcpu, BorderLayout.NORTH);
		cpu.add(lcpu, BorderLayout.CENTER);
		jmem.setFont(font);
		lmem = new JLabel("...");
		lmem.setFont(font);
		mem.add(rn, BorderLayout.NORTH);
		mem.add(lmem, BorderLayout.CENTER);
		panel.add(cpu);
		panel.add(mem);
		return panel;
	}

//	public static void main(String[] args) {
//		WindowUI wu = new WindowUI();
//		wu.setVisible(true);
//	}

}
