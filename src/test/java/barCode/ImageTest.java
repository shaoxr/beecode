package barCode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author shaoxr:
 * @version 2011-6-11 下午01:08:07
 * 
 */
public class ImageTest {

	/**
	 * @param args
	 */
	private static final int WHITE = 0xFFFFFFFF;
	public static void main(String[] args) {
		try {
			BufferedImage imd=ImageIO.read(new File("c:/ban.png"));
			BufferedImage imd2=ImageIO.read(new File("c:/china.gif"));
	            File   fileOne   =   new   File( "c:/ban.png "); 
	            BufferedImage   ImageOne   =   ImageIO.read(fileOne); 
	            int   width   =   ImageOne.getWidth();//图片宽度 
	            int   height   =   ImageOne.getHeight();//图片高度 
	            //从图片中读取RGB 
	            int[]   ImageArrayOne   =   new   int[width*height]; 
	            ImageArrayOne   =   ImageOne.getRGB(0,0,width,height,ImageArrayOne,0,width); 
	            //对第二张图片做相同的处理 
	            File   fileTwo   =   new   File( "c:/china.gif "); 
	            BufferedImage   ImageTwo   =   ImageIO.read(fileTwo);	     
	            int[]   ImageArrayTwo   =   new   int[200*200]; 
	            ImageArrayTwo   =   ImageTwo.getRGB(0,0,200,200,ImageArrayTwo,0,width); 
	            //生成新图片 
	            BufferedImage   ImageNew   =   new   BufferedImage(200,200,BufferedImage.TYPE_INT_RGB); 
	            ImageNew.setRGB(0,0,200,200,ImageArrayTwo,0,200);//设置左半部分的RGB 
	            ImageNew.setRGB(50,50,99,94,ImageArrayOne,0,99);//设置右半部分的RGB 
	            File   outFile   =   new   File( "c:\\out.png "); 
	            ImageIO.write(ImageNew,   "png ",   outFile);//写图片
            
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
