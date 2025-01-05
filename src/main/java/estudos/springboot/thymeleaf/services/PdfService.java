package estudos.springboot.thymeleaf.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import estudos.springboot.thymeleaf.exceptions.LivroNotFoundException;

@Service
public class PdfService {
	
	private static final String PDF_RESOURCES = "/recursos/";
	
	@Autowired
	LivroService livroService;
	
	@Autowired
    private SpringTemplateEngine templateEngine;

    public File generatePdf(Long codigo) throws Exception{
        Context context = getContext(codigo);
        String html = loadAndFillTemplate(context);
        return renderPdf(html);
    }

    private File renderPdf(String html) throws Exception {
        File file = File.createTempFile("livros", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }
    
    private Context getContext(Long codigo) throws LivroNotFoundException {
        Context context = new Context();
        context.setVariable("livro", livroService.validarSeLivroExiste(codigo));
        return context;
    }


    private String loadAndFillTemplate(Context context) {
        return templateEngine.process("livro/PdfLivros", context);
    }

}
