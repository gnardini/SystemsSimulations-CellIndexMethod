function result = getCaudal(tiempos_peaton, samples)

  h = 1;
  for i = samples:200
    tminIndex = i - (samples - 1);
    tmaxIndex = i;

    Q(h,1) = tiempos_peaton(floor(tminIndex + (tmaxIndex - tminIndex) / 2));
    Q(h,2) = samples / (tiempos_peaton(tmaxIndex) - tiempos_peaton(tminIndex));
    h++;
  endfor

  result = Q;
endfunction



