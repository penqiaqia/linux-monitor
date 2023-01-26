package pjx.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.alibaba.fastjson.JSONObject;

import pjx.ui.ConnetUI;
import pjx.ui.WindowUI;

public class ClientSocket {
	private DatagramSocket socket;
	private WindowUI wui;
	private ConnetUI cui;
	private final static String REQ = "info";
	private Timer timer;

	public void setWui(WindowUI wui) {
		this.wui = wui;
	}

	public void setCui(ConnetUI cui) {
		this.cui = cui;
	}

	public ClientSocket() {
		super();
	}

	public boolean connect(String host, int port) throws Exception {
		socket = new DatagramSocket();
		socket.connect(InetAddress.getByName(host), port);
		socket.setSoTimeout(3000);
		byte[] req = REQ.getBytes();
		DatagramPacket packet = new DatagramPacket(req, req.length);
		socket.send(packet);
		socket.receive(packet);
		return true;
	}

	public String getResvInfo(byte[] req) {
		String info = "";
		DatagramPacket packet = new DatagramPacket(req, req.length);
		try {
			socket.send(packet);
			socket.receive(packet);
			info = new String(packet.getData(), packet.getOffset(), packet.getLength(), Charset.forName("utf8"));
		} catch (IOException e) {
			e.printStackTrace();
			String[] option = { "重新连接", "退出程序" };
			int a = JOptionPane.showOptionDialog(null, "", "提示信息", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
			if (a == 0) {
				timer.stop();
				timer.start();
			} else {
				System.exit(0);
			}
		}
		return info;

	}

	public void showInfo() {
		cui.dispose();
		wui.setVisible(true);
		timer = new Timer(2, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String info = getResvInfo(REQ.getBytes());
				JSONObject json = JSONObject.parseObject(info);
				String cpu = json.getString("cpu");
				String mem = json.getString("mem");
				wui.updateInfo(cpu, mem);

			}
		});
		timer.start();
	}

}
