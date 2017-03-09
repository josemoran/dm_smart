package com.navego360.credito.variables;

public class PrintCommands {

    /*
     *  Explicacion de comandos:
     **  EMPHASIZED_ON_COMMAND: Enfatizar impresion activado (ESC + E)
     **  EMPHASIZED_OFF_COMMAND: Enfatizar impresion desactivado (ESC + F)
     **  CANCEL_EXPAND_COMMAND: Cancelar expansion de ancho/largo (ESC + i + 'largo' + 'ancho')
     **  CUT_PAPER_COMMAND: Corgar papel (ESC + d + STX) - El equipo no tiene cortado automatico
     **  BEL_COMMAND: Se marca el final de impresion (BEL)
     */

    public  static byte[] EMPHASIZED_ON_COMMAND = new byte[] { 0x1b, 0x45, 0x00 };
    public  static byte[] EMPHASIZED_OFF_COMMAND = new byte[] { 0x1b, 0x46 };
    public  static byte[] CANCEL_EXPAND_COMMAND = new byte[] { 0x1b, 0x69, 0x00, 0x00 };
    public  static byte[] CUT_PAPER_COMMAND = new byte[] { 0x1b, 0x64, 0x02  };
    public  static byte[] BEL_COMMAND = new byte[] { 0x07 };
}
