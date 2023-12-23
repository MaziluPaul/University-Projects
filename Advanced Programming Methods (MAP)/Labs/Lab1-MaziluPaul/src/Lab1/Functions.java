package Lab1;

public class Functions {

    public static boolean is_prime(int x) {
        if (x < 2)
            return false;
        if (x > 2 && x % 2 == 0)
            return false;
        for (int d = 3; d * d <= x; d += 2)
            if (x % d == 0)
                return false;
        return true;
    }

    public static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }
}
