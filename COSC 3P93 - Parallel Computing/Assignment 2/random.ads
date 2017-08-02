package Random is

  -- One of the must be called prior to calling Unif function.
  procedure Initialize;
  procedure Initialize (Seed1 : Integer; Seed2 : Integer; Seed3 : Integer);

  function Unif return Float;
end Random;
