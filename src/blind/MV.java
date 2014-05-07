package blind;
import com.amd.aparapi.Kernel;
public class MV extends Kernel {
	private int x;
	private int y;
	private int r;
	private int bx;
	private int by;
	private int thr;

	private int[] m;
	private int[] b;

	private int[] img1;
	private int[] img2;
	private int[] res;
	
	private int slice;
	
	MV(int width, int height){
		if(width % 8 != 0 || height % 8 != 0) throw new Error("Dementions not mulitbles of 8");
		mvb a = new mvb(width);
		
		x = width;
		y = height;
		r = x * y;
		
		bx = x / 8;
		by = y / 8;
		
		m = a.m;
		b = a.b;
		thr = bx*by;
		res = new int[thr];
		
		slice = (thr/128)+1;
	}
	public int  getBlock(){
		return bx *by;
	}
	public void setImage(int[] image1, int[] image2){
		img1 = image1;
		img2 = image2;
	}
	public int[] getResults(){
		return res;
	}
	public void exacuteSlice(){
		execute(128,slice);
	}

	@Override
	public void run() {
		int bid = (this.getPassId()* 128) + this.getGlobalId();
		if (bid>thr)return;
		int blockx = bid % bx;
		int blocky = bid / bx;
		int pixP = (bx *64* blocky)+(8*blockx);
		int min = 1000;
		for(int i = 0; i < 1024;i++){
			int mot = pixP + m[i];
			if(mot<0 || mot > r - (64 * bx))continue;
			int sum = 0;
			for(int inblock = 0;inblock < 64;inblock++){
				sum += abs(img1[pixP+b[inblock]]&0xFF - img2[mot+b[inblock]]&0xFF);
				//sum += abs(img2[pixP+b[inblock]]&0xFF00 - img1[mot+b[inblock]]&0xFF00);
				//sum += abs(img2[pixP+b[inblock]]&0xFF0000 - img1[mot+b[inblock]]&0xFF0000);
			}
			if(sum < min){
				min = sum;
				res[bid] = i;
			}
		}
	}

}
