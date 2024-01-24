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
		eUnDrawed("그림판에 그린 도형이 없습니다", "Error Message", JOptionPane.ERROR_MESSAGE),
		eMissingSelect("그린 도형을 선택해 주세요", "Error Message", JOptionPane.ERROR_MESSAGE),
		eNoSelectPoint("붙여넣기할 위치를 선택해 주세요", "Error Message", JOptionPane.ERROR_MESSAGE),
		eNoPasteShape("붙여넣기할 도형이 없습니다", "Error Message", JOptionPane.ERROR_MESSAGE),
		eCutShape("도형 자르기에 성공했습니다", "Success Message", JOptionPane.INFORMATION_MESSAGE),
		eCopyShape("도형 복사에 성공했습니다", "Success Message", JOptionPane.INFORMATION_MESSAGE),
		ePasteShape("도형 붙여넣기에 성공했습니다", "Success Message", JOptionPane.INFORMATION_MESSAGE),
		eCutEnd("도형 자르기가 종료됩니다", "Ending Message", JOptionPane.INFORMATION_MESSAGE),
		eCopyEnd("도형 복사가 종료됩니다", "Ending Message", JOptionPane.INFORMATION_MESSAGE),
		ePasteEnd("도형 붙여넣기가 종료됩니다", "Ending Message", JOptionPane.INFORMATION_MESSAGE),
		eSaveEnd("저장이 취소됩니다.", "Ending Message", JOptionPane.INFORMATION_MESSAGE),
		eOpenEnd("불러오기가 취소됩니다.", "Ending Message", JOptionPane.INFORMATION_MESSAGE),
		eSaveSucceed("저장 성공했습니다!", "Success Message", JOptionPane.INFORMATION_MESSAGE),
		eOpenSucceed("불러오기 성공했습니다!", "Success Message", JOptionPane.INFORMATION_MESSAGE);
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
		jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("텍스트 파일(*.txt)", "txt")); // 0610 추가
	}

	public int createBgBorderSelDialog() {
		return JOptionPane.showOptionDialog(
				parentComponent, 
				"어느 색상을 변경하겠습니까?", 
				"색상 변경", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, 
				null, 
				new String[] {"테두리", "배경"}, 
				"테두리");
	}
	@SuppressWarnings("static-access")
	public Color createColorDialog() {
		JColorChooser chooser = new JColorChooser();
		return chooser.showDialog(parentComponent, "색상 변경", null);
	}
	public String createInputDialog() { 
		return JOptionPane.showInputDialog(
				parentComponent, 
				"추가할 문자열을 입력하세요", 
				"Text Input", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	public void createEditDialog(String command) { 
		String message = null;
		if (command == "eCut") message = "자를 도형을 선택해 주세요";
		else if (command == "eCopy") message = "복사할 도형을 선택해 주세요";
		else if (command == "ePaste") message = "붙여넣기할 위치를 선택해 주세요";
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
