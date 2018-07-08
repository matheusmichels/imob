package view.com.imob.view.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import view.com.imob.view.parts.Prompt;

/**
 * @author mm
 */
public abstract class ReportToolkit 
{
    protected BaseColor color = new BaseColor( 96, 125, 139 );
    
    protected Font fontHead     = new Font( Font.FontFamily.HELVETICA  , 10, Font.BOLD, BaseColor.WHITE );
    protected Font fontRow      = new Font( Font.FontFamily.HELVETICA  , 8,  Font.NORMAL );
    protected Font fontTitle    = new Font( Font.FontFamily.HELVETICA  , 18, Font.BOLD, color );
    protected Font fontSubTitle = new Font( Font.FontFamily.HELVETICA  , 14, Font.BOLD, color );
     
    protected DateFormat df   = new SimpleDateFormat( "dd/MM/yyyy" );
    protected NumberFormat nf = NumberFormat.getCurrencyInstance( new Locale( "pt", "BR" ) );
    
    protected Document document;
    protected PdfWriter writer;
    
    public ReportToolkit() 
    {
        this( false );
    }
    
    
    public ReportToolkit( boolean rotate )
    {
        if( rotate )
        {
            document = new Document( PageSize.A4.rotate(), 36f, 36f, 36f, 36f );
        }
        
        else
        {
            document = new Document( PageSize.A4, 36f, 36f, 36f, 36f );
        }
    }
    
    protected void setTitle( String title ) throws Exception
    {
        setTitle( title, null );
    }

    protected void setTitle( String title, String icon ) throws Exception
    {
        Paragraph paragraph = new Paragraph();
        paragraph.add( new Chunk( title, fontTitle ) );
        paragraph.setAlignment( Paragraph.ALIGN_CENTER );
        paragraph.setSpacingAfter( 2f );
        
        if( icon != null )
        {
            PdfPCell titleCell = new PdfPCell();
            titleCell.addElement( paragraph );
            titleCell.setPadding( 0 );
            titleCell.setHorizontalAlignment( PdfPCell.ALIGN_RIGHT );
            titleCell.setBorder( PdfPCell.NO_BORDER );

            PdfPCell iconCell = new PdfPCell();
            iconCell.setPadding( 0 );
            iconCell.setHorizontalAlignment( PdfPCell.ALIGN_RIGHT );
            iconCell.setBorder( PdfPCell.NO_BORDER );
            Image img = Image.getInstance( ResourceLocator.getImage( icon ) );
            img.scaleAbsolute( 40, 40 );
            img.setAlignment( Image.ALIGN_RIGHT );
            iconCell.addElement( img );

            PdfPTable titleTable = new PdfPTable( new float[]{ 97f, 3f } );
            titleTable.setSpacingAfter( 2f );
            titleTable.setWidthPercentage( 100 );
            titleTable.addCell( titleCell );
            titleTable.addCell( iconCell );
            
            document.add( titleTable );
        }
        
        else
        {
            document.add( paragraph );
        }
    }

    
    
