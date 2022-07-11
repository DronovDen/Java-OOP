package statistics;

public class SymbolCounter {
    private final Character symbol;
    private int frequency;

    public SymbolCounter(Character token) {
        this.symbol = token;
        this.frequency = 1;
    }

    public void increaseFreq() {
        this.frequency++;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public String getSymbol() {
        return this.symbol.toString();
    }

    @Override
    public int hashCode() {
        return this.symbol;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        //ЛУЧШЕ ИСПОЛЬЗОВАТЬ getClass()!!!!
        //instanceof не значит принадлежность к одному классу!
        if (other.getClass() != SymbolCounter.class) {
            return false;
        }

        /*if(!(other instanceof SymbolCounter)){
            return false;
        }*/

        SymbolCounter otherSymb = (SymbolCounter) other;
        if (symbol == otherSymb.symbol) {
            otherSymb.increaseFreq();
            return true;
        }
        return false;
    }

}
