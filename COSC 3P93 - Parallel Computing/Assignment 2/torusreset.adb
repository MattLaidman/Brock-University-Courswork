with Ada.Text_IO; use Ada.Text_IO;
-- COSC 3P93 - Parallel Computing
-- Assignment 2 - Supercomputer Reset v.2
-- Due: Novemeber 23, 2015
-- Matt Laidman
-- 5199807, ml12ef

package body TorusReset is

  SEED1 : Integer := 37;
  SEED2 : Integer := 3973;
  SEED3 : Integer := 39579;

  InitValue : Integer := -1; -- Initial value so semaphore waits for 2 resets

  -- TorusReset procedure uses a pseudo-random number generator to choose an
  -- arbitrary processor on the torus. It then uses the GetFarthest function to
  -- get the farthest processor in the Torus. Reset calls are then issued to both
  -- Processors, and finally the Wait entry of a Semaphore, Done, is called. When
  -- all processors have been reset, the initial two processors will both call the
  -- Signal entry on Done. At this point, the initial Wait call from the DoReset
  -- procedure is accepted, and execution completes.
  procedure TorusReset is

    PE1, PE2, Dummy : Vector;
    Req : Request;
  begin

    Random.Initialize(SEED1, SEED2, SEED3);

    PE1.X := DimX(Integer(Random.Unif*3.0)); -- Initialize to random
    PE1.Y := DimY(Integer(Random.Unif*4.0));
    PE1.Z := DimZ(Integer(Random.Unif*5.0));

    PE2 := GetFarthest(PE1, 3, 4, 5); -- Get farthest processor

    Dummy.IsReal := False; -- Dummy "from" node

    Req.From := Dummy;
    Req.To := PE1;
    Torus.Torus(PE1.X, PE1.Y, PE1.Z).Reset(Req); -- Issue Resets
    Req.To := PE2;
    Torus.Torus(PE2.X, PE2.Y, PE2.Z).Reset(Req);

    New_Line;
    Put_Line("Seeds Used:" & Integer'Image(SEED1) & "," & Integer'Image(SEED2) & "," & Integer'Image(SEED3));
    Put_Line("(Seeds can be changed by modifying the values in torusreset.adb file)");
    New_Line;
    Put_Line("Initial Resets Called On:");
    Put_Line("PE1:" & Integer'Image((Integer(PE1.X) + 1) + (Integer(PE1.Y) * 3) + (Integer(PE1.Z) * 12)) & " :" & DimX'Image(PE1.X) & "," & DimY'Image(PE1.Y) & "," & DimZ'Image(PE1.Z));
    Put_Line("PE2:" & Integer'Image((Integer(PE2.X) + 1) + (Integer(PE2.Y) * 3) + (Integer(PE2.Z) * 12)) & " :" & DimX'Image(PE2.X) & "," & DimY'Image(PE2.Y) & "," & DimZ'Image(PE2.Z));
    New_Line;
    Put_Line("PNO #ACKS");

    Torus.Done.Wait; -- Wait for Reset to complete

    New_Line;
    Put_Line("Reset Complete!");
  end TorusReset;

  -- Get Farthest function returns the farthest processor as a Vector from a
  -- Torus whose dimensions are (DX, DY, DZ).
  function GetFarthest (From : Vector; DX : Integer; DY : Integer;
    DZ : Integer) return Vector is

    To : Vector;
  begin

    To.X := DimX((Integer(From.X) + Integer(Float(DX)/2.0)) mod 3); -- Minimum path given by
    To.Y := DimY((Integer(From.Y) + Integer(Float(DY)/2.0)) mod 4); -- truncation of dimension/2.
    To.Z := DimZ((Integer(From.Z) + Integer(Float(DZ)/2.0)) mod 5); -- Arbitrarily added (vs subtracted).
    return To;
  end GetFarthest;
end TorusReset;
