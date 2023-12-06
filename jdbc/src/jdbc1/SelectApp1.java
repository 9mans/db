package jdbc1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SelectApp1 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		
		String sql = """
				SELECT BOOK_NO, BOOK_TITLE, BOOK_WRITER, BOOK_PRICE, BOOK_STOCK, BOOK_REG_DATE
				FROM SAMPLE_BOOKS
				"""; // 데이터의 타입을 sql을 열어 꼭 확인해볼 것
		
		Class.forName("oracle.jdbc.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "zxcv1234");
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()) {
			int no = rs.getInt("book_no");
			String title = rs.getString("book_title");
			String writer = rs.getString("book_title");
			int price = rs.getInt("book_price");
			int stock = rs.getInt("book_stock");
			Date regDate = rs.getDate("book_reg_date");
			
			System.out.println("번호: " + no);
			System.out.println("제목: " + title);
			System.out.println("저자: " + writer);
			System.out.println("가격: " + price);
			System.out.println("재고: " + stock);
			System.out.println("등록일자: " + regDate);
		}
		rs.close();
		pstmt.close();
		con.close();
	}
}
