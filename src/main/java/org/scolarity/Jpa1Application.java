package org.scolarity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.scolarity.entities.Etudiant;
import org.scolarity.entities.EtudiantRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import lombok.ToString;


@SpringBootApplication
public class Jpa1Application {

	public static void main(String[] args) throws ParseException {
		ApplicationContext ctx=SpringApplication.run(Jpa1Application.class, args);
		EtudiantRepository er = ctx.getBean(EtudiantRepository.class);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 1; i < 12; i++) {
			
		
		er.save(new Etudiant("Sitraka",df.parse("1994-"+Integer.toString(i)+"-22"),
				"feno@gmail.com","fns.jpg"));
		er.save(new Etudiant("Fenofff",df.parse("1994-"+Integer.toString(i)+"-22"),
				"Sitraka@gmail.com","fns2.jpg"));
		}
		
	}

	

}
