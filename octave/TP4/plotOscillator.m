function result = plotOscillator(file_name)
x0= 0.025; % m
v0= 0.000; % m/s
#t = linspace(0,60,1000);
m = 1;     % kg
c = 0.1;   % N*sec/meter
k = 2.5;   % N/m

font_size = 13;

omega_n = sqrt(k/m);
zeta    = 0.5*c/m/omega_n;

x = load(file_name);

h=figure(1);

t = 1:length(x);

t = t .* 0.0001;



plot(t,x,'r-','LineWidth',4);
axis([0 5])
grid on;
FN = findall(h,'-property','FontName');
set(FN,'FontName','/usr/share/fonts/dejavu/DejaVuSerifCondensed.ttf');
FS = findall(h,'-property','FontSize');
set(FS,'FontSize',font_size);

#title(["Response of an Underdamped Single Degree of\n"...
#      "Freedom System Subjected to an Initial Excitation"],...
#      'FontName','/usr/share/fonts/dejavu/DejaVuSerif-Italic.ttf',...
#      'FontSize',font_size);

xlabel('Tiempo (segundos)','FontSize',font_size);
ylabel('Desplazamiento (metros)','FontSize',font_size);


#print(h,'-dpng','-color','vib_plt4.png');

endfunction