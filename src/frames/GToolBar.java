package frames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import main.GConstants.EShape;
import observer.GEditChecking;

public class GToolBar extends JToolBar { 
	private static final long serialVersionUID = 1L;

	private ButtonGroup buttonGroup;
	private EShape eSelectedShape;
	public EShape getESelectedShape() {
		return eSelectedShape;
	}
	public void resetESelectedShape() {
		buttonGroup.clearSelection();
		this.eSelectedShape = EShape.eSelect;
	}
	public GToolBar() {
		super();
		ActionHandler actionHandler = new ActionHandler();
		buttonGroup = new ButtonGroup();
		for(EShape eShape : EShape.values()) {
			if (eShape != EShape.eSelect) {
				JToggleButton buttonShape = new JToggleButton(eShape.getIcon()); 
				this.add(buttonShape);
				buttonShape.setToolTipText(eShape.getName());
				buttonShape.setActionCommand(eShape.toString());
				buttonShape.addActionListener(actionHandler);
				buttonGroup.add(buttonShape);
			}
		}
		resetESelectedShape();
	}
	class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (editChecking.isEditState()) {
				resetESelectedShape();
				return; 
			}
			eSelectedShape = EShape.valueOf(event.getActionCommand());
		}
	}
	private GEditChecking editChecking;
	public void setEditChecking(GEditChecking editChecking) {
		this.editChecking = editChecking;
	}
}
