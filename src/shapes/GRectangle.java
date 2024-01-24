package shapes;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Shape;

public class GRectangle extends GShape {
	private static final long serialVersionUID = -7328306857679450449L;
	private int px, py;
	public GRectangle() {
	}
	public GRectangle(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) { 
		super(shape, bgColor, bdColor, colorStatus);
	}
	@Override
	public GShape clone() {
		return new GRectangle();
	}
	@Override
	public GShape clone(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) { 
		return new GRectangle(shape, bgColor, bdColor, colorStatus);
	}
	@Override
	public void setShape(int x1, int y1, int x2, int y2) {
		this.shape = new Rectangle(x1, y1, x2-x1, y2-y1);
	}
	@Override
	public void resizePoint(int x2, int y2) {
		Rectangle rectangle = (Rectangle)shape;
		rectangle.setFrame(
				rectangle.getX(), rectangle.getY(), 
				x2-rectangle.getX(), y2-rectangle.getY());
	}
	@Override
	public void setPoint(int x, int y) {
		this.px = x;
		this.py = y;
	}
	@Override
	public void movePoint(int x, int y) {
		Rectangle rectangle = (Rectangle)shape;
		rectangle.setLocation(rectangle.x+x-px, rectangle.y+y-py);
		this.px = x;
		this.py = y;
	}
}