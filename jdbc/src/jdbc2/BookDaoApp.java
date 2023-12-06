package jdbc2;

public class BookDaoApp {

	public static void main(String[] args) throws Exception {
		
		Book b1 = new Book();
		b1.setNo(101);
		b1.setTitle("자바의 정석 기초");
		b1.setWriter("남궁성");
		b1.setPrice(32000);
		b1.setStock(10);
		
		Book b2 = new Book();
		b2.setNo(102);
		b2.setTitle("개발자를 위한 devops");
		b2.setWriter("진명조");
		b2.setPrice(30000);
		b2.setStock(20);
		
		BookDao dao = new BookDao();
		dao.insertBook(b1);
		dao.insertBook(b2);
	}
}
