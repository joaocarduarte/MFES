

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Board {
  public static Number BOARD_SIZE = 8L;
  private VDMMap board = MapUtil.map();
  private Object colorWhite = Hitori.quotes.WHITEQuote.getInstance();

  public void cg_init_Board_2(final Number s) {

    BOARD_SIZE = s;
    createBoard();
    setBlackCell();
    return;
  }

  public void cg_init_Board_1() {

    createBoard();
    return;
  }

  public Board() {

    cg_init_Board_1();
  }

  public Board(final Number s) {

    cg_init_Board_2(s);
  }

  public void createBoard() {

    Cell.Coord c = null;
    Cell cell = null;
    for (Iterator iterator_4 = SetUtil.range(0L, Board.BOARD_SIZE.longValue() - 1L).iterator();
        iterator_4.hasNext();
        ) {
      Number i = (Number) iterator_4.next();
      for (Iterator iterator_5 = SetUtil.range(0L, Board.BOARD_SIZE.longValue() - 1L).iterator();
          iterator_5.hasNext();
          ) {
        Number j = (Number) iterator_5.next();
        c = new Cell.Coord(i, j);
        cell = new Cell(0L, Utils.copy(c));
        board = MapUtil.override(Utils.copy(board), MapUtil.map(new Maplet(Utils.copy(c), cell)));
      }
    }
  }

  public Cell getCellOfBoard(final Cell.Coord coord) {

    VDMMap mapCell = MapUtil.map();
    mapCell = MapUtil.domResTo(SetUtil.set(Utils.copy(coord)), Utils.copy(board));
    {
      Cell j = null;
      Boolean success_1 = false;
      VDMSet set_1 = MapUtil.rng(Utils.copy(mapCell));
      for (Iterator iterator_1 = set_1.iterator(); iterator_1.hasNext() && !(success_1); ) {
        j = ((Cell) iterator_1.next());
        success_1 = true;
      }
      if (!(success_1)) {
        throw new RuntimeException("Let Be St found no applicable bindings");
      }

      {
        return j;
      }
    }
  }

  public void setCellOfBoard(final Cell.Coord coord, final Cell cell) {

    board = MapUtil.override(Utils.copy(board), MapUtil.map(new Maplet(Utils.copy(coord), cell)));
  }

  public void setBlackCell() {

    Number numberOfBlack = 1L;
    Number xRand = 0L;
    Number yRand = 0L;
    Boolean whileController = false;
    Cell cell = null;
    whileController = true;
    numberOfBlack = MATH.rand(2L).longValue() + 4L;
    for (Iterator iterator_6 = SetUtil.range(0L, numberOfBlack.longValue() - 1L).iterator();
        iterator_6.hasNext();
        ) {
      Number i = (Number) iterator_6.next();
      Boolean whileCond_1 = true;
      while (whileCond_1) {
        whileCond_1 = whileController;
        if (!(whileCond_1)) {
          break;
        }

        {
          xRand = MATH.rand(Board.BOARD_SIZE);
          yRand = MATH.rand(Board.BOARD_SIZE);
          Boolean andResult_2 = false;

          Boolean orResult_1 = false;

          if (yRand.longValue() < 1L) {
            orResult_1 = true;
          } else {
            orResult_1 =
                Utils.equals(
                    getCellOfBoard(new Cell.Coord(xRand, yRand.longValue() - 1L)).color,
                    Hitori.quotes.WHITEQuote.getInstance());
          }

          if (orResult_1) {
            Boolean andResult_3 = false;

            Boolean orResult_2 = false;

            if (yRand.longValue() + 1L >= Board.BOARD_SIZE.longValue()) {
              orResult_2 = true;
            } else {
              orResult_2 =
                  Utils.equals(
                      getCellOfBoard(new Cell.Coord(xRand, yRand.longValue() + 1L)).color,
                      Hitori.quotes.WHITEQuote.getInstance());
            }

            if (orResult_2) {
              Boolean andResult_4 = false;

              Boolean orResult_3 = false;

              if (xRand.longValue() < 1L) {
                orResult_3 = true;
              } else {
                orResult_3 =
                    Utils.equals(
                        getCellOfBoard(new Cell.Coord(xRand.longValue() - 1L, yRand)).color,
                        Hitori.quotes.WHITEQuote.getInstance());
              }

              if (orResult_3) {
                Boolean orResult_4 = false;

                if (xRand.longValue() + 1L >= Board.BOARD_SIZE.longValue()) {
                  orResult_4 = true;
                } else {
                  orResult_4 =
                      Utils.equals(
                          getCellOfBoard(new Cell.Coord(xRand.longValue() + 1L, yRand)).color,
                          Hitori.quotes.WHITEQuote.getInstance());
                }

                if (orResult_4) {
                  andResult_4 = true;
                }
              }

              if (andResult_4) {
                andResult_3 = true;
              }
            }

            if (andResult_3) {
              andResult_2 = true;
            }
          }

          if (andResult_2) {
            whileController = false;
          }
        }
      }

      whileController = true;
      cell = getCellOfBoard(new Cell.Coord(xRand, yRand));
      cell.color = Hitori.quotes.BLACKQuote.getInstance();
      setCellOfBoard(new Cell.Coord(xRand, yRand), cell);
    }
  }

  public Number changeBoardNumbers() {

    for (Iterator iterator_7 = MapUtil.rng(Utils.copy(board)).iterator(); iterator_7.hasNext(); ) {
      Cell i = (Cell) iterator_7.next();
      Number x = 0L;
      Number y = 0L;
      Cell.Coord coord = null;
      VDMMap mapCell = MapUtil.map();
      Cell cell = null;
      x = i.getCoord().x;
      y = i.getCoord().y;
      coord = new Cell.Coord(x, y);
      cell = new Cell(4L, Utils.copy(coord));
      board = MapUtil.override(Utils.copy(board), MapUtil.map(new Maplet(Utils.copy(coord), cell)));
      mapCell = MapUtil.domResTo(SetUtil.set(Utils.copy(coord)), Utils.copy(board));
      {
        Cell j = null;
        Boolean success_2 = false;
        VDMSet set_2 = MapUtil.rng(Utils.copy(mapCell));
        for (Iterator iterator_2 = set_2.iterator(); iterator_2.hasNext() && !(success_2); ) {
          j = ((Cell) iterator_2.next());
          success_2 = true;
        }
        if (!(success_2)) {
          throw new RuntimeException("Let Be St found no applicable bindings");
        }

        {
          return j.getNumber();
        }
      }
    }
  }

  public String toString() {

    return "Board{"
        + "BOARD_SIZE := "
        + Utils.toString(BOARD_SIZE)
        + ", board := "
        + Utils.toString(board)
        + ", colorWhite := "
        + Utils.toString(colorWhite)
        + "}";
  }
}
