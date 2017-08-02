set title "Rule 54 Number of e Vectors Over Time"
set xlabel "Iteration"
set ylabel "Density of e Vectors"
set logscale xy
unset key

f(x)=A*(x**(-m))
A=0.01
m=0.15

fit [9150:20000] f(x) "a2_q2.dat" via A, m
plot f(x), "a2_q2.dat" ps .1 pt 1