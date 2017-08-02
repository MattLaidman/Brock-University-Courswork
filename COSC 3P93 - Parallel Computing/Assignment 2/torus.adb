with Ada.Text_IO; use Ada.Text_IO;
package body Torus is

  task body Process is

    PreviouslyReset : Boolean := False;
    CurrentRequest : Request;
    Neighbours : ResetBuffer;
    From : Vector;
    Self : Vector;
    ACount : Integer := 0;
    ASent : Integer := 0;
    RSent : Integer := 0;
    RecievedAll : Boolean := False; -- State flags
    SentAll : Boolean := False;
    CalledBack : Boolean := False;
  begin
    loop
      select
        accept Reset(Req : Request) do
          CurrentRequest := Req;
        end Reset;
        if not PreviouslyReset then
          PreviouslyReset := True; -- Flag as being previously reset
          From := CurrentRequest.From; -- save original caller
          Self := CurrentRequest.To;
          Neighbours := ResetNeighbours(CurrentRequest); -- get reset calls to neighbours
          RSent := Integer(Neighbours.Back);
        else -- ACK immediately if previously reset
          Torus(CurrentRequest.From.X, CurrentRequest.From.Y, CurrentRequest.From.Z).Acknowledge;
          ASent:= ASent + 1;
        end if;
        or accept Acknowledge do
          ACount := ACount + 1; -- recieved acknowledgement
        end Acknowledge;
        or delay 0.2;
        if PreviouslyReset then
          if Neighbours.Front /= Neighbours.Back then -- empty reset buffer
            select
              Torus(Neighbours.Buffer(Neighbours.Front).To.X, Neighbours.Buffer(Neighbours.Front).To.Y, Neighbours.Buffer(Neighbours.Front).To.Z).Reset(Neighbours.Buffer(Neighbours.Front));
              Neighbours.Front := Neighbours.Front + 1;
            or delay 0.2;
            end select;
          else
            if not SentAll then -- Flag as having sent all resets
              SentAll := True;
            end if;
          end if;
          if SentAll and then not RecievedAll and then RSent = ACount then
            RecievedAll := True; -- Recieved all acknowledgements backs
          end if;
          if not CalledBack and then SentAll and then RecievedAll then
            if From.IsReal then -- Acknowledge Caller
              select
                Torus(From.X, From.Y, From.Z).Acknowledge;
                ASent := ASent + 1;
                CalledBack := True; -- called initial caller
                Put_Line(Integer'Image((Integer(Self.X) + 1) + (Integer(Self.Y) * 3) + (Integer(Self.Z) * 12)) & "    " & Integer'Image(ASent));
              or delay 0.2;
              end select;
            else -- or signal Semaphore
              Put_Line(Integer'Image((Integer(Self.X) + 1) + (Integer(Self.Y) * 3) + (Integer(Self.Z) * 12)) & "    " & Integer'Image(ASent + 1));
              Done.Signal;
              CalledBack := True; -- called semaphore
            end if;
          end if;
        end if;
      end select;
    end loop;
  end Process;

  function ResetNeighbours (Req : Request) return ResetBuffer is

    To : Vector;
    Neighbours : ResetBuffer; -- Array for requests
    BufferIndex : NeighbourIndex := 0;
  begin

    To.X := Req.To.X;
    To.Y := Req.To.Y;
    To.Z := Req.To.Z;

    To.X := Req.To.X + 1; -- Add x + 1 to buffer
    if Req.From.IsReal = False or else Req.From.X /= To.X then
      Neighbours.Buffer(BufferIndex).From := Req.To;
      Neighbours.Buffer(BufferIndex).To := To;
      BufferIndex := BufferIndex + 1;
      Neighbours.Back := Neighbours.Back + 1;
    end if;
    To.X := Req.To.X - 1; -- Add x - 1 to buffer
    if Req.From.IsReal = False or else Req.From.X /= To.X then
      Neighbours.Buffer(BufferIndex).From := Req.To;
      Neighbours.Buffer(BufferIndex).To := To;
      BufferIndex := BufferIndex + 1;
      Neighbours.Back := Neighbours.Back + 1;
    end if;
    To.X := Req.To.X;
    To.Y := Req.To.Y + 1; -- Add y + 1 to buffer
    if Req.From.IsReal = False or else Req.From.Y /= To.Y then
      Neighbours.Buffer(BufferIndex).From := Req.To;
      Neighbours.Buffer(BufferIndex).To := To;
      BufferIndex := BufferIndex + 1;
      Neighbours.Back := Neighbours.Back + 1;
    end if;
    To.Y := Req.To.Y - 1; -- Add y - 1 to buffer
    if Req.From.IsReal = False or else Req.From.Y /= To.Y then
      Neighbours.Buffer(BufferIndex).From := Req.To;
      Neighbours.Buffer(BufferIndex).To := To;
      BufferIndex := BufferIndex + 1;
      Neighbours.Back := Neighbours.Back + 1;
    end if;
    To.Y := Req.To.Y;
    To.Z := Req.To.Z + 1; -- Add z + 1 to buffer
    if Req.From.IsReal = False or else Req.From.Z /= To.Z then
      Neighbours.Buffer(BufferIndex).From := Req.To;
      Neighbours.Buffer(BufferIndex).To := To;
      BufferIndex := BufferIndex + 1;
      Neighbours.Back := Neighbours.Back + 1;
    end if;
    To.Z := Req.To.Z - 1; -- Add z - 1 to buffer
    if Req.From.IsReal = False or else Req.From.Z /= To.Z then
      Neighbours.Buffer(BufferIndex).From := Req.To;
      Neighbours.Buffer(BufferIndex).To := To;
      BufferIndex := BufferIndex + 1;
      Neighbours.Back := Neighbours.Back + 1;
    end if;
    return Neighbours;
  end ResetNeighbours;

  task body Semaphore is

    Count : Integer := -1;
  begin
    loop
      select
        accept Signal; -- Increment on Signal
          Count := Count + 1;
        or when Count > 0 => accept Wait; -- Decrement on Wait as soon as Count > 0
          Count := Count - 1;
      end select;
    end loop;
  end Semaphore;
end Torus;
