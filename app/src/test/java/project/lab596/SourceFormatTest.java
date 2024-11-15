package project.lab596;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class SourceFormatTest {

     /**
     * Ensuring that a SIMPLE and FILE parse format properly calls the SimpleNewsParser and passes data correctly
     * @result the SourceFormat properly calls acceptVisitor while passing in the expected arguments
     */
    @Test
    void testSimpleNewsParsing() throws IOException {
        SourceFormat sf = Mockito.mock(SourceFormat.class);
        
        when(sf.getFormat()).thenReturn(SourceFormat.Format.SIMPLE);
        when(sf.getSource()).thenReturn(SourceFormat.Source.FILE);

        //requires this complex MockStatic since the readAllBytes method used to provide the json is a static method in the Files class
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {

            mockedFiles.when(() -> Files.readAllBytes(any())).thenReturn("testContent".getBytes());
            
            Utils u = new Utils();
            u.callSourceFormat(sf);
            
            verify(sf).acceptVisitor(any(SimpleNewsParser.class), eq("testContent"), any(Logger.class));
        }

    }

    /**
     * Ensuring that a COMPLEX and FILE parse format properly calls the ComplexNewsParser and passes data correctly
     * @result the SourceFormat properly calls acceptVisitor while passing in the expected arguments
     */
    @Test
    void testComplexNewsParsing() throws IOException {
        SourceFormat sf = Mockito.mock(SourceFormat.class);
        
        when(sf.getFormat()).thenReturn(SourceFormat.Format.COMPLEX);
        when(sf.getSource()).thenReturn(SourceFormat.Source.FILE);

        //requires this complex MockStatic since the readAllBytes method used to provide the json is a static method in the Files class
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {

            mockedFiles.when(() -> Files.readAllBytes(any())).thenReturn("testContent".getBytes());
            
            Utils u = new Utils();
            u.callSourceFormat(sf);
            
            verify(sf).acceptVisitor(any(ComplexNewsParser.class), eq("testContent"), any(Logger.class));
        }

    }

    /**
     * Ensuring that a COMPLEX and URL parse format properly calls the ComplexNewsParser and passes data correctly
     * @result the SourceFormat properly calls acceptVisitor while passing in the expected arguments
     */
    @Test
    void testComplexNewsParsingFromURL() throws IOException {
        SourceFormat sf = Mockito.mock(SourceFormat.class);
        Utils utils = Mockito.spy(new Utils());

        when(sf.getFormat()).thenReturn(SourceFormat.Format.COMPLEX);
        when(sf.getSource()).thenReturn(SourceFormat.Source.URL);
        
        doReturn("urlContent").when(utils).apiCall();

        utils.callSourceFormat(sf);

        verify(sf).acceptVisitor(isA(ComplexNewsParser.class), eq("urlContent"), isA(Logger.class));
    }
   
}
