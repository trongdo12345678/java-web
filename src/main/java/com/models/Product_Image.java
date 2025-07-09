package com.models;

public class Product_Image {
	private int id;
	private String file_name;
	private int main_status;
	private int id_pro;
	private String title_product;
	public Product_Image() {
	}
	public Product_Image(int id, String file_name, int main_status, int id_pro) {
		this.id = id;
		this.file_name = file_name;
		this.main_status = main_status;
		this.id_pro = id_pro;
	}
	public String getTitle_product() {
	        return title_product;
	 }
	
    public void setTitle_product(String title_product) {
        this.title_product = title_product;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFile_name() {
		return file_name;
	}
	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}
	public int getMain_status() {
		return main_status;
	}
	public void setMain_status(int main_status) {
		this.main_status = main_status;
	}
	public int getId_pro() {
		return id_pro;
	}
	public void setId_pro(int id_pro) {
		this.id_pro = id_pro;
	}
	
	
}
