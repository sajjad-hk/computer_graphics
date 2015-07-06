import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Point BeginL;
	private Point EndL;
	private int[][] PixelArr;
	private int[][] tmpPixelArr;
	private BufferedImage BufImg;
	private int panelW = 700;
	private int panelH = 400;
	private int shapeType;
	private int thickness;
	
	private int[][] PixelArr2;
	private int[][] tmpPixelArr2;
	private Point BeginL2;
	private Point EndL2;
	private int thickness2;
	private boolean multicast;
	
	private ArrayList<Ellipse2D.Double> ellipseList = new ArrayList<Ellipse2D.Double>();


	public DrawingPanel() {

		setDoubleBuffered(true);
		BeginL = null;
		EndL = null;
		BeginL2 = null;
		EndL2 = null;
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(panelW, panelH));
		PixelArr = new int[700][400];
		tmpPixelArr = new int[700][400];
		PixelArr2 = new int [1400][800];
		tmpPixelArr2 = new int [1400][800];
		for (int i = 0; i < panelW; i++) {
			for (int j = 0; j < panelH; j++) {
				
				PixelArr[i][j] = 255;
				tmpPixelArr[i][j] = PixelArr[i][j];
			}
		}
		for (int i = 0; i < 1400; i++) {
			for (int j = 0; j <800; j++) {
				PixelArr2[i][j] = 255;
				tmpPixelArr2[i][j]= PixelArr2[i][j];
			}
		}

		addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {

				if (BeginL != null || BeginL2 != null) {
					EndL = e.getPoint();
					//============================
					EndL2 = e.getPoint();
					EndL2.x = 2*EndL.x;
					EndL2.y = 2*EndL.y;
					//============================
					 Ellipse2D.Double Pointer = new Ellipse2D.Double(EndL.x-5, EndL.y-5,10,10);
						ellipseList.add(Pointer);
					
					repaint();
				} else {
					BeginL = e.getPoint();
					//============================
					BeginL2 = e.getPoint();
					BeginL2.x = 2*BeginL.x;
					BeginL2.y = 2*BeginL.y;
					//============================
					Ellipse2D.Double Pointer = new Ellipse2D.Double(BeginL.x-5, BeginL.y-5,10,10);
					ellipseList.add(Pointer);
					
					repaint();
					
				}
			}

			public void mouseReleased(MouseEvent e) {

				for (int i = 0; i < panelW; i++) {
					for (int j = 0; j < panelH; j++) {
						tmpPixelArr[i][j] = PixelArr[i][j];
					}
				}

			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		
		
		if (multicast == false){
			
			if (BeginL != null ) {
				for (int i = 0; i < panelW; i++) {
					for (int j = 0; j < panelH; j++) {
						PixelArr[i][j] = tmpPixelArr[i][j];
					}
				}
				
				if (BeginL != null && EndL != null){
					Draw(shapeType, thickness);
					BeginL = null;
					EndL = null;
				}
				
	
				BufImg = convertToBufferedImage(PixelArr);
				g2.drawImage(BufImg, 0, 0, null);
				g2.setColor(Color.RED);
				for (Ellipse2D.Double a: ellipseList){
					
					g2.fill(a);
				}
				
			}
		} else if( multicast == true){
			
			if (BeginL2 != null ) {
				for (int i = 0; i < 1400; i++) {
					for (int j = 0; j < 800; j++) {
						PixelArr2[i][j] = tmpPixelArr2[i][j];
					}
				}
				
				if (BeginL2 != null && EndL2 != null){
					Draw2(shapeType, thickness);
					multicastConv();
//					BeginL2 = null;
//					EndL2 = null;
				}
				
				
				
				BufImg = convertToBufferedImage(PixelArr);
				g2.drawImage(BufImg, 0, 0, null);
				g2.setColor(Color.RED);
				for (Ellipse2D.Double a: ellipseList){
					g2.fill(a);
				}
				
			}
		}
	}

	BufferedImage convertToBufferedImage(int[][] array) {

		final BufferedImage outputImage = new BufferedImage(panelW, panelH,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) outputImage.getGraphics();

		for (int i = 0; i < panelW; i++) {
			for (int j = 0; j < panelH; j++) {
				int a = array[i][j];
				g.setColor(new Color(a, a, a));
				g.fillRect(i, j, 1, 1);
			}
		}
		return outputImage;
	}

	public void update(Graphics g) {

		paintComponent(g);
	}
	
	public void Draw(int type, int thickness){
		
		switch (type) {
		case 0: drawLine(BeginL, EndL, thickness);break;
		case 1: drawCircle(BeginL, EndL);
		}
		
	}
