package com.fh.util.ppt2img;

import java.io.FileInputStream;


public class TestConvertPPTFileToImage {
	public static void main(String[] args) {
		/**
		 * 需要转换的原始PPT文件的物理路径和文件名，并且必须为*.ppt格式；如果为*.pptx格式，则需要XSSF
		 */
		String orignalPPTFileName ="D:/test/ppt2img/ceshi.ppt";
		String targetPPTFileName ="D:/test/ppt2img/";
		/**
		 *  图像文件的格式字符串为“jpg”、“jpeg”、"bmp"、"png"、 "gif"、"tiff"等
		 */
		String imageFormatNameString ="jpeg";
		ConvertPPTFileToImage oneConvertPPTFileToImage =new ConvertPPTFileToImage();
		/**
		 * 检查需要转换的原始PPT文件是否真正为*.ppt或者*.pptx的格式，如果文件格式满足要求，则进行转换，否则将不进行转换。
		 */		
		switch(oneConvertPPTFileToImage.checkOrignalFileFormat(orignalPPTFileName)){
			case 0:
				System.out.println("待转换的PPT文件不存在或者不是PPT和PPTX格式的文件！");
				break;
			case 1:
				/**
				 * 如果为PPT格式的文件，则最终进行转换。
				 */
				oneConvertPPTFileToImage.convertPPTtoImage(orignalPPTFileName, targetPPTFileName, imageFormatNameString);
				break;
			case 2:
				/**
				 * 如果为PPTX格式的文件，则最终进行转换。
				 */
				oneConvertPPTFileToImage.convertPPTXtoImage(orignalPPTFileName, targetPPTFileName, imageFormatNameString);
				break;
		}	
	}
	
	

	
}