package jp.co.warehouse.controller.image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.warehouse.entity.ImgAddress;

@WebServlet("/showTentativeArticleImg03")
public class ShowTentativeArticleImg03 extends HttpServlet {
	
	private static final long serialVersionUID = -1074375293643843679L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		HttpSession session = request.getSession();
		ImgAddress imgaddr_03 = new ImgAddress();

		//Image 03
		imgaddr_03= (ImgAddress) session.getAttribute("article_img_path_03");

		if(imgaddr_03.getImgAddress().equals("")) {
			ServletContext context = getServletContext();
			InputStream is = context.getResourceAsStream("/common/img/img_no_image_02.jpg");
			BufferedImage bufferedImage_03 = ImageIO.read(is);

			response.setContentType("image/jpeg");
			OutputStream os_03 = response.getOutputStream();
			ImageIO.write(bufferedImage_03, "jpg", os_03);
			os_03.flush();
		}
		else {
			BufferedImage bufferedImage_03 = imgaddr_03.getImage();

			if(imgaddr_03.getImgAddress().matches(".*jpg.*")) {
		        //Send the image out
				try {
					response.setContentType("image/jpeg");
					OutputStream os = response.getOutputStream();
					ImageIO.write(bufferedImage_03, "jpg", os);
					os.flush();
				}
				//If wrong format image is tried to be registered, error message will be indicated.
				catch(IllegalArgumentException e) {
					ServletContext context = getServletContext();
					InputStream is = context.getResourceAsStream("/common/img/img_no_image_02.jpg");
					BufferedImage unconfortableImage = ImageIO.read(is);

			        //Send the image out
					response.setContentType("image/jpeg");
					OutputStream abnormalOs = response.getOutputStream();
					ImageIO.write(unconfortableImage, "jpg", abnormalOs);
					abnormalOs.flush();
				}
			}
			else if(imgaddr_03.getImgAddress().matches(".*png.*")) {
				//Send the image out
				try {
					response.setContentType("image/png");
					OutputStream os = response.getOutputStream();
					ImageIO.write(bufferedImage_03, "png", os);
					os.flush();
				}
				//If wrong format image is tried to be registered, error message will be indicated.
				catch(IllegalArgumentException e) {
					ServletContext context = getServletContext();
					InputStream is = context.getResourceAsStream("/common/img/img_no_image_02.jpg");
					BufferedImage unconfortableImage = ImageIO.read(is);

			        //Send the image out
					response.setContentType("image/jpeg");
					OutputStream abnormalOs = response.getOutputStream();
					ImageIO.write(unconfortableImage, "jpg", abnormalOs);
					abnormalOs.flush();
				}
			}
			else if(imgaddr_03.getImgAddress().matches(".*gif.*")) {
				//Send the image out
				try {
					response.setContentType("image/gif");
					OutputStream os = response.getOutputStream();
					ImageIO.write(bufferedImage_03, "gif", os);
					os.flush();
				}
				//If wrong format image is tried to be registered, error message will be indicated.
				catch(IllegalArgumentException e) {
					ServletContext context = getServletContext();
					InputStream is = context.getResourceAsStream("/common/img/img_no_image_02.jpg");
					BufferedImage unconfortableImage = ImageIO.read(is);

			        //Send the image out
					response.setContentType("image/jpeg");
					OutputStream abnormalOs = response.getOutputStream();
					ImageIO.write(unconfortableImage, "jpg", abnormalOs);
					abnormalOs.flush();
				}
			}
			else {
				//Send the image out
				try {
					response.setContentType("image/jpeg");
					OutputStream os = response.getOutputStream();
					ImageIO.write(bufferedImage_03, "jpg", os);
					os.flush();
				}
				//If wrong format image is tried to be registered, error message will be indicated.
				catch(IllegalArgumentException e) {
					ServletContext context = getServletContext();
					InputStream is = context.getResourceAsStream("/common/img/img_no_image_02.jpg");
					BufferedImage unconfortableImage = ImageIO.read(is);

			        //Send the image out
					response.setContentType("image/jpeg");
					OutputStream abnormalOs = response.getOutputStream();
					ImageIO.write(unconfortableImage, "jpg", abnormalOs);
					abnormalOs.flush();
				}
			}
		}
	}
}
