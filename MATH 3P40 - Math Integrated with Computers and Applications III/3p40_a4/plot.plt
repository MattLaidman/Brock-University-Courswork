set xlabel "p"
set ylabel "{/Symbol Q}(p)"
set title "{/Symbol Q}(p) vs p"

set xrange [-0.1:1.1]
set yrange [-0.1:1.1]

unset key

b = 1
a = 1
h = 0.6
f(x) =  x > h ? a*(x-h)**b : 0

fit [0.4:0.9] f(x) "problem2.dat" via a,b,h

plot "problem2.dat" ps 0.5 pt 7