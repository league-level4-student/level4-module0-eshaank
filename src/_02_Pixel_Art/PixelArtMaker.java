package _02_Pixel_Art;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;


public class PixelArtMaker implements MouseListener, ActionListener, Serializable{
	private static final String DATA_FILE = "src/_02_Pixel_Art/saved.dat";
	
	private JFrame window;
	private GridInputPanel gip;
	private GridPanel gp;
	private JButton saveImg;
	private JButton loadImg;
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
		saveImg = new JButton();
		loadImg = new JButton();
		window.remove(gip);
		window.add(gp);
		window.add(csp);
		
		window.add(saveImg);
		saveImg.setLabel("Save Image");
		saveImg.setLocation(w -100 , h + 100);
		saveImg.addActionListener(this);
		
		window.add(loadImg);
		loadImg.setLabel("Load Image");
		loadImg.setLocation(w - 50, h + 50);
		loadImg.addActionListener(this);
		
		gp.repaint();
		gp.addMouseListener(this);
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
		if (e.getSource() == saveImg) {
			save(new GridPanel(gp.getHeight(), gp.getWidth(), gp.getX(), gp.getY(), gp.pixel));
		System.out.println("reached");
		}
		
		if (e.getSource() == loadImg) {
			gp  = load();
			System.out.println(load());
			window.pack();
		}
		
	}
	private static void save(GridPanel gridPanel) {
		try (FileOutputStream fos = new FileOutputStream(new File(DATA_FILE)); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(gridPanel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static GridPanel load() {
		try (FileInputStream fis = new FileInputStream(new File(DATA_FILE)); ObjectInputStream ois = new ObjectInputStream(fis)) {
			return (GridPanel) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// This can occur if the object we read from the file is not
			// an instance of any recognized class
			e.printStackTrace();
			return null;
		}
	}
}
