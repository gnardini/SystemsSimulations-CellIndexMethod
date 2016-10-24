function result = plotCinetica(file_name)

x = load(file_name);

t = 1:length(x);
t = t .* 0.1;

plot(t,x)

title ("Energía Cinética");

ylabel ("K (J)");
xlabel ("Tiempo (s)");

fontsize=16;
set([gca; findall(gca, 'Type','text')], 'FontSize', fontsize);
set([gca; findall(gca, 'Type','line')], 'linewidth', 3);

endfunction