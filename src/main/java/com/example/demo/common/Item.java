package com.example.demo.common;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Map;

public class Item {

    /**
     *  <item id="BOE-A-2014-10080">
     *                         <titulo>Real Decreto 841/2014, de 3 de octubre, por el que se dispone el cese de doña Mireya Natalia Corredor Lanas como Secretaria General Técnica del Ministerio de Justicia.</titulo>
     *                         <urlPdf szBytes="132878" szKBytes="130" numPag="1">/boe/dias/2014/10/04/pdfs/BOE-A-2014-10080.pdf</urlPdf>
     *                         <urlHtm>/diario_boe/txt.php?id=BOE-A-2014-10080</urlHtm>
     *                         <urlXml>/diario_boe/xml.php?id=BOE-A-2014-10080</urlXml>
     *                     </item>
     */

    private String id;
    private String titulo;
    private Map<URLItem.Type, URLItem> urls;

    public Item(Node xmlNode) {
        Element e = (Element) xmlNode;
    }
}
