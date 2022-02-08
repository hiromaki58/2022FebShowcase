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

import jp.co.warehouse.dao.image.GetImageDAO;
import jp.co.warehouse.exception.DatabaseException;
import jp.co.warehouse.exception.SystemException;

@WebServlet("/showArticleImg02")
public class ShowArticleImg02 extends HttpServlet {
	private static final long serialVersionUID = -1074375293643843679L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		String imgId = request.getParameter("imgId");

		try {
			GetImageDAO getImageDao = new GetImageDAO();
			BufferedImage bimg = getImageDao.selectArticleImg02(Integer.parseInt(imgId));
			String articleImg02Extension = getImageDao.selectArticleImg02TypeById(Integer.parseInt(imgId));

			if(articleImg02Extension.equals("jpg")) {
		        //Send the image out
				try {
					response.setContentType("image/jpeg");
					OutputStream os = response.getOutputStream();
					ImageIO.write(bimg, "jpg", os);
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
			else if(articleImg02Extension.equals("png")) {
				//Send the image out
				try {
					response.setContentType("image/png");
					OutputStream os = response.getOutputStream();
					ImageIO.write(bimg, "png", os);
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
			else if(articleImg02Extension.equals("gif")) {
				//Send the image out
				try {
					response.setContentType("image/gif");
					OutputStream os = response.getOutputStream();
					ImageIO.write(bimg, "gif", os);
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
					ImageIO.write(bimg, "jpg", os);
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
		catch (DatabaseException | SystemException e) {
			e.printStackTrace();
		}
	}
}
