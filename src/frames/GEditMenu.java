package frames;

import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class GEditMenu extends JMenu { 
	private static final long serialVersionUID = 1L;
	public enum EEditMenu { 
		eAllSelect("��ü����", KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK)),
		eCut("�߶󳻱�", KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK)), 
		eCopy("����", KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK)), 
		ePaste("�ٿ��ֱ�", KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_DOWN_MASK));
		private String name;
		private KeyStroke keyStroke;
		private EEditMenu(String name, KeyStroke keyStroke) {
			this.name = name;
			this.keyStroke = keyStroke;
		}
		public String getName() {
			return name;
		}
		public KeyStroke getKeyStroke() {
			return keyStroke;
		}
	}
	public GEditMenu(String title, ActionListener actionListener) {
		super(title);
		for(EEditMenu menus : EEditMenu.values()) {
			JMenuItem item = new JMenuItem(menus.getName());
			item.setActionCommand(menus.toString());
			item.addActionListener(actionListener);
			item.setAccelerator(menus.getKeyStroke());
			this.add(item);
		}
	}
}
