package frames;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;

import dialogs.GDialogsFactory;
import dialogs.GDialogsFactory.EMessageType;
import main.GConstants.EAnchors;
import main.GConstants.EEditCommand;
import main.GConstants.EShape;
import main.GConstants.EUserAction;
import observer.GEdit;
import observer.GEditChecking;
import observer.GFileIO;
import observer.GGroupCreateObserver;
import shapes.GLine;
import shapes.GShape;
import transformer.GDrawer;
import transformer.GMover;
import transformer.GNullTransformer;
import transformer.GPaster;
import transformer.GResizer;
import transformer.GRotator;
import transformer.GSelect;
import transformer.GTransformer;
import transformer.manager.GEditShapePropManager;

public class GDrawingPanel extends JPanel 
implements GGroupCreateObserver, GFileIO, GEdit, GEditChecking {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private enum EDrawingEvent {
		eStart,
		eMoving,
		eCont,
		eEnd
	}
	private enum EDrawingState {
		eIdle, 
		eTransforming
	}

	private EDrawingState eDrawingState;
	private Vector<GShape> shapes;
	private List<GShape> selectedShapeGroup;
	private GShape currentShape;
	private GToolBar toolBar;
	private boolean editResult;
	private GDialogsFactory dialogsFactory;
	private EEditCommand editCommand;
	private GEditShapePropManager propManager;
	private GShape editShape; 
	public void setToolBar(GToolBar toolBar) {
		this.toolBar = toolBar;
	}
	private GTransformer transformer;

	public GDrawingPanel() {
		super();
		this.eDrawingState = EDrawingState.eIdle;
		this.shapes = new Vector<GShape>();
		this.currentShape = null;
		selectedShapeGroup = new ArrayList<GShape>();
		this.editResult = false;
		dialogsFactory = new GDialogsFactory(this);
		editCommand = EEditCommand.eDefault;
		transformer = new GNullTransformer(currentShape);
		propManager = GEditShapePropManager.getInstance();
		editShape = null; 

		this.setBackground(Color.WHITE);
		MouseHandler mouseEventHandler = new MouseHandler();
		this.addMouseListener(mouseEventHandler);
		this.addMouseMotionListener(mouseEventHandler);
	}

	public void paint(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D)graphics;
		super.paint(graphics2D);
		for (GShape shape : this.shapes) {
			shape.draw(graphics2D);
		}
		if ( this.currentShape != null ) 
			this.currentShape.draw(graphics2D);
	}
	private EAnchors onShape(int x, int y) {
		for (GShape gShape : shapes) {
			EAnchors eAnchors = gShape.onShape(x, y);
			if (eAnchors != null) {
				currentShape = gShape;
				if (!inGroup(currentShape)) selectedShapeGroup.clear();
				return eAnchors;
			}
		}
		return null;
	}
	private boolean inGroup(GShape shape) {
		for(GShape gShape : selectedShapeGroup) {
			if (gShape.equals(shape)) return true;
		}
		return false;
	}
	private void clearSelection() {
		for (GShape shape : shapes) {
			shape.setSelected(false);
		}
	}
	public void initTransforming(int x, int y) {
		Graphics2D graphics2D = (Graphics2D)this.getGraphics();
		if (this.toolBar.getESelectedShape() == EShape.eSelect) {
			EAnchors eSelectedAnchor = this.onShape(x, y);
			if (eSelectedAnchor == null) {
				editShape = null;
				if (editCommand == EEditCommand.ePaste) {
					this.transformer = new GPaster(null, dialogsFactory);
					this.transformer.initTransform(x, y, graphics2D);
				} else {
					this.clearSelection();
					this.currentShape = this.toolBar.getESelectedShape().getGShape();
					this.transformer = new GSelect(this.currentShape, this);
					this.transformer.initTransform(x, y, graphics2D);
				}
			} else { 
				this.clearSelection();
				editShape = currentShape; 
				if (selectedShapeGroup.size() == 0) this.currentShape.setSelected(true);
				else for (GShape gShape : selectedShapeGroup) gShape.setSelected(true);
				this.repaint();
				switch (eSelectedAnchor) {
				case MM: 
					this.transformer = new GMover(this.currentShape);
					transformer.setShapeGroup(selectedShapeGroup);
					this.transformer.initTransform(x, y, graphics2D);
					break;
				case RR: 
					this.transformer = new GRotator(currentShape);
					transformer.setShapeGroup(selectedShapeGroup);
					this.transformer.initTransform(x, y, graphics2D);
					break;
				default: 
					this.transformer = new GResizer(currentShape, eSelectedAnchor);
					transformer.setShapeGroup(selectedShapeGroup);
					this.transformer.initTransform(x, y, graphics2D);
					break;
				}
			}
		} else if (this.toolBar.getESelectedShape() == EShape.eDelete) {
			EAnchors eSelectedAnchor = this.onShape(x, y);
			if (eSelectedAnchor != null) {
				shapes.remove(currentShape);
				currentShape = null;
				repaint();
				this.transformer = new GNullTransformer(currentShape);
			}
		} else {
			if (this.toolBar.getESelectedShape().getEUserAction() == EUserAction.e2Point) {
				this.currentShape = this.toolBar.getESelectedShape().getGShape().clone();
				this.transformer = new GDrawer(this.currentShape);
				this.transformer.initTransform(x, y, graphics2D);
				editShape = currentShape;
			} else {
				this.currentShape = this.toolBar.getESelectedShape().getGShape().clone();
				this.transformer = new GDrawer(this.currentShape);
				this.transformer.initTransform(x, y, graphics2D);
				editShape = currentShape;
			}
		}
	}
	public void keepTransforming(int x, int y) {
		Graphics2D graphics2D = (Graphics2D)this.getGraphics();
		this.transformer.keepTransform(x, y, graphics2D);
		this.repaint();
	}
	public void continueTransforming(int x, int y) {
		Graphics2D graphics2D = (Graphics2D)this.getGraphics();
		this.transformer.continueTransform(x, y, graphics2D);
	}
	public void finalizeTransforming(int x, int y) {
		Graphics2D graphics2D = (Graphics2D)this.getGraphics();
		this.clearSelection();
		this.transformer.finalizeTransform(x, y, graphics2D, this.shapes);
		transformer = new GNullTransformer(null);
		editCommand = EEditCommand.eDefault;
		this.currentShape = null;
		this.toolBar.resetESelectedShape();
		this.repaint();
	}
	public void changeShapeColor(int x, int y) { 
		onShape(x, y);
		if (currentShape == null) return;
		if (currentShape instanceof GLine) {
			Color selectedColor = dialogsFactory.createColorDialog();
			if (selectedColor == null) return;
			currentShape.setColorStatus(0);
			currentShape.setBorderColor(selectedColor);
		} else {
			int option = dialogsFactory.createBgBorderSelDialog();
			if (option == -1) return;
			Color selectedColor = dialogsFactory.createColorDialog();
			if (selectedColor == null) return;
			currentShape.setColorStatus(option);
			
			if (option == 0) currentShape.setBorderColor(selectedColor);
			else if (option == 1) currentShape.setBackgroundColor(selectedColor);
		}
		repaint();
		currentShape = null;
		editShape = null;
	}
	@Override
	public void groupCreate(List<GShape> shapeGroup) {
		selectedShapeGroup = shapeGroup;
	}
	private EAnchors onShapeAnchor(int x, int y) { 
		for (GShape gShape : shapes) {
			EAnchors eAnchors = gShape.onShape(x, y);
			if (eAnchors != null) return eAnchors;
		}
		return null;
	}
	private void changeCursors(int x, int y) {
		EAnchors eAnchor = this.onShapeAnchor(x, y);
		if (eAnchor == null) this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		else this.setCursor(eAnchor.getCursor());
	}
	private class MouseHandler implements MouseListener, MouseMotionListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 1) {
				mouse1Clicked(e);
			} else if (e.getClickCount() == 2) {
				mouse2Clicked(e);
			}
		}
		private void mouse1Clicked(MouseEvent e) {
			if (editCommand == EEditCommand.ePaste) {
				initTransforming(e.getX(), e.getY());
				keepTransforming(e.getX(), e.getY());
				finalizeTransforming(e.getX(), e.getY());
			} else if (toolBar.getESelectedShape().getEUserAction() == EUserAction.e2Point)
				return;
			else if (eDrawingState == EDrawingState.eIdle) {
				initTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eTransforming;
			} else if (eDrawingState == EDrawingState.eTransforming) {
				continueTransforming(e.getX(), e.getY());
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eTransforming) {
				keepTransforming(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eIdle) {
				changeCursors(e.getX(), e.getY());
			}
		}
		private void mouse2Clicked(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
				changeShapeColor(e.getX(), e.getY());
				finalizeTransforming(e.getX(), e.getY());
			}
			if (toolBar.getESelectedShape().getEUserAction() == EUserAction.e2Point)
				return;
			if (eDrawingState == EDrawingState.eTransforming) {
				finalizeTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		@Override
		public void mousePressed(MouseEvent e) {
			if (editCommand == EEditCommand.ePaste);
			else if (toolBar.getESelectedShape().getEUserAction() == EUserAction.eNPoint)
				return;
			else if (eDrawingState == EDrawingState.eIdle) {
				initTransforming(e.getX(), e.getY());
				eDrawingState = EDrawingState.eTransforming;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (editCommand == EEditCommand.ePaste);
			else if (eDrawingState == EDrawingState.eTransforming) {
				keepTransforming(e.getX(), e.getY());
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if (editCommand == EEditCommand.ePaste);
			else if (toolBar.getESelectedShape().getEUserAction() == EUserAction.eNPoint)
				return;
			else if (eDrawingState == EDrawingState.eTransforming) {
				finalizeTransforming(e.getX(), getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
	}
	@Override
	public void create() {
		if (editCommand == EEditCommand.ePaste)
			dialogsFactory.createMessageDialog(EMessageType.eNoSelectPoint);
		else reset();
	}

	@Override
	public void save() { 
		if (editCommand == EEditCommand.ePaste) {
			dialogsFactory.createMessageDialog(EMessageType.eNoSelectPoint);
			return;
		}
		File file = dialogsFactory.createFileSaveDialog();
		if (file == null) return;
		clearSelection();
		repaint();
		try(FileOutputStream fos = new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(shapes);
			dialogsFactory.createMessageDialog(EMessageType.eSaveSucceed);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void load() { 
		if (editCommand == EEditCommand.ePaste) {
			dialogsFactory.createMessageDialog(EMessageType.eNoSelectPoint);
			return;
		}
		File file = dialogsFactory.createFileOpenDialog();
		if (file == null) return;
		reset();
		try(FileInputStream fis = new FileInputStream(file);
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			Graphics2D graphics = (Graphics2D)getGraphics();
			shapes = (Vector<GShape>)ois.readObject();
			for (GShape shape : shapes) shape.draw(graphics);
			dialogsFactory.createMessageDialog(EMessageType.eOpenSucceed);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void reset() {
		shapes.removeAllElements();
		currentShape = null;
		repaint();
	}
	@Override
	public void cut() { 
		if (shapes.size() == 0) {
			dialogsFactory.createMessageDialog(EMessageType.eUnDrawed);
		} else {
			if (editShape == null) {
				editResult = false;
				dialogsFactory.createMessageDialog(EMessageType.eMissingSelect);
				return;
			}
			
			propManager.setEditGShape(editShape);
			shapes.remove(editShape);
			dialogsFactory.createMessageDialog(EMessageType.eCutShape);
			editResult = true;
			repaint();
		}
	}
	@Override
	public void copy() { 
		if (shapes.size() == 0) {
			dialogsFactory.createMessageDialog(EMessageType.eUnDrawed);
		} else {
			if (editShape == null) {
				editResult = false;
				dialogsFactory.createMessageDialog(EMessageType.eMissingSelect);
				return;
			}
			propManager.setEditGShape(editShape);
			dialogsFactory.createMessageDialog(EMessageType.eCopyShape);
			editResult = true;
		}
	}
	@Override
	public void paste() {
		if (editResult) {
			editCommand = EEditCommand.ePaste;
			dialogsFactory.createEditDialog("ePaste");
		} else dialogsFactory.createMessageDialog(EMessageType.eNoPasteShape);
	}
	@Override
	public void allSelect() {
		List<GShape> shapeGroup = new ArrayList<GShape>();
		for (GShape shape : shapes) {
			shape.setSelected(true);
			shapeGroup.add(shape);
		}
		groupCreate(shapeGroup);
		repaint();
	}
	@Override
	public boolean isEditState() {
		if (editCommand == EEditCommand.ePaste)
			dialogsFactory.createMessageDialog(EMessageType.eNoSelectPoint);
		return editCommand == EEditCommand.ePaste;
	}
}