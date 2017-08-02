set terminal png

unset key
set title "Ballistic Deposition Saturation Value vs Lattice Length"
set xlabel "Lattice Length"
set ylabel "Saturation Value"

set logscale xy

f(x) = A*x**b

set output "problem2.png"
fit f(x) "problem2.dat" via A, b
plot "problem2.dat" pt 7 ps .5, f(x)