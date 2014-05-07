package blind;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PC {
	private String Folder;
	private int x;
	private int y;
	
	PC(String i,int width, int height){
		Folder = System.getProperty("user.home")+"\\Blind\\"+i+"\\";
		if(!(new File(Folder).exists()))
		if(!(new File(Folder).mkdir()))throw new Error("Faild to make a new folder.");
		x = width;
		y = height;
	}

	public void saveIntra(int[] img, int numb){
		String number = Integer.toString(numb);
		while(number.length()<4)number="0"+number;
		BufferedImage Image = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
		Image.setRGB(0, 0, x , y, img, 0, x);
		try {
			ImageIO.write(Image, "JPEG", new File(Folder+number+"I"+".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void saveInter(int[] img, int numb){
		String number = Integer.toString(numb);
		while(number.length()<4)number="0"+number;
		BufferedImage Image = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
		Image.setRGB(0, 0, x , y, img, 0, x);
		try {
			ImageIO.write(Image, "JPEG", new File(Folder+number+"P"+".jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void saveVector(int[] vect,int[] thresh, int numb){
		String number = Integer.toString(numb);
		while(number.length()<4)number="0"+number;
		try {
			FileOutputStream out = new FileOutputStream(new File(Folder+number+".dat"));
			for(int i = 0;i<vect.length;i++){
				byte[] o = new byte[8];
				o[0]=(byte) ((vect[i]&0xFF000000)>>24);
				o[1]=(byte) ((vect[i]&0x00FF0000)>>16);
				o[2]=(byte) ((vect[i]&0x0000FF00)>>8);
				o[3]=(byte) ((vect[i]&0x000000FF));
				o[4]=(byte) ((thresh[i]&0xFF000000)>>24);
				o[5]=(byte) ((thresh[i]&0x00FF0000)>>16);
				o[6]=(byte) ((thresh[i]&0x0000FF00)>>8);
				o[7]=(byte) ((thresh[i]&0x000000FF));
				out.write(o, 0, 8);
			}
			out.close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
