package calculator;



import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @Before
    public void setUp() {
        stringCalculator = new StringCalculator();
    }

    @Test
    public void should_return_0_when_passed_string_is_empty() {
        assertEquals(stringCalculator.add(""),0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_IllegalArgumentException_when_passed_string_is_not_a_number() {
        stringCalculator.add("1,s,3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_IllegalArgumentException_when_passed_string_has_comma_as_a_first_character() {
        stringCalculator.add(",1,2,3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_IllegalArgumentException_when_passed_string_has_duplicated_commas() {
        stringCalculator.add("1,,3");
    }

    @Test
    public void should_return_sum_when_passed_string_has_comma_as_last_character() {
        assertEquals(stringCalculator.add("1,2,3,"),6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_IllegalArgumentException_when_passed_string_has_space_character() {
        stringCalculator.add("1,2, 3");
    }

   

    @Test
    public void should_return_the_same_number_when_it_was_only_one_number_passed() {
        assertEquals(stringCalculator.add("3"),3);
    }

    @Test
    public void should_return_sum_of_every_amount_of_numbers_when_delimiter_is_only_comma() {
        assertEquals(stringCalculator.add("3,2"),5);
        assertEquals(stringCalculator.add("3,2,6,10"),3 + 2 + 6 + 10);
       
    }

    @Test
    public void should_return_sum_of_every_amount_of_numbers_when_delimiter_is_only_new_line() {
        assertEquals(stringCalculator.add("3\n2"),3 + 2);
        assertEquals(stringCalculator.add("3\n2\n6\n10\n100"),3 + 2 + 6 + 10 + 100);
    }

    @Test
    public void should_return_sum_of_every_amount_of_numbers_when_delimiters_are_new_lines_and_commas() {
        assertEquals(stringCalculator.add("3\n2,6\n10"),3 + 2 + 6 + 10);
        assertEquals(stringCalculator.add("3\n2,6\n10,100"),3 + 2 + 6 + 10 + 100);
    }

    @Test
    public void should_return_sum_when_single_custom_delimiter_is_specified() {
        assertEquals(stringCalculator.add("//[;]\n3;2;4"),3 + 2 + 4);
        assertEquals(stringCalculator.add("//[*]\n3*2*4"),3 + 2 + 4);
    }

    @Test
    public void should_return_sum_when_single_type_and_any_length_custom_delimiter_is_specified() {
        assertEquals(stringCalculator.add("//[ddd]\n3ddd2ddd4"),3 + 2 + 4);
        assertEquals(stringCalculator.add("//[***]\n3***2***4"),3 + 2 + 4);
    }

    @Test
    public void should_return_sum_when_multiple_type_and_any_length_custom_delimiters_are_specified() {
        assertEquals(stringCalculator.add("//[ddd][fff]\n3ddd2fff4"),3 + 2 + 4);
        
        assertEquals(stringCalculator.add("//[***][%][$$]\n3***2%4***5$$6%7"),3 + 2 + 4 + 5 + 6 + 7);
    }


    @Test(expected = NumberFormatException.class)
    public void should_throw_NumberFormatException_when_delimiter_specification_is_separated_by_every_char_but_not_new_line() {
        stringCalculator.add("//;\t3;2;4");
    }

    @Test(expected = NumberFormatException.class)
    public void should_throw_NumberFormatException_when_delimiter_specification_is_not_separated_by_new_line() {
        stringCalculator.add("//;3;2;4");
    }

   

    @Test
    public void should_ignore_numbers_bigger_than_1000() {
        assertEquals(stringCalculator.add("2,1001"),2);
    }
}