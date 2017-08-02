// Original versions were console based, Game class was old driver.
// Not important in recent versions.
class Game {

    private Board b;


    private Player wp, bp;

    Game(Player wp, Player bp) {

        this.wp = wp;
        this.bp = bp;
        b = new Board();
        b.initBoard(); // make it do something I guess
    }

    Board getBoard ( ) {

        return b;
    }
}