    protected void setSubtitle( String subTitle ) throws Exception
    {
        newLine();
        Paragraph paragraph = new Paragraph();
        paragraph.add( new Chunk( subTitle, fontSubTitle ) );
        paragraph.setAlignment( Paragraph.ALIGN_CENTER );
        paragraph.setSpacingAfter( 2f );
        
        document.add( paragraph );
    }

    
    protected void separator() throws Exception
    {
        PdfPTable separator = new PdfPTable( 1 );
        separator.setWidthPercentage( 100 );
        
        PdfPCell cell = new PdfPCell();
        cell.setPadding( 0 );
        cell.setBorder( PdfPCell.BOTTOM );
        cell.setBorderWidth( 2f );
        cell.setBorderColor( color );
        cell.setFixedHeight( 10f );
        
        separator.addCell( cell );
        
        document.add( separator );
    }

    
    protected void newLine() throws Exception
    {
        document.add( new Phrase( "\n" ) );
    }
    
    
    protected void newPage()
    {
        document.newPage();
    }
    
    
    protected void addHTML( String html ) throws Exception
    {
        XMLWorkerHelper.parseToElementList( html.replaceAll( "<br>", "<br></br>"), null ).forEach( element -> 
        {
            try
            {
                if( element instanceof Paragraph )
                {
                    Paragraph paragraph = (Paragraph) element;
                    paragraph.setAlignment( Paragraph.ALIGN_CENTER );
                    paragraph.setSpacingAfter( 2f );
                    paragraph.setFont( fontSubTitle );

                    document.add( paragraph );
                }
                
                else if ( element instanceof List )
                {
                    List list = (List) element;

                    document.add( list );
                    
                }
            }
            catch( Exception e )
            {
                ApplicationUtilities.handleException( e );
            }
        } );
    }

    
    private void printFooter( PdfWriter writer )
    {
        String user = ApplicationUtilities.getInstance().getCurrentUser().getName();
        String date = df.format( new Date( System.currentTimeMillis() ) );
        
        FooterEvent event = new FooterEvent( user, date );
        
        writer.setPageEvent( event );
    }
    
    
    public void generatePDF( File file ) throws Exception
    {
        Platform.runLater( new Runnable() 
        {
            @Override
            public void run() 
            {
                try 
                {
                    writer = PdfWriter.getInstance( document, new FileOutputStream( file ) );

                    printFooter( writer );
                    
                    document.open();

                    document.addAuthor( "Matheus Michels" );
                    document.addCreator( "iMob - Gestão Imobiliária" );
                    document.addTitle( "iMob Report" );

                    generateDocument( document );

                    document.close();

                    Notifications pdfNotification = Notifications.create()
                    .title( "Relatório Gerado!" )
                    .text( "Clique aqui para abrir" )
                    .graphic( new ImageView( new javafx.scene.image.Image( ResourceLocator.getImage( "pdf.png" ) ) ) )
                    .position( Pos.BOTTOM_RIGHT )
                    .hideAfter( Duration.seconds( 10 ) )
                    .onAction( new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event)
                        {
                            try
                            {
                                FileUtilities.open( file );
                            }

                            catch ( Exception e )
                            {
                                Prompt.exception( e );
                            }
                        }
                    } );
                    
                    pdfNotification.show();
                }

                catch ( Exception e )
                {
                    ApplicationUtilities.handleException( e );
                }
            }
        } );
    }

   
    protected abstract void generateDocument( Document document ) throws Exception;
    
    
    
    protected class Table
        extends 
            PdfPTable
    {
        private BaseColor background  = new BaseColor( 144, 164, 174 ); //90A4AE    
        
        public Table( float... widths ) throws Exception
        {
            super( widths );
            setWidthPercentage( 100f );
        }
        
        public void setHeader( String... header )
        {
            for( String head : header )
            {
                PdfPCell cell = new PdfPCell();
                cell.setBorderColor( background );
                cell.setBorderWidth( 1.5f );
                cell.setBackgroundColor( background );

                Paragraph paragraph = new Paragraph();
                paragraph.add( new Chunk( head, fontHead ) );
                paragraph.setAlignment( Paragraph.ALIGN_CENTER );
                paragraph.setSpacingAfter( 5f );

                cell.addElement( paragraph );
                
                addCell( cell );
            }
        }
        
        public void addRow( Object... row )
        {
            for( Object r : row )
            {
                PdfPCell cell = new PdfPCell();
                cell.setBorderColor( background );
                cell.setBorderWidth( 1 );
                cell.setPaddingLeft( 3f );
                
                if ( r instanceof Image )
                {
                    Image img = (Image) r;
                    img.scaleAbsolute( 10, 10 );
                    img.setSpacingBefore( 4f );
                    img.setAlignment( Paragraph.ALIGN_MIDDLE );
                    cell.addElement( img );
                }
                
                else if ( r instanceof PdfPCell )
                {
                    cell = (PdfPCell) r;
                }

                else 
                {
                    Paragraph paragraph = new Paragraph( String.valueOf( r ), fontRow );
                    paragraph.setSpacingAfter( 5f );
                    cell.addElement(paragraph);
                }


                addCell( cell );
            }
        }
    }
    

    
    protected class DetailsTable
        extends 
            PdfPTable
    {
        private BaseColor background2 = new BaseColor( 207, 216, 220 ); //#CFD8DC
        private BaseColor background  = new BaseColor( 144, 164, 174 ); //90A4AE    
        private BaseColor fontColor   = new BaseColor( 38, 50, 56 ); //263238
        private BaseColor fontColor2  = new BaseColor( 55, 71, 79 ); //263238
        
        private boolean formatCelll = true;
        
        public DetailsTable( float... widths ) throws Exception
        {
            super( ( widths.length * 2 ) -1 );
            
            float heads [] = new float[ ( widths.length * 2 ) -1 ];
            
            int j = 0;
            
            for ( int i = 0; i < heads.length; i++ )
            {
                if( i % 2 == 0 )
                {
                    heads[i] = widths[j];
                    j++;
                }
                
                else
                {
                    heads[i] = 1f;
                }
            }
            
            setWidths( heads );
            
            setWidthPercentage( 100f );
            
            addSeparatorRow();
        }
        
        public void setHeader( String... header )
        {
            for( int i = 0; i < header.length; i++ )
            {
                String head = header[i];
                
                PdfPCell cell = new PdfPCell();
                cell.setBorderColor( background );
                cell.setBorderWidth( 1.5f );
                cell.setBackgroundColor( background );

                Paragraph paragraph = new Paragraph();
                paragraph.add( new Chunk( head, new Font( Font.FontFamily.HELVETICA  , 12, Font.BOLD, BaseColor.WHITE ) ) );
                paragraph.setAlignment( Paragraph.ALIGN_CENTER );
                paragraph.setSpacingAfter( 5f );

                cell.addElement( paragraph );
                
                addCell( cell );
            
                if( i != header.length -1 )
                {
                    addSeparatorColumn();
                }
            }
            
            addSeparatorRow();
        }
        
        public void addRow( Object... row )
        {
            for ( int i = 0; i < row.length; i++ )
            {
                Object column = row[i];
                
                PdfPCell cell = new PdfPCell();
                cell.setBackgroundColor( BaseColor.WHITE );            
                cell.setBorderWidth( 1.5f );

                if( i == 0 && formatCelll )
                {
                    cell.setBackgroundColor( background );
                    cell.setBorderColor( background );
                    
                    if( column instanceof String )
                    {
                        Paragraph paragraph = new Paragraph();
                        paragraph.setAlignment( Paragraph.ALIGN_MIDDLE );
                        paragraph.setIndentationLeft( 5f );
                        paragraph.setSpacingAfter( 5f );
                        paragraph.add( new Chunk( column + ":", new Font( Font.FontFamily.HELVETICA  , 12, Font.BOLD, BaseColor.WHITE ) ) );
                        cell.addElement( paragraph );
                    }

                    else if( column instanceof Image )
                    {
                        Image img = (Image) column;
                        img.scaleAbsolute( 10, 10 );
                        img.setSpacingBefore( 4f );
                        img.setAlignment( Paragraph.ALIGN_MIDDLE );
                        cell.addElement( img );
                    }
                }

                else
                {
                     cell.setBackgroundColor( background2 );
                     cell.setBorderColor( background2 );
                     
                    if( column instanceof String )
                    {
                        Font font = new Font( Font.FontFamily.HELVETICA  , 10, Font.BOLD, fontColor2 );
                        Paragraph paragraph = new Paragraph();
                        paragraph.setAlignment( Paragraph.ALIGN_MIDDLE );
                        paragraph.setSpacingAfter( 5f );
                        paragraph.setIndentationLeft( 5f );
                        paragraph.add( new Chunk( (String)column, font ) );
                        cell.addElement( paragraph );
                    }

                    else if( column instanceof Image )
                    {
                        Image img = (Image) column;
                        img.scaleAbsolute( 10, 10 );
                        img.setSpacingBefore( 4f );
                        img.setAlignment( Paragraph.ALIGN_MIDDLE );
                        cell.addElement( img );
                    }
                }

                addCell( cell );
                
                if( i != row.length -1 )
                {
                    addSeparatorColumn();
                }
            }
            
            addSeparatorRow();
        }


        private void addSeparatorColumn()
        {
            PdfPCell cell = new PdfPCell();
            cell.setBorder( PdfPCell.NO_BORDER );

            addCell( cell ); 
        }

        private void addSeparatorRow()
        {
            for( int i =0; i < getAbsoluteWidths().length; i++ )
            {
                PdfPCell cell = new PdfPCell();
                cell.setBorder( PdfPCell.NO_BORDER );
                cell.setFixedHeight( 5f );
                addCell( cell );
            }
        }

        public BaseColor getBackground() 
        {
            return background;
        }

        public void setBackground( BaseColor background )
        {
            this.background = background;
        }

        public boolean isFormatCelll() 
        {
            return formatCelll;
        }

        public void setFormatCelll( boolean formatCelll )
        {
            this.formatCelll = formatCelll;
        }
    }
}
