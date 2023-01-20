package ab3.impl.Nachnamen;

import java.util.List;
import java.util.Set;

public class TuringMachine implements ab3.TuringMachine {

    private Set<Character> alphabet;
    private int numberOfStates;
    private int numberOfTapes;
    private boolean isHalt = false;
    private boolean isError = false;
    private List<TapeContent> tapeContents;
    private int currentState;
    private int initialState;
    private int haltingState;
    private String content;


    public TuringMachine(){

    }

    @Override
    public void reset() {
        setNumberOfTapes(numberOfTapes);
        currentState = initialState;
    }

    @Override
    public int getCurrentState() throws IllegalStateException {
        return currentState;
    }

    @Override
    public void setAlphabet(Set<Character> alphabet) throws IllegalArgumentException {
        this.alphabet = alphabet;

    }

    @Override
    public Set<Character> getAlphabet() {
        return alphabet;
    }

    @Override
    public void addTransition(int fromState, Character read, int toState, Character write, Movement move) throws IllegalArgumentException {

    }

    @Override
    public void addTransition(int fromState, Character[] read, int toState, Character[] write, Movement[] move) throws IllegalArgumentException {

    }

    @Override
    public int getNumberOfStates() {
        return numberOfStates;
    }

    @Override
    public int getNumberOfTapes() {
        return numberOfTapes;
    }

    @Override
    public void setNumberOfStates(int numStates) throws IllegalArgumentException {

        if (numStates < 0){
            throw new IllegalArgumentException();
        }
        numberOfStates = numStates;
    }

    @Override
    public void setNumberOfTapes(int numTapes) throws IllegalArgumentException {

        if (numTapes < 0){
            throw new IllegalArgumentException();
        }
        numberOfTapes = numTapes;
    }

    @Override
    public void setHaltingState(int haltingState) throws IllegalArgumentException {
        this.haltingState = haltingState;

    }

    @Override
    public void setInitialState(int initialState) throws IllegalArgumentException {
        if (initialState < 0){
            throw new IllegalArgumentException();
        }
        this.initialState = initialState;
    }

    @Override
    public void setInput(String content) {
        this.content = content;

    }

    @Override
    public void doNextStep() throws IllegalStateException {

    }

    @Override
    public boolean isInHaltingState() {
        return isHalt;
    }

    @Override
    public boolean isInErrorState() {
        return isError;
    }

    @Override
    public List<TapeContent> getTapeContents() {
        return tapeContents;
    }

    @Override
    public TapeContent getTapeContent(int tape) {
        return tapeContents.get(tape);
    }
}
