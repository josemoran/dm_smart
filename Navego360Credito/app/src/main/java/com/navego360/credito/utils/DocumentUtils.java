package com.navego360.credito.utils;

import android.util.Log;

import com.navego360.credito.variables.PrintCommands;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.navego360.credito.utils.DecimalFormatUtils.percentageFormat;
import static com.navego360.credito.utils.DecimalFormatUtils.twoDigitsFormat;
import static com.navego360.credito.utils.ExcelUtils.EOMONTH;
import static com.navego360.credito.utils.ExcelUtils.calculateAdjustedCapital;
import static com.navego360.credito.utils.ExcelUtils.calculateQuota;
import static com.navego360.credito.utils.ExcelUtils.calculateTea;

public class DocumentUtils {

//    public static List<byte[]> firstDocument(){
//        return null;
//    }

    public static List<byte[]> secondDocument(boolean sendExUnit, String capital, String numQuotas,
                                              String startDate, String flat, String tim,
                                              String creditType, String disbursement, String tcea,
                                              String disgrace, String userName, String userDoc)
            throws ParseException {

        List<byte[]> data = new ArrayList<>();

        Date paymentDate = DateUtils.convertDate(startDate, DateUtils.formatDate4);

        double finalTim = Double.parseDouble(tim)/100;
        double tea = calculateTea(finalTim);

        double finalFlat = Double.parseDouble(flat)/100;

        int finalNumQuotas = Integer.parseInt(numQuotas);
        int numGracePeriods = 0;
        int numMonths = finalNumQuotas + numGracePeriods;

        long graceDaysPayout = DateUtils.daysDifference(EOMONTH(paymentDate, (sendExUnit ? 1 : 0)), paymentDate);
        int graceDaysQuotas = 0;
        int totalGraceDays = (int) (graceDaysPayout + graceDaysQuotas);

        double finalCapital = Double.parseDouble(capital);
        double adjustedCapital = calculateAdjustedCapital(finalCapital, tea, totalGraceDays);
        double quota = calculateQuota(adjustedCapital, finalTim, finalFlat, finalNumQuotas);

        ///////////////////////////////////////// IMPRESION ///////////////////////////////////////
        byte[] emphasizedCommand;
        emphasizedCommand = new byte[] { 0x1b, 0x45, 0x00 };
        emphasizedCommand[2] = 1; // INDICAR NEGRITA
        data.add(emphasizedCommand);
        data.add("       RESUMEN DE DATOS DE PAGO\n\n".getBytes());
        data.add(new byte[] { 0x1b, 0x46 });
        emphasizedCommand = new byte[] { 0x1b, 0x45, 0x00 };
        emphasizedCommand[2] = 0;// Simple
        data.add(emphasizedCommand);

        data.add(("Nro. de Credito       : 24-00530-2014\n".getBytes()));
        data.add(("Fecha Aprobacion      : " + startDate + "\n").getBytes());
        data.add(("Monto Otorgado (S./)  : " + twoDigitsFormat(adjustedCapital) + "\n").getBytes());
        data.add(("Plazo (meses)         : " + numMonths  + "\n").getBytes());
        data.add(("Tipo de Credito       : " + creditType.toUpperCase()  + "\n").getBytes());
        data.add(("Forma de Desembolso   : " + disbursement.toUpperCase()  + "\n").getBytes());
        data.add(("Tasa Efectiva Anual   : " + percentageFormat(tea*100)  + "\n").getBytes());
        data.add(("T.Costo Efectivo Anual: " + percentageFormat(tcea)  + "\n").getBytes());
        data.add(("Comision Gastos Admin.: " + percentageFormat("4.36")  + "\n").getBytes());
        data.add(("Fondo Desgravamen     : " + percentageFormat(disgrace)  + "\n").getBytes());
        data.add(("Forma de descuento    : PLANILLA MINISTERIAL\n".getBytes()));
        data.add("------------------------------------------------\n".getBytes());

        emphasizedCommand = new byte[] { 0x1b, 0x45, 0x00 };
        emphasizedCommand[2] = 1; // INDICAR NEGRITA
        data.add(emphasizedCommand);
        data.add(" F.Vcto.   Cuota   Capital  Interes F.Des Gastos\n".getBytes());
        data.add(new byte[] { 0x1b, 0x46 });
        emphasizedCommand = new byte[] { 0x1b, 0x45, 0x00 };
        emphasizedCommand[2] = 0;// Simple
        data.add(emphasizedCommand);
        data.add("------------------------------------------------\n".getBytes());

        double flatValue = (adjustedCapital*finalFlat)/finalNumQuotas;

        double lastInteres, lastAmortization;
        double lastCapital = adjustedCapital;
        Date lastDate = paymentDate;

//        Log.e("PRINT"," F.Vcto.   Cuota   Capital  Interes F.Des Gastos");
        double totalQuota = 0, totalCapital = 0, totalInteres = 0;//, totalDes = 0, totalGastos = 0;
        double totalAmortizacion = 0, totalFlat = 0;

        Log.e("PRINT","Nro  F.Vcto.   Amortizacion     Capital       Interes      Flat      Cuota");
        for(int i = 0; i < numMonths; i++){
            Date date;
            if(i == 0) date = EOMONTH(lastDate, (sendExUnit ? 2 : 1));
            else date = EOMONTH(lastDate, 1);
            lastDate = date;

            lastInteres = lastCapital * finalTim;
            lastAmortization = quota - flatValue - lastInteres;
            lastCapital =  lastCapital - lastAmortization;

            totalCapital += lastCapital;
            totalInteres += lastInteres;
            totalQuota += quota;

            totalAmortizacion += lastAmortization;
            totalFlat += flatValue;

            String lastDateString = DateUtils.convertDate(lastDate, DateUtils.formatDate4);
            String lastAmortizationString = DecimalFormatUtils.twoDigitsFormat(lastAmortization);
            String lastCapitalString = DecimalFormatUtils.twoDigitsFormat(lastCapital);
            String lastInteresString = DecimalFormatUtils.twoDigitsFormat(lastInteres);
            String flatValueString = DecimalFormatUtils.twoDigitsFormat(flatValue);
            String quotaString = DecimalFormatUtils.twoDigitsFormat(quota);

            Log.e("PRINT"," "+(i+1) + "  " + lastDateString + "      " + lastAmortizationString + "         "
                    + lastCapitalString + "        " + lastInteresString + "        "
                    + flatValueString + "      " + quotaString);

//            data.add("--------  -------   ------   ------  ----  ----\n".getBytes());
        }

        String totalAmortizacionString = DecimalFormatUtils.twoDigitsFormat(totalAmortizacion);
        String totalCapitalString = DecimalFormatUtils.twoDigitsFormat(totalCapital);
        String totalInteresString = DecimalFormatUtils.twoDigitsFormat(totalInteres);
        String totalFlatString = DecimalFormatUtils.twoDigitsFormat(totalFlat);
        String totalQuotaString = DecimalFormatUtils.twoDigitsFormat(totalQuota);

//        data.add("------------------------------------------------\n".getBytes());
        Log.e("PRINT","------------------------------------------------------------------------------------------------");
        Log.e("PRINT","TOTAL :              " + totalAmortizacionString + "        "
                + totalCapitalString + "      " + totalInteresString + "       "
                + totalFlatString + "      " + totalQuotaString);

//        data.add("TOTAL :   --------  -------  ------- ----- -----\n".getBytes());
//        data.add("------------------------------------------------\n".getBytes());

        emphasizedCommand = new byte[] { 0x1b, 0x45, 0x00 };
        emphasizedCommand[2] = 1; // INDICAR NEGRITA
        data.add(emphasizedCommand);
        data.add("Apellidos y Nombres: \n".getBytes());
        data.add(new byte[] { 0x1b, 0x46 });
        emphasizedCommand = new byte[] { 0x1b, 0x45, 0x00 };
        emphasizedCommand[2] = 0;// Simple
        data.add(emphasizedCommand);
        data.add((userName.toUpperCase()+"\n").getBytes());

        emphasizedCommand = new byte[] { 0x1b, 0x45, 0x00 };
        emphasizedCommand[2] = 1; // INDICAR NEGRITA
        data.add(emphasizedCommand);
        data.add("D.N.I.: ".getBytes());
        data.add(new byte[] { 0x1b, 0x46 });
        emphasizedCommand = new byte[] { 0x1b, 0x45, 0x00 };
        emphasizedCommand[2] = 0;// Simple
        data.add(emphasizedCommand);
        data.add((userDoc + "\n\n\n").getBytes());

        data.add("---------------------\n".getBytes());
        data.add("       (Firma)       \n\n\n".getBytes());
        data.add(PrintCommands.CANCEL_EXPAND_COMMAND); // Cancel Character Expansion
        data.add(PrintCommands.CUT_PAPER_COMMAND); // Cut
        data.add(PrintCommands.BEL_COMMAND); // Kick cash drawer

//        TIM
//        TEA
//        MONTO DE CUOTA
        return data;
    }
}
