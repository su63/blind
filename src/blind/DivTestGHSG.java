package blind;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DivTestGHSG {

	public static void main(String[] args) {
		try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
		e.printStackTrace();}

		JFileChooser a = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("MPEG-4", "mp4");
		a.setFileFilter(filter);
		if(a.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)return;
		lod load = new lod(a.getSelectedFile());
		
		int[] a1;
		int[] a2;
		PC cach = new PC("GHSF",load.getWidth(),load.getHeight());
		a1 = new int[load.getWidth()*load.getHeight()];
		load.setFrame(50);
		for(int i = 0;i < 100;i++){
			a2 = a1.clone();
			a1 = load.getFrame();
			cach.saveInter(DF.diff(a1, a2), i);
		}
		
	}

}
