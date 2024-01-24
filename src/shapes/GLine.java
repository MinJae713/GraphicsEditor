package shapes;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import main.GConstants.EAnchors;

public class GLine extends GShape {
	private static final long serialVersionUID = -2599908179484069191L;
	private int px, py; 
	public GLine() {
	}
	public GLine(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) { 
		super(shape, bgColor, bdColor, colorStatus);
	}
	@Override
	public GShape clone() {
		return new GLine();
	}
	@Override
	public GShape clone(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) { 
		return new GLine(shape, bgColor, bdColor, colorStatus);
	}
	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		this.shape = new Line2D.Double(x1, y1, x2, y2);
	}
	
	@Override
	public void pasteShape(int x1, int y1, int x2, int y2) { 
		Line2D prevLine = null;
		if (shape instanceof Path2D) {
			Path2D path = (Path2D)shape;
			List<Point> points = new ArrayList<Point>();
			for (PathIterator pi = path.getPathIterator(null); !pi.isDone(); pi.next()) {
			    double[] coords = new double[6];
			    switch (pi.currentSegment(coords)) {
			        case PathIterator.SEG_MOVETO:
			        	points.add(new Point((int)coords[0], (int)coords[1]));
			        	break;
			        case PathIterator.SEG_LINETO:
			        	points.add(new Point((int)coords[0], (int)coords[1]));
			        	break;
			    }
			}
			prevLine = new Line2D.Double(points.get(0), points.get(1));
		} else prevLine = (Line2D)shape;
		Rectangle bound = prevLine.getBounds();
		int dx = x1-bound.x;
		int dy = y1-bound.y;
		
		Point2D prevP1 = prevLine.getP1();
		Point2D prevP2 = prevLine.getP2();
		this.shape = new Line2D.Double(
				prevP1.getX()+dx, prevP1.getY()+dy,
				prevP2.getX()+dx, prevP2.getY()+dy);
	}
	@Override
	public void resizePoint(int x2, int y2) {
		Line2D line2D = ((Line2D)shape);
		line2D.setLine(line2D.getX1(), line2D.getY1(), x2, y2);
	}
	@Override
	public void setPoint(int x, int y) {
		this.px = x;
		this.py = y;
	}
	@Override
	public void movePoint(int x, int y) {
		Line2D line2D = (Line2D)shape;
		int ox1 = (int)line2D.getX1();
		int oy1 = (int)line2D.getY1();
		int ox2 = (int)line2D.getX2();
		int oy2 = (int)line2D.getY2();
		line2D.setLine(ox1 + x-px, oy1 + y-py, ox2 + x-px, oy2 + y-py);
		this.px = x;
		this.py = y;
	}
	@Override
	public EAnchors onShape(int x, int y) {
		if (this.isbSelected()) {
			EAnchors eAnchors = this.getGAnchors().onShape(x, y);
			if (eAnchors != null) return eAnchors;
		}
		if (shape.getBounds().contains(x, y)) return EAnchors.MM;
		return null;
	}
}