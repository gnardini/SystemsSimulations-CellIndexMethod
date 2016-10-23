function result = plotCaudal(file_name)

x = load(file_name);

h = zeros(length(x), 1);

for i = 2:length(x)
  h(i) = (x(i) - x(i-1)) / 0.1;
endfor

t = 1:length(h);
t = t .* 0.1;

plot(t,h)

title ("Caudal");

ylabel ("Q (1/s)");
xlabel ("Tiempo (s)");

fontsize=20;
set([gca; findall(gca, 'Type','text')], 'FontSize', fontsize);
set([gca; findall(gca, 'Type','line')], 'linewidth', 3);
hx=legend('example');set(hx, "fontsize", fontsize)

endfunction