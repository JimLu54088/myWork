package calculatorTest;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.Test;

import calculator.Calculator;

public class CalculatorTest {


	@Test
public void performOperation_test() throws Exception {// 1. 獲取類的 Class 物件
        Class<?> cls = Calculator.class;

        // 2. 獲取私有方法的 Method 物件
        Method method = cls.getDeclaredMethod("calculate", String.class);

        // 3. 設置私有方法的訪問權限，使其可以被訪問
        method.setAccessible(true);

        // 4. 調用私有方法
        Calculator obj = new Calculator();
        double result = (double) method.invoke(obj, "3+4*2");

        // 在這裡進行斷言，驗證結果是否符合預期
        assertEquals(11.0, result, 0.0000001); // 這裡的第三個參數是一個 delta，用於比較兩個 double 值的相等性

        result = (double) method.invoke(obj, "(1+1)*2");

        // 在這裡進行斷言，驗證結果是否符合預期
        assertEquals(4.0, result, 0.0000001);

        result = (double) method.invoke(obj, "(1+1)*(1+1)");

        // 在這裡進行斷言，驗證結果是否符合預期
        assertEquals(4.0, result, 0.0000001);

        result = (double) method.invoke(obj, "(1+1)^2");

        // 在這裡進行斷言，驗證結果是否符合預期
        assertEquals(4.0, result, 0.0000001);

        result = (double) method.invoke(obj, "(12*3/(-2))*(3+5)/2");

        // 在這裡進行斷言，驗證結果是否符合預期
        assertEquals(-72.0, result, 0.0000001);

        result = (double) method.invoke(obj, "pi*1");

        // 在這裡進行斷言，驗證結果是否符合預期
        assertEquals(3.141592653589793, result, 0.0000001);

        result = (double) method.invoke(obj, "e*1");

        // 在這裡進行斷言，驗證結果是否符合預期
        assertEquals(2.718281828459, result, 0.0000001);



    }


}
