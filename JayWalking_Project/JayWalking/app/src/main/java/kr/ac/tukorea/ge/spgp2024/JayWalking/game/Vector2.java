package kr.ac.tukorea.ge.spgp2024.JayWalking.game;

public class Vector2 {
    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // 덧셈 연산
    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    // 뺄셈 연산
    public Vector2 subtract(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    // 곱셈 연산
    public Vector2 multiply(float scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    // 나눗셈 연산
    public Vector2 divide(float scalar) {
        if (scalar == 0) {
            throw new IllegalArgumentException("Divisor cannot be zero.");
        }
        return new Vector2(this.x / scalar, this.y / scalar);
    }

    // 벡터의 크기 계산
    public float magnitude() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    // 벡터를 정규화
    public Vector2 normalize() {
        float mag = magnitude();
        if (mag == 0) {
            throw new ArithmeticException("Cannot normalize zero vector.");
        }
        return divide(mag);
    }

    // 벡터의 내적 계산
    public float dot(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }

    // 두 벡터 사이의 각도 계산
    public float angleBetween(Vector2 other) {
        float dotProduct = dot(other);
        float magProduct = magnitude() * other.magnitude();
        if (magProduct == 0) {
            throw new ArithmeticException("Cannot calculate angle for zero vector.");
        }
        return (float) Math.acos(dotProduct / magProduct);
    }
}
