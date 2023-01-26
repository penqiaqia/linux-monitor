package pjx.client;

import pjx.client.ui.ConnetUI;
import pjx.client.ui.WindowUI;

public class Client {

	public static void main(String[] args) {
		ConnetUI c = new ConnetUI();
		WindowUI w = new WindowUI();
		ClientSocket cs = new ClientSocket();
		c.setCs(cs);
		w.setCs(cs);
		cs.setCui(c);
		cs.setWui(w);
		c.setVisible(true);
	}

}
