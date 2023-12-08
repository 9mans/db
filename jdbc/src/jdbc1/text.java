package jdbc1;

public class text {

	/*
	 * 	
		static 
			
		static 변수
		static 메소드
		static 블록
		
		public class Sample{
		
			private int x;    				//멤버 변수
			private static int y;				// 정적변수, 클래스 변수
		
			static {					// 정적 블록
				수행문;
				수행문;
				수행문;
			}
			
			public Sample() {				// 생성자
		
			}
		
			public void method1() {				// 멤버 메소드, 인스턴스 메소드
		
			}
		
		
			public static void method2() {			// 정적 메소드, 클래스 메소드
		
			}
		}
		
		멤버변수, 멤버 메소드는 
			- 객체 생성 후 사용가능하다
			- 생성된 객체를 참조하는 참조변수를 이용해서 멤버변수, 멤버메소드를 사용한다
				참조변수.멤버변수 = 값;
				참조변수.멤버메소드();
			Sample s = new Sample();
			s.x = 10;
			s.method1();
		
		
		정적변수, 정적메소드
			- 클래스가 메모리에 로딩되는 즉시 사용가능
			- 클래스 이름으로 정적변수와 정적 메소드를 사용한다
			Sample.y = 10;
			Sample.method2()
		
		정적 블록
			- 클래스가 메모리에 로딩될 때 딱 한번만 자동으로 실행된다
			- 클래스의 일생동안 단 한번만 실행되는 작업을 정의할 목적으로 사용한다
			- 클래스가 메모리에 로딩될때 자동으로 수행할 작업의 정의할 목적으로 사용한다
		
		생성자
			- 클래스로 객체를 만들 때 마다 실행되는 메소드
			- 생성된 객체에 대한 초기화 작업을 담당한다
			
----------------------------------------------------------------------------------

		DML
		
		INSERT	\
		DELETE	 >>>> 행의 갯수 반환(int)
		UPDATE	/
			
			
		SELECT ---> ResultSet 
						- boolean next() : 커서를 한 칸 아래로 이동시킨다
										   해당 위치에 행이 존재하면 ture를 반환한다
										   
										   while(re.next()) {
										   
										   } 사용
						- xxx getxxx(String 컬럼명) : 현재 커서가 위치할 행에서 지정된 컬럼명이 반환
						
		VO.DAO 패턴

			VO(VALue Object_)
				- 데이터를 표현하는 객체
				- 보통은 특정 테이블의 한 행을 표현한다
				- 테이블당 하나의 vo클래스가 필요하다
			
			DAO(Data Access Object)
				- 데이터베이스 엑세스를 담당하는 객체
				- 보통은 특정 테이블마다 하나의 DAO 클래스 필요
				- DAO 클래스에는 특정 테이블에 대한 CRUD(추가, 조회, 수정, 삭제) 작업이 구현되어있다

ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

RowMapper<T> 와 jdbcTemplate

jdbcTemplate
	- JDBC를 활용한 데이터베이스 엑세스작업을 도와주는 기능이 구현된 클래스다
	- 주요 메소드
		int insert(String sql)
		int insert(String sql, object... params)

		int update(String sql)
		int update(Stirng sql, object... params)
		
		int delete(String sql)
		int delete(Stirng sql, object... params)

		* object... params 매개변수가 있는 메소드는 SQL의 바인딩 변수(?) 가 있을 때
		  사용하는 메소드다
		  object... params에 해당하는 부분에 ?와 치환될 값을 순서대로 나열하면 된다
		* 작성 예)
			public void insertBook(Book book) {

				String sql = """
					insert into sample_book
					(book_no, book_title, book_price, book_stock)
					values
					(?,?,?,?,?)
				""";

			jdbcTemplate.insert(sql, book.getNo(), 
						 book.getTitle(), 
						 book.getPrice(), 
						 book.getStock());
			}


		T selectOne(String sql, RowMapper<T> rowMapper);
		T selectOne(String sql, RowMapper<T> rowMapper, object... params);

		List<T> selectList(String sql, RowMapper<T> rowMapper);
		List<T> selectList(String sql, RowMapper<T> rowMapper, object... params);

		* selectOne 메소드는 조회결과가 한 건 조회될때 사용하고
		  selectList 메소드는 조회결과가 여러 건 조회될 때 사용한다
		  selectOne 메소드는 기본키 컬럼이 where 절의 조건식에 포함되어 있을 때 사용한다

		RowMapper<T> 는 인터페이스다

			interface RowMapper<T> {

				T mapRow(ResultSet rs) throws SQLException;
			}

			select 
				book_no, book_title, book_price
			from 
				sample_books;
			
			ResultSet
			-----------------------------------------------
			100    이것이 자바다    35000
			102    자바의 정석	    31000
			-----------------------------------------------						


public Book getBookByNo(int no) {
   String sql = """
      select book_no, book_title, book_price
      from sample_books
      where book_no = ?
   """;

   JdbcTemplate.selectOne(sql, new RowMapper<Book>() {
      public Book mapRow(ResultSet rs) throws SQLException {
         Book book = new Book();
         book.setNo(rs.getInt("book_no"));
      
         return book;
      }
   }, no);

   JdbcTemplate.selectOne(sql, rs -> {
         Book book = new Book();
         book.setNo(rs.getInt("book_no"));
      
         return book;
   }, no);

}
						
						
						
--------------------------------------------------------------------------------

						

// RowMapper<T> 인터페이스는 ResultSet 객체에서 현재 커서가 위치한 행의 데이터틀
// 특정 객체에 담아서 반환하는 기능이 추상화 되어있다

public interface RowMapper<T> {
	T mapRow(Result rs);
}


// 인터페이스를 구현한 구현클래스 작성하기
public class ProductRowMapper implements RowMapper<Product> {
		// 전달받은 ResultSet 객체에서 현재 커서가 위치한 행의 데이터를 가져와서
		// Product 객체를 생성해서 담고 반환한다
		public Product mapRow(ResultSet rs) {
			int no = rs.getInt("prod_no");
			String name = rs.getString("prod_name");
			String maker = rs.getString("prod_maker");
			....
	
			Product product = new Product();
			product.setNo(no);
			product.setName(name);
			....
		
			return product;
		
 		}
	
}

// 인터페이스를 구현한 구현클래스를 작성하지 않고 익명객체 생성하기
RowMapper<Product> rowMapper = new RowMapper<Product>() {
	public Product mapRow(ResultSet rs) {

		Product product = new Product();

		product.setNo(rs.getInt("prod_no"));		
		product.setName(rs.getString("prod_name"));
		product.setMaker(rs.getString("prod_maker"));
		...

		return product;
	}
}

// 인터페이스를 구현한 구현클래스를 작성하지 않고 람다식으로 익명객체 생성하기
RowMapper<Product> rowMapper = rs -> {
		
		Product product = new Product();

		product.setNo(rs.getInt("prod_no"));		
		product.setName(rs.getString("prod_name"));
		product.setMaker(rs.getString("prod_maker"));
		...

		return product;
}



public List<Product> getAllProducts() {
	String sql = """
		select prod_no, prod_name, prod_maker, prod_price...
		from sampel_products
		order by prod_no desc
	""";
	
	return JdbcTemplate.slectList(sql, new RowMapper<Product>() {
		public Product mapRow(ResultSet rs) {
		
		Product product = new Product();			익명객체

		product.setNo(rs.getInt("prod_no"));		
		product.setName(rs.getString("prod_name"));
		product.setMaker(rs.getString("prod_maker"));
		...

		return product;
		}

	}
}

	
	return JdbcTemplate.selectList(sql, rs-> {
		
		Product product = new Product();

		product.setNo(rs.getInt("prod_no"));			람다식
		product.setName(rs.getString("prod_name"));
		product.setMaker(rs.getString("prod_maker"));
		...

		return product;
	});
}

						
						
						
						
						
	close는 역순으로
	 * 
	 */
}
