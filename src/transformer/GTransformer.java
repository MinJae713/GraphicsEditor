package transformer;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import shapes.GShape;

abstract public class GTransformer {
	
	protected GShape shape;
	protected List<GShape> shapeGroup;
	public GTransformer(GShape shape) {
		this.shape = shape;
		shapeGroup = new ArrayList<GShape>();
	}
	public List<GShape> getShapeGroup() {
		return shapeGroup;
	}
	public void setShapeGroup(List<GShape> shapeGroup) {
		this.shapeGroup = shapeGroup;
	}
	abstract public void initTransform(int x, int y, Graphics2D graphics2d);
	abstract public void keepTransform(int x, int y, Graphics2D graphics2d);
	abstract public void finalizeTransform(int x, int y, Graphics2D graphics2d, Vector<GShape> shapes);

	public void continueTransform(int x, int y, Graphics2D graphics2d) {}
}
