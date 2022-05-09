package com.codestates.seb.calculator;
import java.util.Scanner;

public class Calculator {
  public static void main(String[] args) {
    System.out.println("===Java Calculator===");

    Scanner input = new Scanner(System.in);
        /*
            요구 사항에 따라 간단한 계산기를 만들어주세요.
            1. 사용자의 입력으로 첫 번째 숫자, 연산자, 두 번째 숫자를 받아야 합니다.
            2. 연산자의 종류는 +, -, *, / 입니다.
            3. 소수점 연산을 수행할 수 있어야 합니다.
            4. 연산 결과를 콘솔에 출력합니다.
        */
    // TODO...
    char operator;
    double num1, num2, result;

    System.out.println("첫번째 숫자를 입력");
    num1 = input.nextDouble();

    System.out.println("기호를 입력");
    operator = input.next().charAt(0);

    System.out.println("두번째 숫자를 입력");
    num2 = input.nextDouble();

    //스위치문 시작
    switch (operator) {
      case '+':
        result = num1 + num2;
        //더하기 출력
        System.out.println(num1 + " + " + num2 + " = " + result + "입니다");
        break;

      case '-':
        result = num1 - num2;
        //빼기 출력
        System.out.println(num1 + " - " + num2 + " = " + result + "입니다");
        break;

      case '*':
        result = num1 * num2;
        //곱하기 출력
        System.out.println(num1 + " * " + num2 + " = " + result + "입니다");
        break;

      case '/':
        result = num1 / num2;
        //나누기 출력
        System.out.println(num1 + " / " + num2 + " = " + result + "입니다");
        break;

      default:
        System.out.println("---Syntax Error---");
    } input.close();
  }
}
