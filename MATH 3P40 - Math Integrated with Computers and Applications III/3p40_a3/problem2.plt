set terminal png

unset key
set title "Ballistic Deposition Interface Width Over Time"
set xlabel "Time"
set ylabel "Interface Width"

set logscale xy

f(x) = a

set output "problem2_50.png"
fit [500:] f(x) "50.dat" via a
plot [:1000] "50.dat" pt 1 ps .1, f(x)

set output "problem2_100.png"
fit [750:] f(x) "100.dat" via a
plot [:1500] "100.dat" pt 1 ps .1, f(x)

set output "problem2_200.png"
fit [1125:] f(x) "200.dat" via a
plot [:2250] "200.dat" pt 1 ps .1, f(x)

set output "problem2_400.png"
fit [1687:] f(x) "400.dat" via a
plot [:3375] "400.dat" pt 1 ps .1, f(x)

set output "problem2_800.png"
fit [2531:5063] f(x) "800.dat" via a
plot [:5063] "800.dat" pt 1 ps .1, f(x)

set output "problem2_1600.png"
fit [3797:] f(x) "1600.dat" via a
plot [:7594] "1600.dat" pt 1 ps .1, f(x)

set output "problem2_3200.png"
fit [5695:] f(x) "3200.dat" via a
plot [:11391] "3200.dat" pt 1 ps .1, f(x)

set output "problem2_6400.png"
fit [8543:] f(x) "6400.dat" via a
plot [:17086] "6400.dat" pt 1 ps .1, f(x)