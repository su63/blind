package blind;
import com.amd.aparapi.Kernel;
public class MVF extends Kernel {
	private int x;
	private int y;
	private int r;
	private int bx;
	private int by;
	private int thr;
	
	private int[] m;
	private int[] l;
	private int[] b;

	private int[] img1;
	private int[] img2;
	private int[] res;
	private int[] min;
	
	private int slice;
	
	private boolean firstStage;
	
	MVF(int width, int height){
		if(width % 8 != 0 || height % 8 != 0) throw new Error("Dementions not mulitbles of 8");
		mvb a = new mvb(width);
		
		x = width;
		y = height;
		r = x * y;
		
		bx = x / 8;
		by = y / 8;
		
		m = a.m;
		b = a.b;
		l = a.l;
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
	public int[] getThresh(){;
		return min;
	}
	public void exacuteSlice(){
		firstStage = true;
		execute(slice,8);
		System.out.println(getExecutionTime()+" ms 1");
		firstStage = false;
		execute(slice,64);
		System.out.println(getExecutionTime()+" ms 2");
	}

	@Override
	public void run() {
		int bid = this.getGlobalId();
		if (bid>thr)return;
		if(this.getPassId() != 0)
			if(min[bid] < 1)return;
		int blockx = bid % bx;
		int blocky = bid / bx;
		int pixP = (bx *64* blocky)+(8*blockx);
		int pass = this.getPassId();
		if (firstStage == true){
			for(int i = pass*16; i < pass*16 + 16;i++)
			{
				if(i == 0){min[bid] = 10000;res[bid] = 0;}
				if(min[bid] < 2048)return;
				int mot = pixP + m[i];
				if(mot<0 || mot > r - (64 * bx))return;
				int sum = 0;
				for(int inblock = 0;inblock < 64;inblock++){
					sum = max(abs(img1[pixP+b[inblock]]&0xFF - img2[mot+b[inblock]]&0xFF),sum);
					//sum += abs(img2[pixP+b[inblock]]&0xFF00 - img1[mot+b[inblock]]&0xFF00)>>8;
					//sum += abs(img2[pixP+b[inblock]]&0xFF0000 - img1[mot+b[inblock]]&0xFF0000)>>16;
					if(sum>min[bid])inblock = 63;
				}
				if(sum < min[bid]){
					min[bid] = sum;
					res[bid] = i;
				}
			}
		}else{
			int mot = pixP + m[res[bid]&0xFFFF] + l[pass];
			if(mot<0 || mot > r - (64 * bx))return;
			int sum = 0;
			for(int inblock = 0;inblock < 64;inblock++){
				sum += abs(img1[pixP+b[inblock]]&0xFF - img2[mot+b[inblock]]&0xFF);
				sum += abs(img2[pixP+b[inblock]]&0xFF00 - img1[mot+b[inblock]]&0xFF00)>>8;
				sum += abs(img2[pixP+b[inblock]]&0xFF0000 - img1[mot+b[inblock]]&0xFF0000)>>16;
				if(sum>min[bid])return;
			}
			if(sum < min[bid]){
				min[bid] = sum;
				res[bid] = (pass<<16) + (res[bid]&0xFFFF);
			}
		}
	}
}
