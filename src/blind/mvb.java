package blind;

public class mvb {
	
	public int[] m = new int[65536];
	public int[] l = new int[64];
	public int[] b = new int[64];
	
	mvb(int width){
		int inx = 0;
		int iny = 0;
		int is = 0;
		int mode = 0;
		int count = 0;
		int stair = 1;
				
		while(is < 65536){
			m[is] = (iny * width) + inx;
			
			if (mode == 0){
				inx+=2;
			}else if(mode == 1){
				iny+=2;
			}else if(mode == 2){
				inx-=2;
			}else if(mode == 3){
				iny-=2;
			}
			count++;
			if(count == stair){
				mode = (mode+1) % 4 ;
				count = 0;
				if(mode % 2 == 0){
					stair++;
				}
			}
			is++;
		}

		inx = 0;
		iny = 0;
		is = 0;
		mode = 0;
		count = 0;
		stair = 1;
				
		while(is < 64){
			l[is] = (iny * width) + inx;
			
			if (mode == 0){
				inx+=1;
			}else if(mode == 1){
				iny+=1;
			}else if(mode == 2){
				inx-=1;
			}else if(mode == 3){
				iny-=1;
			}
			count++;
			if(count == stair){
				mode = (mode+1) % 4 ;
				count = 0;
				if(mode % 2 == 0){
					stair++;
				}
			}
			is++;
		}
		
		int x = 0;
		int y = 0;
		for (int i = 0; i < 64;i++){
			b[i] = y * width + x;
			x++;
			if(x == 8){
				x = 0;
				y++;
			}
		}
		
	}

}
