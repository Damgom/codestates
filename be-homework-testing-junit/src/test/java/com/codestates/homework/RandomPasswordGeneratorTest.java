package com.codestates.homework;

import com.codestates.helper.RandomPasswordGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class RandomPasswordGeneratorTest {
    @DisplayName("실습 3: 랜덤 패스워드 생성 테스트")
    @Test
    public void generateTest() {
        // TODO 여기에 테스트 케이스를 작성해주세요.
        int numberOfUpper = 2;
        int numberOfLower = 4;
        int numberOfNum = 5;
        int numberOfSpecial = 1;
        int length = numberOfUpper + numberOfLower + numberOfNum + numberOfSpecial;
        String password = RandomPasswordGenerator.generate(numberOfUpper, numberOfLower, numberOfNum, numberOfSpecial);

        assertThat(length).isEqualTo(password.length());

        int upperCount = 0;
        int lowerCount = 0;
        int numCount = 0;
        int specialCount = 0;
        for(int i = 0; i < password.length(); i++){
            if(Character.isUpperCase(password.charAt(i))){
                upperCount++;
            }
            else if(Character.isLowerCase(password.charAt(i))){
                lowerCount++;
            }
            else if(Character.isDigit(password.charAt(i))){
                numCount++;
            }
            else specialCount++;
        }

        assertThat(numberOfUpper).isEqualTo(upperCount);
        assertThat(numberOfLower).isEqualTo(lowerCount);
        assertThat(numberOfNum).isEqualTo(numCount);
        assertThat(numberOfSpecial).isEqualTo(specialCount);
    }
}
