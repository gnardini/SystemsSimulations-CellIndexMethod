samples = 5;

h = 1;
for i = 0.8 : 0.2: 6
  A(h,1) = i;
  A(h,2) = getAverageTime(i);
  h++;
endfor

plot(A(:,1), A(:,2))