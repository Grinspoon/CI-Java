package ContinuousIntegration.Selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    private static final Logger logger = Logger.getLogger(Calculator.class.getName());
    private Calculator calculator;
    private final int a = 1;
    private final int b = 2;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void addShouldReturn3() {
        int result = calculator.add(a,b);
        assertEquals(3,result);
    }
    @Test
    void addShouldReturn3_withLogger(){
        logger.info("1- Logger: Starting test addShouldReturn3_withLogger");

        int result = calculator.add(a, b);
        logger.info("2- Logger: Result from calculator: " + result);

        try {
            assertEquals(3, result);
            System.out.println("3- Logger: Success! Expected 3 and got: " + result);
        }
        catch (AssertionError e) {
            logger.info("3- Logger: Test failed! Expected 3 but got: " + result);
            throw e;
        }
    }


    @Test
    void subtractShouldReturnNegative1() {
        int result = calculator.subtract(a,b);
        assertEquals(-1,result);
    }

    @Test
    void multiplyShouldReturn2() {
        int result = calculator.multiply(a,b);
        assertEquals(2,result);
    }

    @Test
    void divideShouldReturn0() {
        int result = calculator.divide(a,b);
        assertEquals(0,result);
    }

    @Test
    void divideByZeroThrowsError(){
        assertThrows(IllegalArgumentException.class, () -> calculator.divide(5,0));
    }



}