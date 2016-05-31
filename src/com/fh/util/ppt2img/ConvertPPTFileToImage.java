package com.fh.util.ppt2img;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.RichTextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextCharacterProperties;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextFont;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;

import com.fh.util.GuidUtil;
import com.fh.util.PageData;
/**
 * 本类应用了Apache POI系统库，POI 是 Apache 下的 Jakata 项目的一个子项目，主要用于提供 Java 操作 Microsoft Office 办公套件如 Excel，Word，Powerpoint 等文件的 API。
 * @author 
 *
 */
public class ConvertPPTFileToImage {

	public ConvertPPTFileToImage() {		
	}
	/**
	 * convertPPTXtoImage方法完成将PPTX格式文件转换为指定格式的图片，本方法需要如下的第3方的系统库：
	 * xmlbeans-2.6.0.jar、poi-scratchpad-3.11-20141221.jar、 poi-ooxml-3.11-20141221.jar、poi-3.11-20141221.jar、poi-ooxml-schemas-3.11-20141221.jar
	 * @param orignalPPTFileName	代表需要转换的PPT图片的文件路径和文件名称，如"D:/软件项目程序/Demo/demo1.pptx"
	 * @param targetImageFileDir	代表转换后的各个图片文件的保存路径，如"D:/软件项目程序/Demo/pptImg/"
	 * @param imageFormatNameString 代表转换的图片格式的字符串， 如“jpg”、“jpeg”、"bmp"、"png"、 "gif"、"tiff"等
	 * @return 返回true表示转换成功， 返回false表示转换不成功
	 */
	public PageData convertPPTXtoImage(String orignalPPTFileName,String targetImageFileDir, String imageFormatNameString) {
		/**
		 * convertReturnResult代表转换的结果，返回 false则表示转换不成功
		 */
		//封装图片路径
		PageData pd = new PageData();
		StringBuffer sb = new StringBuffer();
		
		
		boolean convertReturnResult =false;
		pd.put("bol", convertReturnResult);
		FileInputStream orignalPPTFileInputStream =null;
		FileOutputStream orignalPPTFileOutputStream =null;
		/**
		 *  org.apache.poi.hslf, 这个包只支持 PowerPoint 2007 以前的 ppt 文档解析. 因为 Office 2007 的文件底层实现方案（OOXML）和以前的版本（OLE2）有根本的变化。
		 *  支持 office 2007 的包为: org.apache.poi.xslf 
		 */
		XMLSlideShow oneSlideShow =null;
		try{
			orignalPPTFileInputStream = new FileInputStream(orignalPPTFileName);
			oneSlideShow = new XMLSlideShow(orignalPPTFileInputStream);
			/**
			 * 获得PPT每页的尺寸大小（宽和高度）
			 */
			Dimension onePPTPageSize = oneSlideShow.getPageSize();
			/**
			 * 获得PPT文件中的所有的PPT页面，并转换为一张张的播放片
			 */
			XSLFSlide[] pptPageXSLFSlideArray = oneSlideShow.getSlides();
			/**
			 * 下面的XML配置文件定义转换后的图片内的文字字体，否则将会出现转换后的图片内的中文为乱码
			 */
			String xmlFontFormat1="<xml-fragment xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\">";
			String xmlFontFormat2=		"<a:rPr lang=\"zh-CN\" altLang=\"en-US\" dirty=\"0\" smtClean=\"0\"> ";
			String xmlFontFormat3=			"<a:latin typeface=\"+mj-ea\"/> ";
			String xmlFontFormat4=		"</a:rPr>";
			String xmlFontFormat5="</xml-fragment>";
			StringBuffer xmlFontFormatStringBuffer=new StringBuffer();
			xmlFontFormatStringBuffer.append(xmlFontFormat1);
			xmlFontFormatStringBuffer.append(xmlFontFormat2);
			xmlFontFormatStringBuffer.append(xmlFontFormat3);
			xmlFontFormatStringBuffer.append(xmlFontFormat4);
			xmlFontFormatStringBuffer.append(xmlFontFormat5);
			
			for (int pptPageXSLFSlideIndex = 0;  pptPageXSLFSlideIndex < pptPageXSLFSlideArray.length;  pptPageXSLFSlideIndex++) {
				/**
				 *设置字体为宋体，解决中文乱码问题 
				 */			
	            CTSlide oneCTSlide=pptPageXSLFSlideArray[pptPageXSLFSlideIndex].getXmlObject();
	            CTGroupShape oneCTGroupShape = oneCTSlide.getCSld().getSpTree();
	            CTShape[] oneCTShapeArray = oneCTGroupShape.getSpArray();
	            for (CTShape oneCTShape : oneCTShapeArray) {
	                CTTextBody oneCTTextBody = oneCTShape.getTxBody();
	                if (null == oneCTTextBody){
	                    continue;
	                }
	                CTTextParagraph[] oneCTTextParagraph = oneCTTextBody.getPArray();	                
	                CTTextFont oneCTTextFont =null;
					oneCTTextFont = CTTextFont.Factory.parse(xmlFontFormatStringBuffer.toString());
	                for (CTTextParagraph textParagraph : oneCTTextParagraph) {
	                    CTRegularTextRun[] oneCTRegularTextRunArray = textParagraph.getRArray();
	                    for (CTRegularTextRun oneCTRegularTextRun : oneCTRegularTextRunArray) {
	                        CTTextCharacterProperties oneCTTextCharacterProperties=oneCTRegularTextRun.getRPr();
	                        oneCTTextCharacterProperties.setLatin(oneCTTextFont);
	                    }
	                }
	            }				
				/**
				 * 创建BufferedImage对象，图象的尺寸为原来PPT的每页的尺寸
				 */
				BufferedImage oneBufferedImage = new BufferedImage(onePPTPageSize.width, onePPTPageSize.height, BufferedImage.TYPE_INT_RGB);	
				Graphics2D oneGraphics2D = oneBufferedImage.createGraphics();
				/**
				 * 将PPT文件中的每个页面中的相关内容画到转换后的图片中
				 */
				pptPageXSLFSlideArray[pptPageXSLFSlideIndex].draw(oneGraphics2D);			
				/**
				 * 设置图片的存放路径和图片的格式，注意生成的文件路径为绝对路径，最终获得各个图像文件所对应的输出流对象
				 */				
				String fileName = GuidUtil.getGuid();
				orignalPPTFileOutputStream = new FileOutputStream(targetImageFileDir+ fileName+ "."+imageFormatNameString);
				sb.append(fileName+ "."+imageFormatNameString).append(",");
				/**
				 * 图像文件的格式字符串为“jpg”、“jpeg”、"bmp"、"png"、 "gif"、"tiff"等，将转换后的各个图片文件保存到指定的目录中
				 */
				ImageIO.write(oneBufferedImage, imageFormatNameString, orignalPPTFileOutputStream);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlException e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(orignalPPTFileInputStream != null ){
					orignalPPTFileInputStream.close();
				}
			} 
			catch (IOException e) {				
				e.printStackTrace();
			}
			try {
				if(orignalPPTFileOutputStream != null ){
					orignalPPTFileOutputStream.close();
				}
			} 
			catch (IOException e) {			
				e.printStackTrace();
			}
			convertReturnResult =true;	
		}		
		
		pd.put("imgurl", sb.substring(0, sb.length()-1).toString());
		pd.put("bol", convertReturnResult);
		return pd;
	}
	/**
	 * convertPPTtoImage方法完成将PPT格式文件转换为指定格式的图片，本方法需要如下的第3方的系统库：
	 * xmlbeans-2.6.0.jar、poi-scratchpad-3.11-20141221.jar、 poi-ooxml-3.11-20141221.jar、poi-3.11-20141221.jar
	 * @param orignalPPTFileName	代表需要转换的PPT图片的文件路径和文件名称，如"D:/软件项目程序/Demo/demo1.pptx"
	 * @param targetImageFileDir	代表转换后的各个图片文件的保存路径，如"D:/软件项目程序/Demo/pptImg/"
	 * @param imageFormatNameString 代表转换的图片格式的字符串， 如“jpg”、“jpeg”、"bmp"、"png"、 "gif"、"tiff"等
	 * @return 返回true表示转换成功， 返回false表示转换不成功
	 */
	public PageData convertPPTtoImage(String orignalPPTFileName,String targetImageFileDir, String imageFormatNameString) {
		/**
		 * convertReturnResult代表转换的结果，返回 false则表示转换不成功
		 */
		//封装图片路径
		PageData pd = new PageData();
		StringBuffer sb = new StringBuffer();
		
		
		boolean convertReturnResult =false;
		pd.put("bol", convertReturnResult);
		FileInputStream orignalPPTFileInputStream =null;
		FileOutputStream orignalPPTFileOutputStream =null;
		/**
		 *  org.apache.poi.hslf, 这个包只支持 PowerPoint 2007 以前的 ppt 文档解析. 因为 Office 2007 的文件底层实现方案（OOXML）和以前的版本（OLE2）有根本的变化。
		 *  支持 office 2007 的包为: org.apache.poi.xslf。
		 *  下面的SlideShow类表示PPT文档，而Slide则表示PPT文档中的某一张幻灯片。 
		 */
		SlideShow oneSlideShow =null;
		try{
			orignalPPTFileInputStream = new FileInputStream(orignalPPTFileName);
			oneSlideShow = new SlideShow(orignalPPTFileInputStream);
			/**
			 * 获得PPT每页的尺寸大小（宽和高度）
			 */
			Dimension onePPTPageSize = oneSlideShow.getPageSize();
			/**
			 * 获得PPT文件中的所有的PPT页面（获得每一张幻灯片），并转换为一张张的播放片
			 */
			Slide[] pptPageSlideArray = oneSlideShow.getSlides();
			/**
			 * 下面的循环的主要功能是实现对PPT文件中的每一张幻灯片进行转换和操作。
			 */
			for (int pptPageSlideIndex = 0;  pptPageSlideIndex < pptPageSlideArray.length;  pptPageSlideIndex++) {				
				TextRun[] textRunsArray = pptPageSlideArray[pptPageSlideIndex].getTextRuns();				
				for (int textRunsArrayIndex = 0; textRunsArrayIndex < textRunsArray.length; textRunsArrayIndex++) {
					RichTextRun[] pptRichTextRunsArray = textRunsArray[textRunsArrayIndex].getRichTextRuns();					
					for (int pptRichTextRunsArrayIndex = 0; pptRichTextRunsArrayIndex < pptRichTextRunsArray.length; pptRichTextRunsArrayIndex++) {								
						/**原来是直接设置为宋体，这样会使得转换后的图片上的文字与原PPT的文字不一致，应该改变为应用PPT本身的字体名
						 * pptRichTextRunsArray[pptRichTextRunsArrayIndex].setFontIndex(1);	
						 * pptRichTextRunsArray[pptRichTextRunsArrayIndex].setFontName("宋体");
						 */ 
						/**
						 * 获得某个文本框内的字体名字符串，并识别是否为null（未正确地获得相关的字体名字符串），则设置为默认的字体名——宋体
						 * 但如果PPT文件在WPS中保存过，则pptRichTextRunsArray[pptRichTextRunsArrayIndex].getFontSize()的值可能为0或者26040。因此，
						 * 首先识别当前文本框内的字体尺寸是否为0或者大于26040，则设置默认的字体尺寸。
						 */
						int currentFontSize = pptRichTextRunsArray[pptRichTextRunsArrayIndex].getFontSize();
						if((currentFontSize <=0)||(currentFontSize >=26040)){
							pptRichTextRunsArray[pptRichTextRunsArrayIndex].setFontSize(30);
						}
						
						String currentFontName=pptRichTextRunsArray[pptRichTextRunsArrayIndex].getFontName();						
						if(currentFontName == null){
							pptRichTextRunsArray[pptRichTextRunsArrayIndex].setFontName("宋体");
						}
						else{
							pptRichTextRunsArray[pptRichTextRunsArrayIndex].setFontName(currentFontName);
						}
						/**
						 * 应用 pptRichTextRunsArray[pptRichTextRunsArrayIndex].getText() 可以获得其中的文字
						 * int pptRichTextRunsNumberIndex = pptRichTextRunsArray[pptRichTextRunsArrayIndex].getFontIndex();
							String fontNameForPPTText = pptRichTextRunsArray[pptRichTextRunsArrayIndex].getFontName();
						 */
					}
				}				
				/**
				 * 下面的代码是获取某一页PPT中的第一个备注文字串
			     System.out.println("备注文字：" + pptPageSlideArray[pptPageSlideIndex].getNotesSheet().getTextRuns()[0].getText());
			     */
				/**
				 * 创建BufferedImage对象，图象的尺寸为原来PPT的每页的尺寸
				 */
				BufferedImage oneBufferedImage = new BufferedImage(onePPTPageSize.width, onePPTPageSize.height, BufferedImage.TYPE_INT_RGB);	
				Graphics2D oneGraphics2D = oneBufferedImage.createGraphics();
				/**
				 * 设置转换后的图片的背景颜色为白色
				 * oneGraphics2D.setPaint(Color.white);
					oneGraphics2D.fill(new Rectangle2D.Float(0, 0, onePPTPageSize.width ,	onePPTPageSize.height));
				 */
				pptPageSlideArray[pptPageSlideIndex].draw(oneGraphics2D);			
				/**
				 * 设置图片的存放路径和图片的格式，注意生成的文件路径为绝对路径，最终获得各个图像文件所对应的输出流对象
				 */				
				String fileName = GuidUtil.getGuid();
				orignalPPTFileOutputStream = new FileOutputStream(targetImageFileDir+ fileName+ "."+imageFormatNameString);
				sb.append(fileName+ "."+imageFormatNameString).append(",");
				/**
				 * 图像文件的格式字符串为“jpg”、“jpeg”、"bmp"、"png"、 "gif"、"tiff"等，将转换后的各个图片文件保存到指定的目录中
				 */
				try {
					ImageIO.write(oneBufferedImage, imageFormatNameString, orignalPPTFileOutputStream);
				} catch (IOException e) {			
					e.printStackTrace();
					return pd;
				}				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			try {
				if(orignalPPTFileInputStream != null ){
					orignalPPTFileInputStream.close();
				}
			} 
			catch (IOException e) {				
				e.printStackTrace();
			}
			try {
				if(orignalPPTFileOutputStream != null ){
					orignalPPTFileOutputStream.close();
				}
			} 
			catch (IOException e) {			
				e.printStackTrace();
			}
			convertReturnResult =true;	
		}		
		pd.put("imgurl", sb.substring(0, sb.length()-1).toString());
		pd.put("bol", convertReturnResult);
		return pd;
	}
	/**
	 *  检查文件是否为ppt或者pptx格式
	 * @param orignalPPTFileName	需要转换的原始PPT的文件名称
	 * @return
	 */
	public short checkOrignalFileFormat(String  orignalPPTFileName) {
		/**
		 * PPT格式的识别结果， =0 不是PPT或者PPTX的格式文件， =1 代表*.ppt 格式， =2  代表 *.pptx 格式
		 */
		short pptORpptxFormatFile = 0;
		/**
		 * 识别需要转换的原始PPT的文件是否为真正的PPT文件
		 */		
		String orignalPPTFileSuffixName = null;
		if (orignalPPTFileName != null && orignalPPTFileName.lastIndexOf(".") != -1) {
			/**
			 * 获得需要转换的原始ppt或者pptx的文件扩展名，返回的结果应该为“ppt”或者"pptx"字符串
			 */			
			orignalPPTFileSuffixName = orignalPPTFileName.substring(orignalPPTFileName.lastIndexOf(".") );
			/** 
			 * 识别是否为.ppt格式
			 */
			if (".ppt".equals(orignalPPTFileSuffixName)) {
				pptORpptxFormatFile = 1;
			}
			/**
			 * 识别是否为.pptx格式
			 */
			else if (".pptx".equals(orignalPPTFileSuffixName)) {
				pptORpptxFormatFile = 2;
			}
			/**
			 * 如果不是.ppt格式或者*.pptx格式，则表示为非法的文件格式，则不能进行转换，符合检测的结果编码为0
			 */
		}		
		return pptORpptxFormatFile;
	}
}
