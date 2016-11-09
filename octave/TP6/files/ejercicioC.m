samples = 5;

h = getData(2.2, samples);

# subplot(2,1,1)

 #hold all;

#Q = getCaudal(h(:,2));
#plot(Q(:,1), Q(:,2))


# hold off;


for i = 1 : 200
  row = h(i,:);
  y(i) = mean(row);
endfor

Q = getCaudal(y, 10);
plot(Q(:,1), Q(:,2))
ylabel ("Caudal [1/s]");
xlabel ("Tiempo [s]");