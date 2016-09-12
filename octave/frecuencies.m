f = load('files/statistics/frequencies.txt');

bar(f(:,1), f(:,2))
text(f(:,1), f(:,2), num2str(f(:,2),'%0.2f'))

xlabel('Cantidad de particulas')
ylabel('Colisiones por unidad de tiempo')
title('Frequencia de colisiones')