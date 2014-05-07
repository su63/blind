package blind;

public class MA {
	
	public int[] vs;
	public int[] vl;
	public int[] bt;
	public int x;
	public int y;
	
	MA(int width,int height){
		mvb er = new mvb(width);
		vs = er.m;
		vl = er.l;
		bt = er.b;
		x = width/8;
		y = height/8;
	}
	
	public int[] apply(int[] image,int[] vector){
		int[] out = new int[image.length];
		int xa = 0;
		int ya = 0;
		int b = 0;
		while(ya < y){
			int block = (64 * x * ya) + (xa * 8);
			try{
			for(int i = 0;i < 64;i++){
				out[block+bt[i]] = 
						image[block+vs[vector[b]&0xFFFF]+ vl[(vector[b]&0xFF0000)>>16] + bt[i]];
			}}catch(ArrayIndexOutOfBoundsException e){}
			b++;
			xa++;
			if(xa == x){
				xa = 0;
				ya++;
			}
		}
		
		return out;
	}

}