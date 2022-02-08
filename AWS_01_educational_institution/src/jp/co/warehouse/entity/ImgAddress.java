package jp.co.warehouse.entity;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.servlet.http.Part;

public class ImgAddress {

	private String imgAddress;
	private String imgHtml;
	private Part filePart;
	private BufferedImage image;
	private InputStream is;
	private byte[] bytedata;

	public ImgAddress() {
	}

	public ImgAddress(String imgAddress) {
	  this.imgAddress = imgAddress;
	}

	/**
	 * @return imgAddress
	 */
	public String getImgAddress() {
		return imgAddress;
	}

	/**
	 * @param imgAddress セットする imgAddress
	 */
	public void setImgAddress(String imgAddress) {
		this.imgAddress = imgAddress;
	}

	/**
	 * @return imgHtml
	 */
	public String getImgHtml() {
		return imgHtml;
	}

	/**
	 * @param imgHtml セットする imgHtml
	 */
	public void setImgHtml(String imgHtml) {
		this.imgHtml = imgHtml;
	}

	/**
	 * @return filePart
	 */
	public Part getFilePart() {
		return filePart;
	}

	/**
	 * @param filePart セットする filePart
	 */
	public void setFilePart(Part filePart) {
		this.filePart = filePart;
	}

	/**
	 * @return image
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 * @param image セットする image
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
	}



	/**
	 * @return bytedata
	 */
	public byte[] getBytedata() {
		return bytedata;
	}

	/**
	 * @param bytedata セットする bytedata
	 */
	public void setBytedata(byte[] bytedata) {
		this.bytedata = bytedata;
	}

	/**
	 * @return is
	 */
	public InputStream getIs() {
		return is;
	}

	/**
	 * @param is セットする is
	 */
	public void setIs(InputStream is) {
		this.is = is;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((imgAddress == null) ? 0 : imgAddress.hashCode());
		result = prime * result + ((imgHtml == null) ? 0 : imgHtml.hashCode());
		return result;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImgAddress other = (ImgAddress) obj;
		if (imgAddress == null) {
			if (other.imgAddress != null)
				return false;
		} else if (!imgAddress.equals(other.imgAddress))
			return false;
		if (imgHtml == null) {
			if (other.imgHtml != null)
				return false;
		} else if (!imgHtml.equals(other.imgHtml))
			return false;
		return true;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ImgAddress [imgAddress=" + imgAddress + ", imgHtml=" + imgHtml + "]";
	}

}
