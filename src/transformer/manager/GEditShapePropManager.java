package transformer.manager;

import java.awt.Color;
import java.awt.Rectangle;

import shapes.GShape;
import shapes.GShape.ColorStatus;

public class GEditShapePropManager {
	private static GEditShapePropManager propManager = null;
	private GShape editGShape;
	private GEditShapePropManager() {}
	public static GEditShapePropManager getInstance() {
		if (propManager == null) 
			propManager = new GEditShapePropManager();
		return propManager;
	}
	public GShape getEditGShape() {
		return editGShape;
	}
	public Rectangle getEditShapeBounds() {
		return editGShape.getShape().getBounds();
	}
	public void setEditGShape(GShape editGShape) {
		this.editGShape = editGShape;
	}
	public Color getEditBgColor() {
		return editGShape.getBgColor();
	}
	public Color getEditBdColor() {
		return editGShape.getBdColor();
	}
	public ColorStatus getEditColorStatus() {
		return editGShape.getColorStatus();
	}
}
