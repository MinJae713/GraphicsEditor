package frames;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;

import frames.GEditMenu.EEditMenu;
import frames.GFileMenu.EFileMenu;
import observer.GEdit;
import observer.GFileIO;

public class GMenuBar extends JMenuBar { 
	private static final long serialVersionUID = 1L;

	private GFileMenu fileMenu;
	private GEditMenu editMenu;
	public GMenuBar() {
		fileMenu = new GFileMenu("File", new FileMenuHandler());
		editMenu = new GEditMenu("Edit", new EditMenuHandler()); 
		this.add(fileMenu);
		this.add(editMenu);
	}
	private EFileMenu currentFileMenu;
	private GFileIO fileIO;
	public class FileMenuHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			currentFileMenu = EFileMenu.valueOf(e.getActionCommand());
			if(currentFileMenu == EFileMenu.eNew) fileIO.create();
			else if(currentFileMenu == EFileMenu.eSave) fileIO.save();
			else if(currentFileMenu == EFileMenu.eLoad) fileIO.load();
		}
	}
	public void setFileIO(GFileIO fileIO) {
		this.fileIO = fileIO;
	}
	private EEditMenu currentEditMenu;
	private GEdit edit;
	public class EditMenuHandler implements ActionListener { 
		@Override
		public void actionPerformed(ActionEvent e) {
			currentEditMenu = EEditMenu.valueOf(e.getActionCommand());
			if(currentEditMenu == EEditMenu.eCut) edit.cut();
			else if(currentEditMenu == EEditMenu.eCopy) edit.copy();
			else if(currentEditMenu == EEditMenu.ePaste) edit.paste();
			else if(currentEditMenu == EEditMenu.eAllSelect) edit.allSelect();
		}
	}
	public void setEdit(GEdit edit) {
		this.edit = edit;
	}
}