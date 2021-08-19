/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import entity.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thanh Dang
 */
public class ProductDAO {
    
    /**
     * Get all products
     * @return
     * @throws Exception 
     */
    public List<Product> getAll() throws Exception{
        DBContext dbContext = new DBContext();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            List<Product> list = new ArrayList<>();
            String sql = "SELECT * FROM Product";

            connection = dbContext.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String image = rs.getString("image");
                double price = rs.getDouble("price");
                
                Product product = new Product(id, name, image, price);
                list.add(product);
            }

            dbContext.closeConnection(rs, ps, connection);
            return list;

        } catch (Exception e) {
            throw e;
        } finally {
            dbContext.closeConnection(rs, ps, connection);
        }
    }
    
    /**
     * Get product by its id
     * @param id
     * @return
     * @throws Exception 
     */
    public Product getProductById(int id) throws Exception {
        DBContext dbContext = new DBContext();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM Product WHERE [id]=?";

            connection = dbContext.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                return new Product(rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getDouble("price"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            dbContext.closeConnection(rs, ps, connection);
        }
        return null;
    }
    
    /**
     * Add new product to database
     * @param name
     * @param image
     * @param price
     * @return
     * @throws Exception 
     */
    public int addNewProduct(String name, String image, double price) throws Exception {
        DBContext dbContext = new DBContext();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO Product([name], [image], [price]) VALUES (?, ?, ?)";
            
            connection = dbContext.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, image);
            ps.setDouble(3, price);
            int n = ps.executeUpdate();
            return n;
        } catch (Exception e) {
            throw e;
        } finally {
            dbContext.closeConnection(null, ps, connection);
        }
    }
    
    /**
     * Delete a product from database
     * @param id
     * @return
     * @throws Exception 
     */
    public int deleteProduct(int id) throws Exception {
        DBContext dbContext = new DBContext();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM Product WHERE [id]=?";
            
            connection = dbContext.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            int n = ps.executeUpdate();
            return n;
        } catch (Exception e) {
            throw e;
        } finally {
            dbContext.closeConnection(null, ps, connection);
        }
    }
    
    /**
     * Update information of product
     * @param p
     * @return
     * @throws Exception 
     */
    public int updateProduct(Product p) throws Exception {
        DBContext dbContext = new DBContext();
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE Product SET [name]=?, [image]=?, [price]=? WHERE [id]=?";
            
            connection = dbContext.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, p.getName());
            ps.setString(2, p.getImage());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getId());
            int n = ps.executeUpdate();
            return n;
        } catch (Exception e) {
            throw e;
        } finally {
            dbContext.closeConnection(null, ps, connection);
        }
    }
    
    /**
     * Count records for paging
     * @param searchStr
     * @return
     * @throws Exception 
     */
    public int countRecord(String searchStr) throws Exception {
        DBContext dbContext = new DBContext();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT COUNT(*) FROM Product WHERE [name] LIKE ?";
            
            connection = dbContext.getConnection();
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + searchStr + "%");
            rs = ps.executeQuery();
            int count = 0;
            
            while (rs.next()) {
                count = rs.getInt(1);
            }
            
            return count;
        } catch (Exception e) {
            throw e;
        } finally {
            dbContext.closeConnection(rs, ps, connection);
        }
    }
    
    /**
     * Search products and paging
     * @param searchStr
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception 
     */
    public List<Product> search(String searchStr, int pageIndex, int pageSize) throws Exception {
        DBContext dbContext = new DBContext();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            List<Product> list = new ArrayList<>();
            String sql = "SELECT * FROM("
                    + " SELECT ROW_NUMBER() OVER (ORDER BY ID ASC) AS rn, *"
                    + " FROM Product WHERE name LIKE ?) AS x"
                    + " WHERE rn BETWEEN ?"
                    + " AND ?";
            
            connection = dbContext.getConnection();
            ps = connection.prepareStatement(sql);
            
            ps.setString(1, "%" + searchStr + "%");
            ps.setInt(2, pageSize*(pageIndex-1) + 1);
            ps.setInt(3, pageIndex*pageSize);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                list.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getDouble("price")));
            }
            return list;
        } catch (Exception e) {
            throw e;
        } finally {
            dbContext.closeConnection(rs, ps, connection);
        }
    }
    
    public int countAllRecord() throws Exception {
        return getAll().size();
    }
    
    /**
     * Paging for home page
     * @param pageIndex
     * @param pageSize
     * @return
     * @throws Exception 
     */
    public List<Product> paging(int pageIndex, int pageSize) throws Exception {
        DBContext dbContext = new DBContext();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            List<Product> list = new ArrayList<>();
            String sql = "SELECT * FROM("
                    + " SELECT ROW_NUMBER() OVER (ORDER BY ID ASC) AS rn, *"
                    + " FROM Product) AS x"
                    + " WHERE rn BETWEEN ?"
                    + " AND ?";
            
            connection = dbContext.getConnection();
            ps = connection.prepareStatement(sql);
            
            ps.setInt(1, pageSize*(pageIndex-1) + 1);
            ps.setInt(2, pageIndex*pageSize);
            
            rs = ps.executeQuery();
            
            while(rs.next()) {
                list.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("image"), rs.getDouble("price")));
            }
            
            return list;
        } catch (Exception e) {
            throw e;
        } finally {
            dbContext.closeConnection(rs, ps, connection);
        }
    }
}
