package shapes.nPointsStraight;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;

import shapes.GShape;

public class GStar extends GNPointsStraight {
	private static final long serialVersionUID = 8374887428973509729L;
	public GStar() {
		points = new Point[] {
					new Point(), new Point(), 
					new Point(), new Point(), new Point(), 
					new Point(), new Point(), new Point(), 
					new Point(), new Point()};
	}
	public GStar(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) { 
		super(shape, bgColor, bdColor, colorStatus);
		points = new Point[] {
					new Point(), new Point(), 
					new Point(), new Point(), new Point(), 
					new Point(), new Point(), new Point(), 
					new Point(), new Point()};
	}
	@Override
	public GShape clone() {
		return new GStar();
	}
	
	@Override
	public GShape clone(Shape shape, 
			Color bgColor, Color bdColor, ColorStatus colorStatus) {
		return new GStar(shape, bgColor, bdColor, colorStatus);
	}
	@Override
	protected void setShapeFrame(int x, int y) {
		int w = x-ox;
		int h = y-oy;
		points[0].setLocation(ox,				oy + 21 * h / 60);
		points[1].setLocation(ox + w / 3, 		oy + 21 * h / 60);
		points[2].setLocation(ox + w / 2, 		oy);
		points[3].setLocation(ox + 2 * w / 3, 	oy + 21 * h / 60);
		points[4].setLocation(ox + w, 			oy + 21 * h / 60);
		points[5].setLocation(ox + 23 * w / 30, oy + 39 * h / 60);
		points[6].setLocation(ox + 49 * w / 60,	oy + h);
		points[7].setLocation(ox + w / 2, 		oy + 17 * h / 20);
		points[8].setLocation(ox + 11 * w / 60, oy + h);
		points[9].setLocation(ox + 7 * w / 30, 	oy + 39 * h / 60);
	}
}
