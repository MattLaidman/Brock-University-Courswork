package Torus is

  task type Semaphore is

    entry Signal;
    entry Wait;
  end Semaphore;

  Done : Semaphore;


  type DimX is mod 3;
  type DimY is mod 4;
  type DimZ is mod 5;

  type Vector is record
    X : DimX;
    Y : DimY;
    Z : DimZ;
    IsReal : Boolean := True;
  end record;
  type Request is record
    From : Vector;
    To : Vector;
  end record;

  type NeighbourIndex is range 0..7;
  type NeighbourArray is array (NeighbourIndex) of Request;

  type ResetBuffer is record
    Buffer : NeighbourArray;
    Front : NeighbourIndex := 0;
    Back : NeighbourIndex := 0;
  end record;

  task type Process is

    entry Reset(Req : Request);
    entry Acknowledge;
  end Process;

  Torus : array (DimX, DimY, DimZ) of Process;

  function ResetNeighbours (Req : Request) return ResetBuffer;

end Torus;
