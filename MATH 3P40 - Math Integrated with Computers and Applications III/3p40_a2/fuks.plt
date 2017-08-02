set title "Rule 54 Number of e Vectors Over Time"
set xlabel "Iteration"
set ylabel "Density of e Vectors"
unset key

f(x)=m*x + A
A=3
m=0.15

fit [14:16] f(x) "fuks.dat" via A, m
plot f(x), "fuks.dat" ps .1 pt 1
