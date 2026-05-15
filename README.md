# Lab: White-Box Testing – Chương trình Triangle

**Môn học:** Kiểm thử phần mềm  
**Phương pháp:** White-Box Testing · Basis Path Method  
**Công cụ:** Java · JUnit 4 · JaCoCo

---

## 1. Chương trình cần kiểm thử

```java
public class Triangle {
    public static String classify(int a, int b, int c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            return "Invalid";
        }
        if (a + b <= c || a + c <= b || b + c <= a) {
            return "Not a triangle";
        }
        if (a == b && b == c) {
            return "Equilateral";
        } else if (a == b || b == c || a == c) {
            return "Isosceles";
        } else {
            return "Scalene";
        }
    }
}
```

Phương thức `classify(int a, int b, int c)` nhận vào 3 cạnh của tam giác và trả về:

| Kết quả | Điều kiện |
|---|---|
| `"Invalid"` | Ít nhất một cạnh ≤ 0 |
| `"Not a triangle"` | Vi phạm bất đẳng thức tam giác |
| `"Equilateral"` | Ba cạnh bằng nhau |
| `"Isosceles"` | Đúng hai cạnh bằng nhau |
| `"Scalene"` | Ba cạnh đều khác nhau |

---

## 2. Phân tích Statements & Branches

### Danh sách Statements

| ST# | Câu lệnh | Điều kiện thực thi |
|---|---|---|
| S1 | `if (a<=0 \|\| b<=0 \|\| c<=0)` | Luôn thực thi |
| S2 | `return "Invalid"` | Khi S1 == TRUE |
| S3 | `if (a+b<=c \|\| a+c<=b \|\| b+c<=a)` | Khi S1 == FALSE |
| S4 | `return "Not a triangle"` | Khi S3 == TRUE |
| S5 | `if (a==b && b==c)` | Khi S1=F, S3=F |
| S6 | `return "Equilateral"` | Khi S5 == TRUE |
| S7 | `else if (a==b \|\| b==c \|\| a==c)` | Khi S5 == FALSE |
| S8 | `return "Isosceles"` | Khi S7 == TRUE |
| S9 | `return "Scalene"` | Khi S7 == FALSE |

### Danh sách Branches

| BR# | Điều kiện | Nhánh | Mô tả |
|---|---|---|---|
| BR1 | `a<=0\|\|b<=0\|\|c<=0` | TRUE | Có ít nhất 1 cạnh ≤ 0 |
| BR2 | `a<=0\|\|b<=0\|\|c<=0` | FALSE | Tất cả cạnh > 0 |
| BR3 | `a+b<=c\|\|a+c<=b\|\|b+c<=a` | TRUE | Không thoả bất đẳng thức |
| BR4 | `a+b<=c\|\|a+c<=b\|\|b+c<=a` | FALSE | Là tam giác hợp lệ |
| BR5 | `a==b && b==c` | TRUE | Ba cạnh bằng nhau |
| BR6 | `a==b && b==c` | FALSE | Không phải tam giác đều |
| BR7 | `a==b\|\|b==c\|\|a==c` | TRUE | Có 2 cạnh bằng nhau |
| BR8 | `a==b\|\|b==c\|\|a==c` | FALSE | Ba cạnh đều khác nhau |

### Đồ thị luồng điều khiển (Control Flow)

```
        START
          │
          ▼
    ┌─────S1─────┐
    │ a<=0||...  │
    └─────────────┘
     TRUE │  │ FALSE
          │  └──────────────────────┐
          ▼                         ▼
    S2: "Invalid"             ┌─────S3─────┐
        EXIT                  │ a+b<=c||.. │
                              └─────────────┘
                         TRUE  │  │ FALSE
                               │  └──────────────────┐
                               ▼                      ▼
                     S4: "Not a triangle"       ┌─────S5─────┐
                         EXIT                   │ a==b&&b==c │
                                                └─────────────┘
                                           TRUE  │  │ FALSE
                                                 │  └────────────────┐
                                                 ▼                   ▼
                                        S6: "Equilateral"    ┌───────S7───────┐
                                            EXIT             │ a==b||b==c||.. │
                                                             └─────────────────┘
                                                        TRUE  │  │ FALSE
                                                              │  │
                                                              ▼  ▼
                                                    S8:"Isosceles" S9:"Scalene"
                                                        EXIT        EXIT
```

---

## 3. Bộ Test Cases

Bộ test được thiết kế theo **Basis Path Method**, đảm bảo mỗi nhánh TRUE/FALSE đều được đi qua ít nhất một lần.

