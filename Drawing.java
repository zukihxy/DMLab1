import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Drawing extends JPanel{
	private Tree tree;
	int xMax = 30;
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		if (tree != null)
			paint(g, tree.root, 200, 50);
	}
	
	private int paint(Graphics g, Node n, int x, int y) {
		FontMetrics fm = g.getFontMetrics();
		if (n.left == null && n.right == null) {// draw leaf node
			//g.drawString(n.value, xMax, y);
			drawNode(n, g, xMax, y);
			int prevX = xMax;
			int width = fm.stringWidth(n.value);
			xMax += (width + 60);
			prevX += width / 2;
			return prevX;
		} else if (n.left != null && n.right != null) {
			int lx = paint(g, n.left, x, y+35);
			int rx = paint(g, n.right, x, y+35);
			x = (lx + rx) / 2;// find proper x for this node
			g.drawLine(x, y, lx, y+20);
			g.drawLine(x, y, rx, y+20);
			int temp = x - fm.stringWidth(n.value)/2;
			temp = temp < 0 ? 0 : temp;
			//g.drawString(n.value, temp, y);
			drawNode(n, g, temp, y);
			return x;
		} else {// only have one child
			int lx = paint(g, n.left, x, y+35);
			g.drawLine(lx, y, lx, y+20);
			int temp = lx - fm.stringWidth(n.value)/2;
			//g.drawString(n.value, temp, y);
			drawNode(n, g, temp, y);
			return lx;
		}
	}
	
	public void setTree(Tree t) {
		tree = t;
		repaint();
	}
	
	private void drawNode(Node n, Graphics g, int x, int y) {
		String input = n.value.trim();
		int mid = 18;
		Font f = new Font(Font.SANS_SERIF, Font.PLAIN, mid);
		g.setFont(f);
		if (!input.contains("{"))
			g.drawString(input, x, y);
		else {
			for (int i = 0; i < input.length(); i++) {
				if (input.charAt(i) == '_' && input.charAt(i+1) == '{') {
					f = new Font(Font.SERIF, Font.PLAIN, mid/3*2);
					g.setFont(f);
					i++;
				} else if (input.charAt(i) == '}') {
					f = new Font(Font.SANS_SERIF, Font.PLAIN, mid);
					g.setFont(f);
				} else {
					g.drawString(Character.toString(input.charAt(i)), x, y);
					FontMetrics fm = g.getFontMetrics();
					x += fm.stringWidth(Character.toString(input.charAt(i)));
				}
			}
		}
	}
}
