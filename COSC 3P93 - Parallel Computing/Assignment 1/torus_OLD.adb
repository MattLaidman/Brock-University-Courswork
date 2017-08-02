with Ada.Text_IO; use Ada.Text_IO;
with Ada.Numerics.Float_Random; use Ada.Numerics.Float_Random;
package body Torus is

  Prism: Processor_Prism;

  procedure DoReset is
  begin
    Prism(0, 0, 0).Reset(0, 0, 0);
  end DoReset;

  task body Processor is
    TempFromX : PX;
    TempFromY : PY;
    TempFromZ : PZ;
    TempToX : PX;
    TempToY : PY;
    TempToZ : PZ;
    SavedX : PX;
    SavedY : PY;
    SavedZ : PZ;
    IsReset : Boolean := False;
    IsFirst : Boolean := False;
    NumAcks : Counter := 0;
    AcksIssued : Counter := 0;
    ResetsIssued : Counter := 0;
    PNO : Integer;
    AckBuf : AckBuffer;
    AckBufFront : Buffer_Index := 0;
    AckBufBack : Buffer_Index := 0;
    ResBuf : ResBuffer;
    ResBufFront : Buffer_Index := 0;
    ResBufBack : Buffer_Index := 0;
    Seed : Generator;
  begin
    loop
      --if IsReset then
      --  PNO := (Integer(TempToX) + 1) + (Integer(TempToY) * 3) + (Integer(TempToZ) * 12);
      --  Put_Line(Integer'Image(PNO) & " is waiting.");
      --end if;
      Reset(Seed);
      select

        -- Reset entry to be called by user program on arbitrary processor.

        accept Reset (ToX : in PX; ToY : in PY; ToZ : in PZ) do
          TempToX := ToX; -- Coordinates of self to call neighbours relatively
          TempToY := ToY;
          TempToZ := ToZ;
        end Reset;

        -- Check that processor is not already in the "Reset" state.

        if not IsReset then
          IsReset := True;
          IsFirst := True;

          PNO := (Integer(TempToX) + 1) + (Integer(TempToY) * 3) + (Integer(TempToZ) * 12);
          --Put_Line(Integer'Image(PNO) & " reset.");

          -- Call all neighbouring processors since Reset entry was called directly.

          ResBuf(ResBufBack).FromX := TempToX;
          ResBuf(ResBufBack).FromY := TempToY;
          ResBuf(ResBufBack).FromZ := TempToZ;
          ResBuf(ResBufBack).ToX := TempToX + 1;
          ResBuf(ResBufBack).ToY := TempToY;
          ResBuf(ResBufBack).ToZ := TempToZ;
          ResBufBack := ResBufBack + 1;
          ResBuf(ResBufBack).FromX := TempToX;
          ResBuf(ResBufBack).FromY := TempToY;
          ResBuf(ResBufBack).FromZ := TempToZ;
          ResBuf(ResBufBack).ToX := TempToX - 1;
          ResBuf(ResBufBack).ToY := TempToY;
          ResBuf(ResBufBack).ToZ := TempToZ;
          ResBufBack := ResBufBack + 1;
          ResBuf(ResBufBack).FromX := TempToX;
          ResBuf(ResBufBack).FromY := TempToY;
          ResBuf(ResBufBack).FromZ := TempToZ;
          ResBuf(ResBufBack).ToX := TempToX;
          ResBuf(ResBufBack).ToY := TempToY + 1;
          ResBuf(ResBufBack).ToZ := TempToZ;
          ResBufBack := ResBufBack + 1;
          ResBuf(ResBufBack).FromX := TempToX;
          ResBuf(ResBufBack).FromY := TempToY;
          ResBuf(ResBufBack).FromZ := TempToZ;
          ResBuf(ResBufBack).ToX := TempToX;
          ResBuf(ResBufBack).ToY := TempToY - 1;
          ResBuf(ResBufBack).ToZ := TempToZ;
          ResBufBack := ResBufBack + 1;
          ResBuf(ResBufBack).FromX := TempToX;
          ResBuf(ResBufBack).FromY := TempToY;
          ResBuf(ResBufBack).FromZ := TempToZ;
          ResBuf(ResBufBack).ToX := TempToX;
          ResBuf(ResBufBack).ToY := TempToY;
          ResBuf(ResBufBack).ToZ := TempToZ + 1;
          ResBufBack := ResBufBack + 1;
          ResBuf(ResBufBack).FromX := TempToX;
          ResBuf(ResBufBack).FromY := TempToY;
          ResBuf(ResBufBack).FromZ := TempToZ;
          ResBuf(ResBufBack).ToX := TempToX;
          ResBuf(ResBufBack).ToY := TempToY;
          ResBuf(ResBufBack).ToZ := TempToZ - 1;
          ResBufBack := ResBufBack + 1;
          --Put_Line(Integer'Image(PNO) & " finished calling reset on neighbours.");
        end if;
      or

        -- Reset entry to be called from other processor.

        accept Reset (FromX : in PX; FromY : in PY; FromZ : in PZ; ToX : in PX; ToY : in PY; ToZ : in PZ) do
          TempFromX := FromX; -- Coordinates of calling processor to return
          TempFromY := FromY; -- acknowledgement.
          TempFromZ := FromZ;
          TempToX := ToX; -- Coordinates of self to call neighbours relatively
          TempToY := ToY;
          TempToZ := ToZ;
        end Reset;

        -- If processor has not been Reset, "Reset", and call neighbours.
        -- Otherwise if it has been Reset already, send acknowledgement
        -- back to caller.

        if not IsReset then
          IsReset := True; -- "Reset"
          PNO := (Integer(TempToX) + 1) + (Integer(TempToY) * 3) + (Integer(TempToZ) * 12);
          --Put_Line(Integer'Image(PNO) & " reset.");
          -- Call Reset entry of all neighbouring processors except processor that called it.

          if TempToX + 1 /= TempFromX then
            --Put_Line(Integer'Image(PNO) & " reset. Resetting x+1.");
            --delay 0.2;
            --Prism(TempToX + 1, TempToY, TempToZ).Reset(TempToX, TempToY, TempToZ, TempToX + 1, TempToY, TempToZ);
            ResBuf(ResBufBack).FromX := TempToX;
            ResBuf(ResBufBack).FromY := TempToY;
            ResBuf(ResBufBack).FromZ := TempToZ;
            ResBuf(ResBufBack).ToX := TempToX + 1;
            ResBuf(ResBufBack).ToY := TempToY;
            ResBuf(ResBufBack).ToZ := TempToZ;
            ResBufBack := ResBufBack + 1;
          end if;
          if TempToX - 1 /= TempFromX then
            --Put_Line(Integer'Image(PNO) & " reset. Resetting x-1.");
            --delay 0.2;
            --Prism(TempToX - 1, TempToY, TempToZ).Reset(TempToX, TempToY, TempToZ, TempToX - 1, TempToY, TempToZ);
            ResBuf(ResBufBack).FromX := TempToX;
            ResBuf(ResBufBack).FromY := TempToY;
            ResBuf(ResBufBack).FromZ := TempToZ;
            ResBuf(ResBufBack).ToX := TempToX - 1;
            ResBuf(ResBufBack).ToY := TempToY;
            ResBuf(ResBufBack).ToZ := TempToZ;
            ResBufBack := ResBufBack + 1;
          end if;
          if TempToY + 1 /= TempFromY then
            --Put_Line(Integer'Image(PNO) & " reset. Resetting y+1.");
            --delay 0.2;
            --Prism(TempToX, TempToY + 1, TempToZ).Reset(TempToX, TempToY, TempToZ, TempToX, TempToY + 1, TempToZ);
            ResBuf(ResBufBack).FromX := TempToX;
            ResBuf(ResBufBack).FromY := TempToY;
            ResBuf(ResBufBack).FromZ := TempToZ;
            ResBuf(ResBufBack).ToX := TempToX;
            ResBuf(ResBufBack).ToY := TempToY + 1;
            ResBuf(ResBufBack).ToZ := TempToZ;
            ResBufBack := ResBufBack + 1;
          end if;
          if TempToY - 1 /= TempFromY then
            --Put_Line(Integer'Image(PNO) & " reset. Resetting y-1.");
            --delay 0.2;
            --Prism(TempToX, TempToY - 1, TempToZ).Reset(TempToX, TempToY, TempToZ, TempToX, TempToY - 1, TempToZ);
            ResBuf(ResBufBack).FromX := TempToX;
            ResBuf(ResBufBack).FromY := TempToY;
            ResBuf(ResBufBack).FromZ := TempToZ;
            ResBuf(ResBufBack).ToX := TempToX;
            ResBuf(ResBufBack).ToY := TempToY - 1;
            ResBuf(ResBufBack).ToZ := TempToZ;
            ResBufBack := ResBufBack + 1;
          end if;
          if TempToZ + 1 /= TempFromZ then
            --Put_Line(Integer'Image(PNO) & " reset. Resetting z+1.");
            --delay 0.2;
            --Prism(TempToX, TempToY, TempToZ + 1).Reset(TempToX, TempToY, TempToZ, TempToX, TempToY, TempToZ + 1);
            ResBuf(ResBufBack).FromX := TempToX;
            ResBuf(ResBufBack).FromY := TempToY;
            ResBuf(ResBufBack).FromZ := TempToZ;
            ResBuf(ResBufBack).ToX := TempToX;
            ResBuf(ResBufBack).ToY := TempToY;
            ResBuf(ResBufBack).ToZ := TempToZ + 1;
            ResBufBack := ResBufBack + 1;
          end if;
          if TempToZ - 1 /= TempFromZ then
            --Put_Line(Integer'Image(PNO) & " reset. Resetting z-1.");
            --delay 0.2;
            --Prism(TempToX, TempToY, TempToZ - 1).Reset(TempToX, TempToY, TempToZ, TempToX, TempToY, TempToZ - 1);
            ResBuf(ResBufBack).FromX := TempToX;
            ResBuf(ResBufBack).FromY := TempToY;
            ResBuf(ResBufBack).FromZ := TempToZ;
            ResBuf(ResBufBack).ToX := TempToX;
            ResBuf(ResBufBack).ToY := TempToY;
            ResBuf(ResBufBack).ToZ := TempToZ - 1;
            ResBufBack := ResBufBack + 1;
          end if;
          --Put_Line(Integer'Image(PNO) & " finished calling reset on neighbours.");

          SavedX := TempFromX; -- Save caller information for later
          SavedY := TempFromY; -- acknowledgement.
          SavedZ := TempFromZ;
        else -- Already Reset
          AcksIssued := AcksIssued + 1;
          --PNO := (Integer(TempToX) + 1) + (Integer(TempToY) * 3) + (Integer(TempToZ) * 12);
          --Put_Line(Integer'Image(PNO) & " already reset");
          AckBuf(AckBufBack).ToX := SavedX;
          AckBuf(AckBufBack).ToY := SavedY;
          AckBuf(AckBufBack).ToZ := SavedZ;
          AckBufBack := AckBufBack + 1;
        end if;
      or

        -- Acknowledge entry called when a processor has been Reset.

        accept Acknowledge do
          if IsReset then
            NumAcks := NumAcks + 1;
          end if;
        end Acknowledge;
        PNO := (Integer(TempToX) + 1) + (Integer(TempToY) * 3) + (Integer(TempToZ) * 12);
        --Put_Line(Integer'Image(PNO) & " acknowledged.");

        -- If all neighbouring processors have called back with acknowledgements.

        if AckBufFront = AckBufBack and ResBufFront = ResBufBack and NumAcks = 5 then
          --Put_Line(Integer'Image(PNO) & " Acknowledged 5 Times.");
          Put(Integer'Image(PNO));
          Set_Col(5);
          Put_Line(Integer'Image(Integer(AcksIssued)));

          -- Processor was called by some other processor, so acknowledge it.

          Prism(SavedX, SavedY, SavedZ).Acknowledge;
          --AckBuf(AckBufBack).ToX := SavedX;
          --AckBuf(AckBufBack).ToY := SavedY;
          --AckBuf(AckBufBack).ToZ := SavedZ;
          --AckBufBack := AckBufBack + 1;
          abort Prism(TempToX, TempToY, TempToZ);
        end if;

        -- Processor was first to have Reset called.

        if AckBufFront = AckBufBack and ResBufFront = ResBufBack and NumAcks = 6 then
          --Put_Line(Integer'Image(PNO) & " Acknowledged 5 Times.");
          Put(Integer'Image(PNO));
          Set_Col(5);
          Put_Line(Integer'Image(Integer(AcksIssued)));
          abort Prism(TempToX, TempToY, TempToZ);
        end if;
      or

        delay Duration(Random(Seed));
        if AckBufFront /= AckBufBack then
          select
            Prism(AckBuf(AckBufFront).ToX, AckBuf(AckBufFront).ToY, AckBuf(AckBufFront).ToZ).Acknowledge;
            AckBufFront := AckBufFront + 1;
            --Put_Line("Acknowledgement Sent.");
          or
            delay 0.0;
            --Put_Line("Acknowledgement Timed Out.");
          end select;
        end if;
        if ResBufFront /= ResBufBack then
          select
            Prism(ResBuf(ResBufFront).ToX, ResBuf(ResBufFront).ToY, ResBuf(ResBufFront).ToZ).Reset(ResBuf(ResBufFront).FromX, ResBuf(ResBufFront).FromY, ResBuf(ResBufFront).FromZ, ResBuf(ResBufFront).ToX, ResBuf(ResBufFront).ToY, ResBuf(ResBufFront).ToZ);
            ResBufFront := ResBufFront + 1;
            ResetsIssued := ResetsIssued + 1;
            --Put_Line("Reset Sent.");
          or
            delay 0.0;
            --Put_Line("Reset Timed Out.");
          end select;
        end if;
      end select;
    end loop;
  end Processor;
begin
  Put_Line("PNO #ACKS");
end Torus;
