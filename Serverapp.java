import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.awt.event.*;

public class Serverapp implements ActionListener,Runnable {
	JFrame jf;
	JButton jb;
	JTextField tf;
	JTextArea ta;
	BufferedReader br;
	PrintWriter pw;
	ServerSocket ss;
	Socket s;
	Container c;
	Thread t;

public Serverapp(){
	jf = new JFrame("Server");
	c = jf.getContentPane();
	c.setLayout(new FlowLayout());

	try{
		ss = new ServerSocket(2000);
		s = ss.accept();
		System.out.println("Connected");
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		pw = new PrintWriter(s.getOutputStream(), true);
	}
	catch(Exception ae){
		ae.printStackTrace();
	}

	jb = new JButton("Send");
	ta = new JTextArea(30,15);
	tf = new JTextField(15);
	c.add(tf);
	c.add(jb);
	c.add(ta);

	jf.setVisible(true);
	jf.setSize(400,350);
	jb.addActionListener(this);

	t = new Thread(this);
	t.start();
}

public void actionPerformed(ActionEvent ae){
	try {
		String msg = tf.getText();
		pw.println(msg);
		tf.setText("");
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void run(){
	while(true){
		try{
			String msg = (String)br.readLine();
			ta.append(msg.toString());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
public static void main(String args[]){
	new Serverapp();
}
}