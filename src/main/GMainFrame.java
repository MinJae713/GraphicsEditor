package main;
import java.awt.BorderLayout;

import javax.swing.JFrame;

import frames.GDrawingPanel;
import frames.GMenuBar;
import frames.GToolBar;

public class GMainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private GMenuBar menuBar;
	private GToolBar toolBar;
	private GDrawingPanel drawingPanel;	
	
	public GMainFrame() {
		super();
		this.setLocation(GConstants.CMainFrame.x, GConstants.CMainFrame.y);
		this.setSize(GConstants.CMainFrame.w, GConstants.CMainFrame.h);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.menuBar = new GMenuBar();
		this.setJMenuBar(menuBar);
		
		BorderLayout flowLayout = new BorderLayout();
		this.setLayout(flowLayout);
		
		this.toolBar = new GToolBar();
		this.add(this.toolBar, BorderLayout.NORTH);
		
		this.drawingPanel = new GDrawingPanel();
		this.add(drawingPanel, BorderLayout.CENTER);

		this.drawingPanel.setToolBar(toolBar);
		
		this.menuBar.setFileIO(drawingPanel); 
		this.menuBar.setEdit(drawingPanel); 
		this.toolBar.setEditChecking(drawingPanel); 
	}
}