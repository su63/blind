package blind;
import com.amd.aparapi.Kernel;
public class MVD extends Kernel {
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
	private int[] min;
	
	private int slice;
	
	MVD(int width, int height){
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
		
		slice = thr;
		min = new int [thr];
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
		//res = new int[thr];
		execute(slice,24);
	}

	@Override
	public void run() {
		int bid = this.getGlobalId();
		if (bid>thr)return;
		int blockx = bid % bx;
		int blocky = bid / bx;
		int pixP = (bx *64* blocky)+(8*blockx);
		int pass = this.getPassId();
		for(int i = pass*16; i < pass*16 + 16;i++)
		{
			if(i == 0){min[bid] = 500;res[bid] = 0;}
			int mot = pixP + m[i];
			if(mot<0 || mot > r - (64 * bx))return;
			int sum = 0;
			for(int inblock = 0;inblock < 64;inblock++){
				sum = max(abs(img2[pixP+b[inblock]]&0xFF - img1[mot+b[inblock]]&0xFF),sum);
				//sum += abs(img2[pixP+b[inblock]]&0xFF00 - img1[mot+b[inblock]]&0xFF00);
				//sum += abs(img2[pixP+b[inblock]]&0xFF0000 - img1[mot+b[inblock]]&0xFF0000);
			}
			if(sum < min[bid]){
				min[bid] = sum;
				res[bid] = i;
			}
		}
	}
}
