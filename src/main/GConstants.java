package main;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import shapes.GLine;
import shapes.GNull;
import shapes.GOval;
import shapes.GPolygon;
import shapes.GRectangle;
import shapes.GSelect;
import shapes.GShape;
import shapes.nPointsStraight.GArrow;
import shapes.nPointsStraight.GStar;
import shapes.nPointsStraight.GTriangle;

public class GConstants {
	
	public class CMainFrame{
		static final int x = 200;
		static final int y = 100;
		static final int w = 600;
		static final int h = 400;
	}
	public enum EUserAction {
		e2Point,
		eNPoint
	}
	public enum EAnchors { 
		NW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
		NN(new Cursor(Cursor.N_RESIZE_CURSOR)),
		NE(new Cursor(Cursor.NE_RESIZE_CURSOR)),
		EE(new Cursor(Cursor.E_RESIZE_CURSOR)),
		SE(new Cursor(Cursor.SE_RESIZE_CURSOR)),
		SS(new Cursor(Cursor.S_RESIZE_CURSOR)),
		SW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
		WW(new Cursor(Cursor.W_RESIZE_CURSOR)),
		RR(new Cursor(Cursor.HAND_CURSOR)),
		MM(new Cursor(Cursor.CROSSHAIR_CURSOR));
		private Cursor cursor;
		private EAnchors(Cursor cursor) {
			if (cursor.getType() == Cursor.HAND_CURSOR) { 
				Toolkit tk = Toolkit.getDefaultToolkit();
				Image image = tk.getImage("cursorImages/RotateCursor.jpg");
				Point point = new Point(20, 20);
				this.cursor = tk.createCustomCursor(image, point, "");
			} else this.cursor = cursor;
		}
		public Cursor getCursor() {
			return cursor;
		}
		public void setCursor(Cursor cursor) {
			this.cursor = cursor;
		}
	}
	public enum EShape {
		eSelect("Select", new GSelect(), EUserAction.e2Point),
		eRectangle("Rectangle", new GRectangle(), EUserAction.e2Point),
		eOval("Oval", new GOval(), EUserAction.e2Point),
		eLine("Line", new GLine(), EUserAction.e2Point),
		ePolygon("Polygon", new GPolygon(), EUserAction.eNPoint),
		eTriangle("Triangle", new GTriangle(), EUserAction.e2Point), 
		eArrow("Arrow", new GArrow(), EUserAction.e2Point),
		eStar("Star", new GStar(), EUserAction.e2Point),
		eDelete("Delete", new GNull(), EUserAction.e2Point);
		
		private String name;
		private Icon icon; 
		private GShape gShape;
		private EUserAction eUserAction;
		
		private EShape(String name, GShape gShape, EUserAction eUserAction) {
			this.name = name;
			this.icon = new ImageIcon("pictures/"+name+".jpg"); 
			this.gShape = gShape;
			this.eUserAction = eUserAction;
		}
		public String getName() {
			return name;
		}
		public Icon getIcon() { 
			return icon;
		}
		public GShape getGShape() {
			return this.gShape;
		}
		public EUserAction getEUserAction() {
			return eUserAction;
		}
	}
	public enum EEditCommand {
		ePaste,
		eDefault
	}
}
