package shapes;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.io.Serializable;

import main.GConstants.EAnchors;

abstract public class GShape implements Serializable { 
	private static final long serialVersionUID = 5529213556315079694L;
	protected Shape shape;
	private boolean bSelected;
	private GAnchors gAnchors;
	protected Color bgColor = Color.WHITE;
	protected Color bdColor = Color.BLACK;
	public enum ColorStatus {
		backgraound,
		border,
		both,
	}
	protected ColorStatus colorStatus = ColorStatus.border;
	public void setColorStatus(int option) {
		if (colorStatus == ColorStatus.both) return;
		if (option == 0) {
			if (colorStatus == ColorStatus.backgraound) {
				colorStatus = ColorStatus.both;
			} else {
				colorStatus = ColorStatus.border;
			}
		}else if(option == 1) {
			if (colorStatus == ColorStatus.border) {
				colorStatus = ColorStatus.both;
			} else {
				colorStatus = ColorStatus.backgraound;
			}
		}
	}
	public Shape getShape() {return this.shape;}
	public void setShape(Shape shape) { this.shape = shape; }
	
	public GShape() {
		this.gAnchors = new GAnchors();
		this.bSelected = false;
	}
	public GShape(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus) { 
		this.shape = shape;
		this.bgColor = bgColor;
		this.bdColor = bdColor;
		this.colorStatus = colorStatus;
		this.gAnchors = new GAnchors();
		this.bSelected = false;
	}
	abstract public GShape clone();
	abstract public GShape clone(Shape shape, Color bgColor, 
			Color bdColor, ColorStatus colorStatus); 
	public boolean onShape(Point p) {
		return shape.contains(p);
	}
	public void draw(Graphics2D graphics2D) {
		graphics2D.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON
		);
		if (colorStatus == ColorStatus.border) {
			graphics2D.setColor(bdColor);
			graphics2D.draw(shape);
		} else if (colorStatus == ColorStatus.backgraound) {
			graphics2D.setColor(bgColor);
			graphics2D.fill(shape);
		} else if (colorStatus == ColorStatus.both) {
			graphics2D.setColor(bgColor);
			graphics2D.fill(shape);
			graphics2D.setColor(bdColor);
			graphics2D.draw(shape);
		}
		if (this.bSelected) {
			graphics2D.setColor(Color.BLACK);
			this.gAnchors.draw(graphics2D, this.shape.getBounds());
		}
	}
	public void setSelected(boolean bSelected) {
		this.bSelected = bSelected;
	}
	public EAnchors onShape(int x, int y) {
		if (this.bSelected) {
			EAnchors eAnchors = this.gAnchors.onShape(x, y);
			if (eAnchors != null) {
				return eAnchors;
			}
		}
		if (this.shape.contains(x, y)) { 
			return EAnchors.MM;
		}
		return null;
	}
	protected boolean isbSelected() {
		return bSelected;
	}
	protected GAnchors getGAnchors() {
		return gAnchors;
	}
	public void setBackgroundColor(Color bgColor) {
		this.bgColor = bgColor;
	}
	public void setBorderColor(Color bdColor) {
		this.bdColor = bdColor;
	}
	public Color getBgColor() {
		return bgColor;
	}
	public Color getBdColor() {
		return bdColor;
	}
	public ColorStatus getColorStatus() {
		return colorStatus;
	}
	public void pasteShape(int x1, int y1, int x2, int y2) {
		setShape(x1, y1, x2, y2); 
	}
	public abstract void setShape(int x1, int y1, int x2, int y2);
	public abstract void setPoint(int x, int y);
	public abstract void resizePoint(int x, int y);
	public abstract void movePoint(int x, int y);
	public void addPoint(int x, int y) {}
	
}