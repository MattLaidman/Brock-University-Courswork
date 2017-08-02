set terminal png enhanced
set out "a1q3-2.png"
set xlabel "t"
set ylabel "U(t)/M"
unset key

set autoscale x
set yrange [0:1]

U(x) = 1.0/(exp(-lambda*(x-ti))+1)

lambda = 0.15
ti = 30

fit U(x) "pop.dat" via ti, lambda
plot U(x), "pop.dat" pt 7 ps 0.3
