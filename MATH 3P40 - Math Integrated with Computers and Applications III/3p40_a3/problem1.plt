unset key
set title "Ballistic Deposition Interface Width Over Time"
set xlabel "Time"
set ylabel "Interface Width"
set logscale xy

f(x) = A*x**b
fit f(x) "problem1.dat" via A, b

plot "problem1.dat" pt 1 ps .1, f(x)