public void Draw2(int type, int thickness){
		
		switch (type) {
		case 0: drawLine2(BeginL2, EndL2, thickness);break;
		case 1: drawCircle2(BeginL2, EndL2);
		}
		
	}
	
public void drawLine(Point A, Point B, int thickness) {
		
	float dy = B.y - A.y;
	float dx = B.x - A.x;
	float steps;
	float m1, m2 ;
	if(Math.abs(dx)>Math.abs(dy))
		 steps=Math.abs(dx);
	else
		 steps=Math.abs(dy);
	m1=(dx/steps);
	m2=(dy/steps);
		 float y1 = A.y;
		 float x1 = A.x;
		 switch (thickness){
			case 1:pen1pt(A.x, Math.round(A.y));break;
			case 2:pen3pt(A.x, Math.round(A.y));break;
			case 3:pen5pt(A.x, Math.round(A.y));break;
			case 4:pen7pt(A.x, Math.round(A.y));break;
			}
		 
		 for(int k=1;k<=steps;k++)
		 {
			 x1+=m1;
			 y1+=m2;
			 switch (thickness){
				case 1:pen1pt(Math.round(x1), Math.round(y1));break;
				case 2:pen3pt(Math.round(x1), Math.round(y1));break;
				case 3:pen5pt(Math.round(x1), Math.round(y1));break;
				case 4:pen7pt(Math.round(x1), Math.round(y1));break;
				}
		 }
	}

public void drawLine2(Point A, Point B, int thickness) {
	
	float dy = B.y - A.y;
	float dx = B.x - A.x;
	float steps;
	float m1, m2 ;
	
	if(Math.abs(dx)>Math.abs(dy))
		 steps=Math.abs(dx);
	else
		 steps=Math.abs(dy);
	m1=(dx/steps);
	m2=(dy/steps);
		 float y1 = A.y;
		 float x1 = A.x;
		 switch (thickness){
			case 1:pen3pt2(A.x, Math.round(A.y));break;
			case 2:pen7pt2(A.x, Math.round(A.y));break;
			case 3:pen5pt2(A.x, Math.round(A.y));break;
			case 4:pen7pt2(A.x, Math.round(A.y));break;
			}
		 
		 for(int k=1;k<=steps;k++)
		 {
			 x1+=m1;
			 y1+=m2;
			 switch (thickness){
				case 1:pen3pt2(Math.round(x1), Math.round(y1));break;
				case 2:pen7pt2(Math.round(x1), Math.round(y1));break;
				case 3:pen5pt2(Math.round(x1), Math.round(y1));break;
				case 4:pen7pt2(Math.round(x1), Math.round(y1));break;
				}
		 }
	}

	public void drawCircle(Point center, Point e) {
		
		
		double dist = (e.x-center.x)*(e.x-center.x) + (e.y-center.y)*(e.y-center.y);
		int radius = (int) Math.floor(Math.sqrt(dist));
		
		int d = 1-radius;
		int x = 0;
		int y = radius;
		
		
		while (y > x)
		{
			switch (thickness){
			case 1:
				pen1pt(center.x + x, center.y + y);
				pen1pt(center.x - x, center.y + y);
				pen1pt(center.x + x, center.y - y);
				pen1pt(center.x - x, center.y - y);
				pen1pt(center.x + y, center.y + x);
				pen1pt(center.x - y, center.y + x);
				pen1pt(center.x + y, center.y - x);
				pen1pt(center.x - y, center.y - x);
				break;
			case 2:
				pen3pt(center.x + x, center.y + y);
				pen3pt(center.x - x, center.y + y);
				pen3pt(center.x + x, center.y - y);
				pen3pt(center.x - x, center.y - y);
				pen3pt(center.x + y, center.y + x);
				pen3pt(center.x - y, center.y + x);
				pen3pt(center.x + y, center.y - x);
				pen3pt(center.x - y, center.y - x);
				;break;
			case 3:
				pen5pt(center.x + x, center.y + y);
				pen5pt(center.x - x, center.y + y);
				pen5pt(center.x + x, center.y - y);
				pen5pt(center.x - x, center.y - y);
				pen5pt(center.x + y, center.y + x);
				pen5pt(center.x - y, center.y + x);
				pen5pt(center.x + y, center.y - x);
				pen5pt(center.x - y, center.y - x);
				break;
			case 4:
				pen7pt(center.x + x, center.y + y);
				pen7pt(center.x - x, center.y + y);
				pen7pt(center.x + x, center.y - y);
				pen7pt(center.x - x, center.y - y);
				pen7pt(center.x + y, center.y + x);
				pen7pt(center.x - y, center.y + x);
				pen7pt(center.x + y, center.y - x);
				pen7pt(center.x - y, center.y - x);
				break;
			}
			
			
		if ( d < 0 ) //move to E
		d += 2*x + 3;
		else //move to SE
		{
			
		d += 2*x - 2*y + 5;
		--y;
		
		}
		++x;
		switch (thickness){
		case 1:
			pen1pt(center.x + x, center.y + y);
			pen1pt(center.x - x, center.y + y);
			pen1pt(center.x + x, center.y - y);
			pen1pt(center.x - x, center.y - y);
			pen1pt(center.x + y, center.y + x);
			pen1pt(center.x - y, center.y + x);
			pen1pt(center.x + y, center.y - x);
			pen1pt(center.x - y, center.y - x);
			break;
		case 2:
			pen3pt(center.x + x, center.y + y);
			pen3pt(center.x - x, center.y + y);
			pen3pt(center.x + x, center.y - y);
			pen3pt(center.x - x, center.y - y);
			pen3pt(center.x + y, center.y + x);
			pen3pt(center.x - y, center.y + x);
			pen3pt(center.x + y, center.y - x);
			pen3pt(center.x - y, center.y - x);
			;break;
		case 3:
			pen5pt(center.x + x, center.y + y);
			pen5pt(center.x - x, center.y + y);
			pen5pt(center.x + x, center.y - y);
			pen5pt(center.x - x, center.y - y);
			pen5pt(center.x + y, center.y + x);
			pen5pt(center.x - y, center.y + x);
			pen5pt(center.x + y, center.y - x);
			pen5pt(center.x - y, center.y - x);
			break;
		case 4:
			pen7pt(center.x + x, center.y + y);
			pen7pt(center.x - x, center.y + y);
			pen7pt(center.x + x, center.y - y);
			pen7pt(center.x - x, center.y - y);
			pen7pt(center.x + y, center.y + x);
			pen7pt(center.x - y, center.y + x);
			pen7pt(center.x + y, center.y - x);
			pen7pt(center.x - y, center.y - x);
			break;
		}
		
		
		}
	}

