with Ada.Text_IO; use Ada.Text_IO;
with Ada.Task_Identification;  use Ada.Task_Identification;
package body Torus is

  -- Public DoReset procedure initializes the Torus Processor and calls the
  -- initial processor in the array with it's reset entry.

  procedure DoReset is

    Req : PCoords;

  begin

    Control := new TorusController;
    Control.StartTorus;

    Put_Line("PNO #ACKS");

    Req.X := 0;
    Req.Y := 0;
    Req.Z := 0;
    Prism(0, 0, 0).Reset(Req);

  end DoReset;

  -- TorusController task Initializes the Torus processors and accepts 'KillThis'
  -- entries to kill tasks that are no longer needed in the simulation.

  task body TorusController is
    KillProcess : PCoords;
  begin
    loop
      select
        accept StartTorus do -- init processors
          for i in PX'Range loop
            for j in PY'Range loop
              for k in PZ'Range loop
                Prism(i, j, k) := new Processor;
              end loop;
            end loop;
          end loop;
        end StartTorus;
      or
        accept KillThis (Location : in PCoords) do -- kill calling task
          KillProcess := Location;
        end KillThis;
        Abort_Task(Prism(KillProcess.X, KillProcess.Y, KillProcess.Z)'Identity);
      or
        terminate;
      end select;
    end loop;
  end TorusController;

  -- Processor task accepts resets and sends resets to it's neighbours, as well
  -- as acknowledgements. Used as individual processors in Torus array.

  task body Processor is

    IsReset : Boolean := False; -- Flag representing if the processor previously reset
    IsFirst : Boolean := False; -- Flag representing if processor was first one called
    SavedCaller : PCoords; -- Saved coordinated of calling processor if applicable
    TempSelf :   PCoords;
    TempFrom : PCoords;
    RecAcks : Counter := 0; -- Number of acknowledgements recieved
    RecResets : Counter := 0; -- Number of resets recieved
    SentAcks : Counter := 0; -- Number of acknowledgements sent
    SentResets : Counter := 0; -- Number of resets sent
    Buffer : ResetBuffer;
    CurrentRequest : ResRequest;

  begin

    loop
      select

        accept Reset (Self : in PCoords) do -- initial reset called by client
          TempSelf := Self;
        end Reset;
        IsFirst := True;
        IsReset := True; -- Flag as being reset
        RecResets := 1;
        SentResets := 6;
        ResetSixNeighbours(TempSelf);  -- Call 6 neighbours

      or

        accept Reset (Req : in ResRequest) do -- reset called by neighbouring task
          TempSelf := Req.To;
          TempFrom := Req.From;
        end Reset;
        RecResets := RecResets + 1;
        if IsReset then -- Processor previously reset
          SentAcks := SentAcks + 1;
          Prism(TempFrom.X, TempFrom.Y, TempFrom.Z).Acknowledge; -- Immediately acknowledge caller
        else -- First time processor recieves reset entry
          IsReset := True; -- Flag as being reset
          SavedCaller := TempFrom; -- Save coordinates of calling processor
          SentResets := 5;
          Buffer := ResetFiveNeighbours(TempSelf, TempFrom); -- Call 5 neighbours
        end if;

      or

        accept Acknowledge do -- ackknowledge recieved
          if IsReset then
            RecAcks := RecAcks + 1;
          end if;
        end Acknowledge;

      or -- or empty erset buffer if need be

        delay 0.0;
        if IsReset and not IsFirst and (Buffer.Front /= Buffer.Back) then
          CurrentRequest.From := TempSelf;
          CurrentRequest.To := Buffer.Buffer(Buffer.Front);
          select
            Prism(Buffer.Buffer(Buffer.Front).X, Buffer.Buffer(Buffer.Front).Y, Buffer.Buffer(Buffer.Front).Z).Reset(CurrentRequest);
            Buffer.Front := Buffer.Front + 1;
          or
            delay 0.0;
          end select;
        end if;
        if IsReset and (RecAcks = SentResets) and RecResets = 6 then
          Put_Line(Integer'Image((Integer(TempSelf.X) + 1) + (Integer(TempSelf.Y) * 3) + (Integer(TempSelf.Z) * 12)) & "   " & Counter'Image(SentAcks));
          Prism(SavedCaller.X, SavedCaller.Y, SavedCaller.Z).Acknowledge; -- send ack back to original caller
          Control.KillThis(TempSelf); -- done, kill me.
        end if;

      end select;
    end loop;

  end Processor;

  -- ResetNeighbours function calls all 6 neighbouring "processors" with reset
  -- requests.

  procedure ResetSixNeighbours (Self : PCoords) is

    Neighbours : NeighbourArray; -- Array for neighbour coords
    Req : ResRequest;

  begin

    for i in Neighbours'Range loop -- Init array with self coords
      Neighbours(i) := Self;
    end loop;
    Neighbours(0).X := Neighbours(0).X + 1; -- Change values to neighbours
    Neighbours(1).X := Neighbours(1).X - 1;
    Neighbours(2).Y := Neighbours(2).Y + 1;
    Neighbours(3).Y := Neighbours(3).Y - 1;
    Neighbours(4).Z := Neighbours(4).Z + 1;
    Neighbours(5).Z := Neighbours(5).Z - 1;
    for i in Neighbours'Range loop -- Create request and call neighbours
      Req.From := Self;
      Req.To := Neighbours(i);
      Prism(Neighbours(i).X, Neighbours(i).Y, Neighbours(i).Z).Reset(Req);
    end loop;

  end ResetSixNeighbours;

  -- ResetNeighbours function calls 5 neighbouring "processors" with reset
  -- requests, excluding the processor that send the original request, when
  -- possible. This is dpne by placing the requests in a buffer that is emptied
  -- when possible.

  function ResetFiveNeighbours (Self : PCoords; Originated : PCoords) return ResetBuffer is

    Neighbours : ResetBuffer; -- Array for neighbour coords
    i : NeighbourIndex := 0;

  begin

    for j in Neighbours.Buffer'Range loop -- Init array with self coords
      Neighbours.Buffer(j) := Self;
    end loop;
    if (Neighbours.Buffer(i).X + 1) /= Originated.X then
      Neighbours.Buffer(i).X := Neighbours.Buffer(i).X + 1; -- Change values to neighbours
      i := i + 1;
    end if;
    if (Neighbours.Buffer(i).X - 1) /= Originated.X then
      Neighbours.Buffer(i).X := Neighbours.Buffer(i).X - 1;
      i := i + 1;
    end if;
    if (Neighbours.Buffer(i).Y + 1) /= Originated.Y then
      Neighbours.Buffer(i).Y := Neighbours.Buffer(i).Y + 1;
      i := i + 1;
    end if;
    if (Neighbours.Buffer(i).Y - 1) /= Originated.Y then
      Neighbours.Buffer(i).Y := Neighbours.Buffer(i).Y - 1;
      i := i + 1;
    end if;
    if (Neighbours.Buffer(i).Z + 1) /= Originated.Z then
      Neighbours.Buffer(i).Z := Neighbours.Buffer(i).Z + 1;
      i := i + 1;
    end if;
    if (Neighbours.Buffer(i).Z - 1) /= Originated.Z then
      Neighbours.Buffer(i).Z := Neighbours.Buffer(i).Z - 1;
      i := i + 1;
    end if;
    Neighbours.Back := 5;
    return Neighbours;

  end ResetFiveNeighbours;

end Torus;
