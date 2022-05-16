package com.codestates.seb.electricityBill;
import java.util.Scanner;

public class ElectricityBill {
  public static void main(String[] args) {
    System.out.println("=".repeat(25));
    System.out.println(" 주택용 전기요금(저압) 계산기 ");
    System.out.println("=".repeat(25));

    //TODO:
    // 1, 100 이하인 경우 전력당 60.7 원이 부가됩니다. (사용한 전력 100 kWh 이하일 경)
    // 2, 100 이하인 경우 전력당 60.7원 부과, 100 초과인 경우 125.9원 부과 (사용한 전력이 100 kWh 초과일 경우)

    Scanner input = new Scanner(System.in);
    while (true) {
      try {

        double e1; //전력량 입력
        double result; //전기요금
        System.out.println("사용한 전력량을 입력하세요"); //전력량 입력문구 출력
        e1 = input.nextDouble();  //Double 타입으로 e1값을 입력받음

        if (e1 <= 100 && e1 > 0) {
          result = Math.floor((e1 * 60.7) / 10) * 10; // 1원단위 절삭
          System.out.println("사용한 전력량은 " + e1 + "kWh 이고" + " 전기요금은" + result + "입니다.");
        }
        else if (e1 > 100 && e1<=200) {
          result = Math.floor((100 * 60.7 + (e1 - 100) * 125.9) / 10) * 10; // 1원단위 절삭

          System.out.println("사용한 전력량은 " + e1 + "kWh 이고" + " 전기요금은" + result + "입니다.");
        }
        else if (e1 > 200 && e1<=300) {
          result = Math.floor(((100 * 60.7 + 100 * 125.9 + (e1-200) * 187.9))/10) * 10; //1원단위 절삭
          System.out.println("사용한 전력량은 " + e1 + "kWh 이고" + " 전기요금은" + result + "입니다.");
        }
        else if (e1 > 300 && e1 <= 400) {
          result = Math.floor(((100 * 60.7 + 100 * 125.9 + 100 * 187.9 + (e1-300)*280.6)/10) * 10); //1원단위 절삭
          System.out.println("사용한 전력량은 " + e1 + "kWh 이고" + " 전기요금은" + result + "입니다.");
        }
        else if (e1 > 400 && e1 <= 500) {
          result = Math.floor(((100 * 60.7 + 100 * 125.9 + 100 * 187.9 + 100 * 280.6 + (e1-400)*417.7)/10)*10); //1원단위 절삭
          System.out.println("사용한 전력량은 " + e1 + "kWh 이고" + " 전기요금은" + result + "입니다.");
        }
        else if (e1>500) {
          result = Math.floor(((100 * 60.7 + 100 * 125.9 + 100 * 187.9 + 100 * 280.6 + 100 * 417.7 + (e1-500)*670.6)/10)*10); //1원단위 절삭
          System.out.println("사용한 전력량은 " + e1 + "kWh 이고" + " 전기요금은" + result + "입니다.");
        }

      }
      catch (Exception e) {
        System.out.println("잘못된 입력값 입니다.");
        e.printStackTrace();
        break;
      }
    }
  }
}

