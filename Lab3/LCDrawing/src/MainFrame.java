import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JRadioButton;

public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DrawingPanel dPanel;
	private Box shapeBox;
	private JRadioButton lineRadioBtn;
	private JRadioButton circleRadioBtn;
	private Box thicknessBox;
	private JRadioButton rBtn1;
	private JRadioButton rBtn2;
	private JRadioButton rBtn3;
	private JRadioButton rBtn4;
	ButtonGroup thicknessGroup;
	
	private Box multicastBox;
	private ButtonGroup multicastGroup;
	private JRadioButton tmBtn;
	private JRadioButton fmBtn;

	GridBagConstraints constraints = new GridBagConstraints();

	void addGB(Component component, int x, int y) {
		constraints.gridx = x;
		constraints.gridy = y;
		add(component, constraints);
	}

	private void init() {
		setLayout(new GridBagLayout());
		dPanel = new DrawingPanel();
		constraints.insets = new Insets(10, 10, 10, 10);
		constraints.gridwidth = 3;
		
		addGB(dPanel, 0, 0);
		constraints.gridwidth = 1;
		
		lineRadioBtn = new JRadioButton("Line");
		lineRadioBtn.setSelected(true);
		lineRadioBtn.setMnemonic(KeyEvent.VK_D);
		lineRadioBtn.setActionCommand("0");
		circleRadioBtn = new JRadioButton("Circle");
		circleRadioBtn.setMnemonic(KeyEvent.VK_D);
		circleRadioBtn.setActionCommand("1");
		shapeBox = Box.createVerticalBox();
		ButtonGroup shapeGroup = new ButtonGroup();
		shapeGroup.add(lineRadioBtn);
		shapeGroup.add(circleRadioBtn);
		shapeBox.add(lineRadioBtn);
		shapeBox.add(circleRadioBtn);
		shapeBox.setBorder(BorderFactory.createTitledBorder("Shape"));
		addGB(shapeBox, 0, 1);

		fmBtn = new JRadioButton("False");
		fmBtn.setSelected(true);
		fmBtn.setMnemonic(KeyEvent.VK_D);
		fmBtn.setActionCommand("33");
		tmBtn = new JRadioButton("True");
		tmBtn.setMnemonic(KeyEvent.VK_D);
		tmBtn.setActionCommand("55");
		
		rBtn1 = new JRadioButton("1 pt");
		rBtn1.setSelected(true);
		rBtn1.setMnemonic(KeyEvent.VK_D);
		rBtn1.setActionCommand("1");
		rBtn2 = new JRadioButton("3 pt");
		rBtn2.setMnemonic(KeyEvent.VK_D);
		rBtn2.setActionCommand("2");
		
		rBtn3 = new JRadioButton("5 pt");
		rBtn3.setMnemonic(KeyEvent.VK_D);
		rBtn3.setActionCommand("3");
		
		rBtn4 = new JRadioButton("7 pt");
		rBtn4.setMnemonic(KeyEvent.VK_D);
		rBtn4.setActionCommand("4");

		thicknessBox = Box.createVerticalBox();
		thicknessGroup = new ButtonGroup();
		thicknessGroup.add(rBtn1);
		thicknessGroup.add(rBtn2);
		thicknessGroup.add(rBtn3);
		thicknessGroup.add(rBtn4);
		thicknessBox.add(rBtn1);
		thicknessBox.add(rBtn2);
		thicknessBox.add(rBtn3);
		thicknessBox.add(rBtn4);
		thicknessBox.setBorder(BorderFactory.createTitledBorder("Thickness"));
		
		addGB(thicknessBox, 1, 1);
		lineRadioBtn.addActionListener( ac);
		circleRadioBtn.addActionListener(ac);
		rBtn1.addActionListener(ac1);
		rBtn2.addActionListener(ac1);
		rBtn3.addActionListener(ac1);
		rBtn4.addActionListener(ac1);
		
		fmBtn.addActionListener(ac2);
		tmBtn.addActionListener(ac2);
		
		
		multicastBox = Box.createVerticalBox();
		multicastGroup = new ButtonGroup();
		multicastGroup.add(tmBtn);
		multicastGroup.add(fmBtn);
		multicastBox.add(tmBtn);
		multicastBox.add(fmBtn);
		multicastBox.setBorder(BorderFactory.createTitledBorder("Multicast"));
		addGB(multicastBox, 2, 1);
	
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}
	public MainFrame() {

		super("Image Filtering");
		setSize(800, 600);

		init();
		
		dPanel.setShapeType(0);
		dPanel.setThickness(1);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	ActionListener ac = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			int a = Integer.parseInt(e.getActionCommand());
			dPanel.setShapeType(a);
		}
	};
	
	ActionListener ac1 = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			int a = Integer.parseInt(e.getActionCommand());
			dPanel.setThickness(a);
		}
	};
	ActionListener ac2 = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			int a = Integer.parseInt(e.getActionCommand());
			if (a  == 33)
				dPanel.setMultiCast(false);
			else if (a == 55){
				dPanel.setMultiCast(true);
			}
		}
	};
	

}
