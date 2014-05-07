package blind;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DivTestGHSD {

	public static void main(String[] args) {
		try {UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
		e.printStackTrace();}

		JFileChooser a = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("MPEG-4", "mp4");
		a.setFileFilter(filter);
		if(a.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)return;
		
		lod load = new lod(a.getSelectedFile());
		PC cach = new PC("GHSD",load.getWidth(),load.getHeight());
		for(int i = 0;i < 100;i++){
			int[] image = load.getFrame();
			cach.saveIntra(image, i);
		}
		
	}

}
