package blind;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DivTestGHSZ {

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
		PC cach = new PC("GHSZ",load.getWidth(),load.getHeight());
		MVF Vec = new MVF(load.getWidth(),load.getHeight());
		MA map = new MA(load.getWidth(),load.getHeight());
		load.setFrame(50);
		a1 = load.getFrame();
		int[] ref = a1.clone();
		for(int i = 0;i < 100;i++){
			a2 = a1.clone();
			a1 = load.getFrame();
			//cach.saveIntra(a1, i);
			Vec.setImage(a1, a2);
			Vec.exacuteSlice();
			int[] vectRez = Vec.getResults();
			ref = map.apply(a2, vectRez);
			ref = DF.diff(a1, ref);
			cach.saveInter(ref, i);
			//cach.saveVector(vectRez, Vec.getThresh(), i);
		}
		
	}

}
