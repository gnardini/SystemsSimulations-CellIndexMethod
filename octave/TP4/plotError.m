function result = plotError(file_name)
  hold all;

  x = load(file_name);

  semilogy(x,'o','LineWidth',4);

  xlabel ("Tiempo máximo del oscilador armónico(s)");
  ylabel ("Error");
  axis([1 length(x)]);
  set(gca,'XTick', 0:1:length(x))

  font_size = 16;

  fig=figure(1);
  FN = findall(fig,'-property','FontName');
  set(FN,'FontName','/usr/share/fonts/dejavu/DejaVuSerifCondensed.ttf');
  FS = findall(fig,'-property','FontSize');
  set(FS,'FontSize',font_size);

  hold off

  result = x;
endfunction