package shapes;

import java.awt.Color;
import java.awt.Shape;

public class GNull extends GShape { 
	private static final long serialVersionUID = -3871660988455885970L;
	public GNull() {
	}
	public GNull(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) {
	}
	@Override
	public GShape clone() {
		return new GNull();
	}
	@Override
	public GShape clone(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) {
		return new GNull(shape, bgColor, bdColor, colorStatus);
	}
	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
	}

	@Override
	public void setPoint(int x, int y) {
	}

	@Override
	public void resizePoint(int x, int y) {
	}

	@Override
	public void movePoint(int x, int y) {
	}

	@Override
	public Shape getShape() {
		return null;
	}
}