public void drawCircle2(Point center, Point e) {
		
		
		double dist = (e.x-center.x)*(e.x-center.x) + (e.y-center.y)*(e.y-center.y);
		int radius = (int) Math.floor(Math.sqrt(dist));
		
		int d = 1-radius;
		int x = 0;
		int y = radius;
		
		
		while (y > x)
		{
			switch (thickness){
			case 1:
				pen3pt2(center.x + x, center.y + y);
				pen3pt2(center.x - x, center.y + y);
				pen3pt2(center.x + x, center.y - y);
				pen3pt2(center.x - x, center.y - y);
				pen3pt2(center.x + y, center.y + x);
				pen3pt2(center.x - y, center.y + x);
				pen3pt2(center.x + y, center.y - x);
				pen3pt2(center.x - y, center.y - x);
				break;
			case 2:
				pen7pt2(center.x + x, center.y + y);
				pen7pt2(center.x - x, center.y + y);
				pen7pt2(center.x + x, center.y - y);
				pen7pt2(center.x - x, center.y - y);
				pen7pt2(center.x + y, center.y + x);
				pen7pt2(center.x - y, center.y + x);
				pen7pt2(center.x + y, center.y - x);
				pen7pt2(center.x - y, center.y - x);
				;break;
			case 3:
				pen5pt2(center.x + x, center.y + y);
				pen5pt2(center.x - x, center.y + y);
				pen5pt2(center.x + x, center.y - y);
				pen5pt2(center.x - x, center.y - y);
				pen5pt2(center.x + y, center.y + x);
				pen5pt2(center.x - y, center.y + x);
				pen5pt2(center.x + y, center.y - x);
				pen5pt2(center.x - y, center.y - x);
				break;
			case 4:
				pen7pt2(center.x + x, center.y + y);
				pen7pt2(center.x - x, center.y + y);
				pen7pt2(center.x + x, center.y - y);
				pen7pt2(center.x - x, center.y - y);
				pen7pt2(center.x + y, center.y + x);
				pen7pt2(center.x - y, center.y + x);
				pen7pt2(center.x + y, center.y - x);
				pen7pt2(center.x - y, center.y - x);
				break;
			}
			
			
		if ( d < 0 ) //move to E
		d += 2*x + 3;
		else //move to SE
		{
			
		d += 2*x - 2*y + 5;
		--y;
		
		}
		++x;
		switch (thickness){
		case 1:
			pen3pt2(center.x + x, center.y + y);
			pen3pt2(center.x - x, center.y + y);
			pen3pt2(center.x + x, center.y - y);
			pen3pt2(center.x - x, center.y - y);
			pen3pt2(center.x + y, center.y + x);
			pen3pt2(center.x - y, center.y + x);
			pen3pt2(center.x + y, center.y - x);
			pen3pt2(center.x - y, center.y - x);
			break;
		case 2:
			pen7pt2(center.x + x, center.y + y);
			pen7pt2(center.x - x, center.y + y);
			pen7pt2(center.x + x, center.y - y);
			pen7pt2(center.x - x, center.y - y);
			pen7pt2(center.x + y, center.y + x);
			pen7pt2(center.x - y, center.y + x);
			pen7pt2(center.x + y, center.y - x);
			pen7pt2(center.x - y, center.y - x);
			;break;
		case 3:
			pen5pt2(center.x + x, center.y + y);
			pen5pt2(center.x - x, center.y + y);
			pen5pt2(center.x + x, center.y - y);
			pen5pt2(center.x - x, center.y - y);
			pen5pt2(center.x + y, center.y + x);
			pen5pt2(center.x - y, center.y + x);
			pen5pt2(center.x + y, center.y - x);
			pen5pt2(center.x - y, center.y - x);
			break;
		case 4:
			pen7pt2(center.x + x, center.y + y);
			pen7pt2(center.x - x, center.y + y);
			pen7pt2(center.x + x, center.y - y);
			pen7pt2(center.x - x, center.y - y);
			pen7pt2(center.x + y, center.y + x);
			pen7pt2(center.x - y, center.y + x);
			pen7pt2(center.x + y, center.y - x);
			pen7pt2(center.x - y, center.y - x);
			break;
		}
		
		
		}
	}

	
	private void pen1pt(int x, int y){
		
		
		PixelArr[x][y] = 33;
		
	}
