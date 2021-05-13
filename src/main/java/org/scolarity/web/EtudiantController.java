package org.scolarity.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.scolarity.entities.Etudiant;
import org.scolarity.entities.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/etudiants")
public class EtudiantController {
     @Autowired
     private EtudiantRepository er;
     @Value("${dir.images}")
     private String imageDir;
     @RequestMapping("/Index")
     public String index(Model model, 
    		 @RequestParam(name="page",defaultValue="0") int p,
    		 @RequestParam(name="motCle",defaultValue="")String mc) {
    	 Page<Etudiant> etudiants = er.findStudent("%"+mc+"%",PageRequest.of(p,5));
    	 int pagesCount=etudiants.getTotalPages();
    	 int[] pages=new int[pagesCount];
    	 for(int i=0;i<pagesCount;i++)pages[i]=i;
    	 model.addAttribute("pageCorrante",p);
    	 model.addAttribute("pages", pages);
    	 model.addAttribute("pageEtudiants", etudiants);
    	 model.addAttribute("motCle", mc); 
    	 return "Etudiants";
     }
     @RequestMapping(value="/form", method=RequestMethod.GET)
     public String formEtudiant(Model model) {
    	 Etudiant etudiant=new Etudiant();
    	 model.addAttribute("etudiant", etudiant);
    	 return "FormEtudiant";
     }
     @RequestMapping(value="/SaveEtudiant", method=RequestMethod.POST)
     public String save(@Validated Etudiant et, BindingResult br,
    		 @RequestParam(name="picture") MultipartFile file ) {
    	 if(br.hasErrors()) {
    		 return "FormEtudiant";
    	 }
    	 if(!(file.isEmpty())) { et.setPhoto(file.getOriginalFilename());}
    	 er.save(et);
    	 if(!(file.isEmpty())) {
    		 et.setPhoto(file.getOriginalFilename());
    		 try {
    			 
				file.transferTo(new File(imageDir+et.getId()));
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	 }
    	 
    	 return "redirect:Index";
     }
     
     @RequestMapping(value="/Delete",method=RequestMethod.GET)
     public String delete(Long id) {
    	 er.deleteById(id);
    	  return "redirect:Index";
     }
     @RequestMapping(value="/edit",method=RequestMethod.GET)
     public String edit(Long id,Model model) {
    	 Etudiant et=er.getOne(id);
    	 model.addAttribute("etudiant", et);
    	   return "EditEtudiant";
     }
     //akana an'lay image any am directory_ny
     @RequestMapping(value="/getPhoto",
    		 produces=MediaType.IMAGE_JPEG_VALUE)
     @ResponseBody
     public byte[] getPhoto(Long id) throws FileNotFoundException, IOException {
    	 File f= new File(imageDir+id);
    	 
    	 return IOUtils.toByteArray(new FileInputStream(f));
     }
     
}
