package calculator;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class CalendarPrinter {
	 public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);

	        System.out.print("請輸入西元年份：");
	        int year = scanner.nextInt();

	        System.out.print("請輸入月份（1-12）：");
	        int month = scanner.nextInt();

	        printCalendar(year, month);

	        scanner.close();
	    }

	    private static void printCalendar(int year, int month) {
	        Calendar calendar = new GregorianCalendar(year, month - 1, 1);
	        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

	        System.out.println("日\t一\t二\t三\t四\t五\t六");

	        // 打印第一行前的空格
	        for (int i = 1; i < dayOfWeek; i++) {
	            System.out.print("\t");
	        }

	        for (int day = 1; day <= daysInMonth; day++) {
	            System.out.print(day + "\t");

	            // 換行處理
	            if ((day + dayOfWeek - 1) % 7 == 0) {
	                System.out.println();
	            }
	        }
	    }

}
