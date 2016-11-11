samples = 4;

h = getData(2.4, samples);
axis([0 200])

subplot(2,1,1)

ylabel ("Tiempo de salida [s]");
xlabel ("Peaton");

hold all;

for i = 1:samples
  plot(h(:,i))
  plot(h(:,i))
endfor

hold off;

subplot(2,1,2)
ejercicioB;
ylabel ("Tiempo de salida [s]");
xlabel ("Peaton");
font_size = 16;
fig=figure(1);
FN = findall(fig,'-property','FontName');
set(FN,'FontName','/usr/share/fonts/dejavu/DejaVuSerifCondensed.ttf');
FS = findall(fig,'-property','FontSize');
set(FS,'FontSize',font_size);