/* ====================================================================
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
==================================================================== */

package br.com.sose.utils;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;

/**
 * Demonstrates a workaround you can use to generate large workbooks and avoid OutOfMemory exception.
 *
 * The trick is as follows:
 * 1. create a template workbook, create sheets and global objects such as cell styles, number formats, etc.
 * 2. create an application that streams data in a text file
 * 3. Substitute the sheet in the template with the generated data
 *
 * <p>
 *      Since 3.8-beta3 POI provides a low-memory footprint SXSSF API which implementing the "BigGridDemo" strategy.
 *      XSSF is an API-compatible streaming extension of XSSF to be used when
 *      very large spreadsheets have to be produced, and heap space is limited.
 *      SXSSF achieves its low memory footprint by limiting access to the rows that
 *      are within a sliding window, while XSSF gives access to all rows in the
 *      document. Older rows that are no longer in the window become inaccessible,
 *      as they are written to the disk.
 * </p>
 * See <a "http://poi.apache.org/spreadsheet/how-to.html#sxssf">
 *     http://poi.apache.org/spreadsheet/how-to.html#sxssf</a>.

 *
 * @author Yegor Kozlov
 */
public class BigGridDemo {
    private static final String XML_ENCODING = "UTF-8";
    
    public static void main(String[] args) throws Exception {


    }

    /**
     * Create a library of cell styles.
     */
   

    private static void generate(Writer out, Map<String, XSSFCellStyle> styles) throws Exception {

        Random rnd = new Random();
        Calendar calendar = Calendar.getInstance();

        SpreadsheetWriter sw = new SpreadsheetWriter(out);
        sw.beginSheet();

        //insert header row
        sw.insertRow(0);
        int styleIndex = styles.get("header").getIndex();
        sw.createCell(0, "Nº OS", styleIndex);
        sw.createCell(1, "Nº OS Pai", styleIndex);
        sw.createCell(2, "Status", styleIndex);
        sw.createCell(3, "Dias Servilogi", styleIndex);
        sw.createCell(4, "Cliente", styleIndex);
        sw.createCell(5, "Unidade", styleIndex);
        sw.createCell(6, "N/S Fabricante", styleIndex);
        sw.createCell(7, "N/S Cliente", styleIndex);
        sw.createCell(8, "Nº OS Cliente", styleIndex);
        sw.createCell(9, "Fabricante", styleIndex);
        sw.createCell(10, "LPU", styleIndex);
        sw.createCell(11, "Laboratório", styleIndex);
        sw.createCell(12, "Prestador de serviço", styleIndex);
        sw.createCell(13, "Nº NF Entrada", styleIndex);
        sw.createCell(14, "DT NF Emissão", styleIndex);
        sw.createCell(14, "DT Chegada", styleIndex);
        sw.createCell(16, "Valor unitário", styleIndex);
        sw.createCell(17, "Cliente avaya", styleIndex);
        sw.createCell(18, "Case avaya", styleIndex);
        sw.createCell(19, "Nº proposta", styleIndex);
        sw.createCell(20, "DT Proposta", styleIndex);
        sw.createCell(21, "Aprovado/Reprovado", styleIndex);
        sw.createCell(22, "DT Aprovado/Reprovado", styleIndex);
        sw.createCell(23, "Nº NF Saída", styleIndex);
        sw.createCell(24, "DT NF Saída", styleIndex);
        sw.createCell(25, "Observação Recebimento", styleIndex);
        sw.createCell(26, "Observação Orçamento", styleIndex);
        sw.createCell(27, "Observação Proposta", styleIndex);
        sw.createCell(28, "Observação Reparo", styleIndex);
        sw.createCell(29, "Observação Expedição", styleIndex);


        sw.endRow();

        //write data rows
        for (int rownum = 1; rownum < 100000; rownum++) {
            sw.insertRow(rownum);

            sw.createCell(0, "Hello, " + rownum + "!");
            sw.createCell(1, (double)rnd.nextInt(100)/100, styles.get("percent").getIndex());
            sw.createCell(2, (double)rnd.nextInt(10)/10, styles.get("coeff").getIndex());
            sw.createCell(3, rnd.nextInt(10000), styles.get("currency").getIndex());
            sw.createCell(4, calendar, styles.get("date").getIndex());

            sw.endRow();

            calendar.roll(Calendar.DAY_OF_YEAR, 1);
        }
        sw.endSheet();
    }

   

   
}