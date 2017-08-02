set logscale x
set logscale y
set title "Rule 18 Number of Pairs Over Time"
set xlabel "Iteration"
set ylabel "Number of Pairs"
unset key

f(x)=A*(x**(-m))
A=10000
m=0.5

fit [500:] f(x) "a2_q1.dat" via A, m
plot "a2_q1.dat" ps .1 pt 1, f(x)