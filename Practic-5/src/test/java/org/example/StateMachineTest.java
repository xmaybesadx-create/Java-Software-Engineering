package org.example;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StateMachineTest{

    @ParameterizedTest
    @MethodSource("transitions")
    public void returnsExpectedState (String input, State expected) {
        StateMachine machine = new StateMachine();
        State actual = machine.processString(input); //Повернення стану автомата
        assertEquals(expected, actual);
    }
    static Stream<Arguments> transitions() {
        return Stream.of(Arguments.of("", State.START), Arguments.of("TE", State.STATE_2)
                , Arguments.of("TEST", State.FINAL), Arguments.of("abcTESTabc", State.FINAL)
                , Arguments.of("T", State.STATE_1), Arguments.of("abcTES", State.STATE_3)
                , Arguments.of("т", State.START), Arguments.of("TA", State.START)
                , Arguments.of("TER", State.START), Arguments.of("TESP", State.START)
        );

    }

}
