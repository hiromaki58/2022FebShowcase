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

/*
 * This class shows the image to confirm what eye catch will be submitted to the database.
 * If no image is assigned, one default image will be selected and registered into the database.
 */
@WebServlet("/showTentativeEyecatch")
public class ShowTentativeEyecatch extends HttpServlet{
	private static final long serialVersionUID = -1074375293643843679L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		HttpSession session = request.getSession();
		ImgAddress imgaddr = new ImgAddress();

		imgaddr= (ImgAddress) session.getAttribute("eyecatch_path");

		/*
		 * No image is selected and no chance to submit a new image.
		 * In that case the default image will be selected.
		 */
		if(imgaddr.getImgAddress().equals("")) {
			ServletContext context = getServletContext();
			InputStream is = context.getResourceAsStream("/common/img/img_no_image_03.jpg");
			BufferedImage bufferedImage = ImageIO.read(is);

	        //Send the image out
			response.setContentType("image/jpeg");
			OutputStream os = response.getOutputStream();
			ImageIO.write(bufferedImage, "jpg", os);
			os.flush();
		}
		else {
			BufferedImage bufferedImage = imgaddr.getImage();

			if(imgaddr.getImgAddress().matches(".*jpg.*")) {
		        //Send the image out
				try {
					response.setContentType("image/jpeg");
					OutputStream os = response.getOutputStream();
					ImageIO.write(bufferedImage, "jpg", os);
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
			else if(imgaddr.getImgAddress().matches(".*png.*")) {
				//Send the image out
				try {
					response.setContentType("image/png");
					OutputStream os = response.getOutputStream();
					ImageIO.write(bufferedImage, "png", os);
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
			else if(imgaddr.getImgAddress().matches(".*gif.*")) {
				//Send the image out
				try {
					response.setContentType("image/gif");
					OutputStream os = response.getOutputStream();
					ImageIO.write(bufferedImage, "gif", os);
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
					ImageIO.write(bufferedImage, "jpg", os);
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
