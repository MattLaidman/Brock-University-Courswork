package Torus is

  procedure DoReset;

private

  type Counter is range 0..6;

  type PX is mod 3;
  type PY is mod 4;
  type PZ is mod 5;

  type PCoords is record
    X : PX;
    Y : PY;
    Z : PZ;
  end record;

  type ResRequest is record
    From : PCoords;
    To : PCoords;
  end record;

  task type Processor is
    entry Reset (Self : PCoords);
    entry Reset (Req : ResRequest);
    entry Acknowledge;
  end Processor;

  type ProcessorAcc is access all Processor;

  Prism : array (PX, PY, PZ) of ProcessorAcc;

  task type TorusController is
    entry StartTorus;
    entry KillThis (Location : PCoords);
  end TorusController;

  type TorusAcc is access all TorusController;
  Control : TorusAcc;

  type NeighbourIndex is range 0..5;
  type NeighbourArray is array (NeighbourIndex) of PCoords;

  type ResetBuffer is record
    Buffer : NeighbourArray;
    Front : NeighbourIndex := 0;
    Back : NeighbourIndex := 0;
  end record;

  procedure ResetSixNeighbours (Self : PCoords);
  function ResetFiveNeighbours (Self : PCoords; Originated : PCoords) return ResetBuffer;

end torus;
