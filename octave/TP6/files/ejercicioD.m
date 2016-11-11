samples = 4;

h = 1;

for i = 0.8 : 0.2: 6
  A(h,1) = i;
  [A(h,2), A(h,3)] = getAverageTime(i, samples);
  h++;
endfor

errorbar(A(:,1),A(:,2), A(:,3), "~.k");

ylabel ("Tiempo de salida [s]");
xlabel ("Peaton");

axis([0 6 0 (max(A(:,2)) + 10)])

font_size = 16;
fig=figure(1);
FN = findall(fig,'-property','FontName');
set(FN,'FontName','/usr/share/fonts/dejavu/DejaVuSerifCondensed.ttf');
FS = findall(fig,'-property','FontSize');
set(FS,'FontSize',font_size);