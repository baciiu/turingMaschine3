package ab3.impl.Nachnamen;

import java.util.*;

public class TuringMachine implements ab3.TuringMachine {

    private Set<Character> alphabet;
    private int numberOfStates;
    private int numberOfTapes;
    private boolean isHalt = false;
    private boolean isError = false;
    private List<TapeContent> tapeContents = new ArrayList<>();
    private List<Transition> transitions = new ArrayList<>();
    private int currentState;
    private int initialState;
    private int haltingState;
    private String content;


    @Override
    public void reset() {
        haltingState = 0;
        isHalt = false;
        isError = false;
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


        if ( (toState == fromState ) && ( move == Movement.Stay ) && (read == write)){
            throw new IllegalArgumentException();
        }

        if ( fromState == haltingState ){
            throw new IllegalArgumentException();
        }

        if( read != null && write != null && ( !(getAlphabet().contains(read) || getAlphabet().contains(write)))) {
            throw new IllegalArgumentException();
        }

        if( ( fromState > getNumberOfStates() ) || ( toState > getNumberOfStates() ) || ( fromState < 0 ) || ( toState < 0 )){
            throw new IllegalArgumentException();
        }

        if( numberOfTapes > 1 ){
            throw new IllegalArgumentException();
        }

        Transition transition = new Transition(fromState,new Character[]{read},toState,new Character[]{write},new Movement[]{move});

        if (!isDeterministicMultipleTapes(transition)){
            throw new IllegalArgumentException();
        }

        transitions.add(transition);
    }

    @Override
    public void addTransition(int fromState, Character[] read, int toState, Character[] write, Movement[] move) throws IllegalArgumentException {

        if ( fromState == haltingState && toState == haltingState ){
            throw new IllegalArgumentException();
        }

        if( !isValid(read) || !isValid(write)) {
            throw new IllegalArgumentException();
        }

        // check if the state doesn't exist
        if( ( fromState > getNumberOfStates() ) || ( toState > getNumberOfStates() ) || ( fromState < 0 ) || ( toState < 0 )){
            throw new IllegalArgumentException();
        }

        Transition transition = new Transition(fromState,read,toState,write,move);

        if (!isDeterministicMultipleTapes(transition)){
            throw new IllegalArgumentException();
        }

        transitions.add(transition);


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

        if ( ( haltingState > numStates ) || ( haltingState < 0 ) || (numStates == haltingState) || numStates == 0 ){
            throw new IllegalArgumentException();
        }else{
            this.numberOfStates = numStates;
        }
    }

    @Override
    public void setNumberOfTapes(int numTapes) throws IllegalArgumentException {

        if (numTapes < 1){
            throw new IllegalArgumentException();
        }else{
            this.numberOfTapes = numTapes;
            for (int i = 0 ; i < numTapes; i++){
                tapeContents.add(new TapeContent(new Character[0],null,new Character[0]));
            }
        }
    }

    @Override
    public void setHaltingState(int haltingState) throws IllegalArgumentException {

        if (haltingState != 0 ){
            throw new IllegalArgumentException();
        }

        this.haltingState = haltingState;

    }

    @Override
    public void setInitialState(int initialState) throws IllegalArgumentException {
       /* if (initialState < 0){
            throw new IllegalArgumentException();
        }
        this.initialState = initialState;*/

        if ( ( getNumberOfStates() < initialState ) || initialState < 0 ){
            throw new IllegalArgumentException();
        }else{
            this.currentState = initialState;
        }
    }

    @Override
    public void setInput(String content) {
        //this.content = content;
        Character[] left = new Character[0];
        Character[] right = new Character[0];
        Character below = null;

        if (content.length() == 1){
            below = content.charAt(0);

        }else if (content.length() > 1){

            below = content.charAt(0);
            right = new Character[content.length()-1];

            for (int i = 0; i < content.length()-1 ; i++) {
                right[i] = content.charAt( i+1 );
            }
        }
        tapeContents.set(0,new TapeContent(left,below,right));

        if (numberOfTapes > 1){
            for ( int i = 1 ; i < numberOfTapes; i++) {
                tapeContents.set(i,new TapeContent(new Character[]{},null,new Character[]{}));
            }
        }

    }

