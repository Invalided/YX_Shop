package com.study.o2o.dto;

import java.io.InputStream;

public class imageHolder {
	private String imageName;
	private InputStream image;
	
	public imageHolder(String imageName,InputStream image) {
		this.imageName = imageName;
		this.image = image;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}
	
}
