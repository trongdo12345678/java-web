package com.repositories;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.models.Product;
import com.models.Product_Image;
import com.modelviews.Product_mapper;
import com.modelviews.ProductImage_mapper;
import com.utils.FileUtils;
import com.utils.Views;



@Repository
public class ProductRepository {
	@Autowired
	JdbcTemplate db;
	private final JdbcTemplate jdbcTemplate;
    public ProductRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	public List<Product> finAllPro()
	{
		return db.query("select * from tbl_product", new Product_mapper());
	}
	public String addPro(Product newItem) {
	    try {
	        String str_query = String.format("insert into %s values( ? , ? , ? )", Views.TBL_PRODUCT);
	        int rowaccept = db.update(str_query, new Object[] { newItem.getTitle(), newItem.getPrice(), newItem.getStatus() });
	        if (rowaccept == 1) {
	            return "success";
	        }
	        return "failed";
	    } catch (Exception e) {
	        // TODO: handle exception
	        return "insert exception data";
	    }
	}
	public String UpdateStatus(int id , int status) {

	    try {
	    	int rowaccept = db.update("update tbl_product " + "set _status = ? " + " where _id = ? ",new Object[] {status,id});
	        if (rowaccept == 1) {
	            return "success";
	        }
	            return "failed";
	        
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return "insert exception data";
	}
	//===============================================
	//===========================================
	//=========================================
	//product_images
	@SuppressWarnings("deprecation")
	public List<Product_Image> findImagesByProductId(int productId) {
	    String sql = "SELECT * FROM tbl_product_image WHERE _id_pro = ?";
	    return jdbcTemplate.query(sql, new Object[]{productId}, (rs, rowNum) -> {
	        Product_Image proI = new Product_Image();
	        proI.setId(rs.getInt("_id"));
	        proI.setFile_name(rs.getString("_file_name"));
	        proI.setId_pro(rs.getInt("_id_pro"));
	        proI.setMain_status(rs.getInt("_main_status"));
	        return proI;
	    });
	}
	@SuppressWarnings("deprecation")
	public List<Product> findImagesBy(int proId) {
	    String sql = "SELECT * FROM tbl_product WHERE _id = ?";
	    return jdbcTemplate.query(sql, new Object[]{proId}, (rs, rowNum) -> {
	        Product pro = new Product();
	        pro.setId(rs.getInt("_id"));
	        pro.setTitle(rs.getString(Views.COL_PRODUCT_TITLE));
	        return pro;
	    });
	}
	public List<Product_Image> finAllProI() {
	    String sql = "SELECT * FROM tbl_product_image";
	    return db.query(sql, new ProductImage_mapper());
	}

	public String addProI(Product_Image newItem) {
	    try {
	        String str_query = String.format("insert into %s values( ? , ? , ? )", Views.TBL_PRODUCT_IMAGE);
	        int rowaccept = db.update(str_query, new Object[] { newItem.getFile_name(), newItem.getMain_status(), newItem.getId_pro() });
	        if (rowaccept == 1) {
	            return "Add Product Image success";
	        }
	        return "failed";
	    } catch (Exception e) {
	        return "insert exception data";
	    }
	}
	public void updateAllImagesToSecondary(int productId) {
	    String sql = String.format("UPDATE %s SET %s = 0 WHERE _id_pro = ?",
	    		Views.TBL_PRODUCT_IMAGE, 
		        Views.COL_PRODUCT_IMAGE_MAIN_STATUS,
		        Views.COL_PRODUCT_IMAGE_ID_PRODUCT);
	    jdbcTemplate.update(sql, productId);
	}
	public void updateImageToMain(int id) {
	    String sql = String.format(
	        "UPDATE %s SET %s = 1 WHERE %s = ?", 
	        Views.TBL_PRODUCT_IMAGE, 
	        Views.COL_PRODUCT_IMAGE_MAIN_STATUS,
	        Views.COL_PRODUCT_IMAGE_ID
	    );
	    jdbcTemplate.update(sql, id);
	}
	public Product_Image findByid(int id) {
	    String str_query = String.format("select * from %s where %s = ?", Views.TBL_PRODUCT_IMAGE, Views.COL_PRODUCT_IMAGE_ID);
	    try {
	        return db.queryForObject(str_query, new ProductImage_mapper(), id);
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
	}
	public String deleteProI(int id) {
	    String str_query = String.format("delete from %s where %s = ?", Views.TBL_PRODUCT_IMAGE, Views.COL_PRODUCT_IMAGE_ID);
	    Product_Image proI = findByid(id);
	    if (proI == null) {
	        return "image not found";
	    }
	    try {
	        int rowaccept = db.update(str_query, new Object[]{id});
	        if (rowaccept == 1) {
	            FileUtils.deleteFile("uploads", proI.getFile_name());
	            return "success";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "delete exception data";
	    }
	    return "delete failed";
	}




}
