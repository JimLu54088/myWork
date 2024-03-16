//package calculatorTest;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.*;
//
//import org.junit.Before;
//import org.junit.Test;
//import static org.hamcrest.CoreMatchers.*;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.junit.Test;
//
//import calculator.Calculator;
//
//public class CalculatorTest {
//
//
//
//	@Before
//	public void setUp() {
//	    MockitoAnnotations.initMocks(this);
//	}
//
//	@Mock
//	private Calculator c;
//
//	@Test
//	public void addTest() {
//	    // 设置模拟对象的行为
//	    when(c.add(4, 5)).thenReturn(9);
//
//	    // 调用被测试的方法
//	    int d = c.add(4, 5);
//
//	    // 断言验证结果
//	    assertEquals(9, d);
//	}
//
//
//
//
//
//
//	@Test
//    public void minusTest() {
//        Calculator c = new Calculator();
//
//        c.minus(4, 5);
//        System.out.println(c);
//	}
//
//
//}
