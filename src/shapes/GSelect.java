package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

public class GSelect extends GRectangle {
	private static final long serialVersionUID = 8703509775439083901L;
	public GSelect() {
	}
	public GSelect(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) {
	}
	@Override
	public void draw(Graphics2D graphics) {
		Graphics2D graphics2d = (Graphics2D)graphics;
		graphics2d.setColor(Color.CYAN);
		graphics2d.draw(shape);
	}
}