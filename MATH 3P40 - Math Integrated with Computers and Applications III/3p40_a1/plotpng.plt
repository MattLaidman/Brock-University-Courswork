set terminal png enhanced
set out "a1q3-3.png"
set xlabel "t"
set ylabel "U(t)/M"
unset key

set autoscale x
set yrange [0:1]

plot "pop.dat" pt 7 ps 0.3, "pop1.dat" pt 7 ps 0.3
