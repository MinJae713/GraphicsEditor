package shapes.nPointsStraight;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;

import shapes.GShape;

public class GArrow extends GNPointsStraight { 
	private static final long serialVersionUID = -7884006409520023617L;
	public GArrow() {
		points = new Point[] {new Point(), new Point(), 
					new Point(), new Point(), new Point(), 
					new Point(), new Point()};
	}
	public GArrow(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) { 
		super(shape, bgColor, bdColor, colorStatus);
		points = new Point[] {new Point(), new Point(), 
					new Point(), new Point(), new Point(), 
					new Point(), new Point()};
	}
	@Override
	public GShape clone() {
		return new GArrow();
	}
	@Override
	public GShape clone(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) { 
		return new GArrow(shape, bgColor, bdColor, colorStatus);
	}
	@Override
	protected void setShapeFrame(int x, int y) {
		points[0].setLocation(ox, oy+(y-oy)/3);
		points[1].setLocation(ox+((x-ox)/3)*2, oy+(y-oy)/3);
		points[2].setLocation(ox+((x-ox)/3)*2, oy);
		points[3].setLocation(x, oy+(y-oy)/2);
		points[4].setLocation(ox+((x-ox)/3)*2, y);
		points[5].setLocation(ox+((x-ox)/3)*2, oy+((y-oy)/3)*2);
		points[6].setLocation(ox, oy+((y-oy)/3)*2);
	}
}
