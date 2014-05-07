package blind;

public class DF {
	
	public static int[] diff(int[] img1, int[] img2){
		int[] out = new int[img1.length];
		for(int i = 0;i < out.length; i++){
			out[i] = Math.min(Math.max((img1[i]&0xFF - img2[i]&0xFF + 0x80), 0),0xFF);
			out[i] += Math.min(Math.max((img1[i]&0xFF00 - img2[i]&0xFF00 + 0x8000), 0),0xFF00);
			out[i] += Math.min(Math.max((img1[i]&0xFF0000 - img2[i]&0xFF0000 + 0x800000), 0),0xFF0000);
		}
		return out;
	}
	
	public static int[] cort(int[] img1, int[] img2){
		int[] out = new int[img1.length];
			for(int i = 0; i < out.length;i++){
				out[i] = Math.min(Math.max((img1[i]&0xFF + img2[i]&0xFF - 0x80), 0),0xFF);
				out[i] += Math.min(Math.max((img1[i]&0xFF00 + img2[i]&0xFF00 - 0x8000), 0),0xFF00);
				out[i] += Math.min(Math.max((img1[i]&0xFF0000 + img2[i]&0xFF0000 - 0x800000), 0),0xFF0000);
			}
		return out;
	}

}
