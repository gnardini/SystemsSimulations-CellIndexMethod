pkg load statistics;

x = load('files/statistics/output.txt');

histfit(x);
title ("Frecuencia de colisiones");

xlabel ("Tiempo(s)");
ylabel ("Colisiones");

#text (pi, 0.7, "arbitrary text"); # TODO: This should have the parameters

set(gca,'XTick', min(x):1:max(x))

axis([min(x) max(x)]

#hold on

#plot(x, normpdf(x));

#hold off

# print -dpng "-S400,400" normal.png