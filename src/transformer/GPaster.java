package transformer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Vector;

import dialogs.GDialogsFactory;
import shapes.GShape;
import shapes.GShape.ColorStatus;
import transformer.manager.GEditShapePropManager;

public class GPaster extends GTransformer { 
	
	protected GDialogsFactory dialogsFactory;
	protected GEditShapePropManager propManager;
	private int newX1, newY1;
	public GPaster(GShape shape, GDialogsFactory dialogsFactory) {
		super(shape);
		this.dialogsFactory = dialogsFactory;
		propManager = GEditShapePropManager.getInstance();
	}
	@Override
	public void initTransform(int x, int y, Graphics2D graphics2d) {
		Rectangle editShapeBound = propManager.getEditShapeBounds();
		Color editBgColor = propManager.getEditBgColor();
		Color editBdColor = propManager.getEditBdColor();
		ColorStatus editColorStatus = propManager.getEditColorStatus();
		GShape editGShape = propManager.getEditGShape();
		newX1 = x - editShapeBound.width/2;
		newY1 = y - editShapeBound.height/2;
		
		propManager.setEditGShape(editGShape.clone(editGShape.getShape(), 
				editBgColor, editBdColor, editColorStatus));
	}
	@Override
	public void keepTransform(int x, int y, Graphics2D graphics2d) {
		GShape editGShape = propManager.getEditGShape();
		Rectangle editShapeBound = propManager.getEditGShape().getShape().getBounds();
		
		editGShape.pasteShape(newX1, newY1,
				newX1+editShapeBound.width, newY1+editShapeBound.height);
	}
	@Override
	public void finalizeTransform(int x, int y, Graphics2D graphics2d, Vector<GShape> shapes) {
		GShape editGShape = propManager.getEditGShape();
		editGShape.draw(graphics2d);
		shapes.add(editGShape);
	}
}
