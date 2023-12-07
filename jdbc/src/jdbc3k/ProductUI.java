package jdbc3k;

import java.sql.SQLException;
import java.util.List;

public class ProductUI {

	private ProductDao productDao = new ProductDao();
	private Scanner scanner = new Scanner(System.in);
	
	public void showMenu() {
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("1.전체조회 2.상세조회 3.검색(가격) 4.신규등록 5.삭제 6.가격수정 7.재고수정 0.종료");
		System.out.println("----------------------------------------------------------------------------------");
		
		System.out.print("### 메뉴번호: ");
		int menuNo = scanner.nextInt();
		
		try {
			switch(menuNo)  {
			case 1: 전체조회(); break;
			case 2: 상세조회(); break;
			case 3: 검색(); break;
			case 4: 신규등록(); break;
			case 5: 삭제(); break;
			case 6: 가격수정(); break;
			case 7: 재고수정(); break;
			case 0: 종료(); break;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		
		showMenu();
	}
	
	private void 전체조회() throws SQLException {
		System.out.println("<< 전체 상품 조회 >>");
		
		List<Product> products = productDao.getAllProducts();
		if (products.isEmpty()) {
			System.out.println("### 등록된 상품정보가 없습니다.");
			return;
		}
		
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("번호\t가격\t할인가격\t상품명");
		System.out.println("----------------------------------------------------------------------------------");
		for (Product product : products) {
			System.out.print(product.getNo() + "\t");
			System.out.print(product.getPrice() + "\t");
			System.out.print(product.getDiscountPrice() + "\t");
			System.out.println(product.getName());
		}
		System.out.println("----------------------------------------------------------------------------------");
	}
	
	private void 상세조회() throws SQLException {
		System.out.println("<< 제품 상세 조회 >>");
		System.out.println("### 상품번호를 입력해서 상품의 상세정보를 확인하세요.");
		
		System.out.print("### 상품번호: ");
		int no = scanner.nextInt();
		
		Product product = productDao.getProductByNo(no);
		if (product == null) {
			System.out.println("["+no+"]번 상품정보가 존재하지 않습니다.");
			return;
		}
		
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("상품번호: " + product.getNo());
		System.out.println("상품이름: " + product.getName());
		System.out.println("제조회사: " + product.getMaker());
		System.out.println("상품가격: " + product.getPrice());
		System.out.println("할인가격: " + product.getDiscountPrice());
		System.out.println("재고수량: " + product.getStock());
		System.out.println("절판여부: " + product.getSoldOut());
		System.out.println("등록일자: " + product.getCreatedDate());
		System.out.println("수정일자: " + product.getUpdatedDate());
		System.out.println("----------------------------------------------------------------------------------");
	}
	
	private void 검색() throws SQLException {
		System.out.println("<< 상품 정보 검색 >>");
		System.out.println("### 최소가격, 최대가격을 입력해서 해당 가격범위에 속하는 상품을 검색하세요.");
		
		System.out.print("### 최소가격: ");
		int min = scanner.nextInt();
		System.out.print("### 최대가격: ");
		int max = scanner.nextInt();
		
		List<Product> products = productDao.getBooksByPrice(min, max);
		if (products.isEmpty()) {
			System.out.println("### 검색된 상품정보가 없습니다.");
			return;
		}
		
		System.out.println("----------------------------------------------------------------------------------");
		System.out.println("번호\t가격\t할인가격\t상품명");
		System.out.println("----------------------------------------------------------------------------------");
		for (Product product : products) {
			System.out.print(product.getNo() + "\t");
			System.out.print(product.getPrice() + "\t");
			System.out.print(product.getDiscountPrice() + "\t");
			System.out.println(product.getName());
		}
		System.out.println("----------------------------------------------------------------------------------");
	}
	
	private void 신규등록() throws SQLException {
		System.out.println("<< 신규 상품 등록 >>");
		System.out.println("### 신규 상품정보를 입력해서 등록하세요.");
		
		System.out.print("### 상품번호: ");
		int no = scanner.nextInt();
		System.out.print("### 상품이름: ");
		String name = scanner.nextString();
		System.out.print("### 제조회사: ");
		String maker = scanner.nextString();
		System.out.print("### 상품가격: ");
		int price = scanner.nextInt();
		System.out.print("### 할인가격: ");
		int discountPrice = scanner.nextInt();
		System.out.print("### 입고수량: ");
		int stock = scanner.nextInt();
		
		Product product = new Product();
		product.setNo(no);
		product.setName(name);
		product.setMaker(maker);
		product.setPrice(price);
		product.setDiscountPrice(discountPrice);
		product.setStock(stock);
		
		productDao.insertProduct(product);
		
		System.out.println("### 신규 상품정보가 등록되었습니다.");
	}
	
	private void 삭제() throws SQLException {
		System.out.println("<< 상품 삭제 >>");
		System.out.println("### 삭제할 상품번호를 입력하세요.");
		
		System.out.print("### 상품번호: ");
		int no = scanner.nextInt();
		
		productDao.deleteProduct(no);
		
		System.out.println("### ["+no+"]번 상품이 삭제되었습니다.");
	}
	
	private void 가격수정() throws SQLException {
		System.out.println("<< 상품 가격 수정 >>");
		System.out.println("### 상품번호, 가격, 할인가격을 입력해서 가격정보를 수정하세요.");
		
		System.out.print("### 상품번호: ");
		int no = scanner.nextInt();
		System.out.print("### 상품가격: ");
		int price = scanner.nextInt();
		System.out.print("### 할인가격: ");
		int discountPrice = scanner.nextInt();
		
		productDao.updateProductPrice(no, price, discountPrice);
		
		System.out.println("### ["+no+"]번 상품의 가격정보가 변경되었습니다.");
	}
	
	private void 재고수정() throws SQLException {
		System.out.println("<< 상품 재고수량 수정 >>");
		System.out.println("### 상품번호, 재고수량을 입력해서 재고량을 수정하세요.");
		
		System.out.print("### 상품번호: ");
		int no = scanner.nextInt();
		System.out.print("### 재고수량: ");
		int stock = scanner.nextInt();
		
		productDao.updateProductStock(no, stock);
		
		System.out.println("### ["+no+"]번 상품의 재고수량이 변경되었습니다.");
	}
	
	private void 종료() {
	
	}
	
	public static void main(String[] args) {
		new ProductUI().showMenu();
	}
	
}
