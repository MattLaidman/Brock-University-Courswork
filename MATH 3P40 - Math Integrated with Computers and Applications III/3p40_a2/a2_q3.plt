set logscale xy
set title "Elementary Cellular Automaton Rule 30 Density of Occupied Cells Over Time"
set xlabel "Iteration"
set ylabel "Density of Occupied Cells"
unset key

f(x)=A*(x**(m))
A=0.5
m=1

fit [500:5000] f(x) "a2_q3.dat" via A, m
plot "a2_q3.dat" ps .1 pt 1, f(x)