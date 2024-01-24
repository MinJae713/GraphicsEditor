package dialogs;

import java.awt.Color;
import java.awt.Component;
import java.io.File;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GDialogsFactory { 
	Component parentComponent;
	public enum EMessageType {
		eUnDrawed("�׸��ǿ� �׸� ������ �����ϴ�", "Error Message", JOptionPane.ERROR_MESSAGE),
		eMissingSelect("�׸� ������ ������ �ּ���", "Error Message", JOptionPane.ERROR_MESSAGE),
		eNoSelectPoint("�ٿ��ֱ��� ��ġ�� ������ �ּ���", "Error Message", JOptionPane.ERROR_MESSAGE),
		eNoPasteShape("�ٿ��ֱ��� ������ �����ϴ�", "Error Message", JOptionPane.ERROR_MESSAGE),
		eCutShape("���� �ڸ��⿡ �����߽��ϴ�", "Success Message", JOptionPane.INFORMATION_MESSAGE),
		eCopyShape("���� ���翡 �����߽��ϴ�", "Success Message", JOptionPane.INFORMATION_MESSAGE),
		ePasteShape("���� �ٿ��ֱ⿡ �����߽��ϴ�", "Success Message", JOptionPane.INFORMATION_MESSAGE),
		eCutEnd("���� �ڸ��Ⱑ ����˴ϴ�", "Ending Message", JOptionPane.INFORMATION_MESSAGE),
		eCopyEnd("���� ���簡 ����˴ϴ�", "Ending Message", JOptionPane.INFORMATION_MESSAGE),
		ePasteEnd("���� �ٿ��ֱⰡ ����˴ϴ�", "Ending Message", JOptionPane.INFORMATION_MESSAGE),
		eSaveEnd("������ ��ҵ˴ϴ�.", "Ending Message", JOptionPane.INFORMATION_MESSAGE),
		eOpenEnd("�ҷ����Ⱑ ��ҵ˴ϴ�.", "Ending Message", JOptionPane.INFORMATION_MESSAGE),
		eSaveSucceed("���� �����߽��ϴ�!", "Success Message", JOptionPane.INFORMATION_MESSAGE),
		eOpenSucceed("�ҷ����� �����߽��ϴ�!", "Success Message", JOptionPane.INFORMATION_MESSAGE);
		private String message;
		private String title;
		private int messageType;
		private EMessageType(String message, String title, int messageType) {
			this.message = message;
			this.title = title;
			this.messageType = messageType;
		}
		public String getMessage() {
			return message;
		}
		public String getTitle() {
			return title;
		}
		public int getMessageType() {
			return messageType;
		}
	}
	private JFileChooser jFileChooser; 
	public GDialogsFactory(Component parentComponent) {
		this.parentComponent = parentComponent;
		jFileChooser = new JFileChooser(); 
		jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("�ؽ�Ʈ ����(*.txt)", "txt")); // 0610 �߰�
	}

	public int createBgBorderSelDialog() {
		return JOptionPane.showOptionDialog(
				parentComponent, 
				"��� ������ �����ϰڽ��ϱ�?", 
				"���� ����", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, 
				null, 
				new String[] {"�׵θ�", "���"}, 
				"�׵θ�");
	}
	@SuppressWarnings("static-access")
	public Color createColorDialog() {
		JColorChooser chooser = new JColorChooser();
		return chooser.showDialog(parentComponent, "���� ����", null);
	}
	public String createInputDialog() { 
		return JOptionPane.showInputDialog(
				parentComponent, 
				"�߰��� ���ڿ��� �Է��ϼ���", 
				"Text Input", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	public void createEditDialog(String command) { 
		String message = null;
		if (command == "eCut") message = "�ڸ� ������ ������ �ּ���";
		else if (command == "eCopy") message = "������ ������ ������ �ּ���";
		else if (command == "ePaste") message = "�ٿ��ֱ��� ��ġ�� ������ �ּ���";
		JOptionPane.showMessageDialog(parentComponent, message, "Edit Shape", JOptionPane.INFORMATION_MESSAGE);
	}
	public void createMessageDialog(EMessageType messageType) { 
		JOptionPane.showMessageDialog(
				parentComponent, messageType.getMessage(), 
				messageType.getTitle(), messageType.getMessageType());
	}
	public File createFileOpenDialog() { 
		int option = jFileChooser.showOpenDialog(parentComponent);
		if (option == JFileChooser.APPROVE_OPTION) {
			File file = jFileChooser.getSelectedFile();
			return file;
		} else if (option == JFileChooser.CANCEL_OPTION) {
			createMessageDialog(EMessageType.eOpenEnd);
		}
		return null;
	}
	public File createFileSaveDialog() { 
		int option = jFileChooser.showSaveDialog(parentComponent);
		if (option == JFileChooser.APPROVE_OPTION) {
			File file = jFileChooser.getSelectedFile();
			return file;
		} else if (option == JFileChooser.CANCEL_OPTION) {
			createMessageDialog(EMessageType.eSaveEnd);
		}
		return null;
	}
}
