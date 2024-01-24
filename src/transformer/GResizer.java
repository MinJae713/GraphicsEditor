package transformer;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import main.GConstants.EAnchors;
import shapes.GShape;

public class GResizer extends GTransformer {

	private EAnchors eAnchors;
	private AffineTransform affineTransform;
	private double px, py;
	public GResizer(GShape shape, EAnchors eAnchors) {
		super(shape);
		this.eAnchors = eAnchors;
		this.affineTransform = new AffineTransform();
	}
	@Override
	public void initTransform(int x, int y, Graphics2D graphics2d) {
		px = x;
		py = y;
	}
	@Override
	public void keepTransform(int x, int y, Graphics2D graphics2d) {
		if (shapeGroup.size() == 0) oneKeep(x, y, graphics2d);
		else {
			for (GShape gShape : shapeGroup) {
				this.shape = gShape;
				oneKeep(x, y, graphics2d);
			}
		}
		px = x;
		py = y;
	}
	private void oneKeep(int x, int y, Graphics2D graphics2d) {
		Rectangle bound = shape.getShape().getBounds();
		double ox = bound.getX();
		double oy = bound.getY();
		double ow = bound.getWidth();
		double oh = bound.getHeight();
		double dx = (x - px)/ow;
		double dy = (y - py)/oh;
		shape.draw(graphics2d);
		switch (eAnchors) {
		case NW:
			affineTransform.setToTranslation(ox + ow, oy + oh);
			affineTransform.scale(1-dx, 1-dy);
			affineTransform.translate(-(ox + ow), -(oy + oh));
			break;
		case NN:
			affineTransform.setToTranslation(0, oy + oh);
			affineTransform.scale(1, 1-dy);
			affineTransform.translate(0, -(oy + oh));
			break;
		case NE:
			affineTransform.setToTranslation(ox, oy + oh);
			affineTransform.scale(1+dx, 1-dy);
			affineTransform.translate(-ox, -(oy + oh));
			break;
		case WW:
			affineTransform.setToTranslation(ox + ow, 0);
			affineTransform.scale(1-dx, 1);
			affineTransform.translate(-(ox + ow), 0);
			break;
		case EE:
			affineTransform.setToTranslation(ox, 0);
			affineTransform.scale(1+dx, 1);
			affineTransform.translate(-ox, 0);
			break;
		case SW:
			affineTransform.setToTranslation(ox + ow, oy);
			affineTransform.scale(1-dx, 1+dy);
			affineTransform.translate(-(ox + ow), -oy);
			break;
		case SS:
			affineTransform.setToTranslation(0, oy);
			affineTransform.scale(1, 1+dy);
			affineTransform.translate(0, -oy);
			break;
		case SE:
			affineTransform.setToTranslation(ox, oy);
			affineTransform.scale(1+dx, 1+dy);
			affineTransform.translate(-ox, -oy);
			break;
		default:
			break;
		}
		this.shape.setShape(affineTransform.createTransformedShape(this.shape.getShape()));
		shape.draw(graphics2d);
	}
	@Override
	public void finalizeTransform(int x, int y, Graphics2D graphics2d, Vector<GShape> shapes) {
		if (shapeGroup.size() == 0) oneFinalize();
		else {
			for(GShape gShape : shapeGroup) {
				this.shape = gShape;
				oneFinalize();
			}
		}
	}
	private void oneFinalize() {
		this.shape.setSelected(true);
	}
}
