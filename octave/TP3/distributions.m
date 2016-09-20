function result = distributions(file_name)
  hold all;

  x = load(file_name);

  [hy, hx] = hist(x);
  title ("Distribución de tiempos de colisión");

  xlabel ("Tiempo(s)");
  ylabel ("Colisión");


  PDF = hy./(sum(hy)*(hx(2) - hx(1)));
  semilogy(hx,PDF, 'o');

  hold off

  result = x;
endfunction