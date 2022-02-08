package jp.co.warehouse.img;

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

/*
 * This class is used for showing the user profile image @ event detail page.
 */
@WebServlet("/getProfileImageById")
public class GetProfileImgById extends HttpServlet {

	private static final long serialVersionUID = -2541805922468942458L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		/*
		 * Receive the user id by the get method
		 */
		String stringSelfRegisteredUserId = request.getParameter("selfRegisteredUserId");
		int selfRegisteredUserId = Integer.parseInt(stringSelfRegisteredUserId);

		try {
			// Find the self registered user image with the self registered user id
			GetImageDAO getImageDAO = new GetImageDAO();
			BufferedImage bimg = getImageDAO.selectImageByUserId(selfRegisteredUserId);
			// Send the image out
			response.setContentType("image/jpeg");
			OutputStream os = response.getOutputStream();
			ImageIO.write(bimg, "jpg", os);
			os.flush();
		}
		/*
		 * If something wrong is happened, the alternative picture will be applied.
		 */
		catch(IllegalArgumentException e) {
			ServletContext context = getServletContext();
			InputStream is = context.getResourceAsStream("/common/img/img_non_profile.jpg");
			BufferedImage unconfortableImage = ImageIO.read(is);

			// Send the image out
			response.setContentType("image/jpeg");
			OutputStream abnormalOs = response.getOutputStream();
			ImageIO.write(unconfortableImage, "jpg", abnormalOs);
			abnormalOs.flush();
		}
		catch (DatabaseException | SystemException e) {
			e.printStackTrace();
		}
	}
}