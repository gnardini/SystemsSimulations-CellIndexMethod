samples = 10;

h = getData(24, samples);

for i = 1 : 200
  row = h(i,:);
  errors(i) = std(row);
  y(i) = mean(row);
endfor

# x = 1:1:200;
errorbar(y,errors)

#semilogyerr(y, errors)

axis([0 200])

