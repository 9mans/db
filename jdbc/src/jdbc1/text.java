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
						
						
						
						
						
						
						
						
						
						
						
						
						
						
	 * 
	 */
}
