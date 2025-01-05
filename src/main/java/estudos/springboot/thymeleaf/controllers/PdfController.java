package estudos.springboot.thymeleaf.controllers;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import estudos.springboot.thymeleaf.services.PdfService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @GetMapping("/gerar-pdf")
	public void downloadPdf(HttpServletResponse response, @RequestParam("codigo") Long codigo) {
		try {
			Path file = Paths.get(pdfService.generatePdf(codigo).getAbsolutePath());
			if (Files.exists(file)) {
				response.setContentType("application/pdf");
				response.addHeader("Content-Disposition", "attachment; filename=" + file.getFileName());
				Files.copy(file, response.getOutputStream());
				response.getOutputStream().flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
