package transformer;

import java.awt.Graphics2D;
import java.util.Vector;

import shapes.GShape;

public class GNullTransformer extends GTransformer {

	public GNullTransformer(GShape shape) {
		super(shape);
	}

	@Override
	public void initTransform(int x, int y, Graphics2D graphics2d) {
	}

	@Override
	public void keepTransform(int x, int y, Graphics2D graphics2d) {
	}

	@Override
	public void finalizeTransform(int x, int y, Graphics2D graphics2d, Vector<GShape> shapes) {
	}

}
