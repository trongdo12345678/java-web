package com.modelviews;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.models.Product_Image;
import com.utils.Views;

public class ProductImage_mapper implements RowMapper<Product_Image> {
    public Product_Image mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Product_Image proI = new Product_Image();
    	proI.setId(rs.getInt(Views.COL_PRODUCT_IMAGE_ID));
    	proI.setFile_name(rs.getString(Views.COL_PRODUCT_IMAGE_FILE));
    	proI.setMain_status(rs.getInt(Views.COL_PRODUCT_IMAGE_MAIN_STATUS));
    	proI.setId_pro(rs.getInt(Views.COL_PRODUCT_IMAGE_ID_PRODUCT));
//    	proI.setTitle_product(rs.getString(Views.COL_PRODUCT_IMAGE_TITLE_PRODUCT));
    	
    	
        return proI;
    }
    
}
