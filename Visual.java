
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class Visual extends JFrame {
	private JPanel p = new JPanel();
	private JTextField jtfIn = new JTextField();
	private JButton jbtSub = new JButton();
	private Drawing draw = new Drawing();
	static Tree t;
	
	Visual() {
		jbtSub.setText("Submit");
		jbtSub.setSize(100, 50);
		jbtSub.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String input = jtfIn.getText();
				t = new Tree();
            	t.root.value = input;// ( (a \imply (b \imply ccccccccccccccc)) \imply ((a \imply b) \imply (a \imply cccccccccccccccc)))
            	if (Parse.isWell(t.root)) {
            		draw.setTree(t);
            		JOptionPane.showMessageDialog(null, "Well defined~", "Parsing",JOptionPane.INFORMATION_MESSAGE);            		
            	} else {
            		draw.setTree(t);
            		JOptionPane.showMessageDialog(null, "Not well defined!", "Parsing",JOptionPane.ERROR_MESSAGE); 
            	}
            	draw.xMax = 10;         	
			}			
		});
		p.setLayout(new BorderLayout(10, 5));
		p.add(jtfIn, BorderLayout.CENTER);
		p.add(jbtSub, BorderLayout.EAST);
		JScrollPane jsp = new JScrollPane(draw);
		jsp.setPreferredSize(new Dimension(100, 100));
		jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		draw.setPreferredSize(new Dimension(1000,500));
		jsp.getViewport().add(draw);
  
		setLayout(new BorderLayout(5, 5));
		add(p, BorderLayout.NORTH);
		add(jsp, BorderLayout.CENTER);
	}

}
