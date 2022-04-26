package field;

public class FieldSize {
    public int width;
    public int height;

    /**
     * Creates the descriptor that stores the width and height of field
     * @param w width of new field
     * @param h height of new field
     */
    public FieldSize(int w, int h) {
        this.height = h;
        this.width = w;
    }

    /**
     * Checks if dimension of field is valid (positive height and width values)
     *
     * @throws IllegalArgumentException if at least one dimension is negative or zero
     */
    public void isValid() {
        if (this.height <= 0 || this.width <= 0) {
            throw new IllegalArgumentException();
        }
    }
}
