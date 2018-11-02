package com.example.demo.common;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@DynamoDBDocument
public class Meta {

    @JsonIgnore
    private final DateTimeFormatter DEFAULT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private String pub;
    private int anno;

    //@JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fecha;

    //@JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fechaAnt;
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fechaAntAnt;

    //@JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate fechaSig;
    private String fechaPub;
    private String pubDate;

    @DynamoDBAttribute(attributeName="pub")
    public String getPub() { return pub; }
    public void setPub(String pub) { this.pub = pub; }

    @DynamoDBAttribute(attributeName="anno")
    public int getAnno() { return anno; }
    public void setAnno(int anno) { this.anno = anno; }

   // @DynamoDBAttribute(attributeName="fecha")
//    public LocalDate getFecha() { return fecha; }
//    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

   // @DynamoDBAttribute(attributeName="fechaAnt")
    public LocalDate getFechaAnt() { return fechaAnt; }
    public void setFechaAnt(LocalDate fechaAnt) { this.fechaAnt = fechaAnt; }

   // @DynamoDBAttribute(attributeName="fechaAntAnt")
    public LocalDate getFechaAntAnt() { return fechaAntAnt; }
    public void setFechaAntAnt(LocalDate fechaAntAnt) { this.fechaAntAnt = fechaAntAnt; }

   // @DynamoDBAttribute(attributeName="fechaSig")
    public LocalDate getFechaSig() { return fechaSig; }
    public void setFechaSig(LocalDate fechaSig) { this.fechaSig = fechaSig; }

    @DynamoDBAttribute(attributeName="fechaPub")
    public String getFechaPub() { return fechaPub; }
    public void setFechaPub(String fechaPub) { this.fechaPub = fechaPub; }

    @DynamoDBAttribute(attributeName="pubDate")
    public String getPubDate() { return pubDate; }
    public void setPubDate(String pubDate) { this.pubDate = pubDate; }

    public Meta(Node node) {
        Element e = (Element) node;
        for (int i = 0; i < e.getChildNodes().getLength(); i++) {
            Node child = e.getChildNodes().item(i);
            if (child.hasChildNodes()) {
                String nodeName = child.getNodeName();
                String value = child.getFirstChild().getNodeValue();
                switch (nodeName) {
                    case "pub":
                        this.pub = value;
                        break;
                    case "anno":
                        this.anno = Integer.valueOf(value);
                        break;
                    case "fecha":
                        this.fecha = LocalDate.parse(value, DEFAULT_FORMAT);
                        break;
                    case "fechaAnt":
                        this.fechaAnt = LocalDate.parse(value, DEFAULT_FORMAT);
                        break;
                    case "fechaAntAnt":
                        this.fechaAntAnt = LocalDate.parse(value, DEFAULT_FORMAT);
                        break;
                    case "fechaSig":
                        this.fechaSig = LocalDate.parse(value, DEFAULT_FORMAT);
                        break;
                    case "fechaPub":
                        this.fechaPub = value; break;
                    case "pubDate":
                        this.pubDate = value; break;
                }
            }
        }
    }
}
