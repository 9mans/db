package jdbc3;

import java.sql.SQLException;
import java.util.List;

public class ProductUI {

	private ProductDao productDao = new ProductDao();
	private Scanner scanner = new Scanner(System.in);
	
	public void showMenu() {
		System.out.println("-------------------------------------------------------------- ");
		System.out.println("1.전체조회 2.상세조회 3.검색 4.신규등록 5.삭제 6.가격변경 7.수량변경 0.종료");
		System.out.println("-------------------------------------------------------------- ");
		
		System.out.println("### 메뉴 선택: ");
		int menuNo = scanner.nextInt();
		
		try {
			switch (menuNo) {
				case 1: 전체조회(); break;
				case 2: 상세조회(); break;
				case 3: 검색(); break;
				case 4: 신규등록(); break;
				case 5: 삭제(); break;
				case 6: 가격변경(); break;
				case 7: 수량변경(); break;
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
		
		List<Product> products = productDao.getAllProduct();
		
		if (products.isEmpty()) {
			System.out.println("### 상품 정보가 존재하지 않습니다");
			return;
		}
		
		System.out.println("----------------------------------------------------------");
		System.out.println("번호\t상품명\t제조사\t가격\t할인가격\t재고수량\t품절여부\t등록일자\t최신화");
		System.out.println("----------------------------------------------------------");
		
		for (Product product : products) {
			System.out.print(product.getNo() + "\t");
			System.out.print(product.getName() + "\t");
			System.out.print(product.getMaker() + "\t");
			System.out.print(product.getPrice() + "\t");
			System.out.print(product.getDistcountPrice() + "\t");
			System.out.print(product.getStock() + "\t");
			System.out.println(product.getSoldOut() + "\t");
		}
		System.out.println("-----------------------------------------------------------");
	}

	private void 상세조회() throws SQLException {
		
		System.out.println("<< 상품 정보 상세 조회 >>");
		
		System.out.println("### 상품 번호를 입력해서 상품 상세정보를 확인하세요");
		System.out.println("### 상품번호: ");
		int productNo = scanner.nextInt();
		
		Product product = productDao.getProductByNo(productNo);
		if (product == null) {
			System.out.println("### 상품번호: [" + productNo + "] 상품이 존재하지 않습니다");
			return;
		}
		
		System.out.println("-------------------------------------------------------");
		System.out.print("번호: " + product.getNo());
		System.out.print("이름: " + product.getName());
		System.out.print("제조사: " + product.getMaker());
		System.out.print("가격: " + product.getPrice());
		System.out.print("할인가격: " + product.getDistcountPrice());
		System.out.print("재고: " + product.getStock());
		System.out.print("품절여부: " + product.getSoldOut());
		System.out.println("-------------------------------------------------------");
	}
	
	private void 검색() throws SQLException {
		System.out.println("<< 상품 검색 기능 >> ");
		System.out.println("### 최저가격과 최고가격을 입력하여 상품을 검색하세요");
		
		
		System.out.println("최저가격");
		int minPrice = scanner.nextInt();
		System.out.println("최고가격");
		int maxPrice = scanner.nextInt();
		
		
				
		List<Product> products = productDao.serch();
		
		System.out.println("----------------------------------------------------------");
		System.out.println("번호\t상품명\t제조사\t가격\t할인가격\t재고수량\t품절여부\t등록일자\t최신화");
		System.out.println("----------------------------------------------------------");
		
		for (Product product : products) {
			System.out.print(product.getNo() + "\t");
			System.out.print(product.getName() + "\t");
			System.out.print(product.getMaker() + "\t");
			System.out.print(product.getPrice() + "\t");
			System.out.print(product.getDistcountPrice() + "\t");
			System.out.print(product.getStock() + "\t");
			System.out.println(product.getSoldOut() + "\t");
		}
		System.out.println("-----------------------------------------------------------");
	}
	
	
	private void 신규등록() throws SQLException {
		
		System.out.println("<< 신규 상품 등록 >>");
		System.out.println("### 신규 상품 정보를 등록하세요");
		
		System.out.println("### 번호: " );
		int no = scanner.nextInt();
		System.out.println("### 이름: " );
		String name = scanner.nextString();
		System.out.println("### 제조사: " );
		String maker = scanner.nextString();
		System.out.println("### 가격: " );
		int price = scanner.nextInt();
		System.out.println("### 할인가격: " );
		int discountPrice = scanner.nextInt();
		System.out.println("### 재고: " );
		int stock = scanner.nextInt();
		System.out.println("### 품절여부: " );
		String soldOut = scanner.nextString();
		
		Product product = new Product();
		product.setNo(no);
		product.setName(name);
		product.setMaker(maker);
		product.setPrice(price);
		product.setDistcountPrice(discountPrice);
		product.setStock(stock);
		product.setSoldOut(soldOut);
		
		productDao.insertProduct(product);
		System.out.println("### 신규 상품 정보를 등록했습니다");
		
		
		
	}
	private void 삭제() throws SQLException {
	
		System.out.println("<< 상품 정보 삭제 >>");
		System.out.println("### 상품 번호를 입력하여 상품 정보를 삭제합니다 ");
		
		System.out.println("### 상품 번호 입력: ");
		int prodNo = scanner.nextInt();
		
		productDao.deleteProductByNo(prodNo);
		System.out.println(" 상품 번호: " + prodNo + " 번의 상품이 삭제 되었습니다");
	}
	
	private void 가격변경() throws SQLException {
		
		System.out.println("<< 상품 가격 변경하기 >>");
		System.out.println("### 상품 번호를 입력하여 가격을 수정합니다");
		
		System.out.println("### 상품 번호 입력: ");
		int prodNo = scanner.nextInt();
		
		System.out.println("### 가격을 수정해주세요");
		int newPrice = scanner.nextInt();
		Product product = new Product();
		product.setPrice(newPrice);
		product.setNo(prodNo);
		productDao.updateProductPrice(product);
		System.out.println("가격이 변경되었습니다");
	}
	
	private void 수량변경() throws SQLException {
		System.out.println("<< 상품 수량 변경하기 >>");
		System.out.println("### 상품 번호를 입력하여 수량을 변경합니다");
		
		System.out.println("### 상품 번호 입력:");
		int prodNo = scanner.nextInt();
		
		System.out.println("### 상품 수량을 수정해주세요");
		int stock = scanner.nextInt();
		
		Product product = new Product();
		product.setStock(stock);
		product.setNo(prodNo);
		productDao.updateProductStock(product);
		System.out.println(" 수량이 변경되었습니다");
	}
	
	private void 종료() throws SQLException {
		System.out.println("### 프로그램 종료");
		System.exit(0);
	}
	public static void main(String[] args) {
		
		new ProductUI().showMenu();
	}
}