private void pen3pt(int x, int y){
		
		PixelArr[x+1][y] = 33;
		PixelArr[x][y+1] = 33;
		PixelArr[x][y] = 33;
		PixelArr[x][y-1] = 33;
		PixelArr[x-1][y] = 33;
		
}
	
private void pen5pt(int x, int y){
		
		PixelArr[x][y-2] = 33;
		PixelArr[x+1][y-2] = 33;
		PixelArr[x-1][y-2] = 33;
		
		PixelArr[x][y-1] = 33;
		PixelArr[x+1][y-1] = 33;
		PixelArr[x+2][y-1] = 33;
		PixelArr[x-1][y-1] = 33;
		PixelArr[x-2][y-1] = 33;
		
		PixelArr[x][y] = 33;
		PixelArr[x+1][y] = 33;
		PixelArr[x+2][y] = 33;
		PixelArr[x-1][y] = 33;
		PixelArr[x-2][y] = 33;
		
		PixelArr[x][y+1] = 33;
		PixelArr[x+1][y+1] = 33;
		PixelArr[x+2][y+1] = 33;
		PixelArr[x-1][y+1] = 33;
		PixelArr[x-2][y+1] = 33;
		
		PixelArr[x][y+2] = 33;
		PixelArr[x+1][y+2] = 33;
		PixelArr[x-1][y+2] = 33;
		
		
	}

private void pen7pt(int x, int y){
	
	PixelArr[x][y-3] = 33;
	PixelArr[x+1][y-3] = 33;
	PixelArr[x-1][y-3] = 33;
	
	PixelArr[x][y-2] = 33;
	PixelArr[x+1][y-2] = 33;
	PixelArr[x+2][y-2] = 33;
	PixelArr[x-1][y-2] = 33;
	PixelArr[x-2][y-2] = 33;
	
	PixelArr[x][y-1] = 33;
	PixelArr[x+1][y-1] = 33;
	PixelArr[x+2][y-1] = 33;
	PixelArr[x+3][y-1] = 33;
	PixelArr[x-1][y-1] = 33;
	PixelArr[x-2][y-1] = 33;
	PixelArr[x-3][y-1] = 33;
	
	PixelArr[x][y] = 33;
	PixelArr[x+1][y] = 33;
	PixelArr[x+2][y] = 33;
	PixelArr[x+3][y] = 33;
	PixelArr[x-1][y] = 33;
	PixelArr[x-2][y] = 33;
	PixelArr[x-3][y] = 33;
	
	PixelArr[x][y+1] = 33;
	PixelArr[x+1][y+1] = 33;
	PixelArr[x+2][y+1] = 33;
	PixelArr[x+3][y+1] = 33;
	PixelArr[x-1][y+1] = 33;
	PixelArr[x-2][y+1] = 33;
	PixelArr[x-3][y+1] = 33;
	
	PixelArr[x][y+2] = 33;
	PixelArr[x+1][y+2] = 33;
	PixelArr[x+2][y+2] = 33;
	PixelArr[x-1][y+2] = 33;
	PixelArr[x-2][y+2] = 33;
	
	PixelArr[x][y+3] = 33;
	PixelArr[x+1][y+3] = 33;
	PixelArr[x-1][y+3] = 33;
}

