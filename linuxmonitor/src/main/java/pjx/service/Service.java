package pjx.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;

public class Service {

	public static void main(String[] args) {
		try {
			DatagramSocket ds = new DatagramSocket(8080);
			String[] arr = { "12.43", "55.55", "79.77", "89.99", "98.90" };
			Random r = new Random();
			while (true) {
				byte[] b = new byte[1024];
				DatagramPacket pack = new DatagramPacket(b, b.length);
				ds.receive(pack);
				String str = new String(pack.getData(), pack.getOffset(), pack.getLength());
				String cpu = arr[r.nextInt(arr.length)];
				String mem = arr[r.nextInt(arr.length)];
				JSONObject json = new JSONObject();
				json.put("cpu", cpu);
				json.put("mem", mem);
				byte[] resv = json.toString().getBytes();
				pack.setData(resv);
				ds.send(pack);

			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
