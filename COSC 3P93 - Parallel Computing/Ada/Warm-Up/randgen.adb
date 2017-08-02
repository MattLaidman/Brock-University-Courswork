with Gnat.Io; use Gnat.Io;
with Random; use Random;
procedure RandGen is
  Seed1 : constant Integer := 5;
  Seed2 : constant Integer := 10000;
  Seed3 : constant Integer := 3000;
  X, Y, Z : Integer;
  L : Integer;
begin
  X := Seed1;
  Y := Seed2;
  Z := Seed3;
  L := 1;
  loop
    Put(L);
    Put(" ==> ");
    Put(My_Float'Image(Unif(X, Y, Z)));
    New_Line;
    L := L + 1;
    exit when L = 1000;
  end loop;
end RandGen;
