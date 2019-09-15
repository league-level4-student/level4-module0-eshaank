package _02_Pixel_Art;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PixelArtMaker implements MouseListener, ActionListener, Serializable {
	private static final String DATA_FILE = "src/_02_Pixel_Art/saved.dat";

	private JFrame window;
	private GridInputPanel gip;
	private GridPanel gp;
	private JPanel savePanel;
	private JButton saveButton;

	ColorSelectionPanel csp;

	public void start() {
		gip = new GridInputPanel(this);
		window = new JFrame("Pixel Art");
		window.setLayout(new FlowLayout());
		window.setResizable(false);

		window.add(gip);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	public void submitGridData(int w, int h, int r, int c) {
		gp = new GridPanel(w, h, r, c);
		csp = new ColorSelectionPanel();

		window.remove(gip);
		window.add(gp);
		window.add(csp);

		gp.repaint();
		gp.addMouseListener(this);

		savePanel = new JPanel();
		saveButton = new JButton("Save Image");
		saveButton.addActionListener((e) -> saveGridData());
		window.add(savePanel);
		savePanel.add(saveButton);

		window.pack();
	}

	private void saveGridData() {
		try (FileOutputStream fos = new FileOutputStream(new File(DATA_FILE)); ObjectOutputStream oos = new ObjectOutputStream(fos)){
			oos.writeObject(gp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void submitGridData(GridPanel gp) {
		this.gp = gp;
		csp = new ColorSelectionPanel();

		window.remove(gip);
		window.add(gp);
		window.add(csp);

		gp.repaint();
		gp.addMouseListener(this);

		savePanel = new JPanel();
		saveButton = new JButton("Save Image");
		saveButton.addActionListener((e) -> saveGridData());
		window.add(savePanel);
		savePanel.add(saveButton);

		window.pack();
	}

	public static void main(String[] args) throws Exception {
		new PixelArtMaker().start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gp.setColor(csp.getSelectedColor());
		System.out.println(csp.getSelectedColor());
		gp.clickPixel(e.getX(), e.getY());
		gp.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
