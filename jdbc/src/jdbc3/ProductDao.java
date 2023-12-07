package jdbc3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
	// 검색하는 기능
	public List<Product> serch() throws SQLException {
		
		String sql = """
				select
					prod_no, prod_name, prod_maker, prod_price, prod_discount_price, prod_stock, prod_sold_out
				from
					sample_products
				where 
					prod_price between ? and ?
				order by
					prod_price desc
				""";
		List<Product> productList = new ArrayList<Product>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		Product product = new Product();
		pstmt.setInt(1, product.getPrice() );
		pstmt.setInt(2, product.getPrice());
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int no = rs.getInt("prod_no");
			String name = rs.getString("prod_name");
			String maker = rs.getString("prod_maker");
			int price = rs.getInt("prod_price");
			int discountPrice = rs.getInt("prod_discount_price");
			int stock = rs.getInt("prod_stock");
			String soldOut = rs.getString("prod_sold_out");
			
			Product product1 = new Product();
			product.setNo(no);
			product.setName(name);
			product.setMaker(maker);
			product.setPrice(price);
			product.setDistcountPrice(discountPrice);
			product.setStock(stock);
			product.setSoldOut(soldOut);
			
			productList.add(product1);
		}
			rs.close();
			pstmt.close();
			connection.close();
		
			return productList;
		
		
	}
	
	// 모든 상품정보를 반환하는 기능
	public List<Product> getAllProduct() throws SQLException {
		
		String sql = """
				select
					prod_no, prod_name, prod_maker, prod_price, prod_discount_price, prod_stock, prod_sold_out, prod_created_date, prod_updated_date
				
				from
				 	sample_products
				order by 
					prod_no desc
					
				""";
		
		List<Product> productList = new ArrayList<Product>();
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int no = rs.getInt("prod_no");
			String name = rs.getString("prod_name");
			String maker = rs.getString("prod_maker");
			int price = rs.getInt("prod_price");
			int discountPrice = rs.getInt("prod_discount_price");
			int stock = rs.getInt("prod_stock");
			String soldOut = rs.getString("prod_sold_out");
			
			Product product = new Product();
			product.setNo(no);
			product.setName(name);
			product.setMaker(maker);
			product.setPrice(price);
			product.setDistcountPrice(discountPrice);
			product.setStock(stock);
			product.setSoldOut(soldOut);
			
			productList.add(product);
		}
		rs.close();
		pstmt.close();
		connection.close();
		
		return productList;
	}
	
	// 상품번호에 해당하는 상품정보를 반환하는 기능
	
	public Product getProductByNo(int productNo) throws SQLException {
		String sql = """
				select
					prod_no, prod_name, prod_maker, prod_price, prod_discount_price, prod_stock, prod_sold_out 
				from
					sample_products
				where
					prod_no = ?
					
				""";
		
		Product product = null;
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, productNo);
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int no = rs.getInt("prod_no");
			String name = rs.getString("prod_name");
			String maker = rs.getString("prod_maker");
			int price = rs.getInt("prod_price");
			int discountPrice = rs.getInt("prod_discount_price");
			int stock = rs.getInt("prod_stock");
			String soldOut = rs.getString("prod_sold_out");
			
			product = new Product();
			product.setNo(no);
			product.setName(name);
			product.setMaker(maker);
			product.setPrice(price);
			product.setDistcountPrice(discountPrice);
			product.setStock(stock);
			product.setSoldOut(soldOut);
		}
		
		rs.close();
		pstmt.close();
		connection.close();
		
		return product;
	}
	
	
	// 상품정보를 삭제하는 기능
	public void deleteProductByNo(int prodNo) throws SQLException {
		String sql = """
				delete from sample_products
				where prod_no = ?
				
				""";
		Connection connection = getConnection();
		
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setInt(1, prodNo);
		pstmt.executeUpdate();
		
		pstmt.close();
		connection.close();
	}
	
	// 상품번호, 수량을 전달받아서 해당상품의 수량을 변경하는 기능
	public void updateProductStock(Product product) throws SQLException {
		String sql = """
				update sample_products
				set
					prod_stock = ?
				where
					prod_no = ?
				
				""";
		Connection connection = getConnection();
		
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		pstmt.setInt(1, product.getStock());
		pstmt.setInt(2, product.getNo());
		pstmt.executeUpdate();
		pstmt.close();
		connection.close();
		
	}
	
	// 가격정보를 변경하는 기능
	public void updateProductPrice(Product product) throws SQLException {
		String sql = """
				update sample_products
				set
					prod_price = ?
				where
					prod_no = ?
				
				""";
		Connection connection = getConnection();
		
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		pstmt.setInt(1, product.getPrice());
		pstmt.setInt(2, product.getNo());
		pstmt.executeUpdate();
		pstmt.close();
		connection.close();
		
	}
	
	// 상품정보를 등록하는 기능
	
	public void insertProduct(Product product) throws SQLException {
		String sql = """
				insert into sample_products
				(prod_no, prod_name, prod_maker, prod_price, prod_discount_price, prod_stock, prod_sold_out)
				values
				(?,?,?,?,?,?,?)
				"""; 
		
		Connection connection = getConnection();
		PreparedStatement pstmt = connection.prepareStatement(sql);
		
		pstmt.setInt(1, product.getNo());
		pstmt.setString(2, product.getName());
		pstmt.setString(3, product.getMaker());
		pstmt.setInt(4, product.getPrice());
		pstmt.setInt(5, product.getDistcountPrice());
		pstmt.setInt(6, product.getStock());
		pstmt.setString(7, product.getSoldOut());
		
		
		pstmt.execute();
		
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
	
	
	
