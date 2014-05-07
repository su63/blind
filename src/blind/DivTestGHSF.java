package blind;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DivTestGHSF {

	public static void main(String[] args) {
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
		e.printStackTrace();}

		JFileChooser a = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("MPEG-4", "mp4");
		a.setFileFilter(filter);
		if(a.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)return;
		lod load = new lod(a.getSelectedFile());
		int[] a1;
		int[] a2;
		PC cach = new PC("GHSG",load.getWidth(),load.getHeight());
		MVD Vec = new MVD(load.getWidth(),load.getHeight());
		MA map = new MA(load.getWidth(),load.getHeight());
		//Vec.setExecutionMode();
		load.setFrame(50);
		a1 = load.getFrame();
		int[] ref = a1.clone();
		for(int i = 0;i < 100;i++){
			a2 = a1.clone();
			a1 = load.getFrame();
			Vec.setImage(a1, a2);
			Vec.exacuteSlice();
			System.out.println(Vec.getExecutionTime() + " ms");
			int[] vectRez = Vec.getResults();
			ref = map.apply(ref, vectRez);
			cach.saveInter(ref, i);
		}
		
	}

}
