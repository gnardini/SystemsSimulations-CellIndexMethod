function result = temperatures(file_name)
  hold all;

  x = load(file_name);

  title("Variación de la temperatura: 401 particulas")

  xlabel ("Modulo de la velocidad máxima (m/s)");
  ylabel ("Temperatura(J)");


  # plot(hx, PDF, 'o')
  semilogy([0.1 0.2 0.3 0.4 0.5 0.6 0.7 0.8], x, 'o');

  hold off

  result = x;
endfunction%