package jdbc3k;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
	
	public List<Product> getAllProducts() throws SQLException {
		String sql = """
			select 
				prod_no, prod_name, prod_maker, prod_price, prod_discount_price,
				prod_stock, prod_sold_out, prod_created_date, prod_updated_date
			from
				sample_products
			order by
				prod_no desc
				""";
		
		List<Product> producetList = new ArrayList<Product>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Product product = new Product();
			product.setNo(rs.getInt("prod_no"));
			product.setName(rs.getString("prod_name"));
			product.setMaker(rs.getString("prod_maker"));
			product.setPrice(rs.getInt("prod_price"));
			product.setDiscountPrice(rs.getInt("prod_discount_price"));
			product.setStock(rs.getInt("prod_stock"));
			product.setSoldOut(rs.getString("prod_sold_out"));
			product.setCreatedDate(rs.getDate("prod_created_date"));
			product.setUpdatedDate(rs.getDate("prod_updated_date"));
			
			producetList.add(product);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return producetList;
	}
	
	public Product getProductByNo(int no) throws SQLException {
		String sql = """
				select 
					prod_no, prod_name, prod_maker, prod_price, prod_discount_price,
					prod_stock, prod_sold_out, prod_created_date, prod_updated_date
				from
					sample_products
				where 
					prod_no = ?
					""";
		
		Product product = null;
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, no);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			product = new Product();
			product.setNo(rs.getInt("prod_no"));
			product.setName(rs.getString("prod_name"));
			product.setMaker(rs.getString("prod_maker"));
			product.setPrice(rs.getInt("prod_price"));
			product.setDiscountPrice(rs.getInt("prod_discount_price"));
			product.setStock(rs.getInt("prod_stock"));
			product.setSoldOut(rs.getString("prod_sold_out"));
			product.setCreatedDate(rs.getDate("prod_created_date"));
			product.setUpdatedDate(rs.getDate("prod_updated_date"));
			
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return product;
	}
	
	public List<Product> getBooksByPrice(int min, int max) throws SQLException {
		String sql = """
				select 
					prod_no, prod_name, prod_maker, prod_price, prod_discount_price,
					prod_stock, prod_sold_out, prod_created_date, prod_updated_date
				from
					sample_products
				where
					prod_price >= ? and prod_price <= ? 
				order by
					prod_price asc
					""";
		List<Product> producetList = new ArrayList<Product>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, min);
		pstmt.setInt(2, max);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			Product product = new Product();
			product.setNo(rs.getInt("prod_no"));
			product.setName(rs.getString("prod_name"));
			product.setMaker(rs.getString("prod_maker"));
			product.setPrice(rs.getInt("prod_price"));
			product.setDiscountPrice(rs.getInt("prod_discount_price"));
			product.setStock(rs.getInt("prod_stock"));
			product.setSoldOut(rs.getString("prod_sold_out"));
			product.setCreatedDate(rs.getDate("prod_created_date"));
			product.setUpdatedDate(rs.getDate("prod_updated_date"));
			
			producetList.add(product);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return producetList;
	}
	

	/**
	 * 새 상품정보를 전달받아서 테이블에 저장시킨다.
	 * @param product 새 상품정보
	 * @throws SQLException
	 */
	public void insertProduct(Product product) throws SQLException {
		String sql = """
			insert into sample_products
			(prod_no, prod_name, prod_maker, prod_price, 
			 prod_discount_price, prod_stock)
			values
			(?,?,?,?,?,?)
			""";
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, product.getNo());
		pstmt.setString(2, product.getName());
		pstmt.setString(3, product.getMaker());
		pstmt.setInt(4, product.getPrice());
		pstmt.setInt(5, product.getDiscountPrice());
		pstmt.setInt(6, product.getStock());
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	public void deleteProduct(int no) throws SQLException {
		String sql = """
			delete from sample_products
			where prod_no = ?
				""";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, no);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	public void updateProductPrice(int no, int price, int discountPrice) throws SQLException {
		String sql = """
			update sample_products
			set
				prod_price = ?,
				prod_discount_price = ?,
				prod_updated_date = sysdate
			where
				prod_no = ?
				""";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, price);
		pstmt.setInt(2, discountPrice);
		pstmt.setInt(3, no);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	public void updateProductStock(int no, int stock) throws SQLException {
		String sql = """
			update sample_products
			set
				prod_stock = ?,
				prod_updated_date = sysdate
			where
				prod_no = ?
				""";
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, stock);
		pstmt.setInt(2, no);
		
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	private Connection getConnection() throws SQLException {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException ex) {
			throw new SQLException(ex.getMessage(), ex);
		}
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "hr";
		String password = "zxcv1234";
		
		return DriverManager.getConnection(url, user, password);
	}
}
