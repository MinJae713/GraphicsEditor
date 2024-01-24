package transformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.Vector;

import shapes.GShape;

public class GRotator extends GTransformer { 
	private int px, py;
	AffineTransform affineTransform;
	private Point rotatePoint;
	private boolean isKeepInit; 
	private int initCenterX; 
	private int initCenterY; 
	public GRotator(GShape shape) {
		super(shape);
		affineTransform = new AffineTransform();
		isKeepInit = true;
	}
	@Override
	public void initTransform(int x, int y, Graphics2D graphics2d) {
		px = x;
		py = y;
		int centerX = (int)shape.getShape().getBounds().getCenterX();
		int centerY = (int)shape.getShape().getBounds().getCenterY();
		rotatePoint = new Point(centerX, centerY);
	}
	private void oneRotate(Graphics2D graphics2D, Point startP, Point endP) {
    	shape.draw(graphics2D);
    	double rotateAngle = Math.toRadians(computeRotateAngle(rotatePoint, startP, endP));
    	affineTransform.setToRotation(rotateAngle, this.rotatePoint.getX(), this.rotatePoint.getY());
    	shape.setShape(affineTransform.createTransformedShape(this.shape.getShape()));
    	shape.draw(graphics2D);
    }
	@Override
	public void keepTransform(int x, int y, Graphics2D graphics2d) { 
		if (isKeepInit) {
			initCenterX = (int)shape.getShape().getBounds().getCenterX();
			initCenterY = (int)shape.getShape().getBounds().getCenterY();
			isKeepInit = false;
		}
		int dPCX = initCenterX - px;
		int dPCY = initCenterY - py; 
		int dSCX = initCenterX - x;
		int dSCY = initCenterY - y; 
		
		if ( this.shapeGroup.size() == 0 ) oneRotate(graphics2d, new Point(px, py), new Point(x,y));
		else {
			for ( GShape shape : this.shapeGroup ) {
				int centerX = (int)shape.getShape().getBounds().getCenterX();
				int centerY = (int)shape.getShape().getBounds().getCenterY();
				this.shape = shape;
				this.rotatePoint = new Point(centerX, centerY); 
				Point startP = new Point(rotatePoint.x-dPCX, rotatePoint.y-dPCY);
				Point endP = new Point(rotatePoint.x-dSCX, rotatePoint.y-dSCY);
				oneRotate(graphics2d, startP, endP);
			}
		}
		px = x;
		py = y;
	}
	private double computeRotateAngle(Point centerP, Point startP, Point endP) { 
    	double startAngle = Math.toDegrees(Math.atan2(startP.getY() - centerP.getY(), startP.getX()-centerP.getX()));
    	double endAngle = Math.toDegrees(Math.atan2(endP.getY()-centerP.getY(), endP.getX()-centerP.getX()));
    	double angle = endAngle - startAngle;
    	if(angle<0) angle += 360;
    	return angle;
    }

	@Override
	public void finalizeTransform(int x, int y, Graphics2D graphics2d, Vector<GShape> shapes) {
		if (shapeGroup.size() == 0) oneFinalize();
		else {
			for(GShape gShape : shapeGroup) {
				this.shape = gShape;
				oneFinalize();
			}
		}
	}
	private void oneFinalize() {
		this.shape.setSelected(true);
	}
}
