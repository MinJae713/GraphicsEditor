package transformer;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import observer.GGroupCreateObserver;
import shapes.GShape;

public class GSelect extends GTransformer {

	@SuppressWarnings("unused")
	private int px, py;
	private GGroupCreateObserver observer;
	public GSelect(GShape shape, GGroupCreateObserver observer) {
		super(shape);
		this.observer = observer;
	}

	@Override
	public void initTransform(int x, int y, Graphics2D graphics2d) {
		px = x;
		py = y;
		this.shape.setShape(x, y, x, y);
	}

	@Override
	public void keepTransform(int x, int y, Graphics2D graphics2d) {
		this.shape.draw(graphics2d);
		this.shape.resizePoint(x, y);
		this.shape.draw(graphics2d);
		px = x;
		py = y;
	}

	@Override
	public void finalizeTransform(int x, int y, Graphics2D graphics2d, Vector<GShape> shapes) {
		this.shape.draw(graphics2d);
		Rectangle bound = this.shape.getShape().getBounds();
		List<GShape> shapeGroup = new ArrayList<GShape>();
		for(GShape shape : shapes) {
			if(bound.contains(shape.getShape().getBounds())) {
				shape.setSelected(true);
				shapeGroup.add(shape);
			}
		}
		observer.groupCreate(shapeGroup);
		this.shape = null;
	}
}
