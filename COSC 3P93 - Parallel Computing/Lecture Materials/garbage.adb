with TEXT_IO, INT_IO, FLT_IO, ADA.UNCHECKED_DEALLOCATION;
 use TEXT_IO, INT_IO, FLT_IO;
 
procedure GARBAGE is

    type BIG_BLOCK;
    type BLOCK is access BIG_BLOCK;
    type BYTE_BLOCK is array (NATURAL range <>) of CHARACTER;

    type BIG_BLOCK(BYTES : NATURAL) is
    record
        BYTE : BYTE_BLOCK(0 .. BYTES);
        NXT  : BLOCK;
    end record;

---------------------------------------------------------------------------------------
    procedure DISPOSE is
        new ADA.UNCHECKED_DEALLOCATION(BIG_BLOCK, BLOCK);
---------------------------------------------------------------------------------------
    procedure FREE(B : in out BLOCK) is
    begin
        if B /= null then
            if B.NXT /= null then
                FREE(B.NXT);
            end if;
            DISPOSE(B);
        end if;
    end FREE;
---------------------------------------------------------------------------------------

    COUNT : NATURAL := 0;
    BLOCX : BLOCK;
    THIS  : BLOCK;
    
begin
    NEW_LINE;
    PUT("----------------------------------------------"); NEW_LINE;
    PUT("Garbage Collection Tester (c) 2002 Vlad WOJCIK"); NEW_LINE;
    PUT("----------------------------------------------"); NEW_LINE; NEW_LINE;

    loop
        BLOCX := new BIG_BLOCK(BYTES => 1048576);
        THIS  := BLOCX;
        loop
            exit when COUNT = 1001; delay 0.01;
            THIS.NXT := new BIG_BLOCK(BYTES => 1048576);
            --------------------
            if COUNT rem 50 = 0 then NEW_LINE; PUT(COUNT, 6); end if;
            if COUNT rem 10 = 0 then PUT(' '); end if;
            PUT('.');
            --------------------
            COUNT := COUNT+1; THIS := THIS.NXT;
        end loop; -- COUNT

        FREE(BLOCX); COUNT := 0; delay 2.0;
        NEW_LINE; NEW_LINE;
    end loop;
end GARBAGE;
