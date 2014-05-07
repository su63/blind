package blind;

import java.io.File;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.FileChannelWrapper;

import java.awt.image.BufferedImage;

public class lod {
	private FrameGrab a;
	private int x;
	private int y;
	
	lod(File f){
		try {
			FileChannel FC = FileChannel.open(f.toPath());
			FileChannelWrapper FCW = new FileChannelWrapper(FC);
			a = new FrameGrab(FCW);
			BufferedImage i = a.getFrame();
			x = i.getWidth();
			y = i.getHeight();
			a.seekToFramePrecise(0);
		} catch (IOException | JCodecException e) {
			e.printStackTrace();
		}
	}
	
	public int getWidth(){
		return x;
	}
	public int getHeight(){
		return y;
	}
	
	public void setFrame(int framNum){
		try {
			a.seekToFramePrecise(framNum);
		} catch (IOException | JCodecException e) {
			e.printStackTrace();
		}
	}
	
	public int[] getFrame(){
		try {
			BufferedImage im = a.getFrame();
			int[] s = im.getRGB(0, 0, x, y, null, 0, x);
			return s;
		} catch (IOException e) {
			return null;
		}
	}
}
