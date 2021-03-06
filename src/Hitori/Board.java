package Hitori;

import java.util.*;
import org.overture.codegen.runtime.*;

@SuppressWarnings("all")
public class Board {
  public static Number BOARD_SIZE = 8L;
  private Rules rules;
  private VDMMap board = MapUtil.map();
  private Object colorWhite = Hitori.quotes.WHITEQuote.getInstance();
  private Boolean rule1 = false;
  private Boolean rule2 = false;
  private Boolean rule3 = false;

  public void cg_init_Board_1(final Number s, final Rules rulesArg) {

    BOARD_SIZE = s;
    rules = rulesArg;
    createBoard();
    setBlackCell();
    setNumbersUnpaintedCell();
    fillBoard();
    return;
  }

  public Number getBoardSize() {

    return Board.BOARD_SIZE;
  }

  public Board(final Number s, final Rules rulesArg) {

    cg_init_Board_1(s, rulesArg);
  }

  public VDMMap getBoard() {

    return Utils.copy(board);
  }

  public void createBoard() {

    Cell.Coord c = null;
    Cell cell = null;
    for (Iterator iterator_22 = SetUtil.range(0L, Board.BOARD_SIZE.longValue() - 1L).iterator();
        iterator_22.hasNext();
        ) {
      Number i = (Number) iterator_22.next();
      for (Iterator iterator_23 = SetUtil.range(0L, Board.BOARD_SIZE.longValue() - 1L).iterator();
          iterator_23.hasNext();
          ) {
        Number j = (Number) iterator_23.next();
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
    Number timeout = 0L;
    Number number =
        Utils.divide((1.0 * Board.BOARD_SIZE.longValue() * Board.BOARD_SIZE.longValue()), 4L);
    whileController = true;
    numberOfBlack = MATH.rand(2L).longValue() + number.longValue();
    for (Iterator iterator_24 = SetUtil.range(0L, numberOfBlack.longValue() - 1L).iterator();
        iterator_24.hasNext();
        ) {
      Number i = (Number) iterator_24.next();
      Boolean whileCond_1 = true;
      while (whileCond_1) {
        whileCond_1 = whileController;
        if (!(whileCond_1)) {
          break;
        }

        {
          xRand = MATH.rand(Board.BOARD_SIZE);
          yRand = MATH.rand(Board.BOARD_SIZE);
          Boolean andResult_4 = false;

          Boolean andResult_5 = false;

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
            Boolean andResult_6 = false;

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
              Boolean andResult_7 = false;

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
                  andResult_7 = true;
                }
              }

              if (andResult_7) {
                andResult_6 = true;
              }
            }

            if (andResult_6) {
              andResult_5 = true;
            }
          }

          if (andResult_5) {
            if (Utils.equals(
                getCellOfBoard(new Cell.Coord(xRand, yRand)).color,
                Hitori.quotes.WHITEQuote.getInstance())) {
              andResult_4 = true;
            }
          }

          if (andResult_4) {
            whileController = false;
          }

          if (Utils.equals(timeout, Board.BOARD_SIZE.longValue() * Board.BOARD_SIZE.longValue())) {
            board = MapUtil.map();
            createBoard();
            setBlackCell();
            return;
          }

          timeout = timeout.longValue() + 1L;
        }
      }

