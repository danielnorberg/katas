Game 1: P(win) = p
Game 2: P(win) = P(three shots) + P(two shots)
               = p^3 + P(hit hit miss) + p(hit miss hit) + P(miss hit hit) +
               = p^3 + p * p * (1-p)   + p * (1-p) * p +   (1-p) * p * p
               = p^3 + 3 * p * p * (1-p)
               = p^3 + 3 * p^2 - 3 * p^3
               = 3 * p^2 - 2 * p^3

p != 0 && p != 1

          Game 1 > Game 2
               p > 3p^2 - 2p^3
               1 > 3p - 2p^2
   2p^2 - 3p + 1 > 0
p^2 - 1.5p + 0.5 > 0                      roots: p = 1, p = 0.5
(p - 1)(p - 0.5) > 0

p < 1 ==> p - 0.5 < 0
      ==> p < 0.5
      ==> Game 1 if p < 0.5
          Game 2 if p > 0.5