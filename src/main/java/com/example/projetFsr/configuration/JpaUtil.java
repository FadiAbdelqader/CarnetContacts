package com.example.projetFsr.configuration;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEmf() {
        if(emf == null){
            emf = Persistence.createEntityManagerFactory("projetFsr");
        }
        return emf;
    }

    public static void close(){
        if(emf!=null){
            emf.close();
            emf=null;
        }
    }

}

