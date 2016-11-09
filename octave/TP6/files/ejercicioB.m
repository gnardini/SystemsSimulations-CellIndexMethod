samples = 5;

h = getData(2.2, samples);

for i = 1 : 200
  row = h(i,:);
  errors(i) = std(row);
  y(i) = mean(row);
endfor

errorbar(y,errors, "~.k");

ylabel ("Tiempo de salida [s]");
xlabel ("Peaton");

axis([0 200])

