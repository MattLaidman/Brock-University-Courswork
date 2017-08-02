package body Random is

  X, Y, Z : Integer;

  -- Default initialization procedure to set default seed values.
  procedure Initialize is
  begin

    Initialize(5, 10000, 3000); -- Default seed values.
  end Initialize;

  -- Optional initialization procedure to set custom seed values.
  procedure Initialize (Seed1 : Integer; Seed2 : Integer; Seed3 : Integer) is
  begin

    X := Seed1;
    Y := Seed2;
    Z := Seed3;
  end Initialize;

  -- Returns a Real value in [0, 1] uniformly distributed.
  function Unif return Float is

    Temp : Float;
  begin
    X := 171*(X mod 177) - Integer(2.0*(Float(X) / 177.0));
    if X < 0 then
      X := X + 30269;
    end if;
    Y := 172*(Y mod 176) - Integer(35.0*(Float(Y) / 176.0));
    if Y < 0 then
      Y := Y + 30307;
    end if;
    Z := 170*(Z mod 178) - Integer(63.0*(Float(Z) / 178.0));
    if Z < 0 then
      Z := Z + 30323;
    end if;
    Temp := (Float(X)/30269.0 + Float(Y)/30307.0 + Float(Z)/30323.0);
    Temp := Temp - Float(Integer(Temp)); -- Enforce (-1, 1)
    if Temp < 0.0 then -- Enforce [0, 1]
      Temp := Temp - (2.0 * Temp);
    end if;
    return Temp;
  end Unif;
end Random;
