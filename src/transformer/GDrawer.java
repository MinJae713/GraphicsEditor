package transformer;

import java.awt.Graphics2D;
import java.util.Vector;

import shapes.GShape;

public class GDrawer extends GTransformer {
	
	public GDrawer(GShape shape) {
		super(shape);
	}
	public void initTransform(int x, int y, Graphics2D graphics2D) {
		this.shape.setShape(x, y, x, y);
	}
	public void keepTransform(int x, int y, Graphics2D graphics2D) {
		this.shape.draw(graphics2D);
		this.shape.resizePoint(x, y);
		this.shape.draw(graphics2D);
	}
	@Override
	public void continueTransform(int x, int y, Graphics2D graphics2d) {
		this.shape.addPoint(x, y);
	}
	public void finalizeTransform(int x, int y, Graphics2D graphics2D, Vector<GShape> shapes) {
		shapes.add(this.shape);
		this.shape.setSelected(true);
		this.shape.draw(graphics2D);
	}
}
