package com.zmj.img2char;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

public class Img2Char {
	//private static final String baseStr=" .`_-'\";=L~!cxIr*ZCg{9FE[R/Ydnpq#V$OMHUN";
	//private static final String baseStr="NUHMO$V#qpndY/R[EF9{gCZ*rIxc!~L=;\"'-_`. ";
	private static final String baseStr="NUH*rI~-_. ";
	private static final char[] baseChars=baseStr.toCharArray();
	
	public static int getGray(Color pixel) {
		return (pixel.getRed()*30+pixel.getGreen()*60+pixel.getBlue()*10)/100;
	}
	public static char color2Char(Color pixel) {
		int index=(int)(getGray(pixel)*1.0/(256*1.0/baseChars.length));
		return baseChars[index];
	}
	
	public static byte[] compress(File file) throws IOException {
		BufferedImage bi=ImageIO.read(file);
		int width=bi.getWidth();
		int height=bi.getHeight();
		
		if(width>120) {
			double rate=height*1.0/width;
			width=120;
			height=(int)(width*rate);
		}
		
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		Thumbnails.of(file).scale(width*1.0/bi.getWidth(),height*1.0/bi.getHeight()/2).toOutputStream(baos);
		
		byte[] imgByteArr=baos.toByteArray();
		baos.close();
		
		return imgByteArr;
	}
	
	
	public static void img2Char(byte[] imgByteArr,File targetFile) throws IOException {
		InputStream is=new ByteArrayInputStream(imgByteArr);		
		BufferedImage bi=ImageIO.read(is);
		is.close();
		
		int width=bi.getWidth();System.out.println(width);
		int height=bi.getHeight();System.out.println(height);
		
		OutputStream os=new FileOutputStream(targetFile);
		
		
		for(int y=0;y<height;y++) {
			for(int x=0;x<width;x++) {
				Color pixel=new Color(bi.getRGB(x, y));
				
				char charPix=color2Char(pixel);
				
				os.write(charPix);
				
			}
			os.write("\r\n".getBytes());
		}
		
		os.close();
		
	}
	
	
	public static void main(String[] args) throws IOException {
		File file=new File("C:\\Users\\ZMJ\\Desktop\\b.jpg");
		
		File targetFile=new File("C:\\Users\\ZMJ\\Desktop\\a.txt");
		img2Char(compress(file), targetFile);
	}

}
