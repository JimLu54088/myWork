package calculator;

import java.util.Random;
import java.util.Scanner;

public class DiceGame {
    private static final int MIN_BET = 50;
    private static final int MAX_BET = 1000;
    private static final int INITIAL_BALANCE = 5000;

    public static void main(String[] args) {
        int balance = INITIAL_BALANCE;
        Scanner scanner = new Scanner(System.in);

        System.out.println("欢迎参加掷骰子游戏！");
        System.out.println("初始金额: " + balance);

        while (balance > 0) {
            System.out.print("请输入下注金额（50的倍数，最大不能超过1000，输入0退出游戏）: ");
            int bet = scanner.nextInt();

            if (bet == 0) {
                System.out.println("退出游戏。");
                break;
            }

            if (bet < MIN_BET || bet > MAX_BET || bet % MIN_BET != 0) {
                System.out.println("下注金额不符合规定，请重新输入。");
                continue;
            }

            int diceTotal = rollDice();
            boolean isBig = diceTotal > 9 && diceTotal <= 18;

            System.out.println("骰子点数: " + diceTotal);

            if (isBig && bet <= balance) {
                System.out.println("恭喜，你赢了！");
                balance += bet;
            } else if (!isBig && bet <= balance) {
                System.out.println("很遗憾，你输了！");
                balance -= bet;
            } else {
                System.out.println("下注金额超过余额，请重新输入。");
                continue;
            }

            if (balance < MIN_BET) {
                System.out.println("余额不足，最后一次下注金额必须大于等于50。");
                break;
            }

            System.out.println("当前余额: " + balance);
        }

        System.out.println("游戏结束，最终余额: " + balance);
        scanner.close();
    }

    private static int rollDice() {
        Random random = new Random();
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        int dice3 = random.nextInt(6) + 1;

        return dice1 + dice2 + dice3;
    }
}
