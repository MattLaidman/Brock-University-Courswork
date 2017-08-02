With Random;
with Ada.Text_IO; use Ada.Text_IO;
procedure RandomTest is
begin
  Random.Initialize;
  for I in Integer range 1 .. 100 loop
    Put_Line(Float'Image(Random.Unif));
  end loop;
end RandomTest;
