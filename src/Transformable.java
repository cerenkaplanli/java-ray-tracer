public interface Transformable {
    void applyTransformation(Matrix transformation);
    Matrix getTransformation();
    Matrix getInverseTransformation();
}