x = load('files/statistics/timePerCollision_401.txt');

[hy, hx] = hist(x);
title ("Frecuencia de colisiones");

xlabel ("Tiempo(s)");
ylabel ("Colisión");

PDF = hy./(sum(hy)*(hx(2) - hx(1)));
semilogy(hx,PDF, 'o');