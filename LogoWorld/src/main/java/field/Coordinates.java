package field;

public class Coordinates {
    public Integer x;
    public Integer y;

    /**
     * Creates coordinates instance
     * @param x X-axis coordinate
     * @param y Y-axis coordinate
     */
    public Coordinates(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sums up the coordinates by X-axis and Y-axis
     *
     * @param coords represents the coordinates that needed to be added to this coordinates
     * @return coordinates of the sum
     */
    public Coordinates add(Coordinates coords) {
        return new Coordinates(this.x + coords.x, this.y + coords.y);
    }

    /**
     * Multiplies this coordinates by integer parameter
     *
     * @param k multiplier
     * @return coordinates of the product
     */
    public Coordinates mul(int k) {
        return new Coordinates(this.x * k, this.y * k);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        Coordinates other = (Coordinates) obj;
        return (this.x == other.x && this.y == other.y);
    }

    public void validate(FieldSize fieldSize) {
        fieldSize.isValid();
        if (this.x < 0) {
            this.x = (this.x % fieldSize.width) + fieldSize.width;
        } else {
            this.x = this.x % fieldSize.width;
        }
        if (this.y < 0) {
            this.y = (this.y % fieldSize.height) + fieldSize.height;
        } else {
            this.y = this.y % fieldSize.height;
        }
    }
}
