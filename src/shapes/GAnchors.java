package shapes;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

import main.GConstants.EAnchors;

public class GAnchors implements Serializable{
	private static final long serialVersionUID = -8521146661164499889L;
	static final int w = 10;
	static final int h = 10;
	private Ellipse2D[] anchors;
	
	public GAnchors() {
		anchors = new Ellipse2D[EAnchors.values().length-1];
		for (int i = 0; i < anchors.length ; i++) {
			anchors[i] = new Ellipse2D.Float(0, 0, w, h);
		}
	}
	public void setPosition(Rectangle r) {
		int x = r.x - w/2;
		int y = r.y - h/2;
		anchors[EAnchors.NW.ordinal()].setFrame(x, 			y, 				w, h);
		anchors[EAnchors.NN.ordinal()].setFrame(x+r.width/2,y, 				w, h);
		anchors[EAnchors.NE.ordinal()].setFrame(x+r.width, 	y, 				w, h);
		anchors[EAnchors.EE.ordinal()].setFrame(x+r.width, 	y+r.height/2,	w, h);
		anchors[EAnchors.SE.ordinal()].setFrame(x+r.width, 	y+r.height, 	w, h);
		anchors[EAnchors.SS.ordinal()].setFrame(x+r.width/2,y+r.height, 	w, h);
		anchors[EAnchors.SW.ordinal()].setFrame(x, 			y+r.height, 	w, h);
		anchors[EAnchors.WW.ordinal()].setFrame(x, 			y+r.height/2, 	w, h);
		anchors[EAnchors.RR.ordinal()].setFrame(x+r.width/2,y-30, 			w, h);
	}
	public void draw(Graphics2D graphics2D, Rectangle rectangle) {
		setPosition(rectangle);
		for(Ellipse2D anchor : anchors) {
			graphics2D.draw(anchor);
		}
	}
	public EAnchors onShape(int x, int y) {
		for(int i = 0; i < anchors.length; i++) {
			if (anchors[i].contains(x, y)) {
				return EAnchors.values()[i];
			}
		}
		return null;
	}
}