### Test Cases cốt lõi (5 TC tối thiểu)

| TC# | Tên | a | b | c | Expected | Statements | Branches |
|---|---|---|---|---|---|---|---|
| TC01 | Invalid | 0 | 3 | 3 | `"Invalid"` | S1, S2 | BR1(T) |
| TC02 | Not a Triangle | 1 | 2 | 5 | `"Not a triangle"` | S1, S3, S4 | BR2(F), BR3(T) |
| TC03 | Equilateral | 3 | 3 | 3 | `"Equilateral"` | S1,S3,S5,S6 | BR2(F),BR4(F),BR5(T) |
| TC04 | Isosceles | 3 | 3 | 4 | `"Isosceles"` | S1,S3,S5,S7,S8 | BR2(F),BR4(F),BR6(F),BR7(T) |
| TC05 | Scalene | 3 | 4 | 5 | `"Scalene"` | S1,S3,S5,S7,S9 | BR2(F),BR4(F),BR6(F),BR8(F) |

### Test Cases bổ sung (tăng độ tin cậy)

| TC# | Mục đích | a | b | c | Expected |
|---|---|---|---|---|---|
| TC01b | b âm | 3 | -1 | 3 | `"Invalid"` |
| TC01c | c âm | 3 | 3 | -5 | `"Invalid"` |
| TC02b | Boundary: a+b = c | 3 | 4 | 7 | `"Not a triangle"` |
| TC03b | Equilateral giá trị lớn | 100 | 100 | 100 | `"Equilateral"` |
| TC04b | Isosceles: b==c | 4 | 3 | 3 | `"Isosceles"` |
| TC04c | Isosceles: a==c | 5 | 3 | 5 | `"Isosceles"` |
| TC05b | Scalene khác | 5 | 6 | 7 | `"Scalene"` |

---

## 4. Unit Test (JUnit 4)

```java
package triangle;

import org.junit.Test;
import static org.junit.Assert.*;

public class TriangleTest {

    // TC01 – Invalid (BR1 TRUE)
    @Test
    public void TC01_Invalid_a_is_zero() {
        assertEquals("Invalid", Triangle.classify(0, 3, 3));
    }

    @Test
    public void TC01b_Invalid_negative_b() {
        assertEquals("Invalid", Triangle.classify(3, -1, 3));
    }

    @Test
    public void TC01c_Invalid_negative_c() {
        assertEquals("Invalid", Triangle.classify(3, 3, -5));
    }

    // TC02 – Not a Triangle (BR2 FALSE, BR3 TRUE)
    @Test
    public void TC02_NotTriangle_sum_less_equal_third() {
        assertEquals("Not a triangle", Triangle.classify(1, 2, 5));
    }

    @Test
    public void TC02b_NotTriangle_boundary_equality() {
        // 3+4=7 <= 7 → vi phạm bất đẳng thức (toán tử <= không phải <)
        assertEquals("Not a triangle", Triangle.classify(3, 4, 7));
    }

    // TC03 – Equilateral (BR4 FALSE, BR5 TRUE)
    @Test
    public void TC03_Equilateral_basic() {
        assertEquals("Equilateral", Triangle.classify(3, 3, 3));
    }

    @Test
    public void TC03b_Equilateral_large_values() {
        assertEquals("Equilateral", Triangle.classify(100, 100, 100));
    }

    // TC04 – Isosceles (BR6 FALSE, BR7 TRUE)
    @Test
    public void TC04_Isosceles_ab_equal() {
        assertEquals("Isosceles", Triangle.classify(3, 3, 4));
    }

    @Test
    public void TC04b_Isosceles_bc_equal() {
        assertEquals("Isosceles", Triangle.classify(4, 3, 3));
    }

    @Test
    public void TC04c_Isosceles_ac_equal() {
        assertEquals("Isosceles", Triangle.classify(5, 3, 5));
    }

    // TC05 – Scalene (BR7 FALSE → BR8)
    @Test
    public void TC05_Scalene_basic() {
        assertEquals("Scalene", Triangle.classify(3, 4, 5));
    }

    @Test
    public void TC05b_Scalene_another() {
        assertEquals("Scalene", Triangle.classify(5, 6, 7));
    }
}
```

---

## 5. Kết quả chạy kiểm thử & đo Coverage

### Kết quả các test case

