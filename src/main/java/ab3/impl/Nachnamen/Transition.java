package ab3.impl.Nachnamen;

import ab3.TuringMachine;

public class Transition {
    private int fromState;
    private Character[] read;
    private int toState;
    private Character[] write;
    private TuringMachine.Movement[] move;

    public Transition(int fromState, Character[] read, int toState, Character[] write, TuringMachine.Movement[] move) {
        this.fromState = fromState;
        this.read = read;
        this.toState = toState;
        this.write = write;
        this.move = move;
    }

    public int getFromState() {
        return fromState;
    }

    public void setFromState(int fromState) {
        this.fromState = fromState;
    }

    public Character[] getRead() {
        return read;
    }

    public void setRead(Character[] read) {
        this.read = read;
    }

    public int getToState() {
        return toState;
    }

    public void setToState(int toState) {
        this.toState = toState;
    }

    public Character[] getWrite() {
        return write;
    }

    public void setWrite(Character[] write) {
        this.write = write;
    }

    public TuringMachine.Movement[] getMove() {
        return move;
    }

    public void setMove(TuringMachine.Movement[] move) {
        this.move = move;
    }
}
