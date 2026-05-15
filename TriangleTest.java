package triangle;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * ============================================================
 *  Unit Test – Triangle.classify(int a, int b, int c)
 *  White-Box Testing: Statement Coverage & Branch Coverage
 *  Phương pháp: Basis Path / Decision Coverage
 *  Bộ test case dựa trên file testcases_coverage.xlsx
 * ============================================================
 *
 *  Sơ đồ Statements & Branches:
 *  ┌─────────────────────────────────────────────────┐
 *  │ S1: if (a<=0 || b<=0 || c<=0)                  │
 *  │       TRUE  → BR1(T) → S2: return "Invalid"    │
 *  │       FALSE → BR2(F)                            │
 *  │ S3: if (a+b<=c || a+c<=b || b+c<=a)            │
 *  │       TRUE  → BR3(T) → S4: return "Not a..."   │
 *  │       FALSE → BR4(F)                            │
 *  │ S5: if (a==b && b==c)                          │
 *  │       TRUE  → BR5(T) → S6: return "Equil..."   │
 *  │       FALSE → BR6(F)                            │
 *  │ S7: else if (a==b || b==c || a==c)             │
 *  │       TRUE  → BR7(T) → S8: return "Isosceles"  │
 *  │       FALSE → BR8(F) → S9: return "Scalene"    │
 *  └─────────────────────────────────────────────────┘
 */
public class TriangleTest {

    // ================================================================
    // TC01 – Invalid  (BR1 TRUE)
    // Mục tiêu: S1=TRUE → S2; đi qua BR1(T)
    // ================================================================

    /** TC01: a = 0 → a<=0 → TRUE → "Invalid" */
    @Test
    public void TC01_Invalid_a_is_zero() {
        assertEquals("Invalid", Triangle.classify(0, 3, 3));
    }

    /** TC01b: b âm → b<=0 → TRUE → "Invalid" */
    @Test
    public void TC01b_Invalid_negative_b() {
        assertEquals("Invalid", Triangle.classify(3, -1, 3));
    }

    /** TC01c: c âm → c<=0 → TRUE → "Invalid" */
    @Test
    public void TC01c_Invalid_negative_c() {
        assertEquals("Invalid", Triangle.classify(3, 3, -5));
    }

    // ================================================================
    // TC02 – Not a Triangle  (BR2 FALSE, BR3 TRUE)
    // Mục tiêu: S1=FALSE → S3=TRUE → S4; đi qua BR1(F), BR3(T)
    // ================================================================

    /**
     * TC02: (1,2,5) → 1+2=3 ≤ 5 → vi phạm bất đẳng thức tam giác
     * Statements: S1, S3, S4 | Branches: BR2(F), BR3(T)
     */
    @Test
    public void TC02_NotTriangle_sum_less_equal_third() {
        assertEquals("Not a triangle", Triangle.classify(1, 2, 5));
    }

    /**
     * TC02b: (3,4,7) → 3+4=7 ≤ 7 → vi phạm (boundary: dùng <=)
     */
    @Test
    public void TC02b_NotTriangle_boundary_equality() {
        assertEquals("Not a triangle", Triangle.classify(3, 4, 7));
    }

    // ================================================================
    // TC03 – Equilateral  (BR4 FALSE, BR5 TRUE)
    // Mục tiêu: S3=FALSE → S5=TRUE → S6; đi qua BR3(F)=BR4, BR5(T)
    // ================================================================

    /**
     * TC03: (3,3,3) → a==b==c → "Equilateral"
     * Statements: S1,S3,S5,S6 | Branches: BR2(F), BR4(F), BR5(T)
     */
    @Test
    public void TC03_Equilateral_basic() {
        assertEquals("Equilateral", Triangle.classify(3, 3, 3));
    }

    /** TC03b: Equilateral với giá trị lớn */
    @Test
    public void TC03b_Equilateral_large_values() {
        assertEquals("Equilateral", Triangle.classify(100, 100, 100));
    }

    // ================================================================
    // TC04 – Isosceles  (BR6 FALSE, BR7 TRUE)
    // Mục tiêu: S5=FALSE → S7=TRUE → S8; đi qua BR5(F)=BR6, BR7(T)
    // ================================================================

    /**
     * TC04: (3,3,4) → a==b nhưng b!=c → "Isosceles"
     * Statements: S1,S3,S5,S7,S8 | Branches: BR2(F), BR4(F), BR6(F), BR7(T)
     */
    @Test
    public void TC04_Isosceles_ab_equal() {
        assertEquals("Isosceles", Triangle.classify(3, 3, 4));
    }

    /** TC04b: b==c, a!=b → "Isosceles" */
    @Test
    public void TC04b_Isosceles_bc_equal() {
        assertEquals("Isosceles", Triangle.classify(4, 3, 3));
    }

    /** TC04c: a==c, a!=b → "Isosceles" */
    @Test
    public void TC04c_Isosceles_ac_equal() {
        assertEquals("Isosceles", Triangle.classify(5, 3, 5));
    }

    // ================================================================
    // TC05 – Scalene  (BR7 FALSE → BR8)
    // Mục tiêu: S5=FALSE → S7=FALSE → S9; đi qua BR5(F)=BR6, BR8(F)
    // ================================================================

    /**
     * TC05: (3,4,5) → a≠b≠c, thoả tam giác → "Scalene"
     * Statements: S1,S3,S5,S7,S9 | Branches: BR2(F), BR4(F), BR6(F), BR8(F)
     */
    @Test
    public void TC05_Scalene_basic() {
        assertEquals("Scalene", Triangle.classify(3, 4, 5));
    }

    /** TC05b: (5,6,7) → ba cạnh khác nhau → "Scalene" */
    @Test
    public void TC05b_Scalene_another() {
        assertEquals("Scalene", Triangle.classify(5, 6, 7));
    }
}
