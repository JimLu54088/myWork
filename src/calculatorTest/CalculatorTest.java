package calculatorTest;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EmptyStackException;

import org.junit.Test;

import calculator.Calculator;

public class CalculatorTest {

	@Test
	public void performOperation_test() throws Exception {
		// 1. 獲取類的 Class 物件
		Class<?> cls = Calculator.class;

		// 2. 獲取私有方法的 Method 物件
		Method method = cls.getDeclaredMethod("calculate", String.class);

		// 3. 設置私有方法的訪問權限，使其可以被訪問
		method.setAccessible(true);

		// 4. 調用私有方法
		Calculator obj = new Calculator();
		double result = (double) method.invoke(obj, "3+4*2");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(11.0, result, 0.0000000000001); // 這裡的第三個參數是一個 delta，用於比較兩個 double 值的相等性

		result = (double) method.invoke(obj, "(1+1)*2");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(4.0, result, 0.0000000000001);

		result = (double) method.invoke(obj, "(1+1)*(1+1)");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(4.0, result, 0.0000000000001);

		result = (double) method.invoke(obj, "(1+1)^2");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(4.0, result, 0.0000000000001);

		result = (double) method.invoke(obj, "(12*3/(-2))*(3+5)/2");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(-72.0, result, 0.0000000000001);

		result = (double) method.invoke(obj, "pi*1");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(3.141592653589793, result, 0.0000000000001);

		result = (double) method.invoke(obj, "e*1");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(2.718281828459, result, 0.0000000000001);

		result = (double) method.invoke(obj, "2-1");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(1.0, result, 0.0000000000001);

		result = (double) method.invoke(obj, "-2-1");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(-3.0, result, 0.0000000000001);

		result = (double) method.invoke(obj, "2*(-9)^2");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(162.0, result, 0.0000000000001);

		result = (double) method.invoke(obj, "(1 + 1)*5");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(10.0, result, 0.0000000000001);

		result = (double) method.invoke(obj, "0.3*5");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(1.5, result, 0.0000000000001);

		result = (double) method.invoke(obj, "2^3");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(8.0, result, 0.0000000000001);

		result = (double) method.invoke(obj, "1+.25*2");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(1.5, result, 0.0000000000001);

		result = (double) method.invoke(obj, "0+123.+2*5");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(133.0, result, 0.0000000000001);

		result = (double) method.invoke(obj, "(2 + 3) * (4 - 2)");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(10, result, 0.0000000000001);

		result = (double) method.invoke(obj, "-.3");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(-0.3, result, 0.0000000000001);

		result = (double) method.invoke(obj, "1-1-1");

		// 在這裡進行斷言，驗證結果是否符合預期
		assertEquals(-1.0, result, 0.0000000000001);


	}

	@Test
	public void performOperation_test_devidedByZero() throws Exception {

		// 1. 獲取類的 Class 物件
		Class<?> cls = Calculator.class;

		// 2. 獲取私有方法的 Method 物件
		Method method = cls.getDeclaredMethod("calculate", String.class);

		// 3. 設置私有方法的訪問權限，使其可以被訪問
		method.setAccessible(true);

		// 4. 調用私有方法，JUnit 會自動捕獲異常
		Calculator obj = new Calculator();
		try {
			double result = (double) method.invoke(obj, "1/0");
		} catch (InvocationTargetException e) {
			// 斷言捕獲的異常是否為 ArithmeticException
			assertEquals(ArithmeticException.class, e.getCause().getClass());
			// 斷言捕獲的異常訊息是否正確
			assertEquals("除數不能為零", e.getCause().getMessage());
		}
	}

	@Test
	public void performOperation_test_wrongInputOperator() throws Exception {

		// 1. 獲取類的 Class 物件
		Class<?> cls = Calculator.class;

		// 2. 獲取私有方法的 Method 物件
		Method method = cls.getDeclaredMethod("calculate", String.class);

		// 3. 設置私有方法的訪問權限，使其可以被訪問
		method.setAccessible(true);

		// 4. 調用私有方法，JUnit 會自動捕獲異常
		Calculator obj = new Calculator();
		try {
			double result = (double) method.invoke(obj, "1[0");
		} catch (InvocationTargetException e) {
			// 斷言捕獲的異常是否為 ArithmeticException
			assertEquals(ArithmeticException.class, e.getCause().getClass());
			// 斷言捕獲的異常訊息是否正確
			assertEquals("Wrong inputed operator", e.getCause().getMessage());
		}
	}

	@Test
	public void performOperation_test_leftParenthessNotFound() throws Exception {

		// 1. 獲取類的 Class 物件
		Class<?> cls = Calculator.class;

		// 2. 獲取私有方法的 Method 物件
		Method method = cls.getDeclaredMethod("calculate", String.class);

		// 3. 設置私有方法的訪問權限，使其可以被訪問
		method.setAccessible(true);

		// 4. 調用私有方法，JUnit 會自動捕獲異常
		Calculator obj = new Calculator();
		try {
			double result = (double) method.invoke(obj, "(1+2)*3)");
		} catch (InvocationTargetException e) {
			// 斷言捕獲的異常是否為 ArithmeticException
			assertEquals(EmptyStackException.class, e.getCause().getClass());

		}
	}

	@Test
	public void performOperation_test_rightParenthessNotFound() throws Exception {

		// 1. 獲取類的 Class 物件
		Class<?> cls = Calculator.class;

		// 2. 獲取私有方法的 Method 物件
		Method method = cls.getDeclaredMethod("calculate", String.class);

		// 3. 設置私有方法的訪問權限，使其可以被訪問
		method.setAccessible(true);

		// 4. 調用私有方法，JUnit 會自動捕獲異常
		Calculator obj = new Calculator();
		try {
			double result = (double) method.invoke(obj, "(3*(1+2)");
		} catch (InvocationTargetException e) {
			// 斷言捕獲的異常是否為 ArithmeticException
			assertEquals(EmptyStackException.class, e.getCause().getClass());

		}
	}


	@Test
	public void testExecuteMain() throws Exception {

		// 準備輸入
        String input = "2 + 3 * 5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // 準備輸出的容器
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // 呼叫你的主程式或方法
        Calculator.main(new String[0]);

        // 斷言輸出是否符合預期
        assertEquals("請輸入表達式："+ System.lineSeparator()+ "結果：17.0", outContent.toString().trim());
	}

	@Test
	public void test_fail_ExecuteMain() throws Exception {

		// 準備輸入
        String input = "1/0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // 準備輸出的容器
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // 呼叫你的主程式或方法
        Calculator.main(new String[0]);

        // 斷言輸出是否符合預期
        assertEquals("請輸入表達式："+ System.lineSeparator()+ "錯誤：除數不能為零", outContent.toString().trim());
	}

}
