/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.com.imob.view.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author artur
 */
public class FooterEvent 
    extends 
        PdfPageEventHelper 
{
    private String footerLeft;
    private String footerRight;
    
    private FooterEvent(){}
            
    public FooterEvent( String footerRight, String footerLeft )
    {
        this.footerLeft = footerLeft;
        this.footerRight = footerRight;
    }
    
    @Override
    public void onEndPage( PdfWriter writer, Document document ) 
    {
        Font font = new Font( Font.FontFamily.HELVETICA  , 10, Font.NORMAL, new BaseColor( 65, 90, 120 ) );
       
        PdfContentByte cb = writer.getDirectContent();
        
        Rectangle rect = writer.getPageSize();
        
        if( footerLeft != null )
        {
            ColumnText.showTextAligned( cb, Element.ALIGN_RIGHT,
                                        new Phrase( footerLeft, font ),
                                        rect.getRight() - document.rightMargin(), rect.getBottom() + 20, 0 );
        }
        
        if( footerRight != null )
        {
             
            ColumnText.showTextAligned( cb, Element.ALIGN_LEFT,
                                        new Phrase( footerRight, font ),
                                        rect.getLeft() + document.leftMargin(), rect.getBottom() + 20, 0 );
        }
        
        ColumnText.showTextAligned( cb, Element.ALIGN_CENTER,
                                    new Phrase( "Este relatório foi gerado por " + ApplicationUtilities.getInstance().getCompany(), font ),
                                    rect.getWidth() / 2, rect.getBottom() + 20, 0 );
    }
}
