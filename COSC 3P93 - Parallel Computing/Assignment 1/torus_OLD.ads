package Torus is

  type PX is mod 3;
  type PY is mod 4;
  type PZ is mod 5;

  task type Processor is
    entry Reset (ToX : in PX; ToY : in PY; ToZ : in PZ);
    entry Reset (FromX : in PX; FromY : in PY; FromZ : in PZ; ToX : in PX; ToY : in PY; ToZ : in PZ);
    entry Acknowledge;
  end Processor;

  type Processor_Prism is array (PX, PY, PZ) of Processor;

  type ResRequest is record
    ToX : PX;
    ToY : PY;
    ToZ : PZ;
    FromX : PX;
    FromY : PY;
    FromZ : PZ;
  end record;

  type AckRequest is record
    ToX : PX;
    ToY : PY;
    ToZ : PZ;
  end record;

  type Buffer_Index is mod 7;
  type AckBuffer is array (Buffer_Index) of AckRequest;
  type ResBuffer is array (Buffer_Index) of ResRequest;

  procedure DoReset;
private
  type Counter is range 0..7;
end torus;
