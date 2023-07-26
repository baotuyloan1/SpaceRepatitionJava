package com.example;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LearnLanguageProjectApplicationTests {

    Calculator calculator = new Calculator();

    @Test
    void itShouldAddTwoNumbers() {

        // give
        int numberOne = 20;
        int numberTwo = 30;

        //when
        int result = calculator.add(numberOne, numberTwo);

        //then
        int expected = 51;
        assertThat(result).isEqualTo(expected);

    }

    class Calculator{
        int add(int numberOne, int numberTwo){
            return  numberOne+numberTwo;
        }
    }

}
