package ab3.impl.Nachnamen;

import java.util.List;
import java.util.Set;

public class TuringMachine implements ab3.TuringMachine {

    private int getCurrentState;
    private Set<Character> alphabet;
    private int numberOfStates;
    private int numberOfTapes;
    private boolean isHalt = false;
    private boolean isError = false;
    private List<TapeContent> tapeContents;


    public TuringMachine(){

    }

    @Override
    public void reset() {

    }

    @Override
    public int getCurrentState() throws IllegalStateException {
        return 0;
    }

    @Override
    public void setAlphabet(Set<Character> alphabet) throws IllegalArgumentException {

    }

    @Override
    public Set<Character> getAlphabet() {
        return null;
    }

    @Override
    public void addTransition(int fromState, Character read, int toState, Character write, Movement move) throws IllegalArgumentException {

    }

    @Override
    public void addTransition(int fromState, Character[] read, int toState, Character[] write, Movement[] move) throws IllegalArgumentException {

    }

    @Override
    public int getNumberOfStates() {
        return 0;
    }

    @Override
    public int getNumberOfTapes() {
        return 0;
    }

    @Override
    public void setNumberOfStates(int numStates) throws IllegalArgumentException {

    }

    @Override
    public void setNumberOfTapes(int numTapes) throws IllegalArgumentException {

    }

    @Override
    public void setHaltingState(int haltingState) throws IllegalArgumentException {

    }

    @Override
    public void setInitialState(int initialState) throws IllegalArgumentException {

    }

    @Override
    public void setInput(String content) {

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
        return null;
    }

    @Override
    public TapeContent getTapeContent(int tape) {
        return null;
    }
}