```
TC       Input           Expected            Actual              Result
-----------------------------------------------------------------------
TC01     (  0,  3,  3)   Invalid             Invalid             PASS ✅
TC01b    (  3, -1,  3)   Invalid             Invalid             PASS ✅
TC01c    (  3,  3, -5)   Invalid             Invalid             PASS ✅
TC02     (  1,  2,  5)   Not a triangle      Not a triangle      PASS ✅
TC02b    (  3,  4,  7)   Not a triangle      Not a triangle      PASS ✅
TC03     (  3,  3,  3)   Equilateral         Equilateral         PASS ✅
TC03b    (100,100,100)   Equilateral         Equilateral         PASS ✅
TC04     (  3,  3,  4)   Isosceles           Isosceles           PASS ✅
TC04b    (  4,  3,  3)   Isosceles           Isosceles           PASS ✅
TC04c    (  5,  3,  5)   Isosceles           Isosceles           PASS ✅
TC05     (  3,  4,  5)   Scalene             Scalene             PASS ✅
TC05b    (  5,  6,  7)   Scalene             Scalene             PASS ✅

Tổng kết: 12/12 PASS ✅
```

### Statement Coverage Matrix

| TC \ Stmt | S1 | S2 | S3 | S4 | S5 | S6 | S7 | S8 | S9 | Cộng |
|---|---|---|---|---|---|---|---|---|---|---|
| TC01 | ✔ | ✔ | – | – | – | – | – | – | – | 2/9 |
| TC02 | ✔ | – | ✔ | ✔ | – | – | – | – | – | 3/9 |
| TC03 | ✔ | – | ✔ | – | ✔ | ✔ | – | – | – | 4/9 |
| TC04 | ✔ | – | ✔ | – | ✔ | – | ✔ | ✔ | – | 5/9 |
| TC05 | ✔ | – | ✔ | – | ✔ | – | ✔ | – | ✔ | 5/9 |
| **∑ TOTAL** | ✔ | ✔ | ✔ | ✔ | ✔ | ✔ | ✔ | ✔ | ✔ | **9/9** |

> **Statement Coverage = 9/9 = 100%** ✅

### Branch Coverage Matrix

| TC \ Branch | BR1(T) | BR2(F) | BR3(T) | BR4(F) | BR5(T) | BR6(F) | BR7(T) | BR8(F) | n/8 |
|---|---|---|---|---|---|---|---|---|---|
| TC01 | ✔ | – | – | – | – | – | – | – | 1/8 |
| TC02 | – | ✔ | ✔ | – | – | – | – | – | 2/8 |
| TC03 | – | ✔ | – | ✔ | ✔ | – | – | – | 3/8 |
| TC04 | – | ✔ | – | ✔ | – | ✔ | ✔ | – | 4/8 |
| TC05 | – | ✔ | – | ✔ | – | ✔ | – | ✔ | 4/8 |
| **∑ TOTAL** | ✔ | ✔ | ✔ | ✔ | ✔ | ✔ | ✔ | ✔ | **8/8** |

> **Branch Coverage = 8/8 = 100%** ✅

---

## 6. Phân tích kết quả Coverage

### 6.1 Vì sao đạt 100% Statement Coverage?

Mỗi trong 5 TC cốt lõi đi qua một đường thực thi khác nhau, phủ đúng một nhóm statements còn thiếu:

- **TC01** → kích hoạt S1(TRUE) → S2 *(nhánh Invalid)*
- **TC02** → kích hoạt S1(FALSE) → S3(TRUE) → S4 *(nhánh Not a triangle)*
- **TC03** → kích hoạt S3(FALSE) → S5(TRUE) → S6 *(nhánh Equilateral)*
- **TC04** → kích hoạt S5(FALSE) → S7(TRUE) → S8 *(nhánh Isosceles)*
- **TC05** → kích hoạt S7(FALSE) → S9 *(nhánh Scalene)*

Tất cả 9 statements đều được thực thi → **đạt 100% Statement Coverage**.

### 6.2 Vì sao đạt 100% Branch Coverage?

Với mỗi điều kiện `if`, bộ test có ít nhất 1 TC đi qua nhánh TRUE và 1 TC đi qua nhánh FALSE:

| Điều kiện | TC cho TRUE | TC cho FALSE |
|---|---|---|
| `a<=0\|\|b<=0\|\|c<=0` | TC01 | TC02 |
| `a+b<=c\|\|...` | TC02 | TC03 |
| `a==b && b==c` | TC03 | TC04 |
| `a==b\|\|b==c\|\|a==c` | TC04 | TC05 |

