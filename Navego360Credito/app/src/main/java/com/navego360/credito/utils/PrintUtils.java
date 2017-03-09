package com.navego360.credito.utils;

import android.content.Context;

import com.starmicronics.stario.PortInfo;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;
import com.starmicronics.stario.StarPrinterStatus;

import java.util.ArrayList;
import java.util.List;

public class PrintUtils {

    // Metodo que realiza la impresion
    public static void sendCommand(Context context, String portName, String portSettings,
                                   List<byte[]> byteList) {
        StarIOPort port;
        try {
            port = StarIOPort.getPort(portName, portSettings, 10000, context);
            StarPrinterStatus status = port.beginCheckedBlock();
            if (status.offline) {
                throw new StarIOPortException("La impresora está desconectada");
            }
            byte[] commandToSendToPrinter = convertFromListByteArrayTobyteArray(byteList);
            port.writePort(commandToSendToPrinter, 0, commandToSendToPrinter.length);
            port.setEndCheckedBlockTimeoutMillis(30000);// Change the timeout time of endCheckedBlock method.
        } catch (StarIOPortException e) {
            e.printStackTrace();
        }
    }

    // Conversion de lista de bytes[] a un solo bytes[]
    private static byte[] convertFromListByteArrayTobyteArray(List<byte[]> ByteArray){
        int dataLength = 0;
        for (int i = 0; i < ByteArray.size(); i++) {
            dataLength += ByteArray.get(i).length;
        }
        int distPosition = 0;
        byte[] byteArray = new byte[dataLength];
        for (int i = 0; i < ByteArray.size(); i++) {
            System.arraycopy(ByteArray.get(i), 0, byteArray, distPosition, ByteArray.get(i).length);
            distPosition += ByteArray.get(i).length;
        }
        return byteArray;
    }

    public static void printDocument(Context context, List<byte[]> list)
            throws StarIOPortException {
        String portName = null;
        List<PortInfo> portList = StarIOPort.searchPrinter("BT:");
        for (PortInfo port : portList) {
            portName=port.getPortName();
        }
        sendCommand(context, portName,  "mini", list);
    }
}
