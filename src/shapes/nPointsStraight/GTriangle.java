package shapes.nPointsStraight;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;

import shapes.GShape;

public class GTriangle extends GNPointsStraight { 
	private static final long serialVersionUID = -1345718677161134469L;
	public GTriangle() {
		points = new Point[] 
				{new Point(), new Point(), new Point()};
	}
	public GTriangle(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) { 
		super(shape, bgColor, bdColor, colorStatus);
		points = new Point[] 
				{new Point(), new Point(), new Point()};
	}
	@Override
	public GShape clone() {
		return new GTriangle();
	}
	@Override
	public GShape clone(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) { 
		return new GTriangle(shape, bgColor, bdColor, colorStatus);
	}
	@Override
	protected void setShapeFrame(int x, int y) {
		points[0].setLocation(ox, y);
		points[1].setLocation(x, y);
		points[2].setLocation(ox+(x-ox)/2, oy);
	}
}
