package com.modelviews;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.models.Product;
import com.utils.Views;

public class Product_mapper implements RowMapper<Product> {
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Product pro = new Product();
    	pro.setId(rs.getInt(Views.COL_PRODUCT_ID));
    	pro.setTitle(rs.getString(Views.COL_PRODUCT_TITLE));
    	pro.setPrice(rs.getDouble(Views.COL_PRODUCT_PRICE));
    	pro.setStatus(rs.getInt(Views.COL_PRODUCT_STATUS));
        return pro;
    }
    
}
