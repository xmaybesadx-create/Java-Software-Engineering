package org.example;

public class StateMachine {

    public State processString(String input) {
        State currentState = State.START; // Початковий стан перед читанням рядка

        for (int i = 0;  i < input.length(); i++) {
            char currentChar = input.charAt(i); // Читаємо символ на поточній позиції
             if(currentState == State.START && currentChar == 'T') {
                 currentState = State.STATE_1; //Перехід в новий стан
             }
            else if(currentState == State.STATE_1 && currentChar == 'E') {
                currentState = State.STATE_2;
            }
            else if(currentState == State.STATE_1 && currentChar != 'E') {
                 currentState = State.START;
            }
            else if(currentState == State.STATE_2 && currentChar == 'S') {
                 currentState = State.STATE_3;
            }
            else if(currentState == State.STATE_2 && currentChar != 'S') {
                 currentState = State.START;
            }
            else if(currentState == State.STATE_3 && currentChar == 'T') {
                 currentState = State.FINAL;
            }
            else if(currentState == State.STATE_3 && currentChar != 'T') {
                 currentState = State.START;
            }
        }
        return currentState;
    }
}
