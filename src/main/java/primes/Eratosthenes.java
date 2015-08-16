package primes;

public class Eratosthenes {

  public static void main(final String... args) {
    final LongBitSet flags = sieveOfEratosthenes(5_000_000_000L);
    final long maxPrime = flags.previousSetBit(5_000_000_000L);
    System.out.println(maxPrime);
  }

  private static LongBitSet sieveOfEratosthenes(final long max) {
    final long n = max + 1;
    final LongBitSet flags = new LongBitSet(n);
    flags.set(2, max);
    long prime = 2;

    // Cross off
    final long maxPrime = (long) Math.sqrt(max);
    while (prime <= maxPrime) {
      for (long i = prime * prime; i < n; i += prime) {
        flags.set(i, false);
      }

      // Search for the next prime
      prime = flags.nextSetBit(prime + 1);
      if (prime == -1) {
        break;
      }
    }

    return flags;
  }

  private static boolean isPrime(final long candidate) {
    if (candidate < 0) {
      throw new IllegalArgumentException();
    }
    final long n = (long) Math.sqrt(candidate);
    if (candidate < 3 || candidate % 2 == 0) {
      return false;
    }
    for (long i = 3; i <= n; i += 2) {
      if (candidate % i == 0) {
        return false;
      }
    }
    return true;
  }

}
