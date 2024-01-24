package shapes.nPointsStraight;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;

import shapes.GShape;

public abstract class GNPointsStraight extends GShape { 
	private static final long serialVersionUID = -3026252849642416387L;
	protected int ox, oy;
	protected int px, py;
	protected Point[] points;

	public GNPointsStraight() {
	}
	public GNPointsStraight(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) { 
		super(shape, bgColor, bdColor, colorStatus);
	}
	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		ox = x1;
		oy = y1;
		setShapeFrame(x2, y2);
		
		this.shape = new Polygon();
		Polygon polygon = (Polygon)shape;
		for(int i = 0 ; i < points.length; i++)
			polygon.addPoint(points[i].x, points[i].y);
	}
	@Override
	public void resizePoint(int x, int y) {
		Polygon polygon = (Polygon)shape;
		setShapeFrame(x, y);
		for(int i = 0; i < polygon.npoints; i++) {
			polygon.xpoints[i] = points[i].x;
			polygon.ypoints[i] = points[i].y;
		}
	}
	@Override
	public void setPoint(int x, int y) {
		px = x;
		py = y;
	}
	@Override
	public void movePoint(int x, int y) {
		Polygon polygon = (Polygon)shape;
		polygon.translate(x-px, y-py);
		ox += x-px;
		oy += y-py;
		px = x;
		py = y;
	}
	protected abstract void setShapeFrame(int x, int y);
}
