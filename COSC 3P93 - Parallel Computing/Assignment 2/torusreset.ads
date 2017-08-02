with Random;
with Torus; use Torus;
package TorusReset is

  procedure TorusReset;

  function GetFarthest (From : Vector; DX : Integer; DY : Integer;
    DZ : Integer) return Vector;

End TorusReset;
