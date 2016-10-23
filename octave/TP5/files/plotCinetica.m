function result = plotCinetica(file_name)

x = load(file_name);
plot(x)

title ("Energía Cinética");

ylabel ("K (J)");
xlabel ("Tiempo (s)");

fontsize=20;
set([gca; findall(gca, 'Type','text')], 'FontSize', fontsize);
set([gca; findall(gca, 'Type','line')], 'linewidth', 3);
hx=legend('example');set(hx, "fontsize", fontsize)

endfunction