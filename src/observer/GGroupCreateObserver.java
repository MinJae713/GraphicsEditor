package observer;

import java.util.List;

import shapes.GShape;

public interface GGroupCreateObserver {
	public void groupCreate(List<GShape> shapeGroup);
}