      timeout = 0L;
      whileController = true;
      cell = getCellOfBoard(new Cell.Coord(xRand, yRand));
      cell.color = Hitori.quotes.BLACKQuote.getInstance();
      setCellOfBoard(new Cell.Coord(xRand, yRand), cell);
    }
    Boolean orResult_5 = false;

    if (!(Utils.equals(rules.checkRule1(Utils.copy(board), Board.BOARD_SIZE), true))) {
      orResult_5 = true;
    } else {
      orResult_5 = !(Utils.equals(rules.checkRule2(Utils.copy(board), Board.BOARD_SIZE), true));
    }

    if (orResult_5) {
      board = MapUtil.map();
      createBoard();
      setBlackCell();
      return;
    }

    rule1 = rules.checkRule1(Utils.copy(board), Board.BOARD_SIZE);
    rule2 = rules.checkRule2(Utils.copy(board), Board.BOARD_SIZE);
  }

  public void setNumbersUnpaintedCell() {

    Number number = 0L;
    Cell cell = null;
    VDMMap tempColumn = MapUtil.map();
    VDMMap tempRow = MapUtil.map();
    VDMSeq tempSeq = SeqUtil.seq();
    VDMMap tempMap = MapUtil.map();
    Boolean whileController = false;
    Number timeout = 0L;
    for (Iterator iterator_25 = MapUtil.rng(Utils.copy(board)).iterator();
        iterator_25.hasNext();
        ) {
      Cell i = (Cell) iterator_25.next();
      if (Utils.equals(i.color, Hitori.quotes.WHITEQuote.getInstance())) {
        whileController = false;
        Boolean whileCond_2 = true;
        while (whileCond_2) {
          whileCond_2 = !(Utils.equals(whileController, true));
          if (!(whileCond_2)) {
            break;
          }

          {
            number = MATH.rand(Board.BOARD_SIZE).longValue() + 1L;
            tempMap = MapUtil.domResTo(SetUtil.set(i.getCoord().x), Utils.copy(tempColumn));
            if (Utils.empty(tempMap)) {
              tempMap = MapUtil.domResTo(SetUtil.set(i.getCoord().y), Utils.copy(tempRow));
              if (!(Utils.empty(tempMap))) {
                {
                  VDMSeq k = null;
                  Boolean success_2 = false;
                  VDMSet set_2 = MapUtil.rng(Utils.copy(tempMap));
                  for (Iterator iterator_2 = set_2.iterator();
                      iterator_2.hasNext() && !(success_2);
                      ) {
                    k = ((VDMSeq) iterator_2.next());
                    success_2 = true;
                  }
                  if (!(success_2)) {
                    throw new RuntimeException("Let Be St found no applicable bindings");
                  }

                  {
                    tempSeq = Utils.copy(k);
                    if (!(SetUtil.inSet(number, SeqUtil.elems(Utils.copy(k))))) {
                      cell = i;
                      cell.number = number;
                      setCellOfBoard(i.getCoord(), cell);
                      tempMap =
                          MapUtil.domResTo(SetUtil.set(i.getCoord().x), Utils.copy(tempColumn));
                      tempMap =
                          MapUtil.domResTo(SetUtil.set(i.getCoord().x), Utils.copy(tempColumn));
                      if (!(Utils.empty(tempMap))) {
                        {
                          VDMSeq m = null;
                          Boolean success_3 = false;
                          VDMSet set_3 = MapUtil.rng(Utils.copy(tempMap));
                          for (Iterator iterator_3 = set_3.iterator();
                              iterator_3.hasNext() && !(success_3);
                              ) {
                            m = ((VDMSeq) iterator_3.next());
                            success_3 = true;
                          }
                          if (!(success_3)) {
                            throw new RuntimeException("Let Be St found no applicable bindings");
                          }

                          {
                            tempSeq = Utils.copy(m);
                            tempSeq = SeqUtil.conc(SeqUtil.seq(number), Utils.copy(tempSeq));
                            tempColumn =
                                MapUtil.override(
                                    Utils.copy(tempColumn),
                                    MapUtil.map(new Maplet(i.getCoord().x, Utils.copy(tempSeq))));
                          }
                        }

                      } else {
                        tempSeq = SeqUtil.seq(number);
                        tempColumn =
                            MapUtil.override(
                                Utils.copy(tempColumn),
                                MapUtil.map(new Maplet(i.getCoord().x, Utils.copy(tempSeq))));
                      }

                      tempMap = MapUtil.domResTo(SetUtil.set(i.getCoord().y), Utils.copy(tempRow));
                      if (!(Utils.empty(tempMap))) {
                        {
                          VDMSeq m = null;
                          Boolean success_4 = false;
                          VDMSet set_4 = MapUtil.rng(Utils.copy(tempMap));
                          for (Iterator iterator_4 = set_4.iterator();
                              iterator_4.hasNext() && !(success_4);
                              ) {
                            m = ((VDMSeq) iterator_4.next());
                            success_4 = true;
                          }
                          if (!(success_4)) {
                            throw new RuntimeException("Let Be St found no applicable bindings");
                          }

                          {
                            tempSeq = Utils.copy(m);
                            tempSeq = SeqUtil.conc(SeqUtil.seq(number), Utils.copy(tempSeq));
                            tempRow =
                                MapUtil.override(
                                    Utils.copy(tempRow),
                                    MapUtil.map(new Maplet(i.getCoord().y, Utils.copy(tempSeq))));
                          }
                        }

                      } else {
                        tempSeq = SeqUtil.seq(number);
                        tempRow =
                            MapUtil.override(
                                Utils.copy(tempRow),
                                MapUtil.map(new Maplet(i.getCoord().y, Utils.copy(tempSeq))));
                      }

                      whileController = true;
                    }
                  }
                }

              } else {
                cell = i;
                cell.number = number;
                setCellOfBoard(i.getCoord(), cell);
                tempMap = MapUtil.domResTo(SetUtil.set(i.getCoord().x), Utils.copy(tempColumn));
                if (!(Utils.empty(tempMap))) {
                  {
                    VDMSeq m = null;
                    Boolean success_5 = false;
                    VDMSet set_5 = MapUtil.rng(Utils.copy(tempMap));
                    for (Iterator iterator_5 = set_5.iterator();
                        iterator_5.hasNext() && !(success_5);
                        ) {
                      m = ((VDMSeq) iterator_5.next());
                      success_5 = true;
                    }
                    if (!(success_5)) {
                      throw new RuntimeException("Let Be St found no applicable bindings");
                    }

                    {
                      tempSeq = Utils.copy(m);
                      tempSeq = SeqUtil.conc(SeqUtil.seq(number), Utils.copy(tempSeq));
                      tempColumn =
                          MapUtil.override(
                              Utils.copy(tempColumn),
                              MapUtil.map(new Maplet(i.getCoord().x, Utils.copy(tempSeq))));
                    }
                  }

                } else {
                  tempSeq = SeqUtil.seq(number);
                  tempColumn =
                      MapUtil.override(
                          Utils.copy(tempColumn),
                          MapUtil.map(new Maplet(i.getCoord().x, Utils.copy(tempSeq))));
                }

                tempMap = MapUtil.domResTo(SetUtil.set(i.getCoord().y), Utils.copy(tempRow));
                if (!(Utils.empty(tempMap))) {
                  {
                    VDMSeq m = null;
                    Boolean success_6 = false;
                    VDMSet set_6 = MapUtil.rng(Utils.copy(tempMap));
                    for (Iterator iterator_6 = set_6.iterator();
                        iterator_6.hasNext() && !(success_6);
                        ) {
                      m = ((VDMSeq) iterator_6.next());
                      success_6 = true;
                    }
                    if (!(success_6)) {
                      throw new RuntimeException("Let Be St found no applicable bindings");
                    }

                    {
                      tempSeq = Utils.copy(m);
                      tempSeq = SeqUtil.conc(SeqUtil.seq(number), Utils.copy(tempSeq));
                      tempRow =
                          MapUtil.override(
                              Utils.copy(tempRow),
                              MapUtil.map(new Maplet(i.getCoord().y, Utils.copy(tempSeq))));
                    }
                  }

                } else {
                  tempSeq = SeqUtil.seq(number);
                  tempRow =
                      MapUtil.override(
                          Utils.copy(tempRow),
                          MapUtil.map(new Maplet(i.getCoord().y, Utils.copy(tempSeq))));
                }

                whileController = true;
              }

            } else {
              {
                VDMSeq j = null;
                Boolean success_7 = false;
                VDMSet set_7 = MapUtil.rng(Utils.copy(tempMap));
                for (Iterator iterator_7 = set_7.iterator();
                    iterator_7.hasNext() && !(success_7);
                    ) {
                  j = ((VDMSeq) iterator_7.next());
                  success_7 = true;
                }
                if (!(success_7)) {
                  throw new RuntimeException("Let Be St found no applicable bindings");
                }

                {
                  tempSeq = Utils.copy(j);
                  if (!(SetUtil.inSet(number, SeqUtil.elems(Utils.copy(j))))) {
                    tempMap = MapUtil.domResTo(SetUtil.set(i.getCoord().y), Utils.copy(tempRow));
                    if (!(Utils.empty(tempMap))) {
                      {
                        VDMSeq k = null;
                        Boolean success_8 = false;
                        VDMSet set_8 = MapUtil.rng(Utils.copy(tempMap));
                        for (Iterator iterator_8 = set_8.iterator();
                            iterator_8.hasNext() && !(success_8);
                            ) {
                          k = ((VDMSeq) iterator_8.next());
                          success_8 = true;
                        }
                        if (!(success_8)) {
                          throw new RuntimeException("Let Be St found no applicable bindings");
                        }

                        {
                          tempSeq = Utils.copy(k);
                          if (!(SetUtil.inSet(number, SeqUtil.elems(Utils.copy(k))))) {
                            cell = i;
                            cell.number = number;
                            setCellOfBoard(i.getCoord(), cell);
                            tempMap =
                                MapUtil.domResTo(
                                    SetUtil.set(i.getCoord().x), Utils.copy(tempColumn));
                            if (!(Utils.empty(tempMap))) {
                              {
                                VDMSeq m = null;
                                Boolean success_9 = false;
                                VDMSet set_9 = MapUtil.rng(Utils.copy(tempMap));
                                for (Iterator iterator_9 = set_9.iterator();
                                    iterator_9.hasNext() && !(success_9);
                                    ) {
                                  m = ((VDMSeq) iterator_9.next());
                                  success_9 = true;
                                }
                                if (!(success_9)) {
                                  throw new RuntimeException(
                                      "Let Be St found no applicable bindings");
                                }

                                {
                                  tempSeq = Utils.copy(m);
                                  tempSeq = SeqUtil.conc(SeqUtil.seq(number), Utils.copy(tempSeq));
                                  tempColumn =
                                      MapUtil.override(
                                          Utils.copy(tempColumn),
                                          MapUtil.map(
                                              new Maplet(i.getCoord().x, Utils.copy(tempSeq))));
                                }
                              }

                            } else {
                              tempSeq = SeqUtil.seq(number);
                              tempColumn =
                                  MapUtil.override(
                                      Utils.copy(tempColumn),
                                      MapUtil.map(new Maplet(i.getCoord().x, Utils.copy(tempSeq))));
                            }

                            tempMap =
                                MapUtil.domResTo(SetUtil.set(i.getCoord().y), Utils.copy(tempRow));
                            if (!(Utils.empty(tempMap))) {
                              {
                                VDMSeq m = null;
                                Boolean success_10 = false;
                                VDMSet set_10 = MapUtil.rng(Utils.copy(tempMap));
                                for (Iterator iterator_10 = set_10.iterator();
                                    iterator_10.hasNext() && !(success_10);
                                    ) {
                                  m = ((VDMSeq) iterator_10.next());
                                  success_10 = true;
                                }
                                if (!(success_10)) {
                                  throw new RuntimeException(
                                      "Let Be St found no applicable bindings");
                                }

                                {
                                  tempSeq = Utils.copy(m);
                                  tempSeq = SeqUtil.conc(SeqUtil.seq(number), Utils.copy(tempSeq));
                                  tempRow =
                                      MapUtil.override(
                                          Utils.copy(tempRow),
                                          MapUtil.map(
                                              new Maplet(i.getCoord().y, Utils.copy(tempSeq))));
                                }
                              }

                            } else {
                              tempSeq = SeqUtil.seq(number);
                              tempRow =
                                  MapUtil.override(
                                      Utils.copy(tempRow),
                                      MapUtil.map(new Maplet(i.getCoord().y, Utils.copy(tempSeq))));
                            }

                            whileController = true;
                          }
                        }
                      }

                    } else {
                      cell = i;
                      cell.number = number;
                      setCellOfBoard(i.getCoord(), cell);
                      tempMap =
                          MapUtil.domResTo(SetUtil.set(i.getCoord().x), Utils.copy(tempColumn));
                      if (!(Utils.empty(tempMap))) {
                        {
                          VDMSeq m = null;
                          Boolean success_11 = false;
                          VDMSet set_11 = MapUtil.rng(Utils.copy(tempMap));
                          for (Iterator iterator_11 = set_11.iterator();
                              iterator_11.hasNext() && !(success_11);
                              ) {
                            m = ((VDMSeq) iterator_11.next());
                            success_11 = true;
                          }
                          if (!(success_11)) {
                            throw new RuntimeException("Let Be St found no applicable bindings");
                          }

                          {
                            tempSeq = Utils.copy(m);
                            tempSeq = SeqUtil.conc(SeqUtil.seq(number), Utils.copy(tempSeq));
                            tempColumn =
                                MapUtil.override(
                                    Utils.copy(tempColumn),
                                    MapUtil.map(new Maplet(i.getCoord().x, Utils.copy(tempSeq))));
                          }
                        }

                      } else {
                        tempSeq = SeqUtil.seq(number);
                        tempColumn =
                            MapUtil.override(
                                Utils.copy(tempColumn),
                                MapUtil.map(new Maplet(i.getCoord().x, Utils.copy(tempSeq))));
                      }

                      tempMap = MapUtil.domResTo(SetUtil.set(i.getCoord().y), Utils.copy(tempRow));
                      if (!(Utils.empty(tempMap))) {
                        {
                          VDMSeq m = null;
                          Boolean success_12 = false;
                          VDMSet set_12 = MapUtil.rng(Utils.copy(tempMap));
                          for (Iterator iterator_12 = set_12.iterator();
                              iterator_12.hasNext() && !(success_12);
                              ) {
                            m = ((VDMSeq) iterator_12.next());
                            success_12 = true;
                          }
                          if (!(success_12)) {
                            throw new RuntimeException("Let Be St found no applicable bindings");
                          }

                          {
                            tempSeq = Utils.copy(m);
                            tempSeq = SeqUtil.conc(SeqUtil.seq(number), Utils.copy(tempSeq));
                            tempRow =
                                MapUtil.override(
                                    Utils.copy(tempRow),
                                    MapUtil.map(new Maplet(i.getCoord().y, Utils.copy(tempSeq))));
                          }
                        }

                      } else {
                        tempSeq = SeqUtil.seq(number);
                        tempRow =
                            MapUtil.override(
                                Utils.copy(tempRow),
                                MapUtil.map(new Maplet(i.getCoord().y, Utils.copy(tempSeq))));
                      }

                      whileController = true;
                    }
                  }
                }
              }
            }

            if (Utils.equals(timeout, Board.BOARD_SIZE.longValue() - 1L)) {
              setNumbersUnpaintedCell();
              return;
            }

            timeout = timeout.longValue() + 1L;
          }
        }

        timeout = 0L;
      }
    }
    Boolean whileCond_3 = true;
    while (whileCond_3) {
      whileCond_3 = !(Utils.equals(rules.checkRule3(Utils.copy(board), Board.BOARD_SIZE), true));
      if (!(whileCond_3)) {
        break;
      }

      {
        /* skip */
      }
    }
  }

  public void fillBoard() {

    Number number = 0L;
    Cell cell = null;
    Number randNum = 0L;
    for (Iterator iterator_26 = MapUtil.rng(Utils.copy(board)).iterator();
        iterator_26.hasNext();
        ) {
      Cell i = (Cell) iterator_26.next();
      if (Utils.equals(i.color, Hitori.quotes.BLACKQuote.getInstance())) {
        Boolean whileCond_4 = true;
        while (whileCond_4) {
          whileCond_4 = Utils.equals(number, 0L);
          if (!(whileCond_4)) {
            break;
          }

          {
            randNum = MATH.rand(Board.BOARD_SIZE);
            if (i.getCoord().x.longValue() >= i.getCoord().y.longValue()) {
              number = getCellOfBoard(new Cell.Coord(i.getCoord().x, randNum)).number;

            } else {
              number = getCellOfBoard(new Cell.Coord(randNum, i.getCoord().y)).number;
            }
          }
        }

        cell = i;
        cell.number = number;
        cell.color = Hitori.quotes.WHITEQuote.getInstance();
        setCellOfBoard(i.getCoord(), cell);
        number = 0L;
      }
    }
  }

  public Board() {}

  public String toString() {

    return "Board{"
        + "BOARD_SIZE := "
        + Utils.toString(BOARD_SIZE)
        + ", rules := "
        + Utils.toString(rules)
        + ", board := "
        + Utils.toString(board)
        + ", colorWhite := "
        + Utils.toString(colorWhite)
        + ", rule1 := "
        + Utils.toString(rule1)
        + ", rule2 := "
        + Utils.toString(rule2)
        + ", rule3 := "
        + Utils.toString(rule3)
        + "}";
  }
}