Cả 8 nhánh đều được đi qua → **đạt 100% Branch Coverage**.

### 6.3 Quan hệ giữa hai tiêu chí

```
Branch Coverage  ⊃  Statement Coverage
(mạnh hơn)           (yếu hơn)
```

- Đạt **100% Branch Coverage** → tự động đảm bảo **100% Statement Coverage**.  
- Ngược lại **không đúng**: có thể đạt 100% Statement mà vẫn bỏ sót branch.  
  *Ví dụ*: Nếu chỉ có TC01, ta thực thi được S1 và S2 (Statement Coverage một phần) nhưng hoàn toàn bỏ qua BR2(F), BR3, BR4, BR5, BR6, BR7, BR8.

---

## 7. Đề xuất bổ sung Test Case

Bộ test hiện tại đã đạt 100% Statement & Branch Coverage. Tuy nhiên, để tăng độ mạnh hơn nữa, có thể bổ sung:

### 7.1 Kiểm tra giá trị biên (Boundary Value Analysis)

| TC đề xuất | Input | Expected | Mục đích |
|---|---|---|---|
| `(1, 1, 1)` | a=b=c=1 | `"Equilateral"` | Giá trị dương nhỏ nhất hợp lệ |
| `(2, 3, 4)` | a+b > c đúng 1 | `"Scalene"` | Gần biên bất đẳng thức |
| `(1, 1, 2)` | 1+1=2 ≤ 2 | `"Not a triangle"` | Biên chính xác của toán tử `<=` |
| `(0, 0, 0)` | a=b=c=0 | `"Invalid"` | Tất cả bằng 0 |

### 7.2 Kiểm tra điều kiện compound (MC/DC Coverage)

Điều kiện `a<=0 || b<=0 || c<=0` có 3 sub-condition. Để đạt **MC/DC**, cần test từng sub-condition ảnh hưởng độc lập đến kết quả:

| TC | a | b | c | Sub-condition kích hoạt |
|---|---|---|---|---|
| `(-1, 3, 3)` | -1 | 3 | 3 | Chỉ `a<=0` là TRUE |
| `(3, -1, 3)` | 3 | -1 | 3 | Chỉ `b<=0` là TRUE |
| `(3, 3, -1)` | 3 | 3 | -1 | Chỉ `c<=0` là TRUE |

### 7.3 Kiểm tra giới hạn kiểu dữ liệu

```java
// Overflow: a + b có thể tràn số nguyên
Triangle.classify(Integer.MAX_VALUE, 1, 1);   // kết quả không xác định
Triangle.classify(Integer.MAX_VALUE, Integer.MAX_VALUE, 1);
```

> ⚠️ Đây là **lỗ hổng tiềm ẩn** trong code hiện tại — phép `a + b` có thể bị overflow với `int`, dẫn đến kết quả sai. Cần sử dụng `long` hoặc kiểm tra overflow nếu muốn code production-ready.

---

## 8. Cấu trúc dự án

```
triangle/
├── src/
│   ├── main/java/triangle/
│   │   └── Triangle.java          # Lớp cần kiểm thử
│   └── test/java/triangle/
│       └── TriangleTest.java      # Bộ unit test (JUnit 4)
├── pom.xml                        # Maven + JaCoCo plugin
└── README.md
```

### Cấu hình Maven (`pom.xml`)

```xml
<dependencies>
  <dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.2</version>
    <scope>test</scope>
  </dependency>
</dependencies>

<build>
  <plugins>
    <plugin>
      <groupId>org.jacoco</groupId>
      <artifactId>jacoco-maven-plugin</artifactId>
      <version>0.8.11</version>
      <executions>
        <execution><id>prepare-agent</id><goals><goal>prepare-agent</goal></goals></execution>
        <execution><id>report</id><phase>test</phase><goals><goal>report</goal></goals></execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

### Chạy kiểm thử & xem báo cáo coverage

```bash
# Chạy toàn bộ test
mvn test

# Xem báo cáo JaCoCo (HTML)
open target/site/jacoco/index.html
```

---

## 9. Tóm tắt

| Tiêu chí | Kết quả |
|---|---|
| Tổng test cases | 12 (5 cốt lõi + 7 bổ sung) |
| Test PASS | 12 / 12 ✅ |
| Statement Coverage | **100%** (9/9) ✅ |
| Branch Coverage | **100%** (8/8) ✅ |
| Phương pháp | Basis Path / Decision Coverage |
| Công cụ đo coverage | JaCoCo (Maven plugin) |
