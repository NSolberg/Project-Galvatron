package bteam.capstone.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class RiskPanel extends JPanel implements ActionListener,
		MouseWheelListener, MouseListener, MouseMotionListener, KeyListener {

	GuiMap map2;
	Point local;
	boolean dragging;
	int sel = 42;

	public RiskPanel(ActionListener actionListener) {
		this.addMouseWheelListener(this);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.setLayout(new BorderLayout());
		this.setSize(800, 600);
		this.addKeyListener(this);
		Timer t = new Timer((int)(1000/30),this);
		t.start();
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		JButton s = new JButton("Switch");
		JButton e = new JButton("Exit");
		p.add(s, BorderLayout.CENTER);
		p.add(e, BorderLayout.EAST);
		s.addActionListener(actionListener);
		e.addActionListener(actionListener);
		this.add(p, BorderLayout.SOUTH);
		map2 = null;//new GuiMap("Earth", false, 650, 600);
		dragging = false;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		// map.paint(g);
		map2.paint(g);
		if (sel != -1&&!map2.inAttack()) {
			Image temp = map2.getCard(sel);
			g.drawImage(map2.getCard(sel), 650, 0, 800, 210, 0, 0, temp.getWidth(null),
					temp.getHeight(null), null);
		}
		// g.drawImage(map2.getCard(0), 0, 0, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(map2 != null &&map2.inAttack()){
			this.repaint();
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		/*
		 * map2.adjScale(e.getPreciseWheelRotation() * 0.04,
		 * this.getMousePosition()); this.repaint();
		 */
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		local = e.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!dragging) {
			int num = map2.onCountry(e.getPoint());
			if (num > -1) {
				if (sel == -1||sel==42)
					sel = num;
				else if (sel == num)
					sel = 42;
				map2.select(num);
				this.repaint();
			}
		}
		dragging = false;
		if(map2.inAttack()){
			//map2.play();
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		dragging = true;
		Point cur = e.getPoint();
		int x = local.x - cur.x;
		int y = local.y - cur.y;
		local = cur;
		map2.adjCenter(x * 5, y * 5);
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.equals(KeyEvent.VK_BACK_SLASH)){
			System.out.println("space");
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

		System.out.println("space");
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("space");
		
	}
}