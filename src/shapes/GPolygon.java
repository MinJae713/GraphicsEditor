package shapes;

import java.awt.Color;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;


public class GPolygon extends GShape {
	private static final long serialVersionUID = 5805156229533210980L;
	private int px, py;
	public GPolygon() {
	}
	public GPolygon(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) { 
		super(shape, bgColor, bdColor, colorStatus);
	}
	@Override
	public GShape clone() {
		return new GPolygon();
	}
	@Override
	public GShape clone(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) { 
		return new GPolygon(shape, bgColor, bdColor, colorStatus);
	}
	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		this.shape = new Polygon();
		Polygon polygon = (Polygon)shape;
		polygon.addPoint(x1, y1);
		polygon.addPoint(x2, y2);
	}
	@Override
	public void pasteShape(int x1, int y1, int x2, int y2) { 
		Polygon prevPolygon = null;
		if (shape instanceof Path2D) { 
			Path2D path = (Path2D)shape;
			prevPolygon = new Polygon();
			prevPolygon.addPoint((int) path.getCurrentPoint().getX(), (int) path.getCurrentPoint().getY());
			for (PathIterator pi = path.getPathIterator(null); !pi.isDone(); pi.next()) {
			    double[] coords = new double[6];
			    switch (pi.currentSegment(coords)) {
			        case PathIterator.SEG_MOVETO:
			        	prevPolygon.addPoint((int) coords[0], (int) coords[1]);
			            break;
			        case PathIterator.SEG_LINETO:
			        	prevPolygon.addPoint((int) coords[0], (int) coords[1]);
			            break;
			        case PathIterator.SEG_QUADTO:
			        	prevPolygon.addPoint((int) coords[0], (int) coords[1]);
			        	prevPolygon.addPoint((int) coords[2], (int) coords[3]);
			            break;
			        case PathIterator.SEG_CUBICTO:
			        	prevPolygon.addPoint((int) coords[0], (int) coords[1]);
			        	prevPolygon.addPoint((int) coords[2], (int) coords[3]);
			        	prevPolygon.addPoint((int) coords[4], (int) coords[5]);
			            break;
			    }
			}
		}
		else prevPolygon = (Polygon)shape;
		Rectangle bound = shape.getBounds();
		int dx = x1-bound.x;
		int dy = y1-bound.y;
		shape = new Polygon();
		Polygon polygon = (Polygon)shape;
		for (int i = 0; i < prevPolygon.npoints; i++)
			polygon.addPoint(
					prevPolygon.xpoints[i]+dx, prevPolygon.ypoints[i]+dy);
	}
	@Override
	public void addPoint(int x, int y) {
		Polygon polygon = (Polygon)shape;
		polygon.addPoint(x, y);
	}
	@Override
	public void resizePoint(int x, int y) {
		Polygon polygon = (Polygon)shape;
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;
	}
	@Override
	public void setPoint(int x, int y) {
		px = x;
		py = y;
	}
	@Override
	public void movePoint(int x, int y) { 
		Polygon polygon = (Polygon)shape;
		Polygon newPolygon = new Polygon();
		for (int i = 0; i < polygon.npoints; i++) {
			newPolygon.addPoint(polygon.xpoints[i]+ x-px, polygon.ypoints[i]+ y-py);
		}
		this.shape = newPolygon;
		px = x;
		py = y;
	}
}
