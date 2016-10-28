function result = plotCaudal(file_name)

x = load(file_name);

h = zeros(length(x), 1);

for i = 2:length(x)
  h(i) = (x(i) - x(i-1)) / 0.5;
endfor

t = 1:length(h);
t = t .* 0.5;

m = mean(h)
M = zeros(length(t), 1);
for i = 1: length(t)
  M(i) = m;
endfor

plot(t, h, t, M, 'r')
title ("Caudal");

ylabel ("Q (1/s)");
xlabel ("Tiempo (s)");

fontsize=16;
set([gca; findall(gca, 'Type','text')], 'FontSize', fontsize);
set([gca; findall(gca, 'Type','line')], 'linewidth', 3);




endfunction