    @Override
    public void doNextStep() throws IllegalStateException {

        if ( isInErrorState() || isInHaltingState() ) {
            throw new IllegalStateException();
        }

        Transition transition = null;
        Map<Character, Transition> transitionMap = new HashMap<>();

        for (Transition t : transitions ) {

            if (t.getFromState() == haltingState){
                throw new IllegalStateException();
            }

            if (t.getFromState() == getCurrentState()) {
                transitionMap.put(t.getRead()[0], t);
            }
        }
        if (transitionMap.size() != 0 ) {

            for (int i = 0; i < numberOfTapes ; i++) {

                transition = transitionMap.get(tapeContents.get(i).getBelowHead());
                if (transition != null) {
                    tapeContents.set(i, new TapeContent(tapeContents.get(i).getLeftOfHead(), transition.getWrite()[0], tapeContents.get(i).getRightOfHead()));
                    move(transition,i);
                    setCurrentState(transition.getToState());
                    if ( transition.getToState() == haltingState){
                        isHalt = true;
                    }


                }else{
                    isError = true;
                }
            }
            if (transition == null){
                isError = true;
            }
        }else{
            isHalt = true;
            throw new IllegalStateException();
        }

    }

    private void setCurrentState(int toState) {
        currentState = toState;
    }

    private void move( Transition transition,int tape){
        Character[] newRightOfHead = tapeContents.get(0).getRightOfHead();
        Character newBelowHead ;
        Character[] newLeftOfHead = tapeContents.get(0).getLeftOfHead();;
        Movement movement = transition.getMove()[0];

        switch (movement) {
            case Left -> {
                if (newLeftOfHead != null) {

                    if (isEmpty(tapeContents.get(tape).getLeftOfHead())){
                        newBelowHead = null;
                    }else{
                        newBelowHead = tapeContents.get(tape).getLeftOfHead()[tapeContents.get(tape).getLeftOfHead().length - 1];
                    }
                    newLeftOfHead = Arrays.copyOf( tapeContents.get(tape).getLeftOfHead(),  tapeContents.get(tape).getLeftOfHead().length);

                    if (tapeContents.get(tape).getBelowHead() != null || tapeContents.get(tape).getRightOfHead().length != 0) {
                        newRightOfHead = new Character[tapeContents.get(tape).getRightOfHead().length + 1];

                        if (newRightOfHead.length > 1) {
                            System.arraycopy(tapeContents.get(tape).getRightOfHead(), 0, newRightOfHead, 1, tapeContents.get(tape).getRightOfHead().length);
                        }
                        newRightOfHead[0] = tapeContents.get(tape).getBelowHead();
                    }
                    tapeContents.set(tape,new TapeContent(newLeftOfHead,newBelowHead,newRightOfHead));

                }
            }
            case Right -> {
                if (tapeContents.get(tape).getRightOfHead().length > 0) {
                    newBelowHead = tapeContents.get(tape).getRightOfHead()[0];
                    newRightOfHead = Arrays.copyOfRange(tapeContents.get(tape).getRightOfHead(), 1, tapeContents.get(tape).getRightOfHead().length);

                } else {
                    newBelowHead = null;
                }
                newLeftOfHead = Arrays.copyOf(tapeContents.get(tape).getLeftOfHead(), tapeContents.get(tape).getLeftOfHead().length + 1);

                newLeftOfHead[tapeContents.get(tape).getLeftOfHead().length] = tapeContents.get(tape).getBelowHead();

                if ( isEmpty(newLeftOfHead)){
                    newLeftOfHead = new Character[0];
                }
                tapeContents.set(tape,new TapeContent(newLeftOfHead,newBelowHead,newRightOfHead));
            }
            case Stay -> {}
        }

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
        if ( isInErrorState()) {
            return null;
        }
        return tapeContents;
    }

    @Override
    public TapeContent getTapeContent(int tape) {
        if (isInErrorState()){
            return null;
        }
        return tapeContents.get(tape);
    }
    private boolean isValid( Character[] array){

        for (Character character:array ) {
            if (character != null  && !getAlphabet().contains(character)){
                return false;
            }
        }
        return true;
    }
    private boolean isDeterministicMultipleTapes( Transition t){

        for ( Transition transition : transitions) {

            if ( (transition.getFromState() == t.getFromState()) && ( Arrays.equals(transition.getRead(),t.getRead()))) {
                return false;
            }
        }
        return true;
    }

    private boolean isEmpty( Character[] array){

        for (Character character : array) {
            if (character != null){
                return false;
            }
        }
        return true;
    }
}
