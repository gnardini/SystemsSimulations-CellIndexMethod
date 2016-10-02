T = load('files/positions_over_time.txt');
tierra_x = T(:,1);
tierra_y = T(:,2);
marte_x = T(:,3);
marte_y = T(:,4);
nave_x = T(:,5);
nave_y = T(:,6);

plot(tierra_x, tierra_y, "linewidth", 3, "color", "g", marte_x, marte_y, "linewidth", 3, "color", "r", nave_x, nave_y, "linewidth", 3, "color", "b", [0], [0], "*", "linewidth", 20, "color", "y")

font_size = 16;
fig=figure(1);
FN = findall(fig,'-property','FontName');
set(FN,'FontName','/usr/share/fonts/dejavu/DejaVuSerifCondensed.ttf');
FS = findall(fig,'-property','FontSize');
set(FS,'FontSize',font_size);

xlabel ("X(m)");
ylabel ("Y(m)");

title("Posición con respecto al sol");