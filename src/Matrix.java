public class Matrix {
    private final double[][] m;
    private Matrix inverse;
    private Matrix transposeInverse;
    
    public Matrix() {
        this.m = new double[][] {
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
        };
    }
    
    public Matrix(double[][] m) {
        if (m.length != 4 || m[0].length != 4) {
            throw new IllegalArgumentException("Matrix must be 4x4");
        }
        this.m = m;
    }
    
    public Vector3 transformPoint(Vector3 point) {
        double x = point.x * m[0][0] + point.y * m[0][1] + point.z * m[0][2] + m[0][3];
        double y = point.x * m[1][0] + point.y * m[1][1] + point.z * m[1][2] + m[1][3];
        double z = point.x * m[2][0] + point.y * m[2][1] + point.z * m[2][2] + m[2][3];
        double w = point.x * m[3][0] + point.y * m[3][1] + point.z * m[3][2] + m[3][3];
        
        if (w != 1 && w != 0) {
            x /= w;
            y /= w;
            z /= w;
        }
        return new Vector3(x, y, z);
    }
    
    public Vector3 transformVector(Vector3 vector) {
        double x = vector.x * m[0][0] + vector.y * m[0][1] + vector.z * m[0][2];
        double y = vector.x * m[1][0] + vector.y * m[1][1] + vector.z * m[1][2];
        double z = vector.x * m[2][0] + vector.y * m[2][1] + vector.z * m[2][2];
        return new Vector3(x, y, z);
    }
    
    public Vector3 transformNormal(Vector3 normal) {
        if (transposeInverse == null) {
            computeInverse();
        }
        return transposeInverse.transformVector(normal);
    }
    
    public Matrix multiply(Matrix other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    result[i][j] += this.m[i][k] * other.m[k][j];
                }
            }
        }
        return new Matrix(result);
    }
    
    private void computeInverse() {
        // Calculate inverse matrix 
        double[][] inv = new double[4][4];
        
        // Calculate determinant
        double det = m[0][0] * (m[1][1] * m[2][2] - m[1][2] * m[2][1]) -
                     m[0][1] * (m[1][0] * m[2][2] - m[1][2] * m[2][0]) +
                     m[0][2] * (m[1][0] * m[2][1] - m[1][1] * m[2][0]);
        
        // Avoid division by zero
        if (Math.abs(det) < 1e-8) {
            inverse = new Matrix();
            transposeInverse = new Matrix();
            return;
        }
        
        // Compute adjoint matrix / determinant
        double invDet = 1.0 / det;
        inv[0][0] = (m[1][1] * m[2][2] - m[1][2] * m[2][1]) * invDet;
        inv[0][1] = (m[0][2] * m[2][1] - m[0][1] * m[2][2]) * invDet;
        inv[0][2] = (m[0][1] * m[1][2] - m[0][2] * m[1][1]) * invDet;
        inv[1][0] = (m[1][2] * m[2][0] - m[1][0] * m[2][2]) * invDet;
        inv[1][1] = (m[0][0] * m[2][2] - m[0][2] * m[2][0]) * invDet;
        inv[1][2] = (m[0][2] * m[1][0] - m[0][0] * m[1][2]) * invDet;
        inv[2][0] = (m[1][0] * m[2][1] - m[1][1] * m[2][0]) * invDet;
        inv[2][1] = (m[0][1] * m[2][0] - m[0][0] * m[2][1]) * invDet;
        inv[2][2] = (m[0][0] * m[1][1] - m[0][1] * m[1][0]) * invDet;
        
        // Translation component
        inv[0][3] = -(m[0][3] * inv[0][0] + m[1][3] * inv[0][1] + m[2][3] * inv[0][2]);
        inv[1][3] = -(m[0][3] * inv[1][0] + m[1][3] * inv[1][1] + m[2][3] * inv[1][2]);
        inv[2][3] = -(m[0][3] * inv[2][0] + m[1][3] * inv[2][1] + m[2][3] * inv[2][2]);
        
        // Last row
        inv[3][0] = 0; inv[3][1] = 0; inv[3][2] = 0; inv[3][3] = 1;
        
        inverse = new Matrix(inv);
        
        // Compute transpose of inverse for normal transformation
        double[][] transInv = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                transInv[i][j] = inv[j][i];
            }
        }
        transposeInverse = new Matrix(transInv);
    }
    
    public Matrix getInverse() {
        if (inverse == null) {
            computeInverse();
        }
        return inverse;
    }
    
    public Matrix getTransposeInverse() {
        if (transposeInverse == null) {
            computeInverse();
        }
        return transposeInverse;
    }
    
    // Static factory methods for common transformations
    public static Matrix translate(double tx, double ty, double tz) {
        return new Matrix(new double[][] {
            {1, 0, 0, tx},
            {0, 1, 0, ty},
            {0, 0, 1, tz},
            {0, 0, 0, 1}
        });
    }
    
    public static Matrix scale(double sx, double sy, double sz) {
        return new Matrix(new double[][] {
            {sx, 0, 0, 0},
            {0, sy, 0, 0},
            {0, 0, sz, 0},
            {0, 0, 0, 1}
        });
    }
    
    public static Matrix rotateX(double angleDeg) {
        double angleRad = Math.toRadians(angleDeg);
        double cos = Math.cos(angleRad);
        double sin = Math.sin(angleRad);
        
        return new Matrix(new double[][] {
            {1, 0, 0, 0},
            {0, cos, -sin, 0},
            {0, sin, cos, 0},
            {0, 0, 0, 1}
        });
    }
    
    public static Matrix rotateY(double angleDeg) {
        double angleRad = Math.toRadians(angleDeg);
        double cos = Math.cos(angleRad);
        double sin = Math.sin(angleRad);
        
        return new Matrix(new double[][] {
            {cos, 0, sin, 0},
            {0, 1, 0, 0},
            {-sin, 0, cos, 0},
            {0, 0, 0, 1}
        });
    }
    
    public static Matrix rotateZ(double angleDeg) {
        double angleRad = Math.toRadians(angleDeg);
        double cos = Math.cos(angleRad);
        double sin = Math.sin(angleRad);
        
        return new Matrix(new double[][] {
            {cos, -sin, 0, 0},
            {sin, cos, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
        });
    }
}