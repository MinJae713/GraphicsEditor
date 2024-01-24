package transformer;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import shapes.GShape;

public class GMover extends GTransformer {

	private AffineTransform affineTransform;
	private int px, py;
	
	public GMover(GShape shape) {
		super(shape);
		this.affineTransform = new AffineTransform();
	}

	@Override
	public void initTransform(int x, int y, Graphics2D graphics2d) {
		px = x;
		py = y;
	}

	@Override
	public void keepTransform(int x, int y, Graphics2D graphics2d) {
		if(shapeGroup.size() == 0) oneKeep(x, y, graphics2d);
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
		this.shape.draw(graphics2d);
		this.affineTransform.setToTranslation(x-px, y-py);
		Shape transformedShape = this.affineTransform.createTransformedShape(this.shape.getShape());
		this.shape.setShape(transformedShape);
		this.shape.draw(graphics2d);
	}

	@Override
	public void finalizeTransform(int x, int y, Graphics2D graphics2D, Vector<GShape> shapes) {
		if (shapeGroup.size() == 0) oneFinalize(graphics2D);
		else {
			for (GShape gShape : shapeGroup) {
				this.shape = gShape;
				oneFinalize(graphics2D);
			}
		}
	}

	private void oneFinalize(Graphics2D graphics2D) {
		this.shape.setSelected(true);
		this.shape.draw(graphics2D);
	}
}