private void pen1pt2(int x, int y){
	
	PixelArr2[x][y] = 33;
	
}
private void pen3pt2(int x, int y){
	
	PixelArr2[x+1][y] = 33;
	PixelArr2[x][y+1] = 33;
	PixelArr2[x][y] = 33;
	PixelArr2[x][y-1] = 33;
	PixelArr2[x-1][y] = 33;
	
}

private void pen5pt2(int x, int y){
	
	PixelArr2[x][y-2] = 33;
	PixelArr2[x+1][y-2] = 33;
	PixelArr2[x-1][y-2] = 33;
	
	PixelArr2[x][y-1] = 33;
	PixelArr2[x+1][y-1] = 33;
	PixelArr2[x+2][y-1] = 33;
	PixelArr2[x-1][y-1] = 33;
	PixelArr2[x-2][y-1] = 33;
	
	PixelArr2[x][y] = 33;
	PixelArr2[x+1][y] = 33;
	PixelArr2[x+2][y] = 33;
	PixelArr2[x-1][y] = 33;
	PixelArr2[x-2][y] = 33;
	
	PixelArr2[x][y+1] = 33;
	PixelArr2[x+1][y+1] = 33;
	PixelArr2[x+2][y+1] = 33;
	PixelArr2[x-1][y+1] = 33;
	PixelArr2[x-2][y+1] = 33;
	
	PixelArr2[x][y+2] = 33;
	PixelArr2[x+1][y+2] = 33;
	PixelArr2[x-1][y+2] = 33;
	
	
}

private void pen7pt2(int x, int y){

PixelArr2[x][y-3] = 33;
PixelArr2[x+1][y-3] = 33;
PixelArr2[x-1][y-3] = 33;

PixelArr2[x][y-2] = 33;
PixelArr2[x+1][y-2] = 33;
PixelArr2[x+2][y-2] = 33;
PixelArr2[x-1][y-2] = 33;
PixelArr2[x-2][y-2] = 33;

PixelArr2[x][y-1] = 33;
PixelArr2[x+1][y-1] = 33;
PixelArr2[x+2][y-1] = 33;
PixelArr2[x+3][y-1] = 33;
PixelArr2[x-1][y-1] = 33;
PixelArr2[x-2][y-1] = 33;
PixelArr2[x-3][y-1] = 33;

PixelArr2[x][y] = 33;
PixelArr2[x+1][y] = 33;
PixelArr2[x+2][y] = 33;
PixelArr2[x+3][y] = 33;
PixelArr2[x-1][y] = 33;
PixelArr2[x-2][y] = 33;
PixelArr2[x-3][y] = 33;

PixelArr2[x][y+1] = 33;
PixelArr2[x+1][y+1] = 33;
PixelArr2[x+2][y+1] = 33;
PixelArr2[x+3][y+1] = 33;
PixelArr2[x-1][y+1] = 33;
PixelArr2[x-2][y+1] = 33;
PixelArr2[x-3][y+1] = 33;

PixelArr2[x][y+2] = 33;
PixelArr2[x+1][y+2] = 33;
PixelArr2[x+2][y+2] = 33;
PixelArr2[x-1][y+2] = 33;
PixelArr2[x-2][y+2] = 33;

PixelArr2[x][y+3] = 33;
PixelArr2[x+1][y+3] = 33;
PixelArr2[x-1][y+3] = 33;
}
	public void setShapeType(int s){
		this.shapeType = s;
	}
	public void setThickness(int s){
		this.thickness = s;
	}
	public void setThickness2(int s){
		this.thickness = 2*s;
	}
	
	public void setMultiCast(boolean a){
		this.multicast = a;
		
	}
	
	public void multicastConv(){
		
		//int [][] tmpPixel = new int [700][400];
		for (int i=0; i<PixelArr.length; i++){
			for (int j=0; j<PixelArr[0].length; j++){
				PixelArr[i][j] = (PixelArr2[2*i][2*j]+PixelArr2[2*i+1][2*j]+PixelArr2[2*i][2*j+1]+PixelArr2[2*i+1][2*j+1])/4;
				
			}
			
		}
		
	}

}
