/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt;

import entities.Publication;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author elis
 */
public class Projekt {

    public boolean publicationUpdate(String isbn, Double price, String name) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("projektPU");
        EntityManager em = emf.createEntityManager();
        Publication p = em.find(Publication.class, isbn);
        if (p == null) {
            p = new Publication();
            p.setIsbn(isbn);
            p.setName(name);
            p.setPrice(price);
        } else {
            if (p.getName() != null && name != null && !name.equals(p.getName())) { //UT20-21
                return false;
            }
            if (name != null && p.getName() == null) { //UT31
                p.setName(name);

            }
            if (price != null) {
                p.setPrice(price);

            }
        }
        persist(em, p);
        return true;
    }

    public void priceListUpdate(Map<String, Double> priceList) {
        if (priceList == null) {  //UT81 
            return;
        }
        for (Map.Entry<String, Double> entry : priceList.entrySet()) {
            publicationUpdate(entry.getKey(), entry.getValue(), null);
        }
    }

    public static void persist(EntityManager em, Object object) {
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        }
        finally {
            em.close();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